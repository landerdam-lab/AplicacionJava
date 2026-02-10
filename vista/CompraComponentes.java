import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.Color;
import java.util.ArrayList; 
import java.util.List;     
import java.awt.Dimension; 

public class CompraComponentes extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextArea txtDetalles;
    private JLabel lblPrecioTotal;
    
    // CAMBIO IMPORTANTE: Usamos JList para poder seleccionar y borrar items
    private JList<String> listaVisualCarrito;
    private DefaultListModel<String> modeloLista;
    
    private Cliente clienteActual;
    private ComponenteDAO componenteDAO;
    private double precioTotalAcumulado = 0.0;
    
    // Lista lógica (los objetos reales)
    private List<LineaPedido> carritoDeCompra;

    // --- CONSTRUCTOR 1: NUEVA COMPRA ---
    public CompraComponentes(Cliente cliente) {
        this.clienteActual = cliente;
        this.componenteDAO = new ComponenteDAO();
        this.carritoDeCompra = new ArrayList<>(); 
        this.modeloLista = new DefaultListModel<>();

        inicializarVentana(); 
    }
    
    // --- CONSTRUCTOR 2: EDITAR PEDIDO (RECIBE PRODUCTOS) ---
    public CompraComponentes(Cliente c, List<LineaPedido> productosPrevios) {
        this(c); // Llama al constructor normal primero
        
        // Cargamos los productos que ya tenía el cliente
        for (LineaPedido linea : productosPrevios) {
            Componente comp = componenteDAO.obtenerComponentePorId(linea.getIdComponente());
            if (comp != null) {
                // Añadimos visual y lógicamente sin restar stock (porque ya era suyo)
                // Pero para simplificar, usaremos el método estándar y asumimos que
                // al BORRAR el pedido antiguo, el stock ya se devolvió a la tienda.
                anadirAlCarrito(comp, linea.getCantidad());
            }
        }
    }

    private void inicializarVentana() {
        setTitle("Comprar Componentes - Cliente: " + clienteActual.getNombre());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 750);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // --- PANEL IZQUIERDO (CATÁLOGO) ---
        JPanel panelContenedorItems = new JPanel();
        panelContenedorItems.setLayout(null);
        panelContenedorItems.setPreferredSize(new Dimension(700, 600)); 

        JScrollPane scrollPanelIzquierdo = new JScrollPane(panelContenedorItems);
        scrollPanelIzquierdo.setBounds(10, 20, 750, 510);
        scrollPanelIzquierdo.setBorder(new TitledBorder("Catálogo Disponible"));
        contentPane.add(scrollPanelIzquierdo);

        String[] etiquetas = { "Procesador:", "Placa Base:", "RAM:", "Gráfica:", "Disco Duro:", "Caja:", "Fuente Alim.:", "Refrigeración:", "Monitor:", "Teclado:", "Ratón:" };
        String[] tablasBD = { "PROCESADOR", "PLACA_BASE", "RAM", "TARJETA_GRAFICA", "DISCO_DURO", "CAJA", "FUENTE_ALIMENTACION", "REFRIGERACION", "MONITOR", "TECLADO", "RATON" };

        int yPos = 20;

        for (int i = 0; i < etiquetas.length; i++) {
            JLabel lbl = new JLabel(etiquetas[i]);
            lbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
            lbl.setBounds(10, yPos, 100, 20);
            panelContenedorItems.add(lbl);

            JComboBox<Componente> combo = new JComboBox<>();
            combo.setBounds(110, yPos, 380, 22);
            
            List<Componente> lista = componenteDAO.obtenerComponentes(tablasBD[i]);
            for (Componente c : lista) combo.addItem(c); 
            
            combo.addActionListener(e -> {
                Componente seleccionado = (Componente) combo.getSelectedItem();
                if(seleccionado != null) mostrarDetalles(seleccionado);
            });
            panelContenedorItems.add(combo);

            JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
            spinner.setBounds(500, yPos, 50, 22);
            panelContenedorItems.add(spinner);

            JButton btnAdd = new JButton("Añadir");
            btnAdd.setBounds(560, yPos, 80, 22);
            btnAdd.addActionListener(e -> {
                Componente c = (Componente) combo.getSelectedItem();
                int cant = (int) spinner.getValue();
                if (c != null) anadirAlCarrito(c, cant);
            });
            panelContenedorItems.add(btnAdd);

            yPos += 45; 
        }
        
        JButton btnVolver = new JButton("VOLVER AL MENÚ");
        btnVolver.setBounds(20, 550, 200, 30);
        btnVolver.addActionListener(e -> {
            this.dispose();
            new MenuCompras(clienteActual).setVisible(true);
        });
        contentPane.add(btnVolver);

        // --- PANEL DETALLES ---
        JPanel panelDetalles = new JPanel();
        panelDetalles.setBorder(new TitledBorder("Detalles"));
        panelDetalles.setBounds(770, 10, 400, 200);
        panelDetalles.setLayout(null);
        contentPane.add(panelDetalles);

        txtDetalles = new JTextArea();
        txtDetalles.setEditable(false);
        txtDetalles.setLineWrap(true);
        txtDetalles.setWrapStyleWord(true);
        JScrollPane scrollDetalles = new JScrollPane(txtDetalles);
        scrollDetalles.setBounds(10, 20, 380, 170);
        panelDetalles.add(scrollDetalles);

        // --- PANEL RESUMEN (CARRITO) ---
        JPanel panelResumen = new JPanel();
        panelResumen.setBorder(new TitledBorder("Carrito de Compra (Selecciona para borrar)"));
        panelResumen.setBounds(770, 220, 400, 480);
        panelResumen.setLayout(null);
        contentPane.add(panelResumen);

        // LISTA VISUAL
        listaVisualCarrito = new JList<>(modeloLista);
        JScrollPane scrollResumen = new JScrollPane(listaVisualCarrito);
        scrollResumen.setBounds(10, 20, 380, 330);
        panelResumen.add(scrollResumen);

        lblPrecioTotal = new JLabel("TOTAL: 0.00€");
        lblPrecioTotal.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblPrecioTotal.setBounds(220, 360, 170, 30);
        panelResumen.add(lblPrecioTotal);
        
        // BOTÓN ELIMINAR ITEM
        JButton btnEliminarItem = new JButton("Quitar Seleccionado");
        btnEliminarItem.setBackground(Color.PINK);
        btnEliminarItem.setBounds(10, 360, 160, 30);
        btnEliminarItem.addActionListener(e -> eliminarDelCarrito());
        panelResumen.add(btnEliminarItem);
        
        JButton btnFinalizar = new JButton("FINALIZAR PEDIDO");
        btnFinalizar.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnFinalizar.setBounds(100, 410, 200, 40);
        btnFinalizar.addActionListener(e -> finalizarPedido());
        panelResumen.add(btnFinalizar);
    }

    private void mostrarDetalles(Componente c) {
        // (Tu código de mostrar detalles igual que antes...)
        txtDetalles.setText(c.toString()); 
    }

    private void anadirAlCarrito(Componente c, int cantidad) {
        if (cantidad > c.getStock()) {
            JOptionPane.showMessageDialog(this, "Stock insuficiente.", "Error", JOptionPane.WARNING_MESSAGE);
            return; 
        }
        
        // Añadimos a la lista lógica
        carritoDeCompra.add(new LineaPedido(c.getIdComponente(), cantidad, c.getPrecioVenta()));
        
        // Añadimos a la lista visual (Texto bonito)
        double subtotal = c.getPrecioVenta() * cantidad;
        String texto = cantidad + "x " + c.getNombre() + " (" + String.format("%.2f", subtotal) + "€)";
        modeloLista.addElement(texto);
        
        actualizarTotal();
    }
    
    private void eliminarDelCarrito() {
        int index = listaVisualCarrito.getSelectedIndex();
        if (index != -1) {
            // Borramos de las dos listas
            modeloLista.remove(index);
            carritoDeCompra.remove(index);
            actualizarTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un artículo de la lista para quitarlo.");
        }
    }
    
    private void actualizarTotal() {
        precioTotalAcumulado = 0;
        for (LineaPedido lp : carritoDeCompra) {
            precioTotalAcumulado += lp.getPrecioUnitario() * lp.getCantidad();
        }
        lblPrecioTotal.setText("TOTAL: " + String.format("%.2f", precioTotalAcumulado) + "€");
    }
    
    private void finalizarPedido() {
        if (carritoDeCompra.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El carrito está vacío.");
            return;
        }

        PedidoDAO pedidoDao = new PedidoDAO();
        boolean exito = pedidoDao.registrarPedido(clienteActual, carritoDeCompra, precioTotalAcumulado, false);

        if (exito) {
            JOptionPane.showMessageDialog(this, "¡Pedido guardado!");
            this.dispose(); 
            new MenuCompras(clienteActual).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}