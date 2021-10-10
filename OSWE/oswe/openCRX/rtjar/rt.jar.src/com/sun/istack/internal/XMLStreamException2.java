/*    */ package com.sun.istack.internal;
/*    */ 
/*    */ import javax.xml.stream.Location;
/*    */ import javax.xml.stream.XMLStreamException;
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
/*    */ public class XMLStreamException2
/*    */   extends XMLStreamException
/*    */ {
/*    */   public XMLStreamException2(String msg) {
/* 38 */     super(msg);
/*    */   }
/*    */   
/*    */   public XMLStreamException2(Throwable th) {
/* 42 */     super(th);
/*    */   }
/*    */   
/*    */   public XMLStreamException2(String msg, Throwable th) {
/* 46 */     super(msg, th);
/*    */   }
/*    */   
/*    */   public XMLStreamException2(String msg, Location location) {
/* 50 */     super(msg, location);
/*    */   }
/*    */   
/*    */   public XMLStreamException2(String msg, Location location, Throwable th) {
/* 54 */     super(msg, location, th);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Throwable getCause() {
/* 61 */     return getNestedException();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/istack/internal/XMLStreamException2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */