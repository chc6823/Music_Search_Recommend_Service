package com.project1;

import java.util.Arrays;

public class SongDataClass {
	
	String[] sentence; //파싱한 문장
	String song; //저작물명
	String album; //앨범명
	String artist; //아티스트명
	String music_distribution_company; //대리중개사명
	String company; //제작사명
	
	public SongDataClass() {}//기본 생성자
	
	
	public void setEverything(String song,String album,String artist,String music_distribution_company
			,String company) {
		
		this.song = song;
		this.album = album;
		this.artist = artist;
		this.music_distribution_company = music_distribution_company;
		this.company = company;
		
	}
	
	//Getter & Setter
	public String[] getSentence() {
		return sentence;
	}



	public void setSentence(String[] sentence) {
		this.sentence = sentence;
	}



	public String getSong() {
		return song;
	}



	public void setSong(String song) {
		this.song = song;
	}



	public String getAlbum() {
		return album;
	}



	public void setAlbum(String album) {
		this.album = album;
	}



	public String getArtist() {
		return artist;
	}



	public void setArtist(String artist) {
		this.artist = artist;
	}



	public String getMusic_distribution_company() {
		return music_distribution_company;
	}



	public void setMusic_distribution_company(String music_distribution_company) {
		this.music_distribution_company = music_distribution_company;
	}



	public String getCompany() {
		return company;
	}



	public void setCompany(String company) {
		this.company = company;
	}



	@Override
	public String toString() {
		return "SongDataClass [sentence=" + Arrays.toString(sentence) + ", song=" + song + ", album=" + album
				+ ", artist=" + artist + ", music_distribution_company=" + music_distribution_company + ", company="
				+ company + "]";
	}
	
	

}
