/*     */ package java.sql;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum JDBCType
/*     */   implements SQLType
/*     */ {
/*  39 */   BIT(Integer.valueOf(-7)),
/*     */ 
/*     */ 
/*     */   
/*  43 */   TINYINT(Integer.valueOf(-6)),
/*     */ 
/*     */ 
/*     */   
/*  47 */   SMALLINT(Integer.valueOf(5)),
/*     */ 
/*     */ 
/*     */   
/*  51 */   INTEGER(Integer.valueOf(4)),
/*     */ 
/*     */ 
/*     */   
/*  55 */   BIGINT(Integer.valueOf(-5)),
/*     */ 
/*     */ 
/*     */   
/*  59 */   FLOAT(Integer.valueOf(6)),
/*     */ 
/*     */ 
/*     */   
/*  63 */   REAL(Integer.valueOf(7)),
/*     */ 
/*     */ 
/*     */   
/*  67 */   DOUBLE(Integer.valueOf(8)),
/*     */ 
/*     */ 
/*     */   
/*  71 */   NUMERIC(Integer.valueOf(2)),
/*     */ 
/*     */ 
/*     */   
/*  75 */   DECIMAL(Integer.valueOf(3)),
/*     */ 
/*     */ 
/*     */   
/*  79 */   CHAR(Integer.valueOf(1)),
/*     */ 
/*     */ 
/*     */   
/*  83 */   VARCHAR(Integer.valueOf(12)),
/*     */ 
/*     */ 
/*     */   
/*  87 */   LONGVARCHAR(Integer.valueOf(-1)),
/*     */ 
/*     */ 
/*     */   
/*  91 */   DATE(Integer.valueOf(91)),
/*     */ 
/*     */ 
/*     */   
/*  95 */   TIME(Integer.valueOf(92)),
/*     */ 
/*     */ 
/*     */   
/*  99 */   TIMESTAMP(Integer.valueOf(93)),
/*     */ 
/*     */ 
/*     */   
/* 103 */   BINARY(Integer.valueOf(-2)),
/*     */ 
/*     */ 
/*     */   
/* 107 */   VARBINARY(Integer.valueOf(-3)),
/*     */ 
/*     */ 
/*     */   
/* 111 */   LONGVARBINARY(Integer.valueOf(-4)),
/*     */ 
/*     */ 
/*     */   
/* 115 */   NULL(Integer.valueOf(0)),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   OTHER(Integer.valueOf(1111)),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   JAVA_OBJECT(Integer.valueOf(2000)),
/*     */ 
/*     */ 
/*     */   
/* 131 */   DISTINCT(Integer.valueOf(2001)),
/*     */ 
/*     */ 
/*     */   
/* 135 */   STRUCT(Integer.valueOf(2002)),
/*     */ 
/*     */ 
/*     */   
/* 139 */   ARRAY(Integer.valueOf(2003)),
/*     */ 
/*     */ 
/*     */   
/* 143 */   BLOB(Integer.valueOf(2004)),
/*     */ 
/*     */ 
/*     */   
/* 147 */   CLOB(Integer.valueOf(2005)),
/*     */ 
/*     */ 
/*     */   
/* 151 */   REF(Integer.valueOf(2006)),
/*     */ 
/*     */ 
/*     */   
/* 155 */   DATALINK(Integer.valueOf(70)),
/*     */ 
/*     */ 
/*     */   
/* 159 */   BOOLEAN(Integer.valueOf(16)),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 166 */   ROWID(Integer.valueOf(-8)),
/*     */ 
/*     */ 
/*     */   
/* 170 */   NCHAR(Integer.valueOf(-15)),
/*     */ 
/*     */ 
/*     */   
/* 174 */   NVARCHAR(Integer.valueOf(-9)),
/*     */ 
/*     */ 
/*     */   
/* 178 */   LONGNVARCHAR(Integer.valueOf(-16)),
/*     */ 
/*     */ 
/*     */   
/* 182 */   NCLOB(Integer.valueOf(2011)),
/*     */ 
/*     */ 
/*     */   
/* 186 */   SQLXML(Integer.valueOf(2009)),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 193 */   REF_CURSOR(Integer.valueOf(2012)),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 198 */   TIME_WITH_TIMEZONE(Integer.valueOf(2013)),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 203 */   TIMESTAMP_WITH_TIMEZONE(Integer.valueOf(2014));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Integer type;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   JDBCType(Integer paramInteger) {
/* 217 */     this.type = paramInteger;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 225 */     return name();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVendor() {
/* 233 */     return "java.sql";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getVendorTypeNumber() {
/* 242 */     return this.type;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/sql/JDBCType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */