package RefBox;

public class GameStatePacket
{
	private char cmd;            // current referee command
	private int cmd_counter;     // increments each time new command is set
	private int goals_blue;      // current score for blue team
	private int goals_yellow;    // current score for yellow team
	private int time_remaining;  // seconds remaining for current game stage

	public GameStatePacket()
	{
		cmd = '\u0000';
		cmd_counter = 0;
		goals_blue = 0;
		goals_yellow = 0;
		time_remaining = 0;
	}

	public char getCmd()
	{
		return cmd;
	}

	public void setCmd( char cmd )
	{
		this.cmd = cmd;
	}

	public int getCmd_counter()
	{
		return cmd_counter;
	}

	public void setCmd_counter( int cmd_counter )
	{
		this.cmd_counter = cmd_counter;
	}

	public int getGoals_blue()
	{
		return goals_blue;
	}

	public void setGoals_blue( int goals_blue )
	{
		this.goals_blue = goals_blue;
	}

	public int getGoals_yellow()
	{
		return goals_yellow;
	}

	public void setGoals_yellow( int goals_yellow )
	{
		this.goals_yellow = goals_yellow;
	}

	public int getTime_remaining()
	{
		return time_remaining;
	}

	public void setTime_remaining( int time_remaining )
	{
		this.time_remaining = time_remaining;
	}
}
