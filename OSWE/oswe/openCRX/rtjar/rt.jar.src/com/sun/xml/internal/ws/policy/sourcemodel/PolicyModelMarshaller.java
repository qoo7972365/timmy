/*    */ package com.sun.xml.internal.ws.policy.sourcemodel;
/*    */ 
/*    */ import com.sun.xml.internal.ws.policy.PolicyException;
/*    */ import java.util.Collection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PolicyModelMarshaller
/*    */ {
/* 38 */   private static final PolicyModelMarshaller defaultXmlMarshaller = new XmlPolicyModelMarshaller(false);
/* 39 */   private static final PolicyModelMarshaller invisibleAssertionXmlMarshaller = new XmlPolicyModelMarshaller(true);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract void marshal(PolicySourceModel paramPolicySourceModel, Object paramObject) throws PolicyException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract void marshal(Collection<PolicySourceModel> paramCollection, Object paramObject) throws PolicyException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static PolicyModelMarshaller getXmlMarshaller(boolean marshallInvisible) {
/* 78 */     return marshallInvisible ? invisibleAssertionXmlMarshaller : defaultXmlMarshaller;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/policy/sourcemodel/PolicyModelMarshaller.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */