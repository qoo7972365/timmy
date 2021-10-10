/*    */ package sun.security.util;
/*    */ 
/*    */ import java.security.AlgorithmParameters;
/*    */ import java.security.CryptoPrimitive;
/*    */ import java.security.Key;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LegacyAlgorithmConstraints
/*    */   extends AbstractAlgorithmConstraints
/*    */ {
/*    */   public static final String PROPERTY_TLS_LEGACY_ALGS = "jdk.tls.legacyAlgorithms";
/*    */   private final String[] legacyAlgorithms;
/*    */   
/*    */   public LegacyAlgorithmConstraints(String paramString, AlgorithmDecomposer paramAlgorithmDecomposer) {
/* 47 */     super(paramAlgorithmDecomposer);
/* 48 */     this.legacyAlgorithms = getAlgorithms(paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public final boolean permits(Set<CryptoPrimitive> paramSet, String paramString, AlgorithmParameters paramAlgorithmParameters) {
/* 54 */     return checkAlgorithm(this.legacyAlgorithms, paramString, this.decomposer);
/*    */   }
/*    */ 
/*    */   
/*    */   public final boolean permits(Set<CryptoPrimitive> paramSet, Key paramKey) {
/* 59 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public final boolean permits(Set<CryptoPrimitive> paramSet, String paramString, Key paramKey, AlgorithmParameters paramAlgorithmParameters) {
/* 65 */     return checkAlgorithm(this.legacyAlgorithms, paramString, this.decomposer);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/LegacyAlgorithmConstraints.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */