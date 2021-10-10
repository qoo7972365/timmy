/*     */ package com.sun.corba.se.impl.interceptors;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.InterceptorsSystemException;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SlotTableStack
/*     */ {
/*     */   private List tableContainer;
/*     */   private int currentIndex;
/*     */   private SlotTablePool tablePool;
/*     */   private ORB orb;
/*     */   private InterceptorsSystemException wrapper;
/*     */   
/*     */   private class SlotTablePool
/*     */   {
/*     */     private SlotTable[] pool;
/*  54 */     private final int HIGH_WATER_MARK = 5;
/*     */     
/*     */     private int currentIndex;
/*     */ 
/*     */     
/*     */     SlotTablePool() {
/*  60 */       this.pool = new SlotTable[5];
/*  61 */       this.currentIndex = 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void putSlotTable(SlotTable param1SlotTable) {
/*  70 */       if (this.currentIndex >= 5) {
/*     */         return;
/*     */       }
/*     */       
/*  74 */       this.pool[this.currentIndex] = param1SlotTable;
/*  75 */       this.currentIndex++;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     SlotTable getSlotTable() {
/*  83 */       if (this.currentIndex == 0) {
/*  84 */         return null;
/*     */       }
/*     */       
/*  87 */       this.currentIndex--;
/*  88 */       return this.pool[this.currentIndex];
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
/*     */   SlotTableStack(ORB paramORB, SlotTable paramSlotTable) {
/* 112 */     this.orb = paramORB;
/* 113 */     this.wrapper = InterceptorsSystemException.get(paramORB, "rpc.protocol");
/*     */     
/* 115 */     this.currentIndex = 0;
/* 116 */     this.tableContainer = new ArrayList();
/* 117 */     this.tablePool = new SlotTablePool();
/*     */ 
/*     */ 
/*     */     
/* 121 */     this.tableContainer.add(this.currentIndex, paramSlotTable);
/* 122 */     this.currentIndex++;
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
/*     */   void pushSlotTable() {
/* 136 */     SlotTable slotTable = this.tablePool.getSlotTable();
/* 137 */     if (slotTable == null) {
/*     */       
/* 139 */       SlotTable slotTable1 = peekSlotTable();
/* 140 */       slotTable = new SlotTable(this.orb, slotTable1.getSize());
/*     */     } 
/*     */     
/* 143 */     if (this.currentIndex == this.tableContainer.size())
/*     */     
/* 145 */     { this.tableContainer.add(this.currentIndex, slotTable); }
/* 146 */     else { if (this.currentIndex > this.tableContainer.size()) {
/* 147 */         throw this.wrapper.slotTableInvariant(new Integer(this.currentIndex), new Integer(this.tableContainer
/* 148 */               .size()));
/*     */       }
/*     */       
/* 151 */       this.tableContainer.set(this.currentIndex, slotTable); }
/*     */     
/* 153 */     this.currentIndex++;
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
/*     */   void popSlotTable() {
/* 166 */     if (this.currentIndex <= 1)
/*     */     {
/*     */       
/* 169 */       throw this.wrapper.cantPopOnlyPicurrent();
/*     */     }
/* 171 */     this.currentIndex--;
/* 172 */     SlotTable slotTable = this.tableContainer.get(this.currentIndex);
/* 173 */     this.tableContainer.set(this.currentIndex, null);
/* 174 */     slotTable.resetSlots();
/* 175 */     this.tablePool.putSlotTable(slotTable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SlotTable peekSlotTable() {
/* 183 */     return this.tableContainer.get(this.currentIndex - 1);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/interceptors/SlotTableStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */