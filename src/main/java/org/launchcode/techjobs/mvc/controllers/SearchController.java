package org.launchcode.techjobs.mvc.controllers;

import org.launchcode.techjobs.mvc.models.Job;
import org.launchcode.techjobs.mvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import static org.launchcode.techjobs.mvc.controllers.ListController.columnChoices;


/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController extends TechJobsController {

    @GetMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        model.addAttribute("searchType", "all");
        return "search";
    }

    // TODO #3 - Create a handler to process a search request and render the updated search view.

    @PostMapping("results")
    public String displaySearchResults( Model model, @RequestParam String searchType, @RequestParam String searchTerm) {
        ArrayList<Job> jobs;
        if(searchTerm == null || searchTerm.trim().isEmpty() || searchTerm.trim().toUpperCase().equals("ALL")) {
            jobs = JobData.findAll();
        }
        else {
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);
        }
        model.addAttribute("jobs", jobs);
        model.addAttribute("columns", columnChoices);
        model.addAttribute("searchType", searchType);

        return "search";
    }

    @GetMapping("results/{searchType}/{searchValue}")
    public String displayClickResults(Model model, @PathVariable String searchType, @PathVariable String searchValue) {
        ArrayList<Job> jobs = new ArrayList<>();

        if(searchValue == null || searchValue.trim().isEmpty() || searchValue.trim().toUpperCase().equals("ALL")) {
            jobs = JobData.findAll();
        } else {
            if (searchType.equals("positionType")) {
                searchValue = searchValue.replaceAll("-", "/");
            }
            jobs = JobData.findByColumnAndValue(searchType, searchValue);
        }
        model.addAttribute("jobs", jobs);
        model.addAttribute("columns", columnChoices);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchValue", searchValue);

        return "search";
    }


}
