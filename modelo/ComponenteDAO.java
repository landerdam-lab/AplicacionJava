
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComponenteDAO {

    public List<Componente> obtenerComponentes(String tabla) {
        List<Componente> lista = new ArrayList<>();
        BaseDatos bd = new BaseDatos();
        Connection con = bd.getConn();

        if (con == null) return lista;

        // SQL Dinámico
        String sql = "SELECT * FROM COMPONENTE c JOIN " + tabla + " t ON c.ID_COMPONENTE = t.ID_COMPONENTE";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Datos Comunes
                int id = rs.getInt("ID_COMPONENTE");
                String desc = rs.getString("DESCRIPCION");
                String nom = rs.getString("NOMBRE");
                int stock = rs.getInt("STOCK");
                int idMarca = rs.getInt("ID_MARCA");
                double precio = rs.getDouble("PRECIO_VENTA");

                Componente comp = null;

                // CORRECCIÓN: Los case ahora tienen guiones bajos y los nombres de columnas son los correctos
                switch (tabla.toUpperCase()) {
                    case "PROCESADOR":
                        // Error ORA-17006 solucionado: NUMERO_DE_NUCLEOS
                        comp = new Procesador(id, desc, nom, null, stock, idMarca, precio, 
                                rs.getString("NUMERO_DE_NUCLEOS"), rs.getString("FRECUENCIA_BASE"));
                        break;
                    case "RAM":
                        comp = new Ram(id, desc, nom, null, stock, idMarca, precio, 
                                rs.getString("TIPO"), rs.getString("FRECUENCIA"), rs.getString("CAPACIDAD"));
                        break;
                    case "DISCO_DURO": 
                        comp = new DiscoDuro(id, desc, nom, null, stock, idMarca, precio, 
                                rs.getString("TIPO_ALMACENAMIENTO"), rs.getString("CAPACIDAD"));
                        break;
                    case "PLACA_BASE": 
                        // Error ORA-17006 solucionado: FACTOR_DE_FORMA
                        comp = new PlacaBase(id, desc, nom, null, stock, idMarca, precio, 
                                rs.getString("SOCKET"), rs.getString("FACTOR_DE_FORMA"));
                        break;
                    case "TARJETA_GRAFICA": 
                        comp = new TarjetaGrafica(id, desc, nom, null, stock, idMarca, precio, 
                                rs.getString("VRAM"));
                        break;
                    case "FUENTE_ALIMENTACION": 
                        comp = new FuenteAlimentacion(id, desc, nom, null, stock, idMarca, precio,
                                rs.getString("CERTIFICACION_ENERGETICA"), rs.getString("POTENCIA"));
                        break;
                    case "CAJA":
                        // Asumo PUERTOS_FRONTALES según tu insert
                        comp = new Caja(id, desc, nom, null, stock, idMarca, precio,
                                rs.getString("DIMENSIONES"), rs.getString("PUERTOS_FRONTALES")); 
                        break;
                    case "REFRIGERACION":
                        // Error ORA-17006 solucionado: TAMAÑO con Ñ
                        comp = new Refrigeracion(id, desc, nom, null, stock, idMarca, precio,
                                rs.getString("TIPO"), rs.getString("TAMAÑO"));
                        break;
                    case "MONITOR":
                        comp = new Monitor(id, desc, nom, null, stock, idMarca, precio,
                                rs.getString("HZ"), rs.getString("MEDIDAS"));
                        break;
                    case "RATON":
                        comp = new Raton(id, desc, nom, null, stock, idMarca, precio,
                                rs.getString("DPI"), rs.getString("TIPO"));
                        break;
                    case "TECLADO":
                        comp = new Teclado(id, desc, nom, null, stock, idMarca, precio,
                                rs.getString("TIPO"), rs.getString("TIPO_CABLE"));
                        break;
                    default:
                        comp = new Componente(id, desc, nom, null, stock, idMarca, precio);
                        break;
                }

                if (comp != null) {
                    lista.add(comp);
                }
            }
            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Error SQL cargando " + tabla + ": " + e.getMessage());
        }
        return lista;
    }
 // ... (resto de métodos anteriores) ...

    // NUEVO: Buscar un componente solo por su ID (para recuperar pedidos)
 // ... (resto de métodos anteriores) ...

    // NUEVO: Buscar un componente solo por su ID
    public Componente obtenerComponentePorId(int id) {
        BaseDatos bd = new BaseDatos();
        Connection con = bd.getConn();
        Componente comp = null;
        
        if (con == null) return null;

        String sql = "SELECT * FROM COMPONENTE WHERE ID_COMPONENTE = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Creamos un componente genérico con los datos básicos
                comp = new Componente(
                    rs.getInt("ID_COMPONENTE"),
                    rs.getString("DESCRIPCION"),
                    rs.getString("NOMBRE"),
                    null, 
                    rs.getInt("STOCK"),
                    rs.getInt("ID_MARCA"),
                    rs.getDouble("PRECIO_VENTA")
                );
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comp;
    }
}
