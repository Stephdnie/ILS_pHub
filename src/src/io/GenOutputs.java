/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.io;

/**
 *
 * @author Stephyaf
 */
public class GenOutputs {
	private String instanceN;
	private double totalCosts;
	
	public GenOutputs(String name, double cos){
		instanceN = name;
		totalCosts = cos;
	}

	public String getInstanceN() {
		return instanceN;
	}

	public double getCosts() {
		return totalCosts;
	}

	public void setInstanceN(String instanceN) {
		this.instanceN = instanceN;
	}

	public void setCosts(double costs) {
		this.totalCosts = costs;
	}
	
	
}
