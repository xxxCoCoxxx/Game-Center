package fall2018.csc207project.PushTheBox.Controllers;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Observable;
import fall2018.csc207project.Models.ScoreManager;
import fall2018.csc207project.PushTheBox.Models.BoxGameCalculator;
import fall2018.csc207project.PushTheBox.Models.BoxScore;
import fall2018.csc207project.PushTheBox.Models.MapManager;
import fall2018.csc207project.PushTheBox.View.MapView;
import fall2018.csc207project.Models.DatabaseUtil;
import fall2018.csc207project.Models.SaveManager;
import fall2018.csc207project.Models.SaveSlot;

/**
 * The class BoxGamePresenter that implements GamePresenter
 */
public class BoxGamePresenter implements GamePresenter {
    /**
     * The map manager.
     */
    private MapManager mapManager;

    /**
     * The save manager.
     */
    private SaveManager saveManager;

    /**
     * Current user playing the game.
     */
    private String currentUser;

    /**
     * The view of the game.
     */
    private MapView view;

    /**
     * The controller which controls the movements of person and boxes.
     */
    private MovementController movementController;

    /**
     * The save slot of the current user.
     */
    private SaveSlot saveSlot;

    /**
     * A new box game presenter.
     *
     * @param view the view of this game
     * @param context the context of this app
     */
    public BoxGamePresenter(MapView view, Context context){
        this.view = view;
        setUpSaveManager(context);
        movementController = new MovementController();
    }

    /**
     * Set up save manager to save game.
     * @param context context
     */
    private void setUpSaveManager(Context context){
        SharedPreferences shared = context.getSharedPreferences("GameData", Context.MODE_PRIVATE);
        currentUser = shared.getString("currentUser", null);
        String currentGame = shared.getString("currentGame", null);
        saveManager = DatabaseUtil.getSaveManager(currentUser, currentGame);
        saveSlot = saveManager.readFromFile(context);
    }

    /**
     * Set up the map manager of this presenter
     * @param mapManager of this presenter
     */
    public void setMapManager(MapManager mapManager){
        this.mapManager = mapManager;
        mapManager.subscribe(this);
        movementController.setMapManager(mapManager);
    }

    /**
     * When one of the arrow buttons is clicked, let the person process movement and update the map.
     * @param direction direction chosen
     */
    public void arrowButtonClicked(Context context, String direction){
        if (!movementController.processTapMovement(direction)){
            view.makeInvalidMovementText();
        }else if(mapManager.boxSolved()){
            view.levelComplete();
        }
        saveSlot.saveToAutoSave(mapManager);
        saveManager.saveToFile(saveSlot, context);
    }

    /**
     * When undo button is clicked, process undo.
     * @param step the number of undo steps.
     */
    public void onUndoButtonClicked(int step){
        if(!mapManager.canProcessUndo(step)) {
            view.makeToastNoUndoTimesLeftText();
        }
    }

    /**
     * When undo text is clicked, show number picker for user to choose undo steps.
     */
    public void onUndoTextClicked(){
        view.showNumberPicker();
    }

    @Override
    public void saveScores(Context context) {
        if (mapManager.boxSolved()){
            BoxGameCalculator calculator = new BoxGameCalculator();
            ScoreManager<BoxScore> scoreManager
                    = DatabaseUtil.getScoreManager("PushBox",currentUser,calculator);
            BoxScore boxScore = new BoxScore(mapManager.getLevel(),
                    mapManager.getTotalUndoTimes(), mapManager.getTotalMoveSteps());
            scoreManager.saveScore(boxScore, context);
        }
    }

    /**
     * Updates notified to observer. Calls the View to update the map.
     *
     * @param o updates to the Observable
     * @param arg the Object to be update
     */
    @Override
    public void update(Observable o, Object arg) {
        view.updateMap(mapManager);
    }

}
