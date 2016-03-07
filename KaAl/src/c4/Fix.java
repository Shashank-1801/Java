package c4;

import java.util.Stack;

public class Fix {
	public static String toPostfix(String Infix){
		char[] arr = Infix.toCharArray();
		char[] postfix = new char[Infix.length()];
		int pos = 0;
		Stack<Character> s = new Stack<>();
		
		for(int i=0; i<Infix.length(); i++){
			if(arr[i] == '+' || arr[i] == '-' || arr[i] == '/' || arr[i] == '*'){
				postfix[pos++] = arr[i];
			}else if(arr[i] == ')'){
				char x = s.pop();
				while(x != '('){
					postfix[pos++] = x;
					x = s.pop();
				}
			}else{
				s.push(arr[i]);				
			}
		}
		while(!s.empty()){
			postfix[pos++] = s.pop();
		}
		return String.valueOf(postfix);
	}
	
	public static void main(String[] args){
		String p = toPostfix("(A+B)-C*D/E");
		System.out.println(p);
	}
}
