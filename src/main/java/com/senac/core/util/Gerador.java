package com.senac.core.util;

import org.springframework.stereotype.Component;

@Component
public class Gerador {

	private Long dado;
    private Long cont = 1L;
    private String num = "";

    public void sigla(Long inicio) {
        this.dado = inicio;
           
           if((this.dado >= 9) || (this.dado<100)) {
        	   Long can = cont + this.dado;
               num = "000" + can; 
           }
           if (this.dado < 9) {
        	   Long can = cont + this.dado;
               num = "0000" + can; 
           }
          
    }

    public String sigla() {
        return "SENAC-" + this.num;
    }

    public String senha() {
    	int qtdeMaximaCaracteres = 6;
        String[] caracteres = { "0", "1", "b", "2", "4", "5", "6", "7", "8",
                    "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
                    "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
                    "x", "y", "z","+","-","/","*","_","!","@","$","%","&"};
        
    	StringBuilder senha = new StringBuilder();

        for (int i = 0; i < qtdeMaximaCaracteres; i++) {
            int posicao = (int) (Math.random() * caracteres.length);
            senha.append(caracteres[posicao]);
        }
        return senha.toString();
        
    }
	
}
