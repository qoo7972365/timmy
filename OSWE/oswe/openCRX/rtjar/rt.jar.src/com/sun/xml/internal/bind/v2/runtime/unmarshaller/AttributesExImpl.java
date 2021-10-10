/*    */ package com.sun.xml.internal.bind.v2.runtime.unmarshaller;
/*    */ 
/*    */ import com.sun.xml.internal.bind.util.AttributesImpl;
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
/*    */ public final class AttributesExImpl
/*    */   extends AttributesImpl
/*    */   implements AttributesEx
/*    */ {
/*    */   public CharSequence getData(int idx) {
/* 39 */     return getValue(idx);
/*    */   }
/*    */   
/*    */   public CharSequence getData(String nsUri, String localName) {
/* 43 */     return getValue(nsUri, localName);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/unmarshaller/AttributesExImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */