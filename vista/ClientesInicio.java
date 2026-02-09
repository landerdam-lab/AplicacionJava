import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ClientesInicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private EventosClientesInicio eventosClientesInicio;
	
	private JButton btnClienteExistente;
	private JButton btnClienteNuevo;
	
	private Usuario usuarioActual;
	
	public ClientesInicio(Usuario usuario) {
		
		this.usuarioActual = usuario;
		
		setResizable(false);
		setTitle("Inicio - Atendido por: " + usuario.getNombre());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(3, 1, 0, 0));
		
		btnClienteExistente = new JButton("Cliente Existente");
		panel.add(btnClienteExistente);
		
		JLabel lblNewLabel = new JLabel("");
		panel.add(lblNewLabel);
		
		btnClienteNuevo = new JButton("Nuevo Cliente");
		panel.add(btnClienteNuevo);
		
		eventosClientesInicio = new EventosClientesInicio(this);
		
	}

	public JButton getBtnClienteExistente() {
		return btnClienteExistente;
	}

	public void setBtnClienteExistente(JButton btnClienteExistente) {
		this.btnClienteExistente = btnClienteExistente;
	}

	public JButton getBtnClienteNuevo() {
		return btnClienteNuevo;
	}

	public void setBtnClienteNuevo(JButton btnClienteNuevo) {
		this.btnClienteNuevo = btnClienteNuevo;
	}
	
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}
}