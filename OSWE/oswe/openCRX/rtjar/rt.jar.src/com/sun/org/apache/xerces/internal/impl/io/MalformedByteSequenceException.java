/*     */ package com.sun.org.apache.xerces.internal.impl.io;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.util.MessageFormatter;
/*     */ import java.io.CharConversionException;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MalformedByteSequenceException
/*     */   extends CharConversionException
/*     */ {
/*     */   static final long serialVersionUID = 8436382245048328739L;
/*     */   private MessageFormatter fFormatter;
/*     */   private Locale fLocale;
/*     */   private String fDomain;
/*     */   private String fKey;
/*     */   private Object[] fArguments;
/*     */   private String fMessage;
/*     */   
/*     */   public MalformedByteSequenceException(MessageFormatter formatter, Locale locale, String domain, String key, Object[] arguments) {
/*  83 */     this.fFormatter = formatter;
/*  84 */     this.fLocale = locale;
/*  85 */     this.fDomain = domain;
/*  86 */     this.fKey = key;
/*  87 */     this.fArguments = arguments;
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
/*     */   public String getDomain() {
/* 100 */     return this.fDomain;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getKey() {
/* 109 */     return this.fKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] getArguments() {
/* 120 */     return this.fArguments;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessage() {
/* 129 */     if (this.fMessage == null) {
/* 130 */       this.fMessage = this.fFormatter.formatMessage(this.fLocale, this.fKey, this.fArguments);
/*     */ 
/*     */       
/* 133 */       this.fFormatter = null;
/* 134 */       this.fLocale = null;
/*     */     } 
/* 136 */     return this.fMessage;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/io/MalformedByteSequenceException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */