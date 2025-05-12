package com.trendist.issue_service.domain.issue.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trendist.issue_service.domain.issue.domain.IssueBookmark;

public interface IssueBookmarkRepository extends JpaRepository<IssueBookmark, UUID> {
	Optional<IssueBookmark> findByUserIdAndIssue_Id(UUID userId, UUID issueId);

	Boolean existsByUserIdAndIssue_Id(UUID userId, UUID issueId);

	void deleteByUserIdAndIssue_Id(UUID userId, UUID issueId);
}
