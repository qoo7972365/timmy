/*     */ package com.sun.xml.internal.fastinfoset.stax.events;
/*     */ 
/*     */ import com.sun.xml.internal.fastinfoset.org.apache.xerces.util.XMLChar;
/*     */ import javax.xml.stream.events.Characters;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CharactersEvent
/*     */   extends EventBase
/*     */   implements Characters
/*     */ {
/*     */   private String _text;
/*     */   private boolean isCData = false;
/*     */   private boolean isSpace = false;
/*     */   private boolean isIgnorable = false;
/*     */   private boolean needtoCheck = true;
/*     */   
/*     */   public CharactersEvent() {
/*  42 */     super(4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharactersEvent(String data) {
/*  49 */     super(4);
/*  50 */     this._text = data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharactersEvent(String data, boolean isCData) {
/*  59 */     super(4);
/*  60 */     this._text = data;
/*  61 */     this.isCData = isCData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getData() {
/*  68 */     return this._text;
/*     */   }
/*     */   
/*     */   public void setData(String data) {
/*  72 */     this._text = data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCData() {
/*  80 */     return this.isCData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  88 */     if (this.isCData) {
/*  89 */       return "<![CDATA[" + this._text + "]]>";
/*     */     }
/*  91 */     return this._text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIgnorableWhiteSpace() {
/* 101 */     return this.isIgnorable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWhiteSpace() {
/* 112 */     if (this.needtoCheck) {
/* 113 */       checkWhiteSpace();
/* 114 */       this.needtoCheck = false;
/*     */     } 
/* 116 */     return this.isSpace;
/*     */   }
/*     */   
/*     */   public void setSpace(boolean isSpace) {
/* 120 */     this.isSpace = isSpace;
/* 121 */     this.needtoCheck = false;
/*     */   }
/*     */   public void setIgnorable(boolean isIgnorable) {
/* 124 */     this.isIgnorable = isIgnorable;
/* 125 */     setEventType(6);
/*     */   }
/*     */   
/*     */   private void checkWhiteSpace() {
/* 129 */     if (!Util.isEmptyString(this._text)) {
/* 130 */       this.isSpace = true;
/* 131 */       for (int i = 0; i < this._text.length(); i++) {
/* 132 */         if (!XMLChar.isSpace(this._text.charAt(i))) {
/* 133 */           this.isSpace = false;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/stax/events/CharactersEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */