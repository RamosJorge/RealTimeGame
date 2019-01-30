package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import base.PanelJuego;
import base.IPantalla;

/**
 * Clase PantallaGameOver. En ella se muestra la pantalla de game over.
 * @author jorgeramosgil
 * @version 1.0
 * @since 1.0
 */
public class PantallaGameOver implements IPantalla{

	PanelJuego panelJuego;
	
	BufferedImage image;
	Image rescaledImage;
		
	public PantallaGameOver(PanelJuego panelJuego) {
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
	 * Método que rellena el fondo y muestra el texto
	 */
	@Override
	public void pintarPantalla(Graphics g) {
		rellenarFondo(g);
		g.setColor(new Color(255, 0, 0, 255));
		g.setFont(new Font("Arial", Font.BOLD, 50));
		g.drawString("HAS PERDIDO!", panelJuego.getWidth()/2-175, panelJuego.getHeight()/2);
	}

	@Override
	public void ejecutarFrame() {
		
	}

	@Override
	public void moverRaton(MouseEvent e) {
		
	}

	/**
	 * Mñetodo que vuelve a la pantalla de juego cuando hacemos click
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
	 * Método que dibuja la imagen de fondo reescalada
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