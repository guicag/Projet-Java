package elementsCarte;

public class CaseRobot extends Case {
	private static CaseRobot caseRobot = null;
	
	/**
	 * Permet d'obtenir l'instance du singleton de l'objet Vide.
	 * @return L'instance de l'objet Vide.
	 */
	public static CaseRobot getInstance() {
		if(caseRobot==null)
			caseRobot = new CaseRobot();
		return caseRobot;
	}
	
	/**
	 * Constructeur de l'objet Vide.
	 */
	private CaseRobot() {
		super('@', "Case robot");
	}
}
