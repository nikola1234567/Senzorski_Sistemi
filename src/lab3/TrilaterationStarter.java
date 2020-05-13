package lab3;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class TrilaterationStarter {
	int L=100;
	int R;
	int N=100;
	int fa;
	int err;
	
	LinkedList<SensorNode> nodes=new LinkedList<SensorNode>();
	
	public TrilaterationStarter() {}
	
	public TrilaterationStarter(int l, int r, int n, int fa, int err) {
		super();
		L = l;
		R = r;
		N = n;
		this.fa = fa;
		this.err = err;
	}

	long counter=0;
	
	private long generateId() {
		return this.counter++;
	}
	
	private LinkedList<PointInSpace> generatePoints() {
		LinkedList<PointInSpace> listOfCoordinates = new LinkedList<PointInSpace>();
		Random random=new Random();
		int generated=0;
		while(generated<N) {
			int x = random.nextInt(this.L - 1);
			int y = random.nextInt(this.L - 1);
			PointInSpace point = new PointInSpace(x,y);
			if(!listOfCoordinates.contains(point)) {
				listOfCoordinates.add(point);
				generated++;
			}
		}
		
		
		return listOfCoordinates;
	}
	
	private int getNumberOfAnchors() {
		return (int)(this.N*(this.fa/100.0f));
	}
	
	private double inProcent(int x,int y) {
		return (double)x/(double)y*100;
	}
	
	private void createSensorNodes() {
		LinkedList<PointInSpace> points=this.generatePoints();
		Iterator<PointInSpace> it = points.iterator();
		int brojac=0;
		int anchors=this.getNumberOfAnchors();
		while(it.hasNext()) {
			this.nodes.add(new SensorNode(this.generateId(),
					(brojac >= N-1-anchors) ? true : false,
					 this.R, 
					 it.next()));
			brojac++;
		}
		
	}
	
	public double localizeNodes() {
		int localized=0,non_localized=0;
		Iterator<SensorNode> it=this.nodes.iterator();
		while(it.hasNext()) {
			SensorNode sn=it.next();
			sn.generalize(nodes);
			boolean successfullLocalization=sn.localizeNonIterativeAlgorithm();
			if(successfullLocalization) {
				localized++;
			}else {
				non_localized++;
			}
		}
		
		//System.out.println("Localized: "+this.inProcent(localized, N)+"% Non-Localized: "+this.inProcent(non_localized, N)+"%");
		//double[] res={this.inProcent(localized, N), this.inProcent(non_localized, N)};
		return this.inProcent(localized, N);
	}
	
	public void testGraph__X_R__Y_Loc__Change_Fa(){
		LinkedList<Graphic> graphics=new LinkedList<Graphic>();
		int[] fa_s={15, 20, 25, 30};
		int[] R_s= {30,35,40,45,50,55,60};

		for(int ff: fa_s) {
			this.fa=ff;
			Graphic graphic=new Graphic();
			for(int rr : R_s) {
				System.out.println(rr);
				int zbir=0;
				for(int i=0;i<10;i++) {
					this.createSensorNodes();
					zbir+=this.localizeNodes();
				}
				zbir/=15.0f;
				graphic.addPoint(new PointInSpace(rr,zbir));
			}
			graphic.setName("Graphic for fa="+ff);
			graphics.add(graphic);
		}
		
		System.out.println("testGraph__X_R__Y_Loc__Change_Fa\n");
		Iterator<Graphic> it = graphics.iterator();
		while(it.hasNext()) {
			System.out.println(it.next().toString());
		}
		
	}
	
	public void testGraph__X_Fa__Y_Loc__Change_R() {
		LinkedList<Graphic> graphics=new LinkedList<Graphic>();
		int[] fa_s={15, 20, 25, 30};
		int[] R_s= {30,35,40,45,50,55,60};

		for(int rr: R_s) {
			Graphic graphic=new Graphic();
			for(int ff : fa_s) {
				this.fa=ff;
				int zbir=0;
				for(int i=0;i<10;i++) {
					this.createSensorNodes();
					zbir+=this.localizeNodes();
				}
				zbir/=15.0f;
				graphic.addPoint(new PointInSpace(rr,zbir));
			}
			graphics.add(graphic);
		}
		
		System.out.println("testGraph__X_Fa__Y_Loc__Change_R\n");
		Iterator<Graphic> it = graphics.iterator();
		while(it.hasNext()) {
			System.out.println(it.next().toString());
		}
	}

}
