package c3;

public class DArray {
	Integer[] array;
	int size;
	int curPos;
	
	
	public DArray() {
		array = null;
		size = 0;
		curPos = 0;
	}
	
	public DArray(int size){
		this.size = size;
		array = new Integer[size];
		curPos = 0;
	}
	
	private boolean isEmpty(){
		return size == 0;
	}
	
	private boolean isFull(){
		return curPos == size;
	}
	
	private boolean isQuater(){
		return curPos == (int)size/4;
	}
	
	public int add(Integer val){
		if(isEmpty()){
			size = 1;
			array = new Integer[1];
		}else if(isFull()){
			size = size * 2;
			Integer[] arr = new Integer[size];
			array = copyItems(array, arr);
		}
		
		array[curPos] = val;
		curPos++;
		return size;
	}
	
	public Integer remove(){
		if(curPos==0){
			return null;
		}else if(isEmpty()){	
			return null;
		}else if(isQuater()){
			size = size/2;
			Integer[] arr = new Integer[size];
			array = copyItems(array, arr); 
		}
		
		Integer val = array[--curPos];
		return val;
	}
	

	private Integer[] copyItems(Integer[] old_arr, Integer[] new_arr) {
		for(int i=0; i< curPos; i++){
			new_arr[i] = old_arr[i];
		}
		
		return new_arr;
	}
}
