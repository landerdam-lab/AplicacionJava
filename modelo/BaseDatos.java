import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDatos {

	Connection conn = null;
	Statement stmt = null;
	String DB_URL = "jdbc:oracle:thin:@10.58.41.147:1521:ORCLCDB";
	String USER = "tienda_informatica";
	String PASS = "Almi12345";

	public BaseDatos () {
		
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			if (conn != null) {
				System.out.println("Connected to the Oracle DB!");
				stmt = conn.createStatement();
			} else {
				System.out.println("Failed to make connection!");
			}

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public Statement getStmt() {
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}

	public String getDB_URL() {
		return DB_URL;
	}

	public void setDB_URL(String dB_URL) {
		DB_URL = dB_URL;
	}

	public String getUSER() {
		return USER;
	}

	public void setUSER(String uSER) {
		USER = uSER;
	}

	public String getPASS() {
		return PASS;
	}

}
