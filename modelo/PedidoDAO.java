
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList; // Importante para las listas
import java.util.List;

public class PedidoDAO {

    // 1. REGISTRAR PEDIDO (El que ya tenías)
    public boolean registrarPedido(Cliente cliente, List<LineaPedido> carrito, double total, boolean conMontaje) {
        BaseDatos bd = new BaseDatos();
        Connection con = bd.getConn();
        
        if (con == null) return false;

        PreparedStatement psPedido = null;
        PreparedStatement psDetalle = null;
        PreparedStatement psStock = null;

        try {
            con.setAutoCommit(false); // Inicio transacción

            // A. Obtener nuevo ID
            int nuevoIdPedido = 1;
            String sqlId = "SELECT NVL(MAX(ID_PEDIDO), 0) + 1 FROM PEDIDO";
            PreparedStatement psId = con.prepareStatement(sqlId);
            ResultSet rsId = psId.executeQuery();
            if (rsId.next()) {
                nuevoIdPedido = rsId.getInt(1);
            }
            rsId.close();
            psId.close();

            // B. Insertar Cabecera PEDIDO
            String sqlCabecera = "INSERT INTO PEDIDO (ID_PEDIDO, PRECIO_TOTAL, MONTAJE, FECHA_VENTA, ID_CLIENTE, USUARIO_TRABAJADOR) " +
                                 "VALUES (?, ?, ?, CURRENT_DATE, (SELECT ID_CLIENTE FROM CLIENTE WHERE EMAIL = ?), 'admin')";
            
            psPedido = con.prepareStatement(sqlCabecera);
            psPedido.setInt(1, nuevoIdPedido);
            psPedido.setDouble(2, total);
            psPedido.setInt(3, conMontaje ? 1 : 0); 
            psPedido.setString(4, cliente.getEmail());
            psPedido.executeUpdate();

            // C. Insertar Detalles y Restar Stock
            String sqlDetalle = "INSERT INTO PEDIDO_COMPONENTE (ID_PEDIDO, ID_COMPONENTE, CANTIDAD, PRECIO_UNITARIO) VALUES (?, ?, ?, ?)";
            String sqlUpdateStock = "UPDATE COMPONENTE SET STOCK = STOCK - ? WHERE ID_COMPONENTE = ?";
            
            psDetalle = con.prepareStatement(sqlDetalle);
            psStock = con.prepareStatement(sqlUpdateStock);

            for (LineaPedido linea : carrito) {
                // Detalle
                psDetalle.setInt(1, nuevoIdPedido);
                psDetalle.setInt(2, linea.getIdComponente());
                psDetalle.setInt(3, linea.getCantidad());
                psDetalle.setDouble(4, linea.getPrecioUnitario());
                psDetalle.executeUpdate();

                // Stock
                psStock.setInt(1, linea.getCantidad());
                psStock.setInt(2, linea.getIdComponente());
                int filas = psStock.executeUpdate();
                
                if (filas == 0) {
                    throw new SQLException("Error de stock en producto ID: " + linea.getIdComponente());
                }
            }

            con.commit(); // Confirmar cambios
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback(); // Cancelar si hay error
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

    // 2. LISTAR PEDIDOS (NUEVO) - Para ver el historial en la tabla
    public List<Pedido> listarPedidos(String emailCliente) {
        List<Pedido> lista = new ArrayList<>();
        BaseDatos bd = new BaseDatos();
        Connection con = bd.getConn();
        
        if (con == null) return lista;
        
        // Buscamos pedidos del cliente por su email ordenados por fecha
        String sql = "SELECT * FROM PEDIDO WHERE ID_CLIENTE = (SELECT ID_CLIENTE FROM CLIENTE WHERE EMAIL = ?) ORDER BY ID_PEDIDO DESC";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, emailCliente);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                Pedido p = new Pedido(
                    rs.getInt("ID_PEDIDO"),
                    rs.getDouble("PRECIO_TOTAL"),
                    rs.getInt("MONTAJE") == 1, // Convertimos el 1 de Oracle a boolean true
                    rs.getDate("FECHA_VENTA")
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

    // 3. ELIMINAR PEDIDO (NUEVO) - Devuelve el stock y borra el pedido
    public boolean eliminarPedido(int idPedido) {
        BaseDatos bd = new BaseDatos();
        Connection con = bd.getConn();
        if (con == null) return false;
        
        try {
            con.setAutoCommit(false); // Inicio transacción para seguridad
            
            // A. Recuperar Stock: Vemos qué tenía el pedido y se lo devolvemos a la tienda
            String sqlRecuperarStock = "SELECT ID_COMPONENTE, CANTIDAD FROM PEDIDO_COMPONENTE WHERE ID_PEDIDO = ?";
            PreparedStatement psLeer = con.prepareStatement(sqlRecuperarStock);
            psLeer.setInt(1, idPedido);
            ResultSet rs = psLeer.executeQuery();
            
            String sqlUpdateStock = "UPDATE COMPONENTE SET STOCK = STOCK + ? WHERE ID_COMPONENTE = ?";
            PreparedStatement psStock = con.prepareStatement(sqlUpdateStock);
            
            while(rs.next()) {
                psStock.setInt(1, rs.getInt("CANTIDAD")); // Sumamos cantidad
                psStock.setInt(2, rs.getInt("ID_COMPONENTE"));
                psStock.executeUpdate();
            }
            
            // B. Borrar las líneas del pedido
            String sqlBorrarLineas = "DELETE FROM PEDIDO_COMPONENTE WHERE ID_PEDIDO = ?";
            PreparedStatement psLineas = con.prepareStatement(sqlBorrarLineas);
            psLineas.setInt(1, idPedido);
            psLineas.executeUpdate();
            
            // C. Borrar el pedido (Cabecera)
            String sqlBorrarCabecera = "DELETE FROM PEDIDO WHERE ID_PEDIDO = ?";
            PreparedStatement psCabecera = con.prepareStatement(sqlBorrarCabecera);
            psCabecera.setInt(1, idPedido);
            psCabecera.executeUpdate();
            
            con.commit(); // Confirmar borrado y devolución de stock
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            try { con.rollback(); } catch (SQLException ex) {}
            return false;
        }
    }
    public List<LineaPedido> recuperarDetallesPedido(int idPedido) {
        List<LineaPedido> detalles = new ArrayList<>();
        // SQL para sacar ID_COMPONENTE, CANTIDAD y PRECIO de PEDIDO_COMPONENTE
        // ...
        return detalles;
    }
}