package csci561;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;

public class homework {
	public static void main(String[] a) throws IOException{
		FileInputStream fin = null;
		BufferedReader reader = null;
		Hashtable<String, TactNode> kb = new Hashtable<>();
		Hashtable<String, TactNode> queries = new Hashtable<>();
		try{
			fin = new FileInputStream("./input.txt");
			reader = new BufferedReader(new InputStreamReader(fin));

			int numQueries = Integer.parseInt(reader.readLine());
			System.out.println("Queries are :");
			ArrayList<String> queryEntries = new ArrayList<>();
			for(int i=0; i< numQueries; i++){
				String q = reader.readLine();
				System.out.println(q);
				queryEntries.add(q);
			}

			int numKB = Integer.parseInt(reader.readLine());
			System.out.println("\nKB entries are :");
			ArrayList<String> KBEntries = new ArrayList<>();
			for(int i=0; i< numKB; i++){
				String s  = reader.readLine();
				System.out.println(s);
				KBEntries.add(s);
			}

			kb = analyse(KBEntries);
			
			//check for each query
			System.out.println();
			for(int i=0; i<queryEntries.size(); i++){
				String q = queryEntries.get(i);
				String sym = getSymbol(q);
				TactNode tn = kb.get(sym);
				if(tn == null){
					System.out.println("FALSE");
				}else if(tn.hasVariable){
					System.out.println("TRUE");
				}else{
					if(tn.constant.equals(getParam(q))){
						System.out.println("TRUE");
					}else{
						System.out.println("FALSE");
					}
				}
			}
			

		} catch(Exception e){
			System.out.println("Some exception:" + e.getMessage());
			e.printStackTrace();
		}

	}


	public static Hashtable<String, TactNode> analyse(ArrayList<String> kbentries){
		//lets analyse the kb entries one by one
		Hashtable<String, TactNode> kb = new Hashtable<>();
		for(int i=0; i<kbentries.size(); i++){
			String k = kbentries.get(i);
			String symbol = getSymbol(k);
			String param = getParam(k);
			
			//TODO check if param is again a expression
			
			
			//TODO add to tact node
			TactNode node = new TactNode(symbol,param);
			kb.put(symbol, node);

		}

		return kb;
	}

	public static String getSymbol(String k){
		char openB = '(';
		char closeB = ')';
		int start = k.indexOf(openB);
		int end = k.indexOf(closeB);
		return k.substring(0, start);
	}
	
	public static String getParam(String k){
		char openB = '(';
		char closeB = ')';
		int start = k.indexOf(openB);
		int end = k.indexOf(closeB);
		return k.substring(start+1, end);
	}

}
