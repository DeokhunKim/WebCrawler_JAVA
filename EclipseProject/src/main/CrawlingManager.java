package main;


import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import main.Configure.FileOpenMode;
import main.LogFileManager.TableName;

public class CrawlingManager {
	private static final String SQLITE_JDBC_DRIVER = "org.sqlite.JDBC" ;
	private static final String SQLITE_PREPOS = "jdbc:sqlite:" ;
	
	private LogFileManager m_logMng = new LogFileManager() ;
	private Configure m_condition = new Configure() ;
	
	private Queue<WebPage> m_pageQueue = new LinkedList<WebPage>() ;
	WebPage m_currentPage = null ;
	
	public boolean Initialize( String conditionFilePath )
	{
		if( false == SetConfig( conditionFilePath ) )
		{
			return false ;
		}
		
		if( m_condition.getFileOpenMode() == FileOpenMode.UseCurrentTimeFile )
		{
			if( false == m_logMng.Open_CurTime( m_condition.getLogFileDirPath() ) )
			{
				return false ;
			}
			if( false == m_logMng.CreateBaseTable() || false == m_logMng.PrepareBaseTable() )
			{
				return false ;
			}
		}
		else 
		{
			if( m_condition.getFileOpenMode() == FileOpenMode.UseUserInputNameFile )
			{
				if( false == m_logMng.CreateBaseTable() || false == m_logMng.PrepareBaseTable() )
				{
					return false ;
				}
			}
			else /*if( m_condition.getFileOpenMode() == FileOpenMode.UseLagacyLogFile )*/
			{
				File file = new File(m_condition.getLogFileDirPath() 
						+ "/" + m_condition.getLogFileName() ) ;
				if( false == file.exists() )
				{
					return false ;
				}
			}
		}
		
		m_currentPage = new WebPage
				( m_condition.startUrl, m_condition.startUrl, m_condition.startUrl, 0 ) ;
		m_currentPage.ReadPage() ;
		
		return true ;
	}
	
	private boolean SetConfig(String conditionFilePath)
	{
		try 
		{
			Class.forName(SQLITE_JDBC_DRIVER) ;
			Connection con = DriverManager.getConnection( SQLITE_PREPOS + conditionFilePath ) ;
			Statement stmt = con.createStatement();
			String query = "SELECT Option, Value FROM Configure" ;
			ResultSet rs = stmt.executeQuery(query) ;
			
			while( rs.next() )
			{
				String option = rs.getString(1) ;
				String value  = rs.getString(2) ;
				if( null == value )
				{
					value = "" ;
				}
				
				if( option.equals("StartUrl") )
				{
					if( false == value.isEmpty()
						&& value.substring(value.length()-1).equals("/") )
					{
						value = value.substring( 0, value.length()-1 ) ;
					}
					m_condition.setStartUrl(value) ;
				}
				else if( option.equals("LogFileDirectoryPath") )
				{
					m_condition.setLogFileDirPath(value) ;					
				}
				else if( option.equals("LogFilenName") )
				{
					if( false == value.isEmpty() )
					{
						m_condition.setFileOpenMode( FileOpenMode.UseUserInputNameFile ) ;
					}
					m_condition.setLogFileName(value) ;					
				}
				else if( option.equals("AccessWatingTime") )
				{
					m_condition.setWatingTime(Integer.parseInt(value)) ; 
				}
				else if( option.equals("LimitDeepLevel") )
				{
					m_condition.setNumDeepLevel(Integer.parseInt(value)) ; 
				}
				else if( option.equals("isUseLagacyLogFile") )
				{
					if( false == value.equals("0") )
					{
						m_condition.setFileOpenMode(FileOpenMode.UseLagacyLogFile) ;
					}
				}
				else if( option.equals("isTryReconnectLagacyFailUrl") )
				{
					if( false == value.equals("0") )
					{
						m_condition.setTryReconnectLagacyFailUrl(true) ;
					}
				}
				else if( option.equals("isCrawlingOtherWebsite") )
				{
					if( false == value.equals("0") )
					{
						m_condition.setCrawlingOtherWebsite(true) ;
					}					
				}
				else
				{
					System.out.println("[ERROR] Unknown option") ;
					return false ;					
				}
			}			
		} catch (SQLException | ClassNotFoundException e) 
		{
			e.printStackTrace();
		}	
		
		return true ;
	}
	
	private boolean NextPage()
	{
		// ?????? ?????? ????????? ?????? ?????? (?????? ??????????????? ?????? 0?????? ???????????? ???)
		WebPageTableFeature feature = m_currentPage.GetTableFeature() ;
		if( feature.level <= m_condition.getNumDeepLevel() )
		{
			Set<String> urlSet = m_currentPage.GetNextUrlSet() ;
			int numUrlPerPage = 1; //TODO Remove test code
			for( String url : urlSet )
			{
				if( false == m_logMng.isSearchedUrl(url) )
				{
					WebPage newWebPage = new WebPage(url, feature.rootUrl, 
							feature.url, feature.level + 1 ) ;
					m_pageQueue.add(newWebPage) ;
					
					//TODO Remove test code
					numUrlPerPage ++ ;
					if( numUrlPerPage > 3 )
					{
						break ;
					}
					
				}				
			}
		}
		
		// ???????????? or ?????? ?????? ??????
		TableName tableName = null ;
		if( m_currentPage.GetConnectStatus() )
		{
			tableName = TableName.SuccessWebPage ;
		}
		else
		{
			tableName = TableName.FailedWebPage ;
		}
		m_logMng.InsertWebPageTable( tableName, feature ) ;
		
		
		
		// ?????? ????????? ???????????? ????????????
		// TODO ????????? ???????????? ?????? ??? ?????? ?????????
		m_currentPage = m_pageQueue.poll() ;
		if( null == m_currentPage )
		{ 
			return false ;				
		}
		m_currentPage.ReadPage() ;
		
		return true ;
	}
	
	public void Do()
	{
		System.out.println("Do Super Class");

	}
	
	public void Start() 
	{
		while( NextPage() )
		{
			Do() ;
			try {
				TimeUnit.MILLISECONDS.sleep(2000) ;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	protected int GetNumOfQueue()
	{
		return m_pageQueue.size() ;
	}

	
	

	
	
}
