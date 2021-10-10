/*     */ package com.sun.security.auth.callback;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import javax.security.auth.callback.Callback;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.callback.ConfirmationCallback;
/*     */ import javax.security.auth.callback.NameCallback;
/*     */ import javax.security.auth.callback.PasswordCallback;
/*     */ import javax.security.auth.callback.TextOutputCallback;
/*     */ import javax.security.auth.callback.UnsupportedCallbackException;
/*     */ import jdk.Exported;
/*     */ import sun.security.util.Password;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Exported
/*     */ public class TextCallbackHandler
/*     */   implements CallbackHandler
/*     */ {
/*     */   public void handle(Callback[] paramArrayOfCallback) throws IOException, UnsupportedCallbackException {
/*  79 */     ConfirmationCallback confirmationCallback = null;
/*     */     
/*  81 */     for (byte b = 0; b < paramArrayOfCallback.length; b++) {
/*  82 */       if (paramArrayOfCallback[b] instanceof TextOutputCallback) {
/*  83 */         String str1; TextOutputCallback textOutputCallback = (TextOutputCallback)paramArrayOfCallback[b];
/*     */ 
/*     */         
/*  86 */         switch (textOutputCallback.getMessageType()) {
/*     */           case 0:
/*  88 */             str1 = "";
/*     */             break;
/*     */           case 1:
/*  91 */             str1 = "Warning: ";
/*     */             break;
/*     */           case 2:
/*  94 */             str1 = "Error: ";
/*     */             break;
/*     */           default:
/*  97 */             throw new UnsupportedCallbackException(paramArrayOfCallback[b], "Unrecognized message type");
/*     */         } 
/*     */ 
/*     */         
/* 101 */         String str2 = textOutputCallback.getMessage();
/* 102 */         if (str2 != null) {
/* 103 */           str1 = str1 + str2;
/*     */         }
/* 105 */         if (str1 != null) {
/* 106 */           System.err.println(str1);
/*     */         }
/*     */       }
/* 109 */       else if (paramArrayOfCallback[b] instanceof NameCallback) {
/* 110 */         NameCallback nameCallback = (NameCallback)paramArrayOfCallback[b];
/*     */         
/* 112 */         if (nameCallback.getDefaultName() == null) {
/* 113 */           System.err.print(nameCallback.getPrompt());
/*     */         } else {
/* 115 */           System.err.print(nameCallback.getPrompt() + " [" + nameCallback
/* 116 */               .getDefaultName() + "] ");
/*     */         } 
/* 118 */         System.err.flush();
/*     */         
/* 120 */         String str = readLine();
/* 121 */         if (str.equals("")) {
/* 122 */           str = nameCallback.getDefaultName();
/*     */         }
/*     */         
/* 125 */         nameCallback.setName(str);
/*     */       }
/* 127 */       else if (paramArrayOfCallback[b] instanceof PasswordCallback) {
/* 128 */         PasswordCallback passwordCallback = (PasswordCallback)paramArrayOfCallback[b];
/*     */         
/* 130 */         System.err.print(passwordCallback.getPrompt());
/* 131 */         System.err.flush();
/*     */         
/* 133 */         passwordCallback.setPassword(Password.readPassword(System.in, passwordCallback.isEchoOn()));
/*     */       }
/* 135 */       else if (paramArrayOfCallback[b] instanceof ConfirmationCallback) {
/* 136 */         confirmationCallback = (ConfirmationCallback)paramArrayOfCallback[b];
/*     */       } else {
/*     */         
/* 139 */         throw new UnsupportedCallbackException(paramArrayOfCallback[b], "Unrecognized Callback");
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 145 */     if (confirmationCallback != null) {
/* 146 */       doConfirmation(confirmationCallback);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String readLine() throws IOException {
/* 153 */     String str = (new BufferedReader(new InputStreamReader(System.in))).readLine();
/* 154 */     if (str == null) {
/* 155 */       throw new IOException("Cannot read from System.in");
/*     */     }
/* 157 */     return str;
/*     */   }
/*     */   private void doConfirmation(ConfirmationCallback paramConfirmationCallback) throws IOException, UnsupportedCallbackException {
/*     */     String str1;
/*     */     OptionInfo[] arrayOfOptionInfo;
/*     */     String[] arrayOfString;
/*     */     byte b;
/* 164 */     int i = paramConfirmationCallback.getMessageType();
/* 165 */     switch (i) {
/*     */       case 1:
/* 167 */         str1 = "Warning: ";
/*     */         break;
/*     */       case 2:
/* 170 */         str1 = "Error: ";
/*     */         break;
/*     */       case 0:
/* 173 */         str1 = "";
/*     */         break;
/*     */       default:
/* 176 */         throw new UnsupportedCallbackException(paramConfirmationCallback, "Unrecognized message type: " + i);
/*     */     } 
/*     */     class OptionInfo
/*     */     {
/*     */       String name;
/*     */       int value;
/*     */       
/*     */       OptionInfo(String param1String, int param1Int) {
/* 184 */         this.name = param1String;
/* 185 */         this.value = param1Int;
/*     */       }
/*     */     };
/*     */ 
/*     */     
/* 190 */     int j = paramConfirmationCallback.getOptionType();
/* 191 */     switch (j) {
/*     */       case 0:
/* 193 */         arrayOfOptionInfo = new OptionInfo[] { new OptionInfo("Yes", 0), new OptionInfo(this, "No", 1) };
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 199 */         arrayOfOptionInfo = new OptionInfo[] { new OptionInfo(this, "Yes", 0), new OptionInfo(this, "No", 1), new OptionInfo(this, "Cancel", 2) };
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 2:
/* 206 */         arrayOfOptionInfo = new OptionInfo[] { new OptionInfo(this, "OK", 3), new OptionInfo(this, "Cancel", 2) };
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case -1:
/* 212 */         arrayOfString = paramConfirmationCallback.getOptions();
/* 213 */         arrayOfOptionInfo = new OptionInfo[arrayOfString.length];
/* 214 */         for (b = 0; b < arrayOfOptionInfo.length; b++) {
/* 215 */           arrayOfOptionInfo[b] = new OptionInfo(this, arrayOfString[b], b);
/*     */         }
/*     */         break;
/*     */       default:
/* 219 */         throw new UnsupportedCallbackException(paramConfirmationCallback, "Unrecognized option type: " + j);
/*     */     } 
/*     */ 
/*     */     
/* 223 */     int k = paramConfirmationCallback.getDefaultOption();
/*     */     
/* 225 */     String str2 = paramConfirmationCallback.getPrompt();
/* 226 */     if (str2 == null) {
/* 227 */       str2 = "";
/*     */     }
/* 229 */     str2 = str1 + str2;
/* 230 */     if (!str2.equals("")) {
/* 231 */       System.err.println(str2);
/*     */     }
/*     */     int m;
/* 234 */     for (m = 0; m < arrayOfOptionInfo.length; m++) {
/* 235 */       if (j == -1) {
/*     */         
/* 237 */         System.err.println(m + ". " + (arrayOfOptionInfo[m]).name + ((m == k) ? " [default]" : ""));
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 242 */         System.err.println(m + ". " + (arrayOfOptionInfo[m]).name + (((arrayOfOptionInfo[m]).value == k) ? " [default]" : ""));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 247 */     System.err.print("Enter a number: ");
/* 248 */     System.err.flush();
/*     */     
/*     */     try {
/* 251 */       m = Integer.parseInt(readLine());
/* 252 */       if (m < 0 || m > arrayOfOptionInfo.length - 1) {
/* 253 */         m = k;
/*     */       }
/* 255 */       m = (arrayOfOptionInfo[m]).value;
/* 256 */     } catch (NumberFormatException numberFormatException) {
/* 257 */       m = k;
/*     */     } 
/*     */     
/* 260 */     paramConfirmationCallback.setSelectedIndex(m);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/auth/callback/TextCallbackHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */