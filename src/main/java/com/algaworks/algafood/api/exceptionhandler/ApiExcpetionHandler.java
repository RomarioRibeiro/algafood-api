package com.algaworks.algafood.api.exceptionhandler;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class ApiExcpetionHandler extends ResponseEntityExceptionHandler {

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

		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;

		String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";

		Problema problema = createProblemBuilder(status, problemType, detail).build();

		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		String path = joinPath(ex.getPath());
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		
		String detail = String.format("A propriedade '%s' não existe."
				+ "Corrija ou remova essa propriedade e tente novamente.", path);
		
		Problema problema = createProblemBuilder(status, problemType, detail).build();
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		String path = joinPath(ex.getPath());
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		
		String detail = String.format("A propriedade '%s' recebeu o valor '%s' "
				+ "que é de um tipo inválido. corrija e informe um valor compativel com tipo '%s'.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());

		Problema problema = createProblemBuilder(status, problemType, detail).build();
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> tratarEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;

		ProblemType problemType = ProblemType.ENTIDADE_NAO_ENCONTRADA;

		String detail = ex.getMessage();

		Problema problema = createProblemBuilder(status, problemType, detail).build();
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handlerNegocioException(NegocioException ex, WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;

		ProblemType problemType = ProblemType.ERRO_NEGOCIO;

		String detail = ex.getMessage();

		Problema problema = createProblemBuilder(status, problemType, detail).build();
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {

		HttpStatus status = HttpStatus.CONFLICT;

		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;

		String detail = ex.getMessage();

		Problema problema = createProblemBuilder(status, problemType, detail).build();
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus statusCode, WebRequest request) {

		if (body == null) {
			body = Problema.builder().title(statusCode.getReasonPhrase()).status(statusCode.value()).build();
		} else if (body instanceof String) {
			body = Problema.builder().title((String) body).status(statusCode.value()).build();
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