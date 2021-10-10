/*     */ package com.sun.org.apache.xpath.internal.compiler;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.utils.ConfigurationError;
/*     */ import com.sun.org.apache.xalan.internal.utils.ObjectFactory;
/*     */ import com.sun.org.apache.xpath.internal.functions.Function;
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
/*     */ public class FuncLoader
/*     */ {
/*     */   private int m_funcID;
/*     */   private String m_funcName;
/*     */   
/*     */   public String getName() {
/*  56 */     return this.m_funcName;
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
/*     */   public FuncLoader(String funcName, int funcID) {
/*  74 */     this.m_funcID = funcID;
/*  75 */     this.m_funcName = funcName;
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
/*     */   Function getFunction() throws TransformerException {
/*     */     try {
/*  90 */       String className = this.m_funcName;
/*  91 */       if (className.indexOf(".") < 0) {
/*  92 */         className = "com.sun.org.apache.xpath.internal.functions." + className;
/*     */       }
/*     */       
/*  95 */       String subString = className.substring(0, className.lastIndexOf('.'));
/*  96 */       if (!subString.equals("com.sun.org.apache.xalan.internal.templates") && 
/*  97 */         !subString.equals("com.sun.org.apache.xpath.internal.functions")) {
/*  98 */         throw new TransformerException("Application can't install his own xpath function.");
/*     */       }
/*     */       
/* 101 */       return (Function)ObjectFactory.newInstance(className, true);
/*     */     
/*     */     }
/* 104 */     catch (ConfigurationError e) {
/*     */       
/* 106 */       throw new TransformerException(e.getException());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/compiler/FuncLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */