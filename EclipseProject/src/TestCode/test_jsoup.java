package TestCode;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;

import org.jsoup.* ;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class test_jsoup {
	public static void main(String[] args) {
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
//			
//			//System.out.println();
//			Charset charset = doc.charset() ;
//			
//			String title = doc.title() ;
//			String text = doc.text();
//			String toString = doc.toString() ;
//			String cssSelector = doc.cssSelector() ;
//			String data = doc.data() ;
//			String html = doc.html() ;
//			String className = doc.className() ;
//			String id = doc.id() ;
//			String location = doc.location() ;
//			String nodename = doc.nodeName();
//			String outerHtml = doc.outerHtml();
//			
//			
//			System.out.println(title);
//			System.out.println(text);
//			System.out.println(toString);
//			System.out.println(cssSelector);
//			System.out.println(data);
//			System.out.println(html);
//			System.out.println(className);
//			System.out.println(id);
//			System.out.println(location);
//			System.out.println(title);
//			System.out.println(title);
//			
//			int a = 1 ;
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		
	}

}
