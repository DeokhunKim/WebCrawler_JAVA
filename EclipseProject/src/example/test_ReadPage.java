package example;

import main.WebPage;

public class test_ReadPage {

	public static void main(String[] args) {
		System.out.println("test_ReadPage run");
		
		
		WebPage web = new WebPage("https://namu.wiki/w/대한민국","","",0) ;
		
		web.ReadPage() ;
		
		
		System.out.println("End") ;

	}

}
