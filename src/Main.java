import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String function = scan.nextLine();
		
	}
	public static String[] splitedString(String a){
		String[] ans;
		a = a.replace("+", " + ");
		a = a.replace("-", " - ");
		a = a.replace("*", " * ");
		a = a.replace("/", " / ");
		ans = a.split("\\s+");
		
		return ans;
	}
	
}
