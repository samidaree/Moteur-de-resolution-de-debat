package up.sm.debat;

import java.util.ArrayList;
import java.util.Scanner;

public class Debat {
	private Graph g ; 
	private ArrayList <String> solutions; 
	
	public Debat() {
		this.g = new Graph(); 
		solutions = new <String> ArrayList(); 
	}
	
	public void setG(int nbSommets) {
		g.setNbSommets(nbSommets);
	}
	
	public void saisieNbArguments() {
		System.out.println("Saisissez le nombre d'arguments"); 
		int x ; 
		Scanner saisie = new Scanner(System.in); 
		x= saisie.nextInt(); 
		this.g.setNbSommets(x);
		//saisie.close();		
	}
	
	public void ajouterContradiction() {
		System.out.println("Saisissez 2 arguments, le premier étant celui qui contredit le deuxième :"); 
		Scanner saisie = new Scanner(System.in); 
		String s1 = saisie.nextLine(); 
		String s2 = saisie.nextLine(); 
		this.g.ajouterArete(Character.getNumericValue(s1.charAt(1)), Character.getNumericValue(s2.charAt(1)));
	}
	
	public void menu1() {
		int x ; 
		Scanner saisie = new Scanner(System.in); 
		
		do {
			System.out.println("Que souhaitez vous faire ? "); 
			System.out.println("1) Ajouter une contradiction"); 
			System.out.println("2) Fin"); 
			
			x = saisie.nextInt(); 
			switch(x) {
			case 1 : ajouterContradiction(); 
				break ; 
			case 2 : 
				break ; 
			default : System.out.println("Vous devez saisir 1 ou 2 !") ; 
			}
			
		} while (x!=2); 
		
		System.out.println("1) Ajouter une contradiction"); 
		System.out.println("2) Fin"); 
		
	}
	
	public void ajouterArgument() {
		System.out.println("Ajoutez un argument dans la solution : ");
		
		Scanner saisie = new Scanner(System.in); 
		String argument = saisie.nextLine(); 
		this.solutions.add(argument); 
	}
	
	public void retirerArgument() {
		System.out.println("Saisissez l'argument que vous souhaitez retirer : ");
		Scanner saisie = new Scanner(System.in); 
		String argument = saisie.nextLine(); 
		solutions.remove(argument);
	}
	
	public void verifierSolution() {
		System.out.println("Voici la solution que vous avez rentré : "); 
		System.out.println(this.solutions);
		
	}
	
	public void menu2() {
		System.out.println("1) Ajouter un argument"); 
		System.out.println("2) Retirer un argument"); 
		System.out.println("3) Verifier la solution"); 
		System.out.println("4) Fin"); 
	}

	public static void main(String[] args) {
			Debat d = new Debat(); 
			d.saisieNbArguments(); 
			d.menu1();
			
		
	}

}