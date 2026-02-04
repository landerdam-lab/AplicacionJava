import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.SpinnerNumberModel;

public class CompraComponentes extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextArea txtDetalles;
    private JLabel lblPrecioTotal;
    private double precioTotal = 0.0;


    public CompraComponentes() {
        setTitle("Comprar Componentes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 750);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // ============== PANEL IZQUIERDO - SELECCIÓN ==============
        JPanel panelSeleccion = new JPanel();
        panelSeleccion.setBorder(new TitledBorder("Selección de Componentes"));
        panelSeleccion.setBounds(10, 10, 750, 520);
        panelSeleccion.setLayout(null);
        contentPane.add(panelSeleccion);

        // Arrays para crear los componentes dinámicamente
        String[] etiquetas = {
            "Placa Base:", "Procesador:", "RAM:", "Caja:", "Refrigeración:",
            "Fuente Alimentación:", "Gráfica:", "Disco Duro:", "Teclado:",
            "Ratón:", "Monitor:"
        };

        // Datos de ejemplo para los ComboBox
        String[][] datosEjemplo = {
            {"Seleccionar...", "ASUS ROG STRIX B550-F Gaming - 189.99€", "MSI MAG B550 TOMAHAWK - 159.99€", "Gigabyte B550 AORUS Pro - 179.99€"},
            {"Seleccionar...", "AMD Ryzen 7 5800X - 299.99€", "AMD Ryzen 5 5600X - 199.99€", "Intel Core i7-12700K - 389.99€"},
            {"Seleccionar...", "Corsair Vengeance 16GB DDR4 - 45.00€", "Kingston Fury 32GB DDR4 - 89.99€", "G.Skill Trident Z 16GB - 79.99€"},
            {"Seleccionar...", "NZXT H510 - 79.99€", "Corsair 4000D Airflow - 94.99€", "Lian Li O11 Dynamic - 149.99€"},
            {"Seleccionar...", "Noctua NH-D15 - 89.99€", "Corsair H100i RGB - 119.99€", "Be Quiet Dark Rock 4 - 69.99€"},
            {"Seleccionar...", "Corsair RM750 750W - 89.99€", "EVGA SuperNOVA 650W - 79.99€", "Seasonic Focus GX-850 - 129.99€"},
            {"Seleccionar...", "NVIDIA RTX 4070 - 599.99€", "AMD RX 7800 XT - 499.99€", "NVIDIA RTX 4060 Ti - 399.99€"},
            {"Seleccionar...", "Samsung 970 EVO 1TB - 109.99€", "WD Black SN850X 1TB - 129.99€", "Crucial P3 2TB - 149.99€"},
            {"Seleccionar...", "Logitech G Pro - 129.99€", "Razer BlackWidow V3 - 139.99€", "Corsair K70 RGB - 159.99€"},
            {"Seleccionar...", "Logitech G502 - 49.99€", "Razer DeathAdder V3 - 69.99€", "SteelSeries Rival 600 - 79.99€"},
            {"Seleccionar...", "LG 27GL850-B 27\" 144Hz - 349.99€", "ASUS VG248QE 24\" 144Hz - 249.99€", "Samsung Odyssey G5 32\" - 299.99€"}
        };

        int yPos = 30;
        JComboBox<String>[] comboBoxes = new JComboBox[etiquetas.length];
        JSpinner[] spinners = new JSpinner[etiquetas.length];
        JButton[] botonesAnadir = new JButton[etiquetas.length];

        for (int i = 0; i < etiquetas.length; i++) {
            // Label
            JLabel lbl = new JLabel(etiquetas[i]);
            lbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
            lbl.setBounds(20, yPos, 120, 20);
            panelSeleccion.add(lbl);

            // ComboBox
            comboBoxes[i] = new JComboBox<>(datosEjemplo[i]);
            comboBoxes[i].setBounds(140, yPos, 380, 22);
            panelSeleccion.add(comboBoxes[i]);

            // Spinner
            spinners[i] = new JSpinner(new SpinnerNumberModel(0, 0, 99, 1));
            spinners[i].setBounds(540, yPos, 50, 22);
            panelSeleccion.add(spinners[i]);

            // Botón Añadir
            botonesAnadir[i] = new JButton("Añadir");
            botonesAnadir[i].setBounds(610, yPos, 80, 22);
            panelSeleccion.add(botonesAnadir[i]);

            // Listener para mostrar detalles al seleccionar
            final int index = i;
            comboBoxes[i].addActionListener(e -> {
                mostrarDetalles(etiquetas[index], (String) comboBoxes[index].getSelectedItem());
            });

            // Listener para añadir al pedido
            botonesAnadir[i].addActionListener(e -> {
                String seleccionado = (String) comboBoxes[index].getSelectedItem();
                int cantidad = (int) spinners[index].getValue();
                if (!seleccionado.equals("Seleccionar...") && cantidad > 0) {
                    anadirAlPedido(seleccionado, cantidad);
                }
            });

            yPos += 42;
        }

        // Botón Finalizar Compra dentro del panel de selección
        JButton btnFinalizar = new JButton("FINALIZAR COMPRA");
        btnFinalizar.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnFinalizar.setBounds(250, 480, 200, 30);
        panelSeleccion.add(btnFinalizar);

        // ============== PANEL DERECHO - DETALLES ==============
        JPanel panelDetalles = new JPanel();
        panelDetalles.setBorder(new TitledBorder("Detalles del Componente"));
        panelDetalles.setBounds(770, 10, 400, 300);
        panelDetalles.setLayout(null);
        contentPane.add(panelDetalles);

        txtDetalles = new JTextArea();
        txtDetalles.setEditable(false);
        txtDetalles.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtDetalles.setLineWrap(true);
        txtDetalles.setWrapStyleWord(true);
        txtDetalles.setText("Selecciona un componente para ver sus detalles...");
        
        JScrollPane scrollDetalles = new JScrollPane(txtDetalles);
        scrollDetalles.setBounds(10, 20, 380, 270);
        panelDetalles.add(scrollDetalles);

        // ============== PANEL DERECHO INFERIOR - RESUMEN ==============
        JPanel panelResumen = new JPanel();
        panelResumen.setBorder(new TitledBorder("Resumen del Pedido"));
        panelResumen.setBounds(770, 320, 400, 380);
        panelResumen.setLayout(null);
        contentPane.add(panelResumen);

        JTextArea txtResumen = new JTextArea();
        txtResumen.setEditable(false);
        txtResumen.setFont(new Font("Monospaced", Font.PLAIN, 11));
        
        JScrollPane scrollResumen = new JScrollPane(txtResumen);
        scrollResumen.setBounds(10, 20, 380, 300);
        panelResumen.add(scrollResumen);

        // Label precio total
        lblPrecioTotal = new JLabel("TOTAL: 0.00€");
        lblPrecioTotal.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblPrecioTotal.setBounds(220, 330, 170, 30);
        panelResumen.add(lblPrecioTotal);

        // Botón limpiar pedido
        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(10, 330, 100, 30);
        panelResumen.add(btnLimpiar);
    }

    private void mostrarDetalles(String tipo, String componente) {
        if (componente.equals("Seleccionar...")) {
            txtDetalles.setText("Selecciona un componente para ver sus detalles...");
            return;
        }

        // Aquí harías la consulta a la BD para obtener los atributos específicos
        // Por ahora muestro datos de ejemplo según el tipo
        StringBuilder sb = new StringBuilder();
        sb.append("TIPO: ").append(tipo).append("\n\n");
        sb.append("NOMBRE: ").append(componente.split(" - ")[0]).append("\n\n");
        
        // Simular atributos específicos según tipo
        if (tipo.contains("Placa")) {
            sb.append("Socket: AM4\n");
            sb.append("Factor de Forma: ATX\n");
            sb.append("Chipset: B550\n");
            sb.append("Slots RAM: 4x DDR4\n");
            sb.append("Puertos USB: 8\n");
        } else if (tipo.contains("Procesador")) {
            sb.append("Núcleos: 8\n");
            sb.append("Hilos: 16\n");
            sb.append("Frecuencia Base: 3.8 GHz\n");
            sb.append("Frecuencia Turbo: 4.7 GHz\n");
            sb.append("TDP: 105W\n");
        } else if (tipo.contains("RAM")) {
            sb.append("Tipo: DDR4\n");
            sb.append("Capacidad: 16GB (2x8GB)\n");
            sb.append("Frecuencia: 3200 MHz\n");
            sb.append("Latencia: CL16\n");
        } else if (tipo.contains("Gráfica")) {
            sb.append("VRAM: 12GB GDDR6X\n");
            sb.append("Núcleos CUDA: 5888\n");
            sb.append("Frecuencia: 2475 MHz\n");
            sb.append("TDP: 200W\n");
        } else {
            sb.append("Detalles específicos del componente...\n");
        }

        sb.append("\nSTOCK: 15 unidades\n");
        sb.append("PRECIO: ").append(componente.split(" - ")[1]);

        txtDetalles.setText(sb.toString());
    }

    private void anadirAlPedido(String componente, int cantidad) {
        // Aquí añadirías la lógica para agregar al pedido
        // Y actualizar el panel de resumen
        System.out.println("Añadido: " + cantidad + "x " + componente);
    }
}