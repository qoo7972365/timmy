/*     */ package javax.swing.text.html.parser;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ContentModel
/*     */   implements Serializable
/*     */ {
/*     */   public int type;
/*     */   public Object content;
/*     */   public ContentModel next;
/*     */   private boolean[] valSet;
/*     */   private boolean[] val;
/*     */   
/*     */   public ContentModel() {}
/*     */   
/*     */   public ContentModel(Element paramElement) {
/*  66 */     this(0, paramElement, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContentModel(int paramInt, ContentModel paramContentModel) {
/*  73 */     this(paramInt, paramContentModel, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContentModel(int paramInt, Object paramObject, ContentModel paramContentModel) {
/*  80 */     this.type = paramInt;
/*  81 */     this.content = paramObject;
/*  82 */     this.next = paramContentModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean empty() {
/*     */     ContentModel contentModel;
/*  90 */     switch (this.type) {
/*     */       case 42:
/*     */       case 63:
/*  93 */         return true;
/*     */       
/*     */       case 43:
/*     */       case 124:
/*  97 */         for (contentModel = (ContentModel)this.content; contentModel != null; contentModel = contentModel.next) {
/*  98 */           if (contentModel.empty()) {
/*  99 */             return true;
/*     */           }
/*     */         } 
/* 102 */         return false;
/*     */       
/*     */       case 38:
/*     */       case 44:
/* 106 */         for (contentModel = (ContentModel)this.content; contentModel != null; contentModel = contentModel.next) {
/* 107 */           if (!contentModel.empty()) {
/* 108 */             return false;
/*     */           }
/*     */         } 
/* 111 */         return true;
/*     */     } 
/*     */     
/* 114 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getElements(Vector<Element> paramVector) {
/*     */     ContentModel contentModel;
/* 123 */     switch (this.type) {
/*     */       case 42:
/*     */       case 43:
/*     */       case 63:
/* 127 */         ((ContentModel)this.content).getElements(paramVector);
/*     */         return;
/*     */       case 38:
/*     */       case 44:
/*     */       case 124:
/* 132 */         for (contentModel = (ContentModel)this.content; contentModel != null; contentModel = contentModel.next) {
/* 133 */           contentModel.getElements(paramVector);
/*     */         }
/*     */         return;
/*     */     } 
/* 137 */     paramVector.addElement((Element)this.content);
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
/*     */   public boolean first(Object paramObject) {
/*     */     ContentModel contentModel1;
/*     */     Element element;
/*     */     ContentModel contentModel2;
/* 152 */     switch (this.type) {
/*     */       case 42:
/*     */       case 43:
/*     */       case 63:
/* 156 */         return ((ContentModel)this.content).first(paramObject);
/*     */       
/*     */       case 44:
/* 159 */         for (contentModel1 = (ContentModel)this.content; contentModel1 != null; contentModel1 = contentModel1.next) {
/* 160 */           if (contentModel1.first(paramObject)) {
/* 161 */             return true;
/*     */           }
/* 163 */           if (!contentModel1.empty()) {
/* 164 */             return false;
/*     */           }
/*     */         } 
/* 167 */         return false;
/*     */       
/*     */       case 38:
/*     */       case 124:
/* 171 */         element = (Element)paramObject;
/* 172 */         if (this.valSet == null || this.valSet.length <= Element.getMaxIndex()) {
/* 173 */           this.valSet = new boolean[Element.getMaxIndex() + 1];
/* 174 */           this.val = new boolean[this.valSet.length];
/*     */         } 
/* 176 */         if (this.valSet[element.index]) {
/* 177 */           return this.val[element.index];
/*     */         }
/* 179 */         for (contentModel2 = (ContentModel)this.content; contentModel2 != null; contentModel2 = contentModel2.next) {
/* 180 */           if (contentModel2.first(paramObject)) {
/* 181 */             this.val[element.index] = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 185 */         this.valSet[element.index] = true;
/* 186 */         return this.val[element.index];
/*     */     } 
/*     */ 
/*     */     
/* 190 */     return (this.content == paramObject);
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
/*     */   public Element first() {
/* 209 */     switch (this.type) {
/*     */       case 38:
/*     */       case 42:
/*     */       case 63:
/*     */       case 124:
/* 214 */         return null;
/*     */       
/*     */       case 43:
/*     */       case 44:
/* 218 */         return ((ContentModel)this.content).first();
/*     */     } 
/*     */     
/* 221 */     return (Element)this.content;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*     */     char[] arrayOfChar;
/*     */     String str;
/*     */     ContentModel contentModel;
/* 229 */     switch (this.type) {
/*     */       case 42:
/* 231 */         return this.content + "*";
/*     */       case 63:
/* 233 */         return this.content + "?";
/*     */       case 43:
/* 235 */         return this.content + "+";
/*     */       
/*     */       case 38:
/*     */       case 44:
/*     */       case 124:
/* 240 */         arrayOfChar = new char[] { ' ', (char)this.type, ' ' };
/* 241 */         str = "";
/* 242 */         for (contentModel = (ContentModel)this.content; contentModel != null; contentModel = contentModel.next) {
/* 243 */           str = str + contentModel;
/* 244 */           if (contentModel.next != null) {
/* 245 */             str = str + new String(arrayOfChar);
/*     */           }
/*     */         } 
/* 248 */         return "(" + str + ")";
/*     */     } 
/*     */     
/* 251 */     return this.content.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/parser/ContentModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */