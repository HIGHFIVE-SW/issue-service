package com.trendist.issue_service.domain.issue.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.trendist.issue_service.domain.issue.domain.Issue;

import lombok.Builder;

@Builder
public record IssueGetAllResponse(
	UUID id,
	String title,
	String imageUrl,
	String keyword
) {
	public static IssueGetAllResponse from(Issue issue) {
		return IssueGetAllResponse.builder()
			.id(issue.getId())
			.title(issue.getTitle())
			.imageUrl(issue.getImageUrl())
			.keyword(issue.getKeyword())
			.build();
	}
}
