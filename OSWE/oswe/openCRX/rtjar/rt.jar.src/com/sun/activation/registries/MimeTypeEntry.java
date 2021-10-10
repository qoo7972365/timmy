/*    */ package com.sun.activation.registries;
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
/*    */ public class MimeTypeEntry
/*    */ {
/*    */   private String type;
/*    */   private String extension;
/*    */   
/*    */   public MimeTypeEntry(String mime_type, String file_ext) {
/* 35 */     this.type = mime_type;
/* 36 */     this.extension = file_ext;
/*    */   }
/*    */   
/*    */   public String getMIMEType() {
/* 40 */     return this.type;
/*    */   }
/*    */   
/*    */   public String getFileExtension() {
/* 44 */     return this.extension;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 48 */     return "MIMETypeEntry: " + this.type + ", " + this.extension;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/activation/registries/MimeTypeEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */