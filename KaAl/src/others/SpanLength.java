package others;

public class SpanLength {

	public static void main(String[] args) {
		int[] week = {6,3,4,5,2};
		
		int [] span = calSpan(week);
		
		for(int i=0; i<week.length; i++){
			System.out.println(span[i]);
		}
		
	}

	public static int[] calSpan(int[] weekData){
		int len = weekData.length;
		int[] s = new int[len];
		
		int last = weekData[0];
		s[0] = 1;
		for(int i=1 ; i<len; i++){
			if(weekData[i] < last){
				s[i] = 1;
			}else{
				s[i] = s[i-1] + 1;
			}
			last = s[i];
		}
				
		return s;				
	}
}
