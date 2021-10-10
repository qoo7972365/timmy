/*    */ package com.sun.xml.internal.ws.api.policy;
/*    */ 
/*    */ import com.sun.xml.internal.ws.addressing.policy.AddressingPrefixMapper;
/*    */ import com.sun.xml.internal.ws.config.management.policy.ManagementPrefixMapper;
/*    */ import com.sun.xml.internal.ws.encoding.policy.EncodingPrefixMapper;
/*    */ import com.sun.xml.internal.ws.policy.sourcemodel.PolicySourceModel;
/*    */ import com.sun.xml.internal.ws.policy.sourcemodel.wspolicy.NamespaceVersion;
/*    */ import com.sun.xml.internal.ws.policy.spi.PrefixMapper;
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
/*    */ public class SourceModel
/*    */   extends PolicySourceModel
/*    */ {
/* 46 */   private static final PrefixMapper[] JAXWS_PREFIX_MAPPERS = new PrefixMapper[] { (PrefixMapper)new AddressingPrefixMapper(), (PrefixMapper)new EncodingPrefixMapper(), (PrefixMapper)new ManagementPrefixMapper() };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private SourceModel(NamespaceVersion nsVersion) {
/* 62 */     this(nsVersion, null, null);
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
/*    */   private SourceModel(NamespaceVersion nsVersion, String policyId, String policyName) {
/* 74 */     super(nsVersion, policyId, policyName, Arrays.asList(JAXWS_PREFIX_MAPPERS));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static PolicySourceModel createSourceModel(NamespaceVersion nsVersion) {
/* 84 */     return new SourceModel(nsVersion);
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
/*    */   public static PolicySourceModel createSourceModel(NamespaceVersion nsVersion, String policyId, String policyName) {
/* 97 */     return new SourceModel(nsVersion, policyId, policyName);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/policy/SourceModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */