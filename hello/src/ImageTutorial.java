// Import the necessary libraries
import javax.imageio.*;

import maslab.camera.ImageUtil;

import java.io.*;
import java.awt.*;
import java.awt.image.*;
/**
 * ImageTutorial.java
 *
 * Demonstrates basic image processing on an image loaded from disk.
 * Abstracts the process of loading an image, computing statistics on
 * it, drawing on it, and writing it back to disk. If this code were
 * to be used in a real robot, it would probably be called MaslabImage
 * or something, and would have accessors for the varoius statistics.
 * The isRed() routine would also probably be placed in another class
 * (like MaslabColor, perhaps), along with other color routines.
 *
 * Created on December 1, 2004
 *
 * @author dlnelson & Maslab Staff
 */
// In mocking of the Instant Messenging community's colloquial
// abreviations, I have adopted the following comment convention:
//
// /** introduces a javadoc comment
//// introduces a comment about the algorithm, or about the
// layout of the code
// IMHO introduces an editorial comment
// BTW introduces a sidebar related tangentially to nearby code
// WTF introduces a comment about a "gotcha" in the code, or
// explains an implementation specific piece of code whose
// purpose may not be readily apparent
// #*$@ introduces a comment displaying existential angst
public class ImageTutorial {
	//
	// Constants
	//
	// thresholds
	//private static final int RED_HUE_MAX = 7; //WTF Why is RED_HUE_MAX < RED_HUE_MIN?
	//private static final int RED_HUE_MIN = 220; // Because hue wraps around at red
												// 220 = -35 (mod 256)
	//private static final int RED_SAT_MAX = 150;
	//private static final int RED_SAT_MIN = 77;
		
	//private static final int RED_VAL_MAX = 220; //WTF This is almost the whole range.
	//private static final int RED_VAL_MIN = 64;  //Is it even worth the test?
												// Remove it and find out.

	private static final int RED_HUE_MAX = 7; //WTF Why is RED_HUE_MAX < RED_HUE_MIN?
	private static final int RED_HUE_MIN = 250; // Because hue wraps around at red
												// 220 = -35 (mod 256)
	private static final int RED_SAT_MAX = 256;
	private static final int RED_SAT_MIN = 10;
	
	private static final int RED_VAL_MAX = 256; 
	private static final int RED_VAL_MIN = 0;  
	//
	// Member variables
	//
	// image state (initialized by constructor)
	public BufferedImage im;
	private int width, height;
	// red blob statistics //#*$@ Are there better things to
	private int area = 0; // which to initialize these? Yes.
	private int x_position = 0, y_position = 0; // Do I care right now? No.
	private int x_min = 0, x_max = 0, y_min = 0, y_max = 0;

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
	public ImageTutorial(String filename) throws IOException {
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
	public ImageTutorial(BufferedImage bi){
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
		ImageIO.write(im, "png", f);
	}
	/**
	 * Determines if the given hsv color is red
	 *
	 * @param hsv the color in HSV packed format
	 * @return true if the color is red, false otherwise
	 */
	public static boolean isRed(int hsv) {
		int val = hsv & 0xFF; //WTF What is this? It grabs the low 8 bits of hsv
		//BTW hsv is stored in packed integer form, meaning
		// that the 32 bits of the int are divided up as
		// follows: xxxxxxxxhhhhhhhhssssssssvvvvvvvv
		// | junk | hue | sat | val |
		// The & 0xFF bit performs a bitwise AND with the
		// mask 0xFF:
		// xxxxxxxxhhhhhhhhssssssssvvvvvvvv
		// & 00000000000000000000000011111111
		// --------------------------------------
		// val = 000000000000000000000000vvvvvvvv
		int sat = (hsv >> 8) & 0xFF;
		int hue = (hsv >> 16) & 0xFF;
		//WTF The >> 16 is a bitwise shift. It shifts
		// the hie portion of hsv into the lower
		// 8 bits so that we can perform the mask
		// as before.
		//WTF Would (hsv >> 16) by itself have worked?
		// Not necessarily. You might have thought
		// that the high-order bits would have been
		// filled with zeros after the shift. Not so!.
		// All numeric types in Java are *signed*. The
		// High order bits after a shift are filled
		// with copies of the original high-order bit.
		//#*$@ Well-behaved functions will have already
		// zeroed the high byte of hsv, but...
		//#*$@ Thank you Sun for only signed integers
		// (Collective fist shaking)
		//BTW Google for "2's complement" for the gory
		// details of signed arithmetic in binary
		return (RED_HUE_MIN <= hue || hue <= RED_HUE_MAX) &&
		(RED_SAT_MIN <= sat && sat <= RED_SAT_MAX) &&
		(RED_VAL_MIN <= val && val <= RED_VAL_MAX);
		//WTF Why || and not && for hue?
		// Because RED_HUE_MIN > RED_HUE_MAX
		//BTW You could avoid having to think about && or || once and for all
		// by writing a general hueDistance(int a, int b) function like this:
		// int absdiff = Math.abs(a-b);
		// return Math.min(absdiff, Math.abs(absdiff-256));
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
				//WTF Why for y, for x instead of the other
				// way around? Performance issue.
				//BTW Arrays in Java (and C, but not MATLAB)
				// are stored in row-major-order, meaning
				// that the right-most index changes the
				// fastest as we move through memory. When
				// we access a location in memory, the
				// values surrounding it get loaded into the
				// cache, and subsequent accesses to them is
				// much faster. So, once we've accessed a
				// row, the rest of it is probably in cache.
				// grab the pixel value for this location
				int pixel = im.getRGB(x,y);
				//BTW Is this really the most efficient away to scan the image? Probably not.
				// You can get access to a linear array of bytes in the form vhsvhsvhs...
				// representing the pixels in row-major order (bytes reversed) like this:
				// byte[] imData = ((DataBufferByte)im.getRaster().getDataBuffer()).getData();
				// This way, the hsv components are already separated for you, and you can
				// scan the array linearly as imData[3*(y*width+x)]
				//BTW Note that myData here is a reference into the actual image data. Changing
				// myData will change im.
				// if it's red, update statistics
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
	public void renderStatistics() {
		// make sure we've computed statistics
		if(area == 0 && x_min == 0) //WTF Why is this test sufficient? Think about it.
			throw new IllegalStateException("renderStatistics() called before find_red_blob()");
		//Get a graphics context so we can draw on im
		Graphics2D g = im.createGraphics();
		//first things we draw are white
		g.setColor(Color.white);
		//iterate through the image
		int count = 0;
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				// for each pixel we think is red, color it white
				if(isRed(im.getRGB(x,y))) {
					count++;
					g.drawLine(x,y,x,y); //WTF Yes, this is the best way
				}
			}
		}
		System.out.println("There are "+count+" number of red pixels!!!");
		//now for the bounding box
		g.setColor(Color.blue);
		g.drawRect(x_min, y_min, x_max-x_min, y_max-y_min);
		//WTF Note that drawRect() takes x, y, width, height;
		//now for the center-of-mass
		g.setColor(Color.green);
		g.drawLine(x_position-10, y_position-10,
				x_position+10, y_position+10);
		g.drawLine(x_position-10, y_position+10,
				x_position+10, y_position-10);
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
	 */
	public static void main(String[] args) {
		// Create the ImageTutorial object, and all that that implies
		ImageTutorial it = null; //WTF initialize to null to placate the compiler
		try {
			it = new ImageTutorial("hello/captureNos.png");
		}
		catch(IOException ioe) {
			System.out.println("Failed to create ImageTutorial object.");
			ioe.printStackTrace();
			System.exit(1);
		}
		// Do the work
		it.find_red_blob();
		// Show the work
		it.renderStatistics();
		// Print the statistics
		System.out.println(it.statisticsToString());
		System.out.println(it.checkDammValues());
		// Write the image back to disk
		try {
			it.writeImage("captureNosresult.png");
		}
		catch(IOException ioe) {
			System.out.println("Failed to write the image to disk");
			ioe.printStackTrace();
			System.exit(1);
		}
	}
}