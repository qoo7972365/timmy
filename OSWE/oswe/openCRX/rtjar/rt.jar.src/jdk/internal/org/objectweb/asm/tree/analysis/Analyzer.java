/*     */ package jdk.internal.org.objectweb.asm.tree.analysis;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import jdk.internal.org.objectweb.asm.Opcodes;
/*     */ import jdk.internal.org.objectweb.asm.Type;
/*     */ import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.IincInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.InsnList;
/*     */ import jdk.internal.org.objectweb.asm.tree.JumpInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.LabelNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.LookupSwitchInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.MethodNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.TableSwitchInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.VarInsnNode;
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
/*     */ public class Analyzer<V extends Value>
/*     */   implements Opcodes
/*     */ {
/*     */   private final Interpreter<V> interpreter;
/*     */   private int n;
/*     */   private InsnList insns;
/*     */   private List<TryCatchBlockNode>[] handlers;
/*     */   private Frame<V>[] frames;
/*     */   private Subroutine[] subroutines;
/*     */   private boolean[] queued;
/*     */   private int[] queue;
/*     */   private int top;
/*     */   
/*     */   public Analyzer(Interpreter<V> paramInterpreter) {
/* 116 */     this.interpreter = paramInterpreter;
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
/*     */   public Frame<V>[] analyze(String paramString, MethodNode paramMethodNode) throws AnalyzerException {
/* 137 */     if ((paramMethodNode.access & 0x500) != 0) {
/* 138 */       this.frames = (Frame<V>[])new Frame[0];
/* 139 */       return this.frames;
/*     */     } 
/* 141 */     this.n = paramMethodNode.instructions.size();
/* 142 */     this.insns = paramMethodNode.instructions;
/* 143 */     this.handlers = (List<TryCatchBlockNode>[])new List[this.n];
/* 144 */     this.frames = (Frame<V>[])new Frame[this.n];
/* 145 */     this.subroutines = new Subroutine[this.n];
/* 146 */     this.queued = new boolean[this.n];
/* 147 */     this.queue = new int[this.n];
/* 148 */     this.top = 0;
/*     */ 
/*     */     
/* 151 */     for (byte b1 = 0; b1 < paramMethodNode.tryCatchBlocks.size(); b1++) {
/* 152 */       TryCatchBlockNode tryCatchBlockNode = paramMethodNode.tryCatchBlocks.get(b1);
/* 153 */       int j = this.insns.indexOf(tryCatchBlockNode.start);
/* 154 */       int k = this.insns.indexOf(tryCatchBlockNode.end);
/* 155 */       for (int m = j; m < k; m++) {
/* 156 */         List<TryCatchBlockNode> list = this.handlers[m];
/* 157 */         if (list == null) {
/* 158 */           list = new ArrayList<>();
/* 159 */           this.handlers[m] = list;
/*     */         } 
/* 161 */         list.add(tryCatchBlockNode);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 166 */     Subroutine subroutine = new Subroutine(null, paramMethodNode.maxLocals, null);
/* 167 */     ArrayList<AbstractInsnNode> arrayList = new ArrayList();
/* 168 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 169 */     findSubroutine(0, subroutine, arrayList);
/* 170 */     while (!arrayList.isEmpty()) {
/* 171 */       JumpInsnNode jumpInsnNode = (JumpInsnNode)arrayList.remove(0);
/* 172 */       Subroutine subroutine1 = (Subroutine)hashMap.get(jumpInsnNode.label);
/* 173 */       if (subroutine1 == null) {
/* 174 */         subroutine1 = new Subroutine(jumpInsnNode.label, paramMethodNode.maxLocals, jumpInsnNode);
/* 175 */         hashMap.put(jumpInsnNode.label, subroutine1);
/* 176 */         findSubroutine(this.insns.indexOf(jumpInsnNode.label), subroutine1, arrayList); continue;
/*     */       } 
/* 178 */       subroutine1.callers.add(jumpInsnNode);
/*     */     } 
/*     */     
/* 181 */     for (byte b2 = 0; b2 < this.n; b2++) {
/* 182 */       if (this.subroutines[b2] != null && (this.subroutines[b2]).start == null) {
/* 183 */         this.subroutines[b2] = null;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 188 */     Frame<V> frame1 = newFrame(paramMethodNode.maxLocals, paramMethodNode.maxStack);
/* 189 */     Frame<V> frame2 = newFrame(paramMethodNode.maxLocals, paramMethodNode.maxStack);
/* 190 */     frame1.setReturn(this.interpreter.newValue(Type.getReturnType(paramMethodNode.desc)));
/* 191 */     Type[] arrayOfType = Type.getArgumentTypes(paramMethodNode.desc);
/* 192 */     byte b3 = 0;
/* 193 */     if ((paramMethodNode.access & 0x8) == 0) {
/* 194 */       Type type = Type.getObjectType(paramString);
/* 195 */       frame1.setLocal(b3++, this.interpreter.newValue(type));
/*     */     }  int i;
/* 197 */     for (i = 0; i < arrayOfType.length; i++) {
/* 198 */       frame1.setLocal(b3++, this.interpreter.newValue(arrayOfType[i]));
/* 199 */       if (arrayOfType[i].getSize() == 2) {
/* 200 */         frame1.setLocal(b3++, this.interpreter.newValue(null));
/*     */       }
/*     */     } 
/* 203 */     while (b3 < paramMethodNode.maxLocals) {
/* 204 */       frame1.setLocal(b3++, this.interpreter.newValue(null));
/*     */     }
/* 206 */     merge(0, frame1, null);
/*     */     
/* 208 */     init(paramString, paramMethodNode);
/*     */ 
/*     */     
/* 211 */     while (this.top > 0) {
/* 212 */       i = this.queue[--this.top];
/* 213 */       Frame<V> frame = this.frames[i];
/* 214 */       Subroutine subroutine1 = this.subroutines[i];
/* 215 */       this.queued[i] = false;
/*     */       
/* 217 */       AbstractInsnNode abstractInsnNode = null;
/*     */       try {
/* 219 */         abstractInsnNode = paramMethodNode.instructions.get(i);
/* 220 */         int j = abstractInsnNode.getOpcode();
/* 221 */         int k = abstractInsnNode.getType();
/*     */         
/* 223 */         if (k == 8 || k == 15 || k == 14) {
/*     */ 
/*     */           
/* 226 */           merge(i + 1, frame, subroutine1);
/* 227 */           newControlFlowEdge(i, i + 1);
/*     */         } else {
/* 229 */           frame1.init(frame).execute(abstractInsnNode, this.interpreter);
/* 230 */           subroutine1 = (subroutine1 == null) ? null : subroutine1.copy();
/*     */           
/* 232 */           if (abstractInsnNode instanceof JumpInsnNode) {
/* 233 */             JumpInsnNode jumpInsnNode = (JumpInsnNode)abstractInsnNode;
/* 234 */             if (j != 167 && j != 168) {
/* 235 */               merge(i + 1, frame1, subroutine1);
/* 236 */               newControlFlowEdge(i, i + 1);
/*     */             } 
/* 238 */             int m = this.insns.indexOf(jumpInsnNode.label);
/* 239 */             if (j == 168) {
/* 240 */               merge(m, frame1, new Subroutine(jumpInsnNode.label, paramMethodNode.maxLocals, jumpInsnNode));
/*     */             } else {
/*     */               
/* 243 */               merge(m, frame1, subroutine1);
/*     */             } 
/* 245 */             newControlFlowEdge(i, m);
/* 246 */           } else if (abstractInsnNode instanceof LookupSwitchInsnNode) {
/* 247 */             LookupSwitchInsnNode lookupSwitchInsnNode = (LookupSwitchInsnNode)abstractInsnNode;
/* 248 */             int m = this.insns.indexOf(lookupSwitchInsnNode.dflt);
/* 249 */             merge(m, frame1, subroutine1);
/* 250 */             newControlFlowEdge(i, m);
/* 251 */             for (byte b = 0; b < lookupSwitchInsnNode.labels.size(); b++) {
/* 252 */               LabelNode labelNode = lookupSwitchInsnNode.labels.get(b);
/* 253 */               m = this.insns.indexOf(labelNode);
/* 254 */               merge(m, frame1, subroutine1);
/* 255 */               newControlFlowEdge(i, m);
/*     */             } 
/* 257 */           } else if (abstractInsnNode instanceof TableSwitchInsnNode) {
/* 258 */             TableSwitchInsnNode tableSwitchInsnNode = (TableSwitchInsnNode)abstractInsnNode;
/* 259 */             int m = this.insns.indexOf(tableSwitchInsnNode.dflt);
/* 260 */             merge(m, frame1, subroutine1);
/* 261 */             newControlFlowEdge(i, m);
/* 262 */             for (byte b = 0; b < tableSwitchInsnNode.labels.size(); b++) {
/* 263 */               LabelNode labelNode = tableSwitchInsnNode.labels.get(b);
/* 264 */               m = this.insns.indexOf(labelNode);
/* 265 */               merge(m, frame1, subroutine1);
/* 266 */               newControlFlowEdge(i, m);
/*     */             } 
/* 268 */           } else if (j == 169) {
/* 269 */             if (subroutine1 == null) {
/* 270 */               throw new AnalyzerException(abstractInsnNode, "RET instruction outside of a sub routine");
/*     */             }
/*     */             
/* 273 */             for (byte b = 0; b < subroutine1.callers.size(); b++) {
/* 274 */               JumpInsnNode jumpInsnNode = subroutine1.callers.get(b);
/* 275 */               int m = this.insns.indexOf(jumpInsnNode);
/* 276 */               if (this.frames[m] != null) {
/* 277 */                 merge(m + 1, this.frames[m], frame1, this.subroutines[m], subroutine1.access);
/*     */                 
/* 279 */                 newControlFlowEdge(i, m + 1);
/*     */               } 
/*     */             } 
/* 282 */           } else if (j != 191 && (j < 172 || j > 177)) {
/*     */             
/* 284 */             if (subroutine1 != null) {
/* 285 */               if (abstractInsnNode instanceof VarInsnNode) {
/* 286 */                 int m = ((VarInsnNode)abstractInsnNode).var;
/* 287 */                 subroutine1.access[m] = true;
/* 288 */                 if (j == 22 || j == 24 || j == 55 || j == 57)
/*     */                 {
/*     */                   
/* 291 */                   subroutine1.access[m + 1] = true;
/*     */                 }
/* 293 */               } else if (abstractInsnNode instanceof IincInsnNode) {
/* 294 */                 int m = ((IincInsnNode)abstractInsnNode).var;
/* 295 */                 subroutine1.access[m] = true;
/*     */               } 
/*     */             }
/* 298 */             merge(i + 1, frame1, subroutine1);
/* 299 */             newControlFlowEdge(i, i + 1);
/*     */           } 
/*     */         } 
/*     */         
/* 303 */         List<TryCatchBlockNode> list = this.handlers[i];
/* 304 */         if (list != null) {
/* 305 */           for (byte b = 0; b < list.size(); b++) {
/* 306 */             Type type; TryCatchBlockNode tryCatchBlockNode = list.get(b);
/*     */             
/* 308 */             if (tryCatchBlockNode.type == null) {
/* 309 */               type = Type.getObjectType("java/lang/Throwable");
/*     */             } else {
/* 311 */               type = Type.getObjectType(tryCatchBlockNode.type);
/*     */             } 
/* 313 */             int m = this.insns.indexOf(tryCatchBlockNode.handler);
/* 314 */             if (newControlFlowExceptionEdge(i, tryCatchBlockNode)) {
/* 315 */               frame2.init(frame);
/* 316 */               frame2.clearStack();
/* 317 */               frame2.push(this.interpreter.newValue(type));
/* 318 */               merge(m, frame2, subroutine1);
/*     */             } 
/*     */           } 
/*     */         }
/* 322 */       } catch (AnalyzerException analyzerException) {
/* 323 */         throw new AnalyzerException(analyzerException.node, "Error at instruction " + i + ": " + analyzerException
/* 324 */             .getMessage(), analyzerException);
/* 325 */       } catch (Exception exception) {
/* 326 */         throw new AnalyzerException(abstractInsnNode, "Error at instruction " + i + ": " + exception
/* 327 */             .getMessage(), exception);
/*     */       } 
/*     */     } 
/*     */     
/* 331 */     return this.frames;
/*     */   }
/*     */ 
/*     */   
/*     */   private void findSubroutine(int paramInt, Subroutine paramSubroutine, List<AbstractInsnNode> paramList) throws AnalyzerException {
/*     */     while (true) {
/* 337 */       if (paramInt < 0 || paramInt >= this.n) {
/* 338 */         throw new AnalyzerException(null, "Execution can fall off end of the code");
/*     */       }
/*     */       
/* 341 */       if (this.subroutines[paramInt] != null) {
/*     */         return;
/*     */       }
/* 344 */       this.subroutines[paramInt] = paramSubroutine.copy();
/* 345 */       AbstractInsnNode abstractInsnNode = this.insns.get(paramInt);
/*     */ 
/*     */       
/* 348 */       if (abstractInsnNode instanceof JumpInsnNode) {
/* 349 */         if (abstractInsnNode.getOpcode() == 168) {
/*     */           
/* 351 */           paramList.add(abstractInsnNode);
/*     */         } else {
/* 353 */           JumpInsnNode jumpInsnNode = (JumpInsnNode)abstractInsnNode;
/* 354 */           findSubroutine(this.insns.indexOf(jumpInsnNode.label), paramSubroutine, paramList);
/*     */         } 
/* 356 */       } else if (abstractInsnNode instanceof TableSwitchInsnNode) {
/* 357 */         TableSwitchInsnNode tableSwitchInsnNode = (TableSwitchInsnNode)abstractInsnNode;
/* 358 */         findSubroutine(this.insns.indexOf(tableSwitchInsnNode.dflt), paramSubroutine, paramList);
/* 359 */         for (int i = tableSwitchInsnNode.labels.size() - 1; i >= 0; i--) {
/* 360 */           LabelNode labelNode = tableSwitchInsnNode.labels.get(i);
/* 361 */           findSubroutine(this.insns.indexOf(labelNode), paramSubroutine, paramList);
/*     */         } 
/* 363 */       } else if (abstractInsnNode instanceof LookupSwitchInsnNode) {
/* 364 */         LookupSwitchInsnNode lookupSwitchInsnNode = (LookupSwitchInsnNode)abstractInsnNode;
/* 365 */         findSubroutine(this.insns.indexOf(lookupSwitchInsnNode.dflt), paramSubroutine, paramList);
/* 366 */         for (int i = lookupSwitchInsnNode.labels.size() - 1; i >= 0; i--) {
/* 367 */           LabelNode labelNode = lookupSwitchInsnNode.labels.get(i);
/* 368 */           findSubroutine(this.insns.indexOf(labelNode), paramSubroutine, paramList);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 373 */       List<TryCatchBlockNode> list = this.handlers[paramInt];
/* 374 */       if (list != null) {
/* 375 */         for (byte b = 0; b < list.size(); b++) {
/* 376 */           TryCatchBlockNode tryCatchBlockNode = list.get(b);
/* 377 */           findSubroutine(this.insns.indexOf(tryCatchBlockNode.handler), paramSubroutine, paramList);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 382 */       switch (abstractInsnNode.getOpcode()) {
/*     */         case 167:
/*     */         case 169:
/*     */         case 170:
/*     */         case 171:
/*     */         case 172:
/*     */         case 173:
/*     */         case 174:
/*     */         case 175:
/*     */         case 176:
/*     */         case 177:
/*     */         case 191:
/*     */           return;
/*     */       } 
/* 396 */       paramInt++;
/*     */     } 
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
/*     */   public Frame<V>[] getFrames() {
/* 412 */     return this.frames;
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
/*     */   public List<TryCatchBlockNode> getHandlers(int paramInt) {
/* 424 */     return this.handlers[paramInt];
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
/*     */   protected void init(String paramString, MethodNode paramMethodNode) throws AnalyzerException {}
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
/*     */   protected Frame<V> newFrame(int paramInt1, int paramInt2) {
/* 452 */     return new Frame<>(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Frame<V> newFrame(Frame<? extends V> paramFrame) {
/* 463 */     return new Frame<>(paramFrame);
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
/*     */   protected void newControlFlowEdge(int paramInt1, int paramInt2) {}
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
/*     */   protected boolean newControlFlowExceptionEdge(int paramInt1, int paramInt2) {
/* 497 */     return true;
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
/*     */   protected boolean newControlFlowExceptionEdge(int paramInt, TryCatchBlockNode paramTryCatchBlockNode) {
/* 521 */     return newControlFlowExceptionEdge(paramInt, this.insns.indexOf(paramTryCatchBlockNode.handler));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void merge(int paramInt, Frame<V> paramFrame, Subroutine paramSubroutine) throws AnalyzerException {
/*     */     boolean bool;
/* 528 */     Frame<V> frame = this.frames[paramInt];
/* 529 */     Subroutine subroutine = this.subroutines[paramInt];
/*     */ 
/*     */     
/* 532 */     if (frame == null) {
/* 533 */       this.frames[paramInt] = newFrame(paramFrame);
/* 534 */       bool = true;
/*     */     } else {
/* 536 */       bool = frame.merge(paramFrame, this.interpreter);
/*     */     } 
/*     */     
/* 539 */     if (subroutine == null) {
/* 540 */       if (paramSubroutine != null) {
/* 541 */         this.subroutines[paramInt] = paramSubroutine.copy();
/* 542 */         bool = true;
/*     */       }
/*     */     
/* 545 */     } else if (paramSubroutine != null) {
/* 546 */       bool |= subroutine.merge(paramSubroutine);
/*     */     } 
/*     */     
/* 549 */     if (bool && !this.queued[paramInt]) {
/* 550 */       this.queued[paramInt] = true;
/* 551 */       this.queue[this.top++] = paramInt;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void merge(int paramInt, Frame<V> paramFrame1, Frame<V> paramFrame2, Subroutine paramSubroutine, boolean[] paramArrayOfboolean) throws AnalyzerException {
/*     */     boolean bool;
/* 558 */     Frame<V> frame = this.frames[paramInt];
/* 559 */     Subroutine subroutine = this.subroutines[paramInt];
/*     */ 
/*     */     
/* 562 */     paramFrame2.merge(paramFrame1, paramArrayOfboolean);
/*     */     
/* 564 */     if (frame == null) {
/* 565 */       this.frames[paramInt] = newFrame(paramFrame2);
/* 566 */       bool = true;
/*     */     } else {
/* 568 */       bool = frame.merge(paramFrame2, this.interpreter);
/*     */     } 
/*     */     
/* 571 */     if (subroutine != null && paramSubroutine != null) {
/* 572 */       bool |= subroutine.merge(paramSubroutine);
/*     */     }
/* 574 */     if (bool && !this.queued[paramInt]) {
/* 575 */       this.queued[paramInt] = true;
/* 576 */       this.queue[this.top++] = paramInt;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/tree/analysis/Analyzer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */