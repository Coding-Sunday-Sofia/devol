package eu.veldsoft.devol.panel;

import android.graphics.fonts.Font;
import android.widget.EditText;
import android.widget.TextView;

import eu.veldsoft.devol.de.T_DEOptimizer;
import eu.veldsoft.devol.dummy.Event;
import eu.veldsoft.devol.dummy.GridBagConstraints;
import eu.veldsoft.devol.dummy.GridBagLayout;
import eu.veldsoft.devol.screen.DEScreen;

public class InputPanel extends MyPanel
/***********************************************************
 ** ** Defines the user operated input panel using scroll ** bars or text input.
 * ** ** Authors: Mikal Keenan ** Rainer Storn ** **
 ***********************************************************/
{
    public final static Font ScrollFont = new Font("Dialog", Font.PLAIN, 12);
    // maximum values for the control variables
    final static int NPMAX = 500;
    final static double FMAX = 1.0;
    final static double CRMAX = 1.0;
    final static double RMAX = 500.0;
    final static int RFMAX = 200;
    public DEScreen deScreen;
    // Text values for the scrollbars
    EditText NPText;
    EditText FText;
    EditText CrText;
    EditText RangeText;
    EditText RefreshText;
    // Labels to the text fields
    TextView NPLab;
    TextView FLab;
    TextView CrLab;
    TextView RangeLab;
    TextView RefreshLab;
    // The actual control variables
    int NP;
    double F;
    double Cr;
    double Range;
    int Refresh;
    // data type wrappers for the control variables
    Integer NPObj;
    Double FObj;
    Double CrObj;
    Double RangeObj;
    Integer RefreshObj;

    public InputPanel(DEScreen app)
    /***********************************************************
     ** ** Defines the input panel where DE's control variables ** can be set.
     * This can be done either by using a scroll- ** bar or by entering the data
     * into the text fields. ** ** Authors: Mikal Keenan ** Rainer Storn ** **
     ** Date: 3/16/98 ** **
     ***********************************************************/
    {
        deScreen = app; // know your container class

        /*----Initialization values for the DE control variables.-----*/
        NP = 30;
        F = 0.5;
        Cr = 1.0;
        Range = 100.0;
        Refresh = 1;
        // ToDo: Change this to GetParameters(deScreen.);
        this.setLayout(new GridBagLayout()); // Slider below the text

        // ------Parameter NP-----------------------------------
        NPText = new EditText(null); // Create the NP text field
        NPText.setTextSize(10);
        NPText.setEnabled(true);
        NPLab = new TextView(null);
        NPLab.setText("NP:");

        NPText.setText(String.valueOf(NP)); // Show initial value
        this.setLayout(new GridBagLayout()); // Slider below the text
        constrain(this, NPLab, 0, 0, 1, 1, GridBagConstraints.BOTH,
                GridBagConstraints.CENTER, 1.0, 1.0, 5, 5, 0, 0);
        constrain(this, NPText, 1, 0, 2, 1, GridBagConstraints.BOTH,
                GridBagConstraints.CENTER, 1.0, 1.0, 5, 5, 0, 0);

        // ------Parameter F-----------------------------------
        FText = new EditText(null);
        FText.setTextSize(10);
        FText.setEnabled(true);
        FLab = new TextView(null);
        FLab.setText("F:");

        FText.setText(Double.toString(F));
        constrain(this, FLab, 0, 2, 1, 1, GridBagConstraints.BOTH,
                GridBagConstraints.CENTER, 1.0, 1.0, 5, 5, 0, 0);
        constrain(this, FText, 1, 2, 2, 1, GridBagConstraints.BOTH,
                GridBagConstraints.CENTER, 1.0, 1.0, 5, 5, 0, 0);

        // ----Parameter CR---------------------------------------
        CrText = new EditText(null);
        CrText.setTextSize(10);
        CrText.setEnabled(true);
        CrLab = new TextView(null);
        CrLab.setText("CR:");

        CrText.setText(Double.toString(Cr));
        constrain(this, CrLab, 0, 4, 1, 1, GridBagConstraints.BOTH,
                GridBagConstraints.CENTER, 1.0, 1.0, 5, 5, 0, 0);
        constrain(this, CrText, 1, 4, 2, 1, GridBagConstraints.BOTH,
                GridBagConstraints.CENTER, 1.0, 1.0, 5, 5, 0, 0);

        // ---Init section-----------------------------------------
        RangeText = new EditText(null);
        RangeText.setTextSize(10);
        RangeText.setEnabled(true);
        RangeLab = new TextView(null);
        RangeLab.setText("Range:  ");

        RangeText.setText(Double.toString(Range));
        constrain(this, RangeLab, 0, 6, 1, 1, GridBagConstraints.BOTH,
                GridBagConstraints.CENTER, 1.0, 1.0, 5, 5, 0, 0);
        constrain(this, RangeText, 1, 6, 2, 1, GridBagConstraints.BOTH,
                GridBagConstraints.CENTER, 1.0, 1.0, 5, 5, 0, 0);

        // ------Range-----------------------------------
        RefreshText = new EditText(null); // Create the Refresh text field
        RefreshText.setTextSize(10);
        RefreshText.setEnabled(true);
        RefreshLab = new TextView(null);
        RefreshLab.setText("Refresh:");

        RefreshText.setText(String.valueOf(Refresh)); // Show initial value
        constrain(this, RefreshLab, 0, 8, 1, 1, GridBagConstraints.BOTH,
                GridBagConstraints.CENTER, 1.0, 1.0, 5, 5, 0, 0);
        constrain(this, RefreshText, 1, 8, 2, 1, GridBagConstraints.BOTH,
                GridBagConstraints.CENTER, 1.0, 1.0, 5, 5, 0, 0);

    }

    public boolean handleEvent(Event E)
    // Handles the scrollbar actions. Note: overriding the Scrollbar
    // class's own event handler.
    {
        repaint(); // Redraw everything

        return super.handleEvent(E); // Propagate message to the superclass
    }

    public void enable()
    /***************************************
     ** Enable the input. **
     ***************************************/
    {
        /*
         * If you enable the sliders, put them into the position which
         * corresponds to the text field
         */
        NPText.setEnabled(true);
        NPText.setText(String.valueOf(NP)); // set the text according to the
        // actual value

        FText.setEnabled(true);
        FText.setText(Double.toString(F));

        CrText.setEnabled(true);
        CrText.setText(Double.toString(Cr));

        RangeText.setEnabled(true);
        RangeText.setText(Double.toString(Range));

        RefreshText.setEnabled(true);
        RefreshText.setText(String.valueOf(Refresh)); // set the text according
        // to the actual value
    }

    public void disable()
    /********************************************
     ** Deactivate text fields and load the ** control variables with their new
     * values.**
     ********************************************/
    {
        // Convert from text representation to numbers
        NP = (Integer.valueOf(NPText.getText().toString())).intValue();
        F = (Double.valueOf(FText.getText().toString())).doubleValue();
        Cr = (Double.valueOf(CrText.getText().toString())).doubleValue();
        Range = (Double.valueOf(RangeText.getText().toString())).doubleValue();
        Refresh = (Integer.valueOf(RefreshText.getText().toString())).intValue();

        // check for violation of ranges and then set the variables
        if ((NP > NPMAX) || (NP < 0))
            NP = NPMAX;
        NPText.setText(String.valueOf(NP)); // reflect a change of variable in
        // the text

        if ((F > FMAX) || (F < 0))
            F = FMAX;
        FText.setText(Double.toString(F));

        if ((Cr > CRMAX) || (Cr < 0))
            Cr = CRMAX;
        CrText.setText(Double.toString(Cr));

        if ((Range > RMAX) || (Range < 0))
            Range = RMAX;
        RangeText.setText(Double.toString(Range));

        if ((Refresh > RFMAX) || (Refresh < 0))
            Refresh = RFMAX;
        RefreshText.setText(String.valueOf(Refresh)); // reflect a change of
        // variable in the text

        // disable the textfields, so that
        // nobody can tamper with them during optimization
        NPText.setEnabled(false);
        CrText.setEnabled(false);
        FText.setEnabled(false);
        RangeText.setEnabled(false);
        RefreshText.setEnabled(false);
    }

    public void pause()
    /********************************************
     ** Activate Cr, F, and refresh rate ** manipulation during pause. **
     ********************************************/
    {
        FText.setEnabled(true);
        FText.setText(Double.toString(F));

        CrText.setEnabled(true);
        CrText.setText(Double.toString(Cr));

        RefreshText.setEnabled(true);
        RefreshText.setText(Integer.toString(Refresh));
    }

    public void resume()
    /********************************************
     ** Deactivate Cr and F manipulation on ** resume. **
     ********************************************/
    {

        // Convert from text representation to numbers
        F = (Double.valueOf(FText.getText().toString())).doubleValue();
        Cr = (Double.valueOf(CrText.getText().toString())).doubleValue();
        Refresh = (Integer.valueOf(RefreshText.getText().toString())).intValue();
        // System.out.println(Refresh);

        // check for violation of ranges and then set the variables
        if ((F > FMAX) || (F < 0))
            F = FMAX;
        FText.setText(Double.toString(F));// reflect a change of variable in the
        // text

        if ((Cr > CRMAX) || (Cr < 0))
            Cr = CRMAX;
        CrText.setText(Double.toString(Cr));

        if ((Refresh > RFMAX) || (Refresh < 0))
            Refresh = RFMAX;
        RefreshText.setText(String.valueOf(Refresh)); // reflect a change of
        // variable in the text

        // disable the textfields, so that
        // nobody can tamper with them during optimization
        CrText.setEnabled(false);
        FText.setEnabled(false);
        RefreshText.setEnabled(false);
    }

    public void done()
    /********************************************
     ** Optimization finished. **
     ********************************************/
    {
        enable();
    }

    public void getParameters(T_DEOptimizer opt)
    /********************************************
     ** DE's control variables. **
     ********************************************/
    {
        opt.NP = NP;
        opt.F = F;
        opt.Cr = Cr;
        opt.Range = Range;
        opt.Refresh = Refresh; // How many generations until next plot
        // System.out.println(Refresh);
    }

}