package up.sm.debat;

public class Argument {
	private final int id ; 
	private String nom ; 
	private static int cptArg = 0; 
	
	public Argument () {
		this.id = cptArg ;
		cptArg++; 
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