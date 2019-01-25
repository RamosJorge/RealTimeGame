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
		// TODO Auto-generated constructor stub
		this.panelJuego = panelJuego;
	}
	
	@Override
	public void inicializarPantalla() {
		// TODO Auto-generated method stub
		try {
			image = ImageIO.read(new File("Recursos/PNG/environment/dirt.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rescaleImage();
	}

	@Override
	public void pintarPantalla(Graphics g) {
		// TODO Auto-generated method stub
		rellenarFondo(g);
	}

	@Override
	public void ejecutarFrame() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		// TODO Auto-generated method stub
		PantallaJuego pantallaJuego = new PantallaJuego(panelJuego);
		pantallaJuego.inicializarPantalla();
		panelJuego.setPantallaActual(pantallaJuego);
		
	}

	@Override
	public void redimensionarPantalla(ComponentEvent e) {
		// TODO Auto-generated method stub
		//redimensionarPantalla(e);
	}
	private void rellenarFondo(Graphics g){
		g.drawImage(rescaledImage, 0, 0, null);
	}
	public void rescaleImage() {
		rescaledImage = image.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
	}
}