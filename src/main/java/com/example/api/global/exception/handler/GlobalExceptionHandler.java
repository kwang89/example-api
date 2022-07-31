package com.example.api.global.exception.handler;

import static com.example.api.global.code.GlobalErrorCode.BIND_ERROR;
import static com.example.api.global.code.GlobalErrorCode.CONVERSION_NOT_SUPPORTED;
import static com.example.api.global.code.GlobalErrorCode.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE;
import static com.example.api.global.code.GlobalErrorCode.HTTP_MESSAGE_NOT_READABLE;
import static com.example.api.global.code.GlobalErrorCode.HTTP_MESSAGE_NOT_WRITABLE;
import static com.example.api.global.code.GlobalErrorCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED;
import static com.example.api.global.code.GlobalErrorCode.METHOD_ARGUMENT_NOT_VALID;
import static com.example.api.global.code.GlobalErrorCode.MISSING_PATH_VARIABLE;
import static com.example.api.global.code.GlobalErrorCode.MISSING_SERVLET_REQUEST_PARAMETER;
import static com.example.api.global.code.GlobalErrorCode.MISSING_SERVLET_REQUEST_PART;
import static com.example.api.global.code.GlobalErrorCode.NO_HANDLER_FOUND;
import static com.example.api.global.code.GlobalErrorCode.SERVLET_REQUEST_BINDING;
import static com.example.api.global.code.GlobalErrorCode.TYPE_MISMATCH;
import static com.example.api.global.code.GlobalErrorCode.UNKNOWN_ERROR;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

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

import com.example.api.dto.ErrorResponse;
import com.example.api.global.code.GlobalErrorCode;
import com.example.api.global.exception.ApiException;
import com.example.api.global.exception.BadRequestException;

import lombok.extern.slf4j.Slf4j;

/**
 * Global Exception Handler.
 * 
 * <p>
 * {@code ResponseEntityExceptionHandler}를 상속 받아 Spring에서 처리하고 있는 기본 에러처리를
 * Overriding
 * <p>
 * 아래 페이지에서 기본적인 Spring MVC 예외처리 목록을 확인
 * {@link https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/ResponseEntityExceptionHandler.html}
 * 
 * @author sk.kwon
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  /**
   * 예상되지 않은 모든 에러 Handle
   * 
   * @see java.lang.Exception
   * 
   * @param ex      Throwed Exception
   * @param request Request Object
   * @return Error Response Object
   */
  @ExceptionHandler(Exception.class)
  private final ResponseEntity<Object> handleUnknownException(Exception ex, WebRequest request) {
    log.error("handleException : {}", ex);
    HttpHeaders headers = new HttpHeaders();
    return super.handleExceptionInternal(ex, createErrorResponse(UNKNOWN_ERROR), headers,
        HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  /**
   * ApiException
   * BadRequestException
   * 
   * @see com.example.api.global.exception.ApiException
   * @see com.example.api.global.exception.BadRequestException
   * 
   * @param ex      Throwed Exception
   * @param request Request Object
   * @return Error Response Object
   */
  @ExceptionHandler({
      ApiException.class, BadRequestException.class
  })
  protected ResponseEntity<Object> handleCustomException(ApiException ex, WebRequest request) {
    log.error("handleCustomException : {}", ex);
    HttpHeaders headers = new HttpHeaders();

    ErrorResponse errorResponse = createErrorResponse(ex.getErrorCode());

    if (!ObjectUtils.isEmpty(ex.getData())) {
      errorResponse.setData(ex.getData());
    }

    return super.handleExceptionInternal(ex,
        errorResponse, headers, ex.getHttpStatus(),
        request);
  }

  /**
   * HttpRequestMethodNotSupportedException
   * 
   * @see org.springframework.web.HttpRequestMethodNotSupportedException
   * 
   * @param ex      Throwed Exception
   * @param request Request Object
   * @return Error Response Object
   *         <p>
   *         HttpStatusCode : 405 - Method Not Allowed
   */
  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    log.error("handleHttpRequestMethodNotSupported : {}", ex);

    Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods();
    if (!CollectionUtils.isEmpty(supportedMethods)) {
      headers.setAllow(supportedMethods);
    }
    return super.handleExceptionInternal(ex, createErrorResponse(HTTP_REQUEST_METHOD_NOT_SUPPORTED), headers, status,
        request);
  }

  /**
   * HttpMediaTypeNotSupportedException
   * 
   * @see org.springframework.web.HttpMediaTypeNotSupportedException
   * 
   * @param ex      Throwed Exception
   * @param request Request Object
   * @return Error Response Object
   *         <p>
   *         HttpStatusCode : 415 - Unsupported Media Type
   */
  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    log.error("handleHttpMediaTypeNotSupported : {}", ex);

    List<MediaType> mediaTypes = ex.getSupportedMediaTypes();
    if (!CollectionUtils.isEmpty(mediaTypes)) {
      headers.setAccept(mediaTypes);
      if (request instanceof ServletWebRequest) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        if (HttpMethod.PATCH.equals(servletWebRequest.getHttpMethod())) {
          headers.setAcceptPatch(mediaTypes);
        }
      }
    }

    return super.handleExceptionInternal(ex, createErrorResponse(HTTP_REQUEST_METHOD_NOT_SUPPORTED), headers, status,
        request);
  }

  /**
   * HttpMediaTypeNotSupportedException
   * 
   * @see org.springframework.web.HttpMediaTypeNotAcceptableException
   * 
   * @param ex      Throwed Exception
   * @param request Request Object
   * @return Error Response Object
   *         <p>
   *         HttpStatusCode : 406 - Not Acceptable
   */
  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    log.error("handleHttpMediaTypeNotAcceptable : {}", ex);
    return super.handleExceptionInternal(ex, createErrorResponse(HTTP_MEDIA_TYPE_NOT_ACCEPTABLE), headers, status,
        request);
  }

  /**
   * MissingPathVariableException
   * 
   * @see org.springframework.web.bind.MissingPathVariableException
   * 
   * @param ex      Throwed Exception
   * @param request Request Object
   * @return Error Response Object
   *         <p>
   *         HttpStatusCode : 500 - Internal Server Error
   */
  @Override
  protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    log.error("handleMissingPathVariable : {}", ex);
    return super.handleExceptionInternal(ex, createErrorResponse(MISSING_PATH_VARIABLE), headers, status, request);
  }

  /**
   * MissingServletRequestParameterException
   * 
   * @see org.springframework.web.bind.MissingServletRequestParameterException
   * 
   * @param ex      Throwed Exception
   * @param request Request Object
   * @return Error Response Object
   *         <p>
   *         HttpStatusCode : 400 - Bad Request
   */
  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    log.error("handleMissingServletRequestParameter : {}", ex);
    return super.handleExceptionInternal(ex, createErrorResponse(MISSING_SERVLET_REQUEST_PARAMETER), headers, status,
        request);
  }

  /**
   * ServletRequestBindingException
   * 
   * @see org.springframework.web.bind.ServletRequestBindingException
   * 
   * @param ex      Throwed Exception
   * @param request Request Object
   * @return Error Response Object
   *         <p>
   *         HttpStatusCode : 400 - Bad Request
   */
  @Override
  protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    log.error("handleServletRequestBindingException : {}", ex);
    return super.handleExceptionInternal(ex, createErrorResponse(SERVLET_REQUEST_BINDING), headers, status, request);
  }

  /**
   * ConversionNotSupportedException
   * 
   * @see org.springframework.beans.ConversionNotSupportedException
   * 
   * @param ex      Throwed Exception
   * @param request Request Object
   * @return Error Response Object
   *         <p>
   *         HttpStatusCode : 500 - Internal Server Error
   */
  @Override
  protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    log.error("handleConversionNotSupported : {}", ex);
    return super.handleExceptionInternal(ex, createErrorResponse(CONVERSION_NOT_SUPPORTED), headers, status, request);
  }

  /**
   * TypeMismatchException
   * 
   * @see org.springframework.beans.TypeMismatchException
   * 
   * @param ex      Throwed Exception
   * @param request Request Object
   * @return Error Response Object
   *         <p>
   *         HttpStatusCode : 400 - Bad Request
   */
  @Override
  protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    log.error("handleTypeMismatch : {}", ex);
    return super.handleExceptionInternal(ex, createErrorResponse(TYPE_MISMATCH), headers, status, request);
  }

  /**
   * HttpMessageNotReadableException
   * 
   * @see org.springframework.http.converter.HttpMessageNotReadableException
   * 
   * @param ex      Throwed Exception
   * @param request Request Object
   * @return Error Response Object
   *         <p>
   *         HttpStatusCode : 400 - Bad Request
   */
  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    log.error("handleHttpMessageNotReadable : {}", ex);
    return super.handleExceptionInternal(ex, createErrorResponse(HTTP_MESSAGE_NOT_READABLE), headers, status, request);
  }

  /**
   * HttpMessageNotWritableException
   * 
   * @see org.springframework.http.converter.HttpMessageNotWritableException
   * 
   * @param ex      Throwed Exception
   * @param request Request Object
   * @return Error Response Object
   *         <p>
   *         HttpStatusCode : 500 - Internal Server Error
   */
  @Override
  protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    log.error("handleHttpMessageNotWritable : {}", ex);
    return super.handleExceptionInternal(ex, createErrorResponse(HTTP_MESSAGE_NOT_WRITABLE), headers, status, request);
  }

  /**
   * MethodArgumentNotValidException
   * 
   * @see org.springframework.web.bind.MethodArgumentNotValidException
   * 
   * @param ex      Throwed Exception
   * @param request Request Object
   * @return Error Response Object
   *         <p>
   *         HttpStatusCode : 400 - Bad Request
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    log.error("handleMethodArgumentNotValid : {}", ex);

    List<String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(e -> e.getDefaultMessage())
        .collect(Collectors.toList());

    ErrorResponse errorResponse = createErrorResponse(METHOD_ARGUMENT_NOT_VALID);
    errorResponse.setErrors(errors);

    return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
  }

  /**
   * MissingServletRequestPartException
   * 
   * @see org.springframework.web.multipart.support.MissingServletRequestPartException
   * 
   * @param ex      Throwed Exception
   * @param request Request Object
   * @return Error Response Object
   *         <p>
   *         HttpStatusCode : 400 - Bad Request
   */
  @Override
  protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    log.error("handleMissingServletRequestPart : {}", ex);
    return super.handleExceptionInternal(ex, createErrorResponse(MISSING_SERVLET_REQUEST_PART), headers, status,
        request);
  }

  /**
   * BindException
   * 
   * @see org.springframework.validation.BindException
   * 
   * @param ex      Throwed Exception
   * @param request Request Object
   * @return Error Response Object
   *         <p>
   *         HttpStatusCode : 400 - Bad Request
   */
  @Override
  protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    log.error("handleBindException : {}", ex);

    List<String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(e -> e.getDefaultMessage())
        .collect(Collectors.toList());

    ErrorResponse errorResponse = createErrorResponse(BIND_ERROR);
    errorResponse.setErrors(errors);

    return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
  }

  /**
   * NoHandlerFoundException
   * 
   * @see org.springframework.web.servlet.NoHandlerFoundException
   * 
   * @param ex      Throwed Exception
   * @param request Request Object
   * @return Error Response Object
   *         <p>
   *         HttpStatusCode : 404 - Not Found
   */
  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    log.error("handleNoHandlerFoundException : {}", ex);
    return super.handleExceptionInternal(ex, createErrorResponse(NO_HANDLER_FOUND), headers, status, request);
  }

  /**
   * AsyncRequestTimeoutException
   * 
   * @see org.springframework.web.context.request.async.AsyncRequestTimeoutException
   * 
   * @param ex      Throwed Exception
   * @param request Request Object
   * @return Error Response Object
   *         <p>
   *         HttpStatusCode : 503 - Service Unavailable
   */
  @Override
  protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex,
      HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
    log.error("handleAsyncRequestTimeoutException : {}", ex);

    if (webRequest instanceof ServletWebRequest) {
      ServletWebRequest servletWebRequest = (ServletWebRequest) webRequest;
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
  private ErrorResponse createErrorResponse(GlobalErrorCode errorCode) {
    return new ErrorResponse(errorCode);
  }

  /**
   * ErrorResponse 객체 생성
   * 
   * @param errorCode 에러코드
   * @param message   클라이언트 메시지
   * @return ErrorResponse Object
   */
  private ErrorResponse createErrorResponse(GlobalErrorCode errorCode, String message) {
    return new ErrorResponse(errorCode, message);
  }

}
