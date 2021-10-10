/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FocusTraversalPolicy;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.KeyboardFocusManager;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.beans.PropertyVetoException;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.DefaultDesktopManager;
/*     */ import javax.swing.DesktopManager;
/*     */ import javax.swing.InputMap;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JDesktopPane;
/*     */ import javax.swing.JInternalFrame;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.SortingFocusTraversalPolicy;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.DesktopPaneUI;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import sun.swing.DefaultLookup;
/*     */ import sun.swing.UIAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicDesktopPaneUI
/*     */   extends DesktopPaneUI
/*     */ {
/*  50 */   private static final Actions SHARED_ACTION = new Actions();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Handler handler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PropertyChangeListener pcl;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JDesktopPane desktop;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DesktopManager desktopManager;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected KeyStroke minimizeKey;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected KeyStroke maximizeKey;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected KeyStroke closeKey;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected KeyStroke navigateKey;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected KeyStroke navigateKey2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 109 */     return new BasicDesktopPaneUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/* 116 */     this.desktop = (JDesktopPane)paramJComponent;
/* 117 */     installDefaults();
/* 118 */     installDesktopManager();
/* 119 */     installListeners();
/* 120 */     installKeyboardActions();
/*     */   }
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/* 124 */     uninstallKeyboardActions();
/* 125 */     uninstallListeners();
/* 126 */     uninstallDesktopManager();
/* 127 */     uninstallDefaults();
/* 128 */     this.desktop = null;
/* 129 */     this.handler = null;
/*     */   }
/*     */   
/*     */   protected void installDefaults() {
/* 133 */     if (this.desktop.getBackground() == null || this.desktop
/* 134 */       .getBackground() instanceof UIResource) {
/* 135 */       this.desktop.setBackground(UIManager.getColor("Desktop.background"));
/*     */     }
/* 137 */     LookAndFeel.installProperty(this.desktop, "opaque", Boolean.TRUE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners() {
/* 151 */     this.pcl = createPropertyChangeListener();
/* 152 */     this.desktop.addPropertyChangeListener(this.pcl);
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
/*     */   protected void uninstallListeners() {
/* 164 */     this.desktop.removePropertyChangeListener(this.pcl);
/* 165 */     this.pcl = null;
/*     */   }
/*     */   
/*     */   protected void installDesktopManager() {
/* 169 */     this.desktopManager = this.desktop.getDesktopManager();
/* 170 */     if (this.desktopManager == null) {
/* 171 */       this.desktopManager = new BasicDesktopManager();
/* 172 */       this.desktop.setDesktopManager(this.desktopManager);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void uninstallDesktopManager() {
/* 177 */     if (this.desktop.getDesktopManager() instanceof UIResource) {
/* 178 */       this.desktop.setDesktopManager((DesktopManager)null);
/*     */     }
/* 180 */     this.desktopManager = null;
/*     */   }
/*     */   
/*     */   protected void installKeyboardActions() {
/* 184 */     InputMap inputMap = getInputMap(2);
/* 185 */     if (inputMap != null) {
/* 186 */       SwingUtilities.replaceUIInputMap(this.desktop, 2, inputMap);
/*     */     }
/*     */     
/* 189 */     inputMap = getInputMap(1);
/* 190 */     if (inputMap != null) {
/* 191 */       SwingUtilities.replaceUIInputMap(this.desktop, 1, inputMap);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 196 */     LazyActionMap.installLazyActionMap(this.desktop, BasicDesktopPaneUI.class, "DesktopPane.actionMap");
/*     */     
/* 198 */     registerKeyboardActions();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerKeyboardActions() {}
/*     */ 
/*     */   
/*     */   protected void unregisterKeyboardActions() {}
/*     */   
/*     */   InputMap getInputMap(int paramInt) {
/* 208 */     if (paramInt == 2) {
/* 209 */       return createInputMap(paramInt);
/*     */     }
/* 211 */     if (paramInt == 1) {
/* 212 */       return (InputMap)DefaultLookup.get(this.desktop, this, "Desktop.ancestorInputMap");
/*     */     }
/*     */     
/* 215 */     return null;
/*     */   }
/*     */   
/*     */   InputMap createInputMap(int paramInt) {
/* 219 */     if (paramInt == 2) {
/* 220 */       Object[] arrayOfObject = (Object[])DefaultLookup.get(this.desktop, this, "Desktop.windowBindings");
/*     */ 
/*     */       
/* 223 */       if (arrayOfObject != null) {
/* 224 */         return LookAndFeel.makeComponentInputMap(this.desktop, arrayOfObject);
/*     */       }
/*     */     } 
/* 227 */     return null;
/*     */   }
/*     */   
/*     */   static void loadActionMap(LazyActionMap paramLazyActionMap) {
/* 231 */     paramLazyActionMap.put(new Actions(Actions.RESTORE));
/* 232 */     paramLazyActionMap.put(new Actions(Actions.CLOSE));
/* 233 */     paramLazyActionMap.put(new Actions(Actions.MOVE));
/* 234 */     paramLazyActionMap.put(new Actions(Actions.RESIZE));
/* 235 */     paramLazyActionMap.put(new Actions(Actions.LEFT));
/* 236 */     paramLazyActionMap.put(new Actions(Actions.SHRINK_LEFT));
/* 237 */     paramLazyActionMap.put(new Actions(Actions.RIGHT));
/* 238 */     paramLazyActionMap.put(new Actions(Actions.SHRINK_RIGHT));
/* 239 */     paramLazyActionMap.put(new Actions(Actions.UP));
/* 240 */     paramLazyActionMap.put(new Actions(Actions.SHRINK_UP));
/* 241 */     paramLazyActionMap.put(new Actions(Actions.DOWN));
/* 242 */     paramLazyActionMap.put(new Actions(Actions.SHRINK_DOWN));
/* 243 */     paramLazyActionMap.put(new Actions(Actions.ESCAPE));
/* 244 */     paramLazyActionMap.put(new Actions(Actions.MINIMIZE));
/* 245 */     paramLazyActionMap.put(new Actions(Actions.MAXIMIZE));
/* 246 */     paramLazyActionMap.put(new Actions(Actions.NEXT_FRAME));
/* 247 */     paramLazyActionMap.put(new Actions(Actions.PREVIOUS_FRAME));
/* 248 */     paramLazyActionMap.put(new Actions(Actions.NAVIGATE_NEXT));
/* 249 */     paramLazyActionMap.put(new Actions(Actions.NAVIGATE_PREVIOUS));
/*     */   }
/*     */   
/*     */   protected void uninstallKeyboardActions() {
/* 253 */     unregisterKeyboardActions();
/* 254 */     SwingUtilities.replaceUIInputMap(this.desktop, 2, null);
/*     */     
/* 256 */     SwingUtilities.replaceUIInputMap(this.desktop, 1, null);
/*     */     
/* 258 */     SwingUtilities.replaceUIActionMap(this.desktop, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {}
/*     */   
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 265 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Dimension getMinimumSize(JComponent paramJComponent) {
/* 270 */     return new Dimension(0, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Dimension getMaximumSize(JComponent paramJComponent) {
/* 275 */     return new Dimension(2147483647, 2147483647);
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
/*     */   protected PropertyChangeListener createPropertyChangeListener() {
/* 287 */     return getHandler();
/*     */   }
/*     */   
/*     */   private Handler getHandler() {
/* 291 */     if (this.handler == null) {
/* 292 */       this.handler = new Handler();
/*     */     }
/* 294 */     return this.handler;
/*     */   }
/*     */   
/*     */   private class Handler implements PropertyChangeListener {
/*     */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 299 */       String str = param1PropertyChangeEvent.getPropertyName();
/* 300 */       if ("desktopManager" == str)
/* 301 */         BasicDesktopPaneUI.this.installDesktopManager(); 
/*     */     }
/*     */     
/*     */     private Handler() {}
/*     */   }
/*     */   
/*     */   private class BasicDesktopManager
/*     */     extends DefaultDesktopManager
/*     */     implements UIResource {
/*     */     private BasicDesktopManager() {}
/*     */   }
/*     */   
/*     */   private static class Actions extends UIAction {
/* 314 */     private static String CLOSE = "close";
/* 315 */     private static String ESCAPE = "escape";
/* 316 */     private static String MAXIMIZE = "maximize";
/* 317 */     private static String MINIMIZE = "minimize";
/* 318 */     private static String MOVE = "move";
/* 319 */     private static String RESIZE = "resize";
/* 320 */     private static String RESTORE = "restore";
/* 321 */     private static String LEFT = "left";
/* 322 */     private static String RIGHT = "right";
/* 323 */     private static String UP = "up";
/* 324 */     private static String DOWN = "down";
/* 325 */     private static String SHRINK_LEFT = "shrinkLeft";
/* 326 */     private static String SHRINK_RIGHT = "shrinkRight";
/* 327 */     private static String SHRINK_UP = "shrinkUp";
/* 328 */     private static String SHRINK_DOWN = "shrinkDown";
/* 329 */     private static String NEXT_FRAME = "selectNextFrame";
/* 330 */     private static String PREVIOUS_FRAME = "selectPreviousFrame";
/* 331 */     private static String NAVIGATE_NEXT = "navigateNext";
/* 332 */     private static String NAVIGATE_PREVIOUS = "navigatePrevious";
/* 333 */     private final int MOVE_RESIZE_INCREMENT = 10;
/*     */     private static boolean moving = false;
/*     */     private static boolean resizing = false;
/* 336 */     private static JInternalFrame sourceFrame = null;
/* 337 */     private static Component focusOwner = null;
/*     */     
/*     */     Actions() {
/* 340 */       super(null);
/*     */     }
/*     */     
/*     */     Actions(String param1String) {
/* 344 */       super(param1String);
/*     */     }
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 348 */       JDesktopPane jDesktopPane = (JDesktopPane)param1ActionEvent.getSource();
/* 349 */       String str = getName();
/*     */       
/* 351 */       if (CLOSE == str || MAXIMIZE == str || MINIMIZE == str || RESTORE == str) {
/*     */         
/* 353 */         setState(jDesktopPane, str);
/*     */       }
/* 355 */       else if (ESCAPE == str) {
/* 356 */         if (sourceFrame == jDesktopPane.getSelectedFrame() && focusOwner != null)
/*     */         {
/* 358 */           focusOwner.requestFocus();
/*     */         }
/* 360 */         moving = false;
/* 361 */         resizing = false;
/* 362 */         sourceFrame = null;
/* 363 */         focusOwner = null;
/*     */       }
/* 365 */       else if (MOVE == str || RESIZE == str) {
/* 366 */         sourceFrame = jDesktopPane.getSelectedFrame();
/* 367 */         if (sourceFrame == null) {
/*     */           return;
/*     */         }
/* 370 */         moving = (str == MOVE);
/* 371 */         resizing = (str == RESIZE);
/*     */ 
/*     */         
/* 374 */         focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
/* 375 */         if (!SwingUtilities.isDescendingFrom(focusOwner, sourceFrame)) {
/* 376 */           focusOwner = null;
/*     */         }
/* 378 */         sourceFrame.requestFocus();
/*     */       }
/* 380 */       else if (LEFT == str || RIGHT == str || UP == str || DOWN == str || SHRINK_RIGHT == str || SHRINK_LEFT == str || SHRINK_UP == str || SHRINK_DOWN == str) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 388 */         JInternalFrame jInternalFrame = jDesktopPane.getSelectedFrame();
/* 389 */         if (sourceFrame != null && jInternalFrame == sourceFrame) {
/*     */           
/* 391 */           if (KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() != sourceFrame)
/*     */             return; 
/*     */         } else {
/*     */           return;
/*     */         } 
/* 396 */         Insets insets = UIManager.getInsets("Desktop.minOnScreenInsets");
/* 397 */         Dimension dimension1 = jInternalFrame.getSize();
/* 398 */         Dimension dimension2 = jInternalFrame.getMinimumSize();
/* 399 */         int i = jDesktopPane.getWidth();
/* 400 */         int j = jDesktopPane.getHeight();
/*     */         
/* 402 */         Point point = jInternalFrame.getLocation();
/* 403 */         if (LEFT == str) {
/* 404 */           if (moving) {
/* 405 */             jInternalFrame.setLocation((point.x + dimension1.width - 10 < insets.right) ? (-dimension1.width + insets.right) : (point.x - 10), point.y);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           }
/* 411 */           else if (resizing) {
/* 412 */             jInternalFrame.setLocation(point.x - 10, point.y);
/* 413 */             jInternalFrame.setSize(dimension1.width + 10, dimension1.height);
/*     */           }
/*     */         
/* 416 */         } else if (RIGHT == str) {
/* 417 */           if (moving) {
/* 418 */             jInternalFrame.setLocation((point.x + 10 > i - insets.left) ? (i - insets.left) : (point.x + 10), point.y);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           }
/* 424 */           else if (resizing) {
/* 425 */             jInternalFrame.setSize(dimension1.width + 10, dimension1.height);
/*     */           }
/*     */         
/* 428 */         } else if (UP == str) {
/* 429 */           if (moving) {
/* 430 */             jInternalFrame.setLocation(point.x, (point.y + dimension1.height - 10 < insets.bottom) ? (-dimension1.height + insets.bottom) : (point.y - 10));
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           }
/* 436 */           else if (resizing) {
/* 437 */             jInternalFrame.setLocation(point.x, point.y - 10);
/* 438 */             jInternalFrame.setSize(dimension1.width, dimension1.height + 10);
/*     */           }
/*     */         
/* 441 */         } else if (DOWN == str) {
/* 442 */           if (moving) {
/* 443 */             jInternalFrame.setLocation(point.x, (point.y + 10 > j - insets.top) ? (j - insets.top) : (point.y + 10));
/*     */ 
/*     */ 
/*     */           
/*     */           }
/* 448 */           else if (resizing) {
/* 449 */             jInternalFrame.setSize(dimension1.width, dimension1.height + 10);
/*     */           }
/*     */         
/* 452 */         } else if (SHRINK_LEFT == str && resizing) {
/*     */           int k;
/* 454 */           if (dimension2.width < dimension1.width - 10) {
/* 455 */             k = 10;
/*     */           } else {
/* 457 */             k = dimension1.width - dimension2.width;
/*     */           } 
/*     */ 
/*     */           
/* 461 */           if (point.x + dimension1.width - k < insets.left) {
/* 462 */             k = point.x + dimension1.width - insets.left;
/*     */           }
/* 464 */           jInternalFrame.setSize(dimension1.width - k, dimension1.height);
/* 465 */         } else if (SHRINK_RIGHT == str && resizing) {
/*     */           int k;
/* 467 */           if (dimension2.width < dimension1.width - 10) {
/* 468 */             k = 10;
/*     */           } else {
/* 470 */             k = dimension1.width - dimension2.width;
/*     */           } 
/*     */ 
/*     */           
/* 474 */           if (point.x + k > i - insets.right) {
/* 475 */             k = i - insets.right - point.x;
/*     */           }
/*     */           
/* 478 */           jInternalFrame.setLocation(point.x + k, point.y);
/* 479 */           jInternalFrame.setSize(dimension1.width - k, dimension1.height);
/* 480 */         } else if (SHRINK_UP == str && resizing) {
/*     */           int k;
/* 482 */           if (dimension2.height < dimension1.height - 10) {
/*     */             
/* 484 */             k = 10;
/*     */           } else {
/* 486 */             k = dimension1.height - dimension2.height;
/*     */           } 
/*     */ 
/*     */           
/* 490 */           if (point.y + dimension1.height - k < insets.bottom)
/*     */           {
/* 492 */             k = point.y + dimension1.height - insets.bottom;
/*     */           }
/*     */           
/* 495 */           jInternalFrame.setSize(dimension1.width, dimension1.height - k);
/* 496 */         } else if (SHRINK_DOWN == str && resizing) {
/*     */           int k;
/* 498 */           if (dimension2.height < dimension1.height - 10) {
/*     */             
/* 500 */             k = 10;
/*     */           } else {
/* 502 */             k = dimension1.height - dimension2.height;
/*     */           } 
/*     */ 
/*     */           
/* 506 */           if (point.y + k > j - insets.top) {
/* 507 */             k = j - insets.top - point.y;
/*     */           }
/*     */           
/* 510 */           jInternalFrame.setLocation(point.x, point.y + k);
/* 511 */           jInternalFrame.setSize(dimension1.width, dimension1.height - k);
/*     */         }
/*     */       
/* 514 */       } else if (NEXT_FRAME == str || PREVIOUS_FRAME == str) {
/* 515 */         jDesktopPane.selectFrame((str == NEXT_FRAME));
/*     */       }
/* 517 */       else if (NAVIGATE_NEXT == str || NAVIGATE_PREVIOUS == str) {
/*     */         
/* 519 */         boolean bool = true;
/* 520 */         if (NAVIGATE_PREVIOUS == str) {
/* 521 */           bool = false;
/*     */         }
/* 523 */         Container container = jDesktopPane.getFocusCycleRootAncestor();
/*     */         
/* 525 */         if (container != null) {
/*     */           
/* 527 */           FocusTraversalPolicy focusTraversalPolicy = container.getFocusTraversalPolicy();
/* 528 */           if (focusTraversalPolicy != null && focusTraversalPolicy instanceof SortingFocusTraversalPolicy) {
/*     */             
/* 530 */             SortingFocusTraversalPolicy sortingFocusTraversalPolicy = (SortingFocusTraversalPolicy)focusTraversalPolicy;
/*     */             
/* 532 */             boolean bool1 = sortingFocusTraversalPolicy.getImplicitDownCycleTraversal();
/*     */             try {
/* 534 */               sortingFocusTraversalPolicy.setImplicitDownCycleTraversal(false);
/* 535 */               if (bool) {
/*     */                 
/* 537 */                 KeyboardFocusManager.getCurrentKeyboardFocusManager()
/* 538 */                   .focusNextComponent(jDesktopPane);
/*     */               } else {
/*     */                 
/* 541 */                 KeyboardFocusManager.getCurrentKeyboardFocusManager()
/* 542 */                   .focusPreviousComponent(jDesktopPane);
/*     */               } 
/*     */             } finally {
/* 545 */               sortingFocusTraversalPolicy.setImplicitDownCycleTraversal(bool1);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private void setState(JDesktopPane param1JDesktopPane, String param1String) {
/* 553 */       if (param1String == CLOSE) {
/* 554 */         JInternalFrame jInternalFrame = param1JDesktopPane.getSelectedFrame();
/* 555 */         if (jInternalFrame == null) {
/*     */           return;
/*     */         }
/* 558 */         jInternalFrame.doDefaultCloseAction();
/* 559 */       } else if (param1String == MAXIMIZE) {
/*     */         
/* 561 */         JInternalFrame jInternalFrame = param1JDesktopPane.getSelectedFrame();
/* 562 */         if (jInternalFrame == null) {
/*     */           return;
/*     */         }
/* 565 */         if (!jInternalFrame.isMaximum()) {
/* 566 */           if (jInternalFrame.isIcon()) {
/*     */             try {
/* 568 */               jInternalFrame.setIcon(false);
/* 569 */               jInternalFrame.setMaximum(true);
/* 570 */             } catch (PropertyVetoException propertyVetoException) {}
/*     */           } else {
/*     */             try {
/* 573 */               jInternalFrame.setMaximum(true);
/* 574 */             } catch (PropertyVetoException propertyVetoException) {}
/*     */           }
/*     */         
/*     */         }
/* 578 */       } else if (param1String == MINIMIZE) {
/*     */         
/* 580 */         JInternalFrame jInternalFrame = param1JDesktopPane.getSelectedFrame();
/* 581 */         if (jInternalFrame == null) {
/*     */           return;
/*     */         }
/* 584 */         if (!jInternalFrame.isIcon()) {
/*     */           try {
/* 586 */             jInternalFrame.setIcon(true);
/* 587 */           } catch (PropertyVetoException propertyVetoException) {}
/*     */         }
/*     */       }
/* 590 */       else if (param1String == RESTORE) {
/*     */         
/* 592 */         JInternalFrame jInternalFrame = param1JDesktopPane.getSelectedFrame();
/* 593 */         if (jInternalFrame == null) {
/*     */           return;
/*     */         }
/*     */         try {
/* 597 */           if (jInternalFrame.isIcon()) {
/* 598 */             jInternalFrame.setIcon(false);
/* 599 */           } else if (jInternalFrame.isMaximum()) {
/* 600 */             jInternalFrame.setMaximum(false);
/*     */           } 
/* 602 */           jInternalFrame.setSelected(true);
/* 603 */         } catch (PropertyVetoException propertyVetoException) {}
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEnabled(Object param1Object) {
/* 609 */       if (param1Object instanceof JDesktopPane) {
/* 610 */         JDesktopPane jDesktopPane = (JDesktopPane)param1Object;
/* 611 */         String str = getName();
/* 612 */         if (str == NEXT_FRAME || str == PREVIOUS_FRAME)
/*     */         {
/* 614 */           return true;
/*     */         }
/* 616 */         JInternalFrame jInternalFrame = jDesktopPane.getSelectedFrame();
/* 617 */         if (jInternalFrame == null)
/* 618 */           return false; 
/* 619 */         if (str == CLOSE)
/* 620 */           return jInternalFrame.isClosable(); 
/* 621 */         if (str == MINIMIZE)
/* 622 */           return jInternalFrame.isIconifiable(); 
/* 623 */         if (str == MAXIMIZE) {
/* 624 */           return jInternalFrame.isMaximizable();
/*     */         }
/* 626 */         return true;
/*     */       } 
/* 628 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected class OpenAction
/*     */     extends AbstractAction
/*     */   {
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 639 */       JDesktopPane jDesktopPane = (JDesktopPane)param1ActionEvent.getSource();
/* 640 */       BasicDesktopPaneUI.SHARED_ACTION.setState(jDesktopPane, BasicDesktopPaneUI.Actions.RESTORE);
/*     */     }
/*     */     
/*     */     public boolean isEnabled() {
/* 644 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected class CloseAction
/*     */     extends AbstractAction
/*     */   {
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 653 */       JDesktopPane jDesktopPane = (JDesktopPane)param1ActionEvent.getSource();
/* 654 */       BasicDesktopPaneUI.SHARED_ACTION.setState(jDesktopPane, BasicDesktopPaneUI.Actions.CLOSE);
/*     */     }
/*     */     
/*     */     public boolean isEnabled() {
/* 658 */       JInternalFrame jInternalFrame = BasicDesktopPaneUI.this.desktop.getSelectedFrame();
/* 659 */       if (jInternalFrame != null) {
/* 660 */         return jInternalFrame.isClosable();
/*     */       }
/* 662 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected class MinimizeAction
/*     */     extends AbstractAction
/*     */   {
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 671 */       JDesktopPane jDesktopPane = (JDesktopPane)param1ActionEvent.getSource();
/* 672 */       BasicDesktopPaneUI.SHARED_ACTION.setState(jDesktopPane, BasicDesktopPaneUI.Actions.MINIMIZE);
/*     */     }
/*     */     
/*     */     public boolean isEnabled() {
/* 676 */       JInternalFrame jInternalFrame = BasicDesktopPaneUI.this.desktop.getSelectedFrame();
/* 677 */       if (jInternalFrame != null) {
/* 678 */         return jInternalFrame.isIconifiable();
/*     */       }
/* 680 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected class MaximizeAction
/*     */     extends AbstractAction
/*     */   {
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 689 */       JDesktopPane jDesktopPane = (JDesktopPane)param1ActionEvent.getSource();
/* 690 */       BasicDesktopPaneUI.SHARED_ACTION.setState(jDesktopPane, BasicDesktopPaneUI.Actions.MAXIMIZE);
/*     */     }
/*     */     
/*     */     public boolean isEnabled() {
/* 694 */       JInternalFrame jInternalFrame = BasicDesktopPaneUI.this.desktop.getSelectedFrame();
/* 695 */       if (jInternalFrame != null) {
/* 696 */         return jInternalFrame.isMaximizable();
/*     */       }
/* 698 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected class NavigateAction
/*     */     extends AbstractAction
/*     */   {
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 707 */       JDesktopPane jDesktopPane = (JDesktopPane)param1ActionEvent.getSource();
/* 708 */       jDesktopPane.selectFrame(true);
/*     */     }
/*     */     
/*     */     public boolean isEnabled() {
/* 712 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicDesktopPaneUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */