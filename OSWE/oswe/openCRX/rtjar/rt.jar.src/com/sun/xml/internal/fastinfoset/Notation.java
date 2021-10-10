/*    */ package com.sun.xml.internal.fastinfoset;
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
/*    */ public class Notation
/*    */ {
/*    */   public final String name;
/*    */   public final String systemIdentifier;
/*    */   public final String publicIdentifier;
/*    */   
/*    */   public Notation(String _name, String _systemIdentifier, String _publicIdentifier) {
/* 37 */     this.name = _name;
/* 38 */     this.systemIdentifier = _systemIdentifier;
/* 39 */     this.publicIdentifier = _publicIdentifier;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/Notation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */