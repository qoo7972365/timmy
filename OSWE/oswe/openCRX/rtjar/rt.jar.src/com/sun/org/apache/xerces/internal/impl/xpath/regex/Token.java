/*      */ package com.sun.org.apache.xerces.internal.impl.xpath.regex;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class Token
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 8484976002585487481L;
/*      */   static final boolean COUNTTOKENS = true;
/*   48 */   static int tokens = 0;
/*      */   
/*      */   static final int CHAR = 0;
/*      */   
/*      */   static final int DOT = 11;
/*      */   
/*      */   static final int CONCAT = 1;
/*      */   
/*      */   static final int UNION = 2;
/*      */   
/*      */   static final int CLOSURE = 3;
/*      */   
/*      */   static final int RANGE = 4;
/*      */   
/*      */   static final int NRANGE = 5;
/*      */   
/*      */   static final int PAREN = 6;
/*      */   
/*      */   static final int EMPTY = 7;
/*      */   
/*      */   static final int ANCHOR = 8;
/*      */   
/*      */   static final int NONGREEDYCLOSURE = 9;
/*      */   
/*      */   static final int STRING = 10;
/*      */   
/*      */   static final int BACKREFERENCE = 12;
/*      */   
/*      */   static final int LOOKAHEAD = 20;
/*      */   
/*      */   static final int NEGATIVELOOKAHEAD = 21;
/*      */   
/*      */   static final int LOOKBEHIND = 22;
/*      */   static final int NEGATIVELOOKBEHIND = 23;
/*      */   static final int INDEPENDENT = 24;
/*      */   static final int MODIFIERGROUP = 25;
/*      */   static final int CONDITION = 26;
/*      */   static final int UTF16_MAX = 1114111;
/*      */   final int type;
/*      */   static Token token_dot;
/*      */   static Token token_0to9;
/*      */   static Token token_wordchars;
/*      */   static Token token_not_0to9;
/*      */   static Token token_not_wordchars;
/*      */   static Token token_spaces;
/*      */   static Token token_not_spaces;
/*   94 */   static Token token_empty = new Token(7);
/*      */   
/*   96 */   static Token token_linebeginning = createAnchor(94);
/*   97 */   static Token token_linebeginning2 = createAnchor(64);
/*   98 */   static Token token_lineend = createAnchor(36);
/*   99 */   static Token token_stringbeginning = createAnchor(65);
/*  100 */   static Token token_stringend = createAnchor(122);
/*  101 */   static Token token_stringend2 = createAnchor(90);
/*  102 */   static Token token_wordedge = createAnchor(98);
/*  103 */   static Token token_not_wordedge = createAnchor(66);
/*  104 */   static Token token_wordbeginning = createAnchor(60);
/*  105 */   static Token token_wordend = createAnchor(62); static final int FC_CONTINUE = 0; static final int FC_TERMINAL = 1; static final int FC_ANY = 2; private static final Map<String, Token> categories; private static final Map<String, Token> categories2; private static final String[] categoryNames; static final int CHAR_INIT_QUOTE = 29; static final int CHAR_FINAL_QUOTE = 30; static final int CHAR_LETTER = 31; static final int CHAR_MARK = 32; static final int CHAR_NUMBER = 33; static final int CHAR_SEPARATOR = 34; static final int CHAR_OTHER = 35; static final int CHAR_PUNCTUATION = 36; static final int CHAR_SYMBOL = 37; private static final String[] blockNames; static final String blockRanges = "\000ÿĀſƀɏɐʯʰ˿̀ͯͰϿЀӿ԰֏֐׿؀ۿ܀ݏހ޿ऀॿঀ৿਀੿઀૿଀୿஀௿ఀ౿ಀ೿ഀൿ඀෿฀๿຀໿ༀ࿿က႟Ⴀჿᄀᇿሀ፿Ꭰ᏿᐀ᙿ ᚟ᚠ᛿ក៿᠀᢯Ḁỿἀ῿ ⁯⁰₟₠⃏⃐⃿℀⅏⅐↏←⇿∀⋿⌀⏿␀␿⑀⑟①⓿─╿▀▟■◿☀⛿✀➿⠀⣿⺀⻿⼀⿟⿰⿿　〿぀ゟ゠ヿ㄀ㄯ㄰㆏㆐㆟ㆠㆿ㈀㋿㌀㏿㐀䶵一鿿ꀀ꒏꒐꓏가힣豈﫿ﬀﭏﭐ﷿︠︯︰﹏﹐﹯ﹰ﻾﻿﻿＀￯"; static final int[] nonBMPBlockRanges; private static final int NONBMP_BLOCK_START = 84; static final Set<String> nonxs; static final String viramaString = "्্੍્୍்్್്ฺ྄"; private static Token token_grapheme; private static Token token_ccs;
/*      */   
/*  107 */   static { token_dot = new Token(11);
/*      */     
/*  109 */     token_0to9 = createRange();
/*  110 */     token_0to9.addRange(48, 57);
/*  111 */     token_wordchars = createRange();
/*  112 */     token_wordchars.addRange(48, 57);
/*  113 */     token_wordchars.addRange(65, 90);
/*  114 */     token_wordchars.addRange(95, 95);
/*  115 */     token_wordchars.addRange(97, 122);
/*  116 */     token_spaces = createRange();
/*  117 */     token_spaces.addRange(9, 9);
/*  118 */     token_spaces.addRange(10, 10);
/*  119 */     token_spaces.addRange(12, 12);
/*  120 */     token_spaces.addRange(13, 13);
/*  121 */     token_spaces.addRange(32, 32);
/*      */     
/*  123 */     token_not_0to9 = complementRanges(token_0to9);
/*  124 */     token_not_wordchars = complementRanges(token_wordchars);
/*  125 */     token_not_spaces = complementRanges(token_spaces);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  596 */     categories = new HashMap<>();
/*  597 */     categories2 = new HashMap<>();
/*  598 */     categoryNames = new String[] { "Cn", "Lu", "Ll", "Lt", "Lm", "Lo", "Mn", "Me", "Mc", "Nd", "Nl", "No", "Zs", "Zl", "Zp", "Cc", "Cf", null, "Co", "Cs", "Pd", "Ps", "Pe", "Pc", "Po", "Sm", "Sc", "Sk", "So", "Pi", "Pf", "L", "M", "N", "Z", "C", "P", "S" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  618 */     blockNames = new String[] { "Basic Latin", "Latin-1 Supplement", "Latin Extended-A", "Latin Extended-B", "IPA Extensions", "Spacing Modifier Letters", "Combining Diacritical Marks", "Greek", "Cyrillic", "Armenian", "Hebrew", "Arabic", "Syriac", "Thaana", "Devanagari", "Bengali", "Gurmukhi", "Gujarati", "Oriya", "Tamil", "Telugu", "Kannada", "Malayalam", "Sinhala", "Thai", "Lao", "Tibetan", "Myanmar", "Georgian", "Hangul Jamo", "Ethiopic", "Cherokee", "Unified Canadian Aboriginal Syllabics", "Ogham", "Runic", "Khmer", "Mongolian", "Latin Extended Additional", "Greek Extended", "General Punctuation", "Superscripts and Subscripts", "Currency Symbols", "Combining Marks for Symbols", "Letterlike Symbols", "Number Forms", "Arrows", "Mathematical Operators", "Miscellaneous Technical", "Control Pictures", "Optical Character Recognition", "Enclosed Alphanumerics", "Box Drawing", "Block Elements", "Geometric Shapes", "Miscellaneous Symbols", "Dingbats", "Braille Patterns", "CJK Radicals Supplement", "Kangxi Radicals", "Ideographic Description Characters", "CJK Symbols and Punctuation", "Hiragana", "Katakana", "Bopomofo", "Hangul Compatibility Jamo", "Kanbun", "Bopomofo Extended", "Enclosed CJK Letters and Months", "CJK Compatibility", "CJK Unified Ideographs Extension A", "CJK Unified Ideographs", "Yi Syllables", "Yi Radicals", "Hangul Syllables", "Private Use", "CJK Compatibility Ideographs", "Alphabetic Presentation Forms", "Arabic Presentation Forms-A", "Combining Half Marks", "CJK Compatibility Forms", "Small Form Variants", "Arabic Presentation Forms-B", "Specials", "Halfwidth and Fullwidth Forms", "Old Italic", "Gothic", "Deseret", "Byzantine Musical Symbols", "Musical Symbols", "Mathematical Alphanumeric Symbols", "CJK Unified Ideographs Extension B", "CJK Compatibility Ideographs Supplement", "Tags" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  732 */     nonBMPBlockRanges = new int[] { 66304, 66351, 66352, 66383, 66560, 66639, 118784, 119039, 119040, 119295, 119808, 120831, 131072, 173782, 194560, 195103, 917504, 917631 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  985 */     nonxs = Collections.synchronizedSet(new HashSet<>());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1025 */     token_grapheme = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1064 */     token_ccs = null; } static ParenToken createLook(int type, Token child) { tokens++; return new ParenToken(type, child, 0); } static ParenToken createParen(Token child, int pnumber) { tokens++; return new ParenToken(6, child, pnumber); } static ClosureToken createClosure(Token tok) { tokens++; return new ClosureToken(3, tok); } static ClosureToken createNGClosure(Token tok) { tokens++; return new ClosureToken(9, tok); } static ConcatToken createConcat(Token tok1, Token tok2) { tokens++; return new ConcatToken(tok1, tok2); } static UnionToken createConcat() { tokens++; return new UnionToken(1); } static UnionToken createUnion() { tokens++; return new UnionToken(2); } static Token createEmpty() { return token_empty; } static RangeToken createRange() { tokens++; return new RangeToken(4); } static RangeToken createNRange() { tokens++; return new RangeToken(5); } static CharToken createChar(int ch) { tokens++; return new CharToken(0, ch); } private static CharToken createAnchor(int ch) { tokens++; return new CharToken(8, ch); } static StringToken createBackReference(int refno) { tokens++; return new StringToken(12, null, refno); } static StringToken createString(String str) { tokens++; return new StringToken(10, str, 0); } static ModifierToken createModifierGroup(Token child, int add, int mask) { tokens++; return new ModifierToken(child, add, mask); } static ConditionToken createCondition(int refno, Token condition, Token yespat, Token nopat) { tokens++; return new ConditionToken(refno, condition, yespat, nopat); } protected Token(int type) { this.type = type; } int size() { return 0; } Token getChild(int index) { return null; } void addChild(Token tok) { throw new RuntimeException("Not supported."); } protected void addRange(int start, int end) { throw new RuntimeException("Not supported."); } protected void sortRanges() { throw new RuntimeException("Not supported."); } protected void compactRanges() { throw new RuntimeException("Not supported."); } protected void mergeRanges(Token tok) { throw new RuntimeException("Not supported."); }
/*      */   protected void subtractRanges(Token tok) { throw new RuntimeException("Not supported."); }
/* 1066 */   static synchronized Token getCombiningCharacterSequence() { if (token_ccs != null) {
/* 1067 */       return token_ccs;
/*      */     }
/* 1069 */     Token foo = createClosure(getRange("M", true));
/* 1070 */     foo = createConcat(getRange("M", false), foo);
/* 1071 */     token_ccs = foo;
/* 1072 */     return token_ccs; }
/*      */   protected void intersectRanges(Token tok) { throw new RuntimeException("Not supported."); }
/*      */   static Token complementRanges(Token tok) { return RangeToken.complementRanges(tok); }
/*      */   void setMin(int min) {}
/*      */   void setMax(int max) {}
/*      */   int getMin() { return -1; }
/*      */   int getMax() { return -1; }
/*      */   int getReferenceNumber() { return 0; }
/*      */   String getString() { return null; }
/*      */   int getParenNumber() { return 0; }
/*      */   int getChar() { return -1; }
/*      */   public String toString() { return toString(0); }
/*      */   public String toString(int options) { return (this.type == 11) ? "." : ""; }
/*      */   final int getMinLength() { int sum; int i; int ret; int j; switch (this.type) { case 1: sum = 0; for (i = 0; i < size(); i++) sum += getChild(i).getMinLength();  return sum;case 2: case 26: if (size() == 0) return 0;  ret = getChild(0).getMinLength(); for (j = 1; j < size(); j++) { int min = getChild(j).getMinLength(); if (min < ret) ret = min;  }  return ret;case 3: case 9: if (getMin() >= 0) return getMin() * getChild(0).getMinLength();  return 0;case 7: case 8: return 0;case 0: case 4: case 5: case 11: return 1;case 6: case 24: case 25: return getChild(0).getMinLength();case 12: return 0;case 10: return getString().length();case 20: case 21: case 22: case 23: return 0; }  throw new RuntimeException("Token#getMinLength(): Invalid Type: " + this.type); }
/*      */   final int getMaxLength() { int sum; int i; int ret; int j; switch (this.type) { case 1: sum = 0; for (i = 0; i < size(); i++) { int d = getChild(i).getMaxLength(); if (d < 0) return -1;  sum += d; }  return sum;case 2: case 26: if (size() == 0) return 0;  ret = getChild(0).getMaxLength(); for (j = 1; ret >= 0 && j < size(); j++) { int max = getChild(j).getMaxLength(); if (max < 0) { ret = -1; break; }  if (max > ret) ret = max;  }  return ret;case 3: case 9: if (getMax() >= 0) return getMax() * getChild(0).getMaxLength();  return -1;case 7: case 8: return 0;case 0: return 1;case 4: case 5: case 11: return 2;case 6: case 24: case 25: return getChild(0).getMaxLength();case 12: return -1;case 10: return getString().length();case 20: case 21: case 22: case 23: return 0; }  throw new RuntimeException("Token#getMaxLength(): Invalid Type: " + this.type); } private static final boolean isSet(int options, int flag) { return ((options & flag) == flag); } final int analyzeFirstCharacter(RangeToken result, int options) { int ret; int i; int ret2; boolean hasEmpty; int j; int ret3; int ret4; int ch; int cha; int ch2; switch (this.type) { case 1: ret = 0; for (i = 0; i < size() && (ret = getChild(i).analyzeFirstCharacter(result, options)) == 0; i++); return ret;case 2: if (size() == 0) return 0;  ret2 = 0; hasEmpty = false; for (j = 0; j < size(); j++) { ret2 = getChild(j).analyzeFirstCharacter(result, options); if (ret2 == 2) break;  if (ret2 == 0) hasEmpty = true;  }  return hasEmpty ? 0 : ret2;case 26: ret3 = getChild(0).analyzeFirstCharacter(result, options); if (size() == 1) return 0;  if (ret3 == 2) return ret3;  ret4 = getChild(1).analyzeFirstCharacter(result, options); if (ret4 == 2) return ret4;  return (ret3 == 0 || ret4 == 0) ? 0 : 1;case 3: case 9: getChild(0).analyzeFirstCharacter(result, options); return 0;case 7: case 8: return 0;case 0: ch = getChar(); result.addRange(ch, ch); if (ch < 65536 && isSet(options, 2)) { ch = Character.toUpperCase((char)ch); result.addRange(ch, ch); ch = Character.toLowerCase((char)ch); result.addRange(ch, ch); }  return 1;case 11: return 2;case 4: result.mergeRanges(this); return 1;case 5: result.mergeRanges(complementRanges(this)); return 1;case 6: case 24: return getChild(0).analyzeFirstCharacter(result, options);case 25: options |= ((ModifierToken)this).getOptions(); options &= ((ModifierToken)this).getOptionsMask() ^ 0xFFFFFFFF; return getChild(0).analyzeFirstCharacter(result, options);case 12: result.addRange(0, 1114111); return 2;case 10: cha = getString().charAt(0); if (REUtil.isHighSurrogate(cha) && getString().length() >= 2 && REUtil.isLowSurrogate(ch2 = getString().charAt(1))) cha = REUtil.composeFromSurrogates(cha, ch2);  result.addRange(cha, cha); if (cha < 65536 && isSet(options, 2)) { cha = Character.toUpperCase((char)cha); result.addRange(cha, cha); cha = Character.toLowerCase((char)cha); result.addRange(cha, cha); }  return 1;case 20: case 21: case 22: case 23: return 0; }  throw new RuntimeException("Token#analyzeHeadCharacter(): Invalid Type: " + this.type); } private final boolean isShorterThan(Token tok) { int mylength; int otherlength; if (tok == null) return false;  if (this.type == 10) { mylength = getString().length(); } else { throw new RuntimeException("Internal Error: Illegal type: " + this.type); }  if (tok.type == 10) { otherlength = tok.getString().length(); } else { throw new RuntimeException("Internal Error: Illegal type: " + tok.type); }  return (mylength < otherlength); } static class FixedStringContainer {
/*      */     Token token = null; int options = 0;
/*      */   } final void findFixedString(FixedStringContainer container, int options) { Token prevToken; int prevOptions; int i; switch (this.type) { case 1: prevToken = null; prevOptions = 0; for (i = 0; i < size(); i++) { getChild(i).findFixedString(container, options); if (prevToken == null || prevToken.isShorterThan(container.token)) { prevToken = container.token; prevOptions = container.options; }  }  container.token = prevToken; container.options = prevOptions; return;case 2: case 3: case 4: case 5: case 7: case 8: case 9: case 11: case 12: case 20: case 21: case 22: case 23: case 26: container.token = null; return;case 0: container.token = null; return;case 10: container.token = this; container.options = options; return;case 6: case 24: getChild(0).findFixedString(container, options); return;case 25: options |= ((ModifierToken)this).getOptions(); options &= ((ModifierToken)this).getOptionsMask() ^ 0xFFFFFFFF; getChild(0).findFixedString(container, options); return; }  throw new RuntimeException("Token#findFixedString(): Invalid Type: " + this.type); } boolean match(int ch) { throw new RuntimeException("NFAArrow#match(): Internal error: " + this.type); } protected static RangeToken getRange(String name, boolean positive) { if (categories.size() == 0) synchronized (categories) { Token[] ranges = new Token[categoryNames.length]; for (int i = 0; i < ranges.length; i++) ranges[i] = createRange();  int j; for (j = 0; j < 65536; j++) { int type = Character.getType((char)j); if (type == 21 || type == 22) { if (j == 171 || j == 8216 || j == 8219 || j == 8220 || j == 8223 || j == 8249) type = 29;  if (j == 187 || j == 8217 || j == 8221 || j == 8250) type = 30;  }  ranges[type].addRange(j, j); switch (type) { case 1: case 2: case 3: case 4: case 5: type = 31; break;case 6: case 7: case 8: type = 32; break;case 9: case 10: case 11: type = 33; break;case 12: case 13: case 14: type = 34; break;case 0: case 15: case 16: case 18: case 19: type = 35; break;case 20: case 21: case 22: case 23: case 24: case 29: case 30: type = 36; break;case 25: case 26: case 27: case 28: type = 37; break;default: throw new RuntimeException("org.apache.xerces.utils.regex.Token#getRange(): Unknown Unicode category: " + type); }  ranges[type].addRange(j, j); }  ranges[0].addRange(65536, 1114111); for (j = 0; j < ranges.length; j++) { if (categoryNames[j] != null) { if (j == 0) ranges[j].addRange(65536, 1114111);  categories.put(categoryNames[j], ranges[j]); categories2.put(categoryNames[j], complementRanges(ranges[j])); }  }  StringBuilder buffer = new StringBuilder(50); for (int k = 0; k < blockNames.length; k++) { Token r1 = createRange(); if (k < 84) { int location = k * 2; int rstart = "\000ÿĀſƀɏɐʯʰ˿̀ͯͰϿЀӿ԰֏֐׿؀ۿ܀ݏހ޿ऀॿঀ৿਀੿઀૿଀୿஀௿ఀ౿ಀ೿ഀൿ඀෿฀๿຀໿ༀ࿿က႟Ⴀჿᄀᇿሀ፿Ꭰ᏿᐀ᙿ ᚟ᚠ᛿ក៿᠀᢯Ḁỿἀ῿ ⁯⁰₟₠⃏⃐⃿℀⅏⅐↏←⇿∀⋿⌀⏿␀␿⑀⑟①⓿─╿▀▟■◿☀⛿✀➿⠀⣿⺀⻿⼀⿟⿰⿿　〿぀ゟ゠ヿ㄀ㄯ㄰㆏㆐㆟ㆠㆿ㈀㋿㌀㏿㐀䶵一鿿ꀀ꒏꒐꓏가힣豈﫿ﬀﭏﭐ﷿︠︯︰﹏﹐﹯ﹰ﻾﻿﻿＀￯".charAt(location); int rend = "\000ÿĀſƀɏɐʯʰ˿̀ͯͰϿЀӿ԰֏֐׿؀ۿ܀ݏހ޿ऀॿঀ৿਀੿઀૿଀୿஀௿ఀ౿ಀ೿ഀൿ඀෿฀๿຀໿ༀ࿿က႟Ⴀჿᄀᇿሀ፿Ꭰ᏿᐀ᙿ ᚟ᚠ᛿ក៿᠀᢯Ḁỿἀ῿ ⁯⁰₟₠⃏⃐⃿℀⅏⅐↏←⇿∀⋿⌀⏿␀␿⑀⑟①⓿─╿▀▟■◿☀⛿✀➿⠀⣿⺀⻿⼀⿟⿰⿿　〿぀ゟ゠ヿ㄀ㄯ㄰㆏㆐㆟ㆠㆿ㈀㋿㌀㏿㐀䶵一鿿ꀀ꒏꒐꓏가힣豈﫿ﬀﭏﭐ﷿︠︯︰﹏﹐﹯ﹰ﻾﻿﻿＀￯".charAt(location + 1); r1.addRange(rstart, rend); } else { int location = (k - 84) * 2; r1.addRange(nonBMPBlockRanges[location], nonBMPBlockRanges[location + 1]); }  String n = blockNames[k]; if (n.equals("Specials")) r1.addRange(65520, 65533);  if (n.equals("Private Use")) { r1.addRange(983040, 1048573); r1.addRange(1048576, 1114109); }  categories.put(n, r1); categories2.put(n, complementRanges(r1)); buffer.setLength(0); buffer.append("Is"); if (n.indexOf(' ') >= 0) { for (int ci = 0; ci < n.length(); ci++) { if (n.charAt(ci) != ' ') buffer.append(n.charAt(ci));  }  } else { buffer.append(n); }  setAlias(buffer.toString(), n, true); }  setAlias("ASSIGNED", "Cn", false); setAlias("UNASSIGNED", "Cn", true); Token all = createRange(); all.addRange(0, 1114111); categories.put("ALL", all); categories2.put("ALL", complementRanges(all)); registerNonXS("ASSIGNED"); registerNonXS("UNASSIGNED"); registerNonXS("ALL"); Token isalpha = createRange(); isalpha.mergeRanges(ranges[1]); isalpha.mergeRanges(ranges[2]); isalpha.mergeRanges(ranges[5]); categories.put("IsAlpha", isalpha); categories2.put("IsAlpha", complementRanges(isalpha)); registerNonXS("IsAlpha"); Token isalnum = createRange(); isalnum.mergeRanges(isalpha); isalnum.mergeRanges(ranges[9]); categories.put("IsAlnum", isalnum); categories2.put("IsAlnum", complementRanges(isalnum)); registerNonXS("IsAlnum"); Token isspace = createRange(); isspace.mergeRanges(token_spaces); isspace.mergeRanges(ranges[34]); categories.put("IsSpace", isspace); categories2.put("IsSpace", complementRanges(isspace)); registerNonXS("IsSpace"); Token isword = createRange(); isword.mergeRanges(isalnum); isword.addRange(95, 95); categories.put("IsWord", isword); categories2.put("IsWord", complementRanges(isword)); registerNonXS("IsWord"); Token isascii = createRange(); isascii.addRange(0, 127); categories.put("IsASCII", isascii); categories2.put("IsASCII", complementRanges(isascii)); registerNonXS("IsASCII"); Token isnotgraph = createRange(); isnotgraph.mergeRanges(ranges[35]); isnotgraph.addRange(32, 32); categories.put("IsGraph", complementRanges(isnotgraph)); categories2.put("IsGraph", isnotgraph); registerNonXS("IsGraph"); Token isxdigit = createRange(); isxdigit.addRange(48, 57); isxdigit.addRange(65, 70); isxdigit.addRange(97, 102); categories.put("IsXDigit", complementRanges(isxdigit)); categories2.put("IsXDigit", isxdigit); registerNonXS("IsXDigit"); setAlias("IsDigit", "Nd", true); setAlias("IsUpper", "Lu", true); setAlias("IsLower", "Ll", true); setAlias("IsCntrl", "C", true); setAlias("IsPrint", "C", false); setAlias("IsPunct", "P", true); registerNonXS("IsDigit"); registerNonXS("IsUpper"); registerNonXS("IsLower"); registerNonXS("IsCntrl"); registerNonXS("IsPrint"); registerNonXS("IsPunct"); setAlias("alpha", "IsAlpha", true); setAlias("alnum", "IsAlnum", true); setAlias("ascii", "IsASCII", true); setAlias("cntrl", "IsCntrl", true); setAlias("digit", "IsDigit", true); setAlias("graph", "IsGraph", true); setAlias("lower", "IsLower", true); setAlias("print", "IsPrint", true); setAlias("punct", "IsPunct", true); setAlias("space", "IsSpace", true); setAlias("upper", "IsUpper", true); setAlias("word", "IsWord", true); setAlias("xdigit", "IsXDigit", true); registerNonXS("alpha"); registerNonXS("alnum"); registerNonXS("ascii"); registerNonXS("cntrl"); registerNonXS("digit"); registerNonXS("graph"); registerNonXS("lower"); registerNonXS("print"); registerNonXS("punct"); registerNonXS("space"); registerNonXS("upper"); registerNonXS("word"); registerNonXS("xdigit"); }   RangeToken tok = positive ? (RangeToken)categories.get(name) : (RangeToken)categories2.get(name); return tok; } protected static RangeToken getRange(String name, boolean positive, boolean xs) { RangeToken range = getRange(name, positive); if (xs && range != null && isRegisterNonXS(name)) range = null;  return range; } protected static void registerNonXS(String name) { nonxs.add(name); } protected static boolean isRegisterNonXS(String name) { return nonxs.contains(name); } private static void setAlias(String newName, String name, boolean positive) { Token t1 = categories.get(name); Token t2 = categories2.get(name); if (positive) { categories.put(newName, t1); categories2.put(newName, t2); } else { categories2.put(newName, t1); categories.put(newName, t2); }  } static synchronized Token getGraphemePattern() { if (token_grapheme != null) return token_grapheme;  Token base_char = createRange(); base_char.mergeRanges(getRange("ASSIGNED", true)); base_char.subtractRanges(getRange("M", true)); base_char.subtractRanges(getRange("C", true)); Token virama = createRange(); for (int i = 0; i < "्্੍્୍்్್്ฺ྄".length(); i++) virama.addRange(i, i);  Token combiner_wo_virama = createRange(); combiner_wo_virama.mergeRanges(getRange("M", true)); combiner_wo_virama.addRange(4448, 4607); combiner_wo_virama.addRange(65438, 65439); Token left = createUnion(); left.addChild(base_char); left.addChild(token_empty); Token foo = createUnion(); foo.addChild(createConcat(virama, getRange("L", true))); foo.addChild(combiner_wo_virama); foo = createClosure(foo); foo = createConcat(left, foo); token_grapheme = foo; return token_grapheme; } static class StringToken extends Token implements Serializable {
/* 1089 */     private static final long serialVersionUID = -4614366944218504172L; StringToken(int type, String str, int n) { super(type);
/* 1090 */       this.string = str;
/* 1091 */       this.refNumber = n; }
/*      */     
/*      */     String string; final int refNumber;
/*      */     int getReferenceNumber() {
/* 1095 */       return this.refNumber;
/*      */     }
/*      */     String getString() {
/* 1098 */       return this.string;
/*      */     }
/*      */     
/*      */     public String toString(int options) {
/* 1102 */       if (this.type == 12) {
/* 1103 */         return "\\" + this.refNumber;
/*      */       }
/* 1105 */       return REUtil.quoteMeta(this.string);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class ConcatToken
/*      */     extends Token
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 8717321425541346381L;
/*      */     
/*      */     final Token child;
/*      */     final Token child2;
/*      */     
/*      */     ConcatToken(Token t1, Token t2) {
/* 1120 */       super(1);
/* 1121 */       this.child = t1;
/* 1122 */       this.child2 = t2;
/*      */     }
/*      */     
/*      */     int size() {
/* 1126 */       return 2;
/*      */     }
/*      */     Token getChild(int index) {
/* 1129 */       return (index == 0) ? this.child : this.child2;
/*      */     }
/*      */     
/*      */     public String toString(int options) {
/*      */       String ret;
/* 1134 */       if (this.child2.type == 3 && this.child2.getChild(0) == this.child) {
/* 1135 */         ret = this.child.toString(options) + "+";
/* 1136 */       } else if (this.child2.type == 9 && this.child2.getChild(0) == this.child) {
/* 1137 */         ret = this.child.toString(options) + "+?";
/*      */       } else {
/* 1139 */         ret = this.child.toString(options) + this.child2.toString(options);
/* 1140 */       }  return ret;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class CharToken
/*      */     extends Token
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -4394272816279496989L;
/*      */     
/*      */     final int chardata;
/*      */     
/*      */     CharToken(int type, int ch) {
/* 1154 */       super(type);
/* 1155 */       this.chardata = ch;
/*      */     }
/*      */     
/*      */     int getChar() {
/* 1159 */       return this.chardata;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString(int options) {
/* 1164 */       switch (this.type)
/*      */       { case 0:
/* 1166 */           switch (this.chardata) { case 40: case 41: case 42: case 43: case 46: case 63: case 91:
/*      */             case 92:
/*      */             case 123:
/*      */             case 124:
/* 1170 */               ret = "\\" + (char)this.chardata;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1197 */               return ret;case 12: ret = "\\f"; return ret;case 10: ret = "\\n"; return ret;case 13: ret = "\\r"; return ret;case 9: ret = "\\t"; return ret;case 27: ret = "\\e"; return ret; }  if (this.chardata >= 65536) { String pre = "0" + Integer.toHexString(this.chardata); ret = "\\v" + pre.substring(pre.length() - 6, pre.length()); } else { ret = "" + (char)this.chardata; }  return ret;case 8: if (this == Token.token_linebeginning || this == Token.token_lineend) { ret = "" + (char)this.chardata; } else { ret = "\\" + (char)this.chardata; }  return ret; }  String ret = null; return ret;
/*      */     }
/*      */     
/*      */     boolean match(int ch) {
/* 1201 */       if (this.type == 0) {
/* 1202 */         return (ch == this.chardata);
/*      */       }
/* 1204 */       throw new RuntimeException("NFAArrow#match(): Internal error: " + this.type);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class ClosureToken
/*      */     extends Token
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1308971930673997452L;
/*      */     
/*      */     int min;
/*      */     int max;
/*      */     final Token child;
/*      */     
/*      */     ClosureToken(int type, Token tok) {
/* 1220 */       super(type);
/* 1221 */       this.child = tok;
/* 1222 */       setMin(-1);
/* 1223 */       setMax(-1);
/*      */     }
/*      */     
/*      */     int size() {
/* 1227 */       return 1;
/*      */     }
/*      */     Token getChild(int index) {
/* 1230 */       return this.child;
/*      */     }
/*      */     
/*      */     final void setMin(int min) {
/* 1234 */       this.min = min;
/*      */     }
/*      */     final void setMax(int max) {
/* 1237 */       this.max = max;
/*      */     }
/*      */     final int getMin() {
/* 1240 */       return this.min;
/*      */     }
/*      */     final int getMax() {
/* 1243 */       return this.max;
/*      */     }
/*      */     
/*      */     public String toString(int options) {
/*      */       String ret;
/* 1248 */       if (this.type == 3) {
/* 1249 */         if (getMin() < 0 && getMax() < 0) {
/* 1250 */           ret = this.child.toString(options) + "*";
/* 1251 */         } else if (getMin() == getMax()) {
/* 1252 */           ret = this.child.toString(options) + "{" + getMin() + "}";
/* 1253 */         } else if (getMin() >= 0 && getMax() >= 0) {
/* 1254 */           ret = this.child.toString(options) + "{" + getMin() + "," + getMax() + "}";
/* 1255 */         } else if (getMin() >= 0 && getMax() < 0) {
/* 1256 */           ret = this.child.toString(options) + "{" + getMin() + ",}";
/*      */         } else {
/* 1258 */           throw new RuntimeException("Token#toString(): CLOSURE " + 
/* 1259 */               getMin() + ", " + getMax());
/*      */         } 
/* 1261 */       } else if (getMin() < 0 && getMax() < 0) {
/* 1262 */         ret = this.child.toString(options) + "*?";
/* 1263 */       } else if (getMin() == getMax()) {
/* 1264 */         ret = this.child.toString(options) + "{" + getMin() + "}?";
/* 1265 */       } else if (getMin() >= 0 && getMax() >= 0) {
/* 1266 */         ret = this.child.toString(options) + "{" + getMin() + "," + getMax() + "}?";
/* 1267 */       } else if (getMin() >= 0 && getMax() < 0) {
/* 1268 */         ret = this.child.toString(options) + "{" + getMin() + ",}?";
/*      */       } else {
/* 1270 */         throw new RuntimeException("Token#toString(): NONGREEDYCLOSURE " + 
/* 1271 */             getMin() + ", " + getMax());
/*      */       } 
/* 1273 */       return ret;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class ParenToken
/*      */     extends Token
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -5938014719827987704L;
/*      */     
/*      */     final Token child;
/*      */     final int parennumber;
/*      */     
/*      */     ParenToken(int type, Token tok, int paren) {
/* 1288 */       super(type);
/* 1289 */       this.child = tok;
/* 1290 */       this.parennumber = paren;
/*      */     }
/*      */     
/*      */     int size() {
/* 1294 */       return 1;
/*      */     }
/*      */     Token getChild(int index) {
/* 1297 */       return this.child;
/*      */     }
/*      */     
/*      */     int getParenNumber() {
/* 1301 */       return this.parennumber;
/*      */     }
/*      */     
/*      */     public String toString(int options) {
/* 1305 */       String ret = null;
/* 1306 */       switch (this.type) {
/*      */         case 6:
/* 1308 */           if (this.parennumber == 0) {
/* 1309 */             ret = "(?:" + this.child.toString(options) + ")"; break;
/*      */           } 
/* 1311 */           ret = "(" + this.child.toString(options) + ")";
/*      */           break;
/*      */ 
/*      */         
/*      */         case 20:
/* 1316 */           ret = "(?=" + this.child.toString(options) + ")";
/*      */           break;
/*      */         case 21:
/* 1319 */           ret = "(?!" + this.child.toString(options) + ")";
/*      */           break;
/*      */         case 22:
/* 1322 */           ret = "(?<=" + this.child.toString(options) + ")";
/*      */           break;
/*      */         case 23:
/* 1325 */           ret = "(?<!" + this.child.toString(options) + ")";
/*      */           break;
/*      */         case 24:
/* 1328 */           ret = "(?>" + this.child.toString(options) + ")";
/*      */           break;
/*      */       } 
/* 1331 */       return ret;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class ConditionToken
/*      */     extends Token
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 4353765277910594411L;
/*      */     final int refNumber;
/*      */     final Token condition;
/*      */     final Token yes;
/*      */     final Token no;
/*      */     
/*      */     ConditionToken(int refno, Token cond, Token yespat, Token nopat) {
/* 1347 */       super(26);
/* 1348 */       this.refNumber = refno;
/* 1349 */       this.condition = cond;
/* 1350 */       this.yes = yespat;
/* 1351 */       this.no = nopat;
/*      */     }
/*      */     int size() {
/* 1354 */       return (this.no == null) ? 1 : 2;
/*      */     }
/*      */     Token getChild(int index) {
/* 1357 */       if (index == 0) return this.yes; 
/* 1358 */       if (index == 1) return this.no; 
/* 1359 */       throw new RuntimeException("Internal Error: " + index);
/*      */     }
/*      */     
/*      */     public String toString(int options) {
/*      */       String ret;
/* 1364 */       if (this.refNumber > 0) {
/* 1365 */         ret = "(?(" + this.refNumber + ")";
/* 1366 */       } else if (this.condition.type == 8) {
/* 1367 */         ret = "(?(" + this.condition + ")";
/*      */       } else {
/* 1369 */         ret = "(?" + this.condition;
/*      */       } 
/*      */       
/* 1372 */       if (this.no == null) {
/* 1373 */         ret = ret + this.yes + ")";
/*      */       } else {
/* 1375 */         ret = ret + this.yes + "|" + this.no + ")";
/*      */       } 
/* 1377 */       return ret;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class ModifierToken
/*      */     extends Token
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -9114536559696480356L;
/*      */     
/*      */     final Token child;
/*      */     final int add;
/*      */     final int mask;
/*      */     
/*      */     ModifierToken(Token tok, int add, int mask) {
/* 1393 */       super(25);
/* 1394 */       this.child = tok;
/* 1395 */       this.add = add;
/* 1396 */       this.mask = mask;
/*      */     }
/*      */     
/*      */     int size() {
/* 1400 */       return 1;
/*      */     }
/*      */     Token getChild(int index) {
/* 1403 */       return this.child;
/*      */     }
/*      */     
/*      */     int getOptions() {
/* 1407 */       return this.add;
/*      */     }
/*      */     int getOptionsMask() {
/* 1410 */       return this.mask;
/*      */     }
/*      */     
/*      */     public String toString(int options) {
/* 1414 */       return "(?" + ((this.add == 0) ? "" : 
/* 1415 */         REUtil.createOptionString(this.add)) + ((this.mask == 0) ? "" : 
/* 1416 */         REUtil.createOptionString(this.mask)) + ":" + this.child
/*      */         
/* 1418 */         .toString(options) + ")";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class UnionToken
/*      */     extends Token
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -2568843945989489861L;
/*      */ 
/*      */ 
/*      */     
/*      */     List<Token> children;
/*      */ 
/*      */     
/* 1436 */     private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("children", Vector.class) };
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     UnionToken(int type) {
/* 1442 */       super(type);
/*      */     }
/*      */     
/*      */     void addChild(Token tok) {
/*      */       StringBuilder buffer;
/* 1447 */       if (tok == null)
/* 1448 */         return;  if (this.children == null) this.children = new ArrayList<>(); 
/* 1449 */       if (this.type == 2) {
/* 1450 */         this.children.add(tok);
/*      */         
/*      */         return;
/*      */       } 
/* 1454 */       if (tok.type == 1) {
/* 1455 */         for (int i = 0; i < tok.size(); i++)
/* 1456 */           addChild(tok.getChild(i)); 
/*      */         return;
/*      */       } 
/* 1459 */       int size = this.children.size();
/* 1460 */       if (size == 0) {
/* 1461 */         this.children.add(tok);
/*      */         return;
/*      */       } 
/* 1464 */       Token previous = this.children.get(size - 1);
/* 1465 */       if ((previous.type != 0 && previous.type != 10) || (tok.type != 0 && tok.type != 10)) {
/*      */         
/* 1467 */         this.children.add(tok);
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */ 
/*      */       
/* 1474 */       int nextMaxLength = (tok.type == 0) ? 2 : tok.getString().length();
/* 1475 */       if (previous.type == 0) {
/* 1476 */         buffer = new StringBuilder(2 + nextMaxLength);
/* 1477 */         int ch = previous.getChar();
/* 1478 */         if (ch >= 65536) {
/* 1479 */           buffer.append(REUtil.decomposeToSurrogates(ch));
/*      */         } else {
/* 1481 */           buffer.append((char)ch);
/* 1482 */         }  previous = Token.createString(null);
/* 1483 */         this.children.set(size - 1, previous);
/*      */       } else {
/* 1485 */         buffer = new StringBuilder(previous.getString().length() + nextMaxLength);
/* 1486 */         buffer.append(previous.getString());
/*      */       } 
/*      */       
/* 1489 */       if (tok.type == 0)
/* 1490 */       { int ch = tok.getChar();
/* 1491 */         if (ch >= 65536) {
/* 1492 */           buffer.append(REUtil.decomposeToSurrogates(ch));
/*      */         } else {
/* 1494 */           buffer.append((char)ch);
/*      */         }  }
/* 1496 */       else { buffer.append(tok.getString()); }
/*      */ 
/*      */       
/* 1499 */       ((Token.StringToken)previous).string = new String(buffer);
/*      */     }
/*      */ 
/*      */     
/*      */     int size() {
/* 1504 */       return (this.children == null) ? 0 : this.children.size();
/*      */     }
/*      */     
/*      */     Token getChild(int index) {
/* 1508 */       return this.children.get(index);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString(int options) {
/*      */       String ret;
/* 1514 */       if (this.type == 1) {
/* 1515 */         if (this.children.size() == 2)
/* 1516 */         { Token ch = getChild(0);
/* 1517 */           Token ch2 = getChild(1);
/* 1518 */           if (ch2.type == 3 && ch2.getChild(0) == ch) {
/* 1519 */             ret = ch.toString(options) + "+";
/* 1520 */           } else if (ch2.type == 9 && ch2.getChild(0) == ch) {
/* 1521 */             ret = ch.toString(options) + "+?";
/*      */           } else {
/* 1523 */             ret = ch.toString(options) + ch2.toString(options);
/*      */           }  }
/* 1525 */         else { StringBuilder sb = new StringBuilder();
/* 1526 */           for (int i = 0; i < this.children.size(); i++) {
/* 1527 */             sb.append(((Token)this.children.get(i)).toString(options));
/*      */           }
/* 1529 */           ret = new String(sb); }
/*      */         
/* 1531 */         return ret;
/*      */       } 
/* 1533 */       if (this.children.size() == 2 && (getChild(1)).type == 7) {
/* 1534 */         ret = getChild(0).toString(options) + "?";
/* 1535 */       } else if (this.children.size() == 2 && 
/* 1536 */         (getChild(0)).type == 7) {
/* 1537 */         ret = getChild(1).toString(options) + "??";
/*      */       } else {
/* 1539 */         StringBuilder sb = new StringBuilder();
/* 1540 */         sb.append(((Token)this.children.get(0)).toString(options));
/* 1541 */         for (int i = 1; i < this.children.size(); i++) {
/* 1542 */           sb.append('|');
/* 1543 */           sb.append(((Token)this.children.get(i)).toString(options));
/*      */         } 
/* 1545 */         ret = new String(sb);
/*      */       } 
/* 1547 */       return ret;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeObject(ObjectOutputStream out) throws IOException {
/* 1555 */       Vector<Token> vChildren = (this.children == null) ? null : new Vector<>(this.children);
/*      */ 
/*      */       
/* 1558 */       ObjectOutputStream.PutField pf = out.putFields();
/* 1559 */       pf.put("children", vChildren);
/* 1560 */       out.writeFields();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 1567 */       ObjectInputStream.GetField gf = in.readFields();
/* 1568 */       Vector<Token> vChildren = (Vector<Token>)gf.get("children", (Object)null);
/*      */ 
/*      */       
/* 1571 */       if (vChildren != null) this.children = new ArrayList<>(vChildren); 
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xpath/regex/Token.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */