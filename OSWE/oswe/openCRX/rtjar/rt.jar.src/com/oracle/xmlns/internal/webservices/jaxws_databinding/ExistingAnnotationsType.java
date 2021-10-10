/*    */ package com.oracle.xmlns.internal.webservices.jaxws_databinding;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
/*    */ import javax.xml.bind.annotation.XmlEnumValue;
/*    */ import javax.xml.bind.annotation.XmlType;
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
/*    */ @XmlType(name = "existing-annotations-type")
/*    */ @XmlEnum
/*    */ public enum ExistingAnnotationsType
/*    */ {
/* 54 */   MERGE("merge"),
/*    */   
/* 56 */   IGNORE("ignore");
/*    */   
/*    */   private final String value;
/*    */   
/*    */   ExistingAnnotationsType(String v) {
/* 61 */     this.value = v;
/*    */   }
/*    */   
/*    */   public String value() {
/* 65 */     return this.value;
/*    */   }
/*    */   
/*    */   public static ExistingAnnotationsType fromValue(String v) {
/* 69 */     for (ExistingAnnotationsType c : values()) {
/* 70 */       if (c.value.equals(v)) {
/* 71 */         return c;
/*    */       }
/*    */     } 
/* 74 */     throw new IllegalArgumentException(v);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/oracle/xmlns/internal/webservices/jaxws_databinding/ExistingAnnotationsType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */