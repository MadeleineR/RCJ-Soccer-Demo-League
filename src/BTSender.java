import lejos.pc.comm.NXTCommLogListener;
import lejos.pc.comm.NXTConnector;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BTSender
{
    private NXTConnector conn;
    private DataOutputStream dos;

    public BTSender()
    {
        // Connect to any NXT over Bluetooth
        conn = new NXTConnector();
        conn.addLogListener( new NXTCommLogListener()
        {
            @Override
            public void logEvent( String message )
            {
                //  System.out.println( "BTSend Log.listener: " + message );
            }

            @Override
            public void logEvent( Throwable throwable )
            {
                //System.out.println( "BTSend Log.listener - stack trace: " );
                //throwable.printStackTrace();
            }
        });

        int attempts = 5;
        while (!conn.connectTo( "btspp://" ))
        {
            System.out.println( "Failed to connect to any NXT." );
            attempts--;
            if(attempts == 0)
            {
                System.out.println("Tired of trying... Exiting.");
                System.exit(1);
            }
            System.out.println("Trying again…");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                Logger.getLogger(BTSender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        dos = new DataOutputStream( conn.getOutputStream() );
        //DataInputStream dis = new DataInputStream(conn.getInputStream());     // if you want to receive something from the robot
    }
    
    
    

    
    public void sendCmd(int cmd)
    {
        try {
            dos.writeInt(cmd);
            dos.flush();
        } catch (IOException e) {
            //e.printStackTrace();
            System.exit(0);
        }
    }
    
    public void setSpeed(char motor_port, int speed){
        
        int cmd;
        
        if(motor_port == 'A'){
            cmd = 1000 + speed;
        }
        
        else if (motor_port == 'B'){
            cmd = 2000 + speed;
        }
        
        else if (motor_port == 'C'){
            cmd = 3000 + speed;
        }
        else{
            cmd = 0;
        }
        sendCmd(cmd);
    }
    
    
    public void close(){
        
        try
         {
            dos.close();
            conn.close();
         }
         catch ( IOException e )
         {
            System.out.println( "Connection already closed." );
         }
    }

}

