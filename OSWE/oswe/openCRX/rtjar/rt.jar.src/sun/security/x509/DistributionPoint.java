/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import sun.security.util.BitArray;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DistributionPoint
/*     */ {
/*     */   public static final int KEY_COMPROMISE = 1;
/*     */   public static final int CA_COMPROMISE = 2;
/*     */   public static final int AFFILIATION_CHANGED = 3;
/*     */   public static final int SUPERSEDED = 4;
/*     */   public static final int CESSATION_OF_OPERATION = 5;
/*     */   public static final int CERTIFICATE_HOLD = 6;
/*     */   public static final int PRIVILEGE_WITHDRAWN = 7;
/*     */   public static final int AA_COMPROMISE = 8;
/* 109 */   private static final String[] REASON_STRINGS = new String[] { null, "key compromise", "CA compromise", "affiliation changed", "superseded", "cessation of operation", "certificate hold", "privilege withdrawn", "AA compromise" };
/*     */ 
/*     */ 
/*     */   
/*     */   private static final byte TAG_DIST_PT = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final byte TAG_REASONS = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final byte TAG_ISSUER = 2;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final byte TAG_FULL_NAME = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final byte TAG_REL_NAME = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   private GeneralNames fullName;
/*     */ 
/*     */ 
/*     */   
/*     */   private RDN relativeName;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean[] reasonFlags;
/*     */ 
/*     */ 
/*     */   
/*     */   private GeneralNames crlIssuer;
/*     */ 
/*     */   
/*     */   private volatile int hashCode;
/*     */ 
/*     */ 
/*     */   
/*     */   public DistributionPoint(GeneralNames paramGeneralNames1, boolean[] paramArrayOfboolean, GeneralNames paramGeneralNames2) {
/* 153 */     if (paramGeneralNames1 == null && paramGeneralNames2 == null) {
/* 154 */       throw new IllegalArgumentException("fullName and crlIssuer may not both be null");
/*     */     }
/*     */     
/* 157 */     this.fullName = paramGeneralNames1;
/* 158 */     this.reasonFlags = paramArrayOfboolean;
/* 159 */     this.crlIssuer = paramGeneralNames2;
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
/*     */   public DistributionPoint(RDN paramRDN, boolean[] paramArrayOfboolean, GeneralNames paramGeneralNames) {
/* 175 */     if (paramRDN == null && paramGeneralNames == null) {
/* 176 */       throw new IllegalArgumentException("relativeName and crlIssuer may not both be null");
/*     */     }
/*     */     
/* 179 */     this.relativeName = paramRDN;
/* 180 */     this.reasonFlags = paramArrayOfboolean;
/* 181 */     this.crlIssuer = paramGeneralNames;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DistributionPoint(DerValue paramDerValue) throws IOException {
/* 191 */     if (paramDerValue.tag != 48) {
/* 192 */       throw new IOException("Invalid encoding of DistributionPoint.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 198 */     while (paramDerValue.data != null && paramDerValue.data.available() != 0) {
/* 199 */       DerValue derValue = paramDerValue.data.getDerValue();
/*     */       
/* 201 */       if (derValue.isContextSpecific((byte)0) && derValue.isConstructed()) {
/* 202 */         if (this.fullName != null || this.relativeName != null) {
/* 203 */           throw new IOException("Duplicate DistributionPointName in DistributionPoint.");
/*     */         }
/*     */         
/* 206 */         DerValue derValue1 = derValue.data.getDerValue();
/* 207 */         if (derValue1.isContextSpecific((byte)0) && derValue1
/* 208 */           .isConstructed()) {
/* 209 */           derValue1.resetTag((byte)48);
/* 210 */           this.fullName = new GeneralNames(derValue1); continue;
/* 211 */         }  if (derValue1.isContextSpecific((byte)1) && derValue1
/* 212 */           .isConstructed()) {
/* 213 */           derValue1.resetTag((byte)49);
/* 214 */           this.relativeName = new RDN(derValue1); continue;
/*     */         } 
/* 216 */         throw new IOException("Invalid DistributionPointName in DistributionPoint");
/*     */       } 
/*     */       
/* 219 */       if (derValue.isContextSpecific((byte)1) && 
/* 220 */         !derValue.isConstructed()) {
/* 221 */         if (this.reasonFlags != null) {
/* 222 */           throw new IOException("Duplicate Reasons in DistributionPoint.");
/*     */         }
/*     */         
/* 225 */         derValue.resetTag((byte)3);
/* 226 */         this.reasonFlags = derValue.getUnalignedBitString().toBooleanArray(); continue;
/* 227 */       }  if (derValue.isContextSpecific((byte)2) && derValue
/* 228 */         .isConstructed()) {
/* 229 */         if (this.crlIssuer != null) {
/* 230 */           throw new IOException("Duplicate CRLIssuer in DistributionPoint.");
/*     */         }
/*     */         
/* 233 */         derValue.resetTag((byte)48);
/* 234 */         this.crlIssuer = new GeneralNames(derValue); continue;
/*     */       } 
/* 236 */       throw new IOException("Invalid encoding of DistributionPoint.");
/*     */     } 
/*     */ 
/*     */     
/* 240 */     if (this.crlIssuer == null && this.fullName == null && this.relativeName == null) {
/* 241 */       throw new IOException("One of fullName, relativeName,  and crlIssuer has to be set");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneralNames getFullName() {
/* 250 */     return this.fullName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RDN getRelativeName() {
/* 257 */     return this.relativeName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean[] getReasonFlags() {
/* 264 */     return this.reasonFlags;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneralNames getCRLIssuer() {
/* 271 */     return this.crlIssuer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(DerOutputStream paramDerOutputStream) throws IOException {
/* 281 */     DerOutputStream derOutputStream = new DerOutputStream();
/*     */ 
/*     */     
/* 284 */     if (this.fullName != null || this.relativeName != null) {
/* 285 */       DerOutputStream derOutputStream1 = new DerOutputStream();
/* 286 */       if (this.fullName != null) {
/* 287 */         DerOutputStream derOutputStream2 = new DerOutputStream();
/* 288 */         this.fullName.encode(derOutputStream2);
/* 289 */         derOutputStream1.writeImplicit(
/* 290 */             DerValue.createTag(-128, true, (byte)0), derOutputStream2);
/*     */       }
/* 292 */       else if (this.relativeName != null) {
/* 293 */         DerOutputStream derOutputStream2 = new DerOutputStream();
/* 294 */         this.relativeName.encode(derOutputStream2);
/* 295 */         derOutputStream1.writeImplicit(
/* 296 */             DerValue.createTag(-128, true, (byte)1), derOutputStream2);
/*     */       } 
/*     */       
/* 299 */       derOutputStream.write(
/* 300 */           DerValue.createTag(-128, true, (byte)0), derOutputStream1);
/*     */     } 
/*     */     
/* 303 */     if (this.reasonFlags != null) {
/* 304 */       DerOutputStream derOutputStream1 = new DerOutputStream();
/* 305 */       BitArray bitArray = new BitArray(this.reasonFlags);
/* 306 */       derOutputStream1.putTruncatedUnalignedBitString(bitArray);
/* 307 */       derOutputStream.writeImplicit(
/* 308 */           DerValue.createTag(-128, false, (byte)1), derOutputStream1);
/*     */     } 
/*     */     
/* 311 */     if (this.crlIssuer != null) {
/* 312 */       DerOutputStream derOutputStream1 = new DerOutputStream();
/* 313 */       this.crlIssuer.encode(derOutputStream1);
/* 314 */       derOutputStream.writeImplicit(
/* 315 */           DerValue.createTag(-128, true, (byte)2), derOutputStream1);
/*     */     } 
/*     */     
/* 318 */     paramDerOutputStream.write((byte)48, derOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 328 */     if (this == paramObject) {
/* 329 */       return true;
/*     */     }
/* 331 */     if (!(paramObject instanceof DistributionPoint)) {
/* 332 */       return false;
/*     */     }
/* 334 */     DistributionPoint distributionPoint = (DistributionPoint)paramObject;
/*     */     
/* 336 */     return (Objects.equals(this.fullName, distributionPoint.fullName) && 
/* 337 */       Objects.equals(this.relativeName, distributionPoint.relativeName) && 
/* 338 */       Objects.equals(this.crlIssuer, distributionPoint.crlIssuer) && 
/* 339 */       Arrays.equals(this.reasonFlags, distributionPoint.reasonFlags));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 344 */     int i = this.hashCode;
/* 345 */     if (i == 0) {
/* 346 */       i = 1;
/* 347 */       if (this.fullName != null) {
/* 348 */         i += this.fullName.hashCode();
/*     */       }
/* 350 */       if (this.relativeName != null) {
/* 351 */         i += this.relativeName.hashCode();
/*     */       }
/* 353 */       if (this.crlIssuer != null) {
/* 354 */         i += this.crlIssuer.hashCode();
/*     */       }
/* 356 */       if (this.reasonFlags != null) {
/* 357 */         for (byte b = 0; b < this.reasonFlags.length; b++) {
/* 358 */           if (this.reasonFlags[b]) {
/* 359 */             i += b;
/*     */           }
/*     */         } 
/*     */       }
/* 363 */       this.hashCode = i;
/*     */     } 
/* 365 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String reasonToString(int paramInt) {
/* 372 */     if (paramInt > 0 && paramInt < REASON_STRINGS.length) {
/* 373 */       return REASON_STRINGS[paramInt];
/*     */     }
/* 375 */     return "Unknown reason " + paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 382 */     StringBuilder stringBuilder = new StringBuilder();
/* 383 */     if (this.fullName != null) {
/* 384 */       stringBuilder.append("DistributionPoint:\n     " + this.fullName + "\n");
/*     */     }
/* 386 */     if (this.relativeName != null) {
/* 387 */       stringBuilder.append("DistributionPoint:\n     " + this.relativeName + "\n");
/*     */     }
/*     */     
/* 390 */     if (this.reasonFlags != null) {
/* 391 */       stringBuilder.append("   ReasonFlags:\n");
/* 392 */       for (byte b = 0; b < this.reasonFlags.length; b++) {
/* 393 */         if (this.reasonFlags[b]) {
/* 394 */           stringBuilder.append("    " + reasonToString(b) + "\n");
/*     */         }
/*     */       } 
/*     */     } 
/* 398 */     if (this.crlIssuer != null) {
/* 399 */       stringBuilder.append("   CRLIssuer:" + this.crlIssuer + "\n");
/*     */     }
/* 401 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/DistributionPoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */