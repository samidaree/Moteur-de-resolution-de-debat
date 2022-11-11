package up.sm.debat;

import java.util.Scanner;

public class Debat {
	private Graph g ; 
    private int []e_tab;
    private static int cont ;
	
	public Debat() {
		this.g = new Graph(); 
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
        e=new int [x];

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
		
	}
	
	public void menu2() {
            int choix;
            boolean etat;
            Scanner sc  = new Scanner(System.in); 
            do{
                System.out.println("Que souhaitez vous faire ? "); 
			    System.out.println("1) Ajouter un argument "); 
			    System.out.println("2) retirer un argument");
                System.out.println("3) vérifier la solution");
                System.out.println("4) fin");
                choix=sc.nextInt();
                switch(choix){
                    case 1:  ajoute_argument();
                             affichage_solution();
                    break;
                    case 2: retire_argument();
                            affichage_solution();
                    break;
                    case 3: etat =verifie_solution();
                            affichage_solution();
                    break;
                    case 4 :  affichage_solution();
                                if(etat)
                                    System.out.println("la solution est admissible ");
                                else 
                                System.out.println("la solution  n est pas  admissible ");
                            
                    break;
                    default : System.out.println("veuillez saisir un choix valide ");

                }
                 
            }while(choix!=4)

        }
    public void ajoute_argument(){
              Scanner sc=new Scanner(System.in);
              int s,j=0;
              System.out.println("donnez le nom d'un argument a ajouter ");
              s=Character.getNumericValue(sc.nextLine().charAt(1));
              for(int i=0;i<e_tab.length;i++){
                if(e_tab[i]==s)
                j=1;
              }
              if(j==1)
              System.out.println("l'argument appartient deja a la solution ");
              else{
                e_tab[cont]=s;
                cont++;
              }



    }
    public void retire_argument(){
        Scanner sc=new Scanner(System.in);
        int s,pos,j;
        System.out.println("donnez l'argument a supprimer de la solution ");
        s=Character.getNumericValue(sc.nextLine().charAt(1));
        for(int i=0;i<e_tab.length;i++){
            if(e_tab[i]==s){
            j=1;
            pos=i;
          }
       }
        if(j!=1)
        System.out.println("l'argument n appartient pas a la solution ");
        else{
            for(int i=pos;i<e_tab.length-1;i++){
                    e_tab[i]=e_tab[i+1];
            }
            e_tab[i]=0;
        }
    }
    public boolean verifie_solution(){
                int [][] matrice = this.g. getMatriceAdjacence();
                int val;
            for(int i=0;i<e_tab.length;i++){
                for(j=0;j<e_tab.length;j++){
                    if(matrice[e_tab[i]][e_tab[j]]==1){
                        System.out.println("la valeur actuele de E ne correspend pas a une solution admissible car y a une contradiction interne ");
                        return false ;
                    }
                }
            }
            for(int i=0;i<this.g.getNbSommets();i++){
                for(j=0;j<e_tab.length;j++){
                    if(matrice[i][e_tab[j]]==1){
                        for(int k=0;k<e_tab.length;k++){
                            if(matrice[e_tab[k]][i]==1)
                            val=1;
                        }
                        if(val!=1){
                            System.out.println("argument dans l’ensemble qui n’est pas défendu contre tous ses contradicteurs ");
                            return false ;
                        }
                    }
                }
            }
            return true ;
    }
    public void affichage_solution(){
                System.out.println("la valeur actuelle de E ");
                for(int i=0;i<e_tab.length;i++){
                    System.out.print(" "+e_tab[i]+" ");
                }
    }
		
	}

	public static void main(String[] args) {
			Debat d = new Debat(); 
			d.saisieNbArguments(); 
			d.menu1();
			
		
	}

