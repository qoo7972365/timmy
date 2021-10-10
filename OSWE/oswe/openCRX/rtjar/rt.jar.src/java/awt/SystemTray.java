/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.peer.SystemTrayPeer;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.beans.PropertyChangeSupport;
/*     */ import java.util.Vector;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.awt.AppContext;
/*     */ import sun.awt.HeadlessToolkit;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.security.util.SecurityConstants;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SystemTray
/*     */ {
/*     */   private static SystemTray systemTray;
/* 125 */   private int currentIconID = 0;
/*     */   
/*     */   private transient SystemTrayPeer peer;
/*     */   
/* 129 */   private static final TrayIcon[] EMPTY_TRAY_ARRAY = new TrayIcon[0];
/*     */   
/*     */   static {
/* 132 */     AWTAccessor.setSystemTrayAccessor(new AWTAccessor.SystemTrayAccessor()
/*     */         {
/*     */ 
/*     */           
/*     */           public void firePropertyChange(SystemTray param1SystemTray, String param1String, Object param1Object1, Object param1Object2)
/*     */           {
/* 138 */             param1SystemTray.firePropertyChange(param1String, param1Object1, param1Object2);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SystemTray() {
/* 148 */     addNotify();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SystemTray getSystemTray() {
/* 178 */     checkSystemTrayAllowed();
/* 179 */     if (GraphicsEnvironment.isHeadless()) {
/* 180 */       throw new HeadlessException();
/*     */     }
/*     */     
/* 183 */     initializeSystemTrayIfNeeded();
/*     */     
/* 185 */     if (!isSupported()) {
/* 186 */       throw new UnsupportedOperationException("The system tray is not supported on the current platform.");
/*     */     }
/*     */ 
/*     */     
/* 190 */     return systemTray;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSupported() {
/* 219 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 220 */     if (toolkit instanceof SunToolkit) {
/*     */       
/* 222 */       initializeSystemTrayIfNeeded();
/* 223 */       return ((SunToolkit)toolkit).isTraySupported();
/* 224 */     }  if (toolkit instanceof HeadlessToolkit)
/*     */     {
/*     */       
/* 227 */       return ((HeadlessToolkit)toolkit).isTraySupported();
/*     */     }
/* 229 */     return false;
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
/*     */   public void add(TrayIcon paramTrayIcon) throws AWTException {
/* 255 */     if (paramTrayIcon == null) {
/* 256 */       throw new NullPointerException("adding null TrayIcon");
/*     */     }
/* 258 */     TrayIcon[] arrayOfTrayIcon1 = null, arrayOfTrayIcon2 = null;
/* 259 */     Vector<TrayIcon> vector = null;
/* 260 */     synchronized (this) {
/* 261 */       arrayOfTrayIcon1 = systemTray.getTrayIcons();
/* 262 */       vector = (Vector)AppContext.getAppContext().get(TrayIcon.class);
/* 263 */       if (vector == null) {
/* 264 */         vector = new Vector(3);
/* 265 */         AppContext.getAppContext().put(TrayIcon.class, vector);
/*     */       }
/* 267 */       else if (vector.contains(paramTrayIcon)) {
/* 268 */         throw new IllegalArgumentException("adding TrayIcon that is already added");
/*     */       } 
/* 270 */       vector.add(paramTrayIcon);
/* 271 */       arrayOfTrayIcon2 = systemTray.getTrayIcons();
/*     */       
/* 273 */       paramTrayIcon.setID(++this.currentIconID);
/*     */     } 
/*     */     try {
/* 276 */       paramTrayIcon.addNotify();
/* 277 */     } catch (AWTException aWTException) {
/* 278 */       vector.remove(paramTrayIcon);
/* 279 */       throw aWTException;
/*     */     } 
/* 281 */     firePropertyChange("trayIcons", arrayOfTrayIcon1, arrayOfTrayIcon2);
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
/*     */   public void remove(TrayIcon paramTrayIcon) {
/* 301 */     if (paramTrayIcon == null) {
/*     */       return;
/*     */     }
/* 304 */     TrayIcon[] arrayOfTrayIcon1 = null, arrayOfTrayIcon2 = null;
/* 305 */     synchronized (this) {
/* 306 */       arrayOfTrayIcon1 = systemTray.getTrayIcons();
/* 307 */       Vector vector = (Vector)AppContext.getAppContext().get(TrayIcon.class);
/*     */       
/* 309 */       if (vector == null || !vector.remove(paramTrayIcon)) {
/*     */         return;
/*     */       }
/* 312 */       paramTrayIcon.removeNotify();
/* 313 */       arrayOfTrayIcon2 = systemTray.getTrayIcons();
/*     */     } 
/* 315 */     firePropertyChange("trayIcons", arrayOfTrayIcon1, arrayOfTrayIcon2);
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
/*     */   public TrayIcon[] getTrayIcons() {
/* 338 */     Vector vector = (Vector)AppContext.getAppContext().get(TrayIcon.class);
/* 339 */     if (vector != null) {
/* 340 */       return (TrayIcon[])vector.toArray((Object[])new TrayIcon[vector.size()]);
/*     */     }
/* 342 */     return EMPTY_TRAY_ARRAY;
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
/*     */   public Dimension getTrayIconSize() {
/* 358 */     return this.peer.getTrayIconSize();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void addPropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener) {
/* 402 */     if (paramPropertyChangeListener == null) {
/*     */       return;
/*     */     }
/* 405 */     getCurrentChangeSupport().addPropertyChangeListener(paramString, paramPropertyChangeListener);
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
/*     */   public synchronized void removePropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener) {
/* 426 */     if (paramPropertyChangeListener == null) {
/*     */       return;
/*     */     }
/* 429 */     getCurrentChangeSupport().removePropertyChangeListener(paramString, paramPropertyChangeListener);
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
/*     */   public synchronized PropertyChangeListener[] getPropertyChangeListeners(String paramString) {
/* 448 */     return getCurrentChangeSupport().getPropertyChangeListeners(paramString);
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
/*     */   private void firePropertyChange(String paramString, Object paramObject1, Object paramObject2) {
/* 469 */     if (paramObject1 != null && paramObject2 != null && paramObject1.equals(paramObject2)) {
/*     */       return;
/*     */     }
/* 472 */     getCurrentChangeSupport().firePropertyChange(paramString, paramObject1, paramObject2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized PropertyChangeSupport getCurrentChangeSupport() {
/* 483 */     PropertyChangeSupport propertyChangeSupport = (PropertyChangeSupport)AppContext.getAppContext().get(SystemTray.class);
/*     */     
/* 485 */     if (propertyChangeSupport == null) {
/* 486 */       propertyChangeSupport = new PropertyChangeSupport(this);
/* 487 */       AppContext.getAppContext().put(SystemTray.class, propertyChangeSupport);
/*     */     } 
/* 489 */     return propertyChangeSupport;
/*     */   }
/*     */   
/*     */   synchronized void addNotify() {
/* 493 */     if (this.peer == null) {
/* 494 */       Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 495 */       if (toolkit instanceof SunToolkit) {
/* 496 */         this.peer = ((SunToolkit)Toolkit.getDefaultToolkit()).createSystemTray(this);
/* 497 */       } else if (toolkit instanceof HeadlessToolkit) {
/* 498 */         this.peer = ((HeadlessToolkit)Toolkit.getDefaultToolkit()).createSystemTray(this);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   static void checkSystemTrayAllowed() {
/* 504 */     SecurityManager securityManager = System.getSecurityManager();
/* 505 */     if (securityManager != null) {
/* 506 */       securityManager.checkPermission(SecurityConstants.AWT.ACCESS_SYSTEM_TRAY_PERMISSION);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void initializeSystemTrayIfNeeded() {
/* 511 */     synchronized (SystemTray.class) {
/* 512 */       if (systemTray == null)
/* 513 */         systemTray = new SystemTray(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/SystemTray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */