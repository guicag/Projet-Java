package partie;

import java.util.Stack;

public class Robot {
	private String nom;
	private String programme;
	private int posX;
	private int posY;
	private int registreC;
	private int registreD;
	private static Stack<Integer> pilePublique;
	
	public Robot() {
		super();
	}
	
	public Robot(String nom, String programme, int posX, int posY) {
		this.nom = nom;
		this.programme = programme;
		this.posX = posX;
		this.posY = posY;
		registreC = 0;
		registreD = 0;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getProgramme() {
		return programme;
	}

	public void setProgramme(String programme) {
		this.programme = programme;
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

	public int getRegistreC() {
		return registreC;
	}

	public void setRegistreC(int registreC) {
		this.registreC = registreC;
	}

	public int getRegistreD() {
		return registreD;
	}

	public void setRegistreD(int registreD) {
		this.registreD = registreD;
	}

	public static Stack<Integer> getPilePublique() {
		return pilePublique;
	}

	public static void setPilePublique(Stack<Integer> pilePublique) {
		Robot.pilePublique = pilePublique;
	}
}
