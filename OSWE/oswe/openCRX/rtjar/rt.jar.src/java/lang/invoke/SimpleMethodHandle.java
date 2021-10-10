/*    */ package java.lang.invoke;
/*    */ 
/*    */ import java.lang.invoke.BoundMethodHandle;
/*    */ import java.lang.invoke.LambdaForm;
/*    */ import java.lang.invoke.MethodHandle;
/*    */ import java.lang.invoke.MethodHandleStatics;
/*    */ import java.lang.invoke.MethodType;
/*    */ import java.lang.invoke.SimpleMethodHandle;
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
/*    */ final class SimpleMethodHandle
/*    */   extends BoundMethodHandle
/*    */ {
/*    */   private SimpleMethodHandle(MethodType paramMethodType, LambdaForm paramLambdaForm) {
/* 37 */     super(paramMethodType, paramLambdaForm);
/*    */   }
/*    */   
/*    */   static BoundMethodHandle make(MethodType paramMethodType, LambdaForm paramLambdaForm) {
/* 41 */     return new SimpleMethodHandle(paramMethodType, paramLambdaForm);
/*    */   }
/*    */   
/* 44 */   static final BoundMethodHandle.SpeciesData SPECIES_DATA = BoundMethodHandle.SpeciesData.EMPTY;
/*    */   
/*    */   public BoundMethodHandle.SpeciesData speciesData() {
/* 47 */     return SPECIES_DATA;
/*    */   }
/*    */ 
/*    */   
/*    */   BoundMethodHandle copyWith(MethodType paramMethodType, LambdaForm paramLambdaForm) {
/* 52 */     return make(paramMethodType, paramLambdaForm);
/*    */   }
/*    */ 
/*    */   
/*    */   String internalProperties() {
/* 57 */     return "\n& Class=" + getClass().getSimpleName();
/*    */   }
/*    */ 
/*    */   
/*    */   public int fieldCount() {
/* 62 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   final BoundMethodHandle copyWithExtendL(MethodType paramMethodType, LambdaForm paramLambdaForm, Object paramObject) {
/* 67 */     return BoundMethodHandle.bindSingle(paramMethodType, paramLambdaForm, paramObject);
/*    */   }
/*    */   
/*    */   final BoundMethodHandle copyWithExtendI(MethodType paramMethodType, LambdaForm paramLambdaForm, int paramInt) {
/*    */     try {
/* 72 */       return SPECIES_DATA.extendWith(LambdaForm.BasicType.I_TYPE).constructor().invokeBasic(paramMethodType, paramLambdaForm, paramInt);
/* 73 */     } catch (Throwable throwable) {
/* 74 */       throw MethodHandleStatics.uncaughtException(throwable);
/*    */     } 
/*    */   }
/*    */   
/*    */   final BoundMethodHandle copyWithExtendJ(MethodType paramMethodType, LambdaForm paramLambdaForm, long paramLong) {
/*    */     try {
/* 80 */       return SPECIES_DATA.extendWith(LambdaForm.BasicType.J_TYPE).constructor().invokeBasic(paramMethodType, paramLambdaForm, paramLong);
/* 81 */     } catch (Throwable throwable) {
/* 82 */       throw MethodHandleStatics.uncaughtException(throwable);
/*    */     } 
/*    */   }
/*    */   
/*    */   final BoundMethodHandle copyWithExtendF(MethodType paramMethodType, LambdaForm paramLambdaForm, float paramFloat) {
/*    */     try {
/* 88 */       return SPECIES_DATA.extendWith(LambdaForm.BasicType.F_TYPE).constructor().invokeBasic(paramMethodType, paramLambdaForm, paramFloat);
/* 89 */     } catch (Throwable throwable) {
/* 90 */       throw MethodHandleStatics.uncaughtException(throwable);
/*    */     } 
/*    */   }
/*    */   
/*    */   final BoundMethodHandle copyWithExtendD(MethodType paramMethodType, LambdaForm paramLambdaForm, double paramDouble) {
/*    */     try {
/* 96 */       return SPECIES_DATA.extendWith(LambdaForm.BasicType.D_TYPE).constructor().invokeBasic(paramMethodType, paramLambdaForm, paramDouble);
/* 97 */     } catch (Throwable throwable) {
/* 98 */       throw MethodHandleStatics.uncaughtException(throwable);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/invoke/SimpleMethodHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */