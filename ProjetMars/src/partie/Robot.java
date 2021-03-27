package partie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import elementsCarte.Case;
import elementsCarte.Minerai;
import elementsCarte.Vide;
import equipements.Batterie;
import equipements.Equipement;
import equipements.Laser;

public class Robot {
	private int baseX;
	private int baseY;
	private int posX;
	private int posY;
	private int charge;
	private Batterie batterieActuelle;
	private Laser laserActuel;
	private ArrayList<Equipement> equipementsDisponibles;
	private Map<String, Number> configuration;
	private ArrayList<Direction> listDeplacementsPourBase;
	private ArrayList<Minerai> pocheDeMinerais;
	private Direction direction;
	private int score;
	private static final String TEMPS_NASA_RESTANT = "temps_avant_que_nasa_repere";
	private static final String TEMPS_INSTALLATION = "temps_installation";
	
	/**
	 * Constructeur non paramatré de l'objet Robot.
	 */
	public Robot() {
		super();
	}
	
	/**
	 * Constructeur paramétré de l'objet Robot.  
	 * @param configuration Map de String et Number correpsondant à la configuration du robot.
	 * @param equipementsDisponibles Liste d'objet Equipement correspondant aux équipement que peux achter le robot.
	 * @param batterieDef Batterie par defaut que va equiper le robot.
	 * @param laserDef Laser par defaut que va equiper le robot.
	 * @param posX 
	 * @param posY
	 */
	public Robot(Map<String, Number> configuration, List<Equipement> equipementsDisponibles, Batterie batterieDef, Laser laserDef, int posX, int posY) {
		this.configuration = configuration;
		this.equipementsDisponibles = (ArrayList<Equipement>) equipementsDisponibles;
		this.batterieActuelle = batterieDef;
		this.laserActuel = laserDef;
		this.posX = posX;
		this.posY = posY;
		this.baseX = posX;
		this.baseY = posY;
		this.direction = Direction.NORD;
		this.score = 0;
		this.charge = 0;
		this.listDeplacementsPourBase = new ArrayList<Direction>();
		this.pocheDeMinerais = new ArrayList<Minerai>();
	}
	
	
	/**
	 * Permet de déplacer le robot dans une direction.
	 * 
	 * @param dir Enum direction indiquant la direction dans laquelle avancer
	 * 
	 * @return actions7
	 * @throws Exception 
	 */
	public String avancer(Direction dir, Carte carte){
		String actions = "";
		if(this.direction!=dir) {
			actions += tourner(dir);
		} else {
			actions += "AVANCER,";
		}
		// Avance dans la "dir" souhaitée
		switch(dir) {
			case NORD :
				this.posX--;
				listDeplacementsPourBase.add(Direction.SUD);
				break;
			case SUD :
				this.posX++;
				listDeplacementsPourBase.add(Direction.NORD);
				break;
			case EST :
				this.posY++;
				listDeplacementsPourBase.add(Direction.OUEST);
				break;
			case OUEST :
				this.posY--;
				listDeplacementsPourBase.add(Direction.EST);
				break;
		}
		if(carte.getMatriceMinerais()[posX][posY] instanceof Minerai) {
			miner((Minerai) carte.getMatriceMinerais()[posX][posY], dir);
			carte.getMatriceMinerais()[posX][posY] = Vide.getInstance();
		}
		else this.configuration.replace(TEMPS_NASA_RESTANT, (Double) configuration.get(TEMPS_NASA_RESTANT) - (Double) configuration.get("temps_deplacement_vide"));
		this.batterieActuelle.setPuissanceActuelle(batterieActuelle.getPuissanceActuelle() - (Double) configuration.get("cout_deplacement"));
		return actions;
	}
	
	/**
	 * Permet de faire tourner le robot dans une direction donner en parametre
	 * @param dir
	 * @return Un string qui decrit le comportement du robot
	 */
	public String tourner(Direction dir) {
		// Décrémentation du temps de rotation et de la batterie
		this.batterieActuelle.setPuissanceActuelle(batterieActuelle.getPuissanceActuelle() - (Double) configuration.get("cout_rotation"));
		this.configuration.replace(TEMPS_NASA_RESTANT, (Double) configuration.get(TEMPS_NASA_RESTANT) - (Double) configuration.get("temps_rotation"));
		this.direction = dir;
		return "TOURNER "+dir.name()+", AVANCER,";
	}
	
	/**
	 * Permet de tester si le robot est capable de porter ce minerai.
	 * 
	 * @param minerai
	 * @return
	 */
	public boolean testerMiner(Minerai c) {
		boolean mineraiOk = true;
		if( charge + c.getPoids() > (Double) configuration.get("charge_maximale")) {
			mineraiOk = false;
		}
		return mineraiOk;
	}
	
	/**
	 *  Permet de miner le minerai à l'endroit ou se trouve le robot.
	 *  
	 * @param minerai Minerai à miner.
	 * 
	 * @throws Exception Lorsque vous ne pouvez pas porter le minerai. (charge maximale atteinte)
	 */
	public void miner(Minerai minerai, Direction dir){
		// Récupération des caractéristiques du minerai
		int poidsMinerai = minerai.getPoids();
		int dureteMinerai = minerai.getDurete();
		double puiLaser = this.laserActuel.getPuissanceActuelle();
		// Calcul du temps de minage et décrémentation du temps restant.
		double tempsMinage =  dureteMinerai*100/puiLaser;
		double emoussageLaser = tempsMinage * (Double) configuration.get("emoussage_laser"); // Emoussage du laser
		if ((puiLaser - emoussageLaser) <= (double) configuration.get("limite_emoussage")) {
			this.laserActuel.setPuissanceActuelle((double) configuration.get("limite_emoussage"));
		} else {
			this.laserActuel.setPuissanceActuelle(puiLaser - emoussageLaser);
		}
		// Utilisation de la batterie
		batterieActuelle.setPuissanceActuelle(batterieActuelle.getPuissanceActuelle() - (Double) configuration.get("cout_minage"));
		// Incrémentation charge max
		charge += poidsMinerai;
		//Décrémentation du temps restant
		this.configuration.put(TEMPS_NASA_RESTANT, (Double) configuration.get(TEMPS_NASA_RESTANT) - tempsMinage);
		pocheDeMinerais.add(minerai);
	}
	
	
	
	/**
	 * Permet de décharger le robot. N'est appellée uniquement si le robot est bien à la base.
	 * 
	 */
	public String decharger() {
		this.charge = 0;
		for(Minerai minerai : pocheDeMinerais) {
			score += minerai.getValeur();
		}
		pocheDeMinerais.clear();
		return "DECHARGER,";
	}
	
	/**
	 * Permet de s'équiper d'un laser ou d'une batterie.
	 */
	public String equiper(Equipement equip) {
		String message = "";
		if(equip instanceof Laser) {
			this.laserActuel = (Laser) equip;
		} else {
			this.batterieActuelle = (Batterie) equip;
		}
		double prix = equip.getCout();
		this.score -= prix;
		this.configuration.replace(TEMPS_NASA_RESTANT, (Double) configuration.get(TEMPS_NASA_RESTANT) - (Double) configuration.get(TEMPS_INSTALLATION));
		message += "ACHETER/EQUIPER : " + equip.getNom() +",";
		return message;
	}
	
	/**
	 * Permet de retourner à la base.
	 * @param Carte carte : la carte du jeu
	 * @return Renvoie la liste des actions effectuées par le robot pour rentrer à la base.
	 */
	public List<String> rentrerBase(Carte carte) {
		List<String> listDeplacements = new ArrayList<String>();
		for (int i = listDeplacementsPourBase.size() - 1; i >= 0; i--) {
			listDeplacements.add(avancer(listDeplacementsPourBase.get(i), carte));
		}
		listDeplacementsPourBase.clear();		
		return listDeplacements;
	}
	
	public Equipement getBestLaser() {
		for(Equipement equip : equipementsDisponibles) {
			if(equip.getClass()==laserActuel.getClass()) return equip;
		}
		return null;
	}
	
	public Equipement getBestBatterie() {
		for(Equipement equip : equipementsDisponibles) {
			if(equip.getClass()==batterieActuelle.getClass()) return equip;
		}
		return null;
	}
	
	public double getCoutRetourBase() {
		double coutRetour = 0;
        for (int i = 1; i < listDeplacementsPourBase.size()-1; i++)
        {
            if (!listDeplacementsPourBase.get(i).equals(listDeplacementsPourBase.get(i-1))) {
                coutRetour += (Double) configuration.get("cout_rotation") + (Double) configuration.get("cout_deplacement");
            } else {
                coutRetour += (Double) configuration.get("cout_deplacement");
            }
        }
       return coutRetour*2;
	}
	
	
	/**
	 * Cette fonction permet de retourner la meilleure batterie en fonction du score
	 * @return Batterie
	 */
	public Batterie getBestBatterieAchetable() {
		for (Equipement equipement : equipementsDisponibles) {
			if (equipement instanceof Batterie) {
				if (score >=  equipement.getCout()) {
					return (Batterie) equipement;
				}
			}
		}
		return null;
	}
	
	/**
	 * Cette fonction permet de retourner le meilleur laser en fonction du score
	 * @return Laser
	 */
	public Laser getBestLaserAchetable() {
		for (Equipement equipement : equipementsDisponibles) {
			if (equipement instanceof Laser) {
				if (score >= equipement.getCout()) return (Laser) equipement;
			}
		}
		return null;
	}

	public int getBaseX() {
		return baseX;
	}

	public void setBaseX(int baseX) {
		this.baseX = baseX;
	}

	public int getBaseY() {
		return baseY;
	}

	public void setBaseY(int baseY) {
		this.baseY = baseY;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public Batterie getBatterieActuelle() {
		return batterieActuelle;
	}

	public void setBatterieActuelle(Batterie batterieActuelle) {
		this.batterieActuelle = batterieActuelle;
	}

	public Laser getLaserActuel() {
		return laserActuel;
	}

	public void setLaserActuel(Laser laserActuel) {
		this.laserActuel = laserActuel;
	}

	public List<Equipement> getEquipementsDisponibles() {
		return equipementsDisponibles;
	}

	public void setEquipementsDisponibles(List<Equipement> equipementsDisponibles) {
		this.equipementsDisponibles = (ArrayList<Equipement>) equipementsDisponibles;
	}

	public Map<String, Number> getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Map<String, Number> configuration) {
		this.configuration = configuration;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public int getCharge() {
		return charge;
	}

	public void setCharge(int charge) {
		this.charge = charge;
	}

	public Number getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public ArrayList<Direction> getListDeplacementsPourBase() {
		return listDeplacementsPourBase;
	}

	public void setListDeplacementsPourBase(ArrayList<Direction> listDeplacementsPourBase) {
		this.listDeplacementsPourBase = listDeplacementsPourBase;
	}

	public ArrayList<Minerai> getPocheDeMinerais() {
		return pocheDeMinerais;
	}

	public void setPocheDeMinerais(ArrayList<Minerai> pocheDeMinerais) {
		this.pocheDeMinerais = pocheDeMinerais;
	}

}
