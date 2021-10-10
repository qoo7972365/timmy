/*     */ package javax.sql.rowset.spi;
/*     */ 
/*     */ import com.sun.rowset.internal.SyncResolverImpl;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SyncProviderException
/*     */   extends SQLException
/*     */ {
/*  72 */   private SyncResolver syncResolver = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final long serialVersionUID = -939908523620640692L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SyncProviderException() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SyncProviderException(String paramString) {
/*  88 */     super(paramString);
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
/*     */   public SyncProviderException(SyncResolver paramSyncResolver) {
/* 101 */     if (paramSyncResolver == null) {
/* 102 */       throw new IllegalArgumentException("Cannot instantiate a SyncProviderException with a null SyncResolver object");
/*     */     }
/*     */     
/* 105 */     this.syncResolver = paramSyncResolver;
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
/*     */   public SyncResolver getSyncResolver() {
/* 129 */     if (this.syncResolver != null) {
/* 130 */       return this.syncResolver;
/*     */     }
/*     */     try {
/* 133 */       this.syncResolver = new SyncResolverImpl();
/* 134 */     } catch (SQLException sQLException) {}
/*     */     
/* 136 */     return this.syncResolver;
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
/*     */   public void setSyncResolver(SyncResolver paramSyncResolver) {
/* 154 */     if (paramSyncResolver == null) {
/* 155 */       throw new IllegalArgumentException("Cannot set a null SyncResolver object");
/*     */     }
/*     */     
/* 158 */     this.syncResolver = paramSyncResolver;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sql/rowset/spi/SyncProviderException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */