/*     */ package sun.nio.cs;
/*     */ 
/*     */ import java.nio.charset.Charset;
/*     */ import sun.util.PreHashedMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StandardCharsets
/*     */   extends FastCharsetProvider
/*     */ {
/*  39 */   static final String[] aliases_US_ASCII = new String[] { "iso-ir-6", "ANSI_X3.4-1986", "ISO_646.irv:1991", "ASCII", "ISO646-US", "us", "IBM367", "cp367", "csASCII", "default", "646", "iso_646.irv:1983", "ANSI_X3.4-1968", "ascii7" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   static final String[] aliases_UTF_8 = new String[] { "UTF8", "unicode-1-1-utf-8" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   static final String[] aliases_CESU_8 = new String[] { "CESU8", "csCESU-8" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   static final String[] aliases_UTF_16 = new String[] { "UTF_16", "utf16", "unicode", "UnicodeBig" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   static final String[] aliases_UTF_16BE = new String[] { "UTF_16BE", "ISO-10646-UCS-2", "X-UTF-16BE", "UnicodeBigUnmarked" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   static final String[] aliases_UTF_16LE = new String[] { "UTF_16LE", "X-UTF-16LE", "UnicodeLittleUnmarked" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   static final String[] aliases_UTF_16LE_BOM = new String[] { "UnicodeLittle" };
/*     */ 
/*     */ 
/*     */   
/*  90 */   static final String[] aliases_UTF_32 = new String[] { "UTF_32", "UTF32" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   static final String[] aliases_UTF_32LE = new String[] { "UTF_32LE", "X-UTF-32LE" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   static final String[] aliases_UTF_32BE = new String[] { "UTF_32BE", "X-UTF-32BE" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   static final String[] aliases_UTF_32LE_BOM = new String[] { "UTF_32LE_BOM", "UTF-32LE-BOM" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   static final String[] aliases_UTF_32BE_BOM = new String[] { "UTF_32BE_BOM", "UTF-32BE-BOM" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   static final String[] aliases_ISO_8859_1 = new String[] { "iso-ir-100", "ISO_8859-1", "latin1", "l1", "IBM819", "cp819", "csISOLatin1", "819", "IBM-819", "ISO8859_1", "ISO_8859-1:1987", "ISO_8859_1", "8859_1", "ISO8859-1" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   static final String[] aliases_ISO_8859_2 = new String[] { "iso8859_2", "8859_2", "iso-ir-101", "ISO_8859-2", "ISO_8859-2:1987", "ISO8859-2", "latin2", "l2", "ibm912", "ibm-912", "cp912", "912", "csISOLatin2" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 148 */   static final String[] aliases_ISO_8859_4 = new String[] { "iso8859_4", "iso8859-4", "8859_4", "iso-ir-110", "ISO_8859-4", "ISO_8859-4:1988", "latin4", "l4", "ibm914", "ibm-914", "cp914", "914", "csISOLatin4" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 164 */   static final String[] aliases_ISO_8859_5 = new String[] { "iso8859_5", "8859_5", "iso-ir-144", "ISO_8859-5", "ISO_8859-5:1988", "ISO8859-5", "cyrillic", "ibm915", "ibm-915", "cp915", "915", "csISOLatinCyrillic" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 179 */   static final String[] aliases_ISO_8859_7 = new String[] { "iso8859_7", "8859_7", "iso-ir-126", "ISO_8859-7", "ISO_8859-7:1987", "ELOT_928", "ECMA-118", "greek", "greek8", "csISOLatinGreek", "sun_eu_greek", "ibm813", "ibm-813", "813", "cp813", "iso8859-7" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 198 */   static final String[] aliases_ISO_8859_9 = new String[] { "iso8859_9", "8859_9", "iso-ir-148", "ISO_8859-9", "ISO_8859-9:1989", "ISO8859-9", "latin5", "l5", "ibm920", "ibm-920", "920", "cp920", "csISOLatin5" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 214 */   static final String[] aliases_ISO_8859_13 = new String[] { "iso8859_13", "8859_13", "iso_8859-13", "ISO8859-13" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 221 */   static final String[] aliases_ISO_8859_15 = new String[] { "ISO_8859-15", "8859_15", "ISO-8859-15", "ISO8859_15", "ISO8859-15", "IBM923", "IBM-923", "cp923", "923", "LATIN0", "LATIN9", "L9", "csISOlatin0", "csISOlatin9", "ISO8859_15_FDIS" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 239 */   static final String[] aliases_KOI8_R = new String[] { "koi8_r", "koi8", "cskoi8r" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 245 */   static final String[] aliases_KOI8_U = new String[] { "koi8_u" };
/*     */ 
/*     */ 
/*     */   
/* 249 */   static final String[] aliases_MS1250 = new String[] { "cp1250", "cp5346" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 254 */   static final String[] aliases_MS1251 = new String[] { "cp1251", "cp5347", "ansi-1251" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 260 */   static final String[] aliases_MS1252 = new String[] { "cp1252", "cp5348" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 265 */   static final String[] aliases_MS1253 = new String[] { "cp1253", "cp5349" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 270 */   static final String[] aliases_MS1254 = new String[] { "cp1254", "cp5350" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 275 */   static final String[] aliases_MS1257 = new String[] { "cp1257", "cp5353" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 280 */   static final String[] aliases_IBM437 = new String[] { "cp437", "ibm437", "ibm-437", "437", "cspc8codepage437", "windows-437" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 289 */   static final String[] aliases_IBM737 = new String[] { "cp737", "ibm737", "ibm-737", "737" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 296 */   static final String[] aliases_IBM775 = new String[] { "cp775", "ibm775", "ibm-775", "775" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 303 */   static final String[] aliases_IBM850 = new String[] { "cp850", "ibm-850", "ibm850", "850", "cspc850multilingual" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 311 */   static final String[] aliases_IBM852 = new String[] { "cp852", "ibm852", "ibm-852", "852", "csPCp852" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 319 */   static final String[] aliases_IBM855 = new String[] { "cp855", "ibm-855", "ibm855", "855", "cspcp855" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 327 */   static final String[] aliases_IBM857 = new String[] { "cp857", "ibm857", "ibm-857", "857", "csIBM857" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 335 */   static final String[] aliases_IBM858 = new String[] { "cp858", "ccsid00858", "cp00858", "858", "PC-Multilingual-850+euro" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 343 */   static final String[] aliases_IBM862 = new String[] { "cp862", "ibm862", "ibm-862", "862", "csIBM862", "cspc862latinhebrew" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 352 */   static final String[] aliases_IBM866 = new String[] { "cp866", "ibm866", "ibm-866", "866", "csIBM866" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 360 */   static final String[] aliases_IBM874 = new String[] { "cp874", "ibm874", "ibm-874", "874" };
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class Aliases
/*     */     extends PreHashedMap<String>
/*     */   {
/*     */     private static final int ROWS = 1024;
/*     */     
/*     */     private static final int SIZE = 211;
/*     */     
/*     */     private static final int SHIFT = 0;
/*     */     
/*     */     private static final int MASK = 1023;
/*     */ 
/*     */     
/*     */     private Aliases() {
/* 377 */       super(1024, 211, 0, 1023);
/*     */     }
/*     */     
/*     */     protected void init(Object[] param1ArrayOfObject) {
/* 381 */       (new Object[2])[0] = "csisolatin0"; (new Object[2])[1] = "iso-8859-15"; param1ArrayOfObject[1] = new Object[2];
/* 382 */       (new Object[2])[0] = "csisolatin1"; (new Object[2])[1] = "iso-8859-1"; param1ArrayOfObject[2] = new Object[2];
/* 383 */       (new Object[2])[0] = "csisolatin2"; (new Object[2])[1] = "iso-8859-2"; param1ArrayOfObject[3] = new Object[2];
/* 384 */       (new Object[2])[0] = "csisolatin4"; (new Object[2])[1] = "iso-8859-4"; param1ArrayOfObject[5] = new Object[2];
/* 385 */       (new Object[2])[0] = "csisolatin5"; (new Object[2])[1] = "iso-8859-9"; param1ArrayOfObject[6] = new Object[2];
/* 386 */       (new Object[2])[0] = "csisolatin9"; (new Object[2])[1] = "iso-8859-15"; param1ArrayOfObject[10] = new Object[2];
/* 387 */       (new Object[2])[0] = "unicodelittle"; (new Object[2])[1] = "x-utf-16le-bom"; param1ArrayOfObject[19] = new Object[2];
/* 388 */       (new Object[2])[0] = "iso646-us"; (new Object[2])[1] = "us-ascii"; param1ArrayOfObject[24] = new Object[2];
/* 389 */       (new Object[2])[0] = "iso_8859-7:1987"; (new Object[2])[1] = "iso-8859-7"; param1ArrayOfObject[25] = new Object[2];
/* 390 */       (new Object[2])[0] = "912"; (new Object[2])[1] = "iso-8859-2"; param1ArrayOfObject[26] = new Object[2];
/* 391 */       (new Object[2])[0] = "914"; (new Object[2])[1] = "iso-8859-4"; param1ArrayOfObject[28] = new Object[2];
/* 392 */       (new Object[2])[0] = "915"; (new Object[2])[1] = "iso-8859-5"; param1ArrayOfObject[29] = new Object[2];
/* 393 */       (new Object[2])[0] = "920"; (new Object[2])[1] = "iso-8859-9"; param1ArrayOfObject[55] = new Object[2];
/* 394 */       (new Object[2])[0] = "923"; (new Object[2])[1] = "iso-8859-15"; param1ArrayOfObject[58] = new Object[2];
/* 395 */       (new Object[3])[0] = "csisolatincyrillic"; (new Object[3])[1] = "iso-8859-5"; (new Object[2])[0] = "8859_1"; (new Object[2])[1] = "iso-8859-1"; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[86] = new Object[3];
/*     */       
/* 397 */       (new Object[2])[0] = "8859_2"; (new Object[2])[1] = "iso-8859-2"; param1ArrayOfObject[87] = new Object[2];
/* 398 */       (new Object[2])[0] = "8859_4"; (new Object[2])[1] = "iso-8859-4"; param1ArrayOfObject[89] = new Object[2];
/* 399 */       (new Object[3])[0] = "813"; (new Object[3])[1] = "iso-8859-7"; (new Object[2])[0] = "8859_5"; (new Object[2])[1] = "iso-8859-5"; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[90] = new Object[3];
/*     */       
/* 401 */       (new Object[2])[0] = "8859_7"; (new Object[2])[1] = "iso-8859-7"; param1ArrayOfObject[92] = new Object[2];
/* 402 */       (new Object[2])[0] = "8859_9"; (new Object[2])[1] = "iso-8859-9"; param1ArrayOfObject[94] = new Object[2];
/* 403 */       (new Object[2])[0] = "iso_8859-1:1987"; (new Object[2])[1] = "iso-8859-1"; param1ArrayOfObject[95] = new Object[2];
/* 404 */       (new Object[2])[0] = "819"; (new Object[2])[1] = "iso-8859-1"; param1ArrayOfObject[96] = new Object[2];
/* 405 */       (new Object[2])[0] = "unicode-1-1-utf-8"; (new Object[2])[1] = "utf-8"; param1ArrayOfObject[106] = new Object[2];
/* 406 */       (new Object[2])[0] = "x-utf-16le"; (new Object[2])[1] = "utf-16le"; param1ArrayOfObject[121] = new Object[2];
/* 407 */       (new Object[2])[0] = "ecma-118"; (new Object[2])[1] = "iso-8859-7"; param1ArrayOfObject[125] = new Object[2];
/* 408 */       (new Object[2])[0] = "koi8_r"; (new Object[2])[1] = "koi8-r"; param1ArrayOfObject[134] = new Object[2];
/* 409 */       (new Object[2])[0] = "koi8_u"; (new Object[2])[1] = "koi8-u"; param1ArrayOfObject[137] = new Object[2];
/* 410 */       (new Object[2])[0] = "cp912"; (new Object[2])[1] = "iso-8859-2"; param1ArrayOfObject[141] = new Object[2];
/* 411 */       (new Object[2])[0] = "cp914"; (new Object[2])[1] = "iso-8859-4"; param1ArrayOfObject[143] = new Object[2];
/* 412 */       (new Object[2])[0] = "cp915"; (new Object[2])[1] = "iso-8859-5"; param1ArrayOfObject[144] = new Object[2];
/* 413 */       (new Object[2])[0] = "cp920"; (new Object[2])[1] = "iso-8859-9"; param1ArrayOfObject[170] = new Object[2];
/* 414 */       (new Object[2])[0] = "cp923"; (new Object[2])[1] = "iso-8859-15"; param1ArrayOfObject[173] = new Object[2];
/* 415 */       (new Object[2])[0] = "utf_32le_bom"; (new Object[2])[1] = "x-utf-32le-bom"; param1ArrayOfObject[177] = new Object[2];
/* 416 */       (new Object[2])[0] = "utf_16be"; (new Object[2])[1] = "utf-16be"; param1ArrayOfObject[192] = new Object[2];
/* 417 */       (new Object[3])[0] = "cspc8codepage437"; (new Object[3])[1] = "ibm437"; (new Object[2])[0] = "ansi-1251"; (new Object[2])[1] = "windows-1251"; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[199] = new Object[3];
/*     */       
/* 419 */       (new Object[2])[0] = "cp813"; (new Object[2])[1] = "iso-8859-7"; param1ArrayOfObject[205] = new Object[2];
/* 420 */       (new Object[3])[0] = "850"; (new Object[3])[1] = "ibm850"; (new Object[2])[0] = "cp819"; (new Object[2])[1] = "iso-8859-1"; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[211] = new Object[3];
/*     */       
/* 422 */       (new Object[2])[0] = "852"; (new Object[2])[1] = "ibm852"; param1ArrayOfObject[213] = new Object[2];
/* 423 */       (new Object[2])[0] = "855"; (new Object[2])[1] = "ibm855"; param1ArrayOfObject[216] = new Object[2];
/* 424 */       (new Object[3])[0] = "857"; (new Object[3])[1] = "ibm857"; (new Object[2])[0] = "iso-ir-6"; (new Object[2])[1] = "us-ascii"; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[218] = new Object[3];
/*     */       
/* 426 */       (new Object[3])[0] = "858"; (new Object[3])[1] = "ibm00858"; (new Object[2])[0] = "737"; (new Object[2])[1] = "x-ibm737"; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[219] = new Object[3];
/*     */       
/* 428 */       (new Object[2])[0] = "csascii"; (new Object[2])[1] = "us-ascii"; param1ArrayOfObject[225] = new Object[2];
/* 429 */       (new Object[2])[0] = "862"; (new Object[2])[1] = "ibm862"; param1ArrayOfObject[244] = new Object[2];
/* 430 */       (new Object[2])[0] = "866"; (new Object[2])[1] = "ibm866"; param1ArrayOfObject[248] = new Object[2];
/* 431 */       (new Object[2])[0] = "x-utf-32be"; (new Object[2])[1] = "utf-32be"; param1ArrayOfObject[253] = new Object[2];
/* 432 */       (new Object[2])[0] = "iso_8859-2:1987"; (new Object[2])[1] = "iso-8859-2"; param1ArrayOfObject[254] = new Object[2];
/* 433 */       (new Object[2])[0] = "unicodebig"; (new Object[2])[1] = "utf-16"; param1ArrayOfObject[259] = new Object[2];
/* 434 */       (new Object[2])[0] = "iso8859_15_fdis"; (new Object[2])[1] = "iso-8859-15"; param1ArrayOfObject[269] = new Object[2];
/* 435 */       (new Object[2])[0] = "874"; (new Object[2])[1] = "x-ibm874"; param1ArrayOfObject[277] = new Object[2];
/* 436 */       (new Object[2])[0] = "unicodelittleunmarked"; (new Object[2])[1] = "utf-16le"; param1ArrayOfObject[280] = new Object[2];
/* 437 */       (new Object[2])[0] = "iso8859_1"; (new Object[2])[1] = "iso-8859-1"; param1ArrayOfObject[283] = new Object[2];
/* 438 */       (new Object[2])[0] = "iso8859_2"; (new Object[2])[1] = "iso-8859-2"; param1ArrayOfObject[284] = new Object[2];
/* 439 */       (new Object[2])[0] = "iso8859_4"; (new Object[2])[1] = "iso-8859-4"; param1ArrayOfObject[286] = new Object[2];
/* 440 */       (new Object[2])[0] = "iso8859_5"; (new Object[2])[1] = "iso-8859-5"; param1ArrayOfObject[287] = new Object[2];
/* 441 */       (new Object[2])[0] = "iso8859_7"; (new Object[2])[1] = "iso-8859-7"; param1ArrayOfObject[289] = new Object[2];
/* 442 */       (new Object[2])[0] = "iso8859_9"; (new Object[2])[1] = "iso-8859-9"; param1ArrayOfObject[291] = new Object[2];
/* 443 */       (new Object[2])[0] = "ibm912"; (new Object[2])[1] = "iso-8859-2"; param1ArrayOfObject[294] = new Object[2];
/* 444 */       (new Object[2])[0] = "ibm914"; (new Object[2])[1] = "iso-8859-4"; param1ArrayOfObject[296] = new Object[2];
/* 445 */       (new Object[2])[0] = "ibm915"; (new Object[2])[1] = "iso-8859-5"; param1ArrayOfObject[297] = new Object[2];
/* 446 */       (new Object[2])[0] = "iso_8859-13"; (new Object[2])[1] = "iso-8859-13"; param1ArrayOfObject[305] = new Object[2];
/* 447 */       (new Object[2])[0] = "iso_8859-15"; (new Object[2])[1] = "iso-8859-15"; param1ArrayOfObject[307] = new Object[2];
/* 448 */       (new Object[3])[0] = "greek8"; (new Object[3])[1] = "iso-8859-7"; (new Object[2])[0] = "646"; (new Object[2])[1] = "us-ascii"; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[312] = new Object[3];
/*     */       
/* 450 */       (new Object[2])[0] = "ibm-912"; (new Object[2])[1] = "iso-8859-2"; param1ArrayOfObject[321] = new Object[2];
/* 451 */       (new Object[3])[0] = "ibm920"; (new Object[3])[1] = "iso-8859-9"; (new Object[2])[0] = "ibm-914"; (new Object[2])[1] = "iso-8859-4"; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[323] = new Object[3];
/*     */       
/* 453 */       (new Object[2])[0] = "ibm-915"; (new Object[2])[1] = "iso-8859-5"; param1ArrayOfObject[324] = new Object[2];
/* 454 */       (new Object[2])[0] = "l1"; (new Object[2])[1] = "iso-8859-1"; param1ArrayOfObject[325] = new Object[2];
/* 455 */       (new Object[3])[0] = "cp850"; (new Object[3])[1] = "ibm850"; (new Object[3])[0] = "ibm923"; (new Object[3])[1] = "iso-8859-15"; (new Object[2])[0] = "l2"; (new Object[2])[1] = "iso-8859-2"; (new Object[3])[2] = new Object[2]; (new Object[3])[2] = new Object[3]; param1ArrayOfObject[326] = new Object[3];
/*     */ 
/*     */       
/* 458 */       (new Object[2])[0] = "cyrillic"; (new Object[2])[1] = "iso-8859-5"; param1ArrayOfObject[327] = new Object[2];
/* 459 */       (new Object[3])[0] = "cp852"; (new Object[3])[1] = "ibm852"; (new Object[2])[0] = "l4"; (new Object[2])[1] = "iso-8859-4"; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[328] = new Object[3];
/*     */       
/* 461 */       (new Object[2])[0] = "l5"; (new Object[2])[1] = "iso-8859-9"; param1ArrayOfObject[329] = new Object[2];
/* 462 */       (new Object[2])[0] = "cp855"; (new Object[2])[1] = "ibm855"; param1ArrayOfObject[331] = new Object[2];
/* 463 */       (new Object[3])[0] = "cp857"; (new Object[3])[1] = "ibm857"; (new Object[2])[0] = "l9"; (new Object[2])[1] = "iso-8859-15"; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[333] = new Object[3];
/*     */       
/* 465 */       (new Object[3])[0] = "cp858"; (new Object[3])[1] = "ibm00858"; (new Object[2])[0] = "cp737"; (new Object[2])[1] = "x-ibm737"; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[334] = new Object[3];
/*     */       
/* 467 */       (new Object[2])[0] = "iso_8859_1"; (new Object[2])[1] = "iso-8859-1"; param1ArrayOfObject[336] = new Object[2];
/* 468 */       (new Object[2])[0] = "koi8"; (new Object[2])[1] = "koi8-r"; param1ArrayOfObject[339] = new Object[2];
/* 469 */       (new Object[2])[0] = "775"; (new Object[2])[1] = "ibm775"; param1ArrayOfObject[341] = new Object[2];
/* 470 */       (new Object[2])[0] = "iso_8859-9:1989"; (new Object[2])[1] = "iso-8859-9"; param1ArrayOfObject[345] = new Object[2];
/* 471 */       (new Object[2])[0] = "ibm-920"; (new Object[2])[1] = "iso-8859-9"; param1ArrayOfObject[350] = new Object[2];
/* 472 */       (new Object[2])[0] = "ibm-923"; (new Object[2])[1] = "iso-8859-15"; param1ArrayOfObject[353] = new Object[2];
/* 473 */       (new Object[2])[0] = "ibm813"; (new Object[2])[1] = "iso-8859-7"; param1ArrayOfObject[358] = new Object[2];
/* 474 */       (new Object[2])[0] = "cp862"; (new Object[2])[1] = "ibm862"; param1ArrayOfObject[359] = new Object[2];
/* 475 */       (new Object[2])[0] = "cp866"; (new Object[2])[1] = "ibm866"; param1ArrayOfObject[363] = new Object[2];
/* 476 */       (new Object[2])[0] = "ibm819"; (new Object[2])[1] = "iso-8859-1"; param1ArrayOfObject[364] = new Object[2];
/* 477 */       (new Object[2])[0] = "ansi_x3.4-1968"; (new Object[2])[1] = "us-ascii"; param1ArrayOfObject[378] = new Object[2];
/* 478 */       (new Object[2])[0] = "ibm-813"; (new Object[2])[1] = "iso-8859-7"; param1ArrayOfObject[385] = new Object[2];
/* 479 */       (new Object[2])[0] = "ibm-819"; (new Object[2])[1] = "iso-8859-1"; param1ArrayOfObject[391] = new Object[2];
/* 480 */       (new Object[2])[0] = "cp874"; (new Object[2])[1] = "x-ibm874"; param1ArrayOfObject[392] = new Object[2];
/* 481 */       (new Object[2])[0] = "iso-ir-100"; (new Object[2])[1] = "iso-8859-1"; param1ArrayOfObject[405] = new Object[2];
/* 482 */       (new Object[2])[0] = "iso-ir-101"; (new Object[2])[1] = "iso-8859-2"; param1ArrayOfObject[406] = new Object[2];
/* 483 */       (new Object[2])[0] = "437"; (new Object[2])[1] = "ibm437"; param1ArrayOfObject[408] = new Object[2];
/* 484 */       (new Object[2])[0] = "iso-8859-15"; (new Object[2])[1] = "iso-8859-15"; param1ArrayOfObject[421] = new Object[2];
/* 485 */       (new Object[2])[0] = "latin0"; (new Object[2])[1] = "iso-8859-15"; param1ArrayOfObject[428] = new Object[2];
/* 486 */       (new Object[2])[0] = "latin1"; (new Object[2])[1] = "iso-8859-1"; param1ArrayOfObject[429] = new Object[2];
/* 487 */       (new Object[2])[0] = "latin2"; (new Object[2])[1] = "iso-8859-2"; param1ArrayOfObject[430] = new Object[2];
/* 488 */       (new Object[2])[0] = "latin4"; (new Object[2])[1] = "iso-8859-4"; param1ArrayOfObject[432] = new Object[2];
/* 489 */       (new Object[2])[0] = "latin5"; (new Object[2])[1] = "iso-8859-9"; param1ArrayOfObject[433] = new Object[2];
/* 490 */       (new Object[2])[0] = "iso-ir-110"; (new Object[2])[1] = "iso-8859-4"; param1ArrayOfObject[436] = new Object[2];
/* 491 */       (new Object[2])[0] = "latin9"; (new Object[2])[1] = "iso-8859-15"; param1ArrayOfObject[437] = new Object[2];
/* 492 */       (new Object[2])[0] = "ansi_x3.4-1986"; (new Object[2])[1] = "us-ascii"; param1ArrayOfObject[438] = new Object[2];
/* 493 */       (new Object[2])[0] = "utf-32be-bom"; (new Object[2])[1] = "x-utf-32be-bom"; param1ArrayOfObject[443] = new Object[2];
/* 494 */       (new Object[2])[0] = "cp775"; (new Object[2])[1] = "ibm775"; param1ArrayOfObject[456] = new Object[2];
/* 495 */       (new Object[2])[0] = "iso-ir-126"; (new Object[2])[1] = "iso-8859-7"; param1ArrayOfObject[473] = new Object[2];
/* 496 */       (new Object[2])[0] = "ibm850"; (new Object[2])[1] = "ibm850"; param1ArrayOfObject[479] = new Object[2];
/* 497 */       (new Object[2])[0] = "ibm852"; (new Object[2])[1] = "ibm852"; param1ArrayOfObject[481] = new Object[2];
/* 498 */       (new Object[2])[0] = "ibm855"; (new Object[2])[1] = "ibm855"; param1ArrayOfObject[484] = new Object[2];
/* 499 */       (new Object[2])[0] = "ibm857"; (new Object[2])[1] = "ibm857"; param1ArrayOfObject[486] = new Object[2];
/* 500 */       (new Object[2])[0] = "ibm737"; (new Object[2])[1] = "x-ibm737"; param1ArrayOfObject[487] = new Object[2];
/* 501 */       (new Object[2])[0] = "utf_16le"; (new Object[2])[1] = "utf-16le"; param1ArrayOfObject[502] = new Object[2];
/* 502 */       (new Object[2])[0] = "ibm-850"; (new Object[2])[1] = "ibm850"; param1ArrayOfObject[506] = new Object[2];
/* 503 */       (new Object[2])[0] = "ibm-852"; (new Object[2])[1] = "ibm852"; param1ArrayOfObject[508] = new Object[2];
/* 504 */       (new Object[2])[0] = "ibm-855"; (new Object[2])[1] = "ibm855"; param1ArrayOfObject[511] = new Object[2];
/* 505 */       (new Object[2])[0] = "ibm862"; (new Object[2])[1] = "ibm862"; param1ArrayOfObject[512] = new Object[2];
/* 506 */       (new Object[2])[0] = "ibm-857"; (new Object[2])[1] = "ibm857"; param1ArrayOfObject[513] = new Object[2];
/* 507 */       (new Object[2])[0] = "ibm-737"; (new Object[2])[1] = "x-ibm737"; param1ArrayOfObject[514] = new Object[2];
/* 508 */       (new Object[2])[0] = "ibm866"; (new Object[2])[1] = "ibm866"; param1ArrayOfObject[516] = new Object[2];
/* 509 */       (new Object[2])[0] = "unicodebigunmarked"; (new Object[2])[1] = "utf-16be"; param1ArrayOfObject[520] = new Object[2];
/* 510 */       (new Object[2])[0] = "cp437"; (new Object[2])[1] = "ibm437"; param1ArrayOfObject[523] = new Object[2];
/* 511 */       (new Object[2])[0] = "utf16"; (new Object[2])[1] = "utf-16"; param1ArrayOfObject[524] = new Object[2];
/* 512 */       (new Object[2])[0] = "iso-ir-144"; (new Object[2])[1] = "iso-8859-5"; param1ArrayOfObject[533] = new Object[2];
/* 513 */       (new Object[2])[0] = "iso-ir-148"; (new Object[2])[1] = "iso-8859-9"; param1ArrayOfObject[537] = new Object[2];
/* 514 */       (new Object[2])[0] = "ibm-862"; (new Object[2])[1] = "ibm862"; param1ArrayOfObject[539] = new Object[2];
/* 515 */       (new Object[2])[0] = "ibm-866"; (new Object[2])[1] = "ibm866"; param1ArrayOfObject[543] = new Object[2];
/* 516 */       (new Object[2])[0] = "ibm874"; (new Object[2])[1] = "x-ibm874"; param1ArrayOfObject[545] = new Object[2];
/* 517 */       (new Object[2])[0] = "x-utf-32le"; (new Object[2])[1] = "utf-32le"; param1ArrayOfObject[563] = new Object[2];
/* 518 */       (new Object[2])[0] = "ibm-874"; (new Object[2])[1] = "x-ibm874"; param1ArrayOfObject[572] = new Object[2];
/* 519 */       (new Object[2])[0] = "iso_8859-4:1988"; (new Object[2])[1] = "iso-8859-4"; param1ArrayOfObject[573] = new Object[2];
/* 520 */       (new Object[2])[0] = "default"; (new Object[2])[1] = "us-ascii"; param1ArrayOfObject[577] = new Object[2];
/* 521 */       (new Object[2])[0] = "utf32"; (new Object[2])[1] = "utf-32"; param1ArrayOfObject[582] = new Object[2];
/* 522 */       (new Object[2])[0] = "pc-multilingual-850+euro"; (new Object[2])[1] = "ibm00858"; param1ArrayOfObject[583] = new Object[2];
/* 523 */       (new Object[2])[0] = "elot_928"; (new Object[2])[1] = "iso-8859-7"; param1ArrayOfObject[588] = new Object[2];
/* 524 */       (new Object[2])[0] = "csisolatingreek"; (new Object[2])[1] = "iso-8859-7"; param1ArrayOfObject[593] = new Object[2];
/* 525 */       (new Object[2])[0] = "csibm857"; (new Object[2])[1] = "ibm857"; param1ArrayOfObject[598] = new Object[2];
/* 526 */       (new Object[2])[0] = "ibm775"; (new Object[2])[1] = "ibm775"; param1ArrayOfObject[609] = new Object[2];
/* 527 */       (new Object[2])[0] = "cp1250"; (new Object[2])[1] = "windows-1250"; param1ArrayOfObject[617] = new Object[2];
/* 528 */       (new Object[2])[0] = "cp1251"; (new Object[2])[1] = "windows-1251"; param1ArrayOfObject[618] = new Object[2];
/* 529 */       (new Object[2])[0] = "cp1252"; (new Object[2])[1] = "windows-1252"; param1ArrayOfObject[619] = new Object[2];
/* 530 */       (new Object[2])[0] = "cp1253"; (new Object[2])[1] = "windows-1253"; param1ArrayOfObject[620] = new Object[2];
/* 531 */       (new Object[2])[0] = "cp1254"; (new Object[2])[1] = "windows-1254"; param1ArrayOfObject[621] = new Object[2];
/* 532 */       (new Object[3])[0] = "csibm862"; (new Object[3])[1] = "ibm862"; (new Object[2])[0] = "cp1257"; (new Object[2])[1] = "windows-1257"; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[624] = new Object[3];
/*     */       
/* 534 */       (new Object[3])[0] = "csibm866"; (new Object[3])[1] = "ibm866"; (new Object[2])[0] = "cesu8"; (new Object[2])[1] = "cesu-8"; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[628] = new Object[3];
/*     */       
/* 536 */       (new Object[2])[0] = "iso8859_13"; (new Object[2])[1] = "iso-8859-13"; param1ArrayOfObject[632] = new Object[2];
/* 537 */       (new Object[3])[0] = "iso8859_15"; (new Object[3])[1] = "iso-8859-15"; (new Object[2])[0] = "utf_32be"; (new Object[2])[1] = "utf-32be"; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[634] = new Object[3];
/*     */       
/* 539 */       (new Object[2])[0] = "utf_32be_bom"; (new Object[2])[1] = "x-utf-32be-bom"; param1ArrayOfObject[635] = new Object[2];
/* 540 */       (new Object[2])[0] = "ibm-775"; (new Object[2])[1] = "ibm775"; param1ArrayOfObject[636] = new Object[2];
/* 541 */       (new Object[2])[0] = "cp00858"; (new Object[2])[1] = "ibm00858"; param1ArrayOfObject[654] = new Object[2];
/* 542 */       (new Object[2])[0] = "8859_13"; (new Object[2])[1] = "iso-8859-13"; param1ArrayOfObject[669] = new Object[2];
/* 543 */       (new Object[2])[0] = "us"; (new Object[2])[1] = "us-ascii"; param1ArrayOfObject[670] = new Object[2];
/* 544 */       (new Object[2])[0] = "8859_15"; (new Object[2])[1] = "iso-8859-15"; param1ArrayOfObject[671] = new Object[2];
/* 545 */       (new Object[2])[0] = "ibm437"; (new Object[2])[1] = "ibm437"; param1ArrayOfObject[676] = new Object[2];
/* 546 */       (new Object[2])[0] = "cp367"; (new Object[2])[1] = "us-ascii"; param1ArrayOfObject[679] = new Object[2];
/* 547 */       (new Object[2])[0] = "iso-10646-ucs-2"; (new Object[2])[1] = "utf-16be"; param1ArrayOfObject[686] = new Object[2];
/* 548 */       (new Object[2])[0] = "ibm-437"; (new Object[2])[1] = "ibm437"; param1ArrayOfObject[703] = new Object[2];
/* 549 */       (new Object[2])[0] = "iso8859-13"; (new Object[2])[1] = "iso-8859-13"; param1ArrayOfObject[710] = new Object[2];
/* 550 */       (new Object[2])[0] = "iso8859-15"; (new Object[2])[1] = "iso-8859-15"; param1ArrayOfObject[712] = new Object[2];
/* 551 */       (new Object[2])[0] = "iso_8859-5:1988"; (new Object[2])[1] = "iso-8859-5"; param1ArrayOfObject[732] = new Object[2];
/* 552 */       (new Object[2])[0] = "unicode"; (new Object[2])[1] = "utf-16"; param1ArrayOfObject[733] = new Object[2];
/* 553 */       (new Object[2])[0] = "greek"; (new Object[2])[1] = "iso-8859-7"; param1ArrayOfObject[768] = new Object[2];
/* 554 */       (new Object[2])[0] = "ascii7"; (new Object[2])[1] = "us-ascii"; param1ArrayOfObject[774] = new Object[2];
/* 555 */       (new Object[2])[0] = "iso8859-1"; (new Object[2])[1] = "iso-8859-1"; param1ArrayOfObject[781] = new Object[2];
/* 556 */       (new Object[2])[0] = "iso8859-2"; (new Object[2])[1] = "iso-8859-2"; param1ArrayOfObject[782] = new Object[2];
/* 557 */       (new Object[2])[0] = "cskoi8r"; (new Object[2])[1] = "koi8-r"; param1ArrayOfObject[783] = new Object[2];
/* 558 */       (new Object[2])[0] = "iso8859-4"; (new Object[2])[1] = "iso-8859-4"; param1ArrayOfObject[784] = new Object[2];
/* 559 */       (new Object[2])[0] = "iso8859-5"; (new Object[2])[1] = "iso-8859-5"; param1ArrayOfObject[785] = new Object[2];
/* 560 */       (new Object[2])[0] = "iso8859-7"; (new Object[2])[1] = "iso-8859-7"; param1ArrayOfObject[787] = new Object[2];
/* 561 */       (new Object[2])[0] = "iso8859-9"; (new Object[2])[1] = "iso-8859-9"; param1ArrayOfObject[789] = new Object[2];
/* 562 */       (new Object[2])[0] = "ccsid00858"; (new Object[2])[1] = "ibm00858"; param1ArrayOfObject[813] = new Object[2];
/* 563 */       (new Object[2])[0] = "cspc862latinhebrew"; (new Object[2])[1] = "ibm862"; param1ArrayOfObject[818] = new Object[2];
/* 564 */       (new Object[2])[0] = "ibm367"; (new Object[2])[1] = "us-ascii"; param1ArrayOfObject[832] = new Object[2];
/* 565 */       (new Object[2])[0] = "iso_8859-1"; (new Object[2])[1] = "iso-8859-1"; param1ArrayOfObject[834] = new Object[2];
/* 566 */       (new Object[3])[0] = "iso_8859-2"; (new Object[3])[1] = "iso-8859-2"; (new Object[2])[0] = "x-utf-16be"; (new Object[2])[1] = "utf-16be"; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[835] = new Object[3];
/*     */       
/* 568 */       (new Object[2])[0] = "sun_eu_greek"; (new Object[2])[1] = "iso-8859-7"; param1ArrayOfObject[836] = new Object[2];
/* 569 */       (new Object[2])[0] = "iso_8859-4"; (new Object[2])[1] = "iso-8859-4"; param1ArrayOfObject[837] = new Object[2];
/* 570 */       (new Object[2])[0] = "iso_8859-5"; (new Object[2])[1] = "iso-8859-5"; param1ArrayOfObject[838] = new Object[2];
/* 571 */       (new Object[3])[0] = "cspcp852"; (new Object[3])[1] = "ibm852"; (new Object[2])[0] = "iso_8859-7"; (new Object[2])[1] = "iso-8859-7"; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[840] = new Object[3];
/*     */       
/* 573 */       (new Object[2])[0] = "iso_8859-9"; (new Object[2])[1] = "iso-8859-9"; param1ArrayOfObject[842] = new Object[2];
/* 574 */       (new Object[2])[0] = "cspcp855"; (new Object[2])[1] = "ibm855"; param1ArrayOfObject[843] = new Object[2];
/* 575 */       (new Object[2])[0] = "windows-437"; (new Object[2])[1] = "ibm437"; param1ArrayOfObject[846] = new Object[2];
/* 576 */       (new Object[2])[0] = "ascii"; (new Object[2])[1] = "us-ascii"; param1ArrayOfObject[849] = new Object[2];
/* 577 */       (new Object[2])[0] = "cscesu-8"; (new Object[2])[1] = "cesu-8"; param1ArrayOfObject[863] = new Object[2];
/* 578 */       (new Object[2])[0] = "utf8"; (new Object[2])[1] = "utf-8"; param1ArrayOfObject[881] = new Object[2];
/* 579 */       (new Object[2])[0] = "iso_646.irv:1983"; (new Object[2])[1] = "us-ascii"; param1ArrayOfObject[896] = new Object[2];
/* 580 */       (new Object[2])[0] = "cp5346"; (new Object[2])[1] = "windows-1250"; param1ArrayOfObject[909] = new Object[2];
/* 581 */       (new Object[2])[0] = "cp5347"; (new Object[2])[1] = "windows-1251"; param1ArrayOfObject[910] = new Object[2];
/* 582 */       (new Object[2])[0] = "cp5348"; (new Object[2])[1] = "windows-1252"; param1ArrayOfObject[911] = new Object[2];
/* 583 */       (new Object[2])[0] = "cp5349"; (new Object[2])[1] = "windows-1253"; param1ArrayOfObject[912] = new Object[2];
/* 584 */       (new Object[2])[0] = "iso_646.irv:1991"; (new Object[2])[1] = "us-ascii"; param1ArrayOfObject[925] = new Object[2];
/* 585 */       (new Object[2])[0] = "cp5350"; (new Object[2])[1] = "windows-1254"; param1ArrayOfObject[934] = new Object[2];
/* 586 */       (new Object[2])[0] = "cp5353"; (new Object[2])[1] = "windows-1257"; param1ArrayOfObject[937] = new Object[2];
/* 587 */       (new Object[2])[0] = "utf_32le"; (new Object[2])[1] = "utf-32le"; param1ArrayOfObject[944] = new Object[2];
/* 588 */       (new Object[2])[0] = "utf_16"; (new Object[2])[1] = "utf-16"; param1ArrayOfObject[957] = new Object[2];
/* 589 */       (new Object[2])[0] = "cspc850multilingual"; (new Object[2])[1] = "ibm850"; param1ArrayOfObject[993] = new Object[2];
/* 590 */       (new Object[2])[0] = "utf-32le-bom"; (new Object[2])[1] = "x-utf-32le-bom"; param1ArrayOfObject[1009] = new Object[2];
/* 591 */       (new Object[2])[0] = "utf_32"; (new Object[2])[1] = "utf-32"; param1ArrayOfObject[1015] = new Object[2];
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class Classes
/*     */     extends PreHashedMap<String>
/*     */   {
/*     */     private static final int ROWS = 32;
/*     */     
/*     */     private static final int SIZE = 39;
/*     */     private static final int SHIFT = 1;
/*     */     private static final int MASK = 31;
/*     */     
/*     */     private Classes() {
/* 606 */       super(32, 39, 1, 31);
/*     */     }
/*     */     
/*     */     protected void init(Object[] param1ArrayOfObject) {
/* 610 */       (new Object[2])[0] = "ibm862"; (new Object[2])[1] = "IBM862"; param1ArrayOfObject[0] = new Object[2];
/* 611 */       (new Object[3])[0] = "ibm866"; (new Object[3])[1] = "IBM866"; (new Object[3])[0] = "utf-32"; (new Object[3])[1] = "UTF_32"; (new Object[2])[0] = "utf-16le"; (new Object[2])[1] = "UTF_16LE"; (new Object[3])[2] = new Object[2]; (new Object[3])[2] = new Object[3]; param1ArrayOfObject[2] = new Object[3];
/*     */ 
/*     */       
/* 614 */       (new Object[3])[0] = "windows-1251"; (new Object[3])[1] = "MS1251"; (new Object[2])[0] = "windows-1250"; (new Object[2])[1] = "MS1250"; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[3] = new Object[3];
/*     */       
/* 616 */       (new Object[3])[0] = "windows-1253"; (new Object[3])[1] = "MS1253"; (new Object[3])[0] = "windows-1252"; (new Object[3])[1] = "MS1252"; (new Object[2])[0] = "utf-32be"; (new Object[2])[1] = "UTF_32BE"; (new Object[3])[2] = new Object[2]; (new Object[3])[2] = new Object[3]; param1ArrayOfObject[4] = new Object[3];
/*     */ 
/*     */       
/* 619 */       (new Object[3])[0] = "windows-1254"; (new Object[3])[1] = "MS1254"; (new Object[2])[0] = "utf-16"; (new Object[2])[1] = "UTF_16"; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[5] = new Object[3];
/*     */       
/* 621 */       (new Object[2])[0] = "windows-1257"; (new Object[2])[1] = "MS1257"; param1ArrayOfObject[6] = new Object[2];
/* 622 */       (new Object[2])[0] = "utf-16be"; (new Object[2])[1] = "UTF_16BE"; param1ArrayOfObject[7] = new Object[2];
/* 623 */       (new Object[3])[0] = "iso-8859-2"; (new Object[3])[1] = "ISO_8859_2"; (new Object[2])[0] = "iso-8859-1"; (new Object[2])[1] = "ISO_8859_1"; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[8] = new Object[3];
/*     */       
/* 625 */       (new Object[3])[0] = "iso-8859-4"; (new Object[3])[1] = "ISO_8859_4"; (new Object[2])[0] = "utf-8"; (new Object[2])[1] = "UTF_8"; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[9] = new Object[3];
/*     */       
/* 627 */       (new Object[2])[0] = "iso-8859-5"; (new Object[2])[1] = "ISO_8859_5"; param1ArrayOfObject[10] = new Object[2];
/* 628 */       (new Object[3])[0] = "x-ibm874"; (new Object[3])[1] = "IBM874"; (new Object[2])[0] = "iso-8859-7"; (new Object[2])[1] = "ISO_8859_7"; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[11] = new Object[3];
/*     */       
/* 630 */       (new Object[2])[0] = "iso-8859-9"; (new Object[2])[1] = "ISO_8859_9"; param1ArrayOfObject[12] = new Object[2];
/* 631 */       (new Object[2])[0] = "x-ibm737"; (new Object[2])[1] = "IBM737"; param1ArrayOfObject[14] = new Object[2];
/* 632 */       (new Object[2])[0] = "ibm850"; (new Object[2])[1] = "IBM850"; param1ArrayOfObject[15] = new Object[2];
/* 633 */       (new Object[3])[0] = "ibm852"; (new Object[3])[1] = "IBM852"; (new Object[2])[0] = "ibm775"; (new Object[2])[1] = "IBM775"; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[16] = new Object[3];
/*     */       
/* 635 */       (new Object[3])[0] = "iso-8859-13"; (new Object[3])[1] = "ISO_8859_13"; (new Object[2])[0] = "us-ascii"; (new Object[2])[1] = "US_ASCII"; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[17] = new Object[3];
/*     */       
/* 637 */       (new Object[3])[0] = "ibm855"; (new Object[3])[1] = "IBM855"; (new Object[3])[0] = "ibm437"; (new Object[3])[1] = "IBM437"; (new Object[2])[0] = "iso-8859-15"; (new Object[2])[1] = "ISO_8859_15"; (new Object[3])[2] = new Object[2]; (new Object[3])[2] = new Object[3]; param1ArrayOfObject[18] = new Object[3];
/*     */ 
/*     */       
/* 640 */       (new Object[3])[0] = "ibm00858"; (new Object[3])[1] = "IBM858"; (new Object[3])[0] = "ibm857"; (new Object[3])[1] = "IBM857"; (new Object[2])[0] = "x-utf-32le-bom"; (new Object[2])[1] = "UTF_32LE_BOM"; (new Object[3])[2] = new Object[2]; (new Object[3])[2] = new Object[3]; param1ArrayOfObject[19] = new Object[3];
/*     */ 
/*     */       
/* 643 */       (new Object[2])[0] = "x-utf-16le-bom"; (new Object[2])[1] = "UTF_16LE_BOM"; param1ArrayOfObject[22] = new Object[2];
/* 644 */       (new Object[2])[0] = "cesu-8"; (new Object[2])[1] = "CESU_8"; param1ArrayOfObject[23] = new Object[2];
/* 645 */       (new Object[2])[0] = "x-utf-32be-bom"; (new Object[2])[1] = "UTF_32BE_BOM"; param1ArrayOfObject[24] = new Object[2];
/* 646 */       (new Object[2])[0] = "koi8-r"; (new Object[2])[1] = "KOI8_R"; param1ArrayOfObject[28] = new Object[2];
/* 647 */       (new Object[2])[0] = "koi8-u"; (new Object[2])[1] = "KOI8_U"; param1ArrayOfObject[29] = new Object[2];
/* 648 */       (new Object[2])[0] = "utf-32le"; (new Object[2])[1] = "UTF_32LE"; param1ArrayOfObject[31] = new Object[2];
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class Cache
/*     */     extends PreHashedMap<Charset>
/*     */   {
/*     */     private static final int ROWS = 32;
/*     */     
/*     */     private static final int SIZE = 39;
/*     */     private static final int SHIFT = 1;
/*     */     private static final int MASK = 31;
/*     */     
/*     */     private Cache() {
/* 663 */       super(32, 39, 1, 31);
/*     */     }
/*     */     
/*     */     protected void init(Object[] param1ArrayOfObject) {
/* 667 */       (new Object[2])[0] = "ibm862"; (new Object[2])[1] = null; param1ArrayOfObject[0] = new Object[2];
/* 668 */       (new Object[3])[0] = "ibm866"; (new Object[3])[1] = null; (new Object[3])[0] = "utf-32"; (new Object[3])[1] = null; (new Object[2])[0] = "utf-16le"; (new Object[2])[1] = null; (new Object[3])[2] = new Object[2]; (new Object[3])[2] = new Object[3]; param1ArrayOfObject[2] = new Object[3];
/*     */ 
/*     */       
/* 671 */       (new Object[3])[0] = "windows-1251"; (new Object[3])[1] = null; (new Object[2])[0] = "windows-1250"; (new Object[2])[1] = null; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[3] = new Object[3];
/*     */       
/* 673 */       (new Object[3])[0] = "windows-1253"; (new Object[3])[1] = null; (new Object[3])[0] = "windows-1252"; (new Object[3])[1] = null; (new Object[2])[0] = "utf-32be"; (new Object[2])[1] = null; (new Object[3])[2] = new Object[2]; (new Object[3])[2] = new Object[3]; param1ArrayOfObject[4] = new Object[3];
/*     */ 
/*     */       
/* 676 */       (new Object[3])[0] = "windows-1254"; (new Object[3])[1] = null; (new Object[2])[0] = "utf-16"; (new Object[2])[1] = null; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[5] = new Object[3];
/*     */       
/* 678 */       (new Object[2])[0] = "windows-1257"; (new Object[2])[1] = null; param1ArrayOfObject[6] = new Object[2];
/* 679 */       (new Object[2])[0] = "utf-16be"; (new Object[2])[1] = null; param1ArrayOfObject[7] = new Object[2];
/* 680 */       (new Object[3])[0] = "iso-8859-2"; (new Object[3])[1] = null; (new Object[2])[0] = "iso-8859-1"; (new Object[2])[1] = null; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[8] = new Object[3];
/*     */       
/* 682 */       (new Object[3])[0] = "iso-8859-4"; (new Object[3])[1] = null; (new Object[2])[0] = "utf-8"; (new Object[2])[1] = null; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[9] = new Object[3];
/*     */       
/* 684 */       (new Object[2])[0] = "iso-8859-5"; (new Object[2])[1] = null; param1ArrayOfObject[10] = new Object[2];
/* 685 */       (new Object[3])[0] = "x-ibm874"; (new Object[3])[1] = null; (new Object[2])[0] = "iso-8859-7"; (new Object[2])[1] = null; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[11] = new Object[3];
/*     */       
/* 687 */       (new Object[2])[0] = "iso-8859-9"; (new Object[2])[1] = null; param1ArrayOfObject[12] = new Object[2];
/* 688 */       (new Object[2])[0] = "x-ibm737"; (new Object[2])[1] = null; param1ArrayOfObject[14] = new Object[2];
/* 689 */       (new Object[2])[0] = "ibm850"; (new Object[2])[1] = null; param1ArrayOfObject[15] = new Object[2];
/* 690 */       (new Object[3])[0] = "ibm852"; (new Object[3])[1] = null; (new Object[2])[0] = "ibm775"; (new Object[2])[1] = null; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[16] = new Object[3];
/*     */       
/* 692 */       (new Object[3])[0] = "iso-8859-13"; (new Object[3])[1] = null; (new Object[2])[0] = "us-ascii"; (new Object[2])[1] = null; (new Object[3])[2] = new Object[2]; param1ArrayOfObject[17] = new Object[3];
/*     */       
/* 694 */       (new Object[3])[0] = "ibm855"; (new Object[3])[1] = null; (new Object[3])[0] = "ibm437"; (new Object[3])[1] = null; (new Object[2])[0] = "iso-8859-15"; (new Object[2])[1] = null; (new Object[3])[2] = new Object[2]; (new Object[3])[2] = new Object[3]; param1ArrayOfObject[18] = new Object[3];
/*     */ 
/*     */       
/* 697 */       (new Object[3])[0] = "ibm00858"; (new Object[3])[1] = null; (new Object[3])[0] = "ibm857"; (new Object[3])[1] = null; (new Object[2])[0] = "x-utf-32le-bom"; (new Object[2])[1] = null; (new Object[3])[2] = new Object[2]; (new Object[3])[2] = new Object[3]; param1ArrayOfObject[19] = new Object[3];
/*     */ 
/*     */       
/* 700 */       (new Object[2])[0] = "x-utf-16le-bom"; (new Object[2])[1] = null; param1ArrayOfObject[22] = new Object[2];
/* 701 */       (new Object[2])[0] = "cesu-8"; (new Object[2])[1] = null; param1ArrayOfObject[23] = new Object[2];
/* 702 */       (new Object[2])[0] = "x-utf-32be-bom"; (new Object[2])[1] = null; param1ArrayOfObject[24] = new Object[2];
/* 703 */       (new Object[2])[0] = "koi8-r"; (new Object[2])[1] = null; param1ArrayOfObject[28] = new Object[2];
/* 704 */       (new Object[2])[0] = "koi8-u"; (new Object[2])[1] = null; param1ArrayOfObject[29] = new Object[2];
/* 705 */       (new Object[2])[0] = "utf-32le"; (new Object[2])[1] = null; param1ArrayOfObject[31] = new Object[2];
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public StandardCharsets() {
/* 711 */     super("sun.nio.cs", new Aliases(null), new Classes(null), new Cache(null));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/cs/StandardCharsets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */