/*    */ package com.sun.org.apache.xerces.internal.impl.dv;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.impl.dv.dtd.DTDDVFactoryImpl;
/*    */ import com.sun.org.apache.xerces.internal.impl.dv.dtd.XML11DTDDVFactoryImpl;
/*    */ import com.sun.org.apache.xerces.internal.utils.ObjectFactory;
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
/*    */ public abstract class DTDDVFactory
/*    */ {
/*    */   private static final String DEFAULT_FACTORY_CLASS = "com.sun.org.apache.xerces.internal.impl.dv.dtd.DTDDVFactoryImpl";
/*    */   private static final String XML11_DATATYPE_VALIDATOR_FACTORY = "com.sun.org.apache.xerces.internal.impl.dv.dtd.XML11DTDDVFactoryImpl";
/*    */   
/*    */   public static final DTDDVFactory getInstance() throws DVFactoryException {
/* 55 */     return getInstance("com.sun.org.apache.xerces.internal.impl.dv.dtd.DTDDVFactoryImpl");
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
/*    */   public static final DTDDVFactory getInstance(String factoryClass) throws DVFactoryException {
/*    */     try {
/* 68 */       if ("com.sun.org.apache.xerces.internal.impl.dv.dtd.DTDDVFactoryImpl".equals(factoryClass))
/* 69 */         return new DTDDVFactoryImpl(); 
/* 70 */       if ("com.sun.org.apache.xerces.internal.impl.dv.dtd.XML11DTDDVFactoryImpl".equals(factoryClass)) {
/* 71 */         return new XML11DTDDVFactoryImpl();
/*    */       }
/*    */       
/* 74 */       return 
/* 75 */         (DTDDVFactory)ObjectFactory.newInstance(factoryClass, true);
/*    */     
/*    */     }
/* 78 */     catch (ClassCastException e) {
/* 79 */       throw new DVFactoryException("DTD factory class " + factoryClass + " does not extend from DTDDVFactory.");
/*    */     } 
/*    */   }
/*    */   
/*    */   public abstract DatatypeValidator getBuiltInDV(String paramString);
/*    */   
/*    */   public abstract Map<String, DatatypeValidator> getBuiltInTypes();
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dv/DTDDVFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */