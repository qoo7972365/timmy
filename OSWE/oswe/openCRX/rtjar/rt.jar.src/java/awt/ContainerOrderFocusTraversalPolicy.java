/*     */ package java.awt;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContainerOrderFocusTraversalPolicy
/*     */   extends FocusTraversalPolicy
/*     */   implements Serializable
/*     */ {
/*  63 */   private static final PlatformLogger log = PlatformLogger.getLogger("java.awt.ContainerOrderFocusTraversalPolicy");
/*     */   
/*  65 */   private final int FORWARD_TRAVERSAL = 0;
/*  66 */   private final int BACKWARD_TRAVERSAL = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 486933713763926351L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean implicitDownCycleTraversal = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Container cachedRoot;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient List<Component> cachedCycle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Component> getFocusTraversalCycle(Container paramContainer) {
/* 106 */     ArrayList<Component> arrayList = new ArrayList();
/* 107 */     enumerateCycle(paramContainer, arrayList);
/* 108 */     return arrayList;
/*     */   }
/*     */   private int getComponentIndex(List<Component> paramList, Component paramComponent) {
/* 111 */     return paramList.indexOf(paramComponent);
/*     */   }
/*     */   
/*     */   private void enumerateCycle(Container paramContainer, List<Component> paramList) {
/* 115 */     if (!paramContainer.isVisible() || !paramContainer.isDisplayable()) {
/*     */       return;
/*     */     }
/*     */     
/* 119 */     paramList.add(paramContainer);
/*     */     
/* 121 */     Component[] arrayOfComponent = paramContainer.getComponents();
/* 122 */     for (byte b = 0; b < arrayOfComponent.length; b++) {
/* 123 */       Component component = arrayOfComponent[b];
/* 124 */       if (component instanceof Container) {
/* 125 */         Container container = (Container)component;
/*     */         
/* 127 */         if (!container.isFocusCycleRoot() && !container.isFocusTraversalPolicyProvider()) {
/* 128 */           enumerateCycle(container, paramList);
/*     */           continue;
/*     */         } 
/*     */       } 
/* 132 */       paramList.add(component);
/*     */       continue;
/*     */     } 
/*     */   }
/*     */   private Container getTopmostProvider(Container paramContainer, Component paramComponent) {
/* 137 */     Container container1 = paramComponent.getParent();
/* 138 */     Container container2 = null;
/* 139 */     while (container1 != paramContainer && container1 != null) {
/* 140 */       if (container1.isFocusTraversalPolicyProvider()) {
/* 141 */         container2 = container1;
/*     */       }
/* 143 */       container1 = container1.getParent();
/*     */     } 
/* 145 */     if (container1 == null) {
/* 146 */       return null;
/*     */     }
/* 148 */     return container2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Component getComponentDownCycle(Component paramComponent, int paramInt) {
/* 159 */     Component component = null;
/*     */     
/* 161 */     if (paramComponent instanceof Container) {
/* 162 */       Container container = (Container)paramComponent;
/*     */       
/* 164 */       if (container.isFocusCycleRoot()) {
/* 165 */         if (getImplicitDownCycleTraversal()) {
/* 166 */           component = container.getFocusTraversalPolicy().getDefaultComponent(container);
/*     */           
/* 168 */           if (component != null && log.isLoggable(PlatformLogger.Level.FINE)) {
/* 169 */             log.fine("### Transfered focus down-cycle to " + component + " in the focus cycle root " + container);
/*     */           }
/*     */         } else {
/*     */           
/* 173 */           return null;
/*     */         } 
/* 175 */       } else if (container.isFocusTraversalPolicyProvider()) {
/*     */ 
/*     */         
/* 178 */         component = (paramInt == 0) ? container.getFocusTraversalPolicy().getDefaultComponent(container) : container.getFocusTraversalPolicy().getLastComponent(container);
/*     */         
/* 180 */         if (component != null && log.isLoggable(PlatformLogger.Level.FINE)) {
/* 181 */           log.fine("### Transfered focus to " + component + " in the FTP provider " + container);
/*     */         }
/*     */       } 
/*     */     } 
/* 185 */     return component;
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
/*     */   public Component getComponentAfter(Container paramContainer, Component paramComponent) {
/* 211 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 212 */       log.fine("### Searching in " + paramContainer + " for component after " + paramComponent);
/*     */     }
/*     */     
/* 215 */     if (paramContainer == null || paramComponent == null) {
/* 216 */       throw new IllegalArgumentException("aContainer and aComponent cannot be null");
/*     */     }
/* 218 */     if (!paramContainer.isFocusTraversalPolicyProvider() && !paramContainer.isFocusCycleRoot()) {
/* 219 */       throw new IllegalArgumentException("aContainer should be focus cycle root or focus traversal policy provider");
/*     */     }
/* 221 */     if (paramContainer.isFocusCycleRoot() && !paramComponent.isFocusCycleRoot(paramContainer)) {
/* 222 */       throw new IllegalArgumentException("aContainer is not a focus cycle root of aComponent");
/*     */     }
/*     */     
/* 225 */     synchronized (paramContainer.getTreeLock()) {
/*     */       
/* 227 */       if (!paramContainer.isVisible() || !paramContainer.isDisplayable()) {
/* 228 */         return null;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 233 */       Component component = getComponentDownCycle(paramComponent, 0);
/* 234 */       if (component != null) {
/* 235 */         return component;
/*     */       }
/*     */ 
/*     */       
/* 239 */       Container container = getTopmostProvider(paramContainer, paramComponent);
/* 240 */       if (container != null) {
/* 241 */         if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 242 */           log.fine("### Asking FTP " + container + " for component after " + paramComponent);
/*     */         }
/*     */ 
/*     */         
/* 246 */         FocusTraversalPolicy focusTraversalPolicy = container.getFocusTraversalPolicy();
/* 247 */         Component component1 = focusTraversalPolicy.getComponentAfter(container, paramComponent);
/*     */ 
/*     */ 
/*     */         
/* 251 */         if (component1 != null) {
/* 252 */           if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 253 */             log.fine("### FTP returned " + component1);
/*     */           }
/* 255 */           return component1;
/*     */         } 
/* 257 */         paramComponent = container;
/*     */       } 
/*     */       
/* 260 */       List<Component> list = getFocusTraversalCycle(paramContainer);
/*     */       
/* 262 */       if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 263 */         log.fine("### Cycle is " + list + ", component is " + paramComponent);
/*     */       }
/*     */       
/* 266 */       int i = getComponentIndex(list, paramComponent);
/*     */       
/* 268 */       if (i < 0) {
/* 269 */         if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 270 */           log.fine("### Didn't find component " + paramComponent + " in a cycle " + paramContainer);
/*     */         }
/* 272 */         return getFirstComponent(paramContainer);
/*     */       } 
/*     */       
/* 275 */       for (; ++i < list.size(); i++) {
/* 276 */         component = list.get(i);
/* 277 */         if (accept(component))
/* 278 */           return component; 
/* 279 */         if ((component = getComponentDownCycle(component, 0)) != null) {
/* 280 */           return component;
/*     */         }
/*     */       } 
/*     */       
/* 284 */       if (paramContainer.isFocusCycleRoot()) {
/* 285 */         this.cachedRoot = paramContainer;
/* 286 */         this.cachedCycle = list;
/*     */         
/* 288 */         component = getFirstComponent(paramContainer);
/*     */         
/* 290 */         this.cachedRoot = null;
/* 291 */         this.cachedCycle = null;
/*     */         
/* 293 */         return component;
/*     */       } 
/*     */     } 
/* 296 */     return null;
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
/*     */   public Component getComponentBefore(Container paramContainer, Component paramComponent) {
/* 315 */     if (paramContainer == null || paramComponent == null) {
/* 316 */       throw new IllegalArgumentException("aContainer and aComponent cannot be null");
/*     */     }
/* 318 */     if (!paramContainer.isFocusTraversalPolicyProvider() && !paramContainer.isFocusCycleRoot()) {
/* 319 */       throw new IllegalArgumentException("aContainer should be focus cycle root or focus traversal policy provider");
/*     */     }
/* 321 */     if (paramContainer.isFocusCycleRoot() && !paramComponent.isFocusCycleRoot(paramContainer)) {
/* 322 */       throw new IllegalArgumentException("aContainer is not a focus cycle root of aComponent");
/*     */     }
/*     */     
/* 325 */     synchronized (paramContainer.getTreeLock()) {
/*     */       
/* 327 */       if (!paramContainer.isVisible() || !paramContainer.isDisplayable()) {
/* 328 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 332 */       Container container = getTopmostProvider(paramContainer, paramComponent);
/* 333 */       if (container != null) {
/* 334 */         if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 335 */           log.fine("### Asking FTP " + container + " for component after " + paramComponent);
/*     */         }
/*     */ 
/*     */         
/* 339 */         FocusTraversalPolicy focusTraversalPolicy = container.getFocusTraversalPolicy();
/* 340 */         Component component = focusTraversalPolicy.getComponentBefore(container, paramComponent);
/*     */ 
/*     */ 
/*     */         
/* 344 */         if (component != null) {
/* 345 */           if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 346 */             log.fine("### FTP returned " + component);
/*     */           }
/* 348 */           return component;
/*     */         } 
/* 350 */         paramComponent = container;
/*     */ 
/*     */         
/* 353 */         if (accept(paramComponent)) {
/* 354 */           return paramComponent;
/*     */         }
/*     */       } 
/*     */       
/* 358 */       List<Component> list = getFocusTraversalCycle(paramContainer);
/*     */       
/* 360 */       if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 361 */         log.fine("### Cycle is " + list + ", component is " + paramComponent);
/*     */       }
/*     */       
/* 364 */       int i = getComponentIndex(list, paramComponent);
/*     */       
/* 366 */       if (i < 0) {
/* 367 */         if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 368 */           log.fine("### Didn't find component " + paramComponent + " in a cycle " + paramContainer);
/*     */         }
/* 370 */         return getLastComponent(paramContainer);
/*     */       } 
/*     */       
/* 373 */       Component component1 = null;
/* 374 */       Component component2 = null;
/*     */       
/* 376 */       for (; --i >= 0; i--) {
/* 377 */         component1 = list.get(i);
/* 378 */         if (component1 != paramContainer && (component2 = getComponentDownCycle(component1, 1)) != null)
/* 379 */           return component2; 
/* 380 */         if (accept(component1)) {
/* 381 */           return component1;
/*     */         }
/*     */       } 
/*     */       
/* 385 */       if (paramContainer.isFocusCycleRoot()) {
/* 386 */         this.cachedRoot = paramContainer;
/* 387 */         this.cachedCycle = list;
/*     */         
/* 389 */         component1 = getLastComponent(paramContainer);
/*     */         
/* 391 */         this.cachedRoot = null;
/* 392 */         this.cachedCycle = null;
/*     */         
/* 394 */         return component1;
/*     */       } 
/*     */     } 
/* 397 */     return null;
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
/*     */   public Component getFirstComponent(Container paramContainer) {
/* 414 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 415 */       log.fine("### Getting first component in " + paramContainer);
/*     */     }
/* 417 */     if (paramContainer == null) {
/* 418 */       throw new IllegalArgumentException("aContainer cannot be null");
/*     */     }
/*     */ 
/*     */     
/* 422 */     synchronized (paramContainer.getTreeLock()) {
/*     */       List<Component> list;
/* 424 */       if (!paramContainer.isVisible() || !paramContainer.isDisplayable()) {
/* 425 */         return null;
/*     */       }
/*     */       
/* 428 */       if (this.cachedRoot == paramContainer) {
/* 429 */         list = this.cachedCycle;
/*     */       } else {
/* 431 */         list = getFocusTraversalCycle(paramContainer);
/*     */       } 
/*     */       
/* 434 */       if (list.size() == 0) {
/* 435 */         if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 436 */           log.fine("### Cycle is empty");
/*     */         }
/* 438 */         return null;
/*     */       } 
/* 440 */       if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 441 */         log.fine("### Cycle is " + list);
/*     */       }
/*     */       
/* 444 */       for (Component component : list) {
/* 445 */         if (accept(component))
/* 446 */           return component; 
/* 447 */         if (component != paramContainer && (
/* 448 */           component = getComponentDownCycle(component, 0)) != null)
/*     */         {
/* 450 */           return component;
/*     */         }
/*     */       } 
/*     */     } 
/* 454 */     return null;
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
/*     */   public Component getLastComponent(Container paramContainer) {
/* 470 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 471 */       log.fine("### Getting last component in " + paramContainer);
/*     */     }
/*     */     
/* 474 */     if (paramContainer == null) {
/* 475 */       throw new IllegalArgumentException("aContainer cannot be null");
/*     */     }
/*     */     
/* 478 */     synchronized (paramContainer.getTreeLock()) {
/*     */       List<Component> list;
/* 480 */       if (!paramContainer.isVisible() || !paramContainer.isDisplayable()) {
/* 481 */         return null;
/*     */       }
/*     */       
/* 484 */       if (this.cachedRoot == paramContainer) {
/* 485 */         list = this.cachedCycle;
/*     */       } else {
/* 487 */         list = getFocusTraversalCycle(paramContainer);
/*     */       } 
/*     */       
/* 490 */       if (list.size() == 0) {
/* 491 */         if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 492 */           log.fine("### Cycle is empty");
/*     */         }
/* 494 */         return null;
/*     */       } 
/* 496 */       if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 497 */         log.fine("### Cycle is " + list);
/*     */       }
/*     */       
/* 500 */       for (int i = list.size() - 1; i >= 0; i--) {
/* 501 */         Component component = list.get(i);
/* 502 */         if (accept(component))
/* 503 */           return component; 
/* 504 */         if (component instanceof Container && component != paramContainer) {
/* 505 */           Container container = (Container)component;
/* 506 */           if (container.isFocusTraversalPolicyProvider()) {
/* 507 */             Component component1 = container.getFocusTraversalPolicy().getLastComponent(container);
/* 508 */             if (component1 != null) {
/* 509 */               return component1;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 515 */     return null;
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
/*     */   public Component getDefaultComponent(Container paramContainer) {
/* 532 */     return getFirstComponent(paramContainer);
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
/*     */   public void setImplicitDownCycleTraversal(boolean paramBoolean) {
/* 551 */     this.implicitDownCycleTraversal = paramBoolean;
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
/*     */   public boolean getImplicitDownCycleTraversal() {
/* 568 */     return this.implicitDownCycleTraversal;
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
/*     */   protected boolean accept(Component paramComponent) {
/* 582 */     if (!paramComponent.canBeFocusOwner()) {
/* 583 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 589 */     if (!(paramComponent instanceof Window)) {
/* 590 */       Container container = paramComponent.getParent();
/* 591 */       for (; container != null; 
/* 592 */         container = container.getParent()) {
/*     */         
/* 594 */         if (!container.isEnabled() && !container.isLightweight()) {
/* 595 */           return false;
/*     */         }
/* 597 */         if (container instanceof Window) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 603 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/ContainerOrderFocusTraversalPolicy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */