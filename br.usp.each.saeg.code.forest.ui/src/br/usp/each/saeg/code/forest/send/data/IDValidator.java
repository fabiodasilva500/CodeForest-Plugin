package br.usp.each.saeg.code.forest.send.data;



import org.eclipse.jface.dialogs.IInputValidator;


public class IDValidator implements IInputValidator{

	@Override
	public String isValid(String text) {
		if(text.length() >= 5){
			return "ID invalido!";
		}
		for(int i = 0; i < text.length(); i++){
			String firstValue = String.valueOf(text.charAt(0));
			if(!firstValue.equalsIgnoreCase("a")){
				return "ID invalido!";
			}
			String secondValue= String.valueOf(text.charAt(1));
			if (!secondValue.equals("0") && !secondValue.equals("1") && !secondValue.equals("2") && !secondValue.equals("3") &&
				!secondValue.equals("4") && !secondValue.equals("5") && !secondValue.equals("6") && !secondValue.equals("7")&&
				!secondValue.equals("8") && !secondValue.equals("9")){
				return "ID invalido!";
			}

			String thirdValue = String.valueOf(text.charAt(2));
			if (!thirdValue.equals("0") && !thirdValue.equals("1") && !thirdValue.equals("2") && !thirdValue.equals("3") &&
					!thirdValue.equals("4") && !thirdValue.equals("5") && !thirdValue.equals("6") && !thirdValue.equals("7") &&
					!thirdValue.equals("8") && !thirdValue.equals("9")){
				return "ID invalido!";
			}
			
			String fourthValue = String.valueOf(text.charAt(2));
			if (!fourthValue.equals("0") && !fourthValue.equals("1") && !fourthValue.equals("2") && !fourthValue.equals("3") &&
					!fourthValue.equals("4") && !fourthValue.equals("5") && !fourthValue.equals("6") && !fourthValue.equals("7") &&
					!fourthValue.equals("8") && !fourthValue.equals("9")){
				return "ID invalido!";
			}


		}
		return null;

	}
}
