package csci561;

public class TactNode {
	String symbol;
	String variable;
	String constant;
	boolean hasVariable;
	boolean hasOperator;
	TactNode leftSide;
	TactNode rightSide;
	String operator;

	public TactNode(String exp){
		if(getOperatorIndex(exp, '=') != -1){
			int impliesAt = getOperatorIndex(exp.toString(), '=');
			String left = exp.substring(1, impliesAt).trim();
			String right = exp.substring(impliesAt+2, exp.length()-1).trim();
			// populating fields
			hasOperator = true;
			operator = "=>";
			leftSide = new TactNode(left);
			rightSide = new TactNode(right);
		}else if(getOperatorIndex(exp, '&') != -1){
			int andAt = getOperatorIndex(exp.toString(), '&');
			String left = exp.substring(1, andAt).trim();
			String right = exp.substring(andAt+1, exp.length()-1).trim();
			// populating fields
			hasOperator = true;
			operator = "&";
			leftSide = new TactNode(left);
			rightSide = new TactNode(right);
		}else if(getOperatorIndex(exp, '|') != -1){
			int orAt = getOperatorIndex(exp.toString(), '|');
			String left = exp.substring(1, orAt).trim();
			String right = exp.substring(orAt+1, exp.length()-1).trim();
			// populating fields
			hasOperator = true;
			operator = "|";
			leftSide = new TactNode(left);
			rightSide = new TactNode(right);
		}else if(getOperatorIndex(exp, '~') != -1){
			int notAt = getOperatorIndex(exp.toString(), '~');
			String right = exp.substring(notAt+1, exp.length()-1).trim();
			// populating fields
			hasOperator = true;
			operator = "~";
			leftSide = null;
			rightSide = new TactNode(right);
		}else{
			String sym = getSymbol(exp);
			String value = getParam(exp);
			if(value.toLowerCase().equals(value)){
				symbol = sym;
				variable = value;
				hasVariable = true;
			}else{
				symbol = sym;
				constant = value;
				hasVariable = false;
			}
		}
	}

	public TactNode(String op, TactNode left, TactNode right) {
		leftSide = left;
		rightSide = right;
		operator = op;
		hasOperator = true;
	}

	public TactNode(String sym, String value) {
		if(value.toLowerCase().equals(value)){
			symbol = sym;
			variable = value;
			hasVariable = true;
		}else{
			symbol = sym;
			constant = value;
			hasVariable = false;
		}
	}

	public String toString(){
		if(hasOperator){
			if(operator == "~"){
				return operator + rightSide.toString();
			}else{
				return leftSide.toString() + operator + rightSide.toString();
			}
		}else{
			if(hasVariable){
				return symbol + "(" + variable + ")";
			}else{
				return symbol + "(" + constant+ ")";
			}
		}
	}

	public String unifiedString(String unificationContant){
		if(!hasOperator){
			return symbol + "(" + unificationContant+ ")";
		}else{
			return null;
		}
	}
	
	private static int getOperatorIndex(String exp, char operator) {
		char[] expChar = exp.toCharArray();
		int count = 0;
		for(int i=0; i<exp.length(); i++){
			if(expChar[i] == '('){
				count++;
			}
			if(expChar[i] == ')'){
				count--;
			}

			if(count == 1 && (expChar[i] == operator)){
				return i;
			}
		}
		return -1;
	}
	
	private static String getSymbol(String k){
		char openB = '(';
		int start = k.indexOf(openB);
		if(start == -1){
			return k;
		}
		//k.lastIndexOf(closeB);
		return k.substring(0, start).trim();
	}

	private static String getParam(String k){
		char[] charArray = k.toCharArray();
		int count = 0;
		int start = k.indexOf('(');
		int end;
		for(int i=start; i<k.length(); i++){
			if(charArray[i] == '('){
				count++;
			}
			if(charArray[i] == ')' && count == 1){
				end = i;
				return k.substring(start+1, end).trim();

			}
			if(charArray[i] == ')'){
				count--;
			}
		}
		return null;
	}


}
