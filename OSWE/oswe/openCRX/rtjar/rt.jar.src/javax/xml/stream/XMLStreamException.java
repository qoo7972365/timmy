/*     */ package javax.xml.stream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLStreamException
/*     */   extends Exception
/*     */ {
/*     */   protected Throwable nested;
/*     */   protected Location location;
/*     */   
/*     */   public XMLStreamException() {}
/*     */   
/*     */   public XMLStreamException(String msg) {
/*  58 */     super(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamException(Throwable th) {
/*  67 */     super(th);
/*  68 */     this.nested = th;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamException(String msg, Throwable th) {
/*  78 */     super(msg, th);
/*  79 */     this.nested = th;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamException(String msg, Location location, Throwable th) {
/*  90 */     super("ParseError at [row,col]:[" + location.getLineNumber() + "," + location
/*  91 */         .getColumnNumber() + "]\nMessage: " + msg);
/*     */     
/*  93 */     this.nested = th;
/*  94 */     this.location = location;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamException(String msg, Location location) {
/* 105 */     super("ParseError at [row,col]:[" + location.getLineNumber() + "," + location
/* 106 */         .getColumnNumber() + "]\nMessage: " + msg);
/*     */     
/* 108 */     this.location = location;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Throwable getNestedException() {
/* 118 */     return this.nested;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Location getLocation() {
/* 127 */     return this.location;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/stream/XMLStreamException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */