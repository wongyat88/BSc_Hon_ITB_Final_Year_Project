package t2;

import java.util.*;
import java.lang.*;

public class tt1 {
	public static void main(String[] args) {
		String str="The quick brown fox jumps over the lazy";
		char s[]=str.toCharArray();
		int c[]=new int[26];
		for(int i=0;i<s.length;i++){
			if(s[i]!=' '){
				System.out.println(s[i]-'a');
				System.out.println(c[i]-'a');
				c[s[i]-'a']++;
				}
			}
		String s2="";
		for(int i=0;i<26;i++){
			if(c[i]==0){
				s2=s2+(char)(i+'a');
				}
			}
		System.out.println(s2);
		}
}