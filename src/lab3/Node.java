package lab3;

import javax.swing.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedList;

public class Node {
    int id;
    int R;
    int err;
    boolean anchor,was_anchor;
    int weight;
    boolean localized;
    Point point,point_prim;
    Hashtable<Integer, NodeNeighbor> nodeNeighbors=new Hashtable<>();
    Trilateration trilateration=new Trilateration();

    public Node()  {}
    public Node(int id, int R, int err, Point point, boolean anchor)  {
        this.id=id;
        this.R=R;
        this.err=err;
        this.point=point;
        this.anchor=anchor;
        this.was_anchor=this.anchor;
        this.weight=0;
        this.localized = this.anchor;
    }

    public double getFromPercent(int x, double y){
        return (x/100.0f)*y;
    }

    public void hearNodesInRange(Node[] nodes){
        for (Node node: nodes){
            if (!node.equals(this)){
                double distance = this.point.eucledianDistance(node.point);
                if(distance<this.R){
                    double noise=this.getFromPercent(this.err, distance);
                    double min=distance-noise;
                    double max=distance+noise;
                    double distanceWithNoise=min + Math.random()*(max-min);
                    this.nodeNeighbors.put(node.id,new NodeNeighbor(distanceWithNoise,node));
                }
            }
        }
    }

    public void printNodesInRange(){
        System.out.println("NODEES IN RANGEE");
        LinkedList<NodeNeighbor> linkedList=new LinkedList<>(this.nodeNeighbors.values());
        for (NodeNeighbor nodeNeighbor: linkedList){
            System.out.println(this.toString()+"---- "+nodeNeighbor.distance+" ---->"+ (nodeNeighbor.node.toString()));
        }
    }

    public AnchorSet getClosestAnchorSet(){
        LinkedList<NodeNeighbor> nodeNeighbors=new LinkedList<>(this.nodeNeighbors.values());
        Collections.sort(nodeNeighbors, new Comparator<NodeNeighbor>() {
            @Override
            public int compare(NodeNeighbor o1, NodeNeighbor o2) {
                return Double.compare(o1.distance,o2.distance);
            }
        });

        AnchorSet anchorSet = new AnchorSet();
        for(NodeNeighbor nodeNeighbor: nodeNeighbors){
            // System.out.println(nodeNeighbor.node+"-----------> "+nodeNeighbor.distance);
            if (nodeNeighbor.node.anchor){
                anchorSet.insertNode(nodeNeighbor.node);
            }
        }
        // System.out.println(anchorSet);
        return anchorSet;
    }

    public AnchorSet getMostRelevantAnchorSet(){
        AnchorSet anchorSet = new AnchorSet();
        LinkedList<NodeNeighbor> nodeNeighbors=new LinkedList<>(this.nodeNeighbors.values());
        Collections.sort(nodeNeighbors, new Comparator<NodeNeighbor>() {
            @Override
            public int compare(NodeNeighbor o1, NodeNeighbor o2) {
                return Integer.compare(o1.node.weight,o2.node.weight);
            }
        });

        for (NodeNeighbor nodeNeighbor: nodeNeighbors){
           // System.out.println(nodeNeighbor.node);
            if (nodeNeighbor.node.anchor){
                anchorSet.insertNode(nodeNeighbor.node);
            }
        }
        //System.out.println(anchorSet);

        return anchorSet;
    }

    public boolean localizeIterativeApproach(String type){
        AnchorSet anchorSet=null;
        if(type.equals("Najbliski"))
            anchorSet=this.getClosestAnchorSet();
        else
            anchorSet=this.getMostRelevantAnchorSet();
        if (anchorSet.isFound()){
            point_prim=this.trilateration.Trilateration(this.id,anchorSet.getN1(),anchorSet.getN2(),anchorSet.getN3());
            this.localized=true;
            this.anchor=true;
            this.weight = 1 + anchorSet.n1.weight+anchorSet.n2.weight+anchorSet.n3.weight;
            if (point_prim.x<0||point_prim.x>=200||point_prim.y<0||point_prim.y>=200){
                point_prim=null;
                this.localized=false;
                this.anchor=false;
                this.weight=0;
                return false;
            }
            return true;
        }else {
            this.localized=false;
            return false;
        }
    }
    
    public boolean localizeNonIterativeApproach(){
        AnchorSet anchorSet=this.getClosestAnchorSet();
        if (anchorSet.isFound()){
            point_prim=this.trilateration.Trilateration(this.id,anchorSet.getN1(),anchorSet.getN2(),anchorSet.getN3());
            this.localized=true;
            if (point_prim.x<0||point_prim.x>=200||point_prim.y<0||point_prim.y>=200){
                point_prim=null;
                this.localized=false;
                return false;
            }
            return true;
        }else {
            this.localized=false;
            return false;
        }
    }


    @Override
    public String toString() {
        return "Node[" +
                "id=" + id +
                ", R=" + R +
                ", err=" + err +
                ", anchor=" + anchor +
                ", localized=" + localized +
                ", point=" + point +
                ", weight=" + weight +
                ']';
    }
}