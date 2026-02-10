
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

public class PedidosImpagados extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaImpagos;
	private DefaultTableModel modeloTabla;
	private JButton btnVolver;
	private JButton btnRefrescar;

	public PedidosImpagados() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("imagenes\\logo.jpg"));
		setTitle("Listado de Morosos (Solo Pedidos)");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 500); 
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitulo = new JLabel("PEDIDOS PENDIENTES DE PAGO");
		lblTitulo.setForeground(Color.RED);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTitulo.setBounds(20, 11, 400, 30);
		contentPane.add(lblTitulo);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 50, 540, 300);
		contentPane.add(scrollPane);

		tablaImpagos = new JTable();
		modeloTabla = new DefaultTableModel(
				new Object[][] {},
				new String[] { "ID Pedido", "Total (€)", "Fecha Venta" }
				) {
			public boolean isCellEditable(int row, int column) { return false; }
		};
		tablaImpagos.setModel(modeloTabla);
		scrollPane.setViewportView(tablaImpagos);

		btnVolver = new JButton("CERRAR");
		btnVolver.setBounds(20, 370, 150, 40);
		contentPane.add(btnVolver);

		btnRefrescar = new JButton("REFRESCAR");
		btnRefrescar.setBounds(380, 370, 150, 40);
		contentPane.add(btnRefrescar);

		EventosPedidosImpagados controller = new EventosPedidosImpagados(this);
		btnVolver.addActionListener(controller);
		btnRefrescar.addActionListener(controller);

		controller.cargarListaImpagados();
	}

	public DefaultTableModel getModeloTabla() { 
		return modeloTabla; 
	}
	public JButton getBtnVolver() { 
		return btnVolver;
	}
	public JButton getBtnRefrescar() { 
		return btnRefrescar; 
	}
}