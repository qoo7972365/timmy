/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.cert.PolicyQualifierInfo;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
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
/*     */ public class PolicyInformation
/*     */ {
/*     */   public static final String NAME = "PolicyInformation";
/*     */   public static final String ID = "id";
/*     */   public static final String QUALIFIERS = "qualifiers";
/*     */   private CertificatePolicyId policyIdentifier;
/*     */   private Set<PolicyQualifierInfo> policyQualifiers;
/*     */   
/*     */   public PolicyInformation(CertificatePolicyId paramCertificatePolicyId, Set<PolicyQualifierInfo> paramSet) throws IOException {
/*  87 */     if (paramSet == null) {
/*  88 */       throw new NullPointerException("policyQualifiers is null");
/*     */     }
/*  90 */     this.policyQualifiers = new LinkedHashSet<>(paramSet);
/*     */     
/*  92 */     this.policyIdentifier = paramCertificatePolicyId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolicyInformation(DerValue paramDerValue) throws IOException {
/* 103 */     if (paramDerValue.tag != 48) {
/* 104 */       throw new IOException("Invalid encoding of PolicyInformation");
/*     */     }
/* 106 */     this.policyIdentifier = new CertificatePolicyId(paramDerValue.data.getDerValue());
/* 107 */     if (paramDerValue.data.available() != 0) {
/* 108 */       this.policyQualifiers = new LinkedHashSet<>();
/* 109 */       DerValue derValue = paramDerValue.data.getDerValue();
/* 110 */       if (derValue.tag != 48)
/* 111 */         throw new IOException("Invalid encoding of PolicyInformation"); 
/* 112 */       if (derValue.data.available() == 0)
/* 113 */         throw new IOException("No data available in policyQualifiers"); 
/* 114 */       while (derValue.data.available() != 0)
/* 115 */         this.policyQualifiers.add(new PolicyQualifierInfo(derValue.data
/* 116 */               .getDerValue().toByteArray())); 
/*     */     } else {
/* 118 */       this.policyQualifiers = Collections.emptySet();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 129 */     if (!(paramObject instanceof PolicyInformation))
/* 130 */       return false; 
/* 131 */     PolicyInformation policyInformation = (PolicyInformation)paramObject;
/*     */     
/* 133 */     if (!this.policyIdentifier.equals(policyInformation.getPolicyIdentifier())) {
/* 134 */       return false;
/*     */     }
/* 136 */     return this.policyQualifiers.equals(policyInformation.getPolicyQualifiers());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 145 */     int i = 37 + this.policyIdentifier.hashCode();
/* 146 */     i = 37 * i + this.policyQualifiers.hashCode();
/* 147 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CertificatePolicyId getPolicyIdentifier() {
/* 157 */     return this.policyIdentifier;
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
/*     */   public Set<PolicyQualifierInfo> getPolicyQualifiers() {
/* 169 */     return this.policyQualifiers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(String paramString) throws IOException {
/* 176 */     if (paramString.equalsIgnoreCase("id"))
/* 177 */       return this.policyIdentifier; 
/* 178 */     if (paramString.equalsIgnoreCase("qualifiers")) {
/* 179 */       return this.policyQualifiers;
/*     */     }
/* 181 */     throw new IOException("Attribute name [" + paramString + "] not recognized by PolicyInformation.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String paramString, Object paramObject) throws IOException {
/* 191 */     if (paramString.equalsIgnoreCase("id")) {
/* 192 */       if (paramObject instanceof CertificatePolicyId) {
/* 193 */         this.policyIdentifier = (CertificatePolicyId)paramObject;
/*     */       } else {
/* 195 */         throw new IOException("Attribute value must be instance of CertificatePolicyId.");
/*     */       } 
/* 197 */     } else if (paramString.equalsIgnoreCase("qualifiers")) {
/* 198 */       if (this.policyIdentifier == null) {
/* 199 */         throw new IOException("Attribute must have a CertificatePolicyIdentifier value before PolicyQualifierInfo can be set.");
/*     */       }
/*     */ 
/*     */       
/* 203 */       if (paramObject instanceof Set) {
/* 204 */         Iterator<Object> iterator = ((Set)paramObject).iterator();
/* 205 */         while (iterator.hasNext()) {
/* 206 */           Object object = iterator.next();
/* 207 */           if (!(object instanceof PolicyQualifierInfo)) {
/* 208 */             throw new IOException("Attribute value must be aSet of PolicyQualifierInfo objects.");
/*     */           }
/*     */         } 
/*     */         
/* 212 */         this.policyQualifiers = (Set<PolicyQualifierInfo>)paramObject;
/*     */       } else {
/* 214 */         throw new IOException("Attribute value must be of type Set.");
/*     */       } 
/*     */     } else {
/* 217 */       throw new IOException("Attribute name [" + paramString + "] not recognized by PolicyInformation");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(String paramString) throws IOException {
/* 226 */     if (paramString.equalsIgnoreCase("qualifiers"))
/* 227 */     { this.policyQualifiers = Collections.emptySet(); }
/* 228 */     else { if (paramString.equalsIgnoreCase("id")) {
/* 229 */         throw new IOException("Attribute ID may not be deleted from PolicyInformation.");
/*     */       }
/*     */ 
/*     */       
/* 233 */       throw new IOException("Attribute name [" + paramString + "] not recognized by PolicyInformation."); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<String> getElements() {
/* 243 */     AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
/* 244 */     attributeNameEnumeration.addElement("id");
/* 245 */     attributeNameEnumeration.addElement("qualifiers");
/*     */     
/* 247 */     return attributeNameEnumeration.elements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 254 */     return "PolicyInformation";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 261 */     StringBuilder stringBuilder = new StringBuilder("  [" + this.policyIdentifier.toString());
/* 262 */     stringBuilder.append(this.policyQualifiers + "  ]\n");
/* 263 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(DerOutputStream paramDerOutputStream) throws IOException {
/* 273 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 274 */     this.policyIdentifier.encode(derOutputStream);
/* 275 */     if (!this.policyQualifiers.isEmpty()) {
/* 276 */       DerOutputStream derOutputStream1 = new DerOutputStream();
/* 277 */       for (PolicyQualifierInfo policyQualifierInfo : this.policyQualifiers) {
/* 278 */         derOutputStream1.write(policyQualifierInfo.getEncoded());
/*     */       }
/* 280 */       derOutputStream.write((byte)48, derOutputStream1);
/*     */     } 
/* 282 */     paramDerOutputStream.write((byte)48, derOutputStream);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/PolicyInformation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */