/*     */ package com.sun.corba.se.impl.naming.pcosnaming;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class CounterDB
/*     */   implements Serializable
/*     */ {
/*     */   private Integer counter;
/*     */   
/*     */   CounterDB(File paramFile) {
/* 208 */     counterFileName = "counter";
/* 209 */     this.counterFile = new File(paramFile, counterFileName);
/* 210 */     if (!this.counterFile.exists()) {
/* 211 */       this.counter = new Integer(0);
/* 212 */       writeCounter();
/*     */     } else {
/* 214 */       readCounter();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void readCounter() {
/*     */     try {
/* 221 */       FileInputStream fileInputStream = new FileInputStream(this.counterFile);
/* 222 */       ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
/* 223 */       this.counter = (Integer)objectInputStream.readObject();
/* 224 */       objectInputStream.close();
/* 225 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeCounter() {
/*     */     try {
/* 232 */       this.counterFile.delete();
/* 233 */       FileOutputStream fileOutputStream = new FileOutputStream(this.counterFile);
/* 234 */       ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
/* 235 */       objectOutputStream.writeObject(this.counter);
/* 236 */       objectOutputStream.flush();
/* 237 */       objectOutputStream.close();
/*     */     }
/* 239 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int getNextCounter() {
/* 245 */     int i = this.counter.intValue();
/* 246 */     this.counter = new Integer(++i);
/* 247 */     writeCounter();
/*     */     
/* 249 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 256 */   private static String counterFileName = "counter";
/*     */   private transient File counterFile;
/*     */   public static final int rootCounter = 0;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/naming/pcosnaming/CounterDB.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */