import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class ImageVar {
	private Image image;
	private Pane layer;
	
	private int bordures;
	
	public ImageVar(Image image, Pane layer, int bordures) {
		this.image = image;
		this.layer = layer;
		this.bordures = bordures;
	}
	
	public Image getImage() {
		return image;
	}
	
	public Pane getLayer() {
		return layer;
	}
	
	public int getBordures() {
		return bordures;
	}
}
