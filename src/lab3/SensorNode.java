package lab3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SensorNode {
	long id;
	boolean anchor;
	int range;
	int error; // vo procenti
	Hashtable<Long,DistanceNodePair> nodesInRange;
	PointInSpace coordinates;
	PointInSpace coordinates_prim;
	
	
	
	public SensorNode(long id, boolean anchor, int range, PointInSpace coordinates,int error) {
		super();
		this.id = id;
		this.anchor = anchor;
		this.range = range;
		this.coordinates = coordinates;
		this.error=error;
	}
	
	public void generalize(LinkedList<SensorNode> nodess) {
		SensorNode []nodes=new SensorNode [nodess.size()];
		int index=0;
		Iterator<SensorNode> it = nodess.iterator();
		while(it.hasNext()) {
			nodes[index++]=it.next();
		}
		this.generateNodesInRange(nodes);
	}
	
	private int getPercentage(int x,int y) {
		return (int)(x*(y/100.0f));
	}

	public boolean localizeNonIterativeAlgorithm() {
		Collection<DistanceNodePair> nodes=this.nodesInRange.values();
		AnchorSet as=this.findThreeClosestAnchor(nodes);
		if(as.isFound()) {
			//localize
			//System.out.println("lokaliziraj "+as.toString());
			return true;
		}else {
			//nemoze da se lokalizira
			//System.out.println("ne moze da se lokalizira " + as.toString());
			return false;
		}
		
	}
	
	private AnchorSet findThreeClosestAnchor(Collection<DistanceNodePair> list){
		Collections.sort(new ArrayList(list));
		AnchorSet as=new AnchorSet();
		for(DistanceNodePair dnp : list) {
			if(dnp.node.anchor==true) {
				as.insertAncor(dnp.node);
			}
		}
		return as;
	}
	
	
	private void generateNodesInRange(SensorNode []nodes) {
		Random r = new Random(); 
		this.nodesInRange=new Hashtable<Long,DistanceNodePair>();
		for(SensorNode node : nodes) {
			int distance=this.coordinates.euclidianDistance(node.coordinates);
			int noise=this.getPercentage(distance, error);
			int distanceWithNoise=(int) (r.nextGaussian()*noise+distance);
			if(distanceWithNoise<=this.range) {
				this.nodesInRange.put(node.id, new DistanceNodePair(distanceWithNoise,node));
			}
		}
	}
	
	public void printNodesInRange() {
		Collection<DistanceNodePair> dnp=this.nodesInRange.values();
		Iterator<DistanceNodePair> it = dnp.iterator();
		while(it.hasNext()) {
			DistanceNodePair tmp = it.next();
			System.out.println(tmp.node.toString());
		}
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (anchor ? 1231 : 1237);
		result = prime * result + ((coordinates == null) ? 0 : coordinates.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((nodesInRange == null) ? 0 : nodesInRange.hashCode());
		result = prime * result + range;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SensorNode other = (SensorNode) obj;
		if (anchor != other.anchor)
			return false;
		if (coordinates == null) {
			if (other.coordinates != null)
				return false;
		} else if (!coordinates.equals(other.coordinates))
			return false;
		if (id != other.id)
			return false;
		if (nodesInRange == null) {
			if (other.nodesInRange != null)
				return false;
		} else if (!nodesInRange.equals(other.nodesInRange))
			return false;
		if (range != other.range)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SensorNode [id=" + id + ", anchor=" + anchor + ", range=" + range + ", coordinates=" + coordinates
				+ "]";
	}

	
	

}
