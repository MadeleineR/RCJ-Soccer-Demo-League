package RefBox;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Receiver extends JFrame
{
	private static boolean running = true;
	private String ip;
	GameStatePacket gsp;

	JLabel[] lab = new JLabel[5];
	JTextField[] tf = new JTextField[5];

	public Receiver( String ip )
	{
		super( "Refbox Receiver" );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		this.ip = ip;
		gsp = new GameStatePacket();

		lab[0] = new JLabel( "Command" );
		lab[1] = new JLabel( "Cmd Counter" );
		lab[2] = new JLabel( "Goals Blue" );
		lab[3] = new JLabel( "Goals Yellow" );
		lab[4] = new JLabel( "Time Remaining" );

		tf[0] = new JTextField( "?" );
		tf[1] = new JTextField( "0" );
		tf[2] = new JTextField( "0" );
		tf[3] = new JTextField( "0" );
		tf[4] = new JTextField( "0" );

		for ( int i = 0; i < 5; i++ )
		{
			lab[i].setHorizontalAlignment( JLabel.RIGHT );
			lab[i].setFont( new Font( "", Font.BOLD, 14 ) );
			tf[i].setHorizontalAlignment( JTextField.CENTER );
			tf[i].setFont( new Font( "", Font.PLAIN, 14 ) );
			tf[i].setEditable( false );

		}

		JPanel pan = new JPanel( new GridLayout( 5, 2 ) );

		for ( int i = 0; i < 5; i++ )
		{
			pan.add( lab[i] );
			pan.add( tf[i] );
		}

		setContentPane( pan );
	}


	public void createFromBytes( byte[] buffer ) throws IOException
	{
		ByteBuffer buf = ByteBuffer.wrap( buffer );
		buf.order( ByteOrder.LITTLE_ENDIAN );
		String binary = Integer.toBinaryString( buffer[0] );
		int charCode = Integer.parseInt( binary, 2 );
		if ( gsp.getCmd() != (char) charCode )
		{
			gsp.setCmd( (char) charCode );
			tf[0].setText( Character.toString( (char) charCode ) );
		}

		binary = Integer.toBinaryString( buffer[1] );
		charCode = Integer.parseInt( binary, 2 );
		if ( gsp.getCmd_counter() != charCode )
		{
			gsp.setCmd_counter( charCode );
			tf[1].setText( String.valueOf( charCode ) );
		}

		binary = Integer.toBinaryString( buffer[2] );
		charCode = Integer.parseInt( binary, 2 );
		if ( gsp.getGoals_blue() != charCode )
		{
			gsp.setGoals_blue( charCode );
			tf[2].setText( String.valueOf( charCode ) );
		}

		binary = Integer.toBinaryString( buffer[3] );
		charCode = Integer.parseInt( binary, 2 );
		if ( gsp.getGoals_yellow() != charCode )
		{
			gsp.setGoals_yellow( charCode );
			tf[3].setText( String.valueOf( charCode ) );
		}

		binary = Integer.toBinaryString( buffer[4] );
		String str = Integer.toBinaryString( buffer[5] & 0xFF );
		while ( str.length() < 8 )
		{
			str = "0" + str;
		}
		charCode = Integer.parseInt( String.valueOf( binary + str ), 2 );
		if ( gsp.getTime_remaining() != charCode )
		{
			gsp.setTime_remaining( charCode );
			tf[4].setText( String.valueOf( charCode ) );
		}

	}


	public void run()
	{
		GameStatePacket gsp = new GameStatePacket();

		int port = 10001;
		try
		{
			InetAddress group = InetAddress.getByName( ip );
			MulticastSocket s = new MulticastSocket( port );
			s.joinGroup( group );

			byte[] buffer = new byte[10 * 1024];
			DatagramPacket data = new DatagramPacket( buffer, buffer.length );
			while ( running )
			{
				s.receive( data );

				createFromBytes( buffer );
			}
		}
		catch ( IOException e )
		{
			System.out.println( e.toString() );
		}
	}


	public static void main( String[] args )
	{
		if ( args.length < 2 )
		{
			System.out.println
					( "Usage: java Receiver 224.5.23.1" );
			return;
		}
		String ip = args[0];

		Receiver r = new Receiver( ip );
		r.setBounds( 100, 100, 300, 200 );
		r.setVisible( true );

		r.run();
	}

}
