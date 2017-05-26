package src.algorithm;

//import com.sun.org.apache.xerces.internal.util.DOMUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;
import org.apache.commons.lang.ArrayUtils;

import src.io.ElapsedTime;
import src.io.Inputs;
import src.io.Test;
import src.io.Outputs;

import src.structures.Nodes;
import src.structures.Connections;
import src.structures.Hubs;

import src.structures.Solution;
import src.utils.Statistics;

/**
 *
 * @author Stephanie Alvarez <salvarezferna@uoc.edu>
 *
 */
public class ILS {

    public static Outputs solver(Test aTest, Inputs inputs, Random rng) {
        Outputs out = new Outputs();
        System.out.println("----------------------");
        Solution bestSol = new Solution();
        Solution baseSol = new Solution();

        double credit = 0;
        for (int i = 0; i < aTest.getnIter(); i++) {
            resetNodes(inputs); //reset the inputs
            LinkedList<Hubs> hubsInitSol = new LinkedList<Hubs>();
            hubsInitSol = genRandSol(inputs, rng, aTest); //locate randomly the initial nodes
            assignNodesToHub(inputs, rng, hubsInitSol, aTest); // the nodes are allocated to hubs applying 
            // biased randomization

            Solution initSol = new Solution(inputs.getNodeList(), hubsInitSol);
            baseSol = initSol;

            baseSol.localSearch(inputs); //local search procedure
            baseSol.calcCosts(inputs, aTest); //update the oobjective function
            if (i == 0) {
                bestSol = baseSol;
            }

            /*
                        perturbate
             */
            LinkedList<Hubs> hubsPerturbateSol = new LinkedList<Hubs>();
            LinkedList<Nodes> nodesPerturbateSol = new LinkedList<Nodes>();
            Solution cSol = new Solution();

            cSol = cSol.copyInto(baseSol, aTest); // copy baseSol into the new cSol
            hubsPerturbateSol = perturbate(cSol, rng, aTest); // a % of p hubs is eliminated and selected a % of
            // p hubs are randomly selected
            nodesPerturbateSol = newAssignNodesToHub(cSol, rng, hubsPerturbateSol, aTest); // nodes are allocated
            // to hubs following a BR
            Solution newSol = new Solution(nodesPerturbateSol, hubsPerturbateSol); // newSol is generated

            newSol.newLocalSearch(newSol); //local search procedure
            newSol.newCalcCosts(newSol, aTest, inputs); //update the objective funtion

            double delta = baseSol.getCosts() - newSol.getCosts(); // total cost of baseSol - total cost of newSol

            if (delta >= 0) { //if delta is greater than zero
                credit = delta;
                baseSol = newSol; //baseSol is the newSol
                if (newSol.getCosts() < bestSol.getCosts()) { // if total cost of newSol is better than total cost bestSol
                    bestSol = newSol; // now the bestSol is the newSol
                }

            } else if (-delta <= credit) { // if delta is less than the credit
                credit = 0;
                baseSol = newSol; //the baseSol is the newSol
            }
            //if(baseSol.getCosts()<bestSol.getCosts()){
            //bestSol = baseSol;
            //}		
        }
        out.setBestSol(bestSol);
        System.out.println("sol objective function " + bestSol.getCosts());
        return out;
    }

    public static void assignNodesToHub(Inputs inputs, Random rng, LinkedList<Hubs> hubs, Test atest) {
        int temp = 0;
        int temp2 = 0;
        while (temp2 < inputs.getNumNodes() - hubs.size()) {// hub nodes are not
            // considered
            Nodes thisHub = hubs.get(temp).getNode();

            for (int j = 0; j < hubs.get(temp).getHubToNodes().size(); j++) {

                Nodes node = new Nodes();

                int index = getRandomPosition(atest.getBeta(), hubs.get(temp).getHubToNodes().size(), rng);
                node = hubs.get(temp).getHubToNodes().get(index).getDestination();

                if (node.getAssignedHub() == null) {
                    node.setAssignedHub(thisHub);
                    for (Hubs hub : hubs) {
                        hub.removeNodeFromList(node);
                    }
                    break;
                }
            }
            temp++;
            if (temp == hubs.size()) {
                temp = 0;
            }
            temp2++;
        }

    }

    public static LinkedList<Nodes> newAssignNodesToHub(Solution sol, Random rng, LinkedList<Hubs> hubs, Test atest) {
        int temp = 0;
        int temp2 = 0;
        LinkedList<Nodes> nodeList = new LinkedList<Nodes>();

//        for(int i = 0; i < sol.getNodes().size(); i++){
//            int temp3 = 0;
//           Nodes thisHub = hubs.get(temp3).getNode();
//           Nodes node = new Nodes();
//           if(node.isHub()){
//               
//               node.setAssignedHub(thisHub);
//               nodeList.add(node);
//               temp3++;
//           }
//        }
        while (temp2 < sol.getNodes().size() - hubs.size()) {// hub nodes are not
            // considered
            Nodes thisHub = hubs.get(temp).getNode();

            for (int j = 0; j < hubs.get(temp).getHubToNodes().size(); j++) {

                Nodes node = new Nodes();

                int index = getRandomPosition(atest.getBeta(), hubs.get(temp).getHubToNodes().size(), rng);
                node = hubs.get(temp).getHubToNodes().get(index).getDestination();

                if (node.getAssignedHub() == null) {
                    node.setAssignedHub(thisHub);
                    nodeList.add(node);
                    for (Hubs hub : hubs) {
                        hub.removeNodeFromList(node);
                        if (nodeList.size() >= sol.getNodes().size() - hubs.size()) {
                            nodeList.add(hub.getNode());
                        }

                    }
                    break;
                }//nodeList.add(node);
            }
            temp++;
            if (temp == hubs.size()) {
                temp = 0;
            }
            temp2++;
        }
        return nodeList;
    }

    public static LinkedList<Hubs> genRandSol(Inputs inputs, Random rng, Test aTest) {
        int pHubs = inputs.getnHubs();
        int[] hubs = new int[pHubs];
        LinkedList<Hubs> openHubs = new LinkedList<Hubs>();
        for (int i = 0; i < pHubs; i++) {
            int aux = rng.nextInt(inputs.getNumNodes());
            if (!contains(hubs, aux)) {
                hubs[i] = aux;
                inputs.getNodeList().get(aux).setHub(true);
                inputs.getNodeList().get(aux).setAssignedHub(inputs.getNodeList().get(aux));
            } else {
                i--;
            }
        }
        for (int i = 0; i < inputs.getNumNodes(); i++) {
            Nodes thisNodes = inputs.getNodeList().get(i);
            if (thisNodes.isHub()) {
                openHubs.add(new Hubs(thisNodes, inputs.getNodeList()));
            }
        }
        return openHubs;
    }

    public static boolean contains(final int[] array, final int key) {
        return ArrayUtils.contains(array, key);
    }

    public static void resetNodes(Inputs inputs) {
        for (Nodes node : inputs.getNodeList()) {
            node.setAssignedHub(null);
            node.setHub(false);
        }
    }

    public static LinkedList<Nodes> resetPerturbatedNodes(LinkedList<Nodes> allNodes, LinkedList<Hubs> hubs) {

        for (int ii = 0; ii < allNodes.size(); ii++) {
            boolean isTrue = false;

            for (int i = 0; i < hubs.size(); i++) {
                if (allNodes.get(ii).getId() == hubs.get(i).getNode().getId()) {
                    isTrue = true;
                }
                //if(allNodes.get(ii).getId() != hubs.get(i).getNode().getId()){
                //  allNodes.get(ii).setAssignedHub(null);
                //  allNodes.get(ii).setHub(false);
                //}
            }
            if (isTrue != true) {
                allNodes.get(ii).setAssignedHub(null);
                allNodes.get(ii).setHub(false);
                isTrue = false;
            }

        }
        return allNodes;
    }

//        public static void resetNewPerturbatedNodes(LinkedList<Nodes> allNodes, LinkedList<Hubs> hubs) {
//        for (Nodes node : allNodes) {
//            for (int i = 0; i < hubs.size(); i++)    {
//                if(node.getId() != hubs.get(i).getNode().getId()){
//                    node.setAssignedHub(null);
//                    node.setHub(false);
//                }else{
//                    node.setAssignedHub(node);
//                    node.setHub(true);
//                }
//            }
//        }
//    }
    public static LinkedList<Hubs> perturbate(Solution cSol, Random rng, Test aTest) {
        //Solution cSol = new Solution();

        //baseSol.copyInto(cSol, aTest);
        //newSol.copyInto(baseSol, aTest);
        //LinkedList<Hubs> newHubs = new LinkedList<Hubs>();
        LinkedList<Hubs> newHubs = cSol.getHubs();
        LinkedList<Nodes> nodes = cSol.getNodes();

        int p = getHubsToReset(cSol, aTest.getpRemove());
        //newHubs = baseSol.getHubs();
        for (int i = 0; i < p; i++) {
            //nodes.add(newHubs.getLast().getNode());
            //newHubs.remove(i);   
            newHubs.remove(newHubs.get(i));
        }
        nodes = resetPerturbatedNodes(nodes, newHubs);
        //nodes.add(newHubs.getLast().getNode());
        //newSol.getHubs().remove(p);
        //newHubs = genRandSol(inputs, rng, aTest);
        newHubs = hubsRandomSelect(nodes, newHubs, p, rng);
        //assignNodesToHub(inputs, rng, newHubs, aTest);
        //newSol.getNodes().;

        return newHubs;

    }

    public static int getRandomPosition(final double beta, final int n, final Random rng) {
        return ((int) (Math.log(rng.nextDouble()) / Math.log(1 - beta))) % n;
    }

    public static int getHubsToReset(Solution sol, double p) {
        int maxFacilitiesToEliminate = (int) Math.ceil(sol.getHubs().size() * p);
        return maxFacilitiesToEliminate;
    }

    public static LinkedList<Hubs> hubsRandomSelect(LinkedList<Nodes> nodes, LinkedList<Hubs> openHubs, int p, Random rng) {
        //resetNodes(inputs);
        int pHubs = p;
        int[] hubsToSelect = new int[pHubs];
        //LinkedList<Hubs> openHubs = new LinkedList<Hubs>();
        for (int i = 0; i < pHubs; i++) {
            int aux = rng.nextInt(nodes.size());
            if (!contains(hubsToSelect, aux)) {
                for (int temp = 0; temp < openHubs.size(); temp++) {
                    if(i<0){
                        i = 0;
                    }
                    if (nodes.get(aux).getId() != openHubs.get(temp).getNode().getId()) {
                        hubsToSelect[i] = aux;
                        nodes.get(aux).setHub(true);
                        nodes.get(aux).setAssignedHub(nodes.get(aux));

                    } else {
                        i--;
                    }
                }
            } else {
                i--;
            }
        }
        int leftHubs = openHubs.size();
        for (int i = 0; i < nodes.size(); i++) {
            Nodes thisNodes = nodes.get(i);
            boolean isTrue = false;
            for (int j = 0; j < leftHubs; j++) {

                if (thisNodes.isHub() && openHubs.get(j).getNode().getId() == thisNodes.getId()) {
                    isTrue = true;
                    openHubs.set(j, new Hubs(openHubs.get(j).getNode(), nodes));
                }
            }

            if (thisNodes.isHub() && isTrue != true) {
                openHubs.add(new Hubs(thisNodes, nodes));
                isTrue = false;
                
            }
        }
        return openHubs;
    }

}
