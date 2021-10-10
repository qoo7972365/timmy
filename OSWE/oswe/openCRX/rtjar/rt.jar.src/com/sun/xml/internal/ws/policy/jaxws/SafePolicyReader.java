/*     */ package com.sun.xml.internal.ws.policy.jaxws;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.policy.ModelUnmarshaller;
/*     */ import com.sun.xml.internal.ws.policy.PolicyException;
/*     */ import com.sun.xml.internal.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.internal.ws.policy.sourcemodel.PolicySourceModel;
/*     */ import com.sun.xml.internal.ws.policy.sourcemodel.wspolicy.NamespaceVersion;
/*     */ import com.sun.xml.internal.ws.policy.sourcemodel.wspolicy.XmlToken;
/*     */ import com.sun.xml.internal.ws.resources.PolicyMessages;
/*     */ import java.io.StringReader;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SafePolicyReader
/*     */ {
/*  52 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(SafePolicyReader.class);
/*     */ 
/*     */   
/*  55 */   private final Set<String> urlsRead = new HashSet<>();
/*     */   
/*  57 */   private final Set<String> qualifiedPolicyUris = new HashSet<>();
/*     */ 
/*     */   
/*     */   public final class PolicyRecord
/*     */   {
/*     */     PolicyRecord next;
/*     */     
/*     */     PolicySourceModel policyModel;
/*     */     
/*     */     Set<String> unresolvedURIs;
/*     */     
/*     */     private String uri;
/*     */     
/*     */     PolicyRecord insert(PolicyRecord insertedRec) {
/*  71 */       if (null == insertedRec.unresolvedURIs || insertedRec.unresolvedURIs.isEmpty()) {
/*  72 */         insertedRec.next = this;
/*  73 */         return insertedRec;
/*     */       } 
/*  75 */       PolicyRecord head = this;
/*  76 */       PolicyRecord oneBeforeCurrent = null;
/*     */       PolicyRecord current;
/*  78 */       for (current = head; null != current.next; ) {
/*  79 */         if (null != current.unresolvedURIs && current.unresolvedURIs.contains(insertedRec.uri)) {
/*  80 */           if (null == oneBeforeCurrent) {
/*  81 */             insertedRec.next = current;
/*  82 */             return insertedRec;
/*     */           } 
/*  84 */           oneBeforeCurrent.next = insertedRec;
/*  85 */           insertedRec.next = current;
/*  86 */           return head;
/*     */         } 
/*     */         
/*  89 */         if (insertedRec.unresolvedURIs.remove(current.uri) && insertedRec.unresolvedURIs.isEmpty()) {
/*  90 */           insertedRec.next = current.next;
/*  91 */           current.next = insertedRec;
/*  92 */           return head;
/*     */         } 
/*  94 */         oneBeforeCurrent = current;
/*  95 */         current = current.next;
/*     */       } 
/*  97 */       insertedRec.next = null;
/*  98 */       current.next = insertedRec;
/*  99 */       return head;
/*     */     }
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
/*     */     public void setUri(String uri, String id) throws PolicyException {
/* 112 */       if (SafePolicyReader.this.qualifiedPolicyUris.contains(uri)) {
/* 113 */         throw (PolicyException)SafePolicyReader.LOGGER.logSevereException(new PolicyException(PolicyMessages.WSP_1020_DUPLICATE_ID(id)));
/*     */       }
/* 115 */       this.uri = uri;
/* 116 */       SafePolicyReader.this.qualifiedPolicyUris.add(uri);
/*     */     }
/*     */     
/*     */     public String getUri() {
/* 120 */       return this.uri;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 125 */       String result = this.uri;
/* 126 */       if (null != this.next) {
/* 127 */         result = result + "->" + this.next.toString();
/*     */       }
/* 129 */       return result;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolicyRecord readPolicyElement(XMLStreamReader reader, String baseUrl) {
/* 146 */     if (null == reader || !reader.isStartElement()) {
/* 147 */       return null;
/*     */     }
/* 149 */     StringBuffer elementCode = new StringBuffer();
/* 150 */     PolicyRecord policyRec = new PolicyRecord();
/* 151 */     QName elementName = reader.getName();
/*     */     
/* 153 */     int depth = 0; try {
/*     */       do {
/*     */         boolean insidePolicyReferenceAttr; QName curName; StringBuffer xmlnsCode; Set<String> tmpNsSet; int attrCount; StringBuffer attrCode; int i;
/* 156 */         switch (reader.getEventType()) {
/*     */           case 1:
/* 158 */             curName = reader.getName();
/* 159 */             insidePolicyReferenceAttr = (NamespaceVersion.resolveAsToken(curName) == XmlToken.PolicyReference);
/* 160 */             if (elementName.equals(curName)) {
/* 161 */               depth++;
/*     */             }
/* 163 */             xmlnsCode = new StringBuffer();
/* 164 */             tmpNsSet = new HashSet<>();
/* 165 */             if (null == curName.getPrefix() || "".equals(curName.getPrefix())) {
/* 166 */               elementCode
/* 167 */                 .append('<')
/* 168 */                 .append(curName.getLocalPart());
/* 169 */               xmlnsCode
/* 170 */                 .append(" xmlns=\"")
/* 171 */                 .append(curName.getNamespaceURI())
/* 172 */                 .append('"');
/*     */             } else {
/*     */               
/* 175 */               elementCode
/* 176 */                 .append('<')
/* 177 */                 .append(curName.getPrefix())
/* 178 */                 .append(':')
/* 179 */                 .append(curName.getLocalPart());
/* 180 */               xmlnsCode
/* 181 */                 .append(" xmlns:")
/* 182 */                 .append(curName.getPrefix())
/* 183 */                 .append("=\"")
/* 184 */                 .append(curName.getNamespaceURI())
/* 185 */                 .append('"');
/* 186 */               tmpNsSet.add(curName.getPrefix());
/*     */             } 
/* 188 */             attrCount = reader.getAttributeCount();
/* 189 */             attrCode = new StringBuffer();
/* 190 */             for (i = 0; i < attrCount; i++) {
/* 191 */               boolean uriAttrFlg = false;
/* 192 */               if (insidePolicyReferenceAttr && "URI".equals(reader
/* 193 */                   .getAttributeName(i).getLocalPart())) {
/* 194 */                 uriAttrFlg = true;
/* 195 */                 if (null == policyRec.unresolvedURIs) {
/* 196 */                   policyRec.unresolvedURIs = new HashSet<>();
/*     */                 }
/* 198 */                 policyRec.unresolvedURIs.add(
/* 199 */                     relativeToAbsoluteUrl(reader.getAttributeValue(i), baseUrl));
/*     */               } 
/* 201 */               if (!"xmlns".equals(reader.getAttributePrefix(i)) || !tmpNsSet.contains(reader.getAttributeLocalName(i)))
/*     */               {
/*     */                 
/* 204 */                 if (null == reader.getAttributePrefix(i) || "".equals(reader.getAttributePrefix(i))) {
/* 205 */                   attrCode
/* 206 */                     .append(' ')
/* 207 */                     .append(reader.getAttributeLocalName(i))
/* 208 */                     .append("=\"")
/* 209 */                     .append(uriAttrFlg ? relativeToAbsoluteUrl(reader.getAttributeValue(i), baseUrl) : reader.getAttributeValue(i))
/* 210 */                     .append('"');
/*     */                 } else {
/* 212 */                   attrCode
/* 213 */                     .append(' ')
/* 214 */                     .append(reader.getAttributePrefix(i))
/* 215 */                     .append(':')
/* 216 */                     .append(reader.getAttributeLocalName(i))
/* 217 */                     .append("=\"")
/* 218 */                     .append(uriAttrFlg ? relativeToAbsoluteUrl(reader.getAttributeValue(i), baseUrl) : reader.getAttributeValue(i))
/* 219 */                     .append('"');
/* 220 */                   if (!tmpNsSet.contains(reader.getAttributePrefix(i))) {
/* 221 */                     xmlnsCode
/* 222 */                       .append(" xmlns:")
/* 223 */                       .append(reader.getAttributePrefix(i))
/* 224 */                       .append("=\"")
/* 225 */                       .append(reader.getAttributeNamespace(i))
/* 226 */                       .append('"');
/* 227 */                     tmpNsSet.add(reader.getAttributePrefix(i));
/*     */                   } 
/*     */                 }  } 
/*     */             } 
/* 231 */             elementCode
/* 232 */               .append(xmlnsCode)
/* 233 */               .append(attrCode)
/* 234 */               .append('>');
/*     */             break;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case 2:
/* 241 */             curName = reader.getName();
/* 242 */             if (elementName.equals(curName)) {
/* 243 */               depth--;
/*     */             }
/* 245 */             elementCode
/* 246 */               .append("</")
/* 247 */               .append("".equals(curName.getPrefix()) ? "" : (curName.getPrefix() + ':'))
/* 248 */               .append(curName.getLocalPart())
/* 249 */               .append('>');
/*     */             break;
/*     */           case 4:
/* 252 */             elementCode.append(reader.getText());
/*     */             break;
/*     */           case 12:
/* 255 */             elementCode
/* 256 */               .append("<![CDATA[")
/* 257 */               .append(reader.getText())
/* 258 */               .append("]]>");
/*     */             break;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 265 */         if (!reader.hasNext() || depth <= 0)
/* 266 */           continue;  reader.next();
/*     */       }
/* 268 */       while (8 != reader.getEventType() && depth > 0);
/* 269 */       policyRec.policyModel = ModelUnmarshaller.getUnmarshaller().unmarshalModel(new StringReader(elementCode
/* 270 */             .toString()));
/* 271 */       if (null != policyRec.policyModel.getPolicyId()) {
/* 272 */         policyRec.setUri(baseUrl + "#" + policyRec.policyModel.getPolicyId(), policyRec.policyModel.getPolicyId());
/* 273 */       } else if (policyRec.policyModel.getPolicyName() != null) {
/* 274 */         policyRec.setUri(policyRec.policyModel.getPolicyName(), policyRec.policyModel.getPolicyName());
/*     */       } 
/* 276 */     } catch (Exception e) {
/* 277 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(PolicyMessages.WSP_1013_EXCEPTION_WHEN_READING_POLICY_ELEMENT(elementCode.toString()), e));
/*     */     } 
/* 279 */     this.urlsRead.add(baseUrl);
/* 280 */     return policyRec;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getUrlsRead() {
/* 285 */     return this.urlsRead;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String readPolicyReferenceElement(XMLStreamReader reader) {
/*     */     try {
/* 297 */       if (NamespaceVersion.resolveAsToken(reader.getName()) == XmlToken.PolicyReference) {
/* 298 */         for (int i = 0; i < reader.getAttributeCount(); i++) {
/* 299 */           if (XmlToken.resolveToken(reader.getAttributeName(i).getLocalPart()) == XmlToken.Uri) {
/* 300 */             String uriValue = reader.getAttributeValue(i);
/* 301 */             reader.next();
/* 302 */             return uriValue;
/*     */           } 
/*     */         } 
/*     */       }
/* 306 */       reader.next();
/* 307 */       return null;
/* 308 */     } catch (XMLStreamException e) {
/* 309 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(PolicyMessages.WSP_1001_XML_EXCEPTION_WHEN_PROCESSING_POLICY_REFERENCE(), e));
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String relativeToAbsoluteUrl(String relativeUri, String baseUri) {
/* 325 */     if ('#' != relativeUri.charAt(0)) {
/* 326 */       return relativeUri;
/*     */     }
/* 328 */     return (null == baseUri) ? relativeUri : (baseUri + relativeUri);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/policy/jaxws/SafePolicyReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */