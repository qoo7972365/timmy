/*    */ package sun.tracing;
/*    */ 
/*    */ import com.sun.tracing.Provider;
/*    */ import com.sun.tracing.ProviderFactory;
/*    */ import java.util.HashSet;
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
/*    */ public class MultiplexProviderFactory
/*    */   extends ProviderFactory
/*    */ {
/*    */   private Set<ProviderFactory> factories;
/*    */   
/*    */   public MultiplexProviderFactory(Set<ProviderFactory> paramSet) {
/* 58 */     this.factories = paramSet;
/*    */   }
/*    */   
/*    */   public <T extends Provider> T createProvider(Class<T> paramClass) {
/* 62 */     HashSet<Provider> hashSet = new HashSet();
/* 63 */     for (ProviderFactory providerFactory : this.factories) {
/* 64 */       hashSet.add(providerFactory.createProvider(paramClass));
/*    */     }
/* 66 */     MultiplexProvider multiplexProvider = new MultiplexProvider(paramClass, hashSet);
/* 67 */     multiplexProvider.init();
/* 68 */     return multiplexProvider.newProxyInstance();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/tracing/MultiplexProviderFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */