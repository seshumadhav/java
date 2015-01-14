import java.net.UnknownHostException;

public class MongoDBDemo {

  public static void main(String[] args) throws UnknownHostException {
    CollectionCrud crud = new CollectionCrud("testColl1");
    crud.demo();
  }

}
