import java.io.IOException;
import java.util.Scanner;


public class Main{
	
	public static void main(String[] args) {
		
		Scanner sc=new Scanner(System.in);
		System.out.println("請輸入關鍵字");
		String input=sc.nextLine();
		
		GoogleQuery google=new GoogleQuery(input);
		try {
			google.query();
		}catch(Exception e) {
			
		}
		
		//Search search=new Search(input);
	
		sc.close();
		
	}
	
}