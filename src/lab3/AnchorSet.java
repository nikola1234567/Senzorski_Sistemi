package lab3;

public class AnchorSet {
	SensorNode node1;
	SensorNode node2;
	SensorNode node3;
	boolean found=false;
	int counter=1;
	
	public AnchorSet() {}
	
	public SensorNode getNode1() {
		return node1;
	}
	public void setNode1(SensorNode node1) {
		this.node1 = node1;
	}
	public SensorNode getNode2() {
		return node2;
	}
	public void setNode2(SensorNode node2) {
		this.node2 = node2;
	}
	public SensorNode getNode3() {
		return node3;
	}
	public void setNode3(SensorNode node3) {
		this.node3 = node3;
	}
	public boolean isFound() {
		return found;
	}
	public void setFound(boolean found) {
		this.found = found;
	}
	
	public void insertAncor(SensorNode sn) {
		if(!found) {
			if(counter==1) {
				this.setNode1(sn);
				counter++;
			}else if(counter==2) {
				this.setNode2(sn);
				counter++;
			}else if(counter==3) {
				this.setNode3(sn);
				counter++;
			}
			
			if(counter==4) {
				this.setFound(true);
			}
			
		}
	}

	@Override
	public String toString() {
		return "AnchorSet [node1=" + node1 + ", node2=" + node2 + ", node3=" + node3 + "]";
	}

	
	
	
	
	
	
}
