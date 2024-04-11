package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	MENSAGEM_INCOMPREENSIVEL("/mensafem-incompreencivel", "Mensagem Incompativel"),
	
	ENTIDADE_NAO_ENCONTRADA("/entidadade-nao-encontrada", "Entidade não encontrada"),
	
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de négocio"),
	
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade está em uso");
	;
	
	
	private String url;
	private String title;
	
	ProblemType(String path, String title) {
		this.url =  "https//algafood.com.br" + path;
		this.title = title;
	}
	
	
}
