package main;

public class LogFileTable 
{
	// Create table query
	public static String CreateSQL_URLTable 
	= "CREATE TABLE [URL] (" +
			"[Record]	INTEGER," +
			"[URL]	TEXT,"
			+ "PRIMARY KEY([Record] AUTOINCREMENT) ) " ;
	public static String CreateSQL_SuccessWebPageTable
	 = "CREATE TABLE [SuccessWebPage] ("
	 		+ "	[RootURL]	INTEGER,"
	 		+ "	[URL]	INTEGER,"
	 		+ "	[PrevURL]	INTEGER,"
	 		+ "	[Level]	INTEGER,"
	 		+ "	[ResponseTime]	INTEGER,"
	 		+ "	[RequestNum]	INTEGER)" ;
	public static String CreateSQL_FailedWebPageTable
	 = "CREATE TABLE [FailedWebPage] ("
	 		+ "	[RootURL]	INTEGER,"
	 		+ "	[URL]	INTEGER,"
	 		+ "	[PrevURL]	INTEGER,"
	 		+ "	[Level]	INTEGER,"
	 		+ "	[ResponseTime]	INTEGER,"
	 		+ "	[RequestNum]	INTEGER)" ;
	
	// Insert prepare query
	public static String InsertSQL_URLTable 
	= "INSERT INTO [URL] (URL) VALUES( ? )" ;
	public static String InsertSQL_SuccessWebPageTable
	= "INSERT INTO [SuccessWebPage] VALUES( ?, ?, ?, ?, ?, ? )" ;
	public static String InsertSQL_FailedWebPageTable
	= "INSERT INTO [FailedWebPage] VALUES( ?, ?, ?, ?, ?, ? )" ;
	
	
	// Select query
	public static String SelectSQL_SearchedUrl = "SELECT page.URL\n"
			+ "FROM URL, (SELECT URL FROM SuccessWebPage UNION SELECT URL FROM FailedWebPage) AS page \n"
			+ "WHERE URL.Record = page.URL\n"
			+ "AND URL.URL = ? " ;
	
	
	

}

