package eu.veldsoft.devol.panel;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.fonts.Font;
import android.media.Image;
import android.util.Size;

import java.awt.Graphics;

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
    public final static Font DataFont = new Font("Dialog", Font.PLAIN, 10);
    private final int font_height = 10;
    private final int gutter = 5;
    private final int new_line = font_height + gutter;
    private final int x = 5;
    private final int y1 = 18;
    private final int y2 = y1 + new_line;
    private final int y3 = y2 + new_line;
    public DEScreen deScreen;
    Size minSize; // set the minimum size of the canvas
    Image offscreenImage; // This is where the image is stored
    Graphics offscreenGraphics;
    boolean initialized = false;

    /**
     * Constructor.
     */
    public MonitorPanel(DEScreen app) {
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
    public void paint(Graphics G) { // Get the values to display
        int width = getWidth();
        int height = getHeight();

        if (!initialized) {
            offscreenImage = createImage(width, height);
            offscreenGraphics = offscreenImage.getGraphics();
            initialized = true;
        }

        offscreenGraphics.setColor(DEScreen.BACKGROUNDCOLOR); // Like applet
        offscreenGraphics.fill3DRect(0, 0, width - 1, height - 1, true); // ?
        offscreenGraphics.setColor(Color.BLUE); // Text color
        offscreenGraphics.setFont(new Font("Helvetica", Font.PLAIN, 12)); // font
        // //
        // Text
        // font

        offscreenGraphics.drawString(genString + deScreen.getGeneration(), x,
                y1);
        offscreenGraphics.drawString(evalString + deScreen.getEvaluation(), x,
                y2);
        offscreenGraphics
                .drawString(valueString + (float) deScreen.getMinimum(), x, y3);
        // reduce length of string to (float)
        G.drawImage(offscreenImage, 0, 0, this); // Display the data panel
    }
}
