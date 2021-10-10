/*     */ package java.sql;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BatchUpdateException
/*     */   extends SQLException
/*     */ {
/*     */   private int[] updateCounts;
/*     */   private long[] longUpdateCounts;
/*     */   private static final long serialVersionUID = 5977529877145521757L;
/*     */   
/*     */   public BatchUpdateException(String paramString1, String paramString2, int paramInt, int[] paramArrayOfint) {
/* 101 */     super(paramString1, paramString2, paramInt);
/* 102 */     this.updateCounts = (paramArrayOfint == null) ? null : Arrays.copyOf(paramArrayOfint, paramArrayOfint.length);
/* 103 */     this.longUpdateCounts = (paramArrayOfint == null) ? null : copyUpdateCount(paramArrayOfint);
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
/*     */   public BatchUpdateException(String paramString1, String paramString2, int[] paramArrayOfint) {
/* 136 */     this(paramString1, paramString2, 0, paramArrayOfint);
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
/*     */   public BatchUpdateException(String paramString, int[] paramArrayOfint) {
/* 167 */     this(paramString, (String)null, 0, paramArrayOfint);
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
/*     */   public BatchUpdateException(int[] paramArrayOfint) {
/* 196 */     this((String)null, (String)null, 0, paramArrayOfint);
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
/*     */   public BatchUpdateException() {
/* 213 */     this((String)null, (String)null, 0, (int[])null);
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
/*     */   public BatchUpdateException(Throwable paramThrowable) {
/* 233 */     this((paramThrowable == null) ? null : paramThrowable.toString(), (String)null, 0, (int[])null, paramThrowable);
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
/*     */   public BatchUpdateException(int[] paramArrayOfint, Throwable paramThrowable) {
/* 266 */     this((paramThrowable == null) ? null : paramThrowable.toString(), (String)null, 0, paramArrayOfint, paramThrowable);
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
/*     */   public BatchUpdateException(String paramString, int[] paramArrayOfint, Throwable paramThrowable) {
/* 297 */     this(paramString, (String)null, 0, paramArrayOfint, paramThrowable);
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
/*     */   public BatchUpdateException(String paramString1, String paramString2, int[] paramArrayOfint, Throwable paramThrowable) {
/* 331 */     this(paramString1, paramString2, 0, paramArrayOfint, paramThrowable);
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
/*     */   public BatchUpdateException(String paramString1, String paramString2, int paramInt, int[] paramArrayOfint, Throwable paramThrowable) {
/* 366 */     super(paramString1, paramString2, paramInt, paramThrowable);
/* 367 */     this.updateCounts = (paramArrayOfint == null) ? null : Arrays.copyOf(paramArrayOfint, paramArrayOfint.length);
/* 368 */     this.longUpdateCounts = (paramArrayOfint == null) ? null : copyUpdateCount(paramArrayOfint);
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
/*     */   public int[] getUpdateCounts() {
/* 403 */     return (this.updateCounts == null) ? null : Arrays.copyOf(this.updateCounts, this.updateCounts.length);
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
/*     */   public BatchUpdateException(String paramString1, String paramString2, int paramInt, long[] paramArrayOflong, Throwable paramThrowable) {
/* 433 */     super(paramString1, paramString2, paramInt, paramThrowable);
/* 434 */     this.longUpdateCounts = (paramArrayOflong == null) ? null : Arrays.copyOf(paramArrayOflong, paramArrayOflong.length);
/* 435 */     this.updateCounts = (this.longUpdateCounts == null) ? null : copyUpdateCount(this.longUpdateCounts);
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
/*     */   public long[] getLargeUpdateCounts() {
/* 466 */     return (this.longUpdateCounts == null) ? null : 
/* 467 */       Arrays.copyOf(this.longUpdateCounts, this.longUpdateCounts.length);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long[] copyUpdateCount(int[] paramArrayOfint) {
/* 510 */     long[] arrayOfLong = new long[paramArrayOfint.length];
/* 511 */     for (byte b = 0; b < paramArrayOfint.length; b++) {
/* 512 */       arrayOfLong[b] = paramArrayOfint[b];
/*     */     }
/* 514 */     return arrayOfLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[] copyUpdateCount(long[] paramArrayOflong) {
/* 523 */     int[] arrayOfInt = new int[paramArrayOflong.length];
/* 524 */     for (byte b = 0; b < paramArrayOflong.length; b++) {
/* 525 */       arrayOfInt[b] = (int)paramArrayOflong[b];
/*     */     }
/* 527 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 536 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 537 */     int[] arrayOfInt = (int[])getField.get("updateCounts", (Object)null);
/* 538 */     long[] arrayOfLong = (long[])getField.get("longUpdateCounts", (Object)null);
/* 539 */     if (arrayOfInt != null && arrayOfLong != null && arrayOfInt.length != arrayOfLong.length)
/* 540 */       throw new InvalidObjectException("update counts are not the expected size"); 
/* 541 */     if (arrayOfInt != null)
/* 542 */       this.updateCounts = (int[])arrayOfInt.clone(); 
/* 543 */     if (arrayOfLong != null)
/* 544 */       this.longUpdateCounts = (long[])arrayOfLong.clone(); 
/* 545 */     if (this.updateCounts == null && this.longUpdateCounts != null)
/* 546 */       this.updateCounts = copyUpdateCount(this.longUpdateCounts); 
/* 547 */     if (this.longUpdateCounts == null && this.updateCounts != null) {
/* 548 */       this.longUpdateCounts = copyUpdateCount(this.updateCounts);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException, ClassNotFoundException {
/* 559 */     ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 560 */     putField.put("updateCounts", this.updateCounts);
/* 561 */     putField.put("longUpdateCounts", this.longUpdateCounts);
/* 562 */     paramObjectOutputStream.writeFields();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/sql/BatchUpdateException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */