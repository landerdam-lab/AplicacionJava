import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventosLogin {
    
    private Login login; 
    private ClientesInicio clientesInicio;

    public EventosLogin(Login login) {
        this.login = login;
        
        this.login.getBtnIngresar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login.setVisible(false);
                
                ClientesInicio inicio = new ClientesInicio(login);
                inicio.setVisible(true);
                
            }
        });
    }
}