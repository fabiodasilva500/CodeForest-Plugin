package br.usp.each.saeg.code.forest.xml;

import java.math.*;
import java.util.*;
import javax.xml.bind.annotation.*;
import org.apache.commons.lang3.*;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 */
public class XmlClass {

    private String name;
    private BigDecimal score = new BigDecimal(XmlInput.nextScore());
    private int loc;
    private List<XmlMethod> methods = new ArrayList<XmlMethod>();
    private int number;
    private int start;
    private int end;
    private int close;
    private String content;
    private boolean javaInterface;
    
    //Adequação dos atributos a nova versão da Jaguar
    private int cef;
    private int cep;
    private int cnf;
    private int cnp;
    
    private int qtdMethods;
    
  
    //Representa um atributo dentro de uma tag
    @XmlAttribute(name="name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //Representa um atributo dentro de uma tag
    @XmlAttribute(name="suspicious-value")
    public BigDecimal getScore() {
        return score;
    }
    public void setScore(BigDecimal score) {
        if (null != score) {
            this.score = score;
        }
    }

    //Representa uma Tag dentro do XML
    @XmlElement(name="method")
    public List<XmlMethod> getMethods() {
        return methods;
    }
    public void setMethods(List<XmlMethod> methods) {
        this.methods = methods;
    }
    public void addMethod(XmlMethod method) {
        if (method != null) {
            methods.add(method);
        }
    }

    public XmlMethod byName(String name) {
        for (int i = 0, j = methods.size(); i < j; i++) {
            XmlMethod method = methods.get(i);
            if (StringUtils.equals(name, method.getName())) {
                return method;
            }
        }
        return null;
    }

    //Representa um atributo dentro de uma tag
    @XmlAttribute(name="location")
    public int getLoc() {
        return loc;
    }
    public void setLoc(int loc) {
        this.loc = loc;
    }

    //Representa um atributo dentro de uma tag
    @XmlAttribute(name="number")
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    @XmlTransient
    public int getStart() {
        return start;
    }
    public void setStart(int start) {
        this.start = start;
        this.loc = start;
    }

    @XmlTransient
    public int getEnd() {
        return end;
    }
    public void setEnd(int end) {
        this.end = end;
    }

    @XmlTransient
    public int getClose() {
        return close;
    }
    public void setClose(int close) {
        this.close = close;
    }

    @XmlTransient
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    @XmlTransient
    public boolean isJavaInterface() {
        return javaInterface;
    }
    public void setJavaInterface(boolean javaInterface) {
        this.javaInterface = javaInterface;
    }
    
    
    //Criação de novos atributos de acordo com a estrutura da Jaguar
    @XmlAttribute(name="cef")
  	public int getCef() {
		return cef;
	}
  	
	public void setCef(int cef) {
		this.cef = cef;
	}
	
	@XmlAttribute(name="cep")
	public int getCep() {
		return cep;
	}
	
	
	public void setCep(int cep) {
		this.cep = cep;
	}
	
	@XmlAttribute(name="cnf")
	public int getCnf() {
		return cnf;
	}
	public void setCnf(int cnf) {
		this.cnf = cnf;
	}
	
	@XmlAttribute(name="cnp")
	public int getCnp() {
		return cnp;
	}
	public void setCnp(int cnp) {
		this.cnp = cnp;
	}
	
	
}
