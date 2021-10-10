/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Spring
/*     */ {
/*     */   public static final int UNSET = -2147483648;
/*     */   
/*     */   public abstract int getMinimumValue();
/*     */   
/*     */   public abstract int getPreferredValue();
/*     */   
/*     */   public abstract int getMaximumValue();
/*     */   
/*     */   public abstract int getValue();
/*     */   
/*     */   public abstract void setValue(int paramInt);
/*     */   
/*     */   private double range(boolean paramBoolean) {
/* 189 */     return paramBoolean ? (getPreferredValue() - getMinimumValue()) : (
/* 190 */       getMaximumValue() - getPreferredValue());
/*     */   }
/*     */   
/*     */   double getStrain() {
/* 194 */     double d = (getValue() - getPreferredValue());
/* 195 */     return d / range((getValue() < getPreferredValue()));
/*     */   }
/*     */   
/*     */   void setStrain(double paramDouble) {
/* 199 */     setValue(getPreferredValue() + (int)(paramDouble * range((paramDouble < 0.0D))));
/*     */   }
/*     */   
/*     */   boolean isCyclic(SpringLayout paramSpringLayout) {
/* 203 */     return false;
/*     */   }
/*     */   
/*     */   static abstract class AbstractSpring extends Spring {
/* 207 */     protected int size = Integer.MIN_VALUE;
/*     */     
/*     */     public int getValue() {
/* 210 */       return (this.size != Integer.MIN_VALUE) ? this.size : getPreferredValue();
/*     */     }
/*     */     
/*     */     public final void setValue(int param1Int) {
/* 214 */       if (this.size == param1Int) {
/*     */         return;
/*     */       }
/* 217 */       if (param1Int == Integer.MIN_VALUE) {
/* 218 */         clear();
/*     */       } else {
/* 220 */         setNonClearValue(param1Int);
/*     */       } 
/*     */     }
/*     */     
/*     */     protected void clear() {
/* 225 */       this.size = Integer.MIN_VALUE;
/*     */     }
/*     */     
/*     */     protected void setNonClearValue(int param1Int) {
/* 229 */       this.size = param1Int;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class StaticSpring extends AbstractSpring {
/*     */     protected int min;
/*     */     protected int pref;
/*     */     protected int max;
/*     */     
/*     */     public StaticSpring(int param1Int) {
/* 239 */       this(param1Int, param1Int, param1Int);
/*     */     }
/*     */     
/*     */     public StaticSpring(int param1Int1, int param1Int2, int param1Int3) {
/* 243 */       this.min = param1Int1;
/* 244 */       this.pref = param1Int2;
/* 245 */       this.max = param1Int3;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 249 */       return "StaticSpring [" + this.min + ", " + this.pref + ", " + this.max + "]";
/*     */     }
/*     */     
/*     */     public int getMinimumValue() {
/* 253 */       return this.min;
/*     */     }
/*     */     
/*     */     public int getPreferredValue() {
/* 257 */       return this.pref;
/*     */     }
/*     */     
/*     */     public int getMaximumValue() {
/* 261 */       return this.max;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class NegativeSpring extends Spring {
/*     */     private Spring s;
/*     */     
/*     */     public NegativeSpring(Spring param1Spring) {
/* 269 */       this.s = param1Spring;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getMinimumValue() {
/* 276 */       return -this.s.getMaximumValue();
/*     */     }
/*     */     
/*     */     public int getPreferredValue() {
/* 280 */       return -this.s.getPreferredValue();
/*     */     }
/*     */     
/*     */     public int getMaximumValue() {
/* 284 */       return -this.s.getMinimumValue();
/*     */     }
/*     */     
/*     */     public int getValue() {
/* 288 */       return -this.s.getValue();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void setValue(int param1Int) {
/* 294 */       this.s.setValue(-param1Int);
/*     */     }
/*     */     
/*     */     boolean isCyclic(SpringLayout param1SpringLayout) {
/* 298 */       return this.s.isCyclic(param1SpringLayout);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ScaleSpring extends Spring {
/*     */     private Spring s;
/*     */     private float factor;
/*     */     
/*     */     private ScaleSpring(Spring param1Spring, float param1Float) {
/* 307 */       this.s = param1Spring;
/* 308 */       this.factor = param1Float;
/*     */     }
/*     */     
/*     */     public int getMinimumValue() {
/* 312 */       return Math.round(((this.factor < 0.0F) ? this.s.getMaximumValue() : this.s.getMinimumValue()) * this.factor);
/*     */     }
/*     */     
/*     */     public int getPreferredValue() {
/* 316 */       return Math.round(this.s.getPreferredValue() * this.factor);
/*     */     }
/*     */     
/*     */     public int getMaximumValue() {
/* 320 */       return Math.round(((this.factor < 0.0F) ? this.s.getMinimumValue() : this.s.getMaximumValue()) * this.factor);
/*     */     }
/*     */     
/*     */     public int getValue() {
/* 324 */       return Math.round(this.s.getValue() * this.factor);
/*     */     }
/*     */     
/*     */     public void setValue(int param1Int) {
/* 328 */       if (param1Int == Integer.MIN_VALUE) {
/* 329 */         this.s.setValue(-2147483648);
/*     */       } else {
/* 331 */         this.s.setValue(Math.round(param1Int / this.factor));
/*     */       } 
/*     */     }
/*     */     
/*     */     boolean isCyclic(SpringLayout param1SpringLayout) {
/* 336 */       return this.s.isCyclic(param1SpringLayout);
/*     */     }
/*     */   }
/*     */   
/*     */   static class WidthSpring extends AbstractSpring {
/*     */     Component c;
/*     */     
/*     */     public WidthSpring(Component param1Component) {
/* 344 */       this.c = param1Component;
/*     */     }
/*     */     
/*     */     public int getMinimumValue() {
/* 348 */       return (this.c.getMinimumSize()).width;
/*     */     }
/*     */     
/*     */     public int getPreferredValue() {
/* 352 */       return (this.c.getPreferredSize()).width;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getMaximumValue() {
/* 359 */       return Math.min(32767, (this.c.getMaximumSize()).width);
/*     */     }
/*     */   }
/*     */   
/*     */   static class HeightSpring extends AbstractSpring {
/*     */     Component c;
/*     */     
/*     */     public HeightSpring(Component param1Component) {
/* 367 */       this.c = param1Component;
/*     */     }
/*     */     
/*     */     public int getMinimumValue() {
/* 371 */       return (this.c.getMinimumSize()).height;
/*     */     }
/*     */     
/*     */     public int getPreferredValue() {
/* 375 */       return (this.c.getPreferredSize()).height;
/*     */     }
/*     */     
/*     */     public int getMaximumValue() {
/* 379 */       return Math.min(32767, (this.c.getMaximumSize()).height);
/*     */     }
/*     */   }
/*     */   
/*     */   static abstract class SpringMap extends Spring {
/*     */     private Spring s;
/*     */     
/*     */     public SpringMap(Spring param1Spring) {
/* 387 */       this.s = param1Spring;
/*     */     }
/*     */     
/*     */     protected abstract int map(int param1Int);
/*     */     
/*     */     protected abstract int inv(int param1Int);
/*     */     
/*     */     public int getMinimumValue() {
/* 395 */       return map(this.s.getMinimumValue());
/*     */     }
/*     */     
/*     */     public int getPreferredValue() {
/* 399 */       return map(this.s.getPreferredValue());
/*     */     }
/*     */     
/*     */     public int getMaximumValue() {
/* 403 */       return Math.min(32767, map(this.s.getMaximumValue()));
/*     */     }
/*     */     
/*     */     public int getValue() {
/* 407 */       return map(this.s.getValue());
/*     */     }
/*     */     
/*     */     public void setValue(int param1Int) {
/* 411 */       if (param1Int == Integer.MIN_VALUE) {
/* 412 */         this.s.setValue(-2147483648);
/*     */       } else {
/* 414 */         this.s.setValue(inv(param1Int));
/*     */       } 
/*     */     }
/*     */     
/*     */     boolean isCyclic(SpringLayout param1SpringLayout) {
/* 419 */       return this.s.isCyclic(param1SpringLayout);
/*     */     }
/*     */   }
/*     */   
/*     */   static abstract class CompoundSpring
/*     */     extends StaticSpring
/*     */   {
/*     */     protected Spring s1;
/*     */     protected Spring s2;
/*     */     
/*     */     public CompoundSpring(Spring param1Spring1, Spring param1Spring2) {
/* 430 */       super(-2147483648);
/* 431 */       this.s1 = param1Spring1;
/* 432 */       this.s2 = param1Spring2;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 436 */       return "CompoundSpring of " + this.s1 + " and " + this.s2;
/*     */     }
/*     */     
/*     */     protected void clear() {
/* 440 */       super.clear();
/* 441 */       this.min = this.pref = this.max = Integer.MIN_VALUE;
/* 442 */       this.s1.setValue(-2147483648);
/* 443 */       this.s2.setValue(-2147483648);
/*     */     }
/*     */     
/*     */     protected abstract int op(int param1Int1, int param1Int2);
/*     */     
/*     */     public int getMinimumValue() {
/* 449 */       if (this.min == Integer.MIN_VALUE) {
/* 450 */         this.min = op(this.s1.getMinimumValue(), this.s2.getMinimumValue());
/*     */       }
/* 452 */       return this.min;
/*     */     }
/*     */     
/*     */     public int getPreferredValue() {
/* 456 */       if (this.pref == Integer.MIN_VALUE) {
/* 457 */         this.pref = op(this.s1.getPreferredValue(), this.s2.getPreferredValue());
/*     */       }
/* 459 */       return this.pref;
/*     */     }
/*     */     
/*     */     public int getMaximumValue() {
/* 463 */       if (this.max == Integer.MIN_VALUE) {
/* 464 */         this.max = op(this.s1.getMaximumValue(), this.s2.getMaximumValue());
/*     */       }
/* 466 */       return this.max;
/*     */     }
/*     */     
/*     */     public int getValue() {
/* 470 */       if (this.size == Integer.MIN_VALUE) {
/* 471 */         this.size = op(this.s1.getValue(), this.s2.getValue());
/*     */       }
/* 473 */       return this.size;
/*     */     }
/*     */     
/*     */     boolean isCyclic(SpringLayout param1SpringLayout) {
/* 477 */       return (param1SpringLayout.isCyclic(this.s1) || param1SpringLayout.isCyclic(this.s2));
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SumSpring extends CompoundSpring {
/*     */     public SumSpring(Spring param1Spring1, Spring param1Spring2) {
/* 483 */       super(param1Spring1, param1Spring2);
/*     */     }
/*     */     
/*     */     protected int op(int param1Int1, int param1Int2) {
/* 487 */       return param1Int1 + param1Int2;
/*     */     }
/*     */     
/*     */     protected void setNonClearValue(int param1Int) {
/* 491 */       super.setNonClearValue(param1Int);
/* 492 */       this.s1.setStrain(getStrain());
/* 493 */       this.s2.setValue(param1Int - this.s1.getValue());
/*     */     }
/*     */   }
/*     */   
/*     */   private static class MaxSpring
/*     */     extends CompoundSpring {
/*     */     public MaxSpring(Spring param1Spring1, Spring param1Spring2) {
/* 500 */       super(param1Spring1, param1Spring2);
/*     */     }
/*     */     
/*     */     protected int op(int param1Int1, int param1Int2) {
/* 504 */       return Math.max(param1Int1, param1Int2);
/*     */     }
/*     */     
/*     */     protected void setNonClearValue(int param1Int) {
/* 508 */       super.setNonClearValue(param1Int);
/* 509 */       this.s1.setValue(param1Int);
/* 510 */       this.s2.setValue(param1Int);
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
/*     */   public static Spring constant(int paramInt) {
/* 526 */     return constant(paramInt, paramInt, paramInt);
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
/*     */   public static Spring constant(int paramInt1, int paramInt2, int paramInt3) {
/* 544 */     return new StaticSpring(paramInt1, paramInt2, paramInt3);
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
/*     */   public static Spring minus(Spring paramSpring) {
/* 556 */     return new NegativeSpring(paramSpring);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Spring sum(Spring paramSpring1, Spring paramSpring2) {
/* 589 */     return new SumSpring(paramSpring1, paramSpring2);
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
/*     */   public static Spring max(Spring paramSpring1, Spring paramSpring2) {
/* 601 */     return new MaxSpring(paramSpring1, paramSpring2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Spring difference(Spring paramSpring1, Spring paramSpring2) {
/* 608 */     return sum(paramSpring1, minus(paramSpring2));
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
/*     */   public static Spring scale(Spring paramSpring, float paramFloat) {
/* 636 */     checkArg(paramSpring);
/* 637 */     return new ScaleSpring(paramSpring, paramFloat);
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
/*     */   public static Spring width(Component paramComponent) {
/* 657 */     checkArg(paramComponent);
/* 658 */     return new WidthSpring(paramComponent);
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
/*     */   public static Spring height(Component paramComponent) {
/* 678 */     checkArg(paramComponent);
/* 679 */     return new HeightSpring(paramComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkArg(Object paramObject) {
/* 687 */     if (paramObject == null)
/* 688 */       throw new NullPointerException("Argument must not be null"); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/Spring.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */