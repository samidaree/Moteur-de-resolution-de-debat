package up.sm.debat;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Classe qui implémente les interfaces homme-machine
 */
public class InterfaceHM {

	private Debat d ;

	/**
	 * Constructeur pour la saisie manuelle de la phase 1
	 */
	public InterfaceHM () {
		d = new Debat();
	}

	/**
	 * Constructeur pour la saisie par fichier de la phase 2
	 * @param s
	 */
	public InterfaceHM (String s) {
		d = new Debat(s);
	}

	public Debat getD(){
		return d;
	}

	/**
	 * Affiche le premier menu à l'utilisateur
	 * @throws InputMismatchException
	 */
	public void menu1() throws InputMismatchException {
		int x = -1;

		do {
			try {
				Scanner saisie = new Scanner(System.in);
				System.out.println("Que souhaitez vous faire ? ");
				System.out.println("1) Ajouter une contradiction");
				System.out.println("2) Afficher tous les arguments");
				System.out.println("3) Générer une image du graphe représentant le débat");
				System.out.println("4) Fin");

				x = saisie.nextInt();
				switch(x) {
					case 1 : d.ajouterContradiction();
						break ;
					case 2 : d.afficherListeArguments();
						break ;
					case 3 : d.afficherDebat();
						d.genererImage();
						break ;
					case 4 :
						break ;
					default : System.out.println("Vous devez saisir 1,2, 3 ou 4 !") ;
				}
			}
			catch (InputMismatchException e ) {
				System.out.println("Veuillez entrer un nombre !");
			}
		} while (x!=4 || x==-1);
	}



	/**
	 * Permet d'afficher le second menu de la phase 1
	 * @throws InputMismatchException
	 */
	public void menu2() throws InputMismatchException {

		int x = -1;
		do {
			try {
				if (d.getAllSolutions().isEmpty())
					for (int i =1; i<=d.getGraph().getNbSommets(); i++){
						d.afficheCombinaisons(i);
					}
				if (d.getSolutionsPreferees().isEmpty())
					d.setSolutionsPreferees();
				Scanner saisie = new Scanner(System.in);

				System.out.println("1) Ajouter un argument");
				System.out.println("2) Retirer un argument");
				System.out.println("3) Verifier si la solution est admissible");
				System.out.println("4) Verifier si la solution est préférée");
				System.out.println("5) Afficher toutes les solutions admissibles");
				System.out.println("6) Afficher toutes les solutions préférées");
				System.out.println("7) Fin");

				x = saisie.nextInt();
				switch(x) {
					case 1:
						d.ajouterArgumentSolution();
						break;
					case 2:
						d.retirerArgument();
						break;
					case 3:
						d.estPasAdmissible(d.getsolution());
						d.affichersolution();
						break;
					case 4:

						d.verifPref(d.getsolution());
						d.affichersolution();
						break ;
					case 5 :

						d.afficheAllSolutions();

						break ;
					case 6 :

						d.afficherSolutionsPreferees();
						break ;
					case 7 : d.estPasAdmissible(d.getsolution());
						d.affichersolution();
						break ;
					default : System.out.println("Vous devez saisir 1, 2, 3, 4, 5, 6 ou 7 !") ;
				}
			}

			catch (InputMismatchException e) {
				System.out.println("Veuillez entrer un nombre");
			}
		} while (x!=7 || x==-1);

	}

	/**
	 * Affiche le menu pour la saisie par fichier
	 * @throws InputMismatchException
	 */
	public void menu3() throws InputMismatchException {

		int x = -1;
		do {
			try {
				if (d.getAllSolutions().isEmpty())
					for (int i =1; i<=d.getGraph().getNbSommets(); i++){
						d.afficheCombinaisons(i);
					}
				if (d.getSolutionsPreferees().isEmpty())
					d.setSolutionsPreferees();
				Scanner saisie = new Scanner(System.in);
				System.out.println("\n0) Générer une image du graphe représentant le débat");
				System.out.println("1) Chercher une solution admissible");
				System.out.println("2) Chercher une solution préférée");
				System.out.println("3) Sauvegarder la solution");
				System.out.println("4) Afficher toutes les solutions admissibles");
				System.out.println("5) Afficher toutes les solutions préférées");
				System.out.println("6) Fin\n");

				x = saisie.nextInt();
				switch(x) {
					case 0 : d.afficherDebat();
						d.genererImage();
					break ;
					case 1:
						d.chercherSolutionAdmissible();
						break;
					case 2:
						d.chercherSolutionPreferee();
						break;
					case 3:
						d.sauvegarderSolution();
						break;

					case 4 :
						d.afficheAllSolutions();
						break ;
					case 5 :
						d.afficherSolutionsPreferees();
						break ;
					case 6 : d.estPasAdmissible(d.getsolution());
						d.affichersolution();
						break ;
					default : System.out.println("Vous devez saisir 1, 2, 3, 4, 5, ou 6 !") ;
				}
			}

			catch (InputMismatchException e) {
				System.out.println("Veuillez entrer un nombre");
			}
		} while (x!=6 || x==-1);

	}

}


class TestInterface{
	public static void main(String []args) {

		if (args.length==0) {
			InterfaceHM i = new InterfaceHM ();
			i.getD().saisirNbArguments();
			i.getD().saisirArgument();
			i.menu1();
			i.menu2();
		}
		else {
			InterfaceHM i = new InterfaceHM (args[0]);
			if (i.getD().initGraphFile() == 1)
				i.menu3();

		}

	}
}