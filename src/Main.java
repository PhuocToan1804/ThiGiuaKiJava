
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        Thread1 thread1 = new Thread1(students);
        Thread2 thread2 = new Thread2(students);
        Thread3 thread3 = new Thread3(students);


        thread1.run();


        thread2.run();


        thread3.run();


        saveResults(students);


        readAndDecodeResults("kq.xml");
    }

    public static void saveResults(List<Student> students) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            Element root = doc.createElement("students");
            doc.appendChild(root);

            for (Student student : students) {
                Element studentElement = doc.createElement("student");
                root.appendChild(studentElement);

                Element idElement = doc.createElement("id");
                idElement.setTextContent(Integer.toString(student.id));
                studentElement.appendChild(idElement);

                Element nameElement = doc.createElement("name");
                nameElement.setTextContent(student.name);
                studentElement.appendChild(nameElement);

                Element addressElement = doc.createElement("address");
                addressElement.setTextContent(student.address);
                studentElement.appendChild(addressElement);

                Element ageElement = doc.createElement("age");
                ageElement.setTextContent(Integer.toString(student.age));
                studentElement.appendChild(ageElement);

                Element sumElement = doc.createElement("sum");
                sumElement.setTextContent(Integer.toString(student.sum));
                studentElement.appendChild(sumElement);

                Element isDigitElement = doc.createElement("isDigit");
                isDigitElement.setTextContent(Boolean.toString(student.isDigit));
                studentElement.appendChild(isDigitElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("kq.xml"));

            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readAndDecodeResults(String fileName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(new File(fileName));

            NodeList studentList = doc.getElementsByTagName("student");

            for (int i = 0; i < studentList.getLength(); i++) {
                Element studentElement = (Element) studentList.item(i);

                int id = Integer.parseInt(studentElement.getElementsByTagName("id").item(0).getTextContent());
                String name = studentElement.getElementsByTagName("name").item(0).getTextContent();
                String address = studentElement.getElementsByTagName("address").item(0).getTextContent();
                int age = Integer.parseInt(studentElement.getElementsByTagName("age").item(0).getTextContent());
                int sum = Integer.parseInt(studentElement.getElementsByTagName("sum").item(0).getTextContent());
                boolean isDigit = Boolean.parseBoolean(studentElement.getElementsByTagName("isDigit").item(0).getTextContent());

                System.out.println("ID: " + id + ", Name: " + name + ", Address: " + address + ", Age: " + age + ", Sum: " + sum + ", Is Digit: " + isDigit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

