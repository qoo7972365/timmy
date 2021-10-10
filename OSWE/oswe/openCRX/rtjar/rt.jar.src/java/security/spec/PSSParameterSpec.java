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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PSSParameterSpec
/*     */   implements AlgorithmParameterSpec
/*     */ {
/*  81 */   private String mdName = "SHA-1";
/*  82 */   private String mgfName = "MGF1";
/*  83 */   private AlgorithmParameterSpec mgfSpec = MGF1ParameterSpec.SHA1;
/*  84 */   private int saltLen = 20;
/*  85 */   private int trailerField = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   public static final PSSParameterSpec DEFAULT = new PSSParameterSpec();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PSSParameterSpec() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PSSParameterSpec(String paramString1, String paramString2, AlgorithmParameterSpec paramAlgorithmParameterSpec, int paramInt1, int paramInt2) {
/* 123 */     if (paramString1 == null) {
/* 124 */       throw new NullPointerException("digest algorithm is null");
/*     */     }
/* 126 */     if (paramString2 == null) {
/* 127 */       throw new NullPointerException("mask generation function algorithm is null");
/*     */     }
/*     */     
/* 130 */     if (paramInt1 < 0) {
/* 131 */       throw new IllegalArgumentException("negative saltLen value: " + paramInt1);
/*     */     }
/*     */     
/* 134 */     if (paramInt2 < 0) {
/* 135 */       throw new IllegalArgumentException("negative trailerField: " + paramInt2);
/*     */     }
/*     */     
/* 138 */     this.mdName = paramString1;
/* 139 */     this.mgfName = paramString2;
/* 140 */     this.mgfSpec = paramAlgorithmParameterSpec;
/* 141 */     this.saltLen = paramInt1;
/* 142 */     this.trailerField = paramInt2;
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
/*     */   public PSSParameterSpec(int paramInt) {
/* 156 */     if (paramInt < 0) {
/* 157 */       throw new IllegalArgumentException("negative saltLen value: " + paramInt);
/*     */     }
/*     */     
/* 160 */     this.saltLen = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDigestAlgorithm() {
/* 170 */     return this.mdName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMGFAlgorithm() {
/* 181 */     return this.mgfName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AlgorithmParameterSpec getMGFParameters() {
/* 191 */     return this.mgfSpec;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSaltLength() {
/* 200 */     return this.saltLen;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTrailerField() {
/* 210 */     return this.trailerField;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/spec/PSSParameterSpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */