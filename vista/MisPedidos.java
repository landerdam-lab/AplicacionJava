
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.util.List;
import java.awt.Color;
import java.awt.event.ActionListener;


public class MisPedidos extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;
    private Cliente clienteActual;
    private PedidoDAO pedidoDAO;

    public MisPedidos(Cliente cliente) {
        this.clienteActual = cliente;
        this.pedidoDAO = new PedidoDAO();
        
        setTitle("Historial de Pedidos - Cliente: " + cliente.getNombre());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblTitulo = new JLabel("Mis Pedidos Realizados");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitulo.setBounds(20, 11, 300, 30);
        contentPane.add(lblTitulo);
        
        // Tabla
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 50, 740, 300);
        contentPane.add(scrollPane);
        
        tablaPedidos = new JTable();
        modeloTabla = new DefaultTableModel(
            new Object[][] {},
            new String[] { "ID Pedido", "Fecha", "Tipo", "Total (€)" }
        ) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaPedidos.setModel(modeloTabla);
        scrollPane.setViewportView(tablaPedidos);
        
        // BOTÓN BORRAR
        JButton btnEliminar = new JButton("ELIMINAR");
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setBackground(Color.RED);
        btnEliminar.setBounds(600, 370, 160, 40);
        btnEliminar.addActionListener(e -> eliminarPedido());
        contentPane.add(btnEliminar);

        // BOTÓN EDITAR (NUEVO)
        JButton btnEditar = new JButton("EDITAR PEDIDO");
        btnEditar.setForeground(Color.BLACK);
        btnEditar.setBackground(Color.ORANGE);
        btnEditar.setBounds(430, 370, 160, 40);
        btnEditar.addActionListener(e -> editarPedido());
        contentPane.add(btnEditar);
        
        JButton btnVolver = new JButton("Volver al Menú");
        btnVolver.setBounds(20, 370, 150, 40);
        btnVolver.addActionListener(e -> {
            this.dispose();
            new MenuCompras(clienteActual).setVisible(true);
        });
        contentPane.add(btnVolver);
        
        cargarTabla();
    }
    
    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Pedido> lista = pedidoDAO.listarPedidos(clienteActual.getEmail());
        for (Pedido p : lista) {
            modeloTabla.addRow(new Object[] {
                p.getIdPedido(),
                p.getFecha(),
                p.getTipoPedido(),
                p.getPrecioTotal()
            });
        }
    }
    
 // EN MisPedidos.java

    private void editarPedido() {
        int fila = tablaPedidos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un pedido para editar.");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Se eliminará el pedido actual y se cargarán los productos para editar.\n¿Continuar?",
            "Editar Pedido", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            int idPedido = (int) modeloTabla.getValueAt(fila, 0);
            
            // Leemos el tipo de la tabla (Columna 2) para saber a dónde ir
            String tipoPedido = (String) modeloTabla.getValueAt(fila, 2);
            boolean esConfiguracion = tipoPedido.contains("Configurado");
            
            // 1. Recuperar productos
            List<LineaPedido> productos = pedidoDAO.recuperarDetallesPedido(idPedido);
            
            // 2. Borrar pedido viejo (Devuelve stock)
            boolean borrado = pedidoDAO.eliminarPedido(idPedido);
            
            if (borrado) {
                this.dispose();
                
                if (esConfiguracion) {
                    // ABRIR CONFIGURADOR (Pasamos true porque es una configuración)
                    ConfigurarPC ventanaConfig = new ConfigurarPC(clienteActual, productos, true);
                    ventanaConfig.setVisible(true);
                } else {
                    // ABRIR TIENDA NORMAL
                    CompraComponentes ventanaTienda = new CompraComponentes(clienteActual, productos);
                    ventanaTienda.setVisible(true);
                }
                
            } else {
                JOptionPane.showMessageDialog(this, "Error al borrar el pedido antiguo.");
            }
        }
    }
    
    // Acción ELIMINAR
    private void eliminarPedido() {
        int fila = tablaPedidos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un pedido para eliminar.");
            return;
        }
        int idPedido = (int) modeloTabla.getValueAt(fila, 0);
        
        int confirm = JOptionPane.showConfirmDialog(this, "¿Borrar pedido " + idPedido + "? El stock se devolverá.", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (pedidoDAO.eliminarPedido(idPedido)) {
                JOptionPane.showMessageDialog(this, "Pedido eliminado.");
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar.");
            }
        }
    }
}