package c3;

public class P1 {
	
	public class LLStack{
		SLL stl = null;
		
		public LLStack() {
			stl = new SLL();
		}
		
		//top is stl.getHead();
		
		public void push(int val){
			stl.insertFromHead(val);
		}
		
		public int peek(){
			if(stl.getHead()==null){
				System.out.println("Stack Empty");
				return Integer.MIN_VALUE;
			}else{
				return stl.getHead().getData();
			}		
		}
		
		public int pop(){
			if(stl.getHead()==null){
				System.out.println("Stack Empty");
				return Integer.MIN_VALUE;
			}else{
				int v = stl.getHead().getData();
				stl.deleteHead();
				return v;
			}
		}
	}
	
	public static void main(String[] args) {
		P1 p = new P1();
		P1.LLStack stack = p.new LLStack();
		
		for(int i=0; i<10; i++){
			stack.push(i);
			System.out.println(stack.peek());
		}
		
		System.out.println("----------------");
		
		for(int i=0; i<5; i++){
			System.out.println(stack.pop());
		}

		System.out.println("----------------");
		
		for(int i=0; i<10; i++){
			stack.push(i);
			System.out.println(stack.peek());
		}
		
		System.out.println("----------------");

		for(int i=0; i<20; i++){
			System.out.println(stack.pop());
		}
	}

}
