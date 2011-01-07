import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

class Picture {
    static BufferedImage convolve(BufferedImage src, Kernel kernel) {
        return new ConvolveOp(kernel).filter(src, null);
    }
    static BufferedImage simpleBlur(BufferedImage src) {
        float[] matrix = new float[] {
            0.0f, 1.0f/6, 0.0f,
            1.0f/6, 1.0f/3, 1.0f/6,
            0.0f, 1.0f/6, 0.0f,
        };
        Kernel kernel = new Kernel(3, 3, matrix);
        return convolve(src, kernel);
    }
    static BufferedImage gaussianBlur(BufferedImage src) {
        float[] matrix = new float[] {
            2.0f/159, 4.0f/159, 5.0f/159, 4.0f/159, 2.0f/159,
            4.0f/159, 9.0f/159, 12.0f/159, 9.0f/159, 4.0f/159,
            5.0f/159, 12.0f/159, 15.0f/159, 12.0f/159, 5.0f/159,
            4.0f/159, 9.0f/159, 12.0f/159, 9.0f/159, 4.0f/159,
            2.0f/159, 4.0f/159, 5.0f/159, 4.0f/159, 2.0f/159,
        };
        Kernel kernel = new Kernel(5, 5, matrix);
        return convolve(src, kernel);
    }
    static BufferedImage sobelX(BufferedImage src) {
        float[] matrix = new float[] {
            -1.0f, 0.0f, 1.0f,
            -2.0f, 0.0f, 2.0f,
            -1.0f, 0.0f, 1.0f,
        };
        Kernel kernel = new Kernel(3, 3, matrix);
        return convolve(src, kernel);
    }
    static BufferedImage sobelY(BufferedImage src) {
        float[] matrix = new float[] {
            1.0f, 2.0f, 1.0f,
            0.0f, 0.0f, 0.0f,
            -1.0f, -2.0f, -1.0f,            
        };
        Kernel kernel = new Kernel(3, 3, matrix);
        return convolve(src, kernel);
    }
    static BufferedImage addMagnitudes(BufferedImage img1, BufferedImage img2) {
        BufferedImage dst = new BufferedImage(img1.getWidth(), img1.getHeight(), img1.getType());
        for (int y = 0; y < img1.getHeight(); ++y) {
            for (int x = 0; x < img1.getWidth(); ++x) {
                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);
                int r1 = (rgb1 & 0x00FF0000) >> 16;
                int r2 = (rgb2 & 0x00FF0000) >> 16;
                int r = (int)(Math.sqrt(r1*r1 + r2*r2)/Math.sqrt(2.0));
                if (r > 255) r = 255;
                if (r < 0) r = 0;
                int g1 = (rgb1 & 0x0000FF00) >> 8;
                int g2 = (rgb2 & 0x0000FF00) >> 8;
                int g = (int)(Math.sqrt(g1*g1 + g2*g2)/Math.sqrt(2.0));
                if (g > 255) g = 255;
                if (g < 0) g = 0;
                int b1 = rgb1 & 0x000000FF;
                int b2 = rgb2 & 0x000000FF;
                int b = (int)(Math.sqrt(b1*b1 + b2*b2)/Math.sqrt(2.0));
                if (b > 255) b = 255;
                if (b < 0) b = 0;
                int rgb = b + (g << 8) + (r << 16);
                dst.setRGB(x, y, rgb);
            }
        }
        return dst;
    }
    public static void main(String[] args) throws Exception {
    	Robot robot = new Robot();
        BufferedImage oimg = robot.cam.capture();
        //BufferedImage img = maslab.camera.ImageUtil.convertImage(oimg, BufferedImage.TYPE_INT_RGB);
        BufferedImage img = new BufferedImage(oimg.getWidth(), oimg.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < oimg.getHeight(); ++y) {
            for (int x = 0; x < oimg.getWidth(); ++x) {
                img.setRGB(x, y, oimg.getRGB(x, y));
            }
        }
        BufferedImage blurred = gaussianBlur(img);
        ImageIO.write(blurred, "bmp", new File("gaussian.bmp"));
        BufferedImage xe = sobelX(blurred);
        ImageIO.write(xe, "bmp", new File("sobelx.bmp"));
        BufferedImage ye = sobelY(blurred);
        ImageIO.write(ye, "bmp", new File("sobely.bmp"));
        BufferedImage edges = addMagnitudes(xe, ye);
        BufferedImage dst = edges;
        ImageIO.write(dst, "bmp", new File("Cedges.bmp"));
        
     // Create the ImageTutorial object, and all that that implies
    	ImageProcessing it = null; //WTF initialize to null to please the compiler
    	try {
    	    it = new ImageProcessing(oimg);
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
    	
    	// Write the image back to disk
    	try {
    	    it.writeImage("capture3result.png");
    	}
    	catch(IOException ioe) {
    	    System.out.println("Failed to write the image to disk");
    	    ioe.printStackTrace();
    	    System.exit(1);
    	}
    }
}

