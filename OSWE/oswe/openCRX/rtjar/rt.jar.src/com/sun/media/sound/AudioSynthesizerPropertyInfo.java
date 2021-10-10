/*    */ package com.sun.media.sound;
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
/*    */ public final class AudioSynthesizerPropertyInfo
/*    */ {
/*    */   public String name;
/*    */   public String description;
/*    */   public Object value;
/*    */   public Class valueClass;
/*    */   public Object[] choices;
/*    */   
/*    */   public AudioSynthesizerPropertyInfo(String paramString, Object paramObject) {
/* 61 */     this.description = null;
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 66 */     this.value = null;
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 71 */     this.valueClass = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 77 */     this.choices = null;
/*    */     this.name = paramString;
/*    */     if (paramObject instanceof Class) {
/*    */       this.valueClass = (Class)paramObject;
/*    */     } else {
/*    */       this.value = paramObject;
/*    */       if (paramObject != null)
/*    */         this.valueClass = paramObject.getClass(); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/AudioSynthesizerPropertyInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */