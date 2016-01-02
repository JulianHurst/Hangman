
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
public class PenduServeurTCP {
    int port,nbessai;
    String mot,essai;
    
    /**
     * 
     * @param port
     * @param mot 
     */
    PenduServeurTCP(int port,String mot){
        this.port=port;
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
        if(mot.equals(crecu))
            return 2;
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
        if(!crecu.isEmpty()){
            StringBuilder b=new StringBuilder(essai);
            if(mot.equals(crecu))
                essai=mot;
            else if(mot.contains(crecu)){
                essai="";
                for(int i=0;i<mot.length();i++)
                    for(int j=0;j<crecu.length();j++)
                        if(crecu.charAt(j)==mot.charAt(i))                    
                            b.setCharAt(i*2, crecu.charAt(j));                
                essai=b.toString();
            }
            else
                nbessai--;
        }                            
    }   
    
    /**
     * @brief Recoit un mot deviné d'un client et affiche s'il a trouvé
     */
    void RecoitMot(){		
        ServerSocket s=null;
        Socket clientsocket=null;
        String crecu="";
        try {
			//Création d'un socket server
            s = new ServerSocket(port);
            //Attente d'une connection du client
            clientsocket = s.accept();
            System.out.println("Client "+clientsocket+" connecté");            
            BufferedReader in = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientsocket.getOutputStream(),true);                        
            for(int i=0;i<mot.length();i++)
                    essai=essai+"_ ";
            out.println(essai);
            while(!essai.equals(mot)){                
                crecu=in.readLine();               
                CalculeMots(crecu);                
                if(!essai.equals(mot) && essai.contains("_")){                    
                    if(nbessai==0){
                        out.println(essai+" Perdu");
                        essai=mot;
                    }
                    else if(nbessai==1)
                        out.println("1 "+essai);                                                                                        
                    else
                        out.println(essai);                                                                                                            
                }
                else{
                    out.println("Trouvé");
                    essai=mot;
                }                
            }               
            in.close();
            clientsocket.close();
            s.close();
            //Réinitialisation du mot d'essai et du nombre d'essais
            essai="";
            nbessai=5;
            System.out.println("Client "+clientsocket+" déconnecté");
        } catch (IOException ex) {
            Logger.getLogger(PenduServeurTCP.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public static void main(String[] args){
        if(args.length!=2){
            System.out.println("USAGE PenduServeurTCP port mot");
            return;
        }
        PenduServeurTCP S=new PenduServeurTCP(Integer.parseInt(args[0]),args[1]);               
        while(true)
            S.RecoitMot();        
    }
}
