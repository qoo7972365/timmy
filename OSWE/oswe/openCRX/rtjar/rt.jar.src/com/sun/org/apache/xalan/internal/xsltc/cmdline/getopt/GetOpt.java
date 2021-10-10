/*     */ package com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GetOpt
/*     */ {
/*     */   private Option theCurrentOption;
/*     */   private ListIterator theOptionsIterator;
/*     */   private List theOptions;
/*     */   private List theCmdArgs;
/*     */   private OptionMatcher theOptionMatcher;
/*     */   
/*     */   public void printOptions() {
/*     */     for (ListIterator<Option> it = this.theOptions.listIterator(); it.hasNext(); ) {
/*     */       Option opt = it.next();
/*     */       System.out.print("OPT =" + opt.getArgLetter());
/*     */       String arg = opt.getArgument();
/*     */       if (arg != null)
/*     */         System.out.print(" " + arg); 
/*     */       System.out.println();
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getNextOption() throws IllegalArgumentException, MissingOptArgException {
/*     */     int retval = -1;
/*     */     if (this.theOptionsIterator.hasNext()) {
/*     */       this.theCurrentOption = this.theOptionsIterator.next();
/*     */       char c = this.theCurrentOption.getArgLetter();
/*     */       boolean shouldHaveArg = this.theOptionMatcher.hasArg(c);
/*     */       String arg = this.theCurrentOption.getArgument();
/*     */       if (!this.theOptionMatcher.match(c)) {
/*     */         ErrorMsg msg = new ErrorMsg("ILLEGAL_CMDLINE_OPTION_ERR", new Character(c));
/*     */         throw new IllegalArgumentException(msg.toString());
/*     */       } 
/*     */       if (shouldHaveArg && arg == null) {
/*     */         ErrorMsg msg = new ErrorMsg("CMDLINE_OPT_MISSING_ARG_ERR", new Character(c));
/*     */         throw new MissingOptArgException(msg.toString());
/*     */       } 
/*     */       retval = c;
/*     */     } 
/*     */     return retval;
/*     */   }
/*     */   
/*     */   public String getOptionArg() {
/*     */     String retval = null;
/*     */     String tmp = this.theCurrentOption.getArgument();
/*     */     char c = this.theCurrentOption.getArgLetter();
/*     */     if (this.theOptionMatcher.hasArg(c))
/*     */       retval = tmp; 
/*     */     return retval;
/*     */   }
/*     */   
/*     */   public String[] getCmdArgs() {
/*     */     String[] retval = new String[this.theCmdArgs.size()];
/*     */     int i = 0;
/*     */     for (ListIterator<String> it = this.theCmdArgs.listIterator(); it.hasNext();)
/*     */       retval[i++] = it.next(); 
/*     */     return retval;
/*     */   }
/*     */   
/*     */   public GetOpt(String[] args, String optString) {
/* 204 */     this.theCurrentOption = null;
/*     */     
/* 206 */     this.theOptions = null;
/* 207 */     this.theCmdArgs = null;
/* 208 */     this.theOptionMatcher = null; this.theOptions = new ArrayList(); int currOptIndex = 0; this.theCmdArgs = new ArrayList(); this.theOptionMatcher = new OptionMatcher(optString); int i; for (i = 0; i < args.length; i++) { String token = args[i]; int tokenLength = token.length(); if (token.equals("--")) { currOptIndex = i + 1; break; }
/*     */        if (token.startsWith("-") && tokenLength == 2) { this.theOptions.add(new Option(token.charAt(1))); }
/*     */       else if (token.startsWith("-") && tokenLength > 2) { for (int j = 1; j < tokenLength; j++)
/*     */           this.theOptions.add(new Option(token.charAt(j)));  }
/*     */       else if (!token.startsWith("-")) { if (this.theOptions.size() == 0) { currOptIndex = i; break; }
/*     */          int indexoflast = 0; indexoflast = this.theOptions.size() - 1; Option op = this.theOptions.get(indexoflast); char opLetter = op.getArgLetter(); if (!op.hasArg() && this.theOptionMatcher.hasArg(opLetter)) { op.setArg(token); }
/*     */         else { currOptIndex = i; break; }
/*     */          }
/*     */        }
/*     */      this.theOptionsIterator = this.theOptions.listIterator(); for (i = currOptIndex; i < args.length; i++) {
/*     */       String token = args[i]; this.theCmdArgs.add(token);
/* 219 */     }  } class Option { private String theArgument = null; private char theArgLetter; public Option(char argLetter) {
/* 220 */       this.theArgLetter = argLetter;
/*     */     } public void setArg(String arg) {
/* 222 */       this.theArgument = arg;
/*     */     }
/* 224 */     public boolean hasArg() { return (this.theArgument != null); }
/* 225 */     public char getArgLetter() { return this.theArgLetter; } public String getArgument() {
/* 226 */       return this.theArgument;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class OptionMatcher
/*     */   {
/*     */     private String theOptString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public OptionMatcher(String optString) {
/* 256 */       this.theOptString = null;
/*     */       this.theOptString = optString;
/*     */     }
/*     */     
/*     */     public boolean match(char c) {
/*     */       boolean retval = false;
/*     */       if (this.theOptString.indexOf(c) != -1)
/*     */         retval = true; 
/*     */       return retval;
/*     */     }
/*     */     
/*     */     public boolean hasArg(char c) {
/*     */       boolean retval = false;
/*     */       int index = this.theOptString.indexOf(c) + 1;
/*     */       if (index == this.theOptString.length()) {
/*     */         retval = false;
/*     */       } else if (this.theOptString.charAt(index) == ':') {
/*     */         retval = true;
/*     */       } 
/*     */       return retval;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/cmdline/getopt/GetOpt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */