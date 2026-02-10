import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventosMenuCompras implements ActionListener {

    private MenuCompras menuCompras;

    public EventosMenuCompras(MenuCompras menuCompras) {
        this.menuCompras = menuCompras;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // 1. Botón COMPRAR COMPONENTES
        if (e.getSource() == menuCompras.getBtnComponentes()) {
            menuCompras.dispose();
            // La vista CompraComponentes ya crea su propio controlador internamente
            new CompraComponentes(menuCompras.getClienteActual()).setVisible(true);
        }

        // 2. Botón CONFIGURAR PC
        else if (e.getSource() == menuCompras.getBtnConfiguraciones()) {
            menuCompras.dispose(); 
            // La vista ConfigurarPC ya crea su propio controlador internamente
            ConfigurarPC ventana = new ConfigurarPC(menuCompras.getClienteActual());
            ventana.setVisible(true);
        }

        // 3. Botón CARGAR PEDIDOS (Historial)
        else if (e.getSource() == menuCompras.getBtnCargarPedidos()) {
            menuCompras.dispose();
            // La vista MisPedidos ya crea su propio controlador internamente
            MisPedidos ventana = new MisPedidos(menuCompras.getClienteActual());
            ventana.setVisible(true);
        }
    }
}