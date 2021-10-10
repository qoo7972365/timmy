/*     */ package com.oracle.webservices.internal.api.databinding;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.databinding.MetadataReader;
/*     */ import com.sun.xml.internal.ws.model.ExternalMetadataReader;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExternalMetadataFeature
/*     */   extends WebServiceFeature
/*     */ {
/*     */   private static final String ID = "com.oracle.webservices.internal.api.databinding.ExternalMetadataFeature";
/*     */   private boolean enabled = true;
/*     */   private List<String> resourceNames;
/*     */   private List<File> files;
/*     */   private MetadataReader reader;
/*     */   
/*     */   public void addResources(String... resourceNames) {
/*  61 */     if (this.resourceNames == null) {
/*  62 */       this.resourceNames = new ArrayList<>();
/*     */     }
/*  64 */     Collections.addAll(this.resourceNames, resourceNames);
/*     */   }
/*     */   public List<String> getResourceNames() {
/*  67 */     return this.resourceNames;
/*     */   }
/*     */   public void addFiles(File... files) {
/*  70 */     if (this.files == null) {
/*  71 */       this.files = new ArrayList<>();
/*     */     }
/*  73 */     Collections.addAll(this.files, files);
/*     */   }
/*     */   public List<File> getFiles() {
/*  76 */     return this.files;
/*     */   }
/*     */   public boolean isEnabled() {
/*  79 */     return this.enabled;
/*     */   }
/*     */   
/*     */   private void setEnabled(boolean x) {
/*  83 */     this.enabled = x;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getID() {
/*  88 */     return "com.oracle.webservices.internal.api.databinding.ExternalMetadataFeature";
/*     */   }
/*     */   
/*     */   public MetadataReader getMetadataReader(ClassLoader classLoader, boolean disableXmlSecurity) {
/*  92 */     if (this.reader != null && this.enabled) return this.reader; 
/*  93 */     return this.enabled ? (MetadataReader)new ExternalMetadataReader(this.files, this.resourceNames, classLoader, true, disableXmlSecurity) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  98 */     if (this == o) return true; 
/*  99 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 101 */     ExternalMetadataFeature that = (ExternalMetadataFeature)o;
/*     */     
/* 103 */     if (this.enabled != that.enabled) return false; 
/* 104 */     if ((this.files != null) ? !this.files.equals(that.files) : (that.files != null)) return false; 
/* 105 */     if ((this.resourceNames != null) ? !this.resourceNames.equals(that.resourceNames) : (that.resourceNames != null)) {
/* 106 */       return false;
/*     */     }
/* 108 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 113 */     int result = this.enabled ? 1 : 0;
/* 114 */     result = 31 * result + ((this.resourceNames != null) ? this.resourceNames.hashCode() : 0);
/* 115 */     result = 31 * result + ((this.files != null) ? this.files.hashCode() : 0);
/* 116 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 121 */     return "[" + getID() + ", enabled=" + this.enabled + ", resourceNames=" + this.resourceNames + ", files=" + this.files + ']';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Builder builder() {
/* 129 */     return new Builder(new ExternalMetadataFeature());
/*     */   }
/*     */   
/*     */   public static final class Builder {
/*     */     private final ExternalMetadataFeature o;
/*     */     
/*     */     Builder(ExternalMetadataFeature x) {
/* 136 */       this.o = x;
/*     */     }
/*     */     
/*     */     public ExternalMetadataFeature build() {
/* 140 */       return this.o;
/*     */     }
/*     */     
/*     */     public Builder addResources(String... res) {
/* 144 */       this.o.addResources(res);
/* 145 */       return this;
/*     */     }
/*     */     
/*     */     public Builder addFiles(File... files) {
/* 149 */       this.o.addFiles(files);
/* 150 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setEnabled(boolean enabled) {
/* 154 */       this.o.setEnabled(enabled);
/* 155 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setReader(MetadataReader r) {
/* 159 */       this.o.reader = r;
/* 160 */       return this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/oracle/webservices/internal/api/databinding/ExternalMetadataFeature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */