/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.util.Enumeration;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
/*     */ import sun.security.util.ObjectIdentifier;
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
/*     */ public class InhibitAnyPolicyExtension
/*     */   extends Extension
/*     */   implements CertAttrSet<String>
/*     */ {
/*  67 */   private static final Debug debug = Debug.getInstance("certpath");
/*     */ 
/*     */   
/*     */   public static final String IDENT = "x509.info.extensions.InhibitAnyPolicy";
/*     */   
/*     */   public static ObjectIdentifier AnyPolicy_Id;
/*     */   
/*     */   public static final String NAME = "InhibitAnyPolicy";
/*     */   
/*     */   public static final String SKIP_CERTS = "skip_certs";
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/*  81 */       AnyPolicy_Id = new ObjectIdentifier("2.5.29.32.0");
/*  82 */     } catch (IOException iOException) {}
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
/*  94 */   private int skipCerts = Integer.MAX_VALUE;
/*     */ 
/*     */   
/*     */   private void encodeThis() throws IOException {
/*  98 */     DerOutputStream derOutputStream = new DerOutputStream();
/*  99 */     derOutputStream.putInteger(this.skipCerts);
/* 100 */     this.extensionValue = derOutputStream.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InhibitAnyPolicyExtension(int paramInt) throws IOException {
/* 110 */     if (paramInt < -1)
/* 111 */       throw new IOException("Invalid value for skipCerts"); 
/* 112 */     if (paramInt == -1) {
/* 113 */       this.skipCerts = Integer.MAX_VALUE;
/*     */     } else {
/* 115 */       this.skipCerts = paramInt;
/* 116 */     }  this.extensionId = PKIXExtensions.InhibitAnyPolicy_Id;
/* 117 */     this.critical = true;
/* 118 */     encodeThis();
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
/*     */   public InhibitAnyPolicyExtension(Boolean paramBoolean, Object paramObject) throws IOException {
/* 133 */     this.extensionId = PKIXExtensions.InhibitAnyPolicy_Id;
/*     */     
/* 135 */     if (!paramBoolean.booleanValue()) {
/* 136 */       throw new IOException("Criticality cannot be false for InhibitAnyPolicy");
/*     */     }
/* 138 */     this.critical = paramBoolean.booleanValue();
/*     */     
/* 140 */     this.extensionValue = (byte[])paramObject;
/* 141 */     DerValue derValue = new DerValue(this.extensionValue);
/* 142 */     if (derValue.tag != 2) {
/* 143 */       throw new IOException("Invalid encoding of InhibitAnyPolicy: data not integer");
/*     */     }
/*     */     
/* 146 */     if (derValue.data == null) {
/* 147 */       throw new IOException("Invalid encoding of InhibitAnyPolicy: null data");
/*     */     }
/* 149 */     int i = derValue.getInteger();
/* 150 */     if (i < -1)
/* 151 */       throw new IOException("Invalid value for skipCerts"); 
/* 152 */     if (i == -1) {
/* 153 */       this.skipCerts = Integer.MAX_VALUE;
/*     */     } else {
/* 155 */       this.skipCerts = i;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 163 */     return super.toString() + "InhibitAnyPolicy: " + this.skipCerts + "\n";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(OutputStream paramOutputStream) throws IOException {
/* 173 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 174 */     if (this.extensionValue == null) {
/* 175 */       this.extensionId = PKIXExtensions.InhibitAnyPolicy_Id;
/* 176 */       this.critical = true;
/* 177 */       encodeThis();
/*     */     } 
/* 179 */     encode(derOutputStream);
/*     */     
/* 181 */     paramOutputStream.write(derOutputStream.toByteArray());
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
/*     */   public void set(String paramString, Object paramObject) throws IOException {
/* 193 */     if (paramString.equalsIgnoreCase("skip_certs")) {
/* 194 */       if (!(paramObject instanceof Integer))
/* 195 */         throw new IOException("Attribute value should be of type Integer."); 
/* 196 */       int i = ((Integer)paramObject).intValue();
/* 197 */       if (i < -1)
/* 198 */         throw new IOException("Invalid value for skipCerts"); 
/* 199 */       if (i == -1) {
/* 200 */         this.skipCerts = Integer.MAX_VALUE;
/*     */       } else {
/* 202 */         this.skipCerts = i;
/*     */       } 
/*     */     } else {
/* 205 */       throw new IOException("Attribute name not recognized by CertAttrSet:InhibitAnyPolicy.");
/*     */     } 
/* 207 */     encodeThis();
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
/*     */   public Integer get(String paramString) throws IOException {
/* 219 */     if (paramString.equalsIgnoreCase("skip_certs")) {
/* 220 */       return new Integer(this.skipCerts);
/*     */     }
/* 222 */     throw new IOException("Attribute name not recognized by CertAttrSet:InhibitAnyPolicy.");
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
/*     */   public void delete(String paramString) throws IOException {
/* 235 */     if (paramString.equalsIgnoreCase("skip_certs")) {
/* 236 */       throw new IOException("Attribute skip_certs may not be deleted.");
/*     */     }
/*     */     
/* 239 */     throw new IOException("Attribute name not recognized by CertAttrSet:InhibitAnyPolicy.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<String> getElements() {
/* 250 */     AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
/* 251 */     attributeNameEnumeration.addElement("skip_certs");
/* 252 */     return attributeNameEnumeration.elements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 261 */     return "InhibitAnyPolicy";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/InhibitAnyPolicyExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */