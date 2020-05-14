package lab3;

public class AnchorSet {
    boolean found=false;
    Node n1,n2,n3;
    int br=0;

    public AnchorSet() {}
    public AnchorSet(boolean found, Node n1, Node n2, Node n3) {
        this.found = found;
        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public Node getN1() {
        return n1;
    }

    public void setN1(Node n1) {
        this.n1 = n1;
    }

    public Node getN2() {
        return n2;
    }

    public void setN2(Node n2) {
        this.n2 = n2;
    }

    public Node getN3() {
        return n3;
    }

    public void setN3(Node n3) {
        this.n3 = n3;
    }

    public void insertNode(Node node){
        if(!isFound()){
            if (br==0){
                this.n1=node;
                br++;
            }else if (br==1){
                this.n2=node;
                br++;
            }else if(br==2){
                this.n3=node;
                br++;
            }

            if(br==3){
                found=true;
            }

        }
    }

    @Override
    public String
    toString() {
        return "AnchorSet [" +
                "found=" + found +
                ", n1=" + n1 +
                ", n2=" + n2 +
                ", n3=" + n3 +
                ']';
    }
}