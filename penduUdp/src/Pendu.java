import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Hurst & Garabedian
 *
 */
public class Pendu {
	int nbTour = 10;
	String mot;
	List<Character> secretMot = new ArrayList<Character>();
	
	/**
	 * @brief constructeur avec un paramètre
	 * @param mot
	 */
	Pendu(String mot){
		this.mot = mot;
		initPendu();
	}
	
	/**
	 * @brief initialise le pendu à vide
	 */
	void initPendu(){
		for(int i = 0; i<mot.length(); i++){
			secretMot.add('_');
		}
	}

	/**
	* @brief remet le pendu à zéro
	**/
	void resetPendu(){
		for(int i = 0; i<secretMot.size(); i++){
			secretMot.set(i, '_');
		}
		nbTour = 10;
	}
	
	/**
	 * @brief vérifie soit la présence de la lettre, soit le mot complet
	 * @param lettre
	 * @return
	 */
	public String check(String lettre){
		nbTour--;
		String result = "Non trouvé";
		//Si plus d'une lettre est entrée, vérifie le mot complet
		if(lettre.length()>1){
			if(lettre.equals(mot)){
				result = "gagne";
			}	
			else
				result = "faux";
		}
		//Si exactement une lettre est entrée, vérifie si elle existe
		else if(lettre.length() == 1){
			for(int i =0; i < mot.length(); i++){
				//Vérifie si la lettre existe
				if(mot.charAt(i)==lettre.charAt(0)){
					//Place les lettres trouvés
					secretMot.set(i, lettre.charAt(0));
					result = "lettre "+lettre+" valide\n";
				}
			}
		}
		//Affichage de la liste en cours
		result += "\n"+this.toString();
		return result;
	}
	
	/**
	 * @brief En cas de fin de partie vérifie si c'est une victoire, ou une défaite
	 * @return retourne gagne si la partie est gagné, et perdu sinon
	 */
	public String find(){
		String result = "";
		if(nbTour == 0){
			result = "perdu";
		}
		else
			result = "gagne";
		return result;
	}
	
	/**
	 * @brief vérfie si l'une des conditions de fin de partie est remplie
	 * @return vrai si la partie est fini, faux sinon
	 */
	public boolean finPartie(String lettre){
		boolean check = true;
		System.out.println(nbTour);
		//Vérifie que le nomnbre de tour est supérieur à 0
		if(nbTour > 0){
			//Si le mot envoyé est juste validé sinon, tester les cases
			if(lettre.equals(mot)){
				System.out.println("trouvé");
			}
			else{
				System.out.println("boucle");
				for(int i = 0; i<secretMot.size(); i++){
					//Cherche si il y a encore des '_' dans le mot
					if(secretMot.get(i)=='_')
						check = false;
				}
			}
		}
		return check;
	}
	
	/**
	 * @brief permet de préparer un string de la liste pour son affichage
	 */
	public String toString(){
		String rt = "";
		if(nbTour == 1){
			rt += "Plus qu'une vie \n";
		}
		for(int i = 0; i<secretMot.size(); i++){
			rt += secretMot.get(i);
		}
		return rt;
	}
}
