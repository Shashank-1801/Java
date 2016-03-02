package c3;

public class SLL {
	private SLLNode head = null;

	public SLLNode getHead(){
		return head;
	}

	public SLLNode createNode(int val){
		return new SLLNode(val);
	}

	public void display(){
		SLLNode temp = head;
		while(temp!=null){
			System.out.print(temp.getData() + "->");
			temp = temp.getNext();
		}
		System.out.println("NULL");
	}

	@Deprecated
	public SLLNode insertFromHead(SLLNode node){
		if(this.getHead() == null){
			this.head = node;
		}else{
			node.setNext(head);
			head = node;
		}
		return head;
	}

	public SLLNode insertFromHead(int val){
		SLLNode node = new SLLNode(val);
		if(this.getHead() == null){
			this.head = node;
		}else{
			node.setNext(head);
			head = node;
		}
		return head;
	}

	@Deprecated
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

	public SLLNode insertFromTail(int val){
		SLLNode node = new SLLNode(val);		
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

	@Deprecated
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

	public SLLNode insertMiddle(int val, int pos){
		SLLNode node = new SLLNode(val);		
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

	public SLLNode deleteHead(){
		if(head ==null){
			return null;
		}else{
			head = head.getNext();		
			return head;
		}
	}

	public SLLNode deleteTail(){
		if(head==null){
			return null;
		}else if(head.getNext()==null){
			return null;
		}else{
			SLLNode temp = head;
			SLLNode prev = null;
			while(temp.getNext()!=null){
				prev = temp;
				temp = temp.getNext();
			}

			prev.setNext(null);		
			return head;
		}
	}
	
	public SLLNode deleteMiddle(int pos){
		if(pos<0){
			System.out.println("Invalid position for deletion");
		}else if(head == null){
			System.out.println("Invalid position for deletion");
		}else if(pos==0){
			deleteHead();
		}else{
			SLLNode temp = head.getNext();
			SLLNode prev = head;
			for(int i=1; (temp!=null) && i<pos; i++){
				prev = temp;
				temp = temp.getNext();
			}
			if(temp == null){
				System.out.println("Invalid position for deletion");
			}else{
				prev.setNext(temp.getNext());
			}
		}
		return head;
	}
	
	public void deleteLL(){
		SLLNode temp = head;
		while(temp!=null){
			SLLNode m = temp.getNext();
			temp=null;
			temp=m;
		}
		
		head = null;
	}
}
