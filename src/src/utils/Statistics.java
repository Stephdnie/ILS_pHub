package src.utils;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

/**
 *
 * @author jde_armasa
 */
public class Statistics {
    
    
    public Statistics() {
    }
    
    public double calcVar(double[] data) {
        DescriptiveStatistics da = new DescriptiveStatistics(data);
        double var = da.getStandardDeviation(); //da.getVariance();
        return var;
    }
    
    public double calcStdDev(double[] data) {
        DescriptiveStatistics da = new DescriptiveStatistics(data);
        double desv = da.getStandardDeviation();
        return desv;
    }

    public double calcMaxMinRange(double[] data) {
        DescriptiveStatistics da = new DescriptiveStatistics(data);
        double maxminr = da.getMax() - da.getMin();
        return maxminr;
    }

    public double calcIQRange(double[] data) {
        DescriptiveStatistics da = new DescriptiveStatistics(data);
        double iqr = da.getPercentile(75) - da.getPercentile(25);
        return iqr;
    }
    
    public double calcQ3(double[] data) {
        DescriptiveStatistics da = new DescriptiveStatistics(data);
        double q3 = da.getPercentile(75);
        return q3;
    }
    
    public double calcPerc90(double[] data) {
        DescriptiveStatistics da = new DescriptiveStatistics(data);
        double p90 = da.getPercentile(90);
        return p90;
    }
    
 
}
