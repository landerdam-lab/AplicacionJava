import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;


public class MenuCompras extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Cliente clienteActual;
	
	private JButton btnConfiguraciones;
	private JButton btnComponentes; 
	private JButton btnCargar;

	public MenuCompras(Cliente cliente) {
		
		this.clienteActual = cliente;
		
		setTitle("Menu Compras - Usuario: " + clienteActual.getNombre());
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(700, 300, 913, 578);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnConfiguraciones = new JButton("Comprar Configuraciones");
		btnConfiguraciones.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnConfiguraciones.setBounds(294, 200, 309, 86);
		contentPane.add(btnConfiguraciones);
		
		btnComponentes = new JButton("Comprar Componentes");
		btnComponentes.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnComponentes.setBounds(294, 63, 309, 86);
		contentPane.add(btnComponentes);
		
		btnCargar = new JButton("Cargar Pedidos");
		btnCargar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnCargar.setBounds(294, 333, 309, 86);
		contentPane.add(btnCargar);
		
		EventosMenuCompras controller = new EventosMenuCompras(this);
		
		btnComponentes.addActionListener(controller);
		btnConfiguraciones.addActionListener(controller);
		btnCargar.addActionListener(controller);
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
}