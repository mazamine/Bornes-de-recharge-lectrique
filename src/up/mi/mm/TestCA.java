package up.mi.mm;
import java.util.Scanner;



public class TestCA {

	public static void main(String [] args) {

		int nbVilles;

		int reponse;

		boolean vrai = false;

		

		Scanner scanner=new Scanner(System.in);

		System.out.println("Saisir le nb de villes");

		nbVilles=scanner.nextInt();

		CA communaute=new CA(nbVilles);

		communaute.remplirMatrice();

		

		while(vrai==false) {

			System.out.println("Tapez: \n"

					+ "1 ajouter une route \n"

					+ "2 fin");

			reponse=scanner.nextInt();

			if(reponse==1) {

				int v1,v2;

				System.out.println("Le numero de la ville source");

				v1=scanner.nextInt();

				System.out.println("Le numero de la ville cible");

				v2=scanner.nextInt();

				communaute.ajouterArete(v1, v2);

			} else if (reponse==2) {

				vrai=true;

			} else {

				System.out.println("Saisir a nouveau");

			}

		}

		

		communaute.RemplirBornesDeRecharge();

		

		vrai=false;

		while(vrai==false) {

			System.out.println("Tapez: "

					+ "1 ajouter une zone de recharge \n"

					+ "2 retirer une zone de recharge \n"

					+ "3 fin");

			reponse=scanner.nextInt();

			switch(reponse) {

			case 1: 

				System.out.println("le numero de la ville");

				communaute.ajouterBorneDeRecharge(scanner.nextInt());

				break;

			case 2: 

				System.out.println("le numero de la ville");

				communaute.supprimerBorneRecharge(scanner.nextInt());

				break;

			case 3:

				vrai=true;

				break;

			default:

				break;

			}

		}

		

		scanner.close();

	}



}