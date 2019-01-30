package pantallas;

import java.awt.Color;
import java.awt.Font;
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

/**
 * Clase PantallaJuego. En ella se muestra la pantalla de juego principal.
 * @author jorgeramosgil
 * @version 1.0
 * @since 1.0
 */
public class PantallaJuego implements IPantalla, KeyListener {
	
	private int tankAngle = 0;
	private int canonAngle = 0;
	private int tankEnemyAngle = -90;
	private int canonEnemyAngle = -90;
	private int puntuacion = 0;
	private int puntuacionEnemigo = 0;
	
	//Para controlar el tiempo que pasa entre disparo y disparo del enemigo
	private float timerCurrent = 0;
	private float timerTotal = 5;
	
	PanelJuego panelJuego;
	
	//Parte de arriba del mapa
	BufferedImage image1;
	Image rescaledImage1;
	
	//Parte de abajo del mapa
	BufferedImage image2;
	Image rescaledImage2;
	
	Sprite2D tank;
	Sprite2D canon;
	Sprite2D disparo;
	
	Sprite2D tankEnemigo;
	Sprite2D canonEnemigo;
	Sprite2D disparoEnemigo;
	
	public PantallaJuego(PanelJuego panelJuego) {
		this.panelJuego = panelJuego;
		panelJuego.addKeyListener(this);
	}
	
	/**
	 * Método que inicializa todos los sprites
	 */
	@Override
	public void inicializarPantalla() {
		tank = new Sprite2D(50, 50, 50, 500, "Recursos/PNG/Tanks/tankGreen.png");
		canon = new Sprite2D(10, 40, (int)tank.getPosX()+(tank.getAncho()/2-5), (int)tank.getPosY()-15, "Recursos/PNG/Tanks/barrelGreen_outline.png");
		tankEnemigo = new Sprite2D(50, 50, 700, 50, -2, 0, "Recursos/PNG/Tanks/tankRed.png");
		canonEnemigo = new Sprite2D(10, 40, (int)tankEnemigo.getPosX()+(tankEnemigo.getAncho()/2-5), (int)tankEnemigo.getPosY()-15, -2, 0, "Recursos/PNG/Tanks/barrelRed_outline.png");
		try {
			image1 = ImageIO.read(new File("Recursos/PNG/Environment/sand.png"));
			image2 = ImageIO.read(new File("Recursos/PNG/Environment/grass.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		rescaleImage();
	}

	/**
	 * Método que pinta por pantalla todos los sprites. Además de las puntuaciones y el tiempo del disparo del enemigo
	 */
	@Override
	public void pintarPantalla(Graphics g) {
		rellenarFondo(g);
		if (disparo != null) {
			disparo.pintarSpriteEnMundo(g, canonAngle);
		}
		if (disparoEnemigo != null) {
			disparoEnemigo.pintarSpriteEnMundo(g, canonEnemyAngle);
		}
		tank.pintarSpriteEnMundo(g,tankAngle);
		canon.pintarSpriteEnMundo2(g,canonAngle);
		tankEnemigo.pintarSpriteEnMundo(g,tankEnemyAngle);
		canonEnemyAngle = (int) Math.toDegrees(Math.atan2((tankEnemigo.getPosX()+tankEnemigo.getAncho()/2)-(tank.getPosX()+tank.getAncho()/2),-((tankEnemigo.getPosY()+tankEnemigo.getAlto()/2)-(tank.getPosY()+tank.getAlto()/2))))+180;
		canonEnemigo.pintarSpriteEnMundo2(g,canonEnemyAngle);
		g.setColor(new Color(255, 0, 0, 255));
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("Puntuación: "+Integer.toString(puntuacion), panelJuego.getWidth()-150, panelJuego.getHeight()-20);
		g.drawString("Puntuación: "+Integer.toString(puntuacionEnemigo), 20, 20);
		timerCurrent+=0.02;
		if (timerCurrent >= timerTotal) {
			if (disparoEnemigo == null) {
				disparoEnemigo = new Sprite2D(16, 40, (int)tankEnemigo.getPosX()+tankEnemigo.getAncho()/2-8, (int)tankEnemigo.getPosY()+tankEnemigo.getAlto()/2-20, (int)(10*Math.cos(Math.toRadians(canonEnemyAngle-90))), (int)(10*Math.sin(Math.toRadians(canonEnemyAngle-90))), "Recursos/PNG/Bullets/bulletRed.png");
			}
			timerCurrent -= timerTotal;
		}
	}

	/**
	 * Método que llama a esos métodos en cada frame
	 */
	@Override
	public void ejecutarFrame() {
		checkCollisions();
		moverSprites();
	}

	/**
	 * Método que que cambia el ángulo del cañón siguiendo al ratón
	 */
	@Override
	public void moverRaton(MouseEvent e) {
		canonAngle = (int) Math.toDegrees(Math.atan2(e.getX()-tank.getPosX(),-(e.getY()-tank.getPosY())));
	}

	/**
	 * Método que permite disparar cuando se hace click
	 */
	@Override
	public void pulsarRaton(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e)){
			if (disparo == null) {
				disparo = new Sprite2D(16, 40, (int)tank.getPosX()+tank.getAncho()/2-8, (int)tank.getPosY()+tank.getAlto()/2-20, (int)(10*Math.cos(Math.toRadians(canonAngle-90))), (int)(10*Math.sin(Math.toRadians(canonAngle-90))), "Recursos/PNG/Bullets/bulletGreen.png");
			}
			
		}
	}

	/**
	 * Método que redimensiona la pantalla
	 */
	@Override
	public void redimensionarPantalla(ComponentEvent e) {
		redimensionarPantalla(e);
		
	}

	/**
	 * Método que pinta los dos fondos de pantalla
	 * @param g
	 */
	private void rellenarFondo(Graphics g){
		g.drawImage(rescaledImage2, 0, 0, null);
		g.drawImage(rescaledImage1, 0, panelJuego.getHeight()/2, null);
	}
	/**
	 * Método que mueve al tanque enemigo y comprueba si los disparos han salido de los límites
	 */
	private void moverSprites(){
		
		tankEnemigo.moverSprite(panelJuego.getWidth(), panelJuego.getHeight());
		canonEnemigo.moverSpriteCanon(panelJuego.getWidth(), panelJuego.getHeight());
		if (disparo != null) {
			disparo.moverDisparo();
			if (disparo.getPosY()+disparo.getAlto() <= 0) {
				disparo = null;
			} else if (disparo.getPosY() >= 600) {
				disparo = null;
			} else if (disparo.getPosX()+disparo.getAncho() <= 0) {
				disparo = null;
			} else if (disparo.getPosX() >= 800) {
				disparo = null;
			}
		}
		
		if (disparoEnemigo != null) {
			disparoEnemigo.moverDisparo();
			if (disparoEnemigo.getPosY()+disparoEnemigo.getAlto() <= 0) {
				disparoEnemigo = null;
			} else if (disparoEnemigo.getPosY() >= 600) {
				disparoEnemigo = null;
			} else if (disparoEnemigo.getPosX()+disparoEnemigo.getAncho() <= 0) {
				disparoEnemigo = null;
			} else if (disparoEnemigo.getPosX() >= 800) {
				disparoEnemigo = null;
			}
		}
	}
	
	/**
	 * Método que comprueba las colisiones con los distintos sprites
	 */
	private void checkCollisions() {
		if (disparo != null) {
			if (tankEnemigo.colisiona(disparo)) {
				disparo = null;
				puntuacion++;
				if (puntuacion == 5) {
					PantallaCongratulations pantallaCongratulations = new PantallaCongratulations(panelJuego);
					pantallaCongratulations.inicializarPantalla();
					panelJuego.setPantallaActual(pantallaCongratulations);
				}
			}
		}
		
		if (disparoEnemigo != null) {
			if (tank.colisiona(disparoEnemigo)) {
				disparoEnemigo = null;
				puntuacionEnemigo++;
				if (puntuacionEnemigo == 5) {
					PantallaGameOver pantallaGameOver = new PantallaGameOver(panelJuego);
					pantallaGameOver.inicializarPantalla();
					panelJuego.setPantallaActual(pantallaGameOver);
				}
			}
		}
		
		if (disparoEnemigo != null && disparo != null) {
			if (disparo.colisiona(disparoEnemigo)) {
				disparoEnemigo = null;
				disparo = null;
			}
		}
	}
	
	/**
	 * Método que reescala los fondos al tamaño que queramos
	 */
	public void rescaleImage() {
		rescaledImage1 = image1.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight()/2, Image.SCALE_SMOOTH);
		rescaledImage2 = image2.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight()/2, Image.SCALE_SMOOTH);
	}

	/**
	 * Método que mueve y rota el tanque conforme pulsamos las teclas
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
	        case KeyEvent.VK_W: 
	        	tank.setPosX((float) (tank.getPosX()+2*Math.cos(Math.toRadians(tankAngle-90))));
	        	tank.setPosY((float) (tank.getPosY()+2*Math.sin(Math.toRadians(tankAngle-90))));
	        	canon.setPosX((int) (tank.getPosX()+(tank.getAncho()/2-5)+(2*Math.cos(Math.toRadians(tankAngle-90)))));
	        	canon.setPosY((int) (tank.getPosY()-15+(2*Math.sin(Math.toRadians(tankAngle-90)))));
	        break;
	        case KeyEvent.VK_A: 
	        	if (tankAngle == 0) {
	        		tankAngle = 360;
	        	}
	        	tankAngle--;
	        break;
	        case KeyEvent.VK_S: 
	        	tank.setPosX((float) (tank.getPosX()+2*Math.cos(Math.toRadians(tankAngle+90))));
	        	tank.setPosY((float) (tank.getPosY()+2*Math.sin(Math.toRadians(tankAngle+90))));
	        	canon.setPosX((float) (tank.getPosX()+(tank.getAncho()/2-5)+(2*Math.cos(Math.toRadians(tankAngle+90)))));
	        	canon.setPosY((float) (tank.getPosY()-15+(-2*Math.sin(Math.toRadians(tankAngle+90)))));
	        break;
	        case KeyEvent.VK_D:
	        	if (tankAngle == 360) {
	        		tankAngle = 0;
	        	}
	        	tankAngle++;
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