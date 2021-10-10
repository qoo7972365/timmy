/*     */ package javax.sql.rowset.serial;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SerialDatalink
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private URL url;
/*     */   private int baseType;
/*     */   private String baseTypeName;
/*     */   static final long serialVersionUID = 2826907821828733626L;
/*     */   
/*     */   public SerialDatalink(URL paramURL) throws SerialException {
/*  84 */     if (paramURL == null) {
/*  85 */       throw new SerialException("Cannot serialize empty URL instance");
/*     */     }
/*  87 */     this.url = paramURL;
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
/*     */   public URL getDatalink() throws SerialException {
/* 100 */     URL uRL = null;
/*     */     
/*     */     try {
/* 103 */       uRL = new URL(this.url.toString());
/* 104 */     } catch (MalformedURLException malformedURLException) {
/* 105 */       throw new SerialException("MalformedURLException: " + malformedURLException.getMessage());
/*     */     } 
/* 107 */     return uRL;
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
/*     */   public boolean equals(Object paramObject) {
/* 123 */     if (this == paramObject) {
/* 124 */       return true;
/*     */     }
/* 126 */     if (paramObject instanceof SerialDatalink) {
/* 127 */       SerialDatalink serialDatalink = (SerialDatalink)paramObject;
/* 128 */       return this.url.equals(serialDatalink.url);
/*     */     } 
/* 130 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 141 */     return 31 + this.url.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 151 */       return super.clone();
/*     */     }
/* 153 */     catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 155 */       throw new InternalError();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sql/rowset/serial/SerialDatalink.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */