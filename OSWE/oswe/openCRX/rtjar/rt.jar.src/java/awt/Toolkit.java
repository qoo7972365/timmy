/*      */ package java.awt;
/*      */ 
/*      */ import java.awt.datatransfer.Clipboard;
/*      */ import java.awt.dnd.DragGestureEvent;
/*      */ import java.awt.dnd.DragGestureListener;
/*      */ import java.awt.dnd.DragSource;
/*      */ import java.awt.dnd.InvalidDnDOperationException;
/*      */ import java.awt.dnd.peer.DragSourceContextPeer;
/*      */ import java.awt.event.AWTEventListener;
/*      */ import java.awt.event.AWTEventListenerProxy;
/*      */ import java.awt.font.TextAttribute;
/*      */ import java.awt.im.InputMethodHighlight;
/*      */ import java.awt.image.ColorModel;
/*      */ import java.awt.image.ImageObserver;
/*      */ import java.awt.image.ImageProducer;
/*      */ import java.awt.peer.ButtonPeer;
/*      */ import java.awt.peer.CanvasPeer;
/*      */ import java.awt.peer.CheckboxMenuItemPeer;
/*      */ import java.awt.peer.CheckboxPeer;
/*      */ import java.awt.peer.ChoicePeer;
/*      */ import java.awt.peer.DesktopPeer;
/*      */ import java.awt.peer.DialogPeer;
/*      */ import java.awt.peer.FileDialogPeer;
/*      */ import java.awt.peer.FontPeer;
/*      */ import java.awt.peer.FramePeer;
/*      */ import java.awt.peer.LabelPeer;
/*      */ import java.awt.peer.LightweightPeer;
/*      */ import java.awt.peer.ListPeer;
/*      */ import java.awt.peer.MenuBarPeer;
/*      */ import java.awt.peer.MenuItemPeer;
/*      */ import java.awt.peer.MenuPeer;
/*      */ import java.awt.peer.MouseInfoPeer;
/*      */ import java.awt.peer.PanelPeer;
/*      */ import java.awt.peer.PopupMenuPeer;
/*      */ import java.awt.peer.ScrollPanePeer;
/*      */ import java.awt.peer.ScrollbarPeer;
/*      */ import java.awt.peer.TextAreaPeer;
/*      */ import java.awt.peer.TextFieldPeer;
/*      */ import java.awt.peer.WindowPeer;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.beans.PropertyChangeSupport;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.net.URL;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.EventListener;
/*      */ import java.util.HashMap;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.MissingResourceException;
/*      */ import java.util.Properties;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.WeakHashMap;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.HeadlessToolkit;
/*      */ import sun.awt.NullComponentPeer;
/*      */ import sun.awt.PeerEvent;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.security.util.SecurityConstants;
/*      */ import sun.util.CoreResourceBundleControl;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class Toolkit
/*      */ {
/*      */   private static LightweightPeer lightweightMarker;
/*      */   private static Toolkit toolkit;
/*      */   private static String atNames;
/*      */   private static ResourceBundle resources;
/*      */   private static ResourceBundle platformResources;
/*      */   
/*      */   protected MouseInfoPeer getMouseInfoPeer() {
/*  415 */     throw new UnsupportedOperationException("Not implemented");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected LightweightPeer createComponent(Component paramComponent) {
/*  428 */     if (lightweightMarker == null) {
/*  429 */       lightweightMarker = new NullComponentPeer();
/*      */     }
/*  431 */     return lightweightMarker;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void loadSystemColors(int[] paramArrayOfint) throws HeadlessException {
/*  464 */     GraphicsEnvironment.checkHeadless();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDynamicLayout(boolean paramBoolean) throws HeadlessException {
/*  499 */     GraphicsEnvironment.checkHeadless();
/*  500 */     if (this != getDefaultToolkit()) {
/*  501 */       getDefaultToolkit().setDynamicLayout(paramBoolean);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isDynamicLayoutSet() throws HeadlessException {
/*  526 */     GraphicsEnvironment.checkHeadless();
/*      */     
/*  528 */     if (this != getDefaultToolkit()) {
/*  529 */       return getDefaultToolkit().isDynamicLayoutSet();
/*      */     }
/*  531 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDynamicLayoutActive() throws HeadlessException {
/*  563 */     GraphicsEnvironment.checkHeadless();
/*      */     
/*  565 */     if (this != getDefaultToolkit()) {
/*  566 */       return getDefaultToolkit().isDynamicLayoutActive();
/*      */     }
/*  568 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Insets getScreenInsets(GraphicsConfiguration paramGraphicsConfiguration) throws HeadlessException {
/*  608 */     GraphicsEnvironment.checkHeadless();
/*  609 */     if (this != getDefaultToolkit()) {
/*  610 */       return getDefaultToolkit().getScreenInsets(paramGraphicsConfiguration);
/*      */     }
/*  612 */     return new Insets(0, 0, 0, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void initAssistiveTechnologies() {
/*  703 */     final String sep = File.separator;
/*  704 */     final Properties properties = new Properties();
/*      */ 
/*      */     
/*  707 */     atNames = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*      */         {
/*      */ 
/*      */           
/*      */           public String run()
/*      */           {
/*      */             try {
/*  714 */               File file = new File(System.getProperty("user.home") + sep + ".accessibility.properties");
/*      */               
/*  716 */               FileInputStream fileInputStream = new FileInputStream(file);
/*      */ 
/*      */ 
/*      */               
/*  720 */               properties.load(fileInputStream);
/*  721 */               fileInputStream.close();
/*  722 */             } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  729 */             if (properties.size() == 0) {
/*      */               
/*      */               try {
/*  732 */                 File file = new File(System.getProperty("java.home") + sep + "lib" + sep + "accessibility.properties");
/*      */                 
/*  734 */                 FileInputStream fileInputStream = new FileInputStream(file);
/*      */ 
/*      */ 
/*      */                 
/*  738 */                 properties.load(fileInputStream);
/*  739 */                 fileInputStream.close();
/*  740 */               } catch (Exception exception) {}
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  748 */             String str1 = System.getProperty("javax.accessibility.screen_magnifier_present");
/*  749 */             if (str1 == null) {
/*  750 */               str1 = properties.getProperty("screen_magnifier_present", null);
/*  751 */               if (str1 != null) {
/*  752 */                 System.setProperty("javax.accessibility.screen_magnifier_present", str1);
/*      */               }
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  759 */             String str2 = System.getProperty("javax.accessibility.assistive_technologies");
/*  760 */             if (str2 == null) {
/*  761 */               str2 = properties.getProperty("assistive_technologies", null);
/*  762 */               if (str2 != null) {
/*  763 */                 System.setProperty("javax.accessibility.assistive_technologies", str2);
/*      */               }
/*      */             } 
/*  766 */             return str2;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void loadAssistiveTechnologies() {
/*  792 */     if (atNames != null) {
/*  793 */       ClassLoader classLoader = ClassLoader.getSystemClassLoader();
/*  794 */       StringTokenizer stringTokenizer = new StringTokenizer(atNames, " ,");
/*      */       
/*  796 */       while (stringTokenizer.hasMoreTokens()) {
/*  797 */         String str = stringTokenizer.nextToken();
/*      */         try {
/*      */           Class<?> clazz;
/*  800 */           if (classLoader != null) {
/*  801 */             clazz = classLoader.loadClass(str);
/*      */           } else {
/*  803 */             clazz = Class.forName(str);
/*      */           } 
/*  805 */           clazz.newInstance();
/*  806 */         } catch (ClassNotFoundException classNotFoundException) {
/*  807 */           throw new AWTError("Assistive Technology not found: " + str);
/*      */         }
/*  809 */         catch (InstantiationException instantiationException) {
/*  810 */           throw new AWTError("Could not instantiate Assistive Technology: " + str);
/*      */         }
/*  812 */         catch (IllegalAccessException illegalAccessException) {
/*  813 */           throw new AWTError("Could not access Assistive Technology: " + str);
/*      */         }
/*  815 */         catch (Exception exception) {
/*  816 */           throw new AWTError("Error trying to install Assistive Technology: " + str + " " + exception);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static synchronized Toolkit getDefaultToolkit() {
/*  853 */     if (toolkit == null) {
/*  854 */       AccessController.doPrivileged(new PrivilegedAction<Void>()
/*      */           {
/*      */             public Void run() {
/*  857 */               Class<?> clazz = null;
/*  858 */               String str = System.getProperty("awt.toolkit");
/*      */               try {
/*  860 */                 clazz = Class.forName(str);
/*  861 */               } catch (ClassNotFoundException classNotFoundException) {
/*  862 */                 ClassLoader classLoader = ClassLoader.getSystemClassLoader();
/*  863 */                 if (classLoader != null) {
/*      */                   try {
/*  865 */                     clazz = classLoader.loadClass(str);
/*  866 */                   } catch (ClassNotFoundException classNotFoundException1) {
/*  867 */                     throw new AWTError("Toolkit not found: " + str);
/*      */                   } 
/*      */                 }
/*      */               } 
/*      */               try {
/*  872 */                 if (clazz != null) {
/*  873 */                   Toolkit.toolkit = (Toolkit)clazz.newInstance();
/*  874 */                   if (GraphicsEnvironment.isHeadless()) {
/*  875 */                     Toolkit.toolkit = new HeadlessToolkit(Toolkit.toolkit);
/*      */                   }
/*      */                 } 
/*  878 */               } catch (InstantiationException instantiationException) {
/*  879 */                 throw new AWTError("Could not instantiate Toolkit: " + str);
/*  880 */               } catch (IllegalAccessException illegalAccessException) {
/*  881 */                 throw new AWTError("Could not access Toolkit: " + str);
/*      */               } 
/*  883 */               return null;
/*      */             }
/*      */           });
/*  886 */       loadAssistiveTechnologies();
/*      */     } 
/*  888 */     return toolkit;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Image createImage(byte[] paramArrayOfbyte) {
/* 1111 */     return createImage(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrintJob getPrintJob(Frame paramFrame, String paramString, JobAttributes paramJobAttributes, PageAttributes paramPageAttributes) {
/* 1221 */     if (this != getDefaultToolkit()) {
/* 1222 */       return getDefaultToolkit().getPrintJob(paramFrame, paramString, paramJobAttributes, paramPageAttributes);
/*      */     }
/*      */ 
/*      */     
/* 1226 */     return getPrintJob(paramFrame, paramString, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Clipboard getSystemSelection() throws HeadlessException {
/* 1328 */     GraphicsEnvironment.checkHeadless();
/*      */     
/* 1330 */     if (this != getDefaultToolkit()) {
/* 1331 */       return getDefaultToolkit().getSystemSelection();
/*      */     }
/* 1333 */     GraphicsEnvironment.checkHeadless();
/* 1334 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMenuShortcutKeyMask() throws HeadlessException {
/* 1359 */     GraphicsEnvironment.checkHeadless();
/*      */     
/* 1361 */     return 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getLockingKeyState(int paramInt) throws UnsupportedOperationException {
/* 1386 */     GraphicsEnvironment.checkHeadless();
/*      */     
/* 1388 */     if (paramInt != 20 && paramInt != 144 && paramInt != 145 && paramInt != 262)
/*      */     {
/* 1390 */       throw new IllegalArgumentException("invalid key for Toolkit.getLockingKeyState");
/*      */     }
/* 1392 */     throw new UnsupportedOperationException("Toolkit.getLockingKeyState");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLockingKeyState(int paramInt, boolean paramBoolean) throws UnsupportedOperationException {
/* 1420 */     GraphicsEnvironment.checkHeadless();
/*      */     
/* 1422 */     if (paramInt != 20 && paramInt != 144 && paramInt != 145 && paramInt != 262)
/*      */     {
/* 1424 */       throw new IllegalArgumentException("invalid key for Toolkit.setLockingKeyState");
/*      */     }
/* 1426 */     throw new UnsupportedOperationException("Toolkit.setLockingKeyState");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static Container getNativeContainer(Component paramComponent) {
/* 1434 */     return paramComponent.getNativeContainer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Cursor createCustomCursor(Image paramImage, Point paramPoint, String paramString) throws IndexOutOfBoundsException, HeadlessException {
/* 1461 */     if (this != getDefaultToolkit()) {
/* 1462 */       return getDefaultToolkit()
/* 1463 */         .createCustomCursor(paramImage, paramPoint, paramString);
/*      */     }
/* 1465 */     return new Cursor(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getBestCursorSize(int paramInt1, int paramInt2) throws HeadlessException {
/* 1495 */     GraphicsEnvironment.checkHeadless();
/*      */ 
/*      */     
/* 1498 */     if (this != getDefaultToolkit()) {
/* 1499 */       return getDefaultToolkit()
/* 1500 */         .getBestCursorSize(paramInt1, paramInt2);
/*      */     }
/* 1502 */     return new Dimension(0, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaximumCursorColors() throws HeadlessException {
/* 1524 */     GraphicsEnvironment.checkHeadless();
/*      */ 
/*      */     
/* 1527 */     if (this != getDefaultToolkit()) {
/* 1528 */       return getDefaultToolkit().getMaximumCursorColors();
/*      */     }
/* 1530 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFrameStateSupported(int paramInt) throws HeadlessException {
/* 1575 */     GraphicsEnvironment.checkHeadless();
/*      */     
/* 1577 */     if (this != getDefaultToolkit()) {
/* 1578 */       return getDefaultToolkit()
/* 1579 */         .isFrameStateSupported(paramInt);
/*      */     }
/* 1581 */     return (paramInt == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setPlatformResources(ResourceBundle paramResourceBundle) {
/* 1595 */     platformResources = paramResourceBundle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean loaded = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void loadLibraries() {
/* 1632 */     if (!loaded) {
/* 1633 */       AccessController.doPrivileged(new PrivilegedAction<Void>()
/*      */           {
/*      */             public Void run() {
/* 1636 */               System.loadLibrary("awt");
/* 1637 */               return null;
/*      */             }
/*      */           });
/* 1640 */       loaded = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   static {
/* 1645 */     AWTAccessor.setToolkitAccessor(new AWTAccessor.ToolkitAccessor()
/*      */         {
/*      */           public void setPlatformResources(ResourceBundle param1ResourceBundle)
/*      */           {
/* 1649 */             Toolkit.setPlatformResources(param1ResourceBundle);
/*      */           }
/*      */         });
/*      */     
/* 1653 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*      */         {
/*      */           public Void run()
/*      */           {
/*      */             try {
/* 1658 */               Toolkit.resources = ResourceBundle.getBundle("sun.awt.resources.awt", 
/* 1659 */                   Locale.getDefault(), 
/* 1660 */                   ClassLoader.getSystemClassLoader(), 
/* 1661 */                   (ResourceBundle.Control)CoreResourceBundleControl.getRBControlInstance());
/* 1662 */             } catch (MissingResourceException missingResourceException) {}
/*      */ 
/*      */             
/* 1665 */             return null;
/*      */           }
/*      */         });
/*      */ 
/*      */     
/* 1670 */     loadLibraries();
/* 1671 */     initAssistiveTechnologies();
/* 1672 */     if (!GraphicsEnvironment.isHeadless()) {
/* 1673 */       initIDs();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getProperty(String paramString1, String paramString2) {
/* 1683 */     if (platformResources != null) {
/*      */       try {
/* 1685 */         return platformResources.getString(paramString1);
/*      */       }
/* 1687 */       catch (MissingResourceException missingResourceException) {}
/*      */     }
/*      */ 
/*      */     
/* 1691 */     if (resources != null) {
/*      */       try {
/* 1693 */         return resources.getString(paramString1);
/*      */       }
/* 1695 */       catch (MissingResourceException missingResourceException) {}
/*      */     }
/*      */     
/* 1698 */     return paramString2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final EventQueue getSystemEventQueue() {
/* 1719 */     SecurityManager securityManager = System.getSecurityManager();
/* 1720 */     if (securityManager != null) {
/* 1721 */       securityManager.checkPermission(SecurityConstants.AWT.CHECK_AWT_EVENTQUEUE_PERMISSION);
/*      */     }
/* 1723 */     return getSystemEventQueueImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static EventQueue getEventQueue() {
/* 1736 */     return getDefaultToolkit().getSystemEventQueueImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends java.awt.dnd.DragGestureRecognizer> T createDragGestureRecognizer(Class<T> paramClass, DragSource paramDragSource, Component paramComponent, int paramInt, DragGestureListener paramDragGestureListener) {
/* 1769 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized Object getDesktopProperty(String paramString) {
/* 1786 */     if (this instanceof HeadlessToolkit) {
/* 1787 */       return ((HeadlessToolkit)this).getUnderlyingToolkit()
/* 1788 */         .getDesktopProperty(paramString);
/*      */     }
/*      */     
/* 1791 */     if (this.desktopProperties.isEmpty()) {
/* 1792 */       initializeDesktopProperties();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1798 */     if (paramString.equals("awt.dynamicLayoutSupported")) {
/* 1799 */       return getDefaultToolkit().lazilyLoadDesktopProperty(paramString);
/*      */     }
/*      */     
/* 1802 */     Object object = this.desktopProperties.get(paramString);
/*      */     
/* 1804 */     if (object == null) {
/* 1805 */       object = lazilyLoadDesktopProperty(paramString);
/*      */       
/* 1807 */       if (object != null) {
/* 1808 */         setDesktopProperty(paramString, object);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1813 */     if (object instanceof RenderingHints) {
/* 1814 */       object = ((RenderingHints)object).clone();
/*      */     }
/*      */     
/* 1817 */     return object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void setDesktopProperty(String paramString, Object paramObject) {
/*      */     Object object;
/* 1829 */     if (this instanceof HeadlessToolkit) {
/* 1830 */       ((HeadlessToolkit)this).getUnderlyingToolkit()
/* 1831 */         .setDesktopProperty(paramString, paramObject);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1836 */     synchronized (this) {
/* 1837 */       object = this.desktopProperties.get(paramString);
/* 1838 */       this.desktopProperties.put(paramString, paramObject);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1843 */     if (object != null || paramObject != null) {
/* 1844 */       this.desktopPropsSupport.firePropertyChange(paramString, object, paramObject);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object lazilyLoadDesktopProperty(String paramString) {
/* 1852 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initializeDesktopProperties() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addPropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener) {
/* 1875 */     this.desktopPropsSupport.addPropertyChangeListener(paramString, paramPropertyChangeListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removePropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener) {
/* 1893 */     this.desktopPropsSupport.removePropertyChangeListener(paramString, paramPropertyChangeListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PropertyChangeListener[] getPropertyChangeListeners() {
/* 1910 */     return this.desktopPropsSupport.getPropertyChangeListeners();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PropertyChangeListener[] getPropertyChangeListeners(String paramString) {
/* 1926 */     return this.desktopPropsSupport.getPropertyChangeListeners(paramString);
/*      */   }
/*      */   
/* 1929 */   protected final Map<String, Object> desktopProperties = new HashMap<>();
/*      */ 
/*      */   
/* 1932 */   protected final PropertyChangeSupport desktopPropsSupport = createPropertyChangeSupport(this);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int LONG_BITS = 64;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAlwaysOnTopSupported() {
/* 1945 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1987 */   private int[] calls = new int[64];
/*      */   private static volatile long enabledOnToolkitMask;
/* 1989 */   private AWTEventListener eventListener = null;
/* 1990 */   private WeakHashMap<AWTEventListener, SelectiveAWTEventListener> listener2SelectiveListener = new WeakHashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static AWTEventListener deProxyAWTEventListener(AWTEventListener paramAWTEventListener) {
/* 1998 */     AWTEventListener aWTEventListener = paramAWTEventListener;
/*      */     
/* 2000 */     if (aWTEventListener == null) {
/* 2001 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 2005 */     if (paramAWTEventListener instanceof AWTEventListenerProxy) {
/* 2006 */       aWTEventListener = ((AWTEventListenerProxy)paramAWTEventListener).getListener();
/*      */     }
/* 2008 */     return aWTEventListener;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addAWTEventListener(AWTEventListener paramAWTEventListener, long paramLong) {
/* 2046 */     AWTEventListener aWTEventListener = deProxyAWTEventListener(paramAWTEventListener);
/*      */     
/* 2048 */     if (aWTEventListener == null) {
/*      */       return;
/*      */     }
/* 2051 */     SecurityManager securityManager = System.getSecurityManager();
/* 2052 */     if (securityManager != null) {
/* 2053 */       securityManager.checkPermission(SecurityConstants.AWT.ALL_AWT_EVENTS_PERMISSION);
/*      */     }
/* 2055 */     synchronized (this) {
/*      */       
/* 2057 */       SelectiveAWTEventListener selectiveAWTEventListener = this.listener2SelectiveListener.get(aWTEventListener);
/*      */       
/* 2059 */       if (selectiveAWTEventListener == null) {
/*      */         
/* 2061 */         selectiveAWTEventListener = new SelectiveAWTEventListener(aWTEventListener, paramLong);
/*      */         
/* 2063 */         this.listener2SelectiveListener.put(aWTEventListener, selectiveAWTEventListener);
/* 2064 */         this.eventListener = ToolkitEventMulticaster.add(this.eventListener, selectiveAWTEventListener);
/*      */       } 
/*      */ 
/*      */       
/* 2068 */       selectiveAWTEventListener.orEventMasks(paramLong);
/*      */       
/* 2070 */       enabledOnToolkitMask |= paramLong;
/*      */       
/* 2072 */       long l = paramLong;
/* 2073 */       for (byte b = 0; b < 64; b++) {
/*      */         
/* 2075 */         if (l == 0L) {
/*      */           break;
/*      */         }
/* 2078 */         if ((l & 0x1L) != 0L) {
/* 2079 */           this.calls[b] = this.calls[b] + 1;
/*      */         }
/* 2081 */         l >>>= 1L;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAWTEventListener(AWTEventListener paramAWTEventListener) {
/* 2115 */     AWTEventListener aWTEventListener = deProxyAWTEventListener(paramAWTEventListener);
/*      */     
/* 2117 */     if (paramAWTEventListener == null) {
/*      */       return;
/*      */     }
/* 2120 */     SecurityManager securityManager = System.getSecurityManager();
/* 2121 */     if (securityManager != null) {
/* 2122 */       securityManager.checkPermission(SecurityConstants.AWT.ALL_AWT_EVENTS_PERMISSION);
/*      */     }
/*      */     
/* 2125 */     synchronized (this) {
/*      */       
/* 2127 */       SelectiveAWTEventListener selectiveAWTEventListener = this.listener2SelectiveListener.get(aWTEventListener);
/*      */       
/* 2129 */       if (selectiveAWTEventListener != null) {
/* 2130 */         this.listener2SelectiveListener.remove(aWTEventListener);
/* 2131 */         int[] arrayOfInt = selectiveAWTEventListener.getCalls();
/* 2132 */         for (byte b = 0; b < 64; b++) {
/* 2133 */           this.calls[b] = this.calls[b] - arrayOfInt[b];
/* 2134 */           assert this.calls[b] >= 0 : "Negative Listeners count";
/*      */           
/* 2136 */           if (this.calls[b] == 0) {
/* 2137 */             enabledOnToolkitMask &= 1L << b ^ 0xFFFFFFFFFFFFFFFFL;
/*      */           }
/*      */         } 
/*      */       } 
/* 2141 */       this.eventListener = ToolkitEventMulticaster.remove(this.eventListener, (selectiveAWTEventListener == null) ? aWTEventListener : selectiveAWTEventListener);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   static boolean enabledOnToolkit(long paramLong) {
/* 2147 */     return ((enabledOnToolkitMask & paramLong) != 0L);
/*      */   }
/*      */   
/*      */   synchronized int countAWTEventListeners(long paramLong) {
/* 2151 */     byte b = 0;
/* 2152 */     while (paramLong != 0L) { paramLong >>>= 1L; b++; }
/*      */     
/* 2154 */     b--;
/* 2155 */     return this.calls[b];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AWTEventListener[] getAWTEventListeners() {
/* 2185 */     SecurityManager securityManager = System.getSecurityManager();
/* 2186 */     if (securityManager != null) {
/* 2187 */       securityManager.checkPermission(SecurityConstants.AWT.ALL_AWT_EVENTS_PERMISSION);
/*      */     }
/* 2189 */     synchronized (this) {
/* 2190 */       Object[] arrayOfObject = ToolkitEventMulticaster.getListeners(this.eventListener, (Class)AWTEventListener.class);
/*      */       
/* 2192 */       AWTEventListener[] arrayOfAWTEventListener = new AWTEventListener[arrayOfObject.length];
/* 2193 */       for (byte b = 0; b < arrayOfObject.length; b++) {
/* 2194 */         SelectiveAWTEventListener selectiveAWTEventListener = (SelectiveAWTEventListener)arrayOfObject[b];
/* 2195 */         AWTEventListener aWTEventListener = selectiveAWTEventListener.getListener();
/*      */ 
/*      */ 
/*      */         
/* 2199 */         arrayOfAWTEventListener[b] = new AWTEventListenerProxy(selectiveAWTEventListener.getEventMask(), aWTEventListener);
/*      */       } 
/* 2201 */       return arrayOfAWTEventListener;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AWTEventListener[] getAWTEventListeners(long paramLong) {
/* 2237 */     SecurityManager securityManager = System.getSecurityManager();
/* 2238 */     if (securityManager != null) {
/* 2239 */       securityManager.checkPermission(SecurityConstants.AWT.ALL_AWT_EVENTS_PERMISSION);
/*      */     }
/* 2241 */     synchronized (this) {
/* 2242 */       Object[] arrayOfObject = ToolkitEventMulticaster.getListeners(this.eventListener, (Class)AWTEventListener.class);
/*      */       
/* 2244 */       ArrayList<AWTEventListenerProxy> arrayList = new ArrayList(arrayOfObject.length);
/*      */       
/* 2246 */       for (byte b = 0; b < arrayOfObject.length; b++) {
/* 2247 */         SelectiveAWTEventListener selectiveAWTEventListener = (SelectiveAWTEventListener)arrayOfObject[b];
/* 2248 */         if ((selectiveAWTEventListener.getEventMask() & paramLong) == paramLong)
/*      */         {
/* 2250 */           arrayList.add(new AWTEventListenerProxy(selectiveAWTEventListener.getEventMask(), selectiveAWTEventListener
/* 2251 */                 .getListener()));
/*      */         }
/*      */       } 
/* 2254 */       return arrayList.<AWTEventListener>toArray(new AWTEventListener[0]);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void notifyAWTEventListeners(AWTEvent paramAWTEvent) {
/* 2269 */     if (this instanceof HeadlessToolkit) {
/* 2270 */       ((HeadlessToolkit)this).getUnderlyingToolkit()
/* 2271 */         .notifyAWTEventListeners(paramAWTEvent);
/*      */       
/*      */       return;
/*      */     } 
/* 2275 */     AWTEventListener aWTEventListener = this.eventListener;
/* 2276 */     if (aWTEventListener != null) {
/* 2277 */       aWTEventListener.eventDispatched(paramAWTEvent);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class ToolkitEventMulticaster
/*      */     extends AWTEventMulticaster
/*      */     implements AWTEventListener
/*      */   {
/*      */     ToolkitEventMulticaster(AWTEventListener param1AWTEventListener1, AWTEventListener param1AWTEventListener2) {
/* 2286 */       super(param1AWTEventListener1, param1AWTEventListener2);
/*      */     }
/*      */ 
/*      */     
/*      */     static AWTEventListener add(AWTEventListener param1AWTEventListener1, AWTEventListener param1AWTEventListener2) {
/* 2291 */       if (param1AWTEventListener1 == null) return param1AWTEventListener2; 
/* 2292 */       if (param1AWTEventListener2 == null) return param1AWTEventListener1; 
/* 2293 */       return new ToolkitEventMulticaster(param1AWTEventListener1, param1AWTEventListener2);
/*      */     }
/*      */ 
/*      */     
/*      */     static AWTEventListener remove(AWTEventListener param1AWTEventListener1, AWTEventListener param1AWTEventListener2) {
/* 2298 */       return (AWTEventListener)removeInternal(param1AWTEventListener1, param1AWTEventListener2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected EventListener remove(EventListener param1EventListener) {
/* 2307 */       if (param1EventListener == this.a) return this.b; 
/* 2308 */       if (param1EventListener == this.b) return this.a; 
/* 2309 */       AWTEventListener aWTEventListener1 = (AWTEventListener)removeInternal(this.a, param1EventListener);
/* 2310 */       AWTEventListener aWTEventListener2 = (AWTEventListener)removeInternal(this.b, param1EventListener);
/* 2311 */       if (aWTEventListener1 == this.a && aWTEventListener2 == this.b) {
/* 2312 */         return this;
/*      */       }
/* 2314 */       return add(aWTEventListener1, aWTEventListener2);
/*      */     }
/*      */     
/*      */     public void eventDispatched(AWTEvent param1AWTEvent) {
/* 2318 */       ((AWTEventListener)this.a).eventDispatched(param1AWTEvent);
/* 2319 */       ((AWTEventListener)this.b).eventDispatched(param1AWTEvent);
/*      */     }
/*      */   }
/*      */   
/*      */   private class SelectiveAWTEventListener
/*      */     implements AWTEventListener
/*      */   {
/*      */     AWTEventListener listener;
/*      */     private long eventMask;
/* 2328 */     int[] calls = new int[64];
/*      */     
/* 2330 */     public AWTEventListener getListener() { return this.listener; }
/* 2331 */     public long getEventMask() { return this.eventMask; } public int[] getCalls() {
/* 2332 */       return this.calls;
/*      */     }
/*      */     public void orEventMasks(long param1Long) {
/* 2335 */       this.eventMask |= param1Long;
/*      */       
/* 2337 */       for (byte b = 0; b < 64; b++) {
/*      */         
/* 2339 */         if (param1Long == 0L) {
/*      */           break;
/*      */         }
/* 2342 */         if ((param1Long & 0x1L) != 0L) {
/* 2343 */           this.calls[b] = this.calls[b] + 1;
/*      */         }
/* 2345 */         param1Long >>>= 1L;
/*      */       } 
/*      */     }
/*      */     
/*      */     SelectiveAWTEventListener(AWTEventListener param1AWTEventListener, long param1Long) {
/* 2350 */       this.listener = param1AWTEventListener;
/* 2351 */       this.eventMask = param1Long;
/*      */     }
/*      */     
/*      */     public void eventDispatched(AWTEvent param1AWTEvent) {
/* 2355 */       long l = 0L;
/* 2356 */       if (((l = this.eventMask & 0x1L) != 0L && param1AWTEvent.id >= 100 && param1AWTEvent.id <= 103) || ((l = this.eventMask & 0x2L) != 0L && param1AWTEvent.id >= 300 && param1AWTEvent.id <= 301) || ((l = this.eventMask & 0x4L) != 0L && param1AWTEvent.id >= 1004 && param1AWTEvent.id <= 1005) || ((l = this.eventMask & 0x8L) != 0L && param1AWTEvent.id >= 400 && param1AWTEvent.id <= 402) || ((l = this.eventMask & 0x20000L) != 0L && param1AWTEvent.id == 507) || ((l = this.eventMask & 0x20L) != 0L && (param1AWTEvent.id == 503 || param1AWTEvent.id == 506)) || ((l = this.eventMask & 0x10L) != 0L && param1AWTEvent.id != 503 && param1AWTEvent.id != 506 && param1AWTEvent.id != 507 && param1AWTEvent.id >= 500 && param1AWTEvent.id <= 507) || ((l = this.eventMask & 0x40L) != 0L && param1AWTEvent.id >= 200 && param1AWTEvent.id <= 209) || ((l = this.eventMask & 0x80L) != 0L && param1AWTEvent.id >= 1001 && param1AWTEvent.id <= 1001) || ((l = this.eventMask & 0x100L) != 0L && param1AWTEvent.id >= 601 && param1AWTEvent.id <= 601) || ((l = this.eventMask & 0x200L) != 0L && param1AWTEvent.id >= 701 && param1AWTEvent.id <= 701) || ((l = this.eventMask & 0x400L) != 0L && param1AWTEvent.id >= 900 && param1AWTEvent.id <= 900) || ((l = this.eventMask & 0x800L) != 0L && param1AWTEvent.id >= 1100 && param1AWTEvent.id <= 1101) || ((l = this.eventMask & 0x2000L) != 0L && param1AWTEvent.id >= 800 && param1AWTEvent.id <= 801) || ((l = this.eventMask & 0x4000L) != 0L && param1AWTEvent.id >= 1200 && param1AWTEvent.id <= 1200) || ((l = this.eventMask & 0x8000L) != 0L && param1AWTEvent.id == 1400) || ((l = this.eventMask & 0x10000L) != 0L && (param1AWTEvent.id == 1401 || param1AWTEvent.id == 1402)) || ((l = this.eventMask & 0x40000L) != 0L && param1AWTEvent.id == 209) || ((l = this.eventMask & 0x80000L) != 0L && (param1AWTEvent.id == 207 || param1AWTEvent.id == 208)) || ((l = this.eventMask & 0xFFFFFFFF80000000L) != 0L && param1AWTEvent instanceof sun.awt.UngrabEvent)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2420 */         byte b1 = 0;
/* 2421 */         for (long l1 = l; l1 != 0L; ) { l1 >>>= 1L; b1++; }
/*      */         
/* 2423 */         b1--;
/*      */ 
/*      */         
/* 2426 */         for (byte b2 = 0; b2 < this.calls[b1]; b2++) {
/* 2427 */           this.listener.eventDispatched(param1AWTEvent);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static PropertyChangeSupport createPropertyChangeSupport(Toolkit paramToolkit) {
/* 2450 */     if (paramToolkit instanceof SunToolkit || paramToolkit instanceof HeadlessToolkit) {
/* 2451 */       return new DesktopPropertyChangeSupport(paramToolkit);
/*      */     }
/* 2453 */     return new PropertyChangeSupport(paramToolkit);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class DesktopPropertyChangeSupport
/*      */     extends PropertyChangeSupport
/*      */   {
/* 2460 */     private static final StringBuilder PROP_CHANGE_SUPPORT_KEY = new StringBuilder("desktop property change support key");
/*      */     
/*      */     private final Object source;
/*      */     
/*      */     public DesktopPropertyChangeSupport(Object param1Object) {
/* 2465 */       super(param1Object);
/* 2466 */       this.source = param1Object;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public synchronized void addPropertyChangeListener(String param1String, PropertyChangeListener param1PropertyChangeListener) {
/* 2475 */       PropertyChangeSupport propertyChangeSupport = (PropertyChangeSupport)AppContext.getAppContext().get(PROP_CHANGE_SUPPORT_KEY);
/* 2476 */       if (null == propertyChangeSupport) {
/* 2477 */         propertyChangeSupport = new PropertyChangeSupport(this.source);
/* 2478 */         AppContext.getAppContext().put(PROP_CHANGE_SUPPORT_KEY, propertyChangeSupport);
/*      */       } 
/* 2480 */       propertyChangeSupport.addPropertyChangeListener(param1String, param1PropertyChangeListener);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public synchronized void removePropertyChangeListener(String param1String, PropertyChangeListener param1PropertyChangeListener) {
/* 2489 */       PropertyChangeSupport propertyChangeSupport = (PropertyChangeSupport)AppContext.getAppContext().get(PROP_CHANGE_SUPPORT_KEY);
/* 2490 */       if (null != propertyChangeSupport) {
/* 2491 */         propertyChangeSupport.removePropertyChangeListener(param1String, param1PropertyChangeListener);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public synchronized PropertyChangeListener[] getPropertyChangeListeners() {
/* 2499 */       PropertyChangeSupport propertyChangeSupport = (PropertyChangeSupport)AppContext.getAppContext().get(PROP_CHANGE_SUPPORT_KEY);
/* 2500 */       if (null != propertyChangeSupport) {
/* 2501 */         return propertyChangeSupport.getPropertyChangeListeners();
/*      */       }
/* 2503 */       return new PropertyChangeListener[0];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public synchronized PropertyChangeListener[] getPropertyChangeListeners(String param1String) {
/* 2511 */       PropertyChangeSupport propertyChangeSupport = (PropertyChangeSupport)AppContext.getAppContext().get(PROP_CHANGE_SUPPORT_KEY);
/* 2512 */       if (null != propertyChangeSupport) {
/* 2513 */         return propertyChangeSupport.getPropertyChangeListeners(param1String);
/*      */       }
/* 2515 */       return new PropertyChangeListener[0];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public synchronized void addPropertyChangeListener(PropertyChangeListener param1PropertyChangeListener) {
/* 2522 */       PropertyChangeSupport propertyChangeSupport = (PropertyChangeSupport)AppContext.getAppContext().get(PROP_CHANGE_SUPPORT_KEY);
/* 2523 */       if (null == propertyChangeSupport) {
/* 2524 */         propertyChangeSupport = new PropertyChangeSupport(this.source);
/* 2525 */         AppContext.getAppContext().put(PROP_CHANGE_SUPPORT_KEY, propertyChangeSupport);
/*      */       } 
/* 2527 */       propertyChangeSupport.addPropertyChangeListener(param1PropertyChangeListener);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public synchronized void removePropertyChangeListener(PropertyChangeListener param1PropertyChangeListener) {
/* 2533 */       PropertyChangeSupport propertyChangeSupport = (PropertyChangeSupport)AppContext.getAppContext().get(PROP_CHANGE_SUPPORT_KEY);
/* 2534 */       if (null != propertyChangeSupport) {
/* 2535 */         propertyChangeSupport.removePropertyChangeListener(param1PropertyChangeListener);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void firePropertyChange(final PropertyChangeEvent evt) {
/* 2545 */       Object object1 = evt.getOldValue();
/* 2546 */       Object object2 = evt.getNewValue();
/* 2547 */       String str = evt.getPropertyName();
/* 2548 */       if (object1 != null && object2 != null && object1.equals(object2)) {
/*      */         return;
/*      */       }
/* 2551 */       Runnable runnable = new Runnable()
/*      */         {
/*      */           public void run() {
/* 2554 */             PropertyChangeSupport propertyChangeSupport = (PropertyChangeSupport)AppContext.getAppContext().get(Toolkit.DesktopPropertyChangeSupport.PROP_CHANGE_SUPPORT_KEY);
/* 2555 */             if (null != propertyChangeSupport) {
/* 2556 */               propertyChangeSupport.firePropertyChange(evt);
/*      */             }
/*      */           }
/*      */         };
/* 2560 */       AppContext appContext = AppContext.getAppContext();
/* 2561 */       for (AppContext appContext1 : AppContext.getAppContexts()) {
/* 2562 */         if (null == appContext1 || appContext1.isDisposed()) {
/*      */           continue;
/*      */         }
/* 2565 */         if (appContext == appContext1) {
/* 2566 */           runnable.run(); continue;
/*      */         } 
/* 2568 */         PeerEvent peerEvent = new PeerEvent(this.source, runnable, 2L);
/* 2569 */         SunToolkit.postEvent(appContext1, peerEvent);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean areExtraMouseButtonsEnabled() throws HeadlessException {
/* 2603 */     GraphicsEnvironment.checkHeadless();
/*      */     
/* 2605 */     return getDefaultToolkit().areExtraMouseButtonsEnabled();
/*      */   }
/*      */   
/*      */   protected abstract DesktopPeer createDesktopPeer(Desktop paramDesktop) throws HeadlessException;
/*      */   
/*      */   protected abstract ButtonPeer createButton(Button paramButton) throws HeadlessException;
/*      */   
/*      */   protected abstract TextFieldPeer createTextField(TextField paramTextField) throws HeadlessException;
/*      */   
/*      */   protected abstract LabelPeer createLabel(Label paramLabel) throws HeadlessException;
/*      */   
/*      */   protected abstract ListPeer createList(List paramList) throws HeadlessException;
/*      */   
/*      */   protected abstract CheckboxPeer createCheckbox(Checkbox paramCheckbox) throws HeadlessException;
/*      */   
/*      */   protected abstract ScrollbarPeer createScrollbar(Scrollbar paramScrollbar) throws HeadlessException;
/*      */   
/*      */   protected abstract ScrollPanePeer createScrollPane(ScrollPane paramScrollPane) throws HeadlessException;
/*      */   
/*      */   protected abstract TextAreaPeer createTextArea(TextArea paramTextArea) throws HeadlessException;
/*      */   
/*      */   protected abstract ChoicePeer createChoice(Choice paramChoice) throws HeadlessException;
/*      */   
/*      */   protected abstract FramePeer createFrame(Frame paramFrame) throws HeadlessException;
/*      */   
/*      */   protected abstract CanvasPeer createCanvas(Canvas paramCanvas);
/*      */   
/*      */   protected abstract PanelPeer createPanel(Panel paramPanel);
/*      */   
/*      */   protected abstract WindowPeer createWindow(Window paramWindow) throws HeadlessException;
/*      */   
/*      */   protected abstract DialogPeer createDialog(Dialog paramDialog) throws HeadlessException;
/*      */   
/*      */   protected abstract MenuBarPeer createMenuBar(MenuBar paramMenuBar) throws HeadlessException;
/*      */   
/*      */   protected abstract MenuPeer createMenu(Menu paramMenu) throws HeadlessException;
/*      */   
/*      */   protected abstract PopupMenuPeer createPopupMenu(PopupMenu paramPopupMenu) throws HeadlessException;
/*      */   
/*      */   protected abstract MenuItemPeer createMenuItem(MenuItem paramMenuItem) throws HeadlessException;
/*      */   
/*      */   protected abstract FileDialogPeer createFileDialog(FileDialog paramFileDialog) throws HeadlessException;
/*      */   
/*      */   protected abstract CheckboxMenuItemPeer createCheckboxMenuItem(CheckboxMenuItem paramCheckboxMenuItem) throws HeadlessException;
/*      */   
/*      */   @Deprecated
/*      */   protected abstract FontPeer getFontPeer(String paramString, int paramInt);
/*      */   
/*      */   public abstract Dimension getScreenSize() throws HeadlessException;
/*      */   
/*      */   public abstract int getScreenResolution() throws HeadlessException;
/*      */   
/*      */   public abstract ColorModel getColorModel() throws HeadlessException;
/*      */   
/*      */   @Deprecated
/*      */   public abstract String[] getFontList();
/*      */   
/*      */   @Deprecated
/*      */   public abstract FontMetrics getFontMetrics(Font paramFont);
/*      */   
/*      */   public abstract void sync();
/*      */   
/*      */   public abstract Image getImage(String paramString);
/*      */   
/*      */   public abstract Image getImage(URL paramURL);
/*      */   
/*      */   public abstract Image createImage(String paramString);
/*      */   
/*      */   public abstract Image createImage(URL paramURL);
/*      */   
/*      */   public abstract boolean prepareImage(Image paramImage, int paramInt1, int paramInt2, ImageObserver paramImageObserver);
/*      */   
/*      */   public abstract int checkImage(Image paramImage, int paramInt1, int paramInt2, ImageObserver paramImageObserver);
/*      */   
/*      */   public abstract Image createImage(ImageProducer paramImageProducer);
/*      */   
/*      */   public abstract Image createImage(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
/*      */   
/*      */   public abstract PrintJob getPrintJob(Frame paramFrame, String paramString, Properties paramProperties);
/*      */   
/*      */   public abstract void beep();
/*      */   
/*      */   public abstract Clipboard getSystemClipboard() throws HeadlessException;
/*      */   
/*      */   private static native void initIDs();
/*      */   
/*      */   protected abstract EventQueue getSystemEventQueueImpl();
/*      */   
/*      */   public abstract DragSourceContextPeer createDragSourceContextPeer(DragGestureEvent paramDragGestureEvent) throws InvalidDnDOperationException;
/*      */   
/*      */   public abstract boolean isModalityTypeSupported(Dialog.ModalityType paramModalityType);
/*      */   
/*      */   public abstract boolean isModalExclusionTypeSupported(Dialog.ModalExclusionType paramModalExclusionType);
/*      */   
/*      */   public abstract Map<TextAttribute, ?> mapInputMethodHighlight(InputMethodHighlight paramInputMethodHighlight) throws HeadlessException;
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/Toolkit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */