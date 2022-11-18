
package up.sm.debat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

/**
 * Cette classe implémente les graphes en Java 
 * @author Sami BOUFASSA & Moncef BOUHABEL 
 */

public class Graph {
	private int nbSommets; 
	private int [][] matriceAdjacence; 
	
	/**
	 * Constructeur 
	 * @param nbSommets Ordre du graphe 
	 */
	public Graph (int nbSommets) {
		this.nbSommets = nbSommets; 
		matriceAdjacence = new int [nbSommets][nbSommets]; 
	}
	
	/**
	 * Constructeur vide 
	 */
	public Graph() {
		
	}
	/**
	 * Getter ordre du graphe 
	 * @return le nombre de sommets du graphe 
	 */
	public int getNbSommets() {
		return this.nbSommets; 
	}
	
	/**
	 * Getter matrice d'adjacence 
	 * @return la matrice d'adjacence du graphe 
	 */
	public int[][] getMatriceAdjacence(){
		return this.matriceAdjacence; 
	}
	
	/**
	 * Modifie l'ordre du graphe 
	 * @param nbSommets nombre de sommets du graphe 
	 */
	public void setNbSommets(int nbSommets) {
		this.nbSommets = nbSommets; 
		matriceAdjacence = new int [nbSommets][nbSommets] ; 
	}
	
	/**
	 * Ajoute une arête dans le graphe 
	 * @param s1 Sommet à la première extrémité de l'arête 
	 * @param s2 Sommet à la deuxième extrémité de l'arête 
	 */
	public void ajouterArete(int s1, int s2) throws ArrayIndexOutOfBoundsException {
		matriceAdjacence[s1][s2]= 1; 
	}
	
	/** 
	 * Affiche le graphe 
	 * @throws IOException 
	 */
	public String afficherGraph() throws IOException {
		StringBuffer sb = new StringBuffer ("digraph debat {\n"); 
		for (int i=0; i<nbSommets;i++) {
			for (int j= 0; j<nbSommets; j++)
				if(matriceAdjacence[i][j]==1) {
					sb.append("A").append(i+1).append("->").append("A").append(j+1).append(";\n"); 
				}
		}
		sb.append("}"); 
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("graph.txt")); 
			bw.write(sb.toString());
			bw.close(); 
			
	
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
	
		return sb.toString(); 
	}
}