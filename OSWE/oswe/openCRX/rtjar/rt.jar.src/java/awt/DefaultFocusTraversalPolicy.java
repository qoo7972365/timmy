/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.peer.ComponentPeer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultFocusTraversalPolicy
/*     */   extends ContainerOrderFocusTraversalPolicy
/*     */ {
/*     */   private static final long serialVersionUID = 8876966522510157497L;
/*     */   
/*     */   protected boolean accept(Component paramComponent) {
/*  97 */     if (!paramComponent.isVisible() || !paramComponent.isDisplayable() || 
/*  98 */       !paramComponent.isEnabled())
/*     */     {
/* 100 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     if (!(paramComponent instanceof Window)) {
/* 107 */       Container container = paramComponent.getParent();
/* 108 */       for (; container != null; 
/* 109 */         container = container.getParent()) {
/*     */         
/* 111 */         if (!container.isEnabled() && !container.isLightweight()) {
/* 112 */           return false;
/*     */         }
/* 114 */         if (container instanceof Window) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 120 */     boolean bool = paramComponent.isFocusable();
/* 121 */     if (paramComponent.isFocusTraversableOverridden()) {
/* 122 */       return bool;
/*     */     }
/*     */     
/* 125 */     ComponentPeer componentPeer = paramComponent.getPeer();
/* 126 */     return (componentPeer != null && componentPeer.isFocusable());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/DefaultFocusTraversalPolicy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */