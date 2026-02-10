
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;



public class EventosMisPedidos implements ActionListener {

    private MisPedidos vista;
    private PedidoDAO pedidoDAO;

    public EventosMisPedidos(MisPedidos vista) {
        this.vista = vista;
        this.pedidoDAO = new PedidoDAO();
    }
    
    public void cargarDatosIniciales() {
        // Limpiamos la tabla
        vista.getModeloTabla().setRowCount(0);
        
        // Obtenemos los pedidos desde la BD
        List<Pedido> lista = pedidoDAO.listarPedidos(vista.getClienteActual().getEmail());
        
        for (Pedido p : lista) {
            vista.getModeloTabla().addRow(new Object[] {
                p.getIdPedido(),
                p.getFecha(),
                p.getTipoPedido(),
                p.getPrecioTotal(),
                p.getEstadoPago() // <--- AÑADIDO: Muestra "PAGADO" o "PENDIENTE"
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == vista.getBtnVolver()) {
            vista.dispose();
            new MenuCompras(vista.getClienteActual()).setVisible(true);
        }
        
        else if (e.getSource() == vista.getBtnEliminar()) {
            eliminarPedido();
        }
        
        else if (e.getSource() == vista.getBtnEditar()) {
            editarPedido();
        }
        
        // --- NUEVO LISTENER PARA PAGAR ---
        else if (e.getSource() == vista.getBtnPagar()) {
            pagarPedidoSeleccionado();
        }
    }

    // --- NUEVO MÉTODO PARA GESTIONAR EL PAGO ---
    private void pagarPedidoSeleccionado() {
        int fila = vista.getTablaPedidos().getSelectedRow();
        
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Selecciona un pedido de la tabla para pagar.");
            return;
        }
        
        // 1. Verificar si ya está pagado (Miramos la columna 4: Estado)
        String estadoActual = (String) vista.getModeloTabla().getValueAt(fila, 4);
        
        if ("PAGADO".equals(estadoActual)) {
            JOptionPane.showMessageDialog(vista, "Este pedido ya está pagado. ¡Gracias!", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // 2. Obtener datos para el mensaje
        int idPedido = (int) vista.getModeloTabla().getValueAt(fila, 0);
        double total = (double) vista.getModeloTabla().getValueAt(fila, 3);
        
        // 3. Confirmación (Simulación de Pasarela de Pago)
        int confirm = JOptionPane.showConfirmDialog(vista, 
                "Vas a proceder al pago del Pedido #" + idPedido + "\n" +
                "Importe total: " + total + "€\n" +
                "¿Confirmar pago?", 
                "Pasarela de Pago", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
                
        if (confirm == JOptionPane.YES_OPTION) {
            // 4. Llamar al DAO para actualizar la BD
            boolean exito = pedidoDAO.pagarPedido(idPedido);
            
            if (exito) {
                JOptionPane.showMessageDialog(vista, "¡Pago realizado con éxito! Gracias por tu compra.");
                // 5. Recargar la tabla para ver el cambio a "PAGADO"
                cargarDatosIniciales();
            } else {
                JOptionPane.showMessageDialog(vista, "Hubo un error al procesar el pago.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarPedido() {
        int fila = vista.getTablaPedidos().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Selecciona un pedido para eliminar.");
            return;
        }
        int idPedido = (int) vista.getModeloTabla().getValueAt(fila, 0);

        int confirm = JOptionPane.showConfirmDialog(vista, "¿Borrar pedido " + idPedido + "? El stock será devuelto.", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (pedidoDAO.eliminarPedido(idPedido)) {
                JOptionPane.showMessageDialog(vista, "Pedido eliminado.");
                cargarDatosIniciales();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al eliminar.");
            }
        }
    }

    private void editarPedido() {
        int fila = vista.getTablaPedidos().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Selecciona un pedido para editar.");
            return;
        }

        // Opcional: Impedir editar si ya está pagado (descomentar si quieres esta regla)
        /*
        String estado = (String) vista.getModeloTabla().getValueAt(fila, 4);
        if ("PAGADO".equals(estado)) {
            JOptionPane.showMessageDialog(vista, "No se puede editar un pedido ya pagado.");
            return;
        }
        */

        int confirm = JOptionPane.showConfirmDialog(vista, 
                "Al editar, se eliminará el pedido actual y sus productos volverán al carrito.\n¿Continuar?", 
                "Editar Pedido", 
                JOptionPane.YES_NO_OPTION);
                
        if (confirm == JOptionPane.YES_OPTION) {
            int idPedido = (int) vista.getModeloTabla().getValueAt(fila, 0);
            String tipoPedido = (String) vista.getModeloTabla().getValueAt(fila, 2);
            boolean esConfiguracion = tipoPedido.contains("Configurado"); // Detectamos si era PC completo

            // 1. Recuperamos los productos antes de borrar
            List<LineaPedido> productos = pedidoDAO.recuperarDetallesPedido(idPedido);
            
            // 2. Borramos el pedido viejo (esto devuelve el stock automáticamente)
            boolean borrado = pedidoDAO.eliminarPedido(idPedido);

            if (borrado) {
                vista.dispose();
                
                // 3. Abrimos la ventana correspondiente recargando los productos
                if (esConfiguracion) {
                    ConfigurarPC ventana = new ConfigurarPC(vista.getClienteActual(), productos, true);
                    ventana.setVisible(true);
                } else {
                    CompraComponentes ventana = new CompraComponentes(vista.getClienteActual(), productos);
                    ventana.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(vista, "Error al procesar la edición.");
            }
        }
    }
}