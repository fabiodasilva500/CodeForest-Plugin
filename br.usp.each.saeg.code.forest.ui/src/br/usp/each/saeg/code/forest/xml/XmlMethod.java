package br.usp.each.saeg.code.forest.xml;

import java.math.*;
import java.util.*;

import javax.swing.JOptionPane;
import javax.xml.bind.annotation.*;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 */
public class XmlMethod {

    private String name;
    private BigDecimal score = new BigDecimal(XmlInput.nextScore());
    private int loc;
    private List<XmlStatement> statements = new ArrayList<XmlStatement>();
    private int number;
    private int start;
    private int end;
    private int close;
    private String content;
    private Integer scriptPosition;
    private Float scriptScore;
    
    //Adequação do código para interpretação do XML da técnica line
    private int cef;
    private int cep;
    private int cnf;
    private int cnp;
    
    //Adequação para que a CodeForest também seja capaz de interpretar a técnica DUA
    private int def;
    private int index;
    private int target;
    private int use;
    private String var;
    
   
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
    @XmlElement(name="requirement")
    public List<XmlStatement> getStatements() {
        return statements;
    }
    public void setStatements(List<XmlStatement> statements) {
        this.statements = statements;
    }
    public void addStatements(XmlStatement statement) {
        if (statement != null) {
            statements.add(statement);
        }
    }

    @XmlAttribute(name="location")
    public int getLoc() {
        return loc;
    }
    public void setLoc(int loc) {
        this.loc = loc;
    }

    public XmlStatement byRelativeLoc(Integer arg) {
        List<XmlStatement> result = new ArrayList<XmlStatement>();
        for (int i = 0, j = statements.size(); i < j; i++) {
            XmlStatement stmt = statements.get(i);
            if (arg.equals(stmt.getLoc() - loc)) {
                result.add(stmt);
            }
        }
        return max(result);
    }

    public XmlStatement byAbsoluteLoc(Integer arg) {
        List<XmlStatement> result = new ArrayList<XmlStatement>();
        for (int i = 0, j = statements.size(); i < j; i++) {
            XmlStatement stmt = statements.get(i);
            if (arg.equals(stmt.getLoc())) {
                result.add(stmt);
            }
        }
        return max(result);
    }

    private XmlStatement max(List<XmlStatement> result) {
        if (result.isEmpty()) {
            return null;
        }
        Collections.sort(result, new Comparator<XmlStatement>() {
            @Override
            public int compare(XmlStatement o1, XmlStatement o2) {
                return o2.getScore().compareTo(o1.getScore());
            }
        });
        return result.get(0);
    }

    @XmlAttribute(name="name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
 
    	//System.out.println("Recebendo o nome do método:"+name);
        this.name = name;
    }

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
        this.setLoc(start);
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

    @XmlAttribute(name="position")
    public Integer getScriptPosition() {
        return scriptPosition;
    }
    public void setScriptPosition(Integer scriptPosition) {
        if (scriptPosition != null) {
            this.scriptPosition = scriptPosition;
        } else {
            this.scriptPosition = 0;
        }
    }

    @XmlAttribute(name="methodsusp")
    public Float getScriptScore() {
        return scriptScore;
    }
    public void setScriptScore(Float scriptScore) {
        if (scriptScore != null) {
            this.scriptScore = scriptScore;
        } else {
            this.scriptScore = 0F;
        }

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
	
	@XmlAttribute(name="def")
	public int getDef() {
		return def;
	}
	public void setDef(int def) {
		this.def = def;
	}
	
	@XmlAttribute(name="index")
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	@XmlAttribute(name="target")
	public int getTarget() {
		return target;
	}
	public void setTarget(int target) {
		this.target = target;
	}
	
	@XmlAttribute(name="use")
	public int getUse() {
		return use;
	}
	public void setUse(int use) {
		this.use = use;
	}
	
	@XmlAttribute(name="var")
	public String getVar() {
		return var;
	}
	public void setVar(String var) {
		this.var = var;
	}
	
	
}
