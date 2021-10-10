/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Insets;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.beans.PropertyVetoException;
/*     */ import javax.swing.DesktopManager;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JDesktopPane;
/*     */ import javax.swing.JInternalFrame;
/*     */ import javax.swing.JLayeredPane;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.event.MouseInputAdapter;
/*     */ import javax.swing.event.MouseInputListener;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.DesktopIconUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicDesktopIconUI
/*     */   extends DesktopIconUI
/*     */ {
/*     */   protected JInternalFrame.JDesktopIcon desktopIcon;
/*     */   protected JInternalFrame frame;
/*     */   protected JComponent iconPane;
/*     */   MouseInputListener mouseInputListener;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  59 */     return new BasicDesktopIconUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/*  66 */     this.desktopIcon = (JInternalFrame.JDesktopIcon)paramJComponent;
/*  67 */     this.frame = this.desktopIcon.getInternalFrame();
/*  68 */     installDefaults();
/*  69 */     installComponents();
/*     */ 
/*     */     
/*  72 */     JInternalFrame jInternalFrame = this.desktopIcon.getInternalFrame();
/*  73 */     if (jInternalFrame.isIcon() && jInternalFrame.getParent() == null) {
/*  74 */       JDesktopPane jDesktopPane = this.desktopIcon.getDesktopPane();
/*  75 */       if (jDesktopPane != null) {
/*  76 */         DesktopManager desktopManager = jDesktopPane.getDesktopManager();
/*  77 */         if (desktopManager instanceof javax.swing.DefaultDesktopManager) {
/*  78 */           desktopManager.iconifyFrame(jInternalFrame);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  83 */     installListeners();
/*  84 */     JLayeredPane.putLayer(this.desktopIcon, JLayeredPane.getLayer(this.frame));
/*     */   }
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/*  88 */     uninstallDefaults();
/*  89 */     uninstallComponents();
/*     */ 
/*     */     
/*  92 */     JInternalFrame jInternalFrame = this.desktopIcon.getInternalFrame();
/*  93 */     if (jInternalFrame.isIcon()) {
/*  94 */       JDesktopPane jDesktopPane = this.desktopIcon.getDesktopPane();
/*  95 */       if (jDesktopPane != null) {
/*  96 */         DesktopManager desktopManager = jDesktopPane.getDesktopManager();
/*  97 */         if (desktopManager instanceof javax.swing.DefaultDesktopManager) {
/*     */           
/*  99 */           jInternalFrame.putClientProperty("wasIconOnce", (Object)null);
/*     */           
/* 101 */           this.desktopIcon.setLocation(-2147483648, 0);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 106 */     uninstallListeners();
/* 107 */     this.frame = null;
/* 108 */     this.desktopIcon = null;
/*     */   }
/*     */   
/*     */   protected void installComponents() {
/* 112 */     this.iconPane = new BasicInternalFrameTitlePane(this.frame);
/* 113 */     this.desktopIcon.setLayout(new BorderLayout());
/* 114 */     this.desktopIcon.add(this.iconPane, "Center");
/*     */   }
/*     */   
/*     */   protected void uninstallComponents() {
/* 118 */     this.desktopIcon.remove(this.iconPane);
/* 119 */     this.desktopIcon.setLayout((LayoutManager)null);
/* 120 */     this.iconPane = null;
/*     */   }
/*     */   
/*     */   protected void installListeners() {
/* 124 */     this.mouseInputListener = createMouseInputListener();
/* 125 */     this.desktopIcon.addMouseMotionListener(this.mouseInputListener);
/* 126 */     this.desktopIcon.addMouseListener(this.mouseInputListener);
/*     */   }
/*     */   
/*     */   protected void uninstallListeners() {
/* 130 */     this.desktopIcon.removeMouseMotionListener(this.mouseInputListener);
/* 131 */     this.desktopIcon.removeMouseListener(this.mouseInputListener);
/* 132 */     this.mouseInputListener = null;
/*     */   }
/*     */   
/*     */   protected void installDefaults() {
/* 136 */     LookAndFeel.installBorder(this.desktopIcon, "DesktopIcon.border");
/* 137 */     LookAndFeel.installProperty(this.desktopIcon, "opaque", Boolean.TRUE);
/*     */   }
/*     */   
/*     */   protected void uninstallDefaults() {
/* 141 */     LookAndFeel.uninstallBorder(this.desktopIcon);
/*     */   }
/*     */   
/*     */   protected MouseInputListener createMouseInputListener() {
/* 145 */     return new MouseInputHandler();
/*     */   }
/*     */   
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 149 */     return this.desktopIcon.getLayout().preferredLayoutSize(this.desktopIcon);
/*     */   }
/*     */   
/*     */   public Dimension getMinimumSize(JComponent paramJComponent) {
/* 153 */     Dimension dimension = new Dimension(this.iconPane.getMinimumSize());
/* 154 */     Border border = this.frame.getBorder();
/*     */     
/* 156 */     if (border != null) {
/* 157 */       dimension.height += (border.getBorderInsets(this.frame)).bottom + 
/* 158 */         (border.getBorderInsets(this.frame)).top;
/*     */     }
/* 160 */     return dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMaximumSize(JComponent paramJComponent) {
/* 170 */     return this.iconPane.getMaximumSize();
/*     */   }
/*     */   
/*     */   public Insets getInsets(JComponent paramJComponent) {
/* 174 */     JInternalFrame jInternalFrame = this.desktopIcon.getInternalFrame();
/* 175 */     Border border = jInternalFrame.getBorder();
/* 176 */     if (border != null) {
/* 177 */       return border.getBorderInsets(jInternalFrame);
/*     */     }
/* 179 */     return new Insets(0, 0, 0, 0);
/*     */   }
/*     */   public void deiconize() {
/*     */     
/* 183 */     try { this.frame.setIcon(false); } catch (PropertyVetoException propertyVetoException) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public class MouseInputHandler
/*     */     extends MouseInputAdapter
/*     */   {
/*     */     int _x;
/*     */     
/*     */     int _y;
/*     */     
/*     */     int __x;
/*     */     
/*     */     int __y;
/*     */     
/*     */     Rectangle startingBounds;
/*     */     
/*     */     public void mouseReleased(MouseEvent param1MouseEvent) {
/* 201 */       this._x = 0;
/* 202 */       this._y = 0;
/* 203 */       this.__x = 0;
/* 204 */       this.__y = 0;
/* 205 */       this.startingBounds = null;
/*     */       
/*     */       JDesktopPane jDesktopPane;
/* 208 */       if ((jDesktopPane = BasicDesktopIconUI.this.desktopIcon.getDesktopPane()) != null) {
/* 209 */         DesktopManager desktopManager = jDesktopPane.getDesktopManager();
/* 210 */         desktopManager.endDraggingFrame(BasicDesktopIconUI.this.desktopIcon);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void mousePressed(MouseEvent param1MouseEvent) {
/* 216 */       Point point = SwingUtilities.convertPoint((Component)param1MouseEvent.getSource(), param1MouseEvent
/* 217 */           .getX(), param1MouseEvent.getY(), null);
/* 218 */       this.__x = param1MouseEvent.getX();
/* 219 */       this.__y = param1MouseEvent.getY();
/* 220 */       this._x = point.x;
/* 221 */       this._y = point.y;
/* 222 */       this.startingBounds = BasicDesktopIconUI.this.desktopIcon.getBounds();
/*     */       
/*     */       JDesktopPane jDesktopPane;
/* 225 */       if ((jDesktopPane = BasicDesktopIconUI.this.desktopIcon.getDesktopPane()) != null) {
/* 226 */         DesktopManager desktopManager = jDesktopPane.getDesktopManager();
/* 227 */         desktopManager.beginDraggingFrame(BasicDesktopIconUI.this.desktopIcon);
/*     */       } 
/*     */       
/* 230 */       try { BasicDesktopIconUI.this.frame.setSelected(true); } catch (PropertyVetoException propertyVetoException) {}
/* 231 */       if (BasicDesktopIconUI.this.desktopIcon.getParent() instanceof JLayeredPane) {
/* 232 */         ((JLayeredPane)BasicDesktopIconUI.this.desktopIcon.getParent()).moveToFront(BasicDesktopIconUI.this.desktopIcon);
/*     */       }
/*     */       
/* 235 */       if (param1MouseEvent.getClickCount() > 1 && 
/* 236 */         BasicDesktopIconUI.this.frame.isIconifiable() && BasicDesktopIconUI.this.frame.isIcon()) {
/* 237 */         BasicDesktopIconUI.this.deiconize();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void mouseMoved(MouseEvent param1MouseEvent) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void mouseDragged(MouseEvent param1MouseEvent) {
/* 252 */       Point point = SwingUtilities.convertPoint((Component)param1MouseEvent.getSource(), param1MouseEvent
/* 253 */           .getX(), param1MouseEvent.getY(), null);
/*     */       
/* 255 */       Insets insets = BasicDesktopIconUI.this.desktopIcon.getInsets();
/*     */       
/* 257 */       int k = ((JComponent)BasicDesktopIconUI.this.desktopIcon.getParent()).getWidth();
/* 258 */       int m = ((JComponent)BasicDesktopIconUI.this.desktopIcon.getParent()).getHeight();
/*     */       
/* 260 */       if (this.startingBounds == null) {
/*     */         return;
/*     */       }
/*     */       
/* 264 */       int i = this.startingBounds.x - this._x - point.x;
/* 265 */       int j = this.startingBounds.y - this._y - point.y;
/*     */       
/* 267 */       if (i + insets.left <= -this.__x)
/* 268 */         i = -this.__x - insets.left; 
/* 269 */       if (j + insets.top <= -this.__y)
/* 270 */         j = -this.__y - insets.top; 
/* 271 */       if (i + this.__x + insets.right > k)
/* 272 */         i = k - this.__x - insets.right; 
/* 273 */       if (j + this.__y + insets.bottom > m) {
/* 274 */         j = m - this.__y - insets.bottom;
/*     */       }
/*     */       JDesktopPane jDesktopPane;
/* 277 */       if ((jDesktopPane = BasicDesktopIconUI.this.desktopIcon.getDesktopPane()) != null) {
/* 278 */         DesktopManager desktopManager = jDesktopPane.getDesktopManager();
/* 279 */         desktopManager.dragFrame(BasicDesktopIconUI.this.desktopIcon, i, j);
/*     */       } else {
/* 281 */         moveAndRepaint(BasicDesktopIconUI.this.desktopIcon, i, j, BasicDesktopIconUI.this.desktopIcon
/* 282 */             .getWidth(), BasicDesktopIconUI.this.desktopIcon.getHeight());
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void moveAndRepaint(JComponent param1JComponent, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 289 */       Rectangle rectangle = param1JComponent.getBounds();
/* 290 */       param1JComponent.setBounds(param1Int1, param1Int2, param1Int3, param1Int4);
/* 291 */       SwingUtilities.computeUnion(param1Int1, param1Int2, param1Int3, param1Int4, rectangle);
/* 292 */       param1JComponent.getParent().repaint(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicDesktopIconUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */