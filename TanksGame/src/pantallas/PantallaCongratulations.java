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
 * Clase PantallaCongratulations. En ella se muestra la pantalla de victoria.
 * @author jorgeramosgil
 * @version 1.0
 * @since 1.0
 */
public class PantallaCongratulations implements IPantalla{

	PanelJuego panelJuego;
	
	BufferedImage image;
	Image rescaledImage;
		
	public PantallaCongratulations(PanelJuego panelJuego) {
		this.panelJuego = panelJuego;
	}
	
	/**
	 * Método que carga la imagen de fondo
	 */
	@Override
	public void inicializarPantalla() {
		try {
			image = ImageIO.read(new File("Recursos/PNG/Environment/dirt.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		rescaleImage();
	}

	/**
	 * Método que rellena el fondo y muestra los textos
	 */
	@Override
	public void pintarPantalla(Graphics g) {
		rellenarFondo(g);
		g.setColor(new Color(255, 0, 0, 255));
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString("LO HAS CONSEGUIDO. ENHORABUENA!", panelJuego.getWidth()/2-250, panelJuego.getHeight()/2);
	}

	@Override
	public void ejecutarFrame() {
		
	}

	@Override
	public void moverRaton(MouseEvent e) {
		
	}

	/**
	 * Método que nos devuelve a la pantalla de juego cuando hacemos click
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
	 * Método que rellena el fondo con la imgagen reescalada
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