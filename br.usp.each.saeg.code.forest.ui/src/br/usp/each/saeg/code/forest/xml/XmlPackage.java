package br.usp.each.saeg.code.forest.xml;

import java.math.*;
import java.util.*;
import javax.xml.bind.annotation.*;

import org.apache.commons.lang3.*;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 */
public class XmlPackage {

    private String name;
    private BigDecimal score = new BigDecimal(XmlInput.nextScore());
    private List<XmlClass> classes = new ArrayList<XmlClass>();
    private int number;
    private int loc = -1;
    private int start;
    private int end;
    
    //Adequação do código para interpretação do XML da nova versão da Jaguar
    private int cef;
    private int cep;
    private int cnf;
    private int cnp;
    private int qtdClasses;

    @XmlAttribute(name="name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute(name="suspicious-value")
    public BigDecimal getScore() {
        return score;
    }
    public void setScore(BigDecimal score) {
        if (null != score) {
            this.score = score;
        }
    }

    //Representa uma tag dentro do XML
    @XmlElement(name="class")
    public List<XmlClass> getClasses() {
    	return classes;
    }
    public void setClasses(List<XmlClass> classes) {
        this.classes = classes;
    }
    public void addClass(XmlClass arg) {
        if (arg != null) {
            classes.add(arg);
        }
    }

    public XmlClass byName(String name) {
        for (XmlClass clz : classes) {
            if (StringUtils.equals(name, clz.getName())) {
                return clz;
            }
        }
        return null;
    }

    @XmlAttribute(name="number")
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public int getLoc() {
        return loc;
    }
    public void setLoc(int loc) {
        this.loc = loc;
    }

    @XmlTransient
    public int getStart() {
        return start;
    }
    public void setStart(int start) {
        this.start = start;
    }

    @XmlTransient
    public int getEnd() {
        return end;
    }
    public void setEnd(int end) {
        this.end = end;
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
