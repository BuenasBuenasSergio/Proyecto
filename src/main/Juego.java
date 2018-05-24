package main;

//Falta hacer los disparos de Bowser, poner la banderita del fin de nivel e implementar los sonidos y editar la imagen de bowser
//Musica de fondo es .loop;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Button;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import enemigos.Boss;
import enemigos.DisparoBoss;
import enemigos.Goomba;
import enemigos.Seta;
import niveles.InitLevel;
import niveles.Nivel1;
import niveles.Nivel2;
import niveles.NivelBoss;
import personaje.Disparo;
import personaje.PowerUP;
import personaje.Vidas;
import personaje.Yoshi;

public class Juego extends Applet implements Runnable {
	public static final int NUM_IMG_YOSHI = 16;
	public static final int NUM_IMG_SETAS = 3;
	public static final int NUM_IMG_GOOMBA = 3;
	public static final int NUM_IMG_BOSS = 3;
	public static final int NUM_POWER_UP = 40;
	public static int NUMVIDAS = 3;
	public static int VIDAS_BOSS = 3;
	int NUM_DISPAROS = 5;
	int tiempoDisparoBoss = 0;
	Thread animacion;
	Image imagen;
	Graphics noseve;
	InitLevel Initlevel;
	Nivel1 nivel1;
	Nivel2 nivel2;
	NivelBoss nivelBoss;
	Boolean movimineto = false;
	Yoshi yoshi;
	Image imgYoshi[], fondoInit, fondoNivel1, fondoNivel2, fondoNivelBoss, imgSeta[], imgGoomba[], imgVida, imgDisparo,
			imgPowerUP, dead, victory, imgBoss[], imgDisparoBoss;
	List<Seta> setaNV1;
	List<Seta> setaNV2;
	List<Goomba> goomba;
	List<Vidas> vidas;
	Boss boss;
	List<Disparo> disparo;
	List<Disparo> cargador;
	List<PowerUP> powerUp;
	List<DisparoBoss> disparosBoss;
	Button start;

	boolean vivo = true;
	boolean initlevel = true;
	boolean level1 = false;
	boolean level2 = false;
	boolean levelBoss = false;

	AudioClip initSong, songLevell, songLevel2, songLevelBoss, jumpSound, yoshiShootSound, yoshiHit, bowserShoot;

	public void init() {
		resize(1920, 500);

		imagen = createImage(1920, 1080);
		noseve = imagen.getGraphics();

		createLevels();

		// Creacion de Yoshi
		createYoshi();

		// creacion Enemigos Nivel1
		crateEnemiesLevel1();
		// Creacion Nivel 2
		createEnemiesLevel2();

		// Creacion Boss
		createBoss();

		// Creacion de Vidas
		createLives();

		// creacion de los Disparos y cargador
		createShootAndMag();

		// creacion de los PowerUP que recargan los tiros
		createReloadMag();

		// Crear Imagenes Fnal Juego
		createEndGame();

		// creacion de tiros de boss
		imgDisparoBoss = getImage(getCodeBase(), "Resources/Bowser/Disparo/Proyectil.png");
		disparosBoss = new ArrayList<DisparoBoss>();

		// Cargar Sonidos
		createSounds();

		initSong.loop();
	}

	public void start() {
		animacion = new Thread(this);
		animacion.start();
	}

	public void paint(Graphics g) {
		noseve.setColor(Color.white);
		noseve.fillRect(0, 0, 1920, 1080);
		// pintar niveles
		paintLevels();
		if (initlevel == false) {
			// dibujar vidas
			paintLives();

			// dibujar Tiros y cargador(Huevos de Arriba)
			paintShootsAndMag();

			// Dibujar setas recargan cargador
			paintMagBullets();

			noseve.drawImage(imgDisparoBoss, 200, 200, 70, 40, this);

			// dibujar yoshi
			yoshi.dibujar(noseve);
		}
		// pantalla fin de juego
		paintEndGame();

		g.drawImage(imagen, 0, 0, this);
	}

	public void update(Graphics g) {
		paint(g);
	}

	public void run() {
		while (true) {
			// Salto de yoshi
			yoshi.caida();

			// Movimiento Enemigo;
			enemiesMove();

			// Enemigos golpeando a yoshi

			enemiesHittingYoshi();

			// Disparos

			yoshiHitShooting();

			// Recarga
			reloadShootYoshi();

			// disparos de bowser
			if (levelBoss) {
				tiempoDisparoBoss++;
				if (tiempoDisparoBoss % 50 == 0) {
					disparosBoss.add(new DisparoBoss(imgDisparoBoss, boss.x, yoshi.y + 30, this));
					tiempoDisparoBoss = 0;
					bowserShoot.play();
				}
				for (int i = 0; i < disparo.size(); i++) {
					for (int j = 0; j < disparosBoss.size(); j++) {
						if (disparo.get(i).intersects(disparosBoss.get(j))) {
							disparo.remove(i);
							disparosBoss.remove(j);
						}
					}
				}
			}
			// Fin del Juego
			if (NUMVIDAS == 0) {
				vivo = false;
			}

			// cambios de Nivel
			levelChange();

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
				levelsMoveRigth();

				break;
			case 97:
				yoshi.derecha = false;
				yoshi.izquierda = true;
				yoshi.actualizar();
				levelsMoveLeft();

				break;
			case 119:
				yoshi.salto();
				jumpSound.play();
				break;
			case 32:
				yoshiShoot();
				break;
			}
		}
		repaint();
		return true;
	}

	public boolean keyUp(Event ev, int tecla) {
		if (vivo) {
			switch (tecla) {
			case 100:
				for (int i = 0; i < setaNV1.size(); i++) {
					setaNV1.get(i).velx = 5;
				}
				yoshi.derecha = false;
				break;
			case 97:
				yoshi.izquierda = false;
			}
		}
		repaint();
		return true;
	}

	public boolean action(Event ev, Object obj) {
		if (ev.target == start) {
			level1 = true;
			initSong.stop();
			songLevell.loop();
			initlevel = false;

		}
		return true;
	}
	// Creaciones de elementos

	public void createLevels() {
		start = new Button("Start");
		this.add(start);
		fondoInit = getImage(getCodeBase(), "Resources/Levels/portada.jpg");
		Initlevel = new InitLevel(fondoInit);

		// Creacion de Niveles
		fondoNivel1 = getImage(getCodeBase(), "Resources/Levels/fondoNivel1.png");
		nivel1 = new Nivel1(fondoNivel1);
		// nivel 2
		fondoNivel2 = getImage(getCodeBase(), "Resources/Levels/fondoNivel2.png");
		nivel2 = new Nivel2(fondoNivel2);
		// nivel Boss
		fondoNivelBoss = getImage(getCodeBase(), "Resources/Levels/fondoBoss.png");
		nivelBoss = new NivelBoss(fondoNivelBoss);
	}

	public void createYoshi() {
		imgYoshi = new Image[NUM_IMG_YOSHI];
		for (int i = 0; i < NUM_IMG_YOSHI; i++) {
			imgYoshi[i] = getImage(getCodeBase(), "Resources/Yoshi/yoshi" + (i + 1) + ".png");
		}
		yoshi = new Yoshi(imgYoshi, this, 100, nivel1.height - 150);
	}

	public void crateEnemiesLevel1() {
		// Creacion de Seta Nivel1
		setaNV1 = new ArrayList<Seta>();
		imgSeta = new Image[NUM_IMG_SETAS];
		for (int i = 0; i < NUM_IMG_SETAS; i++) {
			imgSeta[i] = getImage(getCodeBase(), "Resources/Enemigos/Seta/Seta" + (i + 1) + ".png");
		}
		for (int i = 0; i < 50; i++) {
			setaNV1.add(new Seta(imgSeta, 500 + (500 * i), nivel1.height - 120, this));
		}
	}

	public void createEnemiesLevel2() {
		// Creacion de Goomba NV2
		goomba = new ArrayList<Goomba>();
		imgGoomba = new Image[NUM_IMG_GOOMBA];
		for (int i = 0; i < NUM_IMG_GOOMBA; i++) {
			imgGoomba[i] = getImage(getCodeBase(), "Resources/Enemigos/Goomba/Gomba" + (i + 1) + ".png");
		}
		for (int i = 0; i < 50; i++) {
			goomba.add(new Goomba(imgGoomba, -1000 - (700 * i), nivel1.height - 120, this));
		}

		// Creacion de Seta Nivel2
		setaNV2 = new ArrayList<Seta>();
		imgSeta = new Image[NUM_IMG_SETAS];
		for (int i = 0; i < NUM_IMG_SETAS; i++) {
			imgSeta[i] = getImage(getCodeBase(), "Resources/Enemigos/Seta/Seta" + (i + 1) + ".png");
		}
		for (int i = 0; i < 50; i++) {
			setaNV2.add(new Seta(imgSeta, 800 + (900 * i), nivel1.height - 120, this));
		}
	}

	public void createBoss() {
		imgBoss = new Image[NUM_IMG_BOSS];
		for (int i = 0; i < imgBoss.length; i++) {
			imgBoss[i] = getImage(getCodeBase(), "Resources/Bowser/Bowser" + (i + 1) + ".png");
		}
		boss = new Boss(imgBoss, 1200, 300, this);
	}

	public void createLives() {
		vidas = new ArrayList<Vidas>();
		imgVida = getImage(getCodeBase(), "Resources/Vida/vida.png");
		for (int i = 0; i < NUMVIDAS; i++) {
			vidas.add(new Vidas(imgVida, 1600 + 50 * i, 25, this));
		}
	}

	public void createShootAndMag() {
		imgDisparo = getImage(getCodeBase(), "Resources/Huevo/Huevo.png");
		disparo = new ArrayList<Disparo>();
		cargador = new ArrayList<Disparo>();
		for (int i = 0; i < NUM_DISPAROS; i++) {
			cargador.add(new Disparo(imgDisparo, 200 + (50 * i), 50, this));
		}
	}

	public void createReloadMag() {
		powerUp = new ArrayList<PowerUP>();
		imgPowerUP = getImage(getCodeBase(), "Resources/Power/Power.png");
		for (int i = 0; i < NUM_POWER_UP; i++) {
			powerUp.add(new PowerUP(imgPowerUP, 800 + (1370 * i), yoshi.y + yoshi.height, this));
		}
	}

	public void createEndGame() {
		// victory
		victory = getImage(getCodeBase(), "Resources/Victoria/Victoria.jpg");
		// muerte
		dead = getImage(getCodeBase(), "Resources/Muerte/Fin.png");
	}

	public void createSounds() {
		try {
			initSong = getAudioClip(new URL(getDocumentBase(), "Resources/Sonidos/fondo.wav"));
			songLevell = getAudioClip(new URL(getDocumentBase(), "Resources/Sonidos/fondo1.wav"));
			songLevel2 = getAudioClip(new URL(getDocumentBase(), "Resources/Sonidos/fondo2.wav"));
			songLevelBoss = getAudioClip(new URL(getDocumentBase(), "Resources/Sonidos/jefe.wav"));
			jumpSound = getAudioClip(new URL(getDocumentBase(), "Resources/Sonidos/salto.wav"));
			yoshiShootSound = getAudioClip(new URL(getDocumentBase(), "Resources/Sonidos/disparo.wav"));
			yoshiHit = getAudioClip(new URL(getDocumentBase(), "Resources/Sonidos/muerto.wav"));
			bowserShoot = getAudioClip(new URL(getDocumentBase(), "Resources/Sonidos/bowser.wav"));

		} catch (MalformedURLException e) {
		}
	}

	// pintar

	public void paintLevels() {
		if (initlevel) {
			Initlevel.dibujar(noseve, this);
		}
		if (level1) {
			nivel1.dibujar(noseve, this);
			for (int i = 0; i < setaNV1.size(); i++) {
				setaNV1.get(i).dibujar(noseve);
			}
		} else if (level2) {
			nivel2.dibujar(noseve, this);
			for (int i = 0; i < setaNV2.size(); i++) {
				setaNV2.get(i).dibujar(noseve);
			}
			for (int i = 0; i < goomba.size(); i++) {
				goomba.get(i).dibujar(noseve);
			}
		} else if (levelBoss) {
			nivelBoss.dibujar(noseve, this);
			boss.dibujar(noseve);
			for (int i = 0; i < disparosBoss.size(); i++) {
				disparosBoss.get(i).dibujar(noseve);
			}
		}
	}

	public void paintLives() {
		for (int i = 0; i < vidas.size(); i++) {
			vidas.get(i).dibujar(noseve);
		}
	}

	public void paintShootsAndMag() {
		// dibujar disparos
		for (int i = 0; i < disparo.size(); i++) {
			disparo.get(i).dibujar(noseve);
		}
		// dibujar Cargador
		for (int i = 0; i < NUM_DISPAROS; i++) {
			cargador.get(i).dibujar(noseve);
		}
	}

	public void paintMagBullets() {
		for (int i = 0; i < powerUp.size(); i++) {
			powerUp.get(i).dibujar(noseve);
		}
	}

	public void paintEndGame() {
		if (vivo == false) {
			animacion.stop();
			noseve.drawImage(dead, 700, 100, 350, 100, this);
			songLevell.stop();
			songLevel2.stop();
			songLevelBoss.stop();
		}
		if (VIDAS_BOSS == 0) {
			animacion.stop();
			noseve.drawImage(victory, 0, 0, 1920, 500, this);
			songLevelBoss.stop();
			initSong.loop();
		}
	}
	// movimientos e interacciones

	public void enemiesMove() {
		if (level1) {
			for (int i = 0; i < setaNV1.size(); i++) {
				setaNV1.get(i).actualizar();
			}
		} else if (level2) {
			for (int i = 0; i < setaNV2.size(); i++) {
				setaNV2.get(i).actualizar();
			}
			for (int i = 0; i < goomba.size(); i++) {
				goomba.get(i).actualizar();
			}
		} else if (levelBoss) {
			boss.actualizar();
			for (int i = 0; i < disparosBoss.size(); i++) {
				disparosBoss.get(i).actualizar();
			}
		}
	}

	public void enemiesHittingYoshi() {
		try {
			if (level1) {
				for (int i = 0; i < setaNV1.size(); i++) {
					if (yoshi.intersects(setaNV1.get(i))) {
						setaNV1.remove(i);
						vidas.remove(NUMVIDAS - 1);
						NUMVIDAS--;
						yoshiHit.play();
					}
				}
			}
			if (level2) {
				for (int i = 0; i < setaNV2.size(); i++) {
					if (yoshi.intersects(setaNV2.get(i))) {
						setaNV2.remove(i);
						vidas.remove(NUMVIDAS - 1);
						NUMVIDAS--;
						yoshiHit.play();
					}
				}
				for (int i = 0; i < goomba.size(); i++) {
					if (yoshi.intersects(goomba.get(i))) {
						goomba.remove(i);
						vidas.remove(NUMVIDAS - 1);
						NUMVIDAS--;
						yoshiHit.play();
					}
				}
			}
			if (levelBoss) {
				if (boss.intersects(yoshi)) {
					vidas.remove(NUMVIDAS - 1);
					NUMVIDAS--;
					yoshiHit.play();
					yoshi.x = -500;
				}
				for (int i = 0; i < disparosBoss.size(); i++) {
					if (disparosBoss.get(i).intersects(yoshi)) {
						disparosBoss.remove(i);
						vidas.remove(NUMVIDAS - 1);
						NUMVIDAS--;
						yoshiHit.play();
					}
				}
			}
		} catch (Exception e) {
		}
	}

	public void yoshiHitShooting() {
		for (int i = 0; i < disparo.size(); i++) {
			disparo.get(i).actualizar();
		}
		if (level1) {
			for (int i = 0; i < setaNV1.size(); i++) {
				for (int j = 0; j < disparo.size(); j++) {
					if (disparo.get(j).intersects(setaNV1.get(i))) {
						setaNV1.remove(i);
						disparo.remove(j);
					}
				}
			}
		}
		if (level2) {
			for (int i = 0; i < setaNV2.size(); i++) {
				for (int j = 0; j < disparo.size(); j++) {
					if (disparo.get(j).intersects(setaNV2.get(i))) {
						setaNV2.remove(i);
						disparo.remove(j);
					}
				}
			}
			for (int i = 0; i < goomba.size(); i++) {
				for (int j = 0; j < disparo.size(); j++) {
					if (disparo.get(j).intersects(goomba.get(i))) {
						goomba.remove(i);
						disparo.remove(j);
					}
				}
			}
		}
		if (levelBoss) {
			for (int i = 0; i < disparo.size(); i++) {
				if (disparo.get(i).intersects(boss)) {
					VIDAS_BOSS--;
					disparo.remove(i);
				}
			}
		}
	}

	public void reloadShootYoshi() {
		for (int i = 0; i < powerUp.size(); i++) {
			powerUp.get(i).actualizar();
			if (powerUp.get(i).intersects(yoshi)) {
				if (NUM_DISPAROS < 5) {
					powerUp.remove(i);
					NUM_DISPAROS += 1;
				}
			}
		}
	}

	public void levelChange() {
		if ((yoshi.x >= 1920) && (level1)) {
			level1 = false;
			level2 = true;
			yoshi.x = 50;
			songLevell.stop();
			songLevel2.loop();
		} else if ((yoshi.x >= 1920) && (level2)) {
			level2 = false;
			levelBoss = true;
			yoshi.x = 50;
			songLevel2.stop();
			songLevelBoss.loop();
		}

	}

	public void levelsMoveRigth() {
		if (level1) {
			nivel1.derecha = true;
			nivel1.izquierda = false;
			nivel1.actualizar();
			for (int i = 0; i < setaNV1.size(); i++) {
				setaNV1.get(i).derecha = true;
				setaNV1.get(i).izquierda = false;
				setaNV1.get(i).velx = 10;
			}
		}
		if (level2) {
			nivel2.derecha = true;
			nivel2.izquierda = false;
			nivel2.actualizar();
			for (int i = 0; i < setaNV2.size(); i++) {
				setaNV2.get(i).derecha = true;
				setaNV2.get(i).izquierda = false;
				setaNV2.get(i).velx = 10;
			}
			for (int i = 0; i < goomba.size(); i++) {
				goomba.get(i).derecha = true;
				goomba.get(i).izquierda = false;
			}
		}
	}

	public void levelsMoveLeft() {
		if (level1) {
			nivel1.derecha = false;
			nivel1.izquierda = true;
			nivel1.actualizar();
			for (int i = 0; i < setaNV1.size(); i++) {
				setaNV1.get(i).derecha = false;
				setaNV1.get(i).izquierda = true;
			}
		}
		if (level2) {
			nivel2.derecha = false;
			nivel2.izquierda = true;
			nivel2.actualizar();
			for (int i = 0; i < setaNV1.size(); i++) {
				setaNV2.get(i).derecha = false;
				setaNV2.get(i).izquierda = true;
			}
			for (int i = 0; i < goomba.size(); i++) {
				goomba.get(i).derecha = false;
				goomba.get(i).izquierda = true;
			}
		}
	}

	public void yoshiShoot() {
		if (NUM_DISPAROS > 0) {
			disparo.add(new Disparo(imgDisparo, yoshi.x + yoshi.width, yoshi.y + 30, this));
			NUM_DISPAROS -= 1;
			yoshiShootSound.play();
		}
	}

}