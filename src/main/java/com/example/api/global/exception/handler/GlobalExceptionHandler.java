package com.example.api.global.exception.handler;

import static com.example.api.global.code.GlobalErrorCode.*;

import java.util.List;
import java.util.Set;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.api.global.code.GlobalErrorCode;
import com.example.api.global.code.base.BaseErrorCode;
import com.example.api.global.dto.ErrorResponse;
import com.example.api.global.exception.BadRequestException;
import com.example.api.global.exception.ConflictException;
import com.example.api.global.exception.ForbiddenException;
import com.example.api.global.exception.InternalServerErrorException;
import com.example.api.global.exception.NotFoundException;
import com.example.api.global.exception.UnAuthorizedException;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Global Exception Handler.
 *
 * <p>
 * {@code ResponseEntityExceptionHandler}를 상속 받아 Spring 에서 처리하고 있는 기본 에러처리를 Overriding
 * </p>
 *
 * @author sk.kwon
 */

@SuppressWarnings("unused")
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	/**
	 * 예상되지 않은 모든 에러 Handle
	 *
	 * @param ex      the target exception
	 * @param request the current request
	 * @return Error Response Object<br>
	 * HttpStatusCode : 500 - Internal Server Error
	 * @see java.lang.Exception
	 */
	@ExceptionHandler(Exception.class)
	private ResponseEntity<Object> handleUnknownException(Exception ex, WebRequest request) {
		log.error("handleException : {0}", ex);
		HttpHeaders headers = new HttpHeaders();
		return super.handleExceptionInternal(ex, createErrorResponse(UNKNOWN_ERROR), headers,
			HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	/**
	 * BadRequestException
	 *
	 * @param ex      the target exception
	 * @param request the current request
	 * @return Error Response Object
	 * @see BadRequestException
	 */
	@ExceptionHandler(BadRequestException.class)
	protected ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
		log.error("handleBadRequestException : {0}", ex);
		HttpHeaders headers = new HttpHeaders();

		ErrorResponse errorResponse = createErrorResponse(ex.getErrorCode());

		if (!ObjectUtils.isEmpty(ex.getData())) {
			errorResponse.setData(ex.getData());
		}

		return super.handleExceptionInternal(ex, errorResponse, headers, ex.getHttpStatus(), request);
	}

	/**
	 * UnAuthorizedException
	 *
	 * @param ex      the target exception
	 * @param request the current request
	 * @return Error Response Object
	 * @see UnAuthorizedException
	 */
	@ExceptionHandler(UnAuthorizedException.class)
	protected ResponseEntity<Object> handleUnAuthorizedException(UnAuthorizedException ex, WebRequest request) {
		log.error("handleUnAuthorizedException : {0}", ex);
		HttpHeaders headers = new HttpHeaders();

		ErrorResponse errorResponse = createErrorResponse(ex.getErrorCode());

		if (!ObjectUtils.isEmpty(ex.getData())) {
			errorResponse.setData(ex.getData());
		}

		return super.handleExceptionInternal(ex, errorResponse, headers, ex.getHttpStatus(), request);
	}

	/**
	 * ForbiddenException
	 *
	 * @param ex      the target exception
	 * @param request the current request
	 * @return Error Response Object
	 * @see ForbiddenException
	 */
	@ExceptionHandler(ForbiddenException.class)
	protected ResponseEntity<Object> handleForbiddenException(ForbiddenException ex, WebRequest request) {
		log.error("handleForbiddenException : {0}", ex);
		HttpHeaders headers = new HttpHeaders();

		ErrorResponse errorResponse = createErrorResponse(ex.getErrorCode());

		if (!ObjectUtils.isEmpty(ex.getData())) {
			errorResponse.setData(ex.getData());
		}

		return super.handleExceptionInternal(ex, errorResponse, headers, ex.getHttpStatus(), request);
	}

	/**
	 * NotFoundException
	 *
	 * @param ex      the target exception
	 * @param request the current request
	 * @return Error Response Object
	 * @see NotFoundException
	 */
	@ExceptionHandler(NotFoundException.class)
	protected ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
		log.error("handleNotFoundException : {0}", ex);
		HttpHeaders headers = new HttpHeaders();

		ErrorResponse errorResponse = createErrorResponse(ex.getErrorCode());

		if (!ObjectUtils.isEmpty(ex.getData())) {
			errorResponse.setData(ex.getData());
		}

		return super.handleExceptionInternal(ex, errorResponse, headers, ex.getHttpStatus(), request);
	}

	/**
	 * ConflictException
	 *
	 * @param ex      the target exception
	 * @param request the current request
	 * @return Error Response Object
	 * @see ConflictException
	 */
	@ExceptionHandler(ConflictException.class)
	protected ResponseEntity<Object> handleConflictException(ConflictException ex, WebRequest request) {
		log.error("handleConflictException : {0}", ex);
		HttpHeaders headers = new HttpHeaders();

		ErrorResponse errorResponse = createErrorResponse(ex.getErrorCode());

		if (!ObjectUtils.isEmpty(ex.getData())) {
			errorResponse.setData(ex.getData());
		}

		return super.handleExceptionInternal(ex, errorResponse, headers, ex.getHttpStatus(), request);
	}

	/**
	 * InternalServerErrorException
	 *
	 * @param ex      the target exception
	 * @param request the current request
	 * @return Error Response Object
	 * @see InternalServerErrorException
	 */
	@ExceptionHandler(InternalServerErrorException.class)
	protected ResponseEntity<Object> handleInternalServerErrorException(InternalServerErrorException ex,
		WebRequest request) {
		log.error("handleInternalServerErrorException : {0}", ex);
		HttpHeaders headers = new HttpHeaders();

		ErrorResponse errorResponse = createErrorResponse(ex.getErrorCode());

		if (!ObjectUtils.isEmpty(ex.getData())) {
			errorResponse.setData(ex.getData());
		}

		return super.handleExceptionInternal(ex, errorResponse, headers, ex.getHttpStatus(), request);
	}

	/**
	 * IllegalAccessException
	 *
	 * @return Error Response Object<br>
	 * HttpStatusCode : 406 - Not Acceptable
	 * @see java.lang.IllegalAccessException
	 */
	protected ResponseEntity<Object> handleIllegalAccessException(IllegalAccessException ex, WebRequest request) {
		log.error("handleIllegalAccessException : {0}", ex);
		HttpHeaders headers = new HttpHeaders();
		return super.handleExceptionInternal(ex, createErrorResponse(ILLEGAL_ACCESS), headers,
			HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	/**
	 * HttpRequestMethodNotSupportedException
	 *
	 * @return Error Response Object<br>
	 * HttpStatusCode : 405 - Method Not Allowed
	 * @see org.springframework.web.HttpRequestMethodNotSupportedException
	 */
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("handleHttpRequestMethodNotSupported : {0}", ex);

		Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods();
		if (!CollectionUtils.isEmpty(supportedMethods)) {
			headers.setAllow(supportedMethods);
		}
		return super.handleExceptionInternal(ex, createErrorResponse(HTTP_REQUEST_METHOD_NOT_SUPPORTED), headers,
			status, request);
	}

	/**
	 * HttpMediaTypeNotSupportedException
	 *
	 * @return Error Response Object<br>
	 * HttpStatusCode : 415 - Unsupported Media Type
	 * @see org.springframework.web.HttpMediaTypeNotSupportedException
	 */
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("handleHttpMediaTypeNotSupported : {0}", ex);

		List<MediaType> mediaTypes = ex.getSupportedMediaTypes();
		if (!CollectionUtils.isEmpty(mediaTypes)) {
			headers.setAccept(mediaTypes);
			if (request instanceof ServletWebRequest servletWebRequest
				&& HttpMethod.PATCH.equals(servletWebRequest.getHttpMethod())) {
				headers.setAcceptPatch(mediaTypes);
			}
		}

		return super.handleExceptionInternal(ex, createErrorResponse(HTTP_REQUEST_METHOD_NOT_SUPPORTED), headers,
			status, request);
	}

	/**
	 * HttpMediaTypeNotSupportedException
	 *
	 * @return Error Response Object<br>
	 * HttpStatusCode : 406 - Not Acceptable
	 * @see org.springframework.web.HttpMediaTypeNotAcceptableException
	 */
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("handleHttpMediaTypeNotAcceptable : {0}", ex);
		return super.handleExceptionInternal(ex, createErrorResponse(HTTP_MEDIA_TYPE_NOT_ACCEPTABLE), headers, status,
			request);
	}

	/**
	 * MissingPathVariableException
	 *
	 * @return Error Response Object<br>
	 * HttpStatusCode : 500 - Internal Server Error
	 * @see org.springframework.web.bind.MissingPathVariableException
	 */
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
		HttpStatus status, WebRequest request) {
		log.error("handleMissingPathVariable : {0}", ex);
		return super.handleExceptionInternal(ex, createErrorResponse(MISSING_PATH_VARIABLE), headers, status, request);
	}

	/**
	 * MissingServletRequestParameterException
	 *
	 * @return Error Response Object<br>
	 * HttpStatusCode : 400 - Bad Request
	 * @see org.springframework.web.bind.MissingServletRequestParameterException
	 */
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("handleMissingServletRequestParameter : {0}", ex);
		return super.handleExceptionInternal(ex, createErrorResponse(MISSING_SERVLET_REQUEST_PARAMETER), headers,
			status, request);
	}

	/**
	 * ServletRequestBindingException
	 *
	 * @return Error Response Object<br>
	 * HttpStatusCode : 400 - Bad Request
	 * @see org.springframework.web.bind.ServletRequestBindingException
	 */
	protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("handleServletRequestBindingException : {0}", ex);
		return super.handleExceptionInternal(ex, createErrorResponse(SERVLET_REQUEST_BINDING), headers, status,
			request);
	}

	/**
	 * ConversionNotSupportedException
	 *
	 * @return Error Response Object<br>
	 * HttpStatusCode : 500 - Internal Server Error
	 * @see org.springframework.beans.ConversionNotSupportedException
	 */
	protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
		HttpHeaders headers,
		HttpStatus status, WebRequest request) {
		log.error("handleConversionNotSupported : {0}", ex);
		return super.handleExceptionInternal(ex, createErrorResponse(CONVERSION_NOT_SUPPORTED), headers, status,
			request);
	}

	/**
	 * TypeMismatchException
	 *
	 * @return Error Response Object<br>
	 * HttpStatusCode : 400 - Bad Request
	 * @see org.springframework.beans.TypeMismatchException
	 */
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
		HttpStatus status,
		WebRequest request) {
		log.error("handleTypeMismatch : {0}", ex);
		return super.handleExceptionInternal(ex, createErrorResponse(TYPE_MISMATCH), headers, status, request);
	}

	/**
	 * HttpMessageNotReadableException
	 *
	 * @return Error Response Object<br>
	 * HttpStatusCode : 400 - Bad Request
	 * @see org.springframework.http.converter.HttpMessageNotReadableException
	 */
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("handleHttpMessageNotReadable : {0}", ex);
		return super.handleExceptionInternal(ex, createErrorResponse(HTTP_MESSAGE_NOT_READABLE), headers, status,
			request);
	}

	/**
	 * HttpMessageNotWritableException
	 *
	 * @return Error Response Object<br>
	 * HttpStatusCode : 500 - Internal Server Error
	 * @see org.springframework.http.converter.HttpMessageNotWritableException
	 */
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
		HttpHeaders headers,
		HttpStatus status, WebRequest request) {
		log.error("handleHttpMessageNotWritable : {0}", ex);
		return super.handleExceptionInternal(ex, createErrorResponse(HTTP_MESSAGE_NOT_WRITABLE), headers, status,
			request);
	}

	/**
	 * MethodArgumentNotValidException
	 *
	 * @return Error Response Object<br>
	 * HttpStatusCode : 400 - Bad Request
	 * @see org.springframework.web.bind.MethodArgumentNotValidException
	 */
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("handleMethodArgumentNotValid : {0}", ex);

		List<String> errors = ex.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(FieldError::getDefaultMessage)
			.toList();

		ErrorResponse errorResponse = createErrorResponse(METHOD_ARGUMENT_NOT_VALID);
		errorResponse.setErrors(errors);

		return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
	}

	/**
	 * MissingServletRequestPartException
	 *
	 * @return Error Response Object<br>
	 * HttpStatusCode : 400 - Bad Request
	 * @see org.springframework.web.multipart.support.MissingServletRequestPartException
	 */
	protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("handleMissingServletRequestPart : {0}", ex);
		return super.handleExceptionInternal(ex, createErrorResponse(MISSING_SERVLET_REQUEST_PART), headers, status,
			request);
	}

	/**
	 * BindException
	 *
	 * @return Error Response Object<br>
	 * HttpStatusCode : 400 - Bad Request
	 * @see org.springframework.validation.BindException
	 */
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
		WebRequest request) {
		log.error("handleBindException : {0}", ex);

		List<String> errors = ex.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(FieldError::getDefaultMessage)
			.toList();

		ErrorResponse errorResponse = createErrorResponse(BIND_ERROR);
		errorResponse.setErrors(errors);

		return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
	}

	/**
	 * NoHandlerFoundException
	 *
	 * @return Error Response Object<br>
	 * HttpStatusCode : 404 - Not Found
	 * @see org.springframework.web.servlet.NoHandlerFoundException
	 */
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
		HttpStatus status, WebRequest request) {
		log.error("handleNoHandlerFoundException : {0}", ex);
		return super.handleExceptionInternal(ex, createErrorResponse(NO_HANDLER_FOUND), headers, status, request);
	}

	/**
	 * AsyncRequestTimeoutException
	 *
	 * @return Error Response Object<Br>
	 * HttpStatusCode : 503 - Service Unavailable
	 * @see org.springframework.web.context.request.async.AsyncRequestTimeoutException
	 */
	protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex,
		HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
		log.error("handleAsyncRequestTimeoutException : {0}", ex);

		if (webRequest instanceof ServletWebRequest servletWebRequest) {
			HttpServletResponse response = servletWebRequest.getResponse();
			if (response != null && response.isCommitted()) {
				if (logger.isWarnEnabled()) {
					logger.warn("Async request timed out");
				}
				return null;
			}
		}

		return super.handleExceptionInternal(ex, createErrorResponse(GlobalErrorCode.ASYNC_REQUEST_TIMEOUT), headers,
			status, webRequest);
	}

	/**
	 * ErrorResponse 객체 생성
	 *
	 * @param errorCode 에러코드
	 * @return ErrorResponse Object
	 */
	private ErrorResponse createErrorResponse(BaseErrorCode errorCode) {
		return new ErrorResponse(errorCode);
	}

	/**
	 * ErrorResponse 객체 생성
	 *
	 * @param errorCode 에러코드
	 * @param message   클라이언트 메시지
	 * @return ErrorResponse Object
	 */
	private ErrorResponse createErrorResponse(BaseErrorCode errorCode, String message) {
		return new ErrorResponse(errorCode, message);
	}

}
