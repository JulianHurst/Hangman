
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
 * @author guest-bbAuOz
 */
public class PenduClientTCP {
    String hostname;
    int port;
    
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
            Socket s=new Socket(hostname,port);
            Scanner input=new Scanner(System.in);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(),true);            
            //BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            recu=in.readLine();
            System.out.println(recu);
            while(!recu.equals("Trouvé") && !recu.contains("Perdu")){                  
                mot=input.nextLine();
                out.println(mot);
                recu=in.readLine();
                System.out.println(recu);                
                //out.write(mot);
                //out.newLine();
            }
            in.close();
            out.close();
            s.close();
        } catch (IOException ex) {
            Logger.getLogger(PenduClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void main(String[] args){
        if(args.length!=2){
            System.out.println("USAGE PenduClientTCP \"nom d'hote\" port");
            return;
        }
        PenduClientTCP C = new PenduClientTCP(args[0],Integer.parseInt(args[1]));
        C.DevinerMot();
    }
}
