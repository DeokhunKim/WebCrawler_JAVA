package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.HashMap;

public class LogFileManager {
	private static final String SQLITE_JDBC_DRIVER = "org.sqlite.JDBC" ;
	private static final String SQLITE_PREPOS = "jdbc:sqlite:" ;
	private Connection m_connection = null ;
	private PreparedStatement m_pstmtUrl = null ;
	private PreparedStatement m_pstmtSuccessUrl = null ;
	private PreparedStatement m_pstmtFailUrl = null ;
	private PreparedStatement m_pstmtSearchedUrl = null ;
	
	private Map<String,Integer> m_urlRecordMap = new HashMap<>() ;
	
	static public enum TableName
	{
		SuccessWebPage,
		FailedWebPage		
	}
	
	public boolean Open( String filePath )
	{
		try 
		{
			Class.forName(SQLITE_JDBC_DRIVER) ;
			m_connection = DriverManager.getConnection(filePath) ;
			
			return true ;
			
		} catch (SQLException | ClassNotFoundException e) 
		{
			e.printStackTrace();
			
			return false ;
		}				
	}
	
	public boolean Open_CurTime( String logFileDirPath )
	{
		if( false == logFileDirPath.equals("") 
				&& false == logFileDirPath.substring( logFileDirPath.length() - 1 ).equals("/") ) 
		{
			logFileDirPath += "/" ;
		}
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss" ) ;
		
		String filePath = SQLITE_PREPOS + logFileDirPath + "CrawlingLog_" 
		+ dataFormat.format( System.currentTimeMillis() ) + ".sqlite" ;
		
		return Open(filePath) ;
	}
	
	public boolean Close()
	{
		//Close connect
		try 
		{
			if( m_connection != null )
			{
				m_connection.close() ;
				return true ;
			}
			else
			{
				return true ;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace() ;
			return false ;
		}
		finally 
		{
			m_connection = null ; 
		}		
	}
	
	public boolean CreateBaseTable()
	{
		try 
		{
			java.sql.Statement stmt = m_connection.createStatement();
			stmt.execute(LogFileTable.CreateSQL_URLTable) ;
			stmt.execute(LogFileTable.CreateSQL_SuccessWebPageTable) ;
			stmt.execute(LogFileTable.CreateSQL_FailedWebPageTable) ;
		} catch (SQLException e1) 
		{
			e1.printStackTrace();
			return false ;
		}
		
		return true ;
	}
	
	public boolean PrepareBaseTable()
	{
		try 
		{
			m_pstmtUrl = m_connection.prepareStatement( 
					LogFileTable.InsertSQL_URLTable ) ;
			m_pstmtSuccessUrl = m_connection.prepareStatement( 
					LogFileTable.InsertSQL_SuccessWebPageTable ) ;
			m_pstmtFailUrl = m_connection.prepareStatement( 
					LogFileTable.InsertSQL_FailedWebPageTable ) ;
			m_pstmtSearchedUrl = m_connection.prepareStatement(
					LogFileTable.SelectSQL_SearchedUrl ) ;					
			
		} catch (SQLException e1) 
		{
			e1.printStackTrace();
			return false ;
		}
		
		return true ;
	}
	
	public boolean InsertWebPageTable( TableName tableName, WebPageTableFeature feature )
	{
		PreparedStatement pstmt = null ;
		if( tableName == TableName.SuccessWebPage )
		{
			pstmt = m_pstmtSuccessUrl ;
		}
		else /*if( tableName == TableName.FailedWebPage )*/
		{
			pstmt = m_pstmtFailUrl ;
		}
		
		try 
		{
			pstmt.setInt( 1, GetUrlRecord(feature.rootUrl) ) ;
			pstmt.setInt( 2, GetUrlRecord(feature.url) ) ;
			pstmt.setInt( 3, GetUrlRecord(feature.prevUrl) ) ;
			pstmt.setInt( 4, feature.level ) ;
			pstmt.setInt( 5, feature.responseTime ) ;
			pstmt.setInt( 6, 0 ) ;
			
			pstmt.executeUpdate() ;
			
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true ;
	}
	
	private int GetUrlRecord( String url )
	{
		if( m_urlRecordMap.containsKey(url) )
		{
			return m_urlRecordMap.get(url) ;
		}
		else
		{
			try 
			{
				m_pstmtUrl.setString( 1, url ) ;
				m_pstmtUrl.executeUpdate() ;
				
				// TODO extract
				String selectRecord = "select seq from sqlite_sequence where name = 'URL'" ;
				Statement stmt = m_connection.createStatement() ;
				ResultSet rs =  stmt.executeQuery(selectRecord) ;
				rs.next() ;
				int recordNum = rs.getInt(1) ;
				
				m_urlRecordMap.put( url, recordNum ) ;
				return recordNum ;
						
			} catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return 0 ;
	}
	
	public boolean isSearchedUrl( String url )
	{
		try 
		{
			m_pstmtSearchedUrl.setString( 1, url ) ;
			ResultSet rs = m_pstmtSearchedUrl.executeQuery() ;
			if( rs.next() )
			{
				m_pstmtSearchedUrl.clearParameters() ;
				return true ;
			}
			m_pstmtSearchedUrl.clearParameters() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false ;
	}
}
