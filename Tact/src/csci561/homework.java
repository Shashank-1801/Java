package csci561;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class homework {
	public static void main(String[] a) throws IOException{
		FileInputStream fin = null;
		FileOutputStream fout = null;
		BufferedReader reader = null;
		ArrayList<TactNode> kbArray = new ArrayList<>();
		new Hashtable<>();
		try{
			fin = new FileInputStream("./input.txt");
			reader = new BufferedReader(new InputStreamReader(fin));

			int numQueries = Integer.parseInt(reader.readLine().trim());
			//System.out.println("Queries are :");
			ArrayList<String> queryEntries = new ArrayList<>();
			for(int i=0; i< numQueries; i++){
				String q = reader.readLine();
				//System.out.println(q);
				queryEntries.add(q);
			}

			int numKB = Integer.parseInt(reader.readLine().trim());
			System.out.println("KB entries are :");
			ArrayList<String> KBEntries = new ArrayList<>();
			for(int i=0; i< numKB; i++){
				String s  = reader.readLine();
				System.out.println(s);
				KBEntries.add(s);
			}
			System.out.println();

			analyse(KBEntries, kbArray);

			printKBEntries(kbArray);

			fout = new FileOutputStream("./output.txt");

			for(int i=0; i<queryEntries.size(); i++){
				String q = queryEntries.get(i);
				System.out.print(q +" : ");
				boolean result = isTrue(q, kbArray);
				if(result){
					System.out.println("TRUE");
					fout.write("TRUE\n".getBytes());
				}else{
					System.out.println("FALSE");
					fout.write("FALSE\n".getBytes());
				}
			}


		} catch(Exception e){
			System.out.println("Some exception:" + e.getMessage());
			e.printStackTrace();
		}finally {
			fout.close();
			fin.close();
			reader.close();
		}

	}


	private static void printKBEntries(ArrayList<TactNode> kbArray) {
		System.out.println("\n--------- KB Array is ---------");
		for(int i=0; i< kbArray.size(); i++){
			System.out.println((i+1) + " : " + kbArray.get(i));
		}
		System.out.println();
		
	}


	public static void analyse(ArrayList<String> kbentries, ArrayList<TactNode> kbArray){
		//lets analyse the kb entries one by one
		// expected operators includes: ~, (,), & , =>, |
		for(int i=0; i<kbentries.size(); i++){
			String k = kbentries.get(i);
			TactNode t = new TactNode(k);
			kbArray.add(t);
		}
	}



	public static String getSymbol(String k){
		char openB = '(';
		int start = k.indexOf(openB);
		if(start == -1){
			return k;
		}
		//k.lastIndexOf(closeB);
		return k.substring(0, start).trim();
	}

	public static String getParam(String k){
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
	
	/*
	public static TactNode resolveExpression(TactNode exp, Hashtable<String, TactNode> kb, ArrayList<TactNode> kbArray, boolean toBeAdded){
		System.out.println("String must contain an operator, check for =>, &, |, ~ : " + exp);
		if(containsOperator(exp.toString(), '=')){
			int impliesAt = getOperatorIndex(exp.toString(), '=');
			TactNode leftNode = exp.leftSide;
			TactNode rightNode = exp.rightSide;
			if(getSymbol(leftNode.toString()).isEmpty()){
				leftNode = resolveExpression(leftNode, kb, kbArray, false);
			}
			if(getSymbol(rightNode.toString()).isEmpty()){
				rightNode = resolveExpression(rightNode,kb, kbArray, false);
			}
			TactNode tn = new TactNode("=>", leftNode, rightNode);
			kb.put(rightNode.symbol, tn);
			if(toBeAdded) 
				kbArray.add(tn);
			return tn;
		}else if(containsOperator(exp,'&')){
			int andAt = getOperatorIndex(exp, '&');
			String left = exp.substring(0, andAt).trim();
			String right = exp.substring(andAt+1, exp.length()).trim();
			if(getSymbol(left).isEmpty()){
				left = resolveExpression(left, kb, kbArray, true & toBeAdded);
			}
			if(getSymbol(right).isEmpty()){
				right = resolveExpression(right,kb, kbArray, true & toBeAdded);
			}
			TactNode tn = new TactNode("&", new TactNode(getSymbol(left), getParam(left)), new TactNode(getSymbol(right), getParam(right)));
			kb.put(getSymbol(right), tn);
			if(toBeAdded){
				kbArray.add(new TactNode(getSymbol(left), getParam(left)));
				kbArray.add(new TactNode(getSymbol(right), getParam(right)));
			}
			return tn.toString();
		}else if(containsOperator(exp, '|')){
			int orAt = getOperatorIndex(exp, '|' );
			String left = exp.substring(0, orAt).trim();
			String right = exp.substring(orAt+1, exp.length()).trim();
			if(getSymbol(left).isEmpty()){
				left = resolveExpression(left, kb, kbArray, false);
			}
			if(getSymbol(right).isEmpty()){
				right = resolveExpression(right,kb, kbArray, false);
			}
			TactNode tn = new TactNode("|", new TactNode(getSymbol(left), getParam(left)), new TactNode(getSymbol(right), getParam(right)));
			kb.put(getSymbol(right), tn);
			if(toBeAdded)
				kbArray.add(tn);
			return tn.toString();
		}else if(containsOperator(exp, '~')){
			int notAt = getOperatorIndex(exp, '~');
			String right = exp.substring(notAt+1, exp.length()).trim();

			if(getSymbol(right).isEmpty()){
				right = resolveExpression(right,kb, kbArray, false);
			}
			TactNode tn = new TactNode("~", null, new TactNode(getSymbol(right), getParam(right)));
			kb.put(getSymbol(right), tn);
			if(toBeAdded)
				kbArray.add(tn);
			return tn.toString();
		}else{
			System.out.println("expression doesn't hava an operator " + exp);
			exp = exp.substring(1,  exp.length()-1).trim();
			return resolveExpression(exp, kb, kbArray, toBeAdded);
		}
	}

	*/
	
	private static boolean containsOperator(String exp, char operator) {
		char[] expChar = exp.toCharArray();
		int count = 0;
		for(int i=0; i<exp.length(); i++){
			if(expChar[i] == '('){
				count++;
			}
			if(expChar[i] == ')'){
				count--;
			}

			if(count == 0 && (expChar[i] == operator)){
				return true;
			}
		}
		return false;
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

			if(count == 0 && (expChar[i] == operator)){
				return i;
			}
		}
		return -1;
	}


	public static boolean isTrue(String q, ArrayList<TactNode> kbArray) throws Exception{
		String sym = getSymbol(q);
		String param = getParam(q);
		//System.out.println("Looking for symbol : " + sym );
		TactNode tn = null;
		boolean[] done = new boolean[kbArray.size()];
		Arrays.fill(done, false);
		for(int i=0; i<kbArray.size(); i++){
			// find a way to match symbol
			if((!done[i]) & findSymbol(kbArray.get(i), i, kbArray, done, sym)){
				tn = kbArray.get(i);
				//System.out.println("Found something " + tn.toString());
				done[i] = true;
				break;
			}
		}
		if(tn == null){
			return false;
		}else if(tn.hasVariable){
			return true;
		}else if(tn.hasOperator){
			if(tn.operator.equals("=>")){
				// TODO unification required before passing the value again
				return isTrue(tn.leftSide.unifiedString(param), kbArray);
			}else if(tn.operator.equals("&")){
				return (isTrue(tn.leftSide.unifiedString(param), kbArray) && isTrue(tn.rightSide.unifiedString(param), kbArray)); 
			}else if(tn.operator.equals("|")){
				return (isTrue(tn.leftSide.unifiedString(param), kbArray) || isTrue(tn.rightSide.unifiedString(param), kbArray));
			}else if(tn.operator.equals("~")){
				return (!isTrue(tn.rightSide.unifiedString(param), kbArray));
			}
		}else{
			if(tn.constant.equals(getParam(q))){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}


	private static boolean findSymbol(TactNode t, int i, ArrayList<TactNode> kbArray, boolean[] done, String symbol) {
		if(done[i]){
			return false;
		}
		done[i] = true;
		if(t.hasOperator){
			return findSymbol(t.rightSide, i, kbArray, done, symbol);
		}else{
			if(t.symbol.equals(symbol)){
				return true;
			}else{
				return false;
			}
		}
	}

}

/*
Description clearly states that variables are single lower case characters - only one character.
How to detect infinite loop in resolution?
https://piazza.com/class/isccqbm3kzy7kq?cid=444
https://piazza.com/class/isccqbm3kzy7kq?cid=598
True and False will not appear as literals in HW 3.
https://piazza.com/class/isccqbm3kzy7kq?cid=616



 */
