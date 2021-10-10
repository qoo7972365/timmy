/*    */ package com.sun.xml.internal.ws.wsdl.parser;
/*    */ 
/*    */ import javax.xml.namespace.QName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface WSDLConstants
/*    */ {
/*    */   public static final String PREFIX_NS_WSDL = "wsdl";
/*    */   public static final String NS_XMLNS = "http://www.w3.org/2001/XMLSchema";
/*    */   public static final String NS_WSDL = "http://schemas.xmlsoap.org/wsdl/";
/*    */   public static final String NS_SOAP11_HTTP_BINDING = "http://schemas.xmlsoap.org/soap/http";
/* 43 */   public static final QName QNAME_SCHEMA = new QName("http://www.w3.org/2001/XMLSchema", "schema");
/*    */ 
/*    */   
/* 46 */   public static final QName QNAME_BINDING = new QName("http://schemas.xmlsoap.org/wsdl/", "binding");
/* 47 */   public static final QName QNAME_DEFINITIONS = new QName("http://schemas.xmlsoap.org/wsdl/", "definitions");
/* 48 */   public static final QName QNAME_DOCUMENTATION = new QName("http://schemas.xmlsoap.org/wsdl/", "documentation");
/* 49 */   public static final QName NS_SOAP_BINDING_ADDRESS = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "address");
/* 50 */   public static final QName NS_SOAP_BINDING = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "binding");
/* 51 */   public static final QName NS_SOAP12_BINDING = new QName("http://schemas.xmlsoap.org/wsdl/soap12/", "binding");
/* 52 */   public static final QName NS_SOAP12_BINDING_ADDRESS = new QName("http://schemas.xmlsoap.org/wsdl/soap12/", "address");
/*    */ 
/*    */   
/* 55 */   public static final QName QNAME_IMPORT = new QName("http://schemas.xmlsoap.org/wsdl/", "import");
/*    */ 
/*    */   
/* 58 */   public static final QName QNAME_MESSAGE = new QName("http://schemas.xmlsoap.org/wsdl/", "message");
/* 59 */   public static final QName QNAME_PART = new QName("http://schemas.xmlsoap.org/wsdl/", "part");
/* 60 */   public static final QName QNAME_OPERATION = new QName("http://schemas.xmlsoap.org/wsdl/", "operation");
/* 61 */   public static final QName QNAME_INPUT = new QName("http://schemas.xmlsoap.org/wsdl/", "input");
/* 62 */   public static final QName QNAME_OUTPUT = new QName("http://schemas.xmlsoap.org/wsdl/", "output");
/*    */ 
/*    */ 
/*    */   
/* 66 */   public static final QName QNAME_PORT = new QName("http://schemas.xmlsoap.org/wsdl/", "port");
/* 67 */   public static final QName QNAME_ADDRESS = new QName("http://schemas.xmlsoap.org/wsdl/", "address");
/* 68 */   public static final QName QNAME_PORT_TYPE = new QName("http://schemas.xmlsoap.org/wsdl/", "portType");
/* 69 */   public static final QName QNAME_FAULT = new QName("http://schemas.xmlsoap.org/wsdl/", "fault");
/* 70 */   public static final QName QNAME_SERVICE = new QName("http://schemas.xmlsoap.org/wsdl/", "service");
/* 71 */   public static final QName QNAME_TYPES = new QName("http://schemas.xmlsoap.org/wsdl/", "types");
/*    */   public static final String ATTR_TRANSPORT = "transport";
/*    */   public static final String ATTR_LOCATION = "location";
/*    */   public static final String ATTR_NAME = "name";
/*    */   public static final String ATTR_TNS = "targetNamespace";
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/wsdl/parser/WSDLConstants.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */