import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class BaseDatos {


	public static void main(String[] args) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		String DB_URL = "jdbc:oracle:thin:@192.168.0.72:1521:ORCLCDB";
		String USER = "tienda_empleado_dos";
		String PASS = "Almi12345";  // ← Corregida

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
		} finally {
			// close the connection & statement object
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

	}
}
