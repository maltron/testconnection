
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {
    
    public static void main(String[] args) {
        Test test = new Test(args);
    }
    
    public Test(String[] args) {
        // Testing Number of Parameters
        if(args.length < 5 || args.length > 5) {
            System.out.printf(">>> Testing JDBC Connections. Please type:\n");
            System.out.printf(">>> java -cp testconnection.jar:<<yours jdbc driver>>.jar "+
                    "Test <JDBC Driver Class> <URL Connection> <Username> <Password> <SQL Query>\n");
            System.out.printf(">>> Example: Testing a MySQL Connection\n");
            System.out.printf(">>> java -cp testconnection.jar:mysql.jar Test \"com.mysql.jdbc.Driver\""+
                    "\"jdbc:mysql://localhost:3306/test\" \"someuser\" \"mypassword\" \"select * from people\"\n");
            
            System.exit(-1);
        }
        
        // Parameters
        String className = args[0];
        String urlConnection = args[1];
        String username = args[2];
        String password = args[3];
        String sqlQuery = args[4];

        // Step #1/3: Loading JDBC Device Driver
        try {
            System.out.printf(">>> LOAD    :%s\n", className);
            Class.forName(args[0]);
        } catch(ClassNotFoundException ex) {
            System.err.printf("### UNABLE TO LOAD CLASS:%s", ex.getMessage());
            System.exit(-1);
        }
        
        // Step #2/3: Connecting to Database
        Connection connection = null;
        try {
            System.out.printf(">>> CONNECT :%s, Username:%s, Password:%s\n", urlConnection,
                    username, password);
            connection = DriverManager.getConnection(urlConnection, username, password);
            System.out.printf(">>> CONNECTED\n");

            // Step #3/3: Executing Query into Database
            try {
                System.out.printf(">>> QUERY   :%s\n", sqlQuery);
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(sqlQuery);
                System.out.printf(">>> SUCCESS\n");

            } catch(SQLException ex) {
                System.err.printf("### SQL Exception:%s\n", ex.getMessage());
            }
            
        } catch(SQLException ex) {
            System.err.printf("### SQL Exception:%s\n", ex.getMessage());
            if(connection != null) try {connection.close(); System.exit(-1);}catch(Exception e){}
        } finally {
            System.out.printf(">>> CLOSING CONNECTION\n");
            if(connection != null) try{connection.close(); System.exit(0);}catch(Exception ex){}
        }
    }
}