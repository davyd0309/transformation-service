package pl.transformation.transformationservice;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;

public class XmlTransformationService {

public String transformXml(String xmlData){

    try {
                // Przygotowanie pliku XML wejściowego na podstawie danych przekazanych przez klienta
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                InputSource inputSource = new InputSource(new StringReader(xmlData));
                Document xmlDoc = builder.parse(inputSource);

                // Tworzenie transformatora
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer(new javax.xml.transform.stream.StreamSource("transform.xslt"));

                // Przekazanie parametrów transformacji
                transformer.setParameter("paramName", "wartośćParametru");

                // Przygotowanie strumienia wyjściowego
                StringWriter writer = new StringWriter();
                StreamResult result = new StreamResult(writer);

                // Wykonanie transformacji
                transformer.transform(new DOMSource(xmlDoc), result);

                // Pobranie wyniku transformacji jako tekst
                String transformedXML = writer.toString();

                // Tutaj możesz zapisać wynik transformacji do pliku lub zwrócić go jako odpowiedź HTTP
                // np. zapis do pliku:
                // try (FileWriter fileWriter = new FileWriter("output.xml")) {
                //     fileWriter.write(transformedXML);
                // }

                return transformedXML;
            } catch (Exception e) {
                e.printStackTrace();
                return "Błąd podczas transformacji XML";
            }
        }


}
