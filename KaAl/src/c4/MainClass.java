package c4;

import java.util.Random;

public class MainClass {
	public static void main(String[] arg){
		ArrayStacks stack = new ArrayStacks(20);
		//DyamicArray stack = new DyamicArray();
		for(int i=0; i<50; i++){
			int p = rndm();
			System.out.print("p is " + p + "\t");
			if(p>0){
				System.out.println(stack.pop());
			}else{
				stack.push(p);
				System.out.println(stack.peek());
			}
		}
		
		stack.display();
	}
	
	public static int rndm(){
		Random r = new Random();
		int v = r.nextInt();
		return v%5;
	}

}
