/*     */ package java.io;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import sun.misc.JavaIOFileDescriptorAccess;
/*     */ import sun.misc.SharedSecrets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FileDescriptor
/*     */ {
/*     */   private int fd;
/*     */   private Closeable parent;
/*     */   private List<Closeable> otherParents;
/*     */   private boolean closed;
/*     */   
/*     */   public FileDescriptor() {
/*  59 */     this.fd = -1;
/*     */   }
/*     */   
/*     */   private FileDescriptor(int paramInt) {
/*  63 */     this.fd = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   public static final FileDescriptor in = new FileDescriptor(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public static final FileDescriptor out = new FileDescriptor(1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public static final FileDescriptor err = new FileDescriptor(2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean valid() {
/* 100 */     return (this.fd != -1);
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
/*     */   static {
/* 137 */     initIDs();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 142 */     SharedSecrets.setJavaIOFileDescriptorAccess(new JavaIOFileDescriptorAccess()
/*     */         {
/*     */           public void set(FileDescriptor param1FileDescriptor, int param1Int) {
/* 145 */             param1FileDescriptor.fd = param1Int;
/*     */           }
/*     */           
/*     */           public int get(FileDescriptor param1FileDescriptor) {
/* 149 */             return param1FileDescriptor.fd;
/*     */           }
/*     */           
/*     */           public void setHandle(FileDescriptor param1FileDescriptor, long param1Long) {
/* 153 */             throw new UnsupportedOperationException();
/*     */           }
/*     */           
/*     */           public long getHandle(FileDescriptor param1FileDescriptor) {
/* 157 */             throw new UnsupportedOperationException();
/*     */           }
/*     */         });
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
/*     */   synchronized void attach(Closeable paramCloseable) {
/* 175 */     if (this.parent == null) {
/*     */       
/* 177 */       this.parent = paramCloseable;
/* 178 */     } else if (this.otherParents == null) {
/* 179 */       this.otherParents = new ArrayList<>();
/* 180 */       this.otherParents.add(this.parent);
/* 181 */       this.otherParents.add(paramCloseable);
/*     */     } else {
/* 183 */       this.otherParents.add(paramCloseable);
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
/*     */   synchronized void closeAll(Closeable paramCloseable) throws IOException {
/* 195 */     if (!this.closed) {
/* 196 */       this.closed = true;
/* 197 */       IOException iOException = null;
/* 198 */       try (Closeable null = paramCloseable) {
/* 199 */         if (this.otherParents != null) {
/* 200 */           for (Closeable closeable1 : this.otherParents) {
/*     */             try {
/* 202 */               closeable1.close();
/* 203 */             } catch (IOException iOException1) {
/* 204 */               if (iOException == null) {
/* 205 */                 iOException = iOException1; continue;
/*     */               } 
/* 207 */               iOException.addSuppressed(iOException1);
/*     */             }
/*     */           
/*     */           } 
/*     */         }
/* 212 */       } catch (IOException iOException1) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 217 */         if (iOException != null)
/* 218 */           iOException1.addSuppressed(iOException); 
/* 219 */         iOException = iOException1;
/*     */       } finally {
/* 221 */         if (iOException != null)
/* 222 */           throw iOException; 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public native void sync() throws SyncFailedException;
/*     */   
/*     */   private static native void initIDs();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/FileDescriptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */