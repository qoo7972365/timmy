/*     */ package javax.swing.text.html.parser;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ContentModelState
/*     */ {
/*     */   ContentModel model;
/*     */   long value;
/*     */   ContentModelState next;
/*     */   
/*     */   public ContentModelState(ContentModel paramContentModel) {
/*  54 */     this(paramContentModel, null, 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ContentModelState(Object paramObject, ContentModelState paramContentModelState) {
/*  62 */     this(paramObject, paramContentModelState, 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ContentModelState(Object paramObject, ContentModelState paramContentModelState, long paramLong) {
/*  70 */     this.model = (ContentModel)paramObject;
/*  71 */     this.next = paramContentModelState;
/*  72 */     this.value = paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContentModel getModel() {
/*  79 */     ContentModel contentModel = this.model;
/*  80 */     for (byte b = 0; b < this.value; b++) {
/*  81 */       if (contentModel.next != null) {
/*  82 */         contentModel = contentModel.next;
/*     */       } else {
/*  84 */         return null;
/*     */       } 
/*     */     } 
/*  87 */     return contentModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean terminate() {
/*     */     ContentModel contentModel;
/*     */     byte b;
/*  96 */     switch (this.model.type) {
/*     */       case 43:
/*  98 */         if (this.value == 0L && !this.model.empty()) {
/*  99 */           return false;
/*     */         }
/*     */       case 42:
/*     */       case 63:
/* 103 */         return (this.next == null || this.next.terminate());
/*     */       
/*     */       case 124:
/* 106 */         for (contentModel = (ContentModel)this.model.content; contentModel != null; contentModel = contentModel.next) {
/* 107 */           if (contentModel.empty()) {
/* 108 */             return (this.next == null || this.next.terminate());
/*     */           }
/*     */         } 
/* 111 */         return false;
/*     */       
/*     */       case 38:
/* 114 */         contentModel = (ContentModel)this.model.content;
/*     */         
/* 116 */         for (b = 0; contentModel != null; b++, contentModel = contentModel.next) {
/* 117 */           if ((this.value & 1L << b) == 0L && 
/* 118 */             !contentModel.empty()) {
/* 119 */             return false;
/*     */           }
/*     */         } 
/*     */         
/* 123 */         return (this.next == null || this.next.terminate());
/*     */ 
/*     */       
/*     */       case 44:
/* 127 */         contentModel = (ContentModel)this.model.content;
/* 128 */         for (b = 0; b < this.value; ) { b++; contentModel = contentModel.next; }
/*     */         
/* 130 */         for (; contentModel != null && contentModel.empty(); contentModel = contentModel.next);
/* 131 */         if (contentModel != null) {
/* 132 */           return false;
/*     */         }
/* 134 */         return (this.next == null || this.next.terminate());
/*     */     } 
/*     */ 
/*     */     
/* 138 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element first() {
/*     */     ContentModel contentModel;
/*     */     byte b;
/* 148 */     switch (this.model.type) {
/*     */       case 38:
/*     */       case 42:
/*     */       case 63:
/*     */       case 124:
/* 153 */         return null;
/*     */       
/*     */       case 43:
/* 156 */         return this.model.first();
/*     */       
/*     */       case 44:
/* 159 */         contentModel = (ContentModel)this.model.content;
/* 160 */         for (b = 0; b < this.value; ) { b++; contentModel = contentModel.next; }
/* 161 */          return contentModel.first();
/*     */     } 
/*     */ 
/*     */     
/* 165 */     return this.model.first();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContentModelState advance(Object paramObject) {
/*     */     ContentModel contentModel;
/*     */     byte b1;
/*     */     byte b2;
/* 175 */     switch (this.model.type)
/*     */     { case 43:
/* 177 */         if (this.model.first(paramObject)) {
/* 178 */           return (new ContentModelState(this.model.content, new ContentModelState(this.model, this.next, this.value + 1L)))
/* 179 */             .advance(paramObject);
/*     */         }
/* 181 */         if (this.value != 0L) {
/* 182 */           if (this.next != null) {
/* 183 */             return this.next.advance(paramObject);
/*     */           }
/* 185 */           return null;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 293 */         return null;case 42: if (this.model.first(paramObject)) return (new ContentModelState(this.model.content, this)).advance(paramObject);  if (this.next != null) return this.next.advance(paramObject);  return null;case 63: if (this.model.first(paramObject)) return (new ContentModelState(this.model.content, this.next)).advance(paramObject);  if (this.next != null) return this.next.advance(paramObject);  return null;case 124: for (contentModel = (ContentModel)this.model.content; contentModel != null; contentModel = contentModel.next) { if (contentModel.first(paramObject)) return (new ContentModelState(contentModel, this.next)).advance(paramObject);  }  return null;case 44: contentModel = (ContentModel)this.model.content; for (b1 = 0; b1 < this.value; ) { b1++; contentModel = contentModel.next; }  if (contentModel.first(paramObject) || contentModel.empty()) { if (contentModel.next == null) return (new ContentModelState(contentModel, this.next)).advance(paramObject);  return (new ContentModelState(contentModel, new ContentModelState(this.model, this.next, this.value + 1L))).advance(paramObject); }  return null;case 38: contentModel = (ContentModel)this.model.content; b1 = 1; for (b2 = 0; contentModel != null; b2++, contentModel = contentModel.next) { if ((this.value & 1L << b2) == 0L) { if (contentModel.first(paramObject)) return (new ContentModelState(contentModel, new ContentModelState(this.model, this.next, this.value | 1L << b2))).advance(paramObject);  if (!contentModel.empty()) b1 = 0;  }  }  if (b1 != 0) { if (this.next != null) return this.next.advance(paramObject);  return null; }  return null; }  if (this.model.content == paramObject) { if (this.next == null && paramObject instanceof Element && ((Element)paramObject).content != null) return new ContentModelState(((Element)paramObject).content);  return this.next; }  return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/parser/ContentModelState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */