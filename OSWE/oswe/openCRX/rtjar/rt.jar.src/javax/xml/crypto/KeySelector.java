/*     */ package javax.xml.crypto;
/*     */ 
/*     */ import java.security.Key;
/*     */ import javax.xml.crypto.dsig.keyinfo.KeyInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class KeySelector
/*     */ {
/*     */   public abstract KeySelectorResult select(KeyInfo paramKeyInfo, Purpose paramPurpose, AlgorithmMethod paramAlgorithmMethod, XMLCryptoContext paramXMLCryptoContext) throws KeySelectorException;
/*     */   
/*     */   public static class Purpose
/*     */   {
/*     */     private final String name;
/*     */     
/*     */     private Purpose(String param1String) {
/*  56 */       this.name = param1String;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/*  64 */       return this.name;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  69 */     public static final Purpose SIGN = new Purpose("sign");
/*     */ 
/*     */ 
/*     */     
/*  73 */     public static final Purpose VERIFY = new Purpose("verify");
/*     */ 
/*     */ 
/*     */     
/*  77 */     public static final Purpose ENCRYPT = new Purpose("encrypt");
/*     */ 
/*     */ 
/*     */     
/*  81 */     public static final Purpose DECRYPT = new Purpose("decrypt");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeySelector singletonKeySelector(Key paramKey) {
/* 128 */     return new SingletonKeySelector(paramKey);
/*     */   }
/*     */   
/*     */   private static class SingletonKeySelector extends KeySelector {
/*     */     private final Key key;
/*     */     
/*     */     SingletonKeySelector(Key param1Key) {
/* 135 */       if (param1Key == null) {
/* 136 */         throw new NullPointerException();
/*     */       }
/* 138 */       this.key = param1Key;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KeySelectorResult select(KeyInfo param1KeyInfo, KeySelector.Purpose param1Purpose, AlgorithmMethod param1AlgorithmMethod, XMLCryptoContext param1XMLCryptoContext) throws KeySelectorException {
/* 145 */       return new KeySelectorResult() {
/*     */           public Key getKey() {
/* 147 */             return KeySelector.SingletonKeySelector.this.key;
/*     */           }
/*     */         };
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/crypto/KeySelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */