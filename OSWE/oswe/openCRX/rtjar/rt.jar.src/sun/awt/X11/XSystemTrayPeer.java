/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.AWTException;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.SystemTray;
/*     */ import java.awt.TrayIcon;
/*     */ import java.awt.peer.SystemTrayPeer;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.awt.AppContext;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XSystemTrayPeer
/*     */   implements SystemTrayPeer, XMSelectionListener
/*     */ {
/*  36 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.XSystemTrayPeer");
/*     */   
/*     */   SystemTray target;
/*     */   
/*     */   static XSystemTrayPeer peerInstance;
/*     */   private volatile boolean available;
/*  42 */   private final XMSelection selection = new XMSelection("_NET_SYSTEM_TRAY");
/*     */   
/*     */   private static final int SCREEN = 0;
/*     */   private static final String SYSTEM_TRAY_PROPERTY_NAME = "systemTray";
/*  46 */   private static final XAtom _NET_SYSTEM_TRAY = XAtom.get("_NET_SYSTEM_TRAY_S0");
/*  47 */   private static final XAtom _XEMBED_INFO = XAtom.get("_XEMBED_INFO");
/*  48 */   private static final XAtom _NET_SYSTEM_TRAY_OPCODE = XAtom.get("_NET_SYSTEM_TRAY_OPCODE");
/*  49 */   private static final XAtom _NET_WM_ICON = XAtom.get("_NET_WM_ICON");
/*     */   private static final long SYSTEM_TRAY_REQUEST_DOCK = 0L;
/*     */   
/*     */   XSystemTrayPeer(SystemTray paramSystemTray) {
/*  53 */     this.target = paramSystemTray;
/*  54 */     peerInstance = this;
/*     */     
/*  56 */     this.selection.addSelectionListener(this);
/*     */     
/*  58 */     long l = this.selection.getOwner(0);
/*  59 */     this.available = (l != 0L);
/*     */     
/*  61 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/*  62 */       log.fine(" check if system tray is available. selection owner: " + l);
/*     */     }
/*     */   }
/*     */   
/*     */   public void ownerChanged(int paramInt, XMSelection paramXMSelection, long paramLong1, long paramLong2, long paramLong3) {
/*  67 */     if (paramInt != 0) {
/*     */       return;
/*     */     }
/*  70 */     if (!this.available) {
/*  71 */       this.available = true;
/*  72 */       firePropertyChange("systemTray", null, this.target);
/*     */     } else {
/*  74 */       removeTrayPeers();
/*     */     } 
/*  76 */     createTrayPeers();
/*     */   }
/*     */   
/*     */   public void ownerDeath(int paramInt, XMSelection paramXMSelection, long paramLong) {
/*  80 */     if (paramInt != 0) {
/*     */       return;
/*     */     }
/*  83 */     if (this.available) {
/*  84 */       this.available = false;
/*  85 */       firePropertyChange("systemTray", this.target, null);
/*  86 */       removeTrayPeers();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void selectionChanged(int paramInt, XMSelection paramXMSelection, long paramLong, XPropertyEvent paramXPropertyEvent) {}
/*     */   
/*     */   public Dimension getTrayIconSize() {
/*  94 */     return new Dimension(24, 24);
/*     */   }
/*     */   
/*     */   boolean isAvailable() {
/*  98 */     return this.available;
/*     */   }
/*     */   
/*     */   void dispose() {
/* 102 */     this.selection.removeSelectionListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addTrayIcon(XTrayIconPeer paramXTrayIconPeer) throws AWTException {
/* 109 */     long l1 = this.selection.getOwner(0);
/*     */     
/* 111 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 112 */       log.fine(" send SYSTEM_TRAY_REQUEST_DOCK message to owner: " + l1);
/*     */     }
/*     */     
/* 115 */     if (l1 == 0L) {
/* 116 */       throw new AWTException("TrayIcon couldn't be displayed.");
/*     */     }
/*     */     
/* 119 */     long l2 = paramXTrayIconPeer.getWindow();
/* 120 */     long[] arrayOfLong = { 0L, 1L };
/* 121 */     long l3 = Native.card32ToData(arrayOfLong);
/*     */     
/* 123 */     _XEMBED_INFO.setAtomData(l2, l3, arrayOfLong.length);
/*     */     
/* 125 */     sendMessage(l1, 0L, l2, 0L, 0L);
/*     */   }
/*     */   
/*     */   void sendMessage(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5) {
/* 129 */     XClientMessageEvent xClientMessageEvent = new XClientMessageEvent();
/*     */     
/*     */     try {
/* 132 */       xClientMessageEvent.set_type(33);
/* 133 */       xClientMessageEvent.set_window(paramLong1);
/* 134 */       xClientMessageEvent.set_format(32);
/* 135 */       xClientMessageEvent.set_message_type(_NET_SYSTEM_TRAY_OPCODE.getAtom());
/* 136 */       xClientMessageEvent.set_data(0, 0L);
/* 137 */       xClientMessageEvent.set_data(1, paramLong2);
/* 138 */       xClientMessageEvent.set_data(2, paramLong3);
/* 139 */       xClientMessageEvent.set_data(3, paramLong4);
/* 140 */       xClientMessageEvent.set_data(4, paramLong5);
/*     */       
/* 142 */       XToolkit.awtLock();
/*     */       try {
/* 144 */         XlibWrapper.XSendEvent(XToolkit.getDisplay(), paramLong1, false, 0L, xClientMessageEvent.pData);
/*     */       } finally {
/*     */         
/* 147 */         XToolkit.awtUnlock();
/*     */       } 
/*     */     } finally {
/* 150 */       xClientMessageEvent.dispose();
/*     */     } 
/*     */   }
/*     */   
/*     */   static XSystemTrayPeer getPeerInstance() {
/* 155 */     return peerInstance;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void firePropertyChange(final String propertyName, final Object oldValue, final Object newValue) {
/* 161 */     Runnable runnable = new Runnable() {
/*     */         public void run() {
/* 163 */           AWTAccessor.getSystemTrayAccessor()
/* 164 */             .firePropertyChange(XSystemTrayPeer.this.target, propertyName, oldValue, newValue);
/*     */         }
/*     */       };
/* 167 */     invokeOnEachAppContext(runnable);
/*     */   }
/*     */   
/*     */   private void createTrayPeers() {
/* 171 */     Runnable runnable = new Runnable() {
/*     */         public void run() {
/* 173 */           TrayIcon[] arrayOfTrayIcon = XSystemTrayPeer.this.target.getTrayIcons();
/*     */           try {
/* 175 */             for (TrayIcon trayIcon : arrayOfTrayIcon) {
/* 176 */               AWTAccessor.getTrayIconAccessor().addNotify(trayIcon);
/*     */             }
/* 178 */           } catch (AWTException aWTException) {}
/*     */         }
/*     */       };
/*     */     
/* 182 */     invokeOnEachAppContext(runnable);
/*     */   }
/*     */   
/*     */   private void removeTrayPeers() {
/* 186 */     Runnable runnable = new Runnable() {
/*     */         public void run() {
/* 188 */           TrayIcon[] arrayOfTrayIcon = XSystemTrayPeer.this.target.getTrayIcons();
/* 189 */           for (TrayIcon trayIcon : arrayOfTrayIcon) {
/* 190 */             AWTAccessor.getTrayIconAccessor().removeNotify(trayIcon);
/*     */           }
/*     */         }
/*     */       };
/* 194 */     invokeOnEachAppContext(runnable);
/*     */   }
/*     */   
/*     */   private void invokeOnEachAppContext(Runnable paramRunnable) {
/* 198 */     for (AppContext appContext : AppContext.getAppContexts())
/* 199 */       SunToolkit.invokeLaterOnAppContext(appContext, paramRunnable); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XSystemTrayPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */