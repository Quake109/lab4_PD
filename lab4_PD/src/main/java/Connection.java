import com.arangodb.ArangoDB;

public class Connection {

    final ArangoDB arangoDB = new ArangoDB.Builder().user("root").password("admin").build();
    String dbName = "PolicemanDB";
    String collectionName = "PolicemanCol";

}
