/*     */ package com.sun.xml.internal.ws.policy.sourcemodel;
/*     */ 
/*     */ import com.sun.xml.internal.txw2.TXW;
/*     */ import com.sun.xml.internal.txw2.TypedXmlWriter;
/*     */ import com.sun.xml.internal.txw2.output.StaxSerializer;
/*     */ import com.sun.xml.internal.txw2.output.XmlSerializer;
/*     */ import com.sun.xml.internal.ws.policy.PolicyConstants;
/*     */ import com.sun.xml.internal.ws.policy.PolicyException;
/*     */ import com.sun.xml.internal.ws.policy.privateutil.LocalizationMessages;
/*     */ import com.sun.xml.internal.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.internal.ws.policy.sourcemodel.wspolicy.NamespaceVersion;
/*     */ import com.sun.xml.internal.ws.policy.sourcemodel.wspolicy.XmlToken;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class XmlPolicyModelMarshaller
/*     */   extends PolicyModelMarshaller
/*     */ {
/*  47 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(XmlPolicyModelMarshaller.class);
/*     */   
/*     */   private final boolean marshallInvisible;
/*     */ 
/*     */   
/*     */   XmlPolicyModelMarshaller(boolean marshallInvisible) {
/*  53 */     this.marshallInvisible = marshallInvisible;
/*     */   }
/*     */   
/*     */   public void marshal(PolicySourceModel model, Object storage) throws PolicyException {
/*  57 */     if (storage instanceof StaxSerializer) {
/*  58 */       marshal(model, (StaxSerializer)storage);
/*  59 */     } else if (storage instanceof TypedXmlWriter) {
/*  60 */       marshal(model, (TypedXmlWriter)storage);
/*  61 */     } else if (storage instanceof XMLStreamWriter) {
/*  62 */       marshal(model, (XMLStreamWriter)storage);
/*     */     } else {
/*  64 */       throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0022_STORAGE_TYPE_NOT_SUPPORTED(storage.getClass().getName())));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void marshal(Collection<PolicySourceModel> models, Object storage) throws PolicyException {
/*  69 */     for (PolicySourceModel model : models) {
/*  70 */       marshal(model, storage);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void marshal(PolicySourceModel model, StaxSerializer writer) throws PolicyException {
/*  82 */     TypedXmlWriter policy = TXW.create(model.getNamespaceVersion().asQName(XmlToken.Policy), TypedXmlWriter.class, (XmlSerializer)writer);
/*     */     
/*  84 */     marshalDefaultPrefixes(model, policy);
/*  85 */     marshalPolicyAttributes(model, policy);
/*  86 */     marshal(model.getNamespaceVersion(), model.getRootNode(), policy);
/*  87 */     policy.commit();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void marshal(PolicySourceModel model, TypedXmlWriter writer) throws PolicyException {
/*  98 */     TypedXmlWriter policy = writer._element(model.getNamespaceVersion().asQName(XmlToken.Policy), TypedXmlWriter.class);
/*     */     
/* 100 */     marshalDefaultPrefixes(model, policy);
/* 101 */     marshalPolicyAttributes(model, policy);
/* 102 */     marshal(model.getNamespaceVersion(), model.getRootNode(), policy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void marshal(PolicySourceModel model, XMLStreamWriter writer) throws PolicyException {
/* 113 */     StaxSerializer serializer = new StaxSerializer(writer);
/* 114 */     TypedXmlWriter policy = TXW.create(model.getNamespaceVersion().asQName(XmlToken.Policy), TypedXmlWriter.class, (XmlSerializer)serializer);
/*     */     
/* 116 */     marshalDefaultPrefixes(model, policy);
/* 117 */     marshalPolicyAttributes(model, policy);
/* 118 */     marshal(model.getNamespaceVersion(), model.getRootNode(), policy);
/* 119 */     policy.commit();
/* 120 */     serializer.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void marshalPolicyAttributes(PolicySourceModel model, TypedXmlWriter writer) {
/* 130 */     String policyId = model.getPolicyId();
/* 131 */     if (policyId != null) {
/* 132 */       writer._attribute(PolicyConstants.WSU_ID, policyId);
/*     */     }
/*     */     
/* 135 */     String policyName = model.getPolicyName();
/* 136 */     if (policyName != null) {
/* 137 */       writer._attribute(model.getNamespaceVersion().asQName(XmlToken.Name), policyName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void marshal(NamespaceVersion nsVersion, ModelNode rootNode, TypedXmlWriter writer) {
/* 149 */     for (ModelNode node : rootNode) {
/* 150 */       AssertionData data = node.getNodeData();
/* 151 */       if (this.marshallInvisible || data == null || !data.isPrivateAttributeSet()) {
/* 152 */         TypedXmlWriter child = null;
/* 153 */         if (data == null) {
/* 154 */           child = writer._element(nsVersion.asQName(node.getType().getXmlToken()), TypedXmlWriter.class);
/*     */         } else {
/* 156 */           child = writer._element(data.getName(), TypedXmlWriter.class);
/* 157 */           String value = data.getValue();
/* 158 */           if (value != null) {
/* 159 */             child._pcdata(value);
/*     */           }
/* 161 */           if (data.isOptionalAttributeSet()) {
/* 162 */             child._attribute(nsVersion.asQName(XmlToken.Optional), Boolean.TRUE);
/*     */           }
/* 164 */           if (data.isIgnorableAttributeSet()) {
/* 165 */             child._attribute(nsVersion.asQName(XmlToken.Ignorable), Boolean.TRUE);
/*     */           }
/* 167 */           for (Map.Entry<QName, String> entry : data.getAttributesSet()) {
/* 168 */             child._attribute(entry.getKey(), entry.getValue());
/*     */           }
/*     */         } 
/* 171 */         marshal(nsVersion, node, child);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void marshalDefaultPrefixes(PolicySourceModel model, TypedXmlWriter writer) throws PolicyException {
/* 184 */     Map<String, String> nsMap = model.getNamespaceToPrefixMapping();
/* 185 */     if (!this.marshallInvisible && nsMap.containsKey("http://java.sun.com/xml/ns/wsit/policy")) {
/* 186 */       nsMap.remove("http://java.sun.com/xml/ns/wsit/policy");
/*     */     }
/* 188 */     for (Map.Entry<String, String> nsMappingEntry : nsMap.entrySet())
/* 189 */       writer._namespace(nsMappingEntry.getKey(), nsMappingEntry.getValue()); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/policy/sourcemodel/XmlPolicyModelMarshaller.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */