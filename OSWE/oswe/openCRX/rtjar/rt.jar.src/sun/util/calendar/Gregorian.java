/*    */ package sun.util.calendar;
/*    */ 
/*    */ import java.util.TimeZone;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Gregorian
/*    */   extends BaseCalendar
/*    */ {
/*    */   static class Date
/*    */     extends BaseCalendar.Date
/*    */   {
/*    */     protected Date() {}
/*    */     
/*    */     protected Date(TimeZone param1TimeZone) {
/* 45 */       super(param1TimeZone);
/*    */     }
/*    */     
/*    */     public int getNormalizedYear() {
/* 49 */       return getYear();
/*    */     }
/*    */     
/*    */     public void setNormalizedYear(int param1Int) {
/* 53 */       setYear(param1Int);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 61 */     return "gregorian";
/*    */   }
/*    */   
/*    */   public Date getCalendarDate() {
/* 65 */     return getCalendarDate(System.currentTimeMillis(), newCalendarDate());
/*    */   }
/*    */   
/*    */   public Date getCalendarDate(long paramLong) {
/* 69 */     return getCalendarDate(paramLong, newCalendarDate());
/*    */   }
/*    */   
/*    */   public Date getCalendarDate(long paramLong, CalendarDate paramCalendarDate) {
/* 73 */     return (Date)super.getCalendarDate(paramLong, paramCalendarDate);
/*    */   }
/*    */   
/*    */   public Date getCalendarDate(long paramLong, TimeZone paramTimeZone) {
/* 77 */     return getCalendarDate(paramLong, newCalendarDate(paramTimeZone));
/*    */   }
/*    */   
/*    */   public Date newCalendarDate() {
/* 81 */     return new Date();
/*    */   }
/*    */   
/*    */   public Date newCalendarDate(TimeZone paramTimeZone) {
/* 85 */     return new Date(paramTimeZone);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/calendar/Gregorian.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */