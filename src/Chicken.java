import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Chicken {
    public static final int SIZE = 30;
    private int x, y;
    private int direction = -1;



    public Chicken (int x, int y){
        this.x=x;
        this.y=y;
        this.direction = new Random().nextInt(8);
    }

    public void paint (Graphics graphics) {
        graphics.setColor(Color.YELLOW);
        graphics.fillOval(this.x, this.y, SIZE/2, SIZE);
    }

    public void move (int width, int height){
        int oldX = this.x;
        int oldY= this.y;

        switch (direction){
            case 0: this.y--; break; // North
            case 1: this.y--; this.x++; break; // NE
            case 2: this.x++; break; // East
            case 3: this.y++; this.x++; break; // SE
            case 4: this.y++; break; // South
            case 5: this.y++; this.x--; break; // SW
            case 6: this.x--; break; // West
            case 7: this.y--; this.x--; break; // NW
        }
        if (this.x < 0 || this.y < 0 || this.x > width - SIZE || this.y > height - SIZE) {
            this.x = oldX;
            this.y = oldY;
            this.direction = new Random().nextInt(8);
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }



    public void chickenStartMoving(int random){

        switch (random){
            case 0://North
                this.y--;
                break;
            case 1://North-East
                this.y--;
                this.x++;
                break;
            case 2://East
                this.x++;
                break;
            case 3://South-East
                this.y++;
                this.x++;
                break;
            case 4://South
                this.y++;
                break;
            case 5://South-West
                this.y++;
                this.x--;
                break;
            case 6://West
                this.x--;
                break;
            case 7://North-West
                this.y--;
                this.x--;
                break;
        }

    }

    public boolean checkIfCanMove (int width, int height){
        if(this.y > 0 && this.x > 0 && this.x < width && this.y < height){
            return true;
        }
        return false;
    }

//    public void chickenMove(int random, int width, int height){
//
//        int _x=this.x;
//        int _y = this.y;
//
//        switch (random){
//            case 0://North
//                if((_y-1)>0){
//                    this.y--;
//                }
//                break;
//            case 1://North-East
//                if((_y-1)>0 && (_x+1)<width ){
//                    this.y--;
//                    this.x++;
//                }
//                break;
//            case 2://East
//                if((_x+1)<width){
//                    this.x++;
//                }
//                break;
//            case 3://South-East
//                if((_y+1)<height && (_x+1)<width){
//                    this.y++;
//                    this.x++;
//                }
//                break;
//            case 4://South
//                if((_y+1)<height){
//                    this.y++;
//                }
//                break;
//            case 5://South-West
//                if((_y+1)<height && (_x-1)>0){
//                    this.y++;
//                    this.x--;
//                }
//                break;
//            case 6://West
//                if((_x-1)>0){
//                    this.x--;
//                }
//                break;
//            case 7://North-West
//                if((_y-1)>0 && (_x-1)>0){
//                    this.y--;
//                    this.x--;
//                }
//                break;
//        }
//    }
}
