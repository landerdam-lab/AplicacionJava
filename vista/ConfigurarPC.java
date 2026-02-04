import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.JLabel;

public class ConfigurarPC extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public ConfigurarPC() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelSeleccion = new JPanel();
		panelSeleccion.setLayout(null);
		panelSeleccion.setBorder(new TitledBorder("Selección de Componentes"));
		panelSeleccion.setBounds(10, 11, 750, 520);
		contentPane.add(panelSeleccion);
		
		JButton btnFinalizar = new JButton("FINALIZAR COMPRA");
		btnFinalizar.setBounds(253, 597, 200, 30);
		contentPane.add(btnFinalizar);
		btnFinalizar.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JPanel panelResumen = new JPanel();
		panelResumen.setLayout(null);
		panelResumen.setBorder(new TitledBorder("Resumen del Pedido"));
		panelResumen.setBounds(770, 11, 400, 520);
		contentPane.add(panelResumen);
		
		JScrollPane scrollResumen = new JScrollPane((Component) null);
		scrollResumen.setBounds(10, 20, 380, 446);
		panelResumen.add(scrollResumen);
		
		JLabel lblPrecioTotal = new JLabel("TOTAL: 0.00€");
		lblPrecioTotal.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPrecioTotal.setBounds(220, 477, 170, 30);
		panelResumen.add(lblPrecioTotal);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBounds(10, 479, 100, 30);
		panelResumen.add(btnLimpiar);

	}
	
}
