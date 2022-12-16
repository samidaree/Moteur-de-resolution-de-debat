package App;


/**
 * Cette classe implémente les graphes en Java
 * @author Sami BOUFASSA & Moncef BOUHABEL
 */

public class Graph {
    private int nbSommets;
    private int [][] matriceAdjacence;

    /**
     * Constructeur
     * @param nbSommets Ordre du graphe
     */
    public Graph (int nbSommets) {
        this.nbSommets = nbSommets;
        matriceAdjacence = new int [nbSommets][nbSommets];
    }

    /**
     * Constructeur vide
     */
    public Graph() {

    }
    /**
     * Getter ordre du graphe
     * @return le nombre de sommets du graphe
     */
    public int getNbSommets() {
        return this.nbSommets;
    }

    /**
     * Getter matrice d'adjacence
     * @return la matrice d'adjacence du graphe
     */
    public int[][] getMatriceAdjacence(){
        return this.matriceAdjacence;
    }

    /**
     * Modifie l'ordre du graphe
     * @param nbSommets nombre de sommets du graphe
     */
    public void setNbSommets(int nbSommets) {
        this.nbSommets = nbSommets;
        matriceAdjacence = new int [nbSommets][nbSommets] ;
    }

    /**
     * Ajoute une arête dans le graphe
     * @param s1 Sommet à la première extrémité de l'arête
     * @param s2 Sommet à la deuxième extrémité de l'arête
     */
    public void ajouterArete(int s1, int s2) throws ArrayIndexOutOfBoundsException {
        matriceAdjacence[s1][s2]= 1;
    }


}

