/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Parser.Utilities;

import File.TrackPointImpl;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author matej_000
 */
public class TrackDetailResolver {
    
    private ArrayList<TrackPointImpl> track = null;
    private String trackType = null;
    private ArrayList<String> internetElevation = null;
    private boolean isDrawed = true;
    
    private final double d2r = Math.PI / 180;
    
    public TrackDetailResolver(ArrayList<TrackPointImpl> track, String trackType, ArrayList<String> internetElevation, boolean isDrawed){
        this.track = track;
        this.trackType = trackType;
        this.internetElevation = internetElevation;
        this.isDrawed = isDrawed;
    }
    
    public double resolveTrackLength(){
        double trackLength = 0;
        if(track.size() > 0){
            for(int i = 0; i < track.size() - 1; i++){
                    double dlong = (track.get(i + 1).getLongitude() - track.get(i).getLongitude()) * d2r;
                    double dlat = (track.get(i + 1).getLatitude() - track.get(i).getLatitude()) * d2r;
                    double a = pow(sin(dlat / 2.0), 2) + cos(track.get(i).getLatitude() * d2r) * cos(track.get(i + 1).getLatitude() * d2r) * pow(sin(dlong / 2.0), 2);
                    double c = 2 * atan2(sqrt(a), sqrt(1 - a));
                    double d = 6367 * c;
                    
                    trackLength = trackLength + d;
            }
        }
        return trackLength;
    }
    
    private ArrayList<Double> resolveTrackLengths(){
        ArrayList<Double> trackLengths = new ArrayList<Double>();
        trackLengths.add(0.0);
        
        if(track.size() > 0){
            for(int i = 0; i < track.size() - 1; i++){
                    double dlong = (track.get(i + 1).getLongitude() - track.get(i).getLongitude()) * d2r;
                    double dlat = (track.get(i + 1).getLatitude() - track.get(i).getLatitude()) * d2r;
                    double a = pow(sin(dlat / 2.0), 2) + cos(track.get(i).getLatitude() * d2r) * cos(track.get(i + 1).getLatitude() * d2r) * pow(sin(dlong / 2.0), 2);
                    double c = 2 * atan2(sqrt(a), sqrt(1 - a));
                    double d = 6367 * c;
                    
                    trackLengths.add(d);
            }
        }
        return trackLengths;
    }
    
    public int resolveMaxElevation(){
        int maxElevation = -1000;
        
        if (track.size() > 0) {
            if (!trackType.startsWith("Land") || !trackType.startsWith("Water") || !isDrawed) {
                for (int i = 0; i < track.size(); i++) {
                    if (maxElevation < track.get(i).getDeviceElevation()) {
                        maxElevation = track.get(i).getDeviceElevation();
                    }
                }
            }else{
                for (int i = 0; i < track.size(); i++) {
                    int temp = Integer.parseInt(internetElevation.get(i));
                    if (maxElevation < temp) {
                        maxElevation = temp;
                    }
                }
            }
        }
        if(maxElevation < 0){
            return 0;
        }
        return maxElevation;
    }
    
    public int resolveMinElevation() {
        int minElevation = 1000000;

        if (track.size() > 0) {
            if (!trackType.startsWith("Land") || !trackType.startsWith("Water") || !isDrawed) {
                for (int i = 0; i < track.size(); i++) {
                    if (minElevation > track.get(i).getDeviceElevation()) {
                        minElevation = track.get(i).getDeviceElevation();
                    }
                }
            }else{
                for (int i = 0; i < track.size(); i++) {
                    int temp = Integer.parseInt(internetElevation.get(i));
                    if (minElevation > temp) {
                        minElevation = temp;
                    }
                }
            }
        }
        if (minElevation == 1000000) {
            return 0;
        }
        return minElevation;
    }
    
    public int resolveTrackHeightDiff(){
        int minElevation = resolveMinElevation();
        int maxElevation = resolveMaxElevation();
        
        int diff = Math.abs(maxElevation - minElevation);
        
        return diff;
    }
    
    public ArrayList<Double> resolveTrackSpeed(){
        ArrayList<Double> trackLengths;
        ArrayList<Double> trackSpeed = new ArrayList<Double>();
        trackSpeed.add(0.0);
        trackLengths = resolveTrackLengths();
        
        if(track.size() > 0){
            for(int i = 0; i < track.size() - 1; i++){
                if(!isDrawed){
                    double deltaTime = (track.get(i + 1).getTime().getTime() - track.get(i).getTime().getTime()) / 1000;
                    double kms = trackLengths.get(i + 1) / deltaTime;
                    double kmh = kms * 3600;

                    trackSpeed.add(kmh);
                }else{
                    trackSpeed.add(0.0);
                }
                
            }
        }
        
        return trackSpeed;
    }
    
    public String resolveTrackDuration() {
        if (track.size() > 0) {
            Date date1 = track.get(0).getTime();
            Date date2 = track.get(track.size() - 1).getTime();

            long deltaTime = (date2.getTime() - date1.getTime()) / 1000;

            int seconds = (int) (deltaTime % 60);

            int minutes = (int) ((deltaTime / 60) % 60);

            int hours = (int) ((deltaTime / 3600) % 24);

            int days = (int) (deltaTime / 86400);

            String duration;

            if (days > 0) {
                duration = days + " days " + hours + " hours " + minutes + " minutes " + seconds + " seconds";
            } else {
                duration = hours + " hours " + minutes + " minutes " + seconds + " seconds";
            }
            return duration;
        } else{
            return "N/A";
        }
    }
    
}
