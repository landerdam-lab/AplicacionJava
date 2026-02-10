
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public boolean registrarPedido(Cliente cliente, List<LineaPedido> carrito, double total, boolean conMontaje) {
        BaseDatos bd = new BaseDatos();
        Connection con = bd.getConn();
        
        if (con == null) return false;

        PreparedStatement psPedido = null;
        PreparedStatement psDetalle = null;
        PreparedStatement psStock = null;

        try {
            con.setAutoCommit(false);

            int nuevoIdPedido = 1;
            String sqlId = "SELECT NVL(MAX(ID_PEDIDO), 0) + 1 FROM PEDIDO";
            PreparedStatement psId = con.prepareStatement(sqlId);
            ResultSet rsId = psId.executeQuery();
            if (rsId.next()) {
                nuevoIdPedido = rsId.getInt(1);
            }
            rsId.close();
            psId.close();

            String sqlCabecera = "INSERT INTO PEDIDO (ID_PEDIDO, PRECIO_TOTAL, MONTAJE, FECHA_VENTA, ID_CLIENTE, USUARIO_TRABAJADOR) " +
                                 "VALUES (?, ?, ?, CURRENT_DATE, (SELECT ID_CLIENTE FROM CLIENTE WHERE EMAIL = ?), 'admin')";
            
            psPedido = con.prepareStatement(sqlCabecera);
            psPedido.setInt(1, nuevoIdPedido);
            psPedido.setDouble(2, total);
            psPedido.setInt(3, conMontaje ? 1 : 0); 
            psPedido.setString(4, cliente.getEmail());
            psPedido.executeUpdate();

            String sqlDetalle = "INSERT INTO PEDIDO_COMPONENTE (ID_PEDIDO, ID_COMPONENTE, CANTIDAD, PRECIO_UNITARIO) VALUES (?, ?, ?, ?)";
            String sqlUpdateStock = "UPDATE COMPONENTE SET STOCK = STOCK - ? WHERE ID_COMPONENTE = ?";
            
            psDetalle = con.prepareStatement(sqlDetalle);
            psStock = con.prepareStatement(sqlUpdateStock);

            for (LineaPedido linea : carrito) {
                psDetalle.setInt(1, nuevoIdPedido);
                psDetalle.setInt(2, linea.getIdComponente());
                psDetalle.setInt(3, linea.getCantidad());
                psDetalle.setDouble(4, linea.getPrecioUnitario());
                psDetalle.executeUpdate();

                psStock.setInt(1, linea.getCantidad());
                psStock.setInt(2, linea.getIdComponente());
                int filas = psStock.executeUpdate();
                
                if (filas == 0) {
                    throw new SQLException("Error de stock en producto ID: " + linea.getIdComponente());
                }
            }

            con.commit(); 
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback(); 
            } catch (SQLException ex) { ex.printStackTrace(); }
            return false;
        } finally {
            try {
                if (psPedido != null) psPedido.close();
                if (psDetalle != null) psDetalle.close();
                if (psStock != null) psStock.close();
                con.close();
            } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public List<Pedido> listarPedidos(String emailCliente) {
        List<Pedido> lista = new ArrayList<>();
        BaseDatos bd = new BaseDatos();
        Connection con = bd.getConn();
        
        if (con == null) return lista;
        
        String sql = "SELECT * FROM PEDIDO WHERE ID_CLIENTE = (SELECT ID_CLIENTE FROM CLIENTE WHERE EMAIL = ?) ORDER BY ID_PEDIDO DESC";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, emailCliente);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                Pedido p = new Pedido(
                    rs.getInt("ID_PEDIDO"),
                    rs.getDouble("PRECIO_TOTAL"),
                    rs.getInt("MONTAJE") == 1, 
                    rs.getDate("FECHA_VENTA"),
                    rs.getInt("PAGADO") == 1,
                    rs.getInt("ID_CLIENTE") 
                );
                lista.add(p);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean pagarPedido(int idPedido) {
        BaseDatos bd = new BaseDatos();
        Connection con = bd.getConn();
        
        if (con == null) return false;
        
        String sql = "UPDATE PEDIDO SET PAGADO = 1 WHERE ID_PEDIDO = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idPedido);
            
            int filasAfectadas = ps.executeUpdate();
            
            ps.close();
            con.close();
            
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarPedido(int idPedido) {
        BaseDatos bd = new BaseDatos();
        Connection con = bd.getConn();
        if (con == null) return false;
        
        try {
            con.setAutoCommit(false);
            
            String sqlRecuperarStock = "SELECT ID_COMPONENTE, CANTIDAD FROM PEDIDO_COMPONENTE WHERE ID_PEDIDO = ?";
            PreparedStatement psLeer = con.prepareStatement(sqlRecuperarStock);
            psLeer.setInt(1, idPedido);
            ResultSet rs = psLeer.executeQuery();
            
            String sqlUpdateStock = "UPDATE COMPONENTE SET STOCK = STOCK + ? WHERE ID_COMPONENTE = ?";
            PreparedStatement psStock = con.prepareStatement(sqlUpdateStock);
            
            while(rs.next()) {
                psStock.setInt(1, rs.getInt("CANTIDAD")); 
                psStock.setInt(2, rs.getInt("ID_COMPONENTE"));
                psStock.executeUpdate();
            }
            
            String sqlBorrarLineas = "DELETE FROM PEDIDO_COMPONENTE WHERE ID_PEDIDO = ?";
            PreparedStatement psLineas = con.prepareStatement(sqlBorrarLineas);
            psLineas.setInt(1, idPedido);
            psLineas.executeUpdate();
            
            String sqlBorrarCabecera = "DELETE FROM PEDIDO WHERE ID_PEDIDO = ?";
            PreparedStatement psCabecera = con.prepareStatement(sqlBorrarCabecera);
            psCabecera.setInt(1, idPedido);
            psCabecera.executeUpdate();
            
            con.commit(); 
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            try { con.rollback(); } catch (SQLException ex) {}
            return false;
        }
    }

    public List<LineaPedido> recuperarDetallesPedido(int idPedido) {
        List<LineaPedido> detalles = new ArrayList<>();
        BaseDatos bd = new BaseDatos();
        Connection con = bd.getConn();
        
        if (con == null) return detalles;
        
        String sql = "SELECT ID_COMPONENTE, CANTIDAD, PRECIO_UNITARIO FROM PEDIDO_COMPONENTE WHERE ID_PEDIDO = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idPedido);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                LineaPedido linea = new LineaPedido(
                    rs.getInt("ID_COMPONENTE"),
                    rs.getInt("CANTIDAD"),
                    rs.getDouble("PRECIO_UNITARIO")
                );
                detalles.add(linea);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalles;
    }

    public List<Pedido> listarPedidosImpagados() {
        List<Pedido> lista = new ArrayList<>();
        BaseDatos bd = new BaseDatos();
        Connection con = bd.getConn();

        if (con == null) return lista;

        String sql = "{ call LISTAR_PEDIDOS_IMPAGADOS(?) }";

        try {
            CallableStatement cs = con.prepareCall(sql);
            cs.registerOutParameter(1, -10); 
            cs.execute();

            ResultSet rs = (ResultSet) cs.getObject(1);

            while (rs.next()) {
                Pedido p = new Pedido(
                    rs.getInt("ID_PEDIDO"),
                    rs.getDouble("PRECIO_TOTAL"),
                    false, 
                    rs.getDate("FECHA_VENTA"),
                    false,
                    rs.getInt("ID_CLIENTE")
                );
                lista.add(p);
            }
            
            rs.close();
            cs.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Error al listar impagados: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }
}