/*    */ package com.sun.org.apache.xml.internal.dtm;
/*    */ 
/*    */ import org.w3c.dom.DOMException;
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
/*    */ 
/*    */ public class DTMDOMException
/*    */   extends DOMException
/*    */ {
/*    */   static final long serialVersionUID = 1895654266613192414L;
/*    */   
/*    */   public DTMDOMException(short code, String message) {
/* 43 */     super(code, message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DTMDOMException(short code) {
/* 54 */     super(code, "");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/dtm/DTMDOMException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */