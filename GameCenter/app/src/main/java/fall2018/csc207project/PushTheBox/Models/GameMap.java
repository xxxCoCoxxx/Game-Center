package fall2018.csc207project.PushTheBox.Models;

import java.io.Serializable;
import java.util.List;

/**
 * The map where a person will push the boxes to destinations.
 * Excluded from tests because it's a model class.
 */
public class GameMap implements Serializable {

    /**
     * The list of background tiles.
     */
    private List<BgTile> bgElements;

    /**
     * The total number of rows of the map grid.
     */
    final int NUM_ROW;

    /**
     * The total number of columns of the map grid.
     */
    final int NUM_COL;

    /**
     * A new map in row*col size.
     * @param numRow the total number of rows
     * @param numCol the total number of columns
     * @param bgElements the list of elements that are the background tiles
     */
    public GameMap(int numCol, int numRow, List<BgTile> bgElements){
        this.NUM_ROW = numRow;
        this.NUM_COL = numCol;
        this.bgElements = bgElements;
    }

    /**
     * Returns whether the tile with given tile ID is a wall
     * @param tileId the ID of the tile to be evaluated
     * @return whether the tile with tile's ID is a wall
     */
    public Boolean tileIsWall(int tileId){
        return !bgElements.get(tileId).canBeStand();
    }

    /**
     * Whether tile is a destination.
     * @param tileId the id of the tile
     * @return if tile is a destination
     */
    public Boolean tileIsDestination(int tileId){
        return bgElements.get(tileId).isWinnable();
    }

    /**
     * Returns the list of tiles on the map
     * @return the list of all BgTile
     */
    public List<BgTile> getBgElements(){
        return bgElements;
    }


    /**
     * Returns the number of columns of the map.
     * @return the total number of columns
     */
    public int getNumCol(){
        return NUM_COL;
    }


}
