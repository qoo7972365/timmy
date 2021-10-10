/*    */ package com.sun.nio.file;
/*    */ 
/*    */ import java.nio.file.OpenOption;
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
/*    */ 
/*    */ 
/*    */ public enum ExtendedOpenOption
/*    */   implements OpenOption
/*    */ {
/* 41 */   NOSHARE_READ,
/*    */ 
/*    */ 
/*    */   
/* 45 */   NOSHARE_WRITE,
/*    */ 
/*    */ 
/*    */   
/* 49 */   NOSHARE_DELETE;
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/nio/file/ExtendedOpenOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */