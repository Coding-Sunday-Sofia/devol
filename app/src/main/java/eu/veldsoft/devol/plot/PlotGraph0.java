package eu.veldsoft.devol.plot;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Size;

import androidx.annotation.RequiresApi;

import eu.veldsoft.devol.screen.DEScreen;

/***********************************************************
 ** ** ** Authors: Mikal Keenan ** Rainer Storn ** ** Date: 3/16/98 ** ** Use
 * PlotGraph2 to adjust the graphics. **
 ***********************************************************/
public class PlotGraph0 extends Canvas {
    // The axes, and tolerance scheme are computed once and plotted into a
    // static image (staticI). The sample data is plotted into a background
    // image and copied into the Animation's gc to avoid flicker.

    protected Bitmap staticImage;
    protected Canvas staticGraphics = null;
    protected Bitmap offscreenImage;
    protected Canvas offscreenGraphics = null;

    protected boolean ready = false;
    protected DEScreen deScreen;
    protected Size Area;
    protected int x;
    protected int y;
    protected int w;
    protected int h;
    protected double min_x; // Relative coordinates
    protected double max_x;
    protected double min_y;
    protected double max_y;
    protected int abs_min_x; // Absolute coordinates
    protected int abs_max_x;
    protected int abs_min_y;
    protected int abs_max_y;
    protected int x_tics = 4; // Number of tics in X-direction
    protected int y_tics = 8; // Number of tics in Y-direction
    int choice = 1;
    double[] best;
    int dim;
    int plotting_samples = 60; // (or 60) make this problem dependent!
    int tolerance_samples = 300;

    /*
     * Description of the graphics screen
     *
     * abs_min_x abs_max_x ------------------------------------w | abs_min_y |
     * -------------------------- | | | | | | ((abs_max_x-abs_min_x)/2,
     * (abs_max_y-abs_min_y)/2) | | is center of drawing area | | abs_max_y | |
     * | h
     */
    Size minSize;
    int margin = 40;

    /***********************************************************
     ** Set size of the plot and define the axes. **
     ***********************************************************/
    public PlotGraph0(DEScreen father, int width, int height) {
        deScreen = father;
        minSize = new Size(width - margin, height - margin); // set minimum
        // size
    }

    /***********************************************************
     ** The layout manager needs this to determine the right ** size. **
     ***********************************************************/
    public Size preferredSize() {
        return minimumSize();
    }

    /***********************************************************
     ** The layout manager needs this to determine the right ** size. **
     ***********************************************************/
    public synchronized Size minimumSize() {
        return minSize;
    }

    /***********************************************************
     ** Transform relative X-values in absolute ones. **
     ***********************************************************/
    int absX(double x) {
        return abs_max_x + (int) (((double) (abs_min_x - abs_max_x))
                * ((max_x - x) / (max_x - min_x)));
    }

    /***********************************************************
     ** Transform relative Y-values in absolute ones. **
     ***********************************************************/
    int absY(double y) {
        return abs_min_y + (int) (((double) (abs_max_y - abs_min_y))
                * ((max_y - y) / (max_y - min_y)));
    }

    /*******************************************************
     ** Set some parameters. **
     *******************************************************/
    void initParameters() {
        x = 0;
        y = 0;
        w = getWidth();
        h = getHeight();

        abs_min_x = w / 8; // Compute some variables
        abs_max_x = w * 7 / 8;
        abs_min_y = h / 8;
        abs_max_y = h * 7 / 8;

        min_x = -1.5; // Mimimum abscissa value
        max_x = +1.5;
        min_y = -3; // Minimum ordinate value
        max_y = +10;
    }

    /**
     * Initializes background graphics, computes the tolerance scheme, etc.
     */
    public synchronized void initGraphics() {
        /*---static part of the graphics-------------*/
        staticImage = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888); // create a static image
        staticGraphics = new Canvas(staticImage); // graphics context for the

        // static image.
        Paint paint = new Paint();
        paint.setColor(Color.WHITE); // white background
        staticGraphics.drawRect(x, y, w, h, paint); // in rectangle area.

        preparePlot(staticGraphics); // plot axes and tolerance scheme

        /*---dynamic part of the graphics------------*/
        offscreenImage = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        offscreenGraphics = new Canvas(offscreenImage);

        /*---draw the static image on the graphics context---*/
        /*---offscreenGraphics (location x=0, y=0, no--------*/
        /*---ImageObserver).---------------------------------*/
        offscreenGraphics.drawBitmap(staticImage, 0, 0, null);
    }

    /*******************************************************
     ** As the name says: Initialization. **
     *******************************************************/
    void init() {
        ready = false;
        initParameters();
        initGraphics();
        ready = true;
    }

    /*******************************************************
     ** Draws the static part of the plot. **
     *******************************************************/
    void preparePlot(Canvas staticGraphics) {
        plotAxes(staticGraphics);
        plotTolerance(staticGraphics);
    }

    /*******************************************************
     ** Plot coordinate system in which polynomial will be ** plotted. **
     *******************************************************/
    public void plotAxes(Canvas g) {
        int tick_height = 3;
        int i; // counter variable
        // System.out.println("Plot Axes");

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);

        /*---Draw x-axis----------------------------*/
        int static0 = absY(0.0);
        g.drawLine(absX(min_x), static0, absX(max_x), static0, paint); // X axis

        /*---Draw y-axis----------------------------*/
        static0 = absX(0.0);
        g.drawLine(static0, absY(min_y), static0, absY(max_y), paint); // Y axis

        /*---Prepare x-axis ticks-------------------*/
        static0 = absY(0.0);
        int base_pos = absX(min_x);
        int static1 = static0;
        int static2 = static0 - tick_height;

        double increment = ((double) (abs_max_x - abs_min_x) / (double) x_tics)
                + 0.25;
        /*---Draw x-axis ticks----------------------*/
        for (i = 0; i <= x_tics; i++) {
            int x = base_pos + (int) (i * increment);
            g.drawLine(x, static1, x, static2, paint);
        }

        /*---Prepare y-axis ticks-------------------*/
        base_pos = absY(min_y);
        static0 = absX(0.0);
        static1 = static0 - 2;
        static2 = static0 + 2;

        increment = ((double) (abs_max_y - abs_min_y) / (double) y_tics);

        /*---Draw y-axis ticks----------------------*/
        for (i = 0; i <= y_tics; i++) {
            int y = base_pos - (int) (i * increment);
            g.drawLine(static1, y, static2, y, paint);
        }

        /*---Prepare x-tick labeling----------------*/
        paint.setTextSize(10);
        paint.setTypeface(Typeface.create("Helvetica", Typeface.NORMAL));

        static0 = absY(0) - 10;
        double x = min_x;

        increment = (max_x - min_x) / (double) x_tics;

        /*---Draw x-tick labeling-------------------*/
        for (i = 0; i <= x_tics; i++, x += increment) {
            Double DblObj = new Double(x);
            g.drawText(DblObj.toString(), absX(x) - 10, static0, paint);
        }

        Double DblObj = new Double(max_y);
        g.drawText(DblObj.toString(), absX(0), absY(max_y) - 8, paint);
    }

    /*******************************************************
     ** Computes the upper part of the tolerance scheme. **
     *******************************************************/
    protected double upperTolerance(double x) {
        if ((x < -1) || (x > 1)) {
            return 100;
        }

        if ((x > -1) || (x < 1)) {
            return 1;
        }

        return 100; // take care of the equality cases
    }

    /*******************************************************
     ** Computes the lower part of the tolerance scheme. ** Note that this scheme
     * only holds for the T4 ** problem. **
     *******************************************************/
    protected double lowerTolerance(double x) {
        if ((x < -1.2) || (x > 1.2)) {
            return 5.9;
        }

        if ((x > -1.2) || (x < 1.2)) {
            return -1;
        }

        return 5.9; // take care of the equality cases
    }

    /*******************************************************
     ** Plot the tolerance scheme. **
     *******************************************************/
    public void plotTolerance(Canvas g) {
        Paint paint = new Paint();
        paint.setColor(Color.RED); // Plot upper part of tolerance scheme

        int i;
        double coefficient = (max_x - min_x) / ((double) tolerance_samples);
        double d1, d2;

        d1 = min_x;
        for (i = 1; i <= tolerance_samples; i++, d1 = d2) {
            d2 = min_x + ((double) i) * coefficient;
            g.drawLine(absX(d1), absY(upperTolerance(d1)), absX(d2),
                    absY(upperTolerance(d2)), paint);
        }

        d1 = min_x; // Plot lower part of tolerance scheme
        for (i = 1; i <= tolerance_samples; i++, d1 = d2) {
            d2 = min_x + ((double) i) * coefficient;
            g.drawLine(absX(d1), absY(lowerTolerance(d1)), absX(d2),
                    absY(lowerTolerance(d2)), paint);
        }
    }

    /***********************************************************
     ** Evaluate the current polynomial. **
     ***********************************************************/
    public double polynomial(double[] temp, double x, int dim) {
        double y = temp[0];
        for (int j = 1; j < dim; j++) {
            y = x * y + temp[j];
        }

        return y;
    }

    /*******************************************************
     ** Plots the current polynomial. **
     *******************************************************/
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void plot(Canvas g) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        best = deScreen.getBest();
        dim = deScreen.getDimension();
        double coefficient = (max_x - min_x) / ((double) plotting_samples);
        double x1 = min_x;
        double x2;
        int i;
        for (i = 1; i <= plotting_samples; i++) {
            x2 = min_x + ((double) i) * coefficient;
            g.drawLine(absX(x1), absY(polynomial(best, x1, dim)), absX(x2),
                    absY(polynomial(best, x2, dim)), paint);
            x1 = x2;
        }
    }

    /*******************************************************
     ** Actually draws on the canvas. **
     *******************************************************/
    public void paint(Canvas g) {
        init(); // initializing with every paint() call
        // allows for resizing of the plot screen
        g.drawBitmap(offscreenImage, 0, 0, null);
    }

    /***********************************************************
     ** Update function which recomputes the variable screen ** image. **
     ***********************************************************/
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void refreshImage() {
        if (offscreenGraphics == null) {
            init();
        }

        offscreenGraphics.drawBitmap(staticImage, 0, 0, null);
        plot(offscreenGraphics);
        repaint();
    }

    /*******************************************************
     ** Overriding update() reduces flicker. The normal ** update() method clears
     * the screen before it ** repaints and hence causes flicker. We dont like
     * ** this and leave out the screen clearing. **
     *******************************************************/
    public void update(Canvas g) {
        g.drawBitmap(offscreenImage, 0, 0, null);
    }

    /**
     * It is a dummy method. It was created only to bypass compilation error.
     */
    private void repaint() {
    }
}
