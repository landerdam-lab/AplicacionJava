
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ClientesInicio extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    
    // Controlador
    private EventosClientesInicio eventosClientesInicio;
    
    private JButton btnClienteExistente;
    private JButton btnClienteNuevo;
    
    private Usuario usuarioActual;
    
    public ClientesInicio(Usuario usuario) {
        
        this.usuarioActual = usuario;
        
        // Evitamos error si el usuario es null
        String nombreAtiende = "Desconocido";
        if (usuario != null) {
            nombreAtiende = usuario.getNombre();
        }
        
        setTitle("Inicio - Atendido por: " + nombreAtiende);
        
        try {
             setIconImage(Toolkit.getDefaultToolkit().getImage("imagenes\\logo.jpg"));
        } catch (Exception e) {
             System.out.println("Logo no encontrado.");
        }

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(700, 300, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        
        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(3, 1, 0, 0));
        
        // 1. Crear botones
        btnClienteExistente = new JButton("Cliente Existente");
        panel.add(btnClienteExistente);
        
        JLabel lblNewLabel = new JLabel("");
        panel.add(lblNewLabel);
        
        btnClienteNuevo = new JButton("Nuevo Cliente");
        panel.add(btnClienteNuevo);
        
        // 2. Instanciar el controlador
        // Al hacer esto, el controlador entra en su constructor y ÉL MISMO
        // busca los botones y les pone el listener. No tienes que hacer nada más.
        eventosClientesInicio = new EventosClientesInicio(this);
    }

    // --- Getters necesarios para que el controlador acceda a los botones ---

    public JButton getBtnClienteExistente() {
        return btnClienteExistente;
    }

    public JButton getBtnClienteNuevo() {
        return btnClienteNuevo;
    }
    
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
}