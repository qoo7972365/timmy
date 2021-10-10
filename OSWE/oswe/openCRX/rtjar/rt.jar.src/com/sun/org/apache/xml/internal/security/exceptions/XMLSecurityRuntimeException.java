/*     */ package com.sun.org.apache.xml.internal.security.exceptions;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.utils.I18n;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.text.MessageFormat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLSecurityRuntimeException
/*     */   extends RuntimeException
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected String msgID;
/*     */   
/*     */   public XMLSecurityRuntimeException() {
/*  76 */     super("Missing message string");
/*     */     
/*  78 */     this.msgID = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSecurityRuntimeException(String paramString) {
/*  87 */     super(I18n.getExceptionMessage(paramString));
/*     */     
/*  89 */     this.msgID = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSecurityRuntimeException(String paramString, Object[] paramArrayOfObject) {
/*  99 */     super(MessageFormat.format(I18n.getExceptionMessage(paramString), paramArrayOfObject));
/*     */     
/* 101 */     this.msgID = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSecurityRuntimeException(Exception paramException) {
/* 110 */     super("Missing message ID to locate message string in resource bundle \"com/sun/org/apache/xml/internal/security/resource/xmlsecurity\". Original Exception was a " + paramException
/*     */ 
/*     */         
/* 113 */         .getClass().getName() + " and message " + paramException
/* 114 */         .getMessage(), paramException);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSecurityRuntimeException(String paramString, Exception paramException) {
/* 124 */     super(I18n.getExceptionMessage(paramString, paramException), paramException);
/*     */     
/* 126 */     this.msgID = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSecurityRuntimeException(String paramString, Object[] paramArrayOfObject, Exception paramException) {
/* 137 */     super(MessageFormat.format(I18n.getExceptionMessage(paramString), paramArrayOfObject));
/*     */     
/* 139 */     this.msgID = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMsgID() {
/* 148 */     if (this.msgID == null) {
/* 149 */       return "Missing message ID";
/*     */     }
/* 151 */     return this.msgID;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 156 */     String str1 = getClass().getName();
/* 157 */     String str2 = getLocalizedMessage();
/*     */     
/* 159 */     if (str2 != null) {
/* 160 */       str2 = str1 + ": " + str2;
/*     */     } else {
/* 162 */       str2 = str1;
/*     */     } 
/*     */     
/* 165 */     if (getCause() != null) {
/* 166 */       str2 = str2 + "\nOriginal Exception was " + getCause().toString();
/*     */     }
/*     */     
/* 169 */     return str2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printStackTrace() {
/* 177 */     synchronized (System.err) {
/* 178 */       super.printStackTrace(System.err);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printStackTrace(PrintWriter paramPrintWriter) {
/* 188 */     super.printStackTrace(paramPrintWriter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printStackTrace(PrintStream paramPrintStream) {
/* 197 */     super.printStackTrace(paramPrintStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Exception getOriginalException() {
/* 206 */     if (getCause() instanceof Exception) {
/* 207 */       return (Exception)getCause();
/*     */     }
/* 209 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/exceptions/XMLSecurityRuntimeException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */