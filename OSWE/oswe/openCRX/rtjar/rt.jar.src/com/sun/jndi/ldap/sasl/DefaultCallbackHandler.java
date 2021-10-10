/*     */ package com.sun.jndi.ldap.sasl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import javax.security.auth.callback.Callback;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.callback.NameCallback;
/*     */ import javax.security.auth.callback.PasswordCallback;
/*     */ import javax.security.auth.callback.UnsupportedCallbackException;
/*     */ import javax.security.sasl.RealmCallback;
/*     */ import javax.security.sasl.RealmChoiceCallback;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class DefaultCallbackHandler
/*     */   implements CallbackHandler
/*     */ {
/*     */   private char[] passwd;
/*     */   private String authenticationID;
/*     */   private String authRealm;
/*     */   
/*     */   DefaultCallbackHandler(String paramString1, Object paramObject, String paramString2) throws IOException {
/*  55 */     this.authenticationID = paramString1;
/*  56 */     this.authRealm = paramString2;
/*  57 */     if (paramObject instanceof String) {
/*  58 */       this.passwd = ((String)paramObject).toCharArray();
/*  59 */     } else if (paramObject instanceof char[]) {
/*  60 */       this.passwd = (char[])((char[])paramObject).clone();
/*  61 */     } else if (paramObject != null) {
/*     */       
/*  63 */       String str = new String((byte[])paramObject, "UTF8");
/*  64 */       this.passwd = str.toCharArray();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handle(Callback[] paramArrayOfCallback) throws IOException, UnsupportedCallbackException {
/*  70 */     for (byte b = 0; b < paramArrayOfCallback.length; b++) {
/*  71 */       if (paramArrayOfCallback[b] instanceof NameCallback) {
/*  72 */         ((NameCallback)paramArrayOfCallback[b]).setName(this.authenticationID);
/*     */       }
/*  74 */       else if (paramArrayOfCallback[b] instanceof PasswordCallback) {
/*  75 */         ((PasswordCallback)paramArrayOfCallback[b]).setPassword(this.passwd);
/*     */       }
/*  77 */       else if (paramArrayOfCallback[b] instanceof RealmChoiceCallback) {
/*     */ 
/*     */         
/*  80 */         String[] arrayOfString = ((RealmChoiceCallback)paramArrayOfCallback[b]).getChoices();
/*  81 */         byte b1 = 0;
/*     */         
/*  83 */         if (this.authRealm != null && this.authRealm.length() > 0) {
/*  84 */           b1 = -1;
/*  85 */           for (byte b2 = 0; b2 < arrayOfString.length; b2++) {
/*  86 */             if (arrayOfString[b2].equals(this.authRealm)) {
/*  87 */               b1 = b2;
/*     */             }
/*     */           } 
/*  90 */           if (b1 == -1) {
/*  91 */             StringBuffer stringBuffer = new StringBuffer();
/*  92 */             for (byte b3 = 0; b3 < arrayOfString.length; b3++) {
/*  93 */               stringBuffer.append(arrayOfString[b3] + ",");
/*     */             }
/*  95 */             throw new IOException("Cannot match 'java.naming.security.sasl.realm' property value, '" + this.authRealm + "' with choices " + stringBuffer + "in RealmChoiceCallback");
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 102 */         ((RealmChoiceCallback)paramArrayOfCallback[b]).setSelectedIndex(b1);
/*     */       }
/* 104 */       else if (paramArrayOfCallback[b] instanceof RealmCallback) {
/*     */         
/* 106 */         RealmCallback realmCallback = (RealmCallback)paramArrayOfCallback[b];
/* 107 */         if (this.authRealm != null) {
/* 108 */           realmCallback.setText(this.authRealm);
/*     */         } else {
/* 110 */           String str = realmCallback.getDefaultText();
/* 111 */           if (str != null) {
/* 112 */             realmCallback.setText(str);
/*     */           } else {
/* 114 */             realmCallback.setText("");
/*     */           } 
/*     */         } 
/*     */       } else {
/* 118 */         throw new UnsupportedCallbackException(paramArrayOfCallback[b]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   void clearPassword() {
/* 124 */     if (this.passwd != null) {
/* 125 */       for (byte b = 0; b < this.passwd.length; b++) {
/* 126 */         this.passwd[b] = Character.MIN_VALUE;
/*     */       }
/* 128 */       this.passwd = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 133 */     clearPassword();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/sasl/DefaultCallbackHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */