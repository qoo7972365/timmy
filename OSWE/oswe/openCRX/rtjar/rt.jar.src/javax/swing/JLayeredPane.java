/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Rectangle;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import sun.awt.SunToolkit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JLayeredPane
/*     */   extends JComponent
/*     */   implements Accessible
/*     */ {
/* 161 */   public static final Integer DEFAULT_LAYER = new Integer(0);
/*     */   
/* 163 */   public static final Integer PALETTE_LAYER = new Integer(100);
/*     */   
/* 165 */   public static final Integer MODAL_LAYER = new Integer(200);
/*     */   
/* 167 */   public static final Integer POPUP_LAYER = new Integer(300);
/*     */   
/* 169 */   public static final Integer DRAG_LAYER = new Integer(400);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 176 */   public static final Integer FRAME_CONTENT_LAYER = new Integer(-30000);
/*     */ 
/*     */   
/*     */   public static final String LAYER_PROPERTY = "layeredContainerLayer";
/*     */ 
/*     */   
/*     */   private Hashtable<Component, Integer> componentToLayer;
/*     */ 
/*     */   
/*     */   private boolean optimizedDrawingPossible = true;
/*     */ 
/*     */ 
/*     */   
/*     */   public JLayeredPane() {
/* 190 */     setLayout((LayoutManager)null);
/*     */   }
/*     */   
/*     */   private void validateOptimizedDrawing() {
/* 194 */     boolean bool = false;
/* 195 */     synchronized (getTreeLock()) {
/*     */ 
/*     */       
/* 198 */       for (Component component : getComponents()) {
/* 199 */         Integer integer = null;
/*     */         
/* 201 */         if (SunToolkit.isInstanceOf(component, "javax.swing.JInternalFrame") || (component instanceof JComponent && (
/*     */ 
/*     */           
/* 204 */           integer = (Integer)((JComponent)component).getClientProperty("layeredContainerLayer")) != null))
/*     */         {
/* 206 */           if (integer == null || !integer.equals(FRAME_CONTENT_LAYER)) {
/*     */             
/* 208 */             bool = true;
/*     */             break;
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 214 */     if (bool) {
/* 215 */       this.optimizedDrawingPossible = false;
/*     */     } else {
/* 217 */       this.optimizedDrawingPossible = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addImpl(Component paramComponent, Object paramObject, int paramInt) {
/*     */     int i;
/* 224 */     if (paramObject instanceof Integer) {
/* 225 */       i = ((Integer)paramObject).intValue();
/* 226 */       setLayer(paramComponent, i);
/*     */     } else {
/* 228 */       i = getLayer(paramComponent);
/*     */     } 
/* 230 */     int j = insertIndexForLayer(i, paramInt);
/* 231 */     super.addImpl(paramComponent, paramObject, j);
/* 232 */     paramComponent.validate();
/* 233 */     paramComponent.repaint();
/* 234 */     validateOptimizedDrawing();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(int paramInt) {
/* 245 */     Component component = getComponent(paramInt);
/* 246 */     super.remove(paramInt);
/* 247 */     if (component != null && !(component instanceof JComponent)) {
/* 248 */       getComponentToLayer().remove(component);
/*     */     }
/* 250 */     validateOptimizedDrawing();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAll() {
/* 259 */     Component[] arrayOfComponent = getComponents();
/* 260 */     Hashtable<Component, Integer> hashtable = getComponentToLayer();
/* 261 */     for (int i = arrayOfComponent.length - 1; i >= 0; i--) {
/* 262 */       Component component = arrayOfComponent[i];
/* 263 */       if (component != null && !(component instanceof JComponent)) {
/* 264 */         hashtable.remove(component);
/*     */       }
/*     */     } 
/* 267 */     super.removeAll();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOptimizedDrawingEnabled() {
/* 278 */     return this.optimizedDrawingPossible;
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
/*     */   public static void putLayer(JComponent paramJComponent, int paramInt) {
/* 298 */     Integer integer = new Integer(paramInt);
/* 299 */     paramJComponent.putClientProperty("layeredContainerLayer", integer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getLayer(JComponent paramJComponent) {
/*     */     Integer integer;
/* 311 */     if ((integer = (Integer)paramJComponent.getClientProperty("layeredContainerLayer")) != null)
/* 312 */       return integer.intValue(); 
/* 313 */     return DEFAULT_LAYER.intValue();
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
/*     */   public static JLayeredPane getLayeredPaneAbove(Component paramComponent) {
/* 329 */     if (paramComponent == null) return null;
/*     */     
/* 331 */     Container container = paramComponent.getParent();
/* 332 */     while (container != null && !(container instanceof JLayeredPane))
/* 333 */       container = container.getParent(); 
/* 334 */     return (JLayeredPane)container;
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
/*     */   public void setLayer(Component paramComponent, int paramInt) {
/* 346 */     setLayer(paramComponent, paramInt, -1);
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
/*     */   public void setLayer(Component paramComponent, int paramInt1, int paramInt2) {
/* 361 */     Integer integer = getObjectForLayer(paramInt1);
/*     */     
/* 363 */     if (paramInt1 == getLayer(paramComponent) && paramInt2 == getPosition(paramComponent)) {
/* 364 */       repaint(paramComponent.getBounds());
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 369 */     if (paramComponent instanceof JComponent) {
/* 370 */       ((JComponent)paramComponent).putClientProperty("layeredContainerLayer", integer);
/*     */     } else {
/* 372 */       getComponentToLayer().put(paramComponent, integer);
/*     */     } 
/* 374 */     if (paramComponent.getParent() == null || paramComponent.getParent() != this) {
/* 375 */       repaint(paramComponent.getBounds());
/*     */       
/*     */       return;
/*     */     } 
/* 379 */     int i = insertIndexForLayer(paramComponent, paramInt1, paramInt2);
/*     */     
/* 381 */     setComponentZOrder(paramComponent, i);
/* 382 */     repaint(paramComponent.getBounds());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLayer(Component paramComponent) {
/*     */     Integer integer;
/* 393 */     if (paramComponent instanceof JComponent) {
/* 394 */       integer = (Integer)((JComponent)paramComponent).getClientProperty("layeredContainerLayer");
/*     */     } else {
/* 396 */       integer = getComponentToLayer().get(paramComponent);
/*     */     } 
/* 398 */     if (integer == null)
/* 399 */       return DEFAULT_LAYER.intValue(); 
/* 400 */     return integer.intValue();
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
/*     */   public int getIndexOf(Component paramComponent) {
/* 415 */     int i = getComponentCount();
/* 416 */     for (byte b = 0; b < i; b++) {
/* 417 */       if (paramComponent == getComponent(b))
/* 418 */         return b; 
/*     */     } 
/* 420 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveToFront(Component paramComponent) {
/* 430 */     setPosition(paramComponent, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveToBack(Component paramComponent) {
/* 441 */     setPosition(paramComponent, -1);
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
/*     */   public void setPosition(Component paramComponent, int paramInt) {
/* 459 */     setLayer(paramComponent, getLayer(paramComponent), paramInt);
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
/*     */   public int getPosition(Component paramComponent) {
/* 473 */     byte b = 0;
/*     */     
/* 475 */     getComponentCount();
/* 476 */     int k = getIndexOf(paramComponent);
/*     */     
/* 478 */     if (k == -1) {
/* 479 */       return -1;
/*     */     }
/* 481 */     int j = getLayer(paramComponent);
/* 482 */     for (int i = k - 1; i >= 0; i--) {
/* 483 */       int m = getLayer(getComponent(i));
/* 484 */       if (m == j) {
/* 485 */         b++;
/*     */       } else {
/* 487 */         return b;
/*     */       } 
/* 489 */     }  return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int highestLayer() {
/* 499 */     if (getComponentCount() > 0)
/* 500 */       return getLayer(getComponent(0)); 
/* 501 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int lowestLayer() {
/* 511 */     int i = getComponentCount();
/* 512 */     if (i > 0)
/* 513 */       return getLayer(getComponent(i - 1)); 
/* 514 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getComponentCountInLayer(int paramInt) {
/* 525 */     byte b2 = 0;
/*     */     
/* 527 */     int i = getComponentCount();
/* 528 */     for (byte b1 = 0; b1 < i; b1++) {
/* 529 */       int j = getLayer(getComponent(b1));
/* 530 */       if (j == paramInt) {
/* 531 */         b2++;
/*     */       }
/* 533 */       else if (b2 > 0 || j < paramInt) {
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 538 */     return b2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Component[] getComponentsInLayer(int paramInt) {
/* 549 */     byte b2 = 0;
/*     */ 
/*     */     
/* 552 */     Component[] arrayOfComponent = new Component[getComponentCountInLayer(paramInt)];
/* 553 */     int i = getComponentCount();
/* 554 */     for (byte b1 = 0; b1 < i; b1++) {
/* 555 */       int j = getLayer(getComponent(b1));
/* 556 */       if (j == paramInt) {
/* 557 */         arrayOfComponent[b2++] = getComponent(b1);
/*     */       }
/* 559 */       else if (b2 > 0 || j < paramInt) {
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 564 */     return arrayOfComponent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics paramGraphics) {
/* 573 */     if (isOpaque()) {
/* 574 */       Rectangle rectangle = paramGraphics.getClipBounds();
/* 575 */       Color color = getBackground();
/* 576 */       if (color == null)
/* 577 */         color = Color.lightGray; 
/* 578 */       paramGraphics.setColor(color);
/* 579 */       if (rectangle != null) {
/* 580 */         paramGraphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */       } else {
/*     */         
/* 583 */         paramGraphics.fillRect(0, 0, getWidth(), getHeight());
/*     */       } 
/*     */     } 
/* 586 */     super.paint(paramGraphics);
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
/*     */   protected Hashtable<Component, Integer> getComponentToLayer() {
/* 599 */     if (this.componentToLayer == null)
/* 600 */       this.componentToLayer = new Hashtable<>(4); 
/* 601 */     return this.componentToLayer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Integer getObjectForLayer(int paramInt) {
/* 612 */     switch (paramInt)
/*     */     { case 0:
/* 614 */         integer = DEFAULT_LAYER;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 631 */         return integer;case 100: integer = PALETTE_LAYER; return integer;case 200: integer = MODAL_LAYER; return integer;case 300: integer = POPUP_LAYER; return integer;case 400: integer = DRAG_LAYER; return integer; }  Integer integer = new Integer(paramInt); return integer;
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
/*     */   protected int insertIndexForLayer(int paramInt1, int paramInt2) {
/* 645 */     return insertIndexForLayer((Component)null, paramInt1, paramInt2);
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
/*     */   private int insertIndexForLayer(Component paramComponent, int paramInt1, int paramInt2) {
/* 663 */     int j = -1;
/* 664 */     int k = -1;
/* 665 */     int m = getComponentCount();
/*     */     
/* 667 */     ArrayList<Component> arrayList = new ArrayList(m);
/*     */     
/* 669 */     for (byte b2 = 0; b2 < m; b2++) {
/* 670 */       if (getComponent(b2) != paramComponent) {
/* 671 */         arrayList.add(getComponent(b2));
/*     */       }
/*     */     } 
/*     */     
/* 675 */     int i = arrayList.size();
/* 676 */     for (byte b1 = 0; b1 < i; b1++) {
/* 677 */       int n = getLayer(arrayList.get(b1));
/* 678 */       if (j == -1 && n == paramInt1) {
/* 679 */         j = b1;
/*     */       }
/* 681 */       if (n < paramInt1) {
/* 682 */         if (b1 == 0) {
/*     */ 
/*     */           
/* 685 */           j = 0;
/* 686 */           k = 0; break;
/*     */         } 
/* 688 */         k = b1;
/*     */ 
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 697 */     if (j == -1 && k == -1) {
/* 698 */       return i;
/*     */     }
/*     */     
/* 701 */     if (j != -1 && k == -1) {
/* 702 */       k = i;
/*     */     }
/* 704 */     if (k != -1 && j == -1) {
/* 705 */       j = k;
/*     */     }
/*     */     
/* 708 */     if (paramInt2 == -1) {
/* 709 */       return k;
/*     */     }
/*     */ 
/*     */     
/* 713 */     if (paramInt2 > -1 && j + paramInt2 <= k) {
/* 714 */       return j + paramInt2;
/*     */     }
/*     */     
/* 717 */     return k;
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
/*     */   protected String paramString() {
/* 730 */     String str = this.optimizedDrawingPossible ? "true" : "false";
/*     */ 
/*     */     
/* 733 */     return super.paramString() + ",optimizedDrawingPossible=" + str;
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
/*     */   public AccessibleContext getAccessibleContext() {
/* 751 */     if (this.accessibleContext == null) {
/* 752 */       this.accessibleContext = new AccessibleJLayeredPane();
/*     */     }
/* 754 */     return this.accessibleContext;
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
/*     */   protected class AccessibleJLayeredPane
/*     */     extends JComponent.AccessibleJComponent
/*     */   {
/*     */     public AccessibleRole getAccessibleRole() {
/* 783 */       return AccessibleRole.LAYERED_PANE;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JLayeredPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */