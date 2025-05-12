package com.trendist.issue_service.domain.issue.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trendist.issue_service.domain.issue.dto.response.BookmarkResponse;
import com.trendist.issue_service.domain.issue.dto.response.IssueGetAllBookmarkedResponse;
import com.trendist.issue_service.domain.issue.dto.response.IssueGetAllResponse;
import com.trendist.issue_service.domain.issue.dto.response.IssueGetResponse;
import com.trendist.issue_service.domain.issue.service.IssueService;
import com.trendist.issue_service.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/issues")
public class IssueController {
	private final IssueService issueService;

	@Operation(
		summary = "이슈 전체 조회",
		description = "사용자가 전체 이슈를 조회합니다."
	)
	@GetMapping
	public ApiResponse<Page<IssueGetAllResponse>> getAllIssues(@RequestParam(defaultValue = "0") int page) {
		return ApiResponse.onSuccess(issueService.getAllIssues(page));
	}

	@Operation(
		summary = "키워드 별 이슈 전체 조회",
		description = "사용자가 특정 키워드에 해당하는 이슈 전체를 조회합니다."
	)
	@GetMapping("/keyword/{keyword}")
	public ApiResponse<Page<IssueGetAllResponse>> getAllIssuesByKeyword(
		@RequestParam(defaultValue = "0") int page,
		@PathVariable(name = "keyword") String keyword) {
		return ApiResponse.onSuccess(issueService.getAllIssuesByKeyword(page, keyword));
	}

	@Operation(
		summary = "특정 이슈 상세 조회",
		description = "사용자가 특정 이슈를 상세 조회합니다."
	)
	@GetMapping("/{id}")
	public ApiResponse<IssueGetResponse> getIssue(@PathVariable(name = "id") UUID id) {
		return ApiResponse.onSuccess(issueService.getIssue(id));
	}

	@Operation(
		summary = "이슈 북마크 등록/해제",
		description = "사용자가 특정 이슈를 북마크 등록하거나 해제합니다."
	)
	@PostMapping("{id}/bookmark")
	public ApiResponse<BookmarkResponse> toggleBookmark(@PathVariable(name = "id") UUID id) {
		return ApiResponse.onSuccess(issueService.toggleBookmark(id));
	}

	@Operation(
		summary = "마이페이지 북마크한 이슈 조회",
		description = "사용자가 북마크한 이슈들을 조회합니다."
	)
	@GetMapping("/bookmark")
	public ApiResponse<Page<IssueGetAllBookmarkedResponse>> getAllIssuesBookmarked(
		@RequestParam(defaultValue = "0") int page) {
		return ApiResponse.onSuccess(issueService.getAllIssuesBookmarked(page));
	}
}
