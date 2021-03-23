package equipements;

public class Vide extends Case {
	private static Vide vide = null;
	
	public static Vide getInstance() {
		if(vide==null)
			vide = new Vide();
		return vide;
	}
	
	private Vide() {
		super(' ', "Case vide");
	}
}
