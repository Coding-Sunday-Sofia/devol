package eu.veldsoft.devol.plot;

import android.os.Build;

import androidx.annotation.RequiresApi;

import eu.veldsoft.devol.ptplot.Plot;
import eu.veldsoft.devol.screen.DEScreen;

public class DeLuxePlotGraph1 extends Plot
/***********************************************************
 ** ** ** Authors: Rainer Storn ** ** Date: 1/30/99 ** **
 ***********************************************************/
{

    protected DEScreen deScreen;
    protected int initFlag; // 1: indicates that initialization must be done
    protected double min_x; // Relative coordinates
    protected double max_x;
    protected double min_y;
    protected double max_y;
    double[] best; // best parameter vector so far
    int dim; // dimension of the problem
    int no_of_persistent_points;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public DeLuxePlotGraph1(DEScreen father, int width, int height)
    /***********************************************************
     ** Constructor. **
     ***********************************************************/
    {
        deScreen = father;
        initFlag = 1; // indicates that init is required

        // ---This is not very clean programming since we must-----
        // ---explicitly know the mapping of problem number--------
        // ---to problem. But for demonstration purposes it--------
        // ---Shall suffice----------------------------------------

        if (father.controlPanel.current_problem == 0) {
            no_of_persistent_points = 5;
        } else {
            no_of_persistent_points = 9;
        }

        // Relative coordinates for the plot
        min_x = 0;
        max_x = +10;
        min_y = -20;
        max_y = +20;

        this.setXRange(min_x, max_x); // x-range
        this.setYRange(min_y, max_y); // y-range

        this.setGrid(true); // plot grid
        this.setBars(0.5, 0.0); // use bars with width=0.5, offset=0.0

        this.setNumSets(15); // fifteen graphs my be written into one picture
        this.setPointsPersistence(no_of_persistent_points); // lifetime of
        // points

        this.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void refreshImage()
    /***********************************************************
     ** Update function which recomputes the variable screen ** image. **
     ***********************************************************/
    {
        dim = deScreen.getDimension();
        best = deScreen.getBest();
        double coefficient;

        double x2;
        boolean first = true; // first point is not connected to a predecessor
        int i;

        // ----Take care of proper initialization------------
        if (initFlag == 1) {
            init();
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

        // -----now plot new graph-----------------------------
        for (i = 0; i < dim; i++) {
            addPoint(1, i, best[i], !true);
        }
        paint(_graphics);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void paint()
    /*******************************************************
     ** Whenever the component is exposed anew, this method * is called. **
     *******************************************************/
    {
        // ----Take care of proper initialization------------
        if (initFlag == 1) {
            init();
            drawPlot(_graphics, true); // make graphics immediately visible
            initFlag = 0; // prevent that graphics is constantly initialized
        }

        // ----Take care of screen resize--------------------
        if (_reshapeFlag == 1) {
            drawPlot(_graphics, true); // make graphics immediately visible
            _reshapeFlag = 0;
        }
    }

}
