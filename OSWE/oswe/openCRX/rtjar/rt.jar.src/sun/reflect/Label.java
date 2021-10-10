/*    */ package sun.reflect;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ class Label
/*    */ {
/*    */   static class PatchInfo
/*    */   {
/*    */     final ClassFileAssembler asm;
/*    */     final short instrBCI;
/*    */     final short patchBCI;
/*    */     final int stackDepth;
/*    */     
/*    */     PatchInfo(ClassFileAssembler param1ClassFileAssembler, short param1Short1, short param1Short2, int param1Int) {
/* 43 */       this.asm = param1ClassFileAssembler;
/* 44 */       this.instrBCI = param1Short1;
/* 45 */       this.patchBCI = param1Short2;
/* 46 */       this.stackDepth = param1Int;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 55 */   private List<PatchInfo> patches = new ArrayList<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void add(ClassFileAssembler paramClassFileAssembler, short paramShort1, short paramShort2, int paramInt) {
/* 65 */     this.patches.add(new PatchInfo(paramClassFileAssembler, paramShort1, paramShort2, paramInt));
/*    */   }
/*    */   
/*    */   public void bind() {
/* 69 */     for (PatchInfo patchInfo : this.patches) {
/* 70 */       short s1 = patchInfo.asm.getLength();
/* 71 */       short s2 = (short)(s1 - patchInfo.instrBCI);
/* 72 */       patchInfo.asm.emitShort(patchInfo.patchBCI, s2);
/* 73 */       patchInfo.asm.setStack(patchInfo.stackDepth);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/Label.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */