package main;

public class WebCrawler {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("WebCrawler main method start");
		
		CrawlingManager manager = new CrawlingManager_UrlCollect() ;
		manager.Initialize("Config.sqlite") ;
		System.out.println("end initial");
		
		
		
		manager.Start();
		
		

	}

}
