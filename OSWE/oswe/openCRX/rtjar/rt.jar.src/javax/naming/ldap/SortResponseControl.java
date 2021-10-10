/*     */ package javax.naming.ldap;
/*     */ 
/*     */ import com.sun.jndi.ldap.BerDecoder;
/*     */ import com.sun.jndi.ldap.LdapCtx;
/*     */ import java.io.IOException;
/*     */ import javax.naming.NamingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SortResponseControl
/*     */   extends BasicControl
/*     */ {
/*     */   public static final String OID = "1.2.840.113556.1.4.474";
/*     */   private static final long serialVersionUID = 5142939176006310877L;
/*  95 */   private int resultCode = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   private String badAttrId = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SortResponseControl(String paramString, boolean paramBoolean, byte[] paramArrayOfbyte) throws IOException {
/* 118 */     super(paramString, paramBoolean, paramArrayOfbyte);
/*     */ 
/*     */     
/* 121 */     BerDecoder berDecoder = new BerDecoder(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*     */     
/* 123 */     berDecoder.parseSeq(null);
/* 124 */     this.resultCode = berDecoder.parseEnumeration();
/* 125 */     if (berDecoder.bytesLeft() > 0 && berDecoder.peekByte() == 128) {
/* 126 */       this.badAttrId = berDecoder.parseStringWithTag(128, true, null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSorted() {
/* 137 */     return (this.resultCode == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getResultCode() {
/* 146 */     return this.resultCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttributeID() {
/* 156 */     return this.badAttrId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingException getException() {
/* 167 */     return LdapCtx.mapErrorCode(this.resultCode, null);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/naming/ldap/SortResponseControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */