package br.usp.each.saeg.code.forest.xml;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 */
public class XmlStatement {

    private int loc;
    private BigDecimal score = new BigDecimal(XmlInput.nextScore());
    private int start;
    private int end;
    private String content;
    
   //Adequação do código para interpretação do XML da nova versão da Jaguar
    private String xmlnsnsi;
    private String xsiType;
    private String type;
    private int cef;
    private int cep;
    private int cnf;
    private int cnp;
    

    @XmlAttribute(name="location")
    public int getLoc() {
        return loc;
    }
    public void setLoc(int loc) {
        this.loc = loc;
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
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    
    //Criação de novos atributos de acordo com a estrutura da Jaguar
    @XmlAttribute(name="xmlns:xsi")
    public String getXmlnsnsi() {
		return xmlnsnsi;
	}
    
    public void setXmlnsnsi(String xmlnsnsi) {
		this.xmlnsnsi = xmlnsnsi;
	}
    
    @XmlAttribute(name="xsi:type")
	public String getXsiType() {
		return xsiType;
	}
	
	public void setXsiType(String xsiType) {
		this.xsiType = xsiType;
	}
	
	@XmlAttribute(name="type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
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
