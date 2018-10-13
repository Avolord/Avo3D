package screen;

import obj3D.Obj3D;
import compute.Projection;
import compute.V3D;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.*;

public class JFXdraw extends Application {
	private static ObjectBuffer2D buffer2D = null;
	private static AnimationTimer timer;
	private int counter = 0;
	public static int FPS = 60;
	
	
	public static void initialize(ObjectBuffer2D buffer) {
		JFXdraw.buffer2D = buffer;
		V3D.initBuffer(buffer);
		Obj3D.initBuffer(buffer);
	}

	public static void start(String[] args) {
		if(JFXdraw.buffer2D == null) {
			System.out.println("I have nothing to draw on!");
			return;
		}
		buffer2D.translate(200, 200);
		launch(args);
	}
	
	public static ObjectBuffer2D getBuffer2D() {
		return buffer2D;
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle(buffer2D.getTitle());
		
		StackPane root = new StackPane();
		Scene scene = new Scene(root, buffer2D.getWidth(), buffer2D.getHeight());
		primaryStage.setScene(scene);
		timer = new AnimationTimer() {
	        @Override
	        public void handle(long now) {
	        	counter++;
	            if(counter >= 60 / FPS) {
	            	counter = 0;
	            	buffer2D.clear();
	            	Render.main();
	            }
	        }
	    };
	    timer.start();
	    
	    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
            	switch(event.getCode()) {
            	case UP:
            		Projection.rotateCamera(-10,0,0);
            	break;
            	case DOWN:
            		Projection.rotateCamera(10,0,0);
            	break;
            	case LEFT:
            		Projection.rotateCamera(0,10,0);
            	break;
            	case RIGHT:
            		Projection.rotateCamera(0,-10,0);
            	break;
            	case D:
            		Projection.moveCamera(-10,0,0);
            	break;
            	case A:
            		Projection.moveCamera(10,0,0);
            	break;
            	case W:
            		Projection.moveCamera(0,0,10);
            	break;
            	case S:
            		Projection.moveCamera(0,0,-10);
            	break;
            	case SPACE:
            		Projection.moveCamera(0,10,0);
            	break;
            	case G:
            		Projection.moveCamera(0,-10,0);
            	break;
            	case ENTER:
            		primaryStage.close();
            	break;
            	case NUMPAD8:
            		Projection.moveViewer(0,0,10);
            	break;
            	case NUMPAD2:
            		Projection.moveViewer(0,0,-10);
            	break;
            	case NUMPAD4:
            		Projection.moveViewer(-10,0,0);
            	break;
            	case NUMPAD6:
            		Projection.moveViewer(10,0,0);
            	break;
				default:
					break;
            	}
            }
        });

		root.getChildren().add(buffer2D.getC());

		primaryStage.show();
	}
}
