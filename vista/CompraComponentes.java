import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.util.ArrayList; // Importante
import java.util.List;      // Importante
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
    
    // NUEVO: Lista para recordar qué estamos comprando
    private List<LineaPedido> carritoDeCompra;

    public CompraComponentes(Cliente cliente) {
        this.clienteActual = cliente;
        this.componenteDAO = new ComponenteDAO();
        this.carritoDeCompra = new ArrayList<>(); // Inicializamos la lista

        setTitle("Comprar Componentes - Cliente: " + cliente.getNombre());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 750);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // --- PANEL IZQUIERDO ---
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
        
     // En CompraComponentes.java

        String[] tablasBD = {
            "PROCESADOR", 
            "PLACA_BASE",           // <--- CAMBIO: Con guion bajo
            "RAM", 
            "TARJETA_GRAFICA",      // <--- CAMBIO: Con guion bajo (Aquí está tu error actual)
            "DISCO_DURO",           // <--- CAMBIO: Con guion bajo
            "CAJA", 
            "FUENTE_ALIMENTACION",  // <--- CAMBIO: Con guion bajo
            "REFRIGERACION", 
            "MONITOR", 
            "TECLADO", 
            "RATON"
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

        // --- PANEL DETALLES ---
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

        // --- PANEL RESUMEN ---
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
        // NUEVO: Ahora llama a la lógica real
        btnFinalizar.addActionListener(e -> finalizarPedido());
        panelResumen.add(btnFinalizar);
    }

 // En vista/CompraComponentes.java

    private void mostrarDetalles(Componente c) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("Producto: ").append(c.getNombre()).append("\n");
        sb.append("Precio: ").append(c.getPrecioVenta()).append("€\n");
        // AQUÍ MOSTRAMOS EL STOCK EN LOS DETALLES
        sb.append("Stock disponible: ").append(c.getStock()).append("\n");
        sb.append("Descripción: ").append(c.getDescripcion()).append("\n");
        sb.append("----------------------------\n");
        
        // Detalles específicos según el tipo
        if (c instanceof Procesador) {
            Procesador p = (Procesador) c;
            sb.append("Núcleos: ").append(p.getNumNucleos()).append("\n");
            sb.append("Frecuencia: ").append(p.getFrecuenciaBase()).append("\n");
        } 
        else if (c instanceof Ram) {
            Ram r = (Ram) c;
            sb.append("Tipo: ").append(r.getTipo()).append("\n");
            sb.append("Capacidad: ").append(r.getCapacidad()).append("\n");
            sb.append("Frecuencia: ").append(r.getFrecuencia()).append("\n");
        }
        else if (c instanceof PlacaBase) {
            PlacaBase pb = (PlacaBase) c;
            sb.append("Socket: ").append(pb.getSocket()).append("\n");
            sb.append("Forma: ").append(pb.getFactorForma()).append("\n");
        }
        else if (c instanceof TarjetaGrafica) {
            TarjetaGrafica tg = (TarjetaGrafica) c;
            sb.append("VRAM: ").append(tg.getVram()).append("\n");
        }
        else if (c instanceof DiscoDuro) {
            DiscoDuro dd = (DiscoDuro) c;
            sb.append("Tipo: ").append(dd.getTipoAlmacenamiento()).append("\n");
            sb.append("Capacidad: ").append(dd.getCapacidad()).append("\n");
        }
        else if (c instanceof Caja) {
            Caja ca = (Caja) c;
            sb.append("Dimensiones: ").append(ca.getDimensiones()).append("\n");
            sb.append("Puertos: ").append(ca.getPuertosFrontales()).append("\n");
        }
        else if (c instanceof FuenteAlimentacion) {
            FuenteAlimentacion f = (FuenteAlimentacion) c;
            sb.append("Certificación: ").append(f.getCertificacionEnergetica()).append("\n");
            sb.append("Potencia: ").append(f.getPotencia()).append("\n");
        }
        else if (c instanceof Monitor) {
            Monitor m = (Monitor) c;
            sb.append("Hz: ").append(m.getHz()).append(" Hz\n");
            sb.append("Medidas: ").append(m.getMedidas()).append("\n");
        }
        else if (c instanceof Raton) {
            Raton rat = (Raton) c;
            sb.append("DPI: ").append(rat.getDpi()).append(" DPI\n");
            sb.append("Tipo: ").append(rat.getTipo()).append("\n");
        }
        else if (c instanceof Teclado) {
            Teclado t = (Teclado) c;
            sb.append("Tipo: ").append(t.getTipo()).append("\n");
            sb.append("Cable: ").append(t.getTipoCable()).append("\n");
        }
        else if (c instanceof Refrigeracion) {
            Refrigeracion r = (Refrigeracion) c;
            sb.append("Tipo: ").append(r.getTipo()).append("\n");
            sb.append("Tamaño: ").append(r.getTamanio()).append("\n");
        }
        
        txtDetalles.setText(sb.toString());
        txtDetalles.setCaretPosition(0);
    }

    private void anadirAlCarrito(Componente c, int cantidad) {
        // --- VALIDACIÓN DE STOCK ---
        if (cantidad > c.getStock()) {
            JOptionPane.showMessageDialog(this, 
                "No hay suficiente stock.\nStock disponible: " + c.getStock(), 
                "Error de Stock", 
                JOptionPane.WARNING_MESSAGE);
            return; // Salimos sin añadir nada
        }
        
        // Comprobamos si ya lo hemos añadido al carrito antes (Opcional, pero recomendado)
        // Para simplificar, restamos del objeto visualmente para que el usuario sepa que "se agota"
        // c.setStock(c.getStock() - cantidad); // Esto es solo visual temporal
        
        double subtotal = c.getPrecioVenta() * cantidad;
        precioTotalAcumulado += subtotal;
        
        // 1. Visual
        txtResumen.append(cantidad + "x " + c.getNombre() + " = " + String.format("%.2f", subtotal) + "€\n");
        lblPrecioTotal.setText("TOTAL: " + String.format("%.2f", precioTotalAcumulado) + "€");
        
        // 2. Lógica
        LineaPedido linea = new LineaPedido(c.getIdComponente(), cantidad, c.getPrecioVenta());
        carritoDeCompra.add(linea);
    }
    
    private void finalizarPedido() {
        if (carritoDeCompra.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El carrito está vacío.");
            return;
        }

        // Llamamos al DAO para guardar en Oracle
        PedidoDAO pedidoDao = new PedidoDAO();
        
        // CORRECCIÓN: Añadimos 'false' al final porque comprar piezas sueltas NO incluye montaje
        boolean exito = pedidoDao.registrarPedido(clienteActual, carritoDeCompra, precioTotalAcumulado, false);

        if (exito) {
            JOptionPane.showMessageDialog(this, "¡Pedido guardado en la Base de Datos!\nCliente: " + clienteActual.getNombre());
            
            // Limpiar todo
            txtResumen.setText("");
            precioTotalAcumulado = 0;
            lblPrecioTotal.setText("TOTAL: 0.00€");
            carritoDeCompra.clear(); // Vaciamos la lista
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el pedido en la Base de Datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}