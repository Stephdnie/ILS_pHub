package src.structures;

import java.util.BitSet;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import src.io.Inputs;

/**
 *
 * @author Stephanie Alvarez <salvarezferna@uoc.edu>
 *
 */
public class Nodes {
	/* INSTANCE FIELDS & CONSTRUCTOR */
	private int id; // facilityId
	private double coordinateX;
	private double coordinateY;
	private boolean isHub = false;
	private Nodes assignedHub = null;
	
	
	public Nodes(){}
	public Nodes(int ID, double X, double Y){
		id = ID;
		coordinateX = X;
		coordinateY = Y;	
	}

	
	public Nodes getAssignedHub() {
		return assignedHub;
	}

	public void setAssignedHub(Nodes assignedHub) {
		this.assignedHub = assignedHub;
	}


	public int getId() {
		return id;
	}

	public double getCoordinateX() {
		return coordinateX;
	}

	public double getCoordinateY() {
		return coordinateY;
	}

	public boolean isHub() {
		return isHub;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCoordinateX(double x) {
		this.coordinateX = x;
	}

	public void setCoordinateY(double y) {
		this.coordinateY = y;
	}

	public void setHub(boolean isHub) {
		this.isHub = isHub;
	}
	
	
}