package com.pjqdev.nirvana.demo.repository;

import com.pjqdev.nirvana.demo.model.Issue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssuesRepository extends CrudRepository<Issue, Long> {

}
