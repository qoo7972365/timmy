/*     */ package javax.swing;
/*     */ 
/*     */ import com.sun.awt.AWTUtilities;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.Window;
/*     */ import java.beans.PropertyVetoException;
/*     */ import java.io.Serializable;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.SurfaceData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultDesktopManager
/*     */   implements DesktopManager, Serializable
/*     */ {
/*     */   static final String HAS_BEEN_ICONIFIED_PROPERTY = "wasIconOnce";
/*     */   static final int DEFAULT_DRAG_MODE = 0;
/*     */   static final int OUTLINE_DRAG_MODE = 1;
/*     */   static final int FASTER_DRAG_MODE = 2;
/*  57 */   int dragMode = 0;
/*     */   
/*  59 */   private transient Rectangle currentBounds = null;
/*  60 */   private transient Graphics desktopGraphics = null;
/*  61 */   private transient Rectangle desktopBounds = null;
/*  62 */   private transient Rectangle[] floatingItems = new Rectangle[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient boolean didDrag;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void openFrame(JInternalFrame paramJInternalFrame) {
/*  76 */     if (paramJInternalFrame.getDesktopIcon().getParent() != null) {
/*  77 */       paramJInternalFrame.getDesktopIcon().getParent().add(paramJInternalFrame);
/*  78 */       removeIconFor(paramJInternalFrame);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeFrame(JInternalFrame paramJInternalFrame) {
/*  88 */     JDesktopPane jDesktopPane = paramJInternalFrame.getDesktopPane();
/*  89 */     if (jDesktopPane == null) {
/*     */       return;
/*     */     }
/*  92 */     boolean bool = paramJInternalFrame.isSelected();
/*  93 */     Container container = paramJInternalFrame.getParent();
/*  94 */     JInternalFrame jInternalFrame = null;
/*  95 */     if (bool) {
/*  96 */       jInternalFrame = jDesktopPane.getNextFrame(paramJInternalFrame); 
/*  97 */       try { paramJInternalFrame.setSelected(false); } catch (PropertyVetoException propertyVetoException) {}
/*     */     } 
/*  99 */     if (container != null) {
/* 100 */       container.remove(paramJInternalFrame);
/* 101 */       container.repaint(paramJInternalFrame.getX(), paramJInternalFrame.getY(), paramJInternalFrame.getWidth(), paramJInternalFrame.getHeight());
/*     */     } 
/* 103 */     removeIconFor(paramJInternalFrame);
/* 104 */     if (paramJInternalFrame.getNormalBounds() != null)
/* 105 */       paramJInternalFrame.setNormalBounds((Rectangle)null); 
/* 106 */     if (wasIcon(paramJInternalFrame))
/* 107 */       setWasIcon(paramJInternalFrame, null); 
/* 108 */     if (jInternalFrame != null) { try {
/* 109 */         jInternalFrame.setSelected(true);
/* 110 */       } catch (PropertyVetoException propertyVetoException) {} }
/* 111 */     else if (bool && jDesktopPane.getComponentCount() == 0)
/*     */     
/* 113 */     { jDesktopPane.requestFocus(); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void maximizeFrame(JInternalFrame paramJInternalFrame) {
/* 122 */     if (paramJInternalFrame.isIcon()) {
/*     */ 
/*     */       
/*     */       try {
/* 126 */         paramJInternalFrame.setIcon(false);
/* 127 */       } catch (PropertyVetoException propertyVetoException) {}
/*     */     } else {
/*     */       
/* 130 */       paramJInternalFrame.setNormalBounds(paramJInternalFrame.getBounds());
/* 131 */       Rectangle rectangle = paramJInternalFrame.getParent().getBounds();
/* 132 */       setBoundsForFrame(paramJInternalFrame, 0, 0, rectangle.width, rectangle.height);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 138 */       paramJInternalFrame.setSelected(true);
/* 139 */     } catch (PropertyVetoException propertyVetoException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void minimizeFrame(JInternalFrame paramJInternalFrame) {
/* 150 */     if (paramJInternalFrame.isIcon()) {
/* 151 */       iconifyFrame(paramJInternalFrame);
/*     */       
/*     */       return;
/*     */     } 
/* 155 */     if (paramJInternalFrame.getNormalBounds() != null) {
/* 156 */       Rectangle rectangle = paramJInternalFrame.getNormalBounds();
/* 157 */       paramJInternalFrame.setNormalBounds((Rectangle)null); 
/* 158 */       try { paramJInternalFrame.setSelected(true); } catch (PropertyVetoException propertyVetoException) {}
/* 159 */       setBoundsForFrame(paramJInternalFrame, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void iconifyFrame(JInternalFrame paramJInternalFrame) {
/* 170 */     Container container = paramJInternalFrame.getParent();
/* 171 */     JDesktopPane jDesktopPane = paramJInternalFrame.getDesktopPane();
/* 172 */     boolean bool = paramJInternalFrame.isSelected();
/* 173 */     JInternalFrame.JDesktopIcon jDesktopIcon = paramJInternalFrame.getDesktopIcon();
/* 174 */     if (!wasIcon(paramJInternalFrame)) {
/* 175 */       Rectangle rectangle = getBoundsForIconOf(paramJInternalFrame);
/* 176 */       jDesktopIcon.setBounds(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */       
/* 178 */       jDesktopIcon.revalidate();
/* 179 */       setWasIcon(paramJInternalFrame, Boolean.TRUE);
/*     */     } 
/*     */     
/* 182 */     if (container == null || jDesktopPane == null) {
/*     */       return;
/*     */     }
/*     */     
/* 186 */     if (container instanceof JLayeredPane) {
/* 187 */       JLayeredPane jLayeredPane = (JLayeredPane)container;
/* 188 */       int i = JLayeredPane.getLayer(paramJInternalFrame);
/* 189 */       JLayeredPane.putLayer(jDesktopIcon, i);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 195 */     if (!paramJInternalFrame.isMaximum()) {
/* 196 */       paramJInternalFrame.setNormalBounds(paramJInternalFrame.getBounds());
/*     */     }
/* 198 */     jDesktopPane.setComponentOrderCheckingEnabled(false);
/* 199 */     container.remove(paramJInternalFrame);
/* 200 */     container.add(jDesktopIcon);
/* 201 */     jDesktopPane.setComponentOrderCheckingEnabled(true);
/* 202 */     container.repaint(paramJInternalFrame.getX(), paramJInternalFrame.getY(), paramJInternalFrame.getWidth(), paramJInternalFrame.getHeight());
/* 203 */     if (bool && 
/* 204 */       jDesktopPane.selectFrame(true) == null)
/*     */     {
/* 206 */       paramJInternalFrame.restoreSubcomponentFocus();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deiconifyFrame(JInternalFrame paramJInternalFrame) {
/* 217 */     JInternalFrame.JDesktopIcon jDesktopIcon = paramJInternalFrame.getDesktopIcon();
/* 218 */     Container container = jDesktopIcon.getParent();
/* 219 */     JDesktopPane jDesktopPane = paramJInternalFrame.getDesktopPane();
/* 220 */     if (container != null && jDesktopPane != null) {
/* 221 */       container.add(paramJInternalFrame);
/*     */ 
/*     */       
/* 224 */       if (paramJInternalFrame.isMaximum()) {
/* 225 */         Rectangle rectangle = container.getBounds();
/* 226 */         if (paramJInternalFrame.getWidth() != rectangle.width || paramJInternalFrame
/* 227 */           .getHeight() != rectangle.height) {
/* 228 */           setBoundsForFrame(paramJInternalFrame, 0, 0, rectangle.width, rectangle.height);
/*     */         }
/*     */       } 
/*     */       
/* 232 */       removeIconFor(paramJInternalFrame);
/* 233 */       if (paramJInternalFrame.isSelected()) {
/* 234 */         paramJInternalFrame.moveToFront();
/* 235 */         paramJInternalFrame.restoreSubcomponentFocus();
/*     */       } else {
/*     */         
/*     */         try {
/* 239 */           paramJInternalFrame.setSelected(true);
/* 240 */         } catch (PropertyVetoException propertyVetoException) {}
/*     */       } 
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
/*     */   public void activateFrame(JInternalFrame paramJInternalFrame) {
/* 253 */     Container container = paramJInternalFrame.getParent();
/*     */     
/* 255 */     JDesktopPane jDesktopPane = paramJInternalFrame.getDesktopPane();
/*     */     
/* 257 */     JInternalFrame jInternalFrame = (jDesktopPane == null) ? null : jDesktopPane.getSelectedFrame();
/*     */     
/* 259 */     if (container == null) {
/*     */       
/* 261 */       container = paramJInternalFrame.getDesktopIcon().getParent();
/* 262 */       if (container == null) {
/*     */         return;
/*     */       }
/*     */     } 
/* 266 */     if (jInternalFrame == null) {
/* 267 */       if (jDesktopPane != null) jDesktopPane.setSelectedFrame(paramJInternalFrame); 
/* 268 */     } else if (jInternalFrame != paramJInternalFrame) {
/*     */ 
/*     */       
/* 271 */       if (jInternalFrame.isSelected()) {
/*     */         try {
/* 273 */           jInternalFrame.setSelected(false);
/*     */         }
/* 275 */         catch (PropertyVetoException propertyVetoException) {}
/*     */       }
/* 277 */       if (jDesktopPane != null) jDesktopPane.setSelectedFrame(paramJInternalFrame); 
/*     */     } 
/* 279 */     paramJInternalFrame.moveToFront();
/*     */   }
/*     */ 
/*     */   
/*     */   public void deactivateFrame(JInternalFrame paramJInternalFrame) {
/* 284 */     JDesktopPane jDesktopPane = paramJInternalFrame.getDesktopPane();
/*     */     
/* 286 */     JInternalFrame jInternalFrame = (jDesktopPane == null) ? null : jDesktopPane.getSelectedFrame();
/* 287 */     if (jInternalFrame == paramJInternalFrame) {
/* 288 */       jDesktopPane.setSelectedFrame((JInternalFrame)null);
/*     */     }
/*     */   }
/*     */   
/*     */   public void beginDraggingFrame(JComponent paramJComponent) {
/* 293 */     setupDragMode(paramJComponent);
/*     */     
/* 295 */     if (this.dragMode == 2) {
/* 296 */       Container container = paramJComponent.getParent();
/* 297 */       this.floatingItems = findFloatingItems(paramJComponent);
/* 298 */       this.currentBounds = paramJComponent.getBounds();
/* 299 */       if (container instanceof JComponent) {
/* 300 */         this.desktopBounds = ((JComponent)container).getVisibleRect();
/*     */       } else {
/*     */         
/* 303 */         this.desktopBounds = container.getBounds();
/* 304 */         this.desktopBounds.x = this.desktopBounds.y = 0;
/*     */       } 
/* 306 */       this.desktopGraphics = JComponent.safelyGetGraphics(container);
/* 307 */       ((JInternalFrame)paramJComponent).isDragging = true;
/* 308 */       this.didDrag = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void setupDragMode(JComponent paramJComponent) {
/* 314 */     JDesktopPane jDesktopPane = getDesktopPane(paramJComponent);
/* 315 */     Container container = paramJComponent.getParent();
/* 316 */     this.dragMode = 0;
/* 317 */     if (jDesktopPane != null) {
/* 318 */       String str = (String)jDesktopPane.getClientProperty("JDesktopPane.dragMode");
/* 319 */       Window window = SwingUtilities.getWindowAncestor(paramJComponent);
/* 320 */       if (window != null && !AWTUtilities.isWindowOpaque(window)) {
/* 321 */         this.dragMode = 0;
/* 322 */       } else if (str != null && str.equals("outline")) {
/* 323 */         this.dragMode = 1;
/* 324 */       } else if (str != null && str.equals("faster") && paramJComponent instanceof JInternalFrame && ((JInternalFrame)paramJComponent)
/*     */         
/* 326 */         .isOpaque() && (container == null || container
/* 327 */         .isOpaque())) {
/* 328 */         this.dragMode = 2;
/*     */       }
/* 330 */       else if (jDesktopPane.getDragMode() == 1) {
/* 331 */         this.dragMode = 1;
/* 332 */       } else if (jDesktopPane.getDragMode() == 0 && paramJComponent instanceof JInternalFrame && ((JInternalFrame)paramJComponent)
/*     */         
/* 334 */         .isOpaque()) {
/* 335 */         this.dragMode = 2;
/*     */       } else {
/* 337 */         this.dragMode = 0;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 343 */   private transient Point currentLoc = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dragFrame(JComponent paramJComponent, int paramInt1, int paramInt2) {
/* 353 */     if (this.dragMode == 1) {
/* 354 */       JDesktopPane jDesktopPane = getDesktopPane(paramJComponent);
/* 355 */       if (jDesktopPane != null) {
/* 356 */         Graphics graphics = JComponent.safelyGetGraphics(jDesktopPane);
/*     */         
/* 358 */         graphics.setXORMode(Color.white);
/* 359 */         if (this.currentLoc != null) {
/* 360 */           graphics.drawRect(this.currentLoc.x, this.currentLoc.y, paramJComponent
/* 361 */               .getWidth() - 1, paramJComponent.getHeight() - 1);
/*     */         }
/* 363 */         graphics.drawRect(paramInt1, paramInt2, paramJComponent.getWidth() - 1, paramJComponent.getHeight() - 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 374 */         SurfaceData surfaceData = ((SunGraphics2D)graphics).getSurfaceData();
/*     */         
/* 376 */         if (!surfaceData.isSurfaceLost()) {
/* 377 */           this.currentLoc = new Point(paramInt1, paramInt2);
/*     */         }
/*     */         
/* 380 */         graphics.dispose();
/*     */       } 
/* 382 */     } else if (this.dragMode == 2) {
/* 383 */       dragFrameFaster(paramJComponent, paramInt1, paramInt2);
/*     */     } else {
/* 385 */       setBoundsForFrame(paramJComponent, paramInt1, paramInt2, paramJComponent.getWidth(), paramJComponent.getHeight());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void endDraggingFrame(JComponent paramJComponent) {
/* 391 */     if (this.dragMode == 1 && this.currentLoc != null) {
/* 392 */       setBoundsForFrame(paramJComponent, this.currentLoc.x, this.currentLoc.y, paramJComponent.getWidth(), paramJComponent.getHeight());
/* 393 */       this.currentLoc = null;
/* 394 */     } else if (this.dragMode == 2) {
/* 395 */       this.currentBounds = null;
/* 396 */       if (this.desktopGraphics != null) {
/* 397 */         this.desktopGraphics.dispose();
/* 398 */         this.desktopGraphics = null;
/*     */       } 
/* 400 */       this.desktopBounds = null;
/* 401 */       ((JInternalFrame)paramJComponent).isDragging = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void beginResizingFrame(JComponent paramJComponent, int paramInt) {
/* 407 */     setupDragMode(paramJComponent);
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
/*     */   public void resizeFrame(JComponent paramJComponent, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 420 */     if (this.dragMode == 0 || this.dragMode == 2) {
/* 421 */       setBoundsForFrame(paramJComponent, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     } else {
/* 423 */       JDesktopPane jDesktopPane = getDesktopPane(paramJComponent);
/* 424 */       if (jDesktopPane != null) {
/* 425 */         Graphics graphics = JComponent.safelyGetGraphics(jDesktopPane);
/*     */         
/* 427 */         graphics.setXORMode(Color.white);
/* 428 */         if (this.currentBounds != null) {
/* 429 */           graphics.drawRect(this.currentBounds.x, this.currentBounds.y, this.currentBounds.width - 1, this.currentBounds.height - 1);
/*     */         }
/* 431 */         graphics.drawRect(paramInt1, paramInt2, paramInt3 - 1, paramInt4 - 1);
/*     */ 
/*     */ 
/*     */         
/* 435 */         SurfaceData surfaceData = ((SunGraphics2D)graphics).getSurfaceData();
/* 436 */         if (!surfaceData.isSurfaceLost()) {
/* 437 */           this.currentBounds = new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */         }
/*     */         
/* 440 */         graphics.setPaintMode();
/* 441 */         graphics.dispose();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void endResizingFrame(JComponent paramJComponent) {
/* 449 */     if (this.dragMode == 1 && this.currentBounds != null) {
/* 450 */       setBoundsForFrame(paramJComponent, this.currentBounds.x, this.currentBounds.y, this.currentBounds.width, this.currentBounds.height);
/* 451 */       this.currentBounds = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBoundsForFrame(JComponent paramJComponent, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 458 */     paramJComponent.setBounds(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     
/* 460 */     paramJComponent.revalidate();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void removeIconFor(JInternalFrame paramJInternalFrame) {
/* 465 */     JInternalFrame.JDesktopIcon jDesktopIcon = paramJInternalFrame.getDesktopIcon();
/* 466 */     Container container = jDesktopIcon.getParent();
/* 467 */     if (container != null) {
/* 468 */       container.remove(jDesktopIcon);
/* 469 */       container.repaint(jDesktopIcon.getX(), jDesktopIcon.getY(), jDesktopIcon.getWidth(), jDesktopIcon.getHeight());
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
/*     */   protected Rectangle getBoundsForIconOf(JInternalFrame paramJInternalFrame) {
/* 482 */     JInternalFrame.JDesktopIcon jDesktopIcon1 = paramJInternalFrame.getDesktopIcon();
/* 483 */     Dimension dimension = jDesktopIcon1.getPreferredSize();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 488 */     Container container = paramJInternalFrame.getParent();
/* 489 */     if (container == null) {
/* 490 */       container = paramJInternalFrame.getDesktopIcon().getParent();
/*     */     }
/*     */     
/* 493 */     if (container == null)
/*     */     {
/* 495 */       return new Rectangle(0, 0, dimension.width, dimension.height);
/*     */     }
/*     */     
/* 498 */     Rectangle rectangle1 = container.getBounds();
/* 499 */     Component[] arrayOfComponent = container.getComponents();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 507 */     Rectangle rectangle2 = null;
/* 508 */     JInternalFrame.JDesktopIcon jDesktopIcon2 = null;
/*     */     
/* 510 */     int i = 0;
/* 511 */     int j = rectangle1.height - dimension.height;
/* 512 */     int k = dimension.width;
/* 513 */     int m = dimension.height;
/*     */     
/* 515 */     boolean bool = false;
/*     */     
/* 517 */     while (!bool) {
/*     */       
/* 519 */       rectangle2 = new Rectangle(i, j, k, m);
/*     */       
/* 521 */       bool = true;
/*     */       
/* 523 */       for (byte b = 0; b < arrayOfComponent.length; b++) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 529 */         if (arrayOfComponent[b] instanceof JInternalFrame) {
/* 530 */           jDesktopIcon2 = ((JInternalFrame)arrayOfComponent[b]).getDesktopIcon();
/*     */         }
/* 532 */         else if (arrayOfComponent[b] instanceof JInternalFrame.JDesktopIcon) {
/* 533 */           jDesktopIcon2 = (JInternalFrame.JDesktopIcon)arrayOfComponent[b];
/*     */         } else {
/*     */           continue;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 546 */         if (!jDesktopIcon2.equals(jDesktopIcon1) && 
/* 547 */           rectangle2.intersects(jDesktopIcon2.getBounds())) {
/* 548 */           bool = false;
/*     */           
/*     */           break;
/*     */         } 
/*     */         continue;
/*     */       } 
/* 554 */       if (jDesktopIcon2 == null)
/*     */       {
/*     */ 
/*     */         
/* 558 */         return rectangle2;
/*     */       }
/* 560 */       i += (jDesktopIcon2.getBounds()).width;
/*     */       
/* 562 */       if (i + k > rectangle1.width) {
/* 563 */         i = 0;
/* 564 */         j -= m;
/*     */       } 
/*     */     } 
/*     */     
/* 568 */     return rectangle2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setPreviousBounds(JInternalFrame paramJInternalFrame, Rectangle paramRectangle) {
/* 577 */     paramJInternalFrame.setNormalBounds(paramRectangle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Rectangle getPreviousBounds(JInternalFrame paramJInternalFrame) {
/* 587 */     return paramJInternalFrame.getNormalBounds();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setWasIcon(JInternalFrame paramJInternalFrame, Boolean paramBoolean) {
/* 595 */     if (paramBoolean != null) {
/* 596 */       paramJInternalFrame.putClientProperty("wasIconOnce", paramBoolean);
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
/*     */   protected boolean wasIcon(JInternalFrame paramJInternalFrame) {
/* 610 */     return (paramJInternalFrame.getClientProperty("wasIconOnce") == Boolean.TRUE);
/*     */   }
/*     */ 
/*     */   
/*     */   JDesktopPane getDesktopPane(JComponent paramJComponent) {
/* 615 */     JDesktopPane jDesktopPane = null;
/* 616 */     Container container = paramJComponent.getParent();
/*     */ 
/*     */     
/* 619 */     while (jDesktopPane == null) {
/* 620 */       if (container instanceof JDesktopPane) {
/* 621 */         jDesktopPane = (JDesktopPane)container; continue;
/*     */       } 
/* 623 */       if (container == null) {
/*     */         break;
/*     */       }
/*     */       
/* 627 */       container = container.getParent();
/*     */     } 
/*     */ 
/*     */     
/* 631 */     return jDesktopPane;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void dragFrameFaster(JComponent paramJComponent, int paramInt1, int paramInt2) {
/* 639 */     Rectangle rectangle1 = new Rectangle(this.currentBounds.x, this.currentBounds.y, this.currentBounds.width, this.currentBounds.height);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 645 */     this.currentBounds.x = paramInt1;
/* 646 */     this.currentBounds.y = paramInt2;
/*     */     
/* 648 */     if (this.didDrag) {
/*     */       
/* 650 */       emergencyCleanup(paramJComponent);
/*     */     } else {
/*     */       
/* 653 */       this.didDrag = true;
/*     */ 
/*     */       
/* 656 */       ((JInternalFrame)paramJComponent).danger = false;
/*     */     } 
/*     */     
/* 659 */     boolean bool = isFloaterCollision(rectangle1, this.currentBounds);
/*     */     
/* 661 */     JComponent jComponent = (JComponent)paramJComponent.getParent();
/* 662 */     Rectangle rectangle2 = rectangle1.intersection(this.desktopBounds);
/*     */     
/* 664 */     RepaintManager repaintManager = RepaintManager.currentManager(paramJComponent);
/*     */     
/* 666 */     repaintManager.beginPaint();
/*     */     try {
/* 668 */       if (!bool) {
/* 669 */         repaintManager.copyArea(jComponent, this.desktopGraphics, rectangle2.x, rectangle2.y, rectangle2.width, rectangle2.height, paramInt1 - rectangle1.x, paramInt2 - rectangle1.y, true);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 678 */       paramJComponent.setBounds(this.currentBounds);
/*     */       
/* 680 */       if (!bool) {
/* 681 */         Rectangle rectangle = this.currentBounds;
/* 682 */         repaintManager.notifyRepaintPerformed(jComponent, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */       } 
/*     */       
/* 685 */       if (bool) {
/*     */ 
/*     */ 
/*     */         
/* 689 */         ((JInternalFrame)paramJComponent).isDragging = false;
/* 690 */         jComponent.paintImmediately(this.currentBounds);
/* 691 */         ((JInternalFrame)paramJComponent).isDragging = true;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 696 */       repaintManager.markCompletelyClean(jComponent);
/* 697 */       repaintManager.markCompletelyClean(paramJComponent);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 702 */       Rectangle[] arrayOfRectangle = null;
/* 703 */       if (rectangle1.intersects(this.currentBounds)) {
/* 704 */         arrayOfRectangle = SwingUtilities.computeDifference(rectangle1, this.currentBounds);
/*     */       } else {
/*     */         
/* 707 */         arrayOfRectangle = new Rectangle[1];
/* 708 */         arrayOfRectangle[0] = rectangle1;
/*     */       } 
/*     */       
/*     */       byte b;
/* 712 */       for (b = 0; b < arrayOfRectangle.length; b++) {
/* 713 */         jComponent.paintImmediately(arrayOfRectangle[b]);
/* 714 */         Rectangle rectangle = arrayOfRectangle[b];
/* 715 */         repaintManager.notifyRepaintPerformed(jComponent, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */       } 
/*     */ 
/*     */       
/* 719 */       if (!rectangle2.equals(rectangle1)) {
/* 720 */         arrayOfRectangle = SwingUtilities.computeDifference(rectangle1, this.desktopBounds);
/*     */         
/* 722 */         for (b = 0; b < arrayOfRectangle.length; b++) {
/* 723 */           (arrayOfRectangle[b]).x += paramInt1 - rectangle1.x;
/* 724 */           (arrayOfRectangle[b]).y += paramInt2 - rectangle1.y;
/* 725 */           ((JInternalFrame)paramJComponent).isDragging = false;
/* 726 */           jComponent.paintImmediately(arrayOfRectangle[b]);
/* 727 */           ((JInternalFrame)paramJComponent).isDragging = true;
/* 728 */           Rectangle rectangle = arrayOfRectangle[b];
/* 729 */           repaintManager.notifyRepaintPerformed(jComponent, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */         } 
/*     */       } 
/*     */     } finally {
/*     */       
/* 734 */       repaintManager.endPaint();
/*     */     } 
/*     */ 
/*     */     
/* 738 */     Window window = SwingUtilities.getWindowAncestor(paramJComponent);
/* 739 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 740 */     if (!window.isOpaque() && toolkit instanceof SunToolkit && ((SunToolkit)toolkit)
/*     */       
/* 742 */       .needUpdateWindow())
/*     */     {
/* 744 */       AWTAccessor.getWindowAccessor().updateWindow(window);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isFloaterCollision(Rectangle paramRectangle1, Rectangle paramRectangle2) {
/* 749 */     if (this.floatingItems.length == 0)
/*     */     {
/* 751 */       return false;
/*     */     }
/*     */     
/* 754 */     for (byte b = 0; b < this.floatingItems.length; b++) {
/* 755 */       boolean bool1 = paramRectangle1.intersects(this.floatingItems[b]);
/* 756 */       if (bool1) {
/* 757 */         return true;
/*     */       }
/* 759 */       boolean bool2 = paramRectangle2.intersects(this.floatingItems[b]);
/* 760 */       if (bool2) {
/* 761 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 765 */     return false;
/*     */   }
/*     */   
/*     */   private Rectangle[] findFloatingItems(JComponent paramJComponent) {
/* 769 */     Container container = paramJComponent.getParent();
/* 770 */     Component[] arrayOfComponent = container.getComponents();
/* 771 */     byte b = 0;
/* 772 */     for (b = 0; b < arrayOfComponent.length && 
/* 773 */       arrayOfComponent[b] != paramJComponent; b++);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 778 */     Rectangle[] arrayOfRectangle = new Rectangle[b];
/* 779 */     for (b = 0; b < arrayOfRectangle.length; b++) {
/* 780 */       arrayOfRectangle[b] = arrayOfComponent[b].getBounds();
/*     */     }
/*     */     
/* 783 */     return arrayOfRectangle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void emergencyCleanup(final JComponent f) {
/* 794 */     if (((JInternalFrame)f).danger) {
/*     */       
/* 796 */       SwingUtilities.invokeLater(new Runnable()
/*     */           {
/*     */             public void run() {
/* 799 */               ((JInternalFrame)f).isDragging = false;
/* 800 */               f.paintImmediately(0, 0, f
/* 801 */                   .getWidth(), f
/* 802 */                   .getHeight());
/*     */ 
/*     */               
/* 805 */               ((JInternalFrame)f).isDragging = true;
/*     */             }
/*     */           });
/*     */       
/* 809 */       ((JInternalFrame)f).danger = false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/DefaultDesktopManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */