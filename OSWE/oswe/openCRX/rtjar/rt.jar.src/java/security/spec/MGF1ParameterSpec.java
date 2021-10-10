/*     */ package java.security.spec;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MGF1ParameterSpec
/*     */   implements AlgorithmParameterSpec
/*     */ {
/*  64 */   public static final MGF1ParameterSpec SHA1 = new MGF1ParameterSpec("SHA-1");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public static final MGF1ParameterSpec SHA224 = new MGF1ParameterSpec("SHA-224");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   public static final MGF1ParameterSpec SHA256 = new MGF1ParameterSpec("SHA-256");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   public static final MGF1ParameterSpec SHA384 = new MGF1ParameterSpec("SHA-384");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   public static final MGF1ParameterSpec SHA512 = new MGF1ParameterSpec("SHA-512");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String mdName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MGF1ParameterSpec(String paramString) {
/*  98 */     if (paramString == null) {
/*  99 */       throw new NullPointerException("digest algorithm is null");
/*     */     }
/* 101 */     this.mdName = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDigestAlgorithm() {
/* 111 */     return this.mdName;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/spec/MGF1ParameterSpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */