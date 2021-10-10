/*     */ package sun.net;
/*     */ 
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProgressMonitor
/*     */ {
/*     */   public static synchronized ProgressMonitor getDefault() {
/*  43 */     return pm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void setDefault(ProgressMonitor paramProgressMonitor) {
/*  50 */     if (paramProgressMonitor != null) {
/*  51 */       pm = paramProgressMonitor;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void setMeteringPolicy(ProgressMeteringPolicy paramProgressMeteringPolicy) {
/*  58 */     if (paramProgressMeteringPolicy != null) {
/*  59 */       meteringPolicy = paramProgressMeteringPolicy;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<ProgressSource> getProgressSources() {
/*  67 */     ArrayList<ProgressSource> arrayList = new ArrayList();
/*     */     
/*     */     try {
/*  70 */       synchronized (this.progressSourceList) {
/*  71 */         for (ProgressSource progressSource : this.progressSourceList)
/*     */         {
/*     */ 
/*     */           
/*  75 */           arrayList.add((ProgressSource)progressSource.clone());
/*     */         }
/*     */       }
/*     */     
/*  79 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*  80 */       cloneNotSupportedException.printStackTrace();
/*     */     } 
/*     */     
/*  83 */     return arrayList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int getProgressUpdateThreshold() {
/*  90 */     return meteringPolicy.getProgressUpdateThreshold();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldMeterInput(URL paramURL, String paramString) {
/*  98 */     return meteringPolicy.shouldMeterInput(paramURL, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerSource(ProgressSource paramProgressSource) {
/* 106 */     synchronized (this.progressSourceList) {
/* 107 */       if (this.progressSourceList.contains(paramProgressSource)) {
/*     */         return;
/*     */       }
/* 110 */       this.progressSourceList.add(paramProgressSource);
/*     */     } 
/*     */ 
/*     */     
/* 114 */     if (this.progressListenerList.size() > 0) {
/*     */ 
/*     */       
/* 117 */       ArrayList arrayList = new ArrayList();
/*     */ 
/*     */       
/* 120 */       synchronized (this.progressListenerList) {
/* 121 */         for (Iterator<ProgressListener> iterator = this.progressListenerList.iterator(); iterator.hasNext();) {
/* 122 */           arrayList.add(iterator.next());
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 127 */       for (ProgressListener progressListener : arrayList) {
/*     */         
/* 129 */         ProgressEvent progressEvent = new ProgressEvent(paramProgressSource, paramProgressSource.getURL(), paramProgressSource.getMethod(), paramProgressSource.getContentType(), paramProgressSource.getState(), paramProgressSource.getProgress(), paramProgressSource.getExpected());
/* 130 */         progressListener.progressStart(progressEvent);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregisterSource(ProgressSource paramProgressSource) {
/* 140 */     synchronized (this.progressSourceList) {
/*     */       
/* 142 */       if (!this.progressSourceList.contains(paramProgressSource)) {
/*     */         return;
/*     */       }
/*     */       
/* 146 */       paramProgressSource.close();
/* 147 */       this.progressSourceList.remove(paramProgressSource);
/*     */     } 
/*     */ 
/*     */     
/* 151 */     if (this.progressListenerList.size() > 0) {
/*     */ 
/*     */       
/* 154 */       ArrayList arrayList = new ArrayList();
/*     */ 
/*     */       
/* 157 */       synchronized (this.progressListenerList) {
/* 158 */         for (Iterator<ProgressListener> iterator = this.progressListenerList.iterator(); iterator.hasNext();) {
/* 159 */           arrayList.add(iterator.next());
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 164 */       for (ProgressListener progressListener : arrayList) {
/*     */         
/* 166 */         ProgressEvent progressEvent = new ProgressEvent(paramProgressSource, paramProgressSource.getURL(), paramProgressSource.getMethod(), paramProgressSource.getContentType(), paramProgressSource.getState(), paramProgressSource.getProgress(), paramProgressSource.getExpected());
/* 167 */         progressListener.progressFinish(progressEvent);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateProgress(ProgressSource paramProgressSource) {
/* 177 */     synchronized (this.progressSourceList) {
/* 178 */       if (!this.progressSourceList.contains(paramProgressSource)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 183 */     if (this.progressListenerList.size() > 0) {
/*     */ 
/*     */       
/* 186 */       ArrayList arrayList = new ArrayList();
/*     */ 
/*     */       
/* 189 */       synchronized (this.progressListenerList) {
/* 190 */         for (Iterator<ProgressListener> iterator = this.progressListenerList.iterator(); iterator.hasNext();) {
/* 191 */           arrayList.add(iterator.next());
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 196 */       for (ProgressListener progressListener : arrayList) {
/*     */         
/* 198 */         ProgressEvent progressEvent = new ProgressEvent(paramProgressSource, paramProgressSource.getURL(), paramProgressSource.getMethod(), paramProgressSource.getContentType(), paramProgressSource.getState(), paramProgressSource.getProgress(), paramProgressSource.getExpected());
/* 199 */         progressListener.progressUpdate(progressEvent);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addProgressListener(ProgressListener paramProgressListener) {
/* 208 */     synchronized (this.progressListenerList) {
/* 209 */       this.progressListenerList.add(paramProgressListener);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeProgressListener(ProgressListener paramProgressListener) {
/* 217 */     synchronized (this.progressListenerList) {
/* 218 */       this.progressListenerList.remove(paramProgressListener);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 223 */   private static ProgressMeteringPolicy meteringPolicy = new DefaultProgressMeteringPolicy();
/*     */ 
/*     */   
/* 226 */   private static ProgressMonitor pm = new ProgressMonitor();
/*     */ 
/*     */   
/* 229 */   private ArrayList<ProgressSource> progressSourceList = new ArrayList<>();
/*     */ 
/*     */   
/* 232 */   private ArrayList<ProgressListener> progressListenerList = new ArrayList<>();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/ProgressMonitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */