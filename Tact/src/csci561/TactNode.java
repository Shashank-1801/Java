package csci561;

public class TactNode {
	String symbol;
	String variable;
	String constant;
	boolean hasVariable;

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

}
