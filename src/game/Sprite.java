package game;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import royaume.Constantes;

public abstract class Sprite {
    protected ImageView imageView;
    private Pane layer;

    protected double pos_x;
    protected double pos_y;
    
    private double w;
    private double h;

    private boolean removable = false;

    public Sprite(Pane layer, Image img, double x, double y) {

        this.layer = layer;
        this.pos_x = x;
        this.pos_y = y;
        
        this.imageView = new ImageView(img);
        this.imageView.relocate(x, y);
        
        w = img.getWidth();
        h = img.getHeight();
        
        addToLayer();
    }
    
    public void addToLayer() {
        this.layer.getChildren().add(this.imageView);
    }

    public void removeFromLayer() {
        this.layer.getChildren().remove(this.imageView);
    }

    public double getPos_x() {
        return pos_x;
    }

    public double getPos_y() {
        return pos_y;
    }
    
    public double getWidth() {
    	return w;
    }
    
    public double getHeight() {
    	return h;
    }

    public boolean isRemovable() {
        return removable;
    }

    protected ImageView getView() {
        return imageView;
    }

    public void updateUI() {
        imageView.relocate(pos_x, pos_y);
    }

    public void remove() {
        this.removable = true;
    }
    
	public ImageView getImageView() {
		return imageView;
	}
	
	public Pane getLayer() {
		return layer;
	}

}
