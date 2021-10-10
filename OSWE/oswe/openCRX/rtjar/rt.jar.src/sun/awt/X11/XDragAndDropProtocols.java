/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class XDragAndDropProtocols
/*     */ {
/*     */   private static final List dragProtocols;
/*     */   private static final List dropProtocols;
/*     */   public static final String XDnD = "XDnD";
/*     */   public static final String MotifDnD = "MotifDnD";
/*     */   
/*     */   static {
/*  48 */     XDragSourceProtocolListener xDragSourceProtocolListener = XDragSourceContextPeer.getXDragSourceProtocolListener();
/*     */ 
/*     */     
/*  51 */     XDropTargetProtocolListener xDropTargetProtocolListener = XDropTargetContextPeer.getXDropTargetProtocolListener();
/*     */     
/*  53 */     ArrayList<XDragSourceProtocol> arrayList = new ArrayList();
/*     */     
/*  55 */     XDragSourceProtocol xDragSourceProtocol1 = XDnDDragSourceProtocol.createInstance(xDragSourceProtocolListener);
/*  56 */     arrayList.add(xDragSourceProtocol1);
/*     */     
/*  58 */     XDragSourceProtocol xDragSourceProtocol2 = MotifDnDDragSourceProtocol.createInstance(xDragSourceProtocolListener);
/*  59 */     arrayList.add(xDragSourceProtocol2);
/*     */     
/*  61 */     ArrayList<XDropTargetProtocol> arrayList1 = new ArrayList();
/*     */     
/*  63 */     XDropTargetProtocol xDropTargetProtocol1 = XDnDDropTargetProtocol.createInstance(xDropTargetProtocolListener);
/*  64 */     arrayList1.add(xDropTargetProtocol1);
/*     */     
/*  66 */     XDropTargetProtocol xDropTargetProtocol2 = MotifDnDDropTargetProtocol.createInstance(xDropTargetProtocolListener);
/*  67 */     arrayList1.add(xDropTargetProtocol2);
/*     */     
/*  69 */     dragProtocols = Collections.unmodifiableList(arrayList);
/*  70 */     dropProtocols = Collections.unmodifiableList(arrayList1);
/*     */   }
/*     */   
/*     */   static Iterator getDragSourceProtocols() {
/*  74 */     return dragProtocols.iterator();
/*     */   }
/*     */   
/*     */   static Iterator getDropTargetProtocols() {
/*  78 */     return dropProtocols.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XDragSourceProtocol getDragSourceProtocol(String paramString) {
/*  87 */     if (paramString == null) {
/*  88 */       return null;
/*     */     }
/*     */     
/*  91 */     Iterator<XDragSourceProtocol> iterator = getDragSourceProtocols();
/*  92 */     while (iterator.hasNext()) {
/*     */       
/*  94 */       XDragSourceProtocol xDragSourceProtocol = iterator.next();
/*  95 */       if (xDragSourceProtocol.getProtocolName().equals(paramString)) {
/*  96 */         return xDragSourceProtocol;
/*     */       }
/*     */     } 
/*     */     
/* 100 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XDropTargetProtocol getDropTargetProtocol(String paramString) {
/* 109 */     if (paramString == null) {
/* 110 */       return null;
/*     */     }
/*     */     
/* 113 */     Iterator<XDropTargetProtocol> iterator = getDropTargetProtocols();
/* 114 */     while (iterator.hasNext()) {
/*     */       
/* 116 */       XDropTargetProtocol xDropTargetProtocol = iterator.next();
/* 117 */       if (xDropTargetProtocol.getProtocolName().equals(paramString)) {
/* 118 */         return xDropTargetProtocol;
/*     */       }
/*     */     } 
/*     */     
/* 122 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XDragAndDropProtocols.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */