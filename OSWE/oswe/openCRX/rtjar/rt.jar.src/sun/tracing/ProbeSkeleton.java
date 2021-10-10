/*    */ package sun.tracing;
/*    */ 
/*    */ import com.sun.tracing.Probe;
/*    */ import java.lang.reflect.Field;
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
/*    */ public abstract class ProbeSkeleton
/*    */   implements Probe
/*    */ {
/*    */   protected Class<?>[] parameters;
/*    */   
/*    */   protected ProbeSkeleton(Class<?>[] paramArrayOfClass) {
/* 42 */     this.parameters = paramArrayOfClass;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract boolean isEnabled();
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract void uncheckedTrigger(Object[] paramArrayOfObject);
/*    */ 
/*    */ 
/*    */   
/*    */   private static boolean isAssignable(Object paramObject, Class<?> paramClass) {
/* 57 */     if (paramObject != null && 
/* 58 */       !paramClass.isInstance(paramObject)) {
/* 59 */       if (paramClass.isPrimitive()) {
/*    */         
/*    */         try {
/* 62 */           Field field = paramObject.getClass().getField("TYPE");
/* 63 */           return paramClass.isAssignableFrom((Class)field.get(null));
/* 64 */         } catch (Exception exception) {}
/*    */       }
/*    */ 
/*    */       
/* 68 */       return false;
/*    */     } 
/*    */     
/* 71 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void trigger(Object... paramVarArgs) {
/* 78 */     if (paramVarArgs.length != this.parameters.length) {
/* 79 */       throw new IllegalArgumentException("Wrong number of arguments");
/*    */     }
/* 81 */     for (byte b = 0; b < this.parameters.length; b++) {
/* 82 */       if (!isAssignable(paramVarArgs[b], this.parameters[b])) {
/* 83 */         throw new IllegalArgumentException("Wrong type of argument at position " + b);
/*    */       }
/*    */     } 
/*    */     
/* 87 */     uncheckedTrigger(paramVarArgs);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/tracing/ProbeSkeleton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */