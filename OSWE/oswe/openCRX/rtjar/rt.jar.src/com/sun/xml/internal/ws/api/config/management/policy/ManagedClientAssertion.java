/*    */ package com.sun.xml.internal.ws.api.config.management.policy;
/*    */ 
/*    */ import com.sun.istack.internal.logging.Logger;
/*    */ import com.sun.xml.internal.ws.api.client.WSPortInfo;
/*    */ import com.sun.xml.internal.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.internal.ws.policy.PolicyMap;
/*    */ import com.sun.xml.internal.ws.policy.sourcemodel.AssertionData;
/*    */ import com.sun.xml.internal.ws.policy.spi.AssertionCreationException;
/*    */ import com.sun.xml.internal.ws.resources.ManagementMessages;
/*    */ import java.util.Collection;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.ws.WebServiceException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ManagedClientAssertion
/*    */   extends ManagementAssertion
/*    */ {
/* 48 */   public static final QName MANAGED_CLIENT_QNAME = new QName("http://java.sun.com/xml/ns/metro/management", "ManagedClient");
/*    */ 
/*    */   
/* 51 */   private static final Logger LOGGER = Logger.getLogger(ManagedClientAssertion.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ManagedClientAssertion getAssertion(WSPortInfo portInfo) throws WebServiceException {
/* 61 */     if (portInfo == null) {
/* 62 */       return null;
/*    */     }
/* 64 */     LOGGER.entering(new Object[] { portInfo });
/*    */ 
/*    */ 
/*    */     
/* 68 */     PolicyMap policyMap = portInfo.getPolicyMap();
/* 69 */     ManagedClientAssertion assertion = ManagementAssertion.<ManagedClientAssertion>getAssertion(MANAGED_CLIENT_QNAME, policyMap, portInfo
/* 70 */         .getServiceName(), portInfo.getPortName(), ManagedClientAssertion.class);
/* 71 */     LOGGER.exiting(assertion);
/* 72 */     return assertion;
/*    */   }
/*    */ 
/*    */   
/*    */   public ManagedClientAssertion(AssertionData data, Collection<PolicyAssertion> assertionParameters) throws AssertionCreationException {
/* 77 */     super(MANAGED_CLIENT_QNAME, data, assertionParameters);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isManagementEnabled() {
/* 86 */     String management = getAttributeValue(MANAGEMENT_ATTRIBUTE_QNAME);
/* 87 */     if (management != null && (
/* 88 */       management.trim().toLowerCase().equals("on") || Boolean.parseBoolean(management))) {
/* 89 */       LOGGER.warning(ManagementMessages.WSM_1006_CLIENT_MANAGEMENT_ENABLED());
/*    */     }
/*    */     
/* 92 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/config/management/policy/ManagedClientAssertion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */