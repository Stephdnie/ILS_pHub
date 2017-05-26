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
public class Connections implements Comparable<Connections>  {
	Nodes origin;
	Nodes destination;
	double costs;
	
	public Connections(Nodes o,Nodes e){
		origin = o;
		destination = e;
		costs = costsConnections();
	}	
	
	public Nodes getOrigin() {
		return origin;
	}

	public Nodes getDestination() {
		return destination;
	}

	public double getCosts() {
		return costs;
	}


	public void setOrigin(Nodes origin) {
		this.origin = origin;
	}

	public void setDestination(Nodes destination) {
		this.destination = destination;
	}


	public double costsConnections(){
		double coorX1 = this.getOrigin().getCoordinateX();
        double coorY1 = this.getOrigin().getCoordinateY();
        double coorX2 = this.getDestination().getCoordinateX();
        double coorY2 = this.getDestination().getCoordinateY();
        double distance = Math.sqrt((coorX2 - coorX1) * (coorX2 - coorX1) + (coorY2 - coorY1) * (coorY2 - coorY1));
        return distance/1000;
	}
	
    public int compareTo(Connections otherLink) 
    {   Connections other = otherLink;
        double s1 = this.getCosts();
        double s2 = other.getCosts();
        if(s1 == s2)
        	return 0;
        if (s1 < s2)
            return -1;
        else 
            return 1;
    }
}

