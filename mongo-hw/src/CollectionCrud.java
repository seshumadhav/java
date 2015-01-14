import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class CollectionCrud {

  private DBCollection collection;
  private String       collectionName;
  private DB           db;

  public CollectionCrud(String collectionName) throws UnknownHostException {
    this.collectionName = collectionName;
    init();
  }

  private CollectionCrud() {
  }

  private void init() throws UnknownHostException {
    MongoClient mongoClient = new MongoClient("localhost", 27017);
    db = mongoClient.getDB("mydb");
    collection = db.getCollection(collectionName);
  }

  public Set<String> getCollectionNames() {
    return db.getCollectionNames();
  }

  public void create(DBObject object) throws UnknownHostException {
    collection.insert(object);
  }

  public void bulkCreate() {
    for (int i = 0; i < 100; i++) {
      collection.insert(new BasicDBObject("i", i));
    }
  }

  public void demo() throws UnknownHostException {
    // INIT
    Set<String> collectionNames = getCollectionNames();
    StringBuffer sb = new StringBuffer();
    for (String collection : collectionNames) {
      sb.append(collection);
      sb.append("\n");
    }
    LOG.log("\nCollection names: \n" + sb.toString());

    // CREATE
    DBObject dbObject = createDbObject("name", "Sachin", "age", "40", "skills",
        "[bat, ball]", "state", "mumbai");
    // create(dbObject);
    // bulkCreate();

    // RETRIEVE
    LOG.log("\nTotal docs: " + collection.getCount());

    DBObject oneDocument = collection.findOne();
    LOG.log("\n\nRetrieved one doc: \n" + oneDocument.toString());

    List<DBObject> results = query(null);
    print("\n\nRetrieving all documents!", results);

    results = query(createDbObject("name", "Sachin"));
    print("\n\nRetrieving search results for given query: ", results);

    BasicDBObject obj = new BasicDBObject("i",
        new BasicDBObject("$gt", 53).append("$lt", 55));
    results = query(obj);
    print("\n\nRetrieving search results for given range query: ", results);
  }

  private List<DBObject> query(DBObject obj) {
    DBCursor cursor;

    if (obj != null) {
      cursor = collection.find(obj);
    } else {
      cursor = collection.find();
    }

    List<DBObject> results = new ArrayList<DBObject>();
    while (cursor.hasNext()) {
      results.add(cursor.next());
    }
    return results;
  }

  private void print(String message, Collection<?> collection) {
    StringBuffer sb = new StringBuffer();

    for (Object item : collection) {
      sb.append(item.toString());
      sb.append("\n");
    }

    LOG.log(message + "\n" + sb.toString());
  }

  public static DBObject createDbObject(String... args) {
    BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
    for (int i = 0; i < args.length; i += 2) {
      builder.add(args[i], args[i + 1]);
    }

    return builder.get();
  }

}
