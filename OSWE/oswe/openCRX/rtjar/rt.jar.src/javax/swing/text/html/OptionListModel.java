/*     */ package javax.swing.text.html;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.BitSet;
/*     */ import javax.swing.DefaultListModel;
/*     */ import javax.swing.ListSelectionModel;
/*     */ import javax.swing.event.EventListenerList;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class OptionListModel<E>
/*     */   extends DefaultListModel<E>
/*     */   implements ListSelectionModel, Serializable
/*     */ {
/*     */   private static final int MIN = -1;
/*     */   private static final int MAX = 2147483647;
/*  51 */   private int selectionMode = 0;
/*  52 */   private int minIndex = Integer.MAX_VALUE;
/*  53 */   private int maxIndex = -1;
/*  54 */   private int anchorIndex = -1;
/*  55 */   private int leadIndex = -1;
/*  56 */   private int firstChangedIndex = Integer.MAX_VALUE;
/*  57 */   private int lastChangedIndex = -1;
/*     */   private boolean isAdjusting = false;
/*  59 */   private BitSet value = new BitSet(32);
/*  60 */   private BitSet initialValue = new BitSet(32);
/*  61 */   protected EventListenerList listenerList = new EventListenerList();
/*     */   protected boolean leadAnchorNotificationEnabled = true;
/*     */   
/*     */   public int getMinSelectionIndex() {
/*  65 */     return isSelectionEmpty() ? -1 : this.minIndex;
/*     */   } public int getMaxSelectionIndex() {
/*  67 */     return this.maxIndex;
/*     */   } public boolean getValueIsAdjusting() {
/*  69 */     return this.isAdjusting;
/*     */   } public int getSelectionMode() {
/*  71 */     return this.selectionMode;
/*     */   }
/*     */   public void setSelectionMode(int paramInt) {
/*  74 */     switch (paramInt) {
/*     */       case 0:
/*     */       case 1:
/*     */       case 2:
/*  78 */         this.selectionMode = paramInt;
/*     */         return;
/*     */     } 
/*  81 */     throw new IllegalArgumentException("invalid selectionMode");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSelectedIndex(int paramInt) {
/*  86 */     return (paramInt < this.minIndex || paramInt > this.maxIndex) ? false : this.value.get(paramInt);
/*     */   }
/*     */   
/*     */   public boolean isSelectionEmpty() {
/*  90 */     return (this.minIndex > this.maxIndex);
/*     */   }
/*     */   
/*     */   public void addListSelectionListener(ListSelectionListener paramListSelectionListener) {
/*  94 */     this.listenerList.add(ListSelectionListener.class, paramListSelectionListener);
/*     */   }
/*     */   
/*     */   public void removeListSelectionListener(ListSelectionListener paramListSelectionListener) {
/*  98 */     this.listenerList.remove(ListSelectionListener.class, paramListSelectionListener);
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
/*     */   public ListSelectionListener[] getListSelectionListeners() {
/* 110 */     return this.listenerList.<ListSelectionListener>getListeners(ListSelectionListener.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fireValueChanged(boolean paramBoolean) {
/* 118 */     fireValueChanged(getMinSelectionIndex(), getMaxSelectionIndex(), paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fireValueChanged(int paramInt1, int paramInt2) {
/* 127 */     fireValueChanged(paramInt1, paramInt2, getValueIsAdjusting());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fireValueChanged(int paramInt1, int paramInt2, boolean paramBoolean) {
/* 138 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/* 139 */     ListSelectionEvent listSelectionEvent = null;
/*     */     
/* 141 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 142 */       if (arrayOfObject[i] == ListSelectionListener.class) {
/* 143 */         if (listSelectionEvent == null) {
/* 144 */           listSelectionEvent = new ListSelectionEvent(this, paramInt1, paramInt2, paramBoolean);
/*     */         }
/* 146 */         ((ListSelectionListener)arrayOfObject[i + 1]).valueChanged(listSelectionEvent);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void fireValueChanged() {
/* 152 */     if (this.lastChangedIndex == -1) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 159 */     int i = this.firstChangedIndex;
/* 160 */     int j = this.lastChangedIndex;
/* 161 */     this.firstChangedIndex = Integer.MAX_VALUE;
/* 162 */     this.lastChangedIndex = -1;
/* 163 */     fireValueChanged(i, j);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void markAsDirty(int paramInt) {
/* 169 */     this.firstChangedIndex = Math.min(this.firstChangedIndex, paramInt);
/* 170 */     this.lastChangedIndex = Math.max(this.lastChangedIndex, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   private void set(int paramInt) {
/* 175 */     if (this.value.get(paramInt)) {
/*     */       return;
/*     */     }
/* 178 */     this.value.set(paramInt);
/* 179 */     Option option = (Option)get(paramInt);
/* 180 */     option.setSelection(true);
/* 181 */     markAsDirty(paramInt);
/*     */ 
/*     */     
/* 184 */     this.minIndex = Math.min(this.minIndex, paramInt);
/* 185 */     this.maxIndex = Math.max(this.maxIndex, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   private void clear(int paramInt) {
/* 190 */     if (!this.value.get(paramInt)) {
/*     */       return;
/*     */     }
/* 193 */     this.value.clear(paramInt);
/* 194 */     Option option = (Option)get(paramInt);
/* 195 */     option.setSelection(false);
/* 196 */     markAsDirty(paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 205 */     if (paramInt == this.minIndex) {
/* 206 */       this.minIndex++; for (; this.minIndex <= this.maxIndex && 
/* 207 */         !this.value.get(this.minIndex); this.minIndex++);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 218 */     if (paramInt == this.maxIndex) {
/* 219 */       this.maxIndex--; for (; this.minIndex <= this.maxIndex && 
/* 220 */         !this.value.get(this.maxIndex); this.maxIndex--);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 240 */     if (isSelectionEmpty()) {
/* 241 */       this.minIndex = Integer.MAX_VALUE;
/* 242 */       this.maxIndex = -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLeadAnchorNotificationEnabled(boolean paramBoolean) {
/* 251 */     this.leadAnchorNotificationEnabled = paramBoolean;
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
/*     */   public boolean isLeadAnchorNotificationEnabled() {
/* 267 */     return this.leadAnchorNotificationEnabled;
/*     */   }
/*     */   
/*     */   private void updateLeadAnchorIndices(int paramInt1, int paramInt2) {
/* 271 */     if (this.leadAnchorNotificationEnabled) {
/* 272 */       if (this.anchorIndex != paramInt1) {
/* 273 */         if (this.anchorIndex != -1) {
/* 274 */           markAsDirty(this.anchorIndex);
/*     */         }
/* 276 */         markAsDirty(paramInt1);
/*     */       } 
/*     */       
/* 279 */       if (this.leadIndex != paramInt2) {
/* 280 */         if (this.leadIndex != -1) {
/* 281 */           markAsDirty(this.leadIndex);
/*     */         }
/* 283 */         markAsDirty(paramInt2);
/*     */       } 
/*     */     } 
/* 286 */     this.anchorIndex = paramInt1;
/* 287 */     this.leadIndex = paramInt2;
/*     */   }
/*     */   
/*     */   private boolean contains(int paramInt1, int paramInt2, int paramInt3) {
/* 291 */     return (paramInt3 >= paramInt1 && paramInt3 <= paramInt2);
/*     */   }
/*     */ 
/*     */   
/*     */   private void changeSelection(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
/* 296 */     for (int i = Math.min(paramInt3, paramInt1); i <= Math.max(paramInt4, paramInt2); i++) {
/*     */       
/* 298 */       boolean bool1 = contains(paramInt1, paramInt2, i);
/* 299 */       boolean bool2 = contains(paramInt3, paramInt4, i);
/*     */       
/* 301 */       if (bool2 && bool1) {
/* 302 */         if (paramBoolean) {
/* 303 */           bool1 = false;
/*     */         } else {
/*     */           
/* 306 */           bool2 = false;
/*     */         } 
/*     */       }
/*     */       
/* 310 */       if (bool2) {
/* 311 */         set(i);
/*     */       }
/* 313 */       if (bool1) {
/* 314 */         clear(i);
/*     */       }
/*     */     } 
/* 317 */     fireValueChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void changeSelection(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 326 */     changeSelection(paramInt1, paramInt2, paramInt3, paramInt4, true);
/*     */   }
/*     */   
/*     */   public void clearSelection() {
/* 330 */     removeSelectionInterval(this.minIndex, this.maxIndex);
/*     */   }
/*     */   
/*     */   public void setSelectionInterval(int paramInt1, int paramInt2) {
/* 334 */     if (paramInt1 == -1 || paramInt2 == -1) {
/*     */       return;
/*     */     }
/*     */     
/* 338 */     if (getSelectionMode() == 0) {
/* 339 */       paramInt1 = paramInt2;
/*     */     }
/*     */     
/* 342 */     updateLeadAnchorIndices(paramInt1, paramInt2);
/*     */     
/* 344 */     int i = this.minIndex;
/* 345 */     int j = this.maxIndex;
/* 346 */     int k = Math.min(paramInt1, paramInt2);
/* 347 */     int m = Math.max(paramInt1, paramInt2);
/* 348 */     changeSelection(i, j, k, m);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSelectionInterval(int paramInt1, int paramInt2) {
/* 353 */     if (paramInt1 == -1 || paramInt2 == -1) {
/*     */       return;
/*     */     }
/*     */     
/* 357 */     if (getSelectionMode() != 2) {
/* 358 */       setSelectionInterval(paramInt1, paramInt2);
/*     */       
/*     */       return;
/*     */     } 
/* 362 */     updateLeadAnchorIndices(paramInt1, paramInt2);
/*     */     
/* 364 */     int i = Integer.MAX_VALUE;
/* 365 */     byte b = -1;
/* 366 */     int j = Math.min(paramInt1, paramInt2);
/* 367 */     int k = Math.max(paramInt1, paramInt2);
/* 368 */     changeSelection(i, b, j, k);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeSelectionInterval(int paramInt1, int paramInt2) {
/* 374 */     if (paramInt1 == -1 || paramInt2 == -1) {
/*     */       return;
/*     */     }
/*     */     
/* 378 */     updateLeadAnchorIndices(paramInt1, paramInt2);
/*     */     
/* 380 */     int i = Math.min(paramInt1, paramInt2);
/* 381 */     int j = Math.max(paramInt1, paramInt2);
/* 382 */     int k = Integer.MAX_VALUE;
/* 383 */     byte b = -1;
/* 384 */     changeSelection(i, j, k, b);
/*     */   }
/*     */   
/*     */   private void setState(int paramInt, boolean paramBoolean) {
/* 388 */     if (paramBoolean) {
/* 389 */       set(paramInt);
/*     */     } else {
/*     */       
/* 392 */       clear(paramInt);
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
/*     */   public void insertIndexInterval(int paramInt1, int paramInt2, boolean paramBoolean) {
/* 408 */     int i = paramBoolean ? paramInt1 : (paramInt1 + 1);
/* 409 */     int j = i + paramInt2 - 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 415 */     for (int k = this.maxIndex; k >= i; k--) {
/* 416 */       setState(k + paramInt2, this.value.get(k));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 421 */     boolean bool = this.value.get(paramInt1);
/* 422 */     for (int m = i; m <= j; m++) {
/* 423 */       setState(m, bool);
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
/*     */   public void removeIndexInterval(int paramInt1, int paramInt2) {
/* 436 */     int i = Math.min(paramInt1, paramInt2);
/* 437 */     int j = Math.max(paramInt1, paramInt2);
/* 438 */     int k = j - i + 1;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 443 */     for (int m = i; m <= this.maxIndex; m++) {
/* 444 */       setState(m, this.value.get(m + k));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValueIsAdjusting(boolean paramBoolean) {
/* 450 */     if (paramBoolean != this.isAdjusting) {
/* 451 */       this.isAdjusting = paramBoolean;
/* 452 */       fireValueChanged(paramBoolean);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 458 */     String str = (getValueIsAdjusting() ? "~" : "=") + this.value.toString();
/* 459 */     return getClass().getName() + " " + Integer.toString(hashCode()) + " " + str;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 472 */     OptionListModel optionListModel = (OptionListModel)super.clone();
/* 473 */     optionListModel.value = (BitSet)this.value.clone();
/* 474 */     optionListModel.listenerList = new EventListenerList();
/* 475 */     return optionListModel;
/*     */   }
/*     */   
/*     */   public int getAnchorSelectionIndex() {
/* 479 */     return this.anchorIndex;
/*     */   }
/*     */   
/*     */   public int getLeadSelectionIndex() {
/* 483 */     return this.leadIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAnchorSelectionIndex(int paramInt) {
/* 493 */     this.anchorIndex = paramInt;
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
/*     */   public void setLeadSelectionIndex(int paramInt) {
/* 524 */     int i = this.anchorIndex;
/* 525 */     if (getSelectionMode() == 0) {
/* 526 */       i = paramInt;
/*     */     }
/*     */     
/* 529 */     int j = Math.min(this.anchorIndex, this.leadIndex);
/* 530 */     int k = Math.max(this.anchorIndex, this.leadIndex);
/* 531 */     int m = Math.min(i, paramInt);
/* 532 */     int n = Math.max(i, paramInt);
/* 533 */     if (this.value.get(this.anchorIndex)) {
/* 534 */       changeSelection(j, k, m, n);
/*     */     } else {
/*     */       
/* 537 */       changeSelection(m, n, j, k, false);
/*     */     } 
/* 539 */     this.anchorIndex = i;
/* 540 */     this.leadIndex = paramInt;
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
/*     */   public void setInitialSelection(int paramInt) {
/* 552 */     if (this.initialValue.get(paramInt)) {
/*     */       return;
/*     */     }
/* 555 */     if (this.selectionMode == 0)
/*     */     {
/* 557 */       this.initialValue.and(new BitSet());
/*     */     }
/* 559 */     this.initialValue.set(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BitSet getInitialSelection() {
/* 567 */     return this.initialValue;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/OptionListModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */