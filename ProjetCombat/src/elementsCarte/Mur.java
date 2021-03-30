package elementsCarte;

public class Mur extends Case {
	
	private static Mur mur = null;
	
	/**
	 * Permet d'obtenir l'instance du singleton de l'objet Vide.
	 * @return L'instance de l'objet Vide.
	 */
	public static Mur getInstance() {
		if(mur==null)
			mur = new Mur();
		return mur;
	}
	
	/**
	 * Constructeur de l'objet Vide.
	 */
	private Mur() {
		super('#', "Mur");
	}
}
