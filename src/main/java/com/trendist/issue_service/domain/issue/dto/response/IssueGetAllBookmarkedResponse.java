package com.trendist.issue_service.domain.issue.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.trendist.issue_service.domain.issue.domain.IssueBookmark;

import lombok.Builder;

@Builder
public record IssueGetAllBookmarkedResponse(
	UUID bookmarkId,
	UUID issueId,
	String title,
	String keyword,
	LocalDateTime issueDate
) {
	public static IssueGetAllBookmarkedResponse from(IssueBookmark bookmark) {
		return IssueGetAllBookmarkedResponse.builder()
			.bookmarkId(bookmark.getId())
			.issueId(bookmark.getIssue().getId())
			.title(bookmark.getIssue().getTitle())
			.keyword(bookmark.getIssue().getKeyword())
			.issueDate(bookmark.getIssue().getIssueDate())
			.build();
	}
}
