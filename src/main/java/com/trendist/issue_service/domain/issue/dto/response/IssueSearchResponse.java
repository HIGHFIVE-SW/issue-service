package com.trendist.issue_service.domain.issue.dto.response;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

import com.trendist.issue_service.domain.issue.domain.IssueDocument;

import lombok.Builder;

@Builder
public record IssueSearchResponse(
	UUID id,
	String title,
	String keyword,
	String issueDate,
	String imageUrl,
	Boolean bookmarked
) {
	public static IssueSearchResponse from(IssueDocument issueDocument, Boolean bookmarked) {
		byte[] bytes = Base64.getDecoder().decode(issueDocument.getId());
		ByteBuffer bb = ByteBuffer.wrap(bytes);
		UUID uuid = new UUID(bb.getLong(), bb.getLong());

		return IssueSearchResponse.builder()
			.id(uuid)
			.title(issueDocument.getTitle())
			.keyword(issueDocument.getKeyword())
			.issueDate(issueDocument.getIssueDate())
			.imageUrl(issueDocument.getImageUrl())
			.bookmarked(bookmarked)
			.build();
	}
}
