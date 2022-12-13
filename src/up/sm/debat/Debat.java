
package up.sm.debat;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Cette classe implémente les débats 
 * Author : Sami BOUFASSA & Moncef BOUHABEL 
 */


public class Debat {

	private Graph g ; 
	private ArrayList <Integer> solution; 
	private ArrayList <ArrayList<Integer>> listeSolutions ; 
	private ArrayList <Argument> listeArguments; 
	private Path p ; 
	/**
	 * Class constructor 
	 */
	public Debat() {
		this.g = new Graph(); 
		solution = new <Integer> ArrayList(); 
		listeArguments = new ArrayList <Argument>(); 
	}
	
	public Debat(String s) {
		this.g = new Graph(); 
		solution = new <Integer> ArrayList(); 
		listeArguments = new ArrayList <Argument>(); 
		p = Paths.get(s); 
		
	}
	
	public void addArgument(Argument a) {
		this.listeArguments.add(a); 
	}
	
	public int getIdFromName(String s) {
		int id = -1; 
		boolean trouve = false ; 
		for (int i = 0 ; i<listeArguments.size() && trouve == false; i++) {
			if (listeArguments.get(i).getNom().equals(s)) {
				id = listeArguments.get(i).getId(); 
				trouve= true; 
			}
		}
		
		return id; 
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
	
	public ArrayList <Integer> getsolution(){
		return this.solution ; 
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
	public boolean testerContradiction() {
		boolean contradiction = false; 
		if (solution.size()==0) {
			System.out.println("Votre solution est admissible !"); 
		}
		if (solution.size()==1) {
			StringBuffer sb = new StringBuffer ("Votre solution est admissible !"); 
			StringBuffer sb2 = new StringBuffer (); 
			for (int i = 0; i<g.getNbSommets() && sb.charAt(sb.length()-1) == '!'; i++) {
				if (g.getMatriceAdjacence()[i][solution.get(0)] == 1) {
					if (g.getMatriceAdjacence()[solution.get(0)][i] !=1) {
						contradiction  =true; 
						sb.replace(0,sb.length(),"Votre solution n'est pas admissible"); 
						sb2.replace(0,sb.length(), getNameFromId(i) + " Contredit " + getNameFromId(solution.get(0)) + " et " + getNameFromId(solution.get(0)) + " ne se défend pas lui même" );
					}
				}
			}
			System.out.println(sb2); 
			System.out.println(sb); 
		}
		else if (solution.size()>1) {
			contradiction = false; 
			for (int i = 0; i<this.solution.size() && contradiction == false; i++) {
				for (int j = 0 ; j<g.getNbSommets(); j++) {
					// Si on trouve un sommet du graphe qui contredit un élément de l'ensemble S
					if (g.getMatriceAdjacence()[j][solution.get(i)] == 1) {
						// Si ce sommet est aussi dans l'ensemble S
						if (solution.contains(j)&& solution.contains(i)) {
							contradiction = true; 
							System.out.println("Contradiction interne : "+  getNameFromId(solution.get(i))+" et " + getNameFromId(j) + " se contredisent."); 
						}
							
						else {
							contradiction = true ; 
							StringBuffer sb = new StringBuffer (getNameFromId(j) + " contredit " + getNameFromId(solution.get(i)) + " et " + getNameFromId(solution.get(i))+ " ne se défend pas."); 
							for (int k = 0; k<solution.size(); k++)
								// Cas ou l'argument se défend contre celui qui l'a contredit en l'occurence j 
								if (g.getMatriceAdjacence()[solution.get(k)][j] == 1) {
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
		
		return contradiction ; 
		
	}
	

	public void initGraphFile() {
		//System.out.println(p); 
		try {
			BufferedReader br = Files.newBufferedReader(p); 
			String s ;
			while((s = br.readLine())!=null) {
				System.out.println(s); 
				if (s.substring(0,8).equals("argument")) {
					String nom = s.substring(s.indexOf('(')+1, s.indexOf(')')); 
					nom = nom.replace(" ", ""); 
					Argument a = new Argument (nom); 
					addArgument(a); 
				}
				
				else if (s.substring(0,13).equals("contradiction")) {
					String firstArg = s.substring(s.indexOf('(')+1, s.indexOf(',')); 
					firstArg = firstArg.replace(" ", ""); 
					String secondArg = s.substring(s.indexOf(',')+1, s.indexOf(')')); 
					secondArg = secondArg.replace(" ", ""); 
					try {
						g.setNbSommets(Argument.cptArg);
						g.ajouterArete(getIdFromName(firstArg), getIdFromName(secondArg));
					}
					catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException e) {
						System.out.println("L'un des 2 arguments ou les 2 arguments " + firstArg + " , " + secondArg + " n'existe(nt) pas" ); 
						System.out.println("Votre fichier est mal formé : tous les arguments doivent être définis avant leur utilisation dans une contradiction !"); 
					}
					catch (NullPointerException e) {
						System.out.println("Votre fichier est mal formé : tous les arguments doivent être définis avant leur utilisation dans une contradiction !"); 
					}
				}
				else {
					System.out.println("Votre fichier est mal formé : il ne doit contenir que des arguments et des contradictions "); 
					System.exit(0);
				}
			}
			br.close(); 
		}
		catch (IOException e) {
			System.out.println("Fichier invalide"); 
		}
	}
	
	
	/*public void chercherSolutionAdmissible() {
		for (int i = 0 ; i<listeArguments.size(); i++) {
			for (int j = i ; j<listeArguments.size(); j++) {
				solution.add(listeArguments.get(i).getId()); 
				if (testerContradiction())
					solution.remove(listeArguments.get(i).getId()); 
			}
				solution.add(listeArguments.get(i).getId()); 
		}
	} */

}