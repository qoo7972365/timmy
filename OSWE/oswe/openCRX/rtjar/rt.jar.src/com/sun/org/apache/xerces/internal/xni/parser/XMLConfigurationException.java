/*    */ package com.sun.org.apache.xerces.internal.xni.parser;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.util.Status;
/*    */ import com.sun.org.apache.xerces.internal.xni.XNIException;
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
/*    */ public class XMLConfigurationException
/*    */   extends XNIException
/*    */ {
/*    */   static final long serialVersionUID = -5437427404547669188L;
/*    */   protected Status fType;
/*    */   protected String fIdentifier;
/*    */   
/*    */   public XMLConfigurationException(Status type, String identifier) {
/* 63 */     super(identifier);
/* 64 */     this.fType = type;
/* 65 */     this.fIdentifier = identifier;
/*    */   }
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
/*    */   public XMLConfigurationException(Status type, String identifier, String message) {
/* 78 */     super(message);
/* 79 */     this.fType = type;
/* 80 */     this.fIdentifier = identifier;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Status getType() {
/* 91 */     return this.fType;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIdentifier() {
/* 96 */     return this.fIdentifier;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/xni/parser/XMLConfigurationException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */