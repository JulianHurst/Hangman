import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.GregorianCalendar;

/**
 * 
 * @author Hurst & Garabedian
 *
 */
public class PenduServerUdp {
	String hostname;
	int port;
	Pendu p;
	
	/**
	 * @brief constructeur de PenduServerUdp
	 * @param host
	 * @param port
	 */
	public PenduServerUdp(String host, int port, Pendu p){
		this.p = p;
		this.hostname = host;
		this.port = port;
	}
	/**
	 * @brief Permet de communiquer avec les clients
	 */
	public void lancer(){
		String result="";
		String find = "fin de partie";
		try{
			byte[] buf = new byte[256];
			String lettre;
			//Lecture d'un socket celon un port
			DatagramSocket socket = new DatagramSocket(port);
			//Préparation à la réception des messages du client
			DatagramPacket packetReceive = new DatagramPacket(buf, buf.length);
			String chaineDate;
			DatagramPacket packetSend;
			
			while(true){
				//En attente de reception d'un client
				socket.receive(packetReceive);
				//Récupération du string envoyé par le client
				lettre = new String(packetReceive.getData(), 0, packetReceive.getLength());
				System.out.println(lettre);
				//Met à jour le retour client
				result = p.check(lettre);
				
				System.out.println("reception de : "+packetReceive.getSocketAddress());
				//On test si la partie est finie
				if(p.finPartie(lettre)){
					//Si la partie est fini renvoie perdu ou gagne
					packetSend = new DatagramPacket(p.find().getBytes(), p.find().length(), packetReceive.getAddress(), packetReceive.getPort());
					socket.send(packetSend);
					p.resetPendu();
				}
				else{
					//Si la partie n'est pas fini renvoie l'affichage chargé.
					packetSend = new DatagramPacket(result.getBytes(), result.length(), packetReceive.getAddress(), packetReceive.getPort());
					socket.send(packetSend);
				}
			}
		}
		catch(IOException e){e.printStackTrace();}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Pendu p = new Pendu(args[0]);
		new PenduServerUdp("", 50013,p).lancer();
	}

}
