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

			Cliente clienteLogueado = clienteDao.loginCliente(correo);

			if (clienteLogueado != null) {

				JOptionPane.showMessageDialog(clienteExistente, "¡Hola " + clienteLogueado.getNombre() + "! Acceso permitido.");
				
				clienteExistente.dispose();

				MenuCompras menucompras = new MenuCompras(clienteLogueado);
				menucompras.setVisible(true);
				
			} else {
				JOptionPane.showMessageDialog(clienteExistente, "El correo no existe en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}