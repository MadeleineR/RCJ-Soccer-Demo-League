package client;
import com.google.protobuf.CodedInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;



import position_data.protos.*;

public class Client implements Runnable{
       
        private int ServerPort = 10002;
        private InetAddress ServerIP;
        private float bx = 0;
        private float by = 0;
        private float rx = 0;
        private float ry = 0;
        
        public Client() {
        }
        
       
       @Override
        public void run(){
               
                try {
                        MulticastSocket socket = new MulticastSocket(ServerPort);
                        ServerIP = InetAddress.getByName("224.5.23.2");
                        socket.joinGroup(ServerIP);
                       
                        byte[] buffer = new byte[1024];
                        DatagramPacket indp = new DatagramPacket(buffer, buffer.length);

                       
                      while (true) {

                        socket.receive(indp);

                        CodedInputStream is = CodedInputStream.newInstance(indp.getData(), indp.getOffset(), indp.getLength());
                               
                        // Protokoll parsen
                        MessagesVision.SSL_WrapperPacket wrapper =
                                        MessagesVision.SSL_WrapperPacket.parseFrom(is);
                       
                        //
                        MessagesRobocupSslDetection.SSL_DetectionFrame detect = wrapper.getDetection();
                        //System.out.println("Kamera: " + detect.getCameraId());
                        
                        // Roboter Position Detection
                        if(detect.getRobotsBlueList()!=null && !detect.getRobotsBlueList().isEmpty()){
                        	
                        	for (MessagesRobocupSslDetection.SSL_DetectionRobot robot : detect.getRobotsBlueList()){	                     
		                       // System.out.println("Roboter "+ robot.getRobotId() + ": ");
                                        
                                            setRx(robot.getPixelX());
                                            setRy(robot.getPixelY());
                                            robot.getOrientation();
                                        
		                        //System.out.println("Position X:" + robot.getPixelX());
		                        //System.out.println("Position Y:" + robot.getPixelY());
                        	}
                        }
                        
                       
                        // Ball Position Detection
                        if(detect.getBallsList()!=null && !detect.getBallsList().isEmpty()){
	                        MessagesRobocupSslDetection.SSL_DetectionBall ball = detect.getBalls(0);
                                
                                    setBx(ball.getPixelX());
                                    setBy(ball.getPixelY());
                                
	                        //System.out.printf("Ball X: %f\n", ball.getPixelX() );
	                        //System.out.printf("Ball Y: %f\n\n", ball.getPixelY() );
                        }
                       
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

    public float getBx() {
        return bx;
    }

    public void setBx(float bx) {
        this.bx = bx;
    }

    public float getBy() {
        return by;
    }

    public void setBy(float by) {
        this.by = by;
    }

    public float getRx() {
        return rx;
    }

    public void setRx(float rx) {
        this.rx = rx;
    }

    public float getRy() {
        return ry;
    }

    public void setRy(float ry) {
        this.ry = ry;
    }
        
   
}