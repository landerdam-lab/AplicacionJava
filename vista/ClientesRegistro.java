import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ClientesRegistro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JTextField txtCorreo;
	private JTextField txtDni;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientesRegistro frame = new ClientesRegistro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientesRegistro() {
		setTitle("ClienteRegistro");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		JButton btnConfirmarRegistro = new JButton("CONFIRMAR");
		panel_1.add(btnConfirmarRegistro, BorderLayout.CENTER);
		
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new GridLayout(4, 2, 0, 0));
		
		JLabel lblNewLabel = new JLabel("NOMBRE:");
		panel_3.add(lblNewLabel);
		
		txtNombre = new JTextField();
		panel_3.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("TELEFONO:");
		panel_3.add(lblNewLabel_1);
		
		txtTelefono = new JTextField();
		panel_3.add(txtTelefono);
		txtTelefono.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("CORREO:");
		panel_3.add(lblNewLabel_3);
		
		txtCorreo = new JTextField();
		panel_3.add(txtCorreo);
		txtCorreo.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("DNI:");
		panel_3.add(lblNewLabel_2);
		
		txtDni = new JTextField();
		panel_3.add(txtDni);
		txtDni.setColumns(10);

	}

}
