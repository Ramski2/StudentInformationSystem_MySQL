import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306";
    private static final String user = "root";
    private static final String password = "12345678";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL,user,password);
    }
}
