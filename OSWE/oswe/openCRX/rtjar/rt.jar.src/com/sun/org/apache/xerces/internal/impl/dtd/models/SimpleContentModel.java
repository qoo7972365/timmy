/*     */ package com.sun.org.apache.xerces.internal.impl.dtd.models;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.xni.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleContentModel
/*     */   implements ContentModelValidator
/*     */ {
/*     */   public static final short CHOICE = -1;
/*     */   public static final short SEQUENCE = -1;
/* 114 */   private QName fFirstChild = new QName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   private QName fSecondChild = new QName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int fOperator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleContentModel(short operator, QName firstChild, QName secondChild) {
/* 154 */     this.fFirstChild.setValues(firstChild);
/* 155 */     if (secondChild != null) {
/* 156 */       this.fSecondChild.setValues(secondChild);
/*     */     } else {
/*     */       
/* 159 */       this.fSecondChild.clear();
/*     */     } 
/* 161 */     this.fOperator = operator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int validate(QName[] children, int offset, int length) {
/*     */     int index;
/* 197 */     switch (this.fOperator) {
/*     */ 
/*     */       
/*     */       case 0:
/* 201 */         if (length == 0) {
/* 202 */           return 0;
/*     */         }
/*     */         
/* 205 */         if ((children[offset]).rawname != this.fFirstChild.rawname) {
/* 206 */           return 0;
/*     */         }
/*     */ 
/*     */         
/* 210 */         if (length > 1) {
/* 211 */           return 1;
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
/* 317 */         return -1;case 1: if (length == 1 && (children[offset]).rawname != this.fFirstChild.rawname) return 0;  if (length > 1) return 1;  return -1;case 2: if (length > 0) for (int i = 0; i < length; i++) { if ((children[offset + i]).rawname != this.fFirstChild.rawname) return i;  }   return -1;case 3: if (length == 0) return 0;  for (index = 0; index < length; index++) { if ((children[offset + index]).rawname != this.fFirstChild.rawname) return index;  }  return -1;case 4: if (length == 0) return 0;  if ((children[offset]).rawname != this.fFirstChild.rawname && (children[offset]).rawname != this.fSecondChild.rawname) return 0;  if (length > 1) return 1;  return -1;case 5: if (length == 2) { if ((children[offset]).rawname != this.fFirstChild.rawname) return 0;  if ((children[offset + 1]).rawname != this.fSecondChild.rawname) return 1;  } else { if (length > 2) return 2;  return length; }  return -1;
/*     */     } 
/*     */     throw new RuntimeException("ImplementationMessages.VAL_CST");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dtd/models/SimpleContentModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */