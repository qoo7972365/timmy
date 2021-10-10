/*    */ package sun.reflect;
/*    */ 
/*    */ import java.lang.reflect.Modifier;
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
/*    */ public class FieldInfo
/*    */ {
/*    */   private String name;
/*    */   private String signature;
/*    */   private int modifiers;
/*    */   private int slot;
/*    */   
/*    */   public String name() {
/* 50 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String signature() {
/* 56 */     return this.signature;
/*    */   }
/*    */   
/*    */   public int modifiers() {
/* 60 */     return this.modifiers;
/*    */   }
/*    */   
/*    */   public int slot() {
/* 64 */     return this.slot;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isPublic() {
/* 69 */     return Modifier.isPublic(modifiers());
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/FieldInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */