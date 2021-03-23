package equipements;

public class Base extends Case {
	private static Base base = null;
	
	public static Base getInstance() {
		if(base==null)
			base = new Base();
		return base;
	}
	
	private Base() {
		super('@', "Base");
	}
}
