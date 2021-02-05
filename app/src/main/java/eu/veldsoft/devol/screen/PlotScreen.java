package eu.veldsoft.devol.screen;

import android.graphics.Color;
import android.os.Build;
import android.util.Size;

import androidx.annotation.RequiresApi;

import eu.veldsoft.devol.dummy.GridBagConstraints;
import eu.veldsoft.devol.dummy.GridBagLayout;
import eu.veldsoft.devol.plot.DeLuxePlotGraph0;
import eu.veldsoft.devol.plot.DeLuxePlotGraph1;
import eu.veldsoft.devol.plot.DeLuxePlotGraph2;
import eu.veldsoft.devol.plot.PlotGraph3;

/**
 * A new screen where the optional plotting takes place.
 *
 * @uthors Rainer Storn
 * @date: 3/16/98
 */
public class PlotScreen extends Screen {
    public static final int BACKGROUNDCOLOR = Color.LTGRAY;
    // public PlotGraph0 plotGraph0; // First graphics panel
    public DeLuxePlotGraph0 plotGraph0; // First graphics panel
    // public PlotGraph1 plotGraph1; // Second graphics panel
    public DeLuxePlotGraph1 plotGraph1; // Second graphics panel
    // public PlotGraph2 plotGraph2; // Third graphics panel
    public DeLuxePlotGraph2 plotGraph2; // Third graphics panel
    public PlotGraph3 plotGraph3; // Fourth graphics panel
    public DEScreen app;
    Size minSize; // set the minimum size of the screen
    GridBagLayout gridbag = new GridBagLayout();
    int graph_type; // selects what to plot

    @RequiresApi(api = Build.VERSION_CODES.O)
    public PlotScreen(DEScreen deScreen, int i)
    /***********************************************************
     ** Define a screen which contains the graphics to be ** plotted. The
     * selection process is done in a pretty ** simple way, no inheritance or
     * strategy pattern or the ** like. This leads to a slight inefficiency in
     * the ** refreshImage() method but it allows you to use ** totally
     * different plotting routines for the various ** subplots. You needn't take
     * the plotting functions ** provided in plot graph. **
     ***********************************************************/
    {
        super("Plot"); // print Title
        setBackground(Color.LTGRAY);
        minSize = new Size(300, 300); // set minimum size

        app = deScreen;
        graph_type = i;

        if (graph_type == 0) {
            this.setTitle("Tolerance Scheme Plot"); // print Title
        } else if (graph_type == 1) {
            this.setTitle("Coefficient Plot"); // print Title
        } else if (graph_type == 2) {
            this.setTitle("Magnitude Plot"); // print Title
        } else {
            this.setTitle("Pole/Zero Plot"); // print Title
        }

        this.resize(400, 400);

        int w = size().width;
        int h = size().height;

        // ----Place the graph on the plot screen-----
        this.setLayout(gridbag); // apply gridbag layout to the
        // entire screen

        // ----Create the graphics panel--------------
        if (graph_type == 0) {
            // plotGraph0 = new PlotGraph0(app,w,h);
            plotGraph0 = new DeLuxePlotGraph0(app, w, h);
            constrain(this, plotGraph0, 0, 0, 1, 1, GridBagConstraints.BOTH,
                    GridBagConstraints.CENTER, 1.0, 1.0, 10, 10, 10, 10);
        } else if (graph_type == 1) {
            // plotGraph1 = new PlotGraph1(app,w,h);
            plotGraph1 = new DeLuxePlotGraph1(app, w, h);
            constrain(this, plotGraph1, 0, 0, 1, 1, GridBagConstraints.BOTH,
                    GridBagConstraints.CENTER, 1.0, 1.0, 10, 10, 10, 10);
        } else if (graph_type == 2) {
            // plotGraph2 = new PlotGraph2(app,w,h);
            plotGraph2 = new DeLuxePlotGraph2(app, w, h);
            constrain(this, plotGraph2, 0, 0, 1, 1, GridBagConstraints.BOTH,
                    GridBagConstraints.CENTER, 1.0, 1.0, 10, 10, 10, 10);
        } else {
            plotGraph3 = new PlotGraph3(app, w, h);
            constrain(this, plotGraph3, 0, 0, 1, 1, GridBagConstraints.BOTH,
                    GridBagConstraints.CENTER, 1.0, 1.0, 10, 10, 10, 10);
        }

    }

    public Size preferredSize()
    /***********************************************************
     ** The layout manager needs this to determine the right ** size. **
     ***********************************************************/
    {
        return minimumSize();
    }

    public synchronized Size minimumSize()
    /***********************************************************
     ** The layout manager needs this to determine the right ** size. **
     ***********************************************************/
    {
        return minSize;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void refreshImage()
    /***********************************************************
     ** Updates the actual graph that is plotted. **
     ***********************************************************/
    {
        if (graph_type == 0) {
            plotGraph0.refreshImage();
        } else if (graph_type == 1) {
            plotGraph1.refreshImage();
        } else if (graph_type == 2) {
            plotGraph2.refreshImage();
        } else {
            plotGraph3.refreshImage();
        }
    }
}
