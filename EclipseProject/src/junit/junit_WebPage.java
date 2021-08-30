package junit ;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import main.WebPage;

class junit_WebPage {

	@Test
	void test_GetDirUrl() throws NoSuchMethodException, SecurityException 
	{
		WebPage web = new WebPage("","","",0);
		Method method = web.getClass().getDeclaredMethod("GetDirUrl", String.class ) ;
		method.setAccessible(true) ;
		String res = "";
		try 
		{
			res = (String) method.invoke( web, "http://www.thewoman.kr/product/detail.html?product_no=6602&cate_no=1&display_group=2" ) ;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals( res, "http://www.thewoman.kr/product" ) ;
		assertFalse( res.equals("http://www.thewoman.kr/product/") ) ;
	}
	
	
	@Test
	void test_CreateNewLink() throws NoSuchMethodException, SecurityException, IOException 
	{
		WebPage web = new WebPage("","","",0);
		Method method = web.getClass().getDeclaredMethod("CreateNewLink", 
				String.class, String.class, String.class ) ;
		method.setAccessible(true) ;
		boolean res = true ;
		String output = "" ;
		String input = "" ;
		
		FileReader fr = new FileReader("src/junit/input_test_CreateNewLink") ;
		BufferedReader br = new BufferedReader(fr) ;
				
		try 
		{
			while( ( input = br.readLine() ) != null )
			{
				// "https://namu.wiki/w/%EB%8C%80%ED%95%9C%EB%AF%BC%EA%B5%AD"
				res = (boolean) method.invoke( web, "https://namu.wiki/w",
						input, output ) ;				
			}
						
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
	

}
