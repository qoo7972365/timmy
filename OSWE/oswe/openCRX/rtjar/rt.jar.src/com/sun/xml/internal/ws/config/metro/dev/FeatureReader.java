/*    */ package com.sun.xml.internal.ws.config.metro.dev;
/*    */ 
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.stream.XMLEventReader;
/*    */ import javax.xml.ws.WebServiceException;
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
/*    */ public interface FeatureReader<T extends javax.xml.ws.WebServiceFeature>
/*    */ {
/* 40 */   public static final QName ENABLED_ATTRIBUTE_NAME = new QName("enabled");
/*    */   
/*    */   T parse(XMLEventReader paramXMLEventReader) throws WebServiceException;
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/config/metro/dev/FeatureReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */