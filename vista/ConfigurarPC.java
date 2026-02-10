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
    
    private JComboBox<Componente>[] combosComponentes;
    
    private Cliente clienteActual;
    private ComponenteDAO componenteDAO;
    private double precioTotalCalculado = 0.0;
    private final double PRECIO_MONTAJE = 50.0;

    // --- CONSTRUCTOR 1: NUEVA CONFIGURACIÓN ---
    public ConfigurarPC(Cliente cliente) {
        this.clienteActual = cliente;
        this.componenteDAO = new ComponenteDAO();
        inicializarVentana(); // Dibuja la pantalla vacía
    }

    // --- CONSTRUCTOR 2: EDITAR CONFIGURACIÓN ---
    public ConfigurarPC(Cliente cliente, List<LineaPedido> componentesViejos, boolean teniaMontaje) {
        this(cliente); // Dibuja la pantalla primero
        
        // 1. Restaurar el Checkbox de montaje
        chkMontaje.setSelected(teniaMontaje);
        
        // 2. Restaurar los componentes en los desplegables
        for (LineaPedido linea : componentesViejos) {
            // Buscamos qué componente era por su ID
            // Nota: No hace falta llamar a BD si recorremos los combos que ya tienen los datos cargados,
            // pero usar el ID es más seguro.
            seleccionarEnCombo(linea.getIdComponente());
        }
        
        // 3. Recalcular el precio final para que salga actualizado
        calcularPrecioTotal();
    }

    // Método auxiliar para buscar un ID en todos los combos y seleccionarlo
    private void seleccionarEnCombo(int idComponenteBuscado) {
        // Recorremos los 7 desplegables (Procesador, Placa, RAM...)
        for (JComboBox<Componente> combo : combosComponentes) {
            // Recorremos los items dentro de ese desplegable
            for (int i = 0; i < combo.getItemCount(); i++) {
                Componente c = combo.getItemAt(i);
                if (c != null && c.getIdComponente() == idComponenteBuscado) {
                    combo.setSelectedIndex(i); // ¡Lo encontramos! Lo seleccionamos.
                    return; // Pasamos al siguiente producto del pedido
                }
            }
        }
    }

    private void inicializarVentana() {
        setTitle("Configurador de PC a Medida - Cliente: " + clienteActual.getNombre());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1100, 700);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Panel Izquierdo
        JPanel panelSeleccion = new JPanel();
        panelSeleccion.setBorder(new TitledBorder("Elige tus componentes"));
        panelSeleccion.setBounds(10, 10, 650, 600);
        panelSeleccion.setLayout(null);
        contentPane.add(panelSeleccion);

        String[] etiquetas = {
            "1. Procesador:", "2. Placa Base:", "3. Memoria RAM:", 
            "4. Gráfica:", "5. Disco Duro:", "6. Caja/Torre:", "7. Fuente Alim.:"
        };
        
        String[] tablasBD = {
            "PROCESADOR", "PLACA_BASE", "RAM", 
            "TARJETA_GRAFICA", "DISCO_DURO", "CAJA", "FUENTE_ALIMENTACION"
        };

        combosComponentes = new JComboBox[etiquetas.length];
        int yPos = 30;

        for (int i = 0; i < etiquetas.length; i++) {
            JLabel lbl = new JLabel(etiquetas[i]);
            lbl.setFont(new Font("Tahoma", Font.BOLD, 12));
            lbl.setBounds(20, yPos, 120, 20);
            panelSeleccion.add(lbl);

            combosComponentes[i] = new JComboBox<>();
            combosComponentes[i].setBounds(150, yPos, 450, 25);
            combosComponentes[i].addItem(null); 
            
            List<Componente> lista = componenteDAO.obtenerComponentes(tablasBD[i]);
            for (Componente c : lista) combosComponentes[i].addItem(c);
            
            combosComponentes[i].addActionListener(e -> calcularPrecioTotal());
            
            panelSeleccion.add(combosComponentes[i]);
            yPos += 50;
        }

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

        // Panel Derecho
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
        btnComprar.setBackground(new Color(50, 205, 50)); 
        btnComprar.setBounds(90, 550, 200, 40);
        btnComprar.addActionListener(e -> finalizarCompra());
        panelResumen.add(btnComprar);
    }

    private void calcularPrecioTotal() {
        double total = 0.0;
        StringBuilder sb = new StringBuilder();
        sb.append("--- TU CONFIGURACIÓN ---\n\n");

        for (JComboBox<Componente> combo : combosComponentes) {
            Componente c = (Componente) combo.getSelectedItem();
            if (c != null) {
                total += c.getPrecioVenta();
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
        for (JComboBox<Componente> combo : combosComponentes) {
            if (combo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona todos los componentes.");
                return;
            }
        }

        List<LineaPedido> carrito = new ArrayList<>();
        for (JComboBox<Componente> combo : combosComponentes) {
            Componente c = (Componente) combo.getSelectedItem();
            if (c.getStock() < 1) {
                JOptionPane.showMessageDialog(this, "No hay stock de: " + c.getNombre());
                return;
            }
            carrito.add(new LineaPedido(c.getIdComponente(), 1, c.getPrecioVenta()));
        }

        PedidoDAO pedidoDao = new PedidoDAO();
        boolean exito = pedidoDao.registrarPedido(clienteActual, carrito, precioTotalCalculado, chkMontaje.isSelected());

        if (exito) {
            JOptionPane.showMessageDialog(this, "¡Pedido guardado!");
            this.dispose();
            new MenuCompras(clienteActual).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Hubo un error al guardar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}