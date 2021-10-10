/*    */ package com.sun.xml.internal.bind.v2.model.core;
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
/*    */ public enum PropertyKind
/*    */ {
/* 41 */   VALUE(true, false, 2147483647),
/* 42 */   ATTRIBUTE(false, false, 2147483647),
/* 43 */   ELEMENT(true, true, 0),
/* 44 */   REFERENCE(false, true, 1),
/* 45 */   MAP(false, true, 2);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final boolean canHaveXmlMimeType;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final boolean isOrdered;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final int propertyIndex;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   PropertyKind(boolean canHaveExpectedContentType, boolean isOrdered, int propertyIndex) {
/* 66 */     this.canHaveXmlMimeType = canHaveExpectedContentType;
/* 67 */     this.isOrdered = isOrdered;
/* 68 */     this.propertyIndex = propertyIndex;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/core/PropertyKind.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */