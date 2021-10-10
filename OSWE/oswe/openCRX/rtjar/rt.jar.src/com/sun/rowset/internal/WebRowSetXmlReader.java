/*     */ package com.sun.rowset.internal;
/*     */ 
/*     */ import com.sun.rowset.JdbcRowSetResourceBundle;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.Serializable;
/*     */ import java.sql.SQLException;
/*     */ import java.text.MessageFormat;
/*     */ import javax.sql.RowSetInternal;
/*     */ import javax.sql.rowset.WebRowSet;
/*     */ import javax.sql.rowset.spi.XmlReader;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ import org.xml.sax.XMLReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebRowSetXmlReader
/*     */   implements XmlReader, Serializable
/*     */ {
/*     */   private JdbcRowSetResourceBundle resBundle;
/*     */   static final long serialVersionUID = -9127058392819008014L;
/*     */   
/*     */   public WebRowSetXmlReader() {
/*     */     try {
/*  54 */       this.resBundle = JdbcRowSetResourceBundle.getJdbcRowSetResourceBundle();
/*  55 */     } catch (IOException iOException) {
/*  56 */       throw new RuntimeException(iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readXML(WebRowSet paramWebRowSet, Reader paramReader) throws SQLException {
/*     */     try {
/*  92 */       InputSource inputSource = new InputSource(paramReader);
/*  93 */       XmlErrorHandler xmlErrorHandler = new XmlErrorHandler();
/*  94 */       XmlReaderContentHandler xmlReaderContentHandler = new XmlReaderContentHandler(paramWebRowSet);
/*  95 */       SAXParserFactory sAXParserFactory = SAXParserFactory.newInstance();
/*  96 */       sAXParserFactory.setNamespaceAware(true);
/*  97 */       sAXParserFactory.setValidating(true);
/*  98 */       SAXParser sAXParser = sAXParserFactory.newSAXParser();
/*     */       
/* 100 */       sAXParser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
/*     */ 
/*     */       
/* 103 */       XMLReader xMLReader = sAXParser.getXMLReader();
/* 104 */       xMLReader.setEntityResolver(new XmlResolver());
/* 105 */       xMLReader.setContentHandler(xmlReaderContentHandler);
/*     */       
/* 107 */       xMLReader.setErrorHandler(xmlErrorHandler);
/*     */       
/* 109 */       xMLReader.parse(inputSource);
/*     */     }
/* 111 */     catch (SAXParseException sAXParseException) {
/* 112 */       System.out.println(MessageFormat.format(this.resBundle.handleGetObject("wrsxmlreader.parseerr").toString(), new Object[] { sAXParseException.getMessage(), Integer.valueOf(sAXParseException.getLineNumber()), sAXParseException.getSystemId() }));
/* 113 */       sAXParseException.printStackTrace();
/* 114 */       throw new SQLException(sAXParseException.getMessage());
/*     */     }
/* 116 */     catch (SAXException sAXException) {
/* 117 */       Exception exception = sAXException;
/* 118 */       if (sAXException.getException() != null)
/* 119 */         exception = sAXException.getException(); 
/* 120 */       exception.printStackTrace();
/* 121 */       throw new SQLException(exception.getMessage());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 127 */     catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 128 */       throw new SQLException(this.resBundle.handleGetObject("wrsxmlreader.invalidcp").toString());
/*     */     }
/* 130 */     catch (Throwable throwable) {
/* 131 */       throw new SQLException(MessageFormat.format(this.resBundle.handleGetObject("wrsxmlreader.readxml").toString(), new Object[] { throwable.getMessage() }));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readXML(WebRowSet paramWebRowSet, InputStream paramInputStream) throws SQLException {
/*     */     try {
/* 164 */       InputSource inputSource = new InputSource(paramInputStream);
/* 165 */       XmlErrorHandler xmlErrorHandler = new XmlErrorHandler();
/*     */       
/* 167 */       XmlReaderContentHandler xmlReaderContentHandler = new XmlReaderContentHandler(paramWebRowSet);
/* 168 */       SAXParserFactory sAXParserFactory = SAXParserFactory.newInstance();
/* 169 */       sAXParserFactory.setNamespaceAware(true);
/* 170 */       sAXParserFactory.setValidating(true);
/*     */       
/* 172 */       SAXParser sAXParser = sAXParserFactory.newSAXParser();
/*     */       
/* 174 */       sAXParser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
/*     */ 
/*     */       
/* 177 */       XMLReader xMLReader = sAXParser.getXMLReader();
/* 178 */       xMLReader.setEntityResolver(new XmlResolver());
/* 179 */       xMLReader.setContentHandler(xmlReaderContentHandler);
/*     */       
/* 181 */       xMLReader.setErrorHandler(xmlErrorHandler);
/*     */       
/* 183 */       xMLReader.parse(inputSource);
/*     */     }
/* 185 */     catch (SAXParseException sAXParseException) {
/* 186 */       System.out.println(MessageFormat.format(this.resBundle.handleGetObject("wrsxmlreader.parseerr").toString(), new Object[] { Integer.valueOf(sAXParseException.getLineNumber()), sAXParseException.getSystemId() }));
/* 187 */       System.out.println("   " + sAXParseException.getMessage());
/* 188 */       sAXParseException.printStackTrace();
/* 189 */       throw new SQLException(sAXParseException.getMessage());
/*     */     }
/* 191 */     catch (SAXException sAXException) {
/* 192 */       Exception exception = sAXException;
/* 193 */       if (sAXException.getException() != null)
/* 194 */         exception = sAXException.getException(); 
/* 195 */       exception.printStackTrace();
/* 196 */       throw new SQLException(exception.getMessage());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 202 */     catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 203 */       throw new SQLException(this.resBundle.handleGetObject("wrsxmlreader.invalidcp").toString());
/*     */     
/*     */     }
/* 206 */     catch (Throwable throwable) {
/* 207 */       throw new SQLException(MessageFormat.format(this.resBundle.handleGetObject("wrsxmlreader.readxml").toString(), new Object[] { throwable.getMessage() }));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readData(RowSetInternal paramRowSetInternal) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 226 */     paramObjectInputStream.defaultReadObject();
/*     */     
/*     */     try {
/* 229 */       this.resBundle = JdbcRowSetResourceBundle.getJdbcRowSetResourceBundle();
/* 230 */     } catch (IOException iOException) {
/* 231 */       throw new RuntimeException(iOException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/rowset/internal/WebRowSetXmlReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */