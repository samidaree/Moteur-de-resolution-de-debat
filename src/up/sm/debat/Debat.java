
package up.sm.debat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Cette classe implémente les débats
 * Author : Sami BOUFASSA & Moncef BOUHABEL
 */


public class Debat {
	private Graph g;
	private ArrayList<Integer> solution;
	private ArrayList<ArrayList<Integer>> allSolutions;

	private ArrayList<Argument> listeArguments;
	private Path p;
	private ArrayList <ArrayList<Integer>> solutionsPreferees ;
	private static int cptSolutionAdmissible = 0;
	private static int cptSolutionPreferee = 0;

	/**
	 * Constructeur pour la saisie manuelle de la phase 1
	 */
	public Debat() {
		this.g = new Graph();
		solution = new <Integer>ArrayList();
		listeArguments = new ArrayList<Argument>();
		allSolutions = new ArrayList();
		solutionsPreferees = new ArrayList () ;
	}

	/**
	 * Constructeur pour la phase 2 dans le cas ou l'utilisateur saisit un chemin de fichier
	 * @param s
	 */
	public Debat(String s) {
		this.g = new Graph();
		solution = new <Integer>ArrayList();
		listeArguments = new ArrayList<Argument>();
		allSolutions = new ArrayList<>();
		solutionsPreferees = new ArrayList<>();
		p = Paths.get(s);

	}

	/**
	 * Ajouter un argument dans la liste des arguments
	 * @param a l'argument à ajouter
	 */
	public void addArgument(Argument a) {
		this.listeArguments.add(a);

	}

	/**
	 * Saisie au clavier des arguments
	 */
	public void saisirArgument() {

		do {
			System.out.println("Saisissez les arguments :") ;

			for (int i = 0; i<g.getNbSommets() ; i++) {

				Scanner saisie = new Scanner (System.in);
				String s = saisie.nextLine();
				Argument a = new Argument(s);
				addArgument(a);
				supprimerEspace();
			}
		} while (contientDoublon() == true || contientArgumentVide() == true) ;

	}

	/**
	 * Permet d'ecrire au format dot le graphe associé au débat
	 */
	public void afficherDebat() {
		StringBuffer sb = new StringBuffer();
		sb.append("digraph debat {\n");
		for (int i = 0; i<listeArguments.size(); i++) {
			for (int j = 0 ; j<listeArguments.size(); j++) {
				if (g.getMatriceAdjacence()[i][j] == 1) {
					sb.append(listeArguments.get(i).getNom()).append("->").append(listeArguments.get(j).getNom()).append(";\n");
				}
			}
		}
		sb.append("\n}");

		System.out.println(sb) ;
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("graph.dot"));
			bw.write(sb.toString());
			bw.close();
		}
		catch (IOException e){
			System.out.println("fichier invalide");
		}
	}

	/**
	 * Permet à l'utilisateur d'ajouter les contradictions / arêtes du graphe
	 */
	public void ajouterContradiction() {
		System.out.println("Saisissez 2 arguments, le premier étant celui qui contredit le deuxième :");
		Scanner saisie = new Scanner(System.in);
		String s1 = saisie.nextLine();
		String s2 = saisie.nextLine();
		int id1 = getIdFromName(s1) ;
		int id2 = getIdFromName(s2) ;
		try {
			this.g.ajouterArete(id1, id2);
		}
		catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException e) {
			System.out.println("Cet argument n'existe pas !");
			//System.out.println(e.getMessage());
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
				this.g.setNbSommets(x);
			}
			catch (InputMismatchException e) {
				System.out.println("Veuillez entrer un nombre ! ");
			}
		} while (x==-1);

	}

	/**
	 * Permet d'ajouter un argument dans l'ensemble des solution
	 */
	public void ajouterArgumentSolution() {
		System.out.println("Ajoutez un argument dans la solution : ");

		Scanner saisie = new Scanner(System.in);
		String argument = saisie.nextLine();
		if (solution.contains(getIdFromName(argument)))
			System.out.println("Cet argument est déjà dans la solution !");
		else
			this.solution.add(getIdFromName(argument));


		if (getIdFromName(argument) == -1)
			System.out.println("Cet argument n'existe pas !");
		else
			affichersolution() ;
	}

	/**
	 * Permet de retirer un argument de l'ensemble des solution
	 */
	public void retirerArgument() {
		System.out.println("Saisissez l'argument que vous souhaitez retirer : ");
		Scanner saisie = new Scanner(System.in);
		String argument = saisie.nextLine();
		try {
			this.solution.remove(solution.indexOf(getIdFromName(argument)));

		}
		catch (IndexOutOfBoundsException e) {
			System.out.println("Cet argument existe pas !") ;
		}

		affichersolution() ;

	}

	/**
	 * Permet d'afficher l'ensemble des solution saisies par l'utilisateur
	 */
	public void affichersolution() {
		System.out.println("Voici la solution que vous avez rentrée : ");
		//System.out.println(this.solution);
		StringBuffer sb = new StringBuffer("{");
		for (int i = 0; i<this.solution.size();i++) {
			if (i==this.solution.size()-1)
				sb.append(getNameFromId(solution.get(i)));
			else
				sb.append(getNameFromId(solution.get(i))).append(",");
		}
		sb.append("}") ;
		System.out.println(sb);

	}

	/**
	 * Permet de supprimer les espaces saisies par l'utilisateur et avoir un format d'arguments adapté
	 */
	public void supprimerEspace() {
		for (int i = 0; i < listeArguments.size(); i++) {
			if (this.listeArguments.get(i).getNom().contains(" "))
				this.listeArguments.get(i).setNom(listeArguments.get(i).getNom().replace(" ", ""));
		}
	}

	/**
	 * Permet d'empecher l'utilisateur de saisie un argument vide
	 * @return true si la liste des arguments contient un argument dont le nom est vide
	 */
	public boolean contientArgumentVide() {
		for (int i = 0; i < listeArguments.size(); i++) {
			if (listeArguments.get(i).getNom().isEmpty()) {
				System.out.println("Vous ne pouvez pas saisir d'argument vide !  ");
				listeArguments.get(i).setCptArg(0);
				listeArguments.clear();

				return true;
			}
		}
		return false;
	}

	/**
	 * Permet d'empêcher l'utilisateur de saisir des arguments avec le même nom
	 * @return true si la liste d'arguments contient au moins 2 arguments de même nom
	 */
	public boolean contientDoublon() {
		for (int i = 0; i < listeArguments.size(); i++) {
			for (int j = i + 1; j < listeArguments.size(); j++) {
				if (listeArguments.get(i).getNom().equals(listeArguments.get(j).getNom()) && i != j) {
					System.out.println("Vous ne pouvez pas saisir 2 mêmes arguments ! ");
					listeArguments.get(i).setCptArg(0);
					listeArguments.clear();

					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Affiche tous les arguments du débat
	 */
	public void afficherListeArguments() {
		for (Argument a : listeArguments) {
			System.out.println(a.getNom());
		}
	}

	/**
	 *
	 * @param liste liste de solutions saisies par l'utilisateur
	 * @return l'affichage de la liste
	 */
	public String afficherListeSolution(ArrayList<Integer> liste) {
		StringBuffer sb =new StringBuffer ();
		for (Integer i : liste) {
			if (liste.indexOf(i)==liste.size()-1)
				sb.append(getNameFromId(i)) ;
			else
				sb.append(getNameFromId(i)).append(", ");

		}
		System.out.println(sb) ;
		return sb.toString();
	}

	/**
	 * Permet d'obtenir l'id d'un argument à partir de son nom
	 * @param s le nom de l'argument dont on cherche l'id
	 * @return l'id de l'argument
	 */
	public int getIdFromName(String s) {
		int id = -1;
		boolean trouve = false;
		for (int i = 0; i < listeArguments.size() && trouve == false; i++) {
			if (listeArguments.get(i).getNom().equals(s)) {
				id = listeArguments.get(i).getId();
				trouve = true;
			}
		}

		return id;
	}

	/**
	 * Réciproquement, permet de récupérer le nom d'un argument à partir de son id
	 * @param id l'id de l'argument dont on cherche le nom
	 * @return le nom de l'argument
	 */
	public String getNameFromId(int id) {
		boolean trouve = false;
		String s = null;
		for (int i = 0; i < listeArguments.size() && trouve == false; i++) {
			if (listeArguments.get(i).getId() == id) {
				trouve = true;
				s = listeArguments.get(i).getNom();
			}
		}

		return s;
	}


	public Graph getGraph() {
		return this.g;
	}

	public ArrayList<Argument> getListeArguments() {
		return this.listeArguments;
	}


	public ArrayList<Integer> getsolution() {
		return this.solution;
	}

	/**
	 * Saisit l'ordre du graphe
	 *
	 * @param nbSommets
	 */
	public void setG(int nbSommets) {
		g.setNbSommets(nbSommets);
	}


	/**
	 * Permet de vérifier si les arguments saisis par l'utilisateur forment une solution admissible
	 */
	public boolean estPasAdmissible(ArrayList<Integer> sol) {
		boolean contradiction = false;
		if (sol.size() == 0) {
			System.out.println("Votre solution est admissible !");
		}
		if (sol.size() == 1) {
			StringBuffer sb = new StringBuffer("Votre solution est admissible !");
			StringBuffer sb2 = new StringBuffer();
			for (int i = 0; i < g.getNbSommets() && sb.charAt(sb.length() - 1) == '!' ; i++) {
				if (g.getMatriceAdjacence()[i][sol.get(0)] == 1) {
					if (g.getMatriceAdjacence()[sol.get(0)][i] != 1) {
						contradiction = true;
						sb.replace(0, sb.length(), "Votre solution n'est pas admissible");
						sb2.replace(0, sb.length(), getNameFromId(i) + " Contredit " + getNameFromId(solution.get(0)) + " et " + getNameFromId(solution.get(0)) + " ne se défend pas lui même");
					}
				}
			}
			System.out.println(sb2);
			System.out.println(sb);
		} else if (sol.size() > 1) {
			ArrayList<Integer>temp = new ArrayList<>();
			contradiction = false;
			for (int i = 0; i < sol.size() && contradiction == false; i++) {
				for (int j = 0; j < g.getNbSommets(); j++) {
					// Si on trouve un sommet du graphe qui contredit un élément de l'ensemble S
					if (g.getMatriceAdjacence()[j][sol.get(i)] == 1) {
						// Si ce sommet est aussi dans l'ensemble S
						if (sol.contains(j) && g.getMatriceAdjacence()[sol.get(i)][j] == 1) {
							contradiction = true;
							System.out.println("Contradiction interne : " + getNameFromId(sol.get(i)) + " et " + getNameFromId(j) + " se contredisent.");
							return contradiction;
						} else {
							contradiction = true;
							StringBuffer sb = new StringBuffer(getNameFromId(j) + " contredit " + getNameFromId(sol.get(i)) + " et " + getNameFromId(sol.get(i)) + " ne se défend pas.");
							temp.add(j) ;
							for (int k = 0; k < sol.size(); k++){
								// Cas ou l'argument se défend contre celui qui l'a contredit en l'occurence j
								if (g.getMatriceAdjacence()[sol.get(k)][j] == 1 ) {
									contradiction = false;
									sb.replace(0, sb.length(), "");
								}
							}

							System.out.println(sb);
						}
					}
				}
			}
			System.out.println(temp);

			for (Integer e : temp){
				contradiction = true;

				System.out.println(e);
				if (estContredit(e,sol)!=-1){
					System.out.println("dans le if");
					contradiction= false ;
					System.out.println(estContredit(e,sol));
				}
				else{
					return true;
				}

			}
			if (contradiction == false)
				System.out.println("Votre solution est admissible ! ");
			else
				System.out.println("Votre solution n'est pas admissible ! ");
		}

		return contradiction;

	}

	/**
	 * Permet aussi de verifier l'admissibilité mais sans les affichages en sortie standard
	 *
	 * @param sol
	 * @return
	 */
	public boolean estPasAdmissible2(ArrayList<Integer> sol) {
		boolean contradiction = false;

		if (sol.size() == 1) {

			for (int i = 0; i < g.getNbSommets(); i++) {
				if (g.getMatriceAdjacence()[i][sol.get(0)] == 1) {
					if (g.getMatriceAdjacence()[sol.get(0)][i] != 1) {
						contradiction = true;
					}
				}
			}

		} else if (sol.size() > 1) {
			ArrayList<Integer>temp = new ArrayList<>();
			contradiction = false;
			for (int i = 0; i < sol.size() && contradiction == false; i++) {
				for (int j = 0; j < g.getNbSommets(); j++) {
					// Si on trouve un sommet du graphe qui contredit un élément de l'ensemble S
					if (g.getMatriceAdjacence()[j][sol.get(i)] == 1) {
						// Si ce sommet est aussi dans l'ensemble S
						if (sol.contains(j) && g.getMatriceAdjacence()[sol.get(i)][j] == 1) {
							contradiction = true;
							return contradiction;
						} else {
							contradiction = true;
							temp.add(j) ;
							for (int k = 0; k < sol.size(); k++){
								// Cas ou l'argument se défend contre celui qui l'a contredit en l'occurence j
								if (g.getMatriceAdjacence()[sol.get(k)][j] == 1 ) {
									contradiction = false;
								}
							}

						}
					}
				}
			}
			for (Integer e : temp){
				contradiction = true;
				if (estContredit(e,sol)!=-1){
					contradiction= false ;
				}
				else{
					return true;
				}

			}

		}

		return contradiction;

	}

	public int estContredit(int i, ArrayList<Integer> sol){
		for (int j= 0; j<sol.size(); j++){
			if (g.getMatriceAdjacence()[sol.get(j)][i] == 1)
				return sol.get(j) ;
		}
		return -1;
	}

	/**
	 * Permet de vérifier si un nom est déjà possédé par un argument
	 * @param nom le nom dont on veut vérifier la disponibilité
	 * @return true si le nom n'est pas disponible, false s'il est disponible
	 */
	public boolean nomArgumentExisteDeja(String nom){
		for (Argument a : listeArguments)
			if (nom.equals(a.getNom()))
				return true ;
		return false;
	}

	/**
	 * Initialise le graph associé au débat à partir d'un fichier
	 * @return 1 en cas de succès, -1 sinon
	 */
	public int initGraphFile() {
		//System.out.println(p);
		try {
			BufferedReader br = Files.newBufferedReader(p);
			String s;
			while ((s = br.readLine()) != null) {
				System.out.println(s);
				if (s.length()>=8 ) {
					if (s.substring(0, 8).equalsIgnoreCase("argument")) {
						String nom = s.substring(s.indexOf('(') + 1, s.indexOf(')'));
						nom = nom.replace(" ", "");
						if (nom.isEmpty()) {
							System.out.println("Vous ne pouvez pas mettre d'argument vide ! ");
							return -1;
						}
						if (nomArgumentExisteDeja(nom)) {
							System.out.println("Vous ne pouvez pas mettre 2 arguments avec le même nom");
							return -1;
						}
						Argument a = new Argument(nom);
						addArgument(a);
					} else if (s.substring(0, 13).equalsIgnoreCase("contradiction")) {
						String firstArg = s.substring(s.indexOf('(') + 1, s.indexOf(','));
						firstArg = firstArg.replace(" ", "");
						String secondArg = s.substring(s.indexOf(',') + 1, s.indexOf(')'));
						secondArg = secondArg.replace(" ", "");
						try {
							if (g.getMatriceAdjacence() == null)
								g.setNbSommets(Argument.cptArg);
							g.ajouterArete(getIdFromName(firstArg), getIdFromName(secondArg));
						} catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException e) {
							System.out.println("L'un des 2 arguments ou les 2 arguments " + firstArg + " , " + secondArg + " n'existe(nt) pas");
							System.out.println("Votre fichier est mal formé : tous les arguments doivent être définis avant leur utilisation dans une contradiction !");
							return -1  ;
						} catch (NullPointerException e) {
							System.out.println("Votre fichier est mal formé : tous les arguments doivent être définis avant leur utilisation dans une contradiction !");
							return -1 ;
						}
					} else {
						System.out.println("Votre fichier est mal formé : il ne doit contenir que des arguments et des contradictions ");
						return -1;
					}
				}
				else {
					System.out.println("Votre fichier est mal formé : il ne doit contenir que des arguments et des contradictions ");
					return -1;
				}
			}
			br.close();
		} catch (IOException e) {
			System.out.println("Fichier invalide");
		}
		return 1;
	}

	/**
	 * Calcule les différentes combinaisons et les stocke dans l'attribut d'instance allSolutions si elles sont admissibles
	 * @param tab tableau d'IDs des arguments
	 * @param n taille du tableau d'ID / nombre d'arguments (noeuds)
	 * @param r taille de la combinaison recherchée
	 * @param indice indice courant de temp
	 * @param temp Tableau temporaire stockant la combinaison courante
	 * @param i indice de l'element courant dans tab
	 */
	public void combinaison(int[] tab, int n, int r, int indice, int[] temp, int i) {
		ArrayList<Integer> temp2 = new ArrayList();
		if (indice == r) {
			for (int j = 0; j < r; j++) {
				//System.out.println(temp[j] + " ");
				temp2.add(temp[j]);
			}
			if (estPasAdmissible2(temp2) == false)
				this.allSolutions.add(temp2);
			//temp2.clear();
			return;
		}


		if (i >= n)
			return;

		temp[indice] = tab[i];
		combinaison(tab, n, r, indice + 1, temp, i + 1);

		combinaison(tab, n, r, indice, temp, i + 1);

	}

	/**
	 * Initialise le tableau d'IDs d'argument et le tableau temporaire
	 * @param r taille de la combinaison souhaitée
	 */
	public void afficheCombinaisons(int r) {
		int[] temp = new int[r];
		int tab[] = new int[this.listeArguments.size()];

		for (int i = 0; i < tab.length; i++)
			tab[i] = i;

		combinaison(tab, tab.length, r, 0, temp, 0);

	}

	/**
	 * Affiche la variable d'instance allSolutions autrement dit l'ensemble des solutions admissibles
	 */
	public void afficheAllSolutions() {
		System.out.println("∅");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < allSolutions.size(); i++) {
			for (int j = 0; j < allSolutions.get(i).size(); j++) {
				if (j != allSolutions.get(i).size() - 1)
					sb.append(getNameFromId(allSolutions.get(i).get(j))).append(", ");
				else
					sb.append(getNameFromId(allSolutions.get(i).get(j)));

			}
			System.out.println(sb + "\n");
			sb.replace(0, sb.length() + 1, "");
		}
		//System.out.println(allSolutions);
	}

	/**
	 * Permet de connaitre les relations d'inclusions entre 2 ensembles
	 * @param l1 ensemble potentiellement inclus dans l2
	 * @param l2 ensemble potentiellement incluant l1
	 * @return true si l1 est strictement inclus dans l2, false sinon
	 */
	public boolean estContenu(ArrayList <Integer> l1, ArrayList<Integer> l2){
		if (l1.size()<l2.size()) {
			for (int i = 0; i<l1.size(); i++)
				if (!(l2.contains(l1.get(i))))
					return false;
			return true ;
		}

		return false ;
	}

	/**
	 * Verifier si une solution est une solution préférée
	 * @param sol solution qu'on souhaite vérifier
	 * @return true si la solution est préférée, false si elle ne l'est pas
	 */
	public boolean verifPref(ArrayList<Integer> sol) {

		if (estPasAdmissible2(sol) == true) {
			System.out.println("Votre solution n'est ni admissible ni préférée");
			return false;
		}
		for (int i = 0; i<allSolutions.size(); i++){
			if (estPasAdmissible2(allSolutions.get(i)) == false && estContenu(sol, allSolutions.get(i))) {
				System.out.println("Votre solution n'est pas préférée") ;
				System.out.println("Elle est incluse dans : ") ;
				afficherListeSolution(allSolutions.get(i)) ;
				return false ;
			}
		}
		System.out.println("La solution est préférée");
		return true;

	}

	public boolean verifPref2(ArrayList<Integer> sol) {

		if (estPasAdmissible2(sol) == true) {
			return false;
		}
		for (int i = 0; i<allSolutions.size(); i++){
			if (estPasAdmissible2(allSolutions.get(i)) == false && estContenu(sol, allSolutions.get(i))) {

				return false ;
			}
		}
		return true;

	}

	public ArrayList<ArrayList<Integer>> getAllSolutions(){
		return this.allSolutions;
	}

	public ArrayList<ArrayList<Integer>> getSolutionsPreferees(){
		return this.solutionsPreferees;
	}
	public void setSolutionsPreferees(){
		for (int i = 0; i<allSolutions.size(); i++){
			if (verifPref2(allSolutions.get(i))== true)
				solutionsPreferees.add(allSolutions.get(i));
		}
	}

	/**
	 * Affiche la variable d'instance solutionsPreferees, autrement dit l'ensemble des solutions preferees
	 */
	public void afficherSolutionsPreferees(){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < solutionsPreferees.size(); i++) {
			for (int j = 0; j < solutionsPreferees.get(i).size(); j++) {
				if (j != solutionsPreferees.get(i).size() - 1)
					sb.append(getNameFromId(solutionsPreferees.get(i).get(j))).append(", ");
				else
					sb.append(getNameFromId(solutionsPreferees.get(i).get(j)));

			}
			System.out.println(sb + "\n");
			sb.replace(0, sb.length() + 1, "");
		}
	}

	/**
	 * Permet d'afficher une par une dans l'ordre croissant, les solutions admissibles de allSolutions
	 */
	public void chercherSolutionAdmissible(){
		afficherListeSolution(allSolutions.get(cptSolutionAdmissible));
		this.solution.clear();
		this.solution.addAll(allSolutions.get(cptSolutionAdmissible));
		cptSolutionAdmissible ++ ;
		if (cptSolutionAdmissible == allSolutions.size())
			cptSolutionAdmissible = 0 ;
	}

	/**
	 * Permet d'afficher une par une, dans l'odre croissant, les solutions préférés de solutionsPreferees
	 */
	public void chercherSolutionPreferee(){
		afficherListeSolution(solutionsPreferees.get(cptSolutionPreferee));
		this.solution.clear();
		this.solution.addAll(solutionsPreferees.get(cptSolutionPreferee));
		cptSolutionPreferee ++ ;
		if (cptSolutionPreferee == solutionsPreferees.size())
			cptSolutionPreferee = 0 ;
	}

	/**
	 * Permet de sauvegarder la derniere solution affichée, dans un fichier texte dont le chemin est saisi par l'utilisateur
	 */
	public void sauvegarderSolution(){
		if (this.solution.isEmpty()){
			System.out.println("vous devez d'abord saisir 1) ou 2)");
			return ;
		}
		System.out.println("Saisissez le chemin ") ;
		Scanner saisie = new Scanner(System.in);
		String chemin = saisie.nextLine() ;
		Path p = Paths.get(chemin);
		try {
			BufferedWriter bw = Files.newBufferedWriter(p);
			bw.write(afficherListeSolution(this.solution));
			bw.close();
		}
		catch (NoSuchFileException e){
			System.out.println("Chemin invalide");
		}
		catch(IOException e){
			System.out.println("Fichier invalide");
		}
	}

	/**
	 * Lance un processus fils qui execute un fichier dot contenant le graphe du débat en langage DOT
	 * Génère une image GraphViz du graphe
	 */
	public void genererImage() {
		String chemin ;
		do {
			System.out.println("Saisissez le chemin de l'image à générer (l'extension doit être png, jpg ou pdf)") ;
			Scanner saisie = new Scanner (System.in);
			chemin = saisie.nextLine();
		} while ((chemin.indexOf("png", chemin.length()-4)==-1 && chemin.indexOf("jpg", chemin.length()-4) == -1) && chemin.indexOf("pdf", chemin.length()-4) == -1);

		try {

			StringBuilder commande = new StringBuilder();
			commande.append("dot -Tpng ").
					append("graph").append(".dot ").
					append("-o ").append(chemin);

			executerCommande(commande.toString());
		} catch (Exception ex) {
			System.out.println("Vous devez installer Graphviz sur votre machine !");
		}
	}

	/**
	 * Crée un processus fils qui exécute la commande commande
	 * @param commande commande à exécuter
	 * @throws IOException
	 */
	private void executerCommande(String commande) throws Exception {
		Process process = Runtime.getRuntime().exec(commande);
	}

}


