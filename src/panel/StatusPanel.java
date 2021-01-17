package panel;

// Import all classes from the java.awt package
import java.awt.Font;
import java.awt.Label;

// Import screens
import screen.DEScreen;

public class StatusPanel extends Label
/***********************************************************
 ** ** The little status panel at the bottom of the ** application. ** **
 ** Authors: Mikal Keenan ** Rainer Storn ** **
 ***********************************************************/
{
	public final static String runningString = "Running...";
	public final static String pausedString = "Paused...";
	public final static String completedString = "Completed...";
	public final static String nullString = "";

	public final static Font labelFont = new Font("Dialog", Font.PLAIN, 10);

	public DEScreen deScreen;

	public StatusPanel(DEScreen app)
	/***************************************
	 ** Constructor. **
	 ***************************************/
	{
		deScreen = app;
		setFont(labelFont);
	}

	public void idle()
	/***************************************
	 ** Show nothing **
	 ***************************************/
	{
		setText(nullString);
	}

	public void running()
	/***************************************
	 ** Show "Running" **
	 ***************************************/
	{
		setText(runningString);
	}

	public void pause()
	/***************************************
	 ** Show "Paused" **
	 ***************************************/
	{
		setText(pausedString);
	}

	public void resume()
	/***************************************
	 ** Show "Running" **
	 ***************************************/
	{
		setText(runningString);
	}

	public void stop()
	/***************************************
	 ** Show nothing **
	 ***************************************/
	{
		setText(nullString);
	}

	public void done()
	/***************************************
	 ** Show "Completed" **
	 ***************************************/
	{
		setText(completedString);
	}
}// StatusPanel
