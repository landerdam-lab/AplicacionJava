import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {
	
	public boolean loginCliente(String correo) {

		BaseDatos bd = new BaseDatos();
		Connection con = bd.getConn();

		if (con == null) {
			return false;
		}

		String sql = "SELECT * FROM CLIENTE WHERE email = ?";
		boolean emailEncontrado = false;

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, correo);


			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				emailEncontrado = true;
			}

			rs.close();
			ps.close();
			con.close(); 

		} catch (SQLException e) {
			System.out.println("Error en la consulta SQL: " + e.getMessage());
			e.printStackTrace();
		}
		return emailEncontrado;
	}
}
