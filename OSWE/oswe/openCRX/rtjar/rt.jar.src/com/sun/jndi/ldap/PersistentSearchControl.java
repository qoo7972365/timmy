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
/*     */ public final class PersistentSearchControl
/*     */   extends BasicControl
/*     */ {
/*     */   public static final String OID = "2.16.840.1.113730.3.4.3";
/*     */   public static final int ADD = 1;
/*     */   public static final int DELETE = 2;
/*     */   public static final int MODIFY = 4;
/*     */   public static final int RENAME = 8;
/*     */   public static final int ANY = 15;
/*  88 */   private int changeTypes = 15;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean changesOnly = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean returnControls = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 6335140491154854116L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PersistentSearchControl() throws IOException {
/* 115 */     super("2.16.840.1.113730.3.4.3");
/* 116 */     this.value = setEncodedValue();
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
/*     */   public PersistentSearchControl(int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) throws IOException {
/* 132 */     super("2.16.840.1.113730.3.4.3", paramBoolean3, null);
/* 133 */     this.changeTypes = paramInt;
/* 134 */     this.changesOnly = paramBoolean1;
/* 135 */     this.returnControls = paramBoolean2;
/* 136 */     this.value = setEncodedValue();
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
/*     */   private byte[] setEncodedValue() throws IOException {
/* 151 */     BerEncoder berEncoder = new BerEncoder(32);
/*     */     
/* 153 */     berEncoder.beginSeq(48);
/* 154 */     berEncoder.encodeInt(this.changeTypes);
/* 155 */     berEncoder.encodeBoolean(this.changesOnly);
/* 156 */     berEncoder.encodeBoolean(this.returnControls);
/* 157 */     berEncoder.endSeq();
/*     */     
/* 159 */     return berEncoder.getTrimmedBuf();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/PersistentSearchControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */