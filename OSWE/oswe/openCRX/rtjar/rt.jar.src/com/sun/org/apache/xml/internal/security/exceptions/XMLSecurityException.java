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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLSecurityException
/*     */   extends Exception
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected String msgID;
/*     */   
/*     */   public XMLSecurityException() {
/*  79 */     super("Missing message string");
/*     */     
/*  81 */     this.msgID = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSecurityException(String paramString) {
/*  90 */     super(I18n.getExceptionMessage(paramString));
/*     */     
/*  92 */     this.msgID = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSecurityException(String paramString, Object[] paramArrayOfObject) {
/* 103 */     super(MessageFormat.format(I18n.getExceptionMessage(paramString), paramArrayOfObject));
/*     */     
/* 105 */     this.msgID = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSecurityException(Exception paramException) {
/* 115 */     super("Missing message ID to locate message string in resource bundle \"com/sun/org/apache/xml/internal/security/resource/xmlsecurity\". Original Exception was a " + paramException
/*     */ 
/*     */         
/* 118 */         .getClass().getName() + " and message " + paramException
/* 119 */         .getMessage(), paramException);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSecurityException(String paramString, Exception paramException) {
/* 129 */     super(I18n.getExceptionMessage(paramString, paramException), paramException);
/*     */     
/* 131 */     this.msgID = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSecurityException(String paramString, Object[] paramArrayOfObject, Exception paramException) {
/* 142 */     super(MessageFormat.format(I18n.getExceptionMessage(paramString), paramArrayOfObject), paramException);
/*     */     
/* 144 */     this.msgID = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMsgID() {
/* 153 */     if (this.msgID == null) {
/* 154 */       return "Missing message ID";
/*     */     }
/* 156 */     return this.msgID;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 161 */     String str1 = getClass().getName();
/* 162 */     String str2 = getLocalizedMessage();
/*     */     
/* 164 */     if (str2 != null) {
/* 165 */       str2 = str1 + ": " + str2;
/*     */     } else {
/* 167 */       str2 = str1;
/*     */     } 
/*     */     
/* 170 */     if (getCause() != null) {
/* 171 */       str2 = str2 + "\nOriginal Exception was " + getCause().toString();
/*     */     }
/*     */     
/* 174 */     return str2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printStackTrace() {
/* 182 */     synchronized (System.err) {
/* 183 */       super.printStackTrace(System.err);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printStackTrace(PrintWriter paramPrintWriter) {
/* 193 */     super.printStackTrace(paramPrintWriter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printStackTrace(PrintStream paramPrintStream) {
/* 202 */     super.printStackTrace(paramPrintStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Exception getOriginalException() {
/* 211 */     if (getCause() instanceof Exception) {
/* 212 */       return (Exception)getCause();
/*     */     }
/* 214 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/exceptions/XMLSecurityException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */