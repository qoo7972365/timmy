/*    */ package sun.management;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ public class MethodInfo
/*    */   implements Serializable
/*    */ {
/*    */   private String name;
/*    */   private long type;
/*    */   private int compileSize;
/*    */   private static final long serialVersionUID = 6992337162326171013L;
/*    */   
/*    */   MethodInfo(String paramString, long paramLong, int paramInt) {
/* 38 */     this.name = paramString;
/* 39 */     this.type = paramLong;
/* 40 */     this.compileSize = paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 49 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getType() {
/* 59 */     return this.type;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCompileSize() {
/* 69 */     return this.compileSize;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 73 */     return getName() + " type = " + getType() + " compileSize = " + 
/* 74 */       getCompileSize();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/MethodInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */