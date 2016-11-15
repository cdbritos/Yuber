package tsi2.yuber.common.validator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import tsi2.yuber.common.config.ConstantsConfig;

@FacesValidator(value=ConstantsConfig.CONTRASENIA_VALIDATOR)
public class ContraseniaValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		// TODO Auto-generated method stub
	
	}
}
