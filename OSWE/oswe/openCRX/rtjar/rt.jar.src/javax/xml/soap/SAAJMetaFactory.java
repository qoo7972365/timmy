/*    */ package javax.xml.soap;
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
/*    */ public abstract class SAAJMetaFactory
/*    */ {
/*    */   private static final String META_FACTORY_CLASS_PROPERTY = "javax.xml.soap.MetaFactory";
/*    */   static final String DEFAULT_META_FACTORY_CLASS = "com.sun.xml.internal.messaging.saaj.soap.SAAJMetaFactoryImpl";
/*    */   
/*    */   static SAAJMetaFactory getInstance() throws SOAPException {
/*    */     try {
/* 74 */       SAAJMetaFactory instance = (SAAJMetaFactory)FactoryFinder.find("javax.xml.soap.MetaFactory", "com.sun.xml.internal.messaging.saaj.soap.SAAJMetaFactoryImpl");
/*    */ 
/*    */       
/* 77 */       return instance;
/* 78 */     } catch (Exception e) {
/* 79 */       throw new SOAPException("Unable to create SAAJ meta-factory" + e
/* 80 */           .getMessage());
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract MessageFactory newMessageFactory(String paramString) throws SOAPException;
/*    */   
/*    */   protected abstract SOAPFactory newSOAPFactory(String paramString) throws SOAPException;
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/soap/SAAJMetaFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */