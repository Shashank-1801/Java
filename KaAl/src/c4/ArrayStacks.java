package c4;

import java.util.Iterator;

public class ArrayStacks {
	
	private int capacity;
	private int[] arrStk;
	private int top;
		
	
	public ArrayStacks(int size) {
		capacity = size;
		arrStk = new int[capacity];
		top = -1;
	}
	
	public boolean isEmpty(){
		return top == -1;
	}
	
	public boolean isFull(){
		return top == capacity;
	}
	
	public Integer pop(){
		if(isEmpty()){
			System.out.print("\tunder flow\t");
			return null;
		}else{
			return arrStk[top--];
		}
	}
	
	public void push(Integer val){
		if(isFull()){
			System.out.print("\tover flow\t");
		}else{
			arrStk[++top] = val;
		}
	}
	
	public Integer peek(){
		if(isEmpty()){
			System.out.print("\tunder flow\t");
			return null;
		}else{
			return arrStk[top];
		}
	}

	public void display(){
		if(isEmpty()){
			System.out.println("Empty Stack");
		}else{
			System.out.print("TOP --> ");
			int t = top;
			while(t>=0){
				System.out.println(arrStk[t]);
				t--;
			}
			System.out.println();
		}
	}
}
