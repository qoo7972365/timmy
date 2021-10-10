/*     */ package javax.sql;
/*     */ 
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.util.EventObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StatementEvent
/*     */   extends EventObject
/*     */ {
/*     */   static final long serialVersionUID = -8089573731826608315L;
/*     */   private SQLException exception;
/*     */   private PreparedStatement statement;
/*     */   
/*     */   public StatementEvent(PooledConnection paramPooledConnection, PreparedStatement paramPreparedStatement) {
/*  65 */     super(paramPooledConnection);
/*     */     
/*  67 */     this.statement = paramPreparedStatement;
/*  68 */     this.exception = null;
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
/*     */   public StatementEvent(PooledConnection paramPooledConnection, PreparedStatement paramPreparedStatement, SQLException paramSQLException) {
/*  89 */     super(paramPooledConnection);
/*     */     
/*  91 */     this.statement = paramPreparedStatement;
/*  92 */     this.exception = paramSQLException;
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
/*     */   public PreparedStatement getStatement() {
/* 104 */     return this.statement;
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
/*     */   public SQLException getSQLException() {
/* 116 */     return this.exception;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sql/StatementEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */