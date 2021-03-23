package equipements;
/**
 * @author cleme
 *
 *
 */
public class Minerai extends Case implements Comparable<Case>{

	private int valeur;
	private int durete;
	private int poids;
	private double ratio;// Le ratio correspond au rapport de la dureté sur la valeur du minerai. Différent de 0 si valeur l'est aussi.
	private boolean isMine;
	
	/**
	 * Constructeur de copie.
	 * @param other
	 */
	public Minerai(Minerai other) {
		super(other.caractere, other.nom);
		this.valeur = other.valeur;
		this.durete = other.durete;
		this.poids = other.poids;
		this.ratio = other.ratio;
		this.isMine = other.isMine;
	}
	
	/**
	 * Constructeur paramétré de Minerai.
	 * @param c Symbole du minerai
	 * @param n Nom du minerai
	 * @param v Valeur du minerai
	 * @param d Dureté du minerai
	 * @param p Poids du minerai
	 */
	public Minerai(char c, String n, int v, int d, int p) {
		super(c, n);
		this.setValeur(v);
		this.durete = d;
		this.poids = p;
		this.ratio = (double) v/ (double) d;
		this.isMine = false;
	}

	public int getValeur() {
		return valeur;
	}

	public void setValeur(int valeur) {
		this.valeur = valeur;
	}

	public int getDurete() {
		return durete;
	}

	public void setDurete(int durete) {
		this.durete = durete;
	}

	public int getPoids() {
		return poids;
	}

	public void setPoids(int poids) {
		this.poids = poids;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(int ratio) {
		this.ratio = ratio;
	}

	@Override
	public int compareTo(Case cas) {
		if(cas instanceof Minerai) {
			Minerai min = (Minerai) cas;
			int res = 0;
			if(min.ratio<ratio) res = -1;
			if(min.ratio>ratio) res = 1;
			if(min.ratio == 0 && ratio == 0){
				if(min.durete>durete) res = -1;
				if(min.durete<durete) res = 1;
			}
			return res;
		} else {
			return -1;
		}
	}
	
	public boolean isMine() {
		return isMine;
	}

	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	
}
