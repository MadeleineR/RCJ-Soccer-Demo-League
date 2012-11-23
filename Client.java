package position_data;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {
	
	private int ServerPort = 10002;
	private InetAddress ServerIP;

	
	
	public void connect(){
		
		try {
			MulticastSocket socket = new MulticastSocket(ServerPort);
			ServerIP = InetAddress.getByName("224.5.23.2");
			socket.joinGroup(ServerIP);
			
		 // buffer used to receive
		    byte[] buffer = new byte[1000];
		    DatagramPacket indp = new DatagramPacket(buffer, buffer.length);
		    
		      while (true) {
		    	socket.receive(indp);
		        System.out.println("Received: " + new String(indp.getData(), 0,
		                                                     indp.getLength()));
		      }
		      //socket.leaveGroup(ServerIP);
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
