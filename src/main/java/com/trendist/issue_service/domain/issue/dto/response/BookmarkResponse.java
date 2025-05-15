package com.trendist.issue_service.domain.issue.dto.response;

import java.util.UUID;

import com.trendist.issue_service.domain.issue.domain.IssueBookmark;

import lombok.Builder;

@Builder
public record BookmarkResponse(
	UUID bookmarkId,
	UUID userId,
	UUID issueId,
	Boolean bookmarked
) {
	public static BookmarkResponse of(IssueBookmark issueBookmark, boolean bookmarked) {
		return BookmarkResponse.builder()
			.bookmarkId(issueBookmark.getId())
			.userId(issueBookmark.getUserId())
			.issueId(issueBookmark.getIssue().getId())
			.bookmarked(bookmarked)
			.build();
	}
}
