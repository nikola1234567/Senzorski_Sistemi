package lab3;

import java.util.LinkedList;
import java.util.Random;

public class TrilaterationStarter {
    int L=200;
    int N=100;
    int counter=0;

    public int generatId(){
        return this.counter++;
    }

    public double getFromPercent(int x, int y){
        return (x/100.0f)*y;
    }

    public Node[] createNodes(int fa, int err, int R){
        Node[] nodes=new Node[N];
        Random random = new Random();
        LinkedList<Point> points=new LinkedList<>();
        int pom = this.N;
        while (pom!=0){
            int x = random.nextInt(this.L-1);
            int y = random.nextInt(this.L-1);
            Point point=new Point(x,y);
            if(!points.contains(point)){
                points.add(point);
                pom--;
            }
        }

        LinkedList<Integer> an=new LinkedList<>();
        int anchors=(int)this.getFromPercent(fa, this.N);
        while (anchors!=0){
            int id = random.nextInt(this.N-1);
            if (!an.contains(id)){
                an.add(id);
                anchors--;
            }
        }
        this.counter=0;
        int index=0;
        for (Point point:points){
            int id=this.generatId();
            nodes[index++]=new Node(id,R,err,point,
                    (an.contains(id) ? true : false));
        }

        for (Node node:nodes){
            node.hearNodesInRange(nodes);
        }
        return nodes;
    }

    public int ALE(Node[] nodes, int R){
        double zbir = 0;
        int brojac=0;
        for (Node node:nodes){
            if(!node.was_anchor&&node.localized){
                zbir+=node.point.eucledianDistance(node.point_prim);
            }
            if (!node.was_anchor){
                brojac++;
            }
        }
        zbir/=brojac;
        return (int)(zbir*(100.0f/R));
    }

    public NodesLocalized localizeNetOfNodes(Node[] nodes,String algorithm,String type){
        int br=0;
        for (Node node:nodes){
        	boolean x;
        	if(algorithm.equals("non-iterative"))
        		x=node.localizeNonIterativeApproach();
            ///System.out.println(node.point+"------>"+node.point_prim);
        	else {
        		x = node.localizeIterativeApproach(type);
        	}

            if(x){
                br++;
            }
        }

        // System.out.println(br);
        return new NodesLocalized((double)(((double)br/this.N)*100.0f),nodes);
    }

    public void testCreaatingNodes(){
        Node[] nodes=this.createNodes(20,10,35);
        for (Node node:nodes){
            node.hearNodesInRange(nodes);
        }

        for (Node node: nodes){
           // node.localize();
        }

        for (Node node: nodes){
            node.getMostRelevantAnchorSet();
        }

    }

    public int getN() {
        return N;
    }
    
    ///Prv grafik prv tip non-iterative
    public void generateGraphic__X_R__Y_Loc__Change_fa__Fixed_err(){
        int err=10;
        int fa[] ={20,25,30,35};
        int R[] = {30,35,40,45,50,55,60};
        LinkedList<Graphic> graphics=new LinkedList<>();

        for (int ff: fa){
            Graphic graphic = new Graphic();
            graphic.setName("Graphic for Fa="+ ff+"%");
            for (int rr: R){
                int zbir=0;
                for (int i=0;i<15;i++){
                    Node[] nodes=this.createNodes(ff,err,rr);
                    NodesLocalized nodesLocalized=this.localizeNetOfNodes(nodes,"non-iterative","");
                   // System.out.println(nodesLocalized.localized);
                    zbir+=nodesLocalized.localized;
                }
                //System.out.println(zbir);
                zbir/=15.0f;
                //System.out.println(zbir);
                Point point=new Point(rr, zbir);
                graphic.addPoint(point);
            }
            graphics.add(graphic);
        }

        System.out.println("\ngenerateGraphic__X_R__Y_Loc__Change_fa__Fixed_err\n");
        for (Graphic graphic: graphics){
            System.out.println(graphic.toString());
        }
    }
    
    //Vtor grafik prv tip non-iterative
    public void generateGraphic__X_fa__Y_Loc__Change_R__Fixed_err(){
        int err = 10;
        int[] R={30,35,40,55};
        int[] fa={20,25,30,35,40,45,50};
        LinkedList<Graphic> graphics=new LinkedList<>();

        for (int rr: R){
            Graphic graphic=new Graphic();
            graphic.setName("Graphic for R="+rr+"m");
            for (int ff: fa){
                int zbir=0;
                for (int i = 0; i< 15;i++){
                    Node[] nodes=this.createNodes(ff,err,rr);
                    NodesLocalized nodesLocalized=this.localizeNetOfNodes(nodes,"non-iterative","");
                    zbir+=nodesLocalized.localized;
                }
                zbir/=15.0f;
                graphic.addPoint(new Point(ff, zbir));
            }
            graphics.add(graphic);
        }

        System.out.println("\ngenerateGraphic__X_fa__Y_Loc__Change_R__Fixed_err\n");

        for (Graphic graphic: graphics){
            System.out.println(graphic.toString());
        }
    }
    
    

    //analiza na hevristika 1
    public void test_generateGraphic__X_R__Y_Loc__Change_fa__Fixed_err_Najbliski(){
        int err=10;
        int fa[] ={20,25,30,35};
        int R[] = {45,50,55,60};
        LinkedList<Graphic> graphics=new LinkedList<>();

        for (int ff: fa){
            Graphic graphic = new Graphic();
            graphic.setName("Graphic for Fa="+ ff+"%");
            for (int rr: R){
                int zbir=0;
                for (int i=0;i<15;i++){
                    Node[] nodes=this.createNodes(ff,err,rr);
                    NodesLocalized nodesLocalized=this.localizeNetOfNodes(nodes,"iterative","Najbliski");
                    // System.out.println(nodesLocalized.localized);
                    zbir+=nodesLocalized.localized;
                }
                //System.out.println(zbir);
                zbir/=15.0f;
                //System.out.println(zbir);
                Point point=new Point(rr, zbir);
                graphic.addPoint(point);
            }
            graphics.add(graphic);
        }

        System.out.println("\ntest_generateGraphic__X_R__Y_Loc__Change_fa__Fixed_err_Najbliski\n");
        for (Graphic graphic: graphics){
            System.out.println(graphic.toString());
        }
    }
    
    //analiza na hevristika 2
    public void test_generateGraphic__X_R__Y_Loc__Change_fa__Fixed_err_Najrelevantni(){
        int err=10;
        int fa[] ={20,25,30,35};
        int R[] = {45,50,55,60};
        LinkedList<Graphic> graphics=new LinkedList<>();

        for (int ff: fa){
            Graphic graphic = new Graphic();
            graphic.setName("Graphic for Fa="+ ff+"%");
            for (int rr: R){
                int zbir=0;
                for (int i=0;i<15;i++){
                    Node[] nodes=this.createNodes(ff,err,rr);
                    NodesLocalized nodesLocalized=this.localizeNetOfNodes(nodes,"iterative","most-relevant");
                    // System.out.println(nodesLocalized.localized);
                    zbir+=nodesLocalized.localized;
                }
                //System.out.println(zbir);
                zbir/=15.0f;
                //System.out.println(zbir);
                Point point=new Point(rr, zbir);
                graphic.addPoint(point);
            }
            graphics.add(graphic);
        }

        System.out.println("\ntest_generateGraphic__X_R__Y_Loc__Change_fa__Fixed_err_Najrelevantni\n");
        for (Graphic graphic: graphics){
            System.out.println(graphic.toString());
        }
    }


    public void generateGraphic__X_R__Y_ALE__Change_err__Fixed_fa(String algorithm){
        int fa=20;
        int[] err={5,15,25,30};
        int[] R={30,35,40,45,50,55,60};
        LinkedList<Graphic> graphics=new LinkedList<>();

        for (int ee : err){
            Graphic graphic=new Graphic();
            graphic.setName("Graphic for err="+ee+"%");
            for (int rr : R){
                int zbir=0;
                for (int i = 0; i< 15;i++){
                    Node[] nodes=this.createNodes(fa,ee,rr);
                    NodesLocalized nodesLocalized=this.localizeNetOfNodes(nodes,algorithm,"Najbliski");
                    zbir+=this.ALE(nodesLocalized.nodes,rr);
                }
                zbir/=15.0f;
                graphic.addPoint(new Point(rr, zbir));
            }
            graphics.add(graphic);
        }

        System.out.println("\ngenerateGraphic__X_R__Y_ALE__Change_err__Fixed_fa\n");

        for (Graphic graphic: graphics){
            System.out.println(graphic.toString());
        }

    }

    public void generateGraphic__X_err__Y_ALE__Change_R__Fixed_fa(String algorithm){
        int fa=20;
        int R[] = {30,35,40,45};
        int err[] = {10,15,20,25,30,35,40};
        LinkedList<Graphic> graphics=new LinkedList<>();

        for (int rr: R){
            Graphic graphic=new Graphic();
            graphic.setName("Graphic for R="+rr+"m");
            for(int ee : err){
                int zbir=0;
                for (int i = 0; i< 15;i++){
                    Node[] nodes=this.createNodes(fa,ee,rr);
                    NodesLocalized nodesLocalized=this.localizeNetOfNodes(nodes,algorithm,"Najbliski");
                    zbir+=this.ALE(nodesLocalized.nodes,rr);
                }
                zbir/=15.0f;
                graphic.addPoint(new Point(ee, zbir));
            }
            graphics.add(graphic);
        }

        System.out.println("\ngenerateGraphic__X_err__Y_ALE__Change_R__Fixed_fa\n");
        for (Graphic graphic: graphics){
            System.out.println(graphic.toString());
        }

    }

    public void generateGraphic__X_fa__Y_ALE__Change_R__Fixed_err(String algorithm){
        int err=10;
        int R[] ={30,35,40,45};
        int fa[]={20,25,30,35,40,45,50};
        LinkedList<Graphic> graphics=new LinkedList<>();

        for (int rr: R){
            Graphic graphic=new Graphic();
            graphic.setName("Graphic for R="+rr+"m");
            for (int ff: fa){
                int zbir=0;
                for (int i = 0; i< 15;i++){
                    Node[] nodes=this.createNodes(ff,err,rr);
                    NodesLocalized nodesLocalized=this.localizeNetOfNodes(nodes,algorithm,"Najbliski");
                    zbir+=this.ALE(nodesLocalized.nodes,rr);
                }
                zbir/=15.0f;
                graphic.addPoint(new Point(ff, zbir));
            }
            graphics.add(graphic);
        }

        System.out.println("\ngenerateGraphic__X_fa__Y_ALE__Change_R__Fixed_err\n");
        for (Graphic graphic: graphics){
            System.out.println(graphic.toString());
        }

    }


}