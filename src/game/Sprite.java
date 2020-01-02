package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Class that represents the sprites of the games
 * 
 * @author Moi
 *
 */
public abstract class Sprite {
    protected ImageView imageView;
    private Pane layer;

    protected double pos_x;
    protected double pos_y;
    
    private double w;
    private double h;

    private boolean removable = false;

    /**
     * Construct a sprite
     * @param layer Layer in which the sprite must appear (@see Pane)
     * @param img Image that will be displayed for this sprite
     * @param x Position x on the screen
     * @param y Position y on the screen
     */
    public Sprite(Pane layer, Image img, double x, double y) {

        this.layer = layer;
        this.pos_x = x;
        this.pos_y = y;
        
        this.imageView = new ImageView(img);
        this.imageView.relocate(x, y);
        
        this.w = img.getWidth();
        this.h = img.getHeight();
        
        addToLayer();
    }
    
    /**
     * Change the image to be displayed for this sprite
     * @param img New image
     */
    public void setImageView(Image img) {
    	this.imageView.setImage(img);
    }
    
    /**
     * Display this sprite on the current layer
     */
    public void addToLayer() {
        this.layer.getChildren().add(this.imageView);
    }

    /**
     * Undisplay this sprite of the current layer
     */
    public void removeFromLayer() {
        this.layer.getChildren().remove(this.imageView);
    }

    /**
     * Getter of position x
     * @return Position x of this sprite
     */
    public double getPos_x() {
        return pos_x;
    }

    /**
     * Getter of position y
     * @return Position y of thhis sprite
     */
    public double getPos_y() {
        return pos_y;
    }
    
    /**
     * Getter of the width of this sprite
     * @return Width of this sprite
     */
    public double getWidth() {
    	return w;
    }
    
    /**
     *  Getter of the height of this sprite
     * @return Height of this sprite
     */
    public double getHeight() {
    	return h;
    }

    /**
     * Getter of the removability of this sprite
     * @return true if it is removable, else false
     */
    public boolean isRemovable() {
        return removable;
    }
    
    protected ImageView getView() {
        return imageView;
    }

    /**
     * Relocate the location of this sprite
     */
    public void updateUI() {
        imageView.relocate(pos_x, pos_y);
    }

    /**
     * Set the removability of this sprite to true
     */
    public void remove() {
        this.removable = true;
    }
    
    /**
     * Getter of the ImageView of this sprite (@see ImageView)
     * @return ImageView of this sprite
     */
	public ImageView getImageView() {
		return imageView;
	}
	
	/**
	 * Getter of the layer in which the sprite appears
	 * @return Pane in which the sprite appears
	 */
	public Pane getLayer() {
		return layer;
	}

}
