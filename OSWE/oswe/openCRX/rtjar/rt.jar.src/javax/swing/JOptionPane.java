/*      */ package javax.swing;
/*      */ 
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dialog;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Frame;
/*      */ import java.awt.HeadlessException;
/*      */ import java.awt.KeyboardFocusManager;
/*      */ import java.awt.Point;
/*      */ import java.awt.Window;
/*      */ import java.awt.event.ComponentAdapter;
/*      */ import java.awt.event.ComponentEvent;
/*      */ import java.awt.event.WindowAdapter;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.awt.event.WindowListener;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.beans.PropertyVetoException;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Vector;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.swing.event.InternalFrameAdapter;
/*      */ import javax.swing.event.InternalFrameEvent;
/*      */ import javax.swing.plaf.OptionPaneUI;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JOptionPane
/*      */   extends JComponent
/*      */   implements Accessible
/*      */ {
/*      */   private static final String uiClassID = "OptionPaneUI";
/*  324 */   public static final Object UNINITIALIZED_VALUE = "uninitializedValue";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int DEFAULT_OPTION = -1;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int YES_NO_OPTION = 0;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int YES_NO_CANCEL_OPTION = 1;
/*      */ 
/*      */   
/*      */   public static final int OK_CANCEL_OPTION = 2;
/*      */ 
/*      */   
/*      */   public static final int YES_OPTION = 0;
/*      */ 
/*      */   
/*      */   public static final int NO_OPTION = 1;
/*      */ 
/*      */   
/*      */   public static final int CANCEL_OPTION = 2;
/*      */ 
/*      */   
/*      */   public static final int OK_OPTION = 0;
/*      */ 
/*      */   
/*      */   public static final int CLOSED_OPTION = -1;
/*      */ 
/*      */   
/*      */   public static final int ERROR_MESSAGE = 0;
/*      */ 
/*      */   
/*      */   public static final int INFORMATION_MESSAGE = 1;
/*      */ 
/*      */   
/*      */   public static final int WARNING_MESSAGE = 2;
/*      */ 
/*      */   
/*      */   public static final int QUESTION_MESSAGE = 3;
/*      */ 
/*      */   
/*      */   public static final int PLAIN_MESSAGE = -1;
/*      */ 
/*      */   
/*      */   public static final String ICON_PROPERTY = "icon";
/*      */ 
/*      */   
/*      */   public static final String MESSAGE_PROPERTY = "message";
/*      */ 
/*      */   
/*      */   public static final String VALUE_PROPERTY = "value";
/*      */ 
/*      */   
/*      */   public static final String OPTIONS_PROPERTY = "options";
/*      */ 
/*      */   
/*      */   public static final String INITIAL_VALUE_PROPERTY = "initialValue";
/*      */ 
/*      */   
/*      */   public static final String MESSAGE_TYPE_PROPERTY = "messageType";
/*      */ 
/*      */   
/*      */   public static final String OPTION_TYPE_PROPERTY = "optionType";
/*      */ 
/*      */   
/*      */   public static final String SELECTION_VALUES_PROPERTY = "selectionValues";
/*      */ 
/*      */   
/*      */   public static final String INITIAL_SELECTION_VALUE_PROPERTY = "initialSelectionValue";
/*      */ 
/*      */   
/*      */   public static final String INPUT_VALUE_PROPERTY = "inputValue";
/*      */ 
/*      */   
/*      */   public static final String WANTS_INPUT_PROPERTY = "wantsInput";
/*      */ 
/*      */   
/*      */   protected transient Icon icon;
/*      */ 
/*      */   
/*      */   protected transient Object message;
/*      */ 
/*      */   
/*      */   protected transient Object[] options;
/*      */ 
/*      */   
/*      */   protected transient Object initialValue;
/*      */ 
/*      */   
/*      */   protected int messageType;
/*      */ 
/*      */   
/*      */   protected int optionType;
/*      */ 
/*      */   
/*      */   protected transient Object value;
/*      */ 
/*      */   
/*      */   protected transient Object[] selectionValues;
/*      */ 
/*      */   
/*      */   protected transient Object inputValue;
/*      */ 
/*      */   
/*      */   protected transient Object initialSelectionValue;
/*      */ 
/*      */   
/*      */   protected boolean wantsInput;
/*      */ 
/*      */ 
/*      */   
/*      */   public static String showInputDialog(Object paramObject) throws HeadlessException {
/*  440 */     return showInputDialog((Component)null, paramObject);
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
/*      */   public static String showInputDialog(Object paramObject1, Object paramObject2) {
/*  455 */     return showInputDialog((Component)null, paramObject1, paramObject2);
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
/*      */   public static String showInputDialog(Component paramComponent, Object paramObject) throws HeadlessException {
/*  474 */     return showInputDialog(paramComponent, paramObject, UIManager.getString("OptionPane.inputDialogTitle", paramComponent), 3);
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
/*      */   public static String showInputDialog(Component paramComponent, Object paramObject1, Object paramObject2) {
/*  494 */     return (String)showInputDialog(paramComponent, paramObject1, 
/*  495 */         UIManager.getString("OptionPane.inputDialogTitle", paramComponent), 3, (Icon)null, (Object[])null, paramObject2);
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
/*      */   public static String showInputDialog(Component paramComponent, Object paramObject, String paramString, int paramInt) throws HeadlessException {
/*  524 */     return (String)showInputDialog(paramComponent, paramObject, paramString, paramInt, (Icon)null, (Object[])null, (Object)null);
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
/*      */   public static Object showInputDialog(Component paramComponent, Object paramObject1, String paramString, int paramInt, Icon paramIcon, Object[] paramArrayOfObject, Object paramObject2) throws HeadlessException {
/*  568 */     JOptionPane jOptionPane = new JOptionPane(paramObject1, paramInt, 2, paramIcon, null, null);
/*      */ 
/*      */ 
/*      */     
/*  572 */     jOptionPane.setWantsInput(true);
/*  573 */     jOptionPane.setSelectionValues(paramArrayOfObject);
/*  574 */     jOptionPane.setInitialSelectionValue(paramObject2);
/*  575 */     jOptionPane.setComponentOrientation(((paramComponent == null) ? 
/*  576 */         getRootFrame() : paramComponent).getComponentOrientation());
/*      */     
/*  578 */     int i = styleFromMessageType(paramInt);
/*  579 */     JDialog jDialog = jOptionPane.createDialog(paramComponent, paramString, i);
/*      */     
/*  581 */     jOptionPane.selectInitialValue();
/*  582 */     jDialog.show();
/*  583 */     jDialog.dispose();
/*      */     
/*  585 */     Object object = jOptionPane.getInputValue();
/*      */     
/*  587 */     if (object == UNINITIALIZED_VALUE) {
/*  588 */       return null;
/*      */     }
/*  590 */     return object;
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
/*      */   public static void showMessageDialog(Component paramComponent, Object paramObject) throws HeadlessException {
/*  608 */     showMessageDialog(paramComponent, paramObject, UIManager.getString("OptionPane.messageDialogTitle", paramComponent), 1);
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
/*      */   public static void showMessageDialog(Component paramComponent, Object paramObject, String paramString, int paramInt) throws HeadlessException {
/*  637 */     showMessageDialog(paramComponent, paramObject, paramString, paramInt, (Icon)null);
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
/*      */   public static void showMessageDialog(Component paramComponent, Object paramObject, String paramString, int paramInt, Icon paramIcon) throws HeadlessException {
/*  666 */     showOptionDialog(paramComponent, paramObject, paramString, -1, paramInt, paramIcon, (Object[])null, (Object)null);
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
/*      */   public static int showConfirmDialog(Component paramComponent, Object paramObject) throws HeadlessException {
/*  689 */     return showConfirmDialog(paramComponent, paramObject, 
/*  690 */         UIManager.getString("OptionPane.titleText"), 1);
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
/*      */   public static int showConfirmDialog(Component paramComponent, Object paramObject, String paramString, int paramInt) throws HeadlessException {
/*  718 */     return showConfirmDialog(paramComponent, paramObject, paramString, paramInt, 3);
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
/*      */   public static int showConfirmDialog(Component paramComponent, Object paramObject, String paramString, int paramInt1, int paramInt2) throws HeadlessException {
/*  757 */     return showConfirmDialog(paramComponent, paramObject, paramString, paramInt1, paramInt2, (Icon)null);
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
/*      */   public static int showConfirmDialog(Component paramComponent, Object paramObject, String paramString, int paramInt1, int paramInt2, Icon paramIcon) throws HeadlessException {
/*  795 */     return showOptionDialog(paramComponent, paramObject, paramString, paramInt1, paramInt2, paramIcon, (Object[])null, (Object)null);
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
/*      */   public static int showOptionDialog(Component paramComponent, Object paramObject1, String paramString, int paramInt1, int paramInt2, Icon paramIcon, Object[] paramArrayOfObject, Object paramObject2) throws HeadlessException {
/*  857 */     JOptionPane jOptionPane = new JOptionPane(paramObject1, paramInt2, paramInt1, paramIcon, paramArrayOfObject, paramObject2);
/*      */ 
/*      */ 
/*      */     
/*  861 */     jOptionPane.setInitialValue(paramObject2);
/*  862 */     jOptionPane.setComponentOrientation(((paramComponent == null) ? 
/*  863 */         getRootFrame() : paramComponent).getComponentOrientation());
/*      */     
/*  865 */     int i = styleFromMessageType(paramInt2);
/*  866 */     JDialog jDialog = jOptionPane.createDialog(paramComponent, paramString, i);
/*      */     
/*  868 */     jOptionPane.selectInitialValue();
/*  869 */     jDialog.show();
/*  870 */     jDialog.dispose();
/*      */     
/*  872 */     Object object = jOptionPane.getValue();
/*      */     
/*  874 */     if (object == null)
/*  875 */       return -1; 
/*  876 */     if (paramArrayOfObject == null) {
/*  877 */       if (object instanceof Integer)
/*  878 */         return ((Integer)object).intValue(); 
/*  879 */       return -1;
/*      */     } 
/*  881 */     byte b = 0; int j = paramArrayOfObject.length;
/*  882 */     for (; b < j; b++) {
/*  883 */       if (paramArrayOfObject[b].equals(object))
/*  884 */         return b; 
/*      */     } 
/*  886 */     return -1;
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
/*      */   public JDialog createDialog(Component paramComponent, String paramString) throws HeadlessException {
/*  917 */     int i = styleFromMessageType(getMessageType());
/*  918 */     return createDialog(paramComponent, paramString, i);
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
/*      */   public JDialog createDialog(String paramString) throws HeadlessException {
/*  944 */     int i = styleFromMessageType(getMessageType());
/*  945 */     JDialog jDialog = new JDialog((Dialog)null, paramString, true);
/*  946 */     initDialog(jDialog, i, (Component)null);
/*  947 */     return jDialog;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private JDialog createDialog(Component paramComponent, String paramString, int paramInt) throws HeadlessException {
/*      */     JDialog jDialog;
/*  956 */     Window window = getWindowForComponent(paramComponent);
/*  957 */     if (window instanceof Frame) {
/*  958 */       jDialog = new JDialog((Frame)window, paramString, true);
/*      */     } else {
/*  960 */       jDialog = new JDialog((Dialog)window, paramString, true);
/*      */     } 
/*  962 */     if (window instanceof SwingUtilities.SharedOwnerFrame) {
/*      */       
/*  964 */       WindowListener windowListener = SwingUtilities.getSharedOwnerFrameShutdownListener();
/*  965 */       jDialog.addWindowListener(windowListener);
/*      */     } 
/*  967 */     initDialog(jDialog, paramInt, paramComponent);
/*  968 */     return jDialog;
/*      */   }
/*      */   
/*      */   private void initDialog(final JDialog dialog, int paramInt, Component paramComponent) {
/*  972 */     dialog.setComponentOrientation(getComponentOrientation());
/*  973 */     Container container = dialog.getContentPane();
/*      */     
/*  975 */     container.setLayout(new BorderLayout());
/*  976 */     container.add(this, "Center");
/*  977 */     dialog.setResizable(false);
/*  978 */     if (JDialog.isDefaultLookAndFeelDecorated()) {
/*      */       
/*  980 */       boolean bool = UIManager.getLookAndFeel().getSupportsWindowDecorations();
/*  981 */       if (bool) {
/*  982 */         dialog.setUndecorated(true);
/*  983 */         getRootPane().setWindowDecorationStyle(paramInt);
/*      */       } 
/*      */     } 
/*  986 */     dialog.pack();
/*  987 */     dialog.setLocationRelativeTo(paramComponent);
/*      */     
/*  989 */     final PropertyChangeListener listener = new PropertyChangeListener()
/*      */       {
/*      */         
/*      */         public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent)
/*      */         {
/*  994 */           if (dialog.isVisible() && param1PropertyChangeEvent.getSource() == JOptionPane.this && param1PropertyChangeEvent
/*  995 */             .getPropertyName().equals("value") && param1PropertyChangeEvent
/*  996 */             .getNewValue() != null && param1PropertyChangeEvent
/*  997 */             .getNewValue() != JOptionPane.UNINITIALIZED_VALUE) {
/*  998 */             dialog.setVisible(false);
/*      */           }
/*      */         }
/*      */       };
/*      */     
/* 1003 */     WindowAdapter windowAdapter = new WindowAdapter() { private boolean gotFocus = false;
/*      */         
/*      */         public void windowClosing(WindowEvent param1WindowEvent) {
/* 1006 */           JOptionPane.this.setValue((Object)null);
/*      */         }
/*      */         
/*      */         public void windowClosed(WindowEvent param1WindowEvent) {
/* 1010 */           JOptionPane.this.removePropertyChangeListener(listener);
/* 1011 */           dialog.getContentPane().removeAll();
/*      */         }
/*      */ 
/*      */         
/*      */         public void windowGainedFocus(WindowEvent param1WindowEvent) {
/* 1016 */           if (!this.gotFocus) {
/* 1017 */             JOptionPane.this.selectInitialValue();
/* 1018 */             this.gotFocus = true;
/*      */           } 
/*      */         } }
/*      */       ;
/* 1022 */     dialog.addWindowListener(windowAdapter);
/* 1023 */     dialog.addWindowFocusListener(windowAdapter);
/* 1024 */     dialog.addComponentListener(new ComponentAdapter()
/*      */         {
/*      */           public void componentShown(ComponentEvent param1ComponentEvent) {
/* 1027 */             JOptionPane.this.setValue(JOptionPane.UNINITIALIZED_VALUE);
/*      */           }
/*      */         });
/*      */     
/* 1031 */     addPropertyChangeListener(propertyChangeListener);
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
/*      */   public static void showInternalMessageDialog(Component paramComponent, Object paramObject) {
/* 1047 */     showInternalMessageDialog(paramComponent, paramObject, 
/* 1048 */         UIManager.getString("OptionPane.messageDialogTitle", paramComponent), 1);
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
/*      */   public static void showInternalMessageDialog(Component paramComponent, Object paramObject, String paramString, int paramInt) {
/* 1073 */     showInternalMessageDialog(paramComponent, paramObject, paramString, paramInt, (Icon)null);
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
/*      */   public static void showInternalMessageDialog(Component paramComponent, Object paramObject, String paramString, int paramInt, Icon paramIcon) {
/* 1099 */     showInternalOptionDialog(paramComponent, paramObject, paramString, -1, paramInt, paramIcon, (Object[])null, (Object)null);
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
/*      */   public static int showInternalConfirmDialog(Component paramComponent, Object paramObject) {
/* 1116 */     return showInternalConfirmDialog(paramComponent, paramObject, 
/* 1117 */         UIManager.getString("OptionPane.titleText"), 1);
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
/*      */   public static int showInternalConfirmDialog(Component paramComponent, Object paramObject, String paramString, int paramInt) {
/* 1144 */     return showInternalConfirmDialog(paramComponent, paramObject, paramString, paramInt, 3);
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
/*      */   public static int showInternalConfirmDialog(Component paramComponent, Object paramObject, String paramString, int paramInt1, int paramInt2) {
/* 1181 */     return showInternalConfirmDialog(paramComponent, paramObject, paramString, paramInt1, paramInt2, (Icon)null);
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
/*      */   public static int showInternalConfirmDialog(Component paramComponent, Object paramObject, String paramString, int paramInt1, int paramInt2, Icon paramIcon) {
/* 1220 */     return showInternalOptionDialog(paramComponent, paramObject, paramString, paramInt1, paramInt2, paramIcon, (Object[])null, (Object)null);
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
/*      */   public static int showInternalOptionDialog(Component paramComponent, Object paramObject1, String paramString, int paramInt1, int paramInt2, Icon paramIcon, Object[] paramArrayOfObject, Object paramObject2) {
/* 1276 */     JOptionPane jOptionPane = new JOptionPane(paramObject1, paramInt2, paramInt1, paramIcon, paramArrayOfObject, paramObject2);
/*      */     
/* 1278 */     jOptionPane.putClientProperty(ClientPropertyKey.PopupFactory_FORCE_HEAVYWEIGHT_POPUP, Boolean.TRUE);
/*      */ 
/*      */     
/* 1281 */     Component component = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
/*      */     
/* 1283 */     jOptionPane.setInitialValue(paramObject2);
/*      */ 
/*      */     
/* 1286 */     JInternalFrame jInternalFrame = jOptionPane.createInternalFrame(paramComponent, paramString);
/* 1287 */     jOptionPane.selectInitialValue();
/* 1288 */     jInternalFrame.setVisible(true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1298 */     if (jInternalFrame.isVisible() && !jInternalFrame.isShowing()) {
/* 1299 */       Container container = jInternalFrame.getParent();
/* 1300 */       while (container != null) {
/* 1301 */         if (!container.isVisible()) {
/* 1302 */           container.setVisible(true);
/*      */         }
/* 1304 */         container = container.getParent();
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1310 */     try { Method method = AccessController.<Method>doPrivileged(new ModalPrivilegedAction(Container.class, "startLWModal"));
/*      */       
/* 1312 */       if (method != null) {
/* 1313 */         method.invoke(jInternalFrame, (Object[])null);
/*      */       } }
/* 1315 */     catch (IllegalAccessException illegalAccessException) {  }
/* 1316 */     catch (IllegalArgumentException illegalArgumentException) {  }
/* 1317 */     catch (InvocationTargetException invocationTargetException) {}
/*      */ 
/*      */     
/* 1320 */     if (paramComponent instanceof JInternalFrame) {
/*      */       try {
/* 1322 */         ((JInternalFrame)paramComponent).setSelected(true);
/* 1323 */       } catch (PropertyVetoException propertyVetoException) {}
/*      */     }
/*      */ 
/*      */     
/* 1327 */     Object object = jOptionPane.getValue();
/*      */     
/* 1329 */     if (component != null && component.isShowing()) {
/* 1330 */       component.requestFocus();
/*      */     }
/* 1332 */     if (object == null) {
/* 1333 */       return -1;
/*      */     }
/* 1335 */     if (paramArrayOfObject == null) {
/* 1336 */       if (object instanceof Integer) {
/* 1337 */         return ((Integer)object).intValue();
/*      */       }
/* 1339 */       return -1;
/*      */     } 
/* 1341 */     byte b = 0; int i = paramArrayOfObject.length;
/* 1342 */     for (; b < i; b++) {
/* 1343 */       if (paramArrayOfObject[b].equals(object)) {
/* 1344 */         return b;
/*      */       }
/*      */     } 
/* 1347 */     return -1;
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
/*      */   public static String showInternalInputDialog(Component paramComponent, Object paramObject) {
/* 1362 */     return showInternalInputDialog(paramComponent, paramObject, 
/* 1363 */         UIManager.getString("OptionPane.inputDialogTitle", paramComponent), 3);
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
/*      */   public static String showInternalInputDialog(Component paramComponent, Object paramObject, String paramString, int paramInt) {
/* 1382 */     return (String)showInternalInputDialog(paramComponent, paramObject, paramString, paramInt, (Icon)null, (Object[])null, (Object)null);
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
/*      */   public static Object showInternalInputDialog(Component paramComponent, Object paramObject1, String paramString, int paramInt, Icon paramIcon, Object[] paramArrayOfObject, Object paramObject2) {
/* 1418 */     JOptionPane jOptionPane = new JOptionPane(paramObject1, paramInt, 2, paramIcon, null, null);
/*      */     
/* 1420 */     jOptionPane.putClientProperty(ClientPropertyKey.PopupFactory_FORCE_HEAVYWEIGHT_POPUP, Boolean.TRUE);
/*      */ 
/*      */     
/* 1423 */     Component component = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
/*      */     
/* 1425 */     jOptionPane.setWantsInput(true);
/* 1426 */     jOptionPane.setSelectionValues(paramArrayOfObject);
/* 1427 */     jOptionPane.setInitialSelectionValue(paramObject2);
/*      */ 
/*      */     
/* 1430 */     JInternalFrame jInternalFrame = jOptionPane.createInternalFrame(paramComponent, paramString);
/*      */     
/* 1432 */     jOptionPane.selectInitialValue();
/* 1433 */     jInternalFrame.setVisible(true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1443 */     if (jInternalFrame.isVisible() && !jInternalFrame.isShowing()) {
/* 1444 */       Container container = jInternalFrame.getParent();
/* 1445 */       while (container != null) {
/* 1446 */         if (!container.isVisible()) {
/* 1447 */           container.setVisible(true);
/*      */         }
/* 1449 */         container = container.getParent();
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1455 */     try { Method method = AccessController.<Method>doPrivileged(new ModalPrivilegedAction(Container.class, "startLWModal"));
/*      */       
/* 1457 */       if (method != null) {
/* 1458 */         method.invoke(jInternalFrame, (Object[])null);
/*      */       } }
/* 1460 */     catch (IllegalAccessException illegalAccessException) {  }
/* 1461 */     catch (IllegalArgumentException illegalArgumentException) {  }
/* 1462 */     catch (InvocationTargetException invocationTargetException) {}
/*      */ 
/*      */     
/* 1465 */     if (paramComponent instanceof JInternalFrame) {
/*      */       try {
/* 1467 */         ((JInternalFrame)paramComponent).setSelected(true);
/* 1468 */       } catch (PropertyVetoException propertyVetoException) {}
/*      */     }
/*      */ 
/*      */     
/* 1472 */     if (component != null && component.isShowing()) {
/* 1473 */       component.requestFocus();
/*      */     }
/* 1475 */     Object object = jOptionPane.getInputValue();
/*      */     
/* 1477 */     if (object == UNINITIALIZED_VALUE) {
/* 1478 */       return null;
/*      */     }
/* 1480 */     return object;
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
/*      */   public JInternalFrame createInternalFrame(Component paramComponent, String paramString) {
/* 1506 */     JDesktopPane jDesktopPane = getDesktopPaneForComponent(paramComponent);
/*      */     Container container;
/* 1508 */     if (jDesktopPane == null && (paramComponent == null || (
/* 1509 */       container = paramComponent.getParent()) == null)) {
/* 1510 */       throw new RuntimeException("JOptionPane: parentComponent does not have a valid parent");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1515 */     final JInternalFrame iFrame = new JInternalFrame(paramString, false, true, false, false);
/*      */ 
/*      */     
/* 1518 */     jInternalFrame.putClientProperty("JInternalFrame.frameType", "optionDialog");
/* 1519 */     jInternalFrame.putClientProperty("JInternalFrame.messageType", 
/* 1520 */         Integer.valueOf(getMessageType()));
/*      */     
/* 1522 */     jInternalFrame.addInternalFrameListener(new InternalFrameAdapter() {
/*      */           public void internalFrameClosing(InternalFrameEvent param1InternalFrameEvent) {
/* 1524 */             if (JOptionPane.this.getValue() == JOptionPane.UNINITIALIZED_VALUE) {
/* 1525 */               JOptionPane.this.setValue((Object)null);
/*      */             }
/*      */           }
/*      */         });
/* 1529 */     addPropertyChangeListener(new PropertyChangeListener()
/*      */         {
/*      */           
/*      */           public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent)
/*      */           {
/* 1534 */             if (iFrame.isVisible() && param1PropertyChangeEvent
/* 1535 */               .getSource() == JOptionPane.this && param1PropertyChangeEvent
/* 1536 */               .getPropertyName().equals("value")) {
/*      */ 
/*      */               
/* 1539 */               try { Method method = AccessController.<Method>doPrivileged(new JOptionPane.ModalPrivilegedAction(Container.class, "stopLWModal"));
/*      */ 
/*      */                 
/* 1542 */                 if (method != null) {
/* 1543 */                   method.invoke(iFrame, (Object[])null);
/*      */                 } }
/* 1545 */               catch (IllegalAccessException illegalAccessException) {  }
/* 1546 */               catch (IllegalArgumentException illegalArgumentException) {  }
/* 1547 */               catch (InvocationTargetException invocationTargetException) {}
/*      */ 
/*      */               
/*      */               try {
/* 1551 */                 iFrame.setClosed(true);
/*      */               }
/* 1553 */               catch (PropertyVetoException propertyVetoException) {}
/*      */ 
/*      */               
/* 1556 */               iFrame.setVisible(false);
/*      */             } 
/*      */           }
/*      */         });
/* 1560 */     jInternalFrame.getContentPane().add(this, "Center");
/* 1561 */     if (container instanceof JDesktopPane) {
/* 1562 */       container.add(jInternalFrame, JLayeredPane.MODAL_LAYER);
/*      */     } else {
/* 1564 */       container.add(jInternalFrame, "Center");
/*      */     } 
/* 1566 */     Dimension dimension1 = jInternalFrame.getPreferredSize();
/* 1567 */     Dimension dimension2 = container.getSize();
/* 1568 */     Dimension dimension3 = paramComponent.getSize();
/*      */     
/* 1570 */     jInternalFrame.setBounds((dimension2.width - dimension1.width) / 2, (dimension2.height - dimension1.height) / 2, dimension1.width, dimension1.height);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1575 */     Point point = SwingUtilities.convertPoint(paramComponent, 0, 0, container);
/* 1576 */     int i = (dimension3.width - dimension1.width) / 2 + point.x;
/* 1577 */     int j = (dimension3.height - dimension1.height) / 2 + point.y;
/*      */ 
/*      */     
/* 1580 */     int k = i + dimension1.width - dimension2.width;
/* 1581 */     int m = j + dimension1.height - dimension2.height;
/* 1582 */     i = Math.max((k > 0) ? (i - k) : i, 0);
/* 1583 */     j = Math.max((m > 0) ? (j - m) : j, 0);
/* 1584 */     jInternalFrame.setBounds(i, j, dimension1.width, dimension1.height);
/*      */     
/* 1586 */     container.validate();
/*      */     try {
/* 1588 */       jInternalFrame.setSelected(true);
/* 1589 */     } catch (PropertyVetoException propertyVetoException) {}
/*      */     
/* 1591 */     return jInternalFrame;
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
/*      */   public static Frame getFrameForComponent(Component paramComponent) throws HeadlessException {
/* 1611 */     if (paramComponent == null)
/* 1612 */       return getRootFrame(); 
/* 1613 */     if (paramComponent instanceof Frame)
/* 1614 */       return (Frame)paramComponent; 
/* 1615 */     return getFrameForComponent(paramComponent.getParent());
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
/*      */   static Window getWindowForComponent(Component paramComponent) throws HeadlessException {
/* 1636 */     if (paramComponent == null)
/* 1637 */       return getRootFrame(); 
/* 1638 */     if (paramComponent instanceof Frame || paramComponent instanceof Dialog)
/* 1639 */       return (Window)paramComponent; 
/* 1640 */     return getWindowForComponent(paramComponent.getParent());
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
/*      */   public static JDesktopPane getDesktopPaneForComponent(Component paramComponent) {
/* 1655 */     if (paramComponent == null)
/* 1656 */       return null; 
/* 1657 */     if (paramComponent instanceof JDesktopPane)
/* 1658 */       return (JDesktopPane)paramComponent; 
/* 1659 */     return getDesktopPaneForComponent(paramComponent.getParent());
/*      */   }
/*      */   
/* 1662 */   private static final Object sharedFrameKey = JOptionPane.class;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setRootFrame(Frame paramFrame) {
/* 1674 */     if (paramFrame != null) {
/* 1675 */       SwingUtilities.appContextPut(sharedFrameKey, paramFrame);
/*      */     } else {
/* 1677 */       SwingUtilities.appContextRemove(sharedFrameKey);
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
/*      */   public static Frame getRootFrame() throws HeadlessException {
/* 1694 */     Frame frame = (Frame)SwingUtilities.appContextGet(sharedFrameKey);
/* 1695 */     if (frame == null) {
/* 1696 */       frame = SwingUtilities.getSharedOwnerFrame();
/* 1697 */       SwingUtilities.appContextPut(sharedFrameKey, frame);
/*      */     } 
/* 1699 */     return frame;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JOptionPane() {
/* 1706 */     this("JOptionPane message");
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
/*      */   public JOptionPane(Object paramObject) {
/* 1718 */     this(paramObject, -1);
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
/*      */   public JOptionPane(Object paramObject, int paramInt) {
/* 1734 */     this(paramObject, paramInt, -1);
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
/*      */   public JOptionPane(Object paramObject, int paramInt1, int paramInt2) {
/* 1754 */     this(paramObject, paramInt1, paramInt2, (Icon)null);
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
/*      */   public JOptionPane(Object paramObject, int paramInt1, int paramInt2, Icon paramIcon) {
/* 1776 */     this(paramObject, paramInt1, paramInt2, paramIcon, (Object[])null);
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
/*      */   public JOptionPane(Object paramObject, int paramInt1, int paramInt2, Icon paramIcon, Object[] paramArrayOfObject) {
/* 1808 */     this(paramObject, paramInt1, paramInt2, paramIcon, paramArrayOfObject, (Object)null);
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
/*      */   public JOptionPane(Object paramObject1, int paramInt1, int paramInt2, Icon paramIcon, Object[] paramArrayOfObject, Object paramObject2) {
/* 1837 */     this.message = paramObject1;
/* 1838 */     this.options = paramArrayOfObject;
/* 1839 */     this.initialValue = paramObject2;
/* 1840 */     this.icon = paramIcon;
/* 1841 */     setMessageType(paramInt1);
/* 1842 */     setOptionType(paramInt2);
/* 1843 */     this.value = UNINITIALIZED_VALUE;
/* 1844 */     this.inputValue = UNINITIALIZED_VALUE;
/* 1845 */     updateUI();
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
/*      */   public void setUI(OptionPaneUI paramOptionPaneUI) {
/* 1859 */     if (this.ui != paramOptionPaneUI) {
/* 1860 */       setUI(paramOptionPaneUI);
/* 1861 */       invalidate();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OptionPaneUI getUI() {
/* 1871 */     return (OptionPaneUI)this.ui;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateUI() {
/* 1882 */     setUI((OptionPaneUI)UIManager.getUI(this));
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
/*      */   public String getUIClassID() {
/* 1895 */     return "OptionPaneUI";
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
/*      */   public void setMessage(Object paramObject) {
/* 1910 */     Object object = this.message;
/*      */     
/* 1912 */     this.message = paramObject;
/* 1913 */     firePropertyChange("message", object, this.message);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getMessage() {
/* 1923 */     return this.message;
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
/*      */   public void setIcon(Icon paramIcon) {
/* 1938 */     Icon icon = this.icon;
/*      */     
/* 1940 */     this.icon = paramIcon;
/* 1941 */     firePropertyChange("icon", icon, this.icon);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Icon getIcon() {
/* 1951 */     return this.icon;
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
/*      */   public void setValue(Object paramObject) {
/* 1965 */     Object object = this.value;
/*      */     
/* 1967 */     this.value = paramObject;
/* 1968 */     firePropertyChange("value", object, this.value);
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
/*      */   public Object getValue() {
/* 1986 */     return this.value;
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
/*      */   public void setOptions(Object[] paramArrayOfObject) {
/* 2005 */     Object[] arrayOfObject = this.options;
/*      */     
/* 2007 */     this.options = paramArrayOfObject;
/* 2008 */     firePropertyChange("options", arrayOfObject, this.options);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object[] getOptions() {
/* 2018 */     if (this.options != null) {
/* 2019 */       int i = this.options.length;
/* 2020 */       Object[] arrayOfObject = new Object[i];
/*      */       
/* 2022 */       System.arraycopy(this.options, 0, arrayOfObject, 0, i);
/* 2023 */       return arrayOfObject;
/*      */     } 
/* 2025 */     return this.options;
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
/*      */   public void setInitialValue(Object paramObject) {
/* 2043 */     Object object = this.initialValue;
/*      */     
/* 2045 */     this.initialValue = paramObject;
/* 2046 */     firePropertyChange("initialValue", object, this.initialValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getInitialValue() {
/* 2057 */     return this.initialValue;
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
/*      */   public void setMessageType(int paramInt) {
/* 2079 */     if (paramInt != 0 && paramInt != 1 && paramInt != 2 && paramInt != 3 && paramInt != -1)
/*      */     {
/*      */       
/* 2082 */       throw new RuntimeException("JOptionPane: type must be one of JOptionPane.ERROR_MESSAGE, JOptionPane.INFORMATION_MESSAGE, JOptionPane.WARNING_MESSAGE, JOptionPane.QUESTION_MESSAGE or JOptionPane.PLAIN_MESSAGE");
/*      */     }
/* 2084 */     int i = this.messageType;
/*      */     
/* 2086 */     this.messageType = paramInt;
/* 2087 */     firePropertyChange("messageType", i, this.messageType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMessageType() {
/* 2098 */     return this.messageType;
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
/*      */   public void setOptionType(int paramInt) {
/* 2121 */     if (paramInt != -1 && paramInt != 0 && paramInt != 1 && paramInt != 2)
/*      */     {
/* 2123 */       throw new RuntimeException("JOptionPane: option type must be one of JOptionPane.DEFAULT_OPTION, JOptionPane.YES_NO_OPTION, JOptionPane.YES_NO_CANCEL_OPTION or JOptionPane.OK_CANCEL_OPTION");
/*      */     }
/* 2125 */     int i = this.optionType;
/*      */     
/* 2127 */     this.optionType = paramInt;
/* 2128 */     firePropertyChange("optionType", i, this.optionType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getOptionType() {
/* 2139 */     return this.optionType;
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
/*      */   public void setSelectionValues(Object[] paramArrayOfObject) {
/* 2165 */     Object[] arrayOfObject = this.selectionValues;
/*      */     
/* 2167 */     this.selectionValues = paramArrayOfObject;
/* 2168 */     firePropertyChange("selectionValues", arrayOfObject, paramArrayOfObject);
/* 2169 */     if (this.selectionValues != null) {
/* 2170 */       setWantsInput(true);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object[] getSelectionValues() {
/* 2180 */     return this.selectionValues;
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
/*      */   public void setInitialSelectionValue(Object paramObject) {
/* 2194 */     Object object = this.initialSelectionValue;
/*      */     
/* 2196 */     this.initialSelectionValue = paramObject;
/* 2197 */     firePropertyChange("initialSelectionValue", object, paramObject);
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
/*      */   public Object getInitialSelectionValue() {
/* 2209 */     return this.initialSelectionValue;
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
/*      */   public void setInputValue(Object paramObject) {
/* 2232 */     Object object = this.inputValue;
/*      */     
/* 2234 */     this.inputValue = paramObject;
/* 2235 */     firePropertyChange("inputValue", object, paramObject);
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
/*      */   public Object getInputValue() {
/* 2251 */     return this.inputValue;
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
/*      */   public int getMaxCharactersPerLineCount() {
/* 2263 */     return Integer.MAX_VALUE;
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
/*      */   public void setWantsInput(boolean paramBoolean) {
/* 2286 */     boolean bool = this.wantsInput;
/*      */     
/* 2288 */     this.wantsInput = paramBoolean;
/* 2289 */     firePropertyChange("wantsInput", bool, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getWantsInput() {
/* 2299 */     return this.wantsInput;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void selectInitialValue() {
/* 2309 */     OptionPaneUI optionPaneUI = getUI();
/* 2310 */     if (optionPaneUI != null) {
/* 2311 */       optionPaneUI.selectInitialValue(this);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static int styleFromMessageType(int paramInt) {
/* 2317 */     switch (paramInt) {
/*      */       case 0:
/* 2319 */         return 4;
/*      */       case 3:
/* 2321 */         return 7;
/*      */       case 2:
/* 2323 */         return 8;
/*      */       case 1:
/* 2325 */         return 3;
/*      */     } 
/*      */     
/* 2328 */     return 2;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 2334 */     Vector<String> vector = new Vector();
/*      */     
/* 2336 */     paramObjectOutputStream.defaultWriteObject();
/*      */     
/* 2338 */     if (this.icon != null && this.icon instanceof java.io.Serializable) {
/* 2339 */       vector.addElement("icon");
/* 2340 */       vector.addElement(this.icon);
/*      */     } 
/*      */     
/* 2343 */     if (this.message != null && this.message instanceof java.io.Serializable) {
/* 2344 */       vector.addElement("message");
/* 2345 */       vector.addElement(this.message);
/*      */     } 
/*      */     
/* 2348 */     if (this.options != null) {
/* 2349 */       Vector<Object> vector1 = new Vector();
/*      */       
/* 2351 */       int i = 0, j = this.options.length;
/* 2352 */       for (; i < j; i++) {
/* 2353 */         if (this.options[i] instanceof java.io.Serializable)
/* 2354 */           vector1.addElement(this.options[i]); 
/* 2355 */       }  if (vector1.size() > 0) {
/* 2356 */         i = vector1.size();
/* 2357 */         Object[] arrayOfObject = new Object[i];
/*      */         
/* 2359 */         vector1.copyInto(arrayOfObject);
/* 2360 */         vector.addElement("options");
/* 2361 */         vector.addElement(arrayOfObject);
/*      */       } 
/*      */     } 
/*      */     
/* 2365 */     if (this.initialValue != null && this.initialValue instanceof java.io.Serializable) {
/* 2366 */       vector.addElement("initialValue");
/* 2367 */       vector.addElement(this.initialValue);
/*      */     } 
/*      */     
/* 2370 */     if (this.value != null && this.value instanceof java.io.Serializable) {
/* 2371 */       vector.addElement("value");
/* 2372 */       vector.addElement(this.value);
/*      */     } 
/*      */     
/* 2375 */     if (this.selectionValues != null) {
/* 2376 */       boolean bool = true;
/*      */       
/* 2378 */       byte b = 0; int i = this.selectionValues.length;
/* 2379 */       for (; b < i; b++) {
/* 2380 */         if (this.selectionValues[b] != null && !(this.selectionValues[b] instanceof java.io.Serializable)) {
/*      */           
/* 2382 */           bool = false;
/*      */           break;
/*      */         } 
/*      */       } 
/* 2386 */       if (bool) {
/* 2387 */         vector.addElement("selectionValues");
/* 2388 */         vector.addElement(this.selectionValues);
/*      */       } 
/*      */     } 
/*      */     
/* 2392 */     if (this.inputValue != null && this.inputValue instanceof java.io.Serializable) {
/* 2393 */       vector.addElement("inputValue");
/* 2394 */       vector.addElement(this.inputValue);
/*      */     } 
/*      */     
/* 2397 */     if (this.initialSelectionValue != null && this.initialSelectionValue instanceof java.io.Serializable) {
/*      */       
/* 2399 */       vector.addElement("initialSelectionValue");
/* 2400 */       vector.addElement(this.initialSelectionValue);
/*      */     } 
/* 2402 */     paramObjectOutputStream.writeObject(vector);
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 2407 */     paramObjectInputStream.defaultReadObject();
/*      */     
/* 2409 */     Vector<E> vector = (Vector)paramObjectInputStream.readObject();
/* 2410 */     byte b = 0;
/* 2411 */     int i = vector.size();
/*      */     
/* 2413 */     if (b < i && vector.elementAt(b)
/* 2414 */       .equals("icon")) {
/* 2415 */       this.icon = (Icon)vector.elementAt(++b);
/* 2416 */       b++;
/*      */     } 
/* 2418 */     if (b < i && vector.elementAt(b)
/* 2419 */       .equals("message")) {
/* 2420 */       this.message = vector.elementAt(++b);
/* 2421 */       b++;
/*      */     } 
/* 2423 */     if (b < i && vector.elementAt(b)
/* 2424 */       .equals("options")) {
/* 2425 */       this.options = (Object[])vector.elementAt(++b);
/* 2426 */       b++;
/*      */     } 
/* 2428 */     if (b < i && vector.elementAt(b)
/* 2429 */       .equals("initialValue")) {
/* 2430 */       this.initialValue = vector.elementAt(++b);
/* 2431 */       b++;
/*      */     } 
/* 2433 */     if (b < i && vector.elementAt(b)
/* 2434 */       .equals("value")) {
/* 2435 */       this.value = vector.elementAt(++b);
/* 2436 */       b++;
/*      */     } 
/* 2438 */     if (b < i && vector.elementAt(b)
/* 2439 */       .equals("selectionValues")) {
/* 2440 */       this.selectionValues = (Object[])vector.elementAt(++b);
/* 2441 */       b++;
/*      */     } 
/* 2443 */     if (b < i && vector.elementAt(b)
/* 2444 */       .equals("inputValue")) {
/* 2445 */       this.inputValue = vector.elementAt(++b);
/* 2446 */       b++;
/*      */     } 
/* 2448 */     if (b < i && vector.elementAt(b)
/* 2449 */       .equals("initialSelectionValue")) {
/* 2450 */       this.initialSelectionValue = vector.elementAt(++b);
/* 2451 */       b++;
/*      */     } 
/* 2453 */     if (getUIClassID().equals("OptionPaneUI")) {
/* 2454 */       byte b1 = JComponent.getWriteObjCounter(this);
/* 2455 */       b1 = (byte)(b1 - 1); JComponent.setWriteObjCounter(this, b1);
/* 2456 */       if (b1 == 0 && this.ui != null) {
/* 2457 */         this.ui.installUI(this);
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
/*      */   protected String paramString() {
/* 2475 */     String str4, str5, str1 = (this.icon != null) ? this.icon.toString() : "";
/*      */     
/* 2477 */     String str2 = (this.initialValue != null) ? this.initialValue.toString() : "";
/*      */     
/* 2479 */     String str3 = (this.message != null) ? this.message.toString() : "";
/*      */     
/* 2481 */     if (this.messageType == 0)
/* 2482 */     { str4 = "ERROR_MESSAGE"; }
/* 2483 */     else if (this.messageType == 1)
/* 2484 */     { str4 = "INFORMATION_MESSAGE"; }
/* 2485 */     else if (this.messageType == 2)
/* 2486 */     { str4 = "WARNING_MESSAGE"; }
/* 2487 */     else if (this.messageType == 3)
/* 2488 */     { str4 = "QUESTION_MESSAGE"; }
/* 2489 */     else if (this.messageType == -1)
/* 2490 */     { str4 = "PLAIN_MESSAGE"; }
/* 2491 */     else { str4 = ""; }
/*      */     
/* 2493 */     if (this.optionType == -1)
/* 2494 */     { str5 = "DEFAULT_OPTION"; }
/* 2495 */     else if (this.optionType == 0)
/* 2496 */     { str5 = "YES_NO_OPTION"; }
/* 2497 */     else if (this.optionType == 1)
/* 2498 */     { str5 = "YES_NO_CANCEL_OPTION"; }
/* 2499 */     else if (this.optionType == 2)
/* 2500 */     { str5 = "OK_CANCEL_OPTION"; }
/* 2501 */     else { str5 = ""; }
/* 2502 */      String str6 = this.wantsInput ? "true" : "false";
/*      */ 
/*      */     
/* 2505 */     return super.paramString() + ",icon=" + str1 + ",initialValue=" + str2 + ",message=" + str3 + ",messageType=" + str4 + ",optionType=" + str5 + ",wantsInput=" + str6;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class ModalPrivilegedAction
/*      */     implements PrivilegedAction<Method>
/*      */   {
/*      */     private Class<?> clazz;
/*      */ 
/*      */     
/*      */     private String methodName;
/*      */ 
/*      */ 
/*      */     
/*      */     public ModalPrivilegedAction(Class<?> param1Class, String param1String) {
/* 2522 */       this.clazz = param1Class;
/* 2523 */       this.methodName = param1String;
/*      */     }
/*      */     
/*      */     public Method run() {
/* 2527 */       Method method = null;
/*      */       try {
/* 2529 */         method = this.clazz.getDeclaredMethod(this.methodName, (Class[])null);
/* 2530 */       } catch (NoSuchMethodException noSuchMethodException) {}
/*      */       
/* 2532 */       if (method != null) {
/* 2533 */         method.setAccessible(true);
/*      */       }
/* 2535 */       return method;
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
/*      */   public AccessibleContext getAccessibleContext() {
/* 2558 */     if (this.accessibleContext == null) {
/* 2559 */       this.accessibleContext = new AccessibleJOptionPane();
/*      */     }
/* 2561 */     return this.accessibleContext;
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
/*      */   protected class AccessibleJOptionPane
/*      */     extends JComponent.AccessibleJComponent
/*      */   {
/*      */     public AccessibleRole getAccessibleRole() {
/* 2588 */       switch (JOptionPane.this.messageType) {
/*      */         case 0:
/*      */         case 1:
/*      */         case 2:
/* 2592 */           return AccessibleRole.ALERT;
/*      */       } 
/*      */       
/* 2595 */       return AccessibleRole.OPTION_PANE;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JOptionPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */