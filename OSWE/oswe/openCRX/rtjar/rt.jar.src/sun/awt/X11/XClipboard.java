/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.io.IOException;
/*     */ import java.security.AccessController;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.SortedMap;
/*     */ import sun.awt.UNIXToolkit;
/*     */ import sun.awt.datatransfer.ClipboardTransferable;
/*     */ import sun.awt.datatransfer.DataTransferer;
/*     */ import sun.awt.datatransfer.SunClipboard;
/*     */ import sun.security.action.GetIntegerAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class XClipboard
/*     */   extends SunClipboard
/*     */   implements OwnershipListener
/*     */ {
/*     */   private final XSelection selection;
/*     */   private long convertSelectionTime;
/*     */   private volatile boolean isSelectionNotifyProcessed;
/*     */   private volatile XAtom targetsPropertyAtom;
/*  57 */   private static final Object classLock = new Object();
/*     */ 
/*     */   
/*     */   private static final int defaultPollInterval = 200;
/*     */ 
/*     */   
/*     */   private static int pollInterval;
/*     */   
/*     */   private static Map<Long, XClipboard> targetsAtom2Clipboard;
/*     */ 
/*     */   
/*     */   public XClipboard(String paramString1, String paramString2) {
/*  69 */     super(paramString1);
/*  70 */     this.selection = new XSelection(XAtom.get(paramString2));
/*  71 */     this.selection.registerOwershipListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ownershipChanged(boolean paramBoolean) {
/*  79 */     if (paramBoolean) {
/*  80 */       checkChangeHere(this.contents);
/*     */     } else {
/*  82 */       lostOwnershipImpl();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized void setContentsNative(Transferable paramTransferable) {
/*  89 */     SortedMap<Long, DataFlavor> sortedMap = DataTransferer.getInstance().getFormatsForTransferable(paramTransferable, DataTransferer.adaptFlavorMap(getDefaultFlavorTable()));
/*  90 */     long[] arrayOfLong = DataTransferer.keysToLongArray(sortedMap);
/*     */     
/*  92 */     if (!this.selection.setOwner(paramTransferable, sortedMap, arrayOfLong, 
/*  93 */         XToolkit.getCurrentServerTime())) {
/*  94 */       this.owner = null;
/*  95 */       this.contents = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public long getID() {
/* 100 */     return this.selection.getSelectionAtom().getAtom();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized Transferable getContents(Object paramObject) {
/* 105 */     if (this.contents != null) {
/* 106 */       return this.contents;
/*     */     }
/* 108 */     return new ClipboardTransferable(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void clearNativeContext() {
/* 113 */     this.selection.reset();
/*     */   }
/*     */ 
/*     */   
/*     */   protected long[] getClipboardFormats() {
/* 118 */     return this.selection.getTargets(XToolkit.getCurrentServerTime());
/*     */   }
/*     */   
/*     */   protected byte[] getClipboardData(long paramLong) throws IOException {
/* 122 */     return this.selection.getData(paramLong, XToolkit.getCurrentServerTime());
/*     */   }
/*     */   
/*     */   private void checkChangeHere(Transferable paramTransferable) {
/* 126 */     if (areFlavorListenersRegistered()) {
/* 127 */       checkChange(DataTransferer.getInstance()
/* 128 */           .getFormatsForTransferableAsArray(paramTransferable, getDefaultFlavorTable()));
/*     */     }
/*     */   }
/*     */   
/*     */   private static int getPollInterval() {
/* 133 */     synchronized (classLock) {
/* 134 */       if (pollInterval <= 0) {
/* 135 */         pollInterval = ((Integer)AccessController.<Integer>doPrivileged(new GetIntegerAction("awt.datatransfer.clipboard.poll.interval", 200))).intValue();
/*     */ 
/*     */         
/* 138 */         if (pollInterval <= 0) {
/* 139 */           pollInterval = 200;
/*     */         }
/*     */       } 
/* 142 */       return pollInterval;
/*     */     } 
/*     */   }
/*     */   
/*     */   private XAtom getTargetsPropertyAtom() {
/* 147 */     if (null == this.targetsPropertyAtom) {
/* 148 */       this
/* 149 */         .targetsPropertyAtom = XAtom.get("XAWT_TARGETS_OF_SELECTION:" + this.selection.getSelectionAtom().getName());
/*     */     }
/* 151 */     return this.targetsPropertyAtom;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerClipboardViewerChecked() {
/* 156 */     this.isSelectionNotifyProcessed = true;
/*     */     
/* 158 */     boolean bool = false;
/* 159 */     XToolkit.awtLock();
/*     */     try {
/* 161 */       synchronized (classLock) {
/*     */         try {
/* 163 */           Thread.sleep(70L);
/* 164 */         } catch (InterruptedException interruptedException) {
/* 165 */           interruptedException.printStackTrace();
/*     */         } 
/* 167 */         if (targetsAtom2Clipboard == null) {
/* 168 */           targetsAtom2Clipboard = new HashMap<>(2);
/*     */         }
/* 170 */         bool = targetsAtom2Clipboard.isEmpty();
/* 171 */         targetsAtom2Clipboard.put(Long.valueOf(getTargetsPropertyAtom().getAtom()), this);
/* 172 */         if (bool) {
/* 173 */           XToolkit.addEventDispatcher(XWindow.getXAWTRootWindow().getWindow(), new SelectionNotifyHandler());
/*     */         }
/*     */       } 
/*     */       
/* 177 */       if (bool) {
/* 178 */         XToolkit.schedule(new CheckChangeTimerTask(), getPollInterval());
/*     */       }
/*     */     } finally {
/* 181 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class CheckChangeTimerTask implements Runnable {
/*     */     public void run() {
/* 187 */       for (XClipboard xClipboard : XClipboard.targetsAtom2Clipboard.values()) {
/* 188 */         xClipboard.getTargetsDelayed();
/*     */       }
/* 190 */       synchronized (XClipboard.classLock) {
/* 191 */         if (XClipboard.targetsAtom2Clipboard != null && !XClipboard.targetsAtom2Clipboard.isEmpty())
/*     */         {
/* 193 */           XToolkit.schedule(this, XClipboard.getPollInterval()); } 
/*     */       } 
/*     */     }
/*     */     private CheckChangeTimerTask() {} }
/*     */   
/*     */   private static class SelectionNotifyHandler implements XEventDispatcher { private SelectionNotifyHandler() {}
/*     */     
/*     */     public void dispatchEvent(XEvent param1XEvent) {
/* 201 */       if (param1XEvent.get_type() == 31) {
/* 202 */         XSelectionEvent xSelectionEvent = param1XEvent.get_xselection();
/* 203 */         XClipboard xClipboard = null;
/* 204 */         synchronized (XClipboard.classLock) {
/* 205 */           if (XClipboard.targetsAtom2Clipboard != null && XClipboard.targetsAtom2Clipboard.isEmpty()) {
/*     */             
/* 207 */             XToolkit.removeEventDispatcher(XWindow.getXAWTRootWindow().getWindow(), this);
/*     */             return;
/*     */           } 
/* 210 */           long l = xSelectionEvent.get_property();
/* 211 */           xClipboard = (XClipboard)XClipboard.targetsAtom2Clipboard.get(Long.valueOf(l));
/*     */         } 
/* 213 */         if (null != xClipboard) {
/* 214 */           xClipboard.checkChange(xSelectionEvent);
/*     */         }
/*     */       } 
/*     */     } }
/*     */ 
/*     */   
/*     */   protected void unregisterClipboardViewerChecked() {
/* 221 */     this.isSelectionNotifyProcessed = false;
/* 222 */     synchronized (classLock) {
/* 223 */       targetsAtom2Clipboard.remove(Long.valueOf(getTargetsPropertyAtom().getAtom()));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void getTargetsDelayed() {
/* 229 */     XToolkit.awtLock();
/*     */     try {
/* 231 */       long l = System.currentTimeMillis();
/* 232 */       if (this.isSelectionNotifyProcessed || l >= this.convertSelectionTime + UNIXToolkit.getDatatransferTimeout()) {
/*     */         
/* 234 */         this.convertSelectionTime = l;
/* 235 */         XlibWrapper.XConvertSelection(XToolkit.getDisplay(), this.selection
/* 236 */             .getSelectionAtom().getAtom(), XDataTransferer.TARGETS_ATOM
/* 237 */             .getAtom(), 
/* 238 */             getTargetsPropertyAtom().getAtom(), 
/* 239 */             XWindow.getXAWTRootWindow().getWindow(), 0L);
/*     */         
/* 241 */         this.isSelectionNotifyProcessed = false;
/*     */       } 
/*     */     } finally {
/* 244 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkChange(XSelectionEvent paramXSelectionEvent) {
/* 254 */     long l = paramXSelectionEvent.get_property();
/* 255 */     if (l != getTargetsPropertyAtom().getAtom()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 260 */     XAtom xAtom = XAtom.get(paramXSelectionEvent.get_selection());
/* 261 */     XSelection xSelection = XSelection.getSelection(xAtom);
/*     */     
/* 263 */     if (null == xSelection || xSelection != this.selection) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 268 */     this.isSelectionNotifyProcessed = true;
/*     */     
/* 270 */     if (this.selection.isOwner()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 275 */     long[] arrayOfLong = null;
/*     */     
/* 277 */     if (l == 0L) {
/*     */       
/* 279 */       arrayOfLong = new long[0];
/*     */     }
/*     */     else {
/*     */       
/* 283 */       WindowPropertyGetter windowPropertyGetter = new WindowPropertyGetter(XWindow.getXAWTRootWindow().getWindow(), XAtom.get(l), 0L, 1000000L, true, 0L);
/*     */ 
/*     */       
/*     */       try {
/* 287 */         windowPropertyGetter.execute();
/* 288 */         arrayOfLong = XSelection.getFormats(windowPropertyGetter);
/*     */       } finally {
/* 290 */         windowPropertyGetter.dispose();
/*     */       } 
/*     */     } 
/*     */     
/* 294 */     XToolkit.awtUnlock();
/*     */     try {
/* 296 */       checkChange(arrayOfLong);
/*     */     } finally {
/* 298 */       XToolkit.awtLock();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XClipboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */