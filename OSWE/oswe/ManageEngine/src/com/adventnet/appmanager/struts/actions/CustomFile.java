/*    */ package com.adventnet.appmanager.struts.actions;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.Date;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CustomFile
/*    */   implements Comparable
/*    */ {
/*    */   private long lastModifiedTime;
/*    */   private String fileName;
/*    */   private Date lastModifiedTimeasDate;
/*    */   private File file;
/*    */   private String filePath;
/*    */   
/* 18 */   public Date getLastModifiedTimeasDate() { return this.lastModifiedTimeasDate; }
/*    */   
/*    */   public void setLastModifiedTimeasDate(long lastModifiedTimeasDate) {
/* 21 */     Date d = new Date(lastModifiedTimeasDate);
/* 22 */     this.lastModifiedTimeasDate = d;
/*    */   }
/*    */   
/* 25 */   public long getLastModifiedTime() { return this.lastModifiedTime; }
/*    */   
/*    */   public void setLastModifiedTime(long lastModifiedTime) {
/* 28 */     this.lastModifiedTime = lastModifiedTime;
/*    */   }
/*    */   
/*    */ 
/*    */   public CustomFile(File file)
/*    */   {
/* 34 */     setFile(file);
/* 35 */     setFileName(file.getName());
/* 36 */     this.lastModifiedTime = file.lastModified();
/* 37 */     setLastModifiedTimeasDate(this.lastModifiedTime);
/*    */   }
/*    */   
/*    */   public String getFileName() {
/* 41 */     return this.fileName;
/*    */   }
/*    */   
/*    */   public void setFileName(String fileName) {
/* 45 */     this.fileName = fileName;
/*    */   }
/*    */   
/*    */   private CustomFile() {}
/*    */   
/*    */   public int compareTo(Object o)
/*    */   {
/* 52 */     long u = ((CustomFile)o).lastModifiedTime;
/* 53 */     return this.lastModifiedTime <= u ? 1 : -1;
/*    */   }
/*    */   
/* 56 */   public File getFile() { return this.file; }
/*    */   
/*    */   public void setFile(File file) {
/* 59 */     this.file = file;
/*    */   }
/*    */   
/* 62 */   public String getFilePath() { return this.filePath; }
/*    */   
/*    */   public void setFilePath(String filePath) {
/* 65 */     this.filePath = filePath;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\CustomFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */