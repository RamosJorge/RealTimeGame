package base;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import pantallas.PantallaInicial;


/**
 * 
 * @author jesusredondogarcia
 * Clase PanelJuego. Controla los gráficos del Juego. Por ahora también controla la lógica del Juego.
 * Extiende de JPanel. Todos los gráficos se gestionan mediante los gráficos de un JPanel.
 * Implementa Runnable porque en el constructor se lanza un hilo que permite actualizar el Juego periódicamente.
 * Implementa MouseListener para que pueda capturar las pulsaciones del ratón.
 */
public class PanelJuego extends JPanel implements Runnable, MouseListener, ComponentListener, MouseMotionListener{

	private static final long serialVersionUID = 1L;
	
	public int puntuacion = 0;
	
	IPantalla pantallaActual;
	
	/**
	 * Constructor de PanelJuego.
	 * - Inicializa el arrayList de cuadrados.
	 * - Asigna el mouse listener que implementa la propia clase para lanazar nuevos cuadrados.
	 * - Inicia un hilo para actualizar el juego periódicamente.
	 */
	public PanelJuego(){
		
		this.setFocusable(true);
		this.addComponentListener(this);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		new Thread(this).start();
		pantallaActual = new PantallaInicial(this);
		pantallaActual.inicializarPantalla();
      }

	
	/**
	 * Sobreescritura del método paintComponent. Este método se llama automáticamente cuando se inicia el componente,
	 *  se redimensiona o bien cuando se llama al método "repaint()". Nunca llamarlo directamente.
	 * @param g Es un Graphics que nos proveé JPanel para poner pintar el componente a nuestro antojo.
	 */
	@Override
	public void paintComponent(Graphics g){
		pantallaActual.pintarPantalla(g);
	}

	/**
	 * Método que se utiliza para rellenar el fondo del JPanel. 
	 * @param g Es el gráficos sobre el que vamos a pintar el fondo.
	 */
	

	
	/**
	 * Método para mover todos los Sprites del juego.
	 */
	
	

	@Override
	public void run() {
		while(true){
			repaint();
			try {	Thread.sleep(25);	} catch (InterruptedException e) {e.printStackTrace();}
			pantallaActual.ejecutarFrame();
			Toolkit.getDefaultToolkit().sync();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}


	@Override
	public void mousePressed(MouseEvent e) {
		//Creamos un sprite Random cuando se pulsa el botón:
		pantallaActual.pulsarRaton(e);
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		pantallaActual.redimensionarPantalla(e);
	}


	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		pantallaActual.moverRaton(e);
	}


	public IPantalla getPantallaActual() {
		return pantallaActual;
	}


	public void setPantallaActual(IPantalla pantallaActual) {
		this.pantallaActual = pantallaActual;
	}
	
}