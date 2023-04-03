package com.senac.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//import org.springframework.security.access.AccessDeniedException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import com.senac.domain.exception.EntidadeUtilizadaException;
import com.senac.domain.exception.NotFoundSenacStock;
import com.senac.domain.exception.RegraAppException;

import lombok.extern.slf4j.Slf4j;
 
@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String MSG_ERRO_GENERICO = "An error has occurred.";

	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status).headers(headers).build();
	}
	
	@ExceptionHandler(NotFoundSenacStock.class)
	public ResponseEntity<?> handleEntidadeNaoExisteException(NotFoundSenacStock ex, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		TipoProblema tipoProblema = TipoProblema.RECURSO_NAO_ENCONTRADO;
		String detail = ex.getMessage();
				 
		Problema problema = createProblemBuilder(status, tipoProblema, detail).build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(RegraAppException.class)
	public ResponseEntity<?> handlaNegocioException(RegraAppException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		TipoProblema tipoProblema = TipoProblema.ERRO_NEGOCIO;
		String detail = ex.getMessage();
				
		Problema problema = createProblemBuilder(status, tipoProblema, detail).build();
		 
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntidadeUtilizadaException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeUtilizadaException ex, WebRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		TipoProblema tipoProblema = TipoProblema.ENTIDADE_EM_USO;
		String detail = ex.getMessage(); 
				
		Problema problema = createProblemBuilder(status, tipoProblema, detail).build();
		 
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		if(rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		}else if (rootCause instanceof PropertyBindingException) {
	        return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request); 
	    }
		
		TipoProblema tipoProblema = TipoProblema.MENSAGEM_INCONPREENSIVEL;
		
		String detail = ex.getMessage();
		
		Problema problema = createProblemBuilder(status, tipoProblema, detail).build();
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		TipoProblema tipoProblema = TipoProblema.MENSAGEM_INCONPREENSIVEL;
		
		String path = ex.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining(".")); 
		
		String detail = String.format("A propriedade '%s' recebeu o valor '%s' "
				+ "que é um tipo inválido. Corrija pra um tipo '%s'" , path, ex.getValue(), ex.getTargetType().getSimpleName());
		
		Problema problema = createProblemBuilder(status, tipoProblema, detail)
				.userMessage(MSG_ERRO_GENERICO)
				.build();
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if(body == null) {
			body = Problema.builder()
					.timeStamp(OffsetDateTime.now())
					.detail(ex.getMessage())
					.userMessage(MSG_ERRO_GENERICO)
					.title(status.getReasonPhrase())
					.status(status.value()).build();
		} else if(body instanceof String) {
			body = Problema.builder()
					.timeStamp(OffsetDateTime.now())
					.userMessage(MSG_ERRO_GENERICO)
					.title((String) body)
					.status(status.value()).build(); 
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private Problema.ProblemaBuilder createProblemBuilder(HttpStatus status, TipoProblema tipoProblema, String detail) {
		return Problema.builder()
				.timeStamp(OffsetDateTime.now())
				.detail(detail)
				.status(status.value())
				.title(tipoProblema.getTitle())
				.type(tipoProblema.getUri()); 
	}
	
	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex,
	        HttpHeaders headers, HttpStatus status, WebRequest request) {

	    // Criei o método joinPath para reaproveitar em todos os métodos que precisam
	    // concatenar os nomes das propriedades (separando por ".")
	    String path = ex.getPath().stream()
	    		.map(ref -> ref.getFieldName())
	    		.collect(Collectors.joining(".")); 
	     
	    TipoProblema problemType = TipoProblema.MENSAGEM_INCONPREENSIVEL;
	    String detail = String.format("A propriedade '%s' não existe. "
	            + "Corrija ou remova essa propriedade e tente novamente.", path);

	    Problema problema = createProblemBuilder(status, problemType, detail)
	    		.userMessage(MSG_ERRO_GENERICO)
	    		.build();
	    
	    return handleExceptionInternal(ex, problema, headers, status, request);
	}   
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if(ex instanceof MethodArgumentTypeMismatchException)
			return handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException)ex, headers, status, request);
		
		return super.handleTypeMismatch(ex, headers, status, request);
	}

	private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		TipoProblema tipoProblema =  TipoProblema.PARAMETRO_INVALIDO;
		String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
				+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.", 
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
		
		Problema problema = createProblemBuilder(status, tipoProblema, detail).build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		TipoProblema tipoProblema = TipoProblema.RECURSO_NAO_ENCONTRADO;
		
		String detail = String.format("O recurso %s, que você tentou acessar, é inexistente.", ex.getRequestURL());
		
		Problema problema = createProblemBuilder(status, tipoProblema, detail).build();
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		TipoProblema tipoProblema = TipoProblema.ERRO_DE_SISTEMA;
		String detail = ex.getMessage() == null ? "Não foi possível capturar essa exceção; talvez "
				+ "algum objeto não foi instanciado." : ex.getMessage();
		Problema problema = createProblemBuilder(status, tipoProblema, detail).build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	/**
	 * Capturando uma exceção quando uma regra de validação é ignorada
	 */
	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		
		return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		TipoProblema problemType = TipoProblema.DADOS_INVALIDOS;
	    String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
	    
	    BindingResult bindingResult = ex.getBindingResult();
	    
	    List<Problema.Object> problemFields = bindingResult.getFieldErrors().stream()
	    		.map(fieldError -> {
	    			//Aqui ele pega a mensagem lá do 'messages.properties'
	    			String message = messageSource.getMessage(fieldError, 
	    					LocaleContextHolder.getLocale());
	    			
	    			return Problema.Object.builder()
	    				.name(fieldError.getField())
	    				.userMessage(message)
	    				.build();
	    		})
	    		.collect(Collectors.toList());
	    
	    Problema problema = createProblemBuilder(status, problemType, detail)
	    	.detail(ex.getMessage())
	        .userMessage(detail)
	        .fields(problemFields)
	        .build();
	    
	    return handleExceptionInternal(ex, problema, headers, status, request);
	} 

	private ResponseEntity<Object> handleValidationInternal(Exception ex, HttpHeaders headers,
			HttpStatus status, WebRequest request, BindingResult bindingResult) {
		TipoProblema tipoProblema = TipoProblema.DADOS_INVALIDOS;
		String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
		
		//BindingResult bindingResult = ex.getBindingResult();
		List<Problema.Object> problemaFields = bindingResult.getAllErrors().stream()
				.map(objectErros -> {
					
					String nome = null;
					String message = messageSource.getMessage(objectErros, LocaleContextHolder.getLocale());
					
					if(objectErros instanceof FieldError) {
						nome = ((FieldError) objectErros).getField();
					}
					
					return Problema.Object.builder()
						.name(nome)
						.userMessage(message)
						.build();
					}).collect(Collectors.toList());
		
		Problema problema = createProblemBuilder(status, tipoProblema, detail)
				.detail("Erro: " + ex)
				.userMessage(detail)
				.fields(problemaFields)  
				.build();
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
//	@ExceptionHandler({org.springframework.security.core.AuthenticationException.class})
//	public ResponseEntity<Object> AuthenticationException(InsufficientAuthenticationException ex, HttpServletRequest request) {
//
//		String messageUser = "User and/or password invalid";
//		String messageDeveloper = Optional.ofNullable(ex.getCause()).orElse(ex).toString();
//		
//		List<Error> erros = Arrays.asList(new Error(messageUser, messageDeveloper));
//		
//		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erros);
//	}
//	
//	public static class Error {
//		private String messageUser;
//		private String messageDeveloper;
//
//		public Error(String messageUser, String messageDeveloper) {
//			this.messageUser = messageUser;
//			this.messageDeveloper = messageDeveloper;
//		}
//
//		public String getMessageUser() {
//			return messageUser;
//		}
//
//		public String getMessageDeveloper() {
//			return messageDeveloper;
//		}
//
//	}
	
//	@ExceptionHandler(AccessDeniedException.class)
//	public ResponseEntity<?> handleEntidadeNaoEncontrada(AccessDeniedException ex, WebRequest request) {
//
//	    HttpStatus status = HttpStatus.FORBIDDEN;
//	    ProblemType problemType = ProblemType.ACESSO_NEGADO;
//	    String detail = ex.getMessage();
//
//	    Problem problem = createProblemBuilder(status, problemType, detail)
//	            .userMessage(detail)
//	            .userMessage("You do not have permission to perform this operation.")
//	            .build();
//
//	    return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
//	}
	
}
