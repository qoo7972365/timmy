/*    */ package com.sun.xml.internal.fastinfoset.tools;
/*    */ 
/*    */ import com.sun.xml.internal.fastinfoset.stax.StAXDocumentSerializer;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import javax.xml.parsers.SAXParser;
/*    */ import javax.xml.parsers.SAXParserFactory;
/*    */ import javax.xml.stream.XMLStreamWriter;
/*    */ import org.xml.sax.InputSource;
/*    */ import org.xml.sax.XMLReader;
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
/*    */ public class XML_SAX_StAX_FI
/*    */   extends TransformInputOutput
/*    */ {
/*    */   public void parse(InputStream xml, OutputStream finf, String workingDirectory) throws Exception {
/* 44 */     StAXDocumentSerializer documentSerializer = new StAXDocumentSerializer();
/* 45 */     documentSerializer.setOutputStream(finf);
/*    */     
/* 47 */     SAX2StAXWriter saxTostax = new SAX2StAXWriter((XMLStreamWriter)documentSerializer);
/*    */     
/* 49 */     SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
/* 50 */     saxParserFactory.setNamespaceAware(true);
/* 51 */     SAXParser saxParser = saxParserFactory.newSAXParser();
/*    */     
/* 53 */     XMLReader reader = saxParser.getXMLReader();
/* 54 */     reader.setProperty("http://xml.org/sax/properties/lexical-handler", saxTostax);
/* 55 */     reader.setContentHandler(saxTostax);
/*    */     
/* 57 */     if (workingDirectory != null) {
/* 58 */       reader.setEntityResolver(createRelativePathResolver(workingDirectory));
/*    */     }
/* 60 */     reader.parse(new InputSource(xml));
/*    */     
/* 62 */     xml.close();
/* 63 */     finf.close();
/*    */   }
/*    */   
/*    */   public void parse(InputStream xml, OutputStream finf) throws Exception {
/* 67 */     parse(xml, finf, (String)null);
/*    */   }
/*    */   
/*    */   public static void main(String[] args) throws Exception {
/* 71 */     XML_SAX_StAX_FI s = new XML_SAX_StAX_FI();
/* 72 */     s.parse(args);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/tools/XML_SAX_StAX_FI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */