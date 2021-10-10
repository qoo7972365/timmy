/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Vector;
/*     */ import sun.misc.HexDumpEncoder;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.internal.crypto.EType;
/*     */ import sun.security.krb5.internal.util.KerberosString;
/*     */ import sun.security.util.DerInputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PAData
/*     */ {
/*     */   private int pADataType;
/*  63 */   private byte[] pADataValue = null;
/*     */   
/*     */   private static final byte TAG_PATYPE = 1;
/*     */   private static final byte TAG_PAVALUE = 2;
/*     */   
/*     */   private PAData() {}
/*     */   
/*     */   public PAData(int paramInt, byte[] paramArrayOfbyte) {
/*  71 */     this.pADataType = paramInt;
/*  72 */     if (paramArrayOfbyte != null) {
/*  73 */       this.pADataValue = (byte[])paramArrayOfbyte.clone();
/*     */     }
/*     */   }
/*     */   
/*     */   public Object clone() {
/*  78 */     PAData pAData = new PAData();
/*  79 */     pAData.pADataType = this.pADataType;
/*  80 */     if (this.pADataValue != null) {
/*  81 */       pAData.pADataValue = new byte[this.pADataValue.length];
/*  82 */       System.arraycopy(this.pADataValue, 0, pAData.pADataValue, 0, this.pADataValue.length);
/*     */     } 
/*     */     
/*  85 */     return pAData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PAData(DerValue paramDerValue) throws Asn1Exception, IOException {
/*  95 */     DerValue derValue = null;
/*  96 */     if (paramDerValue.getTag() != 48) {
/*  97 */       throw new Asn1Exception(906);
/*     */     }
/*  99 */     derValue = paramDerValue.getData().getDerValue();
/* 100 */     if ((derValue.getTag() & 0x1F) == 1) {
/* 101 */       this.pADataType = derValue.getData().getBigInteger().intValue();
/*     */     } else {
/*     */       
/* 104 */       throw new Asn1Exception(906);
/* 105 */     }  derValue = paramDerValue.getData().getDerValue();
/* 106 */     if ((derValue.getTag() & 0x1F) == 2) {
/* 107 */       this.pADataValue = derValue.getData().getOctetString();
/*     */     }
/* 109 */     if (paramDerValue.getData().available() > 0) {
/* 110 */       throw new Asn1Exception(906);
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
/*     */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 122 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 123 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/*     */     
/* 125 */     derOutputStream2.putInteger(this.pADataType);
/* 126 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)1), derOutputStream2);
/* 127 */     derOutputStream2 = new DerOutputStream();
/* 128 */     derOutputStream2.putOctetString(this.pADataValue);
/* 129 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)2), derOutputStream2);
/*     */     
/* 131 */     derOutputStream2 = new DerOutputStream();
/* 132 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 133 */     return derOutputStream2.toByteArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getType() {
/* 138 */     return this.pADataType;
/*     */   }
/*     */   
/*     */   public byte[] getValue() {
/* 142 */     return (this.pADataValue == null) ? null : (byte[])this.pADataValue.clone();
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
/*     */   public static PAData[] parseSequence(DerInputStream paramDerInputStream, byte paramByte, boolean paramBoolean) throws Asn1Exception, IOException {
/* 160 */     if (paramBoolean && (
/* 161 */       (byte)paramDerInputStream.peekByte() & 0x1F) != paramByte)
/* 162 */       return null; 
/* 163 */     DerValue derValue1 = paramDerInputStream.getDerValue();
/* 164 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 165 */     if (derValue2.getTag() != 48) {
/* 166 */       throw new Asn1Exception(906);
/*     */     }
/* 168 */     Vector<PAData> vector = new Vector();
/* 169 */     while (derValue2.getData().available() > 0) {
/* 170 */       vector.addElement(new PAData(derValue2.getData().getDerValue()));
/*     */     }
/* 172 */     if (vector.size() > 0) {
/* 173 */       PAData[] arrayOfPAData = new PAData[vector.size()];
/* 174 */       vector.copyInto((Object[])arrayOfPAData);
/* 175 */       return arrayOfPAData;
/*     */     } 
/* 177 */     return null;
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
/*     */   public static int getPreferredEType(PAData[] paramArrayOfPAData, int paramInt) throws IOException, Asn1Exception {
/* 193 */     if (paramArrayOfPAData == null) return paramInt;
/*     */     
/* 195 */     DerValue derValue1 = null, derValue2 = null;
/* 196 */     for (PAData pAData : paramArrayOfPAData) {
/* 197 */       if (pAData.getValue() != null)
/* 198 */         switch (pAData.getType()) {
/*     */           case 11:
/* 200 */             derValue1 = new DerValue(pAData.getValue());
/*     */             break;
/*     */           case 19:
/* 203 */             derValue2 = new DerValue(pAData.getValue());
/*     */             break;
/*     */         }  
/*     */     } 
/* 207 */     if (derValue2 != null) {
/* 208 */       while (derValue2.data.available() > 0) {
/* 209 */         DerValue derValue = derValue2.data.getDerValue();
/* 210 */         ETypeInfo2 eTypeInfo2 = new ETypeInfo2(derValue);
/* 211 */         if (EType.isNewer(eTypeInfo2.getEType()) || eTypeInfo2.getParams() == null)
/*     */         {
/* 213 */           return eTypeInfo2.getEType();
/*     */         }
/*     */       } 
/*     */     }
/* 217 */     if (derValue1 != null && 
/* 218 */       derValue1.data.available() > 0) {
/* 219 */       DerValue derValue = derValue1.data.getDerValue();
/* 220 */       ETypeInfo eTypeInfo = new ETypeInfo(derValue);
/* 221 */       return eTypeInfo.getEType();
/*     */     } 
/*     */     
/* 224 */     return paramInt;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class SaltAndParams
/*     */   {
/*     */     public final String salt;
/*     */     
/*     */     public final byte[] params;
/*     */ 
/*     */     
/*     */     public SaltAndParams(String param1String, byte[] param1ArrayOfbyte) {
/* 236 */       if (param1String != null && param1String.isEmpty()) param1String = null; 
/* 237 */       this.salt = param1String;
/* 238 */       this.params = param1ArrayOfbyte;
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
/*     */   public static SaltAndParams getSaltAndParams(int paramInt, PAData[] paramArrayOfPAData) throws Asn1Exception, IOException {
/* 254 */     if (paramArrayOfPAData == null) return null;
/*     */     
/* 256 */     DerValue derValue1 = null, derValue2 = null;
/* 257 */     String str = null;
/*     */     
/* 259 */     for (PAData pAData : paramArrayOfPAData) {
/* 260 */       if (pAData.getValue() != null)
/* 261 */         switch (pAData.getType()) {
/*     */           case 3:
/* 263 */             str = new String(pAData.getValue(), KerberosString.MSNAME ? "UTF8" : "8859_1");
/*     */             break;
/*     */           
/*     */           case 11:
/* 267 */             derValue1 = new DerValue(pAData.getValue());
/*     */             break;
/*     */           case 19:
/* 270 */             derValue2 = new DerValue(pAData.getValue());
/*     */             break;
/*     */         }  
/*     */     } 
/* 274 */     if (derValue2 != null) {
/* 275 */       while (derValue2.data.available() > 0) {
/* 276 */         DerValue derValue = derValue2.data.getDerValue();
/* 277 */         ETypeInfo2 eTypeInfo2 = new ETypeInfo2(derValue);
/* 278 */         if (eTypeInfo2.getEType() == paramInt && (
/* 279 */           EType.isNewer(paramInt) || eTypeInfo2.getParams() == null))
/*     */         {
/* 281 */           return new SaltAndParams(eTypeInfo2.getSalt(), eTypeInfo2.getParams());
/*     */         }
/*     */       } 
/*     */     }
/* 285 */     if (derValue1 != null) {
/* 286 */       while (derValue1.data.available() > 0) {
/* 287 */         DerValue derValue = derValue1.data.getDerValue();
/* 288 */         ETypeInfo eTypeInfo = new ETypeInfo(derValue);
/* 289 */         if (eTypeInfo.getEType() == paramInt) {
/* 290 */           return new SaltAndParams(eTypeInfo.getSalt(), null);
/*     */         }
/*     */       } 
/*     */     }
/* 294 */     if (str != null) {
/* 295 */       return new SaltAndParams(str, null);
/*     */     }
/* 297 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 302 */     StringBuilder stringBuilder = new StringBuilder();
/* 303 */     stringBuilder.append(">>>Pre-Authentication Data:\n\t PA-DATA type = ")
/* 304 */       .append(this.pADataType).append('\n');
/*     */     
/* 306 */     switch (this.pADataType) {
/*     */       case 2:
/* 308 */         stringBuilder.append("\t PA-ENC-TIMESTAMP");
/*     */         break;
/*     */       case 11:
/* 311 */         if (this.pADataValue != null) {
/*     */           try {
/* 313 */             DerValue derValue = new DerValue(this.pADataValue);
/* 314 */             while (derValue.data.available() > 0) {
/* 315 */               DerValue derValue1 = derValue.data.getDerValue();
/* 316 */               ETypeInfo eTypeInfo = new ETypeInfo(derValue1);
/* 317 */               stringBuilder.append("\t PA-ETYPE-INFO etype = ")
/* 318 */                 .append(eTypeInfo.getEType())
/* 319 */                 .append(", salt = ")
/* 320 */                 .append(eTypeInfo.getSalt())
/* 321 */                 .append('\n');
/*     */             } 
/* 323 */           } catch (IOException|Asn1Exception iOException) {
/* 324 */             stringBuilder.append("\t <Unparseable PA-ETYPE-INFO>\n");
/*     */           } 
/*     */         }
/*     */         break;
/*     */       case 19:
/* 329 */         if (this.pADataValue != null) {
/*     */           try {
/* 331 */             DerValue derValue = new DerValue(this.pADataValue);
/* 332 */             while (derValue.data.available() > 0) {
/* 333 */               DerValue derValue1 = derValue.data.getDerValue();
/* 334 */               ETypeInfo2 eTypeInfo2 = new ETypeInfo2(derValue1);
/* 335 */               stringBuilder.append("\t PA-ETYPE-INFO2 etype = ")
/* 336 */                 .append(eTypeInfo2.getEType())
/* 337 */                 .append(", salt = ")
/* 338 */                 .append(eTypeInfo2.getSalt())
/* 339 */                 .append(", s2kparams = ");
/* 340 */               byte[] arrayOfByte = eTypeInfo2.getParams();
/* 341 */               if (arrayOfByte == null) {
/* 342 */                 stringBuilder.append("null\n"); continue;
/* 343 */               }  if (arrayOfByte.length == 0) {
/* 344 */                 stringBuilder.append("empty\n"); continue;
/*     */               } 
/* 346 */               stringBuilder.append((new HexDumpEncoder())
/* 347 */                   .encodeBuffer(arrayOfByte));
/*     */             }
/*     */           
/* 350 */           } catch (IOException|Asn1Exception iOException) {
/* 351 */             stringBuilder.append("\t <Unparseable PA-ETYPE-INFO>\n");
/*     */           } 
/*     */         }
/*     */         break;
/*     */       case 129:
/* 356 */         stringBuilder.append("\t PA-FOR-USER\n");
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 362 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/PAData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */