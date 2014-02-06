package com.atlascopco.hunspell;

import org.bridj.BridJ;
import org.bridj.CRuntime;
import org.bridj.Pointer;
import org.bridj.ann.Library;
import org.bridj.ann.Runtime;

/**
 * Wrapper for library <b>hunspell</b><br>
 * This file was autogenerated by <a
 * href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a
 * href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few
 * opensource projects.</a>.<br>
 * For help, please visit <a
 * href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a
 * href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("hunspell")
@Runtime(CRuntime.class)
public class HunspellLibrary {
	static {
		BridJ.register();
	}

	public static final int MAXWORDUTF8LEN = 256;

	/**
	 * <p>This will instantiate the Hunspell object and return a handle to it.</p>
	 * Original signature :
	 * <code>Hunhandle* Hunspell_create(const char*, const char*)</code><br>
	 * <i>native declaration : line 12</i>
	 * @param affpath the path to the affix file
	 * @param dpath the path to the dictionary file
	 */
	public static native Pointer<HunspellLibrary.Hunhandle> Hunspell_create(
			Pointer<Byte> affpath, Pointer<Byte> dpath);

	/**
	 * <p>This constructor must be used if the dictionary/affix files were compressed/encrypted
	 * using the Hunspell hzip program.</p>
	 * Original signature :
	 * <code>Hunhandle* Hunspell_create_key(const char*, const char*, const char*)</code>
	 * <br>
	 * <i>native declaration : line 14</i>
	 * @param affpath the path to the affix file
	 * @param dpath the path to the dictionary file
	 * @param key the key to decrypt encrypted dictionary files
	 */
	public static native Pointer<HunspellLibrary.Hunhandle> Hunspell_create_key(
			Pointer<Byte> affpath, Pointer<Byte> dpath, Pointer<Byte> key);

	/**
	 * <p>This calls the destructor on the Hunspell object.</p>
	 * Original signature : <code>void Hunspell_destroy(Hunhandle*)</code><br>
	 * <i>native declaration : line 17</i>
	 * @param pHunspell the handle on the hunspell object
	 */
	public static native void Hunspell_destroy(
			Pointer<HunspellLibrary.Hunhandle> pHunspell);

	/**
	 * <p>spellcheck word</p>
	 * Original signature :
	 * <code>int Hunspell_spell(Hunhandle*, const char*)</code><br>
	 * <i>native declaration : line 22</i>
	 * @param pHunspell the handle on the hunspell object
	 * @param word the word to check
	 * @return 0 if incorrect, anything else is correct
	 */
	public static native int Hunspell_spell(
			Pointer<HunspellLibrary.Hunhandle> pHunspell, Pointer<Byte> word);

	/**
	 * <p>This retrieves the encoding of the dictionary that the Hunspell instance
	 * was instantiated with.</p>
	 * Original signature :
	 * <code>char* Hunspell_get_dic_encoding(Hunhandle*)</code><br>
	 * <i>native declaration : line 24</i>
	 * @param pHunspell the handle on the hunspell object
	 */
	public static native Pointer<Byte> Hunspell_get_dic_encoding(
			Pointer<HunspellLibrary.Hunhandle> pHunspell);

	/**
	 * <p>search suggestions</p>
	 * Original signature :
	 * <code>int Hunspell_suggest(Hunhandle*, char***, const char*)</code><br>
	 * <i>native declaration : line 33</i>
	 * @param pHunspell the handle on the hunspell object
	 * @param slst an out param used to return the suggestions
	 * @param word the bad word that you need suggestions for
	 * @return the number of suggestions returned in the out param {@code slst}
	 */
	public static native int Hunspell_suggest(
			Pointer<HunspellLibrary.Hunhandle> pHunspell,
			Pointer<Pointer<Pointer<Byte>>> slst, Pointer<Byte> word);

	/**
	 * <p>morphological analysis of the word</p>
	 * Original signature :
	 * <code>int Hunspell_analyze(Hunhandle*, char***, const char*)</code><br>
	 * <i>native declaration : line 39</i>
	 * @param pHunspell the handle on the hunspell object
	 * @param slst an out param used to return the analysis of the word
	 * @param word the word to analyze
	 * @return the number of suggestions returned in the out param {@code slst}
	 */
	public static native int Hunspell_analyze(
			Pointer<HunspellLibrary.Hunhandle> pHunspell,
			Pointer<Pointer<Pointer<Byte>>> slst, Pointer<Byte> word);

	/**
	 * <p>stemmer function</p>
	 * Original signature :
	 * <code>int Hunspell_stem(Hunhandle*, char***, const char*)</code><br>
	 * <i>native declaration : line 43</i>
	 * @param pHunspell the handle on the hunspell object
	 * @param slst an out param used to return the possible stems of the word
	 * @param word the word to stem
	 * @return the number of suggestions returned in the out param {@code slst}
	 */
	public static native int Hunspell_stem(
			Pointer<HunspellLibrary.Hunhandle> pHunspell,
			Pointer<Pointer<Pointer<Byte>>> slst, Pointer<Byte> word);

	/**
	 * <p>get stems from a morph. analysis</p>
	 * Original signature :
	 * <code>int Hunspell_stem2(Hunhandle*, char***, char**, int)</code><br>
	 * <i>native declaration : line 52</i>
	 * @param pHunspell the handle on the hunspell object
	 * @param slst an out param used to return the suggestions
	 * @param desc the output from {@link #Hunspell_analyze(Pointer, Pointer, Pointer)} for the word you want to stem
	 * @param n the number of results in {@code desc}
	 * @return the number of suggestions returned in the out param {@code slst}
	 */
	public static native int Hunspell_stem2(
			Pointer<HunspellLibrary.Hunhandle> pHunspell,
			Pointer<Pointer<Pointer<Byte>>> slst, Pointer<Pointer<Byte>> desc,
			int n);

	/**
	 * <p>morphological generation by example(s)</p>
	 * Original signature :
	 * <code>int Hunspell_generate(Hunhandle*, char***, const char*, const char*)</code>
	 * <br>
	 * <i>native declaration : line 56</i>
	 * @param pHunspell the handle on the hunspell object
	 * @param slst an out param used to return the possible stems of the word
	 * @param word the word to generate for
	 * @param word2 the word to use as an example to generate with
	 * @return the number of suggestions returned in the out param {@code slst}
	 */
	public static native int Hunspell_generate(
			Pointer<HunspellLibrary.Hunhandle> pHunspell,
			Pointer<Pointer<Pointer<Byte>>> slst, Pointer<Byte> word,
			Pointer<Byte> word2);

	/**
	 * <p>generation by morph. description(s)</p>
	 * Original signature :
	 * <code>int Hunspell_generate2(Hunhandle*, char***, const char*, char**, int)</code>
	 * <br>
	 * <i>native declaration : line 67</i>
	 * @param pHunspell the handle on the hunspell object
	 * @param slst an out param used to return the suggestions
	 * @param word the word to generate from
	 * @param desc the output from {@link #Hunspell_analyze(Pointer, Pointer, Pointer)} for the word you want to use as an example for generation
	 * @param n the number of results in {@code desc}
	 * @return the number of suggestions returned in the out param {@code slst}
	 */
	public static native int Hunspell_generate2(
			Pointer<HunspellLibrary.Hunhandle> pHunspell,
			Pointer<Pointer<Pointer<Byte>>> slst, Pointer<Byte> word,
			Pointer<Pointer<Byte>> desc, int n);

	/**
	 * <p>add word to the run-time dictionary</p>
	 * Original signature :
	 * <code>int Hunspell_add(Hunhandle*, const char*)</code><br>
	 * <i>native declaration : line 74</i>
	 * @param pHunspell the handle on the hunspell object
	 * @param word the word to add to the runtime dictionary
	 */
	public static native int Hunspell_add(
			Pointer<HunspellLibrary.Hunhandle> pHunspell, Pointer<Byte> word);

	/**
	 * <p>
	 * add word to the run-time dictionary with affix flags of the example (a
	 * dictionary word): Hunspell will recognize affixed forms of the new word,
	 * too.
	 * </p>
	 * Original signature:
	 * <code>int Hunspell_add_with_affix(Hunhandle*, const char*, const char*)</code>
	 * <br>
	 * <i>native declaration : line 81</i>
	 * @param pHunspell the handle on the hunspell object
	 * @param word the word to add to the runtime dictionary
	 * @param example the word to use as an example to figure out which affix flags apply to the added word
	 * @return non-zero if error occurs
	 */
	public static native int Hunspell_add_with_affix(
			Pointer<HunspellLibrary.Hunhandle> pHunspell, Pointer<Byte> word,
			Pointer<Byte> example);

	/**
	 * <p>remove word from the run-time dictionary</p>
	 * Original signature :
	 * <code>int Hunspell_remove(Hunhandle*, const char*)</code><br>
	 * <i>native declaration : line 85</i>
	 * @param pHunspell the handle on the hunspell object
	 * @param word the word to remove from the runtime dictionary
	 * @return non-zero if error occurs
	 */
	public static native int Hunspell_remove(
			Pointer<HunspellLibrary.Hunhandle> pHunspell, Pointer<Byte> word);

	/**
	 * <p>free suggestion lists</p>
	 * Original signature :
	 * <code>void Hunspell_free_list(Hunhandle*, char***, int)</code><br>
	 * <i>native declaration : line 89</i>
	 * @param pHunspell the handle on the hunspell object
	 * @param slst the returned list that you want to clear
	 * @param n the number of items in the list
	 */
	public static native void Hunspell_free_list(
			Pointer<HunspellLibrary.Hunhandle> pHunspell,
			Pointer<Pointer<Pointer<Byte>>> slst, int n);

	/**
	 * This interface is simply here to allow BridJ to maintain type-safety.
	 * 
	 * @author Thomas Joiner
	 */
	public static interface Hunhandle {}
}