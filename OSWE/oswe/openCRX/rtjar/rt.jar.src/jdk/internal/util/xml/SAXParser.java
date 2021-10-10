/*     */ package jdk.internal.util.xml;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import jdk.internal.org.xml.sax.InputSource;
/*     */ import jdk.internal.org.xml.sax.SAXException;
/*     */ import jdk.internal.org.xml.sax.XMLReader;
/*     */ import jdk.internal.org.xml.sax.helpers.DefaultHandler;
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
/*     */ public abstract class SAXParser
/*     */ {
/*     */   public void parse(InputStream paramInputStream, DefaultHandler paramDefaultHandler) throws SAXException, IOException {
/* 102 */     if (paramInputStream == null) {
/* 103 */       throw new IllegalArgumentException("InputStream cannot be null");
/*     */     }
/*     */     
/* 106 */     InputSource inputSource = new InputSource(paramInputStream);
/* 107 */     parse(inputSource, paramDefaultHandler);
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
/*     */   public void parse(String paramString, DefaultHandler paramDefaultHandler) throws SAXException, IOException {
/* 127 */     if (paramString == null) {
/* 128 */       throw new IllegalArgumentException("uri cannot be null");
/*     */     }
/*     */     
/* 131 */     InputSource inputSource = new InputSource(paramString);
/* 132 */     parse(inputSource, paramDefaultHandler);
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
/*     */   public void parse(File paramFile, DefaultHandler paramDefaultHandler) throws SAXException, IOException {
/* 151 */     if (paramFile == null) {
/* 152 */       throw new IllegalArgumentException("File cannot be null");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 158 */     InputSource inputSource = new InputSource(paramFile.toURI().toASCIIString());
/* 159 */     parse(inputSource, paramDefaultHandler);
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
/*     */   public void parse(InputSource paramInputSource, DefaultHandler paramDefaultHandler) throws SAXException, IOException {
/* 180 */     if (paramInputSource == null) {
/* 181 */       throw new IllegalArgumentException("InputSource cannot be null");
/*     */     }
/*     */     
/* 184 */     XMLReader xMLReader = getXMLReader();
/* 185 */     if (paramDefaultHandler != null) {
/* 186 */       xMLReader.setContentHandler(paramDefaultHandler);
/* 187 */       xMLReader.setEntityResolver(paramDefaultHandler);
/* 188 */       xMLReader.setErrorHandler(paramDefaultHandler);
/* 189 */       xMLReader.setDTDHandler(paramDefaultHandler);
/*     */     } 
/* 191 */     xMLReader.parse(paramInputSource);
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
/*     */   public abstract XMLReader getXMLReader() throws SAXException;
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
/*     */   public abstract boolean isNamespaceAware();
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
/*     */   public abstract boolean isValidating();
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
/*     */   public boolean isXIncludeAware() {
/* 239 */     throw new UnsupportedOperationException("This parser does not support specification \"" + 
/*     */         
/* 241 */         getClass().getPackage().getSpecificationTitle() + "\" version \"" + 
/*     */         
/* 243 */         getClass().getPackage().getSpecificationVersion() + "\"");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/util/xml/SAXParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */