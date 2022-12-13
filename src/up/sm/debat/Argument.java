package up.sm.debat;

public class Argument {
	private final int id ; 
	private String nom ; 
	protected static int cptArg = 0; 
	
	public Argument (String s) {
		this.id = cptArg ;
		cptArg++; 
		this.nom = s; 
	}
	
	public void setNom(String s) {
		nom = s; 
	}
	
	public int getId() {
		return this.id ; 
	}
	
	public String getNom() {
		return nom ;
	}
}
