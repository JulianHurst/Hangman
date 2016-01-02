
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hurst 
 */
public class PenduClientTCP {
    String hostname;		//Nom d'hote (localhost, adresse IP)
    int port;				//Numéro de port
    
    /**
     * 
     * @param hostname
     * @param port 
     */
    PenduClientTCP(String hostname,int port){
        this.hostname=hostname;
        this.port=port;       
    }
    
    /**
     * @brief Scanne un mot et l'envoie au serveur
     */
    void DevinerMot(){
        String mot="",recu="";
        try {
	    //Création d'une socket avec le nom d'hôte et le port
            Socket s=new Socket(hostname,port);
            //Création d'un Scanner sur l'entrée standard
            Scanner input=new Scanner(System.in);
            //Création du buffer sur l'entrée
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            //Création du buffer sur la sortie
            PrintWriter out = new PrintWriter(s.getOutputStream(),true);
            //Récuperation et affichage du mot caché sous la forme : _ _ _ _ _
            recu=in.readLine();
            System.out.println(recu);
            //Tant que le mot n'est pas trouvé ou que l'on a pas perdu
            while(!recu.equals("Trouvé") && !recu.contains("Perdu")){ 
				//Récupération d'un mot ou une lettre au clavier                 
                mot=input.nextLine();
                //Envoi du mot au serveur
                out.println(mot);
                //Lecture et affichage de la réponse du serveur
                recu=in.readLine();
                System.out.println(recu);                                
            }
            //Fermeture des buffers et de la socket
            in.close();
            out.close();
            s.close();
        } catch (IOException ex) {
            Logger.getLogger(PenduClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * @param args
     */
    public static void main(String[] args){
		//Si la syntaxe de la commande n'est pas respectée, afficher la syntaxe et quitter
        if(args.length!=2){
            System.out.println("USAGE PenduClientTCP \"nom d'hote\" port");
            return;
        }
        //Initialisation du Client et execution
        PenduClientTCP C = new PenduClientTCP(args[0],Integer.parseInt(args[1]));
        C.DevinerMot();
    }
}
