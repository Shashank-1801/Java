package csci561;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

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
			processKB(kbArray);
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


	private static void processKB(ArrayList<TactNode> kbArray) {
		
		while(true){
			Set<TactNode> hs = new HashSet<>();
			hs.addAll(kbArray);
			int sizeBefore = hs.size();
			kbArray.clear();
			kbArray.addAll(hs);
			//printKBEntries(kbArray);

			for(int i=0; i<kbArray.size(); i++){
				TactNode tn = kbArray.get(i);
				processAND(tn, kbArray);
			}
			
			hs.addAll(kbArray);
			kbArray.clear();
			kbArray.addAll(hs);
			//printKBEntries(kbArray);
			
			for(int i=0; i<kbArray.size(); i++){
				TactNode tn = kbArray.get(i);
				processImplies(tn, kbArray);
			}
			
			hs.addAll(kbArray);
			kbArray.clear();
			kbArray.addAll(hs);
			//printKBEntries(kbArray);

			for(int i=0; i<kbArray.size(); i++){
				TactNode tn = kbArray.get(i);
				processOR(tn, kbArray);
			}
			
			hs.addAll(kbArray);
			kbArray.clear();
			kbArray.addAll(hs);
			//printKBEntries(kbArray);
			
			for(int i=0; i<kbArray.size(); i++){
				TactNode tn = kbArray.get(i);
				processNOT(tn, kbArray);
			}
			
			System.out.println("before rec");
			printKBEntries(kbArray);
			
			for(int i=0; i<kbArray.size(); i++){
				TactNode tn = kbArray.get(i);
				TactNode t = processRec(tn, kbArray);
				if(t!=null && !kbArray.contains(t)){
					kbArray.add(t);
				}
			}
			System.out.println("after rec");
			printKBEntries(kbArray);
			
			hs.addAll(kbArray);
			int sizeAfter = hs.size();
			
			if(sizeAfter==sizeBefore){
				// can't infer anything more from this
				kbArray.clear();
				kbArray.addAll(hs);
				break;
			}
		}
		
	}


	private static void processAND(TactNode tn, ArrayList<TactNode> kbArray) {
		if(tn.hasOperator){
			if(tn.operator.equals("&")){
				kbArray.add(tn.leftSide);
				kbArray.add(tn.rightSide);
			}
		}
	}
	
	private static void processImplies(TactNode tn, ArrayList<TactNode> kbArray) {
		if(tn.hasOperator){
			if(tn.operator.equals("=>")){
				for(int i=0; i<kbArray.size(); i++){
					if(kbArray.get(i).equals(tn.leftSide)){
						TactNode t = new TactNode(tn.rightSide.parenthisizedString());
						TactNode p = t.unify(tn.leftSide, kbArray.get(i));
						if(!kbArray.contains(p)){
							kbArray.add(p);
						}
					}
				}
			}
		}
	}

	private static void processOR(TactNode tn, ArrayList<TactNode> kbArray) {
		if(tn.hasOperator){
			if(tn.operator.equals("|")){
				for(int i=0; i<kbArray.size(); i++){
					if(kbArray.get(i).equals((negate(tn.leftSide)))){
						TactNode t = tn.rightSide;
						TactNode p = t.unify((negate(tn.leftSide)), kbArray.get(i));
						if(!kbArray.contains(p))
							kbArray.add(p);
					}
				}
				for(int i=0; i<kbArray.size(); i++){
					if(kbArray.get(i).equals((negate(tn.rightSide)))){
						TactNode t = tn.leftSide;
						TactNode p = t.unify((negate(tn.rightSide)), kbArray.get(i));
						if(!kbArray.contains(p))
							kbArray.add(p);
					}
				}
			}
		}
	}
	
	private static void processNOT(TactNode tn, ArrayList<TactNode> kbArray) {
		if(tn.hasOperator){
			if(tn.operator.equals("~")){
				if(tn.rightSide.hasOperator && tn.rightSide.operator.equals("~")){
					kbArray.add(tn.rightSide);
				}
			}
		}
	}

	private static TactNode negate(TactNode tn){
		TactNode negated = null;
		String x = "(~(";
		x += tn.parenthisizedString() + "))";
		negated = new TactNode(x);
		if(negated.operator.equals("~")){
			if(negated.rightSide.hasOperator && negated.rightSide.operator.equals("~")){
				return tn.rightSide;
			}
		}
		return negated;
	}

	private static void printKBEntries(ArrayList<TactNode> kbArray) {
		System.out.println("\n--------- KB Array is ---------");
		for(int i=0; i< kbArray.size(); i++){
			System.out.println((i+1) + " : " + kbArray.get(i).parenthisizedString());
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

	
	public static TactNode processRec(TactNode tn, ArrayList<TactNode> kbArray){
		String exp = tn.parenthisizedString();
		if(tn.hasOperator){
			String op = tn.operator;
			if(op.equals("&")){
				TactNode left = processRec(tn.leftSide, kbArray);
				TactNode right = processRec(tn.rightSide, kbArray);
				if(left!=null && right!=null){
					return new TactNode( "("  + left.parenthisizedString() + tn.operator + right.parenthisizedString() + ")");
				}else{
					return null;
				}
			}else if(op.equals("|")){
				TactNode left = processRec(tn.leftSide, kbArray);
				TactNode right = processRec(tn.rightSide, kbArray);
				if(left!=null && right!=null){
					return new TactNode( "("  + tn.leftSide.parenthisizedString() + tn.operator + tn.rightSide.parenthisizedString() + ")");
				}else{
					return null;
				}
			}else if(op.equals("=>")){
				TactNode left = processRec(tn.leftSide, kbArray);
				TactNode ri = tn.rightSide;
				if(left!=null){
					ri.unify(left, tn.leftSide);
					return new TactNode(ri.parenthisizedString());
				}else{
					return null;
				}
			}else if(op.equals("~")){
				TactNode right = processRec(tn.rightSide, kbArray);
				if(right!=null){
					return null;
				}else{
					return right;
				}
			}else{
				System.out.println("Thats odd");
				return null;
			}
		}else{
			// doesn't contain operator
			for(int i=0; i<kbArray.size(); i++){
				if(kbArray.get(i).equals(tn)){
					TactNode t = new TactNode(tn.parenthisizedString());
					TactNode p = t.unify(tn, kbArray.get(i));
					p = new TactNode(p.parenthisizedString());
					return p;
				}
			}
		}
		return null;
	}
	
	
	public static boolean isTrue(String q, ArrayList<TactNode> kbArray) throws Exception{
		
		// create a new copy of the KB
		// check if the symbol exists in the KB
		// if it does, add another sentence to KB with unification 
		//
		
		ArrayList<TactNode> tempKB = new ArrayList<>();
		tempKB.addAll(kbArray);
		
		TactNode query = new TactNode(q);
		if(tempKB.contains(query)){
			return true;
		}
		
		boolean[] done = new boolean[tempKB.size()];
		Arrays.fill(done, false);
		
//		if(query.hasOperator){
//			String op = query.operator;
//			if()
//		}
//		
//		
//		
//		
		
		return false;
		
		
		
		
		/*
		String sym = getSymbol(q);
		String param = getParam(q);
		//System.out.println("Looking for symbol : " + sym );
		TactNode tn = null;
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
		//done[i] = true;
		if(t.hasOperator){
			return findSymbol(t.rightSide, i, kbArray, done, symbol);
		}else{
			if(t.symbol.equals(symbol)){
				return true;
			}else{
				return false;
			}
		}*/
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
