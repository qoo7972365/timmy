/*    */ package com.sun.xml.internal.ws.api.policy;
/*    */ 
/*    */ import com.sun.xml.internal.ws.config.management.policy.ManagementAssertionCreator;
/*    */ import com.sun.xml.internal.ws.policy.PolicyException;
/*    */ import com.sun.xml.internal.ws.policy.privateutil.PolicyLogger;
/*    */ import com.sun.xml.internal.ws.policy.sourcemodel.PolicyModelTranslator;
/*    */ import com.sun.xml.internal.ws.policy.spi.PolicyAssertionCreator;
/*    */ import com.sun.xml.internal.ws.resources.ManagementMessages;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModelTranslator
/*    */   extends PolicyModelTranslator
/*    */ {
/* 46 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(ModelTranslator.class);
/*    */   
/* 48 */   private static final PolicyAssertionCreator[] JAXWS_ASSERTION_CREATORS = new PolicyAssertionCreator[] { (PolicyAssertionCreator)new ManagementAssertionCreator() };
/*    */   
/*    */   private static final ModelTranslator translator;
/*    */   
/*    */   private static final PolicyException creationException;
/*    */ 
/*    */   
/*    */   static {
/* 56 */     ModelTranslator tempTranslator = null;
/* 57 */     PolicyException tempException = null;
/*    */     try {
/* 59 */       tempTranslator = new ModelTranslator();
/* 60 */     } catch (PolicyException e) {
/* 61 */       tempException = e;
/* 62 */       LOGGER.warning(ManagementMessages.WSM_1007_FAILED_MODEL_TRANSLATOR_INSTANTIATION(), (Throwable)e);
/*    */     } finally {
/* 64 */       translator = tempTranslator;
/* 65 */       creationException = tempException;
/*    */     } 
/*    */   }
/*    */   
/*    */   private ModelTranslator() throws PolicyException {
/* 70 */     super(Arrays.asList(JAXWS_ASSERTION_CREATORS));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ModelTranslator getTranslator() throws PolicyException {
/* 80 */     if (creationException != null) {
/* 81 */       throw (PolicyException)LOGGER.logSevereException(creationException);
/*    */     }
/*    */     
/* 84 */     return translator;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/policy/ModelTranslator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */