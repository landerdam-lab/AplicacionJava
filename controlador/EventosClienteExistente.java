import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class EventosClienteExistente implements ActionListener {

	private ClienteExistente clienteExistente;
	private ClienteDAO clienteDao;

	public EventosClienteExistente(ClienteExistente clienteExistente) {
		this.clienteExistente = clienteExistente;
		this.clienteDao = new ClienteDAO();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == clienteExistente.getBtnComprobarUsuario()) {
			
			String correo = clienteExistente.getTxtComprobarUsuario().getText();

			if (correo.isEmpty()) {
				JOptionPane.showMessageDialog(clienteExistente, "Por favor, introduce un correo.");
				return;
			}

			boolean existe = clienteDao.loginCliente(correo);

			if (existe) {
				JOptionPane.showMessageDialog(clienteExistente, "¡Cliente encontrado! Acceso permitido.");
				
				clienteExistente.setVisible(false);
				MenuCompras menucompras = new MenuCompras(clienteExistente);
	            menucompras.setVisible(true);
				
			} else {
				JOptionPane.showMessageDialog(clienteExistente, "El correo no existe en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}