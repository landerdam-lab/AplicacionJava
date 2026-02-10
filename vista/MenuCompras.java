

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Color;


public class MenuCompras extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Cliente clienteActual;

	private JButton btnConfiguraciones;
	private JButton btnComponentes; 
	private JButton btnCargar;
	private JButton btnImpagos;
	private JButton btnVolver; 

	public MenuCompras(Cliente cliente) {
		this.clienteActual = cliente;

		setTitle("Menu Compras - Usuario: " + clienteActual.getNombre());
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(700, 300, 913, 600);


		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnComponentes = new JButton("Comprar Componentes");
		btnComponentes.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnComponentes.setBounds(294, 40, 309, 86);
		contentPane.add(btnComponentes);

		btnConfiguraciones = new JButton("Comprar Configuraciones");
		btnConfiguraciones.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnConfiguraciones.setBounds(294, 150, 309, 86);
		contentPane.add(btnConfiguraciones);

		btnCargar = new JButton("Mis Pedidos");
		btnCargar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnCargar.setBounds(294, 260, 309, 86);
		contentPane.add(btnCargar);

		btnImpagos = new JButton("ADMIN: Ver Impagos");
		btnImpagos.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnImpagos.setForeground(Color.RED);
		btnImpagos.setBounds(294, 400, 309, 60);
		contentPane.add(btnImpagos);

		btnVolver = new JButton("Cerrar Sesión");
		btnVolver.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnVolver.setForeground(Color.BLUE);
		btnVolver.setBounds(20, 500, 150, 40);
		contentPane.add(btnVolver);

		EventosMenuCompras controller = new EventosMenuCompras(this);

		btnComponentes.addActionListener(controller);
		btnConfiguraciones.addActionListener(controller);
		btnCargar.addActionListener(controller);
		btnImpagos.addActionListener(controller);
		btnVolver.addActionListener(controller); 
	}

	public Cliente getClienteActual() { 
		return clienteActual; 
	}
	public JButton getBtnComponentes() {
		return btnComponentes; 
	}
	public JButton getBtnConfiguraciones() { 
		return btnConfiguraciones;
	}
	public JButton getBtnCargarPedidos() { 
		return btnCargar; 
	}
	public JButton getBtnImpagos() {
		return btnImpagos; 
	}

	public JButton getBtnVolver() {
		return btnVolver;
	}
}