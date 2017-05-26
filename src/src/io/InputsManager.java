package src.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import src.structures.Nodes;
import src.structures.Connections;
/**
 * @author Stephanie Alvarez <salvarezferna@uoc.edu>/**
 *

Data file format for USApHMP file:
<n>                                     Number of nodes
<x[1]> <y[1]>                   x & y coordinates of node 1
 :
 :
<x[n]> <y[n]>                           x & y coordinates of node n
<w[1][1]> <w[1][2]> ... <w[1][n]>       flow from node 1 to all others
 :         :             :
 :         :             :
<w[n][1]> <w[n][2]> ... <w[n][n]>       flow from node n to all others
<p>                                     Number of hubs (for p-hub median problems)
<c>                                     Collection cost
<t>                                     Transfer cost
<d>                                     Distribution cost
*/


public class InputsManager {

    public static Inputs readInputs(String inputsPath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputsPath));
        Scanner in = new Scanner(reader);
        Inputs inputs = null;
        inputs = new Inputs();

        in.useLocale(Locale.US);

        int nNodes = in.nextInt();
        //float positions[][] = new float[nNodes][2];
        float positionX = 0;
        float positionY = 0;
        for (int i = 0; i < nNodes; i++) {
           positionX = in.nextFloat();
           positionY = in.nextFloat();
           Nodes node = new Nodes(i, positionX, positionY);
           inputs.addNode(node);
        }
        double[][] flow = new double[inputs.getNumNodes()][inputs.getNumNodes()];

        for (int i = 0; i < nNodes; i++) {
            for (int j = 0; j < nNodes; j++) {
                flow[i][j] = in.nextFloat();
            }
        }
        inputs.setFlowMatrix(flow);
        
        int nHubs = in.nextInt();
        inputs.setnHubs(nHubs);
        float collectionCost = in.nextFloat();
        float transferCost = in.nextFloat();
        float distributionCost = in.nextFloat();

        inputs.setCollectionCost(collectionCost);
        inputs.setTransferCost(transferCost);
        inputs.setDistributionCost(distributionCost);

         return inputs;    
    }
    
    public static void createConnections(Inputs inputs){
    	for(int i = 0; i < inputs.getNumNodes(); i++){
    		for(int j = 0; j < inputs.getNumNodes(); j++){
    			Connections link = new Connections(inputs.getNodeList().get(i),inputs.getNodeList().get(j));
    			inputs.addConnections(link);
    		}
    	}
    }    
}
