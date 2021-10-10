/*     */ package com.sun.org.apache.xml.internal.security.utils;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.transforms.implementations.FuncHere;
/*     */ import com.sun.org.apache.xml.internal.utils.PrefixResolver;
/*     */ import com.sun.org.apache.xml.internal.utils.PrefixResolverDefault;
/*     */ import com.sun.org.apache.xpath.internal.Expression;
/*     */ import com.sun.org.apache.xpath.internal.XPath;
/*     */ import com.sun.org.apache.xpath.internal.XPathContext;
/*     */ import com.sun.org.apache.xpath.internal.compiler.FunctionTable;
/*     */ import com.sun.org.apache.xpath.internal.objects.XObject;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.transform.ErrorListener;
/*     */ import javax.xml.transform.SourceLocator;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XalanXPathAPI
/*     */   implements XPathAPI
/*     */ {
/*  52 */   private static Logger log = Logger.getLogger(XalanXPathAPI.class.getName());
/*     */   
/*  54 */   private String xpathStr = null;
/*     */   
/*  56 */   private XPath xpath = null;
/*     */   
/*  58 */   private static FunctionTable funcTable = null;
/*     */   
/*     */   private static boolean installed;
/*     */   
/*     */   private XPathContext context;
/*     */   
/*     */   static {
/*  65 */     fixupFunctionTable();
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
/*     */   public NodeList selectNodeList(Node paramNode1, Node paramNode2, String paramString, Node paramNode3) throws TransformerException {
/*  86 */     XObject xObject = eval(paramNode1, paramNode2, paramString, paramNode3);
/*     */ 
/*     */     
/*  89 */     return xObject.nodelist();
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
/*     */   public boolean evaluate(Node paramNode1, Node paramNode2, String paramString, Node paramNode3) throws TransformerException {
/* 101 */     XObject xObject = eval(paramNode1, paramNode2, paramString, paramNode3);
/* 102 */     return xObject.bool();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 109 */     this.xpathStr = null;
/* 110 */     this.xpath = null;
/* 111 */     this.context = null;
/*     */   }
/*     */   
/*     */   public static synchronized boolean isInstalled() {
/* 115 */     return installed;
/*     */   }
/*     */ 
/*     */   
/*     */   private XObject eval(Node paramNode1, Node paramNode2, String paramString, Node paramNode3) throws TransformerException {
/* 120 */     if (this.context == null) {
/* 121 */       this.context = new XPathContext(paramNode2);
/* 122 */       this.context.setSecureProcessing(true);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     Node node = (paramNode3.getNodeType() == 9) ? ((Document)paramNode3).getDocumentElement() : paramNode3;
/* 132 */     PrefixResolverDefault prefixResolverDefault = new PrefixResolverDefault(node);
/*     */     
/* 134 */     if (!paramString.equals(this.xpathStr)) {
/* 135 */       if (paramString.indexOf("here()") > 0) {
/* 136 */         this.context.reset();
/*     */       }
/* 138 */       this.xpath = createXPath(paramString, prefixResolverDefault);
/* 139 */       this.xpathStr = paramString;
/*     */     } 
/*     */ 
/*     */     
/* 143 */     int i = this.context.getDTMHandleFromNode(paramNode1);
/*     */     
/* 145 */     return this.xpath.execute(this.context, i, prefixResolverDefault);
/*     */   }
/*     */   
/*     */   private XPath createXPath(String paramString, PrefixResolver paramPrefixResolver) throws TransformerException {
/* 149 */     XPath xPath = null;
/* 150 */     Class[] arrayOfClass = { String.class, SourceLocator.class, PrefixResolver.class, int.class, ErrorListener.class, FunctionTable.class };
/*     */ 
/*     */     
/* 153 */     Object[] arrayOfObject = { paramString, null, paramPrefixResolver, Integer.valueOf(0), null, funcTable };
/*     */     try {
/* 155 */       Constructor<XPath> constructor = XPath.class.getConstructor(arrayOfClass);
/* 156 */       xPath = constructor.newInstance(arrayOfObject);
/* 157 */     } catch (Exception exception) {
/* 158 */       if (log.isLoggable(Level.FINE)) {
/* 159 */         log.log(Level.FINE, exception.getMessage(), exception);
/*     */       }
/*     */     } 
/* 162 */     if (xPath == null) {
/* 163 */       xPath = new XPath(paramString, null, paramPrefixResolver, 0, null);
/*     */     }
/* 165 */     return xPath;
/*     */   }
/*     */   
/*     */   private static synchronized void fixupFunctionTable() {
/* 169 */     installed = false;
/* 170 */     if (log.isLoggable(Level.FINE)) {
/* 171 */       log.log(Level.FINE, "Registering Here function");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 177 */       Class[] arrayOfClass = { String.class, Expression.class };
/* 178 */       Method method = FunctionTable.class.getMethod("installFunction", arrayOfClass);
/* 179 */       if ((method.getModifiers() & 0x8) != 0) {
/* 180 */         Object[] arrayOfObject = { "here", new FuncHere() };
/* 181 */         method.invoke(null, arrayOfObject);
/* 182 */         installed = true;
/*     */       } 
/* 184 */     } catch (Exception exception) {
/* 185 */       log.log(Level.FINE, "Error installing function using the static installFunction method", exception);
/*     */     } 
/* 187 */     if (!installed) {
/*     */       try {
/* 189 */         funcTable = new FunctionTable();
/* 190 */         Class[] arrayOfClass = { String.class, Class.class };
/* 191 */         Method method = FunctionTable.class.getMethod("installFunction", arrayOfClass);
/* 192 */         Object[] arrayOfObject = { "here", FuncHere.class };
/* 193 */         method.invoke(funcTable, arrayOfObject);
/* 194 */         installed = true;
/* 195 */       } catch (Exception exception) {
/* 196 */         log.log(Level.FINE, "Error installing function using the static installFunction method", exception);
/*     */       } 
/*     */     }
/* 199 */     if (log.isLoggable(Level.FINE))
/* 200 */       if (installed) {
/* 201 */         log.log(Level.FINE, "Registered class " + FuncHere.class.getName() + " for XPath function 'here()' function in internal table");
/*     */       } else {
/*     */         
/* 204 */         log.log(Level.FINE, "Unable to register class " + FuncHere.class.getName() + " for XPath function 'here()' function in internal table");
/*     */       }  
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/XalanXPathAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */