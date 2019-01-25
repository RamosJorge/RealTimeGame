package pantallas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import base.PanelJuego;
import base.IPantalla;
import base.Sprite;

public class PantallaNivel implements IPantalla{

	private static final int anchoCuadrado = 30;
		
	PanelJuego panelJuego;
	
	ArrayList<Sprite> asteroides;
	BufferedImage image;
	Image rescaledImage;
	
	Sprite nave;
	Sprite disparo1;
	Sprite disparo2;
	
	double tiempoInicial;
	double tiempoDeJuego;
	private DecimalFormat formatoDecimal; //Formatea la salida.
	Font fuenteTiempo;
	
	public PantallaNivel(PanelJuego panelJuego) {
		// TODO Auto-generated constructor stub
		this.panelJuego = panelJuego;
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		// Set the blank cursor to the JFrame.
		panelJuego.setCursor(blankCursor);
		tiempoInicial = System.nanoTime();
	}
	
	@Override
	public void inicializarPantalla() {
		// TODO Auto-generated method stub
		nave = new Sprite(50, 50, 300, 200, "Imagenes/Imagenes/starship.png");
		asteroides = new ArrayList<Sprite>();
		for (int i = 0; i < 8; i++) {
			Random rd = new Random();
			Sprite creador;
			int velocidadX = rd.nextInt(15)+1;
			int velocidadY = rd.nextInt(15)+1;
			creador = new Sprite(anchoCuadrado+15, anchoCuadrado, 0, 0, velocidadX, velocidadY, "Imagenes/Imagenes/meteor.png");
			asteroides.add(creador);
		}
		try {
			image = ImageIO.read(new File("Imagenes/Imagenes/galaxia.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fuenteTiempo = new Font("Arial", Font.BOLD, 20);
		tiempoInicial = System.nanoTime();
		tiempoDeJuego = 0;
		formatoDecimal = new DecimalFormat("#.##");
		rescaleImage();
	}

	@Override
	public void pintarPantalla(Graphics g) {
		// TODO Auto-generated method stub
		rellenarFondo(g);
		//Pintamos los cuadrados:
		for (Sprite cuadrado : asteroides) {
			cuadrado.pintarSpriteEnMundo(g);
		}
		if (disparo1 != null) {
			disparo1.pintarSpriteEnMundo(g);
		}
		if (disparo2 != null) {
			disparo2.pintarSpriteEnMundo(g);
		}
		nave.pintarSpriteEnMundo(g);
		pintarTiempo(g);
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString("PUNTUACIÓN:       "+Integer.toString(panelJuego.puntuacion), panelJuego.getWidth()-150, 30);
	}
	
	private void pintarTiempo(Graphics g) {
		Font f = g.getFont();
		Color c = g.getColor();
		
		g.setColor(Color.WHITE);
		g.setFont(fuenteTiempo);
		actualizarTiempo();
		g.drawString(formatoDecimal.format(tiempoDeJuego/1000000000d), 25, 25);
		
		g.setColor(c);
		g.setFont(f);
	}

	private void actualizarTiempo() {
		tiempoDeJuego = System.nanoTime() - tiempoInicial;
	}

	@Override
	public void ejecutarFrame() {
		// TODO Auto-generated method stub
		checkCollisions();
		checkCollisionsNave();
		moverSprites();
	}

	@Override
	public void moverRaton(MouseEvent e) {
		// TODO Auto-generated method stub
		nave.setPosX(e.getX()-nave.getAncho()/2);
		nave.setPosY(e.getY()-nave.getAlto()/2);
	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		// TODO Auto-generated method stub
		if(SwingUtilities.isLeftMouseButton(e)){
			if (disparo1 == null) {
				disparo1 = new Sprite(30, 75, nave.getPosX()+nave.getAncho()/2-30, nave.getPosY()+nave.getAlto()/2-80, -10, -20, "Imagenes/Imagenes/blastLeft.png");
			}
			if (disparo2 == null) {
				disparo2 = new Sprite(30, 75, nave.getPosX()+nave.getAncho()/2, nave.getPosY()+nave.getAlto()/2-80, 10, -20, "Imagenes/Imagenes/blastRight.png");
			}
		}
	}

	@Override
	public void redimensionarPantalla(ComponentEvent e) {
		// TODO Auto-generated method stub
		redimensionarPantalla(e);
		
	}
	private void rellenarFondo(Graphics g){

		g.drawImage(rescaledImage, 0, 0, null);
	}
	private void moverSprites(){
		for (int i = 0; i < asteroides.size(); i++) {
			Sprite aux = asteroides.get(i);
			aux.moverSprite(panelJuego.getWidth(), panelJuego.getHeight());
		}
		if (disparo1 != null) {
			disparo1.moverDisparo();
			if (disparo1.getPosY()+disparo1.getAlto() <= 0) {
				disparo1 = null;
			}
		}
		if (disparo2 != null) {
			disparo2.moverDisparo();
			if (disparo2.getPosY()+disparo2.getAlto() <= 0) {
				disparo2 = null;
			}
		}
		
	}
	
	private void checkCollisions() {
		 
		for (int i = 0; i < asteroides.size() && disparo1 != null; i++) {
			if (asteroides.get(i).colisiona(disparo1)) {
				disparo1 = null;
				asteroides.remove(i);
				panelJuego.puntuacion++;
				if (asteroides.size() <= 0) {
					PantallaCongratulations pantallaCongratulations = new PantallaCongratulations(panelJuego);
					pantallaCongratulations.inicializarPantalla();
					panelJuego.setPantallaActual(pantallaCongratulations);
				}
			}
		}
		for (int i = 0; i < asteroides.size() && disparo2 != null; i++) {
			if (asteroides.get(i).colisiona(disparo2)) {
				disparo2 = null;
				asteroides.remove(i);
				panelJuego.puntuacion++;
				if (asteroides.size() <= 0) {
					PantallaCongratulations pantallaCongratulations = new PantallaCongratulations(panelJuego);
					pantallaCongratulations.inicializarPantalla();
					panelJuego.setPantallaActual(pantallaCongratulations);
				}
			}
		}
	}
	
	private void checkCollisionsNave() {
		 
		for (int i = 0; i < asteroides.size(); i++) {
			if (asteroides.get(i).colisiona(nave)) {
				PantallaGameOver pantallaGameOver = new PantallaGameOver(panelJuego);
				pantallaGameOver.inicializarPantalla();
				panelJuego.setPantallaActual(pantallaGameOver);
			}
		}
	}
	
	public void rescaleImage() {
		rescaledImage = image.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH);
	}
}