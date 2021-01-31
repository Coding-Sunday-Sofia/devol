package eu.veldsoft.devol.panel;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.os.Build;
import android.text.TextPaint;
import android.util.Size;

import androidx.annotation.RequiresApi;

import eu.veldsoft.devol.screen.DEScreen;

/**
 * Defines the output panel which shows the current data.
 *
 * @author Rainer Storn
 * @autor Mikal Keenan
 */
public class MonitorPanel extends Canvas {
    public final static String genString = "Generation :  ";
    public final static String evalString = "Evaluations:  ";
    public final static String valueString = "Minimum    :  ";
    public final static Font DataFont = null;//new Font("Dialog", Font.PLAIN, 10);
    private final int font_height = 10;
    private final int gutter = 5;
    private final int new_line = font_height + gutter;
    private final int x = 5;
    private final int y1 = 18;
    private final int y2 = y1 + new_line;
    private final int y3 = y2 + new_line;
    private final Paint rectanglePaintSettings = new Paint();
    private final Paint textPaintSettings = new TextPaint();
    public DEScreen deScreen;
    Size minSize; // set the minimum size of the canvas
    Bitmap offscreenImage; // This is where the image is stored
    Canvas offscreenGraphics;
    boolean initialized = false;

    /**
     * Constructor.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public MonitorPanel(DEScreen app) {
        rectanglePaintSettings.setColor(DEScreen.BACKGROUNDCOLOR);
        textPaintSettings.setAntiAlias(false);
        textPaintSettings.setColor(Color.BLUE);
        textPaintSettings.setTextSize(12);
        textPaintSettings.setTypeface(Typeface.create("Helvetica", Typeface.NORMAL));

        deScreen = app;
        minSize = new Size(100, 60); // set minimum size
    }

    /**
     * The layout managers need this.
     */
    public Size preferredSize() {
        return minimumSize();
    }

    /**
     * The layout managers need this.
     */
    public synchronized Size minimumSize() {
        return minSize;
    }

    /**
     * Paints the current optimization data.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void paint() {
        /* Get the values to display. */

        int width = getWidth();
        int height = getHeight();

        if (initialized == false) {
            offscreenImage = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            offscreenGraphics = new Canvas(offscreenImage);
            initialized = true;
        }

        offscreenGraphics.drawRect(0, 0, width - 1, height - 1, rectanglePaintSettings);

        offscreenGraphics.drawText(genString + deScreen.getGeneration(), x, y1, textPaintSettings);
        offscreenGraphics.drawText(evalString + deScreen.getEvaluation(), x, y2, textPaintSettings);
        offscreenGraphics.drawText(valueString + (float) deScreen.getMinimum(), x, y3, textPaintSettings);
        // reduce length of string to (float)

        // Display the data panel
        this.drawBitmap(offscreenImage, 0F, 0F, null);
    }

    /**
     * It is a dummy method. It was created only to bypass compilation error.
     */
    public void repaint() {
    }
}
