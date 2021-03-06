package eu.veldsoft.devol.panel;

import android.graphics.fonts.Font;
import android.os.Build;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import eu.veldsoft.devol.de.T_DEOptimizer;
import eu.veldsoft.devol.dummy.Event;
import eu.veldsoft.devol.dummy.GridBagConstraints;
import eu.veldsoft.devol.dummy.GridBagLayout;
import eu.veldsoft.devol.screen.DEScreen;

/**
 * Defines the user operated control panel.
 *
 * @аuthor Rainer Storn
 */
public class ControlPanel extends MyPanel {
    public final static String startString = "Start";
    public final static String stopString = "Stop";
    public final static String pauseString = "Pause";
    public final static String resumeString = "Resume";
    public final static Font buttonFont = new Font("Dialog", Font.BOLD, 12);
    public final static Font choiceFont = new Font("Dialog", Font.PLAIN, 12);
    public final static Font errorFont = new Font("TimesRoman", Font.BOLD, 20);
    public DEScreen deScreen;
    public int current_problem = 0; // Initial problem number
    public int current_strategy = 0; // Initial strategy number
    GridBagLayout gridbag = new GridBagLayout();
    Button startButton; // The start button
    Button pauseButton; // The pause button
    Button exitButton; // The exit button
    Spinner problemList; // Problem control
    Spinner strategyList; // Strategy control
    // Labels to the choices
    TextView problemLab;
    TextView strategyLab;
    int i, n; // some general variables

    /**
     * Constructor.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ControlPanel(DEScreen app) {
        deScreen = app;
        // setLayout (new FlowLayout (FlowLayout.LEFT, 10, 10)); // "best
        // effort" layout
        // setLayout(new GridLayout(2,3));
        this.setLayout(gridbag); // P layout manager, 3 rows

        startButton = new Button(null); // Create the start button
        startButton.setFont(buttonFont); // Define its font
        startButton.setText(startString); // and its text

        pauseButton = new Button(null); // Create the pause button
        pauseButton.setFont(buttonFont); // Define its font
        pauseButton.setText(pauseString); // and its text

        exitButton = new Button(null); // Create the exit button
        exitButton.setFont(buttonFont); // Define its font
        exitButton.setText("Exit"); // and its text

        problemList = new Spinner(null);
        problemList.setFont(choiceFont);
        String[] identifier = deScreen.getProblemIdentifiers();
        n = identifier.length; // how many different cost functions ?
        for (i = 0; i < n; i++)
            problemList.addItem(identifier[i]); // Put problems into list
        // add (problemList); // Make the list visible
        problemLab = new TextView("Problem:");

        strategyList = new Spinner(null);
        strategyList.setFont(choiceFont);
        identifier = deScreen.getStrategyIdentifiers();
        n = identifier.length; // how many different strategies ?
        for (i = 0; i < n; i++) {
            strategyList.addItem(identifier[i]); // Put strategies into list
        }
        // add (strategyList); // Make the list visible
        strategyLab = new TextView("Strategy:");

        constrain(this, startButton, 0, 0, 1, 1, GridBagConstraints.BOTH,
                GridBagConstraints.CENTER, 1.0, 1.0, 5, 5, 0, 0);
        constrain(this, pauseButton, 1, 0, 1, 1, GridBagConstraints.BOTH,
                GridBagConstraints.CENTER, 1.0, 1.0, 5, 5, 0, 0);
        constrain(this, exitButton, 2, 0, 1, 1, GridBagConstraints.BOTH,
                GridBagConstraints.CENTER, 1.0, 1.0, 5, 5, 0, 0);
        constrain(this, problemLab, 0, 1, 1, 1, GridBagConstraints.BOTH,
                GridBagConstraints.CENTER, 1.0, 1.0, 5, 5, 0, 0);
        constrain(this, problemList, 1, 1, 2, 1, GridBagConstraints.BOTH,
                GridBagConstraints.CENTER, 1.0, 1.0, 5, 5, 0, 0);
        constrain(this, strategyLab, 0, 2, 1, 1, GridBagConstraints.BOTH,
                GridBagConstraints.CENTER, 1.0, 1.0, 5, 5, 0, 0);
        constrain(this, strategyList, 1, 2, 2, 1, GridBagConstraints.BOTH,
                GridBagConstraints.CENTER, 1.0, 1.0, 5, 5, 0, 0);

        reset(); // ?
        deScreen.statusPanel.idle(); // not nicely programmed
        // deScreen.setProblem (current_problem); // initialize the current
        // problem
    }

    /**
     * Handles mouse events for the panel.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean action(Event E, Object O) {
        if (E.target instanceof Spinner) {
            if (E.target.equals(problemList)) // Selected a problem
            {
                current_problem = ((Spinner) (E.target)).getSelectedItemPosition();
                deScreen.setProblem(current_problem);
            } else if (E.target.equals(strategyList)) // Selected a strategy
            {
                current_strategy = ((Spinner) (E.target)).getSelectedItemPosition();
                userReset();
                deScreen.idle();
            }
        } else if (E.target instanceof Button) {
            if (startString.equals(O)) {
                startButton.setText(stopString); // Now animation can only stop
                pauseButton.setEnabled(true); // or pause
                problemList.setEnabled(false); // No choices during animation
                strategyList.setEnabled(false); // No choices during animation
                deScreen.start();
            } else if (pauseString.equals(O)) {
                pauseButton.setText(resumeString); // Show: animation can
                // resume
                deScreen.pause();
            } else if (resumeString.equals(O)) {
                pauseButton.setText(pauseString); // Show: animation can pause
                deScreen.resume();
            } else if (stopString.equals(O)) {
                reset();
                deScreen.stop();
            } else if (E.target == exitButton) {
                System.exit(0);
                return true;
            } else
                return super.handleEvent(E);
        }
        repaint(); // Redraw everything
        return true;
    }

    /**
     * A special kind of reset.
     */
    private void userReset() {
        startButton.setText(startString); // Start button shows "start"
        pauseButton.setText(pauseString); // Pause button shows "pause"
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
    }

    /**
     * Load the optimizer with DE's control variables.
     */
    public void getParameters(T_DEOptimizer opt) {
        opt.current_problem = current_problem;
        opt.current_strategy = current_strategy;
    }

    /**
     * Do this when the optimization is finished.
     */
    public void done() {
        reset();
    }

    /**
     * Reset button labels.
     */
    public void reset() {
        userReset();
        problemList.setEnabled(true); // Enable problem selection
        strategyList.setEnabled(true); // Enable strategy selection
        repaint(); // Redraw everything
    }
}