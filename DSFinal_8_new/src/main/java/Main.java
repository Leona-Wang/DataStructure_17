import java.io.IOException;
import java.util.Scanner;


public class Main{
	
	public static void main(String[] args) {
		
		
		
		Scanner sc=new Scanner(System.in);
		System.out.println("請輸入關鍵字");
		String input=sc.nextLine();
		
		Search search=new Search(input);
		
		
		/*try 
		{
			System.out.println(new GoogleQuery("輕量休閒氣墊拖鞋(厚底拖鞋 氣墊拖鞋 輕量休閒拖鞋 休閒拖鞋 輕量) momo購物網").query());
//			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}*/
		
		
		sc.close();
		
		
		
		
		
		
		
		
		
		
	}
	
}