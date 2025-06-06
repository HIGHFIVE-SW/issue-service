package com.trendist.issue_service.global.exception;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.trendist.issue_service.global.response.ApiResponse;
import com.trendist.issue_service.global.response.dto.ErrorReasonDto;
import com.trendist.issue_service.global.response.status.ErrorStatus;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

//컨트롤러에서 발생한 예외를 가로채어 일관된 형태의 API 응답을 반환하도록 도와줍니다.
//각 오류 상황에서 적절한 HTTP 상태와 응답 본문(ApiResponse)을 구성하여 클라이언트에게 반환합니다.
//클라이언트가 오류 상황을 쉽게 이해하고 처리할 수 있도록 표준화된 에러 응답을 제공합니다.
@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request) {
		String errorMessage = e.getConstraintViolations().stream()
			.map(ConstraintViolation::getMessage)
			.findFirst()
			.orElseThrow(() -> new RuntimeException("ConstraintViolationException 추출 도중 에러 발생"));

		return handleExceptionInternalConstraint(e, ErrorStatus.valueOf(errorMessage),
			HttpHeaders.EMPTY, request);
	}

	public ResponseEntity<Object> handleMethodArgumentNotValid(
		MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status,
		WebRequest request) {

		Map<String, String> errors = new LinkedHashMap<>();

		e.getBindingResult().getFieldErrors()
			.forEach(fieldError -> {
				String fieldName = fieldError.getField();
				String errorMessage = Optional.ofNullable(fieldError.getDefaultMessage())
					.orElse("");
				errors.merge(fieldName, errorMessage,
					(existingErrorMessage, newErrorMessage) -> existingErrorMessage + ", "
						+ newErrorMessage);
			});

		return handleExceptionInternalArgs(e, HttpHeaders.EMPTY,
			ErrorStatus.valueOf("_BAD_REQUEST"), request, errors);
	}

	@ExceptionHandler
	public ResponseEntity<Object> exception(Exception e, WebRequest request) {
		e.printStackTrace();

		return handleExceptionInternalFalse(e, ErrorStatus._INTERNAL_SERVER_ERROR,
			HttpHeaders.EMPTY, ErrorStatus._INTERNAL_SERVER_ERROR.getHttpStatus(), request,
			e.getMessage());
	}

	@ExceptionHandler(value = ApiException.class)
	public ResponseEntity onThrowException(ApiException apiException, HttpServletRequest request) {
		ErrorReasonDto errorReasonHttpStatus = apiException.getErrorReasonHttpStatus();
		return handleExceptionInternal(apiException, errorReasonHttpStatus, null, request);
	}

	private ResponseEntity<Object> handleExceptionInternal(Exception e, ErrorReasonDto reason,
		HttpHeaders headers, HttpServletRequest request) {

		ApiResponse<Object> body = ApiResponse.onFailure(reason.getCode(), reason.getMessage(),
			null);

		WebRequest webRequest = new ServletWebRequest(request);
		return super.handleExceptionInternal(
			e,
			body,
			headers,
			reason.getHttpStatus(),
			webRequest
		);
	}

	private ResponseEntity<Object> handleExceptionInternalFalse(Exception e,
		ErrorStatus errorCommonStatus,
		HttpHeaders headers, HttpStatus status, WebRequest request, String errorPoint) {
		ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(),
			errorCommonStatus.getMessage(), errorPoint);
		return super.handleExceptionInternal(
			e,
			body,
			headers,
			status,
			request
		);
	}

	private ResponseEntity<Object> handleExceptionInternalArgs(Exception e, HttpHeaders headers,
		ErrorStatus errorCommonStatus,
		WebRequest request, Map<String, String> errorArgs) {
		ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(),
			errorCommonStatus.getMessage(), errorArgs);
		return super.handleExceptionInternal(
			e,
			body,
			headers,
			errorCommonStatus.getHttpStatus(),
			request
		);
	}

	private ResponseEntity<Object> handleExceptionInternalConstraint(Exception e,
		ErrorStatus errorCommonStatus,
		HttpHeaders headers, WebRequest request) {
		ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(),
			errorCommonStatus.getMessage(), null);
		return super.handleExceptionInternal(
			e,
			body,
			headers,
			errorCommonStatus.getHttpStatus(),
			request
		);
	}
}
