/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.Frame;
/*     */ import java.awt.HeadlessException;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.ComponentAdapter;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.io.Serializable;
/*     */ import java.util.Locale;
/*     */ import sun.swing.SwingUtilities2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ColorChooserDialog
/*     */   extends JDialog
/*     */ {
/*     */   private Color initialColor;
/*     */   private JColorChooser chooserPane;
/*     */   private JButton cancelButton;
/*     */   
/*     */   public ColorChooserDialog(Dialog paramDialog, String paramString, boolean paramBoolean, Component paramComponent, JColorChooser paramJColorChooser, ActionListener paramActionListener1, ActionListener paramActionListener2) throws HeadlessException {
/* 617 */     super(paramDialog, paramString, paramBoolean);
/* 618 */     initColorChooserDialog(paramComponent, paramJColorChooser, paramActionListener1, paramActionListener2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ColorChooserDialog(Frame paramFrame, String paramString, boolean paramBoolean, Component paramComponent, JColorChooser paramJColorChooser, ActionListener paramActionListener1, ActionListener paramActionListener2) throws HeadlessException {
/* 625 */     super(paramFrame, paramString, paramBoolean);
/* 626 */     initColorChooserDialog(paramComponent, paramJColorChooser, paramActionListener1, paramActionListener2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initColorChooserDialog(Component paramComponent, JColorChooser paramJColorChooser, ActionListener paramActionListener1, ActionListener paramActionListener2) {
/* 633 */     this.chooserPane = paramJColorChooser;
/*     */     
/* 635 */     Locale locale = getLocale();
/* 636 */     String str1 = UIManager.getString("ColorChooser.okText", locale);
/* 637 */     String str2 = UIManager.getString("ColorChooser.cancelText", locale);
/* 638 */     String str3 = UIManager.getString("ColorChooser.resetText", locale);
/*     */     
/* 640 */     Container container = getContentPane();
/* 641 */     container.setLayout(new BorderLayout());
/* 642 */     container.add(paramJColorChooser, "Center");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 647 */     JPanel jPanel = new JPanel();
/* 648 */     jPanel.setLayout(new FlowLayout(1));
/* 649 */     JButton jButton1 = new JButton(str1);
/* 650 */     getRootPane().setDefaultButton(jButton1);
/* 651 */     jButton1.getAccessibleContext().setAccessibleDescription(str1);
/* 652 */     jButton1.setActionCommand("OK");
/* 653 */     jButton1.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent param1ActionEvent) {
/* 655 */             ColorChooserDialog.this.hide();
/*     */           }
/*     */         });
/* 658 */     if (paramActionListener1 != null) {
/* 659 */       jButton1.addActionListener(paramActionListener1);
/*     */     }
/* 661 */     jPanel.add(jButton1);
/*     */     
/* 663 */     this.cancelButton = new JButton(str2);
/* 664 */     this.cancelButton.getAccessibleContext().setAccessibleDescription(str2);
/*     */ 
/*     */     
/* 667 */     AbstractAction abstractAction = new AbstractAction() {
/*     */         public void actionPerformed(ActionEvent param1ActionEvent) {
/* 669 */           ((AbstractButton)param1ActionEvent.getSource()).fireActionPerformed(param1ActionEvent);
/*     */         }
/*     */       };
/* 672 */     KeyStroke keyStroke = KeyStroke.getKeyStroke(27, 0);
/* 673 */     InputMap inputMap = this.cancelButton.getInputMap(2);
/*     */     
/* 675 */     ActionMap actionMap = this.cancelButton.getActionMap();
/* 676 */     if (inputMap != null && actionMap != null) {
/* 677 */       inputMap.put(keyStroke, "cancel");
/* 678 */       actionMap.put("cancel", abstractAction);
/*     */     } 
/*     */ 
/*     */     
/* 682 */     this.cancelButton.setActionCommand("cancel");
/* 683 */     this.cancelButton.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent param1ActionEvent) {
/* 685 */             ColorChooserDialog.this.hide();
/*     */           }
/*     */         });
/* 688 */     if (paramActionListener2 != null) {
/* 689 */       this.cancelButton.addActionListener(paramActionListener2);
/*     */     }
/* 691 */     jPanel.add(this.cancelButton);
/*     */     
/* 693 */     JButton jButton2 = new JButton(str3);
/* 694 */     jButton2.getAccessibleContext().setAccessibleDescription(str3);
/* 695 */     jButton2.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent param1ActionEvent) {
/* 697 */             ColorChooserDialog.this.reset();
/*     */           }
/*     */         });
/* 700 */     int i = SwingUtilities2.getUIDefaultsInt("ColorChooser.resetMnemonic", locale, -1);
/* 701 */     if (i != -1) {
/* 702 */       jButton2.setMnemonic(i);
/*     */     }
/* 704 */     jPanel.add(jButton2);
/* 705 */     container.add(jPanel, "South");
/*     */     
/* 707 */     if (JDialog.isDefaultLookAndFeelDecorated()) {
/*     */       
/* 709 */       boolean bool = UIManager.getLookAndFeel().getSupportsWindowDecorations();
/* 710 */       if (bool) {
/* 711 */         getRootPane().setWindowDecorationStyle(5);
/*     */       }
/*     */     } 
/* 714 */     applyComponentOrientation(((paramComponent == null) ? getRootPane() : paramComponent).getComponentOrientation());
/*     */     
/* 716 */     pack();
/* 717 */     setLocationRelativeTo(paramComponent);
/*     */     
/* 719 */     addWindowListener(new Closer());
/*     */   }
/*     */   
/*     */   public void show() {
/* 723 */     this.initialColor = this.chooserPane.getColor();
/* 724 */     super.show();
/*     */   }
/*     */   
/*     */   public void reset() {
/* 728 */     this.chooserPane.setColor(this.initialColor);
/*     */   }
/*     */   
/*     */   class Closer extends WindowAdapter implements Serializable {
/*     */     public void windowClosing(WindowEvent param1WindowEvent) {
/* 733 */       ColorChooserDialog.this.cancelButton.doClick(0);
/* 734 */       Window window = param1WindowEvent.getWindow();
/* 735 */       window.hide();
/*     */     }
/*     */   }
/*     */   
/*     */   static class DisposeOnClose extends ComponentAdapter implements Serializable {
/*     */     public void componentHidden(ComponentEvent param1ComponentEvent) {
/* 741 */       Window window = (Window)param1ComponentEvent.getComponent();
/* 742 */       window.dispose();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/ColorChooserDialog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */