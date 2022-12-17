package up.sm.debat;

/**
 * Classe qui implémente les arguments et permet de les identifier de facon unique grace à un ID
 * Facilite la manipulation des arguments en utilisant des entiers plutôt que des chaines de caractères
 */
public class Argument {
	private int id ; 
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
	
	public void setCptArg(int cpt){
		cptArg = cpt; 
	}
	
	public int getId() {
		return this.id ; 
	}
	
	public String getNom() {
		return nom ;
	}
}
