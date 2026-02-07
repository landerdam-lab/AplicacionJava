import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventosClientesInicio {

	private ClientesInicio clientesInicio;
	
	public EventosClientesInicio(ClientesInicio clientesInicio) {

		this.clientesInicio=clientesInicio;

		this.clientesInicio.getBtnClienteExistente().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clientesInicio.setVisible(false);

				ClienteExistente existe = new ClienteExistente();
				existe.setVisible(true);
			}
		});
		
		this.clientesInicio.getBtnClienteNuevo().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clientesInicio.setVisible(false);

				ClientesRegistro existe = new ClientesRegistro();
				existe.setVisible(true);
			}
		});
	}

}
