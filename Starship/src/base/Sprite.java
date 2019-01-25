package base;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

/**
 * @author jesusredondogarcia
 * Clase Sprite. Representa un elemento pintable y colisionable del juego.
 */
public class Sprite {
	
	private BufferedImage buffer;
	private Color color = Color.BLACK;
	//Variables de dimensi贸n
	private int ancho;
	private int alto;
	//Variables de colocaci贸n
	private int posX;
	private int posY;
	//Variables para la velocidad
	private int velocidadX;
	private int velocidadY;
	private String url;
	
	
	/**
	 * Constructor simple para un Sprite sin imagen y sin velocidad.
	 * @param ancho Ancho que ocupa el Sprite (en pixels)
	 * @param alto Altura que ocupa el Sprite (en pixels)
	 * @param posX posici贸n horizontal del sprite en el mundo.
	 * @param posY posici贸n vertical del Sprite en el mundo. El origen se sit煤a en la parte superior.
	 */
	public Sprite(int ancho, int alto, int posX, int posY, String url) {
		this.ancho = ancho;
		this.alto = alto;
		this.posX = posX;
		this.posY = posY;
		this.url = url;
		actualizarBuffer();
	}
	
	
	/**
	 * Constructor para un Sprite sin imagen.
	 * @param ancho Ancho que ocupa el Sprite (en pixels)
	 * @param alto Altura que ocupa el Sprite (en pixels)
	 * @param posX posici贸n horizontal del sprite en el mundo.
	 * @param posY posici贸n vertical del Sprite en el mundo. El origen se sit煤a en la parte superior.
	 * @param velocidadX velocidad horizontal del Sprite.
	 * @param velocidadY velocidad vertical del Sprite. 
	 */
	public Sprite(int ancho, int alto, int posX, int posY, int velocidadX, int velocidadY, String url) {
		this.ancho = ancho;
		this.alto = alto;
		this.posX = posX;
		this.posY = posY;
		this.velocidadX = velocidadX;
		this.velocidadY = velocidadY;
		this.url = url;
		actualizarBuffer();
	}
	
	public Sprite(int ancho, int alto, int posX, int posY, int velocidadY, Color color) {
		this.ancho = ancho;
		this.alto = alto;
		this.posX = posX;
		this.posY = posY;
		this.velocidadY = velocidadY;
		this.color = color;
		actualizarBuffer();
	}
	
	/**
	 * M茅todo para actualizar el buffer que guarda cada Sprite.
	 * Por ahora s贸lo guarda un bufferedImage que est谩 completamente relleno de un color.
	 */
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
	
	/**
	 * M茅todo para mover el Sprite por el mundo.
	 * @param anchoMundo ancho del mundo sobre el que se mueve el Sprite
	 * @param altoMundo alto del mundo sobre el que se mueve el Sprite
	 */
	public void moverSprite(int anchoMundo, int altoMundo){
		if(posX >= anchoMundo - ancho) { //por la derecha
			velocidadX = -1  * Math.abs(velocidadX);
		} 
		if(posX <= 0){//por la izquierda
			velocidadX = Math.abs(velocidadX);;
		}
		if(posY >= altoMundo -alto){//por abajo
			velocidadY = -1  * Math.abs(velocidadY);
		}
		if(posY <= 0){ //Por arriba
			velocidadY = Math.abs(velocidadY);;
		}
		posX = posX + velocidadX;
		posY = posY + velocidadY;
	}
	
	public void moverDisparo() {
		posX = posX + velocidadX;
		posY = posY + velocidadY;
	}
	
	public boolean colisiona(Sprite otro) {

		boolean colisionEjeX = false;
		//緾u醠 m醩 a la izq??:
		if(posX>otro.posX) { //El de de la izquierda es el otro:
			if(otro.getPosX()+otro.getAncho() >= posX) {
				colisionEjeX = true;
			}
		}else { //si no yo soy el de la izq.
			if(posX+ancho >= otro.getPosX()) {
				colisionEjeX = true;
			}
		}
		//EJE Y
		boolean colisionEjeY = false;
		if(posY>otro.posY) { //El de de la izquierda es el otro:
			if(otro.getPosY()+otro.getAlto() >= posY) {
				colisionEjeY = true;
			}
		}else { //si no yo soy el de la izq.
			if(posY+alto >= otro.getPosY()) {
				colisionEjeY = true;
			}
		}
		return colisionEjeX && colisionEjeY;
	}
	
	
	/**
	 * M茅todo que pinta el Sprite en el mundo teniendo en cuenta las caracter铆sticas propias del Sprite.
	 * @param g Es el Graphics del mundo que se utilizar谩 para pintar el Sprite.
	 */
	public void pintarSpriteEnMundo(Graphics g){
		g.drawImage(buffer, posX, posY, null);
	}
	
	public void pintarDisparoEnMundo(Graphics2D g){
		g.rotate(Math.toRadians(30), 50, 50);
		g.drawImage(buffer, posX, posY, null);
	}
	
	
	//M茅todos para obtener:
	public int getAncho(){
		return ancho;
	}
	
	public int getAlto(){
		return alto;
	}
	
	public int getPosX(){
		return posX;
	}
	
	public int getPosY(){
		return posY;
	}
	
	public BufferedImage getBuffer(){
		return buffer;
	}
	
	public int getVelocidadX(){
		return velocidadX;
	}
	
	public int getVelocidadY(){
		return velocidadY;
	}
	
	
	
	//m茅todos para cambiar:
	public void setAncho(int ancho){
		this.ancho = ancho;
	}
	
	public void setAlto(int alto){
		this.alto = alto;
	}
	
	public void setPosX(int posX){
		this.posX = posX;
	}
	
	public void setPosY(int posY){
		this.posY = posY;
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
}