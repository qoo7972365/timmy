/*    */ package com.sun.org.apache.xerces.internal.impl.dv.xs;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.impl.dv.XSSimpleType;
/*    */ import com.sun.org.apache.xerces.internal.util.SymbolHash;
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
/*    */ public class SchemaDVFactoryImpl
/*    */   extends BaseSchemaDVFactory
/*    */ {
/* 39 */   static final SymbolHash fBuiltInTypes = new SymbolHash();
/*    */   
/*    */   static {
/* 42 */     createBuiltInTypes();
/*    */   }
/*    */ 
/*    */   
/*    */   static void createBuiltInTypes() {
/* 47 */     createBuiltInTypes(fBuiltInTypes, XSSimpleTypeDecl.fAnySimpleType);
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
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XSSimpleType getBuiltInType(String name) {
/* 64 */     return (XSSimpleType)fBuiltInTypes.get(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SymbolHash getBuiltInTypes() {
/* 74 */     return fBuiltInTypes.makeClone();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dv/xs/SchemaDVFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */