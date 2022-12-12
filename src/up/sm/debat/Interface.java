package up.sm.debat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Interface {
	
	private Debat d ; 
	private ArrayList <Argument> listeArguments ; 
	
	public Interface () {
		d = new Debat(); 
		listeArguments = new ArrayList <Argument>(); 
	}
	
	public void saisirArgument() {
		System.out.println("Saisissez les arguments :") ; 
		for (int i = 0; i<d.getGraph().getNbSommets() ; i++) {
			Scanner saisie = new Scanner (System.in); 
			String s = saisie.nextLine(); 
			Argument a = new Argument(s); 
			listeArguments.add(a); 
		}

	}
	
	public void afficherDebat() {
		StringBuffer sb = new StringBuffer(); 
		sb.append("digraph debat {\n"); 
		for (int i = 0; i<listeArguments.size(); i++) {
			for (int j = 0 ; j<listeArguments.size(); j++) {
				if (d.getGraph().getMatriceAdjacence()[i][j] == 1) {
					sb.append(listeArguments.get(i).getNom()).append("->").append(listeArguments.get(j).getNom()).append(";"); 
				}
			}
		}
		sb.append("\n}"); 	
		System.out.println(sb) ; 

	}
	
	/**
	 * Permet à l'utilisateur d'ajouter les contradictions / arêtes du graphe 
	 */
	public void ajouterContradiction() {
		System.out.println("Saisissez 2 arguments, le premier étant celui qui contredit le deuxième :"); 
		Scanner saisie = new Scanner(System.in); 
		String s1 = saisie.nextLine(); 
		String s2 = saisie.nextLine(); 
		int id1 = -1 ;  
		int id2 = -2 ; 
		boolean trouve1 = false ; 
		boolean trouve2= false; 
		for (int i =0 ; i<listeArguments.size() && (trouve1 == false || trouve2==false); i++) {
			if (listeArguments.get(i).getNom().equals(s1)) {
				id1 = listeArguments.get(i).getId(); 
				trouve1= true ; 
			}
			if (listeArguments.get(i).getNom().equals(s2)) {
				id2 = listeArguments.get(i).getId(); 
				trouve2 = true; 
			}
		}
		try {
			this.d.getGraph().ajouterArete(id1, id2);
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
		int x = -1 ; 
		do {
			try {
				Scanner saisie = new Scanner(System.in); 
				x= saisie.nextInt(); 
				this.d.getGraph().setNbSommets(x);
			}
			catch (InputMismatchException e) {
				System.out.println("Veuillez entrer un nombre ! "); 
			}
		} while (x==-1); 
		
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
	

	
	/**
	 * Affiche le premier menu à l'utilisateur 
	 */
	public void menu1() throws InputMismatchException {
		int x = -1; 
		
			do {
				try {
					Scanner saisie = new Scanner(System.in); 
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
				}
				catch (InputMismatchException e ) {
					System.out.println("Veuillez entrer un nombre !"); 
				}
			} while (x!=3 || x==-1);
	}
		
	
	
	/**
	 * Permet d'afficher le menu2 
	 */
	public void menu2() throws InputMismatchException {
		
		int x = -1; 
			do {
				try {
					Scanner saisie = new Scanner(System.in); 

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
				}
				
				catch (InputMismatchException e) {
					System.out.println("Veuillez entrer un nombre"); 
				}
			} while (x!=4 || x==-1); 
			
	}
	
	
}


class TestInterface{
	public static void main(String []args) {
		Interface i = new Interface (); 
		i.saisirNbArguments();
		i.saisirArgument();
		i.menu1(); 
		i.menu2(); 
		
	}
}
