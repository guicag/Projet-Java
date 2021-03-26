package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import Partie.Direction;
import Partie.Jeu;
import equipements.Minerai;
import equipements.Vide;

public class testRobot {

	@Test
	/**
	 * Permet de tester si le robot est bien rentré à la base après l'appel de la
	 * fonciton rentrerBase.
	 */
	public void testRetourBase() {
		try {
			Jeu jeu = new Jeu();
			int scoreDebut = (Integer) jeu.getRobot().getScore(); 
			for (int i = 0; i < 5; i++) { // Déplacer le robot 5 fois
				jeu.getRobot().avancer(jeu.choisirDirection(), jeu.getCarte());
			}
			int scoreExpected = 0;
			for(Minerai minerai : jeu.getRobot().getPocheDeMinerais()) {
				scoreExpected += minerai.getValeur();
			}
			assertFalse(jeu.getRobot().getPosX() == jeu.getRobot().getBaseX());
			assertFalse(jeu.getRobot().getPosY() == jeu.getRobot().getBaseY());
			jeu.getRobot().rentrerBase(jeu.getCarte());
			assertEquals(jeu.getRobot().getBaseX(), jeu.getRobot().getPosX());
			assertEquals(jeu.getRobot().getBaseY(), jeu.getRobot().getPosY());
			assertEquals(scoreExpected, jeu.getRobot().getScore());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Permet de tester si le robot s'est bien déplacer d'une case après l'appel de
	 * la méthode avancer.
	 */
	public void testAvancer() {
		try {
			Jeu jeu = new Jeu();
			int posXDebut = jeu.getRobot().getPosX();
			int posYDebut = jeu.getRobot().getPosY();
			Direction dirChoisie = jeu.choisirDirection();
			jeu.getRobot().avancer(dirChoisie, jeu.getCarte());
			switch (dirChoisie) {
			case NORD:
				assertEquals(posXDebut - 1, jeu.getRobot().getPosX());
				break;
			case SUD:
				assertEquals(posXDebut + 1, jeu.getRobot().getPosX());
				break;
			case EST:
				assertEquals(posYDebut + 1, jeu.getRobot().getPosY());
				break;
			case OUEST:
				assertEquals(posYDebut - 1, jeu.getRobot().getPosY());
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Permet de tester si la case dans laquelle le robot a avancé est bien minée,
	 * c'est à dire une instance de l'objet Vide.
	 */
	public void testMiner() {
		try {
			Jeu jeu = new Jeu();
			Direction dirChoisie = jeu.choisirDirection();
			Minerai mineraiMine = new Minerai();
			// Récupération du minerai dans la direction choisie.
			switch (dirChoisie) {
				case NORD:
					mineraiMine = new Minerai((Minerai) jeu.getCarte().getMatriceMinerais()[jeu.getRobot().getPosX()+1][jeu.getRobot().getPosY()]);
					break;
				case SUD:
					mineraiMine = new Minerai((Minerai) jeu.getCarte().getMatriceMinerais()[jeu.getRobot().getPosX()-1][jeu.getRobot().getPosY()]);
					break;
				case EST:
					mineraiMine = new Minerai((Minerai) jeu.getCarte().getMatriceMinerais()[jeu.getRobot().getPosX()][jeu.getRobot().getPosY()+1]);
					break;
				case OUEST:
					mineraiMine = new Minerai((Minerai) jeu.getCarte().getMatriceMinerais()[jeu.getRobot().getPosX()][jeu.getRobot().getPosY()-1]);
					break;
			}
			jeu.getRobot().avancer(dirChoisie, jeu.getCarte());
			assertTrue(jeu.getCarte().getMatriceMinerais()[jeu.getRobot().getPosX()][jeu.getRobot().getPosY()] instanceof Vide);
			assertTrue((Integer) jeu.getRobot().getScore() == newScoreExpected);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
