/*      */ package sun.awt;
/*      */ 
/*      */ import java.awt.AWTEvent;
/*      */ import java.awt.AWTException;
/*      */ import java.awt.CheckboxMenuItem;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Cursor;
/*      */ import java.awt.DefaultKeyboardFocusManager;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.EventQueue;
/*      */ import java.awt.FileDialog;
/*      */ import java.awt.Font;
/*      */ import java.awt.Frame;
/*      */ import java.awt.GraphicsConfiguration;
/*      */ import java.awt.KeyboardFocusManager;
/*      */ import java.awt.Menu;
/*      */ import java.awt.MenuBar;
/*      */ import java.awt.MenuComponent;
/*      */ import java.awt.MenuContainer;
/*      */ import java.awt.MenuItem;
/*      */ import java.awt.MenuShortcut;
/*      */ import java.awt.Point;
/*      */ import java.awt.PopupMenu;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.ScrollPaneAdjustable;
/*      */ import java.awt.Shape;
/*      */ import java.awt.SystemColor;
/*      */ import java.awt.SystemTray;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.TrayIcon;
/*      */ import java.awt.Window;
/*      */ import java.awt.event.InputEvent;
/*      */ import java.awt.event.InvocationEvent;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.peer.ComponentPeer;
/*      */ import java.io.File;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.security.AccessControlContext;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Vector;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import sun.misc.Unsafe;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class AWTAccessor
/*      */ {
/*   58 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ComponentAccessor componentAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ContainerAccessor containerAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static WindowAccessor windowAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static AWTEventAccessor awtEventAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static InputEventAccessor inputEventAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static MouseEventAccessor mouseEventAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static FrameAccessor frameAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static KeyboardFocusManagerAccessor kfmAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static MenuComponentAccessor menuComponentAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static EventQueueAccessor eventQueueAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static PopupMenuAccessor popupMenuAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static FileDialogAccessor fileDialogAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ScrollPaneAdjustableAccessor scrollPaneAdjustableAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static CheckboxMenuItemAccessor checkboxMenuItemAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static CursorAccessor cursorAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static MenuBarAccessor menuBarAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static MenuItemAccessor menuItemAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static MenuAccessor menuAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static KeyEventAccessor keyEventAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ClientPropertyKeyAccessor clientPropertyKeyAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static SystemTrayAccessor systemTrayAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static TrayIconAccessor trayIconAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static DefaultKeyboardFocusManagerAccessor defaultKeyboardFocusManagerAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static SequencedEventAccessor sequencedEventAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ToolkitAccessor toolkitAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static InvocationEventAccessor invocationEventAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static SystemColorAccessor systemColorAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static AccessibleContextAccessor accessibleContextAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setComponentAccessor(ComponentAccessor paramComponentAccessor) {
/*  817 */     componentAccessor = paramComponentAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ComponentAccessor getComponentAccessor() {
/*  824 */     if (componentAccessor == null) {
/*  825 */       unsafe.ensureClassInitialized(Component.class);
/*      */     }
/*      */     
/*  828 */     return componentAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setContainerAccessor(ContainerAccessor paramContainerAccessor) {
/*  835 */     containerAccessor = paramContainerAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ContainerAccessor getContainerAccessor() {
/*  842 */     if (containerAccessor == null) {
/*  843 */       unsafe.ensureClassInitialized(Container.class);
/*      */     }
/*      */     
/*  846 */     return containerAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setWindowAccessor(WindowAccessor paramWindowAccessor) {
/*  853 */     windowAccessor = paramWindowAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static WindowAccessor getWindowAccessor() {
/*  860 */     if (windowAccessor == null) {
/*  861 */       unsafe.ensureClassInitialized(Window.class);
/*      */     }
/*  863 */     return windowAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setAWTEventAccessor(AWTEventAccessor paramAWTEventAccessor) {
/*  870 */     awtEventAccessor = paramAWTEventAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AWTEventAccessor getAWTEventAccessor() {
/*  877 */     if (awtEventAccessor == null) {
/*  878 */       unsafe.ensureClassInitialized(AWTEvent.class);
/*      */     }
/*  880 */     return awtEventAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setInputEventAccessor(InputEventAccessor paramInputEventAccessor) {
/*  887 */     inputEventAccessor = paramInputEventAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static InputEventAccessor getInputEventAccessor() {
/*  894 */     if (inputEventAccessor == null) {
/*  895 */       unsafe.ensureClassInitialized(InputEvent.class);
/*      */     }
/*  897 */     return inputEventAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setMouseEventAccessor(MouseEventAccessor paramMouseEventAccessor) {
/*  904 */     mouseEventAccessor = paramMouseEventAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MouseEventAccessor getMouseEventAccessor() {
/*  911 */     if (mouseEventAccessor == null) {
/*  912 */       unsafe.ensureClassInitialized(MouseEvent.class);
/*      */     }
/*  914 */     return mouseEventAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setFrameAccessor(FrameAccessor paramFrameAccessor) {
/*  921 */     frameAccessor = paramFrameAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FrameAccessor getFrameAccessor() {
/*  928 */     if (frameAccessor == null) {
/*  929 */       unsafe.ensureClassInitialized(Frame.class);
/*      */     }
/*  931 */     return frameAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setKeyboardFocusManagerAccessor(KeyboardFocusManagerAccessor paramKeyboardFocusManagerAccessor) {
/*  938 */     kfmAccessor = paramKeyboardFocusManagerAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static KeyboardFocusManagerAccessor getKeyboardFocusManagerAccessor() {
/*  945 */     if (kfmAccessor == null) {
/*  946 */       unsafe.ensureClassInitialized(KeyboardFocusManager.class);
/*      */     }
/*  948 */     return kfmAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setMenuComponentAccessor(MenuComponentAccessor paramMenuComponentAccessor) {
/*  955 */     menuComponentAccessor = paramMenuComponentAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MenuComponentAccessor getMenuComponentAccessor() {
/*  962 */     if (menuComponentAccessor == null) {
/*  963 */       unsafe.ensureClassInitialized(MenuComponent.class);
/*      */     }
/*  965 */     return menuComponentAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setEventQueueAccessor(EventQueueAccessor paramEventQueueAccessor) {
/*  972 */     eventQueueAccessor = paramEventQueueAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static EventQueueAccessor getEventQueueAccessor() {
/*  979 */     if (eventQueueAccessor == null) {
/*  980 */       unsafe.ensureClassInitialized(EventQueue.class);
/*      */     }
/*  982 */     return eventQueueAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setPopupMenuAccessor(PopupMenuAccessor paramPopupMenuAccessor) {
/*  989 */     popupMenuAccessor = paramPopupMenuAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static PopupMenuAccessor getPopupMenuAccessor() {
/*  996 */     if (popupMenuAccessor == null) {
/*  997 */       unsafe.ensureClassInitialized(PopupMenu.class);
/*      */     }
/*  999 */     return popupMenuAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setFileDialogAccessor(FileDialogAccessor paramFileDialogAccessor) {
/* 1006 */     fileDialogAccessor = paramFileDialogAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FileDialogAccessor getFileDialogAccessor() {
/* 1013 */     if (fileDialogAccessor == null) {
/* 1014 */       unsafe.ensureClassInitialized(FileDialog.class);
/*      */     }
/* 1016 */     return fileDialogAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setScrollPaneAdjustableAccessor(ScrollPaneAdjustableAccessor paramScrollPaneAdjustableAccessor) {
/* 1023 */     scrollPaneAdjustableAccessor = paramScrollPaneAdjustableAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ScrollPaneAdjustableAccessor getScrollPaneAdjustableAccessor() {
/* 1031 */     if (scrollPaneAdjustableAccessor == null) {
/* 1032 */       unsafe.ensureClassInitialized(ScrollPaneAdjustable.class);
/*      */     }
/* 1034 */     return scrollPaneAdjustableAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setCheckboxMenuItemAccessor(CheckboxMenuItemAccessor paramCheckboxMenuItemAccessor) {
/* 1041 */     checkboxMenuItemAccessor = paramCheckboxMenuItemAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CheckboxMenuItemAccessor getCheckboxMenuItemAccessor() {
/* 1048 */     if (checkboxMenuItemAccessor == null) {
/* 1049 */       unsafe.ensureClassInitialized(CheckboxMenuItemAccessor.class);
/*      */     }
/* 1051 */     return checkboxMenuItemAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setCursorAccessor(CursorAccessor paramCursorAccessor) {
/* 1058 */     cursorAccessor = paramCursorAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CursorAccessor getCursorAccessor() {
/* 1065 */     if (cursorAccessor == null) {
/* 1066 */       unsafe.ensureClassInitialized(CursorAccessor.class);
/*      */     }
/* 1068 */     return cursorAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setMenuBarAccessor(MenuBarAccessor paramMenuBarAccessor) {
/* 1075 */     menuBarAccessor = paramMenuBarAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MenuBarAccessor getMenuBarAccessor() {
/* 1082 */     if (menuBarAccessor == null) {
/* 1083 */       unsafe.ensureClassInitialized(MenuBarAccessor.class);
/*      */     }
/* 1085 */     return menuBarAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setMenuItemAccessor(MenuItemAccessor paramMenuItemAccessor) {
/* 1092 */     menuItemAccessor = paramMenuItemAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MenuItemAccessor getMenuItemAccessor() {
/* 1099 */     if (menuItemAccessor == null) {
/* 1100 */       unsafe.ensureClassInitialized(MenuItemAccessor.class);
/*      */     }
/* 1102 */     return menuItemAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setMenuAccessor(MenuAccessor paramMenuAccessor) {
/* 1109 */     menuAccessor = paramMenuAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MenuAccessor getMenuAccessor() {
/* 1116 */     if (menuAccessor == null) {
/* 1117 */       unsafe.ensureClassInitialized(MenuAccessor.class);
/*      */     }
/* 1119 */     return menuAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setKeyEventAccessor(KeyEventAccessor paramKeyEventAccessor) {
/* 1126 */     keyEventAccessor = paramKeyEventAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static KeyEventAccessor getKeyEventAccessor() {
/* 1133 */     if (keyEventAccessor == null) {
/* 1134 */       unsafe.ensureClassInitialized(KeyEventAccessor.class);
/*      */     }
/* 1136 */     return keyEventAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setClientPropertyKeyAccessor(ClientPropertyKeyAccessor paramClientPropertyKeyAccessor) {
/* 1143 */     clientPropertyKeyAccessor = paramClientPropertyKeyAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ClientPropertyKeyAccessor getClientPropertyKeyAccessor() {
/* 1150 */     if (clientPropertyKeyAccessor == null) {
/* 1151 */       unsafe.ensureClassInitialized(ClientPropertyKeyAccessor.class);
/*      */     }
/* 1153 */     return clientPropertyKeyAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setSystemTrayAccessor(SystemTrayAccessor paramSystemTrayAccessor) {
/* 1160 */     systemTrayAccessor = paramSystemTrayAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SystemTrayAccessor getSystemTrayAccessor() {
/* 1167 */     if (systemTrayAccessor == null) {
/* 1168 */       unsafe.ensureClassInitialized(SystemTrayAccessor.class);
/*      */     }
/* 1170 */     return systemTrayAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setTrayIconAccessor(TrayIconAccessor paramTrayIconAccessor) {
/* 1177 */     trayIconAccessor = paramTrayIconAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static TrayIconAccessor getTrayIconAccessor() {
/* 1184 */     if (trayIconAccessor == null) {
/* 1185 */       unsafe.ensureClassInitialized(TrayIconAccessor.class);
/*      */     }
/* 1187 */     return trayIconAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setDefaultKeyboardFocusManagerAccessor(DefaultKeyboardFocusManagerAccessor paramDefaultKeyboardFocusManagerAccessor) {
/* 1194 */     defaultKeyboardFocusManagerAccessor = paramDefaultKeyboardFocusManagerAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DefaultKeyboardFocusManagerAccessor getDefaultKeyboardFocusManagerAccessor() {
/* 1201 */     if (defaultKeyboardFocusManagerAccessor == null) {
/* 1202 */       unsafe.ensureClassInitialized(DefaultKeyboardFocusManagerAccessor.class);
/*      */     }
/* 1204 */     return defaultKeyboardFocusManagerAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setSequencedEventAccessor(SequencedEventAccessor paramSequencedEventAccessor) {
/* 1210 */     sequencedEventAccessor = paramSequencedEventAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SequencedEventAccessor getSequencedEventAccessor() {
/* 1220 */     return sequencedEventAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setToolkitAccessor(ToolkitAccessor paramToolkitAccessor) {
/* 1227 */     toolkitAccessor = paramToolkitAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ToolkitAccessor getToolkitAccessor() {
/* 1234 */     if (toolkitAccessor == null) {
/* 1235 */       unsafe.ensureClassInitialized(Toolkit.class);
/*      */     }
/*      */     
/* 1238 */     return toolkitAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setInvocationEventAccessor(InvocationEventAccessor paramInvocationEventAccessor) {
/* 1245 */     invocationEventAccessor = paramInvocationEventAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static InvocationEventAccessor getInvocationEventAccessor() {
/* 1252 */     return invocationEventAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SystemColorAccessor getSystemColorAccessor() {
/* 1259 */     if (systemColorAccessor == null) {
/* 1260 */       unsafe.ensureClassInitialized(SystemColor.class);
/*      */     }
/*      */     
/* 1263 */     return systemColorAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setSystemColorAccessor(SystemColorAccessor paramSystemColorAccessor) {
/* 1270 */     systemColorAccessor = paramSystemColorAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AccessibleContextAccessor getAccessibleContextAccessor() {
/* 1277 */     if (accessibleContextAccessor == null) {
/* 1278 */       unsafe.ensureClassInitialized(AccessibleContext.class);
/*      */     }
/* 1280 */     return accessibleContextAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setAccessibleContextAccessor(AccessibleContextAccessor paramAccessibleContextAccessor) {
/* 1287 */     accessibleContextAccessor = paramAccessibleContextAccessor;
/*      */   }
/*      */   
/*      */   public static interface AccessibleContextAccessor {
/*      */     void setAppContext(AccessibleContext param1AccessibleContext, AppContext param1AppContext);
/*      */     
/*      */     AppContext getAppContext(AccessibleContext param1AccessibleContext);
/*      */   }
/*      */   
/*      */   public static interface SystemColorAccessor {
/*      */     void updateSystemColors();
/*      */   }
/*      */   
/*      */   public static interface InvocationEventAccessor {
/*      */     void dispose(InvocationEvent param1InvocationEvent);
/*      */   }
/*      */   
/*      */   public static interface ToolkitAccessor {
/*      */     void setPlatformResources(ResourceBundle param1ResourceBundle);
/*      */   }
/*      */   
/*      */   public static interface SequencedEventAccessor {
/*      */     AWTEvent getNested(AWTEvent param1AWTEvent);
/*      */     
/*      */     boolean isSequencedEvent(AWTEvent param1AWTEvent);
/*      */   }
/*      */   
/*      */   public static interface DefaultKeyboardFocusManagerAccessor {
/*      */     void consumeNextKeyTyped(DefaultKeyboardFocusManager param1DefaultKeyboardFocusManager, KeyEvent param1KeyEvent);
/*      */   }
/*      */   
/*      */   public static interface TrayIconAccessor {
/*      */     void addNotify(TrayIcon param1TrayIcon) throws AWTException;
/*      */     
/*      */     void removeNotify(TrayIcon param1TrayIcon);
/*      */   }
/*      */   
/*      */   public static interface SystemTrayAccessor {
/*      */     void firePropertyChange(SystemTray param1SystemTray, String param1String, Object param1Object1, Object param1Object2);
/*      */   }
/*      */   
/*      */   public static interface ClientPropertyKeyAccessor {
/*      */     Object getJComponent_TRANSFER_HANDLER();
/*      */   }
/*      */   
/*      */   public static interface KeyEventAccessor {
/*      */     void setRawCode(KeyEvent param1KeyEvent, long param1Long);
/*      */     
/*      */     void setPrimaryLevelUnicode(KeyEvent param1KeyEvent, long param1Long);
/*      */     
/*      */     void setExtendedKeyCode(KeyEvent param1KeyEvent, long param1Long);
/*      */     
/*      */     Component getOriginalSource(KeyEvent param1KeyEvent);
/*      */   }
/*      */   
/*      */   public static interface MenuAccessor {
/*      */     Vector getItems(Menu param1Menu);
/*      */   }
/*      */   
/*      */   public static interface MenuItemAccessor {
/*      */     boolean isEnabled(MenuItem param1MenuItem);
/*      */     
/*      */     String getActionCommandImpl(MenuItem param1MenuItem);
/*      */     
/*      */     boolean isItemEnabled(MenuItem param1MenuItem);
/*      */     
/*      */     String getLabel(MenuItem param1MenuItem);
/*      */     
/*      */     MenuShortcut getShortcut(MenuItem param1MenuItem);
/*      */   }
/*      */   
/*      */   public static interface MenuBarAccessor {
/*      */     Menu getHelpMenu(MenuBar param1MenuBar);
/*      */     
/*      */     Vector getMenus(MenuBar param1MenuBar);
/*      */   }
/*      */   
/*      */   public static interface CursorAccessor {
/*      */     long getPData(Cursor param1Cursor);
/*      */     
/*      */     void setPData(Cursor param1Cursor, long param1Long);
/*      */     
/*      */     int getType(Cursor param1Cursor);
/*      */   }
/*      */   
/*      */   public static interface CheckboxMenuItemAccessor {
/*      */     boolean getState(CheckboxMenuItem param1CheckboxMenuItem);
/*      */   }
/*      */   
/*      */   public static interface ScrollPaneAdjustableAccessor {
/*      */     void setTypedValue(ScrollPaneAdjustable param1ScrollPaneAdjustable, int param1Int1, int param1Int2);
/*      */   }
/*      */   
/*      */   public static interface FileDialogAccessor {
/*      */     void setFiles(FileDialog param1FileDialog, File[] param1ArrayOfFile);
/*      */     
/*      */     void setFile(FileDialog param1FileDialog, String param1String);
/*      */     
/*      */     void setDirectory(FileDialog param1FileDialog, String param1String);
/*      */     
/*      */     boolean isMultipleMode(FileDialog param1FileDialog);
/*      */   }
/*      */   
/*      */   public static interface PopupMenuAccessor {
/*      */     boolean isTrayIconPopup(PopupMenu param1PopupMenu);
/*      */   }
/*      */   
/*      */   public static interface EventQueueAccessor {
/*      */     Thread getDispatchThread(EventQueue param1EventQueue);
/*      */     
/*      */     boolean isDispatchThreadImpl(EventQueue param1EventQueue);
/*      */     
/*      */     void removeSourceEvents(EventQueue param1EventQueue, Object param1Object, boolean param1Boolean);
/*      */     
/*      */     boolean noEvents(EventQueue param1EventQueue);
/*      */     
/*      */     void wakeup(EventQueue param1EventQueue, boolean param1Boolean);
/*      */     
/*      */     void invokeAndWait(Object param1Object, Runnable param1Runnable) throws InterruptedException, InvocationTargetException;
/*      */     
/*      */     void setFwDispatcher(EventQueue param1EventQueue, FwDispatcher param1FwDispatcher);
/*      */     
/*      */     long getMostRecentEventTime(EventQueue param1EventQueue);
/*      */   }
/*      */   
/*      */   public static interface MenuComponentAccessor {
/*      */     AppContext getAppContext(MenuComponent param1MenuComponent);
/*      */     
/*      */     void setAppContext(MenuComponent param1MenuComponent, AppContext param1AppContext);
/*      */     
/*      */     MenuContainer getParent(MenuComponent param1MenuComponent);
/*      */     
/*      */     Font getFont_NoClientCode(MenuComponent param1MenuComponent);
/*      */     
/*      */     <T extends java.awt.peer.MenuComponentPeer> T getPeer(MenuComponent param1MenuComponent);
/*      */   }
/*      */   
/*      */   public static interface KeyboardFocusManagerAccessor {
/*      */     int shouldNativelyFocusHeavyweight(Component param1Component1, Component param1Component2, boolean param1Boolean1, boolean param1Boolean2, long param1Long, CausedFocusEvent.Cause param1Cause);
/*      */     
/*      */     boolean processSynchronousLightweightTransfer(Component param1Component1, Component param1Component2, boolean param1Boolean1, boolean param1Boolean2, long param1Long);
/*      */     
/*      */     void removeLastFocusRequest(Component param1Component);
/*      */     
/*      */     void setMostRecentFocusOwner(Window param1Window, Component param1Component);
/*      */     
/*      */     KeyboardFocusManager getCurrentKeyboardFocusManager(AppContext param1AppContext);
/*      */     
/*      */     Container getCurrentFocusCycleRoot();
/*      */   }
/*      */   
/*      */   public static interface FrameAccessor {
/*      */     void setExtendedState(Frame param1Frame, int param1Int);
/*      */     
/*      */     int getExtendedState(Frame param1Frame);
/*      */     
/*      */     Rectangle getMaximizedBounds(Frame param1Frame);
/*      */   }
/*      */   
/*      */   public static interface MouseEventAccessor {
/*      */     boolean isCausedByTouchEvent(MouseEvent param1MouseEvent);
/*      */     
/*      */     void setCausedByTouchEvent(MouseEvent param1MouseEvent, boolean param1Boolean);
/*      */   }
/*      */   
/*      */   public static interface InputEventAccessor {
/*      */     int[] getButtonDownMasks();
/*      */   }
/*      */   
/*      */   public static interface AWTEventAccessor {
/*      */     void setPosted(AWTEvent param1AWTEvent);
/*      */     
/*      */     void setSystemGenerated(AWTEvent param1AWTEvent);
/*      */     
/*      */     boolean isSystemGenerated(AWTEvent param1AWTEvent);
/*      */     
/*      */     AccessControlContext getAccessControlContext(AWTEvent param1AWTEvent);
/*      */     
/*      */     byte[] getBData(AWTEvent param1AWTEvent);
/*      */     
/*      */     void setBData(AWTEvent param1AWTEvent, byte[] param1ArrayOfbyte);
/*      */   }
/*      */   
/*      */   public static interface WindowAccessor {
/*      */     float getOpacity(Window param1Window);
/*      */     
/*      */     void setOpacity(Window param1Window, float param1Float);
/*      */     
/*      */     Shape getShape(Window param1Window);
/*      */     
/*      */     void setShape(Window param1Window, Shape param1Shape);
/*      */     
/*      */     void setOpaque(Window param1Window, boolean param1Boolean);
/*      */     
/*      */     void updateWindow(Window param1Window);
/*      */     
/*      */     Dimension getSecurityWarningSize(Window param1Window);
/*      */     
/*      */     void setSecurityWarningSize(Window param1Window, int param1Int1, int param1Int2);
/*      */     
/*      */     void setSecurityWarningPosition(Window param1Window, Point2D param1Point2D, float param1Float1, float param1Float2);
/*      */     
/*      */     Point2D calculateSecurityWarningPosition(Window param1Window, double param1Double1, double param1Double2, double param1Double3, double param1Double4);
/*      */     
/*      */     void setLWRequestStatus(Window param1Window, boolean param1Boolean);
/*      */     
/*      */     boolean isAutoRequestFocus(Window param1Window);
/*      */     
/*      */     boolean isTrayIconWindow(Window param1Window);
/*      */     
/*      */     void setTrayIconWindow(Window param1Window, boolean param1Boolean);
/*      */     
/*      */     Window[] getOwnedWindows(Window param1Window);
/*      */   }
/*      */   
/*      */   public static interface ContainerAccessor {
/*      */     void validateUnconditionally(Container param1Container);
/*      */     
/*      */     Component findComponentAt(Container param1Container, int param1Int1, int param1Int2, boolean param1Boolean);
/*      */   }
/*      */   
/*      */   public static interface ComponentAccessor {
/*      */     void setBackgroundEraseDisabled(Component param1Component, boolean param1Boolean);
/*      */     
/*      */     boolean getBackgroundEraseDisabled(Component param1Component);
/*      */     
/*      */     Rectangle getBounds(Component param1Component);
/*      */     
/*      */     void setMixingCutoutShape(Component param1Component, Shape param1Shape);
/*      */     
/*      */     void setGraphicsConfiguration(Component param1Component, GraphicsConfiguration param1GraphicsConfiguration);
/*      */     
/*      */     boolean requestFocus(Component param1Component, CausedFocusEvent.Cause param1Cause);
/*      */     
/*      */     boolean canBeFocusOwner(Component param1Component);
/*      */     
/*      */     boolean isVisible(Component param1Component);
/*      */     
/*      */     void setRequestFocusController(RequestFocusController param1RequestFocusController);
/*      */     
/*      */     AppContext getAppContext(Component param1Component);
/*      */     
/*      */     void setAppContext(Component param1Component, AppContext param1AppContext);
/*      */     
/*      */     Container getParent(Component param1Component);
/*      */     
/*      */     void setParent(Component param1Component, Container param1Container);
/*      */     
/*      */     void setSize(Component param1Component, int param1Int1, int param1Int2);
/*      */     
/*      */     Point getLocation(Component param1Component);
/*      */     
/*      */     void setLocation(Component param1Component, int param1Int1, int param1Int2);
/*      */     
/*      */     boolean isEnabled(Component param1Component);
/*      */     
/*      */     boolean isDisplayable(Component param1Component);
/*      */     
/*      */     Cursor getCursor(Component param1Component);
/*      */     
/*      */     ComponentPeer getPeer(Component param1Component);
/*      */     
/*      */     void setPeer(Component param1Component, ComponentPeer param1ComponentPeer);
/*      */     
/*      */     boolean isLightweight(Component param1Component);
/*      */     
/*      */     boolean getIgnoreRepaint(Component param1Component);
/*      */     
/*      */     int getWidth(Component param1Component);
/*      */     
/*      */     int getHeight(Component param1Component);
/*      */     
/*      */     int getX(Component param1Component);
/*      */     
/*      */     int getY(Component param1Component);
/*      */     
/*      */     Color getForeground(Component param1Component);
/*      */     
/*      */     Color getBackground(Component param1Component);
/*      */     
/*      */     void setBackground(Component param1Component, Color param1Color);
/*      */     
/*      */     Font getFont(Component param1Component);
/*      */     
/*      */     void processEvent(Component param1Component, AWTEvent param1AWTEvent);
/*      */     
/*      */     AccessControlContext getAccessControlContext(Component param1Component);
/*      */     
/*      */     void revalidateSynchronously(Component param1Component);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/AWTAccessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */