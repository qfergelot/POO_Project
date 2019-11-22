import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class Sprite {
    private ImageView imageView;
    private Pane layer;
    private int bordures;

    protected double pos_x;
    protected double pos_y;
    
    private double w;
    private double h;

    private boolean removable = false;


    public Sprite(ImageVar img, double x, double y) {

        this.layer = img.getLayer();
        this.pos_x = x;
        this.pos_y = y;
        bordures = img.getBordures();
        
        this.imageView = new ImageView(img.getImage());
        this.imageView.relocate(x, y);
        
        w = img.getImage().getWidth();
        h = img.getImage().getHeight();

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
        imageView.relocate(bordures + pos_x, pos_y);
    }

    public void remove() {
        this.removable = true;
    }

    public abstract void checkRemovability();

}
