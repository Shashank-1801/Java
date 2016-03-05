package c4;

import java.util.Iterator;
import java.util.LinkedList;

public class DyamicArray {
	LinkedList<Integer> stackList = new LinkedList<>();

	public boolean isEmpty(){
		return stackList.size() == 0;
	}

	public void push(Integer i){
		stackList.addFirst(i);
	}

	public Integer pop(){
		if(!isEmpty()){
			return stackList.removeFirst();
		}else{
			System.out.print("\t Under Flow \t");
			return null;
		}
	}

	public Integer peek(){
		if(!isEmpty()){
			return stackList.getFirst();
		}else{
			System.out.print("\t Under Flow \t");
			return null;
		}
	}
	
	public void display(){
		if(isEmpty()){
			System.out.println("Empty Stack");
		}else{
			System.out.print("TOP --> ");
			Iterator<Integer> it = stackList.iterator();
			while(it.hasNext()){
				System.out.println(it.next());
			}
			System.out.println();
		}
	}
}
