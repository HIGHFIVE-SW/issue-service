package com.trendist.issue_service.domain.issue.dto.response;

import java.util.UUID;

import com.trendist.issue_service.domain.issue.domain.Issue;

import lombok.Builder;

@Builder
public record IssueGetAllResponse(
	UUID id,
	String title,
	String imageUrl,
	String keyword,
	Boolean bookmarked
) {
	public static IssueGetAllResponse of(Issue issue, Boolean bookmarked) {
		return IssueGetAllResponse.builder()
			.id(issue.getId())
			.title(issue.getTitle())
			.imageUrl(issue.getImageUrl())
			.keyword(issue.getKeyword())
			.bookmarked(bookmarked)
			.build();
	}
}
