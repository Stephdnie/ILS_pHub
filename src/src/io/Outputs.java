/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.io;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import src.structures.Solution;

/**
 *
 * @author Stephyaf
 */
public class Outputs {
	private Solution bestSol;

	public Solution getBestSol() {
		return bestSol;
	}

	public void setBestSol(Solution bestSol) {
		this.bestSol = bestSol;
	}
	
	public static void printGlobalOutput(LinkedList<GenOutputs> list) {
		try {
			PrintWriter out = new PrintWriter("GlobalOutput.txt");
			out.printf("%s \t %s \n",  "Instance", "Costs");
			for(int i = 0; i < list.size();i++){
				out.printf("%s \t %.2f \n",  list.get(i).getInstanceN(), list.get(i).getCosts());
			}
			out.close();
		} catch (IOException exception) {
			System.out.println("Error processing output file: " + exception);
		}
	}
	
}

