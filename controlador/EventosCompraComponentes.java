

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;


public class EventosCompraComponentes implements ActionListener {

    private CompraComponentes vista;
    private ComponenteDAO componenteDAO;
    private PedidoDAO pedidoDAO;
    
    // El estado del carrito vive en el controlador
    private List<LineaPedido> carritoDeCompra;
    private double precioTotalAcumulado = 0.0;

    public EventosCompraComponentes(CompraComponentes vista) {
        this.vista = vista;
        this.componenteDAO = new ComponenteDAO();
        this.pedidoDAO = new PedidoDAO();
        this.carritoDeCompra = new ArrayList<>();
    }

    // Método para cargar los datos en los combos de la vista al inicio
    public List<List<Componente>> obtenerCatalogo() {
        String[] tablasBD = { "PROCESADOR", "PLACA_BASE", "RAM", "TARJETA_GRAFICA", "DISCO_DURO", "CAJA", "FUENTE_ALIMENTACION", "REFRIGERACION", "MONITOR", "TECLADO", "RATON" };
        List<List<Componente>> catalogoCompleto = new ArrayList<>();
        
        for (String tabla : tablasBD) {
            catalogoCompleto.add(componenteDAO.obtenerComponentes(tabla));
        }
        return catalogoCompleto;
    }
    
    // Método para cuando venimos de "Editar Pedido"
    public void cargarProductosPrevios(List<LineaPedido> previos) {
        for (LineaPedido linea : previos) {
            Componente comp = componenteDAO.obtenerComponentePorId(linea.getIdComponente());
            if (comp != null) {
                anadirLogicaCarrito(comp, linea.getCantidad());
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == vista.getBtnVolver()) {
            vista.dispose();
            new MenuCompras(vista.getClienteActual()).setVisible(true);
        } 
        else if (source == vista.getBtnFinalizar()) {
            finalizarPedido();
        } 
        else if (source == vista.getBtnEliminarItem()) {
            eliminarDelCarrito();
        } 
        else {
            // Verificar si es uno de los botones "Añadir"
            // Usamos el ActionCommand que seteamos en la vista (0, 1, 2...)
            try {
                int index = Integer.parseInt(e.getActionCommand());
                Componente c = (Componente) vista.getComboAt(index).getSelectedItem();
                int cantidad = vista.getCantidadAt(index);
                
                if (c != null) anadirLogicaCarrito(c, cantidad);
                
            } catch (NumberFormatException ex) {
                // No era un botón de añadir
            }
        }
    }

    private void anadirLogicaCarrito(Componente c, int cantidad) {
        if (cantidad > c.getStock()) {
            JOptionPane.showMessageDialog(vista, "Stock insuficiente.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double subtotal = c.getPrecioVenta() * cantidad;
        precioTotalAcumulado += subtotal;

        // Añadir a lista lógica
        carritoDeCompra.add(new LineaPedido(c.getIdComponente(), cantidad, c.getPrecioVenta()));
        
        // Actualizar vista
        String texto = cantidad + "x " + c.getNombre() + " (" + String.format("%.2f", subtotal) + "€)";
        vista.agregarItemVisual(texto);
        vista.actualizarTotalVisual("TOTAL: " + String.format("%.2f", precioTotalAcumulado) + "€");
    }

    private void eliminarDelCarrito() {
        int index = vista.getListaVisualCarrito().getSelectedIndex();
        if (index != -1) {
            LineaPedido lp = carritoDeCompra.get(index);
            precioTotalAcumulado -= (lp.getPrecioUnitario() * lp.getCantidad());
            
            carritoDeCompra.remove(index);
            vista.quitarItemVisual(index);
            vista.actualizarTotalVisual("TOTAL: " + String.format("%.2f", precioTotalAcumulado) + "€");
        }
    }

    private void finalizarPedido() {
        if (carritoDeCompra.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Carrito vacío.");
            return;
        }

        boolean exito = pedidoDAO.registrarPedido(vista.getClienteActual(), carritoDeCompra, precioTotalAcumulado, false);

        if (exito) {
            JOptionPane.showMessageDialog(vista, "¡Pedido realizado!");
            vista.dispose();
            new MenuCompras(vista.getClienteActual()).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(vista, "Error al guardar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}