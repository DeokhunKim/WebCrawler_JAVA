package junit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.LogFileManager;
import main.LogFileManager.TableName;
import main.WebPageTableFeature;
import main.WebPage;

class junit_LogFileManager {

	@Test
	void FileOpenCloseTest_CurrentTime() 
	{
		LogFileManager logMng = new LogFileManager() ;
		boolean res = logMng.Open_CurTime("") ;
		assertTrue(res) ;
		
		res = logMng.CreateBaseTable();
		assertTrue(res) ;
		
		res = logMng.PrepareBaseTable();
		assertTrue(res) ;
		
		WebPageTableFeature webFeat = new WebPageTableFeature() ;
		webFeat.level = 0 ;
		webFeat.prevUrl = "prevURL" ;
		webFeat.responseTime = 500 ;
		webFeat.rootUrl = "rootURL" ;
		webFeat.requestNum = 0 ;
		webFeat.url = "curURL" ;

		logMng.InsertWebPageTable( TableName.SuccessWebPage , webFeat ) ;
		logMng.InsertWebPageTable( TableName.SuccessWebPage , webFeat ) ;
		logMng.InsertWebPageTable( TableName.SuccessWebPage , webFeat ) ;
		logMng.InsertWebPageTable( TableName.SuccessWebPage , webFeat ) ;
		logMng.InsertWebPageTable( TableName.SuccessWebPage , webFeat ) ;
		logMng.InsertWebPageTable( TableName.SuccessWebPage , webFeat ) ;
		logMng.InsertWebPageTable( TableName.SuccessWebPage , webFeat ) ;
		logMng.InsertWebPageTable( TableName.SuccessWebPage , webFeat ) ;
		
		logMng.isSearchedUrl("curURL") ;
		logMng.isSearchedUrl("curURL") ;
		logMng.isSearchedUrl("curURLsdfsdf") ;
		logMng.isSearchedUrl("rootURL") ;
		
		res = logMng.Close() ;
		assertTrue(res) ;
	}

}
