package com.trendist.issue_service.domain.issue.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.trendist.issue_service.domain.issue.domain.Issue;

import lombok.Builder;

@Builder
public record IssueGetResponse(
	UUID id,
	String title,
	String content,
	LocalDateTime issueDate,
	String siteUrl,
	String imageUrl,
	String keyword
) {
	public static IssueGetResponse from(Issue issue) {
		return IssueGetResponse.builder()
			.id(issue.getId())
			.title(issue.getTitle())
			.content(issue.getContent())
			.issueDate(issue.getIssueDate())
			.siteUrl(issue.getSiteUrl())
			.imageUrl(issue.getImageUrl())
			.keyword(issue.getKeyword())
			.build();
	}
}
