package main;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebPage {

	public static final String DEFAULT_USER_AGENT 
	= "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 "
			+ "(KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36" ;

	private String m_url = "" ;
	private String m_connUrl = "" ;
	private String m_dirUrl = "" ;
	
	private ArrayList<String> arrayUrl ;
	
	private Connection m_conn = null ;
	private Element element = null ;
	
	
	public WebPage( String url )
	{
		m_url = url ;
	}	
	
	public void ReadPage()
	{
		// Connect url
		m_conn = Jsoup.connect(m_url)
				.userAgent(DEFAULT_USER_AGENT) ;
		try 
		{
			Document doc = m_conn.get() ;
			element = doc.body();
			m_connUrl = doc.location() ;
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		// Get directory url
		m_dirUrl = GetDirUrl( m_connUrl ) ; 
		
		
		
		// Loop other url
		Elements elements = element.getElementsByAttribute("href") ;
		for( Element ele : elements )
		{
			String attr = ele.attr("href");
		}
		
	}
	
	private String GetDirUrl( String url )
	{
		return url.substring( 0, url.lastIndexOf("/") ) ;
		
	}
	
	
}
