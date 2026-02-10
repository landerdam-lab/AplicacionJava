

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.Toolkit;
import java.util.List;

public class ConfigurarPC extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblPrecioTotal;
	private JCheckBox chkMontaje;
	private JTextArea txtResumen;
	private JComboBox<Componente>[] combosComponentes;
	private JButton btnVolver;
	private JButton btnComprar;

	private Cliente clienteActual;
	private EventosConfigurarPC controller;

	public ConfigurarPC(Cliente cliente) {
		this.clienteActual = cliente;
		this.controller = new EventosConfigurarPC(this);
		inicializarVentana();
	}

	public ConfigurarPC(Cliente cliente, List<LineaPedido> componentesViejos, boolean teniaMontaje) {
		this(cliente);
		controller.cargarConfiguracionPrevia(componentesViejos, teniaMontaje);
	}

	private void inicializarVentana() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("imagenes\\logo.jpg"));

		setTitle("Configurador PC - " + clienteActual.getNombre());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(700, 300, 1100, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panelSeleccion = new JPanel();
		panelSeleccion.setBorder(new TitledBorder("Elige tus componentes"));
		panelSeleccion.setLayout(null);
		panelSeleccion.setBounds(10, 10, 650, 600);
		contentPane.add(panelSeleccion);

		String[] etiquetas = { "Procesador:", "Placa Base:", "RAM:", "Gráfica:", "Disco Duro:", "Caja:", "Fuente Alim.:" };
		combosComponentes = new JComboBox[etiquetas.length];

		List<List<Componente>> datos = controller.obtenerDatosCombos();

		int yPos = 30;
		for (int i = 0; i < etiquetas.length; i++) {
			JLabel lbl = new JLabel(etiquetas[i]);
			lbl.setBounds(20, yPos, 120, 20);
			panelSeleccion.add(lbl);

			combosComponentes[i] = new JComboBox<>();
			combosComponentes[i].setBounds(150, yPos, 450, 25);
			combosComponentes[i].addItem(null);

			for(Componente c : datos.get(i)) {
				combosComponentes[i].addItem(c);
			}

			combosComponentes[i].addActionListener(controller);
			panelSeleccion.add(combosComponentes[i]);
			yPos += 50;
		}

		chkMontaje = new JCheckBox("Montaje (+50€)");
		chkMontaje.setBounds(20, yPos + 20, 400, 30);
		chkMontaje.addActionListener(controller); 
		panelSeleccion.add(chkMontaje);

		btnVolver = new JButton("VOLVER");
		btnVolver.setBounds(20, 550, 150, 30);
		btnVolver.addActionListener(controller);
		panelSeleccion.add(btnVolver);

		JPanel panelResumen = new JPanel();
		panelResumen.setBorder(new TitledBorder("Resumen"));
		panelResumen.setBounds(680, 10, 380, 600);
		panelResumen.setLayout(null);
		contentPane.add(panelResumen);

		txtResumen = new JTextArea();
		txtResumen.setEditable(false);
		JScrollPane scrollResumen = new JScrollPane(txtResumen);
		scrollResumen.setBounds(10, 30, 360, 450);
		panelResumen.add(scrollResumen);

		lblPrecioTotal = new JLabel("TOTAL: 0.00€");
		lblPrecioTotal.setBounds(10, 500, 360, 40);
		panelResumen.add(lblPrecioTotal);

		btnComprar = new JButton("COMPRAR PC");
		btnComprar.setBounds(90, 550, 200, 40);
		btnComprar.addActionListener(controller);
		panelResumen.add(btnComprar);
	}

	public void setTextoResumen(String texto) {
		txtResumen.setText(texto);
	}

	public void setTextoTotal(String texto) {
		lblPrecioTotal.setText(texto);
	}

	public JPanel getContentPane() { 
		return contentPane; 
	}
	public JLabel getLblPrecioTotal() { 
		return lblPrecioTotal; 
	}
	public JCheckBox getChkMontaje() { 
		return chkMontaje; 
	}
	public JTextArea getTxtResumen() { 
		return txtResumen; 
	}
	public JComboBox<Componente>[] getCombosComponentes() { 
		return combosComponentes; 
	}
	public JButton getBtnVolver() { 
		return btnVolver;
	}
	public JButton getBtnComprar() {
		return btnComprar; 
	}
	public Cliente getClienteActual() { 
		return clienteActual; 
	}

}