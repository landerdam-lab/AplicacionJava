import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;


public class ConfigurarPC extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblPrecioTotal;
    private JCheckBox chkMontaje;
    private JTextArea txtResumen;
    
    // Arrays para guardar los desplegables y poder leerlos luego
    private JComboBox<Componente>[] combosComponentes;
    
    private Cliente clienteActual;
    private ComponenteDAO componenteDAO;
    private double precioTotalCalculado = 0.0;
    private final double PRECIO_MONTAJE = 50.0; // Precio del servicio

    public ConfigurarPC(Cliente cliente) {
        this.clienteActual = cliente;
        this.componenteDAO = new ComponenteDAO();
        
        setTitle("Configurador de PC a Medida - Cliente: " + cliente.getNombre());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1100, 700);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // --- PANEL IZQUIERDO: SELECCIÓN ---
        JPanel panelSeleccion = new JPanel();
        panelSeleccion.setBorder(new TitledBorder("Elige tus componentes"));
        panelSeleccion.setBounds(10, 10, 650, 600);
        panelSeleccion.setLayout(null);
        contentPane.add(panelSeleccion);

        // Las partes ESENCIALES de un PC
        String[] etiquetas = {
            "1. Procesador:", "2. Placa Base:", "3. Memoria RAM:", 
            "4. Gráfica:", "5. Disco Duro:", "6. Caja/Torre:", "7. Fuente Alim.:"
        };
        
        // Tablas de base de datos correspondientes (CON GUIONES BAJOS)
        String[] tablasBD = {
            "PROCESADOR", "PLACA_BASE", "RAM", 
            "TARJETA_GRAFICA", "DISCO_DURO", "CAJA", "FUENTE_ALIMENTACION"
        };

        // Inicializamos el array de combos
        combosComponentes = new JComboBox[etiquetas.length];
        
        int yPos = 30;

        for (int i = 0; i < etiquetas.length; i++) {
            JLabel lbl = new JLabel(etiquetas[i]);
            lbl.setFont(new Font("Tahoma", Font.BOLD, 12));
            lbl.setBounds(20, yPos, 120, 20);
            panelSeleccion.add(lbl);

            // Crear Combo
            combosComponentes[i] = new JComboBox<>();
            combosComponentes[i].setBounds(150, yPos, 450, 25);
            
            // Añadir opción vacía al principio
            combosComponentes[i].addItem(null); 
            
            // Cargar datos de BD
            List<Componente> lista = componenteDAO.obtenerComponentes(tablasBD[i]);
            for (Componente c : lista) {
                combosComponentes[i].addItem(c);
            }
            
            // Acción: Al seleccionar algo, recalcular el precio
            combosComponentes[i].addActionListener(e -> calcularPrecioTotal());
            
            panelSeleccion.add(combosComponentes[i]);
            yPos += 50;
        }

        // Checkbox de Montaje
        chkMontaje = new JCheckBox("Quiero que me lo envíen montado y testeado (+50.00€)");
        chkMontaje.setFont(new Font("Tahoma", Font.BOLD, 13));
        chkMontaje.setForeground(new Color(0, 102, 204));
        chkMontaje.setBounds(20, yPos + 20, 400, 30);
        chkMontaje.addActionListener(e -> calcularPrecioTotal());
        panelSeleccion.add(chkMontaje);
        
        JButton btnVolver = new JButton("VOLVER AL MENÚ");
        btnVolver.setBounds(20, 550, 150, 30);
        btnVolver.addActionListener(e -> {
            this.dispose();
            new MenuCompras(clienteActual).setVisible(true);
        });
        panelSeleccion.add(btnVolver);

        // --- PANEL DERECHO: RESUMEN ---
        JPanel panelResumen = new JPanel();
        panelResumen.setBorder(new TitledBorder("Resumen de Configuración"));
        panelResumen.setBounds(680, 10, 380, 600);
        panelResumen.setLayout(null);
        contentPane.add(panelResumen);

        txtResumen = new JTextArea();
        txtResumen.setEditable(false);
        txtResumen.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollResumen = new JScrollPane(txtResumen);
        scrollResumen.setBounds(10, 30, 360, 450);
        panelResumen.add(scrollResumen);

        lblPrecioTotal = new JLabel("TOTAL: 0.00€");
        lblPrecioTotal.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblPrecioTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPrecioTotal.setBounds(10, 500, 360, 40);
        panelResumen.add(lblPrecioTotal);
        
        JButton btnComprar = new JButton("COMPRAR PC");
        btnComprar.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnComprar.setBackground(new Color(50, 205, 50)); // Verde
        btnComprar.setBounds(90, 550, 200, 40);
        btnComprar.addActionListener(e -> finalizarCompra());
        panelResumen.add(btnComprar);
    }

    // Método que recorre todos los combos para sumar precios y actualizar texto
    private void calcularPrecioTotal() {
        double total = 0.0;
        StringBuilder sb = new StringBuilder();
        sb.append("--- TU CONFIGURACIÓN ---\n\n");

        for (JComboBox<Componente> combo : combosComponentes) {
            Componente c = (Componente) combo.getSelectedItem();
            if (c != null) {
                total += c.getPrecioVenta();
                // Escribimos nombre corto y precio
                sb.append("• ").append(c.getNombre()).append("\n");
                sb.append("   ").append(c.getPrecioVenta()).append("€\n\n");
            }
        }
        
        if (chkMontaje.isSelected()) {
            total += PRECIO_MONTAJE;
            sb.append("• SERVICIO DE MONTAJE Y TESTEO\n");
            sb.append("   ").append(PRECIO_MONTAJE).append("€\n");
        }

        precioTotalCalculado = total;
        txtResumen.setText(sb.toString());
        lblPrecioTotal.setText("TOTAL: " + String.format("%.2f", total) + "€");
    }

    private void finalizarCompra() {
        // 1. Validar que ha elegido todos los componentes
        for (JComboBox<Componente> combo : combosComponentes) {
            if (combo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona todos los componentes para completar el PC.");
                return;
            }
        }

        // 2. Crear lista de pedido y validar stock individual
        List<LineaPedido> carrito = new ArrayList<>();
        
        for (JComboBox<Componente> combo : combosComponentes) {
            Componente c = (Componente) combo.getSelectedItem();
            
            // Validar Stock
            if (c.getStock() < 1) {
                JOptionPane.showMessageDialog(this, "¡Lo sentimos! No hay stock de: " + c.getNombre());
                return;
            }
            
            carrito.add(new LineaPedido(c.getIdComponente(), 1, c.getPrecioVenta()));
        }

        // 3. Guardar en BD
        PedidoDAO pedidoDao = new PedidoDAO();
        // Pasamos 'true' o 'false' según el checkbox de montaje
        boolean exito = pedidoDao.registrarPedido(clienteActual, carrito, precioTotalCalculado, chkMontaje.isSelected());

        if (exito) {
            JOptionPane.showMessageDialog(this, "¡PC Configurado y Comprado con éxito!");
            this.dispose();
            new MenuCompras(clienteActual).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Hubo un error al guardar el pedido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}