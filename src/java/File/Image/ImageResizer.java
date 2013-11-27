/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package File.Image;

import File.FileImpl;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;

/**
 *
 * @author matej_000
 */
public class ImageResizer {
    
    private ArrayList<FileImpl> files;
    private int width = -1;
    private int height = -1;
    
    public ImageResizer(ArrayList<FileImpl> files, int width, int height){
        this.files = files;
        this.width = width;
        this.height = height;
    }
    
    public ImageResizer(int width, int height){
        this.width = width;
        this.height = height;
    }
    
    public void resizeImagesWithTempThubnails() throws ThumbnailException{
        try {
            for(int i = 0; i < files.size(); i++){
                String originalPath = files.get(i).getPath();
                String extension = originalPath.substring(originalPath.lastIndexOf("."), originalPath.length());
                String newPath = originalPath.substring(0,originalPath.lastIndexOf(".")) + "_THUMB" + extension;
                
                BufferedImage img = ImageIO.read(new File(originalPath));
                BufferedImage scaledImg = Scalr.resize(img, Mode.AUTOMATIC, width, height);
                File destFile = new File(newPath);
                ImageIO.write(scaledImg, "jpg", destFile);
                //System.out.println("Done resizing image: " + newPath + " " + newPath);
            }
            
        } catch (Exception ex) {
            throw new ThumbnailException();
        }
    }
    
    public void resizeImageWithTempThubnails(String pathToImage) throws ThumbnailException{
        try {
                String originalPath = pathToImage;
                String extension = originalPath.substring(originalPath.lastIndexOf("."), originalPath.length());
                String newPath = originalPath.substring(0,originalPath.lastIndexOf(".")) + "_THUMB" + extension;
                
                BufferedImage img = ImageIO.read(new File(originalPath));
                BufferedImage scaledImg = Scalr.resize(img, Mode.AUTOMATIC, width, height);
                File destFile = new File(newPath);
                ImageIO.write(scaledImg, "jpg", destFile);
                //System.out.println("Done resizing image: " + newPath + " " + newPath);
            
        } catch (Exception ex) {
            throw new ThumbnailException();
        }
    }

    /**
     * @return the files
     */
    public ArrayList<FileImpl> getFiles() {
        return files;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }
}
