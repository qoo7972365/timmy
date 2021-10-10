/*    */ package com.sun.corba.se.impl.orb;
/*    */ 
/*    */ import com.sun.corba.se.spi.orb.Operation;
/*    */ import com.sun.corba.se.spi.orb.ParserData;
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
/*    */ public abstract class ParserDataBase
/*    */   implements ParserData
/*    */ {
/*    */   private String propertyName;
/*    */   private Operation operation;
/*    */   private String fieldName;
/*    */   private Object defaultValue;
/*    */   private Object testValue;
/*    */   
/*    */   protected ParserDataBase(String paramString1, Operation paramOperation, String paramString2, Object paramObject1, Object paramObject2) {
/* 42 */     this.propertyName = paramString1;
/* 43 */     this.operation = paramOperation;
/* 44 */     this.fieldName = paramString2;
/* 45 */     this.defaultValue = paramObject1;
/* 46 */     this.testValue = paramObject2;
/*    */   }
/*    */   
/* 49 */   public String getPropertyName() { return this.propertyName; }
/* 50 */   public Operation getOperation() { return this.operation; }
/* 51 */   public String getFieldName() { return this.fieldName; }
/* 52 */   public Object getDefaultValue() { return this.defaultValue; } public Object getTestValue() {
/* 53 */     return this.testValue;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orb/ParserDataBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */