/*     */ package javax.accessibility;
/*     */ 
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AccessibleStateSet
/*     */ {
/*  54 */   protected Vector<AccessibleState> states = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AccessibleStateSet() {
/*  60 */     this.states = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AccessibleStateSet(AccessibleState[] paramArrayOfAccessibleState) {
/*  70 */     if (paramArrayOfAccessibleState.length != 0) {
/*  71 */       this.states = new Vector<>(paramArrayOfAccessibleState.length);
/*  72 */       for (byte b = 0; b < paramArrayOfAccessibleState.length; b++) {
/*  73 */         if (!this.states.contains(paramArrayOfAccessibleState[b])) {
/*  74 */           this.states.addElement(paramArrayOfAccessibleState[b]);
/*     */         }
/*     */       } 
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
/*     */ 
/*     */   
/*     */   public boolean add(AccessibleState paramAccessibleState) {
/*  94 */     if (this.states == null) {
/*  95 */       this.states = new Vector<>();
/*     */     }
/*     */     
/*  98 */     if (!this.states.contains(paramAccessibleState)) {
/*  99 */       this.states.addElement(paramAccessibleState);
/* 100 */       return true;
/*     */     } 
/* 102 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAll(AccessibleState[] paramArrayOfAccessibleState) {
/* 112 */     if (paramArrayOfAccessibleState.length != 0) {
/* 113 */       if (this.states == null) {
/* 114 */         this.states = new Vector<>(paramArrayOfAccessibleState.length);
/*     */       }
/* 116 */       for (byte b = 0; b < paramArrayOfAccessibleState.length; b++) {
/* 117 */         if (!this.states.contains(paramArrayOfAccessibleState[b])) {
/* 118 */           this.states.addElement(paramArrayOfAccessibleState[b]);
/*     */         }
/*     */       } 
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
/*     */   public boolean remove(AccessibleState paramAccessibleState) {
/* 135 */     if (this.states == null) {
/* 136 */       return false;
/*     */     }
/* 138 */     return this.states.removeElement(paramAccessibleState);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 146 */     if (this.states != null) {
/* 147 */       this.states.removeAllElements();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(AccessibleState paramAccessibleState) {
/* 157 */     if (this.states == null) {
/* 158 */       return false;
/*     */     }
/* 160 */     return this.states.contains(paramAccessibleState);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AccessibleState[] toArray() {
/* 169 */     if (this.states == null) {
/* 170 */       return new AccessibleState[0];
/*     */     }
/* 172 */     AccessibleState[] arrayOfAccessibleState = new AccessibleState[this.states.size()];
/* 173 */     for (byte b = 0; b < arrayOfAccessibleState.length; b++) {
/* 174 */       arrayOfAccessibleState[b] = this.states.elementAt(b);
/*     */     }
/* 176 */     return arrayOfAccessibleState;
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
/*     */   public String toString() {
/* 188 */     String str = null;
/* 189 */     if (this.states != null && this.states.size() > 0) {
/* 190 */       str = ((AccessibleState)this.states.elementAt(0)).toDisplayString();
/* 191 */       for (byte b = 1; b < this.states.size(); b++)
/*     */       {
/*     */         
/* 194 */         str = str + "," + ((AccessibleState)this.states.elementAt(b)).toDisplayString();
/*     */       }
/*     */     } 
/* 197 */     return str;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/accessibility/AccessibleStateSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */