/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.security.cert.CRLReason;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.util.Enumeration;
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
/*     */ public class CRLReasonCodeExtension
/*     */   extends Extension
/*     */   implements CertAttrSet<String>
/*     */ {
/*     */   public static final String NAME = "CRLReasonCode";
/*     */   public static final String REASON = "reason";
/*  52 */   private static CRLReason[] values = CRLReason.values();
/*     */   
/*  54 */   private int reasonCode = 0;
/*     */   
/*     */   private void encodeThis() throws IOException {
/*  57 */     if (this.reasonCode == 0) {
/*  58 */       this.extensionValue = null;
/*     */       return;
/*     */     } 
/*  61 */     DerOutputStream derOutputStream = new DerOutputStream();
/*  62 */     derOutputStream.putEnumerated(this.reasonCode);
/*  63 */     this.extensionValue = derOutputStream.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CRLReasonCodeExtension(int paramInt) throws IOException {
/*  73 */     this(false, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CRLReasonCodeExtension(boolean paramBoolean, int paramInt) throws IOException {
/*  84 */     this.extensionId = PKIXExtensions.ReasonCode_Id;
/*  85 */     this.critical = paramBoolean;
/*  86 */     this.reasonCode = paramInt;
/*  87 */     encodeThis();
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
/*     */   public CRLReasonCodeExtension(Boolean paramBoolean, Object paramObject) throws IOException {
/* 100 */     this.extensionId = PKIXExtensions.ReasonCode_Id;
/* 101 */     this.critical = paramBoolean.booleanValue();
/* 102 */     this.extensionValue = (byte[])paramObject;
/* 103 */     DerValue derValue = new DerValue(this.extensionValue);
/* 104 */     this.reasonCode = derValue.getEnumerated();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String paramString, Object paramObject) throws IOException {
/* 111 */     if (!(paramObject instanceof Integer)) {
/* 112 */       throw new IOException("Attribute must be of type Integer.");
/*     */     }
/* 114 */     if (paramString.equalsIgnoreCase("reason")) {
/* 115 */       this.reasonCode = ((Integer)paramObject).intValue();
/*     */     } else {
/* 117 */       throw new IOException("Name not supported by CRLReasonCodeExtension");
/*     */     } 
/*     */     
/* 120 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer get(String paramString) throws IOException {
/* 127 */     if (paramString.equalsIgnoreCase("reason")) {
/* 128 */       return new Integer(this.reasonCode);
/*     */     }
/* 130 */     throw new IOException("Name not supported by CRLReasonCodeExtension");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(String paramString) throws IOException {
/* 139 */     if (paramString.equalsIgnoreCase("reason")) {
/* 140 */       this.reasonCode = 0;
/*     */     } else {
/* 142 */       throw new IOException("Name not supported by CRLReasonCodeExtension");
/*     */     } 
/*     */     
/* 145 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 152 */     return super.toString() + "    Reason Code: " + getReasonCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(OutputStream paramOutputStream) throws IOException {
/* 162 */     DerOutputStream derOutputStream = new DerOutputStream();
/*     */     
/* 164 */     if (this.extensionValue == null) {
/* 165 */       this.extensionId = PKIXExtensions.ReasonCode_Id;
/* 166 */       this.critical = false;
/* 167 */       encodeThis();
/*     */     } 
/* 169 */     encode(derOutputStream);
/* 170 */     paramOutputStream.write(derOutputStream.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<String> getElements() {
/* 178 */     AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
/* 179 */     attributeNameEnumeration.addElement("reason");
/*     */     
/* 181 */     return attributeNameEnumeration.elements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 188 */     return "CRLReasonCode";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CRLReason getReasonCode() {
/* 196 */     if (this.reasonCode > 0 && this.reasonCode < values.length) {
/* 197 */       return values[this.reasonCode];
/*     */     }
/* 199 */     return CRLReason.UNSPECIFIED;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/CRLReasonCodeExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */