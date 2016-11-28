package csci561;

import java.util.ArrayList;

public class TactNode {
	String symbol;
	ArrayList<String> variable = new ArrayList<>();
	//ArrayList<String> constant = new ArrayList<>();
	int numberOfVariables;
	boolean hasVariable;
	boolean hasOperator;
	TactNode leftSide;
	TactNode rightSide;
	String operator;
	boolean[] isConstant;

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
			isConstant = new boolean[numberOfVariables];
			symbol = sym;
			if(value.toLowerCase().equals(value)){
				// all variables, no constants
				for(String x : values)
					variable.add(x.trim());
				hasVariable = true;
			}else{
				// may have variable and/or constants
				hasVariable = false;
				for(int i=0; i<numberOfVariables; i++){
					variable.add(values[i].trim());
					if(values[i].toLowerCase().equals(values[i])){
						hasVariable = true;
					}else{
						isConstant[i] = true;
					}
				}

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
			return symbol + "(" + getList(variable) + ")";
		}
	}


	@Override
	public boolean equals(Object object){
		boolean isSame = false;
		try{
			if (object != null && object instanceof TactNode){
				TactNode otherObject = (TactNode) object;
				if(otherObject.hasOperator && this.hasOperator && this.operator.equals(otherObject.operator)){
					if(this.leftSide.equals(otherObject.leftSide) && (this.rightSide.equals(otherObject.rightSide))){
						isSame = true;
					}
				}else if(otherObject.hasOperator && this.hasOperator && (!this.operator.equals(otherObject.operator))){
					return isSame;
				}else{
					if(this.numberOfVariables == otherObject.numberOfVariables){
						if(this.symbol.equals(otherObject.symbol)){
							if(this.hasVariable && otherObject.hasVariable){
								isSame = true;
							}else if(!this.hasVariable && !otherObject.hasVariable){
								return this.variable.equals(otherObject.variable);
							}else{
								//compare variable list and then check
								for(int i=0; i<numberOfVariables; i++){
									//compare variables and constants
									String thisVar = this.variable.get(i);
									String otherVar = otherObject.variable.get(i);
									if(this.isConstant[i] == true && otherObject.isConstant[i] == true){
										if(!thisVar.equals(otherVar)){
											return false;
										}
									}
								}
								return true;
							}
						}
					}
				}
			}
		}catch (NullPointerException npe) {
			System.out.println("NPE");
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
