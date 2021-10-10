/*     */ package javax.swing;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javax.swing.event.EventListenerList;
/*     */ import javax.swing.event.ListDataEvent;
/*     */ import javax.swing.event.ListDataListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractListModel<E>
/*     */   implements ListModel<E>, Serializable
/*     */ {
/*  51 */   protected EventListenerList listenerList = new EventListenerList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addListDataListener(ListDataListener paramListDataListener) {
/*  61 */     this.listenerList.add(ListDataListener.class, paramListDataListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeListDataListener(ListDataListener paramListDataListener) {
/*  72 */     this.listenerList.remove(ListDataListener.class, paramListDataListener);
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
/*     */   public ListDataListener[] getListDataListeners() {
/*  90 */     return this.listenerList.<ListDataListener>getListeners(ListDataListener.class);
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
/*     */   protected void fireContentsChanged(Object paramObject, int paramInt1, int paramInt2) {
/* 110 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/* 111 */     ListDataEvent listDataEvent = null;
/*     */     
/* 113 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 114 */       if (arrayOfObject[i] == ListDataListener.class) {
/* 115 */         if (listDataEvent == null) {
/* 116 */           listDataEvent = new ListDataEvent(paramObject, 0, paramInt1, paramInt2);
/*     */         }
/* 118 */         ((ListDataListener)arrayOfObject[i + 1]).contentsChanged(listDataEvent);
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fireIntervalAdded(Object paramObject, int paramInt1, int paramInt2) {
/* 140 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/* 141 */     ListDataEvent listDataEvent = null;
/*     */     
/* 143 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 144 */       if (arrayOfObject[i] == ListDataListener.class) {
/* 145 */         if (listDataEvent == null) {
/* 146 */           listDataEvent = new ListDataEvent(paramObject, 1, paramInt1, paramInt2);
/*     */         }
/* 148 */         ((ListDataListener)arrayOfObject[i + 1]).intervalAdded(listDataEvent);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fireIntervalRemoved(Object paramObject, int paramInt1, int paramInt2) {
/* 171 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/* 172 */     ListDataEvent listDataEvent = null;
/*     */     
/* 174 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 175 */       if (arrayOfObject[i] == ListDataListener.class) {
/* 176 */         if (listDataEvent == null) {
/* 177 */           listDataEvent = new ListDataEvent(paramObject, 2, paramInt1, paramInt2);
/*     */         }
/* 179 */         ((ListDataListener)arrayOfObject[i + 1]).intervalRemoved(listDataEvent);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends java.util.EventListener> T[] getListeners(Class<T> paramClass) {
/* 220 */     return this.listenerList.getListeners(paramClass);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/AbstractListModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */