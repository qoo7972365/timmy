/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Vector;
/*     */ import sun.security.krb5.Asn1Exception;
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
/*     */ 
/*     */ public class LastReq
/*     */ {
/*  56 */   private LastReqEntry[] entry = null;
/*     */   
/*     */   public LastReq(LastReqEntry[] paramArrayOfLastReqEntry) throws IOException {
/*  59 */     if (paramArrayOfLastReqEntry != null) {
/*  60 */       this.entry = new LastReqEntry[paramArrayOfLastReqEntry.length];
/*  61 */       for (byte b = 0; b < paramArrayOfLastReqEntry.length; b++) {
/*  62 */         if (paramArrayOfLastReqEntry[b] == null) {
/*  63 */           throw new IOException("Cannot create a LastReqEntry");
/*     */         }
/*  65 */         this.entry[b] = (LastReqEntry)paramArrayOfLastReqEntry[b].clone();
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
/*     */ 
/*     */   
/*     */   public LastReq(DerValue paramDerValue) throws Asn1Exception, IOException {
/*  80 */     Vector<LastReqEntry> vector = new Vector();
/*  81 */     if (paramDerValue.getTag() != 48) {
/*  82 */       throw new Asn1Exception(906);
/*     */     }
/*  84 */     while (paramDerValue.getData().available() > 0) {
/*  85 */       vector.addElement(new LastReqEntry(paramDerValue.getData().getDerValue()));
/*     */     }
/*  87 */     if (vector.size() > 0) {
/*  88 */       this.entry = new LastReqEntry[vector.size()];
/*  89 */       vector.copyInto((Object[])this.entry);
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
/* 100 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 101 */     if (this.entry != null && this.entry.length > 0) {
/* 102 */       DerOutputStream derOutputStream1 = new DerOutputStream();
/* 103 */       for (byte b = 0; b < this.entry.length; b++)
/* 104 */         derOutputStream1.write(this.entry[b].asn1Encode()); 
/* 105 */       derOutputStream.write((byte)48, derOutputStream1);
/* 106 */       return derOutputStream.toByteArray();
/*     */     } 
/* 108 */     return null;
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
/*     */   public static LastReq parse(DerInputStream paramDerInputStream, byte paramByte, boolean paramBoolean) throws Asn1Exception, IOException {
/* 125 */     if (paramBoolean && ((byte)paramDerInputStream.peekByte() & 0x1F) != paramByte)
/* 126 */       return null; 
/* 127 */     DerValue derValue1 = paramDerInputStream.getDerValue();
/* 128 */     if (paramByte != (derValue1.getTag() & 0x1F)) {
/* 129 */       throw new Asn1Exception(906);
/*     */     }
/*     */     
/* 132 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 133 */     return new LastReq(derValue2);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/LastReq.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */