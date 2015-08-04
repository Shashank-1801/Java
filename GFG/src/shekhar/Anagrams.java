package shekhar;

public class Anagrams {

	public static void main(String[] a){
		
		System.out.println(isAnagram("a", "a"));
		System.out.println(isAnagram("abcd", "xsakl"));
		System.out.println(isAnagram("aaaaa", "aaaaa"));
		System.out.println(isAnagram("geeksforgeeks", "forgeeksgeeks"));
		System.out.println(isAnagram("geeksforgeeks", "forgeeksgeekc"));
		System.out.println(isAnagram("sky", "yks"));
		System.out.println(isAnagram("", ""));
		System.out.println(isAnagram(null,"shashank"));

	}

	public static boolean isAnagram(String a, String b){
		if(a!=null && b!=null && a.length() == b.length()){
			if(a.equals(b)){
				return true;
			}

			//get the run length and match 
			String arl = runLen(a);
			String brl = runLen(b);
			System.out.println(arl);
			System.out.println(brl);
			if(arl.equals(brl)){
				return true;
			}else{
				return false;
			}

		}

		return false;
	}

	public static String runLen(String a){

		int[] alpha = new int[26];
		for(int i=0; i<26; i++){
			alpha[i]=0;
		}

		char[] chararray = a.toCharArray();
		for(char x : chararray){
			int pos = (int)x - 'a';
			alpha[pos]++;
		}

		StringBuilder s = new StringBuilder();
		for(int i=0; i<26; i++){
			char c = (char)(i + (int)'a');
			s.append(c);
			s.append(alpha[i]);
		}

		return s.toString();
	}

	public static char[] sort(char[] ca){


		return null;
	}
}
