import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public Usuario loginUsuario(String user, String pass) {
        
        BaseDatos bd = new BaseDatos();
        Connection con = bd.getConn();
        Usuario usuarioEncontrado = null;

        if (con == null) return null;

        String sql = "SELECT * FROM TRABAJADOR WHERE USUARIO_TRABAJADOR = ? AND CONTRASEÑA_TRABAJADOR = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuarioEncontrado = new Usuario(
                    rs.getString("USUARIO_TRABAJADOR"),
                    rs.getString("NOMBRE"),
                    rs.getString("APELLIDO1"),
                    rs.getString("APELLIDO2"),
                    rs.getString("NIF"),
                    rs.getString("CONTRASEÑA_TRABAJADOR"),
                    rs.getInt("ES_ADMIN")
                );
            }

            rs.close();
            ps.close();
            con.close(); 

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarioEncontrado;
    }
}