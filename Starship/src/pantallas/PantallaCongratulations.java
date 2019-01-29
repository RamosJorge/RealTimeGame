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

public class PantallaCongratulations implements IPantalla{

	PanelJuego panelJuego;
	
	BufferedImage image;
	Image rescaledImage;
		
	public PantallaCongratulations(PanelJuego panelJuego) {
		this.panelJuego = panelJuego;
	}
	
	@Override
	public void inicializarPantalla() {
		try {
			image = ImageIO.read(new File("Recursos/PNG/Environment/dirt.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		rescaleImage();
	}

	@Override
	public void pintarPantalla(Graphics g) {
		rellenarFondo(g);
		g.setColor(new Color(255, 0, 0, 255));
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString("LO HAS CONSEGUIDO. ENHORABUENA!", panelJuego.getWidth()/2-300, panelJuego.getHeight()/2);
	}

	@Override
	public void ejecutarFrame() {
		
	}

	@Override
	public void moverRaton(MouseEvent e) {
		
	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		PantallaJuego pantallaJuego = new PantallaJuego(panelJuego);
		pantallaJuego.inicializarPantalla();
		panelJuego.setPantallaActual(pantallaJuego);
		panelJuego.puntuacion = 0;
	}

	@Override
	public void redimensionarPantalla(ComponentEvent e) {
		
	}
	private void rellenarFondo(Graphics g){
		g.drawImage(rescaledImage, 0, 0, null);
	}
	public void rescaleImage() {
		rescaledImage = image.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
	}
}