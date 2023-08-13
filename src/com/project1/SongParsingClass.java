package com.project1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SongParsingClass {
	

	public SongParsingClass() {}//기본 생성자
		
	//파일을 읽어와서 데이터베이스에 삽입까지 처리
	static ArrayList<SongDataClass> SongParsing(ArrayList<SongDataClass> songlist) {
		
		SongDataClass songdataclass = null;
		
		
		String uri = "C:\\Users\\chc68\\Downloads\\음악저작물API목록.txt";
		FileReader filereader = null;
		BufferedReader bufferedreader = null;
		
		try {
			filereader = new FileReader(uri);
			bufferedreader = new BufferedReader(filereader);
			
			String tempsentence = "";
			while((tempsentence = bufferedreader.readLine()) != null) {
				songdataclass = new SongDataClass();
				songdataclass.sentence = tempsentence.split(",");
				
				if(songdataclass.sentence[0].equals("저작물명")) {//데이터 첫번째 줄 무시
					continue;
				}
				songdataclass.song = songdataclass.sentence[0];
				songdataclass.album = songdataclass.sentence[1];
				songdataclass.artist = songdataclass.sentence[2];
				songdataclass.music_distribution_company = songdataclass.sentence[3];
				songdataclass.company = songdataclass.sentence[4];
				
				songlist.add(songdataclass);
				DBClass.insert(songdataclass.song,
						songdataclass.album,
						songdataclass.artist,
						songdataclass.music_distribution_company,
						songdataclass.company);
				System.out.println(songdataclass.toString()+" insert complete!");
				songdataclass = null;
				tempsentence = "";
			}
					
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException : "+e.getMessage());
		}catch (IOException e) {
			System.out.println("IOException : "+e.getMessage());
		}
		
		return songlist; 
		
	}

}
