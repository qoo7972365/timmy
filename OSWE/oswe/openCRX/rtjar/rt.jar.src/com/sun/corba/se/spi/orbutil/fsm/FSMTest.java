/*     */ package com.sun.corba.se.spi.orbutil.fsm;
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
/*     */ public class FSMTest
/*     */ {
/* 149 */   public static final State STATE1 = new StateImpl("1");
/* 150 */   public static final State STATE2 = new StateImpl("2");
/* 151 */   public static final State STATE3 = new StateImpl("3");
/* 152 */   public static final State STATE4 = new StateImpl("4");
/*     */   
/* 154 */   public static final Input INPUT1 = new InputImpl("1");
/* 155 */   public static final Input INPUT2 = new InputImpl("2");
/* 156 */   public static final Input INPUT3 = new InputImpl("3");
/* 157 */   public static final Input INPUT4 = new InputImpl("4");
/*     */   
/* 159 */   private Guard counterGuard = new Guard()
/*     */     {
/*     */       public Guard.Result evaluate(FSM param1FSM, Input param1Input) {
/* 162 */         MyFSM myFSM = (MyFSM)param1FSM;
/* 163 */         return Guard.Result.convert((myFSM.counter < 3));
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private static void add1(StateEngine paramStateEngine, State paramState1, Input paramInput, State paramState2) {
/* 169 */     paramStateEngine.add(paramState1, paramInput, new TestAction1(paramState1, paramInput, paramState2), paramState2);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void add2(StateEngine paramStateEngine, State paramState1, State paramState2) {
/* 174 */     paramStateEngine.setDefault(paramState1, new TestAction2(paramState1, paramState2), paramState2);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] paramArrayOfString) {
/* 179 */     TestAction3 testAction3 = new TestAction3(STATE3, INPUT1);
/*     */     
/* 181 */     StateEngine stateEngine = StateEngineFactory.create();
/* 182 */     add1(stateEngine, STATE1, INPUT1, STATE1);
/* 183 */     add2(stateEngine, STATE1, STATE2);
/*     */     
/* 185 */     add1(stateEngine, STATE2, INPUT1, STATE2);
/* 186 */     add1(stateEngine, STATE2, INPUT2, STATE2);
/* 187 */     add1(stateEngine, STATE2, INPUT3, STATE1);
/* 188 */     add1(stateEngine, STATE2, INPUT4, STATE3);
/*     */     
/* 190 */     stateEngine.add(STATE3, INPUT1, testAction3, STATE3);
/* 191 */     stateEngine.add(STATE3, INPUT1, testAction3, STATE4);
/* 192 */     add1(stateEngine, STATE3, INPUT2, STATE1);
/* 193 */     add1(stateEngine, STATE3, INPUT3, STATE2);
/* 194 */     add1(stateEngine, STATE3, INPUT4, STATE2);
/*     */     
/* 196 */     MyFSM myFSM = new MyFSM(stateEngine);
/* 197 */     TestInput testInput1 = new TestInput(INPUT1, "1.1");
/* 198 */     TestInput testInput2 = new TestInput(INPUT1, "1.2");
/* 199 */     TestInput testInput3 = new TestInput(INPUT2, "2.1");
/* 200 */     TestInput testInput4 = new TestInput(INPUT2, "2.2");
/* 201 */     TestInput testInput5 = new TestInput(INPUT3, "3.1");
/* 202 */     TestInput testInput6 = new TestInput(INPUT3, "3.2");
/* 203 */     TestInput testInput7 = new TestInput(INPUT3, "3.3");
/* 204 */     TestInput testInput8 = new TestInput(INPUT4, "4.1");
/*     */     
/* 206 */     myFSM.doIt(testInput1.getInput());
/* 207 */     myFSM.doIt(testInput2.getInput());
/* 208 */     myFSM.doIt(testInput8.getInput());
/* 209 */     myFSM.doIt(testInput1.getInput());
/* 210 */     myFSM.doIt(testInput4.getInput());
/* 211 */     myFSM.doIt(testInput5.getInput());
/* 212 */     myFSM.doIt(testInput7.getInput());
/* 213 */     myFSM.doIt(testInput8.getInput());
/* 214 */     myFSM.doIt(testInput8.getInput());
/* 215 */     myFSM.doIt(testInput8.getInput());
/* 216 */     myFSM.doIt(testInput4.getInput());
/* 217 */     myFSM.doIt(testInput6.getInput());
/* 218 */     myFSM.doIt(testInput8.getInput());
/* 219 */     myFSM.doIt(testInput1.getInput());
/* 220 */     myFSM.doIt(testInput2.getInput());
/* 221 */     myFSM.doIt(testInput1.getInput());
/* 222 */     myFSM.doIt(testInput1.getInput());
/* 223 */     myFSM.doIt(testInput1.getInput());
/* 224 */     myFSM.doIt(testInput1.getInput());
/* 225 */     myFSM.doIt(testInput1.getInput());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/orbutil/fsm/FSMTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */