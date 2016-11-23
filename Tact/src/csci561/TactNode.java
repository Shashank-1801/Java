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

}
