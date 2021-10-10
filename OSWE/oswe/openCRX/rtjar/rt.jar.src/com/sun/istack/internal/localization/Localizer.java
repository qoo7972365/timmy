/*     */ package com.sun.istack.internal.localization;
/*     */ 
/*     */ import java.text.MessageFormat;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Localizer
/*     */ {
/*     */   private final Locale _locale;
/*     */   private final HashMap _resourceBundles;
/*     */   
/*     */   public Localizer() {
/*  46 */     this(Locale.getDefault());
/*     */   }
/*     */   
/*     */   public Localizer(Locale l) {
/*  50 */     this._locale = l;
/*  51 */     this._resourceBundles = new HashMap<>();
/*     */   }
/*     */   
/*     */   public Locale getLocale() {
/*  55 */     return this._locale;
/*     */   }
/*     */   
/*     */   public String localize(Localizable l) {
/*  59 */     String key = l.getKey();
/*  60 */     if (key == "\000")
/*     */     {
/*  62 */       return (String)l.getArguments()[0];
/*     */     }
/*  64 */     String bundlename = l.getResourceBundleName();
/*     */     
/*     */     try {
/*     */       String msg;
/*  68 */       ResourceBundle bundle = (ResourceBundle)this._resourceBundles.get(bundlename);
/*     */       
/*  70 */       if (bundle == null) {
/*     */         try {
/*  72 */           bundle = ResourceBundle.getBundle(bundlename, this._locale);
/*  73 */         } catch (MissingResourceException e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  81 */           int j = bundlename.lastIndexOf('.');
/*  82 */           if (j != -1) {
/*     */             
/*  84 */             String alternateBundleName = bundlename.substring(j + 1);
/*     */             
/*     */             try {
/*  87 */               bundle = ResourceBundle.getBundle(alternateBundleName, this._locale);
/*     */             
/*     */             }
/*  90 */             catch (MissingResourceException e2) {
/*     */               
/*     */               try {
/*  93 */                 bundle = ResourceBundle.getBundle(bundlename, this._locale, Thread.currentThread().getContextClassLoader());
/*  94 */               } catch (MissingResourceException e3) {
/*     */                 
/*  96 */                 return getDefaultMessage(l);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 103 */         this._resourceBundles.put(bundlename, bundle);
/*     */       } 
/*     */       
/* 106 */       if (bundle == null) {
/* 107 */         return getDefaultMessage(l);
/*     */       }
/*     */       
/* 110 */       if (key == null) {
/* 111 */         key = "undefined";
/*     */       }
/*     */       
/*     */       try {
/* 115 */         msg = bundle.getString(key);
/* 116 */       } catch (MissingResourceException e) {
/*     */         
/* 118 */         msg = bundle.getString("undefined");
/*     */       } 
/*     */ 
/*     */       
/* 122 */       Object[] args = l.getArguments();
/* 123 */       for (int i = 0; i < args.length; i++) {
/* 124 */         if (args[i] instanceof Localizable) {
/* 125 */           args[i] = localize((Localizable)args[i]);
/*     */         }
/*     */       } 
/* 128 */       String message = MessageFormat.format(msg, args);
/* 129 */       return message;
/*     */     }
/* 131 */     catch (MissingResourceException e) {
/* 132 */       return getDefaultMessage(l);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String getDefaultMessage(Localizable l) {
/* 138 */     String key = l.getKey();
/* 139 */     Object[] args = l.getArguments();
/* 140 */     StringBuilder sb = new StringBuilder();
/* 141 */     sb.append("[failed to localize] ");
/* 142 */     sb.append(key);
/* 143 */     if (args != null) {
/* 144 */       sb.append('(');
/* 145 */       for (int i = 0; i < args.length; i++) {
/* 146 */         if (i != 0)
/* 147 */           sb.append(", "); 
/* 148 */         sb.append(String.valueOf(args[i]));
/*     */       } 
/* 150 */       sb.append(')');
/*     */     } 
/* 152 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/istack/internal/localization/Localizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */