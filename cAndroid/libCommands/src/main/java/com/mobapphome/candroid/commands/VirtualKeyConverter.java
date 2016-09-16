/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobapphome.candroid.commands;

import java.awt.event.KeyEvent;

/**
 *
 * @author SETTAR
 */

public class VirtualKeyConverter {

    static public VirtualKey charToVirtualKey(int character){

        VirtualKey vk = new VirtualKey();
        switch(character){

            //-------------Char keys--------------------------------------------------
            case '!': vk.VK = KeyEvent.VK_1; vk.shiftIsOn = true; break;
            case '"': vk.VK = KeyEvent.VK_QUOTE; vk.shiftIsOn = true; break;
            case '#': vk.VK = KeyEvent.VK_3; vk.shiftIsOn = true; break;
            case '$': vk.VK = KeyEvent.VK_4; vk.shiftIsOn = true; break;
            case '%': vk.VK = KeyEvent.VK_5; vk.shiftIsOn = true; break;//Shiftle
            case '&': vk.VK = KeyEvent.VK_7; vk.shiftIsOn = true; break;
            case '\'': vk.VK = KeyEvent.VK_QUOTE; break;
            case '(': vk.VK = KeyEvent.VK_9; vk.shiftIsOn = true; break;
            case ')': vk.VK = KeyEvent.VK_0; vk.shiftIsOn = true; break;
            case '*': vk.VK = KeyEvent.VK_8; vk.shiftIsOn = true; break;
            case '+': vk.VK = KeyEvent.VK_EQUALS; vk.shiftIsOn = true; break;
            case ',': vk.VK = KeyEvent.VK_COMMA; break;
            case '-': vk.VK = KeyEvent.VK_MINUS; break;
            case '.': vk.VK = KeyEvent.VK_PERIOD; break; //Bax Dot
            case '/': vk.VK = KeyEvent.VK_SLASH; break;

            case '0': vk.VK = KeyEvent.VK_O; break;
            case '1': vk.VK = KeyEvent.VK_1; break;
            case '2': vk.VK = KeyEvent.VK_2; break;
            case '3': vk.VK = KeyEvent.VK_3; break;
            case '4': vk.VK = KeyEvent.VK_4; break;
            case '5': vk.VK = KeyEvent.VK_5; break;
            case '6': vk.VK = KeyEvent.VK_6; break;
            case '7': vk.VK = KeyEvent.VK_7; break;
            case '8': vk.VK = KeyEvent.VK_8; break;
            case '9': vk.VK = KeyEvent.VK_9; break;

            case ':': vk.VK = KeyEvent.VK_SEMICOLON; vk.shiftIsOn = true; break;
            case ';': vk.VK = KeyEvent.VK_SEMICOLON; break;
            case '<': vk.VK = KeyEvent.VK_COMMA; vk.shiftIsOn = true; break;
            case '=': vk.VK = KeyEvent.VK_EQUALS; break;
            case '>': vk.VK = KeyEvent.VK_PERIOD; vk.shiftIsOn = true; break;
            case '?': vk.VK = KeyEvent.VK_SLASH; vk.shiftIsOn = true; break; //Shiftle
            case '@': vk.VK = KeyEvent.VK_2; vk.shiftIsOn = true; break;
            case 'A': vk.VK = KeyEvent.VK_A; vk.shiftIsOn = true; break;
            case 'B': vk.VK = KeyEvent.VK_B; vk.shiftIsOn = true; break;
            case 'C': vk.VK = KeyEvent.VK_C; vk.shiftIsOn = true; break;
            case 'D': vk.VK = KeyEvent.VK_D; vk.shiftIsOn = true; break;
            case 'E': vk.VK = KeyEvent.VK_E; vk.shiftIsOn = true; break;
            case 'F': vk.VK = KeyEvent.VK_F; vk.shiftIsOn = true; break;
            case 'G': vk.VK = KeyEvent.VK_G; vk.shiftIsOn = true; break;
            case 'H': vk.VK = KeyEvent.VK_H; vk.shiftIsOn = true; break;
            case 'I': vk.VK = KeyEvent.VK_I; vk.shiftIsOn = true; break;
            case 'J': vk.VK = KeyEvent.VK_J; vk.shiftIsOn = true; break;
            case 'K': vk.VK = KeyEvent.VK_K; vk.shiftIsOn = true; break;
            case 'L': vk.VK = KeyEvent.VK_L; vk.shiftIsOn = true; break;
            case 'M': vk.VK = KeyEvent.VK_M; vk.shiftIsOn = true; break;
            case 'N': vk.VK = KeyEvent.VK_N; vk.shiftIsOn = true; break;
            case 'O': vk.VK = KeyEvent.VK_O; vk.shiftIsOn = true; break;
            case 'P': vk.VK = KeyEvent.VK_P; vk.shiftIsOn = true; break;
            case 'Q': vk.VK = KeyEvent.VK_Q; vk.shiftIsOn = true; break;
            case 'R': vk.VK = KeyEvent.VK_R; vk.shiftIsOn = true; break;
            case 'S': vk.VK = KeyEvent.VK_S; vk.shiftIsOn = true; break;
            case 'T': vk.VK = KeyEvent.VK_T; vk.shiftIsOn = true; break;
            case 'U': vk.VK = KeyEvent.VK_U; vk.shiftIsOn = true; break;
            case 'V': vk.VK = KeyEvent.VK_V; vk.shiftIsOn = true; break;
            case 'W': vk.VK = KeyEvent.VK_W; vk.shiftIsOn = true; break;
            case 'X': vk.VK = KeyEvent.VK_X; vk.shiftIsOn = true; break;
            case 'Y': vk.VK = KeyEvent.VK_Y; vk.shiftIsOn = true; break;
            case 'Z': vk.VK = KeyEvent.VK_Z; vk.shiftIsOn = true; break;

            case '[': vk.VK = KeyEvent.VK_OPEN_BRACKET; break;
            case '\\': vk.VK = KeyEvent.VK_BACK_SLASH; break;
            case ']': vk.VK = KeyEvent.VK_CLOSE_BRACKET; break;
            case '^': vk.VK = KeyEvent.VK_6; vk.shiftIsOn = true; break;
            case '_': vk.VK = KeyEvent.VK_MINUS; vk.shiftIsOn = true; break;
            case '`': vk.VK = KeyEvent.VK_DEAD_GRAVE; break; //Bax

            case 'a': vk.VK = KeyEvent.VK_A; break;
            case 'b': vk.VK = KeyEvent.VK_B; break;
            case 'c': vk.VK = KeyEvent.VK_C; break;
            case 'd': vk.VK = KeyEvent.VK_D; break;
            case 'e': vk.VK = KeyEvent.VK_E; break;
            case 'f': vk.VK = KeyEvent.VK_F; break;
            case 'g': vk.VK = KeyEvent.VK_G; break;
            case 'h': vk.VK = KeyEvent.VK_H; break;
            case 'i': vk.VK = KeyEvent.VK_I; break;
            case 'j': vk.VK = KeyEvent.VK_J; break;
            case 'k': vk.VK = KeyEvent.VK_K; break;
            case 'l': vk.VK = KeyEvent.VK_L; break;
            case 'm': vk.VK = KeyEvent.VK_M; break;
            case 'n': vk.VK = KeyEvent.VK_N; break;
            case 'o': vk.VK = KeyEvent.VK_O; break;
            case 'p': vk.VK = KeyEvent.VK_P; break;
            case 'q': vk.VK = KeyEvent.VK_Q; break;
            case 'r': vk.VK = KeyEvent.VK_R; break;
            case 's': vk.VK = KeyEvent.VK_S; break;
            case 't': vk.VK = KeyEvent.VK_T; break;
            case 'u': vk.VK = KeyEvent.VK_U; break;
            case 'v': vk.VK = KeyEvent.VK_V; break;
            case 'w': vk.VK = KeyEvent.VK_W; break;
            case 'x': vk.VK = KeyEvent.VK_X; break;
            case 'y': vk.VK = KeyEvent.VK_Y; break;
            case 'z': vk.VK = KeyEvent.VK_Z; break;

            case '{': vk.VK = KeyEvent.VK_OPEN_BRACKET; vk.shiftIsOn = true; break; //Bax Shiftle
            case '|': vk.VK = KeyEvent.VK_BACK_SLASH; vk.shiftIsOn = true; break; //Bax Shiftle
            case '}': vk.VK = KeyEvent.VK_CLOSE_BRACKET; vk.shiftIsOn = true;  break; //Bax Shiftle
            case '~': vk.VK = KeyEvent.VK_DEAD_GRAVE; vk.shiftIsOn = true; break; //Bax Shiftle

            //-------------Non char keys--------------------------------------------------
            case -0x08: vk.VK = KeyEvent.VK_BACK_SPACE; break;
            case -0x09: vk.VK = KeyEvent.VK_TAB; break;
            case -0x0D: vk.VK = KeyEvent.VK_ENTER; break;
            case -0x10: vk.VK = KeyEvent.VK_SHIFT; break;
            case -0x11: vk.VK = KeyEvent.VK_CONTROL; break;
            case -0x12: vk.VK = KeyEvent.VK_ALT; break;
            case -0x13: vk.VK = KeyEvent.VK_PAUSE; break;
            case -0x14: vk.VK = KeyEvent.VK_CAPS_LOCK; break;
            case -0x1B: vk.VK = KeyEvent.VK_ESCAPE; break;
            case -0x20: vk.VK = KeyEvent.VK_SPACE; break;
            case -0x21: vk.VK = KeyEvent.VK_PAGE_UP; break;
            case -0x22: vk.VK = KeyEvent.VK_PAGE_DOWN; break;
            case -0x23: vk.VK = KeyEvent.VK_END; break;
            case -0x24: vk.VK = KeyEvent.VK_HOME; break;
            case -0x25: vk.VK = KeyEvent.VK_LEFT; break;
            case -0x26: vk.VK = KeyEvent.VK_UP; break;
            case -0x27: vk.VK = KeyEvent.VK_RIGHT; break;
            case -0x28: vk.VK = KeyEvent.VK_DOWN; break;
            case -0x2C: vk.VK = KeyEvent.VK_PRINTSCREEN; break;
            case -0x2D: vk.VK = KeyEvent.VK_INSERT; break;
            case -0x2E: vk.VK = KeyEvent.VK_DELETE; break;

            case -0x70: vk.VK = KeyEvent.VK_F1; break;
            case -0x71: vk.VK = KeyEvent.VK_F2; break;
            case -0x72: vk.VK = KeyEvent.VK_F3; break;
            case -0x73: vk.VK = KeyEvent.VK_F4; break;
            case -0x74: vk.VK = KeyEvent.VK_F5; break;
            case -0x75: vk.VK = KeyEvent.VK_F6; break;
            case -0x76: vk.VK = KeyEvent.VK_F7; break;
            case -0x77: vk.VK = KeyEvent.VK_F8; break;
            case -0x78: vk.VK = KeyEvent.VK_F9; break;
            case -0x79: vk.VK = KeyEvent.VK_F10; break;
            case -0x7A: vk.VK = KeyEvent.VK_F11; break;
            case -0x7B: vk.VK = KeyEvent.VK_F12; break;
            
            default: vk.VK = 0; break;
        }
        return vk;
    }
}
