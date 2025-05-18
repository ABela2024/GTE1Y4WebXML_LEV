package hu.dom.parseGTE1Y4;

import java.io.File;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomReadGTE1Y4 {

	public static void main(String[] args) {
		
		try {
        	//fájl beolvasás
            File xmlFile = new File("D:\\THE -Programtervezo info\\6_felev\\Webprogramozás\\2025\\GTE1Y4WebXML_LEV\\WebXMLSemTaskGTE1Y4\\XMLGTE1Y4.xml");
            //XML dokumentum feldolgozása
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            //Az XML dokumentum DOM struktúrába rendezése
            Document doc = builder.parse(xmlFile);
            //A dokumentum normalizálása
            doc.getDocumentElement().normalize();
            
     
            //Részlegek kiíratása konzolra -> Külön függvény
            System.out.println("========================GYÁRTÁS========================");
            gyartas(doc);
            //Minőségellenőrzés adatainak kiíratása konzolra -> Külön függvény
            System.out.println("===================MINŐSÉGELLENŐRZÉS===================");
            minosegellenorzes(doc);
            //Minőségbiztosítás adatainak kiíratása konzolra -> Külön függvény
            System.out.println("===================MINŐSÉGBIZTOSÍTÁS===================");
            minosegbiztositas(doc);
            //Vevő adatainak kiíratása konzolra -> Külön függvény
            System.out.println("==========================VEVŐ=========================");
            vevo(doc);
            //Szállítás adatainak kiíratása konzolra -> Külön függvény
            System.out.println("==========================SZÁLLÍTÁS=========================");
            szallitas(doc);
            //Rendelések kilistázása konzolra + a listázott adatok a DomReadGTE1Y4 gyökérkönyvtárba "rendelesek_output.txt" fájlba kiírása -> külön függvény
            System.out.println("==========================RENDELÉSEK LISTÁZÁSA=========================");
            try (PrintWriter writer = new PrintWriter("rendelesek_output.txt")) {
                listazas(doc, writer); 
            }

            
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		
	}
	
	public static void gyartas (Document doc) {
		
		//Részlegek neve és címe
		//XML dokumentum lekérdezi az összes olyan elemet aminek a tagneve "gyartas" és listába rendezi
		NodeList reszlegList = doc.getElementsByTagName("gyartas");
		//végigmegyünk a lista elemein
		for(int i = 0; i < reszlegList.getLength(); i++) {
			//Lekéri a for ciklus alapján az aktuális lista elemet
			Node gyartasNode = reszlegList.item(i);
			
			//ellenőrzi, hogy a kapott elem az valóban egy XML elem
			if(gyartasNode.getNodeType() == Node.ELEMENT_NODE) {
				
				//Az elem átalakítása Element tipussá 
				Element reszlegElem = (Element) gyartasNode;
				
				//Az aktuális részleg elem nevének lekérése
				String reszleg = reszlegElem.getElementsByTagName("reszleg").item(0).getTextContent();
				//Az aktuális részleg elem cimének a lekérése
				String cim = reszlegElem.getElementsByTagName("cim").item(0).getTextContent();
				
				//A lekért név és cím adatok kiíratása konzolra
				System.out.println("*********************************************");
				System.out.println("Részleg neve: " + reszleg.toUpperCase());
				System.out.println("Részleg címe: " + cim);
				System.out.println();
				
				//Dolgozók kiirítása a részlegeken belül => ebben a formában "block-ént jeleníti meg az adatokat
				NodeList dolgozoList = reszlegElem.getElementsByTagName("dolgozo");
				for(int j = 0; j < dolgozoList.getLength(); j++) {
					Node dolgozoNode = dolgozoList.item(j);
					
					if(dolgozoNode.getNodeType() == Node.ELEMENT_NODE) {
						Element dolgozoElem = (Element) dolgozoNode;
						
						String dolgozo_nev = dolgozoElem.getElementsByTagName("nev").item(0).getTextContent();
						String dolgozo_cim = dolgozoElem.getElementsByTagName("cim").item(0).getTextContent();
						String dolgozo_ID = dolgozoElem.getAttribute("DID");
						
						System.out.println("Dolgozó ID: " + dolgozo_ID);
						System.out.println("Dolgozó neve: " + dolgozo_nev);
						System.out.println("Dolgozó címe: " + dolgozo_cim);
						System.out.println();
						
					}
				}
				
				//Termékek kiíratása amiket a részlegek gyártanak
				NodeList termekList = reszlegElem.getElementsByTagName("termek");
				for(int k = 0; k < termekList.getLength(); k++) {
					Node termekNode = termekList.item(k);
					
					if(termekNode.getNodeType() == Node.ELEMENT_NODE) {
						Element termekElem = (Element) termekNode;
						
						String termek_nev = termekElem.getElementsByTagName("nev").item(0).getTextContent();
						String termek_ID = termekElem.getAttribute("TID");
						
						System.out.println("Termék ID: " + termek_ID);
						System.out.println("Termék neve: " + termek_nev);
						System.out.println();
						
					}
				}
			}
		}
	}
	//Minőségellenőrzők kilistázása
	public static void minosegellenorzes(Document doc) {
		NodeList minosegellList = doc.getElementsByTagName("minosegellenorzes");
		for(int i = 0; i < minosegellList.getLength(); i++) {
			Node minosegellNode = minosegellList.item(i);
			if(minosegellNode.getNodeType() == Node.ELEMENT_NODE) {
				Element minosegellElem = (Element) minosegellNode;
				
				String minosegellID = minosegellElem.getAttribute("meo_id");
				String nev = minosegellElem.getElementsByTagName("dolgozo_neve").item(0).getTextContent();
				String cim = minosegellElem.getElementsByTagName("varos").item(0).getTextContent() + ", " + minosegellElem.getElementsByTagName("utca").item(0).getTextContent() +
						" " + minosegellElem.getElementsByTagName("hazszam").item(0).getTextContent() + ".";
				
				System.out.println("Minőségellenőrző ID: " + minosegellID);
				System.out.println("Minőségellenőrző neve: " + nev);
				System.out.println("Minőségellenőrző címe: " + cim);
				System.out.println();
			}
		}
	}
	//Minőségbiztosítók kilistázása
	public static void minosegbiztositas(Document doc) {
		NodeList minosegbizList = doc.getElementsByTagName("minosegbiztositas");
		for(int i = 0; i < minosegbizList.getLength(); i++) {
			Node minosegbizNode = minosegbizList.item(i);
			if(minosegbizNode.getNodeType() == Node.ELEMENT_NODE) {
				Element minosegbizElem = (Element) minosegbizNode;
				
				String minosegbizID = minosegbizElem.getAttribute("mbo_id");
				String nev = minosegbizElem.getElementsByTagName("dolgozo_neve").item(0).getTextContent();
				String cim = minosegbizElem.getElementsByTagName("varos").item(0).getTextContent() + ", " + minosegbizElem.getElementsByTagName("utca").item(0).getTextContent() +
						" " + minosegbizElem.getElementsByTagName("hazszam").item(0).getTextContent() + ".";
				
				System.out.println("Minőségbiztosító ID: " + minosegbizID);
				System.out.println("Minőségbiztosító neve: " + nev);
				System.out.println("Minőségbiztosító címe: " + cim);
				System.out.println();
			}
		}
	}
	
	//Vevők kilistázása
	public static void vevo(Document doc) {
		NodeList vevoList = doc.getElementsByTagName("vevo");
		for(int i = 0; i < vevoList.getLength(); i++) {
			Node vevoNode = vevoList.item(i);
			if(vevoNode.getNodeType() == Node.ELEMENT_NODE) {
				Element vevoElem = (Element) vevoNode;
				
				String vevoID = vevoElem.getAttribute("vevoID");
				String nev = vevoElem.getElementsByTagName("nev").item(0).getTextContent();
				String cim = vevoElem.getElementsByTagName("varos").item(0).getTextContent() + ", " + vevoElem.getElementsByTagName("utca").item(0).getTextContent() +
						" " + vevoElem.getElementsByTagName("hazszam").item(0).getTextContent() + ".";
				String rendelesID = vevoElem.getAttribute("rendelesszam");
				String jaratID = vevoElem.getAttribute("jaratszam");
				
				System.out.println("Vevő ID: " + vevoID);
				System.out.println("Vevő neve: " + nev);
				System.out.println("Vevő címe: " + cim);
				System.out.println();
			}
		}
	}
	
	//Szállítók kilistázása
	public static void szallitas(Document doc) {
		NodeList szallitoList = doc.getElementsByTagName("szallitas");
		for(int i = 0; i < szallitoList.getLength(); i++) {
			Node szallitasNode = szallitoList.item(i);
			if(szallitasNode.getNodeType() == Node.ELEMENT_NODE) {
				Element szallitasElem = (Element) szallitasNode;
				
				String szallitasID = szallitasElem.getAttribute("jaratszam");
				String nev = szallitasElem.getElementsByTagName("cegnev").item(0).getTextContent();
				String tipus = szallitasElem.getElementsByTagName("tipus").item(0).getTextContent();
				String muszaki = szallitasElem.getElementsByTagName("muszaki_ervenyesseg").item(0).getTextContent();
				String rendszam = szallitasElem.getElementsByTagName("rendszam").item(0).getTextContent();
				
				System.out.println("Szállítás kódja: " + szallitasID);
				System.out.println("Szállító cég neve: " + nev);
				System.out.println("	Autó adatai: ");
				System.out.println("		Tipus: " + tipus);
				System.out.println("		Műszaki érvényessége: " + muszaki);
				System.out.println("		Rendszám: " + rendszam);
				System.out.println();
			}
		}
	}
	
	//Rendelések listázása
	public static void listazas(Document doc, PrintWriter writer) {
	    //Gyártás elemek lekérése az XML dokumentumból
		NodeList gyartasok = doc.getElementsByTagName("gyartas");

	    for (int i = 0; i < gyartasok.getLength(); i++) {
	        //Egy adott gyártási elem beolvsása
	    	Element gyartas = (Element) gyartasok.item(i);
	        //A beolvasott gyártási elem kiolvasása és kiírása
	    	String reszleg = gyartas.getElementsByTagName("reszleg").item(0).getTextContent();
	        String cim = gyartas.getElementsByTagName("cim").item(0).getTextContent();

	        log("Részleg: " + reszleg.toUpperCase(), writer);
	        log("Cím: " + cim, writer);
	        log("\nDolgozók:", writer);

	        //A gyártó részleg beolvasása alapján a dologzók listázása
	        NodeList dolgozok = gyartas.getElementsByTagName("dolgozo");
	        for (int j = 0; j < dolgozok.getLength(); j++) {
	            Element dolgozo = (Element) dolgozok.item(j);
	            String nev = dolgozo.getElementsByTagName("nev").item(0).getTextContent();
	            String dcim = dolgozo.getElementsByTagName("cim").item(0).getTextContent();
	            log("- " + nev + " (" + dcim + ")", writer);
	        }

	        log("\nTermékek:", writer);
	        //A gyártó részleg beolvasása alapján a termékek listázása
	        NodeList termekek = gyartas.getElementsByTagName("termek");
	        for (int k = 0; k < termekek.getLength(); k++) {
	            Element termek = (Element) termekek.item(k);
	            String termekNev = termek.getElementsByTagName("nev").item(0).getTextContent();
	            String tid = termek.getAttribute("TID");
	            String meo_id = termek.getAttribute("meo_id");
	            String mbo_id = termek.getAttribute("mbo_id");
	            String vevoID = termek.getAttribute("vevoID");

	            log("  - Termék: " + termekNev, writer);
	            log("    Termék ID: " + tid, writer);

	            // Minőségellenőrzés
	            Element meo = findElementById(doc, "minosegellenorzes", "meo_id", meo_id);
	            if (meo != null) {
	                Element cimElem = (Element) meo.getElementsByTagName("cim").item(0);
	                String dolgozoNev = meo.getElementsByTagName("dolgozo_neve").item(0).getTextContent();
	                String datum = meo.getElementsByTagName("datum").item(0).getTextContent();
	                String varos = cimElem.getElementsByTagName("varos").item(0).getTextContent();
	                String utca = cimElem.getElementsByTagName("utca").item(0).getTextContent();
	                String hazszam = cimElem.getElementsByTagName("hazszam").item(0).getTextContent();
	                log("    MEO: " + dolgozoNev + ", " + datum + ", " + varos + ", " + utca + " " + hazszam, writer);
	            }

	            // Minőségbiztosítás
	            Element mbo = findElementById(doc, "minosegbiztositas", "mbo_id", mbo_id);
	            if (mbo != null) {
	                Element cimElem = (Element) mbo.getElementsByTagName("cim").item(0);
	                String dolgozoNev = mbo.getElementsByTagName("dolgozo_neve").item(0).getTextContent();
	                String datum = mbo.getElementsByTagName("datum").item(0).getTextContent();
	                String varos = cimElem.getElementsByTagName("varos").item(0).getTextContent();
	                String utca = cimElem.getElementsByTagName("utca").item(0).getTextContent();
	                String hazszam = cimElem.getElementsByTagName("hazszam").item(0).getTextContent();
	                log("    MBO: " + dolgozoNev + ", " + datum + ", " + varos + ", " + utca + " " + hazszam, writer);
	            }

	            // Vevő + szállítás
	            Element vevo = findElementById(doc, "vevo", "vevoID", vevoID);
	            if (vevo != null) {
	                String nev = vevo.getElementsByTagName("nev").item(0).getTextContent();
	                Element cimElem = (Element) vevo.getElementsByTagName("cim").item(0);
	                String varos = cimElem.getElementsByTagName("varos").item(0).getTextContent();
	                String utca = cimElem.getElementsByTagName("utca").item(0).getTextContent();
	                String hazszam = cimElem.getElementsByTagName("hazszam").item(0).getTextContent();
	                String jaratszam = vevo.getAttribute("jaratszam");
	                log("    Vevő: " + nev + " – " + varos + ", " + utca + " " + hazszam, writer);

	                Element szallitas = findElementById(doc, "szallitas", "jaratszam", jaratszam);
	                if (szallitas != null) {
	                    String cegnev = szallitas.getElementsByTagName("cegnev").item(0).getTextContent();
	                    Element auto = (Element) szallitas.getElementsByTagName("auto").item(0);
	                    String tipus = auto.getElementsByTagName("tipus").item(0).getTextContent();
	                    String muszaki = auto.getElementsByTagName("muszaki_ervenyesseg").item(0).getTextContent();
	                    String rendszam = auto.getElementsByTagName("rendszam").item(0).getTextContent();
	                    log("    Szállítás: " + cegnev + ", " + tipus + ", Rendszám: " + rendszam + ", műszaki: " + muszaki, writer);
	                    log("", writer);
	                }
	            }
	        }
	    }
	}

	//A megadott XML dokumentumban keres egy elemet az adott cimkével és attributum értékkel
	private static Element findElementById(Document doc, String tagName, String attrName, String id) {
	    //Lekérdezi az XML dokumentumból a "tagName"-ben meghatározott adatokat
		NodeList list = doc.getElementsByTagName(tagName);
	    //végigmegy a listán
		for (int i = 0; i < list.getLength(); i++) {
			Element elem = (Element) list.item(i);
	        //Ha az elem attributuma megegyezik a keresett ID-val akkor visszatér vele
			if (elem.getAttribute(attrName).equals(id)) {
	            return elem;
	        }
	    }
		//egyébként üres értékkel tér vissza
	    return null;
	}
	
	public static void log(String text, PrintWriter writer) {
		//szöveg kiíratása konzolra 
		System.out.println(text);
		//Ha a writer nem 0 akkor kiírja fájlba
		if (writer != null) {
			writer.println(text);
		}
	}

}
