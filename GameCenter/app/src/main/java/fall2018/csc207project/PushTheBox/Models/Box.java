package fall2018.csc207project.PushTheBox.Models;

import java.io.Serializable;
import fall2018.csc207project.R;

/**
 * The box that is to be pushed by a person, to destination points.
 * Excluded from tests because it's a model class
 */
public class Box implements Serializable {

    /**
     * Position of the box.
     */
    private  int position;

    /**
     * The id of the image of when the box is not at a destination point.
     */
    private int normalImage = R.drawable.box;

    /**
     * The id of current image of the box.
     */
    private int currImage = normalImage;

    /**
     * A new box with initial position.
     *
     * @param position initial position
     */
    public Box(int position){
        this.position = position;
    }

    /**
     * Move the box to its new position.
     *
     * @param position new position for the box
     */
    public void move(int position) {
        this.position = position;
    }

    /**
     * The box has arrived at a destination point.
     */
    public void arriveDestination(){
        currImage = R.drawable.winbox;
    }

    /**
     * The box have left a destination point.
     */
    public void  leaveDestination(){
        currImage = normalImage;
    }

    /**
     * Return the position of this box.
     *
     * @return the position of this box
     */
    public int getPosition(){
        return position;
    }

    /**
     * Return the image id of this box.
     *
     * @return image id of this box
     */
    public int getImage(){
        return currImage;
    }

    /**
     * Compare if boxes are the same.
     *
     * @param box the box to compare with.
     * @return if boxes are the same box.
     */
    public Boolean equals(Box box){
        return box.getPosition() == this.getPosition();
    }
}
