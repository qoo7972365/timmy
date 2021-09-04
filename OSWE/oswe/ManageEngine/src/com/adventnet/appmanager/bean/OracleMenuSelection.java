/*    */ package com.adventnet.appmanager.bean;
/*    */ 
/*    */ public class OracleMenuSelection {
/*  4 */   private String availmenu = null;
/*  5 */   private String instmenu = null;
/*  6 */   private String tspacemenu = null;
/*  7 */   private String dfmenu = null;
/*  8 */   private String sessmenu = null;
/*  9 */   private String sgamenu = null;
/* 10 */   private String reconfmenu = null;
/*    */   
/*    */   public void reset()
/*    */   {
/* 14 */     this.availmenu = "unselectedmenu";
/* 15 */     this.instmenu = "unselectedmenu";
/* 16 */     this.tspacemenu = "unselectedmenu";
/* 17 */     this.dfmenu = "unselectedmenu";
/* 18 */     this.sessmenu = "unselectedmenu";
/* 19 */     this.reconfmenu = "unselectedmenu";
/* 20 */     this.sgamenu = "unselectedmenu";
/*    */   }
/*    */   
/*    */   public void setmenu(String menu) {
/* 24 */     if (menu.equals("Availability")) {
/* 25 */       setavailmenu();
/*    */     }
/* 27 */     else if (menu.equals("Instance")) {
/* 28 */       setinstmenu();
/*    */     }
/* 30 */     else if (menu.equals("TableSpace")) {
/* 31 */       settspacemenu();
/*    */     }
/* 33 */     else if (menu.equals("Sga")) {
/* 34 */       setsgamenu();
/*    */     }
/* 36 */     else if (menu.equals("DataFiles")) {
/* 37 */       setdfmenu();
/*    */     }
/* 39 */     else if (menu.equals("Session")) {
/* 40 */       setsessmenu();
/*    */     }
/* 42 */     else if (menu.equals("Reconfigure")) {
/* 43 */       setreconfmenu();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setsgamenu()
/*    */   {
/* 51 */     this.sgamenu = "selectedmenu";
/*    */   }
/*    */   
/* 54 */   public String getsgamenu() { return this.sgamenu; }
/*    */   
/*    */ 
/*    */   public void setavailmenu()
/*    */   {
/* 59 */     this.availmenu = "selectedmenu";
/*    */   }
/*    */   
/* 62 */   public String getavailmenu() { return this.availmenu; }
/*    */   
/*    */   public void setinstmenu()
/*    */   {
/* 66 */     this.instmenu = "selectedmenu";
/*    */   }
/*    */   
/* 69 */   public String getinstmenu() { return this.instmenu; }
/*    */   
/*    */   public void settspacemenu()
/*    */   {
/* 73 */     this.tspacemenu = "selectedmenu";
/*    */   }
/*    */   
/* 76 */   public String gettspacemenu() { return this.tspacemenu; }
/*    */   
/*    */   public void setdfmenu()
/*    */   {
/* 80 */     this.dfmenu = "selectedmenu";
/*    */   }
/*    */   
/* 83 */   public String getdfmenu() { return this.dfmenu; }
/*    */   
/*    */   public void setsessmenu()
/*    */   {
/* 87 */     this.sessmenu = "selectedmenu";
/*    */   }
/*    */   
/* 90 */   public String getsessmenu() { return this.sessmenu; }
/*    */   
/*    */   public void setreconfmenu()
/*    */   {
/* 94 */     this.reconfmenu = "selectedmenu";
/*    */   }
/*    */   
/* 97 */   public String getreconfmenu() { return this.reconfmenu; }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\bean\OracleMenuSelection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */