/*
 *  Copyright 2018 Nikita Shakarun
 *  Copyright 2021 Yury Kharchenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package javax.microedition.lcdui.keyboard;

import android.util.SparseIntArray;
import android.view.KeyEvent;

import androidx.collection.SparseArrayCompat;

import ru.playsoftware.j2meloader.config.ProfileModel;

import static javax.microedition.lcdui.Canvas.*;

public class KeyMapper {
	private static final int DEFAULT_LAYOUT = 0;
	private static final int SIEMENS_LAYOUT = 1;
	private static final int MOTOROLA_LAYOUT = 2;
	private static final int CUSTOM_LAYOUT = 3;

	private static final int SIEMENS_KEY_UP = -59;
	private static final int SIEMENS_KEY_DOWN = -60;
	private static final int SIEMENS_KEY_LEFT = -61;
	private static final int SIEMENS_KEY_RIGHT = -62;
	private static final int SIEMENS_KEY_SOFT_LEFT = -1;
	private static final int SIEMENS_KEY_SOFT_RIGHT = -4;
	private static final int MOTOROLA_KEY_UP = -1;
	private static final int MOTOROLA_KEY_DOWN = -6;
	private static final int MOTOROLA_KEY_LEFT = -2;
	private static final int MOTOROLA_KEY_RIGHT = -5;
	private static final int MOTOROLA_KEY_FIRE = -20;
	private static final int MOTOROLA_KEY_SOFT_LEFT = -21;
	private static final int MOTOROLA_KEY_SOFT_RIGHT = -22;

	public static final int SE_KEY_SPECIAL_GAMING_A = -13;
	public static final int SE_KEY_SPECIAL_GAMING_B = -14;

	private static final SparseArrayCompat<String> keyCodeToKeyName = new SparseArrayCompat<>();
	private static final SparseIntArray keyCodeToCustom = new SparseIntArray();
	private static final SparseIntArray keyCodeToGameAction = new SparseIntArray();
	private static final SparseIntArray gameActionToKeyCode = new SparseIntArray();
	private static SparseIntArray androidToMIDP = new SparseIntArray();
	private static SparseIntArray SymToMIDP = new SparseIntArray(), CommToMIDP = new SparseIntArray();
	private static int layoutType;

	static {
		mapKeyCode(KEY_NUM0, 0, "0");
		mapKeyCode(KEY_NUM1, 0, "1");
		mapKeyCode(KEY_NUM2, UP, "2");
		mapKeyCode(KEY_NUM3, 0, "3");
		mapKeyCode(KEY_NUM4, LEFT, "4");
		mapKeyCode(KEY_NUM5, FIRE, "5");
		mapKeyCode(KEY_NUM6, RIGHT, "6");
		mapKeyCode(KEY_NUM7, GAME_A, "7");
		mapKeyCode(KEY_NUM8, DOWN, "8");
		mapKeyCode(KEY_NUM9, GAME_B, "9");
		mapKeyCode(KEY_STAR, GAME_C, "ASTERISK");
		mapKeyCode(KEY_POUND, GAME_D, "POUND");
		mapKeyCode(KEY_UP, UP, "UP");
		mapKeyCode(KEY_DOWN, DOWN, "DOWN");
		mapKeyCode(KEY_LEFT, LEFT, "LEFT");
		mapKeyCode(KEY_RIGHT, RIGHT, "RIGHT");
		mapKeyCode(KEY_FIRE, FIRE, "SELECT");
		mapKeyCode(KEY_SOFT_LEFT, 0, "SOFT1");
		mapKeyCode(KEY_SOFT_RIGHT, 0, "SOFT2");
		mapKeyCode(KEY_CLEAR, 0, "CLEAR");
		mapKeyCode(KEY_SEND, 0, "SEND");
		mapKeyCode(KEY_END, 0, "END");

		mapGameAction(UP, KEY_UP);
		mapGameAction(LEFT, KEY_LEFT);
		mapGameAction(RIGHT, KEY_RIGHT);
		mapGameAction(DOWN, KEY_DOWN);
		mapGameAction(FIRE, KEY_FIRE);
		mapGameAction(GAME_A, KEY_NUM7);
		mapGameAction(GAME_B, KEY_NUM9);
		mapGameAction(GAME_C, KEY_STAR);
		mapGameAction(GAME_D, KEY_POUND);

		SymToMIDP.put(KeyEvent.KEYCODE_SPACE, 32); //Space
		SymToMIDP.put(8, 33); //!
		SymToMIDP.put(KeyEvent.KEYCODE_APOSTROPHE, 34); //"
		SymToMIDP.put(KeyEvent.KEYCODE_POUND, 35); //#
		SymToMIDP.put(11, 36); //$
		SymToMIDP.put(12, 37); //%
		SymToMIDP.put(14, 38); //&
		SymToMIDP.put(16, 40); //(
		SymToMIDP.put(7, 41); //)
		SymToMIDP.put(KeyEvent.KEYCODE_STAR, 42); //*
		SymToMIDP.put(KeyEvent.KEYCODE_NUMPAD_ADD, 43); //+
		SymToMIDP.put(KeyEvent.KEYCODE_COMMA, 44); //,
		SymToMIDP.put(KeyEvent.KEYCODE_MINUS, 45); //-
		SymToMIDP.put(KeyEvent.KEYCODE_PERIOD, 46); //.
		SymToMIDP.put(KeyEvent.KEYCODE_SLASH, 47); // /
		SymToMIDP.put(KeyEvent.KEYCODE_SEMICOLON, 58); //:
		SymToMIDP.put(55, 60); //<
		SymToMIDP.put(56, 62); //>
		SymToMIDP.put(76, 63); //?
		SymToMIDP.put(KeyEvent.KEYCODE_AT, 64); //@
		SymToMIDP.put(KeyEvent.KEYCODE_GRAVE, 126); //~
		SymToMIDP.put(KeyEvent.KEYCODE_LEFT_BRACKET, 123); //{
		SymToMIDP.put(KeyEvent.KEYCODE_BACKSLASH, 124); // |
		SymToMIDP.put(KeyEvent.KEYCODE_RIGHT_BRACKET, 125); //}
		SymToMIDP.put(13, 94);//^
		SymToMIDP.put(69, 95);//_

		CommToMIDP.put(KeyEvent.KEYCODE_DEL, -8/*127*//*8*/); //Backspace

	}

	private static void remapKeys() {
		if (layoutType == SIEMENS_LAYOUT) {
			keyCodeToCustom.put(KEY_LEFT, SIEMENS_KEY_LEFT);
			keyCodeToCustom.put(KEY_RIGHT, SIEMENS_KEY_RIGHT);
			keyCodeToCustom.put(KEY_UP, SIEMENS_KEY_UP);
			keyCodeToCustom.put(KEY_DOWN, SIEMENS_KEY_DOWN);
			keyCodeToCustom.put(KEY_SOFT_LEFT, SIEMENS_KEY_SOFT_LEFT);
			keyCodeToCustom.put(KEY_SOFT_RIGHT, SIEMENS_KEY_SOFT_RIGHT);

			mapGameAction(LEFT, SIEMENS_KEY_LEFT);
			mapGameAction(RIGHT, SIEMENS_KEY_RIGHT);
			mapGameAction(UP, SIEMENS_KEY_UP);
			mapGameAction(DOWN, SIEMENS_KEY_DOWN);

			mapKeyCode(SIEMENS_KEY_UP, UP, "UP");
			mapKeyCode(SIEMENS_KEY_DOWN, DOWN, "DOWN");
			mapKeyCode(SIEMENS_KEY_LEFT, LEFT, "LEFT");
			mapKeyCode(SIEMENS_KEY_RIGHT, RIGHT, "RIGHT");
			mapKeyCode(SIEMENS_KEY_SOFT_LEFT, 0, "SOFT1");
			mapKeyCode(SIEMENS_KEY_SOFT_RIGHT, 0, "SOFT2");
		} else if (layoutType == MOTOROLA_LAYOUT) {
			keyCodeToCustom.put(KEY_UP, MOTOROLA_KEY_UP);
			keyCodeToCustom.put(KEY_DOWN, MOTOROLA_KEY_DOWN);
			keyCodeToCustom.put(KEY_LEFT, MOTOROLA_KEY_LEFT);
			keyCodeToCustom.put(KEY_RIGHT, MOTOROLA_KEY_RIGHT);
			keyCodeToCustom.put(KEY_FIRE, MOTOROLA_KEY_FIRE);
			keyCodeToCustom.put(KEY_SOFT_LEFT, MOTOROLA_KEY_SOFT_LEFT);
			keyCodeToCustom.put(KEY_SOFT_RIGHT, MOTOROLA_KEY_SOFT_RIGHT);

			mapGameAction(LEFT, MOTOROLA_KEY_LEFT);
			mapGameAction(RIGHT, MOTOROLA_KEY_RIGHT);
			mapGameAction(UP, MOTOROLA_KEY_UP);
			mapGameAction(DOWN, MOTOROLA_KEY_DOWN);
			mapGameAction(FIRE, MOTOROLA_KEY_FIRE);

			mapKeyCode(MOTOROLA_KEY_UP, UP, "UP");
			mapKeyCode(MOTOROLA_KEY_DOWN, DOWN, "DOWN");
			mapKeyCode(MOTOROLA_KEY_LEFT, LEFT, "LEFT");
			mapKeyCode(MOTOROLA_KEY_RIGHT, RIGHT, "RIGHT");
			mapKeyCode(MOTOROLA_KEY_FIRE, FIRE, "SELECT");
			mapKeyCode(MOTOROLA_KEY_SOFT_LEFT, 0, "SOFT1");
			mapKeyCode(MOTOROLA_KEY_SOFT_RIGHT, 0, "SOFT2");
		}
	}

	private static void mapKeyCode(int midpKeyCode, int gameAction, String keyName) {
		keyCodeToGameAction.put(midpKeyCode, gameAction);
		keyCodeToKeyName.put(midpKeyCode, keyName);
	}

	private static void mapGameAction(int gameAction, int keyCode) {
		gameActionToKeyCode.put(gameAction, keyCode);
	}

	public static int convertAndroidKeyCode(int keyCode) {
		return androidToMIDP.get(keyCode, Integer.MAX_VALUE);
	}

	public static int convertKeyCode(int keyCode) {
		if (layoutType == DEFAULT_LAYOUT) {
			return keyCode;
		}
		return keyCodeToCustom.get(keyCode, keyCode);
	}

	public static int SymToMIDP(int code){
		return SymToMIDP.get(code, Integer.MAX_VALUE);
	}

	public static int CommToMIDP(int code){
		return CommToMIDP.get(code, Integer.MAX_VALUE);
	}

	public static void setKeyMapping(ProfileModel params) {
		layoutType = params.keyCodesLayout;
		androidToMIDP = params.keyMappings;
		if (androidToMIDP == null) {
			androidToMIDP = getDefaultKeyMap();
		}
		remapKeys();
	}

	public static int getKeyCode(int gameAction) {
		return gameActionToKeyCode.get(gameAction, Integer.MAX_VALUE);
	}

	public static int getGameAction(int keyCode) {
		return keyCodeToGameAction.get(keyCode, Integer.MAX_VALUE);
	}

	public static String getKeyName(int keyCode) {
		String value = keyCodeToKeyName.get(keyCode, null);;
		if(value == null) return String.valueOf((char)keyCode);
		return value;
	}

	public static SparseIntArray getDefaultKeyMap() {
		SparseIntArray map = new SparseIntArray();
		map.put(KeyEvent.KEYCODE_0, KEY_NUM0);
		map.put(KeyEvent.KEYCODE_1, KEY_NUM1);
		map.put(KeyEvent.KEYCODE_2, KEY_NUM2);
		map.put(KeyEvent.KEYCODE_3, KEY_NUM3);
		map.put(KeyEvent.KEYCODE_4, KEY_NUM4);
		map.put(KeyEvent.KEYCODE_5, KEY_NUM5);
		map.put(KeyEvent.KEYCODE_6, KEY_NUM6);
		map.put(KeyEvent.KEYCODE_7, KEY_NUM7);
		map.put(KeyEvent.KEYCODE_8, KEY_NUM8);
		map.put(KeyEvent.KEYCODE_9, KEY_NUM9);
		map.put(KeyEvent.KEYCODE_STAR, KEY_STAR);
		map.put(KeyEvent.KEYCODE_POUND, KEY_POUND);
		map.put(KeyEvent.KEYCODE_DPAD_UP, KEY_UP);
		map.put(KeyEvent.KEYCODE_DPAD_DOWN, KEY_DOWN);
		map.put(KeyEvent.KEYCODE_DPAD_LEFT, KEY_LEFT);
		map.put(KeyEvent.KEYCODE_DPAD_RIGHT, KEY_RIGHT);
		map.put(KeyEvent.KEYCODE_ENTER, KEY_FIRE);
		map.put(KeyEvent.KEYCODE_SOFT_LEFT, KEY_SOFT_LEFT);
		map.put(KeyEvent.KEYCODE_SOFT_RIGHT, KEY_SOFT_RIGHT);
		map.put(KeyEvent.KEYCODE_CALL, KEY_SEND);
		map.put(KeyEvent.KEYCODE_ENDCALL, KEY_END);
		return map;
	}
}
