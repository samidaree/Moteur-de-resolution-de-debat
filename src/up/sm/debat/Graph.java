package up.sm.debat;

public class Graph {
	private int nbSommets; 
	private int [][] matriceAdjacence; 
	
	public Graph (int nbSommets) {
		this.nbSommets = nbSommets; 
		matriceAdjacence = new int [nbSommets][nbSommets]; 
	}
	
	public Graph() {
		
	}
	
	public int getNbSommets() {
		return this.nbSommets; 
	}
	
	public int[][] getMatriceAdjacence(){
		return this.matriceAdjacence; 
	}
	
	public void setNbSommets(int nbSommets) {
		this.nbSommets = nbSommets; 
		matriceAdjacence = new int [nbSommets][nbSommets] ; 
	}
	
	public void ajouterArete(int s1, int s2) {
		matriceAdjacence[s1][s2]= 1; 
	}
	
	public void afficherGraph() {
		
	}
}