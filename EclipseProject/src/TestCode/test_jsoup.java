package TestCode;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.jsoup.* ;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.sqlite.*;

public class test_jsoup {
	
	private static final String SQLITE_JDBC_DRIVER = "org.sqlite.JDBC";
	
	public static void main(String[] args) 
	{
		// ConnectTest();
		
		SqliteTest();
		
	}
	
	private static void SqliteTest()
	{
		System.out.println("Start sqlite test code");

		//Definition
		String fileName = "jdbc:sqlite:testSqlite.sqlite" ;
		java.sql.Connection con = null ;		
		
		//////* 디비 연결하기 *//////
		try 
		{
			//JDBC driver load
			Class.forName(SQLITE_JDBC_DRIVER);
			
			//Connect DB
			con = DriverManager.getConnection(fileName) ;
			
			// 이걸 해야지 commit 컨트롤 가능
			con.setAutoCommit(false);
			
		} catch (SQLException | ClassNotFoundException e) 
		{
			e.printStackTrace();
			return ;
		}
		
		
		
		
		
		//////* 테이블 생성 *//////
		String deleteQuery = "DROP TABLE [TestTable]" ;
		String cresteQuery = "CREATE TABLE [TestTable] (\n"
				+ "	[Field_Integer]	INTEGER,\n"
				+ "	[Field_Text]	TEXT,\n"
				+ "	[Field_Blob]	BLOB\n"
				+ ")" ;
		try 
		{
			java.sql.Statement stmt = con.createStatement();
			stmt.execute(deleteQuery) ;
			stmt.execute(cresteQuery) ;
			
			con.commit();
			
			System.out.println("Success create table query") ;
		} catch (SQLException e1) 
		{
			e1.printStackTrace();
		}
 		
		
		//////* Insert 쿼리 예제 *//////
		String insertQuery = "INSERT INTO TestTable VALUES(?, ?, ?)" ;
		
		try 
		{
			PreparedStatement pstmt = con.prepareStatement(insertQuery) ;
			
			for( int a = 1 ; a <= 3 ; a++ )
			{
				// 1부터 시작 
				pstmt.setInt(1, a);
				pstmt.setString(2, Integer.toString(a));
				pstmt.setNull(3, java.sql.Types.BLOB) ;
				
				pstmt.executeUpdate() ;				
			}
			
			con.commit();
			
			System.out.println("Success insert data");
			
		} catch (SQLException e1) 
		{
			e1.printStackTrace();
		}
		
		
		//////* Select 쿼리 예제 *//////
		String selectQuery = "SELECT Field_Integer, Field_Text, Field_Blob "
				+"FROM TestTable" ;
		
		try 
		{
			Statement stmt = con.createStatement() ;
			ResultSet rs = stmt.executeQuery(selectQuery) ;
			System.out.println("Select result print");
			
			while( rs.next() )
			{
				int col1 = rs.getInt(1) ;
				String col2 = rs.getString(2) ;
				System.out.println("> 인덱스방식의 결과("+col1+","+col2+")");
				
				col1 = rs.getInt("Field_Integer") ;
				col2 = rs.getString("Field_Text") ;
				System.out.println("> 필드명 명시한 결과("+col1+","+col2+")");
			}
			
		} catch (SQLException e1) 
		{
			e1.printStackTrace();
		}
		
		
		//////* 디비 연결 해재 *//////
		//Close connect
		try 
		{
			if( con != null )
			{
				con.close() ;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace() ;
		}
		finally 
		{
            con = null ;
            System.out.println("Closed") ; 
		}
		
	}
	
	private static void ConnectTest()
	{
		String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36";
		

		System.out.println( "Hello World" );

		String urlStr = "http://www.naver.com/";
		String urlStr2 = "https://help.naver.com/support/welcomePage/guide.help";
		String urlStr3 = "https://namu.wiki/random";
		try
		{
			//Connection con = Jsoup.connect(urlStr3) ;
			Connection con = Jsoup.connect(urlStr3)
					.userAgent("Jsoup Scraper");

			Document doc = con.get();
			
			FileWriter fw = new FileWriter("debug.txt");
			FileWriter fw2 = new FileWriter("html.html");
			
			Element element = doc.body() ;
			fw2.write(element.toString());
			Elements elements = element.getElementsByTag("a") ;
			//Elements elements = element.select("a herf");
			
			System.out.println(doc.location());
			for( Element ele : elements )
			{
				String text = ele.text();
				String attr = ele.attr("href");
//				System.out.println( text );
//				System.out.println(attr);
				fw.write("text: " + text + "	/	attr: " + attr + "\n");
			}
			
			fw.close();
			fw2.close();
			
			
			Elements allElements = element.getAllElements() ;
			for( Element ele : allElements )
			{
				Attributes attr = ele.attributes();	
				System.out.println(attr.html());
			}
			
			
//			
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		
	}
	

}
