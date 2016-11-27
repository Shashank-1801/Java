package csci561;

import java.util.ArrayList;

public class TactNode {
	String symbol;
	ArrayList<String> variable = new ArrayList<>();
	ArrayList<String> constant = new ArrayList<>();
	int numberOfVariables;
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
			String[] values = value.split(",");
			numberOfVariables = values.length;
			if(value.toLowerCase().equals(value)){
				symbol = sym;
				for(String x : values)
					variable.add(x);
				hasVariable = true;
			}else{
				symbol = sym;
				for(String x : values)
					constant.add(x);
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

	/*
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




	public String unifiedString(String unificationContant){
		if(!hasOperator){
			return symbol + "(" + unificationContant+ ")";
		}else{
			if(variable!=null){
				return this.toString().replaceAll(variable, unificationContant);
			}else{
				return this.toString();
			}
		}
	}
	 */

	@Override
	public String toString(){
		if(hasOperator){
			if(operator == "~"){
				return operator + rightSide.toString();
			}else{
				return leftSide.toString() + operator + rightSide.toString();
			}
		}else{
			if(hasVariable){
				return symbol + "(" + getList(variable) + ")";
			}else{
				return symbol + "(" + getList(constant)+ ")";
			}
		}
	}


	@Override
	public boolean equals(Object object){
		boolean isSame = false;
		try{
			if (object != null && object instanceof TactNode){
				TactNode otherObject = (TactNode) object;
				if(otherObject.hasOperator && this.operator.equals(otherObject.operator)){
					if(this.leftSide.equals(otherObject.leftSide) && (this.rightSide.equals(otherObject.rightSide))){
						isSame = true;
					}
				}else if(otherObject.hasOperator && (!this.operator.equals(otherObject.operator))){
					return isSame;
				}else{
					if(this.numberOfVariables == otherObject.numberOfVariables){
						if(this.symbol.equals(otherObject.symbol)){
							isSame = true;
						}
					}
				}
			}
		}catch (NullPointerException npe) {
			return false;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return isSame;
	}

	private static String getList(ArrayList<String> var){
		String p = "";
		for(String x : var){
			p += x + ",";
		}
		p = p.substring(0, p.length()-1);
		return p;
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
