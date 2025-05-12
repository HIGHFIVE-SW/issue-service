package com.trendist.issue_service.domain.issue.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.trendist.issue_service.domain.issue.dto.response.IssueGetAllResponse;
import com.trendist.issue_service.domain.issue.repository.IssueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueService {
	private final IssueRepository issueRepository;

	public Page<IssueGetAllResponse> getAllIssues(int page) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by("issueDate").descending());
		return issueRepository.findAll(pageable)
			.map(IssueGetAllResponse::from);
	}
}
