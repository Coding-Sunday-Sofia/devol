package eu.veldsoft.devol.panel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import eu.veldsoft.devol.screen.DEScreen;

/**
 * The little status panel at the bottom of the application.
 *
 * @author Mikal Keenan
 * @author Rainer Storn
 */
@SuppressLint("AppCompatCustomView")
public class StatusPanel extends TextView {
    public final static String runningString = "Running...";
    public final static String pausedString = "Paused...";
    public final static String completedString = "Completed...";
    public final static String nullString = "";

    public final static Typeface labelFont = Typeface.create("Dialog", Typeface.NORMAL); //new Font("Dialog", Font.PLAIN, 10);

    public DEScreen deScreen;

    /**
     * Constructor.
     */
    public StatusPanel(Context context, DEScreen app) {
        super(context);
        deScreen = app;
        setTypeface(labelFont);
    }

    /**
     * Show nothing.
     */
    public void idle() {
        setText(nullString);
    }

    /**
     * Show "Running".
     */
    public void running() {
        setText(runningString);
    }

    /**
     * Show "Paused".
     */
    public void pause() {
        setText(pausedString);
    }

    /**
     * Show "Running".
     */
    public void resume() {
        setText(runningString);
    }

    /**
     * Show nothing.
     */
    public void stop() {
        setText(nullString);
    }

    /**
     * Show "Completed".
     */
    public void done() {
        setText(completedString);
    }
}
