package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import elementsCarte.Case;
import elementsCarte.Minerai;
import elementsCarte.Vide;
import equipements.Batterie;
import equipements.Equipement;
import partie.Direction;
import partie.Jeu;

/**
 * Classe permettant de tester les m�thodes de l'objet Robot.
 * @author Cl�ment
 */
public class TestRobot {
	
	private Jeu jeu;
	
	TestRobot() {
		try {
			this.jeu = new Jeu();
		} catch (IOException e) {
			System.out.println("Erreur dans la lecture des fichiers de configuration.");
			e.printStackTrace();
		}
	}

	@Ignore
	/**
	 * Permet de tester si le robot est bien rentr� � la base apr�s l'appel de la
	 * fonction rentrerBase.
	 */
	void testRetourBase() {
		for (int i = 0; i < 5; i++) jeu.getRobot().avancer(jeu.choisirDirection(), jeu.getCarte()); // D�placer le robot 5 fois
		assertTrue(jeu.getRobot().getPosX() != jeu.getRobot().getBaseX());
		assertTrue(jeu.getRobot().getPosY() != jeu.getRobot().getBaseY());
		jeu.getRobot().rentrerBase(jeu.getCarte());
		assertEquals(jeu.getRobot().getBaseX(), jeu.getRobot().getPosX());
		assertEquals(jeu.getRobot().getBaseY(), jeu.getRobot().getPosY());
	}
	
	@Ignore
	/**
	 * Fonction permettant de tester si le robot se d�charge bien lorsqu'il est � la base,
	 *  et que les minerais qu'il d�pose incr�mentent bien score.
	 */
	void testDecharger() {
		for (int i = 0; i < 5; i++) jeu.getRobot().avancer(jeu.choisirDirection(), jeu.getCarte()); // D�placer le robot 5 fois
		jeu.getRobot().rentrerBase(jeu.getCarte());
		int scoreInitial = (Integer) jeu.getRobot().getScore();
		int scoreExpected = scoreInitial;
		for(Minerai minerai : jeu.getRobot().getPocheDeMinerais()) {
			scoreExpected += minerai.getValeur();
		}
		jeu.getRobot().decharger();
		assertEquals(scoreExpected, jeu.getRobot().getScore());
		assertTrue(jeu.getRobot().getPocheDeMinerais().isEmpty());
	}
	
	@Ignore
	/**
	 * Permet de tester si le robot s'est bien d�plac� d'une case apr�s l'appel de la m�thode avancer.
	 * V�rifie �galement que le temps restant aie �t� d�cr�ment� si la case est vide, et que la batterie aie bien �t� d�cr�ment�e de cout_deplacement;
	 */
	void testAvancer() {
		int posXDebut = jeu.getRobot().getPosX();
		int posYDebut = jeu.getRobot().getPosY();
		Direction dirChoisie = jeu.choisirDirection();
		double tempsAvantAvancer = (Double) jeu.getRobot().getConfiguration().get("temps_avant_que_nasa_repere");
		double puissanceBatterieAvant = jeu.getRobot().getBatterieActuelle().getPuissanceActuelle();
		boolean isCaseVide = getCaseDirection(dirChoisie) instanceof Vide;
		boolean isDirectionDifferente = jeu.getRobot().getDirection() != dirChoisie;
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
		double newBat = puissanceBatterieAvant;
		if(isCaseVide) assertTrue((Double) jeu.getRobot().getConfiguration().get("temps_avant_que_nasa_repere") == tempsAvantAvancer - (Double) jeu.getRobot().getConfiguration().get("temps_deplacement_vide"));
		else newBat -= (Double) jeu.getRobot().getConfiguration().get("cout_minage");
		if(isDirectionDifferente) newBat -= (Double) jeu.getRobot().getConfiguration().get("cout_rotation");
		assertTrue(jeu.getCarte().getMatriceMinerais()[jeu.getRobot().getPosX()][jeu.getRobot().getPosY()] instanceof Vide);
		assertEquals((Double) jeu.getRobot().getBatterieActuelle().getPuissanceActuelle(), newBat - (Double) jeu.getRobot().getConfiguration().get("cout_deplacement"));
	}

	@Ignore
	/**
	 * Permet de tester si la case dans laquelle le robot a avanc� est bien min�e,
	 * c'est � dire une instance de l'objet Vide. V�rifie �galement que le dernier objet Minerai ajout� � la pocheDeMinerais du robot
	 * est bien �gal au minerai que le robot vient de miner.
	 */
	void testMiner() {
		Direction dirChoisie = jeu.choisirDirection();
		Minerai mineraiMine = new Minerai();
		double batterieAvant = jeu.getRobot().getBatterieActuelle().getPuissanceActuelle();
		double puiLaserAvant = jeu.getRobot().getBatterieActuelle().getPuissanceActuelle();
		double tempsAvant = (Double) jeu.getRobot().getConfiguration().get("temps_avant_que_nasa_repere");
		// R�cup�ration du minerai dans la direction choisie.
		switch (dirChoisie) {
			case NORD:
				jeu.getRobot().setPosX(jeu.getRobot().getPosX()-1);// Simule le d�plamenet du robot dans la case
				mineraiMine = new Minerai((Minerai) jeu.getCarte().getMatriceMinerais()[jeu.getRobot().getPosX()-1][jeu.getRobot().getPosY()]);
				break;
			case SUD:
				jeu.getRobot().setPosX(jeu.getRobot().getPosX()+1);// Simule le d�plamenet du robot dans la case
				mineraiMine = new Minerai((Minerai) jeu.getCarte().getMatriceMinerais()[jeu.getRobot().getPosX()+1][jeu.getRobot().getPosY()]);
				break;
			case EST:
				jeu.getRobot().setPosY(jeu.getRobot().getPosY()+1);// Simule le d�plamenet du robot dans la case
				mineraiMine = new Minerai((Minerai) jeu.getCarte().getMatriceMinerais()[jeu.getRobot().getPosX()][jeu.getRobot().getPosY()+1]);
				break;
			case OUEST:
				jeu.getRobot().setPosY(jeu.getRobot().getPosY()-1); // Simule le d�plamenet du robot dans la case
				mineraiMine = new Minerai((Minerai) jeu.getCarte().getMatriceMinerais()[jeu.getRobot().getPosX()][jeu.getRobot().getPosY()-1]);
				break;
		}
		double tempsMinage = mineraiMine.getDurete()*100/jeu.getRobot().getLaserActuel().getPuissanceActuelle();
		double emoussageLaser = tempsMinage * (Double) jeu.getRobot().getConfiguration().get("emoussage_laser");
		jeu.getRobot().miner(mineraiMine, dirChoisie);
		assertEquals(jeu.getRobot().getLaserActuel().getPuissanceActuelle(), puiLaserAvant - emoussageLaser);
		assertEquals(jeu.getRobot().getBatterieActuelle().getPuissanceActuelle(), batterieAvant 
				- (Double) jeu.getRobot().getConfiguration().get("cout_minage"));
		assertEquals(jeu.getRobot().getConfiguration().get("temps_avant_que_nasa_repere"), 
				tempsAvant - tempsMinage);
		Minerai lastInsertedMinerai = jeu.getRobot().getPocheDeMinerais().get(jeu.getRobot().getPocheDeMinerais().size() -1);
		assertTrue(lastInsertedMinerai.compareTo(mineraiMine) == 0);
	}
	
	@Ignore
	/**
	 * Permet de tester si le robot a bien tourn� apr�s l'appel de la fonction
	 */
	void testTourner() {
		Direction directionChoisie = jeu.choisirDirection();
		jeu.getRobot().tourner(directionChoisie);
		assertEquals(jeu.getRobot().getDirection(), directionChoisie);
	}
	
	@Test
	/**
	 * Permet de tester la m�thode equiper de l'objet Robot.
	 * Le test v�rifie que l'objet donn� en param�tre est bien �quip� au robot,
	 * et que le score a bien �t� d�cr�ment� du montant de l'�quipement.
	 */
	void testEquiper() {
		Equipement equip = jeu.getRobot().getEquipementsDisponibles().get(0);
		int scoreAvant = (Integer) jeu.getRobot().getScore() + equip.getCout();
		double tempsAvant = (Double) jeu.getRobot().getConfiguration().get("temps_avant_que_nasa_repere");
		jeu.getRobot().setScore(scoreAvant);
		jeu.getRobot().equiper(equip);
		if(equip instanceof Batterie)
			assertEquals(jeu.getRobot().getBatterieActuelle(), equip);
		else 
			assertEquals(jeu.getRobot().getLaserActuel(), equip);
		assertEquals(jeu.getRobot().getScore(), scoreAvant - equip.getCout());
		assertEquals((Double) jeu.getRobot().getConfiguration().get("temps_avant_que_nasa_repere"), tempsAvant - (Double) jeu.getRobot().getConfiguration().get("temps_installation"));
	}
	
	Case getCaseDirection(Direction dir) {
		switch (dir) {
			case NORD:
				return jeu.getCarte().getMatriceMinerais()[jeu.getRobot().getPosX()-1][jeu.getRobot().getPosY()];
			case SUD:
				return jeu.getCarte().getMatriceMinerais()[jeu.getRobot().getPosX()-1][jeu.getRobot().getPosY()];
			case EST:
				return (Minerai) jeu.getCarte().getMatriceMinerais()[jeu.getRobot().getPosX()][jeu.getRobot().getPosY()+1];
			case OUEST:
				return (Minerai) jeu.getCarte().getMatriceMinerais()[jeu.getRobot().getPosX()][jeu.getRobot().getPosY()-1];
		}
		return null;
	}
}
