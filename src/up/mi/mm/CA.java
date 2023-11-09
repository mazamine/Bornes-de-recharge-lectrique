package up.mi.mm;



//Cette classe represente la communaute d'aggolomeration

public class CA {

	private int nbSommets;

	private int[][]matrice;//adjacence

	private boolean[] bornesDeRecharge;

	

	public CA(int nbSommets) {

		this.nbSommets=nbSommets;
		matrice=new int[nbSommets][nbSommets];
		bornesDeRecharge=new boolean[nbSommets];
	}

	public void remplirMatrice() {

		for(int i=0; i<nbSommets;i++) {

			for(int j=0; j<nbSommets;j++) {

				matrice[i][j]=0;

			}

		}

	}

	

	public void ajouterVille() {

		nbSommets++;

		int[][] nouvelleMatrice = new int [nbSommets][nbSommets];

		for(int i=0; i<nbSommets;i++) {

			for(int j=0; j<nbSommets;j++) {

				nouvelleMatrice[i][j]=matrice[i][j];

			}

		}

		matrice=nouvelleMatrice;

	}

	

	public void supprimerVille(int sommet) {

		if(sommet < 0 || sommet >= nbSommets) {

			System.out.println("La ville n'existe pas");

			return;

		}

		//supprimer la ligne du sommet

		for(int i=sommet; i<nbSommets-1; i++) {

			for(int j=0; j<nbSommets;j++) {

				matrice[i][j]=matrice[i+1][j];

			}

		}

		//supprimer la colonne du sommet

		for(int j=sommet; j<nbSommets-1; j++) {

			for(int i=0; i<nbSommets;i++) {

				matrice[i][j]=matrice[i][j+1];

			}

		}

		nbSommets--;

		int [][] nouvelleMatrice=new int [nbSommets][nbSommets];

		for(int i=0;i<nbSommets;i++) {

			for(int j=0;j<nbSommets;j++) {

				nouvelleMatrice[j][j]=matrice[i][j];

			}

		}

		matrice=nouvelleMatrice;

	}

	

	public void ajouterArete(int sommetSource, int sommetCible ) {

		//on verifie si les deux sommets existent déjà dans la matrice

		if(sommetSource < nbSommets && sommetCible < nbSommets) {

			// c'est un graphe non orienté donc la matrice est symétrique

			matrice[sommetSource][sommetCible]=1;

			matrice[sommetCible][sommetSource]=1;

		} else {

			System.out.println("les villes n'existent pas dans la matrice");

		}

	}

	

	public void supprimerArete(int sommetSource, int sommetCible ) {

		if(sommetSource < nbSommets && sommetCible < nbSommets) {

			// c'est un graphe non orienté donc la matrice est symétrique

			matrice[sommetSource][sommetCible]=0;

			matrice[sommetCible][sommetSource]=0;

		} else {

			System.out.println("les villes n'existent pas dans la matrice");

		}

	}

	public void ajouterBorneDeRecharge(int sommet) {

		//on verifie si la ville existe, et si elle n'a pas deja une borne de recharge 

		if ((sommet>=0 || sommet < nbSommets) && !bornesDeRecharge[sommet]) {

	            bornesDeRecharge[sommet] = true;
	            System.out.println("hello3");
	    } else {

	    	System.out.println("La ville n'existe pas ou elle a deja une borne de recharge");
	    	System.out.println(sommet + " " + bornesDeRecharge[sommet]);

	    }

		System.out.println("les villes qui ont des bornes de recharges sont: ");

        for(int i=0; i<nbSommets;i++) {

        	if(bornesDeRecharge[i]==true) {

        		System.out.println(i);

        	}

        }

	}

	public void supprimerBorneRecharge(int sommet) {

		//on verifie si le sommet existe 

        if (sommet>0 || sommet < nbSommets) {

        	//on parcourt les sommets adjacents pour savoir si au moins l'un d'eux a une borne

        	for(int j=0;j<nbSommets;j++) {

        		if(matrice[sommet][j]==1 && bornesDeRecharge[j]==true) {

        			bornesDeRecharge[sommet] = false;

        			return;

        		} 

        	}

        	System.out.println("La ville n'a pas de ville adjacente qui a une borne de recharge");

        } else { 

        	System.out.println("La ville n'existe pas");

        }

        System.out.println("les villes qui ont des bornes de recharges sont: ");

        for(int i=0; i<nbSommets;i++) {

        	if(bornesDeRecharge[i]==true) {

        		System.out.println(i);

        	}

        }

    }

	public void RemplirBornesDeRecharge() {

		for(int i=0;i<nbSommets;i++) {

			bornesDeRecharge[i]=true;

		}

	}



}

