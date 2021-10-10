/*     */ package javax.swing.plaf.nimbus;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.swing.JComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class State<T extends JComponent>
/*     */ {
/*  74 */   static final Map<String, StandardState> standardStates = new HashMap<>(7);
/*  75 */   static final State Enabled = new StandardState(1);
/*  76 */   static final State MouseOver = new StandardState(2);
/*  77 */   static final State Pressed = new StandardState(4);
/*  78 */   static final State Disabled = new StandardState(8);
/*  79 */   static final State Focused = new StandardState(256);
/*  80 */   static final State Selected = new StandardState(512);
/*  81 */   static final State Default = new StandardState(1024);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected State(String paramString) {
/* 101 */     this.name = paramString;
/*     */   }
/*     */   public String toString() {
/* 104 */     return this.name;
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
/*     */   boolean isInState(T paramT, int paramInt) {
/* 127 */     return isInState(paramT);
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
/*     */   protected abstract boolean isInState(T paramT);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getName() {
/* 148 */     return this.name;
/*     */   }
/*     */   static boolean isStandardStateName(String paramString) {
/* 151 */     return standardStates.containsKey(paramString);
/*     */   }
/*     */   
/*     */   static StandardState getStandardState(String paramString) {
/* 155 */     return standardStates.get(paramString);
/*     */   }
/*     */   
/*     */   static final class StandardState extends State<JComponent> {
/*     */     private int state;
/*     */     
/*     */     private StandardState(int param1Int) {
/* 162 */       super(toString(param1Int));
/* 163 */       this.state = param1Int;
/* 164 */       standardStates.put(getName(), this);
/*     */     }
/*     */     
/*     */     public int getState() {
/* 168 */       return this.state;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean isInState(JComponent param1JComponent, int param1Int) {
/* 173 */       return ((param1Int & this.state) == this.state);
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isInState(JComponent param1JComponent) {
/* 178 */       throw new AssertionError("This method should never be called");
/*     */     }
/*     */     
/*     */     private static String toString(int param1Int) {
/* 182 */       StringBuffer stringBuffer = new StringBuffer();
/* 183 */       if ((param1Int & 0x400) == 1024) {
/* 184 */         stringBuffer.append("Default");
/*     */       }
/* 186 */       if ((param1Int & 0x8) == 8) {
/* 187 */         if (stringBuffer.length() > 0) stringBuffer.append("+"); 
/* 188 */         stringBuffer.append("Disabled");
/*     */       } 
/* 190 */       if ((param1Int & 0x1) == 1) {
/* 191 */         if (stringBuffer.length() > 0) stringBuffer.append("+"); 
/* 192 */         stringBuffer.append("Enabled");
/*     */       } 
/* 194 */       if ((param1Int & 0x100) == 256) {
/* 195 */         if (stringBuffer.length() > 0) stringBuffer.append("+"); 
/* 196 */         stringBuffer.append("Focused");
/*     */       } 
/* 198 */       if ((param1Int & 0x2) == 2) {
/* 199 */         if (stringBuffer.length() > 0) stringBuffer.append("+"); 
/* 200 */         stringBuffer.append("MouseOver");
/*     */       } 
/* 202 */       if ((param1Int & 0x4) == 4) {
/* 203 */         if (stringBuffer.length() > 0) stringBuffer.append("+"); 
/* 204 */         stringBuffer.append("Pressed");
/*     */       } 
/* 206 */       if ((param1Int & 0x200) == 512) {
/* 207 */         if (stringBuffer.length() > 0) stringBuffer.append("+"); 
/* 208 */         stringBuffer.append("Selected");
/*     */       } 
/* 210 */       return stringBuffer.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/State.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */