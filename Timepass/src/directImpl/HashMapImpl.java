package directImpl;

import java.util.HashMap;

public class HashMapImpl {

	public static void main(String[] a){
		HashMap<Integer, String> m1 = new HashMap<>();
		
		for(int i=0; i<20; i++){
			m1.put(i, "object"+ i);
		}
		
		HashMap<Integer,String> m2 = new HashMap<Integer, String>();
		for(int i=100; i<120; i++){
			m2.put(i, "2nd Object" + i);
		}
		
		m2.putAll(m1);
		
		System.out.println(m2.keySet());
		System.out.println(m2.entrySet());
		
	}
	
	
	
}