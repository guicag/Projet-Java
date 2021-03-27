package elementsCarte;

/**
 * Objet Minerai, hérite de Case, et est un composant de la matrice d'objets Case de la Carte.
 * @author Clément
 *
 *
 */
public class Minerai extends Case implements Comparable<Case>{

	private int valeur;
	private int durete;
	private int poids;
	private double ratio; // Le ratio correspond au rapport de la valeur sur la dureté du minerai
	private boolean isMine;
	
	/**
	 * Constructeur de copie.
	 * @param other L'objet Minerai à copier.
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

	/**
	 * Constructeur non paramétré de l'objet Minerai.
	 */
	public Minerai() {
		super();
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
	/**
	 * Compare deux objets Minerai, Si l'objet en paramètre n'est pas un Minerai, retourne -1 indiquant que le Minerai est supérieur à cet objet.
	 * Sinon comprare les deux objets Minerai en fonction du ratio. Si les deux objets Minerai ont un ratio égal à 0, compare sur la dureté.
	 * @param Case cas : le Minerai ou la Case à comparer.
	 * @return Un entier indiquant l'odre entre les deux éléments. 0 s'il sont égaux, 1 si l'objet Minerai en paramètre a un ratio supérieur ou une durete supérieure, et -1 pour le cas contraire.
	 */
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
