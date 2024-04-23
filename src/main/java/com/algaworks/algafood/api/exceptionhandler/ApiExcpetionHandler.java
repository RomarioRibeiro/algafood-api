package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class ApiExcpetionHandler extends ResponseEntityExceptionHandler {
	
	
	
	private static final String MSG_ERRO_GENERICO_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. "
	        + "Tente novamente e se o problema persistir, entre em contato "
	        + "com o administrador do sistema.";
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

	    ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
	    String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
	    
	    BindingResult bindingResult = ex.getBindingResult();
	    
	    List<Problema.Field> problemFields = bindingResult.getFieldErrors().stream()
	    		.map(fieldError -> Problema.Field.builder()
	    				.name(fieldError.getField())
	    				.userMessagem(fieldError.getDefaultMessage())
	    				.build())
	    		.collect(Collectors.toList());
	    
	    Problema problem = createProblemBuilder(status, problemType, detail)
	        .userMessage(detail)
	        .fields(problemFields)
	        .build();
	    
	    return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
	    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;		
	    ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
	    String detail = MSG_ERRO_GENERICO_USUARIO_FINAL;

	    // Importante colocar o printStackTrace (pelo menos por enquanto, que não estamos
	    // fazendo logging) para mostrar a stacktrace no console
	    // Se não fizer isso, você não vai ver a stacktrace de exceptions que seriam importantes
	    // para você durante, especialmente na fase de desenvolvimento
	    ex.printStackTrace();
	    
	    Problema problem = createProblemBuilder(status, problemType, detail)
	    		.timestamp(LocalDateTime.now())
	    		.build();

	    return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}   
	
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADA;

		
		String detail = String.format("O recurso '%s', que você tentou acessar, é inexistente", ex.getRequestURL());

		Problema problem = createProblemBuilder(status, problemType, detail)
				.timestamp(LocalDateTime.now())
				.userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
				.build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch(
					(MethodArgumentTypeMismatchException) ex, headers, status, request);
		}
	
		return super.handleTypeMismatch(ex, headers, status, request);
	}
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
			MethodArgumentTypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;

		String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
				+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
		
		
		
		Problema problem = createProblemBuilder(status, problemType, detail)
				.timestamp(LocalDateTime.now())
				.userMessage(detail)
				.build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	
//Solução a cima
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		if (rootCause instanceof InvalidFormatException) {
			return  handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		}
		
		if(rootCause instanceof PropertyBindingException) {
			return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
		}
		
		if (rootCause instanceof MethodArgumentTypeMismatchException) {
			return handlerMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException) rootCause, headers, status, request);
		}
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;

		String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";

		Problema problema = createProblemBuilder(status, problemType, detail)
				.timestamp(LocalDateTime.now())
				.userMessage(detail)
				.build();

		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	

	private ResponseEntity<Object> handlerMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
		
		String detail = String.format("Parâmetro de URL '%s' recebeu o valor '%s', "
				+ "que é de um tipo inválido Corrija e informe um valor compatível com tipo '%s'.", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
		
		Problema problema = createProblemBuilder(status, problemType, detail)
				.timestamp(LocalDateTime.now())
				.userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		String path = joinPath(ex.getPath());
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		
		String detail = String.format("A propriedade '%s' não existe."
				+ "Corrija ou remova essa propriedade e tente novamente.", path);
		
		Problema problema = createProblemBuilder(status, problemType, detail)
				.timestamp(LocalDateTime.now())
				.userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
				.build();
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		String path = joinPath(ex.getPath());
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		
		String detail = String.format("A propriedade '%s' recebeu o valor '%s' "
				+ "que é de um tipo inválido. corrija e informe um valor compativel com tipo '%s'.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());

		Problema problema = createProblemBuilder(status, problemType, detail)
				.timestamp(LocalDateTime.now())
				.userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
				.build();
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> tratarEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;

		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADA;

		String detail = ex.getMessage();

		Problema problema = createProblemBuilder(status, problemType, detail)
				.timestamp(LocalDateTime.now())
				.userMessage(detail)
				.build();
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handlerNegocioException(NegocioException ex, WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;

		ProblemType problemType = ProblemType.ERRO_NEGOCIO;

		String detail = ex.getMessage();

		Problema problema = createProblemBuilder(status, problemType, detail)
				.timestamp(LocalDateTime.now())
				.userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
				.build();
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {

		HttpStatus status = HttpStatus.CONFLICT;

		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;

		String detail = ex.getMessage();

		Problema problema = createProblemBuilder(status, problemType, detail)
				.timestamp(LocalDateTime.now())
				.userMessage(detail)
				.build();
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus statusCode, WebRequest request) {

		if (body == null) {
			body = Problema.builder().title(statusCode.getReasonPhrase()).status(statusCode.value()).userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL).timestamp(LocalDateTime.now()).build();
		} else if (body instanceof String) {
			body = Problema.builder().title((String) body).status(statusCode.value()).userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL).timestamp(LocalDateTime.now()).build();
		}
		return super.handleExceptionInternal(ex, body, headers, statusCode, request);
	}

	private Problema.ProblemaBuilder createProblemBuilder(HttpStatus status, ProblemType problemTupe, String detail) {
		return Problema.builder().status(status.value()).type(problemTupe.getUrl()).title(problemTupe.getTitle())
				.detail(detail);
	}
	public String joinPath(List<Reference> references) {
		return references.stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
	}
	
}
