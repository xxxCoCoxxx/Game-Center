package fall2018.csc207project.PushTheBox.Controllers;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fall2018.csc207project.PushTheBox.Models.BgTile;
import fall2018.csc207project.PushTheBox.Models.Box;
import fall2018.csc207project.PushTheBox.Models.GameMap;
import fall2018.csc207project.PushTheBox.Models.MapManager;
import fall2018.csc207project.PushTheBox.Models.Person;
import fall2018.csc207project.R;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class MovementControllerTest {
    private HashMap<String, Object> levelInfo = new HashMap<>();
    private ArrayList<BgTile> bgElements = new ArrayList<>();
    private ArrayList<Box> boxes = new ArrayList<Box>();
    MovementController mc = new MovementController();

    private void init(){
        bgElements.clear();
        bgElements.add(new BgTile("Floor"));
        bgElements.add(new BgTile("Floor"));
        boxes.clear();
        levelInfo.put("boxArrayList", new ArrayList<Box>());
        levelInfo.put("map", new GameMap(2, 1, bgElements));
    }

    @Test
    @Before
    public void testHorizontalMovement() {
        init();
        levelInfo.put("Person", new Person(1));
        MapManager mapManager = new MapManager(0,0,levelInfo);
        mc.setMapManager(mapManager);
        mc.processTapMovement("left");
        assertEquals("person didn't move to left", 0,
                mapManager.person.getPosition());
        mc.processTapMovement("right");
        assertEquals("person didn't move to right", 1,
                mapManager.person.getPosition());
    }

    @Test
    public void testMovementTowardsLeftWall() {
        init();
        bgElements.set(0, new BgTile("Wall"));
        levelInfo.put("bgElements", bgElements);
        levelInfo.put("Person", new Person(1));
        MapManager mapManager = new MapManager(0,0,levelInfo);
        mc.setMapManager(mapManager);
        mc.processTapMovement("left");
        assertEquals("person is unable to move left towards a wall", 1,
                mapManager.person.getPosition());
    }

    @Test
    public void testMovementTowardsRightWall() {
        init();
        bgElements.set(1, new BgTile("Wall"));
        levelInfo.put("bgElements", bgElements);
        levelInfo.put("Person", new Person(0));
        MapManager mapManager = new MapManager(0,0,levelInfo);
        mc.setMapManager(mapManager);
        mc.processTapMovement("right");
        assertEquals("person is unable to move right towards a wall", 0,
                mapManager.person.getPosition());
    }

    @Test
    public void testVerticalMovement() {
        init();
        levelInfo.put("Person", new Person(1));
        levelInfo.put("map", new GameMap(1,2,bgElements));
        MapManager mapManager = new MapManager(0,0,levelInfo);

        mc.setMapManager(mapManager);
        mc.processTapMovement("up");
        assertEquals("person didn't move up", 0,
                mapManager.person.getPosition());
        mc.processTapMovement("down");
        assertEquals("person didn't move down", 1,
                mapManager.person.getPosition());
    }

    @Test
    public void testMovementUpToWall() {
        init();
        bgElements.set(0, new BgTile("Wall"));
        levelInfo.put("Person", new Person(1));
        levelInfo.put("map", new GameMap(1,2,bgElements));
        MapManager mapManager = new MapManager(0,0,levelInfo);

        mc.setMapManager(mapManager);
        mc.processTapMovement("up");
        assertEquals("person is unable to move up towards a wall", 1,
                mapManager.person.getPosition());
    }

    @Test
    public void testMovementDownToWall() {
        init();
        bgElements.set(1, new BgTile("Wall"));
        levelInfo.put("Person", new Person(0));
        levelInfo.put("map", new GameMap(1,2,bgElements));
        MapManager mapManager = new MapManager(0,0,levelInfo);

        mc.setMapManager(mapManager);
        mc.processTapMovement("down");
        assertEquals("person is unable to move down towards a wall", 0,
                mapManager.person.getPosition());
    }

    @Test
    public void testMovementPushBoxToFloor(){
        init();
        bgElements.add(new BgTile("Floor"));
        levelInfo.put("Person", new Person(0));
        boxes.add(new Box(1));
        levelInfo.put("boxArrayList", boxes);
        levelInfo.put("map", new GameMap(3,1,bgElements));
        MapManager mapManager = new MapManager(0,0,levelInfo);

        mc.setMapManager(mapManager);
        mc.processTapMovement("right");
        assertEquals("box should be pushed", 2,
                mapManager.getBoxList().get(0).getPosition());
    }

    @Test
    public void testMovementPushingBoxToDes(){
        init();
        bgElements.add(new BgTile("Destination"));
        levelInfo.put("Person", new Person(0));
        boxes.add(new Box(1));
        levelInfo.put("boxArrayList", boxes);
        levelInfo.put("map", new GameMap(3,1,bgElements));
        MapManager mapManager = new MapManager(0,0,levelInfo);

        mc.setMapManager(mapManager);
        mc.processTapMovement("right");
        assertEquals("box should be pushed", 2,
                mapManager.getBoxList().get(0).getPosition());
    }

    @Test
    public void testMovementPushingBoxToWall(){
        init();
        bgElements.add(new BgTile("Wall"));
        levelInfo.put("Person", new Person(0));
        boxes.add(new Box(1));
        levelInfo.put("boxArrayList", boxes);
        levelInfo.put("map", new GameMap(3,1,bgElements));
        MapManager mapManager = new MapManager(0,0,levelInfo);

        mc.setMapManager(mapManager);
        mc.processTapMovement("right");
        assertEquals("box shouldn't be pushed", 1,
                mapManager.getBoxList().get(0).getPosition());
    }

    @Test
    public void testMovementPushingBoxToBox(){
        init();
        bgElements.add(new BgTile("Floor"));
        bgElements.add(new BgTile("Floor"));
        levelInfo.put("Person", new Person(0));
        boxes.add(new Box(1));
        boxes.add(new Box(2));
        levelInfo.put("boxArrayList", boxes);
        levelInfo.put("map", new GameMap(4,1,bgElements));
        MapManager mapManager = new MapManager(0,0,levelInfo);

        mc.setMapManager(mapManager);
        mc.processTapMovement("right");
        assertEquals("first box shouldn't be pushed", 1,
                mapManager.getBoxList().get(0).getPosition());
        assertEquals("second box shouldn't be pushed", 2,
                mapManager.getBoxList().get(1).getPosition());
    }
}