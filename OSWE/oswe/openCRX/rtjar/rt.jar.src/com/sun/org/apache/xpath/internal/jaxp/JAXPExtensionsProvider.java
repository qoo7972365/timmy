/*     */ package com.sun.org.apache.xpath.internal.jaxp;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.res.XSLMessages;
/*     */ import com.sun.org.apache.xml.internal.utils.WrappedRuntimeException;
/*     */ import com.sun.org.apache.xpath.internal.ExtensionsProvider;
/*     */ import com.sun.org.apache.xpath.internal.functions.FuncExtFunction;
/*     */ import com.sun.org.apache.xpath.internal.objects.XNodeSet;
/*     */ import com.sun.org.apache.xpath.internal.objects.XObject;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Vector;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.xpath.XPathFunction;
/*     */ import javax.xml.xpath.XPathFunctionException;
/*     */ import javax.xml.xpath.XPathFunctionResolver;
/*     */ import jdk.xml.internal.JdkXmlFeatures;
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
/*     */ public class JAXPExtensionsProvider
/*     */   implements ExtensionsProvider
/*     */ {
/*     */   private final XPathFunctionResolver resolver;
/*     */   private boolean extensionInvocationDisabled = false;
/*     */   
/*     */   public JAXPExtensionsProvider(XPathFunctionResolver resolver) {
/*  50 */     this.resolver = resolver;
/*  51 */     this.extensionInvocationDisabled = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public JAXPExtensionsProvider(XPathFunctionResolver resolver, boolean featureSecureProcessing, JdkXmlFeatures featureManager) {
/*  56 */     this.resolver = resolver;
/*  57 */     if (featureSecureProcessing && 
/*  58 */       !featureManager.getFeature(JdkXmlFeatures.XmlFeature.ENABLE_EXTENSION_FUNCTION)) {
/*  59 */       this.extensionInvocationDisabled = true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean functionAvailable(String ns, String funcName) throws TransformerException {
/*     */     try {
/*  70 */       if (funcName == null) {
/*  71 */         String fmsg = XSLMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[] { "Function Name" });
/*     */ 
/*     */         
/*  74 */         throw new NullPointerException(fmsg);
/*     */       } 
/*     */       
/*  77 */       QName myQName = new QName(ns, funcName);
/*     */       
/*  79 */       XPathFunction xpathFunction = this.resolver.resolveFunction(myQName, 0);
/*  80 */       if (xpathFunction == null) {
/*  81 */         return false;
/*     */       }
/*  83 */       return true;
/*  84 */     } catch (Exception e) {
/*  85 */       return false;
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
/*     */   public boolean elementAvailable(String ns, String elemName) throws TransformerException {
/*  97 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object extFunction(String ns, String funcName, Vector argVec, Object methodKey) throws TransformerException {
/*     */     try {
/* 107 */       if (funcName == null) {
/* 108 */         String fmsg = XSLMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[] { "Function Name" });
/*     */ 
/*     */         
/* 111 */         throw new NullPointerException(fmsg);
/*     */       } 
/*     */       
/* 114 */       QName myQName = new QName(ns, funcName);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 119 */       if (this.extensionInvocationDisabled) {
/* 120 */         String fmsg = XSLMessages.createXPATHMessage("ER_EXTENSION_FUNCTION_CANNOT_BE_INVOKED", new Object[] { myQName
/*     */               
/* 122 */               .toString() });
/* 123 */         throw new XPathFunctionException(fmsg);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 128 */       int arity = argVec.size();
/*     */ 
/*     */       
/* 131 */       XPathFunction xpathFunction = this.resolver.resolveFunction(myQName, arity);
/*     */ 
/*     */       
/* 134 */       ArrayList<NodeList> argList = new ArrayList(arity);
/* 135 */       for (int i = 0; i < arity; i++) {
/* 136 */         Object argument = argVec.elementAt(i);
/*     */ 
/*     */         
/* 139 */         if (argument instanceof XNodeSet) {
/* 140 */           argList.add(i, ((XNodeSet)argument).nodelist());
/* 141 */         } else if (argument instanceof XObject) {
/* 142 */           Object passedArgument = ((XObject)argument).object();
/* 143 */           argList.add(i, passedArgument);
/*     */         } else {
/* 145 */           argList.add(i, argument);
/*     */         } 
/*     */       } 
/*     */       
/* 149 */       return xpathFunction.evaluate(argList);
/* 150 */     } catch (XPathFunctionException xfe) {
/*     */ 
/*     */       
/* 153 */       throw new WrappedRuntimeException(xfe);
/* 154 */     } catch (Exception e) {
/* 155 */       throw new TransformerException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object extFunction(FuncExtFunction extFunction, Vector argVec) throws TransformerException {
/*     */     try {
/* 167 */       String namespace = extFunction.getNamespace();
/* 168 */       String functionName = extFunction.getFunctionName();
/* 169 */       int arity = extFunction.getArgCount();
/* 170 */       QName myQName = new QName(namespace, functionName);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 176 */       if (this.extensionInvocationDisabled) {
/* 177 */         String fmsg = XSLMessages.createXPATHMessage("ER_EXTENSION_FUNCTION_CANNOT_BE_INVOKED", new Object[] { myQName
/* 178 */               .toString() });
/* 179 */         throw new XPathFunctionException(fmsg);
/*     */       } 
/*     */ 
/*     */       
/* 183 */       XPathFunction xpathFunction = this.resolver.resolveFunction(myQName, arity);
/*     */       
/* 185 */       ArrayList<NodeList> argList = new ArrayList(arity);
/* 186 */       for (int i = 0; i < arity; i++) {
/* 187 */         Object argument = argVec.elementAt(i);
/*     */ 
/*     */         
/* 190 */         if (argument instanceof XNodeSet) {
/* 191 */           argList.add(i, ((XNodeSet)argument).nodelist());
/* 192 */         } else if (argument instanceof XObject) {
/* 193 */           Object passedArgument = ((XObject)argument).object();
/* 194 */           argList.add(i, passedArgument);
/*     */         } else {
/* 196 */           argList.add(i, argument);
/*     */         } 
/*     */       } 
/*     */       
/* 200 */       return xpathFunction.evaluate(argList);
/*     */     }
/* 202 */     catch (XPathFunctionException xfe) {
/*     */ 
/*     */       
/* 205 */       throw new WrappedRuntimeException(xfe);
/* 206 */     } catch (Exception e) {
/* 207 */       throw new TransformerException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/jaxp/JAXPExtensionsProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */