package Partie;

public class ProprietesRobot {

	private int batterie_defaut;
	private int charge_maximale;
	private double cout_installation;
	private int cout_minage;
	private double cout_rotation;
	private double emoussage_laser;
	private int laser_defaut;
	private double limite_emoussage;
	private int temps_avant_que_nana_repere;
	private double temps_deplacement;
	private int temps_rotation;
	
	/**
	 *  Constructeur paramétré de l'objet PropreietesRobot.
	 * @param charge_maxale Charge maximale de minerai du robot
	 * @param temps_rotation Temps de rotation à 90°
	 * @param cout_rotation	Cout d'une rotation à 90°
	 * @param temps_deplacement Temps de déplacement d'une unité
	 * @param cout_installation Cout de l'installation d'un équipement (Batterie ou Laser)
	 * @param cout_minage	Cout en puissance de batterie du minage d'un mienrai
	 * @param batterie_defaut Puissance de la batterie par défaut
	 * @param laser_defaut Puissance du laser par défaut
	 * @param emoussage_laser Emoussage du laser pour le minage d'un mienrai
	 * @param limite_emoussage Limite de l'émoussage du laser avant qu'il ne casse
	 * @param temps_avant_que_nana_repere Temps en secondes avant que la NASA repère le robot
	 */
	public ProprietesRobot(int charge_maximale, int temps_rotation, double cout_rotation, double temps_deplacement,
			double cout_installation, int cout_minage, int batterie_defaut, int laser_defaut, double emoussage_laser,
			double limite_emoussage, int temps_avant_que_nana_repere) {
		super();
		this.charge_maximale = charge_maximale;
		this.temps_rotation = temps_rotation;
		this.cout_rotation = cout_rotation;
		this.temps_deplacement = temps_deplacement;
		this.cout_installation = cout_installation;
		this.cout_minage = cout_minage;
		this.batterie_defaut = batterie_defaut;
		this.laser_defaut = laser_defaut;
		this.emoussage_laser = emoussage_laser;
		this.limite_emoussage = limite_emoussage;
		this.temps_avant_que_nana_repere = temps_avant_que_nana_repere;
	}
	
	/**
	 * Constructeur non paramétré de l'objet PropreietesRobot.
	 */
	public ProprietesRobot() {
		super();
	}
	
	public int getCharge_maximale() {
		return charge_maximale;
	}
	public void setCharge_maximale(int charge_maxale) {
		this.charge_maximale = charge_maxale;
	}
	public int getTemps_rotation() {
		return temps_rotation;
	}
	public void setTemps_rotation(int temps_rotation) {
		this.temps_rotation = temps_rotation;
	}
	public double getCout_rotation() {
		return cout_rotation;
	}
	public void setCout_rotation(double cout_rotation) {
		this.cout_rotation = cout_rotation;
	}
	public double getTemps_deplacement() {
		return temps_deplacement;
	}
	public void setTemps_deplacement(double temps_deplacement) {
		this.temps_deplacement = temps_deplacement;
	}
	public double getCout_installation() {
		return cout_installation;
	}
	public void setCout_installation(double cout_installation) {
		this.cout_installation = cout_installation;
	}
	public int getCout_minage() {
		return cout_minage;
	}
	public void setCout_minage(int cout_minage) {
		this.cout_minage = cout_minage;
	}
	public int getBatterie_defaut() {
		return batterie_defaut;
	}
	public void setBatterie_defaut(int batterie_defaut) {
		this.batterie_defaut = batterie_defaut;
	}
	public int getLaser_defaut() {
		return laser_defaut;
	}
	public void setLaser_defaut(int laser_defaut) {
		this.laser_defaut = laser_defaut;
	}
	public double getEmoussage_laser() {
		return emoussage_laser;
	}
	public void setEmoussage_laser(double emoussage_laser) {
		this.emoussage_laser = emoussage_laser;
	}
	public double getLimite_emoussage() {
		return limite_emoussage;
	}
	public void setLimite_emoussage(double limite_emoussage) {
		this.limite_emoussage = limite_emoussage;
	}
	public int getTemps_avant_que_nana_repere() {
		return temps_avant_que_nana_repere;
	}
	public void setTemps_avant_que_nana_repere(int temps_avant_que_nana_repere) {
		this.temps_avant_que_nana_repere = temps_avant_que_nana_repere;
	}
	public void set(String prop) {
	}
}
