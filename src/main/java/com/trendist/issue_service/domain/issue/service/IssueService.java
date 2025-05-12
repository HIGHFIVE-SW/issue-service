package com.trendist.issue_service.domain.issue.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trendist.issue_service.domain.issue.domain.Issue;
import com.trendist.issue_service.domain.issue.domain.IssueBookmark;
import com.trendist.issue_service.domain.issue.dto.response.BookmarkResponse;
import com.trendist.issue_service.domain.issue.dto.response.IssueGetAllResponse;
import com.trendist.issue_service.domain.issue.dto.response.IssueGetResponse;
import com.trendist.issue_service.domain.issue.repository.IssueBookmarkRepository;
import com.trendist.issue_service.domain.issue.repository.IssueRepository;
import com.trendist.issue_service.global.exception.ApiException;
import com.trendist.issue_service.global.feign.user.client.UserServiceClient;
import com.trendist.issue_service.global.response.status.ErrorStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueService {
	private final IssueRepository issueRepository;
	private final IssueBookmarkRepository issueBookmarkRepository;
	private final UserServiceClient userServiceClient;

	public Page<IssueGetAllResponse> getAllIssues(int page) {
		Pageable pageable = PageRequest.of(page, 12, Sort.by("issueDate").descending());
		return issueRepository.findAll(pageable)
			.map(IssueGetAllResponse::from);
	}

	public Page<IssueGetAllResponse> getAllIssuesByKeyword(int page, String keyword) {
		Pageable pageable = PageRequest.of(page, 12, Sort.by("issueDate").descending());
		return issueRepository.findAllByKeyword(pageable, keyword)
			.map(IssueGetAllResponse::from);
	}

	public IssueGetResponse getIssue(UUID id) {
		Issue issue = issueRepository.findById(id)
			.orElseThrow(() -> new ApiException(ErrorStatus._ISSUE_NOT_FOUND));

		return IssueGetResponse.from(issue);
	}

	@Transactional
	public BookmarkResponse toggleBookmark(UUID issueId) {
		UUID userId = userServiceClient.getMyProfile("").getResult().id();

		Issue issue = issueRepository.findById(issueId)
			.orElseThrow(() -> new ApiException(ErrorStatus._ISSUE_NOT_FOUND));

		IssueBookmark bookmark;
		boolean bookmarked;

		if (!issueBookmarkRepository.existsByUserIdAndIssue_Id(userId, issueId)) {
			bookmark = IssueBookmark.builder()
				.userId(userId)
				.issue(issue)
				.build();

			issueBookmarkRepository.save(bookmark);
			bookmarked = true;
		} else {
			bookmark = issueBookmarkRepository.findByUserIdAndIssue_Id(userId, issueId)
				.orElseThrow(() -> new ApiException(ErrorStatus._BOOKMARK_NOT_FOUND));

			issueBookmarkRepository.deleteByUserIdAndIssue_Id(userId, issueId);
			bookmarked = false;
		}

		return BookmarkResponse.of(bookmark, bookmarked);
	}
}
