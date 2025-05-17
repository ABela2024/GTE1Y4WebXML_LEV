package domGTE1Y4;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;

public class DOMReadGTE1Y4 {
	
	public static void main(String[] args) {
        try {
        	//fájl beolvasás
            File xmlFile = new File("D:\\THE -Programtervezo info\\6_felev\\Webprogramozás\\2025\\GTE1Y4WebXML_LEV\\GTE1Y4_0425\\I.task\\XMLGTE1Y4.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            
            System.out.println("Gyökérelem: " + doc.getDocumentElement().getNodeName());
            System.out.println();
            
            //Éttermek nevének a kiíratása
            //Végigmegyünk az XML dokumentum etterem nevű elemein és egy listában tárolja
            NodeList nodelist = doc.getElementsByTagName("etterem");
            //for ciklussal végigmegyünk a lista elemein
            for(int i = 0; i < nodelist.getLength(); i++) {
            	//Az aktuális elemet egy node változóba helyezzük
            	Node node = nodelist.item(i);
            	//ellenőrizzük, hogy a node ténylegesen elemtipus
            	if(node.getNodeType() == Node.ELEMENT_NODE) {
            		Element element = (Element) node;
            		//elem paramétereinek a kiíratása
            		String nev = element.getElementsByTagName("nev").item(0).getTextContent();
            		String etteremkod = element.getAttribute("ekod");
            		String cim = element.getElementsByTagName("varos").item(0).getTextContent() + " , " + element.getElementsByTagName("utca").item(0).getTextContent() + "." 
            		+element.getElementsByTagName("hazszam").item(0).getTextContent();
            		String csillag = element.getElementsByTagName("csillag").item(0).getTextContent();
            		
            		System.out.println("Étterem neve: " + nev.toUpperCase());
            		System.out.println("  Kódja: " + etteremkod);
            		System.out.println("  Címe: " + cim);
            		System.out.println("  Csillag: " + csillag);
            		
            		//Az éttermekhez tartozó paraméterek függvényeinek a hívása
            		//Főszakacsok
            		foszakacsok(doc, etteremkod);
            		//Szakácsok
            		szakacsok(doc, etteremkod);
            		//GYakornokok
            		gyakornok(doc, etteremkod);
            		//Rendelés
            		rendeles(doc,etteremkod);
            		System.out.println();
            	}
            
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	//főszakácsok listázása
	public static void foszakacsok(Document doc, String etteremkod) {
		NodeList nodelist = doc.getElementsByTagName("foszakacs");
		for(int i = 0; i < nodelist.getLength(); i++) {
        	Node node = nodelist.item(i);
        	if(node.getNodeType() == Node.ELEMENT_NODE) {
        		Element element = (Element) node;
        		String nev = element.getElementsByTagName("nev").item(0).getTextContent();
        		String eletkor = element.getElementsByTagName("eletkor").item(0).getTextContent();
        		String vegzettseg = element.getElementsByTagName("vegzettseg").item(0).getTextContent();
        		String e_f_kod = element.getAttribute("e_f");
        		
        		if(e_f_kod.equals(etteremkod)) {
            		System.out.println("  Főszakács neve: " + nev);
            		System.out.println("    Életkora: " + eletkor);
            		System.out.println("    Végzettsége: " + vegzettseg);
        		}
 
        	}
        		
        }
		
	}
	
	//Szakácsok listázása
	public static void szakacsok(Document doc, String etteremkod) {
		NodeList nodelist = doc.getElementsByTagName("szakacs");
		for(int i = 0; i < nodelist.getLength(); i++) {
        	Node node = nodelist.item(i);
        	if(node.getNodeType() == Node.ELEMENT_NODE) {
        		Element element = (Element) node;
        		String nev = element.getElementsByTagName("nev").item(0).getTextContent();
        		String reszleg = element.getElementsByTagName("reszleg").item(0).getTextContent();
        		String vegzettseg = element.getElementsByTagName("vegzettseg").item(0).getTextContent();
        		String e_sz_kod = element.getAttribute("e_sz");
        		
        		if(e_sz_kod.equals(etteremkod)) {
            		System.out.println("  Szakács neve: " + nev);
            		System.out.println("    Részleg: " + reszleg);
            		System.out.println("    Végzettsége: " + vegzettseg);	
        		}

        	}
        		
        }
		
	}
	
	//GYakornokok listázása
	public static void gyakornok(Document doc, String etteremkod) {
		NodeList nodelist = doc.getElementsByTagName("gyakornok");
		for(int i = 0; i < nodelist.getLength(); i++) {
        	Node node = nodelist.item(i);
        	if(node.getNodeType() == Node.ELEMENT_NODE) {
        		Element element = (Element) node;
        		String nev = element.getElementsByTagName("nev").item(0).getTextContent();
        		String muszak = element.getElementsByTagName("muszak").item(0).getTextContent();
        		String gyakorlat_kezdete = element.getElementsByTagName("kezdete").item(0).getTextContent();
        		String gyakorlat_idotartama = element.getElementsByTagName("idotartam").item(0).getTextContent();
        		String e_gy_kod = element.getAttribute("e_gy");
        		
        		if(e_gy_kod.equals(etteremkod)) {
            		System.out.println("  Gyakornok neve: " + nev);
            		System.out.println("    Műszakja: " + muszak);
            		System.out.println("    Gyakorlat: ");
            		System.out.println("    	Kezdete: " + gyakorlat_kezdete);
            		System.out.println("    	Időtartam: " + gyakorlat_idotartama);
        		}

        	}
        		
        }
		
	}
	
	//Vendégek listázása = > a vendégnek nincs etteremkódja így az a rendelésen keresztül azanosítjuk és a rendelésben hívjuk meg a rendelés kód alapján
	public static void vendeg (Document doc, String keresettVkod) {
		NodeList nodelist = doc.getElementsByTagName("vendeg");
		for(int i = 0; i < nodelist.getLength(); i++) {
			Node node = nodelist.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String vkod = element.getAttribute("vkod");
				if(vkod.equals(keresettVkod)) {
					String nev = element.getElementsByTagName("nev").item(0).getTextContent();
					String eletkor = element.getElementsByTagName("eletkor").item(0).getTextContent();
					String cim = element.getElementsByTagName("varos").item(0).getTextContent() + ", " + element.getElementsByTagName("utca").item(0).getTextContent() + "." 
		            		+element.getElementsByTagName("hazszam").item(0).getTextContent();
					
						System.out.println("  	Vendég neve: " + nev);
	            		System.out.println("    		Életkora: " + eletkor);
	            		System.out.println("    		Címe: " + cim);
				}
				
			}
		}
	}
	
	//rendelések listázása
	public static void rendeles (Document doc, String etteremkod) {
		NodeList nodelist = doc.getElementsByTagName("rendeles");
		for(int i = 0; i < nodelist.getLength(); i++) {
			Node node = nodelist.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String osszeg = element.getElementsByTagName("osszeg").item(0).getTextContent();
				String etel = element.getElementsByTagName("etel").item(0).getTextContent();
				String e_v_ekod = element.getAttribute("e_v_e");
				String e_v_vkod = element.getAttribute("e_v_v");
				
				if(e_v_ekod.equals(etteremkod)) {
					System.out.println("  Rendelt étel: " + etel);
					System.out.println("  	Végösszeg: " + osszeg + " Ft");
					vendeg(doc, e_v_vkod);
				}
			}
		}
	}
	
}

