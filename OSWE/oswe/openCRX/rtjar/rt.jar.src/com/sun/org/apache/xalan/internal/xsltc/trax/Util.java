/*     */ package com.sun.org.apache.xalan.internal.xsltc.trax;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.utils.XMLSecurityManager;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.XSLTC;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import javax.xml.stream.XMLEventReader;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.sax.SAXSource;
/*     */ import javax.xml.transform.stax.StAXSource;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import jdk.xml.internal.JdkXmlFeatures;
/*     */ import jdk.xml.internal.JdkXmlUtils;
/*     */ import org.w3c.dom.Document;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXNotRecognizedException;
/*     */ import org.xml.sax.SAXNotSupportedException;
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
/*     */ public final class Util
/*     */ {
/*     */   private static final String property = "org.xml.sax.driver";
/*     */   
/*     */   public static String baseName(String name) {
/*  61 */     return com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util.baseName(name);
/*     */   }
/*     */   
/*     */   public static String noExtName(String name) {
/*  65 */     return com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util.noExtName(name);
/*     */   }
/*     */   
/*     */   public static String toJavaName(String name) {
/*  69 */     return com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util.toJavaName(name);
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
/*     */   public static InputSource getInputSource(XSLTC xsltc, Source source) throws TransformerConfigurationException {
/*  81 */     InputSource input = null;
/*     */     
/*  83 */     String systemId = source.getSystemId();
/*     */ 
/*     */     
/*     */     try {
/*  87 */       if (source instanceof SAXSource) {
/*  88 */         SAXSource sax = (SAXSource)source;
/*  89 */         input = sax.getInputSource();
/*     */         
/*     */         try {
/*  92 */           XMLReader reader = sax.getXMLReader();
/*     */           
/*  94 */           if (reader == null) {
/*  95 */             boolean overrideDefaultParser = xsltc.getFeature(JdkXmlFeatures.XmlFeature.JDK_OVERRIDE_PARSER);
/*     */             
/*  97 */             reader = JdkXmlUtils.getXMLReader(overrideDefaultParser, xsltc
/*  98 */                 .isSecureProcessing());
/*     */           } else {
/*     */             
/* 101 */             reader
/* 102 */               .setFeature("http://xml.org/sax/features/namespaces", true);
/* 103 */             reader
/* 104 */               .setFeature("http://xml.org/sax/features/namespace-prefixes", false);
/*     */           } 
/*     */           
/*     */           try {
/* 108 */             reader.setProperty("http://javax.xml.XMLConstants/property/accessExternalDTD", xsltc
/* 109 */                 .getProperty("http://javax.xml.XMLConstants/property/accessExternalDTD"));
/* 110 */           } catch (SAXNotRecognizedException e) {
/* 111 */             XMLSecurityManager.printWarning(reader.getClass().getName(), "http://javax.xml.XMLConstants/property/accessExternalDTD", e);
/*     */           } 
/*     */ 
/*     */           
/* 115 */           String lastProperty = "";
/*     */           
/*     */           try {
/* 118 */             XMLSecurityManager securityManager = (XMLSecurityManager)xsltc.getProperty("http://apache.org/xml/properties/security-manager");
/* 119 */             if (securityManager != null) {
/* 120 */               for (XMLSecurityManager.Limit limit : XMLSecurityManager.Limit.values()) {
/* 121 */                 lastProperty = limit.apiProperty();
/* 122 */                 reader.setProperty(lastProperty, securityManager
/* 123 */                     .getLimitValueAsString(limit));
/*     */               } 
/* 125 */               if (securityManager.printEntityCountInfo()) {
/* 126 */                 lastProperty = "http://www.oracle.com/xml/jaxp/properties/getEntityCountInfo";
/* 127 */                 reader.setProperty("http://www.oracle.com/xml/jaxp/properties/getEntityCountInfo", "yes");
/*     */               } 
/*     */             } 
/* 130 */           } catch (SAXException se) {
/* 131 */             XMLSecurityManager.printWarning(reader.getClass().getName(), lastProperty, se);
/*     */           } 
/* 133 */           xsltc.setXMLReader(reader);
/* 134 */         } catch (SAXNotRecognizedException snre) {
/* 135 */           throw new TransformerConfigurationException("SAXNotRecognizedException ", snre);
/*     */         }
/* 137 */         catch (SAXNotSupportedException snse) {
/* 138 */           throw new TransformerConfigurationException("SAXNotSupportedException ", snse);
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 144 */       else if (source instanceof DOMSource) {
/* 145 */         DOMSource domsrc = (DOMSource)source;
/* 146 */         Document dom = (Document)domsrc.getNode();
/* 147 */         DOM2SAX dom2sax = new DOM2SAX(dom);
/* 148 */         xsltc.setXMLReader(dom2sax);
/*     */ 
/*     */         
/* 151 */         input = SAXSource.sourceToInputSource(source);
/* 152 */         if (input == null) {
/* 153 */           input = new InputSource(domsrc.getSystemId());
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 158 */       else if (source instanceof StAXSource) {
/* 159 */         StAXSource staxSource = (StAXSource)source;
/* 160 */         StAXEvent2SAX staxevent2sax = null;
/* 161 */         StAXStream2SAX staxStream2SAX = null;
/* 162 */         if (staxSource.getXMLEventReader() != null) {
/* 163 */           XMLEventReader xmlEventReader = staxSource.getXMLEventReader();
/* 164 */           staxevent2sax = new StAXEvent2SAX(xmlEventReader);
/* 165 */           xsltc.setXMLReader(staxevent2sax);
/* 166 */         } else if (staxSource.getXMLStreamReader() != null) {
/* 167 */           XMLStreamReader xmlStreamReader = staxSource.getXMLStreamReader();
/* 168 */           staxStream2SAX = new StAXStream2SAX(xmlStreamReader);
/* 169 */           xsltc.setXMLReader(staxStream2SAX);
/*     */         } 
/*     */ 
/*     */         
/* 173 */         input = SAXSource.sourceToInputSource(source);
/* 174 */         if (input == null) {
/* 175 */           input = new InputSource(staxSource.getSystemId());
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 180 */       else if (source instanceof StreamSource) {
/* 181 */         StreamSource stream = (StreamSource)source;
/* 182 */         InputStream istream = stream.getInputStream();
/* 183 */         Reader reader = stream.getReader();
/* 184 */         xsltc.setXMLReader(null);
/*     */ 
/*     */         
/* 187 */         if (istream != null) {
/* 188 */           input = new InputSource(istream);
/*     */         }
/* 190 */         else if (reader != null) {
/* 191 */           input = new InputSource(reader);
/*     */         } else {
/*     */           
/* 194 */           input = new InputSource(systemId);
/*     */         } 
/*     */       } else {
/*     */         
/* 198 */         ErrorMsg err = new ErrorMsg("JAXP_UNKNOWN_SOURCE_ERR");
/* 199 */         throw new TransformerConfigurationException(err.toString());
/*     */       } 
/* 201 */       input.setSystemId(systemId);
/*     */     }
/* 203 */     catch (NullPointerException e) {
/* 204 */       ErrorMsg err = new ErrorMsg("JAXP_NO_SOURCE_ERR", "TransformerFactory.newTemplates()");
/*     */       
/* 206 */       throw new TransformerConfigurationException(err.toString());
/*     */     }
/* 208 */     catch (SecurityException e) {
/* 209 */       ErrorMsg err = new ErrorMsg("FILE_ACCESS_ERR", systemId);
/* 210 */       throw new TransformerConfigurationException(err.toString());
/*     */     } 
/* 212 */     return input;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/trax/Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */