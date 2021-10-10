/*      */ package javax.swing;
/*      */ 
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dialog;
/*      */ import java.awt.Frame;
/*      */ import java.awt.IllegalComponentStateException;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Window;
/*      */ import java.awt.event.WindowAdapter;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.awt.event.WindowListener;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.util.Locale;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleComponent;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.accessibility.AccessibleStateSet;
/*      */ import javax.accessibility.AccessibleText;
/*      */ import javax.accessibility.AccessibleValue;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ChangeListener;
/*      */ import javax.swing.text.AttributeSet;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ProgressMonitor
/*      */   implements Accessible
/*      */ {
/*      */   private ProgressMonitor root;
/*      */   private JDialog dialog;
/*      */   private JOptionPane pane;
/*      */   private JProgressBar myBar;
/*      */   private JLabel noteLabel;
/*      */   private Component parentComponent;
/*      */   private String note;
/*   90 */   private Object[] cancelOption = null;
/*      */   private Object message;
/*      */   private long T0;
/*   93 */   private int millisToDecideToPopup = 500;
/*   94 */   private int millisToPopup = 2000;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int min;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int max;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AccessibleContext accessibleContext;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AccessibleContext accessibleJOptionPane;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ProgressMonitor(Component paramComponent, Object paramObject, String paramString, int paramInt1, int paramInt2) {
/*  127 */     this(paramComponent, paramObject, paramString, paramInt1, paramInt2, null);
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
/*      */   private class ProgressOptionPane
/*      */     extends JOptionPane
/*      */   {
/*      */     ProgressOptionPane(Object param1Object) {
/*  160 */       super(param1Object, 1, -1, (Icon)null, ProgressMonitor.this
/*      */ 
/*      */ 
/*      */           
/*  164 */           .cancelOption, (Object)null);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int getMaxCharactersPerLineCount() {
/*  170 */       return 60;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public JDialog createDialog(Component param1Component, String param1String) {
/*      */       final JDialog dialog;
/*  181 */       Window window = JOptionPane.getWindowForComponent(param1Component);
/*  182 */       if (window instanceof Frame) {
/*  183 */         jDialog = new JDialog((Frame)window, param1String, false);
/*      */       } else {
/*  185 */         jDialog = new JDialog((Dialog)window, param1String, false);
/*      */       } 
/*  187 */       if (window instanceof SwingUtilities.SharedOwnerFrame) {
/*      */         
/*  189 */         WindowListener windowListener = SwingUtilities.getSharedOwnerFrameShutdownListener();
/*  190 */         jDialog.addWindowListener(windowListener);
/*      */       } 
/*  192 */       Container container = jDialog.getContentPane();
/*      */       
/*  194 */       container.setLayout(new BorderLayout());
/*  195 */       container.add(this, "Center");
/*  196 */       jDialog.pack();
/*  197 */       jDialog.setLocationRelativeTo(param1Component);
/*  198 */       jDialog.addWindowListener(new WindowAdapter() {
/*      */             boolean gotFocus = false;
/*      */             
/*      */             public void windowClosing(WindowEvent param2WindowEvent) {
/*  202 */               ProgressMonitor.ProgressOptionPane.this.setValue(ProgressMonitor.this.cancelOption[0]);
/*      */             }
/*      */ 
/*      */             
/*      */             public void windowActivated(WindowEvent param2WindowEvent) {
/*  207 */               if (!this.gotFocus) {
/*  208 */                 ProgressMonitor.ProgressOptionPane.this.selectInitialValue();
/*  209 */                 this.gotFocus = true;
/*      */               } 
/*      */             }
/*      */           });
/*      */       
/*  214 */       addPropertyChangeListener(new PropertyChangeListener() {
/*      */             public void propertyChange(PropertyChangeEvent param2PropertyChangeEvent) {
/*  216 */               if (dialog.isVisible() && param2PropertyChangeEvent
/*  217 */                 .getSource() == ProgressMonitor.ProgressOptionPane.this && (param2PropertyChangeEvent
/*  218 */                 .getPropertyName().equals("value") || param2PropertyChangeEvent
/*  219 */                 .getPropertyName().equals("inputValue"))) {
/*  220 */                 dialog.setVisible(false);
/*  221 */                 dialog.dispose();
/*      */               } 
/*      */             }
/*      */           });
/*      */       
/*  226 */       return jDialog;
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
/*      */     public AccessibleContext getAccessibleContext() {
/*  240 */       return ProgressMonitor.this.getAccessibleContext();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private AccessibleContext getAccessibleJOptionPane() {
/*  247 */       return super.getAccessibleContext();
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
/*      */   public void setProgress(int paramInt) {
/*  263 */     if (paramInt >= this.max) {
/*  264 */       close();
/*      */     
/*      */     }
/*  267 */     else if (this.myBar != null) {
/*  268 */       this.myBar.setValue(paramInt);
/*      */     } else {
/*      */       
/*  271 */       long l1 = System.currentTimeMillis();
/*  272 */       long l2 = (int)(l1 - this.T0);
/*  273 */       if (l2 >= this.millisToDecideToPopup) {
/*      */         int i;
/*  275 */         if (paramInt > this.min) {
/*  276 */           i = (int)(l2 * (this.max - this.min) / (paramInt - this.min));
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  281 */           i = this.millisToPopup;
/*      */         } 
/*  283 */         if (i >= this.millisToPopup) {
/*  284 */           this.myBar = new JProgressBar();
/*  285 */           this.myBar.setMinimum(this.min);
/*  286 */           this.myBar.setMaximum(this.max);
/*  287 */           this.myBar.setValue(paramInt);
/*  288 */           if (this.note != null) this.noteLabel = new JLabel(this.note); 
/*  289 */           this.pane = new ProgressOptionPane(new Object[] { this.message, this.noteLabel, this.myBar });
/*      */ 
/*      */           
/*  292 */           this.dialog = this.pane.createDialog(this.parentComponent, 
/*  293 */               UIManager.getString("ProgressMonitor.progressText"));
/*      */           
/*  295 */           this.dialog.show();
/*      */         } 
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
/*      */   public void close() {
/*  309 */     if (this.dialog != null) {
/*  310 */       this.dialog.setVisible(false);
/*  311 */       this.dialog.dispose();
/*  312 */       this.dialog = null;
/*  313 */       this.pane = null;
/*  314 */       this.myBar = null;
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
/*      */   public int getMinimum() {
/*  326 */     return this.min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMinimum(int paramInt) {
/*  337 */     if (this.myBar != null) {
/*  338 */       this.myBar.setMinimum(paramInt);
/*      */     }
/*  340 */     this.min = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaximum() {
/*  351 */     return this.max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaximum(int paramInt) {
/*  362 */     if (this.myBar != null) {
/*  363 */       this.myBar.setMaximum(paramInt);
/*      */     }
/*  365 */     this.max = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCanceled() {
/*  373 */     if (this.pane == null) return false; 
/*  374 */     Object object = this.pane.getValue();
/*  375 */     return (object != null && this.cancelOption.length == 1 && object
/*      */       
/*  377 */       .equals(this.cancelOption[0]));
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
/*      */   public void setMillisToDecideToPopup(int paramInt) {
/*  390 */     this.millisToDecideToPopup = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMillisToDecideToPopup() {
/*  401 */     return this.millisToDecideToPopup;
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
/*      */   public void setMillisToPopup(int paramInt) {
/*  414 */     this.millisToPopup = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMillisToPopup() {
/*  424 */     return this.millisToPopup;
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
/*      */   public void setNote(String paramString) {
/*  437 */     this.note = paramString;
/*  438 */     if (this.noteLabel != null) {
/*  439 */       this.noteLabel.setText(paramString);
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
/*      */   public String getNote() {
/*  452 */     return this.note;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ProgressMonitor(Component paramComponent, Object paramObject, String paramString, int paramInt1, int paramInt2, ProgressMonitor paramProgressMonitor) {
/*  463 */     this.accessibleContext = null;
/*      */     
/*  465 */     this.accessibleJOptionPane = null; this.min = paramInt1; this.max = paramInt2; this.parentComponent = paramComponent;
/*      */     this.cancelOption = new Object[1];
/*      */     this.cancelOption[0] = UIManager.getString("OptionPane.cancelButtonText");
/*      */     this.message = paramObject;
/*      */     this.note = paramString;
/*      */     if (paramProgressMonitor != null) {
/*      */       this.root = (paramProgressMonitor.root != null) ? paramProgressMonitor.root : paramProgressMonitor;
/*      */       this.T0 = this.root.T0;
/*      */       this.dialog = this.root.dialog;
/*      */     } else {
/*      */       this.T0 = System.currentTimeMillis();
/*  476 */     }  } public AccessibleContext getAccessibleContext() { if (this.accessibleContext == null) {
/*  477 */       this.accessibleContext = new AccessibleProgressMonitor();
/*      */     }
/*  479 */     if (this.pane != null && this.accessibleJOptionPane == null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  486 */       if (this.accessibleContext instanceof AccessibleProgressMonitor) {
/*  487 */         ((AccessibleProgressMonitor)this.accessibleContext).optionPaneCreated();
/*      */       }
/*      */     }
/*  490 */     return this.accessibleContext; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class AccessibleProgressMonitor
/*      */     extends AccessibleContext
/*      */     implements AccessibleText, ChangeListener, PropertyChangeListener
/*      */   {
/*      */     private Object oldModelValue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void optionPaneCreated() {
/*  548 */       ProgressMonitor.this.accessibleJOptionPane = (
/*  549 */         (ProgressMonitor.ProgressOptionPane)ProgressMonitor.this.pane).getAccessibleJOptionPane();
/*      */ 
/*      */       
/*  552 */       if (ProgressMonitor.this.myBar != null) {
/*  553 */         ProgressMonitor.this.myBar.addChangeListener(this);
/*      */       }
/*      */ 
/*      */       
/*  557 */       if (ProgressMonitor.this.noteLabel != null) {
/*  558 */         ProgressMonitor.this.noteLabel.addPropertyChangeListener(this);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/*  569 */       if (param1ChangeEvent == null) {
/*      */         return;
/*      */       }
/*  572 */       if (ProgressMonitor.this.myBar != null) {
/*      */         
/*  574 */         Integer integer = Integer.valueOf(ProgressMonitor.this.myBar.getValue());
/*  575 */         firePropertyChange("AccessibleValue", this.oldModelValue, integer);
/*      */ 
/*      */         
/*  578 */         this.oldModelValue = integer;
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
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/*  590 */       if (param1PropertyChangeEvent.getSource() == ProgressMonitor.this.noteLabel && param1PropertyChangeEvent.getPropertyName() == "text")
/*      */       {
/*  592 */         firePropertyChange("AccessibleText", null, Integer.valueOf(0));
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
/*      */     
/*      */     public String getAccessibleName() {
/*  614 */       if (this.accessibleName != null)
/*  615 */         return this.accessibleName; 
/*  616 */       if (ProgressMonitor.this.accessibleJOptionPane != null)
/*      */       {
/*  618 */         return ProgressMonitor.this.accessibleJOptionPane.getAccessibleName();
/*      */       }
/*  620 */       return null;
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
/*      */     public String getAccessibleDescription() {
/*  636 */       if (this.accessibleDescription != null)
/*  637 */         return this.accessibleDescription; 
/*  638 */       if (ProgressMonitor.this.accessibleJOptionPane != null)
/*      */       {
/*  640 */         return ProgressMonitor.this.accessibleJOptionPane.getAccessibleDescription();
/*      */       }
/*  642 */       return null;
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
/*      */     public AccessibleRole getAccessibleRole() {
/*  664 */       return AccessibleRole.PROGRESS_MONITOR;
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
/*      */     public AccessibleStateSet getAccessibleStateSet() {
/*  680 */       if (ProgressMonitor.this.accessibleJOptionPane != null)
/*      */       {
/*  682 */         return ProgressMonitor.this.accessibleJOptionPane.getAccessibleStateSet();
/*      */       }
/*  684 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Accessible getAccessibleParent() {
/*  694 */       return ProgressMonitor.this.dialog;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private AccessibleContext getParentAccessibleContext() {
/*  701 */       if (ProgressMonitor.this.dialog != null) {
/*  702 */         return ProgressMonitor.this.dialog.getAccessibleContext();
/*      */       }
/*  704 */       return null;
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
/*      */     public int getAccessibleIndexInParent() {
/*  718 */       if (ProgressMonitor.this.accessibleJOptionPane != null)
/*      */       {
/*  720 */         return ProgressMonitor.this.accessibleJOptionPane.getAccessibleIndexInParent();
/*      */       }
/*  722 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getAccessibleChildrenCount() {
/*  733 */       AccessibleContext accessibleContext = getPanelAccessibleContext();
/*  734 */       if (accessibleContext != null) {
/*  735 */         return accessibleContext.getAccessibleChildrenCount();
/*      */       }
/*  737 */       return 0;
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
/*      */     public Accessible getAccessibleChild(int param1Int) {
/*  753 */       AccessibleContext accessibleContext = getPanelAccessibleContext();
/*  754 */       if (accessibleContext != null) {
/*  755 */         return accessibleContext.getAccessibleChild(param1Int);
/*      */       }
/*  757 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private AccessibleContext getPanelAccessibleContext() {
/*  765 */       if (ProgressMonitor.this.myBar != null) {
/*  766 */         Container container = ProgressMonitor.this.myBar.getParent();
/*  767 */         if (container instanceof Accessible) {
/*  768 */           return container.getAccessibleContext();
/*      */         }
/*      */       } 
/*  771 */       return null;
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
/*      */     public Locale getLocale() throws IllegalComponentStateException {
/*  787 */       if (ProgressMonitor.this.accessibleJOptionPane != null)
/*      */       {
/*  789 */         return ProgressMonitor.this.accessibleJOptionPane.getLocale();
/*      */       }
/*  791 */       return null;
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
/*      */     public AccessibleComponent getAccessibleComponent() {
/*  804 */       if (ProgressMonitor.this.accessibleJOptionPane != null)
/*      */       {
/*  806 */         return ProgressMonitor.this.accessibleJOptionPane.getAccessibleComponent();
/*      */       }
/*  808 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleValue getAccessibleValue() {
/*  819 */       if (ProgressMonitor.this.myBar != null)
/*      */       {
/*  821 */         return ProgressMonitor.this.myBar.getAccessibleContext().getAccessibleValue();
/*      */       }
/*  823 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleText getAccessibleText() {
/*  834 */       if (getNoteLabelAccessibleText() != null) {
/*  835 */         return this;
/*      */       }
/*  837 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private AccessibleText getNoteLabelAccessibleText() {
/*  844 */       if (ProgressMonitor.this.noteLabel != null)
/*      */       {
/*      */         
/*  847 */         return ProgressMonitor.this.noteLabel.getAccessibleContext().getAccessibleText();
/*      */       }
/*  849 */       return null;
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
/*      */     public int getIndexAtPoint(Point param1Point) {
/*  864 */       AccessibleText accessibleText = getNoteLabelAccessibleText();
/*  865 */       if (accessibleText != null && sameWindowAncestor(ProgressMonitor.this.pane, ProgressMonitor.this.noteLabel)) {
/*      */ 
/*      */         
/*  868 */         Point point = SwingUtilities.convertPoint(ProgressMonitor.this.pane, param1Point, ProgressMonitor.this
/*      */             
/*  870 */             .noteLabel);
/*  871 */         if (point != null) {
/*  872 */           return accessibleText.getIndexAtPoint(point);
/*      */         }
/*      */       } 
/*  875 */       return -1;
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
/*      */     public Rectangle getCharacterBounds(int param1Int) {
/*  888 */       AccessibleText accessibleText = getNoteLabelAccessibleText();
/*  889 */       if (accessibleText != null && sameWindowAncestor(ProgressMonitor.this.pane, ProgressMonitor.this.noteLabel)) {
/*      */         
/*  891 */         Rectangle rectangle = accessibleText.getCharacterBounds(param1Int);
/*  892 */         if (rectangle != null) {
/*  893 */           return SwingUtilities.convertRectangle(ProgressMonitor.this.noteLabel, rectangle, ProgressMonitor.this
/*      */               
/*  895 */               .pane);
/*      */         }
/*      */       } 
/*  898 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean sameWindowAncestor(Component param1Component1, Component param1Component2) {
/*  906 */       if (param1Component1 == null || param1Component2 == null) {
/*  907 */         return false;
/*      */       }
/*  909 */       return 
/*  910 */         (SwingUtilities.getWindowAncestor(param1Component1) == SwingUtilities.getWindowAncestor(param1Component2));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getCharCount() {
/*  919 */       AccessibleText accessibleText = getNoteLabelAccessibleText();
/*  920 */       if (accessibleText != null) {
/*  921 */         return accessibleText.getCharCount();
/*      */       }
/*  923 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getCaretPosition() {
/*  934 */       AccessibleText accessibleText = getNoteLabelAccessibleText();
/*  935 */       if (accessibleText != null) {
/*  936 */         return accessibleText.getCaretPosition();
/*      */       }
/*  938 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getAtIndex(int param1Int1, int param1Int2) {
/*  949 */       AccessibleText accessibleText = getNoteLabelAccessibleText();
/*  950 */       if (accessibleText != null) {
/*  951 */         return accessibleText.getAtIndex(param1Int1, param1Int2);
/*      */       }
/*  953 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getAfterIndex(int param1Int1, int param1Int2) {
/*  964 */       AccessibleText accessibleText = getNoteLabelAccessibleText();
/*  965 */       if (accessibleText != null) {
/*  966 */         return accessibleText.getAfterIndex(param1Int1, param1Int2);
/*      */       }
/*  968 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getBeforeIndex(int param1Int1, int param1Int2) {
/*  979 */       AccessibleText accessibleText = getNoteLabelAccessibleText();
/*  980 */       if (accessibleText != null) {
/*  981 */         return accessibleText.getBeforeIndex(param1Int1, param1Int2);
/*      */       }
/*  983 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AttributeSet getCharacterAttribute(int param1Int) {
/*  993 */       AccessibleText accessibleText = getNoteLabelAccessibleText();
/*  994 */       if (accessibleText != null) {
/*  995 */         return accessibleText.getCharacterAttribute(param1Int);
/*      */       }
/*  997 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getSelectionStart() {
/* 1008 */       AccessibleText accessibleText = getNoteLabelAccessibleText();
/* 1009 */       if (accessibleText != null) {
/* 1010 */         return accessibleText.getSelectionStart();
/*      */       }
/* 1012 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getSelectionEnd() {
/* 1023 */       AccessibleText accessibleText = getNoteLabelAccessibleText();
/* 1024 */       if (accessibleText != null) {
/* 1025 */         return accessibleText.getSelectionEnd();
/*      */       }
/* 1027 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getSelectedText() {
/* 1036 */       AccessibleText accessibleText = getNoteLabelAccessibleText();
/* 1037 */       if (accessibleText != null) {
/* 1038 */         return accessibleText.getSelectedText();
/*      */       }
/* 1040 */       return null;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/ProgressMonitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */