package src.structures;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import src.io.Inputs;
import src.io.Test;
import umontreal.iro.lecuyer.rng.RandomStream;
import src.structures.Connections;
import src.structures.Nodes;

/**
 * @author Angel A. Juan date: 140926
 */
public final class Solution {

    private double totalCosts = 0;
    private double stochCosts = 0;
    private double stanDev = 0;
    private LinkedList<Nodes> nodes = new LinkedList<Nodes>();
    private LinkedList<Hubs> hubs = new LinkedList<Hubs>();

    public Solution() {
    }

    public Solution(LinkedList<Nodes> nodeList, LinkedList<Hubs> hubList) {
        nodes = new LinkedList<Nodes>(nodeList);
        hubs = new LinkedList<Hubs>(hubList);
    }

    public double getCosts() {
        return totalCosts;
    }

    public double getStochCosts() {
        return stochCosts;
    }

    public double getStanDev() {
        return stanDev;
    }

    public LinkedList<Nodes> getNodes() {
        return nodes;
    }

    public LinkedList<Hubs> getHubs() {
        return hubs;
    }

    public void setCosts(double costs) {
        this.totalCosts = costs;
    }

    public void setStochCosts(double stochCosts) {
        this.stochCosts = stochCosts;
    }

    public void setStanDev(double stanDev) {
        this.stanDev = stanDev;
    }

    public void setNodes(LinkedList<Nodes> nodes) {
        this.nodes = nodes;
    }

    public void setHubs(LinkedList<Hubs> hubs) {
        this.hubs = hubs;
    }

    public void calcCosts(Inputs in, Test aTest) {
        double transportationCosts = 0;
        for (int i = 0; i < nodes.size(); i++) {
            Nodes thisN = nodes.get(i);
            for (int j = 0; j < in.getNumNodes(); j++) {
                transportationCosts += calcTransportationCosts(thisN, in.getNodeList().get(j), aTest, in);
            } 
        } 
        totalCosts = transportationCosts;
    }
    
        public void newCalcCosts(Solution sol, Test aTest, Inputs inputs) {
        double transportationCosts = 0;
        for (int i = 0; i < nodes.size(); i++) {
            Nodes thisN = nodes.get(i);
            for (int j = 0; j < sol.getNodes().size(); j++) {
                transportationCosts += calcTransportationCosts(thisN, sol.getNodes().get(j), aTest, inputs);
            } 
        } 
        totalCosts = transportationCosts;
    }

    public double calcTransportationCosts(Nodes origin, Nodes destination, Test aTest, Inputs inputs) {
        double transportationCosts = 0;
        if (origin.getAssignedHub().getId() == destination.getAssignedHub().getId()) {
            Connections originToHub = new Connections(origin, origin.getAssignedHub());
            transportationCosts += originToHub.getCosts() * inputs.getCollectionCost();

            Connections hubToDestination = new Connections(destination.getAssignedHub(), destination);
            transportationCosts += hubToDestination.getCosts() * inputs.getDistributionCost();
        } else {

            Connections originToHub = new Connections(origin, origin.getAssignedHub());
            transportationCosts += originToHub.getCosts() * inputs.getCollectionCost();
            
            Connections hubToHub = new Connections(origin.getAssignedHub(), destination.getAssignedHub());
            transportationCosts += hubToHub.getCosts() * inputs.getTransferCost();
            
            Connections hubToDestination = new Connections(destination.getAssignedHub(), destination);
            transportationCosts += hubToDestination.getCosts() * inputs.getDistributionCost();
        }
        transportationCosts = transportationCosts * inputs.getFlowMatrix()[origin.getId()][destination.getId()];

        return transportationCosts;
    }

    public void localSearch(Inputs inputs) {
        for (Hubs hub : hubs) {
            LinkedList<Nodes> nodesAssignedToHub = new LinkedList<Nodes>();
            LinkedList<Connections> nodesToHub = new LinkedList<Connections>();
            for (Nodes node : inputs.getNodeList()) {
                if (node.getAssignedHub().getId() == hub.getNode().getId()) {
                    nodesAssignedToHub.add(node);
                }
            }
            for (Nodes node : nodesAssignedToHub) {
                nodesToHub.add(new Connections(node, hub.getNode()));
            }
            Collections.sort(nodesToHub);
            removeNodeFromHub(nodesToHub);
        }
    } 

    public void newLocalSearch(Solution sol) {
        for (Hubs hub : hubs) {
            LinkedList<Nodes> nodesAssignedToHub = new LinkedList<Nodes>();
            LinkedList<Connections> nodesToHub = new LinkedList<Connections>();
            for (Nodes node : sol.getNodes()) {
                if (node.getAssignedHub().getId() == hub.getNode().getId()) {
                    nodesAssignedToHub.add(node);
                }
            }
            for (Nodes node : nodesAssignedToHub) {
                nodesToHub.add(new Connections(node, hub.getNode()));
            }
            Collections.sort(nodesToHub);
            removeNodeFromHub(nodesToHub);
        }
    }     
    
    
    public void removeNodeFromHub(LinkedList<Connections> list) {
        Nodes nodeToRemove = list.getLast().getOrigin();
        double currentCost = list.getLast().getCosts();
        for (Hubs hub : hubs) {
            if (hub.getNode().getId() != list.getLast().getDestination().getId()) {
                Connections link = new Connections(nodeToRemove, hub.getNode());
                double aux = link.getCosts();
                if (currentCost > aux) {
                    nodeToRemove.setAssignedHub(hub.getNode());
                    break;
                }
            }
        }
    }
    
    public Solution copyInto(Solution sol, Test aTest){
        for(int i = 0; i < sol.getHubs().size(); i++){
            sol.getHubs().get(i).setHubToNodes(sol.getHubs().get(i).getHubToNodes());
        }
        //sol.setHubs(hubs);
        //sol.setNodes(nodes);
        //sol.setHubs(sol.getHubs());
        sol.setNodes(sol.getNodes());
        sol.setCosts(sol.getCosts());
        

        
     return sol;
    }
   
}
