package tp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReverseInput {
	public static void main(String[] a) throws IOException{

		FileInputStream fin = null;
		FileOutputStream fout = null;
		BufferedReader reader = null;
		try{
			//read file from here
			fin = new FileInputStream("./inputFiles/a.txt");
			// write to this file
			fout = new FileOutputStream("./outputFiles/a-out.txt");

			reader = new BufferedReader(new InputStreamReader(fin));
			
			String c;
			while ((c = reader.readLine()) != null) {
				System.out.println(c);
				String p = rev(c);
				System.out.println(p);
				fout.write(p.getBytes());
				fout.write("\n".getBytes());
			}
			System.out.println("Done!");

		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if (fin != null) {
				fin.close();
			}
			if (fout != null) {
				fout.close();
			}
			if (reader != null) {
				reader.close();
			}
		}

	}

	private static String rev(String str){
		int len = str.length();
		StringBuffer newS = new StringBuffer();
		for(int i=1; i<=len; i++){
			newS.append(str.charAt(len-i));
		}
		return new String(newS);

	}
}
