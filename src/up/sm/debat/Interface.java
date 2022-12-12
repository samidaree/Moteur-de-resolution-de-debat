package up.sm.debat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Interface {
	
	private Debat d ; 
	private ArrayList <Argument> listeArguments ; 
	
	public Interface () {
		d = new Debat(); 
		listeArguments = new ArrayList <Argument>(); 
	}
	
	/**
	 * Permet à l'utilisateur d'ajouter les contradictions / arêtes du graphe 
	 */
	public void ajouterContradiction() {
		System.out.println("Saisissez 2 arguments, le premier étant celui qui contredit le deuxième :"); 
		Scanner saisie = new Scanner(System.in); 
		String s1 = saisie.nextLine(); 
		String s2 = saisie.nextLine(); 
		Argument a = new Argument ( s1);
		Argument b = new Argument ( s2); 

		listeArguments.add(a); 
		listeArguments.add(b); 
		
		try {
			this.d.getGraph().ajouterArete(a.getId(), b.getId());
		}
		catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException e) {
			System.out.println("Cet argument n'existe pas !"); 
			System.out.println(e.getMessage()); 
		}
	
	}
	
	/**
	 * Permet à l'utilisateur de saisir le nombre d'arguments / l'ordre du graphe 
	 */
	public void saisirNbArguments() {
		System.out.println("Saisissez le nombre d'arguments"); 
		int x ; 
		Scanner saisie = new Scanner(System.in); 
		x= saisie.nextInt(); 
		this.d.getGraph().setNbSommets(x);
	}
	
	/**
	 * Permet d'ajouter un argument dans l'ensemble des solutions 
	 */
	public void ajouterArgument() {
		System.out.println("Ajoutez un argument dans la solution : ");
		
		Scanner saisie = new Scanner(System.in); 
		String argument = saisie.nextLine(); 
		boolean trouve = false; 
		for (int i = 0; i<listeArguments.size() && trouve == false ; i++) {
			if (listeArguments.get(i).getNom().equals(argument)) {
				this.d.getSolutions().add(listeArguments.get(i).getId()); 
				trouve = true; 
			}
		}
		if (trouve == false)
			System.out.println("Cet argument n'existe pas !"); 
		else 
			afficherSolutions() ; 
	}
	
	/**
	 * Permet de retirer un argument de l'ensemble des solutions 
	 */
	public void retirerArgument() {
		System.out.println("Saisissez l'argument que vous souhaitez retirer : ");
		Scanner saisie = new Scanner(System.in); 
		String argument = saisie.nextLine(); 
		boolean trouve = false; 
		for (int i = 0; i<listeArguments.size() && trouve == false ; i++) {
			if (listeArguments.get(i).getNom().equals(argument)) {
				this.d.getSolutions().remove(listeArguments.get(i).getId()); 
				trouve = true; 
			}
		}
		if (trouve == false)
			System.out.println("Cet argument n'existe pas !"); 
		else 
			afficherSolutions() ; 

	}
	
	/**
	 * Permet d'afficher l'ensemble des solutions saisies par l'utilisateur
	 */
	public void afficherSolutions() {
		System.out.println("Voici la solution que vous avez rentré : "); 
		//System.out.println(this.solutions);
		StringBuffer sb = new StringBuffer("{");
		for (int i = 0; i<this.d.getSolutions().size();i++) {
			if (i==this.d.getSolutions().size()-1)
				sb.append(listeArguments.get(i).getNom()); 
			else 
				sb.append(listeArguments.get(i).getNom()).append(","); 
		}
		sb.append("}") ; 
		System.out.println(sb); 
		
	}
	
	public void afficherDebat() {
		try {
			System.out.println(this.d.getGraph().afficherGraph()); 
		}
		catch (IOException e){
			System.out.println("probleme affichage graphe"); 
		}
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
			case 3 : d.verifierSolution() ;
					afficherSolutions();  
				break ; 
			case 4 : d.verifierSolution(); 
					afficherSolutions(); 
				break ; 
			default : System.out.println("Vous devez saisir 1, 2, 3 ou 4 !") ; 
			}
			
		} while (x!=4); 
		
	}
	
	
}


class TestInterface{
	public static void main(String []args) {
		Interface i = new Interface (); 
		i.saisirNbArguments();
		i.menu1(); 
		i.menu2();
	}
}
