package main.java.observer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import main.java.club.BankAccount;
import main.java.club.Club;
import main.java.club.ClubMember;
import main.java.club.ClubMemberProperties;
import main.java.runtime.RuntimeManager;

/**
 * The XML Manager reads, writes and changes elements of a XML file. The XML
 * file stores all information about club members.
 * 
 * @author Stefan Eike (s.eike85@gmail.com)
 */
public class XMLManager implements ClubObserver {

	/* *****************************************
	 * Class variables
	 */

	/** Stores the RuntimeManager. */
	private RuntimeManager runtimeManager;

	/** Name of the XML file. */
	private String xmlFilePath;

	/** Document instance of the XML file. */
	private Document xmlDocument;

	/** Root element of the XML file. */
	private String rootelement;

	/** Set the indentation space in XML files. */
	private static final int INDENT_SPACES = 4;

	/** Store the names of all available XML elements. */
	private Map<String, String> xmlElements = new HashMap<String, String>();

	/* *****************************************
	 * Constructor
	 */

	/**
	 * Create a new XMLManager object.
	 *
	 * @param xmlRuntimeManager
	 *            RuntimeManager
	 * @param rootElement
	 *            root element of the XML file
	 */
	public XMLManager(final RuntimeManager xmlRuntimeManager,
			final String rootElement) {
		this.runtimeManager = xmlRuntimeManager;
		this.rootelement = rootElement;
		this.xmlFilePath = runtimeManager
		        .getClub()
		        .getClubName()
		        + "_club.xml";

		xmlElements.put("CLUBMEMBER", new String("clubmember"));
		xmlElements.put("ID", new String("id"));
		xmlElements.put("FIRSTNAME", new String("firstname"));
		xmlElements.put("LASTNAME", new String("lastname"));
		xmlElements.put("USERNAME", new String("username"));
		xmlElements.put("PASSWORD", new String("password"));
		xmlElements.put("STREET", new String("street"));
		xmlElements.put("HOUSENUMBER", new String("housenumber"));
		xmlElements.put("ZIP", new String("zip"));
		xmlElements.put("CITY", new String("city"));
		xmlElements.put("BANK", new String("bank"));
		xmlElements.put("ACCOUNTNUMBER", new String("accountnumber"));
		xmlElements.put("BANKIDCODE", new String("bankidcode"));
		xmlElements.put("CLUB", new String("club"));

		File f = new File(xmlFilePath);
		if (f.exists()) { // Check if the XML file exists
			System.out.println("File exists");
			loadXMLDocument(); // Load the XML document

			/* Load all club members from
			 * a XML file and return a Club. */
			loadXMLClub();
		} else {
			this.xmlDocument = createXMLDocument(); // Create a new XML document
		}

	}

	/* *****************************************
	 * Methods
	 */

	/**
	 * Create a new XML file and return an instance of Document.
	 *
	 * @return the document
	 */
	public final Document createXMLDocument() {
		System.out.println("createXMLDocument");
		try {
			DocumentBuilderFactory domFactory =
			        DocumentBuilderFactory.newInstance();
			// domFactory.setIgnoringComments(true);

			DocumentBuilder builder =
			        domFactory.newDocumentBuilder();
			Document document = builder.newDocument();

			Element rootElement =
			        document.createElement(rootelement);
			document.appendChild(rootElement);

			TransformerFactory transformerFactory =
			        TransformerFactory.newInstance();
			Transformer transformer =
			        transformerFactory.newTransformer();

			/** Indent the XML output. */
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource source = new DOMSource(document);

			StreamResult result =
			        new StreamResult(new File(xmlFilePath));

			transformer.transform(source, result);

			System.out.println("File saved!");

			return document;
		} catch (
		        ParserConfigurationException
		        | TransformerException e
		        ) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Check if the club member is already stored in the file.
	 *
	 * @param clubMember
	 *            club member
	 * @return TRUE, if the club member is stored in the file
	 */
	public final boolean isClubMemberInFile(final ClubMember clubMember) {

		loadXMLDocument(); // Make sure the XML document is loaded.

		// Create a XPath pointing to the name of the Club Member
		XPath xPath = XPathFactory.newInstance().newXPath();
		String expression = "//id[text()='" + clubMember.getClubMemberId()
				+ "']/../firstname";

		// Create a NodeList for the club member
		NodeList clubMemberList = null;
		try {
			clubMemberList = (NodeList) xPath.compile(expression).evaluate(
					this.xmlDocument, XPathConstants.NODESET);
			System.out.println("tried successfully");

			// If the list is greater than 0, the club member exists
			return clubMemberList.getLength() > 0;
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Add a new club member to the file.
	 *
	 * @param clubMember
	 *            club member
	 */
	public final void addXMLClubMember(final ClubMember clubMember) {

		loadXMLDocument();
		if (xmlDocument == null) {
			System.out.println(
			        "addXMLClubMember: xmlDocument is null"
			        );
		}

		try {
			if (xmlDocument != null && clubMember != null) {
				Node root = xmlDocument.getFirstChild();
				System.out.println("root: "
				        + xmlDocument.getFirstChild());

				Element newClubMemberElement =
				        createClubMemberElement(clubMember);
				System.out.println("Element: "
				        + newClubMemberElement);
				root.appendChild(newClubMemberElement);

				TransformerFactory transformerFactory =
				        TransformerFactory.newInstance();
				/*
				 * FIXME
				 * This does not seem to work with DOMSources,
				 * but StreamSources.
				 */
				transformerFactory.setAttribute(
				        "indent-number",
				        INDENT_SPACES
				        );

				Transformer transformer =
				        transformerFactory.newTransformer();

				/*
				 * Indent the elements.
				 */
				transformer.setOutputProperty(
				        OutputKeys.INDENT,
				        "yes"
				        ); // Indent

				DOMSource source = new DOMSource(xmlDocument);
				StreamResult result = new StreamResult(
				        new File(xmlFilePath)
				        );

				// System.out.println("xmlDocument: " + xmlDocument);
				// loadXMLDocument();
				// System.out.println("xmlDocument2: " + xmlDocument);
				// System.out.println("xmlFilePath: " + xmlFilePath);
				// System.out.println("source: " + source);
				// System.out.println("result: " + result);
				//
				// if (xmlDocument == null) {
				// System.out.println("addXMLClubMember: xmlDocument is null");
				// }
				// if (xmlFilePath == null) {
				// System.out.println("addXMLClubMember: xmlFilePath is null");
				// }
				// if (source == null) {
				// System.out.println("addXMLClubMember: source is null");
				// }
				// if (result == null) {
				// System.out.println("addXMLClubMember: result is null");
				// }

				transformer.transform(source, result);
			}
			System.out.println(
			        "XMLManager has added a new club member!"
			        );
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create XML element of a club member.
	 *
	 * @param clubMember
	 *            the club member
	 * @return the new XML club member element
	 */
	private Element createClubMemberElement(final ClubMember clubMember) {

		if (clubMember != null && xmlDocument != null) {
			Element xmlClubMember;

			xmlClubMember = xmlDocument.createElement(xmlElements
					.get("CLUBMEMBER"));

			xmlClubMember.appendChild(
		        createNewXmlElement(xmlElements.get("ID"),
        			Integer.toString(
        			        clubMember.getClubMemberId()
        	        )
				)
			);

			xmlClubMember.appendChild(
		        createNewXmlElement(xmlElements.get("FIRSTNAME"),
	                clubMember.getFirstName()
                )
            );

			xmlClubMember.appendChild(
		        createNewXmlElement(xmlElements.get("LASTNAME"),
	                clubMember.getLastName()
                )
            );

			xmlClubMember.appendChild(
		        createNewXmlElement(
        			xmlElements.get("LASTNAME"),
        			clubMember.getLastName()
				)
			);

			xmlClubMember.appendChild(
		        createNewXmlElement(
					xmlElements.get("USERNAME"),
					clubMember.getLastName()
				)
			);

			xmlClubMember.appendChild(
		        createNewXmlElement(
	                xmlElements.get("PASSWORD"),
	                clubMember.getHashedPassword()
                )
            );

			// Create the address
			if (clubMember.getAddress() != null) {

				xmlClubMember.appendChild(
			        createNewXmlElement(
		                xmlElements.get("STREET"),
		                clubMember.getAddress().getStreet()
	                )
                );

				xmlClubMember.appendChild(
			        createNewXmlElement(
		                xmlElements.get("HOUSENUMBER"),
		                Integer.toString(
	                        clubMember.getAddress().getHouseNumber()
                        )
                    )
                );

				xmlClubMember.appendChild(
			        createNewXmlElement(
		                xmlElements.get("ZIP"),
		                Integer.toString(
	                        clubMember.getAddress().getZipCode()
                        )
                    )
                );

				xmlClubMember.appendChild(
			        createNewXmlElement(
		                xmlElements.get("CITY"),
		                clubMember.getAddress().getCity()
	                )
                );
			}

			BankAccount clubMemberBankAccount;
			clubMemberBankAccount = clubMember.getBankAccount();

			if (clubMemberBankAccount != null) {

				xmlClubMember.appendChild(
			        createNewXmlElement(
						xmlElements.get("BANK"),
						clubMemberBankAccount.
						    getBankName()
					)
				);

				xmlClubMember.appendChild(
			        createNewXmlElement(
		                xmlElements.get("ACCOUTNUMBER"),
		                Integer.toString(
	                        clubMemberBankAccount.
	                            getAccountNumber()
                        )
                    )
                );

				xmlClubMember.appendChild(
			        createNewXmlElement(
		                xmlElements.get("BANKIDCODE"),
		                Integer.toString(
	                        clubMemberBankAccount.
	                            getBankIdCode()
                        )
                    )
                );
			}

			Club club;
			club = clubMember.getClub();

			if (club != null) {
				xmlClubMember.appendChild(
			        createNewXmlElement(
		                xmlElements.get("CLUB"),
		                club.getClubName()
	                )
                );
			}

			return xmlClubMember;
		} else {
			if (clubMember == null) {
				System.out.println("createClubMemberElement(): "
						+ "clubMember is null");
			}
			if (xmlDocument == null) {
				System.out.println("createClubMemberElement(): "
						+ "doc is null");
			}
			return null;
		}
	}

	/**
	 * Create a new XML element.
	 *
	 * @param xmlElementName
	 *            the name of the element
	 * @param clubMemberPropertyValue
	 *            the value of the new element
	 * @return the element with its value
	 */
	private Node createNewXmlElement(final String xmlElementName,
			final String clubMemberPropertyValue) {

		if (!(clubMemberPropertyValue == null)) {
			Element xmlElement;
			xmlElement = xmlDocument.createElement(xmlElementName);
			xmlElement.appendChild(
		        xmlDocument.createTextNode(
	                clubMemberPropertyValue
                )
            );
			return xmlElement;
		}
		return null;
	}

	/**
	 * Return the loaded XML document. If none has been loaded yet, load the
	 * document from the given xmlFilePath.
	 *
	 * @return the loaded document
	 */
	public final boolean loadXMLDocument() {
		System.out.println("Load XML document ...");

		if (this.xmlDocument == null) {
			try {
				File xmlFile = new File(xmlFilePath);

				// Check if the XML file exists.
				if (xmlFile.exists()) {
					DocumentBuilderFactory dbFactory
					    = DocumentBuilderFactory.newInstance();
					dbFactory.setIgnoringComments(true);
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					
					/** Set the loaded XML document. */
					this.xmlDocument = dBuilder.parse(xmlFile);
				} else {
					System.out.println("loadXMLDocument() failed ...");
				}
			} catch (SAXException
			        | IOException
			        | ParserConfigurationException e
			        ) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * Load the club members from the XML file and return a Club.
	 *
	 * @return The Club that has been loaded from the given XML file.
	 */
	public final Club loadXMLClub() {

		System.out.println("loadXMLClub()");
		loadXMLDocument(); // Make sure the XML document is loaded.

		// Create a XPath pointing to the name of the Club Member
		XPath xPath = XPathFactory.newInstance().newXPath();
		String expression = "//clubmember";

		// Create a NodeList for the club member
		NodeList clubMemberList = null;
		try {
			clubMemberList = (NodeList) xPath.
			        compile(expression).evaluate(
					xmlDocument, XPathConstants.NODESET);
			System.out.println("'" + clubMemberList.getLength()
					+ "' club members loaded.");
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
	}

	// /**
	// * Return the name of the club XML file.
	// * If the file does not exist, create it.
	// * @return the club XML filename
	// */
	// public final String getXmlfileNameOrCreateNew() {
	// if (xmlFilename != null) {
	// return xmlFilename;
	// } else {
	// xmlFilename = runtimeManager.getClub().getClubName() + ".xml";
	// return xmlFilename;
	// }
	// }

	/**
	 * Observer Method - Update is called when
	 * new messages are consumed from Subject.
	 */
	public final void update() {
		synchronizeAllClubMembers();
		System.out.println("XMLManager.update()");
	}

	/**
	 * Compares all members of the club
     * with all club members in the XML file.
	 * If properties of an XML entry are not equal to the club member
	 * properties, overwrite the properties of the XML entry.
	 */
	private void synchronizeAllClubMembers() {
		List<ClubMember> clubMembers;
		clubMembers = runtimeManager.getClub().getClubMembers();
		for (ClubMember clubMember : clubMembers) {
			if (!(isClubMemberInFile(clubMember))) {
				System.out.println("club member '"
				        + clubMember.getUsername()
						+ "', '"
				        + clubMember.getClubMemberId()
						+ "'" + " is not in file. "
						+ "Try to add him.");
				addXMLClubMember(clubMember);
			} else {
				System.out.println(
				        "clubMember "
		                + clubMember.getUsername()
						+ " is in file.");
			}
		}
	}

	/**
	 * Get the club member XML entry and change a property value.
	 *
	 * @param clubMember
	 *            the club member to be changed.
	 * @param property
	 *            of the club member to be changed
	 * @param propertyValue
	 *            new value of the property
	 */
	public final void changeXMLClubMember(final ClubMember clubMember,
			final ClubMemberProperties property, final String propertyValue) {

		if (clubMember == null) {
			System.out.println("changeXMLClubMember: clubMember is null");
		}
		if (property == null) {
			System.out.println("changeXMLClubMember: property is null");
		}
		if (propertyValue == null) {
			System.out.println("changeXMLClubMember: propertyValue is null");
		}

		Source source = new DOMSource(xmlDocument);

		// Hier den XPath-Teil erstellen

		String expression = "//id[text()='333']/../firstname";

		System.out.println("changeXMLClubMember: Firstname: " + expression);

		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList myNodeList = null;

		try {
			myNodeList = (NodeList) xPath.compile(expression).evaluate(
					xmlDocument, XPathConstants.NODESET);
		} catch (XPathExpressionException e1) {
			e1.printStackTrace();
		}
		if (myNodeList.item(0) != null) {
			myNodeList.item(0).setNodeValue("Hi!");
			System.out.println("changeXMLClubMember: hi: "
					+ myNodeList.item(0).getNodeValue());
		}

		File file = new File(xmlFilePath);
		Result result = new StreamResult(file);

		Transformer xformer;
		try {
			xformer = TransformerFactory.newInstance().newTransformer();
			xformer.transform(source, result);
		} catch (
		        TransformerFactoryConfigurationError
		        | TransformerException e
		        ) {
			e.printStackTrace();
		}
	}
}
