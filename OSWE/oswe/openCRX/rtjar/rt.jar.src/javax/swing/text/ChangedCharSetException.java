/*    */ package javax.swing.text;
/*    */ 
/*    */ import java.io.IOException;
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
/*    */ public class ChangedCharSetException
/*    */   extends IOException
/*    */ {
/*    */   String charSetSpec;
/*    */   boolean charSetKey;
/*    */   
/*    */   public ChangedCharSetException(String paramString, boolean paramBoolean) {
/* 41 */     this.charSetSpec = paramString;
/* 42 */     this.charSetKey = paramBoolean;
/*    */   }
/*    */   
/*    */   public String getCharSetSpec() {
/* 46 */     return this.charSetSpec;
/*    */   }
/*    */   
/*    */   public boolean keyEqualsCharSet() {
/* 50 */     return this.charSetKey;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/ChangedCharSetException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */