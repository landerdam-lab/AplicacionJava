
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;


public class EventosPedidosImpagados implements ActionListener {

	private PedidosImpagados vista;
	private PedidoDAO pedidoDAO;

	public EventosPedidosImpagados(PedidosImpagados vista) {
		this.vista = vista;
		this.pedidoDAO = new PedidoDAO();
	}

	public void cargarListaImpagados() {
		vista.getModeloTabla().setRowCount(0);

		List<Pedido> lista = pedidoDAO.listarPedidosImpagados();

		if (lista.isEmpty()) {
			JOptionPane.showMessageDialog(vista, "No hay pedidos pendientes de pago.");
		}

		for (Pedido p : lista) {
			vista.getModeloTabla().addRow(new Object[] {
					p.getIdPedido(),
					p.getPrecioTotal(),
					p.getFecha()
			});
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == vista.getBtnVolver()) {
			vista.dispose();
		} else if (e.getSource() == vista.getBtnRefrescar()) {
			cargarListaImpagados();
		}
	}
}