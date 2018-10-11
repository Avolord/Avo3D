package main;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class console {
	
	public static int Day = LocalDateTime.now().getDayOfMonth();
	public static int Month = LocalDateTime.now().getMonthValue();
	public static int Year = LocalDateTime.now().getYear();
	
	private static String author = "AvoLord";
	private static String version = "0.0.3";
	
	public static void log(String x) {
		System.out.println(x);
	}
	public static void log(char x) {
		System.out.println(x);
	}
	public static void log(int x) {
		System.out.println(x);
	}
	public static void log(float x) {
		System.out.println(x);
	}
	public static void log(double x) {
		System.out.println(x);
	}
	public static void log(long x) {
		System.out.println(x);
	}
	public static void log(byte x) {
		System.out.println(x);
	}
	public static void log(boolean x) {
		System.out.println(x);
	}
	
	
	public static void log(String[] x) {
		System.out.println(Arrays.deepToString(x));
	}
	public static void log(char[] x) {
		System.out.println(Arrays.toString(x));
	}
	public static void log(int[] x) {
		System.out.println(Arrays.toString(x));
	}
	public static void log(float[] x) {
		System.out.println(Arrays.toString(x));
	}
	public static void log(double[] x) {
		System.out.println(Arrays.toString(x));
	}
	public static void log(long[] x) {
		System.out.println(Arrays.toString(x));
	}
	public static void log(byte[] x) {
		System.out.println(Arrays.toString(x));
	}
	public static void log(boolean[] x) {
		System.out.println(Arrays.toString(x));
	}
	
	
	public static void log(String[][] x) {
		for(String[] y : x)
		System.out.println(Arrays.deepToString(y));
	}
	public static void log(char[][] x) {
		for(char[] y : x)
		System.out.println(Arrays.toString(y));
	}
	public static void log(int[][] x) {
		for(int[] y : x)
		System.out.println(Arrays.toString(y));
	}
	public static void log(float[][] x) {
		for(float[] y : x)
		System.out.println(Arrays.toString(y));
	}
	public static void log(double[][] x) {
		for(double[] y : x)
		System.out.println(Arrays.toString(y));
	}
	public static void log(long[][] x) {
		for(long[] y : x)
		System.out.println(Arrays.toString(y));
	}
	public static void log(byte[][] x) {
		for(byte[] y : x)
		System.out.println(Arrays.toString(y));
	}
	public static void log(boolean[][] x) {
		for(boolean[] y : x)
		System.out.println(Arrays.toString(y));
	}
	
	
	public static void log(ArrayList<?> x) {
		x.forEach(y -> System.out.println(Arrays.deepToString((Object[]) y)));
	}
	public static void log(List<?> x) {
		x.forEach(y -> System.out.println(y));
	}


	
	public static void line(int width) {
		for(int i=0; i<width; i++) 
		System.out.println("----------------------------------------");
	}
	public static void line(int width, int length) {
		for(int i=0; i<width; i++) {
			System.out.println("");
			for(int j=0; j<length; j++)
				System.out.print("-");
		}
	}
	
	public static int getHours() {
		return LocalDateTime.now().getHour();
	}
	public static int getMinute() {
		return LocalDateTime.now().getMinute();
	}
	public static int getSecond() {
		return LocalDateTime.now().getSecond();
	}
	public static void Date() {
		String date = LocalDateTime.now().toString();
		System.out.println(date.replace("T"," "));
	}
	
	
	public static long timestamp() {
		return System.nanoTime();
	}
	public static void timestamp(long time) {
		long t2 = System.nanoTime();
		System.out.println("\n//--------------Timestamp--------------\\\\");
		System.out.println(
				"	Nanoseconds: "+(t2-time)+"\n"+
				"	Microseconds: "+(t2-time)/1E3+"\n"+
				"	Milliseconds: "+(t2-time)/1E6+"\n"+
				"	Seconds: "+(t2-time)/1E9+"\n"+
				"	Minutes: "+(t2-time)/1E9/60+"\n"+
				"	Hours: "+(t2-time)/1E9/3600
				);
		System.out.println("\\\\-------------------------------------//");
	}
	public static void timestamp_diff(double diff) {
		System.out.println("\n//--------------Timestamp--------------\\\\");
		System.out.println(
				"	Nanoseconds: "+diff+"\n"+
				"	Microseconds: "+diff/1E3+"\n"+
				"	Milliseconds: "+diff/1E6+"\n"+
				"	Seconds: "+diff/1E9+"\n"+
				"	Minutes: "+diff/1E9/60+"\n"+
				"	Hours: "+diff/1E9/3600
				);
		System.out.println("\\\\-------------------------------------//");
	}
	
	
	public static void warmup() {
		int[] warm = new int[100];
		for(int i=0; i<10000; i++) {
			warm[i%100] = i*i;
		}
		warm = null;
		System.out.println("\nSystem has been warmed up! - [10000 calculations]");
	}
	public static void warmup(int level) {
		if(level > 5) {
			System.out.println("\n//---------------Warning---------------\\\\");
			System.out.println("This process would take quite a while..."
					+ "\nIf you really want to use such a high amount"
					+ "\nadd a second parameter 'true' to warmup()."
					+ "\n	Normal warmup will be used!");
			System.out.println("\\\\-------------------------------------//");
			warmup();
			return;
		}
		int amount = (int) Math.pow(10, level);
		int[] warm = new int[100];
		for(int i=0; i<amount; i++) {
			warm[i%100] = i*i;
		}
		warm = null;
		System.out.println("System has been warmed up! - ["+amount+" calculations]");
	}
	public static void warmup(int level, boolean overload) {
		if(level > 5 && !overload) {
			System.out.println("\n//---------------Warning---------------\\\\");
			System.out.println("This process would take quite a while..."
					+ "\n   If you really want to use such a"
					+ "\n  high amount set 'overload' to 'true'!"
					+ "\n	Normal warmup will be used!");
			System.out.println("\\\\-------------------------------------//");
			warmup();
			return;
		}
		int amount = (int) Math.pow(10, level);
		int[] warm = new int[100];
		for(int i=0; i<amount; i++) {
			warm[i%100] = i*i;
		}
		warm = null;
		System.out.println("System has been warmed up! - ["+amount+" calculations]");
	}
	
	
	private static void credits() {
		System.out.println("\n//---------------Credits---------------\\\\");
		System.out.println(
				  "	      console.java"
				+ "\n	     Author: "+author
				+ "\n	     Version: "+version);
		System.out.println("\\\\-------------------------------------//");
	}
	public static void showCredits() {
		credits();
	}
	
}
