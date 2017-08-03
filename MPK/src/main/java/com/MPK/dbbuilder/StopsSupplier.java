package com.MPK.dbbuilder;

import com.MPK.model.Line;
import com.MPK.model.Stop;

import java.util.ArrayList;
import java.util.List;


public class StopsSupplier implements DataSupplier {

    private boolean containsStopName(String s) {
        return s.contains("span style=' white-space: nowrap");
    }

    private boolean containsRefLink(String s) {
        return s.contains("__") && !s.contains("img") && !s.contains("akcja");
    }

    private String extractStopName(String s) {
        int startIndex = s.indexOf("' >") + 4;
        int endIndex = s.indexOf("</") - 1;
        return s.substring(startIndex, endIndex);
    }

    private String extractRefLink(String s) {
        int startIndex = s.indexOf("'") + 1;
        int endIndex = s.lastIndexOf("'");
        return s.substring(startIndex, endIndex);
    }

    private List<Stop> parsePage(Page page) {
        List<String> pageContent = page.getContent();
        List<Stop> output = new ArrayList<>();

        String currentName = "";
        String currentLink = "";
        Stop currentStop;

        for (String s : pageContent) {
            if (containsRefLink(s)) {
                currentLink = extractRefLink(s);
            }
            if (containsStopName(s)) {
                currentName = extractStopName(s);
                currentStop = new Stop(currentName);
                currentStop.setRefLink(currentLink);
                output.add(currentStop);
            }
        }
        return output;
    }


    @Override
    public void supplyData(List<Line> inputList) {

        for (Line line : inputList) {

            Page pageDirection1 = new Page(line.getRefLink().concat("__1"));
            Page pageDirection2 = new Page(line.getRefLink().concat("__2"));

            List<Stop> stopsDirection1 = parsePage(pageDirection1);
            List<Stop> stopsDirection2 = parsePage(pageDirection2);

            if (!stopsDirection2.isEmpty()) {
                Stop lastStopDirection1 = new Stop(stopsDirection2.get(0).getName());
                stopsDirection1.add(lastStopDirection1);
            }
            if (!stopsDirection1.isEmpty()) {
                Stop lastStopDirection2 = new Stop(stopsDirection1.get(0).getName());
                stopsDirection2.add(lastStopDirection2);

            }

            stopsDirection1.forEach(stop -> stop.setDirection(Stop.Direction.FIRST));
            stopsDirection2.forEach(stop -> stop.setDirection(Stop.Direction.SECOND));

            line.addStops(stopsDirection1);
            line.addStops(stopsDirection2);
        }
    }
}
