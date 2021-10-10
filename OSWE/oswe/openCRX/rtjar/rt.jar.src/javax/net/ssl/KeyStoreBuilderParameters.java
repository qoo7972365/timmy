/*    */ package javax.net.ssl;
/*    */ 
/*    */ import java.security.KeyStore;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KeyStoreBuilderParameters
/*    */   implements ManagerFactoryParameters
/*    */ {
/*    */   private final List<KeyStore.Builder> parameters;
/*    */   
/*    */   public KeyStoreBuilderParameters(KeyStore.Builder paramBuilder) {
/* 54 */     this.parameters = Collections.singletonList(Objects.requireNonNull(paramBuilder));
/*    */   }
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
/*    */   public KeyStoreBuilderParameters(List<KeyStore.Builder> paramList) {
/* 67 */     if (paramList.isEmpty()) {
/* 68 */       throw new IllegalArgumentException();
/*    */     }
/*    */     
/* 71 */     this.parameters = Collections.unmodifiableList(new ArrayList<>(paramList));
/*    */   }
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
/*    */   public List<KeyStore.Builder> getParameters() {
/* 85 */     return this.parameters;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/net/ssl/KeyStoreBuilderParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */