/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.FocusTraversalPolicy;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class LegacyGlueFocusTraversalPolicy
/*     */   extends FocusTraversalPolicy
/*     */   implements Serializable
/*     */ {
/*     */   private transient FocusTraversalPolicy delegatePolicy;
/*     */   private transient DefaultFocusManager delegateManager;
/*  51 */   private HashMap<Component, Component> forwardMap = new HashMap<>(), backwardMap = new HashMap<>();
/*     */ 
/*     */   
/*     */   LegacyGlueFocusTraversalPolicy(FocusTraversalPolicy paramFocusTraversalPolicy) {
/*  55 */     this.delegatePolicy = paramFocusTraversalPolicy;
/*     */   }
/*     */   LegacyGlueFocusTraversalPolicy(DefaultFocusManager paramDefaultFocusManager) {
/*  58 */     this.delegateManager = paramDefaultFocusManager;
/*     */   }
/*     */   
/*     */   void setNextFocusableComponent(Component paramComponent1, Component paramComponent2) {
/*  62 */     this.forwardMap.put(paramComponent1, paramComponent2);
/*  63 */     this.backwardMap.put(paramComponent2, paramComponent1);
/*     */   }
/*     */   void unsetNextFocusableComponent(Component paramComponent1, Component paramComponent2) {
/*  66 */     this.forwardMap.remove(paramComponent1);
/*  67 */     this.backwardMap.remove(paramComponent2);
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getComponentAfter(Container paramContainer, Component paramComponent) {
/*  72 */     Component component = paramComponent;
/*  73 */     HashSet<Component> hashSet = new HashSet();
/*     */     
/*     */     do {
/*  76 */       Component component1 = component;
/*  77 */       component = this.forwardMap.get(component);
/*  78 */       if (component == null) {
/*  79 */         if (this.delegatePolicy != null && component1
/*  80 */           .isFocusCycleRoot(paramContainer)) {
/*  81 */           return this.delegatePolicy.getComponentAfter(paramContainer, component1);
/*     */         }
/*  83 */         if (this.delegateManager != null) {
/*  84 */           return this.delegateManager
/*  85 */             .getComponentAfter(paramContainer, paramComponent);
/*     */         }
/*  87 */         return null;
/*     */       } 
/*     */       
/*  90 */       if (hashSet.contains(component))
/*     */       {
/*  92 */         return null;
/*     */       }
/*  94 */       hashSet.add(component);
/*  95 */     } while (!accept(component));
/*     */     
/*  97 */     return component;
/*     */   }
/*     */   
/*     */   public Component getComponentBefore(Container paramContainer, Component paramComponent) {
/* 101 */     Component component = paramComponent;
/* 102 */     HashSet<Component> hashSet = new HashSet();
/*     */     
/*     */     do {
/* 105 */       Component component1 = component;
/* 106 */       component = this.backwardMap.get(component);
/* 107 */       if (component == null) {
/* 108 */         if (this.delegatePolicy != null && component1
/* 109 */           .isFocusCycleRoot(paramContainer)) {
/* 110 */           return this.delegatePolicy.getComponentBefore(paramContainer, component1);
/*     */         }
/* 112 */         if (this.delegateManager != null) {
/* 113 */           return this.delegateManager
/* 114 */             .getComponentBefore(paramContainer, paramComponent);
/*     */         }
/* 116 */         return null;
/*     */       } 
/*     */       
/* 119 */       if (hashSet.contains(component))
/*     */       {
/* 121 */         return null;
/*     */       }
/* 123 */       hashSet.add(component);
/* 124 */     } while (!accept(component));
/*     */     
/* 126 */     return component;
/*     */   }
/*     */   public Component getFirstComponent(Container paramContainer) {
/* 129 */     if (this.delegatePolicy != null)
/* 130 */       return this.delegatePolicy.getFirstComponent(paramContainer); 
/* 131 */     if (this.delegateManager != null) {
/* 132 */       return this.delegateManager.getFirstComponent(paramContainer);
/*     */     }
/* 134 */     return null;
/*     */   }
/*     */   
/*     */   public Component getLastComponent(Container paramContainer) {
/* 138 */     if (this.delegatePolicy != null)
/* 139 */       return this.delegatePolicy.getLastComponent(paramContainer); 
/* 140 */     if (this.delegateManager != null) {
/* 141 */       return this.delegateManager.getLastComponent(paramContainer);
/*     */     }
/* 143 */     return null;
/*     */   }
/*     */   
/*     */   public Component getDefaultComponent(Container paramContainer) {
/* 147 */     if (this.delegatePolicy != null) {
/* 148 */       return this.delegatePolicy.getDefaultComponent(paramContainer);
/*     */     }
/* 150 */     return getFirstComponent(paramContainer);
/*     */   }
/*     */   
/*     */   private boolean accept(Component paramComponent) {
/* 154 */     if (!paramComponent.isVisible() || !paramComponent.isDisplayable() || 
/* 155 */       !paramComponent.isFocusable() || !paramComponent.isEnabled()) {
/* 156 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 162 */     if (!(paramComponent instanceof java.awt.Window)) {
/* 163 */       Container container = paramComponent.getParent();
/* 164 */       for (; container != null; 
/* 165 */         container = container.getParent()) {
/*     */         
/* 167 */         if (!container.isEnabled() && !container.isLightweight()) {
/* 168 */           return false;
/*     */         }
/* 170 */         if (container instanceof java.awt.Window) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 176 */     return true;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 179 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/* 181 */     if (this.delegatePolicy instanceof Serializable) {
/* 182 */       paramObjectOutputStream.writeObject(this.delegatePolicy);
/*     */     } else {
/* 184 */       paramObjectOutputStream.writeObject(null);
/*     */     } 
/*     */     
/* 187 */     if (this.delegateManager instanceof Serializable) {
/* 188 */       paramObjectOutputStream.writeObject(this.delegateManager);
/*     */     } else {
/* 190 */       paramObjectOutputStream.writeObject(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 196 */     paramObjectInputStream.defaultReadObject();
/* 197 */     this.delegatePolicy = (FocusTraversalPolicy)paramObjectInputStream.readObject();
/* 198 */     this.delegateManager = (DefaultFocusManager)paramObjectInputStream.readObject();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/LegacyGlueFocusTraversalPolicy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */