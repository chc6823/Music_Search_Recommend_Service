package com.project1;

public interface Querys {
	
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/db_music?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&cacheServerConfiguration=false";
	String id_mysql = "root";
	String pw_mysql = "0000";
	
	String insertquery = "insert into music(song,album,artist,"
			+ "music_distribution_company,company) values(?,?,?,?,?)";
	
	String searchquery = "select * from music where song like CONCAT('%',?,'%')"
			+ "or album like CONCAT('%',?,'%') or artist like CONCAT('%',?,'%') or music_distribution_company like CONCAT('%',?,'%')"
			+ "or company like CONCAT('%',?,'%')";
	
	String selectAllQuery = "select * from music";
	
	String selectOneQuery = "select song,artist from music where song_id = ?";
	
	String userinsertquery = "insert into user(user_id) values(?)";
	
	String userid_check_query = "select song from music where song_id = ?";
	String my_song_rating_insert_query = "insert into user_rating(user_id,song_id,rating) values(1,?,?)";

	String getUserRatingsSelectQuery = "select song_id,rating from user_rating where user_id = 1";
	
	String getAllUserRatingsQuery = "select user_id,song_id,rating from user_rating where user_id = ?";
	
	String update_my_song_rating_query = "update user_rating set rating = ? where user_id = 1 and song_id = ?";
	
	String updateUserRatingQuery = "UPDATE user_rating SET rating = ? WHERE user_id = ? AND song_id = ?";

}


//create table music(
//song_id int(10) auto_increment,
//song varchar(100) not null,
//album varchar(100),
//artist varchar(100),
//music_distribution_company varchar(100),
//company varchar(100),
//PRIMARY KEY(song_id));
//
//create table User(
//user_id int(10) auto_increment PRIMARY KEY);
//
//create table User_rating(
//rating_id int(3) auto_increment primary key,
//user_id int(10),
//song_id int(10),
//rating float(1),
//foreign key(user_id) references User(user_id),
//foreign key(song_id) references music(song_id));
