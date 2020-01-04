package game;
import static javafx.scene.input.KeyCode.ESCAPE;
import static javafx.scene.input.KeyCode.SPACE;

import java.util.BitSet;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * 
 * Class managing inputs
 *
 */
public class Input {
	
	/**
	 * Bitset which registers if any {@link KeyCode} keeps being pressed or if it is
	 * released.
	 */
	private BitSet keyboardBitSet = new BitSet();

	private Scene scene = null;

	
	/**
	 * 
	 * @param scene Current scene
	 *
	 */
	public Input(Scene scene) {
		this.scene = scene;
	}

	/**
	 * Add listeners for pressing keys to the current scene
	 */
	public void addListeners() {
		scene.addEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
		scene.addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);
	}

	/**
	 * Remove listeners for pressing keys to the current scenes
	 */
	public void removeListeners() {
		scene.removeEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
		scene.removeEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);
	}

	/**
	 * "Key Pressed" handler for all input events: register pressed key in the
	 * bitset
	 */
	private EventHandler<KeyEvent> keyPressedEventHandler = event -> {
		// register key down
		keyboardBitSet.set(event.getCode().ordinal(), true);
		event.consume();
	};

	/**
	 * "Key Released" handler for all input events: unregister released key in the
	 * bitset
	 */
	private EventHandler<KeyEvent> keyReleasedEventHandler = new EventHandler<KeyEvent>() {
		@Override
		public void handle(KeyEvent event) {
			// register key up
			keyboardBitSet.set(event.getCode().ordinal(), false);
			event.consume();
		}
	};

	/**
	 * 
	 * @param key key to be compared with the current input
	 * @return true if key is inputed, else false
	 */
	private boolean is(KeyCode key) {
		return keyboardBitSet.get(key.ordinal());
	}
	
	/**
	 * 
	 * @return true if Space key is inputed, else false 
	 */
	public boolean isPause() {
		return is(SPACE);
	}

	/**
	 * 
	 * @return true if Escape key is inputed, else false
	 */
	public boolean isExit() {
		return is(ESCAPE);
	}
	
	/**
	 * 
	 * @return true if P key is inputed, else false
	 */
	public boolean isLoad() {
		return is(KeyCode.L);
	}
	
	
	
	

}
