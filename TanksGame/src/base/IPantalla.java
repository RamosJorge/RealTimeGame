package base;

import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

/**
 * Clase IPantalla. Métodos comunes
 * @author jorgeramosgil
 * @version 1.0
 * @since 1.0
 */
public interface IPantalla {

	public void inicializarPantalla();
	public void pintarPantalla(Graphics g);
	public void ejecutarFrame();
	public void moverRaton(MouseEvent e);
	public void pulsarRaton(MouseEvent e);
	public void redimensionarPantalla(ComponentEvent e);
}