package src.io;

import java.util.LinkedList;
import src.structures.Nodes;
import src.structures.Connections;

/**
 *
 * @author Stephanie Alvarez <salvarezferna@uoc.edu>/**
 *
 */
public class Inputs {

    LinkedList<Nodes> nodeList = new LinkedList<Nodes>(); // Nodes list
    LinkedList<Connections> connectionsList = new LinkedList<Connections>(); // Connections list (node to node)
    double[][] flowMatrix; // matrix with demands from node to node
    private float collectionCost; // cost from origin node to its assigned hub
    private float transferCost; // cost from origin hub to destination hub
    private float distributionCost; // cost from destination hub to destination node
    private int nHubs; // number of hubs

    public int getnHubs() {
        return nHubs;
    }

    public void setnHubs(int nHubs) {
        this.nHubs = nHubs;
    }

    public int getNumNodes() {
        return nodeList.size();
    }

    public LinkedList<Nodes> getNodeList() {
        return nodeList;
    }

    public double[][] getFlowMatrix() {
        return flowMatrix;
    }

    public void setNodeList(LinkedList<Nodes> nodeList) {
        this.nodeList = nodeList;
    }

    public void addNode(Nodes node) {
        nodeList.add(node);
    }

    public void setFlowMatrix(double[][] demandMatrix) {
        this.flowMatrix = demandMatrix;
    }

    public LinkedList<Connections> getConnectionsList() {
        return connectionsList;
    }

    public void setConnectionsList(LinkedList<Connections> connectionsList) {
        this.connectionsList = connectionsList;
    }

    public void addConnections(Connections conn) {
        connectionsList.add(conn);
    }

    public double getFlowBetweenTwoNodes(Nodes origin, Nodes destination) {
        return flowMatrix[origin.getId()][destination.getId()];
    }

    public float getCollectionCost() {
        return collectionCost;
    }

    public void setCollectionCost(float collectionCost) {
        this.collectionCost = collectionCost;
    }

    public float getDistributionCost() {
        return distributionCost;
    }

    public void setDistributionCost(float distributionCost) {
        this.distributionCost = distributionCost;
    }

    public float getTransferCost() {
        return transferCost;
    }

    public void setTransferCost(float transferCost) {
        this.transferCost = transferCost;
    }

}
