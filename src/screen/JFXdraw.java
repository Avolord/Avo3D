package screen;

import obj3D.Obj3D;
import compute.V3D;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.*;
import main.console;

public class JFXdraw extends Application {
	private static ObjectBuffer2D buffer2D = null;
	private static AnimationTimer timer;
	private int counter = 0;
	public static int FPS = 60;
	private static long FPStime = System.nanoTime();
	private static double averageFPS = FPS;

	public static void initialize(ObjectBuffer2D buffer) {
		JFXdraw.buffer2D = buffer;
		V3D.initBuffer(buffer);
		Obj3D.initBuffer(buffer);
	}

	public static void start(String[] args) {
		if (JFXdraw.buffer2D == null) {
			System.out.println("I have nothing to draw on!");
			return;
		}
		buffer2D.translate(200, 200);
		Render.rotate(0, 0, 0);
		launch(args);
	}

	public static ObjectBuffer2D getBuffer2D() {
		return buffer2D;
	}
	
	public static void computeFPS() {
		double fps = 60 / FPS /((System.nanoTime() - FPStime) / 1E9);
		FPStime = System.nanoTime();
		averageFPS = (averageFPS + fps) / 2d;
		buffer2D.fps(Math.floor(fps));
	}

	public static void start(String[] args, int width, int height) {
		ObjectBuffer2D buffer = new ObjectBuffer2D(width, height);

		buffer2D = buffer;
		V3D.initBuffer(buffer);
		Obj3D.initBuffer(buffer);

		buffer2D.translate(buffer.getWidth() / 2, buffer.getHeight() / 2);
		//Render.rotate(0, 0, 0);
		launch(args);
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
				if (counter >= 60 / FPS) {
					counter = 0;
					buffer2D.clear();
					computeFPS();
					Obj3D.getProjection().smoothMove(10);
					Render.main();
				}
			}
		};
		timer.start();

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				Obj3D.getProjection().keyInput(event.getCode(), true);
				if(event.getCode() == KeyCode.ENTER) {
					primaryStage.close();
					console.log("Average FPS: "+averageFPS);
				}
			}
		});
		
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				Obj3D.getProjection().keyInput(event.getCode(), false);
			}
		});

		root.getChildren().add(buffer2D.getC());

		primaryStage.show();
	}
}
