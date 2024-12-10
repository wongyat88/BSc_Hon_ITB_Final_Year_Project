package t2;

public class tt2 {

	public static void main(String[] args) {
		String str="abcf";
		if(str.equals("")) {
			str = "a";
			System.out.println("First one : "+str);
		}else {
			int y = 0;
			int flag = 0;
			char s[]=str.toCharArray();
			int x = 97;
			for(int i=0;i<s.length;i++){
				y = s[i];
				if(y!=x) {
					char newletter=(char)x;  
					System.out.println("result : "+newletter);
					flag = 1;
					break;
				}else {
					x++;
				}
			}
			if(flag == 0) {
				char newletter=(char)x;  
				System.out.println("result2 : "+newletter);
			}
		}
		
	}

}
