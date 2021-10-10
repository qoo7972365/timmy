/*     */ package javax.sound.sampled;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Port
/*     */   extends Line
/*     */ {
/*     */   public static class Info
/*     */     extends Line.Info
/*     */   {
/*  73 */     public static final Info MICROPHONE = new Info(Port.class, "MICROPHONE", true);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     public static final Info LINE_IN = new Info(Port.class, "LINE_IN", true);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  83 */     public static final Info COMPACT_DISC = new Info(Port.class, "COMPACT_DISC", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  91 */     public static final Info SPEAKER = new Info(Port.class, "SPEAKER", false);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     public static final Info HEADPHONE = new Info(Port.class, "HEADPHONE", false);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     public static final Info LINE_OUT = new Info(Port.class, "LINE_OUT", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean isSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Info(Class<?> param1Class, String param1String, boolean param1Boolean) {
/* 132 */       super(param1Class);
/* 133 */       this.name = param1String;
/* 134 */       this.isSource = param1Boolean;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getName() {
/* 145 */       return this.name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isSource() {
/* 155 */       return this.isSource;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean matches(Line.Info param1Info) {
/* 166 */       if (!super.matches(param1Info)) {
/* 167 */         return false;
/*     */       }
/*     */       
/* 170 */       if (!this.name.equals(((Info)param1Info).getName())) {
/* 171 */         return false;
/*     */       }
/*     */       
/* 174 */       if (this.isSource != ((Info)param1Info).isSource()) {
/* 175 */         return false;
/*     */       }
/*     */       
/* 178 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final boolean equals(Object param1Object) {
/* 186 */       return super.equals(param1Object);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final int hashCode() {
/* 193 */       return super.hashCode();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final String toString() {
/* 204 */       return this.name + ((this.isSource == true) ? " source" : " target") + " port";
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sound/sampled/Port.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */