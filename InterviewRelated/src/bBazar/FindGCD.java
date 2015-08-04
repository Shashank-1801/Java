package bBazar;

public class FindGCD {

	static int[] coprimeCount(int[] A) {
        int len = A.length;
		int[] c = new int[len];
		for(int m = 0; m < len; m++){
			// for each entry in A, iterate over the length 1 to A[m] and find all the values that are co primes to A[m]
			int count = 0;
			for(int i = 1; i <= A[m]; i++){
				if (gcd(i, A[m]) == 1){
					count++;
				}
			}
			//System.out.println(count);
			c[m] = count;
		}
		return c;
        
    }

    static int gcd(int a, int b){
    	if(b == 0){
            return a;
        }
        return gcd(b, a%b);
    }


}