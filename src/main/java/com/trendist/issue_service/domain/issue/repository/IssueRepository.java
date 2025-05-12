package com.trendist.issue_service.domain.issue.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.trendist.issue_service.domain.issue.domain.Issue;

public interface IssueRepository extends JpaRepository<Issue, UUID> {
	Page<Issue> findAllByKeyword(Pageable pageable, String keyword);
}
