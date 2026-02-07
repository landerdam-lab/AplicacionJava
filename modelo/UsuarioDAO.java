import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public String loginTrabajador(String user, String pass) {
        
        BaseDatos bd = new BaseDatos();
        Connection con = bd.getConn();
        
        if (con == null) {
            return null;
        }

        String sql = "SELECT nombre FROM TRABAJADOR WHERE usuario_trabajador = ? AND contraseña_trabajador = ?";
        String nombreEncontrado = null;

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                nombreEncontrado = rs.getString("nombre");
            }

            rs.close();
            ps.close();
            con.close(); 

        } catch (SQLException e) {
            System.out.println("Error en la consulta SQL: " + e.getMessage());
            e.printStackTrace();
        }

        return nombreEncontrado;
    }
}