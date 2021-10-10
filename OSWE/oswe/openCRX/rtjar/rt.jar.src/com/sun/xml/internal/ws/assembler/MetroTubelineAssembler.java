/*     */ package com.sun.xml.internal.ws.assembler;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.logging.Logger;
/*     */ import com.sun.xml.internal.ws.api.BindingID;
/*     */ import com.sun.xml.internal.ws.api.Component;
/*     */ import com.sun.xml.internal.ws.api.pipe.ClientTubeAssemblerContext;
/*     */ import com.sun.xml.internal.ws.api.pipe.ServerTubeAssemblerContext;
/*     */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*     */ import com.sun.xml.internal.ws.api.pipe.TubelineAssembler;
/*     */ import com.sun.xml.internal.ws.assembler.dev.TubelineAssemblyDecorator;
/*     */ import com.sun.xml.internal.ws.dump.LoggingDumpTube;
/*     */ import com.sun.xml.internal.ws.resources.TubelineassemblyMessages;
/*     */ import com.sun.xml.internal.ws.util.ServiceFinder;
/*     */ import java.util.Collection;
/*     */ import java.util.logging.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetroTubelineAssembler
/*     */   implements TubelineAssembler
/*     */ {
/*     */   private static final String COMMON_MESSAGE_DUMP_SYSTEM_PROPERTY_BASE = "com.sun.metro.soap.dump";
/*  51 */   public static final MetroConfigNameImpl JAXWS_TUBES_CONFIG_NAMES = new MetroConfigNameImpl("jaxws-tubes-default.xml", "jaxws-tubes.xml");
/*     */   
/*     */   private enum Side
/*     */   {
/*  55 */     Client("client"),
/*  56 */     Endpoint("endpoint");
/*     */     private final String name;
/*     */     
/*     */     Side(String name) {
/*  60 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  65 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class MessageDumpingInfo
/*     */   {
/*     */     final boolean dumpBefore;
/*     */     final boolean dumpAfter;
/*     */     final Level logLevel;
/*     */     
/*     */     MessageDumpingInfo(boolean dumpBefore, boolean dumpAfter, Level logLevel) {
/*  76 */       this.dumpBefore = dumpBefore;
/*  77 */       this.dumpAfter = dumpAfter;
/*  78 */       this.logLevel = logLevel;
/*     */     }
/*     */   }
/*     */   
/*  82 */   private static final Logger LOGGER = Logger.getLogger(MetroTubelineAssembler.class);
/*     */   private final BindingID bindingId;
/*     */   private final TubelineAssemblyController tubelineAssemblyController;
/*     */   
/*     */   public MetroTubelineAssembler(BindingID bindingId, MetroConfigName metroConfigName) {
/*  87 */     this.bindingId = bindingId;
/*  88 */     this.tubelineAssemblyController = new TubelineAssemblyController(metroConfigName);
/*     */   }
/*     */   
/*     */   TubelineAssemblyController getTubelineAssemblyController() {
/*  92 */     return this.tubelineAssemblyController;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Tube createClient(@NotNull ClientTubeAssemblerContext jaxwsContext) {
/*  97 */     if (LOGGER.isLoggable(Level.FINER)) {
/*  98 */       LOGGER.finer("Assembling client-side tubeline for WS endpoint: " + jaxwsContext.getAddress().getURI().toString());
/*     */     }
/*     */     
/* 101 */     DefaultClientTubelineAssemblyContext context = createClientContext(jaxwsContext);
/*     */     
/* 103 */     Collection<TubeCreator> tubeCreators = this.tubelineAssemblyController.getTubeCreators(context);
/*     */     
/* 105 */     for (TubeCreator tubeCreator : tubeCreators) {
/* 106 */       tubeCreator.updateContext(context);
/*     */     }
/*     */     
/* 109 */     TubelineAssemblyDecorator decorator = TubelineAssemblyDecorator.composite(
/* 110 */         (Iterable)ServiceFinder.find(TubelineAssemblyDecorator.class, (Component)context.getContainer()));
/*     */     
/* 112 */     boolean first = true;
/* 113 */     for (TubeCreator tubeCreator : tubeCreators) {
/* 114 */       MessageDumpingInfo msgDumpInfo = setupMessageDumping(tubeCreator.getMessageDumpPropertyBase(), Side.Client);
/*     */       
/* 116 */       Tube oldTubelineHead = context.getTubelineHead();
/* 117 */       LoggingDumpTube afterDumpTube = null;
/* 118 */       if (msgDumpInfo.dumpAfter) {
/* 119 */         afterDumpTube = new LoggingDumpTube(msgDumpInfo.logLevel, LoggingDumpTube.Position.After, context.getTubelineHead());
/* 120 */         context.setTubelineHead((Tube)afterDumpTube);
/*     */       } 
/*     */       
/* 123 */       if (!context.setTubelineHead(decorator.decorateClient(tubeCreator.createTube(context), context))) {
/* 124 */         if (afterDumpTube != null) {
/* 125 */           context.setTubelineHead(oldTubelineHead);
/*     */         }
/*     */       } else {
/* 128 */         String loggedTubeName = context.getTubelineHead().getClass().getName();
/* 129 */         if (afterDumpTube != null) {
/* 130 */           afterDumpTube.setLoggedTubeName(loggedTubeName);
/*     */         }
/*     */         
/* 133 */         if (msgDumpInfo.dumpBefore) {
/* 134 */           LoggingDumpTube beforeDumpTube = new LoggingDumpTube(msgDumpInfo.logLevel, LoggingDumpTube.Position.Before, context.getTubelineHead());
/* 135 */           beforeDumpTube.setLoggedTubeName(loggedTubeName);
/* 136 */           context.setTubelineHead((Tube)beforeDumpTube);
/*     */         } 
/*     */       } 
/*     */       
/* 140 */       if (first) {
/* 141 */         context.setTubelineHead(decorator.decorateClientTail(context.getTubelineHead(), context));
/* 142 */         first = false;
/*     */       } 
/*     */     } 
/*     */     
/* 146 */     return decorator.decorateClientHead(context.getTubelineHead(), context);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Tube createServer(@NotNull ServerTubeAssemblerContext jaxwsContext) {
/* 151 */     if (LOGGER.isLoggable(Level.FINER)) {
/* 152 */       LOGGER.finer("Assembling endpoint tubeline for WS endpoint: " + jaxwsContext.getEndpoint().getServiceName() + "::" + jaxwsContext.getEndpoint().getPortName());
/*     */     }
/*     */     
/* 155 */     DefaultServerTubelineAssemblyContext context = createServerContext(jaxwsContext);
/*     */ 
/*     */     
/* 158 */     Collection<TubeCreator> tubeCreators = this.tubelineAssemblyController.getTubeCreators(context);
/* 159 */     for (TubeCreator tubeCreator : tubeCreators) {
/* 160 */       tubeCreator.updateContext(context);
/*     */     }
/*     */     
/* 163 */     TubelineAssemblyDecorator decorator = TubelineAssemblyDecorator.composite(
/* 164 */         (Iterable)ServiceFinder.find(TubelineAssemblyDecorator.class, (Component)context.getEndpoint().getContainer()));
/*     */     
/* 166 */     boolean first = true;
/* 167 */     for (TubeCreator tubeCreator : tubeCreators) {
/* 168 */       MessageDumpingInfo msgDumpInfo = setupMessageDumping(tubeCreator.getMessageDumpPropertyBase(), Side.Endpoint);
/*     */       
/* 170 */       Tube oldTubelineHead = context.getTubelineHead();
/* 171 */       LoggingDumpTube afterDumpTube = null;
/* 172 */       if (msgDumpInfo.dumpAfter) {
/* 173 */         afterDumpTube = new LoggingDumpTube(msgDumpInfo.logLevel, LoggingDumpTube.Position.After, context.getTubelineHead());
/* 174 */         context.setTubelineHead((Tube)afterDumpTube);
/*     */       } 
/*     */       
/* 177 */       if (!context.setTubelineHead(decorator.decorateServer(tubeCreator.createTube(context), context))) {
/* 178 */         if (afterDumpTube != null) {
/* 179 */           context.setTubelineHead(oldTubelineHead);
/*     */         }
/*     */       } else {
/* 182 */         String loggedTubeName = context.getTubelineHead().getClass().getName();
/* 183 */         if (afterDumpTube != null) {
/* 184 */           afterDumpTube.setLoggedTubeName(loggedTubeName);
/*     */         }
/*     */         
/* 187 */         if (msgDumpInfo.dumpBefore) {
/* 188 */           LoggingDumpTube beforeDumpTube = new LoggingDumpTube(msgDumpInfo.logLevel, LoggingDumpTube.Position.Before, context.getTubelineHead());
/* 189 */           beforeDumpTube.setLoggedTubeName(loggedTubeName);
/* 190 */           context.setTubelineHead((Tube)beforeDumpTube);
/*     */         } 
/*     */       } 
/*     */       
/* 194 */       if (first) {
/* 195 */         context.setTubelineHead(decorator.decorateServerTail(context.getTubelineHead(), context));
/* 196 */         first = false;
/*     */       } 
/*     */     } 
/*     */     
/* 200 */     return decorator.decorateServerHead(context.getTubelineHead(), context);
/*     */   }
/*     */   
/*     */   private MessageDumpingInfo setupMessageDumping(String msgDumpSystemPropertyBase, Side side) {
/* 204 */     boolean dumpBefore = false;
/* 205 */     boolean dumpAfter = false;
/* 206 */     Level logLevel = Level.INFO;
/*     */ 
/*     */     
/* 209 */     Boolean value = getBooleanValue("com.sun.metro.soap.dump");
/* 210 */     if (value != null) {
/* 211 */       dumpBefore = value.booleanValue();
/* 212 */       dumpAfter = value.booleanValue();
/*     */     } 
/*     */     
/* 215 */     value = getBooleanValue("com.sun.metro.soap.dump.before");
/* 216 */     dumpBefore = (value != null) ? value.booleanValue() : dumpBefore;
/*     */     
/* 218 */     value = getBooleanValue("com.sun.metro.soap.dump.after");
/* 219 */     dumpAfter = (value != null) ? value.booleanValue() : dumpAfter;
/*     */     
/* 221 */     Level levelValue = getLevelValue("com.sun.metro.soap.dump.level");
/* 222 */     if (levelValue != null) {
/* 223 */       logLevel = levelValue;
/*     */     }
/*     */ 
/*     */     
/* 227 */     value = getBooleanValue("com.sun.metro.soap.dump." + side.toString());
/* 228 */     if (value != null) {
/* 229 */       dumpBefore = value.booleanValue();
/* 230 */       dumpAfter = value.booleanValue();
/*     */     } 
/*     */     
/* 233 */     value = getBooleanValue("com.sun.metro.soap.dump." + side.toString() + ".before");
/* 234 */     dumpBefore = (value != null) ? value.booleanValue() : dumpBefore;
/*     */     
/* 236 */     value = getBooleanValue("com.sun.metro.soap.dump." + side.toString() + ".after");
/* 237 */     dumpAfter = (value != null) ? value.booleanValue() : dumpAfter;
/*     */     
/* 239 */     levelValue = getLevelValue("com.sun.metro.soap.dump." + side.toString() + ".level");
/* 240 */     if (levelValue != null) {
/* 241 */       logLevel = levelValue;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 246 */     value = getBooleanValue(msgDumpSystemPropertyBase);
/* 247 */     if (value != null) {
/* 248 */       dumpBefore = value.booleanValue();
/* 249 */       dumpAfter = value.booleanValue();
/*     */     } 
/*     */     
/* 252 */     value = getBooleanValue(msgDumpSystemPropertyBase + ".before");
/* 253 */     dumpBefore = (value != null) ? value.booleanValue() : dumpBefore;
/*     */     
/* 255 */     value = getBooleanValue(msgDumpSystemPropertyBase + ".after");
/* 256 */     dumpAfter = (value != null) ? value.booleanValue() : dumpAfter;
/*     */     
/* 258 */     levelValue = getLevelValue(msgDumpSystemPropertyBase + ".level");
/* 259 */     if (levelValue != null) {
/* 260 */       logLevel = levelValue;
/*     */     }
/*     */ 
/*     */     
/* 264 */     msgDumpSystemPropertyBase = msgDumpSystemPropertyBase + "." + side.toString();
/*     */     
/* 266 */     value = getBooleanValue(msgDumpSystemPropertyBase);
/* 267 */     if (value != null) {
/* 268 */       dumpBefore = value.booleanValue();
/* 269 */       dumpAfter = value.booleanValue();
/*     */     } 
/*     */     
/* 272 */     value = getBooleanValue(msgDumpSystemPropertyBase + ".before");
/* 273 */     dumpBefore = (value != null) ? value.booleanValue() : dumpBefore;
/*     */     
/* 275 */     value = getBooleanValue(msgDumpSystemPropertyBase + ".after");
/* 276 */     dumpAfter = (value != null) ? value.booleanValue() : dumpAfter;
/*     */     
/* 278 */     levelValue = getLevelValue(msgDumpSystemPropertyBase + ".level");
/* 279 */     if (levelValue != null) {
/* 280 */       logLevel = levelValue;
/*     */     }
/*     */     
/* 283 */     return new MessageDumpingInfo(dumpBefore, dumpAfter, logLevel);
/*     */   }
/*     */   
/*     */   private Boolean getBooleanValue(String propertyName) {
/* 287 */     Boolean retVal = null;
/*     */     
/* 289 */     String stringValue = System.getProperty(propertyName);
/* 290 */     if (stringValue != null) {
/* 291 */       retVal = Boolean.valueOf(stringValue);
/* 292 */       LOGGER.fine(TubelineassemblyMessages.MASM_0018_MSG_LOGGING_SYSTEM_PROPERTY_SET_TO_VALUE(propertyName, retVal));
/*     */     } 
/*     */     
/* 295 */     return retVal;
/*     */   }
/*     */   
/*     */   private Level getLevelValue(String propertyName) {
/* 299 */     Level retVal = null;
/*     */     
/* 301 */     String stringValue = System.getProperty(propertyName);
/* 302 */     if (stringValue != null) {
/*     */       
/* 304 */       LOGGER.fine(TubelineassemblyMessages.MASM_0018_MSG_LOGGING_SYSTEM_PROPERTY_SET_TO_VALUE(propertyName, stringValue));
/*     */       try {
/* 306 */         retVal = Level.parse(stringValue);
/* 307 */       } catch (IllegalArgumentException ex) {
/* 308 */         LOGGER.warning(TubelineassemblyMessages.MASM_0019_MSG_LOGGING_SYSTEM_PROPERTY_ILLEGAL_VALUE(propertyName, stringValue), ex);
/*     */       } 
/*     */     } 
/*     */     
/* 312 */     return retVal;
/*     */   }
/*     */ 
/*     */   
/*     */   protected DefaultServerTubelineAssemblyContext createServerContext(ServerTubeAssemblerContext jaxwsContext) {
/* 317 */     return new DefaultServerTubelineAssemblyContext(jaxwsContext);
/*     */   }
/*     */ 
/*     */   
/*     */   protected DefaultClientTubelineAssemblyContext createClientContext(ClientTubeAssemblerContext jaxwsContext) {
/* 322 */     return new DefaultClientTubelineAssemblyContext(jaxwsContext);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/assembler/MetroTubelineAssembler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */