import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

/**
 * 
 * @author Hurst & Garabedian
 *
 */
public class PenduClientUdp {
	String hostname;
	int port;
	
	/**
	 * @brieg constructeur du client udp
	 * @param host	hostname du client
	 * @param port	port du client
	 */
	public PenduClientUdp(String host, int port){
		this.hostname = host;
		this.port = port;
	}
	/**
	 * @brief permet la communication avec le serveur
	 * @param c lettre à tester
	 * @return Permet de récupérer la réponse du serveur pour le test de fin de partie
	 */
	public String lancer(String c){
		try {
			DatagramSocket soc = new DatagramSocket();
			String reponseServer;
			byte[] buf = new byte[256];
			
			//Création du paquet d'envoie
			//On envoie la lettre, ou le mot entrée par le client
			DatagramPacket paquetSend = new DatagramPacket(c.getBytes(), c.length(), InetAddress.getByName(hostname), port);
			soc.send(paquetSend);
			
			//Création du paquet de reception
			DatagramPacket paquetReceive = new DatagramPacket(buf, buf.length);
			//Reception de la réponse serveur
			soc.receive(paquetReceive);
			//Récupération du string renvoyé par le serveur
			reponseServer = new String(paquetReceive.getData(), 0, paquetReceive.getLength());
			System.out.println(reponseServer);
			return reponseServer;
		} 
		catch (SocketException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}
		return "";
	}
	/**
	 * @brief gestion des tour.
	 */
	public void launchGame(){
		String keyIn="";
		String fin ="";
		boolean check = false;
		//Redemande une entrée tant que l'une des conditions de partie n'est pas remplie
		while(!check && !fin.equals("gagne") && !fin.equals("perdu")){
			Scanner sc = new Scanner(System.in);
			keyIn = sc.nextLine();
			//Vérifie si l'utilisateur demande la fin de partie
			if(keyIn.equals("0quit"))
				check = true;
			else
				fin = this.lancer(keyIn);
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PenduClientUdp p = new PenduClientUdp("localhost", 50013);
		p.launchGame();
	}

}
