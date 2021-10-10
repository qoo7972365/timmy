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
/*    */ public class StringEditor
/*    */   extends PropertyEditorSupport
/*    */ {
/*    */   public String getJavaInitializationString() {
/* 34 */     Object object = getValue();
/* 35 */     if (object == null) {
/* 36 */       return "null";
/*    */     }
/* 38 */     String str = object.toString();
/* 39 */     int i = str.length();
/* 40 */     StringBuilder stringBuilder = new StringBuilder(i + 2);
/* 41 */     stringBuilder.append('"');
/* 42 */     for (byte b = 0; b < i; b++) {
/* 43 */       char c = str.charAt(b);
/* 44 */       switch (c) { case '\b':
/* 45 */           stringBuilder.append("\\b"); break;
/* 46 */         case '\t': stringBuilder.append("\\t"); break;
/* 47 */         case '\n': stringBuilder.append("\\n"); break;
/* 48 */         case '\f': stringBuilder.append("\\f"); break;
/* 49 */         case '\r': stringBuilder.append("\\r"); break;
/* 50 */         case '"': stringBuilder.append("\\\""); break;
/* 51 */         case '\\': stringBuilder.append("\\\\"); break;
/*    */         default:
/* 53 */           if (c < ' ' || c > '~') {
/* 54 */             stringBuilder.append("\\u");
/* 55 */             String str1 = Integer.toHexString(c);
/* 56 */             for (int j = str1.length(); j < 4; j++) {
/* 57 */               stringBuilder.append('0');
/*    */             }
/* 59 */             stringBuilder.append(str1); break;
/*    */           } 
/* 61 */           stringBuilder.append(c);
/*    */           break; }
/*    */ 
/*    */     
/*    */     } 
/* 66 */     stringBuilder.append('"');
/* 67 */     return stringBuilder.toString();
/*    */   }
/*    */   
/*    */   public void setAsText(String paramString) {
/* 71 */     setValue(paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/beans/editors/StringEditor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */