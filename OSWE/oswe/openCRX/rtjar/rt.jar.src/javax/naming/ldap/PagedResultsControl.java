/*     */ package javax.naming.ldap;
/*     */ 
/*     */ import com.sun.jndi.ldap.BerEncoder;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PagedResultsControl
/*     */   extends BasicControl
/*     */ {
/*     */   public static final String OID = "1.2.840.113556.1.4.319";
/* 121 */   private static final byte[] EMPTY_COOKIE = new byte[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 6684806685736844298L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PagedResultsControl(int paramInt, boolean paramBoolean) throws IOException {
/* 141 */     super("1.2.840.113556.1.4.319", paramBoolean, null);
/* 142 */     this.value = setEncodedValue(paramInt, EMPTY_COOKIE);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PagedResultsControl(int paramInt, byte[] paramArrayOfbyte, boolean paramBoolean) throws IOException {
/* 167 */     super("1.2.840.113556.1.4.319", paramBoolean, null);
/* 168 */     if (paramArrayOfbyte == null) {
/* 169 */       paramArrayOfbyte = EMPTY_COOKIE;
/*     */     }
/* 171 */     this.value = setEncodedValue(paramInt, paramArrayOfbyte);
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
/*     */   private byte[] setEncodedValue(int paramInt, byte[] paramArrayOfbyte) throws IOException {
/* 189 */     BerEncoder berEncoder = new BerEncoder(10 + paramArrayOfbyte.length);
/*     */     
/* 191 */     berEncoder.beginSeq(48);
/* 192 */     berEncoder.encodeInt(paramInt);
/* 193 */     berEncoder.encodeOctetString(paramArrayOfbyte, 4);
/* 194 */     berEncoder.endSeq();
/*     */     
/* 196 */     return berEncoder.getTrimmedBuf();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/naming/ldap/PagedResultsControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */