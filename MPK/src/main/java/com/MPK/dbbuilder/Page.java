package com.MPK.dbbuilder;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Page {

    private URL url;
    private Scanner scanner;

    public Page(String pageUrl){

        try {
            this.url = new URL(pageUrl);
            this.scanner = new Scanner(url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getContent(){

        List<String> pageContent = new ArrayList<>();
        while (scanner.hasNext()){
            pageContent.add(scanner.nextLine());
        }
        return pageContent;
    }


}
