
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class EventosMenuCompras implements ActionListener {

    private MenuCompras menuCompras;

    public EventosMenuCompras(MenuCompras menuCompras) {
        this.menuCompras = menuCompras;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == menuCompras.getBtnComponentes()) {
            menuCompras.dispose();
            new CompraComponentes(menuCompras.getClienteActual()).setVisible(true);
        }
        else if (e.getSource() == menuCompras.getBtnConfiguraciones()) {
            menuCompras.dispose(); 
            ConfigurarPC ventana = new ConfigurarPC(menuCompras.getClienteActual());
            ventana.setVisible(true);
        }
        else if (e.getSource() == menuCompras.getBtnCargarPedidos()) {
            menuCompras.dispose();
            MisPedidos ventana = new MisPedidos(menuCompras.getClienteActual());
            ventana.setVisible(true);
        }
        else if (e.getSource() == menuCompras.getBtnImpagos()) {
            new PedidosImpagados().setVisible(true);
        }

        else if (e.getSource() == menuCompras.getBtnVolver()) {
            menuCompras.dispose(); 
            
            ClientesInicio inicio = new ClientesInicio(null); 
            inicio.setVisible(true);
        }
    }
}