/*     */ package sun.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.awt.print.PageFormat;
/*     */ import java.awt.print.Printable;
/*     */ import java.awt.print.PrinterException;
/*     */ import java.awt.print.PrinterJob;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrintingStatus
/*     */ {
/*     */   private final PrinterJob job;
/*     */   private final Component parent;
/*     */   private JDialog abortDialog;
/*     */   private JButton abortButton;
/*     */   private JLabel statusLabel;
/*     */   private MessageFormat statusFormat;
/*  62 */   private final AtomicBoolean isAborted = new AtomicBoolean(false);
/*     */ 
/*     */   
/*  65 */   private final Action abortAction = new AbstractAction() {
/*     */       public void actionPerformed(ActionEvent param1ActionEvent) {
/*  67 */         if (!PrintingStatus.this.isAborted.get()) {
/*  68 */           PrintingStatus.this.isAborted.set(true);
/*     */ 
/*     */           
/*  71 */           PrintingStatus.this.abortButton.setEnabled(false);
/*  72 */           PrintingStatus.this.abortDialog.setTitle(
/*  73 */               UIManager.getString("PrintingDialog.titleAbortingText"));
/*  74 */           PrintingStatus.this.statusLabel.setText(
/*  75 */               UIManager.getString("PrintingDialog.contentAbortingText"));
/*     */ 
/*     */           
/*  78 */           PrintingStatus.this.job.cancel();
/*     */         } 
/*     */       }
/*     */     };
/*     */   
/*  83 */   private final WindowAdapter closeListener = new WindowAdapter() {
/*     */       public void windowClosing(WindowEvent param1WindowEvent) {
/*  85 */         PrintingStatus.this.abortAction.actionPerformed(null);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrintingStatus createPrintingStatus(Component paramComponent, PrinterJob paramPrinterJob) {
/* 100 */     return new PrintingStatus(paramComponent, paramPrinterJob);
/*     */   }
/*     */   
/*     */   protected PrintingStatus(Component paramComponent, PrinterJob paramPrinterJob) {
/* 104 */     this.job = paramPrinterJob;
/* 105 */     this.parent = paramComponent;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void init() {
/* 111 */     String str1 = UIManager.getString("PrintingDialog.titleProgressText");
/*     */ 
/*     */     
/* 114 */     String str2 = UIManager.getString("PrintingDialog.contentInitialText");
/*     */ 
/*     */ 
/*     */     
/* 118 */     this
/* 119 */       .statusFormat = new MessageFormat(UIManager.getString("PrintingDialog.contentProgressText"));
/*     */ 
/*     */     
/* 122 */     String str3 = UIManager.getString("PrintingDialog.abortButtonText");
/*     */     
/* 124 */     String str4 = UIManager.getString("PrintingDialog.abortButtonToolTipText");
/*     */     
/* 126 */     int i = getInt("PrintingDialog.abortButtonMnemonic", -1);
/*     */     
/* 128 */     int j = getInt("PrintingDialog.abortButtonDisplayedMnemonicIndex", -1);
/*     */     
/* 130 */     this.abortButton = new JButton(str3);
/* 131 */     this.abortButton.addActionListener(this.abortAction);
/*     */     
/* 133 */     this.abortButton.setToolTipText(str4);
/* 134 */     if (i != -1) {
/* 135 */       this.abortButton.setMnemonic(i);
/*     */     }
/* 137 */     if (j != -1) {
/* 138 */       this.abortButton.setDisplayedMnemonicIndex(j);
/*     */     }
/* 140 */     this.statusLabel = new JLabel(str2);
/* 141 */     JOptionPane jOptionPane = new JOptionPane(this.statusLabel, 1, -1, null, new Object[] { this.abortButton }, this.abortButton);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 146 */     jOptionPane.getActionMap().put("close", this.abortAction);
/*     */ 
/*     */     
/* 149 */     if (this.parent != null && this.parent.getParent() instanceof javax.swing.JViewport) {
/* 150 */       this
/* 151 */         .abortDialog = jOptionPane.createDialog(this.parent.getParent(), str1);
/*     */     } else {
/* 153 */       this.abortDialog = jOptionPane.createDialog(this.parent, str1);
/*     */     } 
/*     */     
/* 156 */     this.abortDialog.setDefaultCloseOperation(0);
/* 157 */     this.abortDialog.addWindowListener(this.closeListener);
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
/*     */   public void showModal(final boolean isModal) {
/* 170 */     if (SwingUtilities.isEventDispatchThread()) {
/* 171 */       showModalOnEDT(isModal);
/*     */     } else {
/*     */       try {
/* 174 */         SwingUtilities.invokeAndWait(new Runnable() {
/*     */               public void run() {
/* 176 */                 PrintingStatus.this.showModalOnEDT(isModal);
/*     */               }
/*     */             });
/* 179 */       } catch (InterruptedException interruptedException) {
/* 180 */         throw new RuntimeException(interruptedException);
/* 181 */       } catch (InvocationTargetException invocationTargetException) {
/* 182 */         Throwable throwable = invocationTargetException.getCause();
/* 183 */         if (throwable instanceof RuntimeException)
/* 184 */           throw (RuntimeException)throwable; 
/* 185 */         if (throwable instanceof Error) {
/* 186 */           throw (Error)throwable;
/*     */         }
/* 188 */         throw new RuntimeException(throwable);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void showModalOnEDT(boolean paramBoolean) {
/* 200 */     assert SwingUtilities.isEventDispatchThread();
/* 201 */     init();
/* 202 */     this.abortDialog.setModal(paramBoolean);
/* 203 */     this.abortDialog.setVisible(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 212 */     if (SwingUtilities.isEventDispatchThread()) {
/* 213 */       disposeOnEDT();
/*     */     } else {
/* 215 */       SwingUtilities.invokeLater(new Runnable() {
/*     */             public void run() {
/* 217 */               PrintingStatus.this.disposeOnEDT();
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void disposeOnEDT() {
/* 229 */     assert SwingUtilities.isEventDispatchThread();
/* 230 */     if (this.abortDialog != null) {
/* 231 */       this.abortDialog.removeWindowListener(this.closeListener);
/* 232 */       this.abortDialog.dispose();
/* 233 */       this.abortDialog = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAborted() {
/* 243 */     return this.isAborted.get();
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
/*     */   public Printable createNotificationPrintable(Printable paramPrintable) {
/* 256 */     return new NotificationPrintable(paramPrintable);
/*     */   }
/*     */   
/*     */   private class NotificationPrintable implements Printable {
/*     */     private final Printable printDelegatee;
/*     */     
/*     */     public NotificationPrintable(Printable param1Printable) {
/* 263 */       if (param1Printable == null) {
/* 264 */         throw new NullPointerException("Printable is null");
/*     */       }
/* 266 */       this.printDelegatee = param1Printable;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int print(Graphics param1Graphics, PageFormat param1PageFormat, final int pageIndex) throws PrinterException {
/* 274 */       int i = this.printDelegatee.print(param1Graphics, param1PageFormat, pageIndex);
/* 275 */       if (i != 1 && !PrintingStatus.this.isAborted()) {
/* 276 */         if (SwingUtilities.isEventDispatchThread()) {
/* 277 */           updateStatusOnEDT(pageIndex);
/*     */         } else {
/* 279 */           SwingUtilities.invokeLater(new Runnable() {
/*     */                 public void run() {
/* 281 */                   PrintingStatus.NotificationPrintable.this.updateStatusOnEDT(pageIndex);
/*     */                 }
/*     */               });
/*     */         } 
/*     */       }
/* 286 */       return i;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void updateStatusOnEDT(int param1Int) {
/* 295 */       assert SwingUtilities.isEventDispatchThread();
/* 296 */       Object[] arrayOfObject = { new Integer(param1Int + 1) };
/*     */       
/* 298 */       PrintingStatus.this.statusLabel.setText(PrintingStatus.this.statusFormat.format(arrayOfObject));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getInt(Object paramObject, int paramInt) {
/* 306 */     Object object = UIManager.get(paramObject);
/* 307 */     if (object instanceof Integer) {
/* 308 */       return ((Integer)object).intValue();
/*     */     }
/* 310 */     if (object instanceof String) {
/*     */       try {
/* 312 */         return Integer.parseInt((String)object);
/* 313 */       } catch (NumberFormatException numberFormatException) {}
/*     */     }
/*     */     
/* 316 */     return paramInt;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/swing/PrintingStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */