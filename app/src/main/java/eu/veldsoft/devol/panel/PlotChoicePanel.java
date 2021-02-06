package eu.veldsoft.devol.panel;

import android.os.Build;
import android.widget.CheckBox;

import androidx.annotation.RequiresApi;

import eu.veldsoft.devol.de.T_DEOptimizer;
import eu.veldsoft.devol.dummy.Event;
import eu.veldsoft.devol.dummy.GridBagConstraints;
import eu.veldsoft.devol.dummy.GridBagLayout;
import eu.veldsoft.devol.screen.DEScreen;

public class PlotChoicePanel extends MyPanel
/***********************************************************
 ** ** Allows to choose various plots. ** ** Authors: Rainer Storn ** **
 ***********************************************************/

{
    public DEScreen deScreen; // caller class
    public T_DEOptimizer t_DEOptimizer;
    GridBagLayout gridbag = new GridBagLayout();
    CheckBox[] plotCheckBox; // array of check boxes

    @RequiresApi(api = Build.VERSION_CODES.O)
    public PlotChoicePanel(DEScreen app, T_DEOptimizer opt)
    /***************************************
     ** Constructor. **
     ***************************************/
    {
        deScreen = app;
        t_DEOptimizer = opt;
        this.setLayout(gridbag); // P layout manager, 3 rows

        plotCheckBox = new CheckBox[3]; // three check boxes
        plotCheckBox[0] = new CheckBox(null);
        plotCheckBox[0].setText("Tolerance Scheme Plot");
        plotCheckBox[0].setChecked(false); // check box not checked

        plotCheckBox[1] = new CheckBox(null);
        plotCheckBox[1].setText("Coefficient Plot");
        plotCheckBox[1].setChecked(false); // check box not checked

        plotCheckBox[2] = new CheckBox(null);
        plotCheckBox[2].setText("Console Output");
        plotCheckBox[2].setChecked(false); // check box not checked
        deScreen.consoleDisable(); // and hence suppress console trace

        constrain(this, plotCheckBox[0], 0, 0, 1, 1, GridBagConstraints.BOTH,
                GridBagConstraints.CENTER, 1.0, 1.0, 5, 5, 0, 0);
        constrain(this, plotCheckBox[1], 0, 1, 1, 1, GridBagConstraints.BOTH,
                GridBagConstraints.CENTER, 1.0, 1.0, 5, 5, 0, 0);
        constrain(this, plotCheckBox[2], 0, 2, 1, 1, GridBagConstraints.BOTH,
                GridBagConstraints.CENTER, 1.0, 1.0, 5, 5, 0, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean handleEvent(Event e)
    /***************************************
     ** Handles mouse events for the panel.**
     ***************************************/
    {
        if (e.target == plotCheckBox[1] && e.id == Event.ACTION_EVENT) // tolerance
        // scheme
        // plot
        { // BUG:: No check for event type
            if (t_DEOptimizer.current_problem != 2) {
                if (plotCheckBox[1].isChecked() == true
                        && !deScreen.plot_screen1_exists) // new plot screen
                { // BUG:: No check if the plot screen screen already exists
                    deScreen.newPlotScreen1();
                    deScreen.plot_screen1_exists = true;
                } else // disable console output
                {
                    deScreen.destroyPlotScreen1();
                    deScreen.plot_screen1_exists = false;
                }
            } else {
                if (plotCheckBox[1].isChecked() == true
                        && !deScreen.plot_screen3_exists) // new plot screen
                { // BUG:: No check if the plot screen screen already exists
                    deScreen.newPlotScreen3();
                    deScreen.plot_screen3_exists = true;
                } else // disable console output
                {
                    deScreen.destroyPlotScreen3();
                    deScreen.plot_screen3_exists = false;
                }
            }
        }

        if (e.target == plotCheckBox[0] && e.id == Event.ACTION_EVENT) // parameter
        // plot
        { // BUG:: No check for event type
            if (t_DEOptimizer.current_problem != 2) {
                if (plotCheckBox[0].isChecked() == true
                        && !deScreen.plot_screen0_exists) // new plot screen
                { // BUG:: No check if the plot screen screen already exists
                    deScreen.newPlotScreen0();
                    deScreen.plot_screen0_exists = true;
                } else // disable console output
                {
                    deScreen.destroyPlotScreen0();
                    deScreen.plot_screen0_exists = false;
                }
            } else {
                if (plotCheckBox[0].isChecked() == true
                        && !deScreen.plot_screen2_exists) // new plot screen
                { // BUG:: No check if the plot screen screen already exists
                    deScreen.newPlotScreen2();
                    deScreen.plot_screen2_exists = true;
                } else // disable console output
                {
                    deScreen.destroyPlotScreen2();
                    deScreen.plot_screen2_exists = false;
                }
            }
        } else if (e.target == plotCheckBox[2] && e.id == Event.ACTION_EVENT) { // BUG::
            // No
            // check
            // for
            // event
            // type
            if (plotCheckBox[2].isChecked() == true) // enable console output
            {
                deScreen.consoleEnable();
            } else // disable console output
            {
                deScreen.consoleDisable();
            }
        }

        return true;
    }
}
