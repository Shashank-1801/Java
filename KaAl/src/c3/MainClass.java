package c3;

import java.util.Random;

public class MainClass {

	public static void main(String[] args) {
		SLL sll = new SLL();
		
		for(int i=0;i<20; i++){
			int v = rndm();
			System.out.print("v is " + v + " and i is " + i + "\t");
			if(v==0){
				sll.insertFromHead(i);
			}else if(v==1){
				sll.insertFromTail(i);
			}else{
				sll.insertMiddle(i, v);
			}			
			sll.display();
		}
		
		System.out.println("######");
		
		for(int i=0;i<30; i++){
			int v = rndm();
			System.out.print("v is " + v + "\t");
			if(v==0){
				sll.deleteHead();
			}else if(v==1){
				sll.deleteTail();
			}else{
				sll.deleteMiddle(v);
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
