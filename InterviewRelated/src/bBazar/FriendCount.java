package bBazar;

public class FriendCount {

	static int friendCircles(String[] friends) {
        int nf = friends.length;
        int[] nfa = new int[nf];
        for(int i=0; i<nf ; i++){
            nfa[i] = i;
        }
        
        for(int i=0; i<nf ; i++){
            char[] line = friends[i].toCharArray();
            for(int j=0; j<=i; j++){
                if (line[j] == 'Y'){
                    nfa[j] = nfa[i];
                }
            }
        }
        
        int count = 0;
        int last = -1;
        for(int i=0; i<nf; i++){
            if(nfa[i] > last){
                count++;
            }
            last = nfa[i];
        }
          
        return count;
    }
        
    static int min(int a, int b){
        if(a<b) return a;
        else return b;
    }
    
    static void lenOfarray(String[] a){
    	int l = a.length;
    	System.out.println(l);
    	
    }
    static int friendCircles2(String[] friends) {
        int nf = friends.length;
        if(nf == 0){
            return 0;
        }
        int[] nfa = new int[nf];
        for(int i=0; i<nf ; i++){
            nfa[i] = i;
        }
        
        int count = nf;
        int common = 0;
        for(int i=0; i<nf ; i++){
            char[] line = friends[i].toCharArray();
            for(int j=0; j<i; j++){
                if (line[j] == 'Y'){
                    common++;
                }
            }
        }
        
        count = count - common;
        
        return count;
    }
        
   
    
    public static void main(String[] a){
    	
    	lenOfarray(null);
    }

}