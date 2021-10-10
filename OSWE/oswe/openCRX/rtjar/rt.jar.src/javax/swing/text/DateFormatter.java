/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.text.DateFormat;
/*     */ import java.text.Format;
/*     */ import java.text.ParseException;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DateFormatter
/*     */   extends InternationalFormatter
/*     */ {
/*     */   public DateFormatter() {
/*  56 */     this(DateFormat.getDateInstance());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateFormatter(DateFormat paramDateFormat) {
/*  66 */     super(paramDateFormat);
/*  67 */     setFormat(paramDateFormat);
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
/*     */   public void setFormat(DateFormat paramDateFormat) {
/*  81 */     setFormat(paramDateFormat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Calendar getCalendar() {
/*  90 */     Format format = getFormat();
/*     */     
/*  92 */     if (format instanceof DateFormat) {
/*  93 */       return ((DateFormat)format).getCalendar();
/*     */     }
/*  95 */     return Calendar.getInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean getSupportsIncrement() {
/* 104 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Object getAdjustField(int paramInt, Map paramMap) {
/* 111 */     Iterator<Object> iterator = paramMap.keySet().iterator();
/*     */     
/* 113 */     while (iterator.hasNext()) {
/* 114 */       DateFormat.Field field = (DateFormat.Field)iterator.next();
/*     */       
/* 116 */       if (field instanceof DateFormat.Field && (field == DateFormat.Field.HOUR1 || field
/*     */         
/* 118 */         .getCalendarField() != -1)) {
/* 119 */         return field;
/*     */       }
/*     */     } 
/* 122 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Object adjustValue(Object paramObject1, Map paramMap, Object paramObject2, int paramInt) throws BadLocationException, ParseException {
/* 132 */     if (paramObject2 != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 137 */       if (paramObject2 == DateFormat.Field.HOUR1) {
/* 138 */         paramObject2 = DateFormat.Field.HOUR0;
/*     */       }
/* 140 */       int i = ((DateFormat.Field)paramObject2).getCalendarField();
/*     */       
/* 142 */       Calendar calendar = getCalendar();
/*     */       
/* 144 */       if (calendar != null) {
/* 145 */         calendar.setTime((Date)paramObject1);
/*     */         
/* 147 */         int j = calendar.get(i);
/*     */         
/*     */         try {
/* 150 */           calendar.add(i, paramInt);
/* 151 */           paramObject1 = calendar.getTime();
/* 152 */         } catch (Throwable throwable) {
/* 153 */           paramObject1 = null;
/*     */         } 
/* 155 */         return paramObject1;
/*     */       } 
/*     */     } 
/* 158 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/DateFormatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */