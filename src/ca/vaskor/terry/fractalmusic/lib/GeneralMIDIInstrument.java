/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.lib;

/**
 *
 * @author tavaskor
 */
public enum GeneralMIDIInstrument {
    ACOUSTIC("Acoustic Grand Piano"),
    BRIGHT("Bright Acoustic Piano"),
    ELECGRAND("Electric Grand Piano"),
    HONKYTONK("Honky-tonk Piano"),
    ELECPIA1("Electric Piano 1"),
    ELECPIA2("Electric Piano 2"),
    HARPSICHORD("Harpsichord"),
    CLAVINET("Clavinet"),
    CELESTA("Celesta"),
    GLOCKENSPIEL("Glockenspiel"),
    MUSICBOX("Music Box"),
    VIBRAPHONE("Vibraphone"),
    MARIMBA("Marimba"),
    XYLOPHONE("Xylophone"),
    TUBULAR("Tubular Bells"),
    DULCIMER("Dulcimer"),
    DRAWBAR("Drawbar Organ"),
    PERCUSSIVE("Percussive Organ"),
    ROCK_ORGAN("Rock Organ"),
    CHURCH_ORGAN("Church Organ"),
    REED_ORGAN("Reed Organ"),
    ACCORDION("Accordion"),
    HARMONICA("Harmonica"),
    TANGO("Tango Accordion"),
    ACOUSTIC_NYLON("Acoustic Guitar (nylon)"),
    ACOUSTIC_STEEL("Acoustic Guitar (steel)"),
    ELECTRIC_JAZZ("Electric Guitar (jazz)"),
    ELECTRIC_CLEAN("Electric Guitar (clean)"),
    ELECTRIC_MUTED("Electric Guitar (muted)"),
    OVERDRIVEN("Overdriven Guitar"),
    DISTORTION("Distortion Guitar"),
    HARMONICS("Guitar Harmonics"),
    ACOUSTIC_BASS("Acoustic Bass"),
    ELECTRIC_FINGER("Electric Bass (finger"),
    ELECTRIC_PICK("Electric Bass (pick)"),
    FRETLESS("Fretless Bass"),
    SLAP1("Slap Bass 1"),
    SLAP2("Slap Bass 2"),
    SYNTH1("Synth Bass 1"),
    SYNTH2("Synth Bass 2"),
    VIOLIN("Violin"),
    VIOLA("Viola"),
    CELLO("Cello"),
    BASS("Contrabass"),
    TREMOLO("Tremolo Strings"),
    PIZZICATO("Pizzicato Strings"),
    HARP("Orchestral Harp"),
    TIMPANI("Timpani"),
    ENSEMBLE1("String Ensemble 1"),
    ENSEMBLE2("String Ensemble 2"),
    SYNTHSTR1("Synth Strings 1"),
    SYNTHSTR2("Synth Strings 2"),
    CHOIRAAH("Choir Aahs"),
    VOICSOOH("Voice Oohs"),
    SYNTHCHOIR("Synth Choir"),
    ORCH_HIT("Orchestra Hit"),
    TRUMPET("Trumpet"),
    TROMBONE("Trombone"),
    TUBA("Tuba"),
    MUTE_TRUMPET("Muted Trumpet"),
    FRENCH_HORN("French Horn"),
    BRASS("Brass Section"),
    SYNTH_BRASS1("Synth Brass 1"),
    SYNTH_BRASS2("Synth Brass 2"),
    SOPRANO_SAX("Soprano Sax"),
    ALTO_SAX("Alto Sax"),
    TENOR_SAX("Tenor Sax"),
    BARITONE_SAX("Baritone Sax"),
    OBOE("Oboe"),
    ENGLISH_HORN("English Horn"),
    BASSOON("Bassoon"),
    CLARINET("Clarinet"),
    PICCOLO("Piccolo"),
    FLUTE("Flute"),
    RECORDER("Recorder"),
    PANFLUTE("Pan Flute"),
    BOTTLE("Blown Bottle"),
    SHAKUHACHI("Shakuhachi"),
    WHISTLE("Whistle"),
    OCARINA("Ocarina"),
    LEAD1("Lead 1 (square)"),
    LEAD2("Lead 2 (sawtooth"),
    LEAD3("Lead 3 (calliope)"),
    LEAD4("Lead 4 (chiff)"),
    LEAD5("Lead 5 (charang)"),
    LEAD6("Lead 6 (voice)"),
    LEAD7("Lead 7 (fifths)"),
    LEAD8("Lead 8 (bass + lead)"),
    PAD1("Pad 1 (new age)"),
    PAD2("Pad 2 (warm)"),
    PAD3("Pad 3 (polysynth)"),
    PAD4("Pad 4 (choir)"),
    PAD5("Pad 5 (bowed)"),
    PAD6("Pad 6 (metallic)"),
    PAD7("Pad 7 (halo)"),
    PAD8("Pad 8 (sweep)"),
    FX1("FX 1 (rain)"),
    FX2("FX 2 (soundtrack)"),
    FX3("FX 3 (crystal)"),
    FX4("FX 4 (atmosphere)"),
    FX5("FX 5 (brightness)"),
    FX6("FX 6 (goblins)"),
    FX7("FX 7 (echoes)"),
    FX8("FX 8 (sci-fi)"),
    SITAR("Sitar"),
    BANJO("Banjo"),
    SHAMISEN("Shamisen"),
    KOTO("Koto"),
    KALIMBA("Kalimba"),
    BAGPIPE("Bagpipe"),
    FIDDLE("Fiddle"),
    SHANAI("Shanai"),
    BELL("Tinkle Bell"),
    AGOGO("Agogo"),
    STEEL_DRUM("Steel Drums"),
    WOODBLOCK("Woodblock"),
    TAIKO("Taiko Drum"),
    TOM("Melodic Tom"),
    SYNTH_DRUM("Synth Drum"),
    REVERSE_CYMBAL("Reverse Cymbal"),
    FRET("Guitar Fret Noise"),
    BREATH("Breath Noise"),
    SEASHORE("Seashore"),
    TWEET("Bird Tweet"),
    TELEPHONE("Telephone Ring"),
    HELICOPTER("Helicopter"),
    APPLAUSE("Applause"),
    GUNSHOT("Gunshot");
    
    GeneralMIDIInstrument(String name) { nm = name; }
    
    /**
     * 
     * @return A representation of the instrument consisting of its number
     *   according to the MIDI specification, a space, and its human-readable
     *   name.
     */
    @Override
    public String toString() { return ordinal() + " " + nm; }
    
    private String nm;
}
