/*     */ package com.sun.corba.se.impl.interceptors;
/*     */ 
/*     */ import com.sun.corba.se.impl.corba.AnyImpl;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.PortableInterceptor.InvalidSlot;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SlotTable
/*     */ {
/*     */   private Any[] theSlotData;
/*     */   private ORB orb;
/*     */   private boolean dirtyFlag;
/*     */   
/*     */   SlotTable(ORB paramORB, int paramInt) {
/*  53 */     this.dirtyFlag = false;
/*  54 */     this.orb = paramORB;
/*  55 */     this.theSlotData = new Any[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set_slot(int paramInt, Any paramAny) throws InvalidSlot {
/*  65 */     if (paramInt >= this.theSlotData.length) {
/*  66 */       throw new InvalidSlot();
/*     */     }
/*  68 */     this.dirtyFlag = true;
/*  69 */     this.theSlotData[paramInt] = paramAny;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Any get_slot(int paramInt) throws InvalidSlot {
/*  79 */     if (paramInt >= this.theSlotData.length) {
/*  80 */       throw new InvalidSlot();
/*     */     }
/*  82 */     if (this.theSlotData[paramInt] == null) {
/*  83 */       this.theSlotData[paramInt] = (Any)new AnyImpl(this.orb);
/*     */     }
/*  85 */     return this.theSlotData[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void resetSlots() {
/*  93 */     if (this.dirtyFlag == true) {
/*  94 */       for (byte b = 0; b < this.theSlotData.length; b++) {
/*  95 */         this.theSlotData[b] = null;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getSize() {
/* 104 */     return this.theSlotData.length;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/interceptors/SlotTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */