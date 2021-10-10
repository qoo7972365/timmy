/*    */ package com.sun.xml.internal.messaging.saaj.soap;
/*    */ 
/*    */ import com.sun.xml.internal.messaging.saaj.SOAPExceptionImpl;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import java.io.PrintWriter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SOAPIOException
/*    */   extends IOException
/*    */ {
/*    */   SOAPExceptionImpl soapException;
/*    */   
/*    */   public SOAPIOException() {
/* 45 */     this.soapException = new SOAPExceptionImpl();
/* 46 */     this.soapException.fillInStackTrace();
/*    */   }
/*    */ 
/*    */   
/*    */   public SOAPIOException(String s) {
/* 51 */     this.soapException = new SOAPExceptionImpl(s);
/* 52 */     this.soapException.fillInStackTrace();
/*    */   }
/*    */ 
/*    */   
/*    */   public SOAPIOException(String reason, Throwable cause) {
/* 57 */     this.soapException = new SOAPExceptionImpl(reason, cause);
/* 58 */     this.soapException.fillInStackTrace();
/*    */   }
/*    */   
/*    */   public SOAPIOException(Throwable cause) {
/* 62 */     super(cause.toString());
/* 63 */     this.soapException = new SOAPExceptionImpl(cause);
/* 64 */     this.soapException.fillInStackTrace();
/*    */   }
/*    */   
/*    */   public Throwable fillInStackTrace() {
/* 68 */     if (this.soapException != null) {
/* 69 */       this.soapException.fillInStackTrace();
/*    */     }
/* 71 */     return this;
/*    */   }
/*    */   
/*    */   public String getLocalizedMessage() {
/* 75 */     return this.soapException.getLocalizedMessage();
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 79 */     return this.soapException.getMessage();
/*    */   }
/*    */   
/*    */   public void printStackTrace() {
/* 83 */     this.soapException.printStackTrace();
/*    */   }
/*    */   
/*    */   public void printStackTrace(PrintStream s) {
/* 87 */     this.soapException.printStackTrace(s);
/*    */   }
/*    */   
/*    */   public void printStackTrace(PrintWriter s) {
/* 91 */     this.soapException.printStackTrace(s);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 95 */     return this.soapException.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/soap/SOAPIOException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */