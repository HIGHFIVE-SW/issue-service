package com.trendist.issue_service.domain.issue.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trendist.issue_service.domain.issue.domain.Issue;
import com.trendist.issue_service.domain.issue.domain.IssueBookmark;
import com.trendist.issue_service.domain.issue.dto.response.BookmarkResponse;
import com.trendist.issue_service.domain.issue.dto.response.IssueGetAllBookmarkedResponse;
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
		UUID userId = userServiceClient.getMyProfile("").getResult().id();
		Pageable pageable = PageRequest.of(page, 12, Sort.by("issueDate").descending());

		Page<Issue> issues = issueRepository.findAll(pageable);
		List<UUID> issueIds = issues.stream()
			.map(Issue::getId)
			.toList();

		List<IssueBookmark> bookmarks = issueBookmarkRepository.findAllByUserIdAndIssue_IdIn(userId, issueIds);
		Set<UUID> bookmarkIds = bookmarks.stream()
			.map(IssueBookmark::getIssue)
			.map(Issue::getId)
			.collect(Collectors.toSet());

		return issues.map(issue ->
			IssueGetAllResponse.of(issue, bookmarkIds.contains(issue.getId())));
	}

	public Page<IssueGetAllResponse> getAllIssuesByKeyword(int page, String keyword) {
		UUID userId = userServiceClient.getMyProfile("").getResult().id();
		Pageable pageable = PageRequest.of(page, 12, Sort.by("issueDate").descending());

		Page<Issue> issues = issueRepository.findAllByKeyword(pageable, keyword);
		List<UUID> issueIds = issues.stream()
			.map(Issue::getId)
			.toList();

		List<IssueBookmark> bookmarks = issueBookmarkRepository.findAllByUserIdAndIssue_IdIn(userId, issueIds);
		Set<UUID> bookmarkIds = bookmarks.stream()
			.map(IssueBookmark::getIssue)
			.map(Issue::getId)
			.collect(Collectors.toSet());

		return issues.map(issue ->
			IssueGetAllResponse.of(issue, bookmarkIds.contains(issue.getId())));
	}

	public IssueGetResponse getIssue(UUID id) {
		UUID userId = userServiceClient.getMyProfile("").getResult().id();

		Issue issue = issueRepository.findById(id)
			.orElseThrow(() -> new ApiException(ErrorStatus._ISSUE_NOT_FOUND));

		boolean bookmarked = issueBookmarkRepository.existsByUserIdAndIssue_Id(userId, id);
		return IssueGetResponse.of(issue, bookmarked);
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

	public Page<IssueGetAllBookmarkedResponse> getAllIssuesBookmarked(int page) {
		UUID userId = userServiceClient.getMyProfile("").getResult().id();

		Pageable pageable = PageRequest.of(page, 10, Sort.by("issue.issueDate").descending());

		return issueBookmarkRepository.findAllByUserId(userId, pageable)
			.map(IssueGetAllBookmarkedResponse::from);
	}
}
