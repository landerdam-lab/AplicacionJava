import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {

	public Cliente loginCliente(String correo) {
	    
	    BaseDatos bd = new BaseDatos();
	    Connection con = bd.getConn();
	    Cliente clienteEncontrado = null; 

	    if (con == null) {
	    	return null;
	    }

	    String sql = "SELECT * FROM CLIENTE WHERE email = ?";

	    try {
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setString(1, correo);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            String nombre = rs.getString("NOMBRE");
	            String emailDb = rs.getString("EMAIL");
	            String dni = rs.getString("DNI");
	            String telefono = rs.getString("TELEFONO");
	            
	            clienteEncontrado = new Cliente(nombre, emailDb, dni, telefono);
	        }

	        rs.close();
	        ps.close();
	        con.close(); 

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return clienteEncontrado;
	}

	public boolean registrarCliente(String nombre, String email, String dni, String telefono) {

		BaseDatos bd = new BaseDatos();
		Connection con = bd.getConn();

		if (con == null) {
			return false;
		}

		String sql = "INSERT INTO CLIENTE (NOMBRE, EMAIL, DNI, TELEFONO) VALUES (?, ?, ?, ?)";

		try {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, nombre);
			ps.setString(2, email);
			ps.setString(3, dni);
			ps.setString(4, telefono);

			int filasAfectadas = ps.executeUpdate();

			ps.close();
			con.close();

			return filasAfectadas > 0;

		} catch (SQLException e) {
			System.out.println("Error al registrar cliente: " + e.getMessage());
			return false;
		}
	}
}
