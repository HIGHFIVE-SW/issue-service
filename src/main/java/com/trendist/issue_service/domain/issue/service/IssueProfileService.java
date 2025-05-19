package com.trendist.issue_service.domain.issue.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.trendist.issue_service.domain.issue.dto.response.IssueGetAllBookmarkedResponse;
import com.trendist.issue_service.domain.issue.repository.IssueBookmarkRepository;
import com.trendist.issue_service.global.feign.user.client.UserServiceClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueProfileService {
	private final UserServiceClient userServiceClient;
	private final IssueBookmarkRepository issueBookmarkRepository;

	public Page<IssueGetAllBookmarkedResponse> getAllIssuesBookmarked(int page) {
		UUID userId = userServiceClient.getMyProfile("").getResult().id();

		Pageable pageable = PageRequest.of(page, 10, Sort.by("issue.issueDate").descending());

		return issueBookmarkRepository.findAllByUserId(userId, pageable)
			.map(IssueGetAllBookmarkedResponse::from);
	}
}
