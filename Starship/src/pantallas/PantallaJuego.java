package pantallas;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import base.PanelJuego;
import base.IPantalla;
import base.Sprite;
import base.Sprite2D;

public class PantallaJuego implements IPantalla, KeyListener{

	private static final int anchoCuadrado = 30;
	
	private boolean w,a,s,d;
	
	private int tankAngle = 0;
	private int canonAngle = 0;
	
	PanelJuego panelJuego;
	
	ArrayList<Sprite> asteroides;
	BufferedImage image;
	Image rescaledImage;
	
	Sprite2D tank;
	Sprite2D canon;
	Sprite disparo;

	public PantallaJuego(PanelJuego panelJuego) {
		// TODO Auto-generated constructor stub
		this.panelJuego = panelJuego;
		panelJuego.addKeyListener(this);
	}
	
	@Override
	public void inicializarPantalla() {
		// TODO Auto-generated method stub
		tank = new Sprite2D(50, 50, 300, 200, "Recursos/PNG/Tanks/tankGreen.png");
		canon = new Sprite2D(10, 40, tank.getPosX()+(tank.getAncho()/2-5), tank.getPosY()-15, "Recursos/PNG/Tanks/barrelGreen_outline.png");
		asteroides = new ArrayList<Sprite>();
		for (int i = 0; i < 6; i++) {
			Random rd = new Random();
			Sprite creador;
			int velocidadX = rd.nextInt(10)+1;
			int velocidadY = rd.nextInt(10)+1;
			creador = new Sprite(anchoCuadrado, anchoCuadrado, 0, 0, 0, 0, "Imagenes/Imagenes/asteroide.png");
			asteroides.add(creador);
		}
		try {
			image = ImageIO.read(new File("Recursos/PNG/Environment/sand.png"));
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
		//Pintamos los cuadrados:
		for (Sprite cuadrado : asteroides) {
			cuadrado.pintarSpriteEnMundo(g);
		}
		if (disparo != null) {
			disparo.pintarSpriteEnMundo(g);
		}
		tank.pintarSpriteEnMundo(g,tankAngle);
		canon.pintarSpriteEnMundo2(g,canonAngle);
	}

	@Override
	public void ejecutarFrame() {
		// TODO Auto-generated method stub
		checkCollisions();
		//checkCollisionstank();
		moverSprites();
	}

	@Override
	public void moverRaton(MouseEvent e) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		// TODO Auto-generated method stub
		if(SwingUtilities.isLeftMouseButton(e)){
			if (disparo == null) {
				disparo = new Sprite(16, 40, tank.getPosX()+tank.getAncho()/2-8, tank.getPosY()+tank.getAlto()/2-20, 0, -25, "Recursos/PNG/Bullets/bulletGreen.png");
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
		if (disparo != null) {
			disparo.moverDisparo();
			if (disparo.getPosY()+disparo.getAlto() <= 0) {
				disparo = null;
			}
		}
		
	}
	
	private void checkCollisions() {
		 
		for (int i = 0; i < asteroides.size() && disparo != null; i++) {
			if (asteroides.get(i).colisiona(disparo)) {
				disparo = null;
				asteroides.remove(i);
				panelJuego.puntuacion++;
				if (asteroides.size() <= 0) {
					PantallaNivel pantallaNivel = new PantallaNivel(panelJuego);
					pantallaNivel.inicializarPantalla();
					panelJuego.setPantallaActual(pantallaNivel);
				}
			}
		}
	}
	/*
	private void checkCollisionstank() {
		 
		for (int i = 0; i < asteroides.size(); i++) {
			if (asteroides.get(i).colisiona(tank)) {
				PantallaGameOver pantallaGameOver = new PantallaGameOver(panelJuego);
				pantallaGameOver.inicializarPantalla();
				panelJuego.setPantallaActual(pantallaGameOver);
			}
		}
	}
	*/
	public void rescaleImage() {
		rescaledImage = image.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
	        case KeyEvent.VK_W: 
	        	w = true;
	        	tank.setPosX((int) (tank.getPosX()+10*Math.sin(Math.toRadians(tankAngle))));
	        	tank.setPosY((int) (tank.getPosY()+10*Math.cos(Math.toRadians(tankAngle))));
	        	canon.setPosY(canon.getPosY()-2);
	        	System.out.println("Posicionx:"+Math.sin(Math.toRadians(tankAngle)));
	        	System.out.println("Posiciony:"+Math.cos(Math.toRadians(tankAngle)));
	        break;
	        case KeyEvent.VK_A: 
	        	a = true;
	        	if (tankAngle == 0) {
	        		tankAngle = 360;
	        	}
	        	if (canonAngle == 0) {
	        		canonAngle = 360;
	        	}
	        	tankAngle--;
	        	canonAngle--;
	        	System.out.println("Angulo: "+tankAngle);
	        break;
	        case KeyEvent.VK_S: 
	        	s = true; 
	        	tank.setPosY(tank.getPosY()+2);
	        	canon.setPosY(canon.getPosY()+2);
	        break;
	        case KeyEvent.VK_D:
	        	d = true;
	        	if (tankAngle == 360) {
	        		tankAngle = 0;
	        	}
	        	if (canonAngle == 360) {
	        		canonAngle = 0;
	        	}
	        	tankAngle++;
	        	canonAngle++;
	        	System.out.println("Angulo: "+tankAngle);
	        break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {		
	}
}