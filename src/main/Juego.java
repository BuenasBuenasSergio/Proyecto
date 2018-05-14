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
import niveles.NivelBoss;
import obstaculos.Obstaculo;
import personaje.Disparo;
import personaje.PowerUP;
import personaje.Vidas;
import personaje.Yoshi;

public class Juego extends Applet implements Runnable {
	public static final int NUM_IMG_YOSHI = 16;
	public static final int NUM_IMG_SETAS = 3;
	public static final int NUM_POWER_UP = 6;
	public static int NUMVIDAS = 3;
	int NUM_DISPAROS = 3;

	Thread animacion;
	Image imagen;
	Graphics noseve;
	Nivel1 nivel1;
	Nivel2 nivel2;
	NivelBoss nivelBoss;
	Boolean movimineto = false;
	Yoshi yoshi;
	Image imgYoshi[], fondoNivel1, fondoNivel2, fondoNivelBoss, imgSeta[], imgVida, imgDisparo, imgPowerUP, fin;
	List<Seta> seta;
	List<Vidas> vidas;
	List<Disparo> disparo;
	List<Disparo> cargador;
	List<PowerUP> powerUp;
	Obstaculo tuberia;
	boolean vivo = true;
	boolean level1 = true;
	boolean level2 = false;
	boolean levelBoss = false;

	public void init() {
		resize(1920, 500);

		imagen = createImage(1920, 1080);
		noseve = imagen.getGraphics();

		// Creacion de Niveles
		fondoNivel1 = getImage(getCodeBase(), "Resources/Levels/fondoNivel1.png");
		nivel1 = new Nivel1(fondoNivel1);

		fondoNivel2 = getImage(getCodeBase(), "Resources/Levels/fondoNivel2.png");
		nivel2 = new Nivel2(fondoNivel2);

		fondoNivelBoss = getImage(getCodeBase(), "Resources/Levels/fondoBoss.png");
		nivelBoss = new NivelBoss(fondoNivelBoss);

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

		// creacion de los Disparos y cargador
		imgDisparo = getImage(getCodeBase(), "Resources/Huevo/Huevo.png");
		disparo = new ArrayList<Disparo>();
		cargador = new ArrayList<Disparo>();
		for (int i = 0; i < NUM_DISPAROS; i++) {
			cargador.add(new Disparo(imgDisparo, 200 + (50 * i), 50, this));
		}

		// creacion de los PowerUP
		powerUp = new ArrayList<PowerUP>();
		imgPowerUP = getImage(getCodeBase(), "Resources/Power/Power.png");
		for (int i = 0; i < NUM_POWER_UP; i++) {
			powerUp.add(new PowerUP(imgPowerUP, 800 + (1370 * i), yoshi.y + yoshi.height, this));
		}
		// fin
		fin = getImage(getCodeBase(), "Resources/Muerte/Fin.png");
	}

	public void start() {
		animacion = new Thread(this);
		animacion.start();
	}

	public void paint(Graphics g) {
		noseve.setColor(Color.white);
		noseve.fillRect(0, 0, 1920, 1080);
		if (level1) {
			nivel1.dibujar(noseve, this);
		} else if (level2) {
			nivel2.dibujar(noseve, this);
		} else if (levelBoss) {
			nivelBoss.dibujar(noseve, this);
		}

		// nivel2.dibujar(noseve);

		for (int i = 0; i < seta.size(); i++) {
			seta.get(i).dibujar(noseve);
		}

		for (int i = 0; i < vidas.size(); i++) {
			vidas.get(i).dibujar(noseve);
		}

		for (int i = 0; i < disparo.size(); i++) {
			disparo.get(i).dibujar(noseve);
		}

		for (int i = 0; i < NUM_DISPAROS; i++) {
			cargador.get(i).dibujar(noseve);
		}

		for (int i = 0; i < powerUp.size(); i++) {
			powerUp.get(i).dibujar(noseve);
		}
		if (vivo == false) {
			noseve.setColor(Color.red);
			animacion.stop();
			noseve.drawImage(fin, 700, 100, 350, 100, this);
		}
		yoshi.dibujar(noseve);

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

			/*
			 * try { for (int i = 0; i < seta.size(); i++) { if
			 * (yoshi.intersects(seta.get(i))) { seta.remove(i); vidas.remove(NUMVIDAS - 1);
			 * NUMVIDAS--; } }
			 * 
			 * } catch (Exception e) { // TODO: handle exception }
			 */

			for (int i = 0; i < disparo.size(); i++) {
				disparo.get(i).actualizar();
				if (disparo.get(i).intersects(seta.get(i))) {
					seta.remove(i);
					disparo.remove(i);
				}
			}

			for (int i = 0; i < powerUp.size(); i++) {
				powerUp.get(i).actualizar();
				if (powerUp.get(i).intersects(yoshi)) {
					if (NUM_DISPAROS < 3) {
						powerUp.remove(i);
						NUM_DISPAROS += 1;
					}
				}
			}
			if (NUMVIDAS == 0) {
				vivo = false;
			}
			if ((yoshi.x == 1920) && (level1)) {
				level1 = false;
				level2 = true;
				yoshi.x = 50;
			} else if ((yoshi.x == 1920) && (level2)) {
				level2 = false;
				levelBoss = true;
				yoshi.x = 50;
			}
			repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
		}
	}

	public boolean keyDown(Event ev, int tecla) {
		if (vivo) {
			switch (tecla) {
			case 100:
				yoshi.izquierda = false;
				yoshi.derecha = true;
				yoshi.actualizar();

				nivel1.derecha = true;
				nivel1.izquierda = false;
				nivel1.actualizar();

				for (int i = 0; i < seta.size(); i++) {
					seta.get(i).derecha = true;
					seta.get(i).izquierda = false;
					seta.get(i).velx = 10;
				}
				break;
			case 97:
				yoshi.derecha = false;
				yoshi.izquierda = true;
				yoshi.actualizar();

				nivel1.derecha = false;
				nivel1.izquierda = true;
				nivel1.actualizar();

				for (int i = 0; i < seta.size(); i++) {
					seta.get(i).derecha = false;
					seta.get(i).izquierda = true;
				}
				break;
			case 119:
				yoshi.salto();
				break;
			case 32:

				if (NUM_DISPAROS > 0) {
					disparo.add(new Disparo(imgDisparo, yoshi.x + yoshi.width, yoshi.y + 30, this));
					NUM_DISPAROS -= 1;

				}
			}
		}
		repaint();
		return true;
	}

	public boolean keyUp(Event ev, int tecla) {
		if (vivo) {
			switch (tecla) {
			case 100:
				for (int i = 0; i < seta.size(); i++) {
					seta.get(i).velx = 5;
				}
				yoshi.derecha = false;
				break;
			}

		}
		repaint();
		return true;
	}
}