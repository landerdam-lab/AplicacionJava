
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;


public class EventosConfigurarPC implements ActionListener {

    private ConfigurarPC vista;
    private ComponenteDAO componenteDAO;
    private PedidoDAO pedidoDAO;
    private double precioTotalCalculado = 0.0;
    private final double PRECIO_MONTAJE = 50.0;

    public EventosConfigurarPC(ConfigurarPC vista) {
        this.vista = vista;
        this.componenteDAO = new ComponenteDAO();
        this.pedidoDAO = new PedidoDAO();
    }

    public List<List<Componente>> obtenerDatosCombos() {
        String[] tablasBD = { "PROCESADOR", "PLACA_BASE", "RAM", "TARJETA_GRAFICA", "DISCO_DURO", "CAJA", "FUENTE_ALIMENTACION" };
        List<List<Componente>> datos = new ArrayList<>();
        for (String t : tablasBD) datos.add(componenteDAO.obtenerComponentes(t));
        return datos;
    }

    public void cargarConfiguracionPrevia(List<LineaPedido> viejos, boolean montaje) {
        vista.getChkMontaje().setSelected(montaje);
        
        for (LineaPedido linea : viejos) {
            for (JComboBox<Componente> combo : vista.getCombosComponentes()) {
                for (int i = 0; i < combo.getItemCount(); i++) {
                    Componente c = combo.getItemAt(i);
                    if (c != null && c.getIdComponente() == linea.getIdComponente()) {
                        combo.setSelectedIndex(i);
                        break;
                    }
                }
            }
        }
        recalcular();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnVolver()) {
            vista.dispose();
            new MenuCompras(vista.getClienteActual()).setVisible(true);
        }
        else if (e.getSource() == vista.getBtnComprar()) {
            finalizarCompra();
        }
        else {
            recalcular();
        }
    }

    private void recalcular() {
        double total = 0.0;
        StringBuilder sb = new StringBuilder();
        sb.append("--- CONFIGURACIÓN ---\n\n");

        for (JComboBox<Componente> combo : vista.getCombosComponentes()) {
            Componente c = (Componente) combo.getSelectedItem();
            if (c != null) {
                total += c.getPrecioVenta();
                sb.append("• ").append(c.getNombre()).append("\n")
                  .append("   ").append(c.getPrecioVenta()).append("€\n\n");
            }
        }

        if (vista.getChkMontaje().isSelected()) {
            total += PRECIO_MONTAJE;
            sb.append("• MONTAJE Y TESTEO\n   ").append(PRECIO_MONTAJE).append("€\n");
        }

        this.precioTotalCalculado = total;
        vista.setTextoResumen(sb.toString());
        vista.setTextoTotal("TOTAL: " + String.format("%.2f", total) + "€");
    }

    private void finalizarCompra() {
        for (JComboBox<Componente> combo : vista.getCombosComponentes()) {
            if (combo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(vista, "Selecciona todos los componentes.");
                return;
            }
        }

        List<LineaPedido> carrito = new ArrayList<>();
        for (JComboBox<Componente> combo : vista.getCombosComponentes()) {
            Componente c = (Componente) combo.getSelectedItem();
            if (c.getStock() < 1) {
                JOptionPane.showMessageDialog(vista, "Sin stock: " + c.getNombre());
                return;
            }
            carrito.add(new LineaPedido(c.getIdComponente(), 1, c.getPrecioVenta()));
        }

        boolean exito = pedidoDAO.registrarPedido(vista.getClienteActual(), carrito, precioTotalCalculado, vista.getChkMontaje().isSelected());

        if (exito) {
            JOptionPane.showMessageDialog(vista, "¡PC Comprado!");
            vista.dispose();
            new MenuCompras(vista.getClienteActual()).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(vista, "Error al guardar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}