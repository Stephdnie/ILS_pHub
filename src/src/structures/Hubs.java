/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.structures;

/**
 *
 * @author salvarezferna
 */
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class Hubs {
	Nodes nodes;
	LinkedList<Connections> hubToNodes = new LinkedList<Connections>();
	
	public Hubs(Nodes node, LinkedList<Nodes> list){
		nodes = node;
		hubToNodes = orderCosts(list);
	}
	
	public LinkedList<Connections> orderCosts(LinkedList<Nodes> list){
		for(int i = 0; i < list.size();i++){
			Nodes thisNode = list.get(i);
			if(!thisNode.isHub()){
				Connections e = new Connections(nodes, thisNode);
				hubToNodes.add(e);
			}
		}
		Collections.sort(hubToNodes);
		return hubToNodes;
	}
	
	public void removeNodeFromList(Nodes node){
		int nodeID = node.getId();
		int pos = 0;
		for(Connections link : hubToNodes){
			int temp = link.getDestination().getId();
			if(temp == nodeID){
				hubToNodes.remove(pos);
				break;
			}
			pos++;
		}
	}

	public Nodes getNode() {
		return nodes;
	}

	public LinkedList<Connections> getHubToNodes() {
		return hubToNodes;
	}

	public void setNode(Nodes n) {
		this.nodes = n;
	}

	public void setHubToNodes(LinkedList<Connections> hubToNodes) {
		this.hubToNodes = hubToNodes;
	}
	
	
	
}
