import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class MenuCompras extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuCompras frame = new MenuCompras();
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
	public MenuCompras() {
		setTitle("Menu Compras");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 913, 578);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnConfiguraciones = new JButton("Comprar Configuracioens");
		btnConfiguraciones.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnConfiguraciones.setAlignmentX(0.5f);
		btnConfiguraciones.setBounds(294, 200, 309, 86);
		contentPane.add(btnConfiguraciones);
		
		JButton bntComponentes = new JButton("Comprar Componentes");
		bntComponentes.setFont(new Font("Tahoma", Font.PLAIN, 16));
		bntComponentes.setAlignmentX(0.5f);
		bntComponentes.setBounds(294, 63, 309, 86);
		contentPane.add(bntComponentes);
		
		JButton btnCargar = new JButton("Cargar Pedidos");
		btnCargar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnCargar.setAlignmentX(0.5f);
		btnCargar.setBounds(294, 333, 309, 86);
		contentPane.add(btnCargar);

	}
}
