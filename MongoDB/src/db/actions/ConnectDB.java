package db.actions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class ConnectDB {
	public static void main(String[] a){
		
		String mongoLabUri = "mongodb://admin:admin@ds051585.mongolab.com:51585/shekhar-db1";
		MongoClientURI mcURI = new MongoClientURI(mongoLabUri);
		MongoClient mc = new MongoClient(mcURI);
		
		MongoDatabase db = mc.getDatabase("shekhar-db1");
		System.out.println("Name of the data base is " + db.getName());
		
		
		String colName = "Col1";
		System.out.println("Creating collection named " + colName + " in " + db.getName());
		try{
			db.createCollection(colName);
			System.out.println("Collection created succesfully");
		}catch(MongoException me){
			System.out.println(me.getMessage());
		}
		MongoCollection<Document> dbc = db.getCollection(colName);
		
		Map<String, Object> ms = new HashMap<String, Object>();
		ms.put("First Name", "Shashank");
		ms.put("Last Name", "Shekhar");
		ms.put("Company", "Adobe Systems");
		ms.put("Birth Place", "Patna");
		ms.put("Age", 23);
		ms.put("No of Laptop", 2);
		ms.put("Owns Car", false);
		
		Map<String, Object> mo = new HashMap<String, Object>();
		mo.put("First Name", "Shashank");
		mo.put("Last Name", "Shekhar");
		mo.put("Company", "Yo yo");
		mo.put("Birth Place", "Patna");
		mo.put("Age", 23);
		mo.put("No of Laptop", 2);
		mo.put("Owns Car", false);
				
		Document d1 = new Document(ms);
		Document d2 = new Document(mo);
		Document d3 = new Document("NewVal", 55555555);
		
		dbc.insertOne(d1);
		dbc.insertOne(d2);
		dbc.insertOne(d3);
		
		
		for (Document d : dbc.find()) {
			System.out.println(d);
			Set<String> dkeys = d.keySet();
			for(String key : dkeys){
				System.out.println("key : " + key + " :: Val : " + d.get(key) );
			}
			System.out.println();
		}
		
		
		dbc.drop();
		mc.close();
		
	}

}
