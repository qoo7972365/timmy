/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ContainerEvent;
/*     */ import java.awt.event.ContainerListener;
/*     */ import javax.swing.InputMap;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.MenuElement;
/*     */ import javax.swing.MenuSelectionManager;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.MenuBarUI;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicMenuBarUI
/*     */   extends MenuBarUI
/*     */ {
/*  57 */   protected JMenuBar menuBar = null;
/*     */   protected ContainerListener containerListener;
/*     */   protected ChangeListener changeListener;
/*     */   private Handler handler;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  63 */     return new BasicMenuBarUI();
/*     */   }
/*     */   
/*     */   static void loadActionMap(LazyActionMap paramLazyActionMap) {
/*  67 */     paramLazyActionMap.put(new Actions("takeFocus"));
/*     */   }
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/*  71 */     this.menuBar = (JMenuBar)paramJComponent;
/*     */     
/*  73 */     installDefaults();
/*  74 */     installListeners();
/*  75 */     installKeyboardActions();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void installDefaults() {
/*  80 */     if (this.menuBar.getLayout() == null || this.menuBar
/*  81 */       .getLayout() instanceof javax.swing.plaf.UIResource) {
/*  82 */       this.menuBar.setLayout(new DefaultMenuLayout(this.menuBar, 2));
/*     */     }
/*     */     
/*  85 */     LookAndFeel.installProperty(this.menuBar, "opaque", Boolean.TRUE);
/*  86 */     LookAndFeel.installBorder(this.menuBar, "MenuBar.border");
/*  87 */     LookAndFeel.installColorsAndFont(this.menuBar, "MenuBar.background", "MenuBar.foreground", "MenuBar.font");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners() {
/*  94 */     this.containerListener = createContainerListener();
/*  95 */     this.changeListener = createChangeListener();
/*     */     
/*  97 */     for (byte b = 0; b < this.menuBar.getMenuCount(); b++) {
/*  98 */       JMenu jMenu = this.menuBar.getMenu(b);
/*  99 */       if (jMenu != null)
/* 100 */         jMenu.getModel().addChangeListener(this.changeListener); 
/*     */     } 
/* 102 */     this.menuBar.addContainerListener(this.containerListener);
/*     */   }
/*     */   
/*     */   protected void installKeyboardActions() {
/* 106 */     InputMap inputMap = getInputMap(2);
/*     */     
/* 108 */     SwingUtilities.replaceUIInputMap(this.menuBar, 2, inputMap);
/*     */ 
/*     */     
/* 111 */     LazyActionMap.installLazyActionMap(this.menuBar, BasicMenuBarUI.class, "MenuBar.actionMap");
/*     */   }
/*     */ 
/*     */   
/*     */   InputMap getInputMap(int paramInt) {
/* 116 */     if (paramInt == 2) {
/*     */       
/* 118 */       Object[] arrayOfObject = (Object[])DefaultLookup.get(this.menuBar, this, "MenuBar.windowBindings");
/* 119 */       if (arrayOfObject != null) {
/* 120 */         return LookAndFeel.makeComponentInputMap(this.menuBar, arrayOfObject);
/*     */       }
/*     */     } 
/* 123 */     return null;
/*     */   }
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/* 127 */     uninstallDefaults();
/* 128 */     uninstallListeners();
/* 129 */     uninstallKeyboardActions();
/*     */     
/* 131 */     this.menuBar = null;
/*     */   }
/*     */   
/*     */   protected void uninstallDefaults() {
/* 135 */     if (this.menuBar != null) {
/* 136 */       LookAndFeel.uninstallBorder(this.menuBar);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void uninstallListeners() {
/* 141 */     this.menuBar.removeContainerListener(this.containerListener);
/*     */     
/* 143 */     for (byte b = 0; b < this.menuBar.getMenuCount(); b++) {
/* 144 */       JMenu jMenu = this.menuBar.getMenu(b);
/* 145 */       if (jMenu != null) {
/* 146 */         jMenu.getModel().removeChangeListener(this.changeListener);
/*     */       }
/*     */     } 
/* 149 */     this.containerListener = null;
/* 150 */     this.changeListener = null;
/* 151 */     this.handler = null;
/*     */   }
/*     */   
/*     */   protected void uninstallKeyboardActions() {
/* 155 */     SwingUtilities.replaceUIInputMap(this.menuBar, 2, null);
/*     */     
/* 157 */     SwingUtilities.replaceUIActionMap(this.menuBar, null);
/*     */   }
/*     */   
/*     */   protected ContainerListener createContainerListener() {
/* 161 */     return getHandler();
/*     */   }
/*     */   
/*     */   protected ChangeListener createChangeListener() {
/* 165 */     return getHandler();
/*     */   }
/*     */   
/*     */   private Handler getHandler() {
/* 169 */     if (this.handler == null) {
/* 170 */       this.handler = new Handler();
/*     */     }
/* 172 */     return this.handler;
/*     */   }
/*     */ 
/*     */   
/*     */   public Dimension getMinimumSize(JComponent paramJComponent) {
/* 177 */     return null;
/*     */   }
/*     */   
/*     */   public Dimension getMaximumSize(JComponent paramJComponent) {
/* 181 */     return null;
/*     */   }
/*     */   
/*     */   private class Handler implements ChangeListener, ContainerListener {
/*     */     private Handler() {}
/*     */     
/*     */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/*     */       byte b;
/*     */       int i;
/* 190 */       for (b = 0, i = BasicMenuBarUI.this.menuBar.getMenuCount(); b < i; b++) {
/* 191 */         JMenu jMenu = BasicMenuBarUI.this.menuBar.getMenu(b);
/* 192 */         if (jMenu != null && jMenu.isSelected()) {
/* 193 */           BasicMenuBarUI.this.menuBar.getSelectionModel().setSelectedIndex(b);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void componentAdded(ContainerEvent param1ContainerEvent) {
/* 203 */       Component component = param1ContainerEvent.getChild();
/* 204 */       if (component instanceof JMenu)
/* 205 */         ((JMenu)component).getModel().addChangeListener(BasicMenuBarUI.this.changeListener); 
/*     */     }
/*     */     public void componentRemoved(ContainerEvent param1ContainerEvent) {
/* 208 */       Component component = param1ContainerEvent.getChild();
/* 209 */       if (component instanceof JMenu)
/* 210 */         ((JMenu)component).getModel().removeChangeListener(BasicMenuBarUI.this.changeListener); 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Actions
/*     */     extends UIAction {
/*     */     private static final String TAKE_FOCUS = "takeFocus";
/*     */     
/*     */     Actions(String param1String) {
/* 219 */       super(param1String);
/*     */     }
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 224 */       JMenuBar jMenuBar = (JMenuBar)param1ActionEvent.getSource();
/* 225 */       MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/*     */ 
/*     */       
/* 228 */       JMenu jMenu = jMenuBar.getMenu(0);
/* 229 */       if (jMenu != null) {
/* 230 */         MenuElement[] arrayOfMenuElement = new MenuElement[3];
/* 231 */         arrayOfMenuElement[0] = jMenuBar;
/* 232 */         arrayOfMenuElement[1] = jMenu;
/* 233 */         arrayOfMenuElement[2] = jMenu.getPopupMenu();
/* 234 */         menuSelectionManager.setSelectedPath(arrayOfMenuElement);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicMenuBarUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */