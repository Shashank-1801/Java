package c3;

public class SLLNode {
	private int data;
	private SLLNode next;
	
	public SLLNode(int val) {
		data = val;
		next = null;
	}
	public void setData(int val){
		this.data = val;
	}
	
	public void setNext(SLLNode nxtNode){
		next = nxtNode;
	}
	
	public int getData(){
		return this.data;
	}
	
	public SLLNode getNext(){
		return next;
	}
}
