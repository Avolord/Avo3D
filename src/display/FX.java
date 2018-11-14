package display;

import display.camera.Projection;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import object3D.Obj3D;

public class FX extends Application{
	public static Display display;
	public static Projection projector = new Projection(0d, 0d, -200d, 0d, 0d, 0d);
	private static AnimationTimer timer;
	public static int FPS = 60;
	private static long FPStime = System.nanoTime();
	private static double averageFPS = FPS;
	private int counter = 0;
	
	public static void initialize(int width, int height, String title) {
		display = new Display(width, height, title);
		display.translate(width/2, height/2);
		Obj3D.initDisplay(display);
		launch();
	}
	
	public static void computeFPS() {
		double fps = 60 / FPS /((System.nanoTime() - FPStime) / 1E9);
		FPStime = System.nanoTime();
		averageFPS = (averageFPS + fps) / 2d;
		display.fps(Math.floor(fps));
	}
	
	@Override
	public void start(Stage stage) {
		stage.setTitle(display.getTitle());
		stage.setResizable(false);
		
		StackPane root = new StackPane();

	    Scene scene = new Scene(root, display.getWidth(), display.getHeight());
	    stage.setScene(scene);
	    
	    timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				counter++;
				if (counter >= 60 / FPS) {
					counter = 0;
					display.clear();
					projector.smoothMove(10);
					Renderer.start();
					computeFPS();
				}
			}
		};
		timer.start();

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				projector.keyInput(event.getCode(), true);
				if(event.getCode() == KeyCode.ENTER) {
					stage.close();
				}
			}
		});
		
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				projector.keyInput(event.getCode(), false);
			}
		});
		
		root.getChildren().add(display.getC());
	    stage.show();
	}
	
	public static GraphicsContext getCtx() {
		return display.getCtx();
	}

}
