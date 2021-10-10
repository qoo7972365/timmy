/*     */ package javax.swing;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Arrays;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import javax.accessibility.AccessibleText;
/*     */ import javax.accessibility.AccessibleTextSequence;
/*     */ import javax.swing.text.BadLocationException;
/*     */ import javax.swing.text.Document;
/*     */ import javax.swing.text.Segment;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JPasswordField
/*     */   extends JTextField
/*     */ {
/*     */   private static final String uiClassID = "PasswordFieldUI";
/*     */   private char echoChar;
/*     */   private boolean echoCharSet;
/*     */   
/*     */   public JPasswordField() {
/*  85 */     this((Document)null, (String)null, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JPasswordField(String paramString) {
/*  96 */     this((Document)null, paramString, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JPasswordField(int paramInt) {
/* 107 */     this((Document)null, (String)null, paramInt);
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
/*     */   public JPasswordField(String paramString, int paramInt) {
/* 119 */     this((Document)null, paramString, paramInt);
/*     */   }
/*     */   
/*     */   public String getUIClassID() {
/*     */     return "PasswordFieldUI";
/*     */   }
/*     */   
/*     */   public void updateUI() {
/*     */     if (!this.echoCharSet) {
/*     */       this.echoChar = '*';
/*     */     }
/*     */     super.updateUI();
/*     */   }
/*     */   
/*     */   public char getEchoChar() {
/*     */     return this.echoChar;
/*     */   }
/*     */   
/*     */   public JPasswordField(Document paramDocument, String paramString, int paramInt) {
/* 138 */     super(paramDocument, paramString, paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 331 */     this.echoCharSet = false; enableInputMethods(false);
/*     */   }
/*     */   public void setEchoChar(char paramChar) { this.echoChar = paramChar; this.echoCharSet = true; repaint();
/*     */     revalidate(); }
/*     */   public boolean echoCharIsSet() { return (this.echoChar != '\000'); }
/*     */   public void cut() { if (getClientProperty("JPasswordField.cutCopyAllowed") != Boolean.TRUE) {
/*     */       UIManager.getLookAndFeel().provideErrorFeedback(this);
/*     */     } else {
/*     */       super.cut();
/*     */     }  } public void copy() { if (getClientProperty("JPasswordField.cutCopyAllowed") != Boolean.TRUE) {
/*     */       UIManager.getLookAndFeel().provideErrorFeedback(this);
/*     */     } else {
/*     */       super.copy();
/* 344 */     }  } protected String paramString() { return super.paramString() + ",echoChar=" + this.echoChar; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public String getText() {
/*     */     return super.getText();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean customSetUIProperty(String paramString, Object paramObject) {
/* 357 */     if (paramString == "echoChar") {
/* 358 */       if (!this.echoCharSet) {
/* 359 */         setEchoChar(((Character)paramObject).charValue());
/* 360 */         this.echoCharSet = false;
/*     */       } 
/* 362 */       return true;
/*     */     } 
/* 364 */     return false; } @Deprecated
/*     */   public String getText(int paramInt1, int paramInt2) throws BadLocationException { return super.getText(paramInt1, paramInt2); }
/*     */   public char[] getPassword() { Document document = getDocument();
/*     */     Segment segment = new Segment();
/*     */     try {
/*     */       document.getText(0, document.getLength(), segment);
/*     */     } catch (BadLocationException badLocationException) {
/*     */       return null;
/*     */     } 
/*     */     char[] arrayOfChar = new char[segment.count];
/*     */     System.arraycopy(segment.array, segment.offset, arrayOfChar, 0, segment.count);
/*     */     return arrayOfChar; }
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException { paramObjectOutputStream.defaultWriteObject();
/*     */     if (getUIClassID().equals("PasswordFieldUI")) {
/*     */       byte b = JComponent.getWriteObjCounter(this);
/*     */       b = (byte)(b - 1);
/*     */       JComponent.setWriteObjCounter(this, b);
/*     */       if (b == 0 && this.ui != null)
/*     */         this.ui.installUI(this); 
/*     */     }  }
/*     */   public AccessibleContext getAccessibleContext() {
/* 385 */     if (this.accessibleContext == null) {
/* 386 */       this.accessibleContext = new AccessibleJPasswordField();
/*     */     }
/* 388 */     return this.accessibleContext;
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
/*     */   protected class AccessibleJPasswordField
/*     */     extends JTextField.AccessibleJTextField
/*     */   {
/*     */     public AccessibleRole getAccessibleRole() {
/* 416 */       return AccessibleRole.PASSWORD_TEXT;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AccessibleText getAccessibleText() {
/* 433 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String getEchoString(String param1String) {
/* 442 */       if (param1String == null) {
/* 443 */         return null;
/*     */       }
/* 445 */       char[] arrayOfChar = new char[param1String.length()];
/* 446 */       Arrays.fill(arrayOfChar, JPasswordField.this.getEchoChar());
/* 447 */       return new String(arrayOfChar);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getAtIndex(int param1Int1, int param1Int2) {
/* 467 */       String str = null;
/* 468 */       if (param1Int1 == 1) {
/* 469 */         str = super.getAtIndex(param1Int1, param1Int2);
/*     */       }
/*     */       else {
/*     */         
/* 473 */         char[] arrayOfChar = JPasswordField.this.getPassword();
/* 474 */         if (arrayOfChar == null || param1Int2 < 0 || param1Int2 >= arrayOfChar.length)
/*     */         {
/* 476 */           return null;
/*     */         }
/* 478 */         str = new String(arrayOfChar);
/*     */       } 
/* 480 */       return getEchoString(str);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getAfterIndex(int param1Int1, int param1Int2) {
/* 500 */       if (param1Int1 == 1) {
/* 501 */         String str = super.getAfterIndex(param1Int1, param1Int2);
/* 502 */         return getEchoString(str);
/*     */       } 
/*     */ 
/*     */       
/* 506 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getBeforeIndex(int param1Int1, int param1Int2) {
/* 527 */       if (param1Int1 == 1) {
/* 528 */         String str = super.getBeforeIndex(param1Int1, param1Int2);
/* 529 */         return getEchoString(str);
/*     */       } 
/*     */ 
/*     */       
/* 533 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getTextRange(int param1Int1, int param1Int2) {
/* 548 */       String str = super.getTextRange(param1Int1, param1Int2);
/* 549 */       return getEchoString(str);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AccessibleTextSequence getTextSequenceAt(int param1Int1, int param1Int2) {
/* 574 */       if (param1Int1 == 1) {
/* 575 */         AccessibleTextSequence accessibleTextSequence = super.getTextSequenceAt(param1Int1, param1Int2);
/* 576 */         if (accessibleTextSequence == null) {
/* 577 */           return null;
/*     */         }
/* 579 */         return new AccessibleTextSequence(accessibleTextSequence.startIndex, accessibleTextSequence.endIndex, 
/* 580 */             getEchoString(accessibleTextSequence.text));
/*     */       } 
/*     */ 
/*     */       
/* 584 */       char[] arrayOfChar = JPasswordField.this.getPassword();
/* 585 */       if (arrayOfChar == null || param1Int2 < 0 || param1Int2 >= arrayOfChar.length)
/*     */       {
/* 587 */         return null;
/*     */       }
/* 589 */       String str = new String(arrayOfChar);
/* 590 */       return new AccessibleTextSequence(0, arrayOfChar.length - 1, 
/* 591 */           getEchoString(str));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AccessibleTextSequence getTextSequenceAfter(int param1Int1, int param1Int2) {
/* 616 */       if (param1Int1 == 1) {
/* 617 */         AccessibleTextSequence accessibleTextSequence = super.getTextSequenceAfter(param1Int1, param1Int2);
/* 618 */         if (accessibleTextSequence == null) {
/* 619 */           return null;
/*     */         }
/* 621 */         return new AccessibleTextSequence(accessibleTextSequence.startIndex, accessibleTextSequence.endIndex, 
/* 622 */             getEchoString(accessibleTextSequence.text));
/*     */       } 
/*     */ 
/*     */       
/* 626 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AccessibleTextSequence getTextSequenceBefore(int param1Int1, int param1Int2) {
/* 651 */       if (param1Int1 == 1) {
/* 652 */         AccessibleTextSequence accessibleTextSequence = super.getTextSequenceBefore(param1Int1, param1Int2);
/* 653 */         if (accessibleTextSequence == null) {
/* 654 */           return null;
/*     */         }
/* 656 */         return new AccessibleTextSequence(accessibleTextSequence.startIndex, accessibleTextSequence.endIndex, 
/* 657 */             getEchoString(accessibleTextSequence.text));
/*     */       } 
/*     */ 
/*     */       
/* 661 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JPasswordField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */