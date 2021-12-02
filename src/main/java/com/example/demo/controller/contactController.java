package com.example.demo.controller;

import com.example.demo.model.LocationStats;
import com.example.demo.services.CoronaVirusDataServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class contactController {
    @Autowired
    CoronaVirusDataServices CoronaVirusData;


    @RequestMapping (path="/contact", method= RequestMethod.POST)
    public String home2(Model model , HttpServletRequest request) {
        String help=request.getParameter("username");
        //System.out.println(help);
        List<LocationStats> allstats=CoronaVirusData.getAllstats();
        List<LocationStats> specific=new ArrayList<>();
        for(int i=0;i<allstats.size();i++){
            if(allstats.get(i).getCountry().equalsIgnoreCase(help)){
                specific.add(allstats.get(i));
            }
        }
        int totalreportedcases= specific.stream().mapToInt(stat->stat.getLatesttotalcases()).sum();
        int totalnewcases=specific.stream().mapToInt(stat->stat.getNewCasesToday()).sum();

        specific.forEach(stat -> {
            if(stat.getState() == null || stat.getState().isEmpty())
                stat.setState("NA");
        });
        model.addAttribute("locationstats",specific);
        model.addAttribute("totalreportedcases",totalreportedcases);
        model.addAttribute("totalnewcases",totalnewcases);
        return "home2";
    }
}
