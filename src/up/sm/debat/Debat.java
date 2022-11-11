
package up.sm.debat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Cette classe implémente les débats 
 * Author : Sami BOUFASSA & Moncef BOUHABEL 
 */

public class Debat {

	private Graph g ; 
	private ArrayList <Integer> solutions; 
	
	/**
	 * Class constructor 
	 */
	public Debat() {
		this.g = new Graph(); 
		solutions = new <String> ArrayList(); 
	}
	
	/**
	 * Saisit l'ordre du graphe 
	 * @param nbSommets
	 */
	public void setG(int nbSommets) {
		g.setNbSommets(nbSommets);
	}
	
	public void afficherDebat() {
		try {
			System.out.println(this.g.afficherGraph()); 
		}
		catch (IOException e){
			System.out.println("probleme affichage graphe"); 
		}
	}
	
	/**
	 * Permet à l'utilisateur de saisir le nombre d'arguments / l'odre du graphe 
	 */
	public void saisieNbArguments() {
		System.out.println("Saisissez le nombre d'arguments"); 
		int x ; 
		Scanner saisie = new Scanner(System.in); 
		x= saisie.nextInt(); 
		this.g.setNbSommets(x);
		//saisie.close();		
	}
	
	/**
	 * Permet à l'utilisateur d'ajouter les contradictions / arêtes du graphe 
	 */
	public void ajouterContradiction() {
		System.out.println("Saisissez 2 arguments, le premier étant celui qui contredit le deuxième :"); 
		Scanner saisie = new Scanner(System.in); 
		String s1 = saisie.nextLine(); 
		String s2 = saisie.nextLine(); 
		this.g.ajouterArete(Character.getNumericValue(s1.charAt(1)) -1, Character.getNumericValue(s2.charAt(1))-1);
	}
	
	/**
	 * Affiche le premier menu à l'utilisateur 
	 */
	public void menu1() {
		int x ; 
		Scanner saisie = new Scanner(System.in); 
		
		do {
			System.out.println("Que souhaitez vous faire ? "); 
			System.out.println("1) Ajouter une contradiction"); 
			System.out.println("2) Afficher le débat"); 
			System.out.println("3) Fin"); 
			
			x = saisie.nextInt(); 
			switch(x) {
			case 1 : ajouterContradiction(); 
				break ; 
			case 2 : afficherDebat(); 
				break ; 
			case 3 : 
				break ; 
			default : System.out.println("Vous devez saisir 1,2 ou 3 !") ; 
			}
			
		} while (x!=3); 
		
		
	}
	
	/**
	 * Permet d'ajouter un argument dans l'ensemble des solutions 
	 */
	public void ajouterArgument() {
		System.out.println("Ajoutez un argument dans la solution : ");
		
		Scanner saisie = new Scanner(System.in); 
		String argument = saisie.nextLine(); 
		this.solutions.add(Character.getNumericValue(argument.charAt(1))); 
		afficherSolutions() ; 
	}
	
	/**
	 * Permet de retirer un argument de l'ensemble des solutions 
	 */
	public void retirerArgument() {
		System.out.println("Saisissez l'argument que vous souhaitez retirer : ");
		Scanner saisie = new Scanner(System.in); 
		String argument = saisie.nextLine(); 
		solutions.remove(Integer.valueOf(Character.getNumericValue(argument.charAt(1))));
		afficherSolutions() ; 
	}
	
	/**
	 * Permet de vérifier si les arguments saisis par l'utilisateur forment une solution admissible
	 */
	public void verifierSolution() {
		if (solutions.size()==0 || solutions.size()==1) {
			System.out.println("Votre solution est admissible !"); 
			return ; 
		}
		boolean contradiction = false; 
		for (int i = 0; i<this.solutions.size() && contradiction == false; i++) {
			for (int j = 0 ; j<g.getNbSommets(); j++) {
				// Si on trouve un sommet du graphe qui contredit un élément de l'ensemble S
				if (g.getMatriceAdjacence()[j][solutions.get(i)-1] == 1) {
					// Si ce sommet est aussi dans l'ensemble S
					if (solutions.contains(j+1)) {
						contradiction = true; 
						//System.out.println("Contradiction interne" + j); 
					}
						
					else {
						contradiction = true ; 
						for (int k = 0; k<solutions.size(); k++)
							// Cas ou l'argument se défend contre celui qui l'a contredit en l'occurence k 
							if (g.getMatriceAdjacence()[solutions.get(k)-1][j] == 1)
								contradiction = false; 
						
					}
				}
			}
		}
		if (contradiction == false)
			System.out.println("Votre solution est admissible ! "); 
		else 
			System.out.println("Votre solution n'est pas admissible ! "); 
		
		afficherSolutions(); 
	}
	
	/**
	 * Permet d'afficher l'ensemble des solutions saisi par l'utilisateur
	 */
	public void afficherSolutions() {
		System.out.println("Voici la solution que vous avez rentré : "); 
		//System.out.println(this.solutions);
		StringBuffer sb = new StringBuffer("{");
		for (int i = 0; i<solutions.size();i++) {
			if (i==solutions.size()-1)
				sb.append("A").append(solutions.get(i)); 
			else 
				sb.append("A").append(solutions.get(i)).append(","); 
		}
		sb.append("}") ; 
		System.out.println(sb); 
		
	}
	
	/**
	 * Permet d'afficher le menu2 
	 */
	public void menu2() {
	
		int x ; 
		Scanner saisie = new Scanner(System.in); 
		
		do {
			System.out.println("1) Ajouter un argument"); 
			System.out.println("2) Retirer un argument"); 
			System.out.println("3) Verifier la solution"); 
			System.out.println("4) Fin"); 
			
			x = saisie.nextInt(); 
			switch(x) {
			case 1 : ajouterArgument(); 
				break ; 
			case 2 : retirerArgument(); 
				break ; 
			case 3 : verifierSolution() ;
				break ; 
			case 4 : verifierSolution(); 
				break ; 
			default : System.out.println("Vous devez saisir 1, 2, 3 ou 4 !") ; 
			}
			
		} while (x!=4); 
		
	}

	public static void main(String[] args) {
			Debat d = new Debat(); 
			d.saisieNbArguments(); 
			d.menu1();
			d.menu2(); 
			
	}

}