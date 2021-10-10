/*      */ package com.sun.media.sound;
/*      */ 
/*      */ import java.util.Random;
/*      */ import javax.sound.midi.Patch;
/*      */ import javax.sound.sampled.AudioFormat;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class EmergencySoundbank
/*      */ {
/*   40 */   private static final String[] general_midi_instruments = new String[] { "Acoustic Grand Piano", "Bright Acoustic Piano", "Electric Grand Piano", "Honky-tonk Piano", "Electric Piano 1", "Electric Piano 2", "Harpsichord", "Clavi", "Celesta", "Glockenspiel", "Music Box", "Vibraphone", "Marimba", "Xylophone", "Tubular Bells", "Dulcimer", "Drawbar Organ", "Percussive Organ", "Rock Organ", "Church Organ", "Reed Organ", "Accordion", "Harmonica", "Tango Accordion", "Acoustic Guitar (nylon)", "Acoustic Guitar (steel)", "Electric Guitar (jazz)", "Electric Guitar (clean)", "Electric Guitar (muted)", "Overdriven Guitar", "Distortion Guitar", "Guitar harmonics", "Acoustic Bass", "Electric Bass (finger)", "Electric Bass (pick)", "Fretless Bass", "Slap Bass 1", "Slap Bass 2", "Synth Bass 1", "Synth Bass 2", "Violin", "Viola", "Cello", "Contrabass", "Tremolo Strings", "Pizzicato Strings", "Orchestral Harp", "Timpani", "String Ensemble 1", "String Ensemble 2", "SynthStrings 1", "SynthStrings 2", "Choir Aahs", "Voice Oohs", "Synth Voice", "Orchestra Hit", "Trumpet", "Trombone", "Tuba", "Muted Trumpet", "French Horn", "Brass Section", "SynthBrass 1", "SynthBrass 2", "Soprano Sax", "Alto Sax", "Tenor Sax", "Baritone Sax", "Oboe", "English Horn", "Bassoon", "Clarinet", "Piccolo", "Flute", "Recorder", "Pan Flute", "Blown Bottle", "Shakuhachi", "Whistle", "Ocarina", "Lead 1 (square)", "Lead 2 (sawtooth)", "Lead 3 (calliope)", "Lead 4 (chiff)", "Lead 5 (charang)", "Lead 6 (voice)", "Lead 7 (fifths)", "Lead 8 (bass + lead)", "Pad 1 (new age)", "Pad 2 (warm)", "Pad 3 (polysynth)", "Pad 4 (choir)", "Pad 5 (bowed)", "Pad 6 (metallic)", "Pad 7 (halo)", "Pad 8 (sweep)", "FX 1 (rain)", "FX 2 (soundtrack)", "FX 3 (crystal)", "FX 4 (atmosphere)", "FX 5 (brightness)", "FX 6 (goblins)", "FX 7 (echoes)", "FX 8 (sci-fi)", "Sitar", "Banjo", "Shamisen", "Koto", "Kalimba", "Bag pipe", "Fiddle", "Shanai", "Tinkle Bell", "Agogo", "Steel Drums", "Woodblock", "Taiko Drum", "Melodic Tom", "Synth Drum", "Reverse Cymbal", "Guitar Fret Noise", "Breath Noise", "Seashore", "Bird Tweet", "Telephone Ring", "Helicopter", "Applause", "Gunshot" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SF2Soundbank createSoundbank() throws Exception {
/*  172 */     SF2Soundbank sF2Soundbank = new SF2Soundbank();
/*  173 */     sF2Soundbank.setName("Emergency GM sound set");
/*  174 */     sF2Soundbank.setVendor("Generated");
/*  175 */     sF2Soundbank.setDescription("Emergency generated soundbank");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  181 */     SF2Layer sF2Layer1 = new_bass_drum(sF2Soundbank);
/*  182 */     SF2Layer sF2Layer2 = new_snare_drum(sF2Soundbank);
/*  183 */     SF2Layer sF2Layer3 = new_tom(sF2Soundbank);
/*  184 */     SF2Layer sF2Layer4 = new_open_hihat(sF2Soundbank);
/*  185 */     SF2Layer sF2Layer5 = new_closed_hihat(sF2Soundbank);
/*  186 */     SF2Layer sF2Layer6 = new_crash_cymbal(sF2Soundbank);
/*  187 */     SF2Layer sF2Layer7 = new_side_stick(sF2Soundbank);
/*      */     
/*  189 */     SF2Layer[] arrayOfSF2Layer = new SF2Layer[128];
/*  190 */     arrayOfSF2Layer[35] = sF2Layer1;
/*  191 */     arrayOfSF2Layer[36] = sF2Layer1;
/*  192 */     arrayOfSF2Layer[38] = sF2Layer2;
/*  193 */     arrayOfSF2Layer[40] = sF2Layer2;
/*  194 */     arrayOfSF2Layer[41] = sF2Layer3;
/*  195 */     arrayOfSF2Layer[43] = sF2Layer3;
/*  196 */     arrayOfSF2Layer[45] = sF2Layer3;
/*  197 */     arrayOfSF2Layer[47] = sF2Layer3;
/*  198 */     arrayOfSF2Layer[48] = sF2Layer3;
/*  199 */     arrayOfSF2Layer[50] = sF2Layer3;
/*  200 */     arrayOfSF2Layer[42] = sF2Layer5;
/*  201 */     arrayOfSF2Layer[44] = sF2Layer5;
/*  202 */     arrayOfSF2Layer[46] = sF2Layer4;
/*  203 */     arrayOfSF2Layer[49] = sF2Layer6;
/*  204 */     arrayOfSF2Layer[51] = sF2Layer6;
/*  205 */     arrayOfSF2Layer[52] = sF2Layer6;
/*  206 */     arrayOfSF2Layer[55] = sF2Layer6;
/*  207 */     arrayOfSF2Layer[57] = sF2Layer6;
/*  208 */     arrayOfSF2Layer[59] = sF2Layer6;
/*      */ 
/*      */     
/*  211 */     arrayOfSF2Layer[37] = sF2Layer7;
/*  212 */     arrayOfSF2Layer[39] = sF2Layer7;
/*  213 */     arrayOfSF2Layer[53] = sF2Layer7;
/*  214 */     arrayOfSF2Layer[54] = sF2Layer7;
/*  215 */     arrayOfSF2Layer[56] = sF2Layer7;
/*  216 */     arrayOfSF2Layer[58] = sF2Layer7;
/*  217 */     arrayOfSF2Layer[69] = sF2Layer7;
/*  218 */     arrayOfSF2Layer[70] = sF2Layer7;
/*  219 */     arrayOfSF2Layer[75] = sF2Layer7;
/*  220 */     arrayOfSF2Layer[60] = sF2Layer7;
/*  221 */     arrayOfSF2Layer[61] = sF2Layer7;
/*  222 */     arrayOfSF2Layer[62] = sF2Layer7;
/*  223 */     arrayOfSF2Layer[63] = sF2Layer7;
/*  224 */     arrayOfSF2Layer[64] = sF2Layer7;
/*  225 */     arrayOfSF2Layer[65] = sF2Layer7;
/*  226 */     arrayOfSF2Layer[66] = sF2Layer7;
/*  227 */     arrayOfSF2Layer[67] = sF2Layer7;
/*  228 */     arrayOfSF2Layer[68] = sF2Layer7;
/*  229 */     arrayOfSF2Layer[71] = sF2Layer7;
/*  230 */     arrayOfSF2Layer[72] = sF2Layer7;
/*  231 */     arrayOfSF2Layer[73] = sF2Layer7;
/*  232 */     arrayOfSF2Layer[74] = sF2Layer7;
/*  233 */     arrayOfSF2Layer[76] = sF2Layer7;
/*  234 */     arrayOfSF2Layer[77] = sF2Layer7;
/*  235 */     arrayOfSF2Layer[78] = sF2Layer7;
/*  236 */     arrayOfSF2Layer[79] = sF2Layer7;
/*  237 */     arrayOfSF2Layer[80] = sF2Layer7;
/*  238 */     arrayOfSF2Layer[81] = sF2Layer7;
/*      */ 
/*      */     
/*  241 */     SF2Instrument sF2Instrument1 = new SF2Instrument(sF2Soundbank);
/*  242 */     sF2Instrument1.setName("Standard Kit");
/*  243 */     sF2Instrument1.setPatch(new ModelPatch(0, 0, true));
/*  244 */     sF2Soundbank.addInstrument(sF2Instrument1);
/*  245 */     for (byte b = 0; b < arrayOfSF2Layer.length; b++) {
/*  246 */       if (arrayOfSF2Layer[b] != null) {
/*  247 */         SF2InstrumentRegion sF2InstrumentRegion1 = new SF2InstrumentRegion();
/*  248 */         sF2InstrumentRegion1.setLayer(arrayOfSF2Layer[b]);
/*  249 */         sF2InstrumentRegion1.putBytes(43, new byte[] { (byte)b, (byte)b });
/*      */         
/*  251 */         sF2Instrument1.getRegions().add(sF2InstrumentRegion1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  260 */     SF2Layer sF2Layer8 = new_gpiano(sF2Soundbank);
/*  261 */     SF2Layer sF2Layer9 = new_gpiano2(sF2Soundbank);
/*  262 */     SF2Layer sF2Layer10 = new_piano_hammer(sF2Soundbank);
/*  263 */     SF2Layer sF2Layer11 = new_piano1(sF2Soundbank);
/*  264 */     SF2Layer sF2Layer12 = new_epiano1(sF2Soundbank);
/*  265 */     SF2Layer sF2Layer13 = new_epiano2(sF2Soundbank);
/*      */     
/*  267 */     SF2Layer sF2Layer14 = new_guitar1(sF2Soundbank);
/*  268 */     SF2Layer sF2Layer15 = new_guitar_pick(sF2Soundbank);
/*  269 */     SF2Layer sF2Layer16 = new_guitar_dist(sF2Soundbank);
/*  270 */     SF2Layer sF2Layer17 = new_bass1(sF2Soundbank);
/*  271 */     SF2Layer sF2Layer18 = new_bass2(sF2Soundbank);
/*  272 */     SF2Layer sF2Layer19 = new_synthbass(sF2Soundbank);
/*  273 */     SF2Layer sF2Layer20 = new_string2(sF2Soundbank);
/*  274 */     SF2Layer sF2Layer21 = new_orchhit(sF2Soundbank);
/*  275 */     SF2Layer sF2Layer22 = new_choir(sF2Soundbank);
/*  276 */     SF2Layer sF2Layer23 = new_solostring(sF2Soundbank);
/*  277 */     SF2Layer sF2Layer24 = new_organ(sF2Soundbank);
/*  278 */     SF2Layer sF2Layer25 = new_ch_organ(sF2Soundbank);
/*  279 */     SF2Layer sF2Layer26 = new_bell(sF2Soundbank);
/*  280 */     SF2Layer sF2Layer27 = new_flute(sF2Soundbank);
/*      */     
/*  282 */     SF2Layer sF2Layer28 = new_timpani(sF2Soundbank);
/*  283 */     SF2Layer sF2Layer29 = new_melodic_toms(sF2Soundbank);
/*  284 */     SF2Layer sF2Layer30 = new_trumpet(sF2Soundbank);
/*  285 */     SF2Layer sF2Layer31 = new_trombone(sF2Soundbank);
/*  286 */     SF2Layer sF2Layer32 = new_brass_section(sF2Soundbank);
/*  287 */     SF2Layer sF2Layer33 = new_horn(sF2Soundbank);
/*  288 */     SF2Layer sF2Layer34 = new_sax(sF2Soundbank);
/*  289 */     SF2Layer sF2Layer35 = new_oboe(sF2Soundbank);
/*  290 */     SF2Layer sF2Layer36 = new_bassoon(sF2Soundbank);
/*  291 */     SF2Layer sF2Layer37 = new_clarinet(sF2Soundbank);
/*  292 */     SF2Layer sF2Layer38 = new_reverse_cymbal(sF2Soundbank);
/*      */     
/*  294 */     SF2Layer sF2Layer39 = sF2Layer11;
/*      */     
/*  296 */     newInstrument(sF2Soundbank, "Piano", new Patch(0, 0), new SF2Layer[] { sF2Layer8, sF2Layer10 });
/*  297 */     newInstrument(sF2Soundbank, "Piano", new Patch(0, 1), new SF2Layer[] { sF2Layer9, sF2Layer10 });
/*  298 */     newInstrument(sF2Soundbank, "Piano", new Patch(0, 2), new SF2Layer[] { sF2Layer11 });
/*      */     
/*  300 */     SF2Instrument sF2Instrument2 = newInstrument(sF2Soundbank, "Honky-tonk Piano", new Patch(0, 3), new SF2Layer[] { sF2Layer11, sF2Layer11 });
/*      */     
/*  302 */     SF2InstrumentRegion sF2InstrumentRegion = sF2Instrument2.getRegions().get(0);
/*  303 */     sF2InstrumentRegion.putInteger(8, 80);
/*  304 */     sF2InstrumentRegion.putInteger(52, 30);
/*  305 */     sF2InstrumentRegion = sF2Instrument2.getRegions().get(1);
/*  306 */     sF2InstrumentRegion.putInteger(8, 30);
/*      */     
/*  308 */     newInstrument(sF2Soundbank, "Rhodes", new Patch(0, 4), new SF2Layer[] { sF2Layer13 });
/*  309 */     newInstrument(sF2Soundbank, "Rhodes", new Patch(0, 5), new SF2Layer[] { sF2Layer13 });
/*  310 */     newInstrument(sF2Soundbank, "Clavinet", new Patch(0, 6), new SF2Layer[] { sF2Layer12 });
/*  311 */     newInstrument(sF2Soundbank, "Clavinet", new Patch(0, 7), new SF2Layer[] { sF2Layer12 });
/*  312 */     newInstrument(sF2Soundbank, "Rhodes", new Patch(0, 8), new SF2Layer[] { sF2Layer13 });
/*  313 */     newInstrument(sF2Soundbank, "Bell", new Patch(0, 9), new SF2Layer[] { sF2Layer26 });
/*  314 */     newInstrument(sF2Soundbank, "Bell", new Patch(0, 10), new SF2Layer[] { sF2Layer26 });
/*  315 */     newInstrument(sF2Soundbank, "Vibraphone", new Patch(0, 11), new SF2Layer[] { sF2Layer26 });
/*  316 */     newInstrument(sF2Soundbank, "Marimba", new Patch(0, 12), new SF2Layer[] { sF2Layer26 });
/*  317 */     newInstrument(sF2Soundbank, "Marimba", new Patch(0, 13), new SF2Layer[] { sF2Layer26 });
/*  318 */     newInstrument(sF2Soundbank, "Bell", new Patch(0, 14), new SF2Layer[] { sF2Layer26 });
/*  319 */     newInstrument(sF2Soundbank, "Rock Organ", new Patch(0, 15), new SF2Layer[] { sF2Layer24 });
/*  320 */     newInstrument(sF2Soundbank, "Rock Organ", new Patch(0, 16), new SF2Layer[] { sF2Layer24 });
/*  321 */     newInstrument(sF2Soundbank, "Perc Organ", new Patch(0, 17), new SF2Layer[] { sF2Layer24 });
/*  322 */     newInstrument(sF2Soundbank, "Rock Organ", new Patch(0, 18), new SF2Layer[] { sF2Layer24 });
/*  323 */     newInstrument(sF2Soundbank, "Church Organ", new Patch(0, 19), new SF2Layer[] { sF2Layer25 });
/*  324 */     newInstrument(sF2Soundbank, "Accordion", new Patch(0, 20), new SF2Layer[] { sF2Layer24 });
/*  325 */     newInstrument(sF2Soundbank, "Accordion", new Patch(0, 21), new SF2Layer[] { sF2Layer24 });
/*  326 */     newInstrument(sF2Soundbank, "Accordion", new Patch(0, 22), new SF2Layer[] { sF2Layer24 });
/*  327 */     newInstrument(sF2Soundbank, "Accordion", new Patch(0, 23), new SF2Layer[] { sF2Layer24 });
/*  328 */     newInstrument(sF2Soundbank, "Guitar", new Patch(0, 24), new SF2Layer[] { sF2Layer14, sF2Layer15 });
/*  329 */     newInstrument(sF2Soundbank, "Guitar", new Patch(0, 25), new SF2Layer[] { sF2Layer14, sF2Layer15 });
/*  330 */     newInstrument(sF2Soundbank, "Guitar", new Patch(0, 26), new SF2Layer[] { sF2Layer14, sF2Layer15 });
/*  331 */     newInstrument(sF2Soundbank, "Guitar", new Patch(0, 27), new SF2Layer[] { sF2Layer14, sF2Layer15 });
/*  332 */     newInstrument(sF2Soundbank, "Guitar", new Patch(0, 28), new SF2Layer[] { sF2Layer14, sF2Layer15 });
/*  333 */     newInstrument(sF2Soundbank, "Distorted Guitar", new Patch(0, 29), new SF2Layer[] { sF2Layer16 });
/*  334 */     newInstrument(sF2Soundbank, "Distorted Guitar", new Patch(0, 30), new SF2Layer[] { sF2Layer16 });
/*  335 */     newInstrument(sF2Soundbank, "Guitar", new Patch(0, 31), new SF2Layer[] { sF2Layer14, sF2Layer15 });
/*  336 */     newInstrument(sF2Soundbank, "Finger Bass", new Patch(0, 32), new SF2Layer[] { sF2Layer17 });
/*  337 */     newInstrument(sF2Soundbank, "Finger Bass", new Patch(0, 33), new SF2Layer[] { sF2Layer17 });
/*  338 */     newInstrument(sF2Soundbank, "Finger Bass", new Patch(0, 34), new SF2Layer[] { sF2Layer17 });
/*  339 */     newInstrument(sF2Soundbank, "Frettless Bass", new Patch(0, 35), new SF2Layer[] { sF2Layer18 });
/*  340 */     newInstrument(sF2Soundbank, "Frettless Bass", new Patch(0, 36), new SF2Layer[] { sF2Layer18 });
/*  341 */     newInstrument(sF2Soundbank, "Frettless Bass", new Patch(0, 37), new SF2Layer[] { sF2Layer18 });
/*  342 */     newInstrument(sF2Soundbank, "Synth Bass1", new Patch(0, 38), new SF2Layer[] { sF2Layer19 });
/*  343 */     newInstrument(sF2Soundbank, "Synth Bass2", new Patch(0, 39), new SF2Layer[] { sF2Layer19 });
/*  344 */     newInstrument(sF2Soundbank, "Solo String", new Patch(0, 40), new SF2Layer[] { sF2Layer20, sF2Layer23 });
/*  345 */     newInstrument(sF2Soundbank, "Solo String", new Patch(0, 41), new SF2Layer[] { sF2Layer20, sF2Layer23 });
/*  346 */     newInstrument(sF2Soundbank, "Solo String", new Patch(0, 42), new SF2Layer[] { sF2Layer20, sF2Layer23 });
/*  347 */     newInstrument(sF2Soundbank, "Solo String", new Patch(0, 43), new SF2Layer[] { sF2Layer20, sF2Layer23 });
/*  348 */     newInstrument(sF2Soundbank, "Solo String", new Patch(0, 44), new SF2Layer[] { sF2Layer20, sF2Layer23 });
/*  349 */     newInstrument(sF2Soundbank, "Def", new Patch(0, 45), new SF2Layer[] { sF2Layer39 });
/*  350 */     newInstrument(sF2Soundbank, "Harp", new Patch(0, 46), new SF2Layer[] { sF2Layer26 });
/*  351 */     newInstrument(sF2Soundbank, "Timpani", new Patch(0, 47), new SF2Layer[] { sF2Layer28 });
/*  352 */     newInstrument(sF2Soundbank, "Strings", new Patch(0, 48), new SF2Layer[] { sF2Layer20 });
/*      */     
/*  354 */     sF2Instrument2 = newInstrument(sF2Soundbank, "Slow Strings", new Patch(0, 49), new SF2Layer[] { sF2Layer20 });
/*  355 */     sF2InstrumentRegion = sF2Instrument2.getRegions().get(0);
/*  356 */     sF2InstrumentRegion.putInteger(34, 2500);
/*  357 */     sF2InstrumentRegion.putInteger(38, 2000);
/*  358 */     newInstrument(sF2Soundbank, "Synth Strings", new Patch(0, 50), new SF2Layer[] { sF2Layer20 });
/*  359 */     newInstrument(sF2Soundbank, "Synth Strings", new Patch(0, 51), new SF2Layer[] { sF2Layer20 });
/*      */ 
/*      */     
/*  362 */     newInstrument(sF2Soundbank, "Choir", new Patch(0, 52), new SF2Layer[] { sF2Layer22 });
/*  363 */     newInstrument(sF2Soundbank, "Choir", new Patch(0, 53), new SF2Layer[] { sF2Layer22 });
/*  364 */     newInstrument(sF2Soundbank, "Choir", new Patch(0, 54), new SF2Layer[] { sF2Layer22 });
/*      */     
/*  366 */     SF2Instrument sF2Instrument3 = newInstrument(sF2Soundbank, "Orch Hit", new Patch(0, 55), new SF2Layer[] { sF2Layer21, sF2Layer21, sF2Layer28 });
/*      */     
/*  368 */     sF2InstrumentRegion = sF2Instrument3.getRegions().get(0);
/*  369 */     sF2InstrumentRegion.putInteger(51, -12);
/*  370 */     sF2InstrumentRegion.putInteger(48, -100);
/*      */     
/*  372 */     newInstrument(sF2Soundbank, "Trumpet", new Patch(0, 56), new SF2Layer[] { sF2Layer30 });
/*  373 */     newInstrument(sF2Soundbank, "Trombone", new Patch(0, 57), new SF2Layer[] { sF2Layer31 });
/*  374 */     newInstrument(sF2Soundbank, "Trombone", new Patch(0, 58), new SF2Layer[] { sF2Layer31 });
/*  375 */     newInstrument(sF2Soundbank, "Trumpet", new Patch(0, 59), new SF2Layer[] { sF2Layer30 });
/*  376 */     newInstrument(sF2Soundbank, "Horn", new Patch(0, 60), new SF2Layer[] { sF2Layer33 });
/*  377 */     newInstrument(sF2Soundbank, "Brass Section", new Patch(0, 61), new SF2Layer[] { sF2Layer32 });
/*  378 */     newInstrument(sF2Soundbank, "Brass Section", new Patch(0, 62), new SF2Layer[] { sF2Layer32 });
/*  379 */     newInstrument(sF2Soundbank, "Brass Section", new Patch(0, 63), new SF2Layer[] { sF2Layer32 });
/*  380 */     newInstrument(sF2Soundbank, "Sax", new Patch(0, 64), new SF2Layer[] { sF2Layer34 });
/*  381 */     newInstrument(sF2Soundbank, "Sax", new Patch(0, 65), new SF2Layer[] { sF2Layer34 });
/*  382 */     newInstrument(sF2Soundbank, "Sax", new Patch(0, 66), new SF2Layer[] { sF2Layer34 });
/*  383 */     newInstrument(sF2Soundbank, "Sax", new Patch(0, 67), new SF2Layer[] { sF2Layer34 });
/*  384 */     newInstrument(sF2Soundbank, "Oboe", new Patch(0, 68), new SF2Layer[] { sF2Layer35 });
/*  385 */     newInstrument(sF2Soundbank, "Horn", new Patch(0, 69), new SF2Layer[] { sF2Layer33 });
/*  386 */     newInstrument(sF2Soundbank, "Bassoon", new Patch(0, 70), new SF2Layer[] { sF2Layer36 });
/*  387 */     newInstrument(sF2Soundbank, "Clarinet", new Patch(0, 71), new SF2Layer[] { sF2Layer37 });
/*  388 */     newInstrument(sF2Soundbank, "Flute", new Patch(0, 72), new SF2Layer[] { sF2Layer27 });
/*  389 */     newInstrument(sF2Soundbank, "Flute", new Patch(0, 73), new SF2Layer[] { sF2Layer27 });
/*  390 */     newInstrument(sF2Soundbank, "Flute", new Patch(0, 74), new SF2Layer[] { sF2Layer27 });
/*  391 */     newInstrument(sF2Soundbank, "Flute", new Patch(0, 75), new SF2Layer[] { sF2Layer27 });
/*  392 */     newInstrument(sF2Soundbank, "Flute", new Patch(0, 76), new SF2Layer[] { sF2Layer27 });
/*  393 */     newInstrument(sF2Soundbank, "Flute", new Patch(0, 77), new SF2Layer[] { sF2Layer27 });
/*  394 */     newInstrument(sF2Soundbank, "Flute", new Patch(0, 78), new SF2Layer[] { sF2Layer27 });
/*  395 */     newInstrument(sF2Soundbank, "Flute", new Patch(0, 79), new SF2Layer[] { sF2Layer27 });
/*  396 */     newInstrument(sF2Soundbank, "Organ", new Patch(0, 80), new SF2Layer[] { sF2Layer24 });
/*  397 */     newInstrument(sF2Soundbank, "Organ", new Patch(0, 81), new SF2Layer[] { sF2Layer24 });
/*  398 */     newInstrument(sF2Soundbank, "Flute", new Patch(0, 82), new SF2Layer[] { sF2Layer27 });
/*  399 */     newInstrument(sF2Soundbank, "Organ", new Patch(0, 83), new SF2Layer[] { sF2Layer24 });
/*  400 */     newInstrument(sF2Soundbank, "Organ", new Patch(0, 84), new SF2Layer[] { sF2Layer24 });
/*  401 */     newInstrument(sF2Soundbank, "Choir", new Patch(0, 85), new SF2Layer[] { sF2Layer22 });
/*  402 */     newInstrument(sF2Soundbank, "Organ", new Patch(0, 86), new SF2Layer[] { sF2Layer24 });
/*  403 */     newInstrument(sF2Soundbank, "Organ", new Patch(0, 87), new SF2Layer[] { sF2Layer24 });
/*  404 */     newInstrument(sF2Soundbank, "Synth Strings", new Patch(0, 88), new SF2Layer[] { sF2Layer20 });
/*  405 */     newInstrument(sF2Soundbank, "Organ", new Patch(0, 89), new SF2Layer[] { sF2Layer24 });
/*  406 */     newInstrument(sF2Soundbank, "Def", new Patch(0, 90), new SF2Layer[] { sF2Layer39 });
/*  407 */     newInstrument(sF2Soundbank, "Choir", new Patch(0, 91), new SF2Layer[] { sF2Layer22 });
/*  408 */     newInstrument(sF2Soundbank, "Organ", new Patch(0, 92), new SF2Layer[] { sF2Layer24 });
/*  409 */     newInstrument(sF2Soundbank, "Organ", new Patch(0, 93), new SF2Layer[] { sF2Layer24 });
/*  410 */     newInstrument(sF2Soundbank, "Organ", new Patch(0, 94), new SF2Layer[] { sF2Layer24 });
/*  411 */     newInstrument(sF2Soundbank, "Organ", new Patch(0, 95), new SF2Layer[] { sF2Layer24 });
/*  412 */     newInstrument(sF2Soundbank, "Organ", new Patch(0, 96), new SF2Layer[] { sF2Layer24 });
/*  413 */     newInstrument(sF2Soundbank, "Organ", new Patch(0, 97), new SF2Layer[] { sF2Layer24 });
/*  414 */     newInstrument(sF2Soundbank, "Bell", new Patch(0, 98), new SF2Layer[] { sF2Layer26 });
/*  415 */     newInstrument(sF2Soundbank, "Organ", new Patch(0, 99), new SF2Layer[] { sF2Layer24 });
/*  416 */     newInstrument(sF2Soundbank, "Organ", new Patch(0, 100), new SF2Layer[] { sF2Layer24 });
/*  417 */     newInstrument(sF2Soundbank, "Organ", new Patch(0, 101), new SF2Layer[] { sF2Layer24 });
/*  418 */     newInstrument(sF2Soundbank, "Def", new Patch(0, 102), new SF2Layer[] { sF2Layer39 });
/*  419 */     newInstrument(sF2Soundbank, "Synth Strings", new Patch(0, 103), new SF2Layer[] { sF2Layer20 });
/*  420 */     newInstrument(sF2Soundbank, "Def", new Patch(0, 104), new SF2Layer[] { sF2Layer39 });
/*  421 */     newInstrument(sF2Soundbank, "Def", new Patch(0, 105), new SF2Layer[] { sF2Layer39 });
/*  422 */     newInstrument(sF2Soundbank, "Def", new Patch(0, 106), new SF2Layer[] { sF2Layer39 });
/*  423 */     newInstrument(sF2Soundbank, "Def", new Patch(0, 107), new SF2Layer[] { sF2Layer39 });
/*  424 */     newInstrument(sF2Soundbank, "Marimba", new Patch(0, 108), new SF2Layer[] { sF2Layer26 });
/*  425 */     newInstrument(sF2Soundbank, "Sax", new Patch(0, 109), new SF2Layer[] { sF2Layer34 });
/*  426 */     newInstrument(sF2Soundbank, "Solo String", new Patch(0, 110), new SF2Layer[] { sF2Layer20, sF2Layer23 });
/*  427 */     newInstrument(sF2Soundbank, "Oboe", new Patch(0, 111), new SF2Layer[] { sF2Layer35 });
/*  428 */     newInstrument(sF2Soundbank, "Bell", new Patch(0, 112), new SF2Layer[] { sF2Layer26 });
/*  429 */     newInstrument(sF2Soundbank, "Melodic Toms", new Patch(0, 113), new SF2Layer[] { sF2Layer29 });
/*  430 */     newInstrument(sF2Soundbank, "Marimba", new Patch(0, 114), new SF2Layer[] { sF2Layer26 });
/*  431 */     newInstrument(sF2Soundbank, "Melodic Toms", new Patch(0, 115), new SF2Layer[] { sF2Layer29 });
/*  432 */     newInstrument(sF2Soundbank, "Melodic Toms", new Patch(0, 116), new SF2Layer[] { sF2Layer29 });
/*  433 */     newInstrument(sF2Soundbank, "Melodic Toms", new Patch(0, 117), new SF2Layer[] { sF2Layer29 });
/*  434 */     newInstrument(sF2Soundbank, "Reverse Cymbal", new Patch(0, 118), new SF2Layer[] { sF2Layer38 });
/*  435 */     newInstrument(sF2Soundbank, "Reverse Cymbal", new Patch(0, 119), new SF2Layer[] { sF2Layer38 });
/*  436 */     newInstrument(sF2Soundbank, "Guitar", new Patch(0, 120), new SF2Layer[] { sF2Layer14 });
/*  437 */     newInstrument(sF2Soundbank, "Def", new Patch(0, 121), new SF2Layer[] { sF2Layer39 });
/*      */     
/*  439 */     sF2Instrument3 = newInstrument(sF2Soundbank, "Seashore/Reverse Cymbal", new Patch(0, 122), new SF2Layer[] { sF2Layer38 });
/*      */     
/*  441 */     sF2InstrumentRegion = sF2Instrument3.getRegions().get(0);
/*  442 */     sF2InstrumentRegion.putInteger(37, 1000);
/*  443 */     sF2InstrumentRegion.putInteger(36, 18500);
/*  444 */     sF2InstrumentRegion.putInteger(38, 4500);
/*  445 */     sF2InstrumentRegion.putInteger(8, -4500);
/*      */ 
/*      */     
/*  448 */     sF2Instrument3 = newInstrument(sF2Soundbank, "Bird/Flute", new Patch(0, 123), new SF2Layer[] { sF2Layer27 });
/*      */     
/*  450 */     sF2InstrumentRegion = sF2Instrument3.getRegions().get(0);
/*  451 */     sF2InstrumentRegion.putInteger(51, 24);
/*  452 */     sF2InstrumentRegion.putInteger(36, -3000);
/*  453 */     sF2InstrumentRegion.putInteger(37, 1000);
/*      */     
/*  455 */     newInstrument(sF2Soundbank, "Def", new Patch(0, 124), new SF2Layer[] { sF2Layer7 });
/*      */     
/*  457 */     sF2Instrument3 = newInstrument(sF2Soundbank, "Seashore/Reverse Cymbal", new Patch(0, 125), new SF2Layer[] { sF2Layer38 });
/*      */     
/*  459 */     sF2InstrumentRegion = sF2Instrument3.getRegions().get(0);
/*  460 */     sF2InstrumentRegion.putInteger(37, 1000);
/*  461 */     sF2InstrumentRegion.putInteger(36, 18500);
/*  462 */     sF2InstrumentRegion.putInteger(38, 4500);
/*  463 */     sF2InstrumentRegion.putInteger(8, -4500);
/*      */     
/*  465 */     newInstrument(sF2Soundbank, "Applause/crash_cymbal", new Patch(0, 126), new SF2Layer[] { sF2Layer6 });
/*      */     
/*  467 */     newInstrument(sF2Soundbank, "Gunshot/side_stick", new Patch(0, 127), new SF2Layer[] { sF2Layer7 });
/*      */     
/*  469 */     for (SF2Instrument sF2Instrument : sF2Soundbank.getInstruments()) {
/*  470 */       Patch patch = sF2Instrument.getPatch();
/*  471 */       if (!(patch instanceof ModelPatch) || 
/*  472 */         !((ModelPatch)patch).isPercussion())
/*      */       {
/*      */         
/*  475 */         sF2Instrument.setName(general_midi_instruments[patch.getProgram()]);
/*      */       }
/*      */     } 
/*  478 */     return sF2Soundbank;
/*      */   }
/*      */ 
/*      */   
/*      */   public static SF2Layer new_bell(SF2Soundbank paramSF2Soundbank) {
/*  483 */     Random random = new Random(102030201L);
/*  484 */     byte b1 = 8;
/*  485 */     int i = 4096 * b1;
/*  486 */     double[] arrayOfDouble = new double[i * 2];
/*  487 */     double d1 = (b1 * 25);
/*  488 */     double d2 = 0.01D;
/*  489 */     double d3 = 0.05D;
/*  490 */     double d4 = 0.2D;
/*  491 */     double d5 = 1.0E-5D;
/*  492 */     double d6 = d4;
/*  493 */     double d7 = Math.pow(d5 / d4, 0.025D);
/*  494 */     for (byte b2 = 0; b2 < 40; b2++) {
/*  495 */       double d8 = 1.0D + (random.nextDouble() * 2.0D - 1.0D) * 0.01D;
/*  496 */       double d9 = d2 + (d3 - d2) * b2 / 40.0D;
/*  497 */       complexGaussianDist(arrayOfDouble, d1 * (b2 + 1) * d8, d9, d6);
/*  498 */       d6 *= d7;
/*      */     } 
/*  500 */     SF2Sample sF2Sample = newSimpleFFTSample(paramSF2Soundbank, "EPiano", arrayOfDouble, d1);
/*  501 */     SF2Layer sF2Layer = newLayer(paramSF2Soundbank, "EPiano", sF2Sample);
/*  502 */     SF2Region sF2Region = sF2Layer.getRegions().get(0);
/*  503 */     sF2Region.putInteger(54, 1);
/*  504 */     sF2Region.putInteger(34, -12000);
/*  505 */     sF2Region.putInteger(38, 0);
/*  506 */     sF2Region.putInteger(36, 4000);
/*  507 */     sF2Region.putInteger(37, 1000);
/*  508 */     sF2Region.putInteger(26, 1200);
/*  509 */     sF2Region.putInteger(30, 12000);
/*  510 */     sF2Region.putInteger(11, -9000);
/*  511 */     sF2Region.putInteger(8, 16000);
/*  512 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */   
/*      */   public static SF2Layer new_guitar1(SF2Soundbank paramSF2Soundbank) {
/*  517 */     byte b1 = 8;
/*  518 */     int i = 4096 * b1;
/*  519 */     double[] arrayOfDouble1 = new double[i * 2];
/*  520 */     double d1 = (b1 * 25);
/*  521 */     double d2 = 0.01D;
/*  522 */     double d3 = 0.01D;
/*  523 */     double d4 = 2.0D;
/*  524 */     double d5 = 0.01D;
/*  525 */     double d6 = d4;
/*  526 */     double d7 = Math.pow(d5 / d4, 0.025D);
/*      */     
/*  528 */     double[] arrayOfDouble2 = new double[40]; byte b2;
/*  529 */     for (b2 = 0; b2 < 40; b2++) {
/*  530 */       arrayOfDouble2[b2] = d6;
/*  531 */       d6 *= d7;
/*      */     } 
/*      */     
/*  534 */     arrayOfDouble2[0] = 2.0D;
/*  535 */     arrayOfDouble2[1] = 0.5D;
/*  536 */     arrayOfDouble2[2] = 0.45D;
/*  537 */     arrayOfDouble2[3] = 0.2D;
/*  538 */     arrayOfDouble2[4] = 1.0D;
/*  539 */     arrayOfDouble2[5] = 0.5D;
/*  540 */     arrayOfDouble2[6] = 2.0D;
/*  541 */     arrayOfDouble2[7] = 1.0D;
/*  542 */     arrayOfDouble2[8] = 0.5D;
/*  543 */     arrayOfDouble2[9] = 1.0D;
/*  544 */     arrayOfDouble2[9] = 0.5D;
/*  545 */     arrayOfDouble2[10] = 0.2D;
/*  546 */     arrayOfDouble2[11] = 1.0D;
/*  547 */     arrayOfDouble2[12] = 0.7D;
/*  548 */     arrayOfDouble2[13] = 0.5D;
/*  549 */     arrayOfDouble2[14] = 1.0D;
/*      */     
/*  551 */     for (b2 = 0; b2 < 40; b2++) {
/*  552 */       double d = d2 + (d3 - d2) * b2 / 40.0D;
/*  553 */       complexGaussianDist(arrayOfDouble1, d1 * (b2 + 1), d, arrayOfDouble2[b2]);
/*      */     } 
/*      */     
/*  556 */     SF2Sample sF2Sample = newSimpleFFTSample(paramSF2Soundbank, "Guitar", arrayOfDouble1, d1);
/*  557 */     SF2Layer sF2Layer = newLayer(paramSF2Soundbank, "Guitar", sF2Sample);
/*  558 */     SF2Region sF2Region = sF2Layer.getRegions().get(0);
/*  559 */     sF2Region.putInteger(54, 1);
/*  560 */     sF2Region.putInteger(34, -12000);
/*  561 */     sF2Region.putInteger(38, 0);
/*  562 */     sF2Region.putInteger(36, 2400);
/*  563 */     sF2Region.putInteger(37, 1000);
/*      */     
/*  565 */     sF2Region.putInteger(26, -100);
/*  566 */     sF2Region.putInteger(30, 12000);
/*  567 */     sF2Region.putInteger(11, -6000);
/*  568 */     sF2Region.putInteger(8, 16000);
/*  569 */     sF2Region.putInteger(48, -20);
/*  570 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */   
/*      */   public static SF2Layer new_guitar_dist(SF2Soundbank paramSF2Soundbank) {
/*  575 */     byte b1 = 8;
/*  576 */     int i = 4096 * b1;
/*  577 */     double[] arrayOfDouble1 = new double[i * 2];
/*  578 */     double d1 = (b1 * 25);
/*  579 */     double d2 = 0.01D;
/*  580 */     double d3 = 0.01D;
/*  581 */     double d4 = 2.0D;
/*  582 */     double d5 = 0.01D;
/*  583 */     double d6 = d4;
/*  584 */     double d7 = Math.pow(d5 / d4, 0.025D);
/*      */     
/*  586 */     double[] arrayOfDouble2 = new double[40]; byte b2;
/*  587 */     for (b2 = 0; b2 < 40; b2++) {
/*  588 */       arrayOfDouble2[b2] = d6;
/*  589 */       d6 *= d7;
/*      */     } 
/*      */     
/*  592 */     arrayOfDouble2[0] = 5.0D;
/*  593 */     arrayOfDouble2[1] = 2.0D;
/*  594 */     arrayOfDouble2[2] = 0.45D;
/*  595 */     arrayOfDouble2[3] = 0.2D;
/*  596 */     arrayOfDouble2[4] = 1.0D;
/*  597 */     arrayOfDouble2[5] = 0.5D;
/*  598 */     arrayOfDouble2[6] = 2.0D;
/*  599 */     arrayOfDouble2[7] = 1.0D;
/*  600 */     arrayOfDouble2[8] = 0.5D;
/*  601 */     arrayOfDouble2[9] = 1.0D;
/*  602 */     arrayOfDouble2[9] = 0.5D;
/*  603 */     arrayOfDouble2[10] = 0.2D;
/*  604 */     arrayOfDouble2[11] = 1.0D;
/*  605 */     arrayOfDouble2[12] = 0.7D;
/*  606 */     arrayOfDouble2[13] = 0.5D;
/*  607 */     arrayOfDouble2[14] = 1.0D;
/*      */     
/*  609 */     for (b2 = 0; b2 < 40; b2++) {
/*  610 */       double d = d2 + (d3 - d2) * b2 / 40.0D;
/*  611 */       complexGaussianDist(arrayOfDouble1, d1 * (b2 + 1), d, arrayOfDouble2[b2]);
/*      */     } 
/*      */ 
/*      */     
/*  615 */     SF2Sample sF2Sample = newSimpleFFTSample_dist(paramSF2Soundbank, "Distorted Guitar", arrayOfDouble1, d1, 10000.0D);
/*      */ 
/*      */ 
/*      */     
/*  619 */     SF2Layer sF2Layer = newLayer(paramSF2Soundbank, "Distorted Guitar", sF2Sample);
/*  620 */     SF2Region sF2Region = sF2Layer.getRegions().get(0);
/*  621 */     sF2Region.putInteger(54, 1);
/*  622 */     sF2Region.putInteger(34, -12000);
/*  623 */     sF2Region.putInteger(38, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  630 */     sF2Region.putInteger(8, 8000);
/*      */     
/*  632 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SF2Layer new_guitar_pick(SF2Soundbank paramSF2Soundbank) {
/*  641 */     byte b1 = 2;
/*  642 */     int i = 4096 * b1;
/*  643 */     double[] arrayOfDouble2 = new double[2 * i];
/*  644 */     Random random = new Random(3049912L); int j;
/*  645 */     for (j = 0; j < arrayOfDouble2.length; j += 2)
/*  646 */       arrayOfDouble2[j] = 2.0D * (random.nextDouble() - 0.5D); 
/*  647 */     fft(arrayOfDouble2);
/*      */     
/*  649 */     for (j = i / 2; j < arrayOfDouble2.length; j++)
/*  650 */       arrayOfDouble2[j] = 0.0D; 
/*  651 */     for (j = 0; j < 2048 * b1; j++) {
/*  652 */       arrayOfDouble2[j] = arrayOfDouble2[j] * (Math.exp(-Math.abs((j - 23) / b1) * 1.2D) + 
/*  653 */         Math.exp(-Math.abs((j - 40) / b1) * 0.9D));
/*      */     }
/*  655 */     randomPhase(arrayOfDouble2, new Random(3049912L));
/*  656 */     ifft(arrayOfDouble2);
/*  657 */     normalize(arrayOfDouble2, 0.8D);
/*  658 */     arrayOfDouble2 = realPart(arrayOfDouble2);
/*  659 */     double d = 1.0D;
/*  660 */     for (byte b2 = 0; b2 < arrayOfDouble2.length; b2++) {
/*  661 */       arrayOfDouble2[b2] = arrayOfDouble2[b2] * d;
/*  662 */       d *= 0.9994D;
/*      */     } 
/*  664 */     double[] arrayOfDouble1 = arrayOfDouble2;
/*      */     
/*  666 */     fadeUp(arrayOfDouble2, 80);
/*      */ 
/*      */     
/*  669 */     SF2Sample sF2Sample = newSimpleDrumSample(paramSF2Soundbank, "Guitar Noise", arrayOfDouble1);
/*      */     
/*  671 */     SF2Layer sF2Layer = new SF2Layer(paramSF2Soundbank);
/*  672 */     sF2Layer.setName("Guitar Noise");
/*      */     
/*  674 */     SF2GlobalRegion sF2GlobalRegion = new SF2GlobalRegion();
/*  675 */     sF2Layer.setGlobalZone(sF2GlobalRegion);
/*  676 */     paramSF2Soundbank.addResource(sF2Layer);
/*      */     
/*  678 */     SF2LayerRegion sF2LayerRegion = new SF2LayerRegion();
/*  679 */     sF2LayerRegion.putInteger(38, 12000);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  690 */     sF2LayerRegion.setSample(sF2Sample);
/*  691 */     sF2Layer.getRegions().add(sF2LayerRegion);
/*      */     
/*  693 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */   
/*      */   public static SF2Layer new_gpiano(SF2Soundbank paramSF2Soundbank) {
/*  698 */     byte b1 = 8;
/*  699 */     int i = 4096 * b1;
/*  700 */     double[] arrayOfDouble1 = new double[i * 2];
/*  701 */     double d1 = (b1 * 25);
/*  702 */     double d2 = 0.2D;
/*  703 */     double d3 = 0.001D;
/*  704 */     double d4 = d2;
/*  705 */     double d5 = Math.pow(d3 / d2, 0.06666666666666667D);
/*      */     
/*  707 */     double[] arrayOfDouble2 = new double[30]; byte b2;
/*  708 */     for (b2 = 0; b2 < 30; b2++) {
/*  709 */       arrayOfDouble2[b2] = d4;
/*  710 */       d4 *= d5;
/*      */     } 
/*      */     
/*  713 */     arrayOfDouble2[0] = arrayOfDouble2[0] * 2.0D;
/*      */     
/*  715 */     arrayOfDouble2[4] = arrayOfDouble2[4] * 2.0D;
/*      */ 
/*      */     
/*  718 */     arrayOfDouble2[12] = arrayOfDouble2[12] * 0.9D;
/*  719 */     arrayOfDouble2[13] = arrayOfDouble2[13] * 0.7D;
/*  720 */     for (b2 = 14; b2 < 30; b2++) {
/*  721 */       arrayOfDouble2[b2] = arrayOfDouble2[b2] * 0.5D;
/*      */     }
/*      */ 
/*      */     
/*  725 */     for (b2 = 0; b2 < 30; b2++) {
/*      */       
/*  727 */       double d6 = 0.2D;
/*  728 */       double d7 = arrayOfDouble2[b2];
/*  729 */       if (b2 > 10) {
/*  730 */         d6 = 5.0D;
/*  731 */         d7 *= 10.0D;
/*      */       } 
/*  733 */       int j = 0;
/*  734 */       if (b2 > 5) {
/*  735 */         j = (b2 - 5) * 7;
/*      */       }
/*  737 */       complexGaussianDist(arrayOfDouble1, d1 * (b2 + 1) + j, d6, d7);
/*      */     } 
/*      */     
/*  740 */     SF2Sample sF2Sample = newSimpleFFTSample(paramSF2Soundbank, "Grand Piano", arrayOfDouble1, d1, 200);
/*  741 */     SF2Layer sF2Layer = newLayer(paramSF2Soundbank, "Grand Piano", sF2Sample);
/*  742 */     SF2Region sF2Region = sF2Layer.getRegions().get(0);
/*  743 */     sF2Region.putInteger(54, 1);
/*  744 */     sF2Region.putInteger(34, -7000);
/*  745 */     sF2Region.putInteger(38, 0);
/*  746 */     sF2Region.putInteger(36, 4000);
/*  747 */     sF2Region.putInteger(37, 1000);
/*  748 */     sF2Region.putInteger(26, -6000);
/*  749 */     sF2Region.putInteger(30, 12000);
/*  750 */     sF2Region.putInteger(11, -5500);
/*  751 */     sF2Region.putInteger(8, 18000);
/*  752 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */   
/*      */   public static SF2Layer new_gpiano2(SF2Soundbank paramSF2Soundbank) {
/*  757 */     byte b1 = 8;
/*  758 */     int i = 4096 * b1;
/*  759 */     double[] arrayOfDouble1 = new double[i * 2];
/*  760 */     double d1 = (b1 * 25);
/*  761 */     double d2 = 0.2D;
/*  762 */     double d3 = 0.001D;
/*  763 */     double d4 = d2;
/*  764 */     double d5 = Math.pow(d3 / d2, 0.05D);
/*      */     
/*  766 */     double[] arrayOfDouble2 = new double[30]; byte b2;
/*  767 */     for (b2 = 0; b2 < 30; b2++) {
/*  768 */       arrayOfDouble2[b2] = d4;
/*  769 */       d4 *= d5;
/*      */     } 
/*      */     
/*  772 */     arrayOfDouble2[0] = arrayOfDouble2[0] * 1.0D;
/*      */     
/*  774 */     arrayOfDouble2[4] = arrayOfDouble2[4] * 2.0D;
/*      */ 
/*      */     
/*  777 */     arrayOfDouble2[12] = arrayOfDouble2[12] * 0.9D;
/*  778 */     arrayOfDouble2[13] = arrayOfDouble2[13] * 0.7D;
/*  779 */     for (b2 = 14; b2 < 30; b2++) {
/*  780 */       arrayOfDouble2[b2] = arrayOfDouble2[b2] * 0.5D;
/*      */     }
/*      */ 
/*      */     
/*  784 */     for (b2 = 0; b2 < 30; b2++) {
/*      */       
/*  786 */       double d6 = 0.2D;
/*  787 */       double d7 = arrayOfDouble2[b2];
/*  788 */       if (b2 > 10) {
/*  789 */         d6 = 5.0D;
/*  790 */         d7 *= 10.0D;
/*      */       } 
/*  792 */       int j = 0;
/*  793 */       if (b2 > 5) {
/*  794 */         j = (b2 - 5) * 7;
/*      */       }
/*  796 */       complexGaussianDist(arrayOfDouble1, d1 * (b2 + 1) + j, d6, d7);
/*      */     } 
/*      */     
/*  799 */     SF2Sample sF2Sample = newSimpleFFTSample(paramSF2Soundbank, "Grand Piano", arrayOfDouble1, d1, 200);
/*  800 */     SF2Layer sF2Layer = newLayer(paramSF2Soundbank, "Grand Piano", sF2Sample);
/*  801 */     SF2Region sF2Region = sF2Layer.getRegions().get(0);
/*  802 */     sF2Region.putInteger(54, 1);
/*  803 */     sF2Region.putInteger(34, -7000);
/*  804 */     sF2Region.putInteger(38, 0);
/*  805 */     sF2Region.putInteger(36, 4000);
/*  806 */     sF2Region.putInteger(37, 1000);
/*  807 */     sF2Region.putInteger(26, -6000);
/*  808 */     sF2Region.putInteger(30, 12000);
/*  809 */     sF2Region.putInteger(11, -5500);
/*  810 */     sF2Region.putInteger(8, 18000);
/*  811 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SF2Layer new_piano_hammer(SF2Soundbank paramSF2Soundbank) {
/*  820 */     byte b1 = 2;
/*  821 */     int i = 4096 * b1;
/*  822 */     double[] arrayOfDouble2 = new double[2 * i];
/*  823 */     Random random = new Random(3049912L); int j;
/*  824 */     for (j = 0; j < arrayOfDouble2.length; j += 2)
/*  825 */       arrayOfDouble2[j] = 2.0D * (random.nextDouble() - 0.5D); 
/*  826 */     fft(arrayOfDouble2);
/*      */     
/*  828 */     for (j = i / 2; j < arrayOfDouble2.length; j++)
/*  829 */       arrayOfDouble2[j] = 0.0D; 
/*  830 */     for (j = 0; j < 2048 * b1; j++)
/*  831 */       arrayOfDouble2[j] = arrayOfDouble2[j] * Math.exp(-Math.abs((j - 37) / b1) * 0.05D); 
/*  832 */     randomPhase(arrayOfDouble2, new Random(3049912L));
/*  833 */     ifft(arrayOfDouble2);
/*  834 */     normalize(arrayOfDouble2, 0.6D);
/*  835 */     arrayOfDouble2 = realPart(arrayOfDouble2);
/*  836 */     double d = 1.0D;
/*  837 */     for (byte b2 = 0; b2 < arrayOfDouble2.length; b2++) {
/*  838 */       arrayOfDouble2[b2] = arrayOfDouble2[b2] * d;
/*  839 */       d *= 0.9997D;
/*      */     } 
/*  841 */     double[] arrayOfDouble1 = arrayOfDouble2;
/*      */     
/*  843 */     fadeUp(arrayOfDouble2, 80);
/*      */ 
/*      */     
/*  846 */     SF2Sample sF2Sample = newSimpleDrumSample(paramSF2Soundbank, "Piano Hammer", arrayOfDouble1);
/*      */     
/*  848 */     SF2Layer sF2Layer = new SF2Layer(paramSF2Soundbank);
/*  849 */     sF2Layer.setName("Piano Hammer");
/*      */     
/*  851 */     SF2GlobalRegion sF2GlobalRegion = new SF2GlobalRegion();
/*  852 */     sF2Layer.setGlobalZone(sF2GlobalRegion);
/*  853 */     paramSF2Soundbank.addResource(sF2Layer);
/*      */     
/*  855 */     SF2LayerRegion sF2LayerRegion = new SF2LayerRegion();
/*  856 */     sF2LayerRegion.putInteger(38, 12000);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  866 */     sF2LayerRegion.setSample(sF2Sample);
/*  867 */     sF2Layer.getRegions().add(sF2LayerRegion);
/*      */     
/*  869 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */   
/*      */   public static SF2Layer new_piano1(SF2Soundbank paramSF2Soundbank) {
/*  874 */     byte b1 = 8;
/*  875 */     int i = 4096 * b1;
/*  876 */     double[] arrayOfDouble1 = new double[i * 2];
/*  877 */     double d1 = (b1 * 25);
/*  878 */     double d2 = 0.2D;
/*  879 */     double d3 = 1.0E-4D;
/*  880 */     double d4 = d2;
/*  881 */     double d5 = Math.pow(d3 / d2, 0.025D);
/*      */     
/*  883 */     double[] arrayOfDouble2 = new double[30]; byte b2;
/*  884 */     for (b2 = 0; b2 < 30; b2++) {
/*  885 */       arrayOfDouble2[b2] = d4;
/*  886 */       d4 *= d5;
/*      */     } 
/*      */     
/*  889 */     arrayOfDouble2[0] = arrayOfDouble2[0] * 5.0D;
/*  890 */     arrayOfDouble2[2] = arrayOfDouble2[2] * 0.1D;
/*  891 */     arrayOfDouble2[7] = arrayOfDouble2[7] * 5.0D;
/*      */ 
/*      */     
/*  894 */     for (b2 = 0; b2 < 30; b2++) {
/*      */       
/*  896 */       double d6 = 0.2D;
/*  897 */       double d7 = arrayOfDouble2[b2];
/*  898 */       if (b2 > 12) {
/*  899 */         d6 = 5.0D;
/*  900 */         d7 *= 10.0D;
/*      */       } 
/*  902 */       int j = 0;
/*  903 */       if (b2 > 5) {
/*  904 */         j = (b2 - 5) * 7;
/*      */       }
/*  906 */       complexGaussianDist(arrayOfDouble1, d1 * (b2 + 1) + j, d6, d7);
/*      */     } 
/*      */     
/*  909 */     complexGaussianDist(arrayOfDouble1, d1 * 15.5D, 1.0D, 0.1D);
/*  910 */     complexGaussianDist(arrayOfDouble1, d1 * 17.5D, 1.0D, 0.01D);
/*      */     
/*  912 */     SF2Sample sF2Sample = newSimpleFFTSample(paramSF2Soundbank, "EPiano", arrayOfDouble1, d1, 200);
/*  913 */     SF2Layer sF2Layer = newLayer(paramSF2Soundbank, "EPiano", sF2Sample);
/*  914 */     SF2Region sF2Region = sF2Layer.getRegions().get(0);
/*  915 */     sF2Region.putInteger(54, 1);
/*  916 */     sF2Region.putInteger(34, -12000);
/*  917 */     sF2Region.putInteger(38, 0);
/*  918 */     sF2Region.putInteger(36, 4000);
/*  919 */     sF2Region.putInteger(37, 1000);
/*  920 */     sF2Region.putInteger(26, -1200);
/*  921 */     sF2Region.putInteger(30, 12000);
/*  922 */     sF2Region.putInteger(11, -5500);
/*  923 */     sF2Region.putInteger(8, 16000);
/*  924 */     return sF2Layer;
/*      */   }
/*      */   
/*      */   public static SF2Layer new_epiano1(SF2Soundbank paramSF2Soundbank) {
/*  928 */     Random random = new Random(302030201L);
/*  929 */     byte b1 = 8;
/*  930 */     int i = 4096 * b1;
/*  931 */     double[] arrayOfDouble = new double[i * 2];
/*  932 */     double d1 = (b1 * 25);
/*  933 */     double d2 = 0.05D;
/*  934 */     double d3 = 0.05D;
/*  935 */     double d4 = 0.2D;
/*  936 */     double d5 = 1.0E-4D;
/*  937 */     double d6 = d4;
/*  938 */     double d7 = Math.pow(d5 / d4, 0.025D);
/*  939 */     for (byte b2 = 0; b2 < 40; b2++) {
/*  940 */       double d8 = 1.0D + (random.nextDouble() * 2.0D - 1.0D) * 1.0E-4D;
/*  941 */       double d9 = d2 + (d3 - d2) * b2 / 40.0D;
/*  942 */       complexGaussianDist(arrayOfDouble, d1 * (b2 + 1) * d8, d9, d6);
/*  943 */       d6 *= d7;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  948 */     SF2Sample sF2Sample = newSimpleFFTSample(paramSF2Soundbank, "EPiano", arrayOfDouble, d1);
/*  949 */     SF2Layer sF2Layer = newLayer(paramSF2Soundbank, "EPiano", sF2Sample);
/*  950 */     SF2Region sF2Region = sF2Layer.getRegions().get(0);
/*  951 */     sF2Region.putInteger(54, 1);
/*  952 */     sF2Region.putInteger(34, -12000);
/*  953 */     sF2Region.putInteger(38, 0);
/*  954 */     sF2Region.putInteger(36, 4000);
/*  955 */     sF2Region.putInteger(37, 1000);
/*  956 */     sF2Region.putInteger(26, 1200);
/*  957 */     sF2Region.putInteger(30, 12000);
/*  958 */     sF2Region.putInteger(11, -9000);
/*  959 */     sF2Region.putInteger(8, 16000);
/*  960 */     return sF2Layer;
/*      */   }
/*      */   
/*      */   public static SF2Layer new_epiano2(SF2Soundbank paramSF2Soundbank) {
/*  964 */     Random random = new Random(302030201L);
/*  965 */     byte b1 = 8;
/*  966 */     int i = 4096 * b1;
/*  967 */     double[] arrayOfDouble = new double[i * 2];
/*  968 */     double d1 = (b1 * 25);
/*  969 */     double d2 = 0.01D;
/*  970 */     double d3 = 0.05D;
/*  971 */     double d4 = 0.2D;
/*  972 */     double d5 = 1.0E-5D;
/*  973 */     double d6 = d4;
/*  974 */     double d7 = Math.pow(d5 / d4, 0.025D);
/*  975 */     for (byte b2 = 0; b2 < 40; b2++) {
/*  976 */       double d8 = 1.0D + (random.nextDouble() * 2.0D - 1.0D) * 1.0E-4D;
/*  977 */       double d9 = d2 + (d3 - d2) * b2 / 40.0D;
/*  978 */       complexGaussianDist(arrayOfDouble, d1 * (b2 + 1) * d8, d9, d6);
/*  979 */       d6 *= d7;
/*      */     } 
/*      */     
/*  982 */     SF2Sample sF2Sample = newSimpleFFTSample(paramSF2Soundbank, "EPiano", arrayOfDouble, d1);
/*  983 */     SF2Layer sF2Layer = newLayer(paramSF2Soundbank, "EPiano", sF2Sample);
/*  984 */     SF2Region sF2Region = sF2Layer.getRegions().get(0);
/*  985 */     sF2Region.putInteger(54, 1);
/*  986 */     sF2Region.putInteger(34, -12000);
/*  987 */     sF2Region.putInteger(38, 0);
/*  988 */     sF2Region.putInteger(36, 8000);
/*  989 */     sF2Region.putInteger(37, 1000);
/*  990 */     sF2Region.putInteger(26, 2400);
/*  991 */     sF2Region.putInteger(30, 12000);
/*  992 */     sF2Region.putInteger(11, -9000);
/*  993 */     sF2Region.putInteger(8, 16000);
/*  994 */     sF2Region.putInteger(48, -100);
/*  995 */     return sF2Layer;
/*      */   }
/*      */   
/*      */   public static SF2Layer new_bass1(SF2Soundbank paramSF2Soundbank) {
/*  999 */     byte b1 = 8;
/* 1000 */     int i = 4096 * b1;
/* 1001 */     double[] arrayOfDouble1 = new double[i * 2];
/* 1002 */     double d1 = (b1 * 25);
/* 1003 */     double d2 = 0.05D;
/* 1004 */     double d3 = 0.05D;
/* 1005 */     double d4 = 0.2D;
/* 1006 */     double d5 = 0.02D;
/* 1007 */     double d6 = d4;
/* 1008 */     double d7 = Math.pow(d5 / d4, 0.04D);
/*      */     
/* 1010 */     double[] arrayOfDouble2 = new double[25]; byte b2;
/* 1011 */     for (b2 = 0; b2 < 25; b2++) {
/* 1012 */       arrayOfDouble2[b2] = d6;
/* 1013 */       d6 *= d7;
/*      */     } 
/*      */     
/* 1016 */     arrayOfDouble2[0] = arrayOfDouble2[0] * 8.0D;
/* 1017 */     arrayOfDouble2[1] = arrayOfDouble2[1] * 4.0D;
/* 1018 */     arrayOfDouble2[3] = arrayOfDouble2[3] * 8.0D;
/* 1019 */     arrayOfDouble2[5] = arrayOfDouble2[5] * 8.0D;
/*      */     
/* 1021 */     for (b2 = 0; b2 < 25; b2++) {
/* 1022 */       double d = d2 + (d3 - d2) * b2 / 40.0D;
/* 1023 */       complexGaussianDist(arrayOfDouble1, d1 * (b2 + 1), d, arrayOfDouble2[b2]);
/*      */     } 
/*      */ 
/*      */     
/* 1027 */     SF2Sample sF2Sample = newSimpleFFTSample(paramSF2Soundbank, "Bass", arrayOfDouble1, d1);
/* 1028 */     SF2Layer sF2Layer = newLayer(paramSF2Soundbank, "Bass", sF2Sample);
/* 1029 */     SF2Region sF2Region = sF2Layer.getRegions().get(0);
/* 1030 */     sF2Region.putInteger(54, 1);
/* 1031 */     sF2Region.putInteger(34, -12000);
/* 1032 */     sF2Region.putInteger(38, 0);
/* 1033 */     sF2Region.putInteger(36, 4000);
/* 1034 */     sF2Region.putInteger(37, 1000);
/* 1035 */     sF2Region.putInteger(26, -3000);
/* 1036 */     sF2Region.putInteger(30, 12000);
/* 1037 */     sF2Region.putInteger(11, -5000);
/* 1038 */     sF2Region.putInteger(8, 11000);
/* 1039 */     sF2Region.putInteger(48, -100);
/* 1040 */     return sF2Layer;
/*      */   }
/*      */   
/*      */   public static SF2Layer new_synthbass(SF2Soundbank paramSF2Soundbank) {
/* 1044 */     byte b1 = 8;
/* 1045 */     int i = 4096 * b1;
/* 1046 */     double[] arrayOfDouble1 = new double[i * 2];
/* 1047 */     double d1 = (b1 * 25);
/* 1048 */     double d2 = 0.05D;
/* 1049 */     double d3 = 0.05D;
/* 1050 */     double d4 = 0.2D;
/* 1051 */     double d5 = 0.02D;
/* 1052 */     double d6 = d4;
/* 1053 */     double d7 = Math.pow(d5 / d4, 0.04D);
/*      */     
/* 1055 */     double[] arrayOfDouble2 = new double[25]; byte b2;
/* 1056 */     for (b2 = 0; b2 < 25; b2++) {
/* 1057 */       arrayOfDouble2[b2] = d6;
/* 1058 */       d6 *= d7;
/*      */     } 
/*      */     
/* 1061 */     arrayOfDouble2[0] = arrayOfDouble2[0] * 16.0D;
/* 1062 */     arrayOfDouble2[1] = arrayOfDouble2[1] * 4.0D;
/* 1063 */     arrayOfDouble2[3] = arrayOfDouble2[3] * 16.0D;
/* 1064 */     arrayOfDouble2[5] = arrayOfDouble2[5] * 8.0D;
/*      */     
/* 1066 */     for (b2 = 0; b2 < 25; b2++) {
/* 1067 */       double d = d2 + (d3 - d2) * b2 / 40.0D;
/* 1068 */       complexGaussianDist(arrayOfDouble1, d1 * (b2 + 1), d, arrayOfDouble2[b2]);
/*      */     } 
/*      */ 
/*      */     
/* 1072 */     SF2Sample sF2Sample = newSimpleFFTSample(paramSF2Soundbank, "Bass", arrayOfDouble1, d1);
/* 1073 */     SF2Layer sF2Layer = newLayer(paramSF2Soundbank, "Bass", sF2Sample);
/* 1074 */     SF2Region sF2Region = sF2Layer.getRegions().get(0);
/* 1075 */     sF2Region.putInteger(54, 1);
/* 1076 */     sF2Region.putInteger(34, -12000);
/* 1077 */     sF2Region.putInteger(38, 0);
/* 1078 */     sF2Region.putInteger(36, 4000);
/* 1079 */     sF2Region.putInteger(37, 1000);
/* 1080 */     sF2Region.putInteger(26, -3000);
/* 1081 */     sF2Region.putInteger(30, 12000);
/* 1082 */     sF2Region.putInteger(11, -3000);
/* 1083 */     sF2Region.putInteger(9, 100);
/* 1084 */     sF2Region.putInteger(8, 8000);
/* 1085 */     sF2Region.putInteger(48, -100);
/* 1086 */     return sF2Layer;
/*      */   }
/*      */   
/*      */   public static SF2Layer new_bass2(SF2Soundbank paramSF2Soundbank) {
/* 1090 */     byte b1 = 8;
/* 1091 */     int i = 4096 * b1;
/* 1092 */     double[] arrayOfDouble1 = new double[i * 2];
/* 1093 */     double d1 = (b1 * 25);
/* 1094 */     double d2 = 0.05D;
/* 1095 */     double d3 = 0.05D;
/* 1096 */     double d4 = 0.2D;
/* 1097 */     double d5 = 0.002D;
/* 1098 */     double d6 = d4;
/* 1099 */     double d7 = Math.pow(d5 / d4, 0.04D);
/*      */     
/* 1101 */     double[] arrayOfDouble2 = new double[25]; byte b2;
/* 1102 */     for (b2 = 0; b2 < 25; b2++) {
/* 1103 */       arrayOfDouble2[b2] = d6;
/* 1104 */       d6 *= d7;
/*      */     } 
/*      */     
/* 1107 */     arrayOfDouble2[0] = arrayOfDouble2[0] * 8.0D;
/* 1108 */     arrayOfDouble2[1] = arrayOfDouble2[1] * 4.0D;
/* 1109 */     arrayOfDouble2[3] = arrayOfDouble2[3] * 8.0D;
/* 1110 */     arrayOfDouble2[5] = arrayOfDouble2[5] * 8.0D;
/*      */     
/* 1112 */     for (b2 = 0; b2 < 25; b2++) {
/* 1113 */       double d = d2 + (d3 - d2) * b2 / 40.0D;
/* 1114 */       complexGaussianDist(arrayOfDouble1, d1 * (b2 + 1), d, arrayOfDouble2[b2]);
/*      */     } 
/*      */ 
/*      */     
/* 1118 */     SF2Sample sF2Sample = newSimpleFFTSample(paramSF2Soundbank, "Bass2", arrayOfDouble1, d1);
/* 1119 */     SF2Layer sF2Layer = newLayer(paramSF2Soundbank, "Bass2", sF2Sample);
/* 1120 */     SF2Region sF2Region = sF2Layer.getRegions().get(0);
/* 1121 */     sF2Region.putInteger(54, 1);
/* 1122 */     sF2Region.putInteger(34, -8000);
/* 1123 */     sF2Region.putInteger(38, 0);
/* 1124 */     sF2Region.putInteger(36, 4000);
/* 1125 */     sF2Region.putInteger(37, 1000);
/* 1126 */     sF2Region.putInteger(26, -6000);
/* 1127 */     sF2Region.putInteger(30, 12000);
/* 1128 */     sF2Region.putInteger(8, 5000);
/* 1129 */     sF2Region.putInteger(48, -100);
/* 1130 */     return sF2Layer;
/*      */   }
/*      */   
/*      */   public static SF2Layer new_solostring(SF2Soundbank paramSF2Soundbank) {
/* 1134 */     byte b1 = 8;
/* 1135 */     int i = 4096 * b1;
/* 1136 */     double[] arrayOfDouble1 = new double[i * 2];
/* 1137 */     double d1 = (b1 * 25);
/* 1138 */     double d2 = 2.0D;
/* 1139 */     double d3 = 2.0D;
/* 1140 */     double d4 = 0.2D;
/* 1141 */     double d5 = 0.01D;
/*      */     
/* 1143 */     double[] arrayOfDouble2 = new double[18];
/* 1144 */     double d6 = d4;
/* 1145 */     double d7 = Math.pow(d5 / d4, 0.025D); byte b2;
/* 1146 */     for (b2 = 0; b2 < arrayOfDouble2.length; b2++) {
/* 1147 */       d6 *= d7;
/* 1148 */       arrayOfDouble2[b2] = d6;
/*      */     } 
/*      */     
/* 1151 */     arrayOfDouble2[0] = arrayOfDouble2[0] * 5.0D;
/* 1152 */     arrayOfDouble2[1] = arrayOfDouble2[1] * 5.0D;
/* 1153 */     arrayOfDouble2[2] = arrayOfDouble2[2] * 5.0D;
/* 1154 */     arrayOfDouble2[3] = arrayOfDouble2[3] * 4.0D;
/* 1155 */     arrayOfDouble2[4] = arrayOfDouble2[4] * 4.0D;
/* 1156 */     arrayOfDouble2[5] = arrayOfDouble2[5] * 3.0D;
/* 1157 */     arrayOfDouble2[6] = arrayOfDouble2[6] * 3.0D;
/* 1158 */     arrayOfDouble2[7] = arrayOfDouble2[7] * 2.0D;
/*      */     
/* 1160 */     for (b2 = 0; b2 < arrayOfDouble2.length; b2++) {
/* 1161 */       double d = d2 + (d3 - d2) * b2 / 40.0D;
/* 1162 */       complexGaussianDist(arrayOfDouble1, d1 * (b2 + 1), d, d6);
/*      */     } 
/* 1164 */     SF2Sample sF2Sample = newSimpleFFTSample(paramSF2Soundbank, "Strings", arrayOfDouble1, d1);
/* 1165 */     SF2Layer sF2Layer = newLayer(paramSF2Soundbank, "Strings", sF2Sample);
/* 1166 */     SF2Region sF2Region = sF2Layer.getRegions().get(0);
/* 1167 */     sF2Region.putInteger(54, 1);
/* 1168 */     sF2Region.putInteger(34, -5000);
/* 1169 */     sF2Region.putInteger(38, 1000);
/* 1170 */     sF2Region.putInteger(36, 4000);
/* 1171 */     sF2Region.putInteger(37, -100);
/* 1172 */     sF2Region.putInteger(8, 9500);
/* 1173 */     sF2Region.putInteger(24, -1000);
/* 1174 */     sF2Region.putInteger(6, 15);
/* 1175 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */   
/*      */   public static SF2Layer new_orchhit(SF2Soundbank paramSF2Soundbank) {
/* 1180 */     byte b1 = 8;
/* 1181 */     int i = 4096 * b1;
/* 1182 */     double[] arrayOfDouble = new double[i * 2];
/* 1183 */     double d1 = (b1 * 25);
/* 1184 */     double d2 = 2.0D;
/* 1185 */     double d3 = 80.0D;
/* 1186 */     double d4 = 0.2D;
/* 1187 */     double d5 = 0.001D;
/* 1188 */     double d6 = d4;
/* 1189 */     double d7 = Math.pow(d5 / d4, 0.025D);
/* 1190 */     for (byte b2 = 0; b2 < 40; b2++) {
/* 1191 */       double d = d2 + (d3 - d2) * b2 / 40.0D;
/* 1192 */       complexGaussianDist(arrayOfDouble, d1 * (b2 + 1), d, d6);
/* 1193 */       d6 *= d7;
/*      */     } 
/* 1195 */     complexGaussianDist(arrayOfDouble, d1 * 4.0D, 300.0D, 1.0D);
/*      */ 
/*      */     
/* 1198 */     SF2Sample sF2Sample = newSimpleFFTSample(paramSF2Soundbank, "Och Strings", arrayOfDouble, d1);
/* 1199 */     SF2Layer sF2Layer = newLayer(paramSF2Soundbank, "Och Strings", sF2Sample);
/* 1200 */     SF2Region sF2Region = sF2Layer.getRegions().get(0);
/* 1201 */     sF2Region.putInteger(54, 1);
/* 1202 */     sF2Region.putInteger(34, -5000);
/* 1203 */     sF2Region.putInteger(38, 200);
/* 1204 */     sF2Region.putInteger(36, 200);
/* 1205 */     sF2Region.putInteger(37, 1000);
/* 1206 */     sF2Region.putInteger(8, 9500);
/* 1207 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */   
/*      */   public static SF2Layer new_string2(SF2Soundbank paramSF2Soundbank) {
/* 1212 */     byte b1 = 8;
/* 1213 */     int i = 4096 * b1;
/* 1214 */     double[] arrayOfDouble = new double[i * 2];
/* 1215 */     double d1 = (b1 * 25);
/* 1216 */     double d2 = 2.0D;
/* 1217 */     double d3 = 80.0D;
/* 1218 */     double d4 = 0.2D;
/* 1219 */     double d5 = 0.001D;
/* 1220 */     double d6 = d4;
/* 1221 */     double d7 = Math.pow(d5 / d4, 0.025D);
/* 1222 */     for (byte b2 = 0; b2 < 40; b2++) {
/* 1223 */       double d = d2 + (d3 - d2) * b2 / 40.0D;
/* 1224 */       complexGaussianDist(arrayOfDouble, d1 * (b2 + 1), d, d6);
/* 1225 */       d6 *= d7;
/*      */     } 
/* 1227 */     SF2Sample sF2Sample = newSimpleFFTSample(paramSF2Soundbank, "Strings", arrayOfDouble, d1);
/* 1228 */     SF2Layer sF2Layer = newLayer(paramSF2Soundbank, "Strings", sF2Sample);
/* 1229 */     SF2Region sF2Region = sF2Layer.getRegions().get(0);
/* 1230 */     sF2Region.putInteger(54, 1);
/* 1231 */     sF2Region.putInteger(34, -5000);
/* 1232 */     sF2Region.putInteger(38, 1000);
/* 1233 */     sF2Region.putInteger(36, 4000);
/* 1234 */     sF2Region.putInteger(37, -100);
/* 1235 */     sF2Region.putInteger(8, 9500);
/* 1236 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */   
/*      */   public static SF2Layer new_choir(SF2Soundbank paramSF2Soundbank) {
/* 1241 */     byte b1 = 8;
/* 1242 */     int i = 4096 * b1;
/* 1243 */     double[] arrayOfDouble1 = new double[i * 2];
/* 1244 */     double d1 = (b1 * 25);
/* 1245 */     double d2 = 2.0D;
/* 1246 */     double d3 = 80.0D;
/* 1247 */     double d4 = 0.2D;
/* 1248 */     double d5 = 0.001D;
/* 1249 */     double d6 = d4;
/* 1250 */     double d7 = Math.pow(d5 / d4, 0.025D);
/* 1251 */     double[] arrayOfDouble2 = new double[40]; byte b2;
/* 1252 */     for (b2 = 0; b2 < arrayOfDouble2.length; b2++) {
/* 1253 */       d6 *= d7;
/* 1254 */       arrayOfDouble2[b2] = d6;
/*      */     } 
/*      */     
/* 1257 */     arrayOfDouble2[5] = arrayOfDouble2[5] * 0.1D;
/* 1258 */     arrayOfDouble2[6] = arrayOfDouble2[6] * 0.01D;
/* 1259 */     arrayOfDouble2[7] = arrayOfDouble2[7] * 0.1D;
/* 1260 */     arrayOfDouble2[8] = arrayOfDouble2[8] * 0.1D;
/*      */     
/* 1262 */     for (b2 = 0; b2 < arrayOfDouble2.length; b2++) {
/* 1263 */       double d = d2 + (d3 - d2) * b2 / 40.0D;
/* 1264 */       complexGaussianDist(arrayOfDouble1, d1 * (b2 + 1), d, arrayOfDouble2[b2]);
/*      */     } 
/* 1266 */     SF2Sample sF2Sample = newSimpleFFTSample(paramSF2Soundbank, "Strings", arrayOfDouble1, d1);
/* 1267 */     SF2Layer sF2Layer = newLayer(paramSF2Soundbank, "Strings", sF2Sample);
/* 1268 */     SF2Region sF2Region = sF2Layer.getRegions().get(0);
/* 1269 */     sF2Region.putInteger(54, 1);
/* 1270 */     sF2Region.putInteger(34, -5000);
/* 1271 */     sF2Region.putInteger(38, 1000);
/* 1272 */     sF2Region.putInteger(36, 4000);
/* 1273 */     sF2Region.putInteger(37, -100);
/* 1274 */     sF2Region.putInteger(8, 9500);
/* 1275 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */   
/*      */   public static SF2Layer new_organ(SF2Soundbank paramSF2Soundbank) {
/* 1280 */     Random random = new Random(102030201L);
/* 1281 */     byte b1 = 1;
/* 1282 */     int i = 4096 * b1;
/* 1283 */     double[] arrayOfDouble = new double[i * 2];
/* 1284 */     double d1 = (b1 * 15);
/* 1285 */     double d2 = 0.01D;
/* 1286 */     double d3 = 0.01D;
/* 1287 */     double d4 = 0.2D;
/* 1288 */     double d5 = 0.001D;
/* 1289 */     double d6 = d4;
/* 1290 */     double d7 = Math.pow(d5 / d4, 0.025D);
/*      */     
/* 1292 */     for (byte b2 = 0; b2 < 12; b2++) {
/* 1293 */       double d = d2 + (d3 - d2) * b2 / 40.0D;
/* 1294 */       complexGaussianDist(arrayOfDouble, d1 * (b2 + 1), d, d6 * (0.5D + 3.0D * random
/* 1295 */           .nextDouble()));
/* 1296 */       d6 *= d7;
/*      */     } 
/* 1298 */     SF2Sample sF2Sample = newSimpleFFTSample(paramSF2Soundbank, "Organ", arrayOfDouble, d1);
/* 1299 */     SF2Layer sF2Layer = newLayer(paramSF2Soundbank, "Organ", sF2Sample);
/* 1300 */     SF2Region sF2Region = sF2Layer.getRegions().get(0);
/* 1301 */     sF2Region.putInteger(54, 1);
/* 1302 */     sF2Region.putInteger(34, -6000);
/* 1303 */     sF2Region.putInteger(38, -1000);
/* 1304 */     sF2Region.putInteger(36, 4000);
/* 1305 */     sF2Region.putInteger(37, -100);
/* 1306 */     sF2Region.putInteger(8, 9500);
/* 1307 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */   
/*      */   public static SF2Layer new_ch_organ(SF2Soundbank paramSF2Soundbank) {
/* 1312 */     byte b1 = 1;
/* 1313 */     int i = 4096 * b1;
/* 1314 */     double[] arrayOfDouble1 = new double[i * 2];
/* 1315 */     double d1 = (b1 * 15);
/* 1316 */     double d2 = 0.01D;
/* 1317 */     double d3 = 0.01D;
/* 1318 */     double d4 = 0.2D;
/* 1319 */     double d5 = 0.001D;
/* 1320 */     double d6 = d4;
/* 1321 */     double d7 = Math.pow(d5 / d4, 0.016666666666666666D);
/*      */     
/* 1323 */     double[] arrayOfDouble2 = new double[60]; byte b2;
/* 1324 */     for (b2 = 0; b2 < arrayOfDouble2.length; b2++) {
/* 1325 */       d6 *= d7;
/* 1326 */       arrayOfDouble2[b2] = d6;
/*      */     } 
/*      */     
/* 1329 */     arrayOfDouble2[0] = arrayOfDouble2[0] * 5.0D;
/* 1330 */     arrayOfDouble2[1] = arrayOfDouble2[1] * 2.0D;
/* 1331 */     arrayOfDouble2[2] = 0.0D;
/* 1332 */     arrayOfDouble2[4] = 0.0D;
/* 1333 */     arrayOfDouble2[5] = 0.0D;
/* 1334 */     arrayOfDouble2[7] = arrayOfDouble2[7] * 7.0D;
/* 1335 */     arrayOfDouble2[9] = 0.0D;
/* 1336 */     arrayOfDouble2[10] = 0.0D;
/* 1337 */     arrayOfDouble2[12] = 0.0D;
/* 1338 */     arrayOfDouble2[15] = arrayOfDouble2[15] * 7.0D;
/* 1339 */     arrayOfDouble2[18] = 0.0D;
/* 1340 */     arrayOfDouble2[20] = 0.0D;
/* 1341 */     arrayOfDouble2[24] = 0.0D;
/* 1342 */     arrayOfDouble2[27] = arrayOfDouble2[27] * 5.0D;
/* 1343 */     arrayOfDouble2[29] = 0.0D;
/* 1344 */     arrayOfDouble2[30] = 0.0D;
/* 1345 */     arrayOfDouble2[33] = 0.0D;
/* 1346 */     arrayOfDouble2[36] = arrayOfDouble2[36] * 4.0D;
/* 1347 */     arrayOfDouble2[37] = 0.0D;
/* 1348 */     arrayOfDouble2[39] = 0.0D;
/* 1349 */     arrayOfDouble2[42] = 0.0D;
/* 1350 */     arrayOfDouble2[43] = 0.0D;
/* 1351 */     arrayOfDouble2[47] = 0.0D;
/* 1352 */     arrayOfDouble2[50] = arrayOfDouble2[50] * 4.0D;
/* 1353 */     arrayOfDouble2[52] = 0.0D;
/* 1354 */     arrayOfDouble2[55] = 0.0D;
/* 1355 */     arrayOfDouble2[57] = 0.0D;
/*      */ 
/*      */     
/* 1358 */     arrayOfDouble2[10] = arrayOfDouble2[10] * 0.1D;
/* 1359 */     arrayOfDouble2[11] = arrayOfDouble2[11] * 0.1D;
/* 1360 */     arrayOfDouble2[12] = arrayOfDouble2[12] * 0.1D;
/* 1361 */     arrayOfDouble2[13] = arrayOfDouble2[13] * 0.1D;
/*      */     
/* 1363 */     arrayOfDouble2[17] = arrayOfDouble2[17] * 0.1D;
/* 1364 */     arrayOfDouble2[18] = arrayOfDouble2[18] * 0.1D;
/* 1365 */     arrayOfDouble2[19] = arrayOfDouble2[19] * 0.1D;
/* 1366 */     arrayOfDouble2[20] = arrayOfDouble2[20] * 0.1D;
/*      */     
/* 1368 */     for (b2 = 0; b2 < 60; b2++) {
/* 1369 */       double d = d2 + (d3 - d2) * b2 / 40.0D;
/* 1370 */       complexGaussianDist(arrayOfDouble1, d1 * (b2 + 1), d, arrayOfDouble2[b2]);
/* 1371 */       d6 *= d7;
/*      */     } 
/* 1373 */     SF2Sample sF2Sample = newSimpleFFTSample(paramSF2Soundbank, "Organ", arrayOfDouble1, d1);
/* 1374 */     SF2Layer sF2Layer = newLayer(paramSF2Soundbank, "Organ", sF2Sample);
/* 1375 */     SF2Region sF2Region = sF2Layer.getRegions().get(0);
/* 1376 */     sF2Region.putInteger(54, 1);
/* 1377 */     sF2Region.putInteger(34, -10000);
/* 1378 */     sF2Region.putInteger(38, -1000);
/* 1379 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */   
/*      */   public static SF2Layer new_flute(SF2Soundbank paramSF2Soundbank) {
/* 1384 */     byte b = 8;
/* 1385 */     int i = 4096 * b;
/* 1386 */     double[] arrayOfDouble = new double[i * 2];
/* 1387 */     double d = (b * 15);
/*      */     
/* 1389 */     complexGaussianDist(arrayOfDouble, d * 1.0D, 0.001D, 0.5D);
/* 1390 */     complexGaussianDist(arrayOfDouble, d * 2.0D, 0.001D, 0.5D);
/* 1391 */     complexGaussianDist(arrayOfDouble, d * 3.0D, 0.001D, 0.5D);
/* 1392 */     complexGaussianDist(arrayOfDouble, d * 4.0D, 0.01D, 0.5D);
/*      */     
/* 1394 */     complexGaussianDist(arrayOfDouble, d * 4.0D, 100.0D, 120.0D);
/* 1395 */     complexGaussianDist(arrayOfDouble, d * 6.0D, 100.0D, 40.0D);
/* 1396 */     complexGaussianDist(arrayOfDouble, d * 8.0D, 100.0D, 80.0D);
/*      */     
/* 1398 */     complexGaussianDist(arrayOfDouble, d * 5.0D, 0.001D, 0.05D);
/* 1399 */     complexGaussianDist(arrayOfDouble, d * 6.0D, 0.001D, 0.06D);
/* 1400 */     complexGaussianDist(arrayOfDouble, d * 7.0D, 0.001D, 0.04D);
/* 1401 */     complexGaussianDist(arrayOfDouble, d * 8.0D, 0.005D, 0.06D);
/* 1402 */     complexGaussianDist(arrayOfDouble, d * 9.0D, 0.005D, 0.06D);
/* 1403 */     complexGaussianDist(arrayOfDouble, d * 10.0D, 0.01D, 0.1D);
/* 1404 */     complexGaussianDist(arrayOfDouble, d * 11.0D, 0.08D, 0.7D);
/* 1405 */     complexGaussianDist(arrayOfDouble, d * 12.0D, 0.08D, 0.6D);
/* 1406 */     complexGaussianDist(arrayOfDouble, d * 13.0D, 0.08D, 0.6D);
/* 1407 */     complexGaussianDist(arrayOfDouble, d * 14.0D, 0.08D, 0.6D);
/* 1408 */     complexGaussianDist(arrayOfDouble, d * 15.0D, 0.08D, 0.5D);
/* 1409 */     complexGaussianDist(arrayOfDouble, d * 16.0D, 0.08D, 0.5D);
/* 1410 */     complexGaussianDist(arrayOfDouble, d * 17.0D, 0.08D, 0.2D);
/*      */ 
/*      */     
/* 1413 */     complexGaussianDist(arrayOfDouble, d * 1.0D, 10.0D, 8.0D);
/* 1414 */     complexGaussianDist(arrayOfDouble, d * 2.0D, 10.0D, 8.0D);
/* 1415 */     complexGaussianDist(arrayOfDouble, d * 3.0D, 10.0D, 8.0D);
/* 1416 */     complexGaussianDist(arrayOfDouble, d * 4.0D, 10.0D, 8.0D);
/* 1417 */     complexGaussianDist(arrayOfDouble, d * 5.0D, 10.0D, 8.0D);
/* 1418 */     complexGaussianDist(arrayOfDouble, d * 6.0D, 20.0D, 9.0D);
/* 1419 */     complexGaussianDist(arrayOfDouble, d * 7.0D, 20.0D, 9.0D);
/* 1420 */     complexGaussianDist(arrayOfDouble, d * 8.0D, 20.0D, 9.0D);
/* 1421 */     complexGaussianDist(arrayOfDouble, d * 9.0D, 20.0D, 8.0D);
/* 1422 */     complexGaussianDist(arrayOfDouble, d * 10.0D, 30.0D, 8.0D);
/* 1423 */     complexGaussianDist(arrayOfDouble, d * 11.0D, 30.0D, 9.0D);
/* 1424 */     complexGaussianDist(arrayOfDouble, d * 12.0D, 30.0D, 9.0D);
/* 1425 */     complexGaussianDist(arrayOfDouble, d * 13.0D, 30.0D, 8.0D);
/* 1426 */     complexGaussianDist(arrayOfDouble, d * 14.0D, 30.0D, 8.0D);
/* 1427 */     complexGaussianDist(arrayOfDouble, d * 15.0D, 30.0D, 7.0D);
/* 1428 */     complexGaussianDist(arrayOfDouble, d * 16.0D, 30.0D, 7.0D);
/* 1429 */     complexGaussianDist(arrayOfDouble, d * 17.0D, 30.0D, 6.0D);
/*      */     
/* 1431 */     SF2Sample sF2Sample = newSimpleFFTSample(paramSF2Soundbank, "Flute", arrayOfDouble, d);
/* 1432 */     SF2Layer sF2Layer = newLayer(paramSF2Soundbank, "Flute", sF2Sample);
/* 1433 */     SF2Region sF2Region = sF2Layer.getRegions().get(0);
/* 1434 */     sF2Region.putInteger(54, 1);
/* 1435 */     sF2Region.putInteger(34, -6000);
/* 1436 */     sF2Region.putInteger(38, -1000);
/* 1437 */     sF2Region.putInteger(36, 4000);
/* 1438 */     sF2Region.putInteger(37, -100);
/* 1439 */     sF2Region.putInteger(8, 9500);
/* 1440 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */   
/*      */   public static SF2Layer new_horn(SF2Soundbank paramSF2Soundbank) {
/* 1445 */     byte b1 = 8;
/* 1446 */     int i = 4096 * b1;
/* 1447 */     double[] arrayOfDouble = new double[i * 2];
/* 1448 */     double d1 = (b1 * 15);
/*      */     
/* 1450 */     double d2 = 0.5D;
/* 1451 */     double d3 = 1.0E-11D;
/* 1452 */     double d4 = d2;
/* 1453 */     double d5 = Math.pow(d3 / d2, 0.025D);
/* 1454 */     for (byte b2 = 0; b2 < 40; b2++) {
/* 1455 */       if (b2 == 0) {
/* 1456 */         complexGaussianDist(arrayOfDouble, d1 * (b2 + 1), 0.1D, d4 * 0.2D);
/*      */       } else {
/* 1458 */         complexGaussianDist(arrayOfDouble, d1 * (b2 + 1), 0.1D, d4);
/* 1459 */       }  d4 *= d5;
/*      */     } 
/*      */     
/* 1462 */     complexGaussianDist(arrayOfDouble, d1 * 2.0D, 100.0D, 1.0D);
/*      */ 
/*      */     
/* 1465 */     SF2Sample sF2Sample = newSimpleFFTSample(paramSF2Soundbank, "Horn", arrayOfDouble, d1);
/* 1466 */     SF2Layer sF2Layer = newLayer(paramSF2Soundbank, "Horn", sF2Sample);
/* 1467 */     SF2Region sF2Region = sF2Layer.getRegions().get(0);
/* 1468 */     sF2Region.putInteger(54, 1);
/* 1469 */     sF2Region.putInteger(34, -6000);
/* 1470 */     sF2Region.putInteger(38, -1000);
/* 1471 */     sF2Region.putInteger(36, 4000);
/* 1472 */     sF2Region.putInteger(37, -100);
/*      */     
/* 1474 */     sF2Region.putInteger(26, -500);
/* 1475 */     sF2Region.putInteger(30, 12000);
/* 1476 */     sF2Region.putInteger(11, 5000);
/* 1477 */     sF2Region.putInteger(8, 4500);
/* 1478 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */   
/*      */   public static SF2Layer new_trumpet(SF2Soundbank paramSF2Soundbank) {
/* 1483 */     byte b1 = 8;
/* 1484 */     int i = 4096 * b1;
/* 1485 */     double[] arrayOfDouble1 = new double[i * 2];
/* 1486 */     double d1 = (b1 * 15);
/*      */     
/* 1488 */     double d2 = 0.5D;
/* 1489 */     double d3 = 1.0E-5D;
/* 1490 */     double d4 = d2;
/* 1491 */     double d5 = Math.pow(d3 / d2, 0.0125D);
/* 1492 */     double[] arrayOfDouble2 = new double[80]; byte b2;
/* 1493 */     for (b2 = 0; b2 < 80; b2++) {
/* 1494 */       arrayOfDouble2[b2] = d4;
/* 1495 */       d4 *= d5;
/*      */     } 
/*      */     
/* 1498 */     arrayOfDouble2[0] = arrayOfDouble2[0] * 0.05D;
/* 1499 */     arrayOfDouble2[1] = arrayOfDouble2[1] * 0.2D;
/* 1500 */     arrayOfDouble2[2] = arrayOfDouble2[2] * 0.5D;
/* 1501 */     arrayOfDouble2[3] = arrayOfDouble2[3] * 0.85D;
/*      */     
/* 1503 */     for (b2 = 0; b2 < 80; b2++) {
/* 1504 */       complexGaussianDist(arrayOfDouble1, d1 * (b2 + 1), 0.1D, arrayOfDouble2[b2]);
/*      */     }
/*      */     
/* 1507 */     complexGaussianDist(arrayOfDouble1, d1 * 5.0D, 300.0D, 3.0D);
/*      */ 
/*      */     
/* 1510 */     SF2Sample sF2Sample = newSimpleFFTSample(paramSF2Soundbank, "Trumpet", arrayOfDouble1, d1);
/* 1511 */     SF2Layer sF2Layer = newLayer(paramSF2Soundbank, "Trumpet", sF2Sample);
/* 1512 */     SF2Region sF2Region = sF2Layer.getRegions().get(0);
/* 1513 */     sF2Region.putInteger(54, 1);
/* 1514 */     sF2Region.putInteger(34, -10000);
/* 1515 */     sF2Region.putInteger(38, 0);
/* 1516 */     sF2Region.putInteger(36, 4000);
/* 1517 */     sF2Region.putInteger(37, -100);
/*      */     
/* 1519 */     sF2Region.putInteger(26, -4000);
/* 1520 */     sF2Region.putInteger(30, -2500);
/* 1521 */     sF2Region.putInteger(11, 5000);
/* 1522 */     sF2Region.putInteger(8, 4500);
/* 1523 */     sF2Region.putInteger(9, 10);
/* 1524 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */   
/*      */   public static SF2Layer new_brass_section(SF2Soundbank paramSF2Soundbank) {
/* 1529 */     byte b1 = 8;
/* 1530 */     int i = 4096 * b1;
/* 1531 */     double[] arrayOfDouble1 = new double[i * 2];
/* 1532 */     double d1 = (b1 * 15);
/*      */     
/* 1534 */     double d2 = 0.5D;
/* 1535 */     double d3 = 0.005D;
/* 1536 */     double d4 = d2;
/* 1537 */     double d5 = Math.pow(d3 / d2, 0.03333333333333333D);
/* 1538 */     double[] arrayOfDouble2 = new double[30];
/* 1539 */     for (byte b2 = 0; b2 < 30; b2++) {
/* 1540 */       arrayOfDouble2[b2] = d4;
/* 1541 */       d4 *= d5;
/*      */     } 
/*      */     
/* 1544 */     arrayOfDouble2[0] = arrayOfDouble2[0] * 0.8D;
/* 1545 */     arrayOfDouble2[1] = arrayOfDouble2[1] * 0.9D;
/*      */     
/* 1547 */     double d6 = 5.0D;
/* 1548 */     for (byte b3 = 0; b3 < 30; b3++) {
/* 1549 */       complexGaussianDist(arrayOfDouble1, d1 * (b3 + 1), 0.1D * d6, arrayOfDouble2[b3] * d6);
/* 1550 */       d6 += 6.0D;
/*      */     } 
/*      */     
/* 1553 */     complexGaussianDist(arrayOfDouble1, d1 * 6.0D, 300.0D, 2.0D);
/*      */ 
/*      */     
/* 1556 */     SF2Sample sF2Sample = newSimpleFFTSample(paramSF2Soundbank, "Brass Section", arrayOfDouble1, d1);
/* 1557 */     SF2Layer sF2Layer = newLayer(paramSF2Soundbank, "Brass Section", sF2Sample);
/* 1558 */     SF2Region sF2Region = sF2Layer.getRegions().get(0);
/* 1559 */     sF2Region.putInteger(54, 1);
/* 1560 */     sF2Region.putInteger(34, -9200);
/* 1561 */     sF2Region.putInteger(38, -1000);
/* 1562 */     sF2Region.putInteger(36, 4000);
/* 1563 */     sF2Region.putInteger(37, -100);
/*      */     
/* 1565 */     sF2Region.putInteger(26, -3000);
/* 1566 */     sF2Region.putInteger(30, 12000);
/* 1567 */     sF2Region.putInteger(11, 5000);
/* 1568 */     sF2Region.putInteger(8, 4500);
/* 1569 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */   
/*      */   public static SF2Layer new_trombone(SF2Soundbank paramSF2Soundbank) {
/* 1574 */     byte b1 = 8;
/* 1575 */     int i = 4096 * b1;
/* 1576 */     double[] arrayOfDouble1 = new double[i * 2];
/* 1577 */     double d1 = (b1 * 15);
/*      */     
/* 1579 */     double d2 = 0.5D;
/* 1580 */     double d3 = 0.001D;
/* 1581 */     double d4 = d2;
/* 1582 */     double d5 = Math.pow(d3 / d2, 0.0125D);
/* 1583 */     double[] arrayOfDouble2 = new double[80]; byte b2;
/* 1584 */     for (b2 = 0; b2 < 80; b2++) {
/* 1585 */       arrayOfDouble2[b2] = d4;
/* 1586 */       d4 *= d5;
/*      */     } 
/*      */     
/* 1589 */     arrayOfDouble2[0] = arrayOfDouble2[0] * 0.3D;
/* 1590 */     arrayOfDouble2[1] = arrayOfDouble2[1] * 0.7D;
/*      */     
/* 1592 */     for (b2 = 0; b2 < 80; b2++) {
/* 1593 */       complexGaussianDist(arrayOfDouble1, d1 * (b2 + 1), 0.1D, arrayOfDouble2[b2]);
/*      */     }
/*      */     
/* 1596 */     complexGaussianDist(arrayOfDouble1, d1 * 6.0D, 300.0D, 2.0D);
/*      */ 
/*      */     
/* 1599 */     SF2Sample sF2Sample = newSimpleFFTSample(paramSF2Soundbank, "Trombone", arrayOfDouble1, d1);
/* 1600 */     SF2Layer sF2Layer = newLayer(paramSF2Soundbank, "Trombone", sF2Sample);
/* 1601 */     SF2Region sF2Region = sF2Layer.getRegions().get(0);
/* 1602 */     sF2Region.putInteger(54, 1);
/* 1603 */     sF2Region.putInteger(34, -8000);
/* 1604 */     sF2Region.putInteger(38, -1000);
/* 1605 */     sF2Region.putInteger(36, 4000);
/* 1606 */     sF2Region.putInteger(37, -100);
/*      */     
/* 1608 */     sF2Region.putInteger(26, -2000);
/* 1609 */     sF2Region.putInteger(30, 12000);
/* 1610 */     sF2Region.putInteger(11, 5000);
/* 1611 */     sF2Region.putInteger(8, 4500);
/* 1612 */     sF2Region.putInteger(9, 10);
/* 1613 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */   
/*      */   public static SF2Layer new_sax(SF2Soundbank paramSF2Soundbank) {
/* 1618 */     byte b1 = 8;
/* 1619 */     int i = 4096 * b1;
/* 1620 */     double[] arrayOfDouble = new double[i * 2];
/* 1621 */     double d1 = (b1 * 15);
/*      */     
/* 1623 */     double d2 = 0.5D;
/* 1624 */     double d3 = 0.01D;
/* 1625 */     double d4 = d2;
/* 1626 */     double d5 = Math.pow(d3 / d2, 0.025D);
/* 1627 */     for (byte b2 = 0; b2 < 40; b2++) {
/* 1628 */       if (b2 == 0 || b2 == 2) {
/* 1629 */         complexGaussianDist(arrayOfDouble, d1 * (b2 + 1), 0.1D, d4 * 4.0D);
/*      */       } else {
/* 1631 */         complexGaussianDist(arrayOfDouble, d1 * (b2 + 1), 0.1D, d4);
/* 1632 */       }  d4 *= d5;
/*      */     } 
/*      */     
/* 1635 */     complexGaussianDist(arrayOfDouble, d1 * 4.0D, 200.0D, 1.0D);
/*      */     
/* 1637 */     SF2Sample sF2Sample = newSimpleFFTSample(paramSF2Soundbank, "Sax", arrayOfDouble, d1);
/* 1638 */     SF2Layer sF2Layer = newLayer(paramSF2Soundbank, "Sax", sF2Sample);
/* 1639 */     SF2Region sF2Region = sF2Layer.getRegions().get(0);
/* 1640 */     sF2Region.putInteger(54, 1);
/* 1641 */     sF2Region.putInteger(34, -6000);
/* 1642 */     sF2Region.putInteger(38, -1000);
/* 1643 */     sF2Region.putInteger(36, 4000);
/* 1644 */     sF2Region.putInteger(37, -100);
/*      */     
/* 1646 */     sF2Region.putInteger(26, -3000);
/* 1647 */     sF2Region.putInteger(30, 12000);
/* 1648 */     sF2Region.putInteger(11, 5000);
/* 1649 */     sF2Region.putInteger(8, 4500);
/* 1650 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */   
/*      */   public static SF2Layer new_oboe(SF2Soundbank paramSF2Soundbank) {
/* 1655 */     byte b = 8;
/* 1656 */     int i = 4096 * b;
/* 1657 */     double[] arrayOfDouble = new double[i * 2];
/* 1658 */     double d = (b * 15);
/*      */     
/* 1660 */     complexGaussianDist(arrayOfDouble, d * 5.0D, 100.0D, 80.0D);
/*      */ 
/*      */     
/* 1663 */     complexGaussianDist(arrayOfDouble, d * 1.0D, 0.01D, 0.53D);
/* 1664 */     complexGaussianDist(arrayOfDouble, d * 2.0D, 0.01D, 0.51D);
/* 1665 */     complexGaussianDist(arrayOfDouble, d * 3.0D, 0.01D, 0.48D);
/* 1666 */     complexGaussianDist(arrayOfDouble, d * 4.0D, 0.01D, 0.49D);
/* 1667 */     complexGaussianDist(arrayOfDouble, d * 5.0D, 0.01D, 5.0D);
/* 1668 */     complexGaussianDist(arrayOfDouble, d * 6.0D, 0.01D, 0.51D);
/* 1669 */     complexGaussianDist(arrayOfDouble, d * 7.0D, 0.01D, 0.5D);
/* 1670 */     complexGaussianDist(arrayOfDouble, d * 8.0D, 0.01D, 0.59D);
/* 1671 */     complexGaussianDist(arrayOfDouble, d * 9.0D, 0.01D, 0.61D);
/* 1672 */     complexGaussianDist(arrayOfDouble, d * 10.0D, 0.01D, 0.52D);
/* 1673 */     complexGaussianDist(arrayOfDouble, d * 11.0D, 0.01D, 0.49D);
/* 1674 */     complexGaussianDist(arrayOfDouble, d * 12.0D, 0.01D, 0.51D);
/* 1675 */     complexGaussianDist(arrayOfDouble, d * 13.0D, 0.01D, 0.48D);
/* 1676 */     complexGaussianDist(arrayOfDouble, d * 14.0D, 0.01D, 0.51D);
/* 1677 */     complexGaussianDist(arrayOfDouble, d * 15.0D, 0.01D, 0.46D);
/* 1678 */     complexGaussianDist(arrayOfDouble, d * 16.0D, 0.01D, 0.35D);
/* 1679 */     complexGaussianDist(arrayOfDouble, d * 17.0D, 0.01D, 0.2D);
/* 1680 */     complexGaussianDist(arrayOfDouble, d * 18.0D, 0.01D, 0.1D);
/* 1681 */     complexGaussianDist(arrayOfDouble, d * 19.0D, 0.01D, 0.5D);
/* 1682 */     complexGaussianDist(arrayOfDouble, d * 20.0D, 0.01D, 0.1D);
/*      */ 
/*      */     
/* 1685 */     SF2Sample sF2Sample = newSimpleFFTSample(paramSF2Soundbank, "Oboe", arrayOfDouble, d);
/* 1686 */     SF2Layer sF2Layer = newLayer(paramSF2Soundbank, "Oboe", sF2Sample);
/* 1687 */     SF2Region sF2Region = sF2Layer.getRegions().get(0);
/* 1688 */     sF2Region.putInteger(54, 1);
/* 1689 */     sF2Region.putInteger(34, -6000);
/* 1690 */     sF2Region.putInteger(38, -1000);
/* 1691 */     sF2Region.putInteger(36, 4000);
/* 1692 */     sF2Region.putInteger(37, -100);
/* 1693 */     sF2Region.putInteger(8, 9500);
/* 1694 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */   
/*      */   public static SF2Layer new_bassoon(SF2Soundbank paramSF2Soundbank) {
/* 1699 */     byte b = 8;
/* 1700 */     int i = 4096 * b;
/* 1701 */     double[] arrayOfDouble = new double[i * 2];
/* 1702 */     double d = (b * 15);
/*      */     
/* 1704 */     complexGaussianDist(arrayOfDouble, d * 2.0D, 100.0D, 40.0D);
/* 1705 */     complexGaussianDist(arrayOfDouble, d * 4.0D, 100.0D, 20.0D);
/*      */     
/* 1707 */     complexGaussianDist(arrayOfDouble, d * 1.0D, 0.01D, 0.53D);
/* 1708 */     complexGaussianDist(arrayOfDouble, d * 2.0D, 0.01D, 5.0D);
/* 1709 */     complexGaussianDist(arrayOfDouble, d * 3.0D, 0.01D, 0.51D);
/* 1710 */     complexGaussianDist(arrayOfDouble, d * 4.0D, 0.01D, 0.48D);
/* 1711 */     complexGaussianDist(arrayOfDouble, d * 5.0D, 0.01D, 1.49D);
/* 1712 */     complexGaussianDist(arrayOfDouble, d * 6.0D, 0.01D, 0.51D);
/* 1713 */     complexGaussianDist(arrayOfDouble, d * 7.0D, 0.01D, 0.5D);
/* 1714 */     complexGaussianDist(arrayOfDouble, d * 8.0D, 0.01D, 0.59D);
/* 1715 */     complexGaussianDist(arrayOfDouble, d * 9.0D, 0.01D, 0.61D);
/* 1716 */     complexGaussianDist(arrayOfDouble, d * 10.0D, 0.01D, 0.52D);
/* 1717 */     complexGaussianDist(arrayOfDouble, d * 11.0D, 0.01D, 0.49D);
/* 1718 */     complexGaussianDist(arrayOfDouble, d * 12.0D, 0.01D, 0.51D);
/* 1719 */     complexGaussianDist(arrayOfDouble, d * 13.0D, 0.01D, 0.48D);
/* 1720 */     complexGaussianDist(arrayOfDouble, d * 14.0D, 0.01D, 0.51D);
/* 1721 */     complexGaussianDist(arrayOfDouble, d * 15.0D, 0.01D, 0.46D);
/* 1722 */     complexGaussianDist(arrayOfDouble, d * 16.0D, 0.01D, 0.35D);
/* 1723 */     complexGaussianDist(arrayOfDouble, d * 17.0D, 0.01D, 0.2D);
/* 1724 */     complexGaussianDist(arrayOfDouble, d * 18.0D, 0.01D, 0.1D);
/* 1725 */     complexGaussianDist(arrayOfDouble, d * 19.0D, 0.01D, 0.5D);
/* 1726 */     complexGaussianDist(arrayOfDouble, d * 20.0D, 0.01D, 0.1D);
/*      */ 
/*      */     
/* 1729 */     SF2Sample sF2Sample = newSimpleFFTSample(paramSF2Soundbank, "Flute", arrayOfDouble, d);
/* 1730 */     SF2Layer sF2Layer = newLayer(paramSF2Soundbank, "Flute", sF2Sample);
/* 1731 */     SF2Region sF2Region = sF2Layer.getRegions().get(0);
/* 1732 */     sF2Region.putInteger(54, 1);
/* 1733 */     sF2Region.putInteger(34, -6000);
/* 1734 */     sF2Region.putInteger(38, -1000);
/* 1735 */     sF2Region.putInteger(36, 4000);
/* 1736 */     sF2Region.putInteger(37, -100);
/* 1737 */     sF2Region.putInteger(8, 9500);
/* 1738 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */   
/*      */   public static SF2Layer new_clarinet(SF2Soundbank paramSF2Soundbank) {
/* 1743 */     byte b = 8;
/* 1744 */     int i = 4096 * b;
/* 1745 */     double[] arrayOfDouble = new double[i * 2];
/* 1746 */     double d = (b * 15);
/*      */     
/* 1748 */     complexGaussianDist(arrayOfDouble, d * 1.0D, 0.001D, 0.5D);
/* 1749 */     complexGaussianDist(arrayOfDouble, d * 2.0D, 0.001D, 0.02D);
/* 1750 */     complexGaussianDist(arrayOfDouble, d * 3.0D, 0.001D, 0.2D);
/* 1751 */     complexGaussianDist(arrayOfDouble, d * 4.0D, 0.01D, 0.1D);
/*      */     
/* 1753 */     complexGaussianDist(arrayOfDouble, d * 4.0D, 100.0D, 60.0D);
/* 1754 */     complexGaussianDist(arrayOfDouble, d * 6.0D, 100.0D, 20.0D);
/* 1755 */     complexGaussianDist(arrayOfDouble, d * 8.0D, 100.0D, 20.0D);
/*      */     
/* 1757 */     complexGaussianDist(arrayOfDouble, d * 5.0D, 0.001D, 0.1D);
/* 1758 */     complexGaussianDist(arrayOfDouble, d * 6.0D, 0.001D, 0.09D);
/* 1759 */     complexGaussianDist(arrayOfDouble, d * 7.0D, 0.001D, 0.02D);
/* 1760 */     complexGaussianDist(arrayOfDouble, d * 8.0D, 0.005D, 0.16D);
/* 1761 */     complexGaussianDist(arrayOfDouble, d * 9.0D, 0.005D, 0.96D);
/* 1762 */     complexGaussianDist(arrayOfDouble, d * 10.0D, 0.01D, 0.9D);
/* 1763 */     complexGaussianDist(arrayOfDouble, d * 11.0D, 0.08D, 1.2D);
/* 1764 */     complexGaussianDist(arrayOfDouble, d * 12.0D, 0.08D, 1.8D);
/* 1765 */     complexGaussianDist(arrayOfDouble, d * 13.0D, 0.08D, 1.6D);
/* 1766 */     complexGaussianDist(arrayOfDouble, d * 14.0D, 0.08D, 1.2D);
/* 1767 */     complexGaussianDist(arrayOfDouble, d * 15.0D, 0.08D, 0.9D);
/* 1768 */     complexGaussianDist(arrayOfDouble, d * 16.0D, 0.08D, 0.5D);
/* 1769 */     complexGaussianDist(arrayOfDouble, d * 17.0D, 0.08D, 0.2D);
/*      */ 
/*      */     
/* 1772 */     complexGaussianDist(arrayOfDouble, d * 1.0D, 10.0D, 8.0D);
/* 1773 */     complexGaussianDist(arrayOfDouble, d * 2.0D, 10.0D, 8.0D);
/* 1774 */     complexGaussianDist(arrayOfDouble, d * 3.0D, 10.0D, 8.0D);
/* 1775 */     complexGaussianDist(arrayOfDouble, d * 4.0D, 10.0D, 8.0D);
/* 1776 */     complexGaussianDist(arrayOfDouble, d * 5.0D, 10.0D, 8.0D);
/* 1777 */     complexGaussianDist(arrayOfDouble, d * 6.0D, 20.0D, 9.0D);
/* 1778 */     complexGaussianDist(arrayOfDouble, d * 7.0D, 20.0D, 9.0D);
/* 1779 */     complexGaussianDist(arrayOfDouble, d * 8.0D, 20.0D, 9.0D);
/* 1780 */     complexGaussianDist(arrayOfDouble, d * 9.0D, 20.0D, 8.0D);
/* 1781 */     complexGaussianDist(arrayOfDouble, d * 10.0D, 30.0D, 8.0D);
/* 1782 */     complexGaussianDist(arrayOfDouble, d * 11.0D, 30.0D, 9.0D);
/* 1783 */     complexGaussianDist(arrayOfDouble, d * 12.0D, 30.0D, 9.0D);
/* 1784 */     complexGaussianDist(arrayOfDouble, d * 13.0D, 30.0D, 8.0D);
/* 1785 */     complexGaussianDist(arrayOfDouble, d * 14.0D, 30.0D, 8.0D);
/* 1786 */     complexGaussianDist(arrayOfDouble, d * 15.0D, 30.0D, 7.0D);
/* 1787 */     complexGaussianDist(arrayOfDouble, d * 16.0D, 30.0D, 7.0D);
/* 1788 */     complexGaussianDist(arrayOfDouble, d * 17.0D, 30.0D, 6.0D);
/*      */     
/* 1790 */     SF2Sample sF2Sample = newSimpleFFTSample(paramSF2Soundbank, "Clarinet", arrayOfDouble, d);
/* 1791 */     SF2Layer sF2Layer = newLayer(paramSF2Soundbank, "Clarinet", sF2Sample);
/* 1792 */     SF2Region sF2Region = sF2Layer.getRegions().get(0);
/* 1793 */     sF2Region.putInteger(54, 1);
/* 1794 */     sF2Region.putInteger(34, -6000);
/* 1795 */     sF2Region.putInteger(38, -1000);
/* 1796 */     sF2Region.putInteger(36, 4000);
/* 1797 */     sF2Region.putInteger(37, -100);
/* 1798 */     sF2Region.putInteger(8, 9500);
/* 1799 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SF2Layer new_timpani(SF2Soundbank paramSF2Soundbank) {
/* 1810 */     char c = '耀';
/* 1811 */     double[] arrayOfDouble3 = new double[2 * c];
/* 1812 */     double d1 = 48.0D;
/* 1813 */     complexGaussianDist(arrayOfDouble3, d1 * 2.0D, 0.2D, 1.0D);
/* 1814 */     complexGaussianDist(arrayOfDouble3, d1 * 3.0D, 0.2D, 0.7D);
/* 1815 */     complexGaussianDist(arrayOfDouble3, d1 * 5.0D, 10.0D, 1.0D);
/* 1816 */     complexGaussianDist(arrayOfDouble3, d1 * 6.0D, 9.0D, 1.0D);
/* 1817 */     complexGaussianDist(arrayOfDouble3, d1 * 8.0D, 15.0D, 1.0D);
/* 1818 */     complexGaussianDist(arrayOfDouble3, d1 * 9.0D, 18.0D, 0.8D);
/* 1819 */     complexGaussianDist(arrayOfDouble3, d1 * 11.0D, 21.0D, 0.5D);
/* 1820 */     complexGaussianDist(arrayOfDouble3, d1 * 13.0D, 28.0D, 0.3D);
/* 1821 */     complexGaussianDist(arrayOfDouble3, d1 * 14.0D, 22.0D, 0.1D);
/* 1822 */     randomPhase(arrayOfDouble3, new Random(3049912L));
/* 1823 */     ifft(arrayOfDouble3);
/* 1824 */     normalize(arrayOfDouble3, 0.5D);
/* 1825 */     arrayOfDouble3 = realPart(arrayOfDouble3);
/*      */     
/* 1827 */     double d3 = arrayOfDouble3.length;
/* 1828 */     for (byte b2 = 0; b2 < arrayOfDouble3.length; b2++) {
/* 1829 */       double d = 1.0D - b2 / d3;
/* 1830 */       arrayOfDouble3[b2] = arrayOfDouble3[b2] * d * d;
/*      */     } 
/* 1832 */     fadeUp(arrayOfDouble3, 40);
/* 1833 */     double[] arrayOfDouble1 = arrayOfDouble3;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1838 */     c = '䀀';
/* 1839 */     arrayOfDouble3 = new double[2 * c];
/* 1840 */     Random random = new Random(3049912L); int i;
/* 1841 */     for (i = 0; i < arrayOfDouble3.length; i += 2) {
/* 1842 */       arrayOfDouble3[i] = 2.0D * (random.nextDouble() - 0.5D) * 0.1D;
/*      */     }
/* 1844 */     fft(arrayOfDouble3);
/*      */     
/* 1846 */     for (i = c / 2; i < arrayOfDouble3.length; i++)
/* 1847 */       arrayOfDouble3[i] = 0.0D; 
/* 1848 */     for (i = 4096; i < 8192; i++)
/* 1849 */       arrayOfDouble3[i] = 1.0D - (i - 4096) / 4096.0D; 
/* 1850 */     for (i = 0; i < 300; i++) {
/* 1851 */       d3 = 1.0D - i / 300.0D;
/* 1852 */       arrayOfDouble3[i] = arrayOfDouble3[i] * (1.0D + 20.0D * d3 * d3);
/*      */     } 
/* 1854 */     for (i = 0; i < 24; i++)
/* 1855 */       arrayOfDouble3[i] = 0.0D; 
/* 1856 */     randomPhase(arrayOfDouble3, new Random(3049912L));
/* 1857 */     ifft(arrayOfDouble3);
/* 1858 */     normalize(arrayOfDouble3, 0.9D);
/* 1859 */     arrayOfDouble3 = realPart(arrayOfDouble3);
/* 1860 */     double d2 = 1.0D;
/* 1861 */     for (byte b1 = 0; b1 < arrayOfDouble3.length; b1++) {
/* 1862 */       arrayOfDouble3[b1] = arrayOfDouble3[b1] * d2;
/* 1863 */       d2 *= 0.9998D;
/*      */     } 
/* 1865 */     double[] arrayOfDouble2 = arrayOfDouble3;
/*      */ 
/*      */     
/* 1868 */     for (c = Character.MIN_VALUE; c < arrayOfDouble2.length; c++) {
/* 1869 */       arrayOfDouble1[c] = arrayOfDouble1[c] + arrayOfDouble2[c] * 0.02D;
/*      */     }
/* 1871 */     normalize(arrayOfDouble1, 0.9D);
/*      */     
/* 1873 */     SF2Sample sF2Sample = newSimpleDrumSample(paramSF2Soundbank, "Timpani", arrayOfDouble1);
/*      */     
/* 1875 */     SF2Layer sF2Layer = new SF2Layer(paramSF2Soundbank);
/* 1876 */     sF2Layer.setName("Timpani");
/*      */     
/* 1878 */     SF2GlobalRegion sF2GlobalRegion = new SF2GlobalRegion();
/* 1879 */     sF2Layer.setGlobalZone(sF2GlobalRegion);
/* 1880 */     paramSF2Soundbank.addResource(sF2Layer);
/*      */     
/* 1882 */     SF2LayerRegion sF2LayerRegion = new SF2LayerRegion();
/* 1883 */     sF2LayerRegion.putInteger(38, 12000);
/* 1884 */     sF2LayerRegion.putInteger(48, -100);
/* 1885 */     sF2LayerRegion.setSample(sF2Sample);
/* 1886 */     sF2Layer.getRegions().add(sF2LayerRegion);
/*      */     
/* 1888 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SF2Layer new_melodic_toms(SF2Soundbank paramSF2Soundbank) {
/* 1898 */     char c = '䀀';
/* 1899 */     double[] arrayOfDouble3 = new double[2 * c];
/* 1900 */     complexGaussianDist(arrayOfDouble3, 30.0D, 0.5D, 1.0D);
/* 1901 */     randomPhase(arrayOfDouble3, new Random(3049912L));
/* 1902 */     ifft(arrayOfDouble3);
/* 1903 */     normalize(arrayOfDouble3, 0.8D);
/* 1904 */     arrayOfDouble3 = realPart(arrayOfDouble3);
/*      */     
/* 1906 */     double d1 = arrayOfDouble3.length;
/* 1907 */     for (byte b1 = 0; b1 < arrayOfDouble3.length; b1++)
/* 1908 */       arrayOfDouble3[b1] = arrayOfDouble3[b1] * (1.0D - b1 / d1); 
/* 1909 */     double[] arrayOfDouble1 = arrayOfDouble3;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1914 */     c = '䀀';
/* 1915 */     arrayOfDouble3 = new double[2 * c];
/* 1916 */     Random random = new Random(3049912L); int i;
/* 1917 */     for (i = 0; i < arrayOfDouble3.length; i += 2)
/* 1918 */       arrayOfDouble3[i] = 2.0D * (random.nextDouble() - 0.5D) * 0.1D; 
/* 1919 */     fft(arrayOfDouble3);
/*      */     
/* 1921 */     for (i = c / 2; i < arrayOfDouble3.length; i++)
/* 1922 */       arrayOfDouble3[i] = 0.0D; 
/* 1923 */     for (i = 4096; i < 8192; i++)
/* 1924 */       arrayOfDouble3[i] = 1.0D - (i - 4096) / 4096.0D; 
/* 1925 */     for (i = 0; i < 200; i++) {
/* 1926 */       double d = 1.0D - i / 200.0D;
/* 1927 */       arrayOfDouble3[i] = arrayOfDouble3[i] * (1.0D + 20.0D * d * d);
/*      */     } 
/* 1929 */     for (i = 0; i < 30; i++)
/* 1930 */       arrayOfDouble3[i] = 0.0D; 
/* 1931 */     randomPhase(arrayOfDouble3, new Random(3049912L));
/* 1932 */     ifft(arrayOfDouble3);
/* 1933 */     normalize(arrayOfDouble3, 0.9D);
/* 1934 */     arrayOfDouble3 = realPart(arrayOfDouble3);
/* 1935 */     double d2 = 1.0D;
/* 1936 */     for (byte b2 = 0; b2 < arrayOfDouble3.length; b2++) {
/* 1937 */       arrayOfDouble3[b2] = arrayOfDouble3[b2] * d2;
/* 1938 */       d2 *= 0.9996D;
/*      */     } 
/* 1940 */     double[] arrayOfDouble2 = arrayOfDouble3;
/*      */ 
/*      */     
/* 1943 */     for (c = Character.MIN_VALUE; c < arrayOfDouble2.length; c++)
/* 1944 */       arrayOfDouble1[c] = arrayOfDouble1[c] + arrayOfDouble2[c] * 0.5D; 
/* 1945 */     for (c = Character.MIN_VALUE; c < '\005'; c++) {
/* 1946 */       arrayOfDouble1[c] = arrayOfDouble1[c] * c / 5.0D;
/*      */     }
/* 1948 */     normalize(arrayOfDouble1, 0.99D);
/*      */     
/* 1950 */     SF2Sample sF2Sample = newSimpleDrumSample(paramSF2Soundbank, "Melodic Toms", arrayOfDouble1);
/* 1951 */     sF2Sample.setOriginalPitch(63);
/*      */     
/* 1953 */     SF2Layer sF2Layer = new SF2Layer(paramSF2Soundbank);
/* 1954 */     sF2Layer.setName("Melodic Toms");
/*      */     
/* 1956 */     SF2GlobalRegion sF2GlobalRegion = new SF2GlobalRegion();
/* 1957 */     sF2Layer.setGlobalZone(sF2GlobalRegion);
/* 1958 */     paramSF2Soundbank.addResource(sF2Layer);
/*      */     
/* 1960 */     SF2LayerRegion sF2LayerRegion = new SF2LayerRegion();
/* 1961 */     sF2LayerRegion.putInteger(38, 12000);
/*      */     
/* 1963 */     sF2LayerRegion.putInteger(48, -100);
/* 1964 */     sF2LayerRegion.setSample(sF2Sample);
/* 1965 */     sF2Layer.getRegions().add(sF2LayerRegion);
/*      */     
/* 1967 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SF2Layer new_reverse_cymbal(SF2Soundbank paramSF2Soundbank) {
/* 1973 */     char c = '䀀';
/* 1974 */     double[] arrayOfDouble2 = new double[2 * c];
/* 1975 */     Random random = new Random(3049912L); int i;
/* 1976 */     for (i = 0; i < arrayOfDouble2.length; i += 2)
/* 1977 */       arrayOfDouble2[i] = 2.0D * (random.nextDouble() - 0.5D); 
/* 1978 */     for (i = c / 2; i < arrayOfDouble2.length; i++)
/* 1979 */       arrayOfDouble2[i] = 0.0D; 
/* 1980 */     for (i = 0; i < 100; i++) {
/* 1981 */       arrayOfDouble2[i] = 0.0D;
/*      */     }
/* 1983 */     for (i = 0; i < 1024; i++) {
/* 1984 */       double d = i / 1024.0D;
/* 1985 */       arrayOfDouble2[i] = 1.0D - d;
/*      */     } 
/* 1987 */     double[] arrayOfDouble1 = arrayOfDouble2;
/*      */ 
/*      */     
/* 1990 */     SF2Sample sF2Sample = newSimpleFFTSample(paramSF2Soundbank, "Reverse Cymbal", arrayOfDouble1, 100.0D, 20);
/*      */ 
/*      */     
/* 1993 */     SF2Layer sF2Layer = new SF2Layer(paramSF2Soundbank);
/* 1994 */     sF2Layer.setName("Reverse Cymbal");
/*      */     
/* 1996 */     SF2GlobalRegion sF2GlobalRegion = new SF2GlobalRegion();
/* 1997 */     sF2Layer.setGlobalZone(sF2GlobalRegion);
/* 1998 */     paramSF2Soundbank.addResource(sF2Layer);
/*      */     
/* 2000 */     SF2LayerRegion sF2LayerRegion = new SF2LayerRegion();
/* 2001 */     sF2LayerRegion.putInteger(34, -200);
/* 2002 */     sF2LayerRegion.putInteger(36, -12000);
/* 2003 */     sF2LayerRegion.putInteger(54, 1);
/* 2004 */     sF2LayerRegion.putInteger(38, -1000);
/* 2005 */     sF2LayerRegion.putInteger(37, 1000);
/* 2006 */     sF2LayerRegion.setSample(sF2Sample);
/* 2007 */     sF2Layer.getRegions().add(sF2LayerRegion);
/*      */     
/* 2009 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SF2Layer new_snare_drum(SF2Soundbank paramSF2Soundbank) {
/* 2019 */     char c = '䀀';
/* 2020 */     double[] arrayOfDouble3 = new double[2 * c];
/* 2021 */     complexGaussianDist(arrayOfDouble3, 24.0D, 0.5D, 1.0D);
/* 2022 */     randomPhase(arrayOfDouble3, new Random(3049912L));
/* 2023 */     ifft(arrayOfDouble3);
/* 2024 */     normalize(arrayOfDouble3, 0.5D);
/* 2025 */     arrayOfDouble3 = realPart(arrayOfDouble3);
/*      */     
/* 2027 */     double d1 = arrayOfDouble3.length;
/* 2028 */     for (byte b1 = 0; b1 < arrayOfDouble3.length; b1++)
/* 2029 */       arrayOfDouble3[b1] = arrayOfDouble3[b1] * (1.0D - b1 / d1); 
/* 2030 */     double[] arrayOfDouble1 = arrayOfDouble3;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2035 */     c = '䀀';
/* 2036 */     arrayOfDouble3 = new double[2 * c];
/* 2037 */     Random random = new Random(3049912L); int i;
/* 2038 */     for (i = 0; i < arrayOfDouble3.length; i += 2)
/* 2039 */       arrayOfDouble3[i] = 2.0D * (random.nextDouble() - 0.5D) * 0.1D; 
/* 2040 */     fft(arrayOfDouble3);
/*      */     
/* 2042 */     for (i = c / 2; i < arrayOfDouble3.length; i++)
/* 2043 */       arrayOfDouble3[i] = 0.0D; 
/* 2044 */     for (i = 4096; i < 8192; i++)
/* 2045 */       arrayOfDouble3[i] = 1.0D - (i - 4096) / 4096.0D; 
/* 2046 */     for (i = 0; i < 300; i++) {
/* 2047 */       double d = 1.0D - i / 300.0D;
/* 2048 */       arrayOfDouble3[i] = arrayOfDouble3[i] * (1.0D + 20.0D * d * d);
/*      */     } 
/* 2050 */     for (i = 0; i < 24; i++)
/* 2051 */       arrayOfDouble3[i] = 0.0D; 
/* 2052 */     randomPhase(arrayOfDouble3, new Random(3049912L));
/* 2053 */     ifft(arrayOfDouble3);
/* 2054 */     normalize(arrayOfDouble3, 0.9D);
/* 2055 */     arrayOfDouble3 = realPart(arrayOfDouble3);
/* 2056 */     double d2 = 1.0D;
/* 2057 */     for (byte b2 = 0; b2 < arrayOfDouble3.length; b2++) {
/* 2058 */       arrayOfDouble3[b2] = arrayOfDouble3[b2] * d2;
/* 2059 */       d2 *= 0.9998D;
/*      */     } 
/* 2061 */     double[] arrayOfDouble2 = arrayOfDouble3;
/*      */ 
/*      */     
/* 2064 */     for (c = Character.MIN_VALUE; c < arrayOfDouble2.length; c++)
/* 2065 */       arrayOfDouble1[c] = arrayOfDouble1[c] + arrayOfDouble2[c]; 
/* 2066 */     for (c = Character.MIN_VALUE; c < '\005'; c++) {
/* 2067 */       arrayOfDouble1[c] = arrayOfDouble1[c] * c / 5.0D;
/*      */     }
/* 2069 */     SF2Sample sF2Sample = newSimpleDrumSample(paramSF2Soundbank, "Snare Drum", arrayOfDouble1);
/*      */     
/* 2071 */     SF2Layer sF2Layer = new SF2Layer(paramSF2Soundbank);
/* 2072 */     sF2Layer.setName("Snare Drum");
/*      */     
/* 2074 */     SF2GlobalRegion sF2GlobalRegion = new SF2GlobalRegion();
/* 2075 */     sF2Layer.setGlobalZone(sF2GlobalRegion);
/* 2076 */     paramSF2Soundbank.addResource(sF2Layer);
/*      */     
/* 2078 */     SF2LayerRegion sF2LayerRegion = new SF2LayerRegion();
/* 2079 */     sF2LayerRegion.putInteger(38, 12000);
/* 2080 */     sF2LayerRegion.putInteger(56, 0);
/* 2081 */     sF2LayerRegion.putInteger(48, -100);
/* 2082 */     sF2LayerRegion.setSample(sF2Sample);
/* 2083 */     sF2Layer.getRegions().add(sF2LayerRegion);
/*      */     
/* 2085 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SF2Layer new_bass_drum(SF2Soundbank paramSF2Soundbank) {
/* 2095 */     char c = '䀀';
/* 2096 */     double[] arrayOfDouble3 = new double[2 * c];
/* 2097 */     complexGaussianDist(arrayOfDouble3, 10.0D, 2.0D, 1.0D);
/* 2098 */     complexGaussianDist(arrayOfDouble3, 17.2D, 2.0D, 1.0D);
/* 2099 */     randomPhase(arrayOfDouble3, new Random(3049912L));
/* 2100 */     ifft(arrayOfDouble3);
/* 2101 */     normalize(arrayOfDouble3, 0.9D);
/* 2102 */     arrayOfDouble3 = realPart(arrayOfDouble3);
/* 2103 */     double d1 = arrayOfDouble3.length;
/* 2104 */     for (byte b1 = 0; b1 < arrayOfDouble3.length; b1++)
/* 2105 */       arrayOfDouble3[b1] = arrayOfDouble3[b1] * (1.0D - b1 / d1); 
/* 2106 */     double[] arrayOfDouble1 = arrayOfDouble3;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2111 */     c = 'က';
/* 2112 */     arrayOfDouble3 = new double[2 * c];
/* 2113 */     Random random = new Random(3049912L); int i;
/* 2114 */     for (i = 0; i < arrayOfDouble3.length; i += 2)
/* 2115 */       arrayOfDouble3[i] = 2.0D * (random.nextDouble() - 0.5D) * 0.1D; 
/* 2116 */     fft(arrayOfDouble3);
/*      */     
/* 2118 */     for (i = c / 2; i < arrayOfDouble3.length; i++)
/* 2119 */       arrayOfDouble3[i] = 0.0D; 
/* 2120 */     for (i = 1024; i < 2048; i++)
/* 2121 */       arrayOfDouble3[i] = 1.0D - (i - 1024) / 1024.0D; 
/* 2122 */     for (i = 0; i < 512; i++)
/* 2123 */       arrayOfDouble3[i] = (10 * i) / 512.0D; 
/* 2124 */     for (i = 0; i < 10; i++)
/* 2125 */       arrayOfDouble3[i] = 0.0D; 
/* 2126 */     randomPhase(arrayOfDouble3, new Random(3049912L));
/* 2127 */     ifft(arrayOfDouble3);
/* 2128 */     normalize(arrayOfDouble3, 0.9D);
/* 2129 */     arrayOfDouble3 = realPart(arrayOfDouble3);
/* 2130 */     double d2 = 1.0D;
/* 2131 */     for (byte b2 = 0; b2 < arrayOfDouble3.length; b2++) {
/* 2132 */       arrayOfDouble3[b2] = arrayOfDouble3[b2] * d2;
/* 2133 */       d2 *= 0.999D;
/*      */     } 
/* 2135 */     double[] arrayOfDouble2 = arrayOfDouble3;
/*      */ 
/*      */     
/* 2138 */     for (c = Character.MIN_VALUE; c < arrayOfDouble2.length; c++)
/* 2139 */       arrayOfDouble1[c] = arrayOfDouble1[c] + arrayOfDouble2[c] * 0.5D; 
/* 2140 */     for (c = Character.MIN_VALUE; c < '\005'; c++) {
/* 2141 */       arrayOfDouble1[c] = arrayOfDouble1[c] * c / 5.0D;
/*      */     }
/* 2143 */     SF2Sample sF2Sample = newSimpleDrumSample(paramSF2Soundbank, "Bass Drum", arrayOfDouble1);
/*      */     
/* 2145 */     SF2Layer sF2Layer = new SF2Layer(paramSF2Soundbank);
/* 2146 */     sF2Layer.setName("Bass Drum");
/*      */     
/* 2148 */     SF2GlobalRegion sF2GlobalRegion = new SF2GlobalRegion();
/* 2149 */     sF2Layer.setGlobalZone(sF2GlobalRegion);
/* 2150 */     paramSF2Soundbank.addResource(sF2Layer);
/*      */     
/* 2152 */     SF2LayerRegion sF2LayerRegion = new SF2LayerRegion();
/* 2153 */     sF2LayerRegion.putInteger(38, 12000);
/* 2154 */     sF2LayerRegion.putInteger(56, 0);
/* 2155 */     sF2LayerRegion.putInteger(48, -100);
/* 2156 */     sF2LayerRegion.setSample(sF2Sample);
/* 2157 */     sF2Layer.getRegions().add(sF2LayerRegion);
/*      */     
/* 2159 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SF2Layer new_tom(SF2Soundbank paramSF2Soundbank) {
/* 2169 */     char c = '䀀';
/* 2170 */     double[] arrayOfDouble3 = new double[2 * c];
/* 2171 */     complexGaussianDist(arrayOfDouble3, 30.0D, 0.5D, 1.0D);
/* 2172 */     randomPhase(arrayOfDouble3, new Random(3049912L));
/* 2173 */     ifft(arrayOfDouble3);
/* 2174 */     normalize(arrayOfDouble3, 0.8D);
/* 2175 */     arrayOfDouble3 = realPart(arrayOfDouble3);
/*      */     
/* 2177 */     double d1 = arrayOfDouble3.length;
/* 2178 */     for (byte b1 = 0; b1 < arrayOfDouble3.length; b1++)
/* 2179 */       arrayOfDouble3[b1] = arrayOfDouble3[b1] * (1.0D - b1 / d1); 
/* 2180 */     double[] arrayOfDouble1 = arrayOfDouble3;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2185 */     c = '䀀';
/* 2186 */     arrayOfDouble3 = new double[2 * c];
/* 2187 */     Random random = new Random(3049912L); int i;
/* 2188 */     for (i = 0; i < arrayOfDouble3.length; i += 2)
/* 2189 */       arrayOfDouble3[i] = 2.0D * (random.nextDouble() - 0.5D) * 0.1D; 
/* 2190 */     fft(arrayOfDouble3);
/*      */     
/* 2192 */     for (i = c / 2; i < arrayOfDouble3.length; i++)
/* 2193 */       arrayOfDouble3[i] = 0.0D; 
/* 2194 */     for (i = 4096; i < 8192; i++)
/* 2195 */       arrayOfDouble3[i] = 1.0D - (i - 4096) / 4096.0D; 
/* 2196 */     for (i = 0; i < 200; i++) {
/* 2197 */       double d = 1.0D - i / 200.0D;
/* 2198 */       arrayOfDouble3[i] = arrayOfDouble3[i] * (1.0D + 20.0D * d * d);
/*      */     } 
/* 2200 */     for (i = 0; i < 30; i++)
/* 2201 */       arrayOfDouble3[i] = 0.0D; 
/* 2202 */     randomPhase(arrayOfDouble3, new Random(3049912L));
/* 2203 */     ifft(arrayOfDouble3);
/* 2204 */     normalize(arrayOfDouble3, 0.9D);
/* 2205 */     arrayOfDouble3 = realPart(arrayOfDouble3);
/* 2206 */     double d2 = 1.0D;
/* 2207 */     for (byte b2 = 0; b2 < arrayOfDouble3.length; b2++) {
/* 2208 */       arrayOfDouble3[b2] = arrayOfDouble3[b2] * d2;
/* 2209 */       d2 *= 0.9996D;
/*      */     } 
/* 2211 */     double[] arrayOfDouble2 = arrayOfDouble3;
/*      */ 
/*      */     
/* 2214 */     for (c = Character.MIN_VALUE; c < arrayOfDouble2.length; c++)
/* 2215 */       arrayOfDouble1[c] = arrayOfDouble1[c] + arrayOfDouble2[c] * 0.5D; 
/* 2216 */     for (c = Character.MIN_VALUE; c < '\005'; c++) {
/* 2217 */       arrayOfDouble1[c] = arrayOfDouble1[c] * c / 5.0D;
/*      */     }
/* 2219 */     normalize(arrayOfDouble1, 0.99D);
/*      */     
/* 2221 */     SF2Sample sF2Sample = newSimpleDrumSample(paramSF2Soundbank, "Tom", arrayOfDouble1);
/* 2222 */     sF2Sample.setOriginalPitch(50);
/*      */     
/* 2224 */     SF2Layer sF2Layer = new SF2Layer(paramSF2Soundbank);
/* 2225 */     sF2Layer.setName("Tom");
/*      */     
/* 2227 */     SF2GlobalRegion sF2GlobalRegion = new SF2GlobalRegion();
/* 2228 */     sF2Layer.setGlobalZone(sF2GlobalRegion);
/* 2229 */     paramSF2Soundbank.addResource(sF2Layer);
/*      */     
/* 2231 */     SF2LayerRegion sF2LayerRegion = new SF2LayerRegion();
/* 2232 */     sF2LayerRegion.putInteger(38, 12000);
/*      */     
/* 2234 */     sF2LayerRegion.putInteger(48, -100);
/* 2235 */     sF2LayerRegion.setSample(sF2Sample);
/* 2236 */     sF2Layer.getRegions().add(sF2LayerRegion);
/*      */     
/* 2238 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SF2Layer new_closed_hihat(SF2Soundbank paramSF2Soundbank) {
/* 2246 */     char c = '䀀';
/* 2247 */     double[] arrayOfDouble2 = new double[2 * c];
/* 2248 */     Random random = new Random(3049912L); int i;
/* 2249 */     for (i = 0; i < arrayOfDouble2.length; i += 2)
/* 2250 */       arrayOfDouble2[i] = 2.0D * (random.nextDouble() - 0.5D) * 0.1D; 
/* 2251 */     fft(arrayOfDouble2);
/*      */     
/* 2253 */     for (i = c / 2; i < arrayOfDouble2.length; i++)
/* 2254 */       arrayOfDouble2[i] = 0.0D; 
/* 2255 */     for (i = 4096; i < 8192; i++)
/* 2256 */       arrayOfDouble2[i] = 1.0D - (i - 4096) / 4096.0D; 
/* 2257 */     for (i = 0; i < 2048; i++)
/* 2258 */       arrayOfDouble2[i] = 0.2D + 0.8D * i / 2048.0D; 
/* 2259 */     randomPhase(arrayOfDouble2, new Random(3049912L));
/* 2260 */     ifft(arrayOfDouble2);
/* 2261 */     normalize(arrayOfDouble2, 0.9D);
/* 2262 */     arrayOfDouble2 = realPart(arrayOfDouble2);
/* 2263 */     double d = 1.0D;
/* 2264 */     for (byte b = 0; b < arrayOfDouble2.length; b++) {
/* 2265 */       arrayOfDouble2[b] = arrayOfDouble2[b] * d;
/* 2266 */       d *= 0.9996D;
/*      */     } 
/* 2268 */     double[] arrayOfDouble1 = arrayOfDouble2;
/*      */ 
/*      */     
/* 2271 */     for (c = Character.MIN_VALUE; c < '\005'; c++)
/* 2272 */       arrayOfDouble1[c] = arrayOfDouble1[c] * c / 5.0D; 
/* 2273 */     SF2Sample sF2Sample = newSimpleDrumSample(paramSF2Soundbank, "Closed Hi-Hat", arrayOfDouble1);
/*      */     
/* 2275 */     SF2Layer sF2Layer = new SF2Layer(paramSF2Soundbank);
/* 2276 */     sF2Layer.setName("Closed Hi-Hat");
/*      */     
/* 2278 */     SF2GlobalRegion sF2GlobalRegion = new SF2GlobalRegion();
/* 2279 */     sF2Layer.setGlobalZone(sF2GlobalRegion);
/* 2280 */     paramSF2Soundbank.addResource(sF2Layer);
/*      */     
/* 2282 */     SF2LayerRegion sF2LayerRegion = new SF2LayerRegion();
/* 2283 */     sF2LayerRegion.putInteger(38, 12000);
/* 2284 */     sF2LayerRegion.putInteger(56, 0);
/* 2285 */     sF2LayerRegion.putInteger(57, 1);
/* 2286 */     sF2LayerRegion.setSample(sF2Sample);
/* 2287 */     sF2Layer.getRegions().add(sF2LayerRegion);
/*      */     
/* 2289 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SF2Layer new_open_hihat(SF2Soundbank paramSF2Soundbank) {
/* 2295 */     char c = '䀀';
/* 2296 */     double[] arrayOfDouble2 = new double[2 * c];
/* 2297 */     Random random = new Random(3049912L); int i;
/* 2298 */     for (i = 0; i < arrayOfDouble2.length; i += 2)
/* 2299 */       arrayOfDouble2[i] = 2.0D * (random.nextDouble() - 0.5D); 
/* 2300 */     for (i = c / 2; i < arrayOfDouble2.length; i++)
/* 2301 */       arrayOfDouble2[i] = 0.0D; 
/* 2302 */     for (i = 0; i < 200; i++)
/* 2303 */       arrayOfDouble2[i] = 0.0D; 
/* 2304 */     for (i = 0; i < 8192; i++) {
/* 2305 */       double d = i / 8192.0D;
/* 2306 */       arrayOfDouble2[i] = d;
/*      */     } 
/* 2308 */     double[] arrayOfDouble1 = arrayOfDouble2;
/*      */ 
/*      */     
/* 2311 */     SF2Sample sF2Sample = newSimpleFFTSample(paramSF2Soundbank, "Open Hi-Hat", arrayOfDouble1, 1000.0D, 5);
/*      */     
/* 2313 */     SF2Layer sF2Layer = new SF2Layer(paramSF2Soundbank);
/* 2314 */     sF2Layer.setName("Open Hi-Hat");
/*      */     
/* 2316 */     SF2GlobalRegion sF2GlobalRegion = new SF2GlobalRegion();
/* 2317 */     sF2Layer.setGlobalZone(sF2GlobalRegion);
/* 2318 */     paramSF2Soundbank.addResource(sF2Layer);
/*      */     
/* 2320 */     SF2LayerRegion sF2LayerRegion = new SF2LayerRegion();
/* 2321 */     sF2LayerRegion.putInteger(36, 1500);
/* 2322 */     sF2LayerRegion.putInteger(54, 1);
/* 2323 */     sF2LayerRegion.putInteger(38, 1500);
/* 2324 */     sF2LayerRegion.putInteger(37, 1000);
/* 2325 */     sF2LayerRegion.putInteger(56, 0);
/* 2326 */     sF2LayerRegion.putInteger(57, 1);
/* 2327 */     sF2LayerRegion.setSample(sF2Sample);
/* 2328 */     sF2Layer.getRegions().add(sF2LayerRegion);
/*      */     
/* 2330 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SF2Layer new_crash_cymbal(SF2Soundbank paramSF2Soundbank) {
/* 2336 */     char c = '䀀';
/* 2337 */     double[] arrayOfDouble2 = new double[2 * c];
/* 2338 */     Random random = new Random(3049912L); int i;
/* 2339 */     for (i = 0; i < arrayOfDouble2.length; i += 2)
/* 2340 */       arrayOfDouble2[i] = 2.0D * (random.nextDouble() - 0.5D); 
/* 2341 */     for (i = c / 2; i < arrayOfDouble2.length; i++)
/* 2342 */       arrayOfDouble2[i] = 0.0D; 
/* 2343 */     for (i = 0; i < 100; i++)
/* 2344 */       arrayOfDouble2[i] = 0.0D; 
/* 2345 */     for (i = 0; i < 1024; i++) {
/* 2346 */       double d = i / 1024.0D;
/* 2347 */       arrayOfDouble2[i] = d;
/*      */     } 
/* 2349 */     double[] arrayOfDouble1 = arrayOfDouble2;
/*      */ 
/*      */     
/* 2352 */     SF2Sample sF2Sample = newSimpleFFTSample(paramSF2Soundbank, "Crash Cymbal", arrayOfDouble1, 1000.0D, 5);
/*      */     
/* 2354 */     SF2Layer sF2Layer = new SF2Layer(paramSF2Soundbank);
/* 2355 */     sF2Layer.setName("Crash Cymbal");
/*      */     
/* 2357 */     SF2GlobalRegion sF2GlobalRegion = new SF2GlobalRegion();
/* 2358 */     sF2Layer.setGlobalZone(sF2GlobalRegion);
/* 2359 */     paramSF2Soundbank.addResource(sF2Layer);
/*      */     
/* 2361 */     SF2LayerRegion sF2LayerRegion = new SF2LayerRegion();
/* 2362 */     sF2LayerRegion.putInteger(36, 1800);
/* 2363 */     sF2LayerRegion.putInteger(54, 1);
/* 2364 */     sF2LayerRegion.putInteger(38, 1800);
/* 2365 */     sF2LayerRegion.putInteger(37, 1000);
/* 2366 */     sF2LayerRegion.putInteger(56, 0);
/* 2367 */     sF2LayerRegion.setSample(sF2Sample);
/* 2368 */     sF2Layer.getRegions().add(sF2LayerRegion);
/*      */     
/* 2370 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SF2Layer new_side_stick(SF2Soundbank paramSF2Soundbank) {
/* 2378 */     char c = '䀀';
/* 2379 */     double[] arrayOfDouble2 = new double[2 * c];
/* 2380 */     Random random = new Random(3049912L); int i;
/* 2381 */     for (i = 0; i < arrayOfDouble2.length; i += 2)
/* 2382 */       arrayOfDouble2[i] = 2.0D * (random.nextDouble() - 0.5D) * 0.1D; 
/* 2383 */     fft(arrayOfDouble2);
/*      */     
/* 2385 */     for (i = c / 2; i < arrayOfDouble2.length; i++)
/* 2386 */       arrayOfDouble2[i] = 0.0D; 
/* 2387 */     for (i = 4096; i < 8192; i++)
/* 2388 */       arrayOfDouble2[i] = 1.0D - (i - 4096) / 4096.0D; 
/* 2389 */     for (i = 0; i < 200; i++) {
/* 2390 */       double d1 = 1.0D - i / 200.0D;
/* 2391 */       arrayOfDouble2[i] = arrayOfDouble2[i] * (1.0D + 20.0D * d1 * d1);
/*      */     } 
/* 2393 */     for (i = 0; i < 30; i++)
/* 2394 */       arrayOfDouble2[i] = 0.0D; 
/* 2395 */     randomPhase(arrayOfDouble2, new Random(3049912L));
/* 2396 */     ifft(arrayOfDouble2);
/* 2397 */     normalize(arrayOfDouble2, 0.9D);
/* 2398 */     arrayOfDouble2 = realPart(arrayOfDouble2);
/* 2399 */     double d = 1.0D;
/* 2400 */     for (byte b = 0; b < arrayOfDouble2.length; b++) {
/* 2401 */       arrayOfDouble2[b] = arrayOfDouble2[b] * d;
/* 2402 */       d *= 0.9996D;
/*      */     } 
/* 2404 */     double[] arrayOfDouble1 = arrayOfDouble2;
/*      */ 
/*      */     
/* 2407 */     for (c = Character.MIN_VALUE; c < '\n'; c++) {
/* 2408 */       arrayOfDouble1[c] = arrayOfDouble1[c] * c / 10.0D;
/*      */     }
/* 2410 */     SF2Sample sF2Sample = newSimpleDrumSample(paramSF2Soundbank, "Side Stick", arrayOfDouble1);
/*      */     
/* 2412 */     SF2Layer sF2Layer = new SF2Layer(paramSF2Soundbank);
/* 2413 */     sF2Layer.setName("Side Stick");
/*      */     
/* 2415 */     SF2GlobalRegion sF2GlobalRegion = new SF2GlobalRegion();
/* 2416 */     sF2Layer.setGlobalZone(sF2GlobalRegion);
/* 2417 */     paramSF2Soundbank.addResource(sF2Layer);
/*      */     
/* 2419 */     SF2LayerRegion sF2LayerRegion = new SF2LayerRegion();
/* 2420 */     sF2LayerRegion.putInteger(38, 12000);
/* 2421 */     sF2LayerRegion.putInteger(56, 0);
/* 2422 */     sF2LayerRegion.putInteger(48, -50);
/* 2423 */     sF2LayerRegion.setSample(sF2Sample);
/* 2424 */     sF2Layer.getRegions().add(sF2LayerRegion);
/*      */     
/* 2426 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SF2Sample newSimpleFFTSample(SF2Soundbank paramSF2Soundbank, String paramString, double[] paramArrayOfdouble, double paramDouble) {
/* 2432 */     return newSimpleFFTSample(paramSF2Soundbank, paramString, paramArrayOfdouble, paramDouble, 10);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SF2Sample newSimpleFFTSample(SF2Soundbank paramSF2Soundbank, String paramString, double[] paramArrayOfdouble, double paramDouble, int paramInt) {
/* 2438 */     int i = paramArrayOfdouble.length / 2;
/* 2439 */     AudioFormat audioFormat = new AudioFormat(44100.0F, 16, 1, true, false);
/* 2440 */     double d1 = paramDouble / i * audioFormat.getSampleRate() * 0.5D;
/*      */     
/* 2442 */     randomPhase(paramArrayOfdouble);
/* 2443 */     ifft(paramArrayOfdouble);
/* 2444 */     paramArrayOfdouble = realPart(paramArrayOfdouble);
/* 2445 */     normalize(paramArrayOfdouble, 0.9D);
/* 2446 */     float[] arrayOfFloat = toFloat(paramArrayOfdouble);
/* 2447 */     arrayOfFloat = loopExtend(arrayOfFloat, arrayOfFloat.length + 512);
/* 2448 */     fadeUp(arrayOfFloat, paramInt);
/* 2449 */     byte[] arrayOfByte = toBytes(arrayOfFloat, audioFormat);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2454 */     SF2Sample sF2Sample = new SF2Sample(paramSF2Soundbank);
/* 2455 */     sF2Sample.setName(paramString);
/* 2456 */     sF2Sample.setData(arrayOfByte);
/* 2457 */     sF2Sample.setStartLoop(256L);
/* 2458 */     sF2Sample.setEndLoop((i + 256));
/* 2459 */     sF2Sample.setSampleRate((long)audioFormat.getSampleRate());
/*      */     
/* 2461 */     double d2 = 81.0D + 12.0D * Math.log(d1 / 440.0D) / Math.log(2.0D);
/* 2462 */     sF2Sample.setOriginalPitch((int)d2);
/* 2463 */     sF2Sample.setPitchCorrection((byte)(int)(-(d2 - (int)d2) * 100.0D));
/* 2464 */     paramSF2Soundbank.addResource(sF2Sample);
/*      */     
/* 2466 */     return sF2Sample;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SF2Sample newSimpleFFTSample_dist(SF2Soundbank paramSF2Soundbank, String paramString, double[] paramArrayOfdouble, double paramDouble1, double paramDouble2) {
/* 2472 */     int i = paramArrayOfdouble.length / 2;
/* 2473 */     AudioFormat audioFormat = new AudioFormat(44100.0F, 16, 1, true, false);
/* 2474 */     double d1 = paramDouble1 / i * audioFormat.getSampleRate() * 0.5D;
/*      */     
/* 2476 */     randomPhase(paramArrayOfdouble);
/* 2477 */     ifft(paramArrayOfdouble);
/* 2478 */     paramArrayOfdouble = realPart(paramArrayOfdouble);
/*      */     
/* 2480 */     for (byte b = 0; b < paramArrayOfdouble.length; b++) {
/* 2481 */       paramArrayOfdouble[b] = (1.0D - Math.exp(-Math.abs(paramArrayOfdouble[b] * paramDouble2))) * 
/* 2482 */         Math.signum(paramArrayOfdouble[b]);
/*      */     }
/*      */     
/* 2485 */     normalize(paramArrayOfdouble, 0.9D);
/* 2486 */     float[] arrayOfFloat = toFloat(paramArrayOfdouble);
/* 2487 */     arrayOfFloat = loopExtend(arrayOfFloat, arrayOfFloat.length + 512);
/* 2488 */     fadeUp(arrayOfFloat, 80);
/* 2489 */     byte[] arrayOfByte = toBytes(arrayOfFloat, audioFormat);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2494 */     SF2Sample sF2Sample = new SF2Sample(paramSF2Soundbank);
/* 2495 */     sF2Sample.setName(paramString);
/* 2496 */     sF2Sample.setData(arrayOfByte);
/* 2497 */     sF2Sample.setStartLoop(256L);
/* 2498 */     sF2Sample.setEndLoop((i + 256));
/* 2499 */     sF2Sample.setSampleRate((long)audioFormat.getSampleRate());
/*      */     
/* 2501 */     double d2 = 81.0D + 12.0D * Math.log(d1 / 440.0D) / Math.log(2.0D);
/* 2502 */     sF2Sample.setOriginalPitch((int)d2);
/* 2503 */     sF2Sample.setPitchCorrection((byte)(int)(-(d2 - (int)d2) * 100.0D));
/* 2504 */     paramSF2Soundbank.addResource(sF2Sample);
/*      */     
/* 2506 */     return sF2Sample;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SF2Sample newSimpleDrumSample(SF2Soundbank paramSF2Soundbank, String paramString, double[] paramArrayOfdouble) {
/* 2512 */     int i = paramArrayOfdouble.length;
/* 2513 */     AudioFormat audioFormat = new AudioFormat(44100.0F, 16, 1, true, false);
/*      */     
/* 2515 */     byte[] arrayOfByte = toBytes(toFloat(realPart(paramArrayOfdouble)), audioFormat);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2520 */     SF2Sample sF2Sample = new SF2Sample(paramSF2Soundbank);
/* 2521 */     sF2Sample.setName(paramString);
/* 2522 */     sF2Sample.setData(arrayOfByte);
/* 2523 */     sF2Sample.setStartLoop(256L);
/* 2524 */     sF2Sample.setEndLoop((i + 256));
/* 2525 */     sF2Sample.setSampleRate((long)audioFormat.getSampleRate());
/* 2526 */     sF2Sample.setOriginalPitch(60);
/* 2527 */     paramSF2Soundbank.addResource(sF2Sample);
/*      */     
/* 2529 */     return sF2Sample;
/*      */   }
/*      */   
/*      */   public static SF2Layer newLayer(SF2Soundbank paramSF2Soundbank, String paramString, SF2Sample paramSF2Sample) {
/* 2533 */     SF2LayerRegion sF2LayerRegion = new SF2LayerRegion();
/* 2534 */     sF2LayerRegion.setSample(paramSF2Sample);
/*      */     
/* 2536 */     SF2Layer sF2Layer = new SF2Layer(paramSF2Soundbank);
/* 2537 */     sF2Layer.setName(paramString);
/* 2538 */     sF2Layer.getRegions().add(sF2LayerRegion);
/* 2539 */     paramSF2Soundbank.addResource(sF2Layer);
/*      */     
/* 2541 */     return sF2Layer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SF2Instrument newInstrument(SF2Soundbank paramSF2Soundbank, String paramString, Patch paramPatch, SF2Layer... paramVarArgs) {
/* 2550 */     SF2Instrument sF2Instrument = new SF2Instrument(paramSF2Soundbank);
/* 2551 */     sF2Instrument.setPatch(paramPatch);
/* 2552 */     sF2Instrument.setName(paramString);
/* 2553 */     paramSF2Soundbank.addInstrument(sF2Instrument);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2558 */     for (byte b = 0; b < paramVarArgs.length; b++) {
/* 2559 */       SF2InstrumentRegion sF2InstrumentRegion = new SF2InstrumentRegion();
/* 2560 */       sF2InstrumentRegion.setLayer(paramVarArgs[b]);
/* 2561 */       sF2Instrument.getRegions().add(sF2InstrumentRegion);
/*      */     } 
/*      */     
/* 2564 */     return sF2Instrument;
/*      */   }
/*      */   
/*      */   public static void ifft(double[] paramArrayOfdouble) {
/* 2568 */     (new FFT(paramArrayOfdouble.length / 2, 1)).transform(paramArrayOfdouble);
/*      */   }
/*      */   
/*      */   public static void fft(double[] paramArrayOfdouble) {
/* 2572 */     (new FFT(paramArrayOfdouble.length / 2, -1)).transform(paramArrayOfdouble);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void complexGaussianDist(double[] paramArrayOfdouble, double paramDouble1, double paramDouble2, double paramDouble3) {
/* 2577 */     for (byte b = 0; b < paramArrayOfdouble.length / 4; b++) {
/* 2578 */       paramArrayOfdouble[b * 2] = paramArrayOfdouble[b * 2] + paramDouble3 * 1.0D / paramDouble2 * Math.sqrt(6.283185307179586D) * 
/* 2579 */         Math.exp(-0.5D * Math.pow((b - paramDouble1) / paramDouble2, 2.0D));
/*      */     }
/*      */   }
/*      */   
/*      */   public static void randomPhase(double[] paramArrayOfdouble) {
/* 2584 */     for (byte b = 0; b < paramArrayOfdouble.length; b += 2) {
/* 2585 */       double d1 = Math.random() * 2.0D * Math.PI;
/* 2586 */       double d2 = paramArrayOfdouble[b];
/* 2587 */       paramArrayOfdouble[b] = Math.sin(d1) * d2;
/* 2588 */       paramArrayOfdouble[b + 1] = Math.cos(d1) * d2;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void randomPhase(double[] paramArrayOfdouble, Random paramRandom) {
/* 2593 */     for (byte b = 0; b < paramArrayOfdouble.length; b += 2) {
/* 2594 */       double d1 = paramRandom.nextDouble() * 2.0D * Math.PI;
/* 2595 */       double d2 = paramArrayOfdouble[b];
/* 2596 */       paramArrayOfdouble[b] = Math.sin(d1) * d2;
/* 2597 */       paramArrayOfdouble[b + 1] = Math.cos(d1) * d2;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void normalize(double[] paramArrayOfdouble, double paramDouble) {
/* 2602 */     double d1 = 0.0D;
/* 2603 */     for (byte b1 = 0; b1 < paramArrayOfdouble.length; b1++) {
/* 2604 */       if (paramArrayOfdouble[b1] > d1)
/* 2605 */         d1 = paramArrayOfdouble[b1]; 
/* 2606 */       if (-paramArrayOfdouble[b1] > d1)
/* 2607 */         d1 = -paramArrayOfdouble[b1]; 
/*      */     } 
/* 2609 */     if (d1 == 0.0D)
/*      */       return; 
/* 2611 */     double d2 = paramDouble / d1;
/* 2612 */     for (byte b2 = 0; b2 < paramArrayOfdouble.length; b2++)
/* 2613 */       paramArrayOfdouble[b2] = paramArrayOfdouble[b2] * d2; 
/*      */   }
/*      */   
/*      */   public static void normalize(float[] paramArrayOffloat, double paramDouble) {
/* 2617 */     double d1 = 0.5D;
/* 2618 */     for (byte b1 = 0; b1 < paramArrayOffloat.length; b1++) {
/* 2619 */       if (paramArrayOffloat[b1 * 2] > d1)
/* 2620 */         d1 = paramArrayOffloat[b1 * 2]; 
/* 2621 */       if (-paramArrayOffloat[b1 * 2] > d1)
/* 2622 */         d1 = -paramArrayOffloat[b1 * 2]; 
/*      */     } 
/* 2624 */     double d2 = paramDouble / d1;
/* 2625 */     for (byte b2 = 0; b2 < paramArrayOffloat.length; b2++)
/* 2626 */       paramArrayOffloat[b2 * 2] = (float)(paramArrayOffloat[b2 * 2] * d2); 
/*      */   }
/*      */   
/*      */   public static double[] realPart(double[] paramArrayOfdouble) {
/* 2630 */     double[] arrayOfDouble = new double[paramArrayOfdouble.length / 2];
/* 2631 */     for (byte b = 0; b < arrayOfDouble.length; b++) {
/* 2632 */       arrayOfDouble[b] = paramArrayOfdouble[b * 2];
/*      */     }
/* 2634 */     return arrayOfDouble;
/*      */   }
/*      */   
/*      */   public static double[] imgPart(double[] paramArrayOfdouble) {
/* 2638 */     double[] arrayOfDouble = new double[paramArrayOfdouble.length / 2];
/* 2639 */     for (byte b = 0; b < arrayOfDouble.length; b++) {
/* 2640 */       arrayOfDouble[b] = paramArrayOfdouble[b * 2];
/*      */     }
/* 2642 */     return arrayOfDouble;
/*      */   }
/*      */   
/*      */   public static float[] toFloat(double[] paramArrayOfdouble) {
/* 2646 */     float[] arrayOfFloat = new float[paramArrayOfdouble.length];
/* 2647 */     for (byte b = 0; b < arrayOfFloat.length; b++) {
/* 2648 */       arrayOfFloat[b] = (float)paramArrayOfdouble[b];
/*      */     }
/* 2650 */     return arrayOfFloat;
/*      */   }
/*      */   
/*      */   public static byte[] toBytes(float[] paramArrayOffloat, AudioFormat paramAudioFormat) {
/* 2654 */     byte[] arrayOfByte = new byte[paramArrayOffloat.length * paramAudioFormat.getFrameSize()];
/* 2655 */     return AudioFloatConverter.getConverter(paramAudioFormat).toByteArray(paramArrayOffloat, arrayOfByte);
/*      */   }
/*      */   
/*      */   public static void fadeUp(double[] paramArrayOfdouble, int paramInt) {
/* 2659 */     double d = paramInt;
/* 2660 */     for (byte b = 0; b < paramInt; b++)
/* 2661 */       paramArrayOfdouble[b] = paramArrayOfdouble[b] * b / d; 
/*      */   }
/*      */   
/*      */   public static void fadeUp(float[] paramArrayOffloat, int paramInt) {
/* 2665 */     double d = paramInt;
/* 2666 */     for (byte b = 0; b < paramInt; b++)
/* 2667 */       paramArrayOffloat[b] = (float)(paramArrayOffloat[b] * b / d); 
/*      */   }
/*      */   
/*      */   public static double[] loopExtend(double[] paramArrayOfdouble, int paramInt) {
/* 2671 */     double[] arrayOfDouble = new double[paramInt];
/* 2672 */     int i = paramArrayOfdouble.length;
/* 2673 */     byte b1 = 0;
/* 2674 */     for (byte b2 = 0; b2 < arrayOfDouble.length; b2++) {
/* 2675 */       arrayOfDouble[b2] = paramArrayOfdouble[b1];
/* 2676 */       b1++;
/* 2677 */       if (b1 == i)
/* 2678 */         b1 = 0; 
/*      */     } 
/* 2680 */     return arrayOfDouble;
/*      */   }
/*      */   
/*      */   public static float[] loopExtend(float[] paramArrayOffloat, int paramInt) {
/* 2684 */     float[] arrayOfFloat = new float[paramInt];
/* 2685 */     int i = paramArrayOffloat.length;
/* 2686 */     byte b1 = 0;
/* 2687 */     for (byte b2 = 0; b2 < arrayOfFloat.length; b2++) {
/* 2688 */       arrayOfFloat[b2] = paramArrayOffloat[b1];
/* 2689 */       b1++;
/* 2690 */       if (b1 == i)
/* 2691 */         b1 = 0; 
/*      */     } 
/* 2693 */     return arrayOfFloat;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/EmergencySoundbank.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */