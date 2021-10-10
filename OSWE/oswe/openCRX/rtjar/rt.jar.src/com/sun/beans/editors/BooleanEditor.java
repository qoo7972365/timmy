/*    */ package com.sun.beans.editors;
/*    */ 
/*    */ import java.beans.PropertyEditorSupport;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BooleanEditor
/*    */   extends PropertyEditorSupport
/*    */ {
/*    */   public String getJavaInitializationString() {
/* 38 */     Object object = getValue();
/* 39 */     return (object != null) ? object
/* 40 */       .toString() : "null";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAsText() {
/* 45 */     Object object = getValue();
/* 46 */     return (object instanceof Boolean) ? 
/* 47 */       getValidName(((Boolean)object).booleanValue()) : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setAsText(String paramString) throws IllegalArgumentException {
/* 52 */     if (paramString == null) {
/* 53 */       setValue(null);
/* 54 */     } else if (isValidName(true, paramString)) {
/* 55 */       setValue(Boolean.TRUE);
/* 56 */     } else if (isValidName(false, paramString)) {
/* 57 */       setValue(Boolean.FALSE);
/*    */     } else {
/* 59 */       throw new IllegalArgumentException(paramString);
/*    */     } 
/*    */   }
/*    */   
/*    */   public String[] getTags() {
/* 64 */     return new String[] { getValidName(true), getValidName(false) };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private String getValidName(boolean paramBoolean) {
/* 70 */     return paramBoolean ? "True" : "False";
/*    */   }
/*    */   
/*    */   private boolean isValidName(boolean paramBoolean, String paramString) {
/* 74 */     return getValidName(paramBoolean).equalsIgnoreCase(paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/beans/editors/BooleanEditor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */