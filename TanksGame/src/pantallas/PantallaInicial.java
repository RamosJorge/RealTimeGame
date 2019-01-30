package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import base.PanelJuego;
import base.IPantalla;

/**
 * Clase PantallaInicial. En ella se muestra la pantalla inicial.
 * @author jorgeramosgil
 * @version 1.0
 * @since 1.0
 */
public class PantallaInicial implements IPantalla{

	PanelJuego panelJuego;
	
	BufferedImage image;
	Image rescaledImage;
	
	Color colorLetras = Color.RED;
	int counter = 0;
	static final int CAMBIO_COLOR = 5;
	
	public PantallaInicial(PanelJuego panelJuego) {
		this.panelJuego = panelJuego;
	}
	
	/**
	 * Método que carga la imagen de fondo
	 */
	@Override
	public void inicializarPantalla() {
		try {
			image = ImageIO.read(new File("Recursos/PNG/environment/dirt.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		rescaleImage();
	}

	/**
	 * Método que pinta el texto por pantalla
	 */
	@Override
	public void pintarPantalla(Graphics g) {
		rellenarFondo(g);
		g.setColor(new Color(255, 0, 0, 255));
		g.setFont(new Font("Arial", Font.BOLD, 50));
		g.drawString("JUEGO DE TANQUES", panelJuego.getWidth()/2-275, panelJuego.getHeight()/2-50);
		g.setColor(colorLetras);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("HAZ CLICK PARA EMPEZAR", panelJuego.getWidth()/2-150, panelJuego.getHeight()/2+100);
	}

	/**
	 * Método que cambia el color del texto en cada frame
	 */
	@Override
	public void ejecutarFrame() {
		counter++;
		if (counter % CAMBIO_COLOR == 0) {
			if (colorLetras.equals(Color.RED)) {
				colorLetras = Color.GREEN;
			} else {
				colorLetras = Color.RED;
			}
		}
		
	}

	@Override
	public void moverRaton(MouseEvent e) {
		
	}

	/**
	 * Método que vuelve a la pantalla de juego cada vez que hacemos click
	 */
	@Override
	public void pulsarRaton(MouseEvent e) {
		PantallaJuego pantallaJuego = new PantallaJuego(panelJuego);
		pantallaJuego.inicializarPantalla();
		panelJuego.setPantallaActual(pantallaJuego);
		
	}

	@Override
	public void redimensionarPantalla(ComponentEvent e) {
		
	}
	/**
	 * Método que dibuja la imagen de fondo
	 * @param g
	 */
	private void rellenarFondo(Graphics g){
		g.drawImage(rescaledImage, 0, 0, null);
	}
	
	/**
	 * Método que reescala la imagen de fondo 
	 */
	public void rescaleImage() {
		rescaledImage = image.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
	}
}