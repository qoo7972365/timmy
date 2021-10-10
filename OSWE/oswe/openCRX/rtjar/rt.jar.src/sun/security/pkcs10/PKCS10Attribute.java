/*     */ package sun.security.pkcs10;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import sun.security.pkcs.PKCS9Attribute;
/*     */ import sun.security.util.DerEncoder;
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
/*     */ public class PKCS10Attribute
/*     */   implements DerEncoder
/*     */ {
/*  59 */   protected ObjectIdentifier attributeId = null;
/*  60 */   protected Object attributeValue = null;
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
/*     */   public PKCS10Attribute(DerValue paramDerValue) throws IOException {
/*  73 */     PKCS9Attribute pKCS9Attribute = new PKCS9Attribute(paramDerValue);
/*  74 */     this.attributeId = pKCS9Attribute.getOID();
/*  75 */     this.attributeValue = pKCS9Attribute.getValue();
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
/*     */   public PKCS10Attribute(ObjectIdentifier paramObjectIdentifier, Object paramObject) {
/*  88 */     this.attributeId = paramObjectIdentifier;
/*  89 */     this.attributeValue = paramObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PKCS10Attribute(PKCS9Attribute paramPKCS9Attribute) {
/*  98 */     this.attributeId = paramPKCS9Attribute.getOID();
/*  99 */     this.attributeValue = paramPKCS9Attribute.getValue();
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
/*     */   public void derEncode(OutputStream paramOutputStream) throws IOException {
/* 112 */     PKCS9Attribute pKCS9Attribute = new PKCS9Attribute(this.attributeId, this.attributeValue);
/* 113 */     pKCS9Attribute.derEncode(paramOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectIdentifier getAttributeId() {
/* 120 */     return this.attributeId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getAttributeValue() {
/* 127 */     return this.attributeValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 134 */     return this.attributeValue.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/pkcs10/PKCS10Attribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */