package com.MPK.controller;

import com.MPK.model.Line;
import com.MPK.model.LinesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    private final LinesRepository linesRepository;

    @Autowired
    public MainController(LinesRepository linesRepository) {
        this.linesRepository = linesRepository;
    }

    @RequestMapping("/")
    public String showHome() {
        return "home";
    }

    @RequestMapping("/line-info")
    public String showLine(@RequestParam("lineNumber") String lineNumber, ModelMap modelMap) {
        Line line = linesRepository.findByNumber(lineNumber);
        if (line != null) {
            modelMap.addAttribute("line", line);
            modelMap.addAttribute("stopsFirstDir", line.getStopsDirectionFirst());
            modelMap.addAttribute("stopsSecondDir", line.getStopsDirectionSecond());
            return "line-info";
        }
        return "line-not-found";
    }

}
