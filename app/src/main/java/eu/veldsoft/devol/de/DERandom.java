package eu.veldsoft.devol.de;

import java.util.Random;

public class DERandom extends Random
/***********************************************************
 ** ** Random number generator. Certainly not the best one ** around. So if you
 * are not satisfied, implement your ** own one. ** ** Authors: Mikal Keenan **
 ** Rainer Storn ** **
 ***********************************************************/
{

    public DERandom()
    /*********************************************************
     ** Constructor initializes the generator. **
     *********************************************************/
    {
        setMySeed(0);
    }

    public void setMySeed(long seed)
    /*********************************************************
     ** Random initialization. Hence your optimization ** results may differ from
     * run to run. **
     *********************************************************/
    {
        if (seed == 0)
            seed = System.currentTimeMillis();
        setSeed(seed);
    }

    public final int nextValue(int max) // BUG:: infinite loop if this method is
    // called next
    /*********************************************************
     ** Fetch the next integer random number ex [0,max]. **
     *********************************************************/
    {
        return (int) (nextDouble() * (double) max);
    }

    public final double nextValue(double range) // BUG:: infinite loop if this
    // method is called next
    /************************************************************
     ** Fetch the next double random number ex [-range,+range]. **
     ************************************************************/
    {
        return range * (1.0 - 2.0 * nextDouble());
    }
}
