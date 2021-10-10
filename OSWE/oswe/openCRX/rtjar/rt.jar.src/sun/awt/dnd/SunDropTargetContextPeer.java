/*     */ package sun.awt.dnd;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Point;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.awt.datatransfer.UnsupportedFlavorException;
/*     */ import java.awt.dnd.DropTarget;
/*     */ import java.awt.dnd.DropTargetContext;
/*     */ import java.awt.dnd.DropTargetDragEvent;
/*     */ import java.awt.dnd.DropTargetDropEvent;
/*     */ import java.awt.dnd.DropTargetEvent;
/*     */ import java.awt.dnd.InvalidDnDOperationException;
/*     */ import java.awt.dnd.peer.DropTargetContextPeer;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import sun.awt.AppContext;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.awt.datatransfer.DataTransferer;
/*     */ import sun.awt.datatransfer.ToolkitThreadBlockedHandler;
/*     */ import sun.security.util.SecurityConstants;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SunDropTargetContextPeer
/*     */   implements DropTargetContextPeer, Transferable
/*     */ {
/*     */   public static final boolean DISPATCH_SYNC = true;
/*     */   private DropTarget currentDT;
/*     */   private DropTargetContext currentDTC;
/*     */   private long[] currentT;
/*     */   private int currentA;
/*     */   private int currentSA;
/*     */   private int currentDA;
/*     */   private int previousDA;
/*     */   private long nativeDragContext;
/*     */   private Transferable local;
/*     */   private boolean dragRejected = false;
/*  94 */   protected int dropStatus = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean dropComplete = false;
/*     */ 
/*     */ 
/*     */   
/*     */   boolean dropInProcess = false;
/*     */ 
/*     */ 
/*     */   
/* 106 */   protected static final Object _globalLock = new Object();
/*     */   
/* 108 */   private static final PlatformLogger dndLog = PlatformLogger.getLogger("sun.awt.dnd.SunDropTargetContextPeer");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   protected static Transferable currentJVMLocalSourceTransferable = null;
/*     */   
/*     */   public static void setCurrentJVMLocalSourceTransferable(Transferable paramTransferable) throws InvalidDnDOperationException {
/* 117 */     synchronized (_globalLock) {
/* 118 */       if (paramTransferable != null && currentJVMLocalSourceTransferable != null) {
/* 119 */         throw new InvalidDnDOperationException();
/*     */       }
/* 121 */       currentJVMLocalSourceTransferable = paramTransferable;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static final int STATUS_NONE = 0;
/*     */   protected static final int STATUS_WAIT = 1;
/*     */   protected static final int STATUS_ACCEPT = 2;
/*     */   protected static final int STATUS_REJECT = -1;
/*     */   
/*     */   private static Transferable getJVMLocalSourceTransferable() {
/* 131 */     return currentJVMLocalSourceTransferable;
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
/*     */   public DropTarget getDropTarget() {
/* 155 */     return this.currentDT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setTargetActions(int paramInt) {
/* 162 */     this.currentA = paramInt & 0x40000003;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTargetActions() {
/* 171 */     return this.currentA;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transferable getTransferable() {
/* 179 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataFlavor[] getTransferDataFlavors() {
/* 189 */     Transferable transferable = this.local;
/*     */     
/* 191 */     if (transferable != null) {
/* 192 */       return transferable.getTransferDataFlavors();
/*     */     }
/* 194 */     return DataTransferer.getInstance()
/* 195 */       .getFlavorsForFormatsAsArray(this.currentT, 
/* 196 */         DataTransferer.adaptFlavorMap(this.currentDT.getFlavorMap()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDataFlavorSupported(DataFlavor paramDataFlavor) {
/* 205 */     Transferable transferable = this.local;
/*     */     
/* 207 */     if (transferable != null) {
/* 208 */       return transferable.isDataFlavorSupported(paramDataFlavor);
/*     */     }
/* 210 */     return DataTransferer.getInstance()
/* 211 */       .getFlavorsForFormats(this.currentT, 
/* 212 */         DataTransferer.adaptFlavorMap(this.currentDT.getFlavorMap()))
/* 213 */       .containsKey(paramDataFlavor);
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
/*     */   public Object getTransferData(DataFlavor paramDataFlavor) throws UnsupportedFlavorException, IOException, InvalidDnDOperationException {
/* 226 */     SecurityManager securityManager = System.getSecurityManager();
/*     */     try {
/* 228 */       if (!this.dropInProcess && securityManager != null) {
/* 229 */         securityManager.checkPermission(SecurityConstants.AWT.ACCESS_CLIPBOARD_PERMISSION);
/*     */       }
/* 231 */     } catch (Exception exception) {
/* 232 */       Thread thread = Thread.currentThread();
/* 233 */       thread.getUncaughtExceptionHandler().uncaughtException(thread, exception);
/* 234 */       return null;
/*     */     } 
/*     */     
/* 237 */     Long long_ = null;
/* 238 */     Transferable transferable = this.local;
/*     */     
/* 240 */     if (transferable != null) {
/* 241 */       return transferable.getTransferData(paramDataFlavor);
/*     */     }
/*     */     
/* 244 */     if (this.dropStatus != 2 || this.dropComplete) {
/* 245 */       throw new InvalidDnDOperationException("No drop current");
/*     */     }
/*     */ 
/*     */     
/* 249 */     Map<DataFlavor, Long> map = DataTransferer.getInstance().getFlavorsForFormats(this.currentT, 
/* 250 */         DataTransferer.adaptFlavorMap(this.currentDT.getFlavorMap()));
/*     */     
/* 252 */     long_ = map.get(paramDataFlavor);
/* 253 */     if (long_ == null) {
/* 254 */       throw new UnsupportedFlavorException(paramDataFlavor);
/*     */     }
/*     */     
/* 257 */     if (paramDataFlavor.isRepresentationClassRemote() && this.currentDA != 1073741824)
/*     */     {
/* 259 */       throw new InvalidDnDOperationException("only ACTION_LINK is permissable for transfer of java.rmi.Remote objects");
/*     */     }
/*     */     
/* 262 */     long l = long_.longValue();
/*     */     
/* 264 */     Object object = getNativeData(l);
/*     */     
/* 266 */     if (object instanceof byte[])
/*     */       try {
/* 268 */         return DataTransferer.getInstance()
/* 269 */           .translateBytes((byte[])object, paramDataFlavor, l, this);
/* 270 */       } catch (IOException iOException) {
/* 271 */         throw new InvalidDnDOperationException(iOException.getMessage());
/*     */       }  
/* 273 */     if (object instanceof InputStream) {
/*     */       try {
/* 275 */         return DataTransferer.getInstance()
/* 276 */           .translateStream((InputStream)object, paramDataFlavor, l, this);
/* 277 */       } catch (IOException iOException) {
/* 278 */         throw new InvalidDnDOperationException(iOException.getMessage());
/*     */       } 
/*     */     }
/* 281 */     throw new IOException("no native data was transfered");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTransferableJVMLocal() {
/* 292 */     return (this.local != null || getJVMLocalSourceTransferable() != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int handleEnterMessage(Component paramComponent, int paramInt1, int paramInt2, int paramInt3, int paramInt4, long[] paramArrayOflong, long paramLong) {
/* 300 */     return postDropTargetEvent(paramComponent, paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOflong, paramLong, 504, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processEnterMessage(SunDropTargetEvent paramSunDropTargetEvent) {
/* 311 */     Component component = (Component)paramSunDropTargetEvent.getSource();
/* 312 */     DropTarget dropTarget = component.getDropTarget();
/* 313 */     Point point = paramSunDropTargetEvent.getPoint();
/*     */     
/* 315 */     this.local = getJVMLocalSourceTransferable();
/*     */     
/* 317 */     if (this.currentDTC != null) {
/* 318 */       this.currentDTC.removeNotify();
/* 319 */       this.currentDTC = null;
/*     */     } 
/*     */     
/* 322 */     if (component.isShowing() && dropTarget != null && dropTarget.isActive()) {
/* 323 */       this.currentDT = dropTarget;
/* 324 */       this.currentDTC = this.currentDT.getDropTargetContext();
/*     */       
/* 326 */       this.currentDTC.addNotify(this);
/*     */       
/* 328 */       this.currentA = dropTarget.getDefaultActions();
/*     */       
/*     */       try {
/* 331 */         dropTarget.dragEnter(new DropTargetDragEvent(this.currentDTC, point, this.currentDA, this.currentSA));
/*     */ 
/*     */       
/*     */       }
/* 335 */       catch (Exception exception) {
/* 336 */         exception.printStackTrace();
/* 337 */         this.currentDA = 0;
/*     */       } 
/*     */     } else {
/* 340 */       this.currentDT = null;
/* 341 */       this.currentDTC = null;
/* 342 */       this.currentDA = 0;
/* 343 */       this.currentSA = 0;
/* 344 */       this.currentA = 0;
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
/*     */   private void handleExitMessage(Component paramComponent, long paramLong) {
/* 359 */     postDropTargetEvent(paramComponent, 0, 0, 0, 0, null, paramLong, 505, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processExitMessage(SunDropTargetEvent paramSunDropTargetEvent) {
/* 370 */     Component component = (Component)paramSunDropTargetEvent.getSource();
/* 371 */     DropTarget dropTarget = component.getDropTarget();
/* 372 */     DropTargetContext dropTargetContext = null;
/*     */     
/* 374 */     if (dropTarget == null) {
/* 375 */       this.currentDT = null;
/* 376 */       this.currentT = null;
/*     */       
/* 378 */       if (this.currentDTC != null) {
/* 379 */         this.currentDTC.removeNotify();
/*     */       }
/*     */       
/* 382 */       this.currentDTC = null;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 387 */     if (dropTarget != this.currentDT) {
/*     */       
/* 389 */       if (this.currentDTC != null) {
/* 390 */         this.currentDTC.removeNotify();
/*     */       }
/*     */       
/* 393 */       this.currentDT = dropTarget;
/* 394 */       this.currentDTC = dropTarget.getDropTargetContext();
/*     */       
/* 396 */       this.currentDTC.addNotify(this);
/*     */     } 
/*     */     
/* 399 */     dropTargetContext = this.currentDTC;
/*     */     
/* 401 */     if (dropTarget.isActive()) {
/* 402 */       try { dropTarget.dragExit(new DropTargetEvent(dropTargetContext)); }
/* 403 */       catch (Exception exception)
/* 404 */       { exception.printStackTrace(); }
/*     */       finally
/* 406 */       { this.currentA = 0;
/* 407 */         this.currentSA = 0;
/* 408 */         this.currentDA = 0;
/* 409 */         this.currentDT = null;
/* 410 */         this.currentT = null;
/*     */         
/* 412 */         this.currentDTC.removeNotify();
/* 413 */         this.currentDTC = null;
/*     */         
/* 415 */         this.local = null;
/*     */         
/* 417 */         this.dragRejected = false; }
/*     */     
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int handleMotionMessage(Component paramComponent, int paramInt1, int paramInt2, int paramInt3, int paramInt4, long[] paramArrayOflong, long paramLong) {
/* 426 */     return postDropTargetEvent(paramComponent, paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOflong, paramLong, 506, true);
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
/*     */   protected void processMotionMessage(SunDropTargetEvent paramSunDropTargetEvent, boolean paramBoolean) {
/* 438 */     Component component = (Component)paramSunDropTargetEvent.getSource();
/* 439 */     Point point = paramSunDropTargetEvent.getPoint();
/* 440 */     int i = paramSunDropTargetEvent.getID();
/* 441 */     DropTarget dropTarget = component.getDropTarget();
/* 442 */     DropTargetContext dropTargetContext = null;
/*     */     
/* 444 */     if (component.isShowing() && dropTarget != null && dropTarget.isActive()) {
/* 445 */       if (this.currentDT != dropTarget) {
/* 446 */         if (this.currentDTC != null) {
/* 447 */           this.currentDTC.removeNotify();
/*     */         }
/*     */         
/* 450 */         this.currentDT = dropTarget;
/* 451 */         this.currentDTC = null;
/*     */       } 
/*     */       
/* 454 */       dropTargetContext = this.currentDT.getDropTargetContext();
/* 455 */       if (dropTargetContext != this.currentDTC) {
/* 456 */         if (this.currentDTC != null) {
/* 457 */           this.currentDTC.removeNotify();
/*     */         }
/*     */         
/* 460 */         this.currentDTC = dropTargetContext;
/* 461 */         this.currentDTC.addNotify(this);
/*     */       } 
/*     */       
/* 464 */       this.currentA = this.currentDT.getDefaultActions();
/*     */       
/*     */       try {
/* 467 */         DropTargetDragEvent dropTargetDragEvent = new DropTargetDragEvent(dropTargetContext, point, this.currentDA, this.currentSA);
/*     */ 
/*     */ 
/*     */         
/* 471 */         DropTarget dropTarget1 = dropTarget;
/* 472 */         if (paramBoolean) {
/* 473 */           dropTarget1.dropActionChanged(dropTargetDragEvent);
/*     */         } else {
/* 475 */           dropTarget1.dragOver(dropTargetDragEvent);
/*     */         } 
/*     */         
/* 478 */         if (this.dragRejected) {
/* 479 */           this.currentDA = 0;
/*     */         }
/* 481 */       } catch (Exception exception) {
/* 482 */         exception.printStackTrace();
/* 483 */         this.currentDA = 0;
/*     */       } 
/*     */     } else {
/* 486 */       this.currentDA = 0;
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
/*     */   private void handleDropMessage(Component paramComponent, int paramInt1, int paramInt2, int paramInt3, int paramInt4, long[] paramArrayOflong, long paramLong) {
/* 499 */     postDropTargetEvent(paramComponent, paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOflong, paramLong, 502, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processDropMessage(SunDropTargetEvent paramSunDropTargetEvent) {
/* 510 */     Component component = (Component)paramSunDropTargetEvent.getSource();
/* 511 */     Point point = paramSunDropTargetEvent.getPoint();
/* 512 */     DropTarget dropTarget = component.getDropTarget();
/*     */     
/* 514 */     this.dropStatus = 1;
/* 515 */     this.dropComplete = false;
/*     */     
/* 517 */     if (component.isShowing() && dropTarget != null && dropTarget.isActive()) {
/* 518 */       DropTargetContext dropTargetContext = dropTarget.getDropTargetContext();
/*     */       
/* 520 */       this.currentDT = dropTarget;
/*     */       
/* 522 */       if (this.currentDTC != null) {
/* 523 */         this.currentDTC.removeNotify();
/*     */       }
/*     */       
/* 526 */       this.currentDTC = dropTargetContext;
/* 527 */       this.currentDTC.addNotify(this);
/* 528 */       this.currentA = dropTarget.getDefaultActions();
/*     */       
/* 530 */       synchronized (_globalLock) {
/* 531 */         if ((this.local = getJVMLocalSourceTransferable()) != null) {
/* 532 */           setCurrentJVMLocalSourceTransferable(null);
/*     */         }
/*     */       } 
/* 535 */       this.dropInProcess = true;
/*     */       
/*     */       try {
/* 538 */         dropTarget.drop(new DropTargetDropEvent(dropTargetContext, point, this.currentDA, this.currentSA, (this.local != null)));
/*     */       
/*     */       }
/*     */       finally {
/*     */ 
/*     */         
/* 544 */         if (this.dropStatus == 1) {
/* 545 */           rejectDrop();
/* 546 */         } else if (!this.dropComplete) {
/* 547 */           dropComplete(false);
/*     */         } 
/* 549 */         this.dropInProcess = false;
/*     */       } 
/*     */     } else {
/* 552 */       rejectDrop();
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
/*     */   protected int postDropTargetEvent(Component paramComponent, int paramInt1, int paramInt2, int paramInt3, int paramInt4, long[] paramArrayOflong, long paramLong, int paramInt5, boolean paramBoolean) {
/* 564 */     AppContext appContext = SunToolkit.targetToAppContext(paramComponent);
/*     */     
/* 566 */     EventDispatcher eventDispatcher = new EventDispatcher(this, paramInt3, paramInt4, paramArrayOflong, paramLong, paramBoolean);
/*     */ 
/*     */ 
/*     */     
/* 570 */     SunDropTargetEvent sunDropTargetEvent = new SunDropTargetEvent(paramComponent, paramInt5, paramInt1, paramInt2, eventDispatcher);
/*     */ 
/*     */     
/* 573 */     if (paramBoolean == true) {
/* 574 */       DataTransferer.getInstance().getToolkitThreadBlockedHandler().lock();
/*     */     }
/*     */ 
/*     */     
/* 578 */     SunToolkit.postEvent(appContext, sunDropTargetEvent);
/*     */     
/* 580 */     eventPosted(sunDropTargetEvent);
/*     */     
/* 582 */     if (paramBoolean == true) {
/* 583 */       while (!eventDispatcher.isDone()) {
/* 584 */         DataTransferer.getInstance().getToolkitThreadBlockedHandler().enter();
/*     */       }
/*     */       
/* 587 */       DataTransferer.getInstance().getToolkitThreadBlockedHandler().unlock();
/*     */ 
/*     */       
/* 590 */       return eventDispatcher.getReturnValue();
/*     */     } 
/* 592 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void acceptDrag(int paramInt) {
/* 601 */     if (this.currentDT == null) {
/* 602 */       throw new InvalidDnDOperationException("No Drag pending");
/*     */     }
/* 604 */     this.currentDA = mapOperation(paramInt);
/* 605 */     if (this.currentDA != 0) {
/* 606 */       this.dragRejected = false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void rejectDrag() {
/* 615 */     if (this.currentDT == null) {
/* 616 */       throw new InvalidDnDOperationException("No Drag pending");
/*     */     }
/* 618 */     this.currentDA = 0;
/* 619 */     this.dragRejected = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void acceptDrop(int paramInt) {
/* 627 */     if (paramInt == 0) {
/* 628 */       throw new IllegalArgumentException("invalid acceptDrop() action");
/*     */     }
/* 630 */     if (this.dropStatus == 1 || this.dropStatus == 2) {
/* 631 */       this.currentDA = this.currentA = mapOperation(paramInt & this.currentSA);
/*     */       
/* 633 */       this.dropStatus = 2;
/* 634 */       this.dropComplete = false;
/*     */     } else {
/* 636 */       throw new InvalidDnDOperationException("invalid acceptDrop()");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void rejectDrop() {
/* 645 */     if (this.dropStatus != 1) {
/* 646 */       throw new InvalidDnDOperationException("invalid rejectDrop()");
/*     */     }
/* 648 */     this.dropStatus = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 655 */     this.currentDA = 0;
/* 656 */     dropComplete(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int mapOperation(int paramInt) {
/* 664 */     int[] arrayOfInt = { 2, 1, 1073741824 };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 669 */     int i = 0;
/*     */     
/* 671 */     for (byte b = 0; b < arrayOfInt.length; b++) {
/* 672 */       if ((paramInt & arrayOfInt[b]) == arrayOfInt[b]) {
/* 673 */         i = arrayOfInt[b];
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 678 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void dropComplete(boolean paramBoolean) {
/* 686 */     if (this.dropStatus == 0) {
/* 687 */       throw new InvalidDnDOperationException("No Drop pending");
/*     */     }
/*     */     
/* 690 */     if (this.currentDTC != null) this.currentDTC.removeNotify();
/*     */     
/* 692 */     this.currentDT = null;
/* 693 */     this.currentDTC = null;
/* 694 */     this.currentT = null;
/* 695 */     this.currentA = 0;
/*     */     
/* 697 */     synchronized (_globalLock) {
/* 698 */       currentJVMLocalSourceTransferable = null;
/*     */     } 
/*     */     
/* 701 */     this.dropStatus = 0;
/* 702 */     this.dropComplete = true;
/*     */     
/*     */     try {
/* 705 */       doDropDone(paramBoolean, this.currentDA, (this.local != null));
/*     */     } finally {
/* 707 */       this.currentDA = 0;
/*     */ 
/*     */       
/* 710 */       this.nativeDragContext = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized long getNativeDragContext() {
/* 718 */     return this.nativeDragContext;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void eventPosted(SunDropTargetEvent paramSunDropTargetEvent) {}
/*     */   
/*     */   protected void eventProcessed(SunDropTargetEvent paramSunDropTargetEvent, int paramInt, boolean paramBoolean) {}
/*     */   
/*     */   protected abstract Object getNativeData(long paramLong) throws IOException;
/*     */   
/*     */   protected abstract void doDropDone(boolean paramBoolean1, int paramInt, boolean paramBoolean2);
/*     */   
/*     */   protected static class EventDispatcher
/*     */   {
/*     */     private final SunDropTargetContextPeer peer;
/*     */     private final int dropAction;
/*     */     private final int actions;
/*     */     private final long[] formats;
/*     */     private long nativeCtxt;
/*     */     private final boolean dispatchType;
/*     */     private boolean dispatcherDone = false;
/* 739 */     private int returnValue = 0;
/*     */     
/* 741 */     private final HashSet eventSet = new HashSet(3);
/*     */ 
/*     */     
/* 744 */     static final ToolkitThreadBlockedHandler handler = DataTransferer.getInstance().getToolkitThreadBlockedHandler();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     EventDispatcher(SunDropTargetContextPeer param1SunDropTargetContextPeer, int param1Int1, int param1Int2, long[] param1ArrayOflong, long param1Long, boolean param1Boolean) {
/* 753 */       this.peer = param1SunDropTargetContextPeer;
/* 754 */       this.nativeCtxt = param1Long;
/* 755 */       this.dropAction = param1Int1;
/* 756 */       this.actions = param1Int2;
/* 757 */       this
/* 758 */         .formats = (null == param1ArrayOflong) ? null : Arrays.copyOf(param1ArrayOflong, param1ArrayOflong.length);
/* 759 */       this.dispatchType = param1Boolean;
/*     */     }
/*     */     
/*     */     void dispatchEvent(SunDropTargetEvent param1SunDropTargetEvent) {
/* 763 */       int i = param1SunDropTargetEvent.getID();
/*     */       
/* 765 */       switch (i) {
/*     */         case 504:
/* 767 */           dispatchEnterEvent(param1SunDropTargetEvent);
/*     */           return;
/*     */         case 506:
/* 770 */           dispatchMotionEvent(param1SunDropTargetEvent);
/*     */           return;
/*     */         case 505:
/* 773 */           dispatchExitEvent(param1SunDropTargetEvent);
/*     */           return;
/*     */         case 502:
/* 776 */           dispatchDropEvent(param1SunDropTargetEvent);
/*     */           return;
/*     */       } 
/* 779 */       throw new InvalidDnDOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     private void dispatchEnterEvent(SunDropTargetEvent param1SunDropTargetEvent) {
/* 784 */       synchronized (this.peer) {
/*     */ 
/*     */         
/* 787 */         this.peer.previousDA = this.dropAction;
/*     */ 
/*     */         
/* 790 */         this.peer.nativeDragContext = this.nativeCtxt;
/* 791 */         this.peer.currentT = this.formats;
/* 792 */         this.peer.currentSA = this.actions;
/* 793 */         this.peer.currentDA = this.dropAction;
/*     */         
/* 795 */         this.peer.dropStatus = 2;
/* 796 */         this.peer.dropComplete = false;
/*     */         
/*     */         try {
/* 799 */           this.peer.processEnterMessage(param1SunDropTargetEvent);
/*     */         } finally {
/* 801 */           this.peer.dropStatus = 0;
/*     */         } 
/*     */         
/* 804 */         setReturnValue(this.peer.currentDA);
/*     */       } 
/*     */     }
/*     */     
/*     */     private void dispatchMotionEvent(SunDropTargetEvent param1SunDropTargetEvent) {
/* 809 */       synchronized (this.peer) {
/*     */         
/* 811 */         boolean bool = (this.peer.previousDA != this.dropAction) ? true : false;
/* 812 */         this.peer.previousDA = this.dropAction;
/*     */ 
/*     */         
/* 815 */         this.peer.nativeDragContext = this.nativeCtxt;
/* 816 */         this.peer.currentT = this.formats;
/* 817 */         this.peer.currentSA = this.actions;
/* 818 */         this.peer.currentDA = this.dropAction;
/*     */         
/* 820 */         this.peer.dropStatus = 2;
/* 821 */         this.peer.dropComplete = false;
/*     */         
/*     */         try {
/* 824 */           this.peer.processMotionMessage(param1SunDropTargetEvent, bool);
/*     */         } finally {
/* 826 */           this.peer.dropStatus = 0;
/*     */         } 
/*     */         
/* 829 */         setReturnValue(this.peer.currentDA);
/*     */       } 
/*     */     }
/*     */     
/*     */     private void dispatchExitEvent(SunDropTargetEvent param1SunDropTargetEvent) {
/* 834 */       synchronized (this.peer) {
/*     */ 
/*     */         
/* 837 */         this.peer.nativeDragContext = this.nativeCtxt;
/*     */         
/* 839 */         this.peer.processExitMessage(param1SunDropTargetEvent);
/*     */       } 
/*     */     }
/*     */     
/*     */     private void dispatchDropEvent(SunDropTargetEvent param1SunDropTargetEvent) {
/* 844 */       synchronized (this.peer) {
/*     */ 
/*     */         
/* 847 */         this.peer.nativeDragContext = this.nativeCtxt;
/* 848 */         this.peer.currentT = this.formats;
/* 849 */         this.peer.currentSA = this.actions;
/* 850 */         this.peer.currentDA = this.dropAction;
/*     */         
/* 852 */         this.peer.processDropMessage(param1SunDropTargetEvent);
/*     */       } 
/*     */     }
/*     */     
/*     */     void setReturnValue(int param1Int) {
/* 857 */       this.returnValue = param1Int;
/*     */     }
/*     */     
/*     */     int getReturnValue() {
/* 861 */       return this.returnValue;
/*     */     }
/*     */     
/*     */     boolean isDone() {
/* 865 */       return this.eventSet.isEmpty();
/*     */     }
/*     */     
/*     */     void registerEvent(SunDropTargetEvent param1SunDropTargetEvent) {
/* 869 */       handler.lock();
/* 870 */       if (!this.eventSet.add(param1SunDropTargetEvent) && SunDropTargetContextPeer.dndLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 871 */         SunDropTargetContextPeer.dndLog.fine("Event is already registered: " + param1SunDropTargetEvent);
/*     */       }
/* 873 */       handler.unlock();
/*     */     }
/*     */     
/*     */     void unregisterEvent(SunDropTargetEvent param1SunDropTargetEvent) {
/* 877 */       handler.lock();
/*     */       try {
/* 879 */         if (!this.eventSet.remove(param1SunDropTargetEvent)) {
/*     */           return;
/*     */         }
/*     */         
/* 883 */         if (this.eventSet.isEmpty()) {
/* 884 */           if (!this.dispatcherDone && this.dispatchType == true) {
/* 885 */             handler.exit();
/*     */           }
/* 887 */           this.dispatcherDone = true;
/*     */         } 
/*     */       } finally {
/* 890 */         handler.unlock();
/*     */       } 
/*     */       
/*     */       try {
/* 894 */         this.peer.eventProcessed(param1SunDropTargetEvent, this.returnValue, this.dispatcherDone);
/*     */       
/*     */       }
/*     */       finally {
/*     */ 
/*     */         
/* 900 */         if (this.dispatcherDone) {
/* 901 */           this.nativeCtxt = 0L;
/*     */           
/* 903 */           this.peer.nativeDragContext = 0L;
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void unregisterAllEvents() {
/* 910 */       Object[] arrayOfObject = null;
/* 911 */       handler.lock();
/*     */       try {
/* 913 */         arrayOfObject = this.eventSet.toArray();
/*     */       } finally {
/* 915 */         handler.unlock();
/*     */       } 
/*     */       
/* 918 */       if (arrayOfObject != null)
/* 919 */         for (byte b = 0; b < arrayOfObject.length; b++)
/* 920 */           unregisterEvent((SunDropTargetEvent)arrayOfObject[b]);  
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/dnd/SunDropTargetContextPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */