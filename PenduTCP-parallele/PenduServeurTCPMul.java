import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Hurst
 */ 

public class PenduServeurTCPMul{
    /**
     * @detail attend deux argument aux lancement
     * @brief java PenduServeurTCPMul int String
     * @param argv
     * @throws Exception 
     */
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
