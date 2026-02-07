import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class EventosClienteRegistro implements ActionListener {

    private ClientesRegistro clientesRegistro;
    private ClienteDAO clienteDAO;

    public EventosClienteRegistro(ClientesRegistro clientesRegistro) {
        this.clientesRegistro = clientesRegistro;
        this.clienteDAO = new ClienteDAO();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == clientesRegistro.getBtnConfirmarRegistro()) {
            
            String nombre = clientesRegistro.getTxtNombre().getText();
            String telefono = clientesRegistro.getTxtTelefono().getText();
            String correo = clientesRegistro.getTxtCorreo().getText();
            String dni = clientesRegistro.getTxtDni().getText();
            
            if (nombre.isEmpty() || telefono.isEmpty() || correo.isEmpty() || dni.isEmpty()) {
                JOptionPane.showMessageDialog(clientesRegistro, "Por favor, rellena todos los campos.");
                return;
            }
            
            boolean registrado = clienteDAO.registrarCliente(nombre, correo, dni, telefono);
            
            if (registrado) {
                JOptionPane.showMessageDialog(clientesRegistro, "¡Cliente registrado correctamente!");
              
                clientesRegistro.dispose(); 
                ClienteExistente existe = new ClienteExistente();
				existe.setVisible(true);
               
                
            } else {
                JOptionPane.showMessageDialog(clientesRegistro, "Error al registrar. Comprueba si el DNI o el Email ya existen.", "Error de Registro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}