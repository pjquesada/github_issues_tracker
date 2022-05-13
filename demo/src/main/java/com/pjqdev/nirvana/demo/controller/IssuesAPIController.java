package com.pjqdev.nirvana.demo.controller;

import com.pjqdev.nirvana.demo.model.Issue;
import com.pjqdev.nirvana.demo.service.IssuesServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IssuesAPIController {
    @Autowired
    private IssuesServiceI issuesServiceI;

    @GetMapping("/issues")
    public String listAllIssues(Model model) {
        var issues = (List<Issue>) issuesServiceI.getAll();

        model.addAttribute("issues", issues);

        return "issues";
    }


}
