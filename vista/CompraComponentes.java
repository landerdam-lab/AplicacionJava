
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Color;
import java.util.List; 
import java.awt.Dimension;


public class CompraComponentes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea txtDetalles;
	private JLabel lblPrecioTotal;
	private JList<String> listaVisualCarrito;
	private DefaultListModel<String> modeloLista;

	private JButton btnEliminarItem;
	private JButton btnFinalizar;
	private JButton btnVolver;
	private JButton[] botonesAnadir; 
	private JComboBox<Componente>[] combos; 
	private JSpinner[] spinners; 

	private Cliente clienteActual;
	private EventosCompraComponentes controller;

	public CompraComponentes(Cliente cliente) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("imagenes\\logo.jpg"));

		this.clienteActual = cliente;
		this.controller = new EventosCompraComponentes(this);
		inicializarVentana();
	}

	public CompraComponentes(Cliente c, List<LineaPedido> productosPrevios) {
		this(c);
		controller.cargarProductosPrevios(productosPrevios);
	}

	private void inicializarVentana() {
		this.modeloLista = new DefaultListModel<>();

		setTitle("Comprar Componentes - Cliente: " + clienteActual.getNombre());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(700, 300, 1200, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panelContenedorItems = new JPanel();
		panelContenedorItems.setLayout(null);
		panelContenedorItems.setPreferredSize(new Dimension(700, 600)); 
		JScrollPane scrollPanelIzquierdo = new JScrollPane(panelContenedorItems);
		scrollPanelIzquierdo.setBounds(10, 20, 750, 510);
		scrollPanelIzquierdo.setBorder(new TitledBorder("Catálogo Disponible"));
		contentPane.add(scrollPanelIzquierdo);

		String[] etiquetas = { "Procesador:", "Placa Base:", "RAM:", "Gráfica:", "Disco Duro:", "Caja:", "Fuente Alim.:", "Refrigeración:", "Monitor:", "Teclado:", "Ratón:" };

		combos = new JComboBox[etiquetas.length];
		botonesAnadir = new JButton[etiquetas.length];
		spinners = new JSpinner[etiquetas.length];

		int yPos = 20;
		List<List<Componente>> catalogo = controller.obtenerCatalogo();

		for (int i = 0; i < etiquetas.length; i++) {
			JLabel lbl = new JLabel(etiquetas[i]);
			lbl.setBounds(10, yPos, 100, 20);
			panelContenedorItems.add(lbl);

			combos[i] = new JComboBox<>();
			combos[i].setBounds(110, yPos, 380, 22);
			for (Componente c : catalogo.get(i)) combos[i].addItem(c);

			final int index = i;
			combos[i].addActionListener(e -> mostrarDetalles((Componente) combos[index].getSelectedItem()));
			panelContenedorItems.add(combos[i]);

			spinners[i] = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
			spinners[i].setBounds(500, yPos, 50, 22);
			panelContenedorItems.add(spinners[i]);

			botonesAnadir[i] = new JButton("Añadir");
			botonesAnadir[i].setBounds(560, yPos, 80, 22);
			botonesAnadir[i].setActionCommand(String.valueOf(i)); 
			botonesAnadir[i].addActionListener(controller); 
			panelContenedorItems.add(botonesAnadir[i]);

			yPos += 45; 
		}

		btnVolver = new JButton("VOLVER AL MENÚ");
		btnVolver.setBounds(20, 550, 200, 30);
		btnVolver.addActionListener(controller);
		contentPane.add(btnVolver);

		JPanel panelDetalles = new JPanel();
		panelDetalles.setBounds(770, 10, 400, 200);
		panelDetalles.setLayout(null);
		contentPane.add(panelDetalles);
		txtDetalles = new JTextArea();
		JScrollPane scrollDetalles = new JScrollPane(txtDetalles);
		scrollDetalles.setBounds(10, 20, 380, 170);
		panelDetalles.add(scrollDetalles);

		JPanel panelResumen = new JPanel();
		panelResumen.setBounds(770, 220, 400, 480);
		panelResumen.setLayout(null);
		contentPane.add(panelResumen);

		listaVisualCarrito = new JList<>(modeloLista);
		JScrollPane scrollResumen = new JScrollPane(listaVisualCarrito);
		scrollResumen.setBounds(10, 20, 380, 330);
		panelResumen.add(scrollResumen);

		lblPrecioTotal = new JLabel("TOTAL: 0.00€");
		lblPrecioTotal.setBounds(220, 360, 170, 30);
		panelResumen.add(lblPrecioTotal);

		btnEliminarItem = new JButton("Quitar Seleccionado");
		btnEliminarItem.setBounds(10, 360, 160, 30);
		btnEliminarItem.addActionListener(controller);
		panelResumen.add(btnEliminarItem);

		btnFinalizar = new JButton("FINALIZAR PEDIDO");
		btnFinalizar.setBounds(100, 410, 200, 40);
		btnFinalizar.addActionListener(controller);
		panelResumen.add(btnFinalizar);
	}

	private void mostrarDetalles(Componente c) {
		if(c != null) txtDetalles.setText(c.toString());
	}

	public void agregarItemVisual(String texto) {
		modeloLista.addElement(texto);
	}

	public void quitarItemVisual(int index) {
		modeloLista.remove(index);
	}

	public void actualizarTotalVisual(String textoTotal) {
		lblPrecioTotal.setText(textoTotal);
	}



	public Cliente getClienteActual() {
		return clienteActual; 
	}
	public JList<String> getListaVisualCarrito() { 
		return listaVisualCarrito; 
	}
	public JButton getBtnEliminarItem() { 
		return btnEliminarItem;
	}
	public JButton getBtnFinalizar() { 
		return btnFinalizar;
	}
	public JButton getBtnVolver() { 
		return btnVolver; 
	}
	public JButton[] getBotonesAnadir() {
		return botonesAnadir;
	}
	public JComboBox<Componente> getComboAt(int index) { 
		return combos[index]; 
	}
	public int getCantidadAt(int index) {
		return (int) spinners[index].getValue();
	}
}