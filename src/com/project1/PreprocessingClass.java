package com.project1;

import java.util.ArrayList;

public class PreprocessingClass {
	
	static ArrayList<SongDataClass> songlist = null;

	public static void main(String[] args) {
		//0 : DB 연결 및 음악 table에 음악 데이터 삽입
		
		songlist = new ArrayList<SongDataClass>();
		
		DBClass.MySQLConnect();
		
		songlist = SongParsingClass.SongParsing(songlist); 
		
		//내 음악 평점 삽입 및 무작위로 유저 목록 및 다른 유저 평점 데이터 삽입
		DBClass.init_my_song_rating();
		DBClass.init_user();
		DBClass.init_user_rating();
	}

}
