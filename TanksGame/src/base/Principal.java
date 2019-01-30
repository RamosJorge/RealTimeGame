package base;
import java.awt.EventQueue;

/**
 * Clase Principal. En ella se inicializa la ventana.
 * @author jorgeramosgil
 * @version 1.0
 * @since 1.0
 */
public class Principal {

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
				ventanaPrincipal.inicializar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
	}
}