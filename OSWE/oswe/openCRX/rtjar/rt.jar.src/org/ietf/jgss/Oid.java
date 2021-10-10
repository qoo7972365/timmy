/*     */ package org.ietf.jgss;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ 
/*     */ public class Oid
/*     */ {
/*     */   private ObjectIdentifier oid;
/*     */   private byte[] derEncoding;
/*     */   
/*     */   public Oid(String paramString) throws GSSException {
/*     */     try {
/*  68 */       this.oid = new ObjectIdentifier(paramString);
/*  69 */       this.derEncoding = null;
/*  70 */     } catch (Exception exception) {
/*  71 */       throw new GSSException(11, "Improperly formatted Object Identifier String - " + paramString);
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
/*     */   public Oid(InputStream paramInputStream) throws GSSException {
/*     */     try {
/*  89 */       DerValue derValue = new DerValue(paramInputStream);
/*  90 */       this.derEncoding = derValue.toByteArray();
/*  91 */       this.oid = derValue.getOID();
/*  92 */     } catch (IOException iOException) {
/*  93 */       throw new GSSException(11, "Improperly formatted ASN.1 DER encoding for Oid");
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
/*     */   public Oid(byte[] paramArrayOfbyte) throws GSSException {
/*     */     try {
/* 111 */       DerValue derValue = new DerValue(paramArrayOfbyte);
/* 112 */       this.derEncoding = derValue.toByteArray();
/* 113 */       this.oid = derValue.getOID();
/* 114 */     } catch (IOException iOException) {
/* 115 */       throw new GSSException(11, "Improperly formatted ASN.1 DER encoding for Oid");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Oid getInstance(String paramString) {
/* 126 */     Oid oid = null;
/*     */     try {
/* 128 */       oid = new Oid(paramString);
/* 129 */     } catch (GSSException gSSException) {}
/*     */ 
/*     */     
/* 132 */     return oid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 142 */     return this.oid.toString();
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
/*     */   public boolean equals(Object paramObject) {
/* 156 */     if (this == paramObject) {
/* 157 */       return true;
/*     */     }
/* 159 */     if (paramObject instanceof Oid)
/* 160 */       return this.oid.equals(((Oid)paramObject).oid); 
/* 161 */     if (paramObject instanceof ObjectIdentifier) {
/* 162 */       return this.oid.equals(paramObject);
/*     */     }
/* 164 */     return false;
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
/*     */   public byte[] getDER() throws GSSException {
/* 177 */     if (this.derEncoding == null) {
/* 178 */       DerOutputStream derOutputStream = new DerOutputStream();
/*     */       try {
/* 180 */         derOutputStream.putOID(this.oid);
/* 181 */       } catch (IOException iOException) {
/* 182 */         throw new GSSException(11, iOException.getMessage());
/*     */       } 
/* 184 */       this.derEncoding = derOutputStream.toByteArray();
/*     */     } 
/*     */     
/* 187 */     return (byte[])this.derEncoding.clone();
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
/*     */   public boolean containedIn(Oid[] paramArrayOfOid) {
/* 199 */     for (byte b = 0; b < paramArrayOfOid.length; b++) {
/* 200 */       if (paramArrayOfOid[b].equals(this)) {
/* 201 */         return true;
/*     */       }
/*     */     } 
/* 204 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 214 */     return this.oid.hashCode();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/ietf/jgss/Oid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */