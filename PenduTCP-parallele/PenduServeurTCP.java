
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hurst Garabedian
 */
public class PenduServeurTCP implements Runnable {
    int port,nbessai;			//Numéro de port et nombre d'essais
    String mot,essai;			//Mot caché et mot d'essai
    Socket clientsocket=null;	//Socket du client
    
    /**
     * @Constructeur avec paramètre
     * @param client
     * @param mot 
     */
    PenduServeurTCP(Socket client,String mot){
        clientsocket=client;        
        this.mot=mot;
        essai="";
        nbessai=5;
    }
    
    /**
     * @brief Calcule les lettres trouvées et renvoie 2 si le mot est trouvé, 1 si le mot caché contient le mot deviné et 0 sinon
     * @param crecu
     * @return 
     */
    int CalculeMot(String crecu){
		//Le mot est égal à crecu
        if(mot.equals(crecu))
            return 2;
        //Le mot contient crecu
        else if(mot.contains(crecu))                   
            return 1;        
        else
            return 0;
    }
    
    
    /**
     * @brief Calcule les lettres trouvées et leur emplacement dans le mot caché
     * @param crecu 
     * 
     */
    void CalculeMots(String crecu){ 
		//Si crecu n'est pas vide
        if(!crecu.isEmpty()){
			//Création d'un String modifiable par index
            StringBuilder b=new StringBuilder(essai);
            //Si le mot est égal à crecu on affecte à essai la valeur du mot caché
            if(mot.equals(crecu))
                essai=mot;            
            else if(mot.contains(crecu)){
				//On remet le String d'essai à vide
                essai="";
                //Comparaison des mots lettre par lettre
                for(int i=0;i<mot.length();i++)
                    for(int j=0;j<crecu.length();j++)
						//Si les lettres sont égales on insère dans le String modifiable la lettre correspondante
                        if(crecu.charAt(j)==mot.charAt(i))                    
                            b.setCharAt(i*2, crecu.charAt(j)); //i*2 car il y a des espaces entre chaque underscore
                //On affecte au mot d'essai le String résultant du StringBuilder
                essai=b.toString();
            }
            //On décrémente le nombre d'essais que si les mots n'ont rien en commun
            else
                nbessai--;
        }                            
    }  
    
    
        @Override
    public void run() {
        RecoitMot();
    }
    
    /**
     * @brief Recoit un mot deviné d'un client et affiche s'il a trouvé
     */
    void RecoitMot(){        
        String crecu="";
        try {
            System.out.println("Client "+clientsocket+" connecté");
            //Création d'un buffer sur l'entrée
            BufferedReader in = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
            //Création d'un buffer sur la sortie
            PrintWriter out = new PrintWriter(clientsocket.getOutputStream(),true);                        
            //Initialisation du mot d'essai à mot.length fois "_ "
            for(int i=0;i<mot.length();i++)
                    essai=essai+"_ ";
            //Envoi de essai au client
            out.println(essai);
            //Boucle se reproduit tant que le mot n'est pas trouvé
            while(!essai.equals(mot)){
				//Lecture du mot envoyé par le client                
                crecu=in.readLine(); 
                //Compariason du mot envoyé par le client et du mot caché               
                CalculeMots(crecu);  
                //Si le mot n'est pas trouvé ou contient un underscore              
                if(!essai.equals(mot) && essai.contains("_")){  
					//Vérification du nombre d'essais                  
                    if(nbessai==0){
                        out.println(essai+" Perdu");
                        essai=mot;
                    }
                    //Affichage de 1 s'il ne reste qu'une tentative au client
                    else if(nbessai==1)
                        out.println("1 "+essai);                                                                                        
                    else
                        out.println(essai);                                                                                                            
                }
                //Si le mot est trouvé on envoie "Trouvé" au client et on affecte mot à essai pour quitter la boucle
                else{
                    out.println("Trouvé");
                    essai=mot;
                }                
            }         
            //Fermeture des buffers et de la socket      
            in.close();
            out.close();
            clientsocket.close();               
            System.out.println("Client "+clientsocket+" déconnecté");
        } catch (IOException ex) {
            Logger.getLogger(PenduServeurTCP.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public static void main(String argv[]) throws Exception{       
        if(argv.length!=2){
            System.out.println("USAGE : java PenduServeurTCPMul port mot");
            return;
        }
        //création socket server avec port            
        ServerSocket s = new ServerSocket(Integer.parseInt(argv[0]));
        while(true){
            //En attente de la connection client
            Socket client=s.accept();
            //Création d'un nouveau thread
            Thread t = new Thread(new PenduServeurTCP(client,argv[1]));
            //Démarrage du Thread
            t.start();
        }
    }
}
