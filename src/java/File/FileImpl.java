/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package File;

import java.util.Date;

/**
 *
 * @author matej_000
 */
public class FileImpl {
    private String path;
    private Date date;
    private String latitude;
    private String longitude;
    private String elevation;
    private int trackPointIndex;

    public FileImpl() {
        this.path = null;
        this.date = null;
        this.latitude = null;
        this.longitude = null;
        this.elevation = null;
        this.trackPointIndex = -1;
    }

    public FileImpl(String path, Date date) {
        this.path = path;
        this.date = date;
        this.latitude = null;
        this.longitude = null;
        this.elevation = null;
        this.trackPointIndex = -1;
    }

    public FileImpl(String path, Date date, String latitude, String longitude) {
        this.path = path;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = null;
        this.trackPointIndex = -1;
    }

    public FileImpl(String path, Date date, String latitude, String longitude, String elevation) {
        this.path = path;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.trackPointIndex = -1;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the elevation
     */
    public String getElevation() {
        return elevation;
    }

    /**
     * @param elevation the elevation to set
     */
    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    /**
     * @return the trackPointIndex
     */
    public int getTrackPointIndex() {
        return trackPointIndex;
    }

    /**
     * @param trackPointIndex the trackPointIndex to set
     */
    public void setTrackPointIndex(int trackPointIndex) {
        this.trackPointIndex = trackPointIndex;
    }
}
