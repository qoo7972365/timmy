/*      */ package javax.swing;
/*      */ 
/*      */ import java.awt.Component;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.Image;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.datatransfer.Clipboard;
/*      */ import java.awt.datatransfer.DataFlavor;
/*      */ import java.awt.datatransfer.Transferable;
/*      */ import java.awt.datatransfer.UnsupportedFlavorException;
/*      */ import java.awt.dnd.DragGestureEvent;
/*      */ import java.awt.dnd.DragGestureListener;
/*      */ import java.awt.dnd.DragGestureRecognizer;
/*      */ import java.awt.dnd.DragSource;
/*      */ import java.awt.dnd.DragSourceContext;
/*      */ import java.awt.dnd.DragSourceDragEvent;
/*      */ import java.awt.dnd.DragSourceDropEvent;
/*      */ import java.awt.dnd.DragSourceEvent;
/*      */ import java.awt.dnd.DragSourceListener;
/*      */ import java.awt.dnd.DropTarget;
/*      */ import java.awt.dnd.DropTargetDragEvent;
/*      */ import java.awt.dnd.DropTargetDropEvent;
/*      */ import java.awt.dnd.DropTargetEvent;
/*      */ import java.awt.dnd.DropTargetListener;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.InputEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.beans.BeanInfo;
/*      */ import java.beans.IntrospectionException;
/*      */ import java.beans.Introspector;
/*      */ import java.beans.PropertyDescriptor;
/*      */ import java.io.IOException;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Method;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.TooManyListenersException;
/*      */ import javax.swing.event.EventListenerList;
/*      */ import javax.swing.plaf.UIResource;
/*      */ import javax.swing.text.JTextComponent;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.misc.JavaSecurityAccess;
/*      */ import sun.misc.SharedSecrets;
/*      */ import sun.reflect.misc.MethodUtil;
/*      */ import sun.swing.SwingAccessor;
/*      */ import sun.swing.SwingUtilities2;
/*      */ import sun.swing.UIAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class TransferHandler
/*      */   implements Serializable
/*      */ {
/*      */   public static final int NONE = 0;
/*      */   public static final int COPY = 1;
/*      */   public static final int MOVE = 2;
/*      */   public static final int COPY_OR_MOVE = 3;
/*      */   public static final int LINK = 1073741824;
/*      */   private Image dragImage;
/*      */   private Point dragImageOffset;
/*      */   private String propertyName;
/*      */   
/*      */   static interface HasGetTransferHandler
/*      */   {
/*      */     TransferHandler getTransferHandler();
/*      */   }
/*      */   
/*      */   public static class DropLocation
/*      */   {
/*      */     private final Point dropPoint;
/*      */     
/*      */     protected DropLocation(Point param1Point) {
/*  163 */       if (param1Point == null) {
/*  164 */         throw new IllegalArgumentException("Point cannot be null");
/*      */       }
/*      */       
/*  167 */       this.dropPoint = new Point(param1Point);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final Point getDropPoint() {
/*  177 */       return new Point(this.dropPoint);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  189 */       return getClass().getName() + "[dropPoint=" + this.dropPoint + "]";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class TransferSupport
/*      */   {
/*      */     private boolean isDrop;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Component component;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean showDropLocationIsSet;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean showDropLocation;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  220 */     private int dropAction = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Object source;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private TransferHandler.DropLocation dropLocation;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private TransferSupport(Component param1Component, DropTargetEvent param1DropTargetEvent) {
/*  241 */       this.isDrop = true;
/*  242 */       setDNDVariables(param1Component, param1DropTargetEvent);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TransferSupport(Component param1Component, Transferable param1Transferable) {
/*  256 */       if (param1Component == null) {
/*  257 */         throw new NullPointerException("component is null");
/*      */       }
/*      */       
/*  260 */       if (param1Transferable == null) {
/*  261 */         throw new NullPointerException("transferable is null");
/*      */       }
/*      */       
/*  264 */       this.isDrop = false;
/*  265 */       this.component = param1Component;
/*  266 */       this.source = param1Transferable;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void setDNDVariables(Component param1Component, DropTargetEvent param1DropTargetEvent) {
/*  278 */       assert this.isDrop;
/*      */       
/*  280 */       this.component = param1Component;
/*  281 */       this.source = param1DropTargetEvent;
/*  282 */       this.dropLocation = null;
/*  283 */       this.dropAction = -1;
/*  284 */       this.showDropLocationIsSet = false;
/*      */       
/*  286 */       if (this.source == null) {
/*      */         return;
/*      */       }
/*      */       
/*  290 */       assert this.source instanceof DropTargetDragEvent || this.source instanceof DropTargetDropEvent;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  295 */       Point point = (this.source instanceof DropTargetDragEvent) ? ((DropTargetDragEvent)this.source).getLocation() : ((DropTargetDropEvent)this.source).getLocation();
/*      */       
/*  297 */       if (SunToolkit.isInstanceOf(param1Component, "javax.swing.text.JTextComponent")) {
/*  298 */         this
/*  299 */           .dropLocation = SwingAccessor.getJTextComponentAccessor().dropLocationForPoint((JTextComponent)param1Component, point);
/*  300 */       } else if (param1Component instanceof JComponent) {
/*  301 */         this.dropLocation = ((JComponent)param1Component).dropLocationForPoint(point);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isDrop() {
/*  319 */       return this.isDrop;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Component getComponent() {
/*  328 */       return this.component;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void assureIsDrop() {
/*  338 */       if (!this.isDrop) {
/*  339 */         throw new IllegalStateException("Not a drop");
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TransferHandler.DropLocation getDropLocation() {
/*  360 */       assureIsDrop();
/*      */       
/*  362 */       if (this.dropLocation == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  369 */         Point point = (this.source instanceof DropTargetDragEvent) ? ((DropTargetDragEvent)this.source).getLocation() : ((DropTargetDropEvent)this.source).getLocation();
/*      */         
/*  371 */         this.dropLocation = new TransferHandler.DropLocation(point);
/*      */       } 
/*      */       
/*  374 */       return this.dropLocation;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setShowDropLocation(boolean param1Boolean) {
/*  396 */       assureIsDrop();
/*      */       
/*  398 */       this.showDropLocation = param1Boolean;
/*  399 */       this.showDropLocationIsSet = true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setDropAction(int param1Int) {
/*  422 */       assureIsDrop();
/*      */       
/*  424 */       int i = param1Int & getSourceDropActions();
/*      */       
/*  426 */       if (i != 1 && i != 2 && i != 1073741824) {
/*  427 */         throw new IllegalArgumentException("unsupported drop action: " + param1Int);
/*      */       }
/*      */       
/*  430 */       this.dropAction = param1Int;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getDropAction() {
/*  456 */       return (this.dropAction == -1) ? getUserDropAction() : this.dropAction;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getUserDropAction() {
/*  484 */       assureIsDrop();
/*      */       
/*  486 */       return (this.source instanceof DropTargetDragEvent) ? ((DropTargetDragEvent)this.source)
/*  487 */         .getDropAction() : ((DropTargetDropEvent)this.source)
/*  488 */         .getDropAction();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getSourceDropActions() {
/*  517 */       assureIsDrop();
/*      */       
/*  519 */       return (this.source instanceof DropTargetDragEvent) ? ((DropTargetDragEvent)this.source)
/*  520 */         .getSourceActions() : ((DropTargetDropEvent)this.source)
/*  521 */         .getSourceActions();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DataFlavor[] getDataFlavors() {
/*  530 */       if (this.isDrop) {
/*  531 */         if (this.source instanceof DropTargetDragEvent) {
/*  532 */           return ((DropTargetDragEvent)this.source).getCurrentDataFlavors();
/*      */         }
/*  534 */         return ((DropTargetDropEvent)this.source).getCurrentDataFlavors();
/*      */       } 
/*      */ 
/*      */       
/*  538 */       return ((Transferable)this.source).getTransferDataFlavors();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isDataFlavorSupported(DataFlavor param1DataFlavor) {
/*  548 */       if (this.isDrop) {
/*  549 */         if (this.source instanceof DropTargetDragEvent) {
/*  550 */           return ((DropTargetDragEvent)this.source).isDataFlavorSupported(param1DataFlavor);
/*      */         }
/*  552 */         return ((DropTargetDropEvent)this.source).isDataFlavorSupported(param1DataFlavor);
/*      */       } 
/*      */ 
/*      */       
/*  556 */       return ((Transferable)this.source).isDataFlavorSupported(param1DataFlavor);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Transferable getTransferable() {
/*  570 */       if (this.isDrop) {
/*  571 */         if (this.source instanceof DropTargetDragEvent) {
/*  572 */           return ((DropTargetDragEvent)this.source).getTransferable();
/*      */         }
/*  574 */         return ((DropTargetDropEvent)this.source).getTransferable();
/*      */       } 
/*      */ 
/*      */       
/*  578 */       return (Transferable)this.source;
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
/*      */   public static Action getCutAction() {
/*  592 */     return cutAction;
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
/*      */   public static Action getCopyAction() {
/*  604 */     return copyAction;
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
/*      */   public static Action getPasteAction() {
/*  616 */     return pasteAction;
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
/*      */   public TransferHandler(String paramString) {
/*  630 */     this.propertyName = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected TransferHandler() {
/*  637 */     this(null);
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
/*      */   public void setDragImage(Image paramImage) {
/*  663 */     this.dragImage = paramImage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Image getDragImage() {
/*  673 */     return this.dragImage;
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
/*      */   public void setDragImageOffset(Point paramPoint) {
/*  685 */     this.dragImageOffset = new Point(paramPoint);
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
/*      */   public Point getDragImageOffset() {
/*  697 */     if (this.dragImageOffset == null) {
/*  698 */       return new Point(0, 0);
/*      */     }
/*  700 */     return new Point(this.dragImageOffset);
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
/*      */   public void exportAsDrag(JComponent paramJComponent, InputEvent paramInputEvent, int paramInt) {
/*  728 */     int i = getSourceActions(paramJComponent);
/*      */ 
/*      */     
/*  731 */     if (!(paramInputEvent instanceof MouseEvent) || (paramInt != 1 && paramInt != 2 && paramInt != 1073741824) || (i & paramInt) == 0)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  737 */       paramInt = 0;
/*      */     }
/*      */     
/*  740 */     if (paramInt != 0 && !GraphicsEnvironment.isHeadless()) {
/*  741 */       if (recognizer == null) {
/*  742 */         recognizer = new SwingDragGestureRecognizer(new DragHandler());
/*      */       }
/*  744 */       recognizer.gestured(paramJComponent, (MouseEvent)paramInputEvent, i, paramInt);
/*      */     } else {
/*  746 */       exportDone(paramJComponent, null, 0);
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
/*      */   public void exportToClipboard(JComponent paramJComponent, Clipboard paramClipboard, int paramInt) throws IllegalStateException {
/*  781 */     if ((paramInt == 1 || paramInt == 2) && (
/*  782 */       getSourceActions(paramJComponent) & paramInt) != 0) {
/*      */       
/*  784 */       Transferable transferable = createTransferable(paramJComponent);
/*  785 */       if (transferable != null) {
/*      */         try {
/*  787 */           paramClipboard.setContents(transferable, null);
/*  788 */           exportDone(paramJComponent, transferable, paramInt);
/*      */           return;
/*  790 */         } catch (IllegalStateException illegalStateException) {
/*  791 */           exportDone(paramJComponent, transferable, 0);
/*  792 */           throw illegalStateException;
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  797 */     exportDone(paramJComponent, null, 0);
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
/*      */   public boolean importData(TransferSupport paramTransferSupport) {
/*  826 */     return (paramTransferSupport.getComponent() instanceof JComponent) ? 
/*  827 */       importData((JComponent)paramTransferSupport.getComponent(), paramTransferSupport.getTransferable()) : false;
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
/*      */   public boolean importData(JComponent paramJComponent, Transferable paramTransferable) {
/*  851 */     PropertyDescriptor propertyDescriptor = getPropertyDescriptor(paramJComponent);
/*  852 */     if (propertyDescriptor != null) {
/*  853 */       Method method = propertyDescriptor.getWriteMethod();
/*  854 */       if (method == null)
/*      */       {
/*  856 */         return false;
/*      */       }
/*  858 */       Class[] arrayOfClass = method.getParameterTypes();
/*  859 */       if (arrayOfClass.length != 1)
/*      */       {
/*  861 */         return false;
/*      */       }
/*  863 */       DataFlavor dataFlavor = getPropertyDataFlavor(arrayOfClass[0], paramTransferable.getTransferDataFlavors());
/*  864 */       if (dataFlavor != null) {
/*      */         try {
/*  866 */           Object object = paramTransferable.getTransferData(dataFlavor);
/*  867 */           Object[] arrayOfObject = { object };
/*  868 */           MethodUtil.invoke(method, paramJComponent, arrayOfObject);
/*  869 */           return true;
/*  870 */         } catch (Exception exception) {
/*  871 */           System.err.println("Invocation failed");
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  876 */     return false;
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
/*      */   public boolean canImport(TransferSupport paramTransferSupport) {
/*  925 */     return (paramTransferSupport.getComponent() instanceof JComponent) ? 
/*  926 */       canImport((JComponent)paramTransferSupport.getComponent(), paramTransferSupport.getDataFlavors()) : false;
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
/*      */   public boolean canImport(JComponent paramJComponent, DataFlavor[] paramArrayOfDataFlavor) {
/*  949 */     PropertyDescriptor propertyDescriptor = getPropertyDescriptor(paramJComponent);
/*  950 */     if (propertyDescriptor != null) {
/*  951 */       Method method = propertyDescriptor.getWriteMethod();
/*  952 */       if (method == null)
/*      */       {
/*  954 */         return false;
/*      */       }
/*  956 */       Class[] arrayOfClass = method.getParameterTypes();
/*  957 */       if (arrayOfClass.length != 1)
/*      */       {
/*  959 */         return false;
/*      */       }
/*  961 */       DataFlavor dataFlavor = getPropertyDataFlavor(arrayOfClass[0], paramArrayOfDataFlavor);
/*  962 */       if (dataFlavor != null) {
/*  963 */         return true;
/*      */       }
/*      */     } 
/*  966 */     return false;
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
/*      */   public int getSourceActions(JComponent paramJComponent) {
/*  984 */     PropertyDescriptor propertyDescriptor = getPropertyDescriptor(paramJComponent);
/*  985 */     if (propertyDescriptor != null) {
/*  986 */       return 1;
/*      */     }
/*  988 */     return 0;
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
/*      */   public Icon getVisualRepresentation(Transferable paramTransferable) {
/* 1013 */     return null;
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
/*      */   protected Transferable createTransferable(JComponent paramJComponent) {
/* 1030 */     PropertyDescriptor propertyDescriptor = getPropertyDescriptor(paramJComponent);
/* 1031 */     if (propertyDescriptor != null) {
/* 1032 */       return new PropertyTransferable(propertyDescriptor, paramJComponent);
/*      */     }
/* 1034 */     return null;
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
/*      */   protected void exportDone(JComponent paramJComponent, Transferable paramTransferable, int paramInt) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PropertyDescriptor getPropertyDescriptor(JComponent paramJComponent) {
/*      */     BeanInfo beanInfo;
/* 1060 */     if (this.propertyName == null) {
/* 1061 */       return null;
/*      */     }
/* 1063 */     Class<?> clazz = paramJComponent.getClass();
/*      */     
/*      */     try {
/* 1066 */       beanInfo = Introspector.getBeanInfo(clazz);
/* 1067 */     } catch (IntrospectionException introspectionException) {
/* 1068 */       return null;
/*      */     } 
/* 1070 */     PropertyDescriptor[] arrayOfPropertyDescriptor = beanInfo.getPropertyDescriptors();
/* 1071 */     for (byte b = 0; b < arrayOfPropertyDescriptor.length; b++) {
/* 1072 */       if (this.propertyName.equals(arrayOfPropertyDescriptor[b].getName())) {
/* 1073 */         Method method = arrayOfPropertyDescriptor[b].getReadMethod();
/*      */         
/* 1075 */         if (method != null) {
/* 1076 */           Class[] arrayOfClass = method.getParameterTypes();
/*      */           
/* 1078 */           if (arrayOfClass == null || arrayOfClass.length == 0)
/*      */           {
/* 1080 */             return arrayOfPropertyDescriptor[b];
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/* 1085 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DataFlavor getPropertyDataFlavor(Class<?> paramClass, DataFlavor[] paramArrayOfDataFlavor) {
/* 1094 */     for (byte b = 0; b < paramArrayOfDataFlavor.length; b++) {
/* 1095 */       DataFlavor dataFlavor = paramArrayOfDataFlavor[b];
/* 1096 */       if ("application".equals(dataFlavor.getPrimaryType()) && "x-java-jvm-local-objectref"
/* 1097 */         .equals(dataFlavor.getSubType()) && paramClass
/* 1098 */         .isAssignableFrom(dataFlavor.getRepresentationClass()))
/*      */       {
/* 1100 */         return dataFlavor;
/*      */       }
/*      */     } 
/* 1103 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 1108 */   private static SwingDragGestureRecognizer recognizer = null;
/*      */   
/*      */   private static DropTargetListener getDropTargetListener() {
/* 1111 */     synchronized (DropHandler.class) {
/*      */       
/* 1113 */       DropHandler dropHandler = (DropHandler)AppContext.getAppContext().get(DropHandler.class);
/*      */       
/* 1115 */       if (dropHandler == null) {
/* 1116 */         dropHandler = new DropHandler();
/* 1117 */         AppContext.getAppContext().put(DropHandler.class, dropHandler);
/*      */       } 
/*      */       
/* 1120 */       return dropHandler;
/*      */     } 
/*      */   }
/*      */   
/*      */   static class PropertyTransferable
/*      */     implements Transferable {
/*      */     PropertyTransferable(PropertyDescriptor param1PropertyDescriptor, JComponent param1JComponent) {
/* 1127 */       this.property = param1PropertyDescriptor;
/* 1128 */       this.component = param1JComponent;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     JComponent component;
/*      */ 
/*      */     
/*      */     PropertyDescriptor property;
/*      */ 
/*      */     
/*      */     public DataFlavor[] getTransferDataFlavors() {
/* 1140 */       DataFlavor[] arrayOfDataFlavor = new DataFlavor[1];
/* 1141 */       Class<?> clazz = this.property.getPropertyType();
/* 1142 */       String str = "application/x-java-jvm-local-objectref;class=" + clazz.getName();
/*      */       try {
/* 1144 */         arrayOfDataFlavor[0] = new DataFlavor(str);
/* 1145 */       } catch (ClassNotFoundException classNotFoundException) {
/* 1146 */         arrayOfDataFlavor = new DataFlavor[0];
/*      */       } 
/* 1148 */       return arrayOfDataFlavor;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isDataFlavorSupported(DataFlavor param1DataFlavor) {
/* 1159 */       Class<?> clazz = this.property.getPropertyType();
/* 1160 */       if ("application".equals(param1DataFlavor.getPrimaryType()) && "x-java-jvm-local-objectref"
/* 1161 */         .equals(param1DataFlavor.getSubType()) && param1DataFlavor
/* 1162 */         .getRepresentationClass().isAssignableFrom(clazz))
/*      */       {
/* 1164 */         return true;
/*      */       }
/* 1166 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object getTransferData(DataFlavor param1DataFlavor) throws UnsupportedFlavorException, IOException {
/* 1181 */       if (!isDataFlavorSupported(param1DataFlavor)) {
/* 1182 */         throw new UnsupportedFlavorException(param1DataFlavor);
/*      */       }
/* 1184 */       Method method = this.property.getReadMethod();
/* 1185 */       Object object = null;
/*      */       try {
/* 1187 */         object = MethodUtil.invoke(method, this.component, (Object[])null);
/* 1188 */       } catch (Exception exception) {
/* 1189 */         throw new IOException("Property read failed: " + this.property.getName());
/*      */       } 
/* 1191 */       return object;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class SwingDropTarget
/*      */     extends DropTarget
/*      */     implements UIResource
/*      */   {
/*      */     private EventListenerList listenerList;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     SwingDropTarget(Component param1Component) {
/* 1211 */       super(param1Component, 1073741827, null);
/*      */ 
/*      */       
/*      */       try {
/* 1215 */         super.addDropTargetListener(TransferHandler.getDropTargetListener());
/* 1216 */       } catch (TooManyListenersException tooManyListenersException) {}
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void addDropTargetListener(DropTargetListener param1DropTargetListener) throws TooManyListenersException {
/* 1223 */       if (this.listenerList == null) {
/* 1224 */         this.listenerList = new EventListenerList();
/*      */       }
/* 1226 */       this.listenerList.add(DropTargetListener.class, param1DropTargetListener);
/*      */     }
/*      */     
/*      */     public void removeDropTargetListener(DropTargetListener param1DropTargetListener) {
/* 1230 */       if (this.listenerList != null) {
/* 1231 */         this.listenerList.remove(DropTargetListener.class, param1DropTargetListener);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void dragEnter(DropTargetDragEvent param1DropTargetDragEvent) {
/* 1238 */       super.dragEnter(param1DropTargetDragEvent);
/* 1239 */       if (this.listenerList != null) {
/* 1240 */         Object[] arrayOfObject = this.listenerList.getListenerList();
/* 1241 */         for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 1242 */           if (arrayOfObject[i] == DropTargetListener.class) {
/* 1243 */             ((DropTargetListener)arrayOfObject[i + 1]).dragEnter(param1DropTargetDragEvent);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void dragOver(DropTargetDragEvent param1DropTargetDragEvent) {
/* 1250 */       super.dragOver(param1DropTargetDragEvent);
/* 1251 */       if (this.listenerList != null) {
/* 1252 */         Object[] arrayOfObject = this.listenerList.getListenerList();
/* 1253 */         for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 1254 */           if (arrayOfObject[i] == DropTargetListener.class) {
/* 1255 */             ((DropTargetListener)arrayOfObject[i + 1]).dragOver(param1DropTargetDragEvent);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void dragExit(DropTargetEvent param1DropTargetEvent) {
/* 1262 */       super.dragExit(param1DropTargetEvent);
/* 1263 */       if (this.listenerList != null) {
/* 1264 */         Object[] arrayOfObject = this.listenerList.getListenerList();
/* 1265 */         for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 1266 */           if (arrayOfObject[i] == DropTargetListener.class) {
/* 1267 */             ((DropTargetListener)arrayOfObject[i + 1]).dragExit(param1DropTargetEvent);
/*      */           }
/*      */         } 
/*      */       } 
/* 1271 */       if (!isActive()) {
/*      */ 
/*      */         
/* 1274 */         DropTargetListener dropTargetListener = TransferHandler.getDropTargetListener();
/* 1275 */         if (dropTargetListener != null && dropTargetListener instanceof TransferHandler.DropHandler) {
/* 1276 */           ((TransferHandler.DropHandler)dropTargetListener).cleanup(false);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public void drop(DropTargetDropEvent param1DropTargetDropEvent) {
/* 1282 */       super.drop(param1DropTargetDropEvent);
/* 1283 */       if (this.listenerList != null) {
/* 1284 */         Object[] arrayOfObject = this.listenerList.getListenerList();
/* 1285 */         for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 1286 */           if (arrayOfObject[i] == DropTargetListener.class) {
/* 1287 */             ((DropTargetListener)arrayOfObject[i + 1]).drop(param1DropTargetDropEvent);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void dropActionChanged(DropTargetDragEvent param1DropTargetDragEvent) {
/* 1294 */       super.dropActionChanged(param1DropTargetDragEvent);
/* 1295 */       if (this.listenerList != null) {
/* 1296 */         Object[] arrayOfObject = this.listenerList.getListenerList();
/* 1297 */         for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 1298 */           if (arrayOfObject[i] == DropTargetListener.class) {
/* 1299 */             ((DropTargetListener)arrayOfObject[i + 1]).dropActionChanged(param1DropTargetDragEvent);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class DropHandler
/*      */     implements DropTargetListener, Serializable, ActionListener
/*      */   {
/*      */     private Timer timer;
/*      */     
/*      */     private Point lastPosition;
/*      */     
/* 1314 */     private Rectangle outer = new Rectangle();
/* 1315 */     private Rectangle inner = new Rectangle();
/* 1316 */     private int hysteresis = 10;
/*      */     
/*      */     private Component component;
/*      */     private Object state;
/* 1320 */     private TransferHandler.TransferSupport support = new TransferHandler.TransferSupport(null, (DropTargetEvent)null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int AUTOSCROLL_INSET = 10;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void updateAutoscrollRegion(JComponent param1JComponent) {
/* 1339 */       Rectangle rectangle = param1JComponent.getVisibleRect();
/* 1340 */       this.outer.setBounds(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */ 
/*      */       
/* 1343 */       Insets insets = new Insets(0, 0, 0, 0);
/* 1344 */       if (param1JComponent instanceof Scrollable) {
/* 1345 */         byte b = 20;
/*      */         
/* 1347 */         if (rectangle.width >= b) {
/* 1348 */           insets.left = insets.right = 10;
/*      */         }
/*      */         
/* 1351 */         if (rectangle.height >= b) {
/* 1352 */           insets.top = insets.bottom = 10;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1357 */       this.inner.setBounds(rectangle.x + insets.left, rectangle.y + insets.top, rectangle.width - insets.left + insets.right, rectangle.height - insets.top + insets.bottom);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void autoscroll(JComponent param1JComponent, Point param1Point) {
/* 1370 */       if (param1JComponent instanceof Scrollable) {
/* 1371 */         Scrollable scrollable = (Scrollable)param1JComponent;
/* 1372 */         if (param1Point.y < this.inner.y) {
/*      */           
/* 1374 */           int i = scrollable.getScrollableUnitIncrement(this.outer, 1, -1);
/* 1375 */           Rectangle rectangle = new Rectangle(this.inner.x, this.outer.y - i, this.inner.width, i);
/* 1376 */           param1JComponent.scrollRectToVisible(rectangle);
/* 1377 */         } else if (param1Point.y > this.inner.y + this.inner.height) {
/*      */           
/* 1379 */           int i = scrollable.getScrollableUnitIncrement(this.outer, 1, 1);
/* 1380 */           Rectangle rectangle = new Rectangle(this.inner.x, this.outer.y + this.outer.height, this.inner.width, i);
/* 1381 */           param1JComponent.scrollRectToVisible(rectangle);
/*      */         } 
/*      */         
/* 1384 */         if (param1Point.x < this.inner.x) {
/*      */           
/* 1386 */           int i = scrollable.getScrollableUnitIncrement(this.outer, 0, -1);
/* 1387 */           Rectangle rectangle = new Rectangle(this.outer.x - i, this.inner.y, i, this.inner.height);
/* 1388 */           param1JComponent.scrollRectToVisible(rectangle);
/* 1389 */         } else if (param1Point.x > this.inner.x + this.inner.width) {
/*      */           
/* 1391 */           int i = scrollable.getScrollableUnitIncrement(this.outer, 0, 1);
/* 1392 */           Rectangle rectangle = new Rectangle(this.outer.x + this.outer.width, this.inner.y, i, this.inner.height);
/* 1393 */           param1JComponent.scrollRectToVisible(rectangle);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void initPropertiesIfNecessary() {
/* 1403 */       if (this.timer == null) {
/* 1404 */         Toolkit toolkit = Toolkit.getDefaultToolkit();
/*      */ 
/*      */ 
/*      */         
/* 1408 */         Integer integer = (Integer)toolkit.getDesktopProperty("DnD.Autoscroll.interval");
/*      */         
/* 1410 */         this.timer = new Timer((integer == null) ? 100 : integer.intValue(), this);
/*      */ 
/*      */         
/* 1413 */         integer = (Integer)toolkit.getDesktopProperty("DnD.Autoscroll.initialDelay");
/*      */         
/* 1415 */         this.timer.setInitialDelay((integer == null) ? 100 : integer.intValue());
/*      */ 
/*      */         
/* 1418 */         integer = (Integer)toolkit.getDesktopProperty("DnD.Autoscroll.cursorHysteresis");
/*      */         
/* 1420 */         if (integer != null) {
/* 1421 */           this.hysteresis = integer.intValue();
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1433 */       updateAutoscrollRegion((JComponent)this.component);
/* 1434 */       if (this.outer.contains(this.lastPosition) && !this.inner.contains(this.lastPosition)) {
/* 1435 */         autoscroll((JComponent)this.component, this.lastPosition);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void setComponentDropLocation(TransferHandler.TransferSupport param1TransferSupport, boolean param1Boolean) {
/* 1446 */       TransferHandler.DropLocation dropLocation = (param1TransferSupport == null) ? null : param1TransferSupport.getDropLocation();
/*      */       
/* 1448 */       if (SunToolkit.isInstanceOf(this.component, "javax.swing.text.JTextComponent")) {
/* 1449 */         this
/* 1450 */           .state = SwingAccessor.getJTextComponentAccessor().setDropLocation((JTextComponent)this.component, dropLocation, this.state, param1Boolean);
/* 1451 */       } else if (this.component instanceof JComponent) {
/* 1452 */         this.state = ((JComponent)this.component).setDropLocation(dropLocation, this.state, param1Boolean);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void handleDrag(DropTargetDragEvent param1DropTargetDragEvent) {
/* 1458 */       TransferHandler transferHandler = ((TransferHandler.HasGetTransferHandler)this.component).getTransferHandler();
/*      */       
/* 1460 */       if (transferHandler == null) {
/* 1461 */         param1DropTargetDragEvent.rejectDrag();
/* 1462 */         setComponentDropLocation(null, false);
/*      */         
/*      */         return;
/*      */       } 
/* 1466 */       this.support.setDNDVariables(this.component, param1DropTargetDragEvent);
/* 1467 */       boolean bool1 = transferHandler.canImport(this.support);
/*      */       
/* 1469 */       if (bool1) {
/* 1470 */         param1DropTargetDragEvent.acceptDrag(this.support.getDropAction());
/*      */       } else {
/* 1472 */         param1DropTargetDragEvent.rejectDrag();
/*      */       } 
/*      */ 
/*      */       
/* 1476 */       boolean bool2 = this.support.showDropLocationIsSet ? this.support.showDropLocation : bool1;
/*      */ 
/*      */       
/* 1479 */       setComponentDropLocation(bool2 ? this.support : null, false);
/*      */     }
/*      */     
/*      */     public void dragEnter(DropTargetDragEvent param1DropTargetDragEvent) {
/* 1483 */       this.state = null;
/* 1484 */       this.component = param1DropTargetDragEvent.getDropTargetContext().getComponent();
/*      */       
/* 1486 */       handleDrag(param1DropTargetDragEvent);
/*      */       
/* 1488 */       if (this.component instanceof JComponent) {
/* 1489 */         this.lastPosition = param1DropTargetDragEvent.getLocation();
/* 1490 */         updateAutoscrollRegion((JComponent)this.component);
/* 1491 */         initPropertiesIfNecessary();
/*      */       } 
/*      */     }
/*      */     
/*      */     public void dragOver(DropTargetDragEvent param1DropTargetDragEvent) {
/* 1496 */       handleDrag(param1DropTargetDragEvent);
/*      */       
/* 1498 */       if (!(this.component instanceof JComponent)) {
/*      */         return;
/*      */       }
/*      */       
/* 1502 */       Point point = param1DropTargetDragEvent.getLocation();
/*      */       
/* 1504 */       if (Math.abs(point.x - this.lastPosition.x) > this.hysteresis || 
/* 1505 */         Math.abs(point.y - this.lastPosition.y) > this.hysteresis)
/*      */       
/* 1507 */       { if (this.timer.isRunning()) this.timer.stop();
/*      */          }
/* 1509 */       else if (!this.timer.isRunning()) { this.timer.start(); }
/*      */ 
/*      */       
/* 1512 */       this.lastPosition = point;
/*      */     }
/*      */     
/*      */     public void dragExit(DropTargetEvent param1DropTargetEvent) {
/* 1516 */       cleanup(false);
/*      */     }
/*      */ 
/*      */     
/*      */     public void drop(DropTargetDropEvent param1DropTargetDropEvent) {
/* 1521 */       TransferHandler transferHandler = ((TransferHandler.HasGetTransferHandler)this.component).getTransferHandler();
/*      */       
/* 1523 */       if (transferHandler == null) {
/* 1524 */         param1DropTargetDropEvent.rejectDrop();
/* 1525 */         cleanup(false);
/*      */         
/*      */         return;
/*      */       } 
/* 1529 */       this.support.setDNDVariables(this.component, param1DropTargetDropEvent);
/* 1530 */       boolean bool = transferHandler.canImport(this.support);
/*      */       
/* 1532 */       if (bool) {
/* 1533 */         boolean bool2; param1DropTargetDropEvent.acceptDrop(this.support.getDropAction());
/*      */ 
/*      */         
/* 1536 */         boolean bool1 = this.support.showDropLocationIsSet ? this.support.showDropLocation : bool;
/*      */ 
/*      */         
/* 1539 */         setComponentDropLocation(bool1 ? this.support : null, false);
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 1544 */           bool2 = transferHandler.importData(this.support);
/* 1545 */         } catch (RuntimeException runtimeException) {
/* 1546 */           bool2 = false;
/*      */         } 
/*      */         
/* 1549 */         param1DropTargetDropEvent.dropComplete(bool2);
/* 1550 */         cleanup(bool2);
/*      */       } else {
/* 1552 */         param1DropTargetDropEvent.rejectDrop();
/* 1553 */         cleanup(false);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void dropActionChanged(DropTargetDragEvent param1DropTargetDragEvent) {
/* 1562 */       if (this.component == null) {
/*      */         return;
/*      */       }
/*      */       
/* 1566 */       handleDrag(param1DropTargetDragEvent);
/*      */     }
/*      */     
/*      */     private void cleanup(boolean param1Boolean) {
/* 1570 */       setComponentDropLocation(null, param1Boolean);
/* 1571 */       if (this.component instanceof JComponent) {
/* 1572 */         ((JComponent)this.component).dndDone();
/*      */       }
/*      */       
/* 1575 */       if (this.timer != null) {
/* 1576 */         this.timer.stop();
/*      */       }
/*      */       
/* 1579 */       this.state = null;
/* 1580 */       this.component = null;
/* 1581 */       this.lastPosition = null;
/*      */     }
/*      */ 
/*      */     
/*      */     private DropHandler() {}
/*      */   }
/*      */ 
/*      */   
/*      */   private static class DragHandler
/*      */     implements DragGestureListener, DragSourceListener
/*      */   {
/*      */     private boolean scrolls;
/*      */ 
/*      */     
/*      */     private DragHandler() {}
/*      */ 
/*      */     
/*      */     public void dragGestureRecognized(DragGestureEvent param1DragGestureEvent) {
/* 1599 */       JComponent jComponent = (JComponent)param1DragGestureEvent.getComponent();
/* 1600 */       TransferHandler transferHandler = jComponent.getTransferHandler();
/* 1601 */       Transferable transferable = transferHandler.createTransferable(jComponent);
/* 1602 */       if (transferable != null) {
/* 1603 */         this.scrolls = jComponent.getAutoscrolls();
/* 1604 */         jComponent.setAutoscrolls(false);
/*      */         try {
/* 1606 */           Image image = transferHandler.getDragImage();
/* 1607 */           if (image == null) {
/* 1608 */             param1DragGestureEvent.startDrag(null, transferable, this);
/*      */           } else {
/* 1610 */             param1DragGestureEvent.startDrag(null, image, transferHandler.getDragImageOffset(), transferable, this);
/*      */           } 
/*      */           return;
/* 1613 */         } catch (RuntimeException runtimeException) {
/* 1614 */           jComponent.setAutoscrolls(this.scrolls);
/*      */         } 
/*      */       } 
/*      */       
/* 1618 */       transferHandler.exportDone(jComponent, transferable, 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void dragEnter(DragSourceDragEvent param1DragSourceDragEvent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void dragOver(DragSourceDragEvent param1DragSourceDragEvent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void dragExit(DragSourceEvent param1DragSourceEvent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void dragDropEnd(DragSourceDropEvent param1DragSourceDropEvent) {
/* 1645 */       DragSourceContext dragSourceContext = param1DragSourceDropEvent.getDragSourceContext();
/* 1646 */       JComponent jComponent = (JComponent)dragSourceContext.getComponent();
/* 1647 */       if (param1DragSourceDropEvent.getDropSuccess()) {
/* 1648 */         jComponent.getTransferHandler().exportDone(jComponent, dragSourceContext.getTransferable(), param1DragSourceDropEvent.getDropAction());
/*      */       } else {
/* 1650 */         jComponent.getTransferHandler().exportDone(jComponent, dragSourceContext.getTransferable(), 0);
/*      */       } 
/* 1652 */       jComponent.setAutoscrolls(this.scrolls);
/*      */     }
/*      */     
/*      */     public void dropActionChanged(DragSourceDragEvent param1DragSourceDragEvent) {}
/*      */   }
/*      */   
/*      */   private static class SwingDragGestureRecognizer
/*      */     extends DragGestureRecognizer
/*      */   {
/*      */     SwingDragGestureRecognizer(DragGestureListener param1DragGestureListener) {
/* 1662 */       super(DragSource.getDefaultDragSource(), null, 0, param1DragGestureListener);
/*      */     }
/*      */     
/*      */     void gestured(JComponent param1JComponent, MouseEvent param1MouseEvent, int param1Int1, int param1Int2) {
/* 1666 */       setComponent(param1JComponent);
/* 1667 */       setSourceActions(param1Int1);
/* 1668 */       appendEvent(param1MouseEvent);
/* 1669 */       fireDragGestureRecognized(param1Int2, param1MouseEvent.getPoint());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void registerListeners() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void unregisterListeners() {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1688 */   static final Action cutAction = new TransferAction("cut");
/* 1689 */   static final Action copyAction = new TransferAction("copy");
/* 1690 */   static final Action pasteAction = new TransferAction("paste");
/*      */   
/*      */   static class TransferAction
/*      */     extends UIAction implements UIResource {
/*      */     TransferAction(String param1String) {
/* 1695 */       super(param1String);
/*      */     }
/*      */     
/*      */     public boolean isEnabled(Object param1Object) {
/* 1699 */       if (param1Object instanceof JComponent && ((JComponent)param1Object)
/* 1700 */         .getTransferHandler() == null) {
/* 1701 */         return false;
/*      */       }
/*      */       
/* 1704 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 1708 */     private static final JavaSecurityAccess javaSecurityAccess = SharedSecrets.getJavaSecurityAccess();
/*      */     
/*      */     public void actionPerformed(final ActionEvent e) {
/* 1711 */       Object object = e.getSource();
/*      */       
/* 1713 */       final PrivilegedAction<Void> action = new PrivilegedAction<Void>() {
/*      */           public Void run() {
/* 1715 */             TransferHandler.TransferAction.this.actionPerformedImpl(e);
/* 1716 */             return null;
/*      */           }
/*      */         };
/*      */       
/* 1720 */       AccessControlContext accessControlContext1 = AccessController.getContext();
/* 1721 */       AccessControlContext accessControlContext2 = AWTAccessor.getComponentAccessor().getAccessControlContext((Component)object);
/* 1722 */       final AccessControlContext eventAcc = AWTAccessor.getAWTEventAccessor().getAccessControlContext(e);
/*      */       
/* 1724 */       if (accessControlContext2 == null) {
/* 1725 */         javaSecurityAccess.doIntersectionPrivilege(privilegedAction, accessControlContext1, accessControlContext3);
/*      */       } else {
/* 1727 */         javaSecurityAccess.doIntersectionPrivilege(new PrivilegedAction<Void>()
/*      */             {
/*      */               public Void run() {
/* 1730 */                 TransferHandler.TransferAction.javaSecurityAccess.doIntersectionPrivilege(action, eventAcc);
/* 1731 */                 return null;
/*      */               }
/*      */             }accessControlContext1, accessControlContext2);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void actionPerformedImpl(ActionEvent param1ActionEvent) {
/* 1738 */       Object object = param1ActionEvent.getSource();
/* 1739 */       if (object instanceof JComponent) {
/* 1740 */         JComponent jComponent = (JComponent)object;
/* 1741 */         TransferHandler transferHandler = jComponent.getTransferHandler();
/* 1742 */         Clipboard clipboard = getClipboard(jComponent);
/* 1743 */         String str = (String)getValue("Name");
/*      */         
/* 1745 */         Transferable transferable = null;
/*      */ 
/*      */         
/*      */         try {
/* 1749 */           if (clipboard != null && transferHandler != null && str != null) {
/* 1750 */             if ("cut".equals(str)) {
/* 1751 */               transferHandler.exportToClipboard(jComponent, clipboard, 2);
/* 1752 */             } else if ("copy".equals(str)) {
/* 1753 */               transferHandler.exportToClipboard(jComponent, clipboard, 1);
/* 1754 */             } else if ("paste".equals(str)) {
/* 1755 */               transferable = clipboard.getContents(null);
/*      */             } 
/*      */           }
/* 1758 */         } catch (IllegalStateException illegalStateException) {
/*      */           
/* 1760 */           UIManager.getLookAndFeel().provideErrorFeedback(jComponent);
/*      */           
/*      */           return;
/*      */         } 
/*      */         
/* 1765 */         if (transferable != null) {
/* 1766 */           transferHandler.importData(new TransferHandler.TransferSupport(jComponent, transferable));
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Clipboard getClipboard(JComponent param1JComponent) {
/* 1775 */       if (SwingUtilities2.canAccessSystemClipboard()) {
/* 1776 */         return param1JComponent.getToolkit().getSystemClipboard();
/*      */       }
/*      */       
/* 1779 */       Clipboard clipboard = (Clipboard)AppContext.getAppContext().get(SandboxClipboardKey);
/* 1780 */       if (clipboard == null) {
/* 1781 */         clipboard = new Clipboard("Sandboxed Component Clipboard");
/* 1782 */         AppContext.getAppContext().put(SandboxClipboardKey, clipboard);
/*      */       } 
/*      */       
/* 1785 */       return clipboard;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1792 */     private static Object SandboxClipboardKey = new Object();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/TransferHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */