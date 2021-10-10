/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TabSet
/*     */   implements Serializable
/*     */ {
/*     */   private TabStop[] tabs;
/*  55 */   private int hashCode = Integer.MAX_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TabSet(TabStop[] paramArrayOfTabStop) {
/*  63 */     if (paramArrayOfTabStop != null) {
/*  64 */       int i = paramArrayOfTabStop.length;
/*     */       
/*  66 */       this.tabs = new TabStop[i];
/*  67 */       System.arraycopy(paramArrayOfTabStop, 0, this.tabs, 0, i);
/*     */     } else {
/*     */       
/*  70 */       this.tabs = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTabCount() {
/*  77 */     return (this.tabs == null) ? 0 : this.tabs.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TabStop getTab(int paramInt) {
/*  86 */     int i = getTabCount();
/*     */     
/*  88 */     if (paramInt < 0 || paramInt >= i) {
/*  89 */       throw new IllegalArgumentException(paramInt + " is outside the range of tabs");
/*     */     }
/*  91 */     return this.tabs[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TabStop getTabAfter(float paramFloat) {
/*  99 */     int i = getTabIndexAfter(paramFloat);
/*     */     
/* 101 */     return (i == -1) ? null : this.tabs[i];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTabIndex(TabStop paramTabStop) {
/* 109 */     for (int i = getTabCount() - 1; i >= 0; i--) {
/*     */       
/* 111 */       if (getTab(i) == paramTabStop)
/* 112 */         return i; 
/* 113 */     }  return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTabIndexAfter(float paramFloat) {
/* 123 */     int i = 0;
/* 124 */     int j = getTabCount();
/* 125 */     while (i != j) {
/* 126 */       int k = (j - i) / 2 + i;
/* 127 */       if (paramFloat > this.tabs[k].getPosition()) {
/* 128 */         if (i == k) {
/* 129 */           i = j; continue;
/*     */         } 
/* 131 */         i = k;
/*     */         continue;
/*     */       } 
/* 134 */       if (k == 0 || paramFloat > this.tabs[k - 1].getPosition())
/* 135 */         return k; 
/* 136 */       j = k;
/*     */     } 
/*     */ 
/*     */     
/* 140 */     return -1;
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
/*     */   public boolean equals(Object paramObject) {
/* 154 */     if (paramObject == this) {
/* 155 */       return true;
/*     */     }
/* 157 */     if (paramObject instanceof TabSet) {
/* 158 */       TabSet tabSet = (TabSet)paramObject;
/* 159 */       int i = getTabCount();
/* 160 */       if (tabSet.getTabCount() != i) {
/* 161 */         return false;
/*     */       }
/* 163 */       for (byte b = 0; b < i; b++) {
/* 164 */         TabStop tabStop1 = getTab(b);
/* 165 */         TabStop tabStop2 = tabSet.getTab(b);
/* 166 */         if ((tabStop1 == null && tabStop2 != null) || (tabStop1 != null && 
/* 167 */           !getTab(b).equals(tabSet.getTab(b)))) {
/* 168 */           return false;
/*     */         }
/*     */       } 
/* 171 */       return true;
/*     */     } 
/* 173 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 183 */     if (this.hashCode == Integer.MAX_VALUE) {
/* 184 */       this.hashCode = 0;
/* 185 */       int i = getTabCount();
/* 186 */       for (byte b = 0; b < i; b++) {
/* 187 */         TabStop tabStop = getTab(b);
/* 188 */         this.hashCode ^= (tabStop != null) ? getTab(b).hashCode() : 0;
/*     */       } 
/* 190 */       if (this.hashCode == Integer.MAX_VALUE) {
/* 191 */         this.hashCode--;
/*     */       }
/*     */     } 
/* 194 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 201 */     int i = getTabCount();
/* 202 */     StringBuilder stringBuilder = new StringBuilder("[ ");
/*     */     
/* 204 */     for (byte b = 0; b < i; b++) {
/* 205 */       if (b > 0)
/* 206 */         stringBuilder.append(" - "); 
/* 207 */       stringBuilder.append(getTab(b).toString());
/*     */     } 
/* 209 */     stringBuilder.append(" ]");
/* 210 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/TabSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */