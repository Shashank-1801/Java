package shekhar;

public class NextIntWithSameDigits {

	public static void main(String[] a){
		nextInt(123);
		nextInt(91);
		nextInt(254);
		nextInt(63);
		nextInt(45215);
		nextInt(984525);
		nextInt(852);
		nextInt(182);
		nextInt(582);
	}

	public static int nextInt(int x){

		char[] m = (String.valueOf(x)).toCharArray();
		int len = m.length;

		int[] intArray = new int[len];
		for(int i=0; i< len; i++){
			intArray[i] = (int)(m[i])-48;
		}

		for(int i=0; i< len; i++){
			System.out.print(intArray[i]);
		}
		System.out.print("\t : \t");

		boolean found = false;

		for(int i=len-1; i>0; i--){
			if(intArray[i] > intArray[i-1]){
				swap(i, i-1, intArray);
				found = true;
				break;
			}
		}

		int[] newIntArray = null;
		if(!found){
			newIntArray = new int[len+1];
			newIntArray[0] = 1;
			for(int i=0; i< len; i++){
				newIntArray[i+1] = intArray[i];
			}
		}

		if(found){
			for(int i=0; i< len; i++){
				System.out.print(intArray[i]);
			}
		}else{
			for(int i=0; i<len+1; i++){
				System.out.print(newIntArray[i]);
			}
		}
		System.out.println();
		
		if(found) return getNumber(intArray);
		else return getNumber(newIntArray);
		
	}


	public static int getNumber(int[] nums){

		StringBuilder strNum = new StringBuilder();

		for (int num : nums) 
		{
			strNum.append(num);
		}
		int finalInt = Integer.parseInt(strNum.toString());
		
		return finalInt;
	}

	public static void swap(int x, int y, int[] a){
		int temp = a[x];
		a[x] = a[y];
		a[y] = temp;
	}
}
