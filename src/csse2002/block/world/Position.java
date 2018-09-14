package csse2002.block.world;

public class Position {

    private int x, y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;

    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }


    @Override
    public boolean equals(Object obj) {
        Position otherPosition = (Position)obj;

        if(getX()==otherPosition.getX() && getY() == otherPosition.getY()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (getX()*10 + getY());
    }

    public int compareTo(Position other){
        if(getX()<other.getX()){
            return -1;

        }else if(getX()>other.getX()){
            return 1;

        }else if(getY()<other.getX()){
            return -1;

        }else if(getY() > other.getY()){
            return 1;

        }else{
            return 0;
        }
    }

    public String toString(){
        return "("+getX()+", "+getY()+")";
    }
}
