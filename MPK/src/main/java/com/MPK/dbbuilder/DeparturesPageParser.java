package com.MPK.dbbuilder;

import com.MPK.model.Departures;
import com.MPK.model.Departures.DepartureType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static com.MPK.model.Departures.DepartureType.*;

public class DeparturesPageParser {

    private final Page departuresPage;
    private final Elements departuresTableRows;

    public DeparturesPageParser(Page departuresPage) {
        this.departuresPage = departuresPage;
        this.departuresTableRows = extractDeparturesTableRows();
    }

    private Elements extractDeparturesTableRows() {
        final String pageContent = getPageContentAsString(departuresPage);
        final Document document = Jsoup.parse(String.valueOf(pageContent));

        //SPECIFIC SELECTIONS OF SITE HTML TAGS
        return document.select("table[style= width: 700px; ]").first()
                .select("table").get(2)
                .select("tbody").first()
                .select("tr");
    }

    private List<Departures> parsePage() {
        return parseDepartureTableRows();
    }

    private List<Departures> parseDepartureTableRows() {

        final int DEPARTURE_TYPES_AMOUNT = extractDepartureTypesAmount();
        final List<Departures> departuresList = new ArrayList<>();

        //ITERATING OVER DEPARTURES (TYPES WEEK HOLIDAY etc.)
        for (int i = 1; i <= DEPARTURE_TYPES_AMOUNT; i++) {

            final Element currentDepartureTypeElement = departuresTableRows.get(0).select("td").get(i);
            final DepartureType currentDepartureType = parseDepartureType(currentDepartureTypeElement.text());

            final Departures currentDepartures = new Departures(currentDepartureType);

            //ITERATING OVER HOURS TABLE ROWS
            for (int j = 1; j < departuresTableRows.size() - 2; j++) {

                final String currentHourRow = departuresTableRows.get(j).select("td").get(0).text();
                final String currentMinutesRow = departuresTableRows.get(j).select("td").get(i).text();

                if (containMinutes(currentMinutesRow)) {
                    String departureHour = currentHourRow + " " + parseMinutes(currentMinutesRow);
                    currentDepartures.addHour(departureHour);
                }
            }

            departuresList.add(currentDepartures);
        }
        return departuresList;
    }

    private String getPageContentAsString(Page page) {
        return String.valueOf(page.getContent());
    }

    private int extractDepartureTypesAmount() {
        return departuresTableRows.get(0).select("td").size() - 2;
    }

    private DepartureType parseDepartureType(String s) {
        switch (s) {
            case "Dzień powszedni":
                return WEEKDAYS;
            case "Soboty":
                return SATURDAYS;
            case "Święta":
                return HOLIDAYS;
            case "Pt/Sob-Sob/Nd":
                return FR_SA_SU;
            case "Wszystkie dni tygodnia":
                return EVERYDAY;
            default:
                throw new RuntimeException("Error parsing departure type");
        }
    }

    private String parseMinutes(String s) {
        return s.substring(2);
    }

    private boolean containMinutes(String s) {
        return s.matches(".*\\d+.*");
    }
}
