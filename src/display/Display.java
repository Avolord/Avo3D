package display;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class Display {
	private GraphicsContext ctx = null;
	private Canvas c = null;
	private int width, height;
	private String title = "default";
	private double transX = 0;
	private double transY = 0;

	private Color color = Color.BLACK;
	private ArcType arcT = ArcType.OPEN;
	private double strokewidth = 1;
	private String arcmode = "center";
	
	public Display(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
		this.initializeCanvas();
	}
	
	private void initializeCanvas() {
		c = new Canvas(width, height);
		ctx = c.getGraphicsContext2D();
	}

	private final void initDim(int w, int h) {
		this.width = w;
		this.height = h;
	}

	public void translate(double x, double y) {
		ctx.translate(-transX, -transY);
		transX = x;
		transY = y;
		ctx.translate(x, y);
	}

	private final void initGraphics() {
		c = new Canvas(width, height);
		ctx = c.getGraphicsContext2D();
	}

	public void strokeWidth(double width) {
		strokewidth = width;
		System.out.println(strokewidth);
	}

	public void changeArcMode() {
		arcmode = (arcmode == "standard") ? "center" : "standard";
		System.out.println("Set ArcMode to: " + arcmode);
	}

	public void clear() {
		ctx.clearRect(-transX, -transY, width, height);
	}

	public void background(String color) {
		rect(0, 0, width, height, "fill", color);
	}

	public void line(double x1, double y1, double x2, double y2) {
		ctx.setLineWidth(strokewidth);
		ctx.setStroke(color);
		ctx.strokeLine(x1, y1, x2, y2);
	}

	public void line(double x1, double y1, double x2, double y2, double width) {
		ctx.setLineWidth(width);
		ctx.setStroke(color);
		ctx.strokeLine(x1, y1, x2, y2);
	}

	public void line(double x1, double y1, double x2, double y2, Color color) {
		ctx.setLineWidth(strokewidth);
		ctx.setStroke(color);
		ctx.strokeLine(x1, y1, x2, y2);
	}

	public void line(double x1, double y1, double x2, double y2, String color) {
		ctx.setLineWidth(strokewidth);
		ctx.setStroke(Color.web(color));
		ctx.strokeLine(x1, y1, x2, y2);
	}

	public void line(double x1, double y1, double x2, double y2, Color color, double width) {
		ctx.setLineWidth(width);
		ctx.setStroke(color);
		ctx.strokeLine(x1, y1, x2, y2);
	}

	public void line(double x1, double y1, double x2, double y2, String color, double width) {
		ctx.setLineWidth(width);
		ctx.setStroke(Color.web(color));
		ctx.strokeLine(x1, y1, x2, y2);
	}

	public void line(Point2D p1, Point2D p2) {
		ctx.setLineWidth(strokewidth);
		ctx.setStroke(color);
		ctx.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}
	
	public void line(Point2D p1, Point2D p2, String color) {
		ctx.setLineWidth(strokewidth);
		ctx.setStroke(Color.web(color));
		ctx.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}
	
	public void line(Point2D p1, Point2D p2, Color color) {
		ctx.setLineWidth(strokewidth);
		ctx.setStroke(color);
		ctx.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}
	
	public void PolyLine(String color, Point2D... pts) {
		double[] valX = new double[pts.length];
		double[] valY = new double[pts.length];

		for (int i = 0; i < pts.length; i++) {
			valX[i] = pts[i].getX();
			valY[i] = pts[i].getY();
		}
		ctx.setFill(Color.web(color));
		ctx.strokePolyline(valX, valY, pts.length);
	}
	
	public void PolyLine(Color color, Point2D... pts) {
		double[] valX = new double[pts.length];
		double[] valY = new double[pts.length];

		for (int i = 0; i < pts.length; i++) {
			valX[i] = pts[i].getX();
			valY[i] = pts[i].getY();
		}
		ctx.setFill(color);
		ctx.strokePolyline(valX, valY, pts.length);
	}

	public void rect(double x, double y, double width, double height) {
		ctx.setFill(color);
		ctx.fillRect(x, y, width, height);
	}

	public void rect(double x, double y, double width, double height, Color color) {
		ctx.setFill(color);
		ctx.fillRect(x, y, width, height);
	}

	public void rect(double x, double y, double width, double height, String color) {
		ctx.setFill(Color.web(color));
		ctx.fillRect(x, y, width, height);
	}

	public void rect(double x, double y, double width, double height, String style, Color color) {
		ctx.setLineWidth(strokewidth);
		switch (style.toLowerCase()) {
		case "fill":
			ctx.setFill(color);
			ctx.fillRect(x, y, width, height);
			break;
		case "stroke":
			ctx.setStroke(color);
			ctx.strokeRect(x, y, width, height);
			break;
		default:
			System.out.println("Available styles: 'fill', 'stroke'.");
		}
	}

	public void rect(double x, double y, double width, double height, String style, String color) {
		ctx.setLineWidth(strokewidth);
		switch (style.toLowerCase()) {
		case "fill":
			ctx.setFill(Color.web(color));
			ctx.fillRect(x, y, width, height);
			break;
		case "stroke":
			ctx.setStroke(Color.web(color));
			ctx.strokeRect(x, y, width, height);
			break;
		default:
			System.out.println("Available styles: 'fill', 'stroke'.");
		}
	}

	public void circle(double x, double y, double radius) {
		if (arcmode == "center") {
			x -= radius / 2;
			y -= radius / 2;
		}
		ctx.setFill(color);
		ctx.fillArc(x, y, radius, radius, 0.0, 360.0, arcT);
	}

	public void circle(double x, double y, double radius, Color color) {
		if (arcmode == "center") {
			x -= radius / 2;
			y -= radius / 2;
		}
		ctx.setFill(color);
		ctx.fillArc(x, y, radius, radius, 0.0, 360.0, arcT);
	}

	public void circle(double x, double y, double radius, String color) {
		if (arcmode == "center") {
			x -= radius / 2;
			y -= radius / 2;
		}
		ctx.setFill(Color.web(color));
		ctx.fillArc(x, y, radius, radius, 0.0, 360.0, arcT);
	}

	public void circle(double x, double y, double radius, String style, Color color) {
		if (arcmode == "center") {
			x -= radius / 2;
			y -= radius / 2;
		}
		ctx.setLineWidth(strokewidth);
		switch (style.toLowerCase()) {
		case "fill":
			ctx.setFill(color);
			ctx.fillArc(x, y, radius, radius, 0.0, 360.0, arcT);
			break;
		case "stroke":
			ctx.setStroke(color);
			ctx.strokeArc(x, y, radius, radius, 0.0, 360.0, arcT);
			break;
		default:
			System.out.println("Available styles: 'fill', 'stroke'.");
		}
	}

	public void circle(double x, double y, double radius, String style, String color) {
		if (arcmode == "center") {
			x -= radius / 2;
			y -= radius / 2;
		}
		ctx.setLineWidth(strokewidth);
		switch (style.toLowerCase()) {
		case "fill":
			ctx.setFill(Color.web(color));
			ctx.fillArc(x, y, radius, radius, 0.0, 360.0, arcT);
			break;
		case "stroke":
			ctx.setStroke(Color.web(color));
			ctx.strokeArc(x, y, radius, radius, 0.0, 360.0, arcT);
			break;
		default:
			System.out.println("Available styles: 'fill', 'stroke'.");
		}
	}

	public void ellipse(double x, double y, double width, double height) {
		if (arcmode == "center") {
			x -= width / 2;
			y -= height / 2;
		}
		ctx.setFill(color);
		ctx.fillArc(x, y, width, height, 0.0, 360.0, arcT);
	}

	public void ellipse(double x, double y, double width, double height, Color color) {
		if (arcmode == "center") {
			x -= width / 2;
			y -= height / 2;
		}
		ctx.setFill(color);
		ctx.fillArc(x, y, width, height, 0.0, 360.0, arcT);
	}

	public void ellipse(double x, double y, double width, double height, String color) {
		if (arcmode == "center") {
			x -= width / 2;
			y -= height / 2;
		}
		ctx.setFill(Color.web(color));
		ctx.fillArc(x, y, width, height, 0.0, 360.0, arcT);
	}

	public void ellipse(double x, double y, double width, double height, String style, Color color) {
		if (arcmode == "center") {
			x -= width / 2;
			y -= height / 2;
		}
		ctx.setLineWidth(strokewidth);
		switch (style.toLowerCase()) {
		case "fill":
			ctx.setFill(color);
			ctx.fillArc(x, y, width, height, 0.0, 360.0, arcT);
			break;
		case "stroke":
			ctx.setStroke(color);
			ctx.strokeArc(x, y, width, height, 0.0, 360.0, arcT);
			break;
		default:
			System.out.println("Available styles: 'fill', 'stroke'.");
		}
	}

	public void ellipse(double x, double y, double width, double height, String style, String color) {
		if (arcmode == "center") {
			x -= width / 2;
			y -= height / 2;
		}
		ctx.setLineWidth(strokewidth);
		switch (style.toLowerCase()) {
		case "fill":
			ctx.setFill(Color.web(color));
			ctx.fillArc(x, y, width, height, 0.0, 360.0, arcT);
			break;
		case "stroke":
			ctx.setStroke(Color.web(color));
			ctx.strokeArc(x, y, width, height, 0.0, 360.0, arcT);
			break;
		default:
			System.out.println("Available styles: 'fill', 'stroke'.");
		}
	}

	public void fillPoly(String color, Point2D... p2ds) {
		ctx.setFill(Color.web(color));
		double[] valX = new double[p2ds.length];
		double[] valY = new double[p2ds.length];
		
		for (int i = 0; i < p2ds.length; i++) {
			valX[i] = p2ds[i].getX();
			valY[i] = p2ds[i].getY();
		}	
		ctx.fillPolygon(valX, valY, p2ds.length);
	}
	
	public void fillPoly(Color color, Point2D... p2ds) {
		ctx.setFill(color);
		double[] valX = new double[p2ds.length];
		double[] valY = new double[p2ds.length];
		
		for (int i = 0; i < p2ds.length; i++) {
			valX[i] = p2ds[i].getX();
			valY[i] = p2ds[i].getY();
		}	
		ctx.fillPolygon(valX, valY, p2ds.length);
	}
	
	public void strokePoly(String color, Point2D... p2ds) {
		ctx.setStroke(Color.web(color));
		double[] valX = new double[p2ds.length];
		double[] valY = new double[p2ds.length];
		
		for (int i = 0; i < p2ds.length; i++) {
			valX[i] = p2ds[i].getX();
			valY[i] = p2ds[i].getY();
		}	
		ctx.strokePolygon(valX, valY, p2ds.length);
	}
	
	public void strokePoly(Color color, Point2D... p2ds) {
		ctx.setStroke(color);
		double[] valX = new double[p2ds.length];
		double[] valY = new double[p2ds.length];
		
		for (int i = 0; i < p2ds.length; i++) {
			valX[i] = p2ds[i].getX();
			valY[i] = p2ds[i].getY();
		}	
		ctx.strokePolygon(valX, valY, p2ds.length);
	}
	
	public void write(String text, double x, double y) {
		ctx.setFill(Color.BLACK);
		ctx.fillText(text, x, y);
	}
	
	public void fps(double FPS) {
		ctx.setFill(Color.RED);
		ctx.fillText(Double.toString(FPS), -transX+10, -transY+10);
	}

	public Canvas getC() {
		return c;
	}
	
	public GraphicsContext getCtx() {
		return ctx;
	}

	public String getTitle() {
		return title;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
