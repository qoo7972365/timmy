/*     */ package java.awt.dnd;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.HeadlessException;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.datatransfer.FlavorMap;
/*     */ import java.awt.datatransfer.SystemFlavorMap;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.awt.dnd.peer.DragSourceContextPeer;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.security.AccessController;
/*     */ import sun.awt.dnd.SunDragSourceContextPeer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DragSource
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 6236096958971414066L;
/*     */   
/*     */   private static Cursor load(String paramString) {
/* 126 */     if (GraphicsEnvironment.isHeadless()) {
/* 127 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 131 */       return (Cursor)Toolkit.getDefaultToolkit().getDesktopProperty(paramString);
/* 132 */     } catch (Exception exception) {
/* 133 */       exception.printStackTrace();
/*     */       
/* 135 */       throw new RuntimeException("failed to load system cursor: " + paramString + " : " + exception.getMessage());
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
/* 148 */   public static final Cursor DefaultCopyDrop = load("DnD.Cursor.CopyDrop");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 158 */   public static final Cursor DefaultMoveDrop = load("DnD.Cursor.MoveDrop");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 168 */   public static final Cursor DefaultLinkDrop = load("DnD.Cursor.LinkDrop");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 178 */   public static final Cursor DefaultCopyNoDrop = load("DnD.Cursor.CopyNoDrop");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 188 */   public static final Cursor DefaultMoveNoDrop = load("DnD.Cursor.MoveNoDrop");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 198 */   public static final Cursor DefaultLinkNoDrop = load("DnD.Cursor.LinkNoDrop");
/*     */ 
/*     */   
/* 201 */   private static final DragSource dflt = GraphicsEnvironment.isHeadless() ? null : new DragSource();
/*     */ 
/*     */   
/*     */   static final String dragSourceListenerK = "dragSourceL";
/*     */ 
/*     */   
/*     */   static final String dragSourceMotionListenerK = "dragSourceMotionL";
/*     */ 
/*     */   
/*     */   private transient FlavorMap flavorMap;
/*     */ 
/*     */   
/*     */   private transient DragSourceListener listener;
/*     */   
/*     */   private transient DragSourceMotionListener motionListener;
/*     */ 
/*     */   
/*     */   public static DragSource getDefaultDragSource() {
/* 219 */     if (GraphicsEnvironment.isHeadless()) {
/* 220 */       throw new HeadlessException();
/*     */     }
/* 222 */     return dflt;
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
/*     */   public static boolean isDragImageSupported() {
/* 236 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 241 */       Boolean bool = (Boolean)Toolkit.getDefaultToolkit().getDesktopProperty("DnD.isDragImageSupported");
/*     */       
/* 243 */       return bool.booleanValue();
/* 244 */     } catch (Exception exception) {
/* 245 */       return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DragSource() throws HeadlessException {
/* 916 */     this.flavorMap = SystemFlavorMap.getDefaultFlavorMap();
/*     */     if (GraphicsEnvironment.isHeadless())
/*     */       throw new HeadlessException(); 
/*     */   }
/*     */   
/*     */   public void startDrag(DragGestureEvent paramDragGestureEvent, Cursor paramCursor, Image paramImage, Point paramPoint, Transferable paramTransferable, DragSourceListener paramDragSourceListener, FlavorMap paramFlavorMap) throws InvalidDnDOperationException {
/*     */     SunDragSourceContextPeer.setDragDropInProgress(true);
/*     */     try {
/*     */       if (paramFlavorMap != null)
/*     */         this.flavorMap = paramFlavorMap; 
/*     */       DragSourceContextPeer dragSourceContextPeer = Toolkit.getDefaultToolkit().createDragSourceContextPeer(paramDragGestureEvent);
/*     */       DragSourceContext dragSourceContext = createDragSourceContext(dragSourceContextPeer, paramDragGestureEvent, paramCursor, paramImage, paramPoint, paramTransferable, paramDragSourceListener);
/*     */       if (dragSourceContext == null)
/*     */         throw new InvalidDnDOperationException(); 
/*     */       dragSourceContextPeer.startDrag(dragSourceContext, dragSourceContext.getCursor(), paramImage, paramPoint);
/*     */     } catch (RuntimeException runtimeException) {
/*     */       SunDragSourceContextPeer.setDragDropInProgress(false);
/*     */       throw runtimeException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void startDrag(DragGestureEvent paramDragGestureEvent, Cursor paramCursor, Transferable paramTransferable, DragSourceListener paramDragSourceListener, FlavorMap paramFlavorMap) throws InvalidDnDOperationException {
/*     */     startDrag(paramDragGestureEvent, paramCursor, null, null, paramTransferable, paramDragSourceListener, paramFlavorMap);
/*     */   }
/*     */   
/*     */   public void startDrag(DragGestureEvent paramDragGestureEvent, Cursor paramCursor, Image paramImage, Point paramPoint, Transferable paramTransferable, DragSourceListener paramDragSourceListener) throws InvalidDnDOperationException {
/*     */     startDrag(paramDragGestureEvent, paramCursor, paramImage, paramPoint, paramTransferable, paramDragSourceListener, null);
/*     */   }
/*     */   
/*     */   public void startDrag(DragGestureEvent paramDragGestureEvent, Cursor paramCursor, Transferable paramTransferable, DragSourceListener paramDragSourceListener) throws InvalidDnDOperationException {
/*     */     startDrag(paramDragGestureEvent, paramCursor, null, null, paramTransferable, paramDragSourceListener, null);
/*     */   }
/*     */   
/*     */   protected DragSourceContext createDragSourceContext(DragSourceContextPeer paramDragSourceContextPeer, DragGestureEvent paramDragGestureEvent, Cursor paramCursor, Image paramImage, Point paramPoint, Transferable paramTransferable, DragSourceListener paramDragSourceListener) {
/*     */     return new DragSourceContext(paramDragSourceContextPeer, paramDragGestureEvent, paramCursor, paramImage, paramPoint, paramTransferable, paramDragSourceListener);
/*     */   }
/*     */   
/*     */   public FlavorMap getFlavorMap() {
/*     */     return this.flavorMap;
/*     */   }
/*     */   
/*     */   public <T extends DragGestureRecognizer> T createDragGestureRecognizer(Class<T> paramClass, Component paramComponent, int paramInt, DragGestureListener paramDragGestureListener) {
/*     */     return Toolkit.getDefaultToolkit().createDragGestureRecognizer(paramClass, this, paramComponent, paramInt, paramDragGestureListener);
/*     */   }
/*     */   
/*     */   public DragGestureRecognizer createDefaultDragGestureRecognizer(Component paramComponent, int paramInt, DragGestureListener paramDragGestureListener) {
/*     */     return Toolkit.getDefaultToolkit().createDragGestureRecognizer((Class)MouseDragGestureRecognizer.class, this, paramComponent, paramInt, paramDragGestureListener);
/*     */   }
/*     */   
/*     */   public void addDragSourceListener(DragSourceListener paramDragSourceListener) {
/*     */     if (paramDragSourceListener != null)
/*     */       synchronized (this) {
/*     */         this.listener = DnDEventMulticaster.add(this.listener, paramDragSourceListener);
/*     */       }  
/*     */   }
/*     */   
/*     */   public void removeDragSourceListener(DragSourceListener paramDragSourceListener) {
/*     */     if (paramDragSourceListener != null)
/*     */       synchronized (this) {
/*     */         this.listener = DnDEventMulticaster.remove(this.listener, paramDragSourceListener);
/*     */       }  
/*     */   }
/*     */   
/*     */   public DragSourceListener[] getDragSourceListeners() {
/*     */     return getListeners(DragSourceListener.class);
/*     */   }
/*     */   
/*     */   public void addDragSourceMotionListener(DragSourceMotionListener paramDragSourceMotionListener) {
/*     */     if (paramDragSourceMotionListener != null)
/*     */       synchronized (this) {
/*     */         this.motionListener = DnDEventMulticaster.add(this.motionListener, paramDragSourceMotionListener);
/*     */       }  
/*     */   }
/*     */   
/*     */   public void removeDragSourceMotionListener(DragSourceMotionListener paramDragSourceMotionListener) {
/*     */     if (paramDragSourceMotionListener != null)
/*     */       synchronized (this) {
/*     */         this.motionListener = DnDEventMulticaster.remove(this.motionListener, paramDragSourceMotionListener);
/*     */       }  
/*     */   }
/*     */   
/*     */   public DragSourceMotionListener[] getDragSourceMotionListeners() {
/*     */     return getListeners(DragSourceMotionListener.class);
/*     */   }
/*     */   
/*     */   public <T extends java.util.EventListener> T[] getListeners(Class<T> paramClass) {
/*     */     DragSourceMotionListener dragSourceMotionListener;
/*     */     DragSourceListener dragSourceListener = null;
/*     */     if (paramClass == DragSourceListener.class) {
/*     */       dragSourceListener = this.listener;
/*     */     } else if (paramClass == DragSourceMotionListener.class) {
/*     */       dragSourceMotionListener = this.motionListener;
/*     */     } 
/*     */     return DnDEventMulticaster.getListeners(dragSourceMotionListener, paramClass);
/*     */   }
/*     */   
/*     */   void processDragEnter(DragSourceDragEvent paramDragSourceDragEvent) {
/*     */     DragSourceListener dragSourceListener = this.listener;
/*     */     if (dragSourceListener != null)
/*     */       dragSourceListener.dragEnter(paramDragSourceDragEvent); 
/*     */   }
/*     */   
/*     */   void processDragOver(DragSourceDragEvent paramDragSourceDragEvent) {
/*     */     DragSourceListener dragSourceListener = this.listener;
/*     */     if (dragSourceListener != null)
/*     */       dragSourceListener.dragOver(paramDragSourceDragEvent); 
/*     */   }
/*     */   
/*     */   void processDropActionChanged(DragSourceDragEvent paramDragSourceDragEvent) {
/*     */     DragSourceListener dragSourceListener = this.listener;
/*     */     if (dragSourceListener != null)
/*     */       dragSourceListener.dropActionChanged(paramDragSourceDragEvent); 
/*     */   }
/*     */   
/*     */   void processDragExit(DragSourceEvent paramDragSourceEvent) {
/*     */     DragSourceListener dragSourceListener = this.listener;
/*     */     if (dragSourceListener != null)
/*     */       dragSourceListener.dragExit(paramDragSourceEvent); 
/*     */   }
/*     */   
/*     */   void processDragDropEnd(DragSourceDropEvent paramDragSourceDropEvent) {
/*     */     DragSourceListener dragSourceListener = this.listener;
/*     */     if (dragSourceListener != null)
/*     */       dragSourceListener.dragDropEnd(paramDragSourceDropEvent); 
/*     */   }
/*     */   
/*     */   void processDragMouseMoved(DragSourceDragEvent paramDragSourceDragEvent) {
/*     */     DragSourceMotionListener dragSourceMotionListener = this.motionListener;
/*     */     if (dragSourceMotionListener != null)
/*     */       dragSourceMotionListener.dragMouseMoved(paramDragSourceDragEvent); 
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*     */     paramObjectOutputStream.defaultWriteObject();
/*     */     paramObjectOutputStream.writeObject(SerializationTester.test(this.flavorMap) ? this.flavorMap : null);
/*     */     DnDEventMulticaster.save(paramObjectOutputStream, "dragSourceL", this.listener);
/*     */     DnDEventMulticaster.save(paramObjectOutputStream, "dragSourceMotionL", this.motionListener);
/*     */     paramObjectOutputStream.writeObject(null);
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
/*     */     paramObjectInputStream.defaultReadObject();
/*     */     this.flavorMap = (FlavorMap)paramObjectInputStream.readObject();
/*     */     if (this.flavorMap == null)
/*     */       this.flavorMap = SystemFlavorMap.getDefaultFlavorMap(); 
/*     */     Object object;
/*     */     while (null != (object = paramObjectInputStream.readObject())) {
/*     */       String str = ((String)object).intern();
/*     */       if ("dragSourceL" == str) {
/*     */         addDragSourceListener((DragSourceListener)paramObjectInputStream.readObject());
/*     */         continue;
/*     */       } 
/*     */       if ("dragSourceMotionL" == str) {
/*     */         addDragSourceMotionListener((DragSourceMotionListener)paramObjectInputStream.readObject());
/*     */         continue;
/*     */       } 
/*     */       paramObjectInputStream.readObject();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int getDragThreshold() {
/*     */     int i = ((Integer)AccessController.<Integer>doPrivileged(new GetIntegerAction("awt.dnd.drag.threshold", 0))).intValue();
/*     */     if (i > 0)
/*     */       return i; 
/*     */     Integer integer = (Integer)Toolkit.getDefaultToolkit().getDesktopProperty("DnD.gestureMotionThreshold");
/*     */     if (integer != null)
/*     */       return integer.intValue(); 
/*     */     return 5;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/dnd/DragSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */