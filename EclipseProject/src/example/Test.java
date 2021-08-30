package example;

import static org.junit.jupiter.api.Assertions.assertTrue;

import main.LogFileManager;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Run");
		
		LogFileManager logMng = new LogFileManager() ;
		boolean res = logMng.Open_CurTime("") ;

		
		res = logMng.Close() ;


	}

}
