package base;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

public class Sprite2D implements ImageObserver {
	
	private BufferedImage buffer;
	private Color color = Color.BLACK;
	private int ancho;
	private int alto;
	private float posX;
	private float posY;
	private int velocidadX;
	private int velocidadY;
	private String url;
	private AffineTransform transform = new AffineTransform();

	public Sprite2D(int ancho, int alto, int posX, int posY, String url) {
		this.ancho = ancho;
		this.alto = alto;
		this.posX = posX;
		this.posY = posY;
		this.url = url;
		actualizarBuffer();
	}

	public Sprite2D(int ancho, int alto, int posX, int posY, int velocidadX, int velocidadY, String url) {
		this.ancho = ancho;
		this.alto = alto;
		this.posX = posX;
		this.posY = posY;
		this.velocidadX = velocidadX;
		this.velocidadY = velocidadY;
		this.url = url;
		actualizarBuffer();
	}
	
	public Sprite2D(int ancho, int alto, int posX, int posY, int velocidadY, Color color) {
		this.ancho = ancho;
		this.alto = alto;
		this.posX = posX;
		this.posY = posY;
		this.velocidadY = velocidadY;
		this.color = color;
		actualizarBuffer();
	}
	
	public void actualizarBuffer(){
		buffer = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
		Graphics g = buffer.getGraphics();
		
		try {
			BufferedImage sprite = ImageIO.read(new File(url));
			g.drawImage(sprite.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH), 0, 0, null);
		} catch (Exception e) {
			g.setColor(color);
			g.fillRect(0, 0, ancho, alto);
			g.dispose();
		}
	}

	public void moverSprite(int anchoMundo, int altoMundo){
		if(posX >= anchoMundo - ancho) {
			velocidadX = -1  * Math.abs(velocidadX);
		} 
		if(posX <= 0){
			velocidadX = Math.abs(velocidadX);;
		}
		if(posY >= altoMundo -alto){
			velocidadY = -1  * Math.abs(velocidadY);
		}
		if(posY <= 0){
			velocidadY = Math.abs(velocidadY);;
		}
		posX = (float) (posX + velocidadX);
		posY = (float) (posY + velocidadY);
	}
	
	public void moverSpriteCanon(int anchoMundo, int altoMundo){
		if(posX >= anchoMundo -20 - ancho) {
			velocidadX = -1  * Math.abs(velocidadX);
		} 
		if(posX <= 20){
			velocidadX = Math.abs(velocidadX);;
		}
		if(posY >= altoMundo -25 -alto){
			velocidadY = -1  * Math.abs(velocidadY);
		}
		if(posY <= 25){
			velocidadY = Math.abs(velocidadY);;
		}
		posX = (float) (posX + velocidadX);
		posY = (float) (posY + velocidadY);
	}
	
	public void moverDisparo() {
		posX = (float) (posX + velocidadX);
		posY = (float) (posY + velocidadY);
	}
	
	public boolean colisiona(Sprite2D otro) {

		boolean colisionEjeX = false;
		if(posX>otro.posX) {
			if(otro.getPosX()+otro.getAncho() >= posX) {
				colisionEjeX = true;
			}
		}else {
			if(posX+ancho >= otro.getPosX()) {
				colisionEjeX = true;
			}
		}
		boolean colisionEjeY = false;
		if(posY>otro.posY) {
			if(otro.getPosY()+otro.getAlto() >= posY) {
				colisionEjeY = true;
			}
		}else {
			if(posY+alto >= otro.getPosY()) {
				colisionEjeY = true;
			}
		}
		return colisionEjeX && colisionEjeY;
	}
	
	public void pintarSpriteEnMundo(Graphics g, int angulo){
		Graphics2D g2d = (Graphics2D) g;
		transform.setToTranslation(posX, posY);
		transform.rotate(Math.toRadians(angulo),getAncho()/2,getAlto()/2);
		g2d.drawImage(buffer, transform, this);
	}
	
	public void pintarSpriteEnMundo2(Graphics g, int angulo){
		Graphics2D g2d = (Graphics2D) g;
		transform.setToTranslation(posX, posY);
		transform.rotate(Math.toRadians(angulo),getAncho()/2,getAlto());
		g2d.drawImage(buffer, transform, this);
	}
	
	public void pintarDisparoEnMundo(Graphics2D g){
		g.rotate(Math.toRadians(30), 50, 50);
		g.drawImage(buffer, (int)posX, (int)posY, null);
	}
	
	public int getAncho(){
		return ancho;
	}
	
	public int getAlto(){
		return alto;
	}
	
	public float getPosX(){
		return posX;
	}
	
	public float getPosY(){
		return posY;
	}
	
	public BufferedImage getBuffer(){
		return buffer;
	}
	
	public double getVelocidadX(){
		return velocidadX;
	}
	
	public double getVelocidadY(){
		return velocidadY;
	}
	
	public void setAncho(int ancho){
		this.ancho = ancho;
	}
	
	public void setAlto(int alto){
		this.alto = alto;
	}
	
	public void setPosX(float f){
		this.posX = f;
	}
	
	public void setPosY(float f){
		this.posY = f;
	}
	
	public void setBuffer(BufferedImage buffer){
		this.buffer = buffer;
	}
	
	public void setVelocidadX(int velocidadX){
		this.velocidadX = velocidadX;
	}
	
	public void setAceleracionY(int velocidadY){
		this.velocidadY = velocidadY;
	}

	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}


	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return false;
	}
}