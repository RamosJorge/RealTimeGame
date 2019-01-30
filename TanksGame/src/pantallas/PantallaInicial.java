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
	
	@Override
	public void inicializarPantalla() {
		try {
			image = ImageIO.read(new File("Recursos/PNG/environment/dirt.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		rescaleImage();
	}

	@Override
	public void pintarPantalla(Graphics g) {
		rellenarFondo(g);
		g.setColor(new Color(255, 0, 0, 255));
		g.setFont(new Font("Arial", Font.BOLD, 50));
		g.drawString("JUEGO DE TANQUES", panelJuego.getWidth()/2-275, panelJuego.getHeight()/2-50);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("HAZ CLICK PARA EMPEZAR", panelJuego.getWidth()/2-150, panelJuego.getHeight()/2+100);
	}

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

	@Override
	public void pulsarRaton(MouseEvent e) {
		PantallaJuego pantallaJuego = new PantallaJuego(panelJuego);
		pantallaJuego.inicializarPantalla();
		panelJuego.setPantallaActual(pantallaJuego);
		
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