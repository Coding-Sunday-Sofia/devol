package eu.veldsoft.devol.screen;

import android.graphics.Color;
import android.os.Build;
import android.util.Size;

import androidx.annotation.RequiresApi;

import eu.veldsoft.devol.de.T_DEOptimizer;
import eu.veldsoft.devol.dummy.Event;
import eu.veldsoft.devol.dummy.GridBagConstraints;
import eu.veldsoft.devol.dummy.GridBagLayout;
import eu.veldsoft.devol.dummy.Panel;
import eu.veldsoft.devol.panel.ControlPanel;
import eu.veldsoft.devol.panel.InputPanel;
import eu.veldsoft.devol.panel.MonitorPanel;
import eu.veldsoft.devol.panel.PlotChoicePanel;
import eu.veldsoft.devol.panel.StatusPanel;

/**
 * This is the mediator class for the entire application. All major classes
 * are known to DEScreen. DEScreen also provides for the main graphical user
 * interface (GUI). Note that we have almost always employed the gridbag
 * layout to allow resizing of the GUI.
 *
 * @author Mikal Keenan
 * @author Rainer Storn
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class DEScreen extends Screen {
    public static final int BACKGROUNDCOLOR = Color.LTGRAY;

    /*-----Define identifiers which are used to select classes-------*/
    public String[] problem_identifier = {"T4", "T8", "Lowpass1"}; // chooses

    public String[] strategy_identifier = {"Best2Bin", "Rand1Bin",
            "RandToBest1Bin", // only show the most promising
            "Best3Bin", "Best1Bin"}; // strategies

    public T_DEOptimizer t_DEOptimizer; // Optimization thread
    // the cost
    // function

    public ControlPanel controlPanel; // Push controlPanel for start, pause etc.

    public MonitorPanel monitorPanel; // monitorPanel generations, evaluations,

    // minimum
    public InputPanel inputPanel; // inputPanel for NP, F and CR

    public StatusPanel statusPanel; // Shows the applet's current status

    public PlotChoicePanel plotChoicePanel;// Chooses various plots for

    // monitoring
    public PrintOut printOut;

    /* =====plotting stuff============================ */
    public PlotScreen plotScreen0; // a separate screen for plots

    public PlotScreen plotScreen1; // a separate screen for plots

    public PlotScreen plotScreen2; // a separate screen for plots

    public PlotScreen plotScreen3; // a separate screen for plots

    public boolean plot_screen0_exists = false; // flag for existence of plot

    // screen
    public boolean plot_screen1_exists = false; // flag for existence of plot

    // screen
    public boolean plot_screen2_exists = false; // flag for existence of plot

    // screen
    public boolean plot_screen3_exists = false; // flag for existence of plot

    Size minSize; // set the minimum size of the screen

    GridBagLayout gridbag = new GridBagLayout(); // define the layout
    // screen

    /* ===== end plotting stuff======================= */

    /**
     * Constructor of the class.
     */
    public DEScreen() {
        super("DeApp 1.0.3"); // print Title
        setBackground(Color.LTGRAY);

        // this really sets the initial size if you provide the
        // methods preferredSize() and minimumSize()
        minSize = new Size(205, 450); // set minimum size

        Panel mainPanel = new Panel(); // For controlPanel, monitorPanel,
        // inputPanel

        t_DEOptimizer = new T_DEOptimizer(this); // Create the optimizer object,
        // must be
        // after control and input
        // because the optimizer
        // needs data from these
        // panels

        statusPanel = new StatusPanel(null, this); // Create the status panel
        controlPanel = new ControlPanel(this); // Create button controls panel
        monitorPanel = new MonitorPanel(this); // Create monitorPanel panel
        inputPanel = new InputPanel(this); // Create scrollbar panel
        plotChoicePanel = new PlotChoicePanel(this, t_DEOptimizer); // Create
        // panel for
        // choosing
        // plots
        printOut = new PrintOut(this); // Object for file output

        System.out.println("Panels Created"); // Debug message

        /*---Arrange the subpanels on the panel P----------------------*/
        /*---which contains four subpanels.----------------------------*/
        mainPanel.setLayout(gridbag); // gridbag layout for the panel P
        constrain(mainPanel, controlPanel, 0, 0, 8, 1, GridBagConstraints.BOTH,
                GridBagConstraints.CENTER, 1.0, 1.0, 0, 0, 0, 0);
        constrain(mainPanel, monitorPanel, 0, 1, 8, 10, GridBagConstraints.BOTH,
                GridBagConstraints.CENTER, 1.0, 1.0, 5, 5, 0, 0);
        constrain(mainPanel, inputPanel, 0, 12, 5, 2, GridBagConstraints.BOTH,
                GridBagConstraints.CENTER, 1.0, 1.0, 5, 5, 0, 0);
        constrain(mainPanel, plotChoicePanel, 0, 15, 5, 2,
                GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1.0, 1.0, 5,
                5, 0, 0);

        /*---Arrange the main panel and the status panel on the DE screen--------------*/
        this.setLayout(gridbag); // apply gridbag layout to DEScreen
        constrain(this, mainPanel, 0, 0, 5, 10, GridBagConstraints.BOTH,
                GridBagConstraints.CENTER, 1.0, 1.0, 10, 10, 10, 10);
        constrain(this, statusPanel, 0, 15, 5, 1, GridBagConstraints.BOTH,
                GridBagConstraints.CENTER, 1.0, 1.0, 10, 10, 10, 10);

        t_DEOptimizer.start(); // start the optimization thread
    }

    /**
     * Handles events occuring from manipulating the screen (not the panels
     * inside).
     */
    public boolean handleEvent(Event e) { // The "x" field closes the application
        if (e.id == Event.WINDOW_DESTROY) {
            System.exit(0);
            return true;
        } else { // if something else happens, use the parent's class event
            // handler
            return super.handleEvent(e);
        } // we need this in order to keep writing access to the DEScreen
    } // especially in the input panel

    /**
     * The layout manager needs this to determine the right size.
     */
    public Size preferredSize() {
        return minimumSize();
    }

    /**
     * The layout manager needs this to determine the right size.
     */
    public synchronized Size minimumSize() {
        return minSize;
    }

    /**
     * Optimization is sleeping.
     */
    public void idle() {
        t_DEOptimizer.optIdle();
        monitorPanel.repaint();
        inputPanel.enable(); // allow for a change of control variables
        statusPanel.idle(); // show "Idle"
    }

    /**
     * Start the optimization.
     */
    public void start() {
        t_DEOptimizer.optStart();
        inputPanel.disable(); // freeze control variables
        statusPanel.running(); // show "Running"
        t_DEOptimizer.optStart();
    }

    /**
     * Pause the optimization.
     */
    public void pause() {
        t_DEOptimizer.makeNotReady();
        inputPanel.pause(); // allow to change some (not all) variables
        statusPanel.pause(); // show "Paused"
    }

    /**
     * Resume the optimization activity.
     */
    public void resume() {
        inputPanel.resume(); // freeze variables
        statusPanel.resume(); // show "Running"
        t_DEOptimizer.optResume();
    }

    /**
     * Stop the optimization.
     */
    public void stop() {
        t_DEOptimizer.makeNotReady();
        inputPanel.enable();
        statusPanel.stop(); // show nothing
        printOut.printResult(); // as the name says
    }

    /**
     * Is called when the optimization has ** come to an end.
     */
    public void done() {
        controlPanel.done();// reset control panel
        inputPanel.done(); // Enable input panel
        statusPanel.done(); // show "Completed"
        printOut.printResult(); // as the name says
    }

    /**
     * Access the problem identifiers.
     */
    public String[] getProblemIdentifiers() {
        return problem_identifier;
    }

    /**
     * Access the strategy identifiers.
     */
    public String[] getStrategyIdentifiers() {
        return strategy_identifier;
    }

    /**
     * Fetch DE's control variables from the various panels.
     */
    public void getParameters() {
        inputPanel.getParameters(t_DEOptimizer);
        controlPanel.getParameters(t_DEOptimizer);
    }

    /**
     * Define the cost function for DE.
     */
    public void setProblem(int Index) {
        t_DEOptimizer.setProblem(Index);
        idle();
    }

    /**
     * Update the status panel.
     */
    public void setStatus(String Text) {
        if (statusPanel != null) {
            statusPanel.setText(Text);
        }
    }

    /**
     * Update the monitor panel and the plot screens (if existing).
     */
    public void repaint() {
        monitorPanel.repaint();

        if (plot_screen0_exists) {
            plotScreen0.refreshImage();
        }

        if (plot_screen1_exists) {
            plotScreen1.refreshImage();
        }

        if (plot_screen2_exists) {
            plotScreen2.refreshImage();
        }

        if (plot_screen3_exists) {
            plotScreen3.refreshImage();
        }
    }

    /**
     * Get generation counter.
     */
    public int getGeneration() {
        return t_DEOptimizer.getGeneration();
    }

    /**
     * Get evaluation counter.
     */
    public int getEvaluation() {
        return t_DEOptimizer.getEvaluation();
    }

    /**
     * Best vector.
     */
    public double[] getBest() {
        return t_DEOptimizer.best;
    }

    /**
     * Get number of parameters.
     */
    public int getDimension() {
        return t_DEOptimizer.dim;
    }

    /**
     * Get best so far cost.
     */
    public double getMinimum() {
        return t_DEOptimizer.getMinimum();
    }

    /**
     * Enable console output trace of results.
     */
    public void consoleEnable() {
        t_DEOptimizer.consoleEnable();
    }

    /**
     * Enable console output trace of results.
     */
    public void consoleDisable() {
        t_DEOptimizer.consoleDisable();
    }

    /**
     * Instantiate plot screen.
     */
    public void newPlotScreen0() {
        plotScreen0 = new PlotScreen(this, 0);
        plotScreen0.pack(); // arrange components
        plotScreen0.show(); // and show them
    }

    /**
     * Destroy plot screen.
     */
    public void destroyPlotScreen0() {
        if (plotScreen0 != null) {
            plotScreen0.dispose(); // BUG:: No Null Pointer check
        }
    }

    /**
     * Instantiate plot screen.
     */
    public void newPlotScreen1() {
        plotScreen1 = new PlotScreen(this, 1);
        plotScreen1.pack(); // arrange components
        plotScreen1.show(); // and show them
    }

    /**
     * Destroy plot screen.
     */
    public void destroyPlotScreen1() {
        if (plotScreen1 != null) {
            plotScreen1.dispose(); // BUG:: No Null Pointer check
        }
    }

    /**
     * Instantiate plot screen.
     */
    public void newPlotScreen2() {
        plotScreen2 = new PlotScreen(this, 2);
        plotScreen2.pack(); // arrange components
        plotScreen2.show(); // and show them
    }

    /**
     * Destroy plot screen.
     */
    public void destroyPlotScreen2() {
        if (plotScreen2 != null) {
            plotScreen2.dispose(); // BUG:: No Null Pointer check
        }
    }

    /**
     * Instantiate plot screen.
     */
    public void newPlotScreen3() {
        plotScreen3 = new PlotScreen(this, 3);
        plotScreen3.pack(); // arrange components
        plotScreen3.show(); // and show them
    }

    /**
     * Destroy plot screen.
     */
    public void destroyPlotScreen3() {
        if (plotScreen3 != null) {
            plotScreen3.dispose(); // BUG:: No Null Pointer check
        }
    }
}
