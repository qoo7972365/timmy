/*     */ package javax.naming.ldap;
/*     */ 
/*     */ import com.sun.jndi.ldap.BerDecoder;
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
/*     */ 
/*     */ public final class PagedResultsResponseControl
/*     */   extends BasicControl
/*     */ {
/*     */   public static final String OID = "1.2.840.113556.1.4.319";
/*     */   private static final long serialVersionUID = -8819778744844514666L;
/*     */   private int resultSize;
/*     */   private byte[] cookie;
/*     */   
/*     */   public PagedResultsResponseControl(String paramString, boolean paramBoolean, byte[] paramArrayOfbyte) throws IOException {
/*  99 */     super(paramString, paramBoolean, paramArrayOfbyte);
/*     */ 
/*     */     
/* 102 */     BerDecoder berDecoder = new BerDecoder(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*     */     
/* 104 */     berDecoder.parseSeq(null);
/* 105 */     this.resultSize = berDecoder.parseInt();
/* 106 */     this.cookie = berDecoder.parseOctetString(4, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getResultSize() {
/* 115 */     return this.resultSize;
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
/*     */   public byte[] getCookie() {
/* 127 */     if (this.cookie.length == 0) {
/* 128 */       return null;
/*     */     }
/* 130 */     return this.cookie;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/naming/ldap/PagedResultsResponseControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */