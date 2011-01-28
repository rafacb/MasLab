

//Import the necessary libraries
import javax.imageio.*;

import maslab.camera.ImageUtil;

import java.io.*;
import java.awt.*;
import java.awt.image.*;

public class Image2 {
	// 0.0635 cm = ball size
	// Constants
	//
	// thresholds
	
	//ORIGINALES
	/**private static final int RED_HUE_MAX = 7; //WTF Why is RED_HUE_MAX < RED_HUE_MIN?
	private static final int RED_HUE_MIN = 250; // Because hue wraps around at red
												// 220 = -35 (mod 256)
	private static final int RED_SAT_MAX = 256;
	private static final int RED_SAT_MIN = 10;
	
	private static final int RED_VAL_MAX = 256; 
	private static final int RED_VAL_MIN = 0;  
	**/
	
	//CAPTURENOS
	/**private static final int RED_HUE_MAX = 7; //WTF Why is RED_HUE_MAX < RED_HUE_MIN?
	private static final int RED_HUE_MIN = 220; // Because hue wraps around at red
												// 220 = -35 (mod 256)
	private static final int RED_SAT_MAX = 150;
	private static final int RED_SAT_MIN = 77;
		
	private static final int RED_VAL_MAX = 220; //WTF This is almost the whole range.
	private static final int RED_VAL_MIN = 64;  //Is it even worth the test?
												// Remove it and find out.
	
	private static final int YELLOW_HUE_MAX = 70; 
	private static final int YELLOW_HUE_MIN = 35; 
	
	private static final int BLUE_HUE_MAX = 185; 
	private static final int BLUE_HUE_MIN = 150; 
	
	private static final int GREEN_HUE_MAX = 130; 
	private static final int GREEN_HUE_MIN = 51; 
	**/
	
	//26-100
	private static final int RED_HUE_MAX = 7; //WTF Why is RED_HUE_MAX < RED_HUE_MIN?
	private static final int RED_HUE_MIN = 220; // Because hue wraps around at red
												// 220 = -35 (mod 256)
	private static final int RED_SAT_MAX = 256;
	private static final int RED_SAT_MIN = 100;
		
	private static final int RED_VAL_MAX = 256; 
	private static final int RED_VAL_MIN = 104; 
	
	private static final int WHITE_HUE_MAX = 256; //WTF Why is RED_HUE_MAX < RED_HUE_MIN?
	private static final int WHITE_HUE_MIN = 0; // Because hue wraps around at red
												// 220 = -35 (mod 256)
	private static final int WHITE_SAT_MAX = 5;
	private static final int WHITE_SAT_MIN = 0;
		
	private static final int WHITE_VAL_MAX = 256; 
	private static final int WHITE_VAL_MIN = 250; 
	
	private static final int YELLOW_HUE_MAX = 70; 
	private static final int YELLOW_HUE_MIN = 30; 
	
	private static final int YELLOW_SAT_MAX = 256;
	private static final int YELLOW_SAT_MIN = 100;
		
	private static final int YELLOW_VAL_MAX = 255; 
	private static final int YELLOW_VAL_MIN = 104;  
	
	private static final int BLUE_HUE_MAX = 185; 
	private static final int BLUE_HUE_MIN = 150;
	
	private static final int BLUE_SAT_MAX = 256;
	private static final int BLUE_SAT_MIN = 105;
		
	private static final int BLUE_VAL_MAX = 256; 
	private static final int BLUE_VAL_MIN = 114;  
	
	private static final int GREEN_HUE_MAX = 130; 
	private static final int GREEN_HUE_MIN = 60; 
	
	private static final int GREEN_SAT_MAX = 256;
	private static final int GREEN_SAT_MIN = 75;
		
	private static final int GREEN_VAL_MAX = 256; 
	private static final int GREEN_VAL_MIN = 94; 
	
	private static final int BLACK_HUE_MAX = 256; 
	private static final int BLACK_HUE_MIN = 0; 
	
	private static final int BLACK_SAT_MAX = 0;
	private static final int BLACK_SAT_MIN = 0;
		
	private static final int BLACK_VAL_MAX = 0; 
	private static final int BLACK_VAL_MIN = 0; 
	
	// Member variables
	//
	// image state (initialized by constructor)
	public BufferedImage im;
	public int width, height;
	private int importantStuff = 0;
	// red blob statistics //#*$@ Are there better things to
	public int area = 0, green_area = 0; // which to initialize these? Yes.
	private int x_green = 0, y_green = 0, x_position = 0, y_position = 0; // Do I care right now? No.
	private int x_min = 0, x_max = 0, y_min = 0, y_max = 0;
	private int xgreen_min = 0, xgreen_max = 0, ygreen_min = 0, ygreen_max = 0;
	
	//goal variables
	public int goal_area = 0, black_area = 0, yellow_area = 0, blue_area = 0, white_area = 0; // which to initialize these? Yes.
	public int x_goal = 0, y_goal = 0; // Do I care right now? No.
	private int x_goal_min = 0, x_goal_max = 0, y_goal_min = 0, y_goal_max = 0;
	
	//Position of ball
	public int[] pos = new int[4];

	//
	// Member functions
	//
	/**
	 * Constructs a new ImageTutorial object, reads the specified image
	 * file from disk, and converts its pixel values to HSV color
	 * space
	 *
	 * @param filename the name of the file to read
	 * @throws IOException if the file cannot be opened or if the read
	 * fails (i.e. the file was not in a known format)
	 */
	public Image2(String filename) throws IOException {
		// Create a File object bound to our filename
		File f = new File(filename);
		// read the image into a BufferedImage
		im = ImageIO.read(f); //IMHO Yay for Java's convenient runtime library
		// Use Maslab's nifty utility function to do most of the work
		im = ImageUtil.rgbToHsv(im);
		width = im.getWidth(); //BTW these won't change, so cache them here.
		height = im.getHeight(); // this will save some clutter later
	}
	
	
	/**
	 * Constructor that accepts Buffered Image object as argument.
	 * @param bi
	 */
	public Image2(BufferedImage bi){
		im = bi;
		im = ImageUtil.rgbToHsv(im);
		width = im.getWidth(); //BTW these won't change, so cache them here.
		height = im.getHeight(); // this will save some clutter later
	}
	
	
	/**
	 * Write the image out as a PNG file
	 *
	 * @param filename the name of the file to write
	 * @throws IOException if the file IO fails
	 */
	public void writeImage(String filename) throws IOException {
		File f = new File(filename);
		if (filename.endsWith("png")){
			ImageIO.write(im, "png", f);
		}else if (filename.endsWith("jpg")){
			ImageIO.write(im, "jpg", f);
		}
	}
	
	public void findBoundaries(){
		int blueCount = 0;
		for(int y = 0; y < height; y++) {
			if (blueCount > width/9){
				System.out.println("blueCount = "+blueCount);
				importantStuff = y-1;
				break;
			}else{
				blueCount = 0;
			}
			for(int x = 0; x < width; x++) {
				int pixel = im.getRGB(x,y);
				if(isBlue(pixel)) {
					blueCount++;
				}
			}
		}
	}
	
	public void renderStatistics2(String color) {
		// make sure we've computed statistics
		if(area == 0 && x_min == 0) //WTF Why is this test sufficient? Think about it.
			throw new IllegalStateException("renderStatistics() called before find_red_blob()");
		//Get a graphics context so we can draw on im
		Graphics2D g = im.createGraphics();
		
		//iterate through the image
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				// for each pixel we think is red, color it white
				if (y < importantStuff){
					g.setColor(Color.black);
					g.drawLine(x,y,x,y);
				}else if(isRed(im.getRGB(x,y))) {
					g.setColor(Color.red);
					g.drawLine(x,y,x,y);
				}else if (isYellow(im.getRGB(x, y))){
					g.setColor(Color.yellow);
					g.drawLine(x,y,x,y);
				}else if ((isBlue(im.getRGB(x, y)))){
					g.setColor(Color.blue);
					g.drawLine(x,y,x,y);
				}else if (isGreen(im.getRGB(x, y))){
					g.setColor(Color.green);
					g.drawLine(x, y, x, y);
				}else if (isBlack(im.getRGB(x, y))){
					g.setColor(Color.black);
					g.drawLine(x, y, x, y);
				}else if (isWhite(im.getRGB(x, y))){
					g.setColor(Color.white);
					g.drawLine(x, y, x, y);
				}//else{
					//Let it be its true color.
					//g.setColor(Color.pink);
					//g.drawLine(x,y,x,y);
				//}
			}
		}
		//now for the bounding box
		g.setColor(Color.pink);
		g.drawRect(x_min, y_min, x_max-x_min, y_max-y_min);
		g.drawRect(xgreen_min, ygreen_min, xgreen_max-xgreen_min, ygreen_max-ygreen_min);
		//WTF Note that drawRect() takes x, y, width, height;
		//now for the center-of-mass
		
		//For red balls
		g.setColor(Color.green);
	
		g.drawLine(x_position-10, y_position-10,
				x_position+10, y_position+10);
		g.drawLine(x_position-10, y_position+10,
				x_position+10, y_position-10);
		
		//For green balls
		g.setColor(Color.red);
		
		g.drawLine(x_green-10, y_green-10,
				x_green+10, y_green+10);
		g.drawLine(x_green-10, y_green+10,
				x_green+10, y_green-10);
		
		//Draw the X on the goal
		if (isGoal()){
			g.drawLine(x_goal-10, y_goal-10,
					x_goal+10, y_goal+10);
			g.drawLine(x_goal-10, y_goal+10,
					x_goal+10, y_goal-10);
		}
		
		pos[0] = x_position;
		pos[1] = y_position;

		pos[2] = x_green;
		pos[3] = y_green;
	}
	
	
	/**
	 * Determines if the given hsv color is red
	 *
	 * @param hsv the color in HSV packed format
	 * @return true if the color is red, false otherwise
	 */
	public static boolean isRed(int hsv) {
		int val = hsv & 0xFF; 
		int sat = (hsv >> 8) & 0xFF;
		int hue = (hsv >> 16) & 0xFF;
		return (RED_HUE_MIN <= hue || hue <= RED_HUE_MAX) &&
		(RED_SAT_MIN <= sat && sat <= RED_SAT_MAX) &&
		(RED_VAL_MIN <= val && val <= RED_VAL_MAX);
	}
	public static boolean isWhite(int hsv) {
		int val = hsv & 0xFF; 
		int sat = (hsv >> 8) & 0xFF;
		int hue = (hsv >> 16) & 0xFF;
		return (WHITE_HUE_MIN <= hue && hue <= WHITE_HUE_MAX) &&
		(WHITE_SAT_MIN <= sat && sat <= WHITE_SAT_MAX) &&
		(WHITE_VAL_MIN <= val && val <= WHITE_VAL_MAX);
	}
	/**
	 * Determines if the given hsv color is yellow
	 *
	 * @param hsv the color in HSV packed format
	 * @return true if the color is yellow, false otherwise
	 */
	public static boolean isYellow(int hsv) {
		int val = hsv & 0xFF; 
		int sat = (hsv >> 8) & 0xFF;
		int hue = (hsv >> 16) & 0xFF;
		return (YELLOW_HUE_MIN <= hue && hue <= YELLOW_HUE_MAX) &&
		(YELLOW_SAT_MIN <= sat && sat <= YELLOW_SAT_MAX) &&
		(YELLOW_VAL_MIN <= val && val <= YELLOW_VAL_MAX);
		//(100 <= sat && sat <= 200) &&
		//(94 <= val && val <= 220);
	}
	public static boolean isBlue(int hsv) {
		int val = hsv & 0xFF; 
		int sat = (hsv >> 8) & 0xFF;
		int hue = (hsv >> 16) & 0xFF;
		return (BLUE_HUE_MIN <= hue && hue <= BLUE_HUE_MAX) &&
		(BLUE_SAT_MIN <= sat && sat <= BLUE_SAT_MAX) &&
		(BLUE_VAL_MIN <= val && val <= BLUE_VAL_MAX);
		//(77 <= sat && sat <= 150) &&
		//(64 <= val && val <= 220);
	}
	public static boolean isBlack(int hsv) {
		int val = hsv & 0xFF; 
		int sat = (hsv >> 8) & 0xFF;
		int hue = (hsv >> 16) & 0xFF;
		return (BLACK_HUE_MIN <= hue && hue <= BLACK_HUE_MAX) &&
		(BLACK_SAT_MIN <= sat && sat <= BLACK_SAT_MAX) &&
		(BLACK_VAL_MIN <= val && val <= BLACK_VAL_MAX);
		//(90 <= sat && sat <= 250) &&
		//(64 <= val && val <= 200);
	}
	public static boolean isGreen(int hsv) {
		int val = hsv & 0xFF; 
		int sat = (hsv >> 8) & 0xFF;
		int hue = (hsv >> 16) & 0xFF;
		return (GREEN_HUE_MIN <= hue && hue <= GREEN_HUE_MAX) &&
		(GREEN_SAT_MIN <= sat && sat <= GREEN_SAT_MAX) &&
		(GREEN_VAL_MIN <= val && val <= GREEN_VAL_MAX);
		//(90 <= sat && sat <= 250) &&
		//(64 <= val && val <= 200);
	}
	
	/**
	 * Scans the image for red pixels and computes statistics about
	 * them
	 *
	 * @modifies all statistics member variables
	 */
	public void find_green_blob() {
		// initialize statistics
		area = 0;
		x_position = y_position = 0;
		x_min = y_min = Integer.MAX_VALUE;
		//WTF Why MAX_VALUE? So that it will always
		// update on the first pixel. We could also
		// have used width and height.
		x_max = y_max = 0;
		// scan through every pixel in the image
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				int pixel = im.getRGB(x,y);
				if(isGreen(pixel)) {
					area++;
					x_position += x;
					y_position += y;
					x_min = (x < x_min) ? x : x_min; //IMHO Yay for the conditional operator
					y_min = (y < y_min) ? y : y_min; //WTF Conditional operator?

					x_max = (x > x_max) ? x : x_max; // a ? b : c <==> if a then b else c
					y_max = (y > y_max) ? y : y_max;
				}
			}
		}
		// finish updating statistics
		if(area != 0) { //there may not have been any red
			x_position /= area;
			y_position /= area;
		}
		//System.out.println("area =  "+area);
	}
	
	/**
	 * Scans the image for red pixels and computes statistics about
	 * them
	 *
	 * @modifies all statistics member variables
	 */
	public void find_red_blob() {
		// initialize statistics
		area = 0;
		x_position = y_position = 0;
		x_min = y_min = Integer.MAX_VALUE;
		//WTF Why MAX_VALUE? So that it will always
		// update on the first pixel. We could also
		// have used width and height.
		x_max = y_max = 0;
		// scan through every pixel in the image
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				int pixel = im.getRGB(x,y);
				if(isRed(pixel)) {
					area++;
					x_position += x;
					y_position += y;
					x_min = (x < x_min) ? x : x_min; //IMHO Yay for the conditional operator
					y_min = (y < y_min) ? y : y_min; //WTF Conditional operator?

					x_max = (x > x_max) ? x : x_max; // a ? b : c <==> if a then b else c
					y_max = (y > y_max) ? y : y_max;
				}
			}
		}
		// finish updating statistics
		if(area != 0) { //there may not have been any red
			x_position /= area;
			y_position /= area;
		}
		//System.out.println("area =  "+area);
	}
	
	/**
	 * Scans the image for red pixels and computes statistics about
	 * them
	 * @return 
	 *
	 * @modifies all statistics member variables
	 */
	public void find_objects(String color) {
		// initialize statistics
		goal_area = 0;
		x_goal = y_goal = 0;
		x_goal_min = y_goal_min = Integer.MAX_VALUE;
		// initialize statistics
		area = 0;
		x_position = y_position = 0;
		x_min = y_min = Integer.MAX_VALUE;
		//WTF Why MAX_VALUE? So that it will always
		// update on the first pixel. We could also
		// have used width and height.
		x_goal_max = y_goal_max = 0;
		x_max = y_max = 0;
		// scan through every pixel in the image
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				int pixel = im.getRGB(x,y);
				if(isYellow(pixel)) {
					goal_area++;
					yellow_area++;
					x_goal += x;
					y_goal += y;
					x_goal_min = (x < x_goal_min) ? x : x_goal_min; //IMHO Yay for the conditional operator
					y_goal_min = (y < y_goal_min) ? y : y_goal_min; //WTF Conditional operator?

					x_goal_max = (x > x_goal_max) ? x : x_goal_max; // a ? b : c <==> if a then b else c
					y_goal_max = (y > y_goal_max) ? y : y_goal_max;
				}else if (isBlack(pixel)){
					goal_area++;
					black_area++;
					x_goal += x;
					y_goal += y;
					x_goal_min = (x < x_goal_min) ? x : x_goal_min; //IMHO Yay for the conditional operator
					y_goal_min = (y < y_goal_min) ? y : y_goal_min; //WTF Conditional operator?

					x_goal_max = (x > x_goal_max) ? x : x_goal_max; // a ? b : c <==> if a then b else c
					y_goal_max = (y > y_goal_max) ? y : y_goal_max;
				}else if (isBlue(pixel)){
					blue_area++;
				}else if (color == "red" && isRed(pixel)){
					area++;
					x_position += x;
					y_position += y;
					x_min = (x < x_min) ? x : x_min; //IMHO Yay for the conditional operator
					y_min = (y < y_min) ? y : y_min; //WTF Conditional operator?

					x_max = (x > x_max) ? x : x_max; // a ? b : c <==> if a then b else c
					y_max = (y > y_max) ? y : y_max;
				}else if (color == "green" && isGreen(pixel)){
					area++;
					x_position += x;
					y_position += y;
					x_min = (x < x_min) ? x : x_min; //IMHO Yay for the conditional operator
					y_min = (y < y_min) ? y : y_min; //WTF Conditional operator?

					x_max = (x > x_max) ? x : x_max; // a ? b : c <==> if a then b else c
					y_max = (y > y_max) ? y : y_max;
				}
			}
		}
		// finish updating statistics
		if(goal_area != 0) { //there may not have been any goals
			x_goal /= goal_area;
			y_goal /= goal_area;
		}
		// finish updating statistics
		if(area != 0) { //there may not have been any red
			x_position /= area;
			y_position /= area;
		}
	}
	
	
	public void find_objects2(String color) {
		// initialize statistics
		goal_area = 0;
		x_goal = y_goal = 0;
		x_goal_min = y_goal_min = Integer.MAX_VALUE;
		// initialize statistics
		area = 0;
		green_area = 0;
		x_position = y_position = 0;
		x_green = y_green = 0;
		x_min = y_min = Integer.MAX_VALUE;
		xgreen_min = ygreen_min = Integer.MAX_VALUE;
		//WTF Why MAX_VALUE? So that it will always
		// update on the first pixel. We could also
		// have used width and height.
		x_goal_max = y_goal_max = 0;
		x_max = y_max = 0;
		xgreen_max = ygreen_max = 0;
		// scan through every pixel in the image
		for(int y = importantStuff; y < height; y++) {
			for(int x = 0; x < width; x++) {
				int pixel = im.getRGB(x,y);
				if(isYellow(pixel)) {
					goal_area++;
					yellow_area++;
					x_goal += x;
					y_goal += y;
					x_goal_min = (x < x_goal_min) ? x : x_goal_min; //IMHO Yay for the conditional operator
					y_goal_min = (y < y_goal_min) ? y : y_goal_min; //WTF Conditional operator?

					x_goal_max = (x > x_goal_max) ? x : x_goal_max; // a ? b : c <==> if a then b else c
					y_goal_max = (y > y_goal_max) ? y : y_goal_max;
				}else if (isBlue(pixel)){
					blue_area++;
				}else if (isBlack(pixel)){
					goal_area++;
					black_area++;
					x_goal += x;
					y_goal += y;
					x_goal_min = (x < x_goal_min) ? x : x_goal_min; //IMHO Yay for the conditional operator
					y_goal_min = (y < y_goal_min) ? y : y_goal_min; //WTF Conditional operator?

					x_goal_max = (x > x_goal_max) ? x : x_goal_max; // a ? b : c <==> if a then b else c
					y_goal_max = (y > y_goal_max) ? y : y_goal_max;
				}else if (isRed(pixel)){
					area++;
					x_position += x;
					y_position += y;
					x_min = (x < x_min) ? x : x_min; //IMHO Yay for the conditional operator
					y_min = (y < y_min) ? y : y_min; //WTF Conditional operator?

					x_max = (x > x_max) ? x : x_max; // a ? b : c <==> if a then b else c
					y_max = (y > y_max) ? y : y_max;
				}else if (isGreen(pixel)){
					green_area++;
					x_green += x;
					y_green += y;
					xgreen_min = (x < xgreen_min) ? x : xgreen_min; //IMHO Yay for the conditional operator
					ygreen_min = (y < ygreen_min) ? y : ygreen_min; //WTF Conditional operator?

					xgreen_max = (x > xgreen_max) ? x : xgreen_max; // a ? b : c <==> if a then b else c
					ygreen_max = (y > ygreen_max) ? y : ygreen_max;
				}else if (isWhite(pixel)){
					white_area++;
				}
			}
		}
		// finish updating statistics
		if(goal_area != 0) { //there may not have been any goals
			x_goal /= goal_area;
			y_goal /= goal_area;
		}
		// finish updating statistics
		if(area != 0) { //there may not have been any red
			x_position /= area;
			y_position /= area;
		}
		if (green_area != 0){
			x_green /= green_area;
			y_green /= green_area;
		}
	}
	
	
	public void find_goal() {
		// initialize statistics
		goal_area = 0;
		x_goal = y_goal = 0;
		x_goal_min = y_goal_min = Integer.MAX_VALUE;
		//WTF Why MAX_VALUE? So that it will always
		// update on the first pixel. We could also
		// have used width and height.
		x_goal_max = y_goal_max = 0;
		// scan through every pixel in the image
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				int pixel = im.getRGB(x,y);
				if(isYellow(pixel) || isBlack(pixel)) {
					goal_area++;
					x_goal += x;
					y_goal += y;
					x_goal_min = (x < x_goal_min) ? x : x_goal_min; //IMHO Yay for the conditional operator
					y_goal_min = (y < y_goal_min) ? y : y_goal_min; //WTF Conditional operator?

					x_goal_max = (x > x_goal_max) ? x : x_goal_max; // a ? b : c <==> if a then b else c
					y_goal_max = (y > y_goal_max) ? y : y_goal_max;
				}
			}
		}
		// finish updating statistics
		if(goal_area != 0) { //there may not have been any goals
			x_goal /= goal_area;
			y_goal /= goal_area;
		}
	}
	
	/**
	 * Check if there is a ball in the image.
	 * @return
	 */
	public boolean isBall(){
		return area > 300 || green_area > 300;
	}
	
	/**
	 * Check if we are indeed looking at a goal.
	 * @return
	 */
	public boolean isGoal(){
		boolean goal = false;
		//Check some pixels!
		if ( x_goal >= 1 && y_goal >= 1 && x_goal < width && y_goal < height){
			if (!isYellow(im.getRGB(x_goal, y_goal)) && !isYellow(im.getRGB(x_goal+1, y_goal)) &&
					!isYellow(im.getRGB(x_goal-1, y_goal)) && !isYellow(im.getRGB(x_goal, y_goal+1)) &&
					!isYellow(im.getRGB(x_goal, y_goal+1)) && !isYellow(im.getRGB(x_goal-1, y_goal-1)) &&
					!isYellow(im.getRGB(x_goal-1, y_goal+1)) && !isYellow(im.getRGB(x_goal+1, y_goal+1)) &&
					!isYellow(im.getRGB(x_goal+1, y_goal+1))){
				goal = true;
			}
		}else{
			goal = !isYellow(im.getRGB(x_goal, y_goal));
		}
		System.out.println("black = "+black_area);
		return (!isWall());
	}
	
	public boolean isWall(){
		boolean goal = false;
		//Check some pixels!
		if ( x_goal >= 1 && y_goal >= 1 && x_goal < width && y_goal < height){
			if (isYellow(im.getRGB(x_goal, y_goal)) && isYellow(im.getRGB(x_goal+1, y_goal)) &&
					isYellow(im.getRGB(x_goal-1, y_goal)) && isYellow(im.getRGB(x_goal, y_goal+1)) &&
					isYellow(im.getRGB(x_goal, y_goal+1)) && isYellow(im.getRGB(x_goal-1, y_goal-1)) &&
					isYellow(im.getRGB(x_goal-1, y_goal+1)) && isYellow(im.getRGB(x_goal+1, y_goal+1)) &&
					isYellow(im.getRGB(x_goal+1, y_goal+1))){
				goal = true;
			}
		}else{
			goal = isYellow(im.getRGB(x_goal, y_goal));
		}return goal && black_area < 50;
	}
	
	/**
	 * Draws statistics on the image. Specifically, draws the bounding
	 * box in blue, the center-of-mass as a green X, and colors white
	 * (because it shows up well) each pixel thought to be
	 * red. Performs a second scan of the image. Not intended to be
	 * efficient. If you want to color in each red pixel every time,
	 * you should move that code into find_red_blob(). You must call
	 * find_red_blob before calling this function.
	 *
	 * @throws IllegalStateException of find_red_blob() has not
	 * previously been called
	 * @modifies im
	 */
	public void renderStatistics(String color) {
		// make sure we've computed statistics
		if(area == 0 && x_min == 0) //WTF Why is this test sufficient? Think about it.
			throw new IllegalStateException("renderStatistics() called before find_red_blob()");
		//Get a graphics context so we can draw on im
		Graphics2D g = im.createGraphics();
		
		//iterate through the image
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				// for each pixel we think is red, color it white
				if(isRed(im.getRGB(x,y))) {
					g.setColor(Color.red);
					g.drawLine(x,y,x,y);
				}else if (isYellow(im.getRGB(x, y))){
					g.setColor(Color.yellow);
					g.drawLine(x,y,x,y);
				}else if ((isBlue(im.getRGB(x, y)))){
					g.setColor(Color.blue);
					g.drawLine(x,y,x,y);
				}else if (isGreen(im.getRGB(x, y))){
					g.setColor(Color.green);
					g.drawLine(x, y, x, y);
				}else if (isBlack(im.getRGB(x, y))){
					g.setColor(Color.black);
					g.drawLine(x, y, x, y);
				}else if (isWhite(im.getRGB(x, y))){
					g.setColor(Color.white);
					g.drawLine(x, y, x, y);
				}else{
					//Let the colors flow.
					//g.setColor(Color.pink);
					//g.drawLine(x,y,x,y);
				}
			}
		}
		//now for the bounding box
		g.setColor(Color.pink);
		g.drawRect(x_min, y_min, x_max-x_min, y_max-y_min);
		//WTF Note that drawRect() takes x, y, width, height;
		//now for the center-of-mass
		if (color.equals("red")){
			g.setColor(Color.green);
		}else{
			g.setColor(Color.red);
		}
		
		g.drawLine(x_position-10, y_position-10,
				x_position+10, y_position+10);
		g.drawLine(x_position-10, y_position+10,
				x_position+10, y_position-10);
		
		//Draw the X on the goal
		g.drawLine(x_goal-10, y_goal-10,
				x_goal+10, y_goal+10);
		g.drawLine(x_goal-10, y_goal+10,
				x_goal+10, y_goal-10);
		
		
		pos[0] = x_position;
		pos[1] = y_position;
		
	}
	
	/**
	 * Sumarize the statistics as a string
	 *
	 * @return a String representation of the statistics
	 * @throws IllegalStateException if find_red_blob() has not
	 * prevoiusly been called
	 */
	public String statisticsToString() {

		//make sure we've got meaningful statistics
		if(area == 0 && x_min == 0)
			throw new IllegalStateException("statisticsToString() called before find_red_blob()");
		return "Area = "+area+"\n"+
		"Center-of-mass = ("+x_position+","+y_position+")\n"+
		"Bounding box = ("+x_min+","+y_min+"), ("+x_max+","+y_max+")\n"+
		"Aspect ratio = "+((float)(x_max-x_min+1))/(y_max-y_min+1)+"\n";
		//WTF Why the +1's in aspect ratio? Well, if x_min==x_max, width=1. So,...
		//WTF Why did I cast the numerator to float? To avoid integer division
	}
	
	
	
	public String checkDammValues(){
		//double val = 0;
		double vals = height * width;
		double result = 0;
		
		//double hue = 0;
		double sat = 0;
		
		
		Double max_val = null;
		Double min_val = null;
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				//val = im.getRGB(x,y) & 0xFF;
				sat = (im.getRGB(x,y) >> 8) & 0xFF;
				//hue = (im.getRGB(x,y) >> 16) & 0xFF;
				if (max_val == null || max_val < sat){
					max_val = sat;
					}
				if (min_val == null || min_val > sat){
					min_val = sat;
					}
				result += sat;
				}
			}System.out.println(result+" and "+vals);
			System.out.println("h = "+height+" w = "+width);
		return "The average value is: "+result/vals+"\nMin = "+min_val+"\nMax = "+max_val;
	}
	
	
	
	/**
	 * Demonstrates the functions of ImageTutorial
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// Create the ImageTutorial object, and all that that implies
		Image2 it = null; //WTF initialize to null to placate the compiler
		//for (int i =10; i < 18; i++){
			//it = new Image2("hello/capture"+i+".jpg");
		it = new Image2("hello/black.jpg");
		// Do the work
		//it.find_green_blob();
		it.findBoundaries();
		it.find_objects2("red");
		System.out.println(it.isGoal());
		//it.find_goal();
		//System.out.println("pic "+i+"goal??? "+it.isGoal()+" wall??? "+it.isWall());
		//System.out.println(it.isBall());
		//System.out.println("total = "+it.goal_area);
		//System.out.println("black = "+it.black_area);
		//System.out.println("yellow = "+it.yellow_area);
		// Show the work
		//it.renderStatistics("green");
		System.out.println("important stuff = "+it.importantStuff);
		it.renderStatistics2("red");
		it.writeImage("hello/black_result.jpg");
	}
}
