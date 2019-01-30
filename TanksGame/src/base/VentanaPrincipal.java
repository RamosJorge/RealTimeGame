package base;

import java.awt.GridLayout;

import javax.swing.JFrame;

/**
 * Clase VentanaPrincipal. En ella se pinta el juego.
 * @author jorgeramosgil
 * @version 1.0
 * @since 1.0
 */
public class VentanaPrincipal {

	final int anchoLienzo = 300;
	final int altoLienzo = 300;
	
	JFrame ventana;
	PanelJuego panelJuego;
	
	public VentanaPrincipal() {
		ventana = new JFrame();
		ventana.setBounds(0, 0, 800, 600);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setResizable(false);
	}
	
	/**
	 * Método que inicializa todos los componentes de la ventana
	 */
	public void inicializarComponentes(){
		ventana.setLayout(new GridLayout(1,1));
		panelJuego = new PanelJuego();
		ventana.add(panelJuego);
	}
	
	/**
	 * Método que realiza todas las llamadas necesarias para inicializar la ventana correctamente.
	 */
	public void inicializar(){
		ventana.setVisible(true);
		inicializarComponentes();	
	}
}