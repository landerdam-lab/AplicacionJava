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
        
        String usuarioTexto = login.getTxtUsuario().getText(); 
        String password = new String(login.getTxtPassword().getPassword());
        
        if(usuarioTexto.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(login, "Por favor, rellena usuario y contraseña.");
            return;
        }

        Usuario usuarioLogueado = dao.loginUsuario(usuarioTexto, password);

        if (usuarioLogueado != null) {
            
            JOptionPane.showMessageDialog(login, "Bienvenido " + usuarioLogueado.getNombre() + " " + usuarioLogueado.getApellido1());
            
            login.dispose();


            ClientesInicio inicio = new ClientesInicio(usuarioLogueado);
            inicio.setVisible(true);
            
        } else {
            JOptionPane.showMessageDialog(login, 
                "Usuario o contraseña incorrectos", 
                "Error de acceso", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}