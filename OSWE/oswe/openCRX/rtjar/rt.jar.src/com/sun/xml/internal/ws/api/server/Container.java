/*     */ package com.sun.xml.internal.ws.api.server;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.api.Component;
/*     */ import com.sun.xml.internal.ws.api.ComponentEx;
/*     */ import com.sun.xml.internal.ws.api.ComponentRegistry;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CopyOnWriteArraySet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Container
/*     */   implements ComponentRegistry, ComponentEx
/*     */ {
/*  75 */   private final Set<Component> components = new CopyOnWriteArraySet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   public static final Container NONE = new NoneContainer();
/*     */   
/*     */   private static final class NoneContainer extends Container {
/*     */     private NoneContainer() {} }
/*     */   
/*     */   public <S> S getSPI(Class<S> spiType) {
/*  93 */     if (this.components == null) return null; 
/*  94 */     for (Component c : this.components) {
/*  95 */       S s = (S)c.getSPI(spiType);
/*  96 */       if (s != null)
/*  97 */         return s; 
/*     */     } 
/*  99 */     return null;
/*     */   }
/*     */   
/*     */   public Set<Component> getComponents() {
/* 103 */     return this.components;
/*     */   }
/*     */   @NotNull
/*     */   public <E> Iterable<E> getIterableSPI(Class<E> spiType) {
/* 107 */     E item = getSPI(spiType);
/* 108 */     if (item != null) {
/* 109 */       Collection<E> c = Collections.singletonList(item);
/* 110 */       return c;
/*     */     } 
/* 112 */     return Collections.emptySet();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/server/Container.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */