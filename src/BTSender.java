

import client.Client;
import refbox.RefBox;
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

	public static void main( String[] args )
	{
        RefBox r = new RefBox();
        Thread t = new Thread( r );
        t.start();
        
        // edit Madeleine
        Client visionclient = new Client();
        Thread t1 = new Thread(visionclient);
        t1.start();

        BTSender sender = new BTSender();

		while ( true )
		{
            try {
                String refCMD;
                refCMD = r.getCmd();
                System.out.println( refCMD );

    // check the referee command
                if ( "HALT".equals(refCMD) )
                {
        // send command to robot
                    System.out.println( "Sending 2 (Stop)" );
                    sender.sendCmd(2);      // sends only one number! (see method below)
                }
                else
                {
                    if (visionclient.getRx()<220 || visionclient.getRx()>575){
                        System.out.println("außerhalb");
                        sender.sendCmd(3);
                    }else{
                        System.out.println( "innerhalb" );
                        sender.sendCmd(1); // sends only one number! (see method below)
                    }
                    
                }
                Thread.sleep(300);
            }
            /*try
            {
            //dis.close();
            dos.close();
            conn.close();
            }
            catch ( IOException e )
            {
            System.out.println( "Connection already closed." );
            }*/ 
            catch (InterruptedException ex) {
                Logger.getLogger(BTSender.class.getName()).log(Level.SEVERE, null, ex);
            }
		}


		/*try
		{
			//dis.close();
			dos.close();
			conn.close();
		}
		catch ( IOException e )
		{
			System.out.println( "Connection already closed." );
		}*/
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

	/*// example for receiving data from robot
	try {
		System.out.println("Received " + dis.readInt());
	} catch (IOException ioe) {
		System.out.println("IO Exception reading bytes:");
	    System.out.println(ioe.getMessage());
	}*/
}

