

import java.io.DataInputStream;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import lejos.util.Delay;

public class BTReceiver {
    public static void main(String [] args)  throws Exception
	{
		String connected = "Connected";
		String disconnected = "Disconnected";
		String waiting = "Waiting...";
		String closing = "Closing...";

		LCD.drawString(waiting,0,0);
		LCD.refresh();

		BTConnection btc = Bluetooth.waitForConnection();
		DataInputStream dis = btc.openDataInputStream();
		//DataOutputStream dos = btc.openDataOutputStream();    // if you want to send something to the PC

		int cmd = 0;
                
                NXTMotor motora = new NXTMotor(MotorPort.A);
                NXTMotor motorb = new NXTMotor(MotorPort.B);
                NXTMotor motorc = new NXTMotor(MotorPort.C);

		while (true)
		{
			if( Button.readButtons()>0)
			{
				//dos.close();
				LCD.clear();
				LCD.drawString(closing,0,0);
				LCD.refresh();
				dis.close();
				btc.close();
				Thread.sleep(100); // wait for data to drain
				break;
			}
			else
			{
				try
				{
					cmd = dis.readInt();
				}
				catch (Exception e)
				{
					Motor.B.stop();
                                        Motor.C.stop();
					LCD.clear();
					LCD.drawString(disconnected,0,0);
					LCD.refresh();
					Thread.sleep(1500);
					break;
				}
				LCD.clear();
				LCD.drawString(connected,0,0);
				LCD.refresh();

                // checks what command the robot received and executes it
				if(cmd <= 1100){ //Command for motor A
                                    motora.setPower(cmd-1000);
                                    motora.forward();
                                } else if(cmd <= 2100){ //Command for motor B
                                    motorb.setPower(cmd-2000);
                                    motorb.forward();
                                } else if(cmd <= 3100){ //Command for motor C
                                    motorc.setPower(cmd-3000);
                                    motorc.forward();
                                }
                                /*
                                switch (cmd)
				{
					case 1:
						LCD.drawString("-1-", 0,3);
						motorb.setPower(cmd);
                                                Motor.B.forward();
                                                Motor.C.setPower(50);
                                                Motor.C.forward();                                                	
						break;
					case 2:
						LCD.drawString("-2-", 0,3);
						Motor.B.stop();
                                                Motor.C.stop();
						break;
                                        case 3: 
                                                Motor.B.setSpeed(360); 
                                                Motor.C.setSpeed(360);
                                                Motor.B.backward();
                                                Motor.C.backward();
                                                Motor.B.rotate(1000);
						LCD.drawString("-3-", 0,3);
                                                break;
					default:
						Motor.C.setSpeed(50);
                                                Motor.C.forward();
						LCD.drawString("default", 0,3);
                                                break;
						//dos.writeInt(-n);
						//dos.flush();
				}
                                */
				LCD.refresh();
			}
		}
	}
}
