
package up.sm.debat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
	 * Class constructor
	 */
	public Debat() {
		this.g = new Graph();
		solution = new <Integer>ArrayList();
		listeArguments = new ArrayList<Argument>();
		allSolutions = new ArrayList();
		solutionsPreferees = new ArrayList () ;
	}

	public Debat(String s) {
		this.g = new Graph();
		solution = new <Integer>ArrayList();
		listeArguments = new ArrayList<Argument>();
		allSolutions = new ArrayList<>();
		solutionsPreferees = new ArrayList<>();
		p = Paths.get(s);

	}

	public void addArgument(Argument a) {
		this.listeArguments.add(a);

	}

	public void supprimerEspace() {
		for (int i = 0; i < listeArguments.size(); i++) {
			if (this.listeArguments.get(i).getNom().contains(" "))
				this.listeArguments.get(i).setNom(listeArguments.get(i).getNom().replace(" ", ""));
		}
	}

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

	public void afficherListeArguments() {
		for (Argument a : listeArguments) {
			System.out.println(a.getNom());
		}
	}

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
			for (int i = 0; i < g.getNbSommets() && sb.charAt(sb.length() - 1) == '!'; i++) {
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
							for (int k = 0; k < sol.size(); k++)
								// Cas ou l'argument se défend contre celui qui l'a contredit en l'occurence j
								if (g.getMatriceAdjacence()[sol.get(k)][j] == 1) {
									System.out.println("test");
									contradiction = false;
									sb.replace(0, sb.length(), "");

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
			contradiction = false;
			for (int i = 0; i < sol.size() && contradiction == false; i++) {
				for (int j = 0; j < g.getNbSommets(); j++) {
					// Si on trouve un sommet du graphe qui contredit un élément de l'ensemble S
					if (g.getMatriceAdjacence()[j][sol.get(i)] == 1) {
						// Si ce sommet est aussi dans l'ensemble S
						if (sol.contains(j) && g.getMatriceAdjacence()[sol.get(i)][j] == 1) {
							contradiction = true;
						} else {
							contradiction = true;
							for (int k = 0; k < sol.size(); k++)
								// Cas ou l'argument se défend contre celui qui l'a contredit en l'occurence j
								if (g.getMatriceAdjacence()[sol.get(k)][j] == 1) {
									contradiction = false;

								}
						}
					}
				}
			}

		}

		return contradiction;

	}

	public boolean nomArgumentExisteDeja(String nom){
		for (Argument a : listeArguments)
			if (nom.equals(a.getNom()))
				return true ;
		return false;
	}

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
					} else if (s.substring(0, 13).equals("contradiction")) {
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


	public void afficheCombinaisons(int r) {
		int[] temp = new int[r];
		int tab[] = new int[this.listeArguments.size()];

		for (int i = 0; i < tab.length; i++)
			tab[i] = i;

		combinaison(tab, tab.length, r, 0, temp, 0);

	}

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

	public boolean estContenu(ArrayList <Integer> l1, ArrayList<Integer> l2){
		if (l1.size()<l2.size()) {
			for (int i = 0; i<l1.size(); i++)
				if (!(l2.contains(l1.get(i))))
					return false;
			return true ;
		}

		return false ;
	}
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

	public ArrayList<ArrayList<Integer>> getAllSolutions(){
		return this.allSolutions;
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
	public ArrayList<ArrayList<Integer>> getSolutionsPreferees(){
		return this.solutionsPreferees;
	}
	public void setSolutionsPreferees(){
		for (int i = 0; i<allSolutions.size(); i++){
			if (verifPref2(allSolutions.get(i))== true)
				solutionsPreferees.add(allSolutions.get(i));
		}
	}

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

	public void chercherSolutionAdmissible(){
		afficherListeSolution(allSolutions.get(cptSolutionAdmissible));
		this.solution.clear();
		this.solution.addAll(allSolutions.get(cptSolutionAdmissible));
		cptSolutionAdmissible ++ ;
		if (cptSolutionAdmissible == allSolutions.size())
			cptSolutionAdmissible = 0 ;
	}

	public void chercherSolutionPreferee(){
		afficherListeSolution(solutionsPreferees.get(cptSolutionPreferee));
		this.solution.clear();
		this.solution.addAll(solutionsPreferees.get(cptSolutionPreferee));
		cptSolutionPreferee ++ ;
		if (cptSolutionPreferee == solutionsPreferees.size())
			cptSolutionPreferee = 0 ;
	}

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
			ex.printStackTrace();
		}
	}

	private void executerCommande(String commande) throws Exception {
		Process process = Runtime.getRuntime().exec(commande);
	}

}


