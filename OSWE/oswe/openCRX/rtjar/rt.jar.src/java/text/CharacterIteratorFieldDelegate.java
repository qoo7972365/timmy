/*     */ package java.text;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class CharacterIteratorFieldDelegate
/*     */   implements Format.FieldDelegate
/*     */ {
/*  53 */   private ArrayList<AttributedString> attributedStrings = new ArrayList<>();
/*     */   
/*     */   private int size;
/*     */   
/*     */   public void formatted(Format.Field paramField, Object paramObject, int paramInt1, int paramInt2, StringBuffer paramStringBuffer) {
/*  58 */     if (paramInt1 != paramInt2) {
/*  59 */       if (paramInt1 < this.size) {
/*     */         
/*  61 */         int i = this.size;
/*  62 */         int j = this.attributedStrings.size() - 1;
/*     */         
/*  64 */         while (paramInt1 < i) {
/*     */           
/*  66 */           AttributedString attributedString = this.attributedStrings.get(j--);
/*  67 */           int k = i - attributedString.length();
/*  68 */           int m = Math.max(0, paramInt1 - k);
/*     */           
/*  70 */           attributedString.addAttribute(paramField, paramObject, m, Math.min(paramInt2 - paramInt1, attributedString
/*  71 */                 .length() - m) + m);
/*     */           
/*  73 */           i = k;
/*     */         } 
/*     */       } 
/*  76 */       if (this.size < paramInt1) {
/*     */         
/*  78 */         this.attributedStrings.add(new AttributedString(paramStringBuffer
/*  79 */               .substring(this.size, paramInt1)));
/*  80 */         this.size = paramInt1;
/*     */       } 
/*  82 */       if (this.size < paramInt2) {
/*     */         
/*  84 */         int i = Math.max(paramInt1, this.size);
/*     */         
/*  86 */         AttributedString attributedString = new AttributedString(paramStringBuffer.substring(i, paramInt2));
/*     */         
/*  88 */         attributedString.addAttribute(paramField, paramObject);
/*  89 */         this.attributedStrings.add(attributedString);
/*  90 */         this.size = paramInt2;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void formatted(int paramInt1, Format.Field paramField, Object paramObject, int paramInt2, int paramInt3, StringBuffer paramStringBuffer) {
/*  97 */     formatted(paramField, paramObject, paramInt2, paramInt3, paramStringBuffer);
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
/*     */   public AttributedCharacterIterator getIterator(String paramString) {
/* 109 */     if (paramString.length() > this.size) {
/* 110 */       this.attributedStrings.add(new AttributedString(paramString
/* 111 */             .substring(this.size)));
/* 112 */       this.size = paramString.length();
/*     */     } 
/* 114 */     int i = this.attributedStrings.size();
/* 115 */     AttributedCharacterIterator[] arrayOfAttributedCharacterIterator = new AttributedCharacterIterator[i];
/*     */ 
/*     */     
/* 118 */     for (byte b = 0; b < i; b++) {
/* 119 */       arrayOfAttributedCharacterIterator[b] = ((AttributedString)this.attributedStrings
/* 120 */         .get(b)).getIterator();
/*     */     }
/* 122 */     return (new AttributedString(arrayOfAttributedCharacterIterator)).getIterator();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/text/CharacterIteratorFieldDelegate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */