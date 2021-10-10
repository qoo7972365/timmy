/*    */ package sun.font;
/*    */ 
/*    */ import java.awt.Font;
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
/*    */ public abstract class FontAccess
/*    */ {
/*    */   private static FontAccess access;
/*    */   
/*    */   public static synchronized void setFontAccess(FontAccess paramFontAccess) {
/* 34 */     if (access != null) {
/* 35 */       throw new InternalError("Attempt to set FontAccessor twice");
/*    */     }
/* 37 */     access = paramFontAccess;
/*    */   }
/*    */   
/*    */   public static synchronized FontAccess getFontAccess() {
/* 41 */     return access;
/*    */   }
/*    */   
/*    */   public abstract Font2D getFont2D(Font paramFont);
/*    */   
/*    */   public abstract void setFont2D(Font paramFont, Font2DHandle paramFont2DHandle);
/*    */   
/*    */   public abstract void setCreatedFont(Font paramFont);
/*    */   
/*    */   public abstract boolean isCreatedFont(Font paramFont);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/FontAccess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */