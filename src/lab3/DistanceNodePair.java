package lab3;

public class DistanceNodePair implements Comparable<DistanceNodePair>{
	int distanseToNode;
	SensorNode node;
	
	
	
	public DistanceNodePair(int distanseToNode, SensorNode node) {
		super();
		this.distanseToNode = distanseToNode;
		this.node = node;
	}


	@Override
	public int hashCode() {
		return (int)this.node.id;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DistanceNodePair other = (DistanceNodePair) obj;
		if (distanseToNode != other.distanseToNode)
			return false;
		if (node == null) {
			if (other.node != null)
				return false;
		} else if (!node.equals(other.node))
			return false;
		return true;
	}


	@Override
	public int compareTo(DistanceNodePair arg0) {
		// TODO Auto-generated method stub
		return Integer.compare(this.distanseToNode, arg0.distanseToNode);
	}
	

}
