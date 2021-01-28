package eu.veldsoft.devol.panel;

// Import all classes from the java.awt package

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import eu.veldsoft.devol.screen.DEScreen;

// Import screens

public class MonitorPanel extends Canvas
/***********************************************************
 ** ** Defines the output panel which shows the current data. ** ** Authors:
 * Mikal Keenan ** Rainer Storn ** **
 ***********************************************************/
{
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
    Dimension minSize; // set the minimum size of the canvas
    Image offscreenImage; // This is where the image is stored
    Graphics offscreenGraphics;
    boolean initialized = false;

    public MonitorPanel(DEScreen app)
    /***************************************
     ** Constructor. **
     ***************************************/
    {
        deScreen = app;
        minSize = new Dimension(100, 60); // set minimum size
    }

    public Dimension preferredSize()
    /******************************************
     ** The layout managers need this. **
     ******************************************/
    {
        return minimumSize();
    }

    public synchronized Dimension minimumSize()
    /******************************************
     ** The layout managers need this. **
     ******************************************/
    {
        return minSize;
    }

    public void paint(Graphics G)
    /******************************************
     ** Paints the current optimization data. **
     ******************************************/
    { // Get the values to display
        Dimension Area = size();

        int width = Area.width;
        int height = Area.height;

        if (!initialized) {
            offscreenImage = createImage(width, height);
            offscreenGraphics = offscreenImage.getGraphics();
            initialized = true;
        }

        offscreenGraphics.setColor(DEScreen.BACKGROUNDCOLOR); // Like applet
        offscreenGraphics.fill3DRect(0, 0, width - 1, height - 1, true); // ?
        offscreenGraphics.setColor(Color.blue); // Text color
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
}// End class MonitorPanel
