package up.mi.mm.projet;

//Cette classe represente la communaute d'aggolomeration
public class CA {
	private int nbSommets;
	
	private int[][]matrice;
	private boolean[] bornesDeRecharge;
	
	public CA(int nbSommets) throws InvalidInputException {
		if(nbSommets>0) {
			this.nbSommets=nbSommets;
			matrice=new int[nbSommets][nbSommets];
			bornesDeRecharge=new boolean[nbSommets];
		} else {
			throw new InvalidInputException("Le nombre de villes doit etre supérieur strictement à 0");
		}
	}
	
	public void remplirMatrice() {
		for(int i=0; i<nbSommets;i++) {
			for(int j=0; j<nbSommets;j++) {
				matrice[i][j]=0;
			}
		}
	}
	
	public void RemplirBornesDeRecharge() {
		for(int i=0;i<nbSommets;i++) {
			bornesDeRecharge[i]=true;
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
	
	public void supprimerVille(int sommet) throws InvalidInputException {
		if(sommet < 0 || sommet >= nbSommets) {
			throw new InvalidInputException("La ville n'existe pas");
		} else {
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
					nouvelleMatrice[i][j]=matrice[i][j];
				}
			}
			matrice=nouvelleMatrice;
		}
	}
	
	public void ajouterArete(int sommetSource, int sommetCible ) throws InvalidInputException {
		//on verifie si les deux sommets existent déjà dans la matrice
		if((sommetSource<nbSommets) && (sommetSource>=0) && (sommetCible<nbSommets) && (sommetCible>=0)) {
			if (matrice[sommetSource][sommetCible]==1) {
				System.out.println("Il existe déjà une route entre la ville "+ sommetSource + " et la ville "+ sommetCible);
			} else {
			// c'est un graphe non orienté donc la matrice est symétrique
				matrice[sommetSource][sommetCible]=1;
				matrice[sommetCible][sommetSource]=1;
			}
		} else {
			throw new InvalidInputException("les villes n'existent pas");
		}
	}
	
	public void supprimerArete(int sommetSource, int sommetCible )throws InvalidInputException {
		if((sommetSource<nbSommets) && (sommetSource>=0) && (sommetCible<nbSommets) && (sommetCible>=0)) {
			if(matrice[sommetSource][sommetCible]==1) {
			// c'est un graphe non orienté donc la matrice est symétrique
				matrice[sommetSource][sommetCible]=0;
				matrice[sommetCible][sommetSource]=0;
			} else {
				System.out.println("Il n'existe pas de route entre la ville "+ sommetSource + " et la ville "+ sommetCible);
			}
		} else {
			throw new InvalidInputException("les villes n'existent pas");
		}
	}
	public void ajouterBorneDeRecharge(int sommet) throws InvalidInputException{
		//on verifie si la ville existe, et si elle n'a pas deja une borne de recharge 
		if(sommet < 0 || sommet >= nbSommets) {
			throw new InvalidInputException("La ville n'existe pas");
		} else if (bornesDeRecharge[sommet]) {
			System.out.println("La ville a déjà une borne de recharge");
	    } else {
	    	 bornesDeRecharge[sommet] = true;
	    }
		System.out.println("les villes qui ont des bornes de recharges sont: ");
        for(int i=0; i<nbSommets;i++) {
        	if(bornesDeRecharge[i]==true) {
        		System.out.print(i+ " ");
        	}
        }
        System.out.println("\n");
	}
	
	public void supprimerBorneRecharge(int sommet)throws InvalidInputException {
		//on verifie si le sommet existe 
        if (sommet>=0 || sommet < nbSommets) {
        	if (!bornesDeRecharge[sommet]) {
        		System.out.println("La ville n'a pas de borne de recharge");
        		return;
        	}
        	//on parcourt les sommets adjacents pour savoir si au moins l'un d'eux a une borne
        	else {
        		for(int j=0;j<nbSommets;j++) {
	        		if(matrice[sommet][j]==1 && bornesDeRecharge[j]==true) {
	        			bornesDeRecharge[sommet] = false;
	        			System.out.println("les villes qui ont des bornes de recharges sont: ");
	        	        for(int i=0; i<nbSommets;i++) {
	        	        	if(bornesDeRecharge[i]==true) {
	        	        		System.out.print(i+ " ");
	        	        	}
	        	        }
	        			return;
	        		} 
        		}
        	}
        	System.out.println("La ville n'a pas de ville adjacente qui a une borne de recharge");
        } else { 
        	throw new InvalidInputException("La ville n'existe pas");
        }
        System.out.println("les villes qui ont des bornes de recharges sont: ");
        for(int i=0; i<nbSommets;i++) {
        	if(bornesDeRecharge[i]==true) {
        		System.out.println(i);
        	}
        }
    }
} 

