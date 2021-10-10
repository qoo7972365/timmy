/*    */ package com.sun.org.apache.xerces.internal.impl.dv.dtd;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.impl.dv.DTDDVFactory;
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
/*    */ public class DTDDVFactoryImpl
/*    */   extends DTDDVFactory
/*    */ {
/*    */   static final Map<String, DatatypeValidator> fBuiltInTypes;
/*    */   
/*    */   static {
/* 41 */     Map<String, DatatypeValidator> builtInTypes = new HashMap<>();
/*    */ 
/*    */     
/* 44 */     builtInTypes.put("string", new StringDatatypeValidator());
/* 45 */     builtInTypes.put("ID", new IDDatatypeValidator());
/* 46 */     DatatypeValidator dvTemp = new IDREFDatatypeValidator();
/* 47 */     builtInTypes.put("IDREF", dvTemp);
/* 48 */     builtInTypes.put("IDREFS", new ListDatatypeValidator(dvTemp));
/* 49 */     dvTemp = new ENTITYDatatypeValidator();
/* 50 */     builtInTypes.put("ENTITY", new ENTITYDatatypeValidator());
/* 51 */     builtInTypes.put("ENTITIES", new ListDatatypeValidator(dvTemp));
/* 52 */     builtInTypes.put("NOTATION", new NOTATIONDatatypeValidator());
/* 53 */     dvTemp = new NMTOKENDatatypeValidator();
/* 54 */     builtInTypes.put("NMTOKEN", dvTemp);
/* 55 */     builtInTypes.put("NMTOKENS", new ListDatatypeValidator(dvTemp));
/*    */     
/* 57 */     fBuiltInTypes = Collections.unmodifiableMap(builtInTypes);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DatatypeValidator getBuiltInDV(String name) {
/* 68 */     return fBuiltInTypes.get(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Map<String, DatatypeValidator> getBuiltInTypes() {
/* 78 */     return new HashMap<>(fBuiltInTypes);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dv/dtd/DTDDVFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */