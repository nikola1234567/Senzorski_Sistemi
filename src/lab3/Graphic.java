package lab3;

import java.util.Iterator;
import java.util.LinkedList;

public class Graphic {
	String name;
	LinkedList<PointInSpace> graph_points=new LinkedList<PointInSpace>();
	
	public Graphic() {
		super();
	}
	

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public void addPoint(PointInSpace point) {
		this.graph_points.add(point);
	}

	public LinkedList<PointInSpace> getGraph_points() {
		return graph_points;
	}

	public void setGraph_points(LinkedList<PointInSpace> graph_points) {
		this.graph_points = graph_points;
	}
	
	
	public String toString() {
		String ret="";
		ret+=(this.getName()+"\n");
		Iterator<PointInSpace> it=this.graph_points.iterator();
		while(it.hasNext()) {
			ret+=(it.next()+"\n");
		}
		return ret;
	}
	

}
