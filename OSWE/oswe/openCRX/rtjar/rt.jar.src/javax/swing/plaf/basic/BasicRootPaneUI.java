/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.KeyboardFocusManager;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.ComponentInputMap;
/*     */ import javax.swing.InputMap;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.MenuElement;
/*     */ import javax.swing.MenuSelectionManager;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.plaf.ComponentInputMapUIResource;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.RootPaneUI;
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
/*     */ public class BasicRootPaneUI
/*     */   extends RootPaneUI
/*     */   implements PropertyChangeListener
/*     */ {
/*  49 */   private static RootPaneUI rootPaneUI = new BasicRootPaneUI();
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  52 */     return rootPaneUI;
/*     */   }
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/*  56 */     installDefaults((JRootPane)paramJComponent);
/*  57 */     installComponents((JRootPane)paramJComponent);
/*  58 */     installListeners((JRootPane)paramJComponent);
/*  59 */     installKeyboardActions((JRootPane)paramJComponent);
/*     */   }
/*     */ 
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/*  64 */     uninstallDefaults((JRootPane)paramJComponent);
/*  65 */     uninstallComponents((JRootPane)paramJComponent);
/*  66 */     uninstallListeners((JRootPane)paramJComponent);
/*  67 */     uninstallKeyboardActions((JRootPane)paramJComponent);
/*     */   }
/*     */   
/*     */   protected void installDefaults(JRootPane paramJRootPane) {
/*  71 */     LookAndFeel.installProperty(paramJRootPane, "opaque", Boolean.FALSE);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void installComponents(JRootPane paramJRootPane) {}
/*     */   
/*     */   protected void installListeners(JRootPane paramJRootPane) {
/*  78 */     paramJRootPane.addPropertyChangeListener(this);
/*     */   }
/*     */   
/*     */   protected void installKeyboardActions(JRootPane paramJRootPane) {
/*  82 */     InputMap inputMap = getInputMap(2, paramJRootPane);
/*  83 */     SwingUtilities.replaceUIInputMap(paramJRootPane, 2, inputMap);
/*     */     
/*  85 */     inputMap = getInputMap(1, paramJRootPane);
/*     */     
/*  87 */     SwingUtilities.replaceUIInputMap(paramJRootPane, 1, inputMap);
/*     */ 
/*     */     
/*  90 */     LazyActionMap.installLazyActionMap(paramJRootPane, BasicRootPaneUI.class, "RootPane.actionMap");
/*     */     
/*  92 */     updateDefaultButtonBindings(paramJRootPane);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults(JRootPane paramJRootPane) {}
/*     */ 
/*     */   
/*     */   protected void uninstallComponents(JRootPane paramJRootPane) {}
/*     */   
/*     */   protected void uninstallListeners(JRootPane paramJRootPane) {
/* 102 */     paramJRootPane.removePropertyChangeListener(this);
/*     */   }
/*     */   
/*     */   protected void uninstallKeyboardActions(JRootPane paramJRootPane) {
/* 106 */     SwingUtilities.replaceUIInputMap(paramJRootPane, 2, null);
/*     */     
/* 108 */     SwingUtilities.replaceUIActionMap(paramJRootPane, null);
/*     */   }
/*     */   
/*     */   InputMap getInputMap(int paramInt, JComponent paramJComponent) {
/* 112 */     if (paramInt == 1) {
/* 113 */       return (InputMap)DefaultLookup.get(paramJComponent, this, "RootPane.ancestorInputMap");
/*     */     }
/*     */ 
/*     */     
/* 117 */     if (paramInt == 2) {
/* 118 */       return createInputMap(paramInt, paramJComponent);
/*     */     }
/* 120 */     return null;
/*     */   }
/*     */   
/*     */   ComponentInputMap createInputMap(int paramInt, JComponent paramJComponent) {
/* 124 */     return new RootPaneInputMap(paramJComponent);
/*     */   }
/*     */   
/*     */   static void loadActionMap(LazyActionMap paramLazyActionMap) {
/* 128 */     paramLazyActionMap.put(new Actions("press"));
/* 129 */     paramLazyActionMap.put(new Actions("release"));
/* 130 */     paramLazyActionMap.put(new Actions("postPopup"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void updateDefaultButtonBindings(JRootPane paramJRootPane) {
/* 139 */     InputMap inputMap = SwingUtilities.getUIInputMap(paramJRootPane, 2);
/*     */     
/* 141 */     while (inputMap != null && !(inputMap instanceof RootPaneInputMap)) {
/* 142 */       inputMap = inputMap.getParent();
/*     */     }
/* 144 */     if (inputMap != null) {
/* 145 */       inputMap.clear();
/* 146 */       if (paramJRootPane.getDefaultButton() != null) {
/* 147 */         Object[] arrayOfObject = (Object[])DefaultLookup.get(paramJRootPane, this, "RootPane.defaultButtonWindowKeyBindings");
/*     */         
/* 149 */         if (arrayOfObject != null) {
/* 150 */           LookAndFeel.loadKeyBindings(inputMap, arrayOfObject);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 162 */     if (paramPropertyChangeEvent.getPropertyName().equals("defaultButton")) {
/* 163 */       JRootPane jRootPane = (JRootPane)paramPropertyChangeEvent.getSource();
/* 164 */       updateDefaultButtonBindings(jRootPane);
/* 165 */       if (jRootPane.getClientProperty("temporaryDefaultButton") == null)
/* 166 */         jRootPane.putClientProperty("initialDefaultButton", paramPropertyChangeEvent.getNewValue()); 
/*     */     } 
/*     */   }
/*     */   
/*     */   static class Actions
/*     */     extends UIAction
/*     */   {
/*     */     public static final String PRESS = "press";
/*     */     public static final String RELEASE = "release";
/*     */     public static final String POST_POPUP = "postPopup";
/*     */     
/*     */     Actions(String param1String) {
/* 178 */       super(param1String);
/*     */     }
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 182 */       JRootPane jRootPane = (JRootPane)param1ActionEvent.getSource();
/* 183 */       JButton jButton = jRootPane.getDefaultButton();
/* 184 */       String str = getName();
/*     */       
/* 186 */       if (str == "postPopup") {
/*     */ 
/*     */         
/* 189 */         Component component = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
/*     */         
/* 191 */         if (component instanceof JComponent) {
/* 192 */           JComponent jComponent = (JComponent)component;
/* 193 */           JPopupMenu jPopupMenu = jComponent.getComponentPopupMenu();
/* 194 */           if (jPopupMenu != null) {
/* 195 */             Point point = jComponent.getPopupLocation((MouseEvent)null);
/* 196 */             if (point == null) {
/* 197 */               Rectangle rectangle = jComponent.getVisibleRect();
/* 198 */               point = new Point(rectangle.x + rectangle.width / 2, rectangle.y + rectangle.height / 2);
/*     */             } 
/*     */             
/* 201 */             jPopupMenu.show(component, point.x, point.y);
/*     */           }
/*     */         
/*     */         } 
/* 205 */       } else if (jButton != null && 
/* 206 */         SwingUtilities.getRootPane(jButton) == jRootPane && 
/* 207 */         str == "press") {
/* 208 */         jButton.doClick(20);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEnabled(Object param1Object) {
/* 214 */       String str = getName();
/* 215 */       if (str == "postPopup") {
/*     */ 
/*     */         
/* 218 */         MenuElement[] arrayOfMenuElement = MenuSelectionManager.defaultManager().getSelectedPath();
/* 219 */         if (arrayOfMenuElement != null && arrayOfMenuElement.length != 0) {
/* 220 */           return false;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 226 */         Component component = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
/* 227 */         if (component instanceof JComponent) {
/* 228 */           JComponent jComponent = (JComponent)component;
/* 229 */           return (jComponent.getComponentPopupMenu() != null);
/*     */         } 
/*     */         
/* 232 */         return false;
/*     */       } 
/*     */       
/* 235 */       if (param1Object != null && param1Object instanceof JRootPane) {
/* 236 */         JButton jButton = ((JRootPane)param1Object).getDefaultButton();
/* 237 */         return (jButton != null && jButton.getModel().isEnabled());
/*     */       } 
/* 239 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class RootPaneInputMap extends ComponentInputMapUIResource {
/*     */     public RootPaneInputMap(JComponent param1JComponent) {
/* 245 */       super(param1JComponent);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicRootPaneUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */