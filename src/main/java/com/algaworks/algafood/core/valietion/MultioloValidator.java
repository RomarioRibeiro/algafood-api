package com.algaworks.algafood.core.valietion;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultioloValidator implements ConstraintValidator<Multiplo, Number> {
	
	private int numberMultiplo;
	
	@Override
	public void initialize(Multiplo constraintAnnotation) {
		this.numberMultiplo = constraintAnnotation.numero();
	}
	
	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		boolean valido = true;
		
		if(value != null) {
			var valorDecimal = BigDecimal.valueOf(value.doubleValue());
			var multiploDecimal = BigDecimal.valueOf(this.numberMultiplo);
			var resto = valorDecimal.remainder(multiploDecimal);
			
			
			valido =  BigDecimal.ZERO.compareTo(resto) == 0;
		}
		return valido;
	}

}
