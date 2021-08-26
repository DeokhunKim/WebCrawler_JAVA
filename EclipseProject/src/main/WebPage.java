package main;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebPage {

	public static final String DEFAULT_USER_AGENT 
	= "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 "
			+ "(KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36" ;
	public static final String JSOUP_USER_AGENT  = "Jsoup Scraper" ;

	private String m_url = "" ;
	private String m_connUrl = "" ;
	private String m_dirUrl = "" ;
	
	private Set<String> m_nextUrlSet = new HashSet<String>() ;
	
	private Connection m_conn = null ;
	private Element m_elementBody = null ;
	
	
	public WebPage( String url )
	{
		m_url = url ;
	}	
	
	public void ReadPage()
	{
		// Connect url
		m_conn = Jsoup.connect(m_url)
				.userAgent(JSOUP_USER_AGENT) ;
		try 
		{
			Document doc = m_conn.get() ;
			m_elementBody = doc.body();
			m_connUrl = doc.location() ;
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		// Get directory url
		m_dirUrl = GetDirUrl( m_connUrl ) ; 		
		
		// Loop other url
		Elements elements = m_elementBody.getElementsByAttribute("href") ;
		for( Element element : elements )
		{
			String newUrl = CreateNewLink( m_dirUrl, element.attr("href") ) ;
			if( "" != newUrl )
			{
				m_nextUrlSet.add(newUrl) ;				
			}
		}
	}
	
	private String GetDirUrl( String url )
	{
		return url.substring( 0, url.lastIndexOf("/") ) ;
	}
	
	private String CreateNewLink( String dirUrl, String herfUrl )
	{
		// Filter condition...
		if( herfUrl.equals("") )
		{
			//TODO log remove
			System.out.println("[log] remove empty") ;
			return "" ;
		}
		else if( herfUrl.charAt(0) == '#' )
		{
			//TODO log remove
			System.out.println("[log] remove inner link - " + herfUrl ) ;
			return "" ;
		}
		else if( herfUrl.indexOf("@") != -1 )
		{
			//TODO log remove
			System.out.println("[log] remove presmably by email - " + herfUrl ) ;
			return "" ;
		}
		
		// Not filter condition..
		String newUrl ;
		if( herfUrl.charAt(0) == '/' )
		{
			if( herfUrl.length() != 1 && herfUrl.charAt(1) == '/' )
			{
				//newUrl = GetDirUrl( GetDirUrl( dirUrl ) ) + herfUrl ;	
				return CreateNewLink( GetDirUrl( dirUrl ), herfUrl.substring(1) ) ;
	
			}
			else if( dirUrl.equals("https://") || dirUrl.equals("http://") )
			{
				newUrl = GetDirUrl( dirUrl ) + herfUrl.substring(1) ;
			}
			else
			{
				newUrl = GetDirUrl( dirUrl ) + herfUrl ;				
			}
		}
		else if( herfUrl.substring(0, 4).equals("http") )
		{
			newUrl = herfUrl ;
		}
		else 
		{
			newUrl = dirUrl + herfUrl ;
		}
		
		
		//TODO log remove
		System.out.println("[log] Create new link \"" + newUrl  
				+ "\" origin \"" + herfUrl + "\"" ) ;
		
		return newUrl ;
	}
	
	
}
