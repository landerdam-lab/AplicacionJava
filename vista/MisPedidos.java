
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.util.List;
import java.awt.Color;


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
        
        // --- TABLA ---
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 50, 740, 300);
        contentPane.add(scrollPane);
        
        tablaPedidos = new JTable();
        // Modelo: Columnas no editables
        modeloTabla = new DefaultTableModel(
            new Object[][] {},
            new String[] { "ID Pedido", "Fecha", "Tipo", "Total (€)" }
        ) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaPedidos.setModel(modeloTabla);
        scrollPane.setViewportView(tablaPedidos);
        
        // --- BOTONES ---
        JButton btnEliminar = new JButton("ELIMINAR / CANCELAR");
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setBackground(Color.RED);
        btnEliminar.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnEliminar.setBounds(560, 370, 200, 40);
        btnEliminar.addActionListener(e -> eliminarPedidoSeleccionado());
        contentPane.add(btnEliminar);
        
        JButton btnVolver = new JButton("Volver al Menú");
        btnVolver.setBounds(20, 370, 150, 40);
        btnVolver.addActionListener(e -> {
            this.dispose();
            new MenuCompras(clienteActual).setVisible(true);
        });
        contentPane.add(btnVolver);
        
        // Cargar datos al iniciar
        cargarTabla();
    }
    
    private void cargarTabla() {
        // Limpiamos la tabla
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
    
    private void eliminarPedidoSeleccionado() {
        int fila = tablaPedidos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un pedido de la tabla para eliminarlo.");
            return;
        }
        
        int idPedido = (int) modeloTabla.getValueAt(fila, 0);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
                "¿Seguro que quieres borrar el pedido " + idPedido + "?\nEl stock será devuelto.",
                "Confirmar Borrado", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean exito = pedidoDAO.eliminarPedido(idPedido);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Pedido eliminado correctamente.");
                cargarTabla(); // Refrescamos la tabla
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el pedido.");
            }
        }
    }
}