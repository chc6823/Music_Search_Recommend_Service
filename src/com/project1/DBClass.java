package com.project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

//Model,MySQL
public class DBClass implements Querys {
	
	static Connection connection = null;
	static Scanner scanner = null;
	
	public DBClass() {} //기본 생성자
	
	public static void MySQLConnect() {	
		//stage1 - loading JDBC Driver
		try {
			Class.forName(driver);
			System.out.println("JDBC 드라이버 로딩 성공!");
			
			System.out.println(url);
			connection = DriverManager.getConnection(url,id_mysql,pw_mysql);
		} catch (ClassNotFoundException e) {
			System.out.println("Error Loading Driver : "+e.getMessage());
		}catch (SQLException e) {
			System.out.println("SQLException : "+e.getMessage());
		}
	}//MySQLConnect()
	
	public static void selectAll() {
		//레코드 print 담당 함수 
		//HTML 형태로 리턴
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<SongDataClass> songs = new ArrayList<SongDataClass>();
		SongDataClass songdata = null;
		
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(selectAllQuery);

			while (rs.next()) {
				songdata = new SongDataClass();
				String song = rs.getString("song");
				String album = rs.getString("album");
				String artist = rs.getString("artist");
				String music_distribution_company = rs.getString("music_distribution_company");
				String company = rs.getString("company");
				
				songdata.setEverything(song, album, artist, music_distribution_company, company);
				
				songs.add(songdata);
			}

		} catch (SQLException e) {
			System.out.println("selectAll()  ERR : " + e.getMessage());
		} finally {
			close(stmt, rs);
		}
		
		HTMLClass.saveHTML(HTMLClass.createHTML(songs));
		System.out.println("현재 노래 조회 성공 !!!");
	
	} // selectAll() END
	
	public static String selectOne(int song_id) {
		
		String songandartist = null;
		
		PreparedStatement preparedstatement = null;
		ResultSet resultset = null;
		
		try {
			preparedstatement = connection.prepareStatement(selectOneQuery);
			preparedstatement.setInt(1, song_id);
			resultset = preparedstatement.executeQuery();
			
			while(resultset.next()) {
				String song = resultset.getString("song");
				String artist = resultset.getString("artist");
				
				songandartist = song +"     "+ artist;
				
			}
		} catch (SQLException e) {
			System.out.println("Error in selectOne() : "+e.getMessage());
		}finally {
			close(preparedstatement,resultset);
		}
		
		return songandartist;
		
	}//selectOne()
	
	public static void insert(String song,String album,String artist,
			String music_distribution_company,String company) {
		
		PreparedStatement preparedstatement = null;
		//텍스트 파일로부터 파싱한 레코드 정보를 DB에 삽입한다.
		
		
		try {
			preparedstatement = connection.prepareStatement(insertquery);
			preparedstatement.setString(1, song);
			preparedstatement.setString(2, album);
			preparedstatement.setString(3, artist);
			preparedstatement.setString(4, music_distribution_company);
			preparedstatement.setString(5, company);
			preparedstatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("insert() Error : "+e.getMessage());
		} finally {
			System.out.println("");
			close(preparedstatement);
		}
		
	}
	
	public static void search(String search) {
		//1.search 문구를 포함한 검색결과가 존재하는지 확인
		PreparedStatement preparedstatement = null;
		ResultSet rs = null;
		
		//where 검색필드 like CONCAT('%',?,'%')으로 사용. 자바에서 %?%으로 사용하면 하나의 문자열로 인식하기
		//때문에 set으로 치환이 불가능하다. 
		
		try {
			preparedstatement = connection.prepareStatement(searchquery);
			for(int i = 1 ; i <= 5; i++) {
				preparedstatement.setString(i, search);
			}
			 rs = preparedstatement.executeQuery();
			 while (rs.next()) {
				String song = rs.getString("song");
				String album = rs.getString("album");
				String artist = rs.getString("artist");
				String music_distribution_company = rs.getString("music_distribution_company");
				String company = rs.getString("company");
				
				System.out.println("검색 결과 : "+song+" "+album+" "+artist+" "+music_distribution_company+" "+company);
			 }
			 
		} catch (SQLException e) {
			System.out.println("Error in search() : "+e.getMessage());
		}finally {
			close(preparedstatement);
		}
	}
		
	//methods overloading
	public static void close(PreparedStatement ptms) {
		try {
			ptms.close();
		} catch (SQLException e) {
			System.out.println("Error in closing pstmt: "+e.getMessage());
		}
	}
	
	public static void close(PreparedStatement ptms,ResultSet rs) {
		try {
			ptms.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error in closing pstmt,rs: "+e.getMessage());
		}
	}
	
	public static void close(Statement stmt,ResultSet rs) {
		try {
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error in closing stmt,rs: "+e.getMessage());
		}
	}

	public static void init_user() {
		//user 테이블에 100명의 user_id를 삽입한다.
		PreparedStatement preparedstatement = null;
		
		//유저 아이디를 DB에 삽입한다.
		
		
		try {
			for(int i = 1; i<=100 ;i++) {
				preparedstatement = connection.prepareStatement(userinsertquery);
				preparedstatement.setInt(1, i);
				preparedstatement.executeUpdate();
				System.out.println("user "+i+" inserted!");
			}
			
		} catch (SQLException e) {
			System.out.println("user() Error : "+e.getMessage());
		} finally {
			System.out.println("");
			close(preparedstatement);
		}
		
	}

	public static void init_user_rating() {
		//유저 데이터 무작위로 생성 및 삽입
		System.out.println("유저 데이터 삽입을 시작합니다!");
		
		String init_user_rating_insert_query = "insert into user_rating(user_id,song_id,rating)"
				+ " values(?,?,?)";
		PreparedStatement preparedstatement = null;
		//유저 99명마다 모든 1698개의 노래에 대해 평가 데이터를 삽입
		for(int userindex = 2 ;userindex<=100;userindex++ ) {
			for(int song_index = 1; song_index<=1698;song_index++) {
				try {
					preparedstatement = connection.prepareStatement(init_user_rating_insert_query);
					preparedstatement.setInt(1, userindex); //user_id
					preparedstatement.setInt(2, song_index); //song_id
					preparedstatement.setDouble(3, getRandomNumber());
					preparedstatement.executeUpdate();
				} catch (SQLException e) {
					System.out.println("SQLException in init_user_rating() : "+e.getMessage());
				}finally {
					close(preparedstatement);
				}
			}
			System.out.println("user "+userindex+" 사용자 데이터 입력완료!");
		}
		
		
		
	}

	public static double getRandomNumber() {
	    //long seed = System.currentTimeMillis(); // 현재 시간(milliseconds)을 시드로 사용
	    Random random = new Random();
	    double randomNumber = random.nextDouble() * 5; // 0 이상 5 미만의 무작위 실수 추출
	    return Math.round(randomNumber * 10) / 10.0; // 소수 둘째 자리까지 반올림
	}


	public static void init_my_song_rating() {
		//내 노래 평점 초기화 메소드(나는 user_id=1)
		
		//System.out.println("맞춤 음악 추천을 위해 노래마다 평점을 매겨주세요. 평점은 1~5점 사이로 매겨주세요.");
		
		// music 테이블에서 노래를 하나 씩 호출해서 평점을 입력받는다.
		// 이후 user_rating 테이블에 user_id=1로 각 노래의 평점을 입력하고 평점을 테이블에 insert한다.
		
		PreparedStatement preparedstatement1 = null;
		PreparedStatement preparedstatement2 = null;
		ResultSet resultset = null;
		//scanner = new Scanner(System.in);
			
			try {
				for(int i = 1;i<=1698;i++) {
					preparedstatement1 = connection.prepareStatement(userid_check_query);
					preparedstatement1.setInt(1,i);
					resultset = preparedstatement1.executeQuery();
					resultset.next();
					
					Double song_score = getRandomNumber();
					System.out.print(resultset.getString(1)+" : " + song_score);
					
					preparedstatement2 = connection.prepareStatement(my_song_rating_insert_query);
					preparedstatement2.setInt(1, i); //insert song_id
					preparedstatement2.setDouble(2, song_score); //insert rating
					preparedstatement2.executeUpdate();
					close(preparedstatement2);
					
					System.out.println(resultset.getString(1) + " 평가 완료!");
					
					
				}
			} catch (SQLException e) {
				System.out.println("SQLException in init_my_song_rating(): "+e.getMessage());
			}finally {
				close(preparedstatement1,resultset);
				close(preparedstatement2);
			}
	}

	public static void recommendmusic() {
		
		// 사용자의 모든 노래 평점 데이터,Map<song_id, rating>
        Map<Integer, Float> userRatings = getUserRatings();

        // 나머지 99명의 모든 노래 평점 데이터 Map <user_id,Map<song_id, rating>>
        Map<Integer, Map<Integer, Float>> allUserRatings = getAllUserRatings();

        // 추천 알고리즘 실행
        List<Integer> recommendedSongIds = recommendSongs(userRatings, allUserRatings);

        // 추천된 노래 ID 출력
        System.out.println("사용자님을 위한 노래 모음입니다");
        for (int songId : recommendedSongIds) {
            System.out.println(selectOne(songId));
        }
		
	}

	private static List<Integer> recommendSongs(Map<Integer, Float> userRatings,
	        Map<Integer, Map<Integer, Float>> allUserRatings) {

	    // 추천 점수를 추적하기 위한 용도 songScores
		//Map<song_id,rating>
	    Map<Integer, Float> songScores = new HashMap<>();

	    // 현재 사용자와 다른 유저 간 유사도 계산
	    //Map<user_id,similarity>
	    Map<Integer, Float> userSimilarities = calculateUserSimilarities(userRatings, allUserRatings);

	    // 다른 사용자 평점에 대해 반복
	    for (Map.Entry<Integer, Map<Integer, Float>> entry : allUserRatings.entrySet()) {
	        int userId = entry.getKey();
	        Map<Integer, Float> ratings = entry.getValue();

	        // 현재 사용자가 entry 사용자와 비슷한지 체크
	        if (userSimilarities.containsKey(userId)) {
	            float similarity = userSimilarities.get(userId);

	            // 다른 사용자의 평점 데이터로 비교하며 체크
	            for (Map.Entry<Integer, Float> ratingEntry : ratings.entrySet()) {
	                int songId = ratingEntry.getKey();
	                float rating = ratingEntry.getValue();

	                float score = similarity * rating;

	                // 추천 점수 축적
	                float songScore = songScores.getOrDefault(songId, 0f);
	                songScore += score;
	                songScores.put(songId, songScore);
	            }
	        }
	    }

	    List<Integer> recommendedSongs = new ArrayList<>(songScores.keySet());
	    recommendedSongs.sort(Comparator.comparingDouble(songScores::get).reversed());

	    if (recommendedSongs.size() > 10) {
	        recommendedSongs = recommendedSongs.subList(0, 10);
	    }

	    return recommendedSongs;
	}

	private static Map<Integer, Float> calculateUserSimilarities(Map<Integer, Float> userRatings,
	        Map<Integer, Map<Integer, Float>> allUserRatings) {
		//Map<userId,similarity>
	    Map<Integer, Float> userSimilarities = new HashMap<>();

	    // 현재 사용자와 다른 사용자들 간의 유사도 계산
	    for (Map.Entry<Integer, Map<Integer, Float>> entry : allUserRatings.entrySet()) {
	        int userId = entry.getKey();
	        Map<Integer, Float> ratings = entry.getValue();

	        // 대상 사용자는 스킵
	        if (userId == 1) {
	            continue;
	        }

	        float similarity = calculateSimilarity(userRatings, ratings);
	        userSimilarities.put(userId, similarity);
	    }

	    return userSimilarities;
	}

	private static float calculateSimilarity(Map<Integer, Float> user1Ratings, Map<Integer, Float> user2Ratings) {
	   //두 사용자의 평점 벡터간 코사인 유사도 계싼
		float dotProduct = 0;
		
		for (Map.Entry<Integer, Float> ratingEntry : user1Ratings.entrySet()) {
	        int songId = ratingEntry.getKey();
	        float user1Rating = ratingEntry.getValue();
	        if (user2Ratings.containsKey(songId)) {
	            float user2Rating = user2Ratings.get(songId);
	            dotProduct += user1Rating * user2Rating;
	        }
	    }
		
		 // 두 사용자의 평가 점수 벡터의 크기 계싼
	    float magnitudeUser1 = calculateMagnitude(user1Ratings);
	    float magnitudeUser2 = calculateMagnitude(user2Ratings);
	    
	    //코사인 유사도 계싼
	    float similarity = dotProduct / (magnitudeUser1 * magnitudeUser2);
	    
	    return similarity;
	}

	private static float calculateMagnitude(Map<Integer, Float> ratings) {
		float magnitude = 0;
		
		for (float rating : ratings.values()) {
	        magnitude += Math.pow(rating, 2);
	    }
		
		return (float) Math.sqrt(magnitude);
		
	}

	private static Map<Integer, Map<Integer, Float>> getAllUserRatings() {
		 // 나머지 99명의 모든 노래 평점 데이터를 가져오는 로직
		//Map< user_id , Map<song_id,rating> >
		Map<Integer, Map<Integer, Float>> allUserRatings = new HashMap<>();
		
		//랜덤하게 일부 다른 사용자 평점데이터 재설정
		updateOtherUserRatings();
		
		// allUserRatings.put(user_id, Map<song_id,rating>);
		// user_rating 테이블에서 user_id= 2 ~ 100인 사용자의 평점을 다 가져와서 allUserRatings에 삽입
		PreparedStatement preparedstatement = null;
		ResultSet resultset = null;
		
		for(int i =2;i<=100;i++) {
			
			try {
				Map<Integer, Float> otheruserRatings = new HashMap<>();
				preparedstatement = connection.prepareStatement(getAllUserRatingsQuery);
				preparedstatement.setInt(1, i);
				resultset = preparedstatement.executeQuery();
				
				while(resultset.next()) {
					int user_id = resultset.getInt("user_id");
					int song_id = resultset.getInt("song_id");
					Float rating = resultset.getFloat("rating");
					
					otheruserRatings.put(song_id,rating);
					allUserRatings.put(user_id, otheruserRatings);
				}
			}catch(SQLException e) {
				System.out.println("Error in getAllUserRatings() : "+e.getMessage());
			}finally {
				close(preparedstatement,resultset);
			}
		}
		return allUserRatings;
	}//getAllUserRatings() END

	private static Map<Integer, Float> getUserRatings() {
		// 사용자의 모든 노래 평점 데이터를 가져오는 로직
		 Map<Integer, Float> userRatings = new HashMap<>();
		 
		 //나 자신 평점 데이터 재설정
		update_my_song_rating();
		 
		 // userRatings.put(song_id, rating);
		 // user_rating 테이블에서 user_id=1인 사용자의 평점을 다 가져와서 userratings에 삽입
		 
		 Statement statement = null;
		 ResultSet resultset = null;
		 
		 try {
			statement = connection.createStatement();
			resultset = statement.executeQuery(getUserRatingsSelectQuery);
			
			while(resultset.next()) {
				int song_id = resultset.getInt("song_id");
				Float rating = resultset.getFloat("rating");
				
				userRatings.put(song_id,rating);
			}
		} catch (SQLException e) {
			System.out.println("Error in getUserRatings() : "+e.getMessage());
		}finally {
			close(statement,resultset);
		}
		return userRatings;
	}//getUserRatings() END
	
	public static void update_my_song_rating() {
	    PreparedStatement preparedStatement = null;

	    try {
	        // Disable autocommit
	        connection.setAutoCommit(false);

	        for (int i = 1; i <= 1698; i++) {
	            double song_score = getRandomNumber();
	            preparedStatement = connection.prepareStatement(update_my_song_rating_query);
	            preparedStatement.setDouble(1, song_score);
	            preparedStatement.setInt(2, i);

	            preparedStatement.executeUpdate();
	        }

	        // Commit the changes
	        connection.commit();
	    } catch (SQLException e) {
	        System.out.println("SQLException in update_my_song_rating(): " + e.getMessage());
	        // Rollback the changes if an exception occurs
	        try {
	            connection.rollback();
	        } catch (SQLException ex) {
	            System.out.println("SQLException in update_my_song_rating() - Rollback: " + ex.getMessage());
	        }
	    } finally {
	        close(preparedStatement);
	        // Enable autocommit
	        try {
	            connection.setAutoCommit(true);
	        } catch (SQLException ex) {
	            System.out.println("SQLException in update_my_song_rating() - Enable autocommit: " + ex.getMessage());
	        }
	    }
	}//update_my_song_rating() END
	
	private static void updateOtherUserRatings() {
	    PreparedStatement preparedStatement = null;

	    try {
	        // Disable autocommit
	        connection.setAutoCommit(false);

	        for (int userId = 2; userId <= 100; userId++) {
	            // 무작위로 선택된 사용자의 평점 데이터를 수정
	            if (Math.random() < 0.6) {  //일정확률로 다른 사용자의 평점 데이터를 수정함.
	            	for (int songId = 1; songId <= 1698; songId++) {
	                    // 무작위로 선택된 평점 데이터를 수정
	                    if (Math.random() < 0.5) {  // 평점 데이터 수정 확률을 50%로 설정
	                        Double rating = getRandomNumber();

	                        // 데이터베이스의 평점 데이터를 무작위로 수정
	                        preparedStatement = connection.prepareStatement(updateUserRatingQuery);
	                        preparedStatement.setDouble(1, rating);
	                        preparedStatement.setInt(2, userId);
	                        preparedStatement.setInt(3, songId);

	                        preparedStatement.executeUpdate();
	                    }
	                }
	            }
	        }

	        // Commit the changes
	        connection.commit();
	    } catch (SQLException e) {
	        System.out.println("SQLException in updateOtherUserRatings(): " + e.getMessage());
	    } finally {
	        close(preparedStatement);
	        // Enable autocommit
	        try {
	            connection.setAutoCommit(true);
	        } catch (SQLException ex) {
	            System.out.println("SQLException in updateOtherUserRatings() - Enable autocommit: " + ex.getMessage());
	        }
	    }//updateOtherUserRatings() END
	}
}
