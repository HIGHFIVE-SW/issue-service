package com.trendist.issue_service.domain.issue.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trendist.issue_service.domain.issue.dto.response.IssueGetAllBookmarkedResponse;
import com.trendist.issue_service.domain.issue.service.IssueProfileService;
import com.trendist.issue_service.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class IssueProfileController {
	private final IssueProfileService issueProfileService;

	@Operation(
		summary = "마이페이지 북마크한 이슈 조회",
		description = "사용자가 북마크한 이슈들을 조회합니다."
	)
	@GetMapping("/issues/bookmark")
	public ApiResponse<Page<IssueGetAllBookmarkedResponse>> getAllIssuesBookmarked(
		@RequestParam(defaultValue = "0") int page) {
		return ApiResponse.onSuccess(issueProfileService.getAllIssuesBookmarked(page));
	}
}
