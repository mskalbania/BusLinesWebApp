package com.MPK.dbbuilder;

import com.MPK.model.Line;

import java.util.List;

public class LinesSupplier implements DataSupplier {

    private Page page;

    public LinesSupplier() {
        this.page = new Page("http://rozklady.mpk.krakow.pl/?akcja=index&rozklad=20170715&lang=PL");
    }

    private boolean containsType(String s) {
        return s.contains("Linie") && s.contains("t_info_left");
    }

    private boolean containsLine(String s) {
        return s.contains("linia=");
    }

    private String extractLineType(String s) {
        int startIndex = s.indexOf("L");
        int endIndex = s.indexOf(":");
        return s.substring(startIndex, endIndex);
    }

    private String extractLink(String s) {
        String[] temp = s.split("'");
        return temp[1];
    }

    @Override
    public void supplyData(List<Line> inputList) {

        List<String> pageContent = page.getContent();

        String currentType = "";
        for (String s : pageContent) {

            //Type parsing
            if (containsType(s)) {
                currentType = extractLineType(s);
            }
            //com.mpk.Line parsing
            else if (containsLine(s)) {
                String lineLink = extractLink(s);
                String lineNumber = lineLink.substring(lineLink.lastIndexOf("=") + 1);
                inputList.add(new Line(lineNumber, currentType, lineLink));
            }
        }
    }
}
