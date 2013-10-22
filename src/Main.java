
import java.util.logging.Level;
import java.util.logging.Logger;
import lejos.nxt.Button;
import refbox.RefBox;



public class Main {
    
    public static void main(String[] args) {
       
        RefBox r = new RefBox();
        Thread t = new Thread(r);
        t.start();


        BTSender sender = new BTSender();

        while (!Button.ENTER.isPressed()) {
            
            try {
                String refCMD;
                refCMD = r.getCmd();
                System.out.println(refCMD);

                // check the referee command
                if ("HALT".equals(refCMD)) {
                    
                    // send command to robot
                    System.out.println("Sending 2 (Stop)");
                    sender.setSpeed('B', 0);
                    sender.setSpeed('C', 0);

                    
                } else {
                    //send command to robot
                    sender.setSpeed('B', 75);
                    sender.setSpeed('C', 75);
                }
                Thread.sleep(300);
            }
            catch (InterruptedException ex) {
                Logger.getLogger(BTSender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        sender.close();

        
    }

   
}