package base;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import pantallas.PantallaInicial;

public class PanelJuego extends JPanel implements Runnable, MouseListener, ComponentListener, MouseMotionListener{

	private static final long serialVersionUID = 1L;
		
	IPantalla pantallaActual;

	public PanelJuego(){
		
		this.setFocusable(true);
		this.addComponentListener(this);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		new Thread(this).start();
		pantallaActual = new PantallaInicial(this);
		pantallaActual.inicializarPantalla();
      }

	@Override
	public void paintComponent(Graphics g){
		pantallaActual.pintarPantalla(g);
	}

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
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		pantallaActual.pulsarRaton(e);
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		
	}


	@Override
	public void componentHidden(ComponentEvent e) {
		
	}


	@Override
	public void componentMoved(ComponentEvent e) {
		
	}


	@Override
	public void componentResized(ComponentEvent e) {
		pantallaActual.redimensionarPantalla(e);
	}


	@Override
	public void componentShown(ComponentEvent e) {
		
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		pantallaActual.moverRaton(e);
	}


	public IPantalla getPantallaActual() {
		return pantallaActual;
	}


	public void setPantallaActual(IPantalla pantallaActual) {
		this.pantallaActual = pantallaActual;
	}
}