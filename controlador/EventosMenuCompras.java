import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// Importamos las vistas necesarias


public class EventosMenuCompras implements ActionListener {

    private MenuCompras menuCompras;

    public EventosMenuCompras(MenuCompras menuCompras) {
        this.menuCompras = menuCompras;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        // 1. Botón COMPRAR COMPONENTES
        if (e.getSource() == menuCompras.getBtnComponentes()) {
            menuCompras.dispose(); // Cierra el menú
            
            // Abre la tienda pasando el cliente
            CompraComponentes ventana = new CompraComponentes(menuCompras.getClienteActual());
            ventana.setVisible(true);
        }
        
        // 2. Botón CONFIGURAR PC
        else if (e.getSource() == menuCompras.getBtnConfiguraciones()) {
            menuCompras.dispose();
            
            ConfigurarPC ventana = new ConfigurarPC(menuCompras.getClienteActual());
            ventana.setVisible(true);
        }
        
        // 3. Botón CARGAR PEDIDOS
        else if (e.getSource() == menuCompras.getBtnCargarPedidos()) {
            menuCompras.dispose();
            
            // Abre el historial de pedidos
            MisPedidos ventana = new MisPedidos(menuCompras.getClienteActual());
            ventana.setVisible(true);
        }
    }
}