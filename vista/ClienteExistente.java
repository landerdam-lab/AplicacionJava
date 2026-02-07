import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Font;

public class ClienteExistente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtComprobarUsuario;
	private EventosClienteExistente eventosClienteExistente;
	private JButton btnComprobarUsuario;

	// Constructor
	public ClienteExistente() {
		setTitle("ClienteExistente");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(3, 0, 0, 0));

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("Correo Usuario:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_2.add(lblNewLabel, BorderLayout.SOUTH);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		txtComprobarUsuario = new JTextField();
		txtComprobarUsuario.setBounds(74, 11, 296, 52);
		panel_1.add(txtComprobarUsuario);
		txtComprobarUsuario.setColumns(10);

		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnComprobarUsuario = new JButton("COMPROBAR");
		panel.add(btnComprobarUsuario);

		eventosClienteExistente = new EventosClienteExistente(this);
		RegistrarEventos();
	}

	private void RegistrarEventos() {
		btnComprobarUsuario.addActionListener(eventosClienteExistente);		
	}

	public JTextField getTxtComprobarUsuario() {
		return txtComprobarUsuario;
	}

	public void setTxtComprobarUsuario(JTextField txtComprobarUsuario) {
		this.txtComprobarUsuario = txtComprobarUsuario;
	}

	public JButton getBtnComprobarUsuario() {
		return btnComprobarUsuario;
	}

	public void setBtnComprobarUsuario(JButton btnComprobarUsuario) {
		this.btnComprobarUsuario = btnComprobarUsuario;
	}

	
}