/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Vector;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.internal.ccache.CCacheOutputStream;
/*     */ import sun.security.util.DerInputStream;
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
/*     */ public class AuthorizationData
/*     */   implements Cloneable
/*     */ {
/*  57 */   private AuthorizationDataEntry[] entry = null;
/*     */ 
/*     */   
/*     */   private AuthorizationData() {}
/*     */ 
/*     */   
/*     */   public AuthorizationData(AuthorizationDataEntry[] paramArrayOfAuthorizationDataEntry) throws IOException {
/*  64 */     if (paramArrayOfAuthorizationDataEntry != null) {
/*  65 */       this.entry = new AuthorizationDataEntry[paramArrayOfAuthorizationDataEntry.length];
/*  66 */       for (byte b = 0; b < paramArrayOfAuthorizationDataEntry.length; b++) {
/*  67 */         if (paramArrayOfAuthorizationDataEntry[b] == null) {
/*  68 */           throw new IOException("Cannot create an AuthorizationData");
/*     */         }
/*  70 */         this.entry[b] = (AuthorizationDataEntry)paramArrayOfAuthorizationDataEntry[b].clone();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AuthorizationData(AuthorizationDataEntry paramAuthorizationDataEntry) {
/*  77 */     this.entry = new AuthorizationDataEntry[1];
/*  78 */     this.entry[0] = paramAuthorizationDataEntry;
/*     */   }
/*     */   
/*     */   public Object clone() {
/*  82 */     AuthorizationData authorizationData = new AuthorizationData();
/*     */     
/*  84 */     if (this.entry != null) {
/*  85 */       authorizationData.entry = new AuthorizationDataEntry[this.entry.length];
/*     */       
/*  87 */       for (byte b = 0; b < this.entry.length; b++) {
/*  88 */         authorizationData.entry[b] = (AuthorizationDataEntry)this.entry[b]
/*  89 */           .clone();
/*     */       }
/*     */     } 
/*  92 */     return authorizationData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthorizationData(DerValue paramDerValue) throws Asn1Exception, IOException {
/* 102 */     Vector<AuthorizationDataEntry> vector = new Vector();
/* 103 */     if (paramDerValue.getTag() != 48) {
/* 104 */       throw new Asn1Exception(906);
/*     */     }
/* 106 */     while (paramDerValue.getData().available() > 0) {
/* 107 */       vector.addElement(new AuthorizationDataEntry(paramDerValue.getData().getDerValue()));
/*     */     }
/* 109 */     if (vector.size() > 0) {
/* 110 */       this.entry = new AuthorizationDataEntry[vector.size()];
/* 111 */       vector.copyInto((Object[])this.entry);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 122 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 123 */     DerValue[] arrayOfDerValue = new DerValue[this.entry.length];
/* 124 */     for (byte b = 0; b < this.entry.length; b++) {
/* 125 */       arrayOfDerValue[b] = new DerValue(this.entry[b].asn1Encode());
/*     */     }
/* 127 */     derOutputStream.putSequence(arrayOfDerValue);
/* 128 */     return derOutputStream.toByteArray();
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
/*     */   public static AuthorizationData parse(DerInputStream paramDerInputStream, byte paramByte, boolean paramBoolean) throws Asn1Exception, IOException {
/* 145 */     if (paramBoolean && ((byte)paramDerInputStream.peekByte() & 0x1F) != paramByte) {
/* 146 */       return null;
/*     */     }
/* 148 */     DerValue derValue1 = paramDerInputStream.getDerValue();
/* 149 */     if (paramByte != (derValue1.getTag() & 0x1F)) {
/* 150 */       throw new Asn1Exception(906);
/*     */     }
/* 152 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 153 */     return new AuthorizationData(derValue2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeAuth(CCacheOutputStream paramCCacheOutputStream) throws IOException {
/* 164 */     for (byte b = 0; b < this.entry.length; b++) {
/* 165 */       this.entry[b].writeEntry(paramCCacheOutputStream);
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString() {
/* 170 */     String str = "AuthorizationData:\n";
/* 171 */     for (byte b = 0; b < this.entry.length; b++) {
/* 172 */       str = str + this.entry[b].toString();
/*     */     }
/* 174 */     return str;
/*     */   }
/*     */   
/*     */   public int count() {
/* 178 */     return this.entry.length;
/*     */   }
/*     */   
/*     */   public AuthorizationDataEntry item(int paramInt) {
/* 182 */     return (AuthorizationDataEntry)this.entry[paramInt].clone();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/AuthorizationData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */