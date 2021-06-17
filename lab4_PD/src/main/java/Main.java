import java.util.Scanner;
import com.arangodb.*;
import com.arangodb.entity.BaseDocument;

public class Main {


    public static void save(){

        Connection con = new Connection();
        ArangoDB arangoDB = con.arangoDB;
        String dbName = con.dbName;
        String collectionName = con.collectionName;

        BaseDocument Policeman1 = new BaseDocument();
        Policeman1.setKey("1");
        Policeman1.addAttribute("Name", "Krzyszotof");
        Policeman1.addAttribute("Surname", "Siczek");
        Policeman1.addAttribute("Salary", 5000.0);

        BaseDocument Policeman2 = new BaseDocument();
        Policeman2.setKey("2");
        Policeman2.addAttribute("Name", "Anna");
        Policeman2.addAttribute("Surname", "Bak");
        Policeman2.addAttribute("Salary", 1900.0);

        BaseDocument Policeman3 = new BaseDocument();
        Policeman3.setKey("3");
        Policeman3.addAttribute("Name", "Jan");
        Policeman3.addAttribute("Surname", "Kowalski");
        Policeman3.addAttribute("Salary", 2500.0);


        try {
            arangoDB.db(dbName).collection(collectionName).insertDocument(Policeman1);
            arangoDB.db(dbName).collection(collectionName).insertDocument(Policeman2);
            arangoDB.db(dbName).collection(collectionName).insertDocument(Policeman3);
            System.out.println("Documents created");
        } catch (ArangoDBException e) {
            System.err.println("Failed to create document. " + e.getMessage());
        }
    }

    public static void update(){

        Connection con = new Connection();
        ArangoDB arangoDB = con.arangoDB;
        String dbName = con.dbName;
        String collectionName = con.collectionName;


        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the ID of Policeman to update records: ");
        String id = scan.next();
        if(arangoDB.db(dbName).collection(collectionName).documentExists(id)) {

            BaseDocument newPolicemanSalary = arangoDB.db(dbName).collection(collectionName).getDocument(id, BaseDocument.class);

            newPolicemanSalary.setKey(id);
            System.out.println("Enter new salary");
            String newSalary = scan.next();
            newPolicemanSalary.addAttribute("Salary", newSalary);


            try {
                arangoDB.db(dbName).collection(collectionName).updateDocument(id, newPolicemanSalary);
            } catch (ArangoDBException e) {
                System.err.println("Failed to update document. " + e.getMessage());
            }
        }
        else {
            System.out.println("Record #" + id + " doesn't exist. Failed to update record.");
        }
    }

    public static void delete(){

        Connection con = new Connection();
        ArangoDB arangoDB = con.arangoDB;
        String dbName = con.dbName;
        String collectionName = con.collectionName;


        Scanner scan = new Scanner(System.in);
        System.out.println("Enter ID of Policeman to delete : ");
        String delID = scan.next();

        try {
            arangoDB.db(dbName).collection(collectionName).deleteDocument(delID);
            System.out.println("Record #" + delID + " deleted successfully.");
        } catch (ArangoDBException e) {
            System.err.println("The record ID does not exist. Failed to delete record.");
        }
    }

    public static void download(){

        Connection con = new Connection();
        ArangoDB arangoDB = con.arangoDB;
        String dbName = con.dbName;
        String collectionName = con.collectionName;

        try {
                System.out.println(arangoDB.db(dbName).collection(collectionName).getDocument("1",
                        BaseDocument.class));
                System.out.println(arangoDB.db(dbName).collection(collectionName).getDocument("2",
                        BaseDocument.class));
                System.out.println(arangoDB.db(dbName).collection(collectionName).getDocument("3",
                        BaseDocument.class));
        } catch (NullPointerException e) {
            System.err.println("Record doesn't exist. ");
        }
    }

    public static void downloadByID(){

        Connection con = new Connection();
        ArangoDB arangoDB = con.arangoDB;
        String dbName = con.dbName;
        String collectionName = con.collectionName;

        Scanner scan = new Scanner(System.in);
        try {
            System.out.println("Enter ID of Policeman to check records :");
            String checkID = scan.next();

            System.out.println(arangoDB.db(dbName).collection(collectionName).getDocument(checkID,
                    BaseDocument.class));

        } catch (NullPointerException e) {
            System.err.println("Record doesn't exist. ");
        }
    }


    public static void main(String[] args) {

        Connection con = new Connection();
        ArangoDB arangoDB = con.arangoDB;
        String dbName = con.dbName;
        String collectionName = con.collectionName;

        try {
            arangoDB.createDatabase(dbName);
            arangoDB.db(dbName).createCollection(collectionName);
        } catch (ArangoDBException e) {
            System.err.println("Database already created.");
        }

        Scanner scan = new Scanner(System.in);
        String option = "";
        while (!option.equals("0")) {
            System.out.println("Pick the option: ");
            System.out.println("1.Save");
            System.out.println("2.Update Policeman Salary");
            System.out.println("3.Delete Policeman");
            System.out.println("4.Download");
            System.out.println("5.Download Policeman by id");
            System.out.println("0.Exit");
            option = scan.nextLine();
            switch (option) {
                case "0":
                    System.exit(0);
                    break;
                case "1":
                    save();
                    break;
                case "2":
                    update();
                    break;
                case "3":
                    delete();
                    break;
                case "4":
                    download();
                    break;
                case "5":
                    downloadByID();
                    break;
            }
        }
    }

}