package eu.veldsoft.devol.plot;

import android.os.Build;

import androidx.annotation.RequiresApi;

import eu.veldsoft.devol.ptplot.Plot;
import eu.veldsoft.devol.screen.DEScreen;

/**
 * @author Rainer Storn
 * @date: 2/5/99
 */
public class DeLuxePlotGraph0 extends Plot {
    protected DEScreen deScreen;
    protected int initFlag; // 1: indicates that initialization must be done
    protected double min_x; // Relative coordinates
    protected double max_x;
    protected double min_y;
    protected double max_y;

    double[] best; // best parameter vector so far
    int dim; // dimension of the problem
    int no_of_persistent_points;
    int plotting_samples; // number of samples used for graph plotting

    @RequiresApi(api = Build.VERSION_CODES.O)
    public DeLuxePlotGraph0(DEScreen father, int width, int height)
    /***********************************************************
     ** Constructor. **
     ***********************************************************/
    {
        deScreen = father;

        initFlag = 1; // indicates that init is required
        plotting_samples = 100; // number of samples for the plot
        no_of_persistent_points = plotting_samples; // no. of points which
        // remain
        // visible at a time.

        // Relative coordinates for the plot
        min_x = -2;
        max_x = +2;
        min_y = -1;
        max_y = +10;

        this.setXRange(min_x, max_x); // x-range
        this.setYRange(min_y, max_y); // y-range

        this.setGrid(true); // plot grid
        this.setNumSets(15); // fifteen graphs my be written into one picture
        this.setPointsPersistence(no_of_persistent_points); // lifetime of
        // points. This
        // holds for every
        // graph number. When the number of plotting samples
        // is less than those in the argument none of them
        // will disappear (we uses that for the tolerance scheme).

        this.show();
    }

    /**
     * Plot the tolerance scheme. We have much less points than
     * no_of_persistent_points so the tolerance scheme remains visible all
     * the time.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void plotTolerance() {
        // ---upper part of tolerance scheme--------
        addPoint(1, -1, +10, !true);
        addPoint(1, -1, +1, !false);
        addPoint(1, +1, +1, !false);
        addPoint(1, +1, +10, !false);

        // ---lower part of tolerance scheme--------
        addPoint(1, -2, +5.9, !true);
        addPoint(1, -1.2, +5.9, !false);
        addPoint(1, -1.2, -1, !false);
        addPoint(1, +1.2, -1, !false);
        addPoint(1, +1.2, +5.9, !false);
        addPoint(1, +2, +5.9, !false);
    }

    public double polynomial(double[] temp, double x, int dim)
    /***********************************************************
     ** Evaluate the current polynomial. **
     ***********************************************************/
    {
        double y = temp[0];
        for (int j = 1; j < dim; j++) {
            y = x * y + temp[j];
        }
        return y;
    }

    /**
     * Update function which recomputes the variable screen image.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void refreshImage() {
        dim = deScreen.getDimension();
        best = deScreen.getBest();
        double coefficient;
        double x2;
        boolean first = true; // first point is not connected to a predecessor
        int i;

        // ----Take care of proper initialization------------
        if (initFlag == 1) {
            init();
            plotTolerance();
            drawPlot(_graphics, true); // make graphics immediately visible
            initFlag = 0; // prevent that graphics is constantly initialized
        }
        // ***********************************************************
        // ****Here is the actual plotting routine********************
        // ***********************************************************

        // ----Colors (which are tightly linked to graph numbers)-----
        // 0: white
        // 1: red // graph number 1 is plotted in red
        // 2: blue
        // 3: green-ish
        // 4: black
        // 5: orange
        // 6: cadetblue4
        // 7: coral
        // 8: dark green-ish
        // 9: sienna-ish
        // 10: grey-ish
        // 11: cyan-ish

        coefficient = (max_x - min_x) / ((double) plotting_samples);

        // -----now plot new graph-----------------------------
        for (i = 0; i <= plotting_samples; i++) {
            x2 = min_x + ((double) i) * coefficient;
            this.addPoint(2, x2, polynomial(best, x2, dim), !first);
            first = false; // from now on points will be connected with lines
        }

        paint(_graphics);
    }

    /**
     * Whenever the component is exposed anew, this method is called.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void paint() {
        /* Take care of proper initialization. */
        if (initFlag == 1) {
            init();
            plotTolerance();
            drawPlot(_graphics, true); // make graphics immediately visible
            initFlag = 0; // prevent that graphics is constantly initialized
        }

        /* Take care of screen resize. */
        if (_reshapeFlag == 1) {
            /* Make graphics immediately visible. */
            drawPlot(_graphics, true);

            _reshapeFlag = 0;
        }
    }
}
