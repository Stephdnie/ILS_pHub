package src.io;

import java.util.Random;
import umontreal.iro.lecuyer.rng.LFSR113;
import umontreal.iro.lecuyer.rng.RandomStream;

/**
 * @author Angel A. Juan date: 140926
 */
public class Test {

    private String instanceName; // instance name.   
    private double maxTime; // maximum execution time.
    final private transient String instancePath; // path to the instance file.
    private int nIter; // maximum number of iterations.
    private String distr; // probability distribution.
    private double beta; // parameter for the biased randomization 
    private int seed; // seed for the rng.
    private double pRemove; // % of hubs to be removed during the perturbation

    public Test(String iName, String iPath, double t, int ite, String dis, double b, int s, double p) {
        instanceName = iName;
        instancePath = iPath;
        maxTime = t;
        nIter = ite;
        distr = dis;
        beta = b;
        seed = s;
        pRemove = p;
    }

    public String getDistr() {
        return distr;
    }

    public void setDistr(String distr) {
        this.distr = distr;
    }

    public double getpRemove() {
        return pRemove;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public String getInstanceFullPath() {
        return instancePath + instanceName;
    }

    public double getMaxTime() {
        return maxTime;
    }

    public int getnIter() {
        return nIter;
    }

    public String getDistribution() {
        return distr;
    }

    public double getBeta() {
        return beta;
    }

    public int getSeed() {
        return seed;
    }

    public void setpRemove(double pRemove) {
        this.pRemove = pRemove;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public void setnIter(int nIter) {
        this.nIter = nIter;
    }

    public void setDistribution(String distrib) {
        this.distr = distrib;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

}
