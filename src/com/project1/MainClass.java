package com.project1;

import java.util.Scanner;

public class MainClass {
	
	
	//Controller
	public static void main(String[] args) {
		
		DBClass.MySQLConnect();
		
		Scanner scanner = new Scanner(System.in);
		System.out.println();
		
		//menu 
		boolean status = true;
		while(status) {
			System.out.println("음원 검색 및 추천 서비스");
			System.out.println("원하는 서비스를 선택하세요");
			System.out.println("1.전체 음원 열람 2. 음원 검색 3. 음원 추천 4. 종료");
			System.out.print("입력 : ");
			
			int select = scanner.nextInt();
			
			switch(select) {
			
			case 1: //1.전체 음원 열람
				DBClass.selectAll();
				System.out.println();
				break;
			case 2: //2. 음원 검색
				System.out.print("검색어를 입력해주세요 : ");
				String search = scanner.next();
				DBClass.search(search);
				System.out.println();
				break;
			case 3: //3.음원 추천
				System.out.println("추천 서비스!");
				DBClass.recommendmusic();
				System.out.println();
				break;
			case 4: //4.종료
				status = false;
				System.out.println();
				break;
			
			}
			
		}
		scanner.close();
		System.out.println("음원 검색 및 추천 서비스 종료");

	}

}
