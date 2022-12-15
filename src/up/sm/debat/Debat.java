
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
	private ArrayList <ArrayList<Integer>> allSolutions ; 
	
	private ArrayList <Argument> listeArguments; 
	private Path p ; 
	/**
	 * Class constructor 
	 */
	public Debat() {
		this.g = new Graph(); 
		solution = new <Integer> ArrayList(); 
		listeArguments = new ArrayList <Argument>(); 
		allSolutions = new ArrayList (); 
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
	
	public void supprimerEspace() {
		for (int i = 0 ; i<listeArguments.size(); i++) {
			if (this.listeArguments.get(i).getNom().contains(" "))
				this.listeArguments.get(i).setNom(listeArguments.get(i).getNom().replace(" ", "")); 
		}
	}
	
	public boolean contientArgumentVide() {
		for (int i  = 0;  i<listeArguments.size() ; i++) {
				if (listeArguments.get(i).getNom().isEmpty()) {
					System.out.println("Vous ne pouvez pas saisir d'argument vide !  ");
					listeArguments.get(i).setCptArg(0); 
					listeArguments.clear();
					
					return true ; 
				}
			}
		return false; 
	}
	public boolean contientDoublon() {
		for (int i  = 0;  i<listeArguments.size() ; i++) {
			for (int j = i+1 ; j<listeArguments.size(); j++) {
				if (listeArguments.get(i).getNom().equals(listeArguments.get(j).getNom()) && i!=j) {
					System.out.println("Vous ne pouvez pas saisir 2 mêmes arguments ! ");
					listeArguments.get(i).setCptArg(0); 
					listeArguments.clear();
					
					return true ; 
				}
			}
		}
		return false; 
	}
	
	public void afficherListeArguments() {
		for (Argument a : listeArguments ) {
			System.out.println(a.getNom()); 
		}
	}
	
	public void afficherListeSolution(ArrayList <Integer> liste) {
		for (Integer i : liste) {
			System.out.println(getNameFromId(i)); 
		}
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
	public boolean estPasAdmissible(ArrayList <Integer> sol) {
		boolean contradiction = false; 
		if (sol.size()==0) {
			System.out.println("Votre solution est admissible !"); 
		}
		if (sol.size()==1) {
			StringBuffer sb = new StringBuffer ("Votre solution est admissible !"); 
			StringBuffer sb2 = new StringBuffer (); 
			for (int i = 0; i<g.getNbSommets() && sb.charAt(sb.length()-1) == '!'; i++) {
				if (g.getMatriceAdjacence()[i][sol.get(0)] == 1) {
					if (g.getMatriceAdjacence()[sol.get(0)][i] !=1) {
						contradiction  =true; 
						sb.replace(0,sb.length(),"Votre solution n'est pas admissible"); 
						sb2.replace(0,sb.length(), getNameFromId(i) + " Contredit " + getNameFromId(solution.get(0)) + " et " + getNameFromId(solution.get(0)) + " ne se défend pas lui même" );
					}
				}
			}
			System.out.println(sb2); 
			System.out.println(sb); 
		}
		else if (sol.size()>1) {
			contradiction = false; 
			for (int i = 0; i<sol.size() && contradiction == false; i++) {
				for (int j = 0 ; j<g.getNbSommets(); j++) {
					// Si on trouve un sommet du graphe qui contredit un élément de l'ensemble S
					if (g.getMatriceAdjacence()[j][sol.get(i)] == 1) {
						// Si ce sommet est aussi dans l'ensemble S
						if (sol.contains(j)) {
							contradiction = true; 
							System.out.println("Contradiction interne : "+  getNameFromId(sol.get(i))+" et " + getNameFromId(j) + " se contredisent."); 
							return contradiction ; 
						}
							
						else {
							contradiction = true ; 
							StringBuffer sb = new StringBuffer (getNameFromId(j) + " contredit " + getNameFromId(sol.get(i)) + " et " + getNameFromId(sol.get(i))+ " ne se défend pas."); 
							for (int k = 0; k<sol.size(); k++)
								// Cas ou l'argument se défend contre celui qui l'a contredit en l'occurence j 
								if (g.getMatriceAdjacence()[sol.get(k)][j] == 1) {
									System.out.println("test"); 
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
	
	/**
	 * Permet aussi de verifier l'admissibilité mais sans les affichages en sortie standard
	 * @param sol
	 * @return
	 */
	public boolean estPasAdmissible2(ArrayList <Integer> sol) {
		boolean contradiction = false; 
		
		if (sol.size()==1) {
	
			for (int i = 0; i<g.getNbSommets(); i++) {
				if (g.getMatriceAdjacence()[i][sol.get(0)] == 1) {
					if (g.getMatriceAdjacence()[sol.get(0)][i] !=1) {
						contradiction  =true; 
						
					}
				}
			}
 
		}
		else if (sol.size()>1) {
			contradiction = false; 
			for (int i = 0; i<sol.size() && contradiction == false; i++) {
				for (int j = 0 ; j<g.getNbSommets(); j++) {
					// Si on trouve un sommet du graphe qui contredit un élément de l'ensemble S
					if (g.getMatriceAdjacence()[j][sol.get(i)] == 1) {
						// Si ce sommet est aussi dans l'ensemble S
						if (sol.contains(j)) {
							contradiction = true; 
						}
							
						else {
							contradiction = true ; 
							for (int k = 0; k<sol.size(); k++)
								// Cas ou l'argument se défend contre celui qui l'a contredit en l'occurence j 
								if (g.getMatriceAdjacence()[sol.get(k)][j] == 1) {
									contradiction = false; 

								}
						}
					}
				}
			}
	
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
	
	
	public void combinaison(int [] tab, int n, int r, int indice, int [] temp, int i) {
		ArrayList <Integer> temp2 = new ArrayList (); 
		if (indice == r) {
			for (int j = 0 ;j<r ; j++) {
				//System.out.println(temp[j] + " "); 
				temp2.add(temp[j]); 
			}
			if (estPasAdmissible2(temp2) == false)
				this.allSolutions.add(temp2); 
			//temp2.clear();
			return ; 
		}
		
		 
		
		if (i>=n )
			return ; 
		
		temp[indice] = tab[i];
		combinaison(tab, n,r,indice +1, temp, i+1); 
		
		combinaison (tab, n, r, indice, temp, i+1); 
			
	}
	
	
	
	public void afficheCombinaisons(int r) {
		int []temp = new int [r];  
		int tab[] = new int[this.listeArguments.size()]; 
		
		for (int i =0 ; i<tab.length; i++) 
			tab[i]= i; 
		
		combinaison(tab,tab.length, r, 0,temp, 0);
		
	}
	
	public void afficheAllSolutions() {
		System.out.println("∅"); 
		StringBuffer sb = new StringBuffer(); 
		for (int i = 0; i<allSolutions.size();i++) {
			for (int j = 0; j<allSolutions.get(i).size();j++ ) {
				if (j!=allSolutions.get(i).size()-1)
					sb.append(getNameFromId(allSolutions.get(i).get(j))).append(", ");
				else 
					sb.append(getNameFromId(allSolutions.get(i).get(j)));

			}
			System.out.println(sb + "\n");
			sb.replace(0, sb.length()+1, ""); 
		}
		//System.out.println(allSolutions); 
	}
	
	public boolean verifPref(ArrayList <Integer> sol) {
		
		if (estPasAdmissible2(sol)==true) {
			System.out.println("Votre solution n'est ni admissible ni préférée"); 
			return false; 
		}
		ArrayList <Integer> temp = new ArrayList(); 
		temp.addAll(sol); 
		for (int i = 0 ; i<listeArguments.size(); i++) {
			if (!sol.contains(listeArguments.get(i).getId())) {
				temp.add(listeArguments.get(i).getId()); 
				// Un ensemble de solution plus grand est admissible 
				if (estPasAdmissible2(temp) == false)  {
					afficherListeSolution(temp); 
					System.out.println("La solution n'est pas préférée"); 

					return false ; 
				}
				for (int j= i+1 ; j<listeArguments.size(); j++) {
					if (!sol.contains(listeArguments.get(j).getId())) {
						temp.add(listeArguments.get(j).getId()); 
						// Un ensemble de solution plus grand est admissible 
						if (estPasAdmissible2(temp) == false) {
							afficherListeSolution(temp); 
							System.out.println("La solution n'est pas préférée"); 
							return false ; 
						}
						
					}
				temp.clear(); 
				temp.addAll(sol); 
				}
			}
		}
		System.out.println("La solution est préférée");
		return true; 

	}
}