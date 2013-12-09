package main.java.observer;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import main.java.club.Address;
import main.java.club.BankAccount;
import main.java.club.Club;
import main.java.club.ClubMember;
import main.java.club.ClubMemberProperties;
import main.java.runtime.RuntimeManager;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * The XML Manager reads, writes and changes elements of a XML file.
 * The XML file stores all information about club members.
 * @author Stefan Eike (s.eike85@gmail.com)
 */
public class XMLManager implements ClubObserver {

    /* *****************************************
     * Class variables
     */

    /** Stores the RuntimeManager. */
    private RuntimeManager runtimeManager;

    /** Name of the XML file. */
    private String xmlFilename;

    /** Root element of the XML file. */
    private String rootelement;

    /** Set the element name containing a club member. */
    private final String clubMemberElementName = "clubmember";

    /** Set the element name containing the club member's ID. */
    private final String idElementName = "id";

    /** Set the element name containing the club member's first name. */
    private final String firstNameElementName = "firstname";

    /** Set the element name containing the club member's last name. */
    private final String lastNameElementName = "lastname";

    /** Set the element name containing the club member's user name. */
    private final String userNameElementName = "username";

    /** Set the element name containing the club member's password. */
    private final String passwordElementName = "password";

    /** Set the element name containing the club member's street. */
    private final String streetElementName = "street";

    /** Set the element name containing the club member's house number. */
    private final String houseNumberElementName = "housenumber";

    /** Set the element name containing the club member's zip code. */
    private final String zipCodeElementName = "zip";

    /** Set the element name containing the club member's city. */
    private final String cityElementName = "city";

    /** Set the element name containing the club member's bank. */
    private final String bankElementName = "bank";

    /** Set the element name containing the club member's bank
     * account number. */
    private final String accountNumberElementName
    = "accountnumber";

    /** Set the element name containing the club member's bank ID code. */
    private final String bankIdCodeElementName = "bankidcode";

    /** Set the element name containing the club member's club. */
    private final String clubElementName = "club";


    /* *****************************************
     * Constructor
     */

    /**
     * Create a new XMLManager object.
     * @param xmlRuntimeManager RuntimeManager
     * @param rootElement root element of the XML file
     */
    public XMLManager(final RuntimeManager xmlRuntimeManager,
            final String rootElement) {
        this.runtimeManager = xmlRuntimeManager;
        this.rootelement = rootElement;
        this.xmlFilename = runtimeManager.getClub().getClubName() + "_club.xml";

        File f = new File(xmlFilename);
        if (f.exists()) {
            System.out.println("File exists");
        } else {
            System.out.println("File not found, create a new one!");
            createXMLDocument();
        }

    }


    /* *****************************************
     * Methods
     */

    /**
     * Create a new XML file and return an instance of Document.
     * @return 
     */
    public final Document createXMLDocument() {
        System.out.println("createXMLDocument");
        try {
            DocumentBuilderFactory domFactory
            = DocumentBuilderFactory.newInstance();
            domFactory.setIgnoringComments(true);
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element rootElement = document.createElement(rootelement);
            document.appendChild(rootElement);

            TransformerFactory transformerFactory
            = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(xmlFilename));

            transformer.transform(source, result);

            System.out.println("File saved!");

            return document;
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Check if the club member is already stored in the file.
     * @param clubMember club member
     * @return TRUE, if the club member is stored in the file
     */
    public final boolean isClubMemberInFile(final ClubMember clubMember) {
        Document document = getXmlDocument(xmlFilename);

        XPath xPath =  XPathFactory.newInstance().newXPath();
        String expression = "//id[text()='333']/../firstname";

        NodeList clubMemberList = null;
        try {
            clubMemberList = (NodeList) xPath.compile(expression).
                    evaluate(document, XPathConstants.NODESET);
            System.out.println("tried successfully");

            return clubMemberList.getLength() > 0;
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Create a new Document instance and return it.
     * @param filename name of the XML file
     * @return the Document instance
     */
    private final Document getXmlDocument(String filename) {
        try {
            DocumentBuilderFactory domFactory
                    = DocumentBuilderFactory.newInstance();
            domFactory.setIgnoringComments(true);
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            Document doc = builder.parse(xmlFilename);
            doc.getDocumentElement().normalize();
            if (doc != null) {
                System.out.println("getXMLDocument(): XML document imported.");
                return doc;
            }
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Add a new club member to the file.
     * @param clubMember club member
     */
    public final void addXMLClubMember(final ClubMember clubMember) {
        System.out.println("addXMLClubMember()");

        
        
        try {
            Document doc = getXmlDocument(xmlFilename);
            OutputFormat format = new OutputFormat(doc);
            format.setLineWidth(65);
            format.setIndenting(true);
            format.setIndent(2);
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(doc);

            if (doc != null && clubMember != null) {
                Node root = doc.getFirstChild();

                Element newClubMemberElement =
                        createClubMemberElement(doc, clubMember);
                root.appendChild(newClubMemberElement);

                TransformerFactory transformerFactory
                = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(xmlFilename));
                transformer.transform(source, result);                        
            }
            System.out.println("XMLManager has added a new club member!");
            } catch (TransformerException e) {
                    e.printStackTrace();
        } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    /**
     * Create XML element of a club member.
     * @param doc the XML document
     * @param clubMember the club member
     * @return the new XML club member element
     */
    private Element createClubMemberElement(
            final Document doc,
            final ClubMember clubMember) {

        if (clubMember != null && doc != null) {
            Element xmlClubMember;
            xmlClubMember = doc.createElement(clubMemberElementName);

            Element xmlClubMemberId;
            String clubMemberId = "";
            clubMemberId = Integer.toString(clubMember.getClubMemberId());
            xmlClubMemberId = doc.createElement(idElementName);
            xmlClubMemberId.appendChild(doc.createTextNode(clubMemberId));
            xmlClubMember.appendChild(xmlClubMemberId);

            Element xmlFirstName;
            String firstName = "";
            firstName = clubMember.getFirstName();
            xmlFirstName = doc.createElement(firstNameElementName);
            xmlFirstName.appendChild(doc.createTextNode(firstName));
            xmlClubMember.appendChild(xmlFirstName);

            Element xmlLastName;
            String lastName = "";
            lastName = clubMember.getLastName();
            xmlLastName = doc.createElement(lastNameElementName);
            xmlLastName.appendChild(doc.createTextNode(lastName));
            xmlClubMember.appendChild(xmlLastName);

            Element xmlUserName;
            String userName = "";
            userName = clubMember.getUsername();
            xmlUserName = doc.createElement(userNameElementName);
            xmlUserName.appendChild(doc.createTextNode(userName));
            xmlClubMember.appendChild(xmlUserName);

            Element xmlPassword;
            String password = "";
            password = clubMember.getHashedPassword();
            xmlPassword = doc.createElement(passwordElementName);
            xmlPassword.appendChild(doc.createTextNode(password));
            xmlClubMember.appendChild(xmlPassword);

            Address clubMemberAddress;
            clubMemberAddress = clubMember.getAddress();

            if (clubMemberAddress != null) {
                Element xmlStreet;
                String street = "";
                street = clubMemberAddress.getStreet();
                xmlStreet = doc.createElement(streetElementName);
                xmlStreet.appendChild(doc.createTextNode(street));
                xmlClubMember.appendChild(xmlStreet);

                Element xmlHouseNumber;
                String houseNumber = "";
                houseNumber = Integer.toString(clubMemberAddress
                        .getHouseNumber());
                xmlHouseNumber = doc.createElement(houseNumberElementName);
                xmlHouseNumber.appendChild(doc.createTextNode(houseNumber));
                xmlClubMember.appendChild(xmlHouseNumber);

                Element xmlZipCode;
                String zipCode = "";
                zipCode = Integer.toString(clubMemberAddress.getZipCode());
                xmlZipCode = doc.createElement(zipCodeElementName);
                xmlZipCode.appendChild(doc.createTextNode(zipCode));
                xmlClubMember.appendChild(xmlZipCode);

                Element xmlCity;
                String city = "";
                city = clubMemberAddress.getCity();
                xmlCity = doc.createElement(cityElementName);
                xmlCity.appendChild(doc.createTextNode(city));
                xmlClubMember.appendChild(xmlCity);
            }

            BankAccount clubMemberBankAccount;
            clubMemberBankAccount = clubMember.getBankAccount();

            if (clubMemberBankAccount != null) {
                Element xmlBank;
                String bank = "";
                bank = clubMemberBankAccount.getBankName();
                xmlBank = doc.createElement(bankElementName);
                xmlBank.appendChild(doc.createTextNode(bank));
                xmlClubMember.appendChild(xmlBank);

                Element xmlAccountNumber;
                String accountNumber = "";
                accountNumber = Integer.toString(
                        clubMemberBankAccount.getAccountNumber());
                xmlAccountNumber = doc.createElement(accountNumberElementName);
                xmlAccountNumber.appendChild(doc.createTextNode(accountNumber));
                xmlClubMember.appendChild(xmlAccountNumber);

                Element xmlBankIdCode;
                String bankIdCode = "";
                bankIdCode = Integer.toString(
                        clubMemberBankAccount.getBankIdCode());
                xmlBankIdCode = doc.createElement(bankIdCodeElementName);
                xmlBankIdCode.appendChild(doc.createTextNode(bankIdCode));
                xmlClubMember.appendChild(xmlBankIdCode);
            }

            Club club;
            club = clubMember.getClub();

            if (club != null) {
                Element xmlClubName;
                String clubName = "";

                clubName = clubMember.getClub().getClubName();
                xmlClubName = doc.createElement(clubElementName);
                xmlClubName.appendChild(doc.createTextNode(clubName));
                xmlClubMember.appendChild(xmlClubName);
            }

            return xmlClubMember;
        } else {
            if (clubMember == null) {
                System.out.println("createClubMemberElement(): "
                        + "clubMember is null");
            }
            if (doc == null) {
                System.out.println("createClubMemberElement(): "
                        + "doc is null");
            }
            return null;
        }
    }


    /**
     * Load a created XML file and return true on success.
     */
    public final void loadXMLDocument() {
        System.out.println("loadXMLDocument");
        try {
            File fXmlFile = new File(xmlFilename);

            // Check if the XML file exists.
            if (fXmlFile.exists()) {
                DocumentBuilderFactory dbFactory
                = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                dBuilder.parse(fXmlFile);
                System.out.println("Document geladen");
                createXMLDocument();
            }
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        System.out.println("Document geladen");
    }

    /**
     * Return the name of the club XML file.
     * If the file does not exist, create it.
     * @return the club XML filename
     */
    public final String getXmlfileNameOrCreateNew() {
        if (xmlFilename != null) {
            return xmlFilename;
        } else {
            xmlFilename = runtimeManager.getClub().getClubName() + ".xml";
            return xmlFilename;
        }
    }

    /**
     * Observer Method - Update is called when new messages
     * are consumed from Subject.
     */
    public final void update() {
        synchronizeAllClubMembers();
        System.out.println("XMLManager.update()");
    }

    /**
     * Compares all members of the club with all club members
     * in the XML file. If properties of an XML entry are not
     * equal to the club member properties, overwrite the
     * properties of the XML entry.
     */
    private void synchronizeAllClubMembers() {
        List<ClubMember> clubMembers;
        clubMembers = runtimeManager.getClub().getClubMembers();
        for (ClubMember clubMember : clubMembers) {
            if (!(isClubMemberInFile(clubMember))) {
                System.out.println("club member is not in file. "
                        + "Try to add him.");
                addXMLClubMember(clubMember);
            } else {
                System.out.println("clubMember is in file.");
            }
        }
    }

    /**
     * Get the club member XML entry and change a property value.
     * @param clubMember the club member to be changed.
     * @param property of the club member to be changed
     * @param propertyValue new value of the property
     */
    // TODO Write this method
    public final void changeXMLClubMember(
            final ClubMember clubMember,
            final ClubMemberProperties property,
            final String propertyValue) {

        DocumentBuilderFactory domFactory
        = DocumentBuilderFactory.newInstance();
        domFactory.setIgnoringComments(true);
        DocumentBuilder builder;
        Document doc = null;

        try {
            builder = domFactory.newDocumentBuilder();
            doc = builder.parse(xmlFilename);
        } catch (SAXException | IOException | ParserConfigurationException e1) {
            e1.printStackTrace();
        }
        Source source = new DOMSource(doc);

        // Hier den XPath-Teil erstellen

        String expression = "//id[text()='333']/../firstname";

        XPath xPath =  XPathFactory.newInstance().newXPath();
        NodeList myNodeList = null;

        try {
            myNodeList = (NodeList) xPath.compile(expression)
                    .evaluate(doc, XPathConstants.NODESET);
        } catch (XPathExpressionException e1) {
            e1.printStackTrace();
        }
        myNodeList.item(0).setNodeValue("Hi mom!");

        File file = new File(xmlFilename);
        Result result = new StreamResult(file);

        Transformer xformer;
        try {
            xformer = TransformerFactory.newInstance().newTransformer();
            xformer.transform(source, result);
        } catch (TransformerFactoryConfigurationError
                | TransformerException e) {
            e.printStackTrace();
        }
    }


//    /**
//     * Add a new club member to the file.
//     * @param clubMember club member
//     */
//    public final void addXMLClubMember(final ClubMember clubMember) {
//        System.out.println("addXMLClubMember()");
//    
//        try {
//            Document doc = getXmlDocument(xmlFilename);
//    
//            if (doc != null && clubMember != null) {
//                Node root = doc.getFirstChild();
//    
//                Element newClubMemberElement =
//                        createClubMemberElement(doc, clubMember);
//                root.appendChild(newClubMemberElement);
//    
//                TransformerFactory transformerFactory
//                = TransformerFactory.newInstance();
//                Transformer transformer = transformerFactory.newTransformer();
//                DOMSource source = new DOMSource(doc);
//                StreamResult result = new StreamResult(new File(xmlFilename));
//                transformer.transform(source, result);                        
//            }
//            System.out.println("XMLManager has added a new club member!");
//            } catch (TransformerException e) {
//                    e.printStackTrace();
//        }
//    }
}

//package ecb.sdw.pretty;
//
//import org.apache.xml.serialize.OutputFormat;
//import org.apache.xml.serialize.XMLSerializer;
//import org.w3c.dom.Document;
//import org.xml.sax.InputSource;
//import org.xml.sax.SAXException;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import java.io.IOException;
//import java.io.StringReader;
//import java.io.StringWriter;
//import java.io.Writer;
//
///**
// * Pretty-prints xml, supplied as a string.
// * <p/>
// * eg.
// * <code>
// * String formattedXml = new XmlFormatter().format("<tag><nested>hello</nested></tag>");
// * </code>
// */
//public class XmlFormatter {
//
//    public XmlFormatter() {
//    }
//
//    public String format(String unformattedXml) {
//        try {
//            final Document document = parseXmlFile(unformattedXml);
//
//            OutputFormat format = new OutputFormat(document);
//            format.setLineWidth(65);
//            format.setIndenting(true);
//            format.setIndent(2);
//            Writer out = new StringWriter();
//            XMLSerializer serializer = new XMLSerializer(out, format);
//            serializer.serialize(document);
//
//            return out.toString();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private Document parseXmlFile(String in) {
//        try {
//            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//            DocumentBuilder db = dbf.newDocumentBuilder();
//            InputSource is = new InputSource(new StringReader(in));
//            return db.parse(is);
//        } catch (ParserConfigurationException e) {
//            throw new RuntimeException(e);
//        } catch (SAXException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static void main(String[] args) {
//        String unformattedXml =
//                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><QueryMessage\n" +
//                        "        xmlns=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message\"\n" +
//                        "        xmlns:query=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query\">\n" +
//                        "    <Query>\n" +
//                        "        <query:CategorySchemeWhere>\n" +
//                        "   \t\t\t\t\t         <query:AgencyID>ECB\n\n\n\n</query:AgencyID>\n" +
//                        "        </query:CategorySchemeWhere>\n" +
//                        "    </Query>\n\n\n\n\n" +
//                        "</QueryMessage>";
//
//        System.out.println(new XmlFormatter().format(unformattedXml));
//    }
//
//}
