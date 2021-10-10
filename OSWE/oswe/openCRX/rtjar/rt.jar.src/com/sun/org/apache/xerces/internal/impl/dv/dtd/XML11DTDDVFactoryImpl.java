/*    */ package com.sun.org.apache.xerces.internal.impl.dv.dtd;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.impl.dv.DatatypeValidator;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ public class XML11DTDDVFactoryImpl
/*    */   extends DTDDVFactoryImpl
/*    */ {
/*    */   static Map<String, DatatypeValidator> XML11BUILTINTYPES;
/*    */   
/*    */   static {
/* 40 */     Map<String, DatatypeValidator> xml11BuiltInTypes = new HashMap<>();
/* 41 */     xml11BuiltInTypes.put("XML11ID", new XML11IDDatatypeValidator());
/* 42 */     DatatypeValidator dvTemp = new XML11IDREFDatatypeValidator();
/* 43 */     xml11BuiltInTypes.put("XML11IDREF", dvTemp);
/* 44 */     xml11BuiltInTypes.put("XML11IDREFS", new ListDatatypeValidator(dvTemp));
/* 45 */     dvTemp = new XML11NMTOKENDatatypeValidator();
/* 46 */     xml11BuiltInTypes.put("XML11NMTOKEN", dvTemp);
/* 47 */     xml11BuiltInTypes.put("XML11NMTOKENS", new ListDatatypeValidator(dvTemp));
/* 48 */     XML11BUILTINTYPES = Collections.unmodifiableMap(xml11BuiltInTypes);
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
/*    */   public DatatypeValidator getBuiltInDV(String name) {
/* 61 */     if (XML11BUILTINTYPES.get(name) != null) {
/* 62 */       return XML11BUILTINTYPES.get(name);
/*    */     }
/* 64 */     return fBuiltInTypes.get(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Map<String, DatatypeValidator> getBuiltInTypes() {
/* 75 */     HashMap<String, DatatypeValidator> toReturn = new HashMap<>(fBuiltInTypes);
/* 76 */     toReturn.putAll(XML11BUILTINTYPES);
/* 77 */     return toReturn;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dv/dtd/XML11DTDDVFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */