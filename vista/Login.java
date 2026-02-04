import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.JCheckBox;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelC;
	private JPanel panelN;
	private JPanel panelS;
	private JPanel panelE;
	private JLabel lblIniciarsesion;
	private JLabel lblUsuario;
	private JTextField txtUsuario;
	private JLabel lblPassword;
	private JPasswordField txtPassword;
	private JButton btnIngresar;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JCheckBox btnVer;
	private JLabel lblNewLabel_6;
	private char caracterDefecto;
	private boolean passwordVisible = false; //queremos saber si esta mostrando o ocultando
	private EventosLogin eventosLogin;

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
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panelC = new JPanel();
		contentPane.add(panelC, BorderLayout.CENTER);
		panelC.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblNewLabel = new JLabel("");
		panelC.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("");
		panelC.add(lblNewLabel_1);
		
		lblUsuario = new JLabel("Usuario:");
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuario.setFont(new Font("Arial", Font.BOLD, 13));
		panelC.add(lblUsuario);
		
		txtUsuario = new JTextField();
		panelC.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		lblNewLabel_2 = new JLabel("");
		panelC.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("");
		panelC.add(lblNewLabel_3);
		
		lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Arial", Font.BOLD, 13));
		panelC.add(lblPassword);
		
		txtPassword = new JPasswordField();
		panelC.add(txtPassword);
		caracterDefecto = txtPassword.getEchoChar();
		lblNewLabel_4 = new JLabel("");
		panelC.add(lblNewLabel_4);
		
		lblNewLabel_5 = new JLabel("");
		panelC.add(lblNewLabel_5);
		
		panelN = new JPanel();
		contentPane.add(panelN, BorderLayout.NORTH);
		
		lblIniciarsesion = new JLabel("INICIAR SESION");
		lblIniciarsesion.setFont(new Font("Arial", Font.BOLD, 20));
		panelN.add(lblIniciarsesion);
		
		panelS = new JPanel();
		contentPane.add(panelS, BorderLayout.SOUTH);
		
		btnIngresar = new JButton("INGRESAR");
		btnIngresar.setFont(new Font("Arial", Font.BOLD, 16));
		panelS.add(btnIngresar);
		
		JPanel panelW = new JPanel();
		contentPane.add(panelW, BorderLayout.WEST);
		
		panelE = new JPanel();
		contentPane.add(panelE, BorderLayout.EAST);
		panelE.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblNewLabel_6 = new JLabel("");
		panelE.add(lblNewLabel_6);
		
		//boton ver
		btnVer = new JCheckBox("Ver contraseña");
		btnVer.setHorizontalAlignment(SwingConstants.CENTER);
		panelE.add(btnVer);
		btnVer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(passwordVisible){
					txtPassword.setEchoChar(caracterDefecto);
					passwordVisible = false;
				}else {
					txtPassword.setEchoChar((char)0);
					passwordVisible = true;
				}
				
			}
			
		});
		
		eventosLogin= new EventosLogin(this);
	}
	
	public void RegistrarEventos() {
		btnIngresar.addActionListener(null);
	}

	public JButton getBtnIngresar() {
		return btnIngresar;
	}

	public void setBtnIngresar(JButton btnIngresar) {
		this.btnIngresar = btnIngresar;
	}

	public EventosLogin getEventosLogin() {
		return eventosLogin;
	}

	public void setEventosLogin(EventosLogin eventosLogin) {
		this.eventosLogin = eventosLogin;
	}

}
