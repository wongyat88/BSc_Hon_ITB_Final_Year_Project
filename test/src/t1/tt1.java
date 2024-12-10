package t1;

import java.util.Scanner;

public class tt1 {
	
	String tid;
	EmpModel emp;
	int tablecount = 0;
	int sit = 0;
	String yy = "";
	
	public void conDB() {
		String sql = "select * from ttest where tid = " + tid;
		emp = new EmpModel();
		emp.runSql(sql);
		tablecount = Integer.parseInt((String)emp.getValueAt(0, 1));
		System.out.println("Sit are provide : "+tablecount);
	}
	
	public void conDB2() {
		String sql = "select * from tidtest where tid LIKE '" + tid +"%' order by tid ";
		emp = new EmpModel();
		emp.runSql(sql);
		for(int i=0;i<emp.getRowCount();i++) {
			String x = (String)emp.getValueAt(i, 0);
			String y = x.substring(2,3);
			int c = Integer.parseInt((String)emp.getValueAt(i, 2));
			sit = sit+c;
			yy = yy+y;
			System.out.println(x+" "+ c);
		}
		System.out.println("Sitting Number : "+sit);
		System.out.println("Sitting position :"+yy);
	}
	
	public void newPos(){
		String str=yy;
		if(str.equals("")) {
			str = "a";
			System.out.println("New First one : "+str);
		}else {
			int y = 0;
			int flag = 0;
			char s[]=str.toCharArray();
			int x = 97;
			for(int i=0;i<s.length;i++){
				y = s[i];
				if(y!=x) {
					char newletter=(char)x;  
					System.out.println("New result : "+newletter);
					flag = 1;
					break;
				}else {
					x++;
				}
			}
			if(flag == 0) {
				char newletter=(char)x;  
				System.out.println("New result2 : "+newletter);
			}
		}
	}
	
	public tt1() {
		Scanner myObj = new Scanner(System.in);
		System.out.println("Enter a tableID:");
		tid = myObj.nextLine();
		conDB();
		conDB2();
		newPos();
		
	}
	
	public static void main(String[] args) {
		tt1 tt1 = new tt1();
	}
}
