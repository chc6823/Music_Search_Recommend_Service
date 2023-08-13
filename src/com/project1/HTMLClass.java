package com.project1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

//View
public class HTMLClass {
	
	static String createHTML(ArrayList<SongDataClass> songs) {
	    String tags = "";
	    tags += "<html>";
	    tags += "<head><title>음원저작물목록</title></head>";

	    tags += "<body>";
	    tags += "<table>";
	    tags += "<caption>음원저작물목록</caption>";
	    tags += "<colgroup>";
	    tags += "<col style='width: 20%;'>";
	    tags += "<col style='width: 20%;'>";
	    tags += "<col style='width: 20%;'>";
	    tags += "<col style='width: 20%;'>";
	    tags += "<col style='width: 20%;'>";
	    tags += "</colgroup>";

	    tags += "<thead>";
	    tags += "<tr>";
	    tags += "<th>노래</th>";
	    tags += "<th>앨범</th>";
	    tags += "<th>아티스트</th>";
	    tags += "<th>배급사</th>";
	    tags += "<th>제작사</th>";
	    tags += "</tr>";
	    tags += "</thead>";

	    tags += "<tbody>";

	    for (SongDataClass song : songs) {
	        tags += "<tr>";
	        tags += "<td>" + song.song + "</td>";
	        tags += "<td>" + song.album + "</td>";
	        tags += "<td>" + song.artist + "</td>";
	        tags += "<td>" + song.music_distribution_company + "</td>";
	        tags += "<td>" + song.company + "</td>";
	        tags += "</tr>";
	    }

	    tags += "</tbody>";
	    tags += "</table>";
	    tags += "</body>";
	    tags += "</html>";
	    return tags;
	}
	
	static void saveHTML(String tags) {
	    String uri = "C:\\Users\\chc68\\filetest\\음원 저작물 리스트.html";
	    FileWriter fw = null;

	    try {
	        fw = new FileWriter(uri);
	        fw.write(tags);
	    } catch (IOException e) {
	        System.out.println("saveHTML() ==> " + e.getMessage());
	    } finally {
	        try {
	            if (fw != null) {
	                fw.close();
	            }
	        } catch (IOException e) {
	            System.out.println("saveHTML() finally ==> " + e.getMessage());
	        }
	    }
	}

}
