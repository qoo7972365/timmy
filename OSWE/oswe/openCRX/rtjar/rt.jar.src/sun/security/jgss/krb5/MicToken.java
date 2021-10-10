/*     */ package sun.security.jgss.krb5;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.MessageProp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MicToken
/*     */   extends MessageToken
/*     */ {
/*     */   public MicToken(Krb5Context paramKrb5Context, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, MessageProp paramMessageProp) throws GSSException {
/*  40 */     super(257, paramKrb5Context, paramArrayOfbyte, paramInt1, paramInt2, paramMessageProp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MicToken(Krb5Context paramKrb5Context, InputStream paramInputStream, MessageProp paramMessageProp) throws GSSException {
/*  47 */     super(257, paramKrb5Context, paramInputStream, paramMessageProp);
/*     */   }
/*     */   
/*     */   public void verify(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws GSSException {
/*  51 */     if (!verifySignAndSeqNumber(null, paramArrayOfbyte, paramInt1, paramInt2, null)) {
/*  52 */       throw new GSSException(6, -1, "Corrupt checksum or sequence number in MIC token");
/*     */     }
/*     */   }
/*     */   
/*     */   public void verify(InputStream paramInputStream) throws GSSException {
/*  57 */     byte[] arrayOfByte = null;
/*     */     try {
/*  59 */       arrayOfByte = new byte[paramInputStream.available()];
/*  60 */       paramInputStream.read(arrayOfByte);
/*  61 */     } catch (IOException iOException) {
/*     */       
/*  63 */       throw new GSSException(6, -1, "Corrupt checksum or sequence number in MIC token");
/*     */     } 
/*     */     
/*  66 */     verify(arrayOfByte, 0, arrayOfByte.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MicToken(Krb5Context paramKrb5Context, MessageProp paramMessageProp, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws GSSException {
/*  72 */     super(257, paramKrb5Context);
/*     */ 
/*     */ 
/*     */     
/*  76 */     if (paramMessageProp == null) paramMessageProp = new MessageProp(0, false); 
/*  77 */     genSignAndSeqNumber(paramMessageProp, null, paramArrayOfbyte, paramInt1, paramInt2, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MicToken(Krb5Context paramKrb5Context, MessageProp paramMessageProp, InputStream paramInputStream) throws GSSException, IOException {
/*  83 */     super(257, paramKrb5Context);
/*  84 */     byte[] arrayOfByte = new byte[paramInputStream.available()];
/*  85 */     paramInputStream.read(arrayOfByte);
/*     */ 
/*     */ 
/*     */     
/*  89 */     if (paramMessageProp == null) paramMessageProp = new MessageProp(0, false); 
/*  90 */     genSignAndSeqNumber(paramMessageProp, null, arrayOfByte, 0, arrayOfByte.length, null);
/*     */   }
/*     */   
/*     */   protected int getSealAlg(boolean paramBoolean, int paramInt) {
/*  94 */     return 65535;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int encode(byte[] paramArrayOfbyte, int paramInt) throws IOException, GSSException {
/* 100 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 101 */     encode(byteArrayOutputStream);
/* 102 */     byte[] arrayOfByte = byteArrayOutputStream.toByteArray();
/* 103 */     System.arraycopy(arrayOfByte, 0, paramArrayOfbyte, paramInt, arrayOfByte.length);
/* 104 */     return arrayOfByte.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] encode() throws IOException, GSSException {
/* 109 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(50);
/* 110 */     encode(byteArrayOutputStream);
/* 111 */     return byteArrayOutputStream.toByteArray();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/krb5/MicToken.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */