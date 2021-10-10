/*     */ package sun.security.jgss.krb5;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Arrays;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.MessageProp;
/*     */ import sun.security.jgss.GSSHeader;
/*     */ import sun.security.krb5.Confounder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WrapToken_v2
/*     */   extends MessageToken_v2
/*     */ {
/*  51 */   byte[] confounder = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean privacy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WrapToken_v2(Krb5Context paramKrb5Context, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, MessageProp paramMessageProp) throws GSSException {
/*  71 */     super(1284, paramKrb5Context, paramArrayOfbyte, paramInt1, paramInt2, paramMessageProp);
/*     */     
/*  73 */     this.privacy = paramMessageProp.getPrivacy();
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
/*     */   public WrapToken_v2(Krb5Context paramKrb5Context, InputStream paramInputStream, MessageProp paramMessageProp) throws GSSException {
/*  91 */     super(1284, paramKrb5Context, paramInputStream, paramMessageProp);
/*  92 */     this.privacy = paramMessageProp.getPrivacy();
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
/*     */   public byte[] getData() throws GSSException {
/* 104 */     byte[] arrayOfByte = new byte[this.tokenDataLen];
/* 105 */     int i = getData(arrayOfByte, 0);
/* 106 */     return Arrays.copyOf(arrayOfByte, i);
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
/*     */   public int getData(byte[] paramArrayOfbyte, int paramInt) throws GSSException {
/* 127 */     if (this.privacy) {
/*     */ 
/*     */       
/* 130 */       this.cipherHelper.decryptData(this, this.tokenData, 0, this.tokenDataLen, paramArrayOfbyte, paramInt, 
/* 131 */           getKeyUsage());
/*     */       
/* 133 */       return this.tokenDataLen - 16 - 16 - this.cipherHelper
/* 134 */         .getChecksumLength();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     int i = this.tokenDataLen - this.cipherHelper.getChecksumLength();
/* 142 */     System.arraycopy(this.tokenData, 0, paramArrayOfbyte, paramInt, i);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 150 */     if (!verifySign(paramArrayOfbyte, paramInt, i)) {
/* 151 */       throw new GSSException(6, -1, "Corrupt checksum in Wrap token");
/*     */     }
/*     */     
/* 154 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WrapToken_v2(Krb5Context paramKrb5Context, MessageProp paramMessageProp, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws GSSException {
/* 165 */     super(1284, paramKrb5Context);
/*     */     
/* 167 */     this.confounder = Confounder.bytes(16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 173 */     genSignAndSeqNumber(paramMessageProp, paramArrayOfbyte, paramInt1, paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 181 */     if (!paramKrb5Context.getConfState()) {
/* 182 */       paramMessageProp.setPrivacy(false);
/*     */     }
/* 184 */     this.privacy = paramMessageProp.getPrivacy();
/*     */     
/* 186 */     if (!this.privacy) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 191 */       this.tokenData = new byte[paramInt2 + this.checksum.length];
/* 192 */       System.arraycopy(paramArrayOfbyte, paramInt1, this.tokenData, 0, paramInt2);
/* 193 */       System.arraycopy(this.checksum, 0, this.tokenData, paramInt2, this.checksum.length);
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 200 */       this.tokenData = this.cipherHelper.encryptData(this, this.confounder, getTokenHeader(), paramArrayOfbyte, paramInt1, paramInt2, 
/* 201 */           getKeyUsage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void encode(OutputStream paramOutputStream) throws IOException {
/* 206 */     encodeHeader(paramOutputStream);
/* 207 */     paramOutputStream.write(this.tokenData);
/*     */   }
/*     */   
/*     */   public byte[] encode() throws IOException {
/* 211 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(16 + this.tokenData.length);
/*     */     
/* 213 */     encode(byteArrayOutputStream);
/* 214 */     return byteArrayOutputStream.toByteArray();
/*     */   }
/*     */   
/*     */   public int encode(byte[] paramArrayOfbyte, int paramInt) throws IOException {
/* 218 */     byte[] arrayOfByte = encode();
/* 219 */     System.arraycopy(arrayOfByte, 0, paramArrayOfbyte, paramInt, arrayOfByte.length);
/* 220 */     return arrayOfByte.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getSizeLimit(int paramInt1, boolean paramBoolean, int paramInt2, CipherHelper paramCipherHelper) throws GSSException {
/* 227 */     return GSSHeader.getMaxMechTokenSize(OID, paramInt2) - 16 + paramCipherHelper
/* 228 */       .getChecksumLength() + 16 - 8;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/krb5/WrapToken_v2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */