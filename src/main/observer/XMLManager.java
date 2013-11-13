package main.observer;

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

import main.club.BankAccount;
import main.club.Club;
import main.club.ClubMember;
import main.runtime.RuntimeManager;

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

            for (int i = 0; i < list.getLength(); i++) {

                Node node = list.item(i);

                if ("mitgliedsnummer".equals(clubMember.getId())) {
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
    public final void addClubMember(final ClubMember clubMember) {
        System.out.println("addClubMember()");
        int clubMemberId            = clubMember.getId();
        String firstName            = clubMember.getFirstName();
        String lastName             = clubMember.getLastName();
        String userName             = clubMember.getUsername();
        BankAccount bankAccount     = clubMember.getBankAccount();
        String bank                 = bankAccount.getBankName();
        int accountNumber           = bankAccount.getAccountNumber();
        int bankIdCode              = bankAccount.getBankIdCode();
        String clubName             = clubMember.getClub().getClubName();

        // VERY DIRTY BUG ROUNDTRIPPING

//        if (clubMemberId == null) {
//            clubMemberId = 0;
//        }
        if (firstName == null) {
            firstName = "";
        }
        if (lastName == null) {
            lastName = "";
        }
        if (userName == null) {
            userName = "";
        }
//        if (accountNumber == null) {
//            accountNumber = 0;
//        }
        if (clubName == null) {
            clubName = "";
        }

        try {
            DocumentBuilderFactory domFactory
            = DocumentBuilderFactory.newInstance();
            domFactory.setIgnoringComments(true);
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            Document document = builder.parse(xmlFilename);

            Node elementRoot = document.getFirstChild();

            Element xmlClubMember = document.createElement("clubmember");
            elementRoot.appendChild(xmlClubMember);

            Element xmlClubMemberId = document.createElement("id");
            String xmlClubMemberIdStr = Integer.toString(clubMemberId);
            xmlClubMemberId.appendChild(document.
                    createTextNode(xmlClubMemberIdStr));
            xmlClubMember.appendChild(xmlClubMemberId);

            Element xmlFirstName = document.createElement("firstname");
            xmlFirstName.appendChild(document.createTextNode(firstName));
            xmlClubMember.appendChild(xmlFirstName);

            Element xmlLastName = document.createElement("nachname");
            xmlLastName.appendChild(document.createTextNode(lastName));
            xmlClubMember.appendChild(xmlLastName);

            Element xmlUserName = document.createElement("username");
            xmlUserName.appendChild(document.createTextNode(userName));
            xmlClubMember.appendChild(xmlUserName);

            Element xmlBank = document.createElement("bank");
            xmlBank.appendChild(document.createTextNode(bank));
            xmlClubMember.appendChild(xmlBank);

            Element xmlAccountNumber = document.createElement("accountnumber");
            String accountNumberStr = Integer.toString(accountNumber);
            xmlAccountNumber.appendChild(document.
                    createTextNode(accountNumberStr));
            xmlClubMember.appendChild(xmlAccountNumber);

            Element xmlBankIdCode = document.createElement("bankidcode");
            String bankIdCodeStr = Integer.toString(accountNumber);
            xmlBankIdCode.appendChild(document.createTextNode(bankIdCodeStr));
            xmlClubMember.appendChild(xmlBankIdCode);

            Element xmlClubName = document.createElement("club");
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
        System.out.println("XMLManager.update()");
    }
}
