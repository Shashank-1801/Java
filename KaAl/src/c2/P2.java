package c2;

public class P2 {

	static int[] a = {3, 2, 5, 8, 7, 4, 0};
	public static void main(String[] ar){
		int n = a.length;
		Binary(n);
	}

	public static void Binary(int n){
		if(n<1){
			disp(a);
			System.out.println();
		}else{
			Binary(n-1);
			a[n-1] = 1;
			Binary(n-1);
		}
	}

	public static void disp(int[] arr){
		for(int t=0; t<arr.length; t++){
			System.out.print(arr[t] + " ");
		}
	}
}
