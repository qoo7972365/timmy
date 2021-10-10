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
/*    */ public final class RIFFInvalidFormatException
/*    */   extends InvalidFormatException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public RIFFInvalidFormatException() {
/* 38 */     super("Invalid format!");
/*    */   }
/*    */   
/*    */   public RIFFInvalidFormatException(String paramString) {
/* 42 */     super(paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/RIFFInvalidFormatException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */