/*     */ package com.sun.xml.internal.ws.api.server;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.Component;
/*     */ import com.sun.xml.internal.ws.server.provider.AsyncProviderInvokerTube;
/*     */ import com.sun.xml.internal.ws.server.provider.ProviderArgumentsBuilder;
/*     */ import com.sun.xml.internal.ws.server.provider.ProviderInvokerTube;
/*     */ import com.sun.xml.internal.ws.server.provider.SyncProviderInvokerTube;
/*     */ import com.sun.xml.internal.ws.util.ServiceFinder;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ProviderInvokerTubeFactory<T>
/*     */ {
/*  54 */   private static final ProviderInvokerTubeFactory DEFAULT = new DefaultProviderInvokerTubeFactory();
/*     */   
/*     */   protected abstract ProviderInvokerTube<T> doCreate(@NotNull Class<T> paramClass, @NotNull Invoker paramInvoker, @NotNull ProviderArgumentsBuilder<?> paramProviderArgumentsBuilder, boolean paramBoolean);
/*     */   
/*     */   private static class DefaultProviderInvokerTubeFactory<T>
/*     */     extends ProviderInvokerTubeFactory<T> {
/*     */     private DefaultProviderInvokerTubeFactory() {}
/*     */     
/*     */     public ProviderInvokerTube<T> doCreate(@NotNull Class<T> implType, @NotNull Invoker invoker, @NotNull ProviderArgumentsBuilder<?> argsBuilder, boolean isAsync) {
/*  63 */       return createDefault(implType, invoker, argsBuilder, isAsync);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> ProviderInvokerTube<T> create(@Nullable ClassLoader classLoader, @NotNull Container container, @NotNull Class<T> implType, @NotNull Invoker invoker, @NotNull ProviderArgumentsBuilder<?> argsBuilder, boolean isAsync) {
/*  84 */     for (ProviderInvokerTubeFactory<T> factory : (Iterable<ProviderInvokerTubeFactory<T>>)ServiceFinder.find(ProviderInvokerTubeFactory.class, classLoader, (Component)container)) {
/*     */ 
/*     */       
/*  87 */       ProviderInvokerTube<T> tube = factory.doCreate(implType, invoker, argsBuilder, isAsync);
/*  88 */       if (tube != null) {
/*  89 */         if (logger.isLoggable(Level.FINE)) {
/*  90 */           logger.log(Level.FINE, "{0} successfully created {1}", new Object[] { factory.getClass(), tube });
/*     */         }
/*  92 */         return tube;
/*     */       } 
/*     */     } 
/*  95 */     return DEFAULT.createDefault(implType, invoker, argsBuilder, isAsync);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ProviderInvokerTube<T> createDefault(@NotNull Class<T> implType, @NotNull Invoker invoker, @NotNull ProviderArgumentsBuilder<?> argsBuilder, boolean isAsync) {
/* 103 */     return isAsync ? (ProviderInvokerTube<T>)new AsyncProviderInvokerTube(invoker, argsBuilder) : (ProviderInvokerTube<T>)new SyncProviderInvokerTube(invoker, argsBuilder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   private static final Logger logger = Logger.getLogger(ProviderInvokerTubeFactory.class.getName());
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/server/ProviderInvokerTubeFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */