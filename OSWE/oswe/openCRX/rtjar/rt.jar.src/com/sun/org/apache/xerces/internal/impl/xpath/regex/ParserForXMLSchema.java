/*     */ package com.sun.org.apache.xerces.internal.impl.xpath.regex;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
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
/*     */ class ParserForXMLSchema
/*     */   extends RegexParser
/*     */ {
/*     */   public ParserForXMLSchema() {}
/*     */   
/*     */   public ParserForXMLSchema(Locale locale) {
/*  41 */     super(locale);
/*     */   }
/*     */   
/*     */   Token processCaret() throws ParseException {
/*  45 */     next();
/*  46 */     return Token.createChar(94);
/*     */   }
/*     */   Token processDollar() throws ParseException {
/*  49 */     next();
/*  50 */     return Token.createChar(36);
/*     */   }
/*     */   Token processLookahead() throws ParseException {
/*  53 */     throw ex("parser.process.1", this.offset);
/*     */   }
/*     */   Token processNegativelookahead() throws ParseException {
/*  56 */     throw ex("parser.process.1", this.offset);
/*     */   }
/*     */   Token processLookbehind() throws ParseException {
/*  59 */     throw ex("parser.process.1", this.offset);
/*     */   }
/*     */   Token processNegativelookbehind() throws ParseException {
/*  62 */     throw ex("parser.process.1", this.offset);
/*     */   }
/*     */   Token processBacksolidus_A() throws ParseException {
/*  65 */     throw ex("parser.process.1", this.offset);
/*     */   }
/*     */   Token processBacksolidus_Z() throws ParseException {
/*  68 */     throw ex("parser.process.1", this.offset);
/*     */   }
/*     */   Token processBacksolidus_z() throws ParseException {
/*  71 */     throw ex("parser.process.1", this.offset);
/*     */   }
/*     */   Token processBacksolidus_b() throws ParseException {
/*  74 */     throw ex("parser.process.1", this.offset);
/*     */   }
/*     */   Token processBacksolidus_B() throws ParseException {
/*  77 */     throw ex("parser.process.1", this.offset);
/*     */   }
/*     */   Token processBacksolidus_lt() throws ParseException {
/*  80 */     throw ex("parser.process.1", this.offset);
/*     */   }
/*     */   Token processBacksolidus_gt() throws ParseException {
/*  83 */     throw ex("parser.process.1", this.offset);
/*     */   }
/*     */   Token processStar(Token tok) throws ParseException {
/*  86 */     next();
/*  87 */     return Token.createClosure(tok);
/*     */   }
/*     */   
/*     */   Token processPlus(Token tok) throws ParseException {
/*  91 */     next();
/*  92 */     return Token.createConcat(tok, Token.createClosure(tok));
/*     */   }
/*     */   
/*     */   Token processQuestion(Token tok) throws ParseException {
/*  96 */     next();
/*  97 */     Token par = Token.createUnion();
/*  98 */     par.addChild(tok);
/*  99 */     par.addChild(Token.createEmpty());
/* 100 */     return par;
/*     */   }
/*     */   boolean checkQuestion(int off) {
/* 103 */     return false;
/*     */   }
/*     */   Token processParen() throws ParseException {
/* 106 */     next();
/* 107 */     Token tok = Token.createParen(parseRegex(), 0);
/* 108 */     if (read() != 7) throw ex("parser.factor.1", this.offset - 1); 
/* 109 */     next();
/* 110 */     return tok;
/*     */   }
/*     */   Token processParen2() throws ParseException {
/* 113 */     throw ex("parser.process.1", this.offset);
/*     */   }
/*     */   Token processCondition() throws ParseException {
/* 116 */     throw ex("parser.process.1", this.offset);
/*     */   }
/*     */   Token processModifiers() throws ParseException {
/* 119 */     throw ex("parser.process.1", this.offset);
/*     */   }
/*     */   Token processIndependent() throws ParseException {
/* 122 */     throw ex("parser.process.1", this.offset);
/*     */   }
/*     */   Token processBacksolidus_c() throws ParseException {
/* 125 */     next();
/* 126 */     return getTokenForShorthand(99);
/*     */   }
/*     */   Token processBacksolidus_C() throws ParseException {
/* 129 */     next();
/* 130 */     return getTokenForShorthand(67);
/*     */   }
/*     */   Token processBacksolidus_i() throws ParseException {
/* 133 */     next();
/* 134 */     return getTokenForShorthand(105);
/*     */   }
/*     */   Token processBacksolidus_I() throws ParseException {
/* 137 */     next();
/* 138 */     return getTokenForShorthand(73);
/*     */   }
/*     */   Token processBacksolidus_g() throws ParseException {
/* 141 */     throw ex("parser.process.1", this.offset - 2);
/*     */   }
/*     */   Token processBacksolidus_X() throws ParseException {
/* 144 */     throw ex("parser.process.1", this.offset - 2);
/*     */   }
/*     */   Token processBackreference() throws ParseException {
/* 147 */     throw ex("parser.process.1", this.offset - 4);
/*     */   }
/*     */   
/*     */   int processCIinCharacterClass(RangeToken tok, int c) {
/* 151 */     tok.mergeRanges(getTokenForShorthand(c));
/* 152 */     return -1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected RangeToken parseCharacterClass(boolean useNrange) throws ParseException {
/*     */     RangeToken tok;
/* 174 */     setContext(1);
/* 175 */     next();
/* 176 */     boolean nrange = false;
/* 177 */     boolean wasDecoded = false;
/* 178 */     RangeToken base = null;
/*     */     
/* 180 */     if (read() == 0 && this.chardata == 94) {
/* 181 */       nrange = true;
/* 182 */       next();
/* 183 */       base = Token.createRange();
/* 184 */       base.addRange(0, 1114111);
/* 185 */       tok = Token.createRange();
/*     */     } else {
/* 187 */       tok = Token.createRange();
/*     */     } 
/*     */     
/* 190 */     boolean firstloop = true; int type;
/* 191 */     while ((type = read()) != 1) {
/*     */       
/* 193 */       wasDecoded = false;
/*     */       
/* 195 */       if (type == 0 && this.chardata == 93 && !firstloop) {
/* 196 */         if (nrange) {
/* 197 */           base.subtractRanges(tok);
/* 198 */           tok = base;
/*     */         } 
/*     */         break;
/*     */       } 
/* 202 */       int c = this.chardata;
/* 203 */       boolean end = false;
/* 204 */       if (type == 10) {
/* 205 */         int pstart; RangeToken tok2; switch (c) { case 68: case 83: case 87:
/*     */           case 100:
/*     */           case 115:
/*     */           case 119:
/* 209 */             tok.mergeRanges(getTokenForShorthand(c));
/* 210 */             end = true; break;
/*     */           case 67:
/*     */           case 73:
/*     */           case 99:
/*     */           case 105:
/* 215 */             c = processCIinCharacterClass(tok, c);
/* 216 */             if (c < 0) end = true;
/*     */             
/*     */             break;
/*     */           case 80:
/*     */           case 112:
/* 221 */             pstart = this.offset;
/* 222 */             tok2 = processBacksolidus_pP(c);
/* 223 */             if (tok2 == null) throw ex("parser.atom.5", pstart); 
/* 224 */             tok.mergeRanges(tok2);
/* 225 */             end = true;
/*     */             break;
/*     */           
/*     */           case 45:
/* 229 */             c = decodeEscaped();
/* 230 */             wasDecoded = true;
/*     */             break;
/*     */           
/*     */           default:
/* 234 */             c = decodeEscaped();
/*     */             break; }
/*     */       
/* 237 */       } else if (type == 24 && !firstloop) {
/*     */         
/* 239 */         if (nrange) {
/* 240 */           base.subtractRanges(tok);
/* 241 */           tok = base;
/*     */         } 
/* 243 */         RangeToken range2 = parseCharacterClass(false);
/* 244 */         tok.subtractRanges(range2);
/* 245 */         if (read() != 0 || this.chardata != 93)
/* 246 */           throw ex("parser.cc.5", this.offset); 
/*     */         break;
/*     */       } 
/* 249 */       next();
/* 250 */       if (!end) {
/* 251 */         if (type == 0) {
/* 252 */           if (c == 91) throw ex("parser.cc.6", this.offset - 2); 
/* 253 */           if (c == 93) throw ex("parser.cc.7", this.offset - 2); 
/* 254 */           if (c == 45 && this.chardata != 93 && !firstloop) throw ex("parser.cc.8", this.offset - 2); 
/*     */         } 
/* 256 */         if (read() != 0 || this.chardata != 45 || (c == 45 && firstloop)) {
/* 257 */           if (!isSet(2) || c > 65535) {
/* 258 */             tok.addRange(c, c);
/*     */           } else {
/*     */             
/* 261 */             addCaseInsensitiveChar(tok, c);
/*     */           } 
/*     */         } else {
/*     */           
/* 265 */           next();
/* 266 */           if ((type = read()) == 1) throw ex("parser.cc.2", this.offset);
/*     */           
/* 268 */           if (type == 0 && this.chardata == 93) {
/* 269 */             if (!isSet(2) || c > 65535) {
/* 270 */               tok.addRange(c, c);
/*     */             } else {
/*     */               
/* 273 */               addCaseInsensitiveChar(tok, c);
/*     */             } 
/* 275 */             tok.addRange(45, 45);
/*     */           } else {
/* 277 */             if (type == 24) {
/* 278 */               throw ex("parser.cc.8", this.offset - 1);
/*     */             }
/*     */             
/* 281 */             int rangeend = this.chardata;
/* 282 */             if (type == 0) {
/* 283 */               if (rangeend == 91) throw ex("parser.cc.6", this.offset - 1); 
/* 284 */               if (rangeend == 93) throw ex("parser.cc.7", this.offset - 1); 
/* 285 */               if (rangeend == 45) throw ex("parser.cc.8", this.offset - 2);
/*     */             
/* 287 */             } else if (type == 10) {
/* 288 */               rangeend = decodeEscaped();
/* 289 */             }  next();
/*     */             
/* 291 */             if (c > rangeend) throw ex("parser.ope.3", this.offset - 1); 
/* 292 */             if (!isSet(2) || (c > 65535 && rangeend > 65535)) {
/*     */               
/* 294 */               tok.addRange(c, rangeend);
/*     */             } else {
/*     */               
/* 297 */               addCaseInsensitiveCharRange(tok, c, rangeend);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 302 */       firstloop = false;
/*     */     } 
/* 304 */     if (read() == 1)
/* 305 */       throw ex("parser.cc.2", this.offset); 
/* 306 */     tok.sortRanges();
/* 307 */     tok.compactRanges();
/*     */     
/* 309 */     setContext(0);
/* 310 */     next();
/*     */     
/* 312 */     return tok;
/*     */   }
/*     */   
/*     */   protected RangeToken parseSetOperations() throws ParseException {
/* 316 */     throw ex("parser.process.1", this.offset);
/*     */   }
/*     */   
/*     */   Token getTokenForShorthand(int ch) {
/* 320 */     switch (ch) {
/*     */       case 100:
/* 322 */         return getRange("xml:isDigit", true);
/*     */       case 68:
/* 324 */         return getRange("xml:isDigit", false);
/*     */       case 119:
/* 326 */         return getRange("xml:isWord", true);
/*     */       case 87:
/* 328 */         return getRange("xml:isWord", false);
/*     */       case 115:
/* 330 */         return getRange("xml:isSpace", true);
/*     */       case 83:
/* 332 */         return getRange("xml:isSpace", false);
/*     */       case 99:
/* 334 */         return getRange("xml:isNameChar", true);
/*     */       case 67:
/* 336 */         return getRange("xml:isNameChar", false);
/*     */       case 105:
/* 338 */         return getRange("xml:isInitialNameChar", true);
/*     */       case 73:
/* 340 */         return getRange("xml:isInitialNameChar", false);
/*     */     } 
/* 342 */     throw new RuntimeException("Internal Error: shorthands: \\u" + Integer.toString(ch, 16));
/*     */   }
/*     */   
/*     */   int decodeEscaped() throws ParseException {
/* 346 */     if (read() != 10) throw ex("parser.next.1", this.offset - 1); 
/* 347 */     int c = this.chardata;
/* 348 */     switch (c) { case 110:
/* 349 */         c = 10;
/* 350 */       case 114: c = 13;
/* 351 */       case 116: c = 9;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 40:
/*     */       case 41:
/*     */       case 42:
/*     */       case 43:
/*     */       case 45:
/*     */       case 46:
/*     */       case 63:
/*     */       case 91:
/*     */       case 92:
/*     */       case 93:
/*     */       case 94:
/*     */       case 123:
/*     */       case 124:
/*     */       case 125:
/* 370 */         return c; }
/*     */     
/*     */     throw ex("parser.process.1", this.offset - 2);
/* 373 */   } private static Map<String, Token> ranges = null;
/* 374 */   private static Map<String, Token> ranges2 = null; private static final String SPACES = "\t\n\r\r  ";
/*     */   protected static synchronized RangeToken getRange(String name, boolean positive) {
/* 376 */     if (ranges == null) {
/* 377 */       ranges = new HashMap<>();
/* 378 */       ranges2 = new HashMap<>();
/*     */       
/* 380 */       Token token = Token.createRange();
/* 381 */       setupRange(token, "\t\n\r\r  ");
/* 382 */       ranges.put("xml:isSpace", token);
/* 383 */       ranges2.put("xml:isSpace", Token.complementRanges(token));
/*     */       
/* 385 */       token = Token.createRange();
/* 386 */       setupRange(token, "09٠٩۰۹०९০৯੦੯૦૯୦୯௧௯౦౯೦೯൦൯๐๙໐໙༠༩၀၉፩፱០៩᠐᠙０９");
/* 387 */       setupRange(token, DIGITS_INT);
/* 388 */       ranges.put("xml:isDigit", token);
/* 389 */       ranges2.put("xml:isDigit", Token.complementRanges(token));
/*     */       
/* 391 */       token = Token.createRange();
/* 392 */       setupRange(token, "AZazÀÖØöøıĴľŁňŊžƀǰǴǵǺȗɐʨʻˁʰˑΆΆΈΊΌΌΎΡΣώϐϖϚϚϜϜϞϞϠϠϢϳЁЌЎяёќўҁҐӄӇӈӋӌӐӫӮӵӸӹԱՖՙՙաֆאתװײءغفيٱڷںھۀێېۓەەۥۦअहऽऽक़ॡঅঌএঐওনপরললশহড়ঢ়য়ৡৰৱਅਊਏਐਓਨਪਰਲਲ਼ਵਸ਼ਸਹਖ਼ੜਫ਼ਫ਼ੲੴઅઋઍઍએઑઓનપરલળવહઽઽૠૠଅଌଏଐଓନପରଲଳଶହଽଽଡ଼ଢ଼ୟୡஅஊஎஐஒகஙசஜஜஞடணதநபமவஷஹఅఌఎఐఒనపళవహౠౡಅಌಎಐಒನಪಳವಹೞೞೠೡഅഌഎഐഒനപഹൠൡกฮะะาำเๅກຂຄຄງຈຊຊຍຍດທນຟມຣລລວວສຫອຮະະາຳຽຽເໄཀཇཉཀྵႠჅაჶᄀᄀᄂᄃᄅᄇᄉᄉᄋᄌᄎᄒᄼᄼᄾᄾᅀᅀᅌᅌᅎᅎᅐᅐᅔᅕᅙᅙᅟᅡᅣᅣᅥᅥᅧᅧᅩᅩᅭᅮᅲᅳᅵᅵᆞᆞᆨᆨᆫᆫᆮᆯᆷᆸᆺᆺᆼᇂᇫᇫᇰᇰᇹᇹḀẛẠỹἀἕἘἝἠὅὈὍὐὗὙὙὛὛὝὝὟώᾀᾴᾶᾼιιῂῄῆῌῐΐῖΊῠῬῲῴῶῼΩΩKÅ℮℮ↀↂ〇〇〡〩ぁゔァヺㄅㄬ一龥가힣ｦﾟ");
/* 393 */       setupRange(token, LETTERS_INT);
/* 394 */       token.mergeRanges(ranges.get("xml:isDigit"));
/* 395 */       ranges.put("xml:isWord", token);
/* 396 */       ranges2.put("xml:isWord", Token.complementRanges(token));
/*     */       
/* 398 */       token = Token.createRange();
/* 399 */       setupRange(token, "-.0:AZ__az··ÀÖØöøıĴľŁňŊžƀǃǍǰǴǵǺȗɐʨʻˁːˑ̀͠͡ͅΆΊΌΌΎΡΣώϐϖϚϚϜϜϞϞϠϠϢϳЁЌЎяёќўҁ҃҆ҐӄӇӈӋӌӐӫӮӵӸӹԱՖՙՙաֆֹֻֽֿֿׁׂ֑֣֡ׄׄאתװײءغـْ٠٩ٰڷںھۀێېۓە۪ۭۨ۰۹ँःअह़्॑॔क़ॣ०९ঁঃঅঌএঐওনপরললশহ়়াৄেৈো্ৗৗড়ঢ়য়ৣ০ৱਂਂਅਊਏਐਓਨਪਰਲਲ਼ਵਸ਼ਸਹ਼਼ਾੂੇੈੋ੍ਖ਼ੜਫ਼ਫ਼੦ੴઁઃઅઋઍઍએઑઓનપરલળવહ઼ૅેૉો્ૠૠ૦૯ଁଃଅଌଏଐଓନପରଲଳଶହ଼ୃେୈୋ୍ୖୗଡ଼ଢ଼ୟୡ୦୯ஂஃஅஊஎஐஒகஙசஜஜஞடணதநபமவஷஹாூெைொ்ௗௗ௧௯ఁఃఅఌఎఐఒనపళవహాౄెైొ్ౕౖౠౡ౦౯ಂಃಅಌಎಐಒನಪಳವಹಾೄೆೈೊ್ೕೖೞೞೠೡ೦೯ംഃഅഌഎഐഒനപഹാൃെൈൊ്ൗൗൠൡ൦൯กฮะฺเ๎๐๙ກຂຄຄງຈຊຊຍຍດທນຟມຣລລວວສຫອຮະູົຽເໄໆໆ່ໍ໐໙༘༙༠༩༹༹༵༵༷༷༾ཇཉཀྵ྄ཱ྆ྋྐྕྗྗྙྭྱྷྐྵྐྵႠჅაჶᄀᄀᄂᄃᄅᄇᄉᄉᄋᄌᄎᄒᄼᄼᄾᄾᅀᅀᅌᅌᅎᅎᅐᅐᅔᅕᅙᅙᅟᅡᅣᅣᅥᅥᅧᅧᅩᅩᅭᅮᅲᅳᅵᅵᆞᆞᆨᆨᆫᆫᆮᆯᆷᆸᆺᆺᆼᇂᇫᇫᇰᇰᇹᇹḀẛẠỹἀἕἘἝἠὅὈὍὐὗὙὙὛὛὝὝὟώᾀᾴᾶᾼιιῂῄῆῌῐΐῖΊῠῬῲῴῶῼ⃐⃜⃡⃡ΩΩKÅ℮℮ↀↂ々々〇〇〡〯〱〵ぁゔ゙゚ゝゞァヺーヾㄅㄬ一龥가힣");
/* 400 */       ranges.put("xml:isNameChar", token);
/* 401 */       ranges2.put("xml:isNameChar", Token.complementRanges(token));
/*     */       
/* 403 */       token = Token.createRange();
/* 404 */       setupRange(token, "AZazÀÖØöøıĴľŁňŊžƀǰǴǵǺȗɐʨʻˁʰˑΆΆΈΊΌΌΎΡΣώϐϖϚϚϜϜϞϞϠϠϢϳЁЌЎяёќўҁҐӄӇӈӋӌӐӫӮӵӸӹԱՖՙՙաֆאתװײءغفيٱڷںھۀێېۓەەۥۦअहऽऽक़ॡঅঌএঐওনপরললশহড়ঢ়য়ৡৰৱਅਊਏਐਓਨਪਰਲਲ਼ਵਸ਼ਸਹਖ਼ੜਫ਼ਫ਼ੲੴઅઋઍઍએઑઓનપરલળવહઽઽૠૠଅଌଏଐଓନପରଲଳଶହଽଽଡ଼ଢ଼ୟୡஅஊஎஐஒகஙசஜஜஞடணதநபமவஷஹఅఌఎఐఒనపళవహౠౡಅಌಎಐಒನಪಳವಹೞೞೠೡഅഌഎഐഒനപഹൠൡกฮะะาำเๅກຂຄຄງຈຊຊຍຍດທນຟມຣລລວວສຫອຮະະາຳຽຽເໄཀཇཉཀྵႠჅაჶᄀᄀᄂᄃᄅᄇᄉᄉᄋᄌᄎᄒᄼᄼᄾᄾᅀᅀᅌᅌᅎᅎᅐᅐᅔᅕᅙᅙᅟᅡᅣᅣᅥᅥᅧᅧᅩᅩᅭᅮᅲᅳᅵᅵᆞᆞᆨᆨᆫᆫᆮᆯᆷᆸᆺᆺᆼᇂᇫᇫᇰᇰᇹᇹḀẛẠỹἀἕἘἝἠὅὈὍὐὗὙὙὛὛὝὝὟώᾀᾴᾶᾼιιῂῄῆῌῐΐῖΊῠῬῲῴῶῼΩΩKÅ℮℮ↀↂ〇〇〡〩ぁゔァヺㄅㄬ一龥가힣ｦﾟ");
/* 405 */       token.addRange(95, 95);
/* 406 */       token.addRange(58, 58);
/* 407 */       ranges.put("xml:isInitialNameChar", token);
/* 408 */       ranges2.put("xml:isInitialNameChar", Token.complementRanges(token));
/*     */     } 
/*     */     
/* 411 */     RangeToken tok = positive ? (RangeToken)ranges.get(name) : (RangeToken)ranges2.get(name);
/* 412 */     return tok;
/*     */   }
/*     */   private static final String NAMECHARS = "-.0:AZ__az··ÀÖØöøıĴľŁňŊžƀǃǍǰǴǵǺȗɐʨʻˁːˑ̀͠͡ͅΆΊΌΌΎΡΣώϐϖϚϚϜϜϞϞϠϠϢϳЁЌЎяёќўҁ҃҆ҐӄӇӈӋӌӐӫӮӵӸӹԱՖՙՙաֆֹֻֽֿֿׁׂ֑֣֡ׄׄאתװײءغـْ٠٩ٰڷںھۀێېۓە۪ۭۨ۰۹ँःअह़्॑॔क़ॣ०९ঁঃঅঌএঐওনপরললশহ়়াৄেৈো্ৗৗড়ঢ়য়ৣ০ৱਂਂਅਊਏਐਓਨਪਰਲਲ਼ਵਸ਼ਸਹ਼਼ਾੂੇੈੋ੍ਖ਼ੜਫ਼ਫ਼੦ੴઁઃઅઋઍઍએઑઓનપરલળવહ઼ૅેૉો્ૠૠ૦૯ଁଃଅଌଏଐଓନପରଲଳଶହ଼ୃେୈୋ୍ୖୗଡ଼ଢ଼ୟୡ୦୯ஂஃஅஊஎஐஒகஙசஜஜஞடணதநபமவஷஹாூெைொ்ௗௗ௧௯ఁఃఅఌఎఐఒనపళవహాౄెైొ్ౕౖౠౡ౦౯ಂಃಅಌಎಐಒನಪಳವಹಾೄೆೈೊ್ೕೖೞೞೠೡ೦೯ംഃഅഌഎഐഒനപഹാൃെൈൊ്ൗൗൠൡ൦൯กฮะฺเ๎๐๙ກຂຄຄງຈຊຊຍຍດທນຟມຣລລວວສຫອຮະູົຽເໄໆໆ່ໍ໐໙༘༙༠༩༹༹༵༵༷༷༾ཇཉཀྵ྄ཱ྆ྋྐྕྗྗྙྭྱྷྐྵྐྵႠჅაჶᄀᄀᄂᄃᄅᄇᄉᄉᄋᄌᄎᄒᄼᄼᄾᄾᅀᅀᅌᅌᅎᅎᅐᅐᅔᅕᅙᅙᅟᅡᅣᅣᅥᅥᅧᅧᅩᅩᅭᅮᅲᅳᅵᅵᆞᆞᆨᆨᆫᆫᆮᆯᆷᆸᆺᆺᆼᇂᇫᇫᇰᇰᇹᇹḀẛẠỹἀἕἘἝἠὅὈὍὐὗὙὙὛὛὝὝὟώᾀᾴᾶᾼιιῂῄῆῌῐΐῖΊῠῬῲῴῶῼ⃐⃜⃡⃡ΩΩKÅ℮℮ↀↂ々々〇〇〡〯〱〵ぁゔ゙゚ゝゞァヺーヾㄅㄬ一龥가힣"; private static final String LETTERS = "AZazÀÖØöøıĴľŁňŊžƀǰǴǵǺȗɐʨʻˁʰˑΆΆΈΊΌΌΎΡΣώϐϖϚϚϜϜϞϞϠϠϢϳЁЌЎяёќўҁҐӄӇӈӋӌӐӫӮӵӸӹԱՖՙՙաֆאתװײءغفيٱڷںھۀێېۓەەۥۦअहऽऽक़ॡঅঌএঐওনপরললশহড়ঢ়য়ৡৰৱਅਊਏਐਓਨਪਰਲਲ਼ਵਸ਼ਸਹਖ਼ੜਫ਼ਫ਼ੲੴઅઋઍઍએઑઓનપરલળવહઽઽૠૠଅଌଏଐଓନପରଲଳଶହଽଽଡ଼ଢ଼ୟୡஅஊஎஐஒகஙசஜஜஞடணதநபமவஷஹఅఌఎఐఒనపళవహౠౡಅಌಎಐಒನಪಳವಹೞೞೠೡഅഌഎഐഒനപഹൠൡกฮะะาำเๅກຂຄຄງຈຊຊຍຍດທນຟມຣລລວວສຫອຮະະາຳຽຽເໄཀཇཉཀྵႠჅაჶᄀᄀᄂᄃᄅᄇᄉᄉᄋᄌᄎᄒᄼᄼᄾᄾᅀᅀᅌᅌᅎᅎᅐᅐᅔᅕᅙᅙᅟᅡᅣᅣᅥᅥᅧᅧᅩᅩᅭᅮᅲᅳᅵᅵᆞᆞᆨᆨᆫᆫᆮᆯᆷᆸᆺᆺᆼᇂᇫᇫᇰᇰᇹᇹḀẛẠỹἀἕἘἝἠὅὈὍὐὗὙὙὛὛὝὝὟώᾀᾴᾶᾼιιῂῄῆῌῐΐῖΊῠῬῲῴῶῼΩΩKÅ℮℮ↀↂ〇〇〡〩ぁゔァヺㄅㄬ一龥가힣ｦﾟ";
/*     */   static void setupRange(Token range, String src) {
/* 416 */     int len = src.length();
/* 417 */     for (int i = 0; i < len; i += 2)
/* 418 */       range.addRange(src.charAt(i), src.charAt(i + 1)); 
/*     */   }
/*     */   
/*     */   static void setupRange(Token range, int[] src) {
/* 422 */     int len = src.length;
/* 423 */     for (int i = 0; i < len; i += 2) {
/* 424 */       range.addRange(src[i], src[i + 1]);
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 504 */   private static final int[] LETTERS_INT = new int[] { 120720, 120744, 120746, 120777, 195099, 195101 };
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String DIGITS = "09٠٩۰۹०९০৯੦੯૦૯୦୯௧௯౦౯೦೯൦൯๐๙໐໙༠༩၀၉፩፱០៩᠐᠙０９";
/*     */ 
/*     */   
/* 511 */   private static final int[] DIGITS_INT = new int[] { 120782, 120831 };
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xpath/regex/ParserForXMLSchema.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */