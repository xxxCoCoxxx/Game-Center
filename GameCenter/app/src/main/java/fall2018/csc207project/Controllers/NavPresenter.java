package fall2018.csc207project.Controllers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import fall2018.csc207project.Models.ImageManager;
import fall2018.csc207project.Views.NavView;

/**
 * The class NavPresenter implements BasePresenter
 */
public class NavPresenter implements BasePresenter{

    /**
     * The NavView for the current user.
     */
    private NavView view;

    /**
     * The ImageManager to manage images.
     */
    private ImageManager manager;

    /**
     * Construct a new NavPresenter by given a NavView and an ImageManager.
     *
     * @param view the NavView for the current user
     * @param manager the ImageManager to manage images
     */
    public NavPresenter(NavView view, ImageManager manager){
        this.manager = manager;
        this.view = view;
    }

    @Override
    public void onBackgroundSelected(Uri imageUri, Context context) {
       Drawable drawable = manager.saveBackground(imageUri, context);
       view.showBackground(drawable);
    }

    @Override
    public void onAvatarSelected(Uri imageUri, Context context) {
        Drawable drawable = manager.saveAvatar(imageUri, context);
        view.showAvatar(drawable);
    }

    @Override
    public void onResetClicked(Context context) {
        if(manager.clearBackground(context)){
            view.showBackground(null);
        }
    }

    @Override
    public void initializeView(Context context) {
        Drawable drawable = manager.getBackground(context);
        Drawable avatar = manager.getAvatar(context);
        if(drawable != null){
            view.showBackground(drawable);
        }
        if(avatar != null){
            view.showAvatar(avatar);
        }
    }
}
