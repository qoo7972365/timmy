/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.FocusTraversalPolicy;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import sun.security.action.GetPropertyAction;
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
/*     */ public class SortingFocusTraversalPolicy
/*     */   extends InternalFrameFocusTraversalPolicy
/*     */ {
/*     */   private Comparator<? super Component> comparator;
/*     */   private boolean implicitDownCycleTraversal = true;
/*  72 */   private PlatformLogger log = PlatformLogger.getLogger("javax.swing.SortingFocusTraversalPolicy");
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
/*  92 */   private static final SwingContainerOrderFocusTraversalPolicy fitnessTestPolicy = new SwingContainerOrderFocusTraversalPolicy();
/*     */   
/*  94 */   private final int FORWARD_TRAVERSAL = 0;
/*  95 */   private final int BACKWARD_TRAVERSAL = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   private static final boolean legacySortingFTPEnabled = "true".equals(AccessController.doPrivileged(new GetPropertyAction("swing.legacySortingFTPEnabled", "true")));
/*     */   
/* 108 */   private static final Method legacyMergeSortMethod = legacySortingFTPEnabled ? 
/* 109 */     AccessController.<Method>doPrivileged(new PrivilegedAction<Method>() {
/*     */         public Method run() {
/*     */           try {
/* 112 */             Class<?> clazz = Class.forName("java.util.Arrays");
/* 113 */             Method method = clazz.getDeclaredMethod("legacyMergeSort", new Class[] { Object[].class, Comparator.class });
/* 114 */             method.setAccessible(true);
/* 115 */             return method;
/* 116 */           } catch (ClassNotFoundException|NoSuchMethodException classNotFoundException) {
/*     */             
/* 118 */             return null;
/*     */           } 
/*     */         }
/*     */       }) : null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SortingFocusTraversalPolicy() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SortingFocusTraversalPolicy(Comparator<? super Component> paramComparator) {
/* 138 */     this.comparator = paramComparator;
/*     */   }
/*     */   
/*     */   private List<Component> getFocusTraversalCycle(Container paramContainer) {
/* 142 */     ArrayList<Component> arrayList = new ArrayList();
/* 143 */     enumerateAndSortCycle(paramContainer, arrayList);
/* 144 */     return arrayList;
/*     */   }
/*     */   private int getComponentIndex(List<Component> paramList, Component paramComponent) {
/*     */     int i;
/*     */     try {
/* 149 */       i = Collections.binarySearch(paramList, paramComponent, this.comparator);
/* 150 */     } catch (ClassCastException classCastException) {
/* 151 */       if (this.log.isLoggable(PlatformLogger.Level.FINE)) {
/* 152 */         this.log.fine("### During the binary search for " + paramComponent + " the exception occurred: ", classCastException);
/*     */       }
/* 154 */       return -1;
/*     */     } 
/* 156 */     if (i < 0)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 161 */       i = paramList.indexOf(paramComponent);
/*     */     }
/* 163 */     return i;
/*     */   }
/*     */   
/*     */   private void enumerateAndSortCycle(Container paramContainer, List<Component> paramList) {
/* 167 */     if (paramContainer.isShowing()) {
/* 168 */       enumerateCycle(paramContainer, paramList);
/* 169 */       if (!legacySortingFTPEnabled || 
/* 170 */         !legacySort(paramList, this.comparator))
/*     */       {
/* 172 */         Collections.sort(paramList, this.comparator);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean legacySort(List<Component> paramList, Comparator<? super Component> paramComparator) {
/* 178 */     if (legacyMergeSortMethod == null) {
/* 179 */       return false;
/*     */     }
/* 181 */     Object[] arrayOfObject = paramList.toArray();
/*     */     try {
/* 183 */       legacyMergeSortMethod.invoke(null, new Object[] { arrayOfObject, paramComparator });
/* 184 */     } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException illegalAccessException) {
/* 185 */       return false;
/*     */     } 
/* 187 */     ListIterator<Component> listIterator = paramList.listIterator();
/* 188 */     for (Object object : arrayOfObject) {
/* 189 */       listIterator.next();
/* 190 */       listIterator.set((Component)object);
/*     */     } 
/* 192 */     return true;
/*     */   }
/*     */   
/*     */   private void enumerateCycle(Container paramContainer, List<Component> paramList) {
/* 196 */     if (!paramContainer.isVisible() || !paramContainer.isDisplayable()) {
/*     */       return;
/*     */     }
/*     */     
/* 200 */     paramList.add(paramContainer);
/*     */     
/* 202 */     Component[] arrayOfComponent = paramContainer.getComponents();
/* 203 */     for (Component component : arrayOfComponent) {
/* 204 */       if (component instanceof Container) {
/* 205 */         Container container = (Container)component;
/*     */         
/* 207 */         if (!container.isFocusCycleRoot() && 
/* 208 */           !container.isFocusTraversalPolicyProvider() && (!(container instanceof JComponent) || 
/* 209 */           !((JComponent)container).isManagingFocus())) {
/*     */           
/* 211 */           enumerateCycle(container, paramList);
/*     */           continue;
/*     */         } 
/*     */       } 
/* 215 */       paramList.add(component);
/*     */       continue;
/*     */     } 
/*     */   }
/*     */   Container getTopmostProvider(Container paramContainer, Component paramComponent) {
/* 220 */     Container container1 = paramComponent.getParent();
/* 221 */     Container container2 = null;
/* 222 */     while (container1 != paramContainer && container1 != null) {
/* 223 */       if (container1.isFocusTraversalPolicyProvider()) {
/* 224 */         container2 = container1;
/*     */       }
/* 226 */       container1 = container1.getParent();
/*     */     } 
/* 228 */     if (container1 == null) {
/* 229 */       return null;
/*     */     }
/* 231 */     return container2;
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
/* 242 */     Component component = null;
/*     */     
/* 244 */     if (paramComponent instanceof Container) {
/* 245 */       Container container = (Container)paramComponent;
/*     */       
/* 247 */       if (container.isFocusCycleRoot()) {
/* 248 */         if (getImplicitDownCycleTraversal()) {
/* 249 */           component = container.getFocusTraversalPolicy().getDefaultComponent(container);
/*     */           
/* 251 */           if (component != null && this.log.isLoggable(PlatformLogger.Level.FINE)) {
/* 252 */             this.log.fine("### Transfered focus down-cycle to " + component + " in the focus cycle root " + container);
/*     */           }
/*     */         } else {
/*     */           
/* 256 */           return null;
/*     */         } 
/* 258 */       } else if (container.isFocusTraversalPolicyProvider()) {
/*     */ 
/*     */         
/* 261 */         component = (paramInt == 0) ? container.getFocusTraversalPolicy().getDefaultComponent(container) : container.getFocusTraversalPolicy().getLastComponent(container);
/*     */         
/* 263 */         if (component != null && this.log.isLoggable(PlatformLogger.Level.FINE)) {
/* 264 */           this.log.fine("### Transfered focus to " + component + " in the FTP provider " + container);
/*     */         }
/*     */       } 
/*     */     } 
/* 268 */     return component;
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
/* 294 */     if (this.log.isLoggable(PlatformLogger.Level.FINE)) {
/* 295 */       this.log.fine("### Searching in " + paramContainer + " for component after " + paramComponent);
/*     */     }
/*     */     
/* 298 */     if (paramContainer == null || paramComponent == null) {
/* 299 */       throw new IllegalArgumentException("aContainer and aComponent cannot be null");
/*     */     }
/* 301 */     if (!paramContainer.isFocusTraversalPolicyProvider() && !paramContainer.isFocusCycleRoot()) {
/* 302 */       throw new IllegalArgumentException("aContainer should be focus cycle root or focus traversal policy provider");
/*     */     }
/* 304 */     if (paramContainer.isFocusCycleRoot() && !paramComponent.isFocusCycleRoot(paramContainer)) {
/* 305 */       throw new IllegalArgumentException("aContainer is not a focus cycle root of aComponent");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 310 */     Component component = getComponentDownCycle(paramComponent, 0);
/* 311 */     if (component != null) {
/* 312 */       return component;
/*     */     }
/*     */ 
/*     */     
/* 316 */     Container container = getTopmostProvider(paramContainer, paramComponent);
/* 317 */     if (container != null) {
/* 318 */       if (this.log.isLoggable(PlatformLogger.Level.FINE)) {
/* 319 */         this.log.fine("### Asking FTP " + container + " for component after " + paramComponent);
/*     */       }
/*     */ 
/*     */       
/* 323 */       FocusTraversalPolicy focusTraversalPolicy = container.getFocusTraversalPolicy();
/* 324 */       Component component1 = focusTraversalPolicy.getComponentAfter(container, paramComponent);
/*     */ 
/*     */ 
/*     */       
/* 328 */       if (component1 != null) {
/* 329 */         if (this.log.isLoggable(PlatformLogger.Level.FINE)) {
/* 330 */           this.log.fine("### FTP returned " + component1);
/*     */         }
/* 332 */         return component1;
/*     */       } 
/* 334 */       paramComponent = container;
/*     */     } 
/*     */     
/* 337 */     List<Component> list = getFocusTraversalCycle(paramContainer);
/*     */     
/* 339 */     if (this.log.isLoggable(PlatformLogger.Level.FINE)) {
/* 340 */       this.log.fine("### Cycle is " + list + ", component is " + paramComponent);
/*     */     }
/*     */     
/* 343 */     int i = getComponentIndex(list, paramComponent);
/*     */     
/* 345 */     if (i < 0) {
/* 346 */       if (this.log.isLoggable(PlatformLogger.Level.FINE)) {
/* 347 */         this.log.fine("### Didn't find component " + paramComponent + " in a cycle " + paramContainer);
/*     */       }
/* 349 */       return getFirstComponent(paramContainer);
/*     */     } 
/*     */     
/* 352 */     for (; ++i < list.size(); i++) {
/* 353 */       component = list.get(i);
/* 354 */       if (accept(component))
/* 355 */         return component; 
/* 356 */       if ((component = getComponentDownCycle(component, 0)) != null) {
/* 357 */         return component;
/*     */       }
/*     */     } 
/*     */     
/* 361 */     if (paramContainer.isFocusCycleRoot()) {
/* 362 */       this.cachedRoot = paramContainer;
/* 363 */       this.cachedCycle = list;
/*     */       
/* 365 */       component = getFirstComponent(paramContainer);
/*     */       
/* 367 */       this.cachedRoot = null;
/* 368 */       this.cachedCycle = null;
/*     */       
/* 370 */       return component;
/*     */     } 
/* 372 */     return null;
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
/*     */   public Component getComponentBefore(Container paramContainer, Component paramComponent) {
/* 398 */     if (paramContainer == null || paramComponent == null) {
/* 399 */       throw new IllegalArgumentException("aContainer and aComponent cannot be null");
/*     */     }
/* 401 */     if (!paramContainer.isFocusTraversalPolicyProvider() && !paramContainer.isFocusCycleRoot()) {
/* 402 */       throw new IllegalArgumentException("aContainer should be focus cycle root or focus traversal policy provider");
/*     */     }
/* 404 */     if (paramContainer.isFocusCycleRoot() && !paramComponent.isFocusCycleRoot(paramContainer)) {
/* 405 */       throw new IllegalArgumentException("aContainer is not a focus cycle root of aComponent");
/*     */     }
/*     */ 
/*     */     
/* 409 */     Container container = getTopmostProvider(paramContainer, paramComponent);
/* 410 */     if (container != null) {
/* 411 */       if (this.log.isLoggable(PlatformLogger.Level.FINE)) {
/* 412 */         this.log.fine("### Asking FTP " + container + " for component after " + paramComponent);
/*     */       }
/*     */ 
/*     */       
/* 416 */       FocusTraversalPolicy focusTraversalPolicy = container.getFocusTraversalPolicy();
/* 417 */       Component component = focusTraversalPolicy.getComponentBefore(container, paramComponent);
/*     */ 
/*     */ 
/*     */       
/* 421 */       if (component != null) {
/* 422 */         if (this.log.isLoggable(PlatformLogger.Level.FINE)) {
/* 423 */           this.log.fine("### FTP returned " + component);
/*     */         }
/* 425 */         return component;
/*     */       } 
/* 427 */       paramComponent = container;
/*     */ 
/*     */       
/* 430 */       if (accept(paramComponent)) {
/* 431 */         return paramComponent;
/*     */       }
/*     */     } 
/*     */     
/* 435 */     List<Component> list = getFocusTraversalCycle(paramContainer);
/*     */     
/* 437 */     if (this.log.isLoggable(PlatformLogger.Level.FINE)) {
/* 438 */       this.log.fine("### Cycle is " + list + ", component is " + paramComponent);
/*     */     }
/*     */     
/* 441 */     int i = getComponentIndex(list, paramComponent);
/*     */     
/* 443 */     if (i < 0) {
/* 444 */       if (this.log.isLoggable(PlatformLogger.Level.FINE)) {
/* 445 */         this.log.fine("### Didn't find component " + paramComponent + " in a cycle " + paramContainer);
/*     */       }
/* 447 */       return getLastComponent(paramContainer);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 453 */     for (; --i >= 0; i--) {
/* 454 */       Component component1 = list.get(i); Component component2;
/* 455 */       if (component1 != paramContainer && (component2 = getComponentDownCycle(component1, 1)) != null)
/* 456 */         return component2; 
/* 457 */       if (accept(component1)) {
/* 458 */         return component1;
/*     */       }
/*     */     } 
/*     */     
/* 462 */     if (paramContainer.isFocusCycleRoot()) {
/* 463 */       this.cachedRoot = paramContainer;
/* 464 */       this.cachedCycle = list;
/*     */       
/* 466 */       Component component = getLastComponent(paramContainer);
/*     */       
/* 468 */       this.cachedRoot = null;
/* 469 */       this.cachedCycle = null;
/*     */       
/* 471 */       return component;
/*     */     } 
/* 473 */     return null;
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
/*     */   public Component getFirstComponent(Container paramContainer) {
/*     */     List<Component> list;
/* 490 */     if (this.log.isLoggable(PlatformLogger.Level.FINE)) {
/* 491 */       this.log.fine("### Getting first component in " + paramContainer);
/*     */     }
/* 493 */     if (paramContainer == null) {
/* 494 */       throw new IllegalArgumentException("aContainer cannot be null");
/*     */     }
/*     */     
/* 497 */     if (this.cachedRoot == paramContainer) {
/* 498 */       list = this.cachedCycle;
/*     */     } else {
/* 500 */       list = getFocusTraversalCycle(paramContainer);
/*     */     } 
/*     */     
/* 503 */     if (list.size() == 0) {
/* 504 */       if (this.log.isLoggable(PlatformLogger.Level.FINE)) {
/* 505 */         this.log.fine("### Cycle is empty");
/*     */       }
/* 507 */       return null;
/*     */     } 
/* 509 */     if (this.log.isLoggable(PlatformLogger.Level.FINE)) {
/* 510 */       this.log.fine("### Cycle is " + list);
/*     */     }
/*     */     
/* 513 */     for (Component component : list) {
/* 514 */       if (accept(component))
/* 515 */         return component; 
/* 516 */       if (component != paramContainer && (
/* 517 */         component = getComponentDownCycle(component, 0)) != null)
/*     */       {
/* 519 */         return component;
/*     */       }
/*     */     } 
/* 522 */     return null;
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
/*     */   public Component getLastComponent(Container paramContainer) {
/*     */     List<Component> list;
/* 538 */     if (this.log.isLoggable(PlatformLogger.Level.FINE)) {
/* 539 */       this.log.fine("### Getting last component in " + paramContainer);
/*     */     }
/*     */     
/* 542 */     if (paramContainer == null) {
/* 543 */       throw new IllegalArgumentException("aContainer cannot be null");
/*     */     }
/*     */     
/* 546 */     if (this.cachedRoot == paramContainer) {
/* 547 */       list = this.cachedCycle;
/*     */     } else {
/* 549 */       list = getFocusTraversalCycle(paramContainer);
/*     */     } 
/*     */     
/* 552 */     if (list.size() == 0) {
/* 553 */       if (this.log.isLoggable(PlatformLogger.Level.FINE)) {
/* 554 */         this.log.fine("### Cycle is empty");
/*     */       }
/* 556 */       return null;
/*     */     } 
/* 558 */     if (this.log.isLoggable(PlatformLogger.Level.FINE)) {
/* 559 */       this.log.fine("### Cycle is " + list);
/*     */     }
/*     */     
/* 562 */     for (int i = list.size() - 1; i >= 0; i--) {
/* 563 */       Component component = list.get(i);
/* 564 */       if (accept(component))
/* 565 */         return component; 
/* 566 */       if (component instanceof Container && component != paramContainer) {
/* 567 */         Container container = (Container)component;
/* 568 */         if (container.isFocusTraversalPolicyProvider()) {
/* 569 */           Component component1 = container.getFocusTraversalPolicy().getLastComponent(container);
/* 570 */           if (component1 != null) {
/* 571 */             return component1;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 576 */     return null;
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
/* 593 */     return getFirstComponent(paramContainer);
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
/*     */   public void setImplicitDownCycleTraversal(boolean paramBoolean) {
/* 611 */     this.implicitDownCycleTraversal = paramBoolean;
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
/* 628 */     return this.implicitDownCycleTraversal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setComparator(Comparator<? super Component> paramComparator) {
/* 638 */     this.comparator = paramComparator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Comparator<? super Component> getComparator() {
/* 648 */     return this.comparator;
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
/* 662 */     return fitnessTestPolicy.accept(paramComponent);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/SortingFocusTraversalPolicy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */