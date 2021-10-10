/*     */ package sun.security.provider;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.security.AlgorithmParametersSpi;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.security.spec.DSAParameterSpec;
/*     */ import java.security.spec.InvalidParameterSpecException;
/*     */ import sun.security.util.Debug;
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
/*     */ public class DSAParameters
/*     */   extends AlgorithmParametersSpi
/*     */ {
/*     */   protected BigInteger p;
/*     */   protected BigInteger q;
/*     */   protected BigInteger g;
/*     */   
/*     */   protected void engineInit(AlgorithmParameterSpec paramAlgorithmParameterSpec) throws InvalidParameterSpecException {
/*  63 */     if (!(paramAlgorithmParameterSpec instanceof DSAParameterSpec)) {
/*  64 */       throw new InvalidParameterSpecException("Inappropriate parameter specification");
/*     */     }
/*     */     
/*  67 */     this.p = ((DSAParameterSpec)paramAlgorithmParameterSpec).getP();
/*  68 */     this.q = ((DSAParameterSpec)paramAlgorithmParameterSpec).getQ();
/*  69 */     this.g = ((DSAParameterSpec)paramAlgorithmParameterSpec).getG();
/*     */   }
/*     */   
/*     */   protected void engineInit(byte[] paramArrayOfbyte) throws IOException {
/*  73 */     DerValue derValue = new DerValue(paramArrayOfbyte);
/*     */     
/*  75 */     if (derValue.tag != 48) {
/*  76 */       throw new IOException("DSA params parsing error");
/*     */     }
/*     */     
/*  79 */     derValue.data.reset();
/*     */     
/*  81 */     this.p = derValue.data.getBigInteger();
/*  82 */     this.q = derValue.data.getBigInteger();
/*  83 */     this.g = derValue.data.getBigInteger();
/*     */     
/*  85 */     if (derValue.data.available() != 0) {
/*  86 */       throw new IOException("encoded params have " + derValue.data
/*  87 */           .available() + " extra bytes");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineInit(byte[] paramArrayOfbyte, String paramString) throws IOException {
/*  94 */     engineInit(paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected <T extends AlgorithmParameterSpec> T engineGetParameterSpec(Class<T> paramClass) throws InvalidParameterSpecException {
/*     */     try {
/* 103 */       Class<?> clazz = Class.forName("java.security.spec.DSAParameterSpec");
/* 104 */       if (clazz.isAssignableFrom(paramClass)) {
/* 105 */         return paramClass.cast(new DSAParameterSpec(this.p, this.q, this.g));
/*     */       }
/*     */       
/* 108 */       throw new InvalidParameterSpecException("Inappropriate parameter Specification");
/*     */     
/*     */     }
/* 111 */     catch (ClassNotFoundException classNotFoundException) {
/* 112 */       throw new InvalidParameterSpecException("Unsupported parameter specification: " + classNotFoundException
/* 113 */           .getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected byte[] engineGetEncoded() throws IOException {
/* 118 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 119 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/*     */     
/* 121 */     derOutputStream2.putInteger(this.p);
/* 122 */     derOutputStream2.putInteger(this.q);
/* 123 */     derOutputStream2.putInteger(this.g);
/* 124 */     derOutputStream1.write((byte)48, derOutputStream2);
/* 125 */     return derOutputStream1.toByteArray();
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte[] engineGetEncoded(String paramString) throws IOException {
/* 130 */     return engineGetEncoded();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String engineToString() {
/* 137 */     return "\n\tp: " + Debug.toHexString(this.p) + "\n\tq: " + 
/* 138 */       Debug.toHexString(this.q) + "\n\tg: " + 
/* 139 */       Debug.toHexString(this.g) + "\n";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/DSAParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */