/*     */ package com.sun.corba.se.impl.oa.toa;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TransientObjectManager
/*     */ {
/*     */   private ORB orb;
/*  39 */   private int maxSize = 128;
/*     */   private Element[] elementArray;
/*     */   private Element freeList;
/*     */   
/*     */   void dprint(String paramString) {
/*  44 */     ORBUtility.dprint(this, paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public TransientObjectManager(ORB paramORB) {
/*  49 */     this.orb = paramORB;
/*     */     
/*  51 */     this.elementArray = new Element[this.maxSize];
/*  52 */     this.elementArray[this.maxSize - 1] = new Element(this.maxSize - 1, null);
/*  53 */     for (int i = this.maxSize - 2; i >= 0; i--)
/*  54 */       this.elementArray[i] = new Element(i, this.elementArray[i + 1]); 
/*  55 */     this.freeList = this.elementArray[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized byte[] storeServant(Object paramObject1, Object paramObject2) {
/*  60 */     if (this.freeList == null) {
/*  61 */       doubleSize();
/*     */     }
/*  63 */     Element element = this.freeList;
/*  64 */     this.freeList = (Element)this.freeList.servant;
/*     */     
/*  66 */     byte[] arrayOfByte = element.getKey(paramObject1, paramObject2);
/*  67 */     if (this.orb.transientObjectManagerDebugFlag)
/*  68 */       dprint("storeServant returns key for element " + element); 
/*  69 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized Object lookupServant(byte[] paramArrayOfbyte) {
/*  74 */     int i = ORBUtility.bytesToInt(paramArrayOfbyte, 0);
/*  75 */     int j = ORBUtility.bytesToInt(paramArrayOfbyte, 4);
/*     */     
/*  77 */     if (this.orb.transientObjectManagerDebugFlag) {
/*  78 */       dprint("lookupServant called with index=" + i + ", counter=" + j);
/*     */     }
/*  80 */     if ((this.elementArray[i]).counter == j && (this.elementArray[i]).valid) {
/*     */       
/*  82 */       if (this.orb.transientObjectManagerDebugFlag)
/*  83 */         dprint("\tcounter is valid"); 
/*  84 */       return (this.elementArray[i]).servant;
/*     */     } 
/*     */ 
/*     */     
/*  88 */     if (this.orb.transientObjectManagerDebugFlag)
/*  89 */       dprint("\tcounter is invalid"); 
/*  90 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized Object lookupServantData(byte[] paramArrayOfbyte) {
/*  95 */     int i = ORBUtility.bytesToInt(paramArrayOfbyte, 0);
/*  96 */     int j = ORBUtility.bytesToInt(paramArrayOfbyte, 4);
/*     */     
/*  98 */     if (this.orb.transientObjectManagerDebugFlag) {
/*  99 */       dprint("lookupServantData called with index=" + i + ", counter=" + j);
/*     */     }
/* 101 */     if ((this.elementArray[i]).counter == j && (this.elementArray[i]).valid) {
/*     */       
/* 103 */       if (this.orb.transientObjectManagerDebugFlag)
/* 104 */         dprint("\tcounter is valid"); 
/* 105 */       return (this.elementArray[i]).servantData;
/*     */     } 
/*     */ 
/*     */     
/* 109 */     if (this.orb.transientObjectManagerDebugFlag)
/* 110 */       dprint("\tcounter is invalid"); 
/* 111 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void deleteServant(byte[] paramArrayOfbyte) {
/* 116 */     int i = ORBUtility.bytesToInt(paramArrayOfbyte, 0);
/* 117 */     if (this.orb.transientObjectManagerDebugFlag) {
/* 118 */       dprint("deleting servant at index=" + i);
/*     */     }
/* 120 */     this.elementArray[i].delete(this.freeList);
/* 121 */     this.freeList = this.elementArray[i];
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized byte[] getKey(Object paramObject) {
/* 126 */     for (byte b = 0; b < this.maxSize; b++) {
/* 127 */       if ((this.elementArray[b]).valid && (this.elementArray[b]).servant == paramObject)
/*     */       {
/* 129 */         return this.elementArray[b].toBytes();
/*     */       }
/*     */     } 
/* 132 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void doubleSize() {
/* 139 */     Element[] arrayOfElement = this.elementArray;
/* 140 */     int i = this.maxSize;
/* 141 */     this.maxSize *= 2;
/* 142 */     this.elementArray = new Element[this.maxSize];
/*     */     int j;
/* 144 */     for (j = 0; j < i; j++) {
/* 145 */       this.elementArray[j] = arrayOfElement[j];
/*     */     }
/* 147 */     this.elementArray[this.maxSize - 1] = new Element(this.maxSize - 1, null);
/* 148 */     for (j = this.maxSize - 2; j >= i; j--)
/* 149 */       this.elementArray[j] = new Element(j, this.elementArray[j + 1]); 
/* 150 */     this.freeList = this.elementArray[i];
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/oa/toa/TransientObjectManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */