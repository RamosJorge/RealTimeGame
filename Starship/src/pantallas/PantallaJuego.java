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

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import base.PanelJuego;
import base.IPantalla;
import base.Sprite2D;

public class PantallaJuego implements IPantalla, KeyListener{

	private static final int anchoCuadrado = 30;
	
	private boolean w,a,s,d;
	
	private int tankAngle = 0;
	private int canonAngle = 0;
	
	PanelJuego panelJuego;
	
	BufferedImage image;
	Image rescaledImage;
	
	Sprite2D tank;
	Sprite2D canon;
	Sprite2D disparo;

	Sprite2D tankEnemigo;
	Sprite2D canonEnemigo;
	Sprite2D disparoEnemigo;
	
	public PantallaJuego(PanelJuego panelJuego) {
		// TODO Auto-generated constructor stub
		this.panelJuego = panelJuego;
		panelJuego.addKeyListener(this);
	}
	
	@Override
	public void inicializarPantalla() {
		// TODO Auto-generated method stub
		tank = new Sprite2D(50, 50, 50, 500, "Recursos/PNG/Tanks/tankGreen.png");
		canon = new Sprite2D(10, 40, (int)tank.getPosX()+(tank.getAncho()/2-5), (int)tank.getPosY()-15, "Recursos/PNG/Tanks/barrelGreen_outline.png");
		tankEnemigo = new Sprite2D(50, 50, 700, 50, "Recursos/PNG/Tanks/tankRed.png");
		canonEnemigo = new Sprite2D(10, 40, (int)tankEnemigo.getPosX()+(tankEnemigo.getAncho()/2-5), (int)tankEnemigo.getPosY()-15, "Recursos/PNG/Tanks/barrelRed_outline.png");
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
		if (disparo != null) {
			disparo.pintarSpriteEnMundo(g, tankAngle);
		}
		tank.pintarSpriteEnMundo(g,tankAngle);
		canon.pintarSpriteEnMundo2(g,canonAngle);
		tankEnemigo.pintarSpriteEnMundo(g,-90);
		canonEnemigo.pintarSpriteEnMundo2(g,(int) Math.toDegrees(Math.atan2((tankEnemigo.getPosY() - tank.getPosY()), (tankEnemigo.getPosX() - tank.getPosX()))));
	}

	@Override
	public void ejecutarFrame() {
		// TODO Auto-generated method stub
		checkCollisions();
		moverSprites();
	}

	@Override
	public void moverRaton(MouseEvent e) {
	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		// TODO Auto-generated method stub
		if(SwingUtilities.isLeftMouseButton(e)){
			if (disparo == null) {
				disparo = new Sprite2D(16, 40, (int)tank.getPosX()+tank.getAncho()/2-8, (int)tank.getPosY()+tank.getAlto()/2-20, (int)(10*Math.cos(Math.toRadians(tankAngle-90))), (int)(10*Math.sin(Math.toRadians(tankAngle-90))), "Recursos/PNG/Bullets/bulletGreen.png");
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
		/*
		for (int i = 0; i < asteroides.size(); i++) {
			Sprite aux = asteroides.get(i);
			aux.moverSprite(panelJuego.getWidth(), panelJuego.getHeight());
		}*/
		if (disparo != null) {
			disparo.moverDisparo();
			if (disparo.getPosY()+disparo.getAlto() <= 0) {
				disparo = null;
			}
		}
		
	}
	
	private void checkCollisions() {
		if (disparo != null) {
			if (tankEnemigo.colisiona(disparo)) {
				disparo = null;
				panelJuego.puntuacion++;
				PantallaCongratulations pantallaCongratulations = new PantallaCongratulations(panelJuego);
				pantallaCongratulations.inicializarPantalla();
				panelJuego.setPantallaActual(pantallaCongratulations);
			}
		}
	}
	
	public void rescaleImage() {
		rescaledImage = image.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
	        case KeyEvent.VK_W: 
	        	w = true;
	        	tank.setPosX((float) (tank.getPosX()+2*Math.cos(Math.toRadians(tankAngle-90))));
	        	tank.setPosY((float) (tank.getPosY()+2*Math.sin(Math.toRadians(tankAngle-90))));
	        	canon.setPosX((int) (tank.getPosX()+(tank.getAncho()/2-5)+(2*Math.cos(Math.toRadians(tankAngle-90)))));
	        	canon.setPosY((int) (tank.getPosY()-15+(2*Math.sin(Math.toRadians(tankAngle-90)))));
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
	        break;
	        case KeyEvent.VK_S: 
	        	s = true; 
	        	tank.setPosX((float) (tank.getPosX()+2*Math.cos(Math.toRadians(tankAngle+90))));
	        	tank.setPosY((float) (tank.getPosY()+2*Math.sin(Math.toRadians(tankAngle+90))));
	        	canon.setPosX((float) (tank.getPosX()+(tank.getAncho()/2-5)+(2*Math.cos(Math.toRadians(tankAngle+90)))));
	        	canon.setPosY((float) (tank.getPosY()-15+(-2*Math.sin(Math.toRadians(tankAngle+90)))));
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