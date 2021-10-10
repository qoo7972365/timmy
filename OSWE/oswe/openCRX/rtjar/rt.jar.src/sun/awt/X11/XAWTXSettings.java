/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.Toolkit;
/*     */ import java.util.Map;
/*     */ import sun.awt.XSettings;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class XAWTXSettings
/*     */   extends XSettings
/*     */   implements XMSelectionListener
/*     */ {
/*  43 */   private final XAtom xSettingsPropertyAtom = XAtom.get("_XSETTINGS_SETTINGS");
/*     */   
/*  45 */   private static PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.XAWTXSettings");
/*     */   
/*     */   public static final long MAX_LENGTH = 1000000L;
/*     */   
/*     */   XMSelection settings;
/*     */ 
/*     */   
/*     */   public XAWTXSettings() {
/*  53 */     initXSettings();
/*     */   }
/*     */ 
/*     */   
/*     */   void initXSettings() {
/*  58 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/*  59 */       log.fine("Initializing XAWT XSettings");
/*     */     }
/*  61 */     this.settings = new XMSelection("_XSETTINGS");
/*  62 */     this.settings.addSelectionListener(this);
/*  63 */     initPerScreenXSettings();
/*     */   }
/*     */   
/*     */   void dispose() {
/*  67 */     this.settings.removeSelectionListener(this);
/*     */   }
/*     */   
/*     */   public void ownerDeath(int paramInt, XMSelection paramXMSelection, long paramLong) {
/*  71 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/*  72 */       log.fine("Owner " + paramLong + " died for selection " + paramXMSelection + " screen " + paramInt);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void ownerChanged(int paramInt, XMSelection paramXMSelection, long paramLong1, long paramLong2, long paramLong3) {
/*  78 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/*  79 */       log.fine("New Owner " + paramLong1 + " for selection = " + paramXMSelection + " screen " + paramInt);
/*     */     }
/*     */   }
/*     */   
/*     */   public void selectionChanged(int paramInt, XMSelection paramXMSelection, long paramLong, XPropertyEvent paramXPropertyEvent) {
/*  84 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/*  85 */       log.fine("Selection changed on sel " + paramXMSelection + " screen = " + paramInt + " owner = " + paramLong + " event = " + paramXPropertyEvent);
/*     */     }
/*  87 */     updateXSettings(paramInt, paramLong);
/*     */   }
/*     */   
/*     */   void initPerScreenXSettings() {
/*  91 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/*  92 */       log.fine("Updating Per XSettings changes");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     Map map = null;
/* 102 */     XToolkit.awtLock();
/*     */     try {
/* 104 */       long l = XToolkit.getDisplay();
/* 105 */       int i = (int)XlibWrapper.DefaultScreen(l);
/* 106 */       map = getUpdatedSettings(this.settings.getOwner(i));
/*     */     } finally {
/* 108 */       XToolkit.awtUnlock();
/*     */     } 
/*     */     
/* 111 */     ((XToolkit)Toolkit.getDefaultToolkit()).parseXSettings(0, map);
/*     */   }
/*     */   
/*     */   private void updateXSettings(int paramInt, long paramLong) {
/* 115 */     final Map updatedSettings = getUpdatedSettings(paramLong);
/*     */ 
/*     */ 
/*     */     
/* 119 */     EventQueue.invokeLater(new Runnable() {
/*     */           public void run() {
/* 121 */             ((XToolkit)Toolkit.getDefaultToolkit()).parseXSettings(0, updatedSettings);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private Map getUpdatedSettings(long paramLong) {
/* 127 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 128 */       log.fine("owner =" + paramLong);
/*     */     }
/* 130 */     if (0L == paramLong) {
/* 131 */       return null;
/*     */     }
/*     */     
/* 134 */     Map map = null;
/*     */ 
/*     */     
/*     */     try {
/* 138 */       WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(paramLong, this.xSettingsPropertyAtom, 0L, 1000000L, false, this.xSettingsPropertyAtom.getAtom());
/*     */       try {
/* 140 */         int i = windowPropertyGetter.execute(XErrorHandler.IgnoreBadWindowHandler.getInstance());
/*     */         
/* 142 */         if (i != 0 || windowPropertyGetter.getData() == 0L) {
/* 143 */           if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 144 */             log.fine("OH OH : getter failed  status = " + i);
/*     */           }
/* 146 */           map = null;
/*     */         } 
/*     */         
/* 149 */         long l = windowPropertyGetter.getData();
/*     */         
/* 151 */         if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 152 */           log.fine("noItems = " + windowPropertyGetter.getNumberOfItems());
/*     */         }
/* 154 */         byte[] arrayOfByte = Native.toBytes(l, windowPropertyGetter.getNumberOfItems());
/* 155 */         if (arrayOfByte != null) {
/* 156 */           map = update(arrayOfByte);
/*     */         }
/*     */       } finally {
/* 159 */         windowPropertyGetter.dispose();
/*     */       }
/*     */     
/* 162 */     } catch (Exception exception) {
/* 163 */       exception.printStackTrace();
/*     */     } 
/* 165 */     return map;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XAWTXSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */