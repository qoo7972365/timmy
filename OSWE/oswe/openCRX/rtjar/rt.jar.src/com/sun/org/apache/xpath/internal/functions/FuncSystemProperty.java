/*     */ package com.sun.org.apache.xpath.internal.functions;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.utils.ObjectFactory;
/*     */ import com.sun.org.apache.xalan.internal.utils.SecuritySupport;
/*     */ import com.sun.org.apache.xml.internal.utils.WrappedRuntimeException;
/*     */ import com.sun.org.apache.xpath.internal.XPathContext;
/*     */ import com.sun.org.apache.xpath.internal.objects.XObject;
/*     */ import com.sun.org.apache.xpath.internal.objects.XString;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.util.Properties;
/*     */ import javax.xml.transform.TransformerException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FuncSystemProperty
/*     */   extends FunctionOneArg
/*     */ {
/*     */   static final long serialVersionUID = 3694874980992204867L;
/*     */   static final String XSLT_PROPERTIES = "com/sun/org/apache/xalan/internal/res/XSLTInfo.properties";
/*     */   
/*     */   public XObject execute(XPathContext xctxt) throws TransformerException {
/*  63 */     String result, fullName = this.m_arg0.execute(xctxt).str();
/*  64 */     int indexOfNSSep = fullName.indexOf(':');
/*     */     
/*  66 */     String propName = "";
/*     */ 
/*     */ 
/*     */     
/*  70 */     Properties xsltInfo = new Properties();
/*     */     
/*  72 */     loadPropertyFile("com/sun/org/apache/xalan/internal/res/XSLTInfo.properties", xsltInfo);
/*     */     
/*  74 */     if (indexOfNSSep > 0) {
/*     */ 
/*     */       
/*  77 */       String prefix = (indexOfNSSep >= 0) ? fullName.substring(0, indexOfNSSep) : "";
/*     */ 
/*     */       
/*  80 */       String namespace = xctxt.getNamespaceContext().getNamespaceForPrefix(prefix);
/*     */       
/*  82 */       propName = (indexOfNSSep < 0) ? fullName : fullName.substring(indexOfNSSep + 1);
/*     */       
/*  84 */       if (namespace.startsWith("http://www.w3.org/XSL/Transform") || namespace
/*  85 */         .equals("http://www.w3.org/1999/XSL/Transform")) {
/*     */         
/*  87 */         result = xsltInfo.getProperty(propName);
/*     */         
/*  89 */         if (null == result)
/*     */         {
/*  91 */           warn(xctxt, "WG_PROPERTY_NOT_SUPPORTED", new Object[] { fullName });
/*     */ 
/*     */           
/*  94 */           return XString.EMPTYSTRING;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/*  99 */         warn(xctxt, "WG_DONT_DO_ANYTHING_WITH_NS", new Object[] { namespace, fullName });
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 105 */           result = SecuritySupport.getSystemProperty(propName);
/*     */           
/* 107 */           if (null == result)
/*     */           {
/*     */ 
/*     */             
/* 111 */             return XString.EMPTYSTRING;
/*     */           }
/*     */         }
/* 114 */         catch (SecurityException se) {
/*     */           
/* 116 */           warn(xctxt, "WG_SECURITY_EXCEPTION", new Object[] { fullName });
/*     */ 
/*     */           
/* 119 */           return XString.EMPTYSTRING;
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 127 */         result = SecuritySupport.getSystemProperty(fullName);
/*     */         
/* 129 */         if (null == result)
/*     */         {
/*     */ 
/*     */           
/* 133 */           return XString.EMPTYSTRING;
/*     */         }
/*     */       }
/* 136 */       catch (SecurityException se) {
/*     */         
/* 138 */         warn(xctxt, "WG_SECURITY_EXCEPTION", new Object[] { fullName });
/*     */ 
/*     */         
/* 141 */         return XString.EMPTYSTRING;
/*     */       } 
/*     */     } 
/*     */     
/* 145 */     if (propName.equals("version") && result.length() > 0) {
/*     */       
/*     */       try {
/*     */ 
/*     */         
/* 150 */         return new XString("1.0");
/*     */       }
/* 152 */       catch (Exception ex) {
/*     */         
/* 154 */         return new XString(result);
/*     */       } 
/*     */     }
/*     */     
/* 158 */     return new XString(result);
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
/*     */   public void loadPropertyFile(String file, Properties target) {
/*     */     try {
/* 173 */       InputStream is = SecuritySupport.getResourceAsStream(ObjectFactory.findClassLoader(), file);
/*     */ 
/*     */ 
/*     */       
/* 177 */       BufferedInputStream bis = new BufferedInputStream(is);
/*     */       
/* 179 */       target.load(bis);
/* 180 */       bis.close();
/*     */     }
/* 182 */     catch (Exception ex) {
/*     */ 
/*     */       
/* 185 */       throw new WrappedRuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/functions/FuncSystemProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */