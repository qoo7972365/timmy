/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
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
/*     */ public class OIDName
/*     */   implements GeneralNameInterface
/*     */ {
/*     */   private ObjectIdentifier oid;
/*     */   
/*     */   public OIDName(DerValue paramDerValue) throws IOException {
/*  52 */     this.oid = paramDerValue.getOID();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OIDName(ObjectIdentifier paramObjectIdentifier) {
/*  61 */     this.oid = paramObjectIdentifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OIDName(String paramString) throws IOException {
/*     */     try {
/*  72 */       this.oid = new ObjectIdentifier(paramString);
/*  73 */     } catch (Exception exception) {
/*  74 */       throw new IOException("Unable to create OIDName: " + exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/*  82 */     return 8;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(DerOutputStream paramDerOutputStream) throws IOException {
/*  92 */     paramDerOutputStream.putOID(this.oid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  99 */     return "OIDName: " + this.oid.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectIdentifier getOID() {
/* 106 */     return this.oid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 115 */     if (this == paramObject) {
/* 116 */       return true;
/*     */     }
/* 118 */     if (!(paramObject instanceof OIDName)) {
/* 119 */       return false;
/*     */     }
/* 121 */     OIDName oIDName = (OIDName)paramObject;
/*     */     
/* 123 */     return this.oid.equals(oIDName.oid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 132 */     return this.oid.hashCode();
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
/*     */ 
/*     */ 
/*     */   
/*     */   public int constrains(GeneralNameInterface paramGeneralNameInterface) throws UnsupportedOperationException {
/*     */     boolean bool;
/* 152 */     if (paramGeneralNameInterface == null) {
/* 153 */       bool = true;
/* 154 */     } else if (paramGeneralNameInterface.getType() != 8) {
/* 155 */       bool = true;
/* 156 */     } else if (equals(paramGeneralNameInterface)) {
/* 157 */       bool = false;
/*     */     } else {
/*     */       
/* 160 */       throw new UnsupportedOperationException("Narrowing and widening are not supported for OIDNames");
/* 161 */     }  return bool;
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
/*     */   public int subtreeDepth() throws UnsupportedOperationException {
/* 173 */     throw new UnsupportedOperationException("subtreeDepth() not supported for OIDName.");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/OIDName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */