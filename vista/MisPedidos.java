import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Color;

public class MisPedidos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaPedidos;
	private DefaultTableModel modeloTabla;
	
	private JButton btnEliminar;
	private JButton btnEditar;
	private JButton btnVolver;
	private JButton btnPagar;

	private Cliente clienteActual;

	public MisPedidos(Cliente cliente) {
		this.clienteActual = cliente;
		setIconImage(Toolkit.getDefaultToolkit().getImage("imagenes\\logo.jpg"));

		setTitle("Historial de Pedidos - Cliente: " + cliente.getNombre());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitulo = new JLabel("Mis Pedidos Realizados");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTitulo.setBounds(20, 11, 300, 30);
		contentPane.add(lblTitulo);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 50, 840, 300);
		contentPane.add(scrollPane);

		tablaPedidos = new JTable();
		modeloTabla = new DefaultTableModel(
				new Object[][] {},
				new String[] { "ID Pedido", "Fecha", "Tipo", "Total (€)", "Estado" }
				) {
			public boolean isCellEditable(int row, int column) { return false; }
		};
		tablaPedidos.setModel(modeloTabla);
		scrollPane.setViewportView(tablaPedidos);

		btnVolver = new JButton("Volver al Menú");
		btnVolver.setBounds(20, 370, 150, 40);
		contentPane.add(btnVolver);

		btnPagar = new JButton("PAGAR AHORA");
		btnPagar.setForeground(Color.BLACK);
		btnPagar.setBackground(Color.GREEN);
		btnPagar.setBounds(360, 370, 150, 40);
		contentPane.add(btnPagar);

		btnEditar = new JButton("EDITAR PEDIDO");
		btnEditar.setForeground(Color.BLACK);
		btnEditar.setBackground(Color.ORANGE);
		btnEditar.setBounds(520, 370, 150, 40);
		contentPane.add(btnEditar);
		
		btnEliminar = new JButton("ELIMINAR");
		btnEliminar.setForeground(Color.WHITE);
		btnEliminar.setBackground(Color.RED);
		btnEliminar.setBounds(680, 370, 150, 40);
		contentPane.add(btnEliminar);

		EventosMisPedidos controller = new EventosMisPedidos(this);
		
		btnEliminar.addActionListener(controller);
		btnEditar.addActionListener(controller);
		btnVolver.addActionListener(controller);
		btnPagar.addActionListener(controller);

		controller.cargarDatosIniciales();
	}

	
	public Cliente getClienteActual() { 
		return clienteActual; 
	}
	public JTable getTablaPedidos() {
		return tablaPedidos; 
	}
	public DefaultTableModel getModeloTabla() {
		return modeloTabla; 
	}
	public JButton getBtnEliminar() { 
		return btnEliminar; 
	}
	public JButton getBtnEditar() { 
		return btnEditar; 
	}
	public JButton getBtnVolver() {
		return btnVolver; 
	}
	public JButton getBtnPagar() { 
		return btnPagar; 
	}
}