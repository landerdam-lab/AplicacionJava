import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblLogin;
	private JLabel lblPassword;
	private JLabel lblUsuario;
	private JTextField textUsuario;
	private JTextField textPass;
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblLogin = new JLabel("INICIAR SESION");
		lblLogin.setFont(new Font("Arial", Font.BOLD, 12));
		lblLogin.setBounds(162, 11, 99, 28);
		contentPane.add(lblLogin);
		
		lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(101, 67, 46, 14);
		contentPane.add(lblUsuario);
		
		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(101, 127, 64, 14);
		contentPane.add(lblPassword);
		
		textUsuario = new JTextField();
		textUsuario.setBounds(162, 64, 99, 20);
		contentPane.add(textUsuario);
		textUsuario.setColumns(10);
		
		textPass = new JTextField();
		textPass.setBounds(162, 124, 99, 20);
		contentPane.add(textPass);
		textPass.setColumns(10);
		
		btnNewButton = new JButton("INGRESAR");
		btnNewButton.setBounds(162, 188, 99, 23);
		contentPane.add(btnNewButton);

	}
}
