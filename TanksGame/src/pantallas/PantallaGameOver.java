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

public class PantallaGameOver implements IPantalla{

	PanelJuego panelJuego;
	
	BufferedImage image;
	Image rescaledImage;
		
	public PantallaGameOver(PanelJuego panelJuego) {
		this.panelJuego = panelJuego;
	}
	
	@Override
	public void inicializarPantalla() {
		try {
			image = ImageIO.read(new File("Imagenes/Imagenes/gameover.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		rescaleImage();
	}

	@Override
	public void pintarPantalla(Graphics g) {
		rellenarFondo(g);
		g.setColor(new Color(255, 204, 0, 255));
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString("PUNTUACIÓN:    "+Integer.toString(panelJuego.puntuacion), panelJuego.getWidth()/2-150, panelJuego.getHeight()/2+200);
		g.drawString("HAS PERDIDO!", panelJuego.getWidth()/2-125, panelJuego.getHeight()/2+250);
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