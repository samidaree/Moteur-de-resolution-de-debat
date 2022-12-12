
package up.sm.debat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Cette classe implémente les débats 
 * Author : Sami BOUFASSA & Moncef BOUHABEL 
 */

/* 
 * Créer une classe interface homme machinne (saisir argument, saisir contradictions, menus...) 
 * Créer une classe solution et une classe graph 
 * Créer une classe argument qui contient l'id de l'argument 
 * 
 */
public class Debat {

	private Graph g ; 
	private ArrayList <Integer> solutions; 
	private ArrayList <Argument> listeArguments; 
	
	/**
	 * Class constructor 
	 */
	public Debat() {
		this.g = new Graph(); 
		solutions = new <Integer> ArrayList(); 
		listeArguments = new ArrayList <Argument>(); 
	}
	
	
	public void addArgument(Argument a) {
		this.listeArguments.add(a); 
	}
	
	public String getNameFromId(int id) {
		boolean trouve = false; 
		String s = null ; 
		for (int i = 0; i<listeArguments.size()&&trouve==false; i++ ) {
			if (listeArguments.get(i).getId() == id) {
				trouve = true ; 
				s = listeArguments.get(i).getNom(); 
			}
		}
		
		return s ; 
	}
	
	
	public Graph getGraph() {
		return this.g ; 
	}
	
	public ArrayList<Argument> getListeArguments(){
		return this.listeArguments ; 
	}
	
	public ArrayList <Integer> getSolutions(){
		return this.solutions ; 
	}
	
	/**
	 * Saisit l'ordre du graphe 
	 * @param nbSommets
	 */
	public void setG(int nbSommets) {
		g.setNbSommets(nbSommets);
	}
	
	
	
	/**
	 * Permet de vérifier si les arguments saisis par l'utilisateur forment une solution admissible
	 */
	public void verifierSolution() {
		if (solutions.size()==0) {
			System.out.println("Votre solution est admissible !"); 
		}
		if (solutions.size()==1) {
			StringBuffer sb = new StringBuffer ("Votre solution est admissible !"); 
			StringBuffer sb2 = new StringBuffer (); 
			for (int i = 0; i<g.getNbSommets() && sb.charAt(sb.length()-1) == '!'; i++) {
				if (g.getMatriceAdjacence()[i][solutions.get(0)] == 1) {
					if (g.getMatriceAdjacence()[solutions.get(0)][i] !=1) {
						sb.replace(0,sb.length(),"Votre solution n'est pas admissible"); 
						sb2.replace(0,sb.length(), getNameFromId(i) + " Contredit " + getNameFromId(solutions.get(0)) + " et " + getNameFromId(solutions.get(0)) + " ne se défend pas lui même" );
					}
				}
			}
			System.out.println(sb2); 
			System.out.println(sb); 
		}
		else if (solutions.size()>1) {
			boolean contradiction = false; 
			for (int i = 0; i<this.solutions.size() && contradiction == false; i++) {
				for (int j = 0 ; j<g.getNbSommets(); j++) {
					// Si on trouve un sommet du graphe qui contredit un élément de l'ensemble S
					if (g.getMatriceAdjacence()[j][solutions.get(i)] == 1) {
						// Si ce sommet est aussi dans l'ensemble S
						if (solutions.contains(j)&& solutions.contains(i)) {
							contradiction = true; 
							System.out.println("Contradiction interne : "+  getNameFromId(solutions.get(i))+" et " + getNameFromId(j) + " se contredisent."); 
						}
							
						else {
							contradiction = true ; 
							StringBuffer sb = new StringBuffer (getNameFromId(j) + " contredit " + getNameFromId(solutions.get(i)) + " et " + getNameFromId(solutions.get(i))+ " ne se défend pas."); 
							for (int k = 0; k<solutions.size(); k++)
								// Cas ou l'argument se défend contre celui qui l'a contredit en l'occurence j 
								if (g.getMatriceAdjacence()[solutions.get(k)][j] == 1) {
									contradiction = false; 
									sb.replace(0, sb.length(), "") ;

								}
							System.out.println(sb); 
						}
					}
				}
			}
			if (contradiction == false)
				System.out.println("Votre solution est admissible ! "); 
			else 
				System.out.println("Votre solution n'est pas admissible ! "); 
		}
		
		
	}
	
	
	

}