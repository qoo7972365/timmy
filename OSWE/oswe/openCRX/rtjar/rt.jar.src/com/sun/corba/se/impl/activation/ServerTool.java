/*     */ package com.sun.corba.se.impl.activation;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.CorbaResourceUtil;
/*     */ import com.sun.corba.se.spi.activation.Repository;
/*     */ import com.sun.corba.se.spi.activation.RepositoryHelper;
/*     */ import com.sun.corba.se.spi.activation.ServerNotRegistered;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import org.omg.CORBA.ORB;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerTool
/*     */ {
/*     */   static final String helpCommand = "help";
/*     */   static final String toolName = "servertool";
/*     */   static final String commandArg = "-cmd";
/*     */   private static final boolean debug = false;
/*     */   
/*     */   static int getServerIdForAlias(ORB paramORB, String paramString) throws ServerNotRegistered {
/*     */     try {
/*  60 */       Repository repository = RepositoryHelper.narrow(paramORB
/*  61 */           .resolve_initial_references("ServerRepository"));
/*  62 */       int i = repository.getServerID(paramString);
/*     */       
/*  64 */       return repository.getServerID(paramString);
/*  65 */     } catch (Exception exception) {
/*  66 */       throw new ServerNotRegistered();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void run(String[] paramArrayOfString) {
/*  72 */     String[] arrayOfString = null;
/*     */ 
/*     */     
/*  75 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/*     */       
/*  77 */       if (paramArrayOfString[b].equals("-cmd")) {
/*     */         
/*  79 */         int i = paramArrayOfString.length - b - 1;
/*  80 */         arrayOfString = new String[i];
/*  81 */         for (byte b1 = 0; b1 < i; ) { arrayOfString[b1] = paramArrayOfString[++b]; b1++; }
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/*     */     try {
/*  89 */       Properties properties = System.getProperties();
/*  90 */       properties.put("org.omg.CORBA.ORBClass", "com.sun.corba.se.impl.orb.ORBImpl");
/*     */       
/*  92 */       this.orb = ORB.init(paramArrayOfString, properties);
/*     */ 
/*     */       
/*  95 */       if (arrayOfString != null) { executeCommand(arrayOfString); }
/*     */       
/*     */       else
/*     */       
/*  99 */       { BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
/*     */ 
/*     */ 
/*     */         
/* 103 */         System.out.println(CorbaResourceUtil.getText("servertool.banner"));
/*     */ 
/*     */         
/*     */         while (true) {
/* 107 */           arrayOfString = readCommand(bufferedReader);
/* 108 */           if (arrayOfString != null) { executeCommand(arrayOfString); continue; }
/* 109 */            printAvailableCommands();
/*     */         }  }
/*     */     
/* 112 */     } catch (Exception exception) {
/* 113 */       System.out.println(CorbaResourceUtil.getText("servertool.usage", "servertool"));
/* 114 */       System.out.println();
/* 115 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] paramArrayOfString) {
/* 121 */     ServerTool serverTool = new ServerTool();
/* 122 */     serverTool.run(paramArrayOfString);
/*     */   }
/*     */ 
/*     */   
/*     */   String[] readCommand(BufferedReader paramBufferedReader) {
/* 127 */     System.out.print("servertool > ");
/*     */     
/*     */     try {
/* 130 */       byte b = 0;
/* 131 */       String[] arrayOfString = null;
/*     */       
/* 133 */       String str = paramBufferedReader.readLine();
/*     */       
/* 135 */       if (str != null) {
/* 136 */         StringTokenizer stringTokenizer = new StringTokenizer(str);
/* 137 */         if (stringTokenizer.countTokens() != 0) {
/* 138 */           arrayOfString = new String[stringTokenizer.countTokens()];
/* 139 */           for (; stringTokenizer.hasMoreTokens(); arrayOfString[b++] = stringTokenizer.nextToken());
/*     */         } 
/*     */       } 
/*     */       
/* 143 */       return arrayOfString;
/* 144 */     } catch (Exception exception) {
/* 145 */       System.out.println(CorbaResourceUtil.getText("servertool.usage", "servertool"));
/* 146 */       System.out.println();
/* 147 */       exception.printStackTrace();
/*     */ 
/*     */       
/* 150 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void printAvailableCommands() {
/* 158 */     System.out.println(CorbaResourceUtil.getText("servertool.shorthelp"));
/*     */     
/* 160 */     for (byte b = 0; b < handlers.size(); b++) {
/* 161 */       CommandHandler commandHandler = handlers.elementAt(b);
/* 162 */       System.out.print("\t" + commandHandler.getCommandName());
/* 163 */       int i = commandHandler.getCommandName().length();
/* 164 */       while (i < maxNameLen) { System.out.print(" "); i++; }
/* 165 */        System.out.print(" - ");
/* 166 */       commandHandler.printCommandHelp(System.out, true);
/*     */     } 
/*     */ 
/*     */     
/* 170 */     System.out.println();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void executeCommand(String[] paramArrayOfString) {
/* 179 */     if (paramArrayOfString[0].equals("help")) {
/* 180 */       if (paramArrayOfString.length == 1) { printAvailableCommands(); }
/*     */       else
/*     */       
/* 183 */       { for (byte b1 = 0; b1 < handlers.size(); b1++) {
/* 184 */           CommandHandler commandHandler = handlers.elementAt(b1);
/* 185 */           if (commandHandler.getCommandName().equals(paramArrayOfString[1])) {
/* 186 */             commandHandler.printCommandHelp(System.out, false);
/*     */           }
/*     */         }  }
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 196 */     for (byte b = 0; b < handlers.size(); b++) {
/* 197 */       CommandHandler commandHandler = handlers.elementAt(b);
/* 198 */       if (commandHandler.getCommandName().equals(paramArrayOfString[0])) {
/* 199 */         String[] arrayOfString = new String[paramArrayOfString.length - 1];
/*     */ 
/*     */         
/* 202 */         for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/* 203 */           arrayOfString[b1] = paramArrayOfString[b1 + 1];
/*     */         }
/*     */         
/*     */         try {
/* 207 */           System.out.println();
/*     */           
/* 209 */           boolean bool = commandHandler.processCommand(arrayOfString, this.orb, System.out);
/*     */           
/* 211 */           if (bool == true) {
/* 212 */             commandHandler.printCommandHelp(System.out, false);
/*     */           }
/*     */ 
/*     */           
/* 216 */           System.out.println();
/*     */         }
/* 218 */         catch (Exception exception) {}
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 225 */     printAvailableCommands();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 230 */   ORB orb = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 236 */   static Vector handlers = new Vector(); static {
/* 237 */     handlers.addElement(new RegisterServer());
/* 238 */     handlers.addElement(new UnRegisterServer());
/* 239 */     handlers.addElement(new GetServerID());
/* 240 */     handlers.addElement(new ListServers());
/* 241 */     handlers.addElement(new ListAliases());
/* 242 */     handlers.addElement(new ListActiveServers());
/* 243 */     handlers.addElement(new LocateServer());
/* 244 */     handlers.addElement(new LocateServerForORB());
/* 245 */     handlers.addElement(new ListORBs());
/* 246 */     handlers.addElement(new ShutdownServer());
/* 247 */     handlers.addElement(new StartServer());
/* 248 */     handlers.addElement(new Help());
/* 249 */     handlers.addElement(new Quit());
/*     */   }
/*     */   
/* 252 */   static int maxNameLen = 0;
/*     */   static {
/* 254 */     for (byte b = 0; b < handlers.size(); b++) {
/* 255 */       CommandHandler commandHandler = handlers.elementAt(b);
/* 256 */       int i = commandHandler.getCommandName().length();
/* 257 */       if (i > maxNameLen) maxNameLen = i; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/activation/ServerTool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */