

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
        vista.getModeloTabla().setRowCount(0);
        List<Pedido> lista = pedidoDAO.listarPedidos(vista.getClienteActual().getEmail());
        for (Pedido p : lista) {
            vista.getModeloTabla().addRow(new Object[] {
                p.getIdPedido(),
                p.getFecha(),
                p.getTipoPedido(),
                p.getPrecioTotal()
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
    }

    private void eliminarPedido() {
        int fila = vista.getTablaPedidos().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Selecciona un pedido para eliminar.");
            return;
        }
        int idPedido = (int) vista.getModeloTabla().getValueAt(fila, 0);

        int confirm = JOptionPane.showConfirmDialog(vista, "¿Borrar pedido " + idPedido + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
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

        int confirm = JOptionPane.showConfirmDialog(vista, "Se eliminará el pedido actual y se cargará el carrito.\n¿Continuar?", "Editar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int idPedido = (int) vista.getModeloTabla().getValueAt(fila, 0);
            String tipoPedido = (String) vista.getModeloTabla().getValueAt(fila, 2);
            boolean esConfiguracion = tipoPedido.contains("Configurado");

            List<LineaPedido> productos = pedidoDAO.recuperarDetallesPedido(idPedido);
            boolean borrado = pedidoDAO.eliminarPedido(idPedido);

            if (borrado) {
                vista.dispose();
                if (esConfiguracion) {
                    new ConfigurarPC(vista.getClienteActual(), productos, true).setVisible(true);
                } else {
                    new CompraComponentes(vista.getClienteActual(), productos).setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(vista, "Error al procesar la edición.");
            }
        }
    }
}