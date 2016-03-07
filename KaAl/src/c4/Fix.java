package c4;

import java.util.Stack;

public class Fix {
	public static String toPrefix(String Infix){
		char[] arr = Infix.toCharArray();
		char[] prefix = new char[Infix.length()];
		int pos = 0;
		Stack<Character> s = new Stack<>();
		
		for(int i=0; i<Infix.length(); i++){
			if(arr[i] == '+' || arr[i] == '-' || arr[i] == '/' || arr[i] == '*'){
				prefix[pos++] = arr[i];
			}else if(arr[i] == ')'){
				char x = s.pop();
				while(x != '('){
					prefix[pos++] = x;
					x = s.pop();
				}
			}else{
				s.push(arr[i]);				
			}
		}
		while(!s.empty()){
			prefix[pos++] = s.pop();
		}
		return String.valueOf(prefix);
	}
	
	public static String toPostfix(String Infix){
		char[] arr = Infix.toCharArray();
		char[] postfix = new char[Infix.length()];
		int pos = 0;
		Stack<Character> s = new Stack<>();
		
		for(int i=0; i<Infix.length(); i++){
			if(arr[i] == '+' || arr[i] == '-' || arr[i] == '/' || arr[i] == '*' || arr[i] == '('){
				s.push(arr[i]);
			}else if(arr[i]==')'){
				char x = s.pop();
				while(x != '('){
					postfix[pos++] = x;
					x = s.pop();
				}
			}else{
				postfix[pos++] = arr[i];
			}
		}
		while(!s.empty()){
			postfix[pos++] = s.pop();
		}
		return String.valueOf(postfix);
	}
	
	public static void main(String[] args){
		String infix = "(A+B)-C*D/E";
		String p = toPrefix(infix);
		System.out.println(p);
		p = toPostfix(infix);
		System.out.println(p);
	}
}
