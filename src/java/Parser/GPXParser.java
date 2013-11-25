/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Parser;

import File.FileImpl;
import File.TrackPointImpl;
import Parser.Utilities.ElevationLoader;
import Parser.Utilities.MultimediaSearcher;
import Parser.Utilities.TimezoneLoader;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

/**
 *
 * @author matej_000
 */
public class GPXParser {
    
    private String path;
    private String os = System.getProperty("os.name");
    private File gpxFile;
    private File destFolder;
    private boolean isLoadedElevationsFromServer = false;
    private static ArrayList<String> latitude = new ArrayList<String>();
    private static ArrayList<String> longitude = new ArrayList<String>();
    private static ArrayList<String> deviceElevation = new ArrayList<String>();
    private static ArrayList<String> serverElevation = null;
    private ArrayList<FileImpl> files = new ArrayList<FileImpl>();
    private static ArrayList<Date> time = new ArrayList<Date>();
    private ArrayList<TrackPointImpl> track = new ArrayList<TrackPointImpl>();
    private DateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private DateFormat formEU = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    
    public GPXParser(String pathToFiles, String sourceFile){
        gpxFile = new File(pathToFiles + sourceFile);
        
        String destFile = sourceFile.substring(0, sourceFile.indexOf(".gpx"));
        destFolder = new File(pathToFiles + destFile);
        
        this.readGpx();
    }
    
    public void readGpx() {
        latitude.clear();
        longitude.clear();
        deviceElevation.clear();
        serverElevation = null;
        time.clear();
        track.clear();

        try {
            DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();
            DocumentBuilder DB = DBF.newDocumentBuilder();
            org.w3c.dom.Document DOC = DB.parse(gpxFile);
            DOC.getDocumentElement().normalize();
            Node gpxStartNode = DOC.getElementsByTagName("gpx").item(0);
            Element gpxStartElem = (Element) gpxStartNode;

            Node gpxStartNode1 = gpxStartElem.getElementsByTagName("trk").item(0);
            Element gpxStartElem1 = (Element) gpxStartNode1;

            Node gpxStartNode2 = gpxStartElem1.getElementsByTagName("trkseg").item(0);
            Element gpxStartElem2 = (Element) gpxStartNode2;

            NodeList gpxStartNode3 = gpxStartElem1.getElementsByTagName("trkpt");

            for (int i = 0; i < gpxStartNode3.getLength(); i++) {
                Element trackPointElement = (Element) gpxStartNode3.item(i);

                latitude.add(trackPointElement.getAttribute("lat"));
                longitude.add(trackPointElement.getAttribute("lon"));

                Node trackPointNode2 = trackPointElement.getElementsByTagName("ele").item(0);
                Element trackPointElement2 = (Element) trackPointNode2;
                deviceElevation.add(trackPointElement2.getTextContent());

                Node trackPointNode3 = trackPointElement.getElementsByTagName("time").item(0);
                Element trackPointElement3 = (Element) trackPointNode3;
                String temp = trackPointElement3.getTextContent().replace('T', ' ').substring(0, trackPointElement3.getTextContent().length() - 1);
                if (trackPointElement3.getTextContent().toUpperCase().endsWith("Z")) {
                    Date tempDate = (Date) form.parse(temp);
                    time.add(tempDate);
                }
            }

        } catch (Exception ex) {
            System.out.println("Error: Cannot read file");
        } finally {
            for (int i = 0; i < latitude.size(); i++) {
                TrackPointImpl tempTP = new TrackPointImpl();
                tempTP.setLatitude(Double.parseDouble(latitude.get(i)));
                tempTP.setLongitude(Double.parseDouble(longitude.get(i)));
                tempTP.setDeviceElevation(Integer.parseInt(deviceElevation.get(i).substring(0, deviceElevation.get(i).indexOf("."))));
                tempTP.setTime(time.get(i));
                track.add(tempTP);
            }
        }
    }
    
    public void parseGpx(String trackType, String trackDescr) {
        if (gpxFile != null && destFolder != null) {
            if (destFolder.getAbsolutePath().toLowerCase().endsWith(".tlv")) {
                path = destFolder.getAbsolutePath();
            }
            if (!destFolder.getAbsolutePath().toLowerCase().endsWith(".tlv")) {
                path = destFolder.getAbsolutePath() + ".tlv";
            }

            File f = new File(path);

            if (f.exists()) {
                try {
                    f.delete();
                    f.createNewFile();
                } catch (IOException ex) {
                    System.out.println("Error: Cannot create *.tlv file!!!");
                }
            }

            if (!f.exists()) {
                try {
                    f.createNewFile();
                } catch (IOException ex) {
                    System.out.println("Error: Cannot create *.tlv file!!!");
                }
            }
            try {
                DocumentBuilderFactory DBF1 = DocumentBuilderFactory.newInstance();
                DocumentBuilder DB1 = DBF1.newDocumentBuilder();
                org.w3c.dom.Document document = DB1.newDocument();
                org.w3c.dom.Element rootElement = document.createElement("TLV");
                document.appendChild(rootElement);
                org.w3c.dom.Element rootElement3 = document.createElement("SYSTEM");
                rootElement3.appendChild(document.createTextNode(os));
                rootElement.appendChild(rootElement3);
                org.w3c.dom.Element rootElement2 = document.createElement("FILES");
                if (files.isEmpty() == true) {
                   rootElement2.appendChild(document.createTextNode("null"));
                }
                rootElement.appendChild(rootElement2);

                for (int i = 0; i < files.size(); i++) {
                    org.w3c.dom.Element em = document.createElement("File_entity");
                    rootElement2.appendChild(em);
                    org.w3c.dom.Element em1 = document.createElement("path");
                    em1.appendChild(document.createTextNode(files.get(i).getPath().toString()));
                    em.appendChild(em1);

                    org.w3c.dom.Element em2 = document.createElement("creation_date");
                    em2.appendChild(document.createTextNode(String.valueOf(files.get(i).getDate().getTime())));
                    em.appendChild(em2);

                    org.w3c.dom.Element emr = document.createElement("gps_latitude");
                    if (files.get(i).getLatitude() != null) {
                        emr.appendChild(document.createTextNode(files.get(i).getLatitude()));
                    } else {
                        emr.appendChild(document.createTextNode("null"));
                    }
                    em.appendChild(emr);

                    org.w3c.dom.Element emr1 = document.createElement("gps_longitude");
                    if (files.get(i).getLongitude() != null) {
                        emr1.appendChild(document.createTextNode(files.get(i).getLongitude()));
                    } else {
                        emr1.appendChild(document.createTextNode("null"));
                    }
                    em.appendChild(emr1);

                    org.w3c.dom.Element emr2 = document.createElement("gps_elevation");
                    if (files.get(i).getElevation() != null) {
                        emr2.appendChild(document.createTextNode(files.get(i).getElevation()));
                    } else {
                        emr2.appendChild(document.createTextNode("null"));
                    }
                    em.appendChild(emr2);

                }
                
                ElevationLoader eleLoader = new ElevationLoader();
                serverElevation = eleLoader.reclaimElevation(track);
                if(serverElevation.size() == deviceElevation.size()){
                    isLoadedElevationsFromServer = true;
                }else{
                    isLoadedElevationsFromServer = false;
                }

                org.w3c.dom.Element rootElement1 = document.createElement("COORDINATES");
                rootElement.appendChild(rootElement1);

                org.w3c.dom.Element element2 = document.createElement("Track_Type");
                element2.appendChild(document.createTextNode(trackType));
                rootElement1.appendChild(element2);
                
                org.w3c.dom.Element element2_1 = document.createElement("Track_Description");
                element2_1.appendChild(document.createTextNode(trackDescr));
                rootElement1.appendChild(element2_1);

                org.w3c.dom.Element element3 = document.createElement("Elevations_type");
                if (isLoadedElevationsFromServer == true) {
                    element3.appendChild(document.createTextNode("INTERNET"));
                } else {
                    element3.appendChild(document.createTextNode("DEVICE"));
                }
                rootElement1.appendChild(element3);

                for (int i = 0; i < latitude.size(); i++) {
                    org.w3c.dom.Element em = document.createElement("TrackPoint");
                    rootElement1.appendChild(em);
                    org.w3c.dom.Element em1 = document.createElement("Latitude");
                    em1.appendChild(document.createTextNode(latitude.get(i)));
                    em.appendChild(em1);

                    org.w3c.dom.Element em2 = document.createElement("Longitude");
                    em2.appendChild(document.createTextNode(longitude.get(i)));
                    em.appendChild(em2);

                    org.w3c.dom.Element em3 = document.createElement("Device_Elevation");
                    em3.appendChild(document.createTextNode(deviceElevation.get(i)));
                    em.appendChild(em3);

                    if (isLoadedElevationsFromServer == true) {
                        org.w3c.dom.Element em3aPol = document.createElement("Internet_Elevation");
                        em3aPol.appendChild(document.createTextNode(serverElevation.get(i)));
                        em.appendChild(em3aPol);
                    }

                    org.w3c.dom.Element em4 = document.createElement("Time");
                    StringBuilder str = new StringBuilder();
                    str = str.append(time.get(i).getTime());
                    String tempStr = str.toString();
                    em4.appendChild(document.createTextNode(tempStr));
                    em.appendChild(em4);
                }
                TransformerFactory TF = TransformerFactory.newInstance();
                Transformer T = TF.newTransformer();
                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(f);
                T.transform(source, result);
                
                DB1.reset();

            } catch (Exception ex) {
                System.out.println("Error: Cannot create *.tlv file!!!");
            }
        }
    }
    
    public void searchForMultimediaFiles(String searchFolder){
        TimezoneLoader gmt = new TimezoneLoader(track);
        gmt.correctTimeZone();
        MultimediaSearcher searcher = new MultimediaSearcher(destFolder.getPath(), searchFolder, track);
        files = searcher.startSearch();
    }

}
