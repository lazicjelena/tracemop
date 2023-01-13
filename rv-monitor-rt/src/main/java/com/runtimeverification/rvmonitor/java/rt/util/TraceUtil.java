package com.runtimeverification.rvmonitor.java.rt.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TraceUtil {

    private static Map<String, Integer> locationMap = new HashMap<>();

    private static int freshID = 1;

    public static File artifactsDir = null;

    /**
     * This method reduces the size of stored traces.
     *
     * @param fullLOC E.g., org.apache.commons.fileupload2.MultipartStream$ItemInputStream.close(MultipartStream.java:950),
     * @return A short location ID, e.g., loc2.
     */
    public static Integer getShortLocation(String fullLOC) {
        Integer shortLocation = locationMap.get(fullLOC);
        if (shortLocation == null) {
            // we do not have the fullLOC in the map; add it and return the shortLocation
            shortLocation =  freshID++;
            locationMap.put(fullLOC, shortLocation);
        }
        return shortLocation;
    }

    public static void updateLocationMapFromFile(File locationMapFile) {
        if (!locationMapFile.exists()) return;
        String line;
        int largestId = freshID;
        try (BufferedReader reader = new BufferedReader(new FileReader(locationMapFile))) {
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("===")) continue; // skip the first line
                String[] splits = line.split("\\s+");
                int id = Integer.valueOf(splits[1]);
                locationMap.put(splits[0], id);
                if (id > largestId) {
                    largestId = id;
                }
            }
        } catch (FileNotFoundException ex) { // ignore if we can't read
        } catch (IOException ex) {
        }
        freshID = largestId;
    }

    public static Map<String, Integer> getLocationMap() {
        return locationMap;
    }

    public static String getAbsolutePath(String fileName) {
        return new File(artifactsDir + File.separator + fileName).getAbsolutePath();
    }
}
