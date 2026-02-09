import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.util.ArrayList; 
import java.util.List;     
import java.awt.Dimension; 

public class CompraComponentes extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextArea txtDetalles;
    private JLabel lblPrecioTotal;
    private JTextArea txtResumen;
    
    private Cliente clienteActual;
    private ComponenteDAO componenteDAO;
    private double precioTotalAcumulado = 0.0;
    private List<LineaPedido> carritoDeCompra;

    // --- CONSTRUCTOR 1: COMPRA NORMAL (VACÍA) ---
    public CompraComponentes(Cliente cliente) {
        this.clienteActual = cliente;
        this.componenteDAO = new ComponenteDAO();
        this.carritoDeCompra = new ArrayList<>(); 

        inicializarVentana(); // Dibuja la ventana
    }
    
    // --- CONSTRUCTOR 2: MODO EDICIÓN (CARGA PRODUCTOS) ---
    public CompraComponentes(Cliente c, List<LineaPedido> productosPrevios) {
        // 1. Llamamos al constructor normal para que dibuje la ventana
        this(c); 
        
        // 2. Rellenamos el carrito con los productos antiguos
        for (LineaPedido linea : productosPrevios) {
            // Buscamos el producto en BD por su ID
            Componente comp = componenteDAO.obtenerComponentePorId(linea.getIdComponente());
            
            // Si existe, lo añadimos al carrito visual y lógico
            if (comp != null) {
                anadirAlCarrito(comp, linea.getCantidad());
            }
        }
    }

    // Método con todo el diseño gráfico (para no repetirlo)
    private void inicializarVentana() {
        setTitle("Comprar Componentes - Cliente: " + clienteActual.getNombre());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 750);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Panel Izquierdo
        JPanel panelContenedorItems = new JPanel();
        panelContenedorItems.setLayout(null);
        panelContenedorItems.setPreferredSize(new Dimension(700, 600)); 

        JScrollPane scrollPanelIzquierdo = new JScrollPane(panelContenedorItems);
        scrollPanelIzquierdo.setBounds(10, 20, 750, 510);
        scrollPanelIzquierdo.setBorder(new TitledBorder("Catálogo Disponible"));
        contentPane.add(scrollPanelIzquierdo);

        String[] etiquetas = {
            "Procesador:", "Placa Base:", "RAM:", "Gráfica:", "Disco Duro:", 
            "Caja:", "Fuente Alim.:", "Refrigeración:", "Monitor:", "Teclado:", "Ratón:"
        };
        
        String[] tablasBD = {
            "PROCESADOR", "PLACA_BASE", "RAM", "TARJETA_GRAFICA", 
            "DISCO_DURO", "CAJA", "FUENTE_ALIMENTACION", 
            "REFRIGERACION", "MONITOR", "TECLADO", "RATON"
        };

        int yPos = 20;

        for (int i = 0; i < etiquetas.length; i++) {
            JLabel lbl = new JLabel(etiquetas[i]);
            lbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
            lbl.setBounds(10, yPos, 100, 20);
            panelContenedorItems.add(lbl);

            JComboBox<Componente> combo = new JComboBox<>();
            combo.setBounds(110, yPos, 380, 22);
            
            List<Componente> lista = componenteDAO.obtenerComponentes(tablasBD[i]);
            for (Componente c : lista) {
                combo.addItem(c); 
            }
            
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

        // Panel Detalles
        JPanel panelDetalles = new JPanel();
        panelDetalles.setBorder(new TitledBorder("Detalles"));
        panelDetalles.setBounds(770, 10, 400, 250);
        panelDetalles.setLayout(null);
        contentPane.add(panelDetalles);

        txtDetalles = new JTextArea();
        txtDetalles.setEditable(false);
        txtDetalles.setLineWrap(true);
        txtDetalles.setWrapStyleWord(true);
        JScrollPane scrollDetalles = new JScrollPane(txtDetalles);
        scrollDetalles.setBounds(10, 20, 380, 220);
        panelDetalles.add(scrollDetalles);

        // Panel Resumen
        JPanel panelResumen = new JPanel();
        panelResumen.setBorder(new TitledBorder("Carrito de Compra"));
        panelResumen.setBounds(770, 270, 400, 430);
        panelResumen.setLayout(null);
        contentPane.add(panelResumen);

        txtResumen = new JTextArea();
        txtResumen.setEditable(false);
        JScrollPane scrollResumen = new JScrollPane(txtResumen);
        scrollResumen.setBounds(10, 20, 380, 330);
        panelResumen.add(scrollResumen);

        lblPrecioTotal = new JLabel("TOTAL: 0.00€");
        lblPrecioTotal.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblPrecioTotal.setBounds(220, 360, 170, 30);
        panelResumen.add(lblPrecioTotal);
        
        JButton btnFinalizar = new JButton("FINALIZAR PEDIDO");
        btnFinalizar.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnFinalizar.setBounds(100, 390, 200, 30);
        btnFinalizar.addActionListener(e -> finalizarPedido());
        panelResumen.add(btnFinalizar);
    }

    private void mostrarDetalles(Componente c) {
        StringBuilder sb = new StringBuilder();
        sb.append("Producto: ").append(c.getNombre()).append("\n");
        sb.append("Precio: ").append(c.getPrecioVenta()).append("€\n");
        sb.append("Stock disponible: ").append(c.getStock()).append("\n");
        sb.append("Descripción: ").append(c.getDescripcion()).append("\n");
        
        // Aquí puedes añadir los 'instanceof' si quieres ver detalles específicos (CPU, RAM, etc)
        // Por simplicidad muestro lo básico, pero puedes copiar tu método anterior si lo prefieres
        
        txtDetalles.setText(sb.toString());
        txtDetalles.setCaretPosition(0);
    }

    private void anadirAlCarrito(Componente c, int cantidad) {
        if (cantidad > c.getStock()) {
            JOptionPane.showMessageDialog(this, "Stock insuficiente.", "Error", JOptionPane.WARNING_MESSAGE);
            return; 
        }
        
        double subtotal = c.getPrecioVenta() * cantidad;
        precioTotalAcumulado += subtotal;
        
        txtResumen.append(cantidad + "x " + c.getNombre() + " = " + String.format("%.2f", subtotal) + "€\n");
        lblPrecioTotal.setText("TOTAL: " + String.format("%.2f", precioTotalAcumulado) + "€");
        
        carritoDeCompra.add(new LineaPedido(c.getIdComponente(), cantidad, c.getPrecioVenta()));
    }
    
    private void finalizarPedido() {
        if (carritoDeCompra.isEmpty()) return;

        PedidoDAO pedidoDao = new PedidoDAO();
        boolean exito = pedidoDao.registrarPedido(clienteActual, carritoDeCompra, precioTotalAcumulado, false);

        if (exito) {
            JOptionPane.showMessageDialog(this, "¡Pedido guardado!");
            this.dispose(); 
            // Al guardar, volvemos al menú principal
            new MenuCompras(clienteActual).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}