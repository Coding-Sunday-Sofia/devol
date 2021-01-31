package eu.veldsoft.devol.problem;

import eu.veldsoft.devol.de.T_DEOptimizer;

/**
 * Describes the problem to solve. The abstract class declares the methods that all subtypes must
 * have. Not all of them, however, are implemented in the abstract class.
 *
 * @author Mikal Keenan
 * @author Rainer Storn
 */
public abstract class DEProblem {
    public static final int NAPTIME = 10;
    public double mincost;
    int dim;
    double[] best;

    /**
     * @return True if evaluation is completed.
     */
    public abstract boolean completed();

    /**
     * The actal cost function.
     */
    public abstract double evaluate(T_DEOptimizer t_DEOptimizer, double[] X,
                                    int dim);

    /**
     * Best vector.
     */
    public final double[] getBest() {
        return best;
    }

    /**
     * Dimensionality of the problem.
     */
    public final int getLength() {
        return dim;
    }
}
