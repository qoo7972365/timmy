/*     */ package com.sun.security.auth.callback;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import javax.security.auth.callback.Callback;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.callback.ConfirmationCallback;
/*     */ import javax.security.auth.callback.NameCallback;
/*     */ import javax.security.auth.callback.PasswordCallback;
/*     */ import javax.security.auth.callback.TextOutputCallback;
/*     */ import javax.security.auth.callback.UnsupportedCallbackException;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPasswordField;
/*     */ import javax.swing.JTextField;
/*     */ import jdk.Exported;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Exported(false)
/*     */ @Deprecated
/*     */ public class DialogCallbackHandler
/*     */   implements CallbackHandler
/*     */ {
/*     */   private Component parentComponent;
/*     */   private static final int JPasswordFieldLen = 8;
/*     */   private static final int JTextFieldLen = 8;
/*     */   
/*     */   public DialogCallbackHandler() {}
/*     */   
/*     */   public DialogCallbackHandler(Component paramComponent) {
/*  82 */     this.parentComponent = paramComponent;
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
/*     */   public void handle(Callback[] paramArrayOfCallback) throws UnsupportedCallbackException {
/* 105 */     ArrayList<String> arrayList = new ArrayList(3);
/*     */ 
/*     */     
/* 108 */     ArrayList<Action> arrayList1 = new ArrayList(2);
/*     */     
/* 110 */     ConfirmationInfo confirmationInfo = new ConfirmationInfo();
/*     */     int i;
/* 112 */     for (i = 0; i < paramArrayOfCallback.length; i++) {
/* 113 */       if (paramArrayOfCallback[i] instanceof TextOutputCallback) {
/* 114 */         TextOutputCallback textOutputCallback = (TextOutputCallback)paramArrayOfCallback[i];
/*     */         
/* 116 */         switch (textOutputCallback.getMessageType()) {
/*     */           case 0:
/* 118 */             confirmationInfo.messageType = 1;
/*     */             break;
/*     */           case 1:
/* 121 */             confirmationInfo.messageType = 2;
/*     */             break;
/*     */           case 2:
/* 124 */             confirmationInfo.messageType = 0;
/*     */             break;
/*     */           default:
/* 127 */             throw new UnsupportedCallbackException(paramArrayOfCallback[i], "Unrecognized message type");
/*     */         } 
/*     */ 
/*     */         
/* 131 */         arrayList.add(textOutputCallback.getMessage());
/*     */       }
/* 133 */       else if (paramArrayOfCallback[i] instanceof NameCallback) {
/* 134 */         final NameCallback nc = (NameCallback)paramArrayOfCallback[i];
/*     */         
/* 136 */         JLabel jLabel = new JLabel(nameCallback.getPrompt());
/*     */         
/* 138 */         final JTextField name = new JTextField(8);
/* 139 */         String str = nameCallback.getDefaultName();
/* 140 */         if (str != null) {
/* 141 */           jTextField.setText(str);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 148 */         Box box = Box.createHorizontalBox();
/* 149 */         box.add(jLabel);
/* 150 */         box.add(jTextField);
/* 151 */         arrayList.add(box);
/*     */ 
/*     */         
/* 154 */         arrayList1.add(new Action() {
/*     */               public void perform() {
/* 156 */                 nc.setName(name.getText());
/*     */               }
/*     */             });
/*     */       }
/* 160 */       else if (paramArrayOfCallback[i] instanceof PasswordCallback) {
/* 161 */         final PasswordCallback pc = (PasswordCallback)paramArrayOfCallback[i];
/*     */         
/* 163 */         JLabel jLabel = new JLabel(passwordCallback.getPrompt());
/*     */         
/* 165 */         final JPasswordField password = new JPasswordField(8);
/*     */         
/* 167 */         if (!passwordCallback.isEchoOn()) {
/* 168 */           jPasswordField.setEchoChar('*');
/*     */         }
/*     */         
/* 171 */         Box box = Box.createHorizontalBox();
/* 172 */         box.add(jLabel);
/* 173 */         box.add(jPasswordField);
/* 174 */         arrayList.add(box);
/*     */         
/* 176 */         arrayList1.add(new Action() {
/*     */               public void perform() {
/* 178 */                 pc.setPassword(password.getPassword());
/*     */               }
/*     */             });
/*     */       }
/* 182 */       else if (paramArrayOfCallback[i] instanceof ConfirmationCallback) {
/* 183 */         ConfirmationCallback confirmationCallback = (ConfirmationCallback)paramArrayOfCallback[i];
/*     */         
/* 185 */         confirmationInfo.setCallback(confirmationCallback);
/* 186 */         if (confirmationCallback.getPrompt() != null) {
/* 187 */           arrayList.add(confirmationCallback.getPrompt());
/*     */         }
/*     */       } else {
/*     */         
/* 191 */         throw new UnsupportedCallbackException(paramArrayOfCallback[i], "Unrecognized Callback");
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 197 */     i = JOptionPane.showOptionDialog(this.parentComponent, arrayList
/*     */         
/* 199 */         .toArray(), "Confirmation", confirmationInfo.optionType, confirmationInfo.messageType, null, confirmationInfo.options, confirmationInfo.initialValue);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 208 */     if (i == 0 || i == 0) {
/*     */ 
/*     */       
/* 211 */       Iterator<Action> iterator = arrayList1.iterator();
/* 212 */       while (iterator.hasNext()) {
/* 213 */         ((Action)iterator.next()).perform();
/*     */       }
/*     */     } 
/* 216 */     confirmationInfo.handleResult(i);
/*     */   }
/*     */   
/*     */   private static interface Action
/*     */   {
/*     */     void perform();
/*     */   }
/*     */   
/*     */   private static class ConfirmationInfo
/*     */   {
/*     */     private int[] translations;
/* 227 */     int optionType = 2;
/* 228 */     Object[] options = null;
/* 229 */     Object initialValue = null;
/*     */     
/* 231 */     int messageType = 3;
/*     */ 
/*     */     
/*     */     private ConfirmationCallback callback;
/*     */ 
/*     */ 
/*     */     
/*     */     void setCallback(ConfirmationCallback param1ConfirmationCallback) throws UnsupportedCallbackException {
/* 239 */       this.callback = param1ConfirmationCallback;
/*     */       
/* 241 */       int i = param1ConfirmationCallback.getOptionType();
/* 242 */       switch (i) {
/*     */         case 0:
/* 244 */           this.optionType = 0;
/* 245 */           this.translations = new int[] { 0, 0, 1, 1, -1, 1 };
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 1:
/* 252 */           this.optionType = 1;
/* 253 */           this.translations = new int[] { 0, 0, 1, 1, 2, 2, -1, 2 };
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 2:
/* 261 */           this.optionType = 2;
/* 262 */           this.translations = new int[] { 0, 3, 2, 2, -1, 2 };
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case -1:
/* 269 */           this.options = (Object[])param1ConfirmationCallback.getOptions();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 275 */           this
/* 276 */             .translations = new int[] { -1, param1ConfirmationCallback.getDefaultOption() };
/*     */           break;
/*     */         
/*     */         default:
/* 280 */           throw new UnsupportedCallbackException(param1ConfirmationCallback, "Unrecognized option type: " + i);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 285 */       int j = param1ConfirmationCallback.getMessageType();
/* 286 */       switch (j) {
/*     */         case 1:
/* 288 */           this.messageType = 2;
/*     */           return;
/*     */         case 2:
/* 291 */           this.messageType = 0;
/*     */           return;
/*     */         case 0:
/* 294 */           this.messageType = 1;
/*     */           return;
/*     */       } 
/* 297 */       throw new UnsupportedCallbackException(param1ConfirmationCallback, "Unrecognized message type: " + j);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void handleResult(int param1Int) {
/* 306 */       if (this.callback == null) {
/*     */         return;
/*     */       }
/*     */       
/* 310 */       for (byte b = 0; b < this.translations.length; b += 2) {
/* 311 */         if (this.translations[b] == param1Int) {
/* 312 */           param1Int = this.translations[b + 1];
/*     */           break;
/*     */         } 
/*     */       } 
/* 316 */       this.callback.setSelectedIndex(param1Int);
/*     */     }
/*     */     
/*     */     private ConfirmationInfo() {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/auth/callback/DialogCallbackHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */