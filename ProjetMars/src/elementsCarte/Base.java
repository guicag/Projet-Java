package elementsCarte;

public class Base extends Case {
	private static Base base = null;
	
	/**
	 * Permet d'obtenir l'instance du singleton de l'objet Base.
	 * @return L'instance de l'objet Base.
	 */
	public static Base getInstance() {
		if(base==null)
			base = new Base();
		return base;
	}
	
	/**
	 * Constructeur de l'objet Base.
	 */
	private Base() {
		super('@', "Base");
	}
}
