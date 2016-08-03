package br.usp.each.saeg.code.forest.xml;

import java.io.*;
import java.util.*;
import javax.xml.bind.*;
import javax.xml.bind.annotation.*;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 */

//Classe que carrega todo o arquivo XML


//Tag principal do arquivo XML
@XmlRootElement(name="HierarchicalFaultClassification")
public class XmlInput {

    private static final Random random = new Random();
    private List<XmlPackage> packages = new ArrayList<XmlPackage>();
    private Map<String, XmlPackage> namePackage = new HashMap<String, XmlPackage>();
    private String heuristica;
    private int qtdPackages;
    
    
    //Encapsula uma lista
    //@XmlElementWrapper(name="packages") 
    @XmlElement(name="package")
    public List<XmlPackage> getPackages() {
        return packages;
    }
  
    public void setPackages(List<XmlPackage> packages) {
    	System.out.println("Pacote atual:"+packages.size());
        this.packages = packages;
    }

    public XmlPackage byName(String name) {
        if (namePackage.isEmpty()) {
            for (XmlPackage pkg : packages) {
                namePackage.put(pkg.getName(), pkg);
            }
        }
        return namePackage.get(name);
    }

    //Realizando a leitura do arquivo XML
    public static XmlInput unmarshal(File reportLocation) {
    String arquivo = String.valueOf(reportLocation);
    arquivo=arquivo.replace("\\jaguar", "\\.jaguar");
    
    reportLocation = new File(arquivo);
   
    System.out.println("Chegou:"+arquivo);
        try {
            JAXBContext context = JAXBContext.newInstance(XmlInput.class);
            return (XmlInput) context.createUnmarshaller().unmarshal(reportLocation);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

   
    public static float nextScore() {
        return random.nextFloat();
    }
    
    
    @XmlAttribute(name="heuristic")
    public String getHeuristica(){
    System.out.println("Obtendo a heurística:"+heuristica);
      return heuristica;
    }
    
    
    public void setHeuristica(String heuristica){
    System.out.println("Informando a heurística:"+heuristica);
    this.heuristica=heuristica;
    }
    

  
    
}
