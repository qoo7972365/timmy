/*     */ package jdk.internal.org.objectweb.asm.tree;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import jdk.internal.org.objectweb.asm.MethodVisitor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FrameNode
/*     */   extends AbstractInsnNode
/*     */ {
/*     */   public int type;
/*     */   public List<Object> local;
/*     */   public List<Object> stack;
/*     */   
/*     */   private FrameNode() {
/* 110 */     super(-1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FrameNode(int paramInt1, int paramInt2, Object[] paramArrayOfObject1, int paramInt3, Object[] paramArrayOfObject2) {
/* 139 */     super(-1);
/* 140 */     this.type = paramInt1;
/* 141 */     switch (paramInt1) {
/*     */       case -1:
/*     */       case 0:
/* 144 */         this.local = asList(paramInt2, paramArrayOfObject1);
/* 145 */         this.stack = asList(paramInt3, paramArrayOfObject2);
/*     */         break;
/*     */       case 1:
/* 148 */         this.local = asList(paramInt2, paramArrayOfObject1);
/*     */         break;
/*     */       case 2:
/* 151 */         this.local = Arrays.asList(new Object[paramInt2]);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 4:
/* 156 */         this.stack = asList(1, paramArrayOfObject2);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getType() {
/* 163 */     return 14;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void accept(MethodVisitor paramMethodVisitor) {
/* 174 */     switch (this.type) {
/*     */       case -1:
/*     */       case 0:
/* 177 */         paramMethodVisitor.visitFrame(this.type, this.local.size(), asArray(this.local), this.stack.size(), 
/* 178 */             asArray(this.stack));
/*     */         break;
/*     */       case 1:
/* 181 */         paramMethodVisitor.visitFrame(this.type, this.local.size(), asArray(this.local), 0, null);
/*     */         break;
/*     */       case 2:
/* 184 */         paramMethodVisitor.visitFrame(this.type, this.local.size(), null, 0, null);
/*     */         break;
/*     */       case 3:
/* 187 */         paramMethodVisitor.visitFrame(this.type, 0, null, 0, null);
/*     */         break;
/*     */       case 4:
/* 190 */         paramMethodVisitor.visitFrame(this.type, 0, null, 1, asArray(this.stack));
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractInsnNode clone(Map<LabelNode, LabelNode> paramMap) {
/* 197 */     FrameNode frameNode = new FrameNode();
/* 198 */     frameNode.type = this.type;
/* 199 */     if (this.local != null) {
/* 200 */       frameNode.local = new ArrayList();
/* 201 */       for (byte b = 0; b < this.local.size(); b++) {
/* 202 */         Object object = this.local.get(b);
/* 203 */         if (object instanceof LabelNode) {
/* 204 */           object = paramMap.get(object);
/*     */         }
/* 206 */         frameNode.local.add(object);
/*     */       } 
/*     */     } 
/* 209 */     if (this.stack != null) {
/* 210 */       frameNode.stack = new ArrayList();
/* 211 */       for (byte b = 0; b < this.stack.size(); b++) {
/* 212 */         Object object = this.stack.get(b);
/* 213 */         if (object instanceof LabelNode) {
/* 214 */           object = paramMap.get(object);
/*     */         }
/* 216 */         frameNode.stack.add(object);
/*     */       } 
/*     */     } 
/* 219 */     return frameNode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<Object> asList(int paramInt, Object[] paramArrayOfObject) {
/* 225 */     return Arrays.<Object>asList(paramArrayOfObject).subList(0, paramInt);
/*     */   }
/*     */   
/*     */   private static Object[] asArray(List<Object> paramList) {
/* 229 */     Object[] arrayOfObject = new Object[paramList.size()];
/* 230 */     for (byte b = 0; b < arrayOfObject.length; b++) {
/* 231 */       Object object = paramList.get(b);
/* 232 */       if (object instanceof LabelNode) {
/* 233 */         object = ((LabelNode)object).getLabel();
/*     */       }
/* 235 */       arrayOfObject[b] = object;
/*     */     } 
/* 237 */     return arrayOfObject;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/tree/FrameNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */