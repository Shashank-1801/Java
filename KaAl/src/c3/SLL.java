package c3;

public class SLL {
	private SLLNode head = null;

	public SLLNode getHead(){
		return head;
	}

	public void display(){
		SLLNode temp = head;
		while(temp!=null){
			System.out.print(temp.getData() + "->");
			temp = temp.getNext();
		}
		System.out.println("NULL");
	}

	public SLLNode insertFromHead(SLLNode node){
		if(this.getHead() == null){
			this.head = node;
		}else{
			node.setNext(head);
			head = node;
		}
		return head;
	}

	public SLLNode insertFromTail(SLLNode node){
		if(this.getHead()==null){
			this.head = node;
		}else{
			SLLNode temp = head;
			while(temp.getNext()!=null){
				temp = temp.getNext();
			}
			temp.setNext(node);
			node.setNext(null);
		}

		return head;
	}

	public SLLNode insertMiddle(SLLNode node, int pos){
		if(this.getHead()==null && pos!=0){
			System.out.println("LL is empty");
		}else if(pos<0){
			System.out.println("Invalid position");
		}else{
			int count = 1;
			SLLNode temp = head;
			while(temp.getNext()!=null && count!=pos){
				temp = temp.getNext();
				count ++;
			}

			if(temp.getNext()!=null){
				//we have reached the position to insert
				node.setNext(temp.getNext());
				temp.setNext(node);
			}else{
				System.out.println("Invalid postion");
			}
		}

		return head;
	}
}
