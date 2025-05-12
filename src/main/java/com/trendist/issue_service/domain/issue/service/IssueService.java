package com.trendist.issue_service.domain.issue.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.trendist.issue_service.domain.issue.domain.Issue;
import com.trendist.issue_service.domain.issue.dto.response.IssueGetAllResponse;
import com.trendist.issue_service.domain.issue.dto.response.IssueGetResponse;
import com.trendist.issue_service.domain.issue.repository.IssueRepository;
import com.trendist.issue_service.global.exception.ApiException;
import com.trendist.issue_service.global.response.status.ErrorStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueService {
	private final IssueRepository issueRepository;

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
}
