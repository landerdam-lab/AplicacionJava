import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;


public class EventosLogin implements ActionListener {
    
    private Login login; 
    private UsuarioDAO dao;

    public EventosLogin(Login login) {
        this.login = login;
        this.dao = new UsuarioDAO();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String usuario = login.getTxtUsuario().getText(); 
        String password = new String(login.getTxtPassword().getPassword());
        
        if(usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(login, "Por favor, rellena usuario y contraseña.");
            return;
        }

        String nombreRecibido = dao.loginTrabajador(usuario, password);

        if (nombreRecibido != null) {
            
            JOptionPane.showMessageDialog(login, "Bienvenido/a " + nombreRecibido);
            
            login.setVisible(false);

            ClientesInicio inicio = new ClientesInicio(login);
            inicio.setVisible(true);
            
        } else {
            JOptionPane.showMessageDialog(login, "Usuario o contraseña incorrectos", "Error de acceso", JOptionPane.ERROR_MESSAGE);
        }
    }
}