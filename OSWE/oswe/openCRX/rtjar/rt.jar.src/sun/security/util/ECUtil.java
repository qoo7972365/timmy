/*     */ package sun.security.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.security.AlgorithmParameters;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.Provider;
/*     */ import java.security.spec.ECGenParameterSpec;
/*     */ import java.security.spec.ECParameterSpec;
/*     */ import java.security.spec.ECPoint;
/*     */ import java.security.spec.EllipticCurve;
/*     */ import java.security.spec.InvalidParameterSpecException;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ECUtil
/*     */ {
/*     */   public static ECPoint decodePoint(byte[] paramArrayOfbyte, EllipticCurve paramEllipticCurve) throws IOException {
/*  47 */     if (paramArrayOfbyte.length == 0 || paramArrayOfbyte[0] != 4) {
/*  48 */       throw new IOException("Only uncompressed point format supported");
/*     */     }
/*     */ 
/*     */     
/*  52 */     int i = (paramArrayOfbyte.length - 1) / 2;
/*  53 */     if (i != paramEllipticCurve.getField().getFieldSize() + 7 >> 3) {
/*  54 */       throw new IOException("Point does not match field size");
/*     */     }
/*     */     
/*  57 */     byte[] arrayOfByte1 = Arrays.copyOfRange(paramArrayOfbyte, 1, 1 + i);
/*  58 */     byte[] arrayOfByte2 = Arrays.copyOfRange(paramArrayOfbyte, i + 1, i + 1 + i);
/*     */     
/*  60 */     return new ECPoint(new BigInteger(1, arrayOfByte1), new BigInteger(1, arrayOfByte2));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] encodePoint(ECPoint paramECPoint, EllipticCurve paramEllipticCurve) {
/*  66 */     int i = paramEllipticCurve.getField().getFieldSize() + 7 >> 3;
/*  67 */     byte[] arrayOfByte1 = trimZeroes(paramECPoint.getAffineX().toByteArray());
/*  68 */     byte[] arrayOfByte2 = trimZeroes(paramECPoint.getAffineY().toByteArray());
/*  69 */     if (arrayOfByte1.length > i || arrayOfByte2.length > i) {
/*  70 */       throw new RuntimeException("Point coordinates do not match field size");
/*     */     }
/*     */     
/*  73 */     byte[] arrayOfByte3 = new byte[1 + (i << 1)];
/*  74 */     arrayOfByte3[0] = 4;
/*  75 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte3, i - arrayOfByte1.length + 1, arrayOfByte1.length);
/*  76 */     System.arraycopy(arrayOfByte2, 0, arrayOfByte3, arrayOfByte3.length - arrayOfByte2.length, arrayOfByte2.length);
/*  77 */     return arrayOfByte3;
/*     */   }
/*     */   
/*     */   public static byte[] trimZeroes(byte[] paramArrayOfbyte) {
/*  81 */     byte b = 0;
/*  82 */     while (b < paramArrayOfbyte.length - 1 && paramArrayOfbyte[b] == 0) {
/*  83 */       b++;
/*     */     }
/*  85 */     if (b == 0) {
/*  86 */       return paramArrayOfbyte;
/*     */     }
/*     */     
/*  89 */     return Arrays.copyOfRange(paramArrayOfbyte, b, paramArrayOfbyte.length);
/*     */   }
/*     */   
/*     */   public static AlgorithmParameters getECParameters(Provider paramProvider) {
/*     */     try {
/*  94 */       if (paramProvider != null) {
/*  95 */         return AlgorithmParameters.getInstance("EC", paramProvider);
/*     */       }
/*     */       
/*  98 */       return AlgorithmParameters.getInstance("EC");
/*  99 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 100 */       throw new RuntimeException(noSuchAlgorithmException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] encodeECParameterSpec(Provider paramProvider, ECParameterSpec paramECParameterSpec) {
/* 106 */     AlgorithmParameters algorithmParameters = getECParameters(paramProvider);
/*     */     
/*     */     try {
/* 109 */       algorithmParameters.init(paramECParameterSpec);
/* 110 */     } catch (InvalidParameterSpecException invalidParameterSpecException) {
/* 111 */       throw new RuntimeException("Not a known named curve: " + paramECParameterSpec);
/*     */     } 
/*     */     
/*     */     try {
/* 115 */       return algorithmParameters.getEncoded();
/* 116 */     } catch (IOException iOException) {
/*     */       
/* 118 */       throw new RuntimeException(iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static ECParameterSpec getECParameterSpec(Provider paramProvider, ECParameterSpec paramECParameterSpec) {
/* 124 */     AlgorithmParameters algorithmParameters = getECParameters(paramProvider);
/*     */     
/*     */     try {
/* 127 */       algorithmParameters.init(paramECParameterSpec);
/* 128 */       return algorithmParameters.<ECParameterSpec>getParameterSpec(ECParameterSpec.class);
/* 129 */     } catch (InvalidParameterSpecException invalidParameterSpecException) {
/* 130 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ECParameterSpec getECParameterSpec(Provider paramProvider, byte[] paramArrayOfbyte) throws IOException {
/* 137 */     AlgorithmParameters algorithmParameters = getECParameters(paramProvider);
/*     */     
/* 139 */     algorithmParameters.init(paramArrayOfbyte);
/*     */     
/*     */     try {
/* 142 */       return algorithmParameters.<ECParameterSpec>getParameterSpec(ECParameterSpec.class);
/* 143 */     } catch (InvalidParameterSpecException invalidParameterSpecException) {
/* 144 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ECParameterSpec getECParameterSpec(Provider paramProvider, String paramString) {
/* 149 */     AlgorithmParameters algorithmParameters = getECParameters(paramProvider);
/*     */     
/*     */     try {
/* 152 */       algorithmParameters.init(new ECGenParameterSpec(paramString));
/* 153 */       return algorithmParameters.<ECParameterSpec>getParameterSpec(ECParameterSpec.class);
/* 154 */     } catch (InvalidParameterSpecException invalidParameterSpecException) {
/* 155 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ECParameterSpec getECParameterSpec(Provider paramProvider, int paramInt) {
/* 160 */     AlgorithmParameters algorithmParameters = getECParameters(paramProvider);
/*     */     
/*     */     try {
/* 163 */       algorithmParameters.init(new ECKeySizeParameterSpec(paramInt));
/* 164 */       return algorithmParameters.<ECParameterSpec>getParameterSpec(ECParameterSpec.class);
/* 165 */     } catch (InvalidParameterSpecException invalidParameterSpecException) {
/* 166 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getCurveName(Provider paramProvider, ECParameterSpec paramECParameterSpec) {
/*     */     ECGenParameterSpec eCGenParameterSpec;
/* 173 */     AlgorithmParameters algorithmParameters = getECParameters(paramProvider);
/*     */     
/*     */     try {
/* 176 */       algorithmParameters.init(paramECParameterSpec);
/* 177 */       eCGenParameterSpec = algorithmParameters.<ECGenParameterSpec>getParameterSpec(ECGenParameterSpec.class);
/* 178 */     } catch (InvalidParameterSpecException invalidParameterSpecException) {
/* 179 */       return null;
/*     */     } 
/*     */     
/* 182 */     if (eCGenParameterSpec == null) {
/* 183 */       return null;
/*     */     }
/*     */     
/* 186 */     return eCGenParameterSpec.getName();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/ECUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */