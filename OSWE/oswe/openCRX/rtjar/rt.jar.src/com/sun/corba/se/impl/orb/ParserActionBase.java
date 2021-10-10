/*    */ package com.sun.corba.se.impl.orb;
/*    */ 
/*    */ import com.sun.corba.se.spi.orb.Operation;
/*    */ import java.util.Properties;
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
/*    */ public abstract class ParserActionBase
/*    */   implements ParserAction
/*    */ {
/*    */   private String propertyName;
/*    */   private boolean prefix;
/*    */   private Operation operation;
/*    */   private String fieldName;
/*    */   
/*    */   public int hashCode() {
/* 40 */     return this.propertyName.hashCode() ^ this.operation.hashCode() ^ this.fieldName
/* 41 */       .hashCode() ^ (this.prefix ? 0 : 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 46 */     if (paramObject == this) {
/* 47 */       return true;
/*    */     }
/* 49 */     if (!(paramObject instanceof ParserActionBase)) {
/* 50 */       return false;
/*    */     }
/* 52 */     ParserActionBase parserActionBase = (ParserActionBase)paramObject;
/*    */     
/* 54 */     return (this.propertyName.equals(parserActionBase.propertyName) && this.prefix == parserActionBase.prefix && this.operation
/*    */       
/* 56 */       .equals(parserActionBase.operation) && this.fieldName
/* 57 */       .equals(parserActionBase.fieldName));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ParserActionBase(String paramString1, boolean paramBoolean, Operation paramOperation, String paramString2) {
/* 63 */     this.propertyName = paramString1;
/* 64 */     this.prefix = paramBoolean;
/* 65 */     this.operation = paramOperation;
/* 66 */     this.fieldName = paramString2;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPropertyName() {
/* 71 */     return this.propertyName;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isPrefix() {
/* 76 */     return this.prefix;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getFieldName() {
/* 81 */     return this.fieldName;
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract Object apply(Properties paramProperties);
/*    */   
/*    */   protected Operation getOperation() {
/* 88 */     return this.operation;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orb/ParserActionBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */