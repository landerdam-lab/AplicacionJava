import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ClientesRegistro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JTextField txtCorreo;
	private JTextField txtDni;
	
	private JButton btnConfirmarRegistro;
	
	private EventosClienteRegistro eventosClienteRegistro;


	public ClientesRegistro(ClientesInicio clientesInicio) {
		setTitle("Registro de Cliente");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Mejor DISPOSE que EXIT para no cerrar toda la app
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.EAST);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		btnConfirmarRegistro = new JButton("CONFIRMAR");
		panel_1.add(btnConfirmarRegistro, BorderLayout.CENTER);
		
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new GridLayout(4, 2, 0, 0));
		
		JLabel lblNombre = new JLabel(" NOMBRE:");
		panel_3.add(lblNombre);
		
		txtNombre = new JTextField();
		panel_3.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblTelefono = new JLabel(" TELEFONO:");
		panel_3.add(lblTelefono);
		
		txtTelefono = new JTextField();
		panel_3.add(txtTelefono);
		txtTelefono.setColumns(10);
		
		JLabel lblCorreo = new JLabel(" CORREO:");
		panel_3.add(lblCorreo);
		
		txtCorreo = new JTextField();
		panel_3.add(txtCorreo);
		txtCorreo.setColumns(10);
		
		JLabel lblDni = new JLabel(" DNI:");
		panel_3.add(lblDni);
		
		txtDni = new JTextField();
		panel_3.add(txtDni);
		txtDni.setColumns(10);
		
		eventosClienteRegistro = new EventosClienteRegistro(this);
		RegistrarEventos();
	}

	private void RegistrarEventos() {
		btnConfirmarRegistro.addActionListener(eventosClienteRegistro);
	}

	public JTextField getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(JTextField txtNombre) {
		this.txtNombre = txtNombre;
	}

	public JTextField getTxtTelefono() {
		return txtTelefono;
	}

	public void setTxtTelefono(JTextField txtTelefono) {
		this.txtTelefono = txtTelefono;
	}

	public JTextField getTxtCorreo() {
		return txtCorreo;
	}

	public void setTxtCorreo(JTextField txtCorreo) {
		this.txtCorreo = txtCorreo;
	}

	public JTextField getTxtDni() {
		return txtDni;
	}

	public void setTxtDni(JTextField txtDni) {
		this.txtDni = txtDni;
	}

	public JButton getBtnConfirmarRegistro() {
		return btnConfirmarRegistro;
	}

	public void setBtnConfirmarRegistro(JButton btnConfirmarRegistro) {
		this.btnConfirmarRegistro = btnConfirmarRegistro;
	}
}