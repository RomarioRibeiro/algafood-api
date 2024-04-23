package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de Sistema"),
	
	PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro está incorreto"),
	
	MENSAGEM_INCOMPREENSIVEL("/mensafem-incompreencivel", "Mensagem Incompativel"),
	
	RECURSO_NAO_ENCONTRADA("/recurso-nao-encontrada", "Recurso não encontrada"),
	
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de négocio"),
	
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade está em uso");
	
	
	
	private String url;
	private String title;
	
	ProblemType(String path, String title) {
		this.url =  "https//algafood.com.br" + path;
		this.title = title;
	}
	
	
}
