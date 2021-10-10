/*     */ package org.xml.sax.helpers;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import org.xml.sax.SAXException;
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
/*     */ public final class XMLReaderFactory
/*     */ {
/*     */   private static final String property = "org.xml.sax.driver";
/*  86 */   private static SecuritySupport ss = new SecuritySupport();
/*     */   
/*  88 */   private static String _clsFromJar = null;
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
/*     */   private static boolean _jarread = false;
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
/*     */   public static XMLReader createXMLReader() throws SAXException {
/* 135 */     String className = null;
/* 136 */     ClassLoader cl = ss.getContextClassLoader();
/*     */ 
/*     */     
/*     */     try {
/* 140 */       className = ss.getSystemProperty("org.xml.sax.driver");
/*     */     }
/* 142 */     catch (RuntimeException runtimeException) {}
/*     */ 
/*     */     
/* 145 */     if (className == null) {
/* 146 */       if (!_jarread) {
/* 147 */         _jarread = true;
/* 148 */         String service = "META-INF/services/org.xml.sax.driver";
/*     */         
/*     */         try {
/*     */           InputStream in;
/*     */           
/* 153 */           if (cl != null) {
/* 154 */             in = ss.getResourceAsStream(cl, service);
/*     */ 
/*     */             
/* 157 */             if (in == null) {
/* 158 */               cl = null;
/* 159 */               in = ss.getResourceAsStream(cl, service);
/*     */             } 
/*     */           } else {
/*     */             
/* 163 */             in = ss.getResourceAsStream(cl, service);
/*     */           } 
/*     */           
/* 166 */           if (in != null) {
/* 167 */             BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF8"));
/* 168 */             _clsFromJar = reader.readLine();
/* 169 */             in.close();
/*     */           } 
/* 171 */         } catch (Exception exception) {}
/*     */       } 
/*     */       
/* 174 */       className = _clsFromJar;
/*     */     } 
/*     */ 
/*     */     
/* 178 */     if (className == null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 184 */       className = "com.sun.org.apache.xerces.internal.parsers.SAXParser";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 190 */     if (className != null) {
/* 191 */       return loadClass(cl, className);
/*     */     }
/*     */     
/*     */     try {
/* 195 */       return new ParserAdapter(ParserFactory.makeParser());
/* 196 */     } catch (Exception e) {
/* 197 */       throw new SAXException("Can't create default XMLReader; is system property org.xml.sax.driver set?");
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
/*     */   public static XMLReader createXMLReader(String className) throws SAXException {
/* 221 */     return loadClass(ss.getContextClassLoader(), className);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static XMLReader loadClass(ClassLoader loader, String className) throws SAXException {
/*     */     try {
/* 228 */       return (XMLReader)NewInstance.newInstance(loader, className);
/* 229 */     } catch (ClassNotFoundException e1) {
/* 230 */       throw new SAXException("SAX2 driver class " + className + " not found", e1);
/*     */     }
/* 232 */     catch (IllegalAccessException e2) {
/* 233 */       throw new SAXException("SAX2 driver class " + className + " found but cannot be loaded", e2);
/*     */     }
/* 235 */     catch (InstantiationException e3) {
/* 236 */       throw new SAXException("SAX2 driver class " + className + " loaded but cannot be instantiated (no empty public constructor?)", e3);
/*     */     
/*     */     }
/* 239 */     catch (ClassCastException e4) {
/* 240 */       throw new SAXException("SAX2 driver class " + className + " does not implement XMLReader", e4);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/xml/sax/helpers/XMLReaderFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */