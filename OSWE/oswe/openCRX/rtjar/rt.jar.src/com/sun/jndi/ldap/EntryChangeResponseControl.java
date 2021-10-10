/*     */ package com.sun.jndi.ldap;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EntryChangeResponseControl
/*     */   extends BasicControl
/*     */ {
/*     */   public static final String OID = "2.16.840.1.113730.3.4.7";
/*     */   public static final int ADD = 1;
/*     */   public static final int DELETE = 2;
/*     */   public static final int MODIFY = 4;
/*     */   public static final int RENAME = 8;
/*     */   private int changeType;
/*  97 */   private String previousDN = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   private long changeNumber = -1L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = -2087354136750180511L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntryChangeResponseControl(String paramString, boolean paramBoolean, byte[] paramArrayOfbyte) throws IOException {
/* 121 */     super(paramString, paramBoolean, paramArrayOfbyte);
/*     */ 
/*     */     
/* 124 */     if (paramArrayOfbyte != null && paramArrayOfbyte.length > 0) {
/* 125 */       BerDecoder berDecoder = new BerDecoder(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*     */       
/* 127 */       berDecoder.parseSeq(null);
/* 128 */       this.changeType = berDecoder.parseEnumeration();
/*     */       
/* 130 */       if (berDecoder.bytesLeft() > 0 && berDecoder.peekByte() == 4) {
/* 131 */         this.previousDN = berDecoder.parseString(true);
/*     */       }
/* 133 */       if (berDecoder.bytesLeft() > 0 && berDecoder.peekByte() == 2) {
/* 134 */         this.changeNumber = berDecoder.parseInt();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getChangeType() {
/* 145 */     return this.changeType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPreviousDN() {
/* 155 */     return this.previousDN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getChangeNumber() {
/* 165 */     return this.changeNumber;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/EntryChangeResponseControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */