/*     */ package sun.util.locale;
/*     */ 
/*     */ import java.util.HashMap;
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
/*     */ final class LocaleEquivalentMaps
/*     */ {
/*  38 */   static final Map<String, String> singleEquivMap = new HashMap<>();
/*  39 */   static final Map<String, String[]> multiEquivsMap = (Map)new HashMap<>();
/*  40 */   static final Map<String, String> regionVariantEquivMap = new HashMap<>();
/*     */ 
/*     */   
/*     */   static {
/*  44 */     singleEquivMap.put("aam", "aas");
/*  45 */     singleEquivMap.put("aas", "aam");
/*  46 */     singleEquivMap.put("acn", "xia");
/*  47 */     singleEquivMap.put("adp", "dz");
/*  48 */     singleEquivMap.put("adx", "pcr");
/*  49 */     singleEquivMap.put("ami", "i-ami");
/*  50 */     singleEquivMap.put("art-lojban", "jbo");
/*  51 */     singleEquivMap.put("ase", "sgn-us");
/*  52 */     singleEquivMap.put("aue", "ktz");
/*  53 */     singleEquivMap.put("ayx", "nun");
/*  54 */     singleEquivMap.put("bcg", "bgm");
/*  55 */     singleEquivMap.put("bfi", "sgn-gb");
/*  56 */     singleEquivMap.put("bfy", "ppa");
/*  57 */     singleEquivMap.put("bgm", "bcg");
/*  58 */     singleEquivMap.put("bjd", "drl");
/*  59 */     singleEquivMap.put("bmf", "krm");
/*  60 */     singleEquivMap.put("bnn", "i-bnn");
/*  61 */     singleEquivMap.put("bzs", "sgn-br");
/*  62 */     singleEquivMap.put("cax", "xba");
/*  63 */     singleEquivMap.put("cir", "meg");
/*  64 */     singleEquivMap.put("cjr", "mom");
/*  65 */     singleEquivMap.put("cka", "cmr");
/*  66 */     singleEquivMap.put("cmk", "xch");
/*  67 */     singleEquivMap.put("cmn-hans", "zh-cmn-hans");
/*  68 */     singleEquivMap.put("cmn-hant", "zh-cmn-hant");
/*  69 */     singleEquivMap.put("cmr", "cka");
/*  70 */     singleEquivMap.put("cqu", "quh");
/*  71 */     singleEquivMap.put("csn", "sgn-co");
/*  72 */     singleEquivMap.put("dev", "gav");
/*  73 */     singleEquivMap.put("drh", "khk");
/*  74 */     singleEquivMap.put("drl", "bjd");
/*  75 */     singleEquivMap.put("dse", "sgn-nl");
/*  76 */     singleEquivMap.put("dsl", "sgn-dk");
/*  77 */     singleEquivMap.put("duz", "guv");
/*  78 */     singleEquivMap.put("dz", "adp");
/*  79 */     singleEquivMap.put("ema", "uok");
/*  80 */     singleEquivMap.put("en-gb-oed", "en-gb-oxendict");
/*  81 */     singleEquivMap.put("en-gb-oxendict", "en-gb-oed");
/*  82 */     singleEquivMap.put("fsl", "sgn-fr");
/*  83 */     singleEquivMap.put("gal", "ilw");
/*  84 */     singleEquivMap.put("gan", "zh-gan");
/*  85 */     singleEquivMap.put("gav", "dev");
/*  86 */     singleEquivMap.put("gdj", "kvs");
/*  87 */     singleEquivMap.put("ggn", "gvr");
/*  88 */     singleEquivMap.put("gsg", "sgn-de");
/*  89 */     singleEquivMap.put("gss", "sgn-gr");
/*  90 */     singleEquivMap.put("gti", "nyc");
/*  91 */     singleEquivMap.put("guv", "duz");
/*  92 */     singleEquivMap.put("gvr", "ggn");
/*  93 */     singleEquivMap.put("he", "iw");
/*  94 */     singleEquivMap.put("hle", "sca");
/*  95 */     singleEquivMap.put("hrr", "jal");
/*  96 */     singleEquivMap.put("hsn", "zh-xiang");
/*  97 */     singleEquivMap.put("huw", "pmc");
/*  98 */     singleEquivMap.put("i-ami", "ami");
/*  99 */     singleEquivMap.put("i-bnn", "bnn");
/* 100 */     singleEquivMap.put("i-klingon", "tlh");
/* 101 */     singleEquivMap.put("i-lux", "lb");
/* 102 */     singleEquivMap.put("i-navajo", "nv");
/* 103 */     singleEquivMap.put("i-pwn", "pwn");
/* 104 */     singleEquivMap.put("i-tao", "tao");
/* 105 */     singleEquivMap.put("i-tay", "tay");
/* 106 */     singleEquivMap.put("i-tsu", "tsu");
/* 107 */     singleEquivMap.put("ibi", "opa");
/* 108 */     singleEquivMap.put("id", "in");
/* 109 */     singleEquivMap.put("ilw", "gal");
/* 110 */     singleEquivMap.put("in", "id");
/* 111 */     singleEquivMap.put("ise", "sgn-it");
/* 112 */     singleEquivMap.put("isg", "sgn-ie");
/* 113 */     singleEquivMap.put("iw", "he");
/* 114 */     singleEquivMap.put("jal", "hrr");
/* 115 */     singleEquivMap.put("jbo", "art-lojban");
/* 116 */     singleEquivMap.put("ji", "yi");
/* 117 */     singleEquivMap.put("jsl", "sgn-jp");
/* 118 */     singleEquivMap.put("jv", "jw");
/* 119 */     singleEquivMap.put("jw", "jv");
/* 120 */     singleEquivMap.put("kak", "tne");
/* 121 */     singleEquivMap.put("kdz", "ncp");
/* 122 */     singleEquivMap.put("kgc", "tdf");
/* 123 */     singleEquivMap.put("kgh", "kml");
/* 124 */     singleEquivMap.put("khk", "drh");
/* 125 */     singleEquivMap.put("kml", "kgh");
/* 126 */     singleEquivMap.put("koj", "kwv");
/* 127 */     singleEquivMap.put("krm", "bmf");
/* 128 */     singleEquivMap.put("ktz", "aue");
/* 129 */     singleEquivMap.put("kvs", "gdj");
/* 130 */     singleEquivMap.put("kwq", "yam");
/* 131 */     singleEquivMap.put("kwv", "koj");
/* 132 */     singleEquivMap.put("kxe", "tvd");
/* 133 */     singleEquivMap.put("lb", "i-lux");
/* 134 */     singleEquivMap.put("lcq", "ppr");
/* 135 */     singleEquivMap.put("lii", "raq");
/* 136 */     singleEquivMap.put("lmm", "rmx");
/* 137 */     singleEquivMap.put("lrr", "yma");
/* 138 */     singleEquivMap.put("meg", "cir");
/* 139 */     singleEquivMap.put("mfs", "sgn-mx");
/* 140 */     singleEquivMap.put("mo", "ro");
/* 141 */     singleEquivMap.put("mom", "cjr");
/* 142 */     singleEquivMap.put("mtm", "ymt");
/* 143 */     singleEquivMap.put("nad", "xny");
/* 144 */     singleEquivMap.put("nan", "zh-min-nan");
/* 145 */     singleEquivMap.put("nb", "no-bok");
/* 146 */     singleEquivMap.put("ncp", "kdz");
/* 147 */     singleEquivMap.put("ncs", "sgn-ni");
/* 148 */     singleEquivMap.put("ngv", "nnx");
/* 149 */     singleEquivMap.put("nn", "no-nyn");
/* 150 */     singleEquivMap.put("nnx", "ngv");
/* 151 */     singleEquivMap.put("no-bok", "nb");
/* 152 */     singleEquivMap.put("no-nyn", "nn");
/* 153 */     singleEquivMap.put("nsl", "sgn-no");
/* 154 */     singleEquivMap.put("nun", "ayx");
/* 155 */     singleEquivMap.put("nv", "i-navajo");
/* 156 */     singleEquivMap.put("nyc", "gti");
/* 157 */     singleEquivMap.put("opa", "ibi");
/* 158 */     singleEquivMap.put("pcr", "adx");
/* 159 */     singleEquivMap.put("phr", "pmu");
/* 160 */     singleEquivMap.put("pmc", "huw");
/* 161 */     singleEquivMap.put("pmu", "phr");
/* 162 */     singleEquivMap.put("ppa", "bfy");
/* 163 */     singleEquivMap.put("ppr", "lcq");
/* 164 */     singleEquivMap.put("prt", "pry");
/* 165 */     singleEquivMap.put("pry", "prt");
/* 166 */     singleEquivMap.put("psr", "sgn-pt");
/* 167 */     singleEquivMap.put("pub", "puz");
/* 168 */     singleEquivMap.put("puz", "pub");
/* 169 */     singleEquivMap.put("pwn", "i-pwn");
/* 170 */     singleEquivMap.put("quh", "cqu");
/* 171 */     singleEquivMap.put("raq", "lii");
/* 172 */     singleEquivMap.put("ras", "tie");
/* 173 */     singleEquivMap.put("rmx", "lmm");
/* 174 */     singleEquivMap.put("ro", "mo");
/* 175 */     singleEquivMap.put("sca", "hle");
/* 176 */     singleEquivMap.put("sfb", "sgn-be-fr");
/* 177 */     singleEquivMap.put("sfs", "sgn-za");
/* 178 */     singleEquivMap.put("sgg", "sgn-ch-de");
/* 179 */     singleEquivMap.put("sgn-be-fr", "sfb");
/* 180 */     singleEquivMap.put("sgn-be-nl", "vgt");
/* 181 */     singleEquivMap.put("sgn-br", "bzs");
/* 182 */     singleEquivMap.put("sgn-ch-de", "sgg");
/* 183 */     singleEquivMap.put("sgn-co", "csn");
/* 184 */     singleEquivMap.put("sgn-de", "gsg");
/* 185 */     singleEquivMap.put("sgn-dk", "dsl");
/* 186 */     singleEquivMap.put("sgn-es", "ssp");
/* 187 */     singleEquivMap.put("sgn-fr", "fsl");
/* 188 */     singleEquivMap.put("sgn-gb", "bfi");
/* 189 */     singleEquivMap.put("sgn-gr", "gss");
/* 190 */     singleEquivMap.put("sgn-ie", "isg");
/* 191 */     singleEquivMap.put("sgn-it", "ise");
/* 192 */     singleEquivMap.put("sgn-jp", "jsl");
/* 193 */     singleEquivMap.put("sgn-mx", "mfs");
/* 194 */     singleEquivMap.put("sgn-ni", "ncs");
/* 195 */     singleEquivMap.put("sgn-nl", "dse");
/* 196 */     singleEquivMap.put("sgn-no", "nsl");
/* 197 */     singleEquivMap.put("sgn-pt", "psr");
/* 198 */     singleEquivMap.put("sgn-se", "swl");
/* 199 */     singleEquivMap.put("sgn-us", "ase");
/* 200 */     singleEquivMap.put("sgn-za", "sfs");
/* 201 */     singleEquivMap.put("ssp", "sgn-es");
/* 202 */     singleEquivMap.put("suj", "xsj");
/* 203 */     singleEquivMap.put("swl", "sgn-se");
/* 204 */     singleEquivMap.put("taj", "tsf");
/* 205 */     singleEquivMap.put("tao", "i-tao");
/* 206 */     singleEquivMap.put("tay", "i-tay");
/* 207 */     singleEquivMap.put("tdf", "kgc");
/* 208 */     singleEquivMap.put("thc", "tpo");
/* 209 */     singleEquivMap.put("tie", "ras");
/* 210 */     singleEquivMap.put("tkk", "twm");
/* 211 */     singleEquivMap.put("tlh", "i-klingon");
/* 212 */     singleEquivMap.put("tlw", "weo");
/* 213 */     singleEquivMap.put("tmp", "tyj");
/* 214 */     singleEquivMap.put("tne", "kak");
/* 215 */     singleEquivMap.put("tpo", "thc");
/* 216 */     singleEquivMap.put("tsf", "taj");
/* 217 */     singleEquivMap.put("tsu", "i-tsu");
/* 218 */     singleEquivMap.put("tvd", "kxe");
/* 219 */     singleEquivMap.put("twm", "tkk");
/* 220 */     singleEquivMap.put("tyj", "tmp");
/* 221 */     singleEquivMap.put("uok", "ema");
/* 222 */     singleEquivMap.put("vgt", "sgn-be-nl");
/* 223 */     singleEquivMap.put("waw", "xkh");
/* 224 */     singleEquivMap.put("weo", "tlw");
/* 225 */     singleEquivMap.put("wuu", "zh-wuu");
/* 226 */     singleEquivMap.put("xba", "cax");
/* 227 */     singleEquivMap.put("xch", "cmk");
/* 228 */     singleEquivMap.put("xia", "acn");
/* 229 */     singleEquivMap.put("xkh", "waw");
/* 230 */     singleEquivMap.put("xny", "nad");
/* 231 */     singleEquivMap.put("xsj", "suj");
/* 232 */     singleEquivMap.put("yam", "kwq");
/* 233 */     singleEquivMap.put("yi", "ji");
/* 234 */     singleEquivMap.put("yma", "lrr");
/* 235 */     singleEquivMap.put("ymt", "mtm");
/* 236 */     singleEquivMap.put("yos", "zom");
/* 237 */     singleEquivMap.put("yue", "zh-yue");
/* 238 */     singleEquivMap.put("yug", "yuu");
/* 239 */     singleEquivMap.put("yuu", "yug");
/* 240 */     singleEquivMap.put("zh-cmn-hans", "cmn-hans");
/* 241 */     singleEquivMap.put("zh-cmn-hant", "cmn-hant");
/* 242 */     singleEquivMap.put("zh-gan", "gan");
/* 243 */     singleEquivMap.put("zh-min-nan", "nan");
/* 244 */     singleEquivMap.put("zh-wuu", "wuu");
/* 245 */     singleEquivMap.put("zh-xiang", "hsn");
/* 246 */     singleEquivMap.put("zh-yue", "yue");
/* 247 */     singleEquivMap.put("zom", "yos");
/*     */     
/* 249 */     multiEquivsMap.put("ccq", new String[] { "rki", "ybd" });
/* 250 */     multiEquivsMap.put("cmn", new String[] { "zh-guoyu", "zh-cmn" });
/* 251 */     multiEquivsMap.put("coy", new String[] { "pij", "nts" });
/* 252 */     multiEquivsMap.put("drw", new String[] { "prs", "tnf" });
/* 253 */     multiEquivsMap.put("dtp", new String[] { "ktr", "kzj", "kzt", "tdu" });
/* 254 */     multiEquivsMap.put("gfx", new String[] { "vaj", "mwj", "oun" });
/* 255 */     multiEquivsMap.put("hak", new String[] { "i-hak", "zh-hakka" });
/* 256 */     multiEquivsMap.put("i-hak", new String[] { "hak", "zh-hakka" });
/* 257 */     multiEquivsMap.put("jeg", new String[] { "oyb", "skk", "thx" });
/* 258 */     multiEquivsMap.put("ktr", new String[] { "dtp", "kzj", "kzt", "tdu" });
/* 259 */     multiEquivsMap.put("kzj", new String[] { "dtp", "ktr", "kzt", "tdu" });
/* 260 */     multiEquivsMap.put("kzt", new String[] { "dtp", "ktr", "kzj", "tdu" });
/* 261 */     multiEquivsMap.put("mry", new String[] { "mst", "myt" });
/* 262 */     multiEquivsMap.put("mst", new String[] { "mry", "myt" });
/* 263 */     multiEquivsMap.put("mwj", new String[] { "vaj", "gfx", "oun" });
/* 264 */     multiEquivsMap.put("myt", new String[] { "mry", "mst" });
/* 265 */     multiEquivsMap.put("nts", new String[] { "pij", "coy" });
/* 266 */     multiEquivsMap.put("oun", new String[] { "vaj", "gfx", "mwj" });
/* 267 */     multiEquivsMap.put("oyb", new String[] { "jeg", "skk", "thx" });
/* 268 */     multiEquivsMap.put("pij", new String[] { "coy", "nts" });
/* 269 */     multiEquivsMap.put("prs", new String[] { "drw", "tnf" });
/* 270 */     multiEquivsMap.put("rki", new String[] { "ccq", "ybd" });
/* 271 */     multiEquivsMap.put("skk", new String[] { "oyb", "jeg", "thx" });
/* 272 */     multiEquivsMap.put("tdu", new String[] { "dtp", "ktr", "kzj", "kzt" });
/* 273 */     multiEquivsMap.put("thx", new String[] { "oyb", "jeg", "skk" });
/* 274 */     multiEquivsMap.put("tnf", new String[] { "prs", "drw" });
/* 275 */     multiEquivsMap.put("vaj", new String[] { "gfx", "mwj", "oun" });
/* 276 */     multiEquivsMap.put("ybd", new String[] { "rki", "ccq" });
/* 277 */     multiEquivsMap.put("zh-cmn", new String[] { "cmn", "zh-guoyu" });
/* 278 */     multiEquivsMap.put("zh-guoyu", new String[] { "cmn", "zh-cmn" });
/* 279 */     multiEquivsMap.put("zh-hakka", new String[] { "hak", "i-hak" });
/*     */     
/* 281 */     regionVariantEquivMap.put("-alalc97", "-heploc");
/* 282 */     regionVariantEquivMap.put("-bu", "-mm");
/* 283 */     regionVariantEquivMap.put("-cd", "-zr");
/* 284 */     regionVariantEquivMap.put("-dd", "-de");
/* 285 */     regionVariantEquivMap.put("-de", "-dd");
/* 286 */     regionVariantEquivMap.put("-fr", "-fx");
/* 287 */     regionVariantEquivMap.put("-fx", "-fr");
/* 288 */     regionVariantEquivMap.put("-heploc", "-alalc97");
/* 289 */     regionVariantEquivMap.put("-mm", "-bu");
/* 290 */     regionVariantEquivMap.put("-tl", "-tp");
/* 291 */     regionVariantEquivMap.put("-tp", "-tl");
/* 292 */     regionVariantEquivMap.put("-yd", "-ye");
/* 293 */     regionVariantEquivMap.put("-ye", "-yd");
/* 294 */     regionVariantEquivMap.put("-zr", "-cd");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/LocaleEquivalentMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */