/*    */ package com.sun.xml.internal.bind.v2.runtime.reflect.opt;
/*    */ 
/*    */ import com.sun.xml.internal.bind.DatatypeConverterImpl;
/*    */ import com.sun.xml.internal.bind.api.AccessorException;
/*    */ import com.sun.xml.internal.bind.v2.runtime.reflect.DefaultTransducedAccessor;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class TransducedAccessor_field_Boolean
/*    */   extends DefaultTransducedAccessor
/*    */ {
/*    */   public String print(Object o) {
/* 44 */     return DatatypeConverterImpl._printBoolean(((Bean)o).f_boolean);
/*    */   }
/*    */   
/*    */   public void parse(Object o, CharSequence lexical) {
/* 48 */     Boolean b = DatatypeConverterImpl._parseBoolean(lexical);
/*    */     
/* 50 */     if (b != null)
/* 51 */       ((Bean)o).f_boolean = b.booleanValue(); 
/*    */   }
/*    */   
/*    */   public boolean hasValue(Object o) {
/* 55 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/reflect/opt/TransducedAccessor_field_Boolean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */