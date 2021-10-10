/*     */ package sun.security.pkcs10;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.math.BigInteger;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PublicKey;
/*     */ import java.security.Signature;
/*     */ import java.security.SignatureException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Base64;
/*     */ import sun.security.util.DerInputStream;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
/*     */ import sun.security.x509.AlgorithmId;
/*     */ import sun.security.x509.X500Name;
/*     */ import sun.security.x509.X509Key;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PKCS10
/*     */ {
/*     */   private X500Name subject;
/*     */   private PublicKey subjectPublicKeyInfo;
/*     */   private String sigAlg;
/*     */   private PKCS10Attributes attributeSet;
/*     */   private byte[] encoded;
/*     */   
/*     */   public PKCS10(PublicKey paramPublicKey) {
/*  88 */     this.subjectPublicKeyInfo = paramPublicKey;
/*  89 */     this.attributeSet = new PKCS10Attributes();
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
/*     */   public PKCS10(PublicKey paramPublicKey, PKCS10Attributes paramPKCS10Attributes) {
/* 103 */     this.subjectPublicKeyInfo = paramPublicKey;
/* 104 */     this.attributeSet = paramPKCS10Attributes;
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
/*     */   public PKCS10(byte[] paramArrayOfbyte) throws IOException, SignatureException, NoSuchAlgorithmException {
/* 127 */     this.encoded = paramArrayOfbyte;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 133 */     DerInputStream derInputStream = new DerInputStream(paramArrayOfbyte);
/* 134 */     DerValue[] arrayOfDerValue = derInputStream.getSequence(3);
/*     */     
/* 136 */     if (arrayOfDerValue.length != 3) {
/* 137 */       throw new IllegalArgumentException("not a PKCS #10 request");
/*     */     }
/* 139 */     paramArrayOfbyte = arrayOfDerValue[0].toByteArray();
/* 140 */     AlgorithmId algorithmId = AlgorithmId.parse(arrayOfDerValue[1]);
/* 141 */     byte[] arrayOfByte = arrayOfDerValue[2].getBitString();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 149 */     BigInteger bigInteger = (arrayOfDerValue[0]).data.getBigInteger();
/* 150 */     if (!bigInteger.equals(BigInteger.ZERO)) {
/* 151 */       throw new IllegalArgumentException("not PKCS #10 v1");
/*     */     }
/* 153 */     this.subject = new X500Name((arrayOfDerValue[0]).data);
/* 154 */     this.subjectPublicKeyInfo = X509Key.parse((arrayOfDerValue[0]).data.getDerValue());
/*     */ 
/*     */     
/* 157 */     if ((arrayOfDerValue[0]).data.available() != 0) {
/* 158 */       this.attributeSet = new PKCS10Attributes((arrayOfDerValue[0]).data);
/*     */     } else {
/* 160 */       this.attributeSet = new PKCS10Attributes();
/*     */     } 
/* 162 */     if ((arrayOfDerValue[0]).data.available() != 0) {
/* 163 */       throw new IllegalArgumentException("illegal PKCS #10 data");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 170 */       this.sigAlg = algorithmId.getName();
/* 171 */       Signature signature = Signature.getInstance(this.sigAlg);
/* 172 */       signature.initVerify(this.subjectPublicKeyInfo);
/* 173 */       signature.update(paramArrayOfbyte);
/* 174 */       if (!signature.verify(arrayOfByte))
/* 175 */         throw new SignatureException("Invalid PKCS #10 signature"); 
/* 176 */     } catch (InvalidKeyException invalidKeyException) {
/* 177 */       throw new SignatureException("invalid key");
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void encodeAndSign(X500Name paramX500Name, Signature paramSignature) throws CertificateException, IOException, SignatureException {
/* 197 */     if (this.encoded != null) {
/* 198 */       throw new SignatureException("request is already signed");
/*     */     }
/* 200 */     this.subject = paramX500Name;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 205 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 206 */     derOutputStream2.putInteger(BigInteger.ZERO);
/* 207 */     paramX500Name.encode(derOutputStream2);
/* 208 */     derOutputStream2.write(this.subjectPublicKeyInfo.getEncoded());
/* 209 */     this.attributeSet.encode(derOutputStream2);
/*     */     
/* 211 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 212 */     derOutputStream1.write((byte)48, derOutputStream2);
/* 213 */     byte[] arrayOfByte1 = derOutputStream1.toByteArray();
/* 214 */     derOutputStream2 = derOutputStream1;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 219 */     paramSignature.update(arrayOfByte1, 0, arrayOfByte1.length);
/*     */     
/* 221 */     byte[] arrayOfByte2 = paramSignature.sign();
/* 222 */     this.sigAlg = paramSignature.getAlgorithm();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 227 */     AlgorithmId algorithmId = null;
/*     */     try {
/* 229 */       algorithmId = AlgorithmId.get(paramSignature.getAlgorithm());
/* 230 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 231 */       throw new SignatureException(noSuchAlgorithmException);
/*     */     } 
/* 233 */     algorithmId.encode(derOutputStream2);
/* 234 */     derOutputStream2.putBitString(arrayOfByte2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 239 */     derOutputStream1 = new DerOutputStream();
/* 240 */     derOutputStream1.write((byte)48, derOutputStream2);
/* 241 */     this.encoded = derOutputStream1.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public X500Name getSubjectName() {
/* 247 */     return this.subject;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PublicKey getSubjectPublicKeyInfo() {
/* 253 */     return this.subjectPublicKeyInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSigAlg() {
/* 258 */     return this.sigAlg;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PKCS10Attributes getAttributes() {
/* 264 */     return this.attributeSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getEncoded() {
/* 274 */     if (this.encoded != null) {
/* 275 */       return (byte[])this.encoded.clone();
/*     */     }
/* 277 */     return null;
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
/*     */   public void print(PrintStream paramPrintStream) throws IOException, SignatureException {
/* 296 */     if (this.encoded == null) {
/* 297 */       throw new SignatureException("Cert request was not signed");
/*     */     }
/*     */     
/* 300 */     byte[] arrayOfByte = { 13, 10 };
/* 301 */     paramPrintStream.println("-----BEGIN NEW CERTIFICATE REQUEST-----");
/* 302 */     paramPrintStream.println(Base64.getMimeEncoder(64, arrayOfByte).encodeToString(this.encoded));
/* 303 */     paramPrintStream.println("-----END NEW CERTIFICATE REQUEST-----");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 310 */     return "[PKCS #10 certificate request:\n" + this.subjectPublicKeyInfo
/* 311 */       .toString() + " subject: <" + this.subject + ">\n attributes: " + this.attributeSet
/*     */       
/* 313 */       .toString() + "\n]";
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
/*     */   public boolean equals(Object paramObject) {
/* 329 */     if (this == paramObject)
/* 330 */       return true; 
/* 331 */     if (!(paramObject instanceof PKCS10))
/* 332 */       return false; 
/* 333 */     if (this.encoded == null)
/* 334 */       return false; 
/* 335 */     byte[] arrayOfByte = ((PKCS10)paramObject).getEncoded();
/* 336 */     if (arrayOfByte == null) {
/* 337 */       return false;
/*     */     }
/* 339 */     return Arrays.equals(this.encoded, arrayOfByte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 349 */     int i = 0;
/* 350 */     if (this.encoded != null)
/* 351 */       for (byte b = 1; b < this.encoded.length; b++)
/* 352 */         i += this.encoded[b] * b;  
/* 353 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/pkcs10/PKCS10.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */