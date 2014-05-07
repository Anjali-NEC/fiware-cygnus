/**
 * Copyright 2014 Telefonica Investigación y Desarrollo, S.A.U
 *
 * This file is part of fiware-connectors (FI-WARE project).
 *
 * cosmos-injector is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * cosmos-injector is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License along with fiware-connectors. If not, see
 * http://www.gnu.org/licenses/.
 *
 * For those usages not covered by the GNU Affero General Public License please contact with Francisco Romero
 * frb@tid.es
 */

package es.tid.fiware.fiwareconnectors.cygnus.containers;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author frb
 */
public class NotifyContextRequestTest extends TestCase {
    
    private Logger logger;
    private final String notifyXMLSimple = ""
            + "<notifyContextRequest>"
            +   "<subscriptionId>51c0ac9ed714fb3b37d7d5a8</subscriptionId>"
            +   "<originator>localhost</originator>"
            +   "<contextResponseList>"
            +     "<contextElementResponse>"
            +       "<contextElement>"
            +         "<entityId type=\"Room\" isPattern=\"false\">"
            +           "<id>Room1</id>"
            +         "</entityId>"
            +         "<contextAttributeList>"
            +           "<contextAttribute>"
            +             "<name>temperature</name>"
            +             "<type>centigrade</type>"
            +             "<value>26.5</value>"
            +           "</contextAttribute>"
            +         "</contextAttributeList>"
            +       "</contextElement>"
            +       "<statusCode>"
            +         "<code>200</code>"
            +         "<reasonPhrase>OK</reasonPhrase>"
            +       "</statusCode>"
            +     "</contextElementResponse>"
            +   "</contextResponseList>"
            + "</notifyContextRequest>";
    private final String notifyXMLCompound = ""
            + "<notifyContextRequest>"
            +   "<subscriptionId>51c0ac9ed714fb3b37d7d5a8</subscriptionId>"
            +   "<originator>localhost</originator>"
            +   "<contextResponseList>"
            +     "<contextElementResponse>"
            +       "<contextElement>"
            +         "<entityId type=\"Room\" isPattern=\"false\">"
            +           "<id>Room2</id>"
            +         "</entityId>"
            +         "<contextAttributeList>"
            +           "<contextAttribute>"
            +             "<name>field1</name>"
            +             "<type>type1</type>"
            +             "<value>"
            +               "<a>1</a>"
            +               "<b>2</b>"
            +             "</value>"
            +           "</contextAttribute>"
            +           "<contextAttribute>"
            +             "<name>field2</name>"
            +             "<type>type2</type>"
            +             "<value type=\"vector\">"
            +               "<item>v1</item>"
            +               "<item>v2</item>"
            +             "</value>"
            +           "</contextAttribute>"
            +         "</contextAttributeList>"
            +       "</contextElement>"
            +       "<statusCode>"
            +         "<code>200</code>"
            +         "<reasonPhrase>OK</reasonPhrase>"
            +       "</statusCode>"
            +     "</contextElementResponse>"
            +   "</contextResponseList>"
            + "</notifyContextRequest>";
    private final String notifyJsonSimple = ""
            + "{"
            +   "\"subscriptionId\" : \"51c0ac9ed714fb3b37d7d5a8\","
            +   "\"originator\" : \"localhost\","
            +   "\"contextResponses\" : ["
            +     "{"
            +       "\"contextElement\" : {"
            +         "\"attributes\" : ["
            +           "{"
            +             "\"name\" : \"temperature\","
            +             "\"type\" : \"centigrade\","
            +             "\"value\" : \"26.5\""
            +           "}"
            +         "],"
            +         "\"type\" : \"Room\","
            +         "\"isPattern\" : \"false\","
            +         "\"id\" : \"Room1\""
            +       "},"
            +       "\"statusCode\" : {"
            +         "\"code\" : \"200\","
            +         "\"reasonPhrase\" : \"OK\""
            +       "}"
            +     "}"
            +   "]"
            + "}";
    private final String notifyJsonCompound = ""
            + "{"
            +   "\"subscriptionId\" : \"51c0ac9ed714fb3b37d7d5a8\","
            +   "\"originator\" : \"localhost\","
            +   "\"contextResponses\" : ["
            +     "{"
            +       "\"contextElement\" : {"
            +         "\"attributes\" : ["
            +           "{"
            +             "\"name\" : \"field1\","
            +             "\"type\" : \"type1\","
            +             "\"value\" : { \"a\": \"1\", \"b\": \"2\" }"
            +           "},"
            +           "{"
            +             "\"name\" : \"field2\","
            +             "\"type\" : \"type2\","
            +             "\"value\" : [ \"v1\", \"v2\" ]"
            +           "}"
            +         "],"
            +         "\"type\" : \"Room\","
            +         "\"isPattern\" : \"false\","
            +         "\"id\" : \"Room2\""
            +       "},"
            +       "\"statusCode\" : {"
            +         "\"code\" : \"200\","
            +         "\"reasonPhrase\" : \"OK\""
            +       "}"
            +     "}"
            +   "]"
            + "}";
    /**
     * Constructor.
     * @param testName
     */
    public NotifyContextRequestTest(String testName) {
        super(testName);
        logger = Logger.getLogger("NotifyContextRequestTest.class");
    } // NotifyContextRequestTest

    /**
     * Test of getSubscriptionId method, of class NotifyContextRequest.
     */
    public void testGetSubscriptionId() {
        String expResult = "51c0ac9ed714fb3b37d7d5a8";
        
        // test case for nofity-xml-simple
        System.out.println("getSubscriptionId (notify-xml-simple)");
        NotifyContextRequest instance = createXMLNotifyContextRequest(notifyXMLSimple);
        String result = instance.getSubscriptionId();
        assertEquals(expResult, result);
        
        // test case for notify-xml-compound
        System.out.println("getSubscriptionId (notify-xml-compound)");
        instance = createXMLNotifyContextRequest(notifyXMLCompound);
        result = instance.getSubscriptionId();
        assertEquals(expResult, result);
        
        // test case for nofity-json-simple
        System.out.println("getSubscriptionId (notify-json-simple)");
        Gson gson = new Gson();
        instance = gson.fromJson(notifyJsonSimple, NotifyContextRequest.class);
        result = instance.getSubscriptionId();
        assertEquals(expResult, result);
        
        // test case for nofify-json-compound
        System.out.println("getSubscriptionId (notify-json-compound)");
        instance = gson.fromJson(notifyJsonCompound, NotifyContextRequest.class);
        result = instance.getSubscriptionId();
        assertEquals(expResult, result);
    } // testGetSubscriptionID

    /**
     * Test of getOriginator method, of class NotifyContextRequest.
     */
    public void testGetOriginator() {
        String expResult = "localhost";
        
        // test case for nofity-xml-simple
        System.out.println("getOriginator (notify-xml-simple)");
        NotifyContextRequest instance = createXMLNotifyContextRequest(notifyXMLSimple);
        String result = instance.getOriginator();
        assertEquals(expResult, result);
        
        // test case for notify-xml-compound
        System.out.println("getOriginator (notify-xml-compound)");
        instance = createXMLNotifyContextRequest(notifyXMLCompound);
        result = instance.getOriginator();
        assertEquals(expResult, result);
        
        // test case for nofity-json-simple
        System.out.println("getOriginator (notify-json-simple)");
        Gson gson = new Gson();
        instance = gson.fromJson(notifyJsonSimple, NotifyContextRequest.class);
        result = instance.getOriginator();
        assertEquals(expResult, result);
        
        // test case for nofify-json-compound
        System.out.println("getOriginator (notify-json-compound)");
        instance = gson.fromJson(notifyJsonCompound, NotifyContextRequest.class);
        result = instance.getOriginator();
        assertEquals(expResult, result);
    } // testGetOriginator

    /**
     * Test of getContextResponses method, of class NotifyContextRequest.
     */
    public void testGetContextResponses() {
        // test case for nofity-xml-simple
        System.out.println("getOriginator (notify-xml-simple)");
        NotifyContextRequest instance = createXMLNotifyContextRequest(notifyXMLSimple);
        ArrayList result = instance.getContextResponses();
        assertTrue(result != null);
        
        // test case for notify-xml-compound
        System.out.println("getOriginator (notify-xml-compound)");
        instance = createXMLNotifyContextRequest(notifyXMLCompound);
        result = instance.getContextResponses();
        assertTrue(result != null);
        
        // test case for nofity-json-simple
        System.out.println("getOriginator (notify-json-simple)");
        Gson gson = new Gson();
        instance = gson.fromJson(notifyJsonSimple, NotifyContextRequest.class);
        result = instance.getContextResponses();
        assertTrue(result != null);
        
        // test case for nofify-json-compound
        System.out.println("getOriginator (notify-json-compound)");
        instance = gson.fromJson(notifyJsonCompound, NotifyContextRequest.class);
        result = instance.getContextResponses();
        assertTrue(result != null);
    } // testGetContextResponses
    
    /**
     * Create a XML-based notificationContextRequest given the string representation of such XML.
     * @param xmlStr
     * @return The XML-based notificationContextRequest
     */
    private NotifyContextRequest createXMLNotifyContextRequest(String xmlStr) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.fatal(e.getMessage());
        } // try catch

        InputSource is = new InputSource(new StringReader(xmlStr));
        Document doc = null;
        
        try {
            doc = dBuilder.parse(is);
        } catch (SAXException e) {
            logger.fatal(e.getMessage());
        } catch (IOException e) {
            logger.fatal(e.getMessage());
        } // try catch
        
        doc.getDocumentElement().normalize();
        NotifyContextRequest instance = null;
        
        try {
            instance = new NotifyContextRequest(doc);
        } catch (Exception e) {
            logger.fatal(e.getMessage());
        } // try catch
        
        return instance;
    } // createXMLNotifyContextRequest

} // NotifyContextRequestTest