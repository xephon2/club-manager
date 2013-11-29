package main.java.observer;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import main.java.club.Address;
import main.java.club.BankAccount;
import main.java.club.ClubMember;
import main.java.runtime.RuntimeManager;

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
    private final String xmlClubMemberElementName = "clubmember";

    /** Set the element name containing the club member's ID. */
    private final String xmlClubMemberIdElementName = "id";

    /** Set the element name containing the club member's first name. */
    private final String xmlClubMemberFirstNameElementName = "firstname";

    /** Set the element name containing the club member's last name. */
    private final String xmlClubMemberLastNameElementName = "lastname";

    /** Set the element name containing the club member's user name. */
    private final String xmlClubMemberUserNameElementName = "username";

    /** Set the element name containing the club member's password. */
    private final String xmlClubMemberPasswordElementName = "password";

    /** Set the element name containing the club member's street. */
    private final String xmlClubMemberStreetElementName = "street";

    /** Set the element name containing the club member's house number. */
    private final String xmlClubMemberHouseNumberElementName = "housenumber";

    /** Set the element name containing the club member's zip code. */
    private final String xmlClubMemberZipCodeElementName = "zip";

    /** Set the element name containing the club member's city. */
    private final String xmlClubMemberCityElementName = "city";

    /** Set the element name containing the club member's bank. */
    private final String xmlClubMemberBankElementName = "bank";

    /** Set the element name containing the club member's bank
     * account number. */
    private final String xmlClubMemberAccountNumberElementName
    = "accountnumber";

    /** Set the element name containing the club member's bank ID code. */
    private final String xmlClubMemberBankIdCodeElementName = "bankidcode";

    /** Set the element name containing the club member's club. */
    private final String xmlClubMemberClubElementName = "club";


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
        this.xmlFilename = runtimeManager.getClub().getClubName() + ".xml";

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
     * Create a new XML file.
     */
    public final void createXMLDocument() {
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
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if the club member is already stored in the file.
     * @param clubMember club member
     * @return TRUE, if the club member is stored in the file
     */
    public final boolean isClubMemberInFile(final ClubMember clubMember) {
        try {
            DocumentBuilderFactory domFactory
            = DocumentBuilderFactory.newInstance();
            domFactory.setIgnoringComments(true);
            DocumentBuilder builder;
            builder = domFactory.newDocumentBuilder();
            Document document = builder.parse(xmlFilename);

            Node rootNode = document.getFirstChild();

            NodeList list = rootNode.getChildNodes();

            for (int i = 0; i < list.getLength();) {

                if (xmlClubMemberIdElementName.equals(clubMember.getClubMemberId())) {
                    System.out.println("Gibt es schon");
                    return true;
                } else {
                    System.out.println("Gibt es noch nicht");
                    return false;
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Add a new club member to the file.
     * @param clubMember club member
     */
    public final void addXMLClubMember(final ClubMember clubMember) {
        System.out.println("addClubMember()");

        /* A club member ID is mandatory.
         * All other properties are not mandatory. */
        String clubMemberId = Integer.toString(clubMember.getClubMemberId());
        String firstName = "";
        String lastName = "";
        String userName = "";
        String password = "";
        String street = "";
        String houseNumber = "";
        String zipCode = "";
        String city = "";
        String bank = "";
        String accountNumber = "";
        String bankIdCode = "";
        String clubName = "";

        if (clubMember.getFirstName() != null) {
            firstName = clubMember.getFirstName();
        }
        if (clubMember.getLastName() != null) {
            lastName = clubMember.getLastName();
        }
        if (clubMember.getUsername() != null) {
            userName = clubMember.getUsername();
        }
        if (clubMember.getHashedPassword() != null) {
            password = clubMember.getHashedPassword();
        }

        Address clubMemberAddress = null;
        if (clubMember.getAddress() != null) {
            clubMemberAddress = clubMember.getAddress();
            if (clubMemberAddress.getStreet() != null) {
                street = clubMemberAddress.getStreet();
            }
            if (clubMemberAddress.getHouseNumber() != 0) {
                houseNumber = Integer.toString(clubMemberAddress.getHouseNumber());
            }
            if (clubMemberAddress.getZipCode() != 0) {
                zipCode = Integer.toString(clubMemberAddress.getZipCode());
            }
            if (clubMemberAddress.getCity() != null) {
                city = clubMemberAddress.getCity();
            }
        }

        BankAccount clubMemberBankAccount = null;
        if (clubMember.getBankAccount() != null) {
            clubMemberBankAccount = clubMember.getBankAccount();
            if (clubMemberBankAccount.getBankName() != null) {
                bank = clubMemberBankAccount.getBankName();
            }
            if (clubMemberBankAccount.getAccountNumber() != 0) {
                accountNumber = Integer.toString(
                        clubMemberBankAccount.getAccountNumber());
            }
            if (clubMemberBankAccount.getBankIdCode() != 0) {
                bankIdCode = Integer.toString(
                        clubMemberBankAccount.getBankIdCode());
            }
        }

        if (clubMember.getClub() != null) {
            clubName = clubMember.getClub().getClubName();
        }


        try {
            DocumentBuilderFactory domFactory
            = DocumentBuilderFactory.newInstance();
            domFactory.setIgnoringComments(true);
            DocumentBuilder builder = domFactory.newDocumentBuilder();

            System.out.println("xmlFilename: " + xmlFilename);

            Document document = builder.parse(xmlFilename);
            System.out.println("document: " + document.toString());

            Node elementRoot = document.getFirstChild();

            System.out.println("elementRoot: " + elementRoot.toString());

            Element xmlClubMember = document.createElement(
                    xmlClubMemberElementName);
            elementRoot.appendChild(xmlClubMember);

            Element xmlClubMemberId = document.createElement(
                    xmlClubMemberIdElementName);
            xmlClubMemberId.appendChild(document.createTextNode(clubMemberId));
            xmlClubMember.appendChild(xmlClubMemberId);

            Element xmlFirstName = document.createElement(
                    xmlClubMemberFirstNameElementName);
            xmlFirstName.appendChild(document.createTextNode(firstName));
            xmlClubMember.appendChild(xmlFirstName);

            Element xmlLastName = document.createElement(
                    xmlClubMemberLastNameElementName);
            xmlLastName.appendChild(document.createTextNode(lastName));
            xmlClubMember.appendChild(xmlLastName);

            Element xmlUserName = document.createElement(
                    xmlClubMemberUserNameElementName);
            xmlUserName.appendChild(document.createTextNode(userName));
            xmlClubMember.appendChild(xmlUserName);

            Element xmlPassword = document.createElement(
                    xmlClubMemberPasswordElementName);
            xmlPassword.appendChild(document.createTextNode(password));
            xmlClubMember.appendChild(xmlPassword);

            Element xmlStreet = document.createElement(
                    xmlClubMemberStreetElementName);
            xmlStreet.appendChild(document.createTextNode(street));
            xmlClubMember.appendChild(xmlStreet);

            Element xmlHouseNumber = document.createElement(
                    xmlClubMemberHouseNumberElementName);
            xmlHouseNumber.appendChild(document.createTextNode(houseNumber));
            xmlClubMember.appendChild(xmlHouseNumber);

            Element xmlZipCode = document.createElement(
                    xmlClubMemberZipCodeElementName);
            xmlZipCode.appendChild(document.createTextNode(zipCode));
            xmlClubMember.appendChild(xmlZipCode);

            Element xmlCity = document.createElement(
                    xmlClubMemberCityElementName);
            xmlCity.appendChild(document.createTextNode(city));
            xmlClubMember.appendChild(xmlCity);

            Element xmlBank = document.createElement(
                    xmlClubMemberBankElementName);
            xmlBank.appendChild(document.createTextNode(bank));
            xmlClubMember.appendChild(xmlBank);

            Element xmlAccountNumber = document.createElement(
                    xmlClubMemberAccountNumberElementName);
            xmlAccountNumber.appendChild(
                    document.createTextNode(accountNumber));
            xmlClubMember.appendChild(xmlAccountNumber);

            Element xmlBankIdCode = document.createElement(
                    xmlClubMemberBankIdCodeElementName);
            xmlBankIdCode.appendChild(document.createTextNode(bankIdCode));
            xmlClubMember.appendChild(xmlBankIdCode);

            Element xmlClubName = document.createElement(
                    xmlClubMemberClubElementName);
            xmlClubName.appendChild(document.createTextNode(clubName));
            xmlClubMember.appendChild(xmlClubName);

            TransformerFactory transformerFactory
            = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(xmlFilename));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("XMLManager has added a new Vereinsmitglied!");
        } catch (ParserConfigurationException | TransformerException |
                SAXException | IOException e) {
                e.printStackTrace();
        }
    }

    /**
     * Load a created XML file and return true on success.
     */
    public final void loadXMLDocument() {
        System.out.println("loadXMLDocument");
        try {
            File fXmlFile = new File(xmlFilename);
            DocumentBuilderFactory dbFactory
            = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = null;
            dBuilder = dbFactory.newDocumentBuilder();
            if (dBuilder == null) {
                System.out.println("dBuilder ist null");
            }
            dBuilder.parse(fXmlFile);
            System.out.println("Document geladen");
            createXMLDocument();
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
        addOrChangeXMLClubMember();
        System.out.println("XMLManager.update()");
    }

    /**
     * Compare all club members with the corresponding XML entries.
     * If an entry deviates or is missing, change it.
     */
    // TODO This method is incredible slow. Optimize it!
    private void addOrChangeXMLClubMember() {
        for (ClubMember clubMember : runtimeManager.
                getClub().getClubMembers()) {
            addXMLClubMember(clubMember);
//            if (isClubMemberInFile(clubMember)) {
//                // Compare properties
//            } else {
//                addXMLClubMember(clubMember);
//            }
        }
    }

    /**
     * Get the club member XML entry and change a property value.
     */
    // TODO Write this method
    public final void changeXMLClubMember(final ClubMember clubMember) { }
}
