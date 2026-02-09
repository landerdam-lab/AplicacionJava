import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class EventosClientesInicio {

	private ClientesInicio clientesInicio;
	
	public EventosClientesInicio(ClientesInicio clientesInicio) {

		this.clientesInicio = clientesInicio;

		this.clientesInicio.getBtnClienteExistente().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clientesInicio.setVisible(false);

				ClienteExistente ventanaLoginCliente = new ClienteExistente();
				ventanaLoginCliente.setVisible(true);
			}
		});
		
		this.clientesInicio.getBtnClienteNuevo().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clientesInicio.setVisible(false);

				ClientesRegistro ventanaRegistro = new ClientesRegistro(clientesInicio);
				ventanaRegistro.setVisible(true);
			}
		});
	}
}