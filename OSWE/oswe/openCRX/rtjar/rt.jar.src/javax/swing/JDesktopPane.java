/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.beans.PropertyVetoException;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.TreeSet;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import javax.swing.plaf.DesktopPaneUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JDesktopPane
/*     */   extends JLayeredPane
/*     */   implements Accessible
/*     */ {
/*     */   private static final String uiClassID = "DesktopPaneUI";
/*     */   transient DesktopManager desktopManager;
/* 100 */   private transient JInternalFrame selectedFrame = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int LIVE_DRAG_MODE = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int OUTLINE_DRAG_MODE = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   private int dragMode = 0;
/*     */   
/*     */   private boolean dragModeSet = false;
/*     */   
/*     */   private transient List<JInternalFrame> framesCache;
/*     */   
/*     */   private boolean componentOrderCheckingEnabled = true;
/*     */   private boolean componentOrderChanged = false;
/*     */   
/*     */   public JDesktopPane() {
/* 130 */     setUIProperty("opaque", Boolean.TRUE);
/* 131 */     setFocusCycleRoot(true);
/*     */     
/* 133 */     setFocusTraversalPolicy(new LayoutFocusTraversalPolicy() {
/*     */           public Component getDefaultComponent(Container param1Container) {
/* 135 */             JInternalFrame[] arrayOfJInternalFrame = JDesktopPane.this.getAllFrames();
/* 136 */             Component component = null;
/* 137 */             for (JInternalFrame jInternalFrame : arrayOfJInternalFrame) {
/* 138 */               component = jInternalFrame.getFocusTraversalPolicy().getDefaultComponent(jInternalFrame);
/* 139 */               if (component != null) {
/*     */                 break;
/*     */               }
/*     */             } 
/* 143 */             return component;
/*     */           }
/*     */         });
/* 146 */     updateUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DesktopPaneUI getUI() {
/* 156 */     return (DesktopPaneUI)this.ui;
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
/*     */   public void setUI(DesktopPaneUI paramDesktopPaneUI) {
/* 171 */     setUI(paramDesktopPaneUI);
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
/*     */   public void setDragMode(int paramInt) {
/* 192 */     int i = this.dragMode;
/* 193 */     this.dragMode = paramInt;
/* 194 */     firePropertyChange("dragMode", i, this.dragMode);
/* 195 */     this.dragModeSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDragMode() {
/* 206 */     return this.dragMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DesktopManager getDesktopManager() {
/* 214 */     return this.desktopManager;
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
/*     */   public void setDesktopManager(DesktopManager paramDesktopManager) {
/* 230 */     DesktopManager desktopManager = this.desktopManager;
/* 231 */     this.desktopManager = paramDesktopManager;
/* 232 */     firePropertyChange("desktopManager", desktopManager, this.desktopManager);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateUI() {
/* 243 */     setUI((DesktopPaneUI)UIManager.getUI(this));
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
/*     */   public String getUIClassID() {
/* 255 */     return "DesktopPaneUI";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JInternalFrame[] getAllFrames() {
/* 265 */     return getAllFrames(this).<JInternalFrame>toArray(new JInternalFrame[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Collection<JInternalFrame> getAllFrames(Container paramContainer) {
/* 270 */     LinkedHashSet<JInternalFrame> linkedHashSet = new LinkedHashSet();
/* 271 */     int i = paramContainer.getComponentCount();
/* 272 */     for (byte b = 0; b < i; b++) {
/* 273 */       Component component = paramContainer.getComponent(b);
/* 274 */       if (component instanceof JInternalFrame) {
/* 275 */         linkedHashSet.add((JInternalFrame)component);
/* 276 */       } else if (component instanceof JInternalFrame.JDesktopIcon) {
/* 277 */         JInternalFrame jInternalFrame = ((JInternalFrame.JDesktopIcon)component).getInternalFrame();
/* 278 */         if (jInternalFrame != null) {
/* 279 */           linkedHashSet.add(jInternalFrame);
/*     */         }
/* 281 */       } else if (component instanceof Container) {
/* 282 */         linkedHashSet.addAll(getAllFrames((Container)component));
/*     */       } 
/*     */     } 
/* 285 */     return linkedHashSet;
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
/*     */   public JInternalFrame getSelectedFrame() {
/* 298 */     return this.selectedFrame;
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
/*     */   public void setSelectedFrame(JInternalFrame paramJInternalFrame) {
/* 314 */     this.selectedFrame = paramJInternalFrame;
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
/*     */   public JInternalFrame[] getAllFramesInLayer(int paramInt) {
/* 327 */     Collection<JInternalFrame> collection = getAllFrames(this);
/* 328 */     Iterator<JInternalFrame> iterator = collection.iterator();
/* 329 */     while (iterator.hasNext()) {
/* 330 */       if (((JInternalFrame)iterator.next()).getLayer() != paramInt) {
/* 331 */         iterator.remove();
/*     */       }
/*     */     } 
/* 334 */     return collection.<JInternalFrame>toArray(new JInternalFrame[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   private List<JInternalFrame> getFrames() {
/* 339 */     TreeSet<ComponentPosition> treeSet = new TreeSet();
/* 340 */     for (byte b = 0; b < getComponentCount(); b++) {
/* 341 */       Component component = getComponent(b);
/* 342 */       if (component instanceof JInternalFrame) {
/* 343 */         treeSet.add(new ComponentPosition((JInternalFrame)component, getLayer(component), b));
/*     */       
/*     */       }
/* 346 */       else if (component instanceof JInternalFrame.JDesktopIcon) {
/* 347 */         component = ((JInternalFrame.JDesktopIcon)component).getInternalFrame();
/* 348 */         treeSet.add(new ComponentPosition((JInternalFrame)component, getLayer(component), b));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 353 */     ArrayList<JInternalFrame> arrayList = new ArrayList(treeSet.size());
/* 354 */     for (ComponentPosition componentPosition : treeSet) {
/* 355 */       arrayList.add(componentPosition.component);
/*     */     }
/* 357 */     return arrayList;
/*     */   }
/*     */   
/*     */   private static class ComponentPosition
/*     */     implements Comparable<ComponentPosition> {
/*     */     private final JInternalFrame component;
/*     */     private final int layer;
/*     */     private final int zOrder;
/*     */     
/*     */     ComponentPosition(JInternalFrame param1JInternalFrame, int param1Int1, int param1Int2) {
/* 367 */       this.component = param1JInternalFrame;
/* 368 */       this.layer = param1Int1;
/* 369 */       this.zOrder = param1Int2;
/*     */     }
/*     */     
/*     */     public int compareTo(ComponentPosition param1ComponentPosition) {
/* 373 */       int i = param1ComponentPosition.layer - this.layer;
/* 374 */       if (i == 0) {
/* 375 */         return this.zOrder - param1ComponentPosition.zOrder;
/*     */       }
/* 377 */       return i;
/*     */     }
/*     */   }
/*     */   
/*     */   private JInternalFrame getNextFrame(JInternalFrame paramJInternalFrame, boolean paramBoolean) {
/* 382 */     verifyFramesCache();
/* 383 */     if (paramJInternalFrame == null) {
/* 384 */       return getTopInternalFrame();
/*     */     }
/* 386 */     int i = this.framesCache.indexOf(paramJInternalFrame);
/* 387 */     if (i == -1 || this.framesCache.size() == 1)
/*     */     {
/* 389 */       return null;
/*     */     }
/* 391 */     if (paramBoolean) {
/*     */       
/* 393 */       if (++i == this.framesCache.size())
/*     */       {
/* 395 */         i = 0;
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 400 */     else if (--i == -1) {
/*     */       
/* 402 */       i = this.framesCache.size() - 1;
/*     */     } 
/*     */     
/* 405 */     return this.framesCache.get(i);
/*     */   }
/*     */   
/*     */   JInternalFrame getNextFrame(JInternalFrame paramJInternalFrame) {
/* 409 */     return getNextFrame(paramJInternalFrame, true);
/*     */   }
/*     */   
/*     */   private JInternalFrame getTopInternalFrame() {
/* 413 */     if (this.framesCache.size() == 0) {
/* 414 */       return null;
/*     */     }
/* 416 */     return this.framesCache.get(0);
/*     */   }
/*     */   
/*     */   private void updateFramesCache() {
/* 420 */     this.framesCache = getFrames();
/*     */   }
/*     */ 
/*     */   
/*     */   private void verifyFramesCache() {
/* 425 */     if (this.componentOrderChanged) {
/* 426 */       this.componentOrderChanged = false;
/* 427 */       updateFramesCache();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(Component paramComponent) {
/* 436 */     super.remove(paramComponent);
/* 437 */     updateFramesCache();
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
/*     */   public JInternalFrame selectFrame(boolean paramBoolean) {
/* 451 */     JInternalFrame jInternalFrame1 = getSelectedFrame();
/* 452 */     JInternalFrame jInternalFrame2 = getNextFrame(jInternalFrame1, paramBoolean);
/* 453 */     if (jInternalFrame2 == null) {
/* 454 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 458 */     setComponentOrderCheckingEnabled(false);
/* 459 */     if (paramBoolean && jInternalFrame1 != null)
/* 460 */       jInternalFrame1.moveToBack(); 
/*     */     try {
/* 462 */       jInternalFrame2.setSelected(true);
/* 463 */     } catch (PropertyVetoException propertyVetoException) {}
/* 464 */     setComponentOrderCheckingEnabled(true);
/* 465 */     return jInternalFrame2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setComponentOrderCheckingEnabled(boolean paramBoolean) {
/* 476 */     this.componentOrderCheckingEnabled = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addImpl(Component paramComponent, Object paramObject, int paramInt) {
/* 484 */     super.addImpl(paramComponent, paramObject, paramInt);
/* 485 */     if (this.componentOrderCheckingEnabled && (
/* 486 */       paramComponent instanceof JInternalFrame || paramComponent instanceof JInternalFrame.JDesktopIcon))
/*     */     {
/* 488 */       this.componentOrderChanged = true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(int paramInt) {
/* 498 */     if (this.componentOrderCheckingEnabled) {
/* 499 */       Component component = getComponent(paramInt);
/* 500 */       if (component instanceof JInternalFrame || component instanceof JInternalFrame.JDesktopIcon)
/*     */       {
/* 502 */         this.componentOrderChanged = true;
/*     */       }
/*     */     } 
/* 505 */     super.remove(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAll() {
/* 513 */     if (this.componentOrderCheckingEnabled) {
/* 514 */       int i = getComponentCount();
/* 515 */       for (byte b = 0; b < i; b++) {
/* 516 */         Component component = getComponent(b);
/* 517 */         if (component instanceof JInternalFrame || component instanceof JInternalFrame.JDesktopIcon) {
/*     */           
/* 519 */           this.componentOrderChanged = true;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 524 */     super.removeAll();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setComponentZOrder(Component paramComponent, int paramInt) {
/* 532 */     super.setComponentZOrder(paramComponent, paramInt);
/* 533 */     if (this.componentOrderCheckingEnabled && (
/* 534 */       paramComponent instanceof JInternalFrame || paramComponent instanceof JInternalFrame.JDesktopIcon))
/*     */     {
/* 536 */       this.componentOrderChanged = true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 546 */     paramObjectOutputStream.defaultWriteObject();
/* 547 */     if (getUIClassID().equals("DesktopPaneUI")) {
/* 548 */       byte b = JComponent.getWriteObjCounter(this);
/* 549 */       b = (byte)(b - 1); JComponent.setWriteObjCounter(this, b);
/* 550 */       if (b == 0 && this.ui != null) {
/* 551 */         this.ui.installUI(this);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void setUIProperty(String paramString, Object paramObject) {
/* 557 */     if (paramString == "dragMode") {
/* 558 */       if (!this.dragModeSet) {
/* 559 */         setDragMode(((Integer)paramObject).intValue());
/* 560 */         this.dragModeSet = false;
/*     */       } 
/*     */     } else {
/* 563 */       super.setUIProperty(paramString, paramObject);
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
/*     */   protected String paramString() {
/* 578 */     String str = (this.desktopManager != null) ? this.desktopManager.toString() : "";
/*     */     
/* 580 */     return super.paramString() + ",desktopManager=" + str;
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
/*     */   public AccessibleContext getAccessibleContext() {
/* 599 */     if (this.accessibleContext == null) {
/* 600 */       this.accessibleContext = new AccessibleJDesktopPane();
/*     */     }
/* 602 */     return this.accessibleContext;
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
/*     */   protected class AccessibleJDesktopPane
/*     */     extends JComponent.AccessibleJComponent
/*     */   {
/*     */     public AccessibleRole getAccessibleRole() {
/* 630 */       return AccessibleRole.DESKTOP_PANE;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JDesktopPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */