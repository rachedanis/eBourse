package com.bfigroupe.ebourse.web.error;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bfigroupe.ebourse.web.util.GenericResponse;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messages;

	Locale locale;

	public RestResponseEntityExceptionHandler() {
		super();
		locale = LocaleContextHolder.getLocale();
	}

	// API

	// 400
	@Override
	protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		logger.error("400 Status Code", ex);
		locale = LocaleContextHolder.getLocale();
		final BindingResult result = ex.getBindingResult();
		final GenericResponse bodyOfResponse = new GenericResponse(result.getFieldErrors(), result.getGlobalErrors());
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.error("400 Status Code", ex);
		locale = LocaleContextHolder.getLocale();
		final BindingResult result = ex.getBindingResult();
		final GenericResponse bodyOfResponse = new GenericResponse(result.getFieldErrors(), result.getGlobalErrors());
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ InvalidOldPasswordException.class })
	public ResponseEntity<Object> handleInvalidOldPassword(final RuntimeException ex, final WebRequest request) {
		logger.error("400 Status Code", ex);
		locale = LocaleContextHolder.getLocale();
		final GenericResponse bodyOfResponse = new GenericResponse(
				messages.getMessage("message.invalidOldPassword", null, locale), "InvalidOldEmail");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ RequestException.class })
	public ResponseEntity<Object> requestException(final RuntimeException ex, final WebRequest request) {
		logger.error("409 Status Code", ex);
		locale = LocaleContextHolder.getLocale();
		final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage(ex.getMessage(), null, locale),
				"InvalidRequest");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler({ ExecutionException.class })
	public ResponseEntity<Object> executionException(final RuntimeException ex, final WebRequest request) {
		logger.error("409 Status Code", ex);
		locale = LocaleContextHolder.getLocale();
		final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage(ex.getMessage(), null, locale),
				"InvalidExecution");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler({ MarketValueException.class })
	public ResponseEntity<Object> marketValueException(final RuntimeException ex, final WebRequest request) {
		logger.error("409 Status Code", ex);
		locale = LocaleContextHolder.getLocale();
		final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage(ex.getMessage(), null, locale),
				"InvalidMarketValue");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
	
	@ExceptionHandler({ UserException.class })
	public ResponseEntity<Object> userException(final RuntimeException ex, final WebRequest request) {
		logger.error("409 Status Code", ex);
		locale = LocaleContextHolder.getLocale();
		final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage(ex.getMessage(), null, locale),
				"InvalidUser");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler({ ValueException.class })
	public ResponseEntity<Object> valueException(final RuntimeException ex, final WebRequest request) {
		logger.error("409 Status Code", ex);
		locale = LocaleContextHolder.getLocale();
		final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage(ex.getMessage(), null, locale),
				"InvalidValue");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler({ PortfolioException.class })
	public ResponseEntity<Object> portfolioException(final RuntimeException ex, final WebRequest request) {
		logger.error("409 Status Code", ex);
		locale = LocaleContextHolder.getLocale();
		final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage(ex.getMessage(), null, locale),
				"InvalidPortfolio");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler({ BankAccountException.class })
	public ResponseEntity<Object> bankAccountException(final RuntimeException ex, final WebRequest request) {
		logger.error("409 Status Code", ex);
		locale = LocaleContextHolder.getLocale();
		final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage(ex.getMessage(), null, locale),
				"InvalidBankAccount");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler({ OrderException.class })
	public ResponseEntity<Object> orderException(final RuntimeException ex, final WebRequest request) {
		logger.error("409 Status Code", ex);
		locale = LocaleContextHolder.getLocale();
		final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage(ex.getMessage(), null, locale),
				"InvalidOrder");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	// 404
	@ExceptionHandler({ UserNotFoundException.class })
	public ResponseEntity<Object> handleUserNotFound(final RuntimeException ex, final WebRequest request) {
		logger.error("404 Status Code", ex);
		locale = LocaleContextHolder.getLocale();
		final GenericResponse bodyOfResponse = new GenericResponse(
				messages.getMessage("message.userNotFound", null, locale), "UserNotFound");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	// 409
	@ExceptionHandler({ UserAlreadyExistException.class })
	public ResponseEntity<Object> handleUserAlreadyExist(final RuntimeException ex, final WebRequest request) {
		logger.error("409 Status Code", ex);
		locale = LocaleContextHolder.getLocale();
		final GenericResponse bodyOfResponse = new GenericResponse(
				messages.getMessage("message.regError", null, locale), "UserAlreadyExist");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	// 500
	@ExceptionHandler({ MailAuthenticationException.class })
	public ResponseEntity<Object> handleMail(final RuntimeException ex, final WebRequest request) {
		logger.error("500 Status Code", ex);
		locale = LocaleContextHolder.getLocale();
		final GenericResponse bodyOfResponse = new GenericResponse(
				messages.getMessage("message.email.config.error", null, locale), "MailError");
		return new ResponseEntity<Object>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
		logger.error("500 Status Code", ex);
		locale = LocaleContextHolder.getLocale();
		final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.error", null, locale),
				"InternalError");
		return new ResponseEntity<Object>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
