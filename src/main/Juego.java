package main;

//acabar el disparo
import java.applet.Applet;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import enemigos.Seta;
import niveles.Nivel1;
import niveles.Nivel2;
import obstaculos.Obstaculo;
import personaje.Disparo;
import personaje.Vidas;
import personaje.Yoshi;

public class Juego extends Applet implements Runnable {
	public static final int NUM_IMG_YOSHI = 16;
	public static final int NUM_IMG_SETAS = 3;
	public static int NUMVIDAS = 3;

	Thread animacion;
	Image imagen;
	Graphics noseve;
	Nivel1 nivel1;
	Nivel2 nivel2;
	Boolean movimineto = false;
	Yoshi yoshi;
	Image imgYoshi[], fondoNivel1, imgSeta[], imgVida;
	List<Seta> seta;
	List<Vidas> vidas;
	List<Disparo> disparo;
	Obstaculo tuberia;

	public void init() {
		resize(1920, 500);

		imagen = createImage(1920, 1080);
		noseve = imagen.getGraphics();

		fondoNivel1 = getImage(getCodeBase(), "Resources/Levels/fondoLevel1.png");
		nivel1 = new Nivel1(fondoNivel1);

		nivel2 = new Nivel2();

		// Creacion de Yoshi
		imgYoshi = new Image[NUM_IMG_YOSHI];
		for (int i = 0; i < NUM_IMG_YOSHI; i++) {
			imgYoshi[i] = getImage(getCodeBase(), "Resources/Yoshi/yoshi" + (i + 1) + ".png");
		}
		yoshi = new Yoshi(imgYoshi, this, 100, nivel1.height - 150);

		// Creacion de Seta
		seta = new ArrayList<Seta>();
		imgSeta = new Image[NUM_IMG_SETAS];
		for (int i = 0; i < NUM_IMG_SETAS; i++) {
			imgSeta[i] = getImage(getCodeBase(), "Resources/Enemigos/Seta/Seta" + (i + 1) + ".png");
		}
		for (int i = 0; i < 50; i++) {
			seta.add(new Seta(imgSeta, 500 + (500 * i), nivel1.height - 120, this));
		}

		// Creacion de Vidas
		vidas = new ArrayList<Vidas>();
		imgVida = getImage(getCodeBase(), "Resources/Vida/vida.png");
		for (int i = 0; i < NUMVIDAS; i++) {
			vidas.add(new Vidas(imgVida, 1600 + 50 * i, 25, this));
		}

		// creacion de los Disparos
		disparo = new ArrayList<Disparo>();
		disparo.add(new Disparo(yoshi.x + yoshi.width, yoshi.y + 30, this));

	}

	public void start() {
		animacion = new Thread(this);
		animacion.start(); // llama al método run()
	}

	public void paint(Graphics g) {
		noseve.setColor(Color.white);
		noseve.fillRect(0, 0, 1920, 1080);
		nivel1.dibujar(noseve, this);
		// nivel2.dibujar(noseve);

		for (int i = 0; i < seta.size(); i++) {
			seta.get(i).dibujar(noseve);
		}

		for (int i = 0; i < vidas.size(); i++) {
			vidas.get(i).dibujar(noseve);
		}

		yoshi.dibujar(noseve);

		for (int i = 0; i < disparo.size(); i++) {
			disparo.get(i).dibujar(noseve);
		}
		g.drawImage(imagen, 0, 0, this);
	}

	public void update(Graphics g) {
		paint(g);
	}

	public void run() {
		while (true) {
			for (int i = 0; i < seta.size(); i++) {
				seta.get(i).actualizar();
			}
			yoshi.caida();
			try {
				for (int i = 0; i < seta.size(); i++) {
					if (yoshi.intersects(seta.get(i))) {
						seta.remove(i);
						vidas.remove(NUMVIDAS - 1);
						NUMVIDAS--;
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
			}

			for (int i = 0; i < disparo.size(); i++) {
				disparo.get(i).actualizar();
				if (disparo.get(i).intersects(seta.get(i))) {
					seta.remove(i);
					disparo.remove(i);
				}
			}

			repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}

		}
	}

	public boolean keyDown(Event ev, int tecla) {
		switch (tecla) {
		case 100:
			yoshi.izquierda = false;
			yoshi.derecha = true;
			yoshi.actualizar();
			nivel1.derecha = true;
			nivel1.izquierda = false;
			for (int i = 0; i < seta.size(); i++) {
				seta.get(i).derecha = true;
				seta.get(i).izquierda = false;
				seta.get(i).velx = 10;
			}
			nivel1.actualizar();

			break;
		case 97:
			yoshi.derecha = false;
			yoshi.izquierda = true;
			nivel1.derecha = false;
			nivel1.izquierda = true;
			for (int i = 0; i < seta.size(); i++) {
				seta.get(i).derecha = false;
				seta.get(i).izquierda = true;
			}
			yoshi.actualizar();
			nivel1.actualizar();
			break;
		case 119:
			yoshi.salto();
			break;
		}
		repaint();
		return true;
	}

	public boolean keyUp(Event ev, int tecla) {
		switch (tecla) {
		case 100:
			for (int i = 0; i < seta.size(); i++) {
				seta.get(i).velx = 5;
			}
			yoshi.derecha = false;
			break;
		}
		repaint();
		return true;
	}

}
