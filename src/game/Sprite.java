package game;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import royaume.Constantes;

public abstract class Sprite {
    protected ImageView imageView;
    private Pane layer;

    protected long pos_x;
    protected long pos_y;
    
    private long w;
    private long h;

    private boolean removable = false;


    public Sprite(Pane layer, Image img, long x, long y) {

        this.layer = layer;
        this.pos_x = x;
        this.pos_y = y;
        
        this.imageView = new ImageView(img);
        this.imageView.relocate(x, y);
        
        w = (long)img.getWidth();
        h = (long)img.getHeight();

        addToLayer();

    }

    public void addToLayer() {
        this.layer.getChildren().add(this.imageView);
    }

    public void removeFromLayer() {
        this.layer.getChildren().remove(this.imageView);
    }

    public long getPos_x() {
        return pos_x;
    }

    public long getPos_y() {
        return pos_y;
    }
    
    public long getWidth() {
    	return w;
    }
    
    public long getHeight() {
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
