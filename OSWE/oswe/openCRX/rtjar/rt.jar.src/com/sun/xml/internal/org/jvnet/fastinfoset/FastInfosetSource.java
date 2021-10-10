/*    */ package com.sun.xml.internal.org.jvnet.fastinfoset;
/*    */ 
/*    */ import com.sun.xml.internal.fastinfoset.sax.SAXDocumentParser;
/*    */ import java.io.InputStream;
/*    */ import javax.xml.transform.sax.SAXSource;
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
/*    */ public class FastInfosetSource
/*    */   extends SAXSource
/*    */ {
/*    */   public FastInfosetSource(InputStream inputStream) {
/* 65 */     super(new InputSource(inputStream));
/*    */   }
/*    */   public XMLReader getXMLReader() {
/*    */     SAXDocumentParser sAXDocumentParser;
/* 69 */     XMLReader reader = super.getXMLReader();
/* 70 */     if (reader == null) {
/* 71 */       sAXDocumentParser = new SAXDocumentParser();
/* 72 */       setXMLReader((XMLReader)sAXDocumentParser);
/*    */     } 
/* 74 */     sAXDocumentParser.setInputStream(getInputStream());
/* 75 */     return (XMLReader)sAXDocumentParser;
/*    */   }
/*    */   
/*    */   public InputStream getInputStream() {
/* 79 */     return getInputSource().getByteStream();
/*    */   }
/*    */   
/*    */   public void setInputStream(InputStream inputStream) {
/* 83 */     setInputSource(new InputSource(inputStream));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/fastinfoset/FastInfosetSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */