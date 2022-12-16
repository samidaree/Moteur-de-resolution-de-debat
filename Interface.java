package App;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Interface {

    private Debat d ;


    public Interface () {
        d = new Debat();
    }

    public Interface (String s) {
        d = new Debat(s);
    }

    public Debat getD(){
        return d;
    }
    public void saisirArgument() {

        do {
            System.out.println("Saisissez les arguments :") ;

            for (int i = 0; i<d.getGraph().getNbSommets() ; i++) {

                Scanner saisie = new Scanner (System.in);
                String s = saisie.nextLine();
                Argument a = new Argument(s);
                d.addArgument(a);
                d.supprimerEspace();
            }
        } while (d.contientDoublon() || d.contientArgumentVide()) ;



    }

    public void afficherDebat() {
        StringBuffer sb = new StringBuffer();
        sb.append("digraph debat {\n");
        for (int i = 0; i<d.getListeArguments().size(); i++) {
            for (int j = 0 ; j<d.getListeArguments().size(); j++) {
                if (d.getGraph().getMatriceAdjacence()[i][j] == 1) {
                    sb.append(d.getListeArguments().get(i).getNom()).append("->").append(d.getListeArguments().get(j).getNom()).append(";\n");
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
        int id1 = d.getIdFromName(s1) ;
        int id2 = d.getIdFromName(s2) ;
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
     * Permet d'ajouter un argument dans l'ensemble des solution
     */
    public void ajouterArgumentSolution() {
        System.out.println("Ajoutez un argument dans la solution : ");

        Scanner saisie = new Scanner(System.in);
        String argument = saisie.nextLine();
        if ( d.getsolution().contains(d.getIdFromName(argument)))
            System.out.println("Cet argument est déjà dans la solution !");
        else
            this.d.getsolution().add(d.getIdFromName(argument));


        if (d.getIdFromName(argument) == -1)
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
            this.d.getsolution().remove(d.getsolution().indexOf(d.getIdFromName(argument)));

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
        for (int i = 0; i<this.d.getsolution().size();i++) {
            if (i==this.d.getsolution().size()-1)
                sb.append(d.getNameFromId(d.getsolution().get(i)));
            else
                sb.append(d.getNameFromId(d.getsolution().get(i))).append(",");
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
                System.out.println("2) Afficher tous les arguments");
                System.out.println("3) Afficher le débat");
                System.out.println("4) Fin");

                x = saisie.nextInt();
                switch(x) {
                    case 1 : ajouterContradiction();
                        break ;
                    case 2 : d.afficherListeArguments();
                        break ;
                    case 3 : afficherDebat();
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
        } while (x != 4);
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
                System.out.println("3) Verifier si la solution est admissible");
                System.out.println("4) Verifier si la solution est préférée");
                System.out.println("5) Afficher toutes les solutions admissibles");
                System.out.println("6) Afficher toutes les solutions préférées");
                System.out.println("7) Fin");

                x = saisie.nextInt();
                switch (x) {
                    case 1 -> ajouterArgumentSolution();
                    case 2 -> retirerArgument();
                    case 3, 7 -> {
                        d.estPasAdmissible(d.getsolution());
                        affichersolution();
                    }
                    case 4 -> {
                        for (int i = 1; i <= d.getGraph().getNbSommets(); i++) {
                            d.afficheCombinaisons(i);
                        }
                        d.verifPref(d.getsolution());
                        affichersolution();
                    }
                    case 5 -> {
                        if (d.getAllSolutions().isEmpty())
                            for (int i = 1; i <= d.getGraph().getNbSommets(); i++) {
                                d.afficheCombinaisons(i);
                            }
                        d.afficheAllSolutions();
                    }
                    case 6 -> {
                        if (d.getAllSolutions().isEmpty())
                            for (int i = 1; i <= d.getGraph().getNbSommets(); i++) {
                                d.afficheCombinaisons(i);
                            }
                        if (d.getSolutionsPreferees().isEmpty())
                            d.setSolutionsPreferees();
                        d.afficherSolutionsPreferees();
                    }
                    default -> System.out.println("Vous devez saisir 1, 2, 3, 4, 5, 6 ou 7 !");
                }
            }

            catch (InputMismatchException e) {
                System.out.println("Veuillez entrer un nombre");
            }
        } while (x != 7);

    }
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
                switch (x) {
                    case 0 -> {
                        afficherDebat();
                        d.genererImage();
                    }
                    case 1 -> d.chercherSolutionAdmissible();
                    case 2 -> d.chercherSolutionPreferee();
                    case 3 -> d.sauvegarderSolution();
                    case 4 -> d.afficheAllSolutions();
                    case 5 -> d.afficherSolutionsPreferees();
                    case 6 -> {
                        d.estPasAdmissible(d.getsolution());
                        affichersolution();
                    }
                    default -> System.out.println("Vous devez saisir 1, 2, 3, 4, 5, ou 6 !");
                }
            }

            catch (InputMismatchException e) {
                System.out.println("Veuillez entrer un nombre");
            }
        } while (x != 6);

    }

}


class TestInterface{
    public static void main(String []args) {

        if (args.length==0) {
            Interface i = new Interface ();
            i.saisirNbArguments();
            i.saisirArgument();
            i.menu1();
            i.menu2();
        }
        else {
            Interface i = new Interface (args[0]);
            if (i.getD().initGraphFile() == 1)
                i.menu3();

        }

    }
}

