package junit ;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import main.WebPage;

class junit_WebPage {

	@Test
	void test_GetDirUrl() throws NoSuchMethodException, SecurityException 
	{
		WebPage web = new WebPage("");
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
	
	

}
