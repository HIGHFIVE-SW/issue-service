package com.trendist.issue_service.domain.issue.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trendist.issue_service.domain.issue.dto.response.IssueGetAllResponse;
import com.trendist.issue_service.domain.issue.service.IssueService;
import com.trendist.issue_service.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/issues")
public class IssueController {
	private final IssueService issueService;

	@GetMapping
	public ApiResponse<Page<IssueGetAllResponse>> getAllIssues(@RequestParam(defaultValue = "0") int page) {
		return ApiResponse.onSuccess(issueService.getAllIssues(page));
	}

	@GetMapping("/{keyword}")
	public ApiResponse<Page<IssueGetAllResponse>> getAllIssuesByKeyword(
		@RequestParam(defaultValue = "0") int page,
		@PathVariable(name = "keyword") String keyword) {
		return ApiResponse.onSuccess(issueService.getAllIssuesByKeyword(page, keyword));
	}
}
