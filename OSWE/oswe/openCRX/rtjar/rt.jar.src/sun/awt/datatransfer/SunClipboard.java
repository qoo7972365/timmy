/*     */ package sun.awt.datatransfer;
/*     */ 
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.datatransfer.Clipboard;
/*     */ import java.awt.datatransfer.ClipboardOwner;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.FlavorEvent;
/*     */ import java.awt.datatransfer.FlavorListener;
/*     */ import java.awt.datatransfer.FlavorTable;
/*     */ import java.awt.datatransfer.SystemFlavorMap;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.awt.datatransfer.UnsupportedFlavorException;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Set;
/*     */ import sun.awt.AppContext;
/*     */ import sun.awt.EventListenerAggregate;
/*     */ import sun.awt.PeerEvent;
/*     */ import sun.awt.SunToolkit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SunClipboard
/*     */   extends Clipboard
/*     */   implements PropertyChangeListener
/*     */ {
/*  67 */   private AppContext contentsContext = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Object CLIPBOARD_FLAVOR_LISTENER_KEY;
/*     */ 
/*     */ 
/*     */   
/*  75 */   private volatile int numberOfFlavorListeners = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile long[] currentFormats;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SunClipboard(String paramString) {
/*  85 */     super(paramString);
/*  86 */     this.CLIPBOARD_FLAVOR_LISTENER_KEY = new StringBuffer(paramString + "_CLIPBOARD_FLAVOR_LISTENER_KEY");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setContents(Transferable paramTransferable, ClipboardOwner paramClipboardOwner) {
/*  93 */     if (paramTransferable == null) {
/*  94 */       throw new NullPointerException("contents");
/*     */     }
/*     */     
/*  97 */     initContext();
/*     */     
/*  99 */     final ClipboardOwner oldOwner = this.owner;
/* 100 */     final Transferable oldContents = this.contents;
/*     */     
/*     */     try {
/* 103 */       this.owner = paramClipboardOwner;
/* 104 */       this.contents = new TransferableProxy(paramTransferable, true);
/*     */       
/* 106 */       setContentsNative(paramTransferable);
/*     */     } finally {
/* 108 */       if (clipboardOwner != null && clipboardOwner != paramClipboardOwner) {
/* 109 */         EventQueue.invokeLater(new Runnable() {
/*     */               public void run() {
/* 111 */                 oldOwner.lostOwnership(SunClipboard.this, oldContents);
/*     */               }
/*     */             });
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private synchronized void initContext() {
/* 119 */     AppContext appContext = AppContext.getAppContext();
/*     */     
/* 121 */     if (this.contentsContext != appContext) {
/*     */ 
/*     */       
/* 124 */       synchronized (appContext) {
/* 125 */         if (appContext.isDisposed()) {
/* 126 */           throw new IllegalStateException("Can't set contents from disposed AppContext");
/*     */         }
/* 128 */         appContext
/* 129 */           .addPropertyChangeListener("disposed", this);
/*     */       } 
/* 131 */       if (this.contentsContext != null) {
/* 132 */         this.contentsContext
/* 133 */           .removePropertyChangeListener("disposed", this);
/*     */       }
/* 135 */       this.contentsContext = appContext;
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized Transferable getContents(Object paramObject) {
/* 140 */     if (this.contents != null) {
/* 141 */       return this.contents;
/*     */     }
/* 143 */     return new ClipboardTransferable(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized Transferable getContextContents() {
/* 153 */     AppContext appContext = AppContext.getAppContext();
/* 154 */     return (appContext == this.contentsContext) ? this.contents : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataFlavor[] getAvailableDataFlavors() {
/* 163 */     Transferable transferable = getContextContents();
/* 164 */     if (transferable != null) {
/* 165 */       return transferable.getTransferDataFlavors();
/*     */     }
/*     */     
/* 168 */     long[] arrayOfLong = getClipboardFormatsOpenClose();
/*     */     
/* 170 */     return DataTransferer.getInstance()
/* 171 */       .getFlavorsForFormatsAsArray(arrayOfLong, getDefaultFlavorTable());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDataFlavorAvailable(DataFlavor paramDataFlavor) {
/* 179 */     if (paramDataFlavor == null) {
/* 180 */       throw new NullPointerException("flavor");
/*     */     }
/*     */     
/* 183 */     Transferable transferable = getContextContents();
/* 184 */     if (transferable != null) {
/* 185 */       return transferable.isDataFlavorSupported(paramDataFlavor);
/*     */     }
/*     */     
/* 188 */     long[] arrayOfLong = getClipboardFormatsOpenClose();
/*     */     
/* 190 */     return formatArrayAsDataFlavorSet(arrayOfLong).contains(paramDataFlavor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getData(DataFlavor paramDataFlavor) throws UnsupportedFlavorException, IOException {
/* 199 */     if (paramDataFlavor == null) {
/* 200 */       throw new NullPointerException("flavor");
/*     */     }
/*     */     
/* 203 */     Transferable transferable1 = getContextContents();
/* 204 */     if (transferable1 != null) {
/* 205 */       return transferable1.getTransferData(paramDataFlavor);
/*     */     }
/*     */     
/* 208 */     long l = 0L;
/* 209 */     byte[] arrayOfByte = null;
/* 210 */     Transferable transferable2 = null;
/*     */     
/*     */     try {
/* 213 */       openClipboard((SunClipboard)null);
/*     */       
/* 215 */       long[] arrayOfLong = getClipboardFormats();
/*     */       
/* 217 */       Long long_ = (Long)DataTransferer.getInstance().getFlavorsForFormats(arrayOfLong, getDefaultFlavorTable()).get(paramDataFlavor);
/*     */       
/* 219 */       if (long_ == null) {
/* 220 */         throw new UnsupportedFlavorException(paramDataFlavor);
/*     */       }
/*     */       
/* 223 */       l = long_.longValue();
/* 224 */       arrayOfByte = getClipboardData(l);
/*     */       
/* 226 */       if (DataTransferer.getInstance().isLocaleDependentTextFormat(l)) {
/* 227 */         transferable2 = createLocaleTransferable(arrayOfLong);
/*     */       }
/*     */     } finally {
/*     */       
/* 231 */       closeClipboard();
/*     */     } 
/*     */     
/* 234 */     return DataTransferer.getInstance()
/* 235 */       .translateBytes(arrayOfByte, paramDataFlavor, l, transferable2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Transferable createLocaleTransferable(long[] paramArrayOflong) throws IOException {
/* 244 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void openClipboard(SunClipboard paramSunClipboard) {}
/*     */ 
/*     */   
/*     */   public void closeClipboard() {}
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 256 */     if ("disposed".equals(paramPropertyChangeEvent.getPropertyName()) && Boolean.TRUE
/* 257 */       .equals(paramPropertyChangeEvent.getNewValue())) {
/* 258 */       AppContext appContext = (AppContext)paramPropertyChangeEvent.getSource();
/* 259 */       lostOwnershipLater(appContext);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void lostOwnershipImpl() {
/* 264 */     lostOwnershipLater((AppContext)null);
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
/*     */   protected void lostOwnershipLater(AppContext paramAppContext) {
/* 278 */     AppContext appContext = this.contentsContext;
/* 279 */     if (appContext == null) {
/*     */       return;
/*     */     }
/*     */     
/* 283 */     SunToolkit.postEvent(appContext, new PeerEvent(this, () -> lostOwnershipNow(paramAppContext), 1L));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void lostOwnershipNow(AppContext paramAppContext) {
/* 288 */     SunClipboard sunClipboard = this;
/* 289 */     ClipboardOwner clipboardOwner = null;
/* 290 */     Transferable transferable = null;
/*     */     
/* 292 */     synchronized (sunClipboard) {
/* 293 */       AppContext appContext = sunClipboard.contentsContext;
/*     */       
/* 295 */       if (appContext == null) {
/*     */         return;
/*     */       }
/*     */       
/* 299 */       if (paramAppContext == null || appContext == paramAppContext) {
/* 300 */         clipboardOwner = sunClipboard.owner;
/* 301 */         transferable = sunClipboard.contents;
/* 302 */         sunClipboard.contentsContext = null;
/* 303 */         sunClipboard.owner = null;
/* 304 */         sunClipboard.contents = null;
/* 305 */         sunClipboard.clearNativeContext();
/* 306 */         appContext
/* 307 */           .removePropertyChangeListener("disposed", sunClipboard);
/*     */       } else {
/*     */         return;
/*     */       } 
/*     */     } 
/* 312 */     if (clipboardOwner != null) {
/* 313 */       clipboardOwner.lostOwnership(sunClipboard, transferable);
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
/*     */   protected long[] getClipboardFormatsOpenClose() {
/*     */     try {
/* 327 */       openClipboard((SunClipboard)null);
/* 328 */       return getClipboardFormats();
/*     */     } finally {
/* 330 */       closeClipboard();
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
/*     */   
/*     */   private static Set formatArrayAsDataFlavorSet(long[] paramArrayOflong) {
/* 345 */     return (paramArrayOflong == null) ? null : 
/* 346 */       DataTransferer.getInstance()
/* 347 */       .getFlavorsForFormatsAsSet(paramArrayOflong, getDefaultFlavorTable());
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void addFlavorListener(FlavorListener paramFlavorListener) {
/* 352 */     if (paramFlavorListener == null) {
/*     */       return;
/*     */     }
/* 355 */     AppContext appContext = AppContext.getAppContext();
/*     */     
/* 357 */     EventListenerAggregate eventListenerAggregate = (EventListenerAggregate)appContext.get(this.CLIPBOARD_FLAVOR_LISTENER_KEY);
/* 358 */     if (eventListenerAggregate == null) {
/* 359 */       eventListenerAggregate = new EventListenerAggregate(FlavorListener.class);
/* 360 */       appContext.put(this.CLIPBOARD_FLAVOR_LISTENER_KEY, eventListenerAggregate);
/*     */     } 
/* 362 */     eventListenerAggregate.add(paramFlavorListener);
/*     */     
/* 364 */     if (this.numberOfFlavorListeners++ == 0) {
/* 365 */       long[] arrayOfLong = null;
/*     */       
/* 367 */       try { openClipboard((SunClipboard)null);
/* 368 */         arrayOfLong = getClipboardFormats(); }
/* 369 */       catch (IllegalStateException illegalStateException) {  }
/*     */       finally
/* 371 */       { closeClipboard(); }
/*     */       
/* 373 */       this.currentFormats = arrayOfLong;
/*     */       
/* 375 */       registerClipboardViewerChecked();
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized void removeFlavorListener(FlavorListener paramFlavorListener) {
/* 380 */     if (paramFlavorListener == null) {
/*     */       return;
/*     */     }
/* 383 */     AppContext appContext = AppContext.getAppContext();
/*     */     
/* 385 */     EventListenerAggregate eventListenerAggregate = (EventListenerAggregate)appContext.get(this.CLIPBOARD_FLAVOR_LISTENER_KEY);
/* 386 */     if (eventListenerAggregate == null) {
/*     */       return;
/*     */     }
/*     */     
/* 390 */     if (eventListenerAggregate.remove(paramFlavorListener) && --this.numberOfFlavorListeners == 0) {
/*     */       
/* 392 */       unregisterClipboardViewerChecked();
/* 393 */       this.currentFormats = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized FlavorListener[] getFlavorListeners() {
/* 399 */     EventListenerAggregate eventListenerAggregate = (EventListenerAggregate)AppContext.getAppContext().get(this.CLIPBOARD_FLAVOR_LISTENER_KEY);
/* 400 */     return (eventListenerAggregate == null) ? new FlavorListener[0] : (FlavorListener[])eventListenerAggregate
/* 401 */       .getListenersCopy();
/*     */   }
/*     */   
/*     */   public boolean areFlavorListenersRegistered() {
/* 405 */     return (this.numberOfFlavorListeners > 0);
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
/*     */   protected final void checkChange(long[] paramArrayOflong) {
/* 423 */     if (Arrays.equals(paramArrayOflong, this.currentFormats)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 429 */     this.currentFormats = paramArrayOflong;
/*     */     class SunFlavorChangeNotifier
/*     */       implements Runnable
/*     */     {
/*     */       private final FlavorListener flavorListener;
/*     */       
/*     */       SunFlavorChangeNotifier(FlavorListener param1FlavorListener) {
/* 436 */         this.flavorListener = param1FlavorListener;
/*     */       }
/*     */       
/*     */       public void run() {
/* 440 */         if (this.flavorListener != null) {
/* 441 */           this.flavorListener.flavorsChanged(new FlavorEvent(SunClipboard.this));
/*     */         }
/*     */       }
/*     */     };
/*     */     
/* 446 */     for (AppContext appContext : AppContext.getAppContexts()) {
/*     */       
/* 448 */       if (appContext == null || appContext.isDisposed()) {
/*     */         continue;
/*     */       }
/*     */       
/* 452 */       EventListenerAggregate eventListenerAggregate = (EventListenerAggregate)appContext.get(this.CLIPBOARD_FLAVOR_LISTENER_KEY);
/* 453 */       if (eventListenerAggregate != null) {
/*     */         
/* 455 */         FlavorListener[] arrayOfFlavorListener = (FlavorListener[])eventListenerAggregate.getListenersInternal();
/* 456 */         for (byte b = 0; b < arrayOfFlavorListener.length; b++) {
/* 457 */           SunToolkit.postEvent(appContext, new PeerEvent(this, new SunFlavorChangeNotifier(arrayOfFlavorListener[b]), 1L));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static FlavorTable getDefaultFlavorTable() {
/* 466 */     return (FlavorTable)SystemFlavorMap.getDefaultFlavorMap();
/*     */   }
/*     */   
/*     */   public abstract long getID();
/*     */   
/*     */   protected abstract void clearNativeContext();
/*     */   
/*     */   protected abstract void setContentsNative(Transferable paramTransferable);
/*     */   
/*     */   protected abstract long[] getClipboardFormats();
/*     */   
/*     */   protected abstract byte[] getClipboardData(long paramLong) throws IOException;
/*     */   
/*     */   protected abstract void registerClipboardViewerChecked();
/*     */   
/*     */   protected abstract void unregisterClipboardViewerChecked();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/datatransfer/SunClipboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */