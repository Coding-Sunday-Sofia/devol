package eu.veldsoft.devol.screen;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class PrintOut {
    DEScreen deScreen;

    /**
     * Constructor of the class.
     */
    public PrintOut(DEScreen app) {
        deScreen = app;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void printResult() {
        int i, imax;
        double[] best = new double[100];

        try {
            FileOutputStream out = new FileOutputStream("DEOut.dat");
            PrintStream printout = new PrintStream(out, false);
            imax = deScreen.getDimension();
            best = deScreen.getBest();
            for (i = 0; i < imax; i++) {
                printout.println("best[" + i + "]=" + best[i] + "\r");
            }

            printout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
