
import java.util.logging.Level;
import java.util.logging.Logger;
import lejos.nxt.Button;
import refbox.RefBox;
import visionclient.Client;
import visionclient.Robot;

public class Main {

    public static void main(String[] args) {

        GUIJFrame gui = new GUIJFrame();
        GUIThread gt = new GUIThread(gui);
        gt.start();
        RefBox r = new RefBox();
        Thread t = new Thread(r);
        t.start();

        Client vision = new Client();
        Thread vt = new Thread(vision);
        vt.start();


        BTSender sender = new BTSender();
        
        

        while (!Button.ENTER.isPressed()) {

            try {
                String refCMD = "HALT";
                refCMD = r.getCmd();
                System.out.println(refCMD);
                
                
                // check the referee command
                if ("HALT".equals(refCMD)) {

                    // send command to robot
                    System.out.println("Sending 2 (Stop)");
                    

                    
                    


                } else {
                    //send command to robot
                    
                    
                    
                   
                   
                   //sender.setSpeed('B', 25);
                 if (vision.getRobots() != null && !vision.getRobots().isEmpty()) {
                     System.out.println(vision.getRobots().get(0).getX());
                     System.out.println(vision.getRobots().get(0).getY());
                     System.out.println("\n");
                     System.out.println(vision.getRobots().get(0).getOrientation());
                     System.out.println("\n");
                     System.out.println(vision.getBall().getX());
                     System.out.println(vision.getBall().getY());
                    
                }
            }
                     
                
              
                
                
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(BTSender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        sender.close();


    }
}