/*    */ package com.sun.xml.internal.fastinfoset.tools;
/*    */ 
/*    */ import com.sun.xml.internal.fastinfoset.sax.SAXDocumentSerializer;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.io.Reader;
/*    */ import javax.xml.parsers.SAXParser;
/*    */ import javax.xml.parsers.SAXParserFactory;
/*    */ import org.xml.sax.ContentHandler;
/*    */ import org.xml.sax.InputSource;
/*    */ import org.xml.sax.XMLReader;
/*    */ import org.xml.sax.helpers.DefaultHandler;
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
/*    */ public class XML_SAX_FI
/*    */   extends TransformInputOutput
/*    */ {
/*    */   public void parse(InputStream xml, OutputStream finf, String workingDirectory) throws Exception {
/* 45 */     SAXParser saxParser = getParser();
/* 46 */     SAXDocumentSerializer documentSerializer = getSerializer(finf);
/*    */     
/* 48 */     XMLReader reader = saxParser.getXMLReader();
/* 49 */     reader.setProperty("http://xml.org/sax/properties/lexical-handler", documentSerializer);
/* 50 */     reader.setContentHandler((ContentHandler)documentSerializer);
/*    */     
/* 52 */     if (workingDirectory != null) {
/* 53 */       reader.setEntityResolver(createRelativePathResolver(workingDirectory));
/*    */     }
/* 55 */     reader.parse(new InputSource(xml));
/*    */   }
/*    */   
/*    */   public void parse(InputStream xml, OutputStream finf) throws Exception {
/* 59 */     parse(xml, finf, (String)null);
/*    */   }
/*    */   
/*    */   public void convert(Reader reader, OutputStream finf) throws Exception {
/* 63 */     InputSource is = new InputSource(reader);
/*    */     
/* 65 */     SAXParser saxParser = getParser();
/* 66 */     SAXDocumentSerializer documentSerializer = getSerializer(finf);
/*    */     
/* 68 */     saxParser.setProperty("http://xml.org/sax/properties/lexical-handler", documentSerializer);
/* 69 */     saxParser.parse(is, (DefaultHandler)documentSerializer);
/*    */   }
/*    */   
/*    */   private SAXParser getParser() {
/* 73 */     SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
/* 74 */     saxParserFactory.setNamespaceAware(true);
/*    */     try {
/* 76 */       return saxParserFactory.newSAXParser();
/* 77 */     } catch (Exception e) {
/* 78 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   private SAXDocumentSerializer getSerializer(OutputStream finf) {
/* 83 */     SAXDocumentSerializer documentSerializer = new SAXDocumentSerializer();
/* 84 */     documentSerializer.setOutputStream(finf);
/* 85 */     return documentSerializer;
/*    */   }
/*    */   
/*    */   public static void main(String[] args) throws Exception {
/* 89 */     XML_SAX_FI s = new XML_SAX_FI();
/* 90 */     s.parse(args);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/tools/XML_SAX_FI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */