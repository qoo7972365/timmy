/*    */ package com.sun.org.apache.xml.internal.resolver.tools;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.resolver.CatalogManager;
/*    */ import javax.xml.parsers.SAXParser;
/*    */ import javax.xml.parsers.SAXParserFactory;
/*    */ import jdk.xml.internal.JdkXmlUtils;
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
/*    */ public class ResolvingXMLReader
/*    */   extends ResolvingXMLFilter
/*    */ {
/*    */   public static boolean namespaceAware = true;
/*    */   public static boolean validating = false;
/*    */   
/*    */   public ResolvingXMLReader() {
/* 68 */     SAXParserFactory spf = JdkXmlUtils.getSAXFactory(this.catalogManager.overrideDefaultParser());
/* 69 */     spf.setValidating(validating);
/*    */     try {
/* 71 */       SAXParser parser = spf.newSAXParser();
/* 72 */       setParent(parser.getXMLReader());
/* 73 */     } catch (Exception ex) {
/* 74 */       ex.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ResolvingXMLReader(CatalogManager manager) {
/* 86 */     super(manager);
/* 87 */     SAXParserFactory spf = JdkXmlUtils.getSAXFactory(this.catalogManager.overrideDefaultParser());
/* 88 */     spf.setValidating(validating);
/*    */     try {
/* 90 */       SAXParser parser = spf.newSAXParser();
/* 91 */       setParent(parser.getXMLReader());
/* 92 */     } catch (Exception ex) {
/* 93 */       ex.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/resolver/tools/ResolvingXMLReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */