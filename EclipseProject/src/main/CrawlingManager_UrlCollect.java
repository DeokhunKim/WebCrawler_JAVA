package main;

public class CrawlingManager_UrlCollect extends CrawlingManager
{
	public void StartCrawling() 
	{
		Do() ;		
	}	
	public void Do()
	{
		System.out.println("Num queue: "+ GetNumOfQueue() + " / Lv." + m_currentPage.GetTableFeature().level + " / page: " + m_currentPage.GetTableFeature().url );
		
	}

}
