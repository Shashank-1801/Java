package c3;

import java.util.Random;

public class MainClass {

	public static void main(String[] args) {
		SLL sll = new SLL();
		
		for(int i=0;i<20; i++){
			SLLNode n = new SLLNode(i);
			int v = rndm();
			System.out.print("v is " + v + " and i is " + i + "\t");
			if(v==0){
				sll.insertFromHead(n);
			}else if(v==1){
				sll.insertFromTail(n);
			}else{
				sll.insertMiddle(n, v);
			}
			
			sll.display();
		}
		
	}
	
	public static int rndm(){
		Random r = new Random();
		int v = r.nextInt();
		return v%5;
	}

}
