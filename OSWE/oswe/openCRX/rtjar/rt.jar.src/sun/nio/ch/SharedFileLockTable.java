/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.nio.channels.Channel;
/*     */ import java.nio.channels.FileLock;
/*     */ import java.nio.channels.OverlappingFileLockException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SharedFileLockTable
/*     */   extends FileLockTable
/*     */ {
/*     */   private static class FileLockReference
/*     */     extends WeakReference<FileLock>
/*     */   {
/*     */     private FileKey fileKey;
/*     */     
/*     */     FileLockReference(FileLock param1FileLock, ReferenceQueue<FileLock> param1ReferenceQueue, FileKey param1FileKey) {
/*  94 */       super(param1FileLock, param1ReferenceQueue);
/*  95 */       this.fileKey = param1FileKey;
/*     */     }
/*     */     
/*     */     FileKey fileKey() {
/*  99 */       return this.fileKey;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   private static ConcurrentHashMap<FileKey, List<FileLockReference>> lockMap = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */   
/* 110 */   private static ReferenceQueue<FileLock> queue = new ReferenceQueue<>();
/*     */ 
/*     */   
/*     */   private final Channel channel;
/*     */   
/*     */   private final FileKey fileKey;
/*     */ 
/*     */   
/*     */   SharedFileLockTable(Channel paramChannel, FileDescriptor paramFileDescriptor) throws IOException {
/* 119 */     this.channel = paramChannel;
/* 120 */     this.fileKey = FileKey.create(paramFileDescriptor);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(FileLock paramFileLock) throws OverlappingFileLockException {
/* 125 */     List<FileLockReference> list = lockMap.get(this.fileKey);
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 130 */       if (list == null) {
/* 131 */         List<FileLockReference> list1; list = new ArrayList(2);
/*     */         
/* 133 */         synchronized (list) {
/* 134 */           list1 = lockMap.putIfAbsent(this.fileKey, list);
/* 135 */           if (list1 == null) {
/*     */             
/* 137 */             list.add(new FileLockReference(paramFileLock, queue, this.fileKey));
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 142 */         list = list1;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 149 */       synchronized (list) {
/* 150 */         List<FileLockReference> list1 = lockMap.get(this.fileKey);
/* 151 */         if (list == list1) {
/* 152 */           checkList(list, paramFileLock.position(), paramFileLock.size());
/* 153 */           list.add(new FileLockReference(paramFileLock, queue, this.fileKey));
/*     */           break;
/*     */         } 
/* 156 */         list = list1;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 162 */     removeStaleEntries();
/*     */   }
/*     */   
/*     */   private void removeKeyIfEmpty(FileKey paramFileKey, List<FileLockReference> paramList) {
/* 166 */     assert Thread.holdsLock(paramList);
/* 167 */     assert lockMap.get(paramFileKey) == paramList;
/* 168 */     if (paramList.isEmpty()) {
/* 169 */       lockMap.remove(paramFileKey);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove(FileLock paramFileLock) {
/* 175 */     assert paramFileLock != null;
/*     */ 
/*     */     
/* 178 */     List<FileLockReference> list = lockMap.get(this.fileKey);
/* 179 */     if (list == null)
/*     */       return; 
/* 181 */     synchronized (list) {
/* 182 */       byte b = 0;
/* 183 */       while (b < list.size()) {
/* 184 */         FileLockReference fileLockReference = list.get(b);
/* 185 */         FileLock fileLock = fileLockReference.get();
/* 186 */         if (fileLock == paramFileLock) {
/* 187 */           assert fileLock != null && fileLock.acquiredBy() == this.channel;
/* 188 */           fileLockReference.clear();
/* 189 */           list.remove(b);
/*     */           break;
/*     */         } 
/* 192 */         b++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<FileLock> removeAll() {
/* 199 */     ArrayList<FileLock> arrayList = new ArrayList();
/* 200 */     List<FileLockReference> list = lockMap.get(this.fileKey);
/* 201 */     if (list != null) {
/* 202 */       synchronized (list) {
/* 203 */         byte b = 0;
/* 204 */         while (b < list.size()) {
/* 205 */           FileLockReference fileLockReference = list.get(b);
/* 206 */           FileLock fileLock = fileLockReference.get();
/*     */ 
/*     */           
/* 209 */           if (fileLock != null && fileLock.acquiredBy() == this.channel) {
/*     */             
/* 211 */             fileLockReference.clear();
/* 212 */             list.remove(b);
/*     */ 
/*     */             
/* 215 */             arrayList.add(fileLock); continue;
/*     */           } 
/* 217 */           b++;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 222 */         removeKeyIfEmpty(this.fileKey, list);
/*     */       } 
/*     */     }
/* 225 */     return arrayList;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void replace(FileLock paramFileLock1, FileLock paramFileLock2) {
/* 231 */     List<FileLockReference> list = lockMap.get(this.fileKey);
/* 232 */     assert list != null;
/*     */     
/* 234 */     synchronized (list) {
/* 235 */       for (byte b = 0; b < list.size(); b++) {
/* 236 */         FileLockReference fileLockReference = list.get(b);
/* 237 */         FileLock fileLock = fileLockReference.get();
/* 238 */         if (fileLock == paramFileLock1) {
/* 239 */           fileLockReference.clear();
/* 240 */           list.set(b, new FileLockReference(paramFileLock2, queue, this.fileKey));
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkList(List<FileLockReference> paramList, long paramLong1, long paramLong2) throws OverlappingFileLockException {
/* 251 */     assert Thread.holdsLock(paramList);
/* 252 */     for (FileLockReference fileLockReference : paramList) {
/* 253 */       FileLock fileLock = fileLockReference.get();
/* 254 */       if (fileLock != null && fileLock.overlaps(paramLong1, paramLong2)) {
/* 255 */         throw new OverlappingFileLockException();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void removeStaleEntries() {
/*     */     FileLockReference fileLockReference;
/* 262 */     while ((fileLockReference = (FileLockReference)queue.poll()) != null) {
/* 263 */       FileKey fileKey = fileLockReference.fileKey();
/* 264 */       List<FileLockReference> list = lockMap.get(fileKey);
/* 265 */       if (list != null)
/* 266 */         synchronized (list) {
/* 267 */           list.remove(fileLockReference);
/* 268 */           removeKeyIfEmpty(fileKey, list);
/*     */         }  
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/SharedFileLockTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */