package csci561;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;

public class homework {
	public static void main(String[] a) throws IOException{
		FileInputStream fin = null;
		FileOutputStream fout = null;
		BufferedReader reader = null;
		Hashtable<String, TactNode> kb = new Hashtable<>();
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

			kb = analyse(KBEntries);

			printKBEntries(kb);

			fout = new FileOutputStream("./output.txt");

			for(int i=0; i<queryEntries.size(); i++){
				String q = queryEntries.get(i);
				System.out.println("Is true for: " + q);
				boolean result = isTrue(q, kb);
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


	private static void printKBEntries(Hashtable<String, TactNode> kb) {
		System.out.println("\n--------- KB is ---------");
		for(String key : kb.keySet()){
			System.out.println(key + " : " + kb.get(key));
		}
		System.out.println();
	}


	public static Hashtable<String, TactNode> analyse(ArrayList<String> kbentries){
		//lets analyse the kb entries one by one
		// expected operators includes: ~, (,), & , =>, |
		Hashtable<String, TactNode> kb = new Hashtable<>();
		for(int i=0; i<kbentries.size(); i++){
			String k = kbentries.get(i);
			String symbol = getSymbol(k);
			String param = getParam(k);
			if(symbol.isEmpty()){
				System.out.println("String must contain an operator, check for & | ~ and => : " + param);
				resolveExpression(param, kb);
			}else{
				System.out.println("String is an predicate : " + k);
				//TODO add to tact node
				TactNode node = new TactNode(symbol,param);
				kb.put(symbol, node);
			}

			//TODO check if param is again a expression
		}

		return kb;
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
		char openB = '(';
		char closeB = ')';
		int start = k.indexOf(openB);
		int end = k.lastIndexOf(closeB);
		return k.substring(start+1, end).trim();
	}

	public static void resolveExpression(String exp, Hashtable<String, TactNode> kb){
		if(containsOperator(exp, '=')){
			int impliesAt = exp.indexOf("=");
			String left = exp.substring(0, impliesAt).trim();
			String right = exp.substring(impliesAt+2, exp.length()).trim();
			if(getSymbol(left).isEmpty()){
				resolveExpression(left, kb);
			}
			if(getSymbol(right).isEmpty()){
				resolveExpression(right,kb);
			}
			TactNode tn = new TactNode("=>", new TactNode(getSymbol(left), getParam(left)), new TactNode(getSymbol(right), getParam(right)));
			kb.put(getSymbol(right), tn);
		}else if(containsOperator(exp,'&')){
			int andAt = exp.indexOf("&");
			String left = exp.substring(0, andAt).trim();
			String right = exp.substring(andAt+1, exp.length()).trim();
			if(getSymbol(left).isEmpty()){
				resolveExpression(left, kb);
			}
			if(getSymbol(right).isEmpty()){
				resolveExpression(right,kb);
			}
			TactNode tn = new TactNode("&", new TactNode(getSymbol(left), getParam(left)), new TactNode(getSymbol(right), getParam(right)));
			kb.put(getSymbol(right), tn);
		}else if(containsOperator(exp, '|')){
			int andAt = exp.indexOf("|");
			String left = exp.substring(0, andAt).trim();
			String right = exp.substring(andAt+1, exp.length()).trim();
			if(getSymbol(left).isEmpty()){
				resolveExpression(left, kb);
			}
			if(getSymbol(right).isEmpty()){
				resolveExpression(right,kb);
			}
			TactNode tn = new TactNode("|", new TactNode(getSymbol(left), getParam(left)), new TactNode(getSymbol(right), getParam(right)));
			kb.put(getSymbol(right), tn);
		}else if(containsOperator(exp, '~')){
			int andAt = exp.indexOf("~");
			String right = exp.substring(andAt+1, exp.length()-1).trim();

			if(getSymbol(right).isEmpty()){
				resolveExpression(right,kb);
			}
			TactNode tn = new TactNode("~", null, new TactNode(getSymbol(right), getParam(right)));
			kb.put(getSymbol(right), tn);
		}else{
			System.out.println("expression doesn't hava an operator");
		}
	}

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


	public static boolean isTrue(String q, Hashtable<String, TactNode> kb){
		String sym = getSymbol(q);
		TactNode tn = kb.get(sym);
		if(tn == null){
			return false;
		}else if(tn.hasVariable){
			return true;
		}else if(tn.hasOperator){
			if(tn.operator.equals("=>")){
				return isTrue(tn.leftSide.toString(), kb);
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

}

/*
Description clearly states that variables are single lower case characters - only one character.
How to detect infinite loop in resolution?
https://piazza.com/class/isccqbm3kzy7kq?cid=444
https://piazza.com/class/isccqbm3kzy7kq?cid=598
True and False will not appear as literals in HW 3.
https://piazza.com/class/isccqbm3kzy7kq?cid=616



 */
