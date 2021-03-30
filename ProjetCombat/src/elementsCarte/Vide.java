package elementsCarte;

public class Vide extends Case {
	
	private static Vide vide = null;
	
	/**
	 * Permet d'obtenir l'instance du singleton de l'objet Vide.
	 * @return L'instance de l'objet Vide.
	 */
	public static Vide getInstance() {
		if(vide==null)
			vide = new Vide();
		return vide;
	}
	
	/**
	 * Constructeur de l'objet Vide.
	 */
	private Vide() {
		super(' ', "Case vide");
	}
}
