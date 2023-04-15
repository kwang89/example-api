package com.example.api.configuration.xss;

import org.apache.commons.text.StringEscapeUtils;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;

/**
 * The type Html character escapes.
 */
public class HtmlCharacterEscapes extends CharacterEscapes {

	private final int[] asciiEscapes;

	public HtmlCharacterEscapes() {
		this.asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();
		this.asciiEscapes['<'] = CharacterEscapes.ESCAPE_CUSTOM;
		this.asciiEscapes['>'] = CharacterEscapes.ESCAPE_CUSTOM;
		this.asciiEscapes['\"'] = CharacterEscapes.ESCAPE_CUSTOM;
		this.asciiEscapes['('] = CharacterEscapes.ESCAPE_CUSTOM;
		this.asciiEscapes[')'] = CharacterEscapes.ESCAPE_CUSTOM;
		this.asciiEscapes['#'] = CharacterEscapes.ESCAPE_CUSTOM;
		this.asciiEscapes['\''] = CharacterEscapes.ESCAPE_CUSTOM;
	}

	@Override
	public int[] getEscapeCodesForAscii() {
		return asciiEscapes;
	}

	@Override
	public SerializableString getEscapeSequence(int ch) {
		return new SerializedString(StringEscapeUtils.escapeHtml4(Character.toString((char)ch)));
	}
}
