/*     */ package java.text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FieldPosition
/*     */ {
/*  79 */   int field = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   int endIndex = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   int beginIndex = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Format.Field attribute;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldPosition(int paramInt) {
/* 110 */     this.field = paramInt;
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
/*     */   public FieldPosition(Format.Field paramField) {
/* 123 */     this(paramField, -1);
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
/*     */   public FieldPosition(Format.Field paramField, int paramInt) {
/* 143 */     this.attribute = paramField;
/* 144 */     this.field = paramInt;
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
/*     */   public Format.Field getFieldAttribute() {
/* 156 */     return this.attribute;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getField() {
/* 165 */     return this.field;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBeginIndex() {
/* 174 */     return this.beginIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEndIndex() {
/* 184 */     return this.endIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBeginIndex(int paramInt) {
/* 194 */     this.beginIndex = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEndIndex(int paramInt) {
/* 204 */     this.endIndex = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Format.FieldDelegate getFieldDelegate() {
/* 214 */     return new Delegate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 222 */     if (paramObject == null) return false; 
/* 223 */     if (!(paramObject instanceof FieldPosition))
/* 224 */       return false; 
/* 225 */     FieldPosition fieldPosition = (FieldPosition)paramObject;
/* 226 */     if (this.attribute == null) {
/* 227 */       if (fieldPosition.attribute != null) {
/* 228 */         return false;
/*     */       }
/*     */     }
/* 231 */     else if (!this.attribute.equals(fieldPosition.attribute)) {
/* 232 */       return false;
/*     */     } 
/* 234 */     return (this.beginIndex == fieldPosition.beginIndex && this.endIndex == fieldPosition.endIndex && this.field == fieldPosition.field);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 244 */     return this.field << 24 | this.beginIndex << 16 | this.endIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 252 */     return getClass().getName() + "[field=" + this.field + ",attribute=" + this.attribute + ",beginIndex=" + this.beginIndex + ",endIndex=" + this.endIndex + ']';
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
/*     */   private boolean matchesField(Format.Field paramField) {
/* 264 */     if (this.attribute != null) {
/* 265 */       return this.attribute.equals(paramField);
/*     */     }
/* 267 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesField(Format.Field paramField, int paramInt) {
/* 276 */     if (this.attribute != null) {
/* 277 */       return this.attribute.equals(paramField);
/*     */     }
/* 279 */     return (paramInt == this.field);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class Delegate
/*     */     implements Format.FieldDelegate
/*     */   {
/*     */     private boolean encounteredField;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Delegate() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void formatted(Format.Field param1Field, Object param1Object, int param1Int1, int param1Int2, StringBuffer param1StringBuffer) {
/* 298 */       if (!this.encounteredField && FieldPosition.this.matchesField(param1Field)) {
/* 299 */         FieldPosition.this.setBeginIndex(param1Int1);
/* 300 */         FieldPosition.this.setEndIndex(param1Int2);
/* 301 */         this.encounteredField = (param1Int1 != param1Int2);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void formatted(int param1Int1, Format.Field param1Field, Object param1Object, int param1Int2, int param1Int3, StringBuffer param1StringBuffer) {
/* 307 */       if (!this.encounteredField && FieldPosition.this.matchesField(param1Field, param1Int1)) {
/* 308 */         FieldPosition.this.setBeginIndex(param1Int2);
/* 309 */         FieldPosition.this.setEndIndex(param1Int3);
/* 310 */         this.encounteredField = (param1Int2 != param1Int3);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/text/FieldPosition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */