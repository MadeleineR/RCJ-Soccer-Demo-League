package visionclient;
import com.google.protobuf.CodedInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;



import position_data.protos.*;

public class Client implements Runnable{
       
        private int ServerPort = 10002;
        private InetAddress ServerIP;
        private int camera = -1;
        private ArrayList<Robot> robots = new ArrayList<Robot>();
        private Ball ball = new Ball();
        
        
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
                        
                        robots.clear();

                        CodedInputStream is = CodedInputStream.newInstance(indp.getData(), indp.getOffset(), indp.getLength());
                               
                        // Protokoll parsen
                        MessagesVision.SSL_WrapperPacket wrapper =
                                        MessagesVision.SSL_WrapperPacket.parseFrom(is);
                       
                        
                        MessagesRobocupSslDetection.SSL_DetectionFrame detect = wrapper.getDetection();
                        setCamera(detect.getCameraId());
                        
                        // Roboter Position Detection
                        if(detect.getRobotsBlueList()!=null && !detect.getRobotsBlueList().isEmpty()){
                            
                            ArrayList<Robot> list = new ArrayList<Robot>();
                        	
                        	for (MessagesRobocupSslDetection.SSL_DetectionRobot robot : detect.getRobotsBlueList()){	
                                      Robot r = new Robot();
                                      r.setX(robot.getPixelX());
                                      r.setY(robot.getPixelY());
                                      r.setOrientation(robot.getOrientation());
                                      list.add(r);                                            
                        	}
                                robots = list;
                        }
                        
                       
                        // Ball Position Detection
                        if(detect.getBallsList()!=null && !detect.getBallsList().isEmpty()){
                            
	                        MessagesRobocupSslDetection.SSL_DetectionBall ball = detect.getBalls(0);
                                this.ball.setX(ball.getPixelX());
                                this.ball.setY(ball.getPixelY());
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

    public void setCamera(int camera) {
        this.camera = camera;
    }

    public int getCamera() {
        return this.camera;
    }
    
    public ArrayList<Robot> getRobots(){
        return this.robots;
    }
    
    public Ball getBall(){
        return this.ball;
    }

        
   
}