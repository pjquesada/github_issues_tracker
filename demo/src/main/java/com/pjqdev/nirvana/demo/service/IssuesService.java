package com.pjqdev.nirvana.demo.service;

import com.pjqdev.nirvana.demo.model.Issue;
import com.pjqdev.nirvana.demo.repository.IssuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssuesService implements IssuesServiceI {
    @Autowired
    private IssuesRepository issuesRepository;

    @Override
    public List<Issue> getAll() {
        var issues = (List<Issue>) issuesRepository.findAll();

        return issues;
    }
}
