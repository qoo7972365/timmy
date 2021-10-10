/*    */ package com.sun.org.apache.xml.internal.utils;
/*    */ 
/*    */ import org.xml.sax.SAXException;
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
/*    */ public class StopParseException
/*    */   extends SAXException
/*    */ {
/*    */   static final long serialVersionUID = 210102479218258961L;
/*    */   
/*    */   StopParseException() {
/* 40 */     super("Stylesheet PIs found, stop the parse");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/utils/StopParseException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */