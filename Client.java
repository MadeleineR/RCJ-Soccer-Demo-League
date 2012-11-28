package position_data;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import position_data.MessagesRobocupSslDetection;
import position_data.MessagesRobocupSslGeometry;
import position_data.MessagesRobocupSslRefboxLog;
import position_data.MessagesRobocupSslWrapper;


public class Client {
	
	private int ServerPort = 10002;
	private InetAddress ServerIP;

	
	
	public void connect(){
		
		try {
			MulticastSocket socket = new MulticastSocket(ServerPort);
			ServerIP = InetAddress.getByName("224.5.23.2");
			socket.joinGroup(ServerIP);
			
			byte[] buffer = new byte[55];
			DatagramPacket indp = new DatagramPacket(buffer, buffer.length);

			
		      while (true) {

		    	socket.receive(indp);
				System.out.println(indp.getLength());

				//InputStream is = new ByteArrayInputStream(indp.getData());
				
		    	// Protokoll parsen
		    	MessagesRobocupSslWrapper.SSL_WrapperPacket wrapper = 
		    			MessagesRobocupSslWrapper.SSL_WrapperPacket.parseFrom(indp.getData());
		    	
		    	//
		    	MessagesRobocupSslDetection.SSL_DetectionFrame detect = wrapper.getDetection();
		    	
		    	/* Roboter Position Detection - findet keinen Roboter
		    	MessagesRobocupSslDetection.SSL_DetectionRobot robot = detect.getRobotsBlue(0);
		    	System.out.println("Roboter 1:");
		    	System.out.println("Position X:" + robot.getPixelX());
		    	System.out.println("Position X:" + robot.getPixelY());
		    	*/
		    	
		    	// Ball Position Detection
				MessagesRobocupSslDetection.SSL_DetectionBall ball = detect.getBalls(0);
		        System.out.printf("Ball X: %f\n", ball.getPixelX() );
		        System.out.printf("Ball Y: %f\n\n", ball.getPixelY() );
		        
		        
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
