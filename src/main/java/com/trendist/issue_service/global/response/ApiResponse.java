package com.trendist.issue_service.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.trendist.issue_service.global.response.code.BaseCode;
import com.trendist.issue_service.global.response.status.SuccessStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

//모든 API 응답의 기본 구조를 표준화하기 위해 사용되는 제네릭 클래스
@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

	@JsonProperty("isSuccess")
	private final Boolean isSuccess;
	private final String code;
	private final String message;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T result;

	// 성공한 경우 응답 생성
	public static <T> ApiResponse<T> onSuccess(T result) {
		return new ApiResponse<>(true, SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(),
			result);
	}

	public static <T> ApiResponse<T> of(BaseCode code, T result) {
		return new ApiResponse<>(true, code.getReasonHttpStatus().getCode(),
			code.getReasonHttpStatus().getMessage(), result);
	}

	// 실패한 경우 응답 생성
	public static <T> ApiResponse<T> onFailure(String code, String message, T data) {
		return new ApiResponse<>(false, code, message, data);
	}
}
