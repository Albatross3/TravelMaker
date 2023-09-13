package shop.zip.travel.global.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.zip.travel.domain.post.travelogue.exception.InvalidPublishTravelogueException;
import shop.zip.travel.domain.post.travelogue.exception.NoAuthorizationException;
import shop.zip.travel.global.error.exception.CustomNotFoundException;
import shop.zip.travel.global.error.exception.DuplicatedException;
import shop.zip.travel.global.error.exception.InvalidTokenException;
import shop.zip.travel.global.error.exception.NotMatchException;
import shop.zip.travel.global.error.exception.NotVerifiedCodeException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(BindException.class)
	public ResponseEntity<ErrorResponse> handleBindException(BindException e) {
		log.info("BindException : ", e);
		int httpStatus = ErrorCode.BINDING_WRONG.getStatusValue();
		String message = ErrorCode.BINDING_WRONG.getMessage();
		return ResponseEntity.status(httpStatus).body(new ErrorResponse(message));
	}

	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException e) {
		log.info("InvalidTokenException : ", e);
		int httpStatus = e.getErrorCode().getStatusValue();
		String message = e.getErrorCode().getMessage();
		return ResponseEntity.status(httpStatus).body(new ErrorResponse(message));
	}

	@ExceptionHandler(CustomNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleCustomNotFoundException(CustomNotFoundException e) {
		log.info("CustomNotFoundException : ", e);
		int httpStatus = e.getErrorCode().getStatusValue();
		String message = e.getErrorCode().getMessage();
		return ResponseEntity.status(httpStatus).body(new ErrorResponse(message));
	}

	@ExceptionHandler(DuplicatedException.class)
	public ResponseEntity<ErrorResponse> handleDuplicatedException(DuplicatedException e) {
		log.info("DuplicatedException : ", e);
		int httpStatus = e.getErrorCode().getStatusValue();
		String message = e.getErrorCode().getMessage();
		return ResponseEntity.status(httpStatus).body(new ErrorResponse(message));
	}

	@ExceptionHandler(NotVerifiedCodeException.class)
	public ResponseEntity<ErrorResponse> handleNotVerifiedCodeException(NotVerifiedCodeException e) {
		log.info("NotVerifiedCodeException : ", e);
		int httpStatus = e.getErrorCode().getStatusValue();
		String message = e.getErrorCode().getMessage();
		return ResponseEntity.status(httpStatus).body(new ErrorResponse(message));
	}

	@ExceptionHandler(NotMatchException.class)
	public ResponseEntity<ErrorResponse> handleNotMatchException(NotMatchException e) {
		log.info("NotMatchException : ", e);
		int httpStatus = e.getErrorCode().getStatusValue();
		String message = e.getErrorCode().getMessage();
		return ResponseEntity.status(httpStatus).body(new ErrorResponse(message));
	}

	@ExceptionHandler(InvalidPublishTravelogueException.class)
	public ResponseEntity<ErrorResponse> handleInvalidPublishTravelogueException(
			InvalidPublishTravelogueException e) {
		log.info("InvalidPublishTravelogueException : ", e);
		int httpStatus = e.getErrorCode().getStatusValue();
		String message = e.getErrorCode().getMessage();
		return ResponseEntity.status(httpStatus).body(new ErrorResponse(message));
	}

	@ExceptionHandler(NoAuthorizationException.class)
	public ResponseEntity<ErrorResponse> handleNoAuthorizationException(NoAuthorizationException e) {
		log.info("NoAuthorizationException : ", e);
		int httpStatus = e.getErrorCode().getStatusValue();
		String message = e.getErrorCode().getMessage();
		return ResponseEntity.status(httpStatus).body(new ErrorResponse(message));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
		log.info("IllegalArgumentException: ", e);
		int httpStatus = HttpStatus.BAD_REQUEST.value();
		String message = e.getMessage();
		return ResponseEntity.status(httpStatus).body(new ErrorResponse(message));
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException e) {
		log.info("IllegalStateException: ", e);
		int httpStatus = HttpStatus.BAD_REQUEST.value();
		String message = e.getMessage();
		return ResponseEntity.status(httpStatus).body(new ErrorResponse(message));
	}

	@ExceptionHandler(BeanInstantiationException.class)
	public ResponseEntity<ErrorResponse> handleBeanInstantiationException(
			BeanInstantiationException e) {
		log.info("BeanInstantiationException: ", e);
		int httpStatus = HttpStatus.BAD_REQUEST.value();
		String message = e.getCause().getMessage();
		return ResponseEntity.status(httpStatus).body(new ErrorResponse(message));
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
		log.error("RuntimeException : ", e);
		return ResponseEntity.internalServerError().build();
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		log.error("Exception : ", e);
		return ResponseEntity.internalServerError().build();
	}
}
