package com.caoduchiep.escposprinter.charset;


import android.util.Log;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.IllegalCharsetNameException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Test charset decoding Java New I/O book.
 * Created and tested: Dec, 2001
 *
 * @author Ron Hitchens (ron@ronsoft.com)
 * @version $Id: CharsetDecode.java,v 1.4 2002/05/20 07:24:31 ron Exp $
 */
public class CharsetESP extends Charset {

    private boolean debug = false;

    // HashMap's used for encoding and decoding
    protected static HashMap<String, Byte> defaultEncodeMap = new HashMap<String, Byte>();
    protected static HashMap<Byte, String> defaultDecodeMap = new HashMap<Byte, String>();
    protected static HashMap<String, Byte> extEncodeMap = new HashMap<String, Byte>();
    protected static HashMap<Byte, String> extDecodeMap = new HashMap<Byte, String>();

    // Data to populate the hashmaps with
    public static final Object[][] gsmCharacters = {
            {"@", new Byte((byte) 0x00)},
            {"£", new Byte((byte) 0x01)},
            {"$", new Byte((byte) 0x02)},
            {"¥", new Byte((byte) 0x03)},
//            {"è", new Byte((byte) 0x04)},
//            {"é", new Byte((byte) 0x05)},
//            {"ù", new Byte((byte) 0x06)},
//            {"ì", new Byte((byte) 0x07)},
//            {"ò", new Byte((byte) 0x08)},
            {"Ç", new Byte((byte) 0x09)},
            {"\n", new Byte((byte) 0x0a)},
            {"Ø", new Byte((byte) 0x0b)},
            {"ø", new Byte((byte) 0x0c)},
            {"\r", new Byte((byte) 0x0d)},
            {"Å", new Byte((byte) 0x0e)},
            {"å", new Byte((byte) 0x0f)},
            {"\u0394", new Byte((byte) 0x10)},
            {"_", new Byte((byte) 0x11)},
            {"\u03A6", new Byte((byte) 0x12)},
            {"\u0393", new Byte((byte) 0x13)},
            {"\u039B", new Byte((byte) 0x14)},
            {"\u03A9", new Byte((byte) 0x15)},
            {"\u03A0", new Byte((byte) 0x16)},
            {"\u03A8", new Byte((byte) 0x17)},
            {"\u03A3", new Byte((byte) 0x18)},
            {"\u0398", new Byte((byte) 0x19)},
            {"\u039E", new Byte((byte) 0x1a)},
            {"\u001B", new Byte((byte) 0x1b)}, // 27 is Escape character
            {"Æ", new Byte((byte) 0x1c)},
            {"æ", new Byte((byte) 0x1d)},
            {"ß", new Byte((byte) 0x1e)},
            {"É", new Byte((byte) 0x1f)},
            {"\u0020", new Byte((byte) 0x20)},
            {"!", new Byte((byte) 0x21)},
            {"\"", new Byte((byte) 0x22)},
            {"#", new Byte((byte) 0x23)},
            {"¤", new Byte((byte) 0x24)},
            {"%", new Byte((byte) 0x25)},
            {"&", new Byte((byte) 0x26)},
            {"'", new Byte((byte) 0x27)},
            {"(", new Byte((byte) 0x28)},
            {")", new Byte((byte) 0x29)},
            {"*", new Byte((byte) 0x2a)},
            {"+", new Byte((byte) 0x2b)},
            {",", new Byte((byte) 0x2c)},
            {"-", new Byte((byte) 0x2d)},
            {".", new Byte((byte) 0x2e)},
            {"/", new Byte((byte) 0x2f)},
            {"0", new Byte((byte) 0x30)},
            {"1", new Byte((byte) 0x31)},
            {"2", new Byte((byte) 0x32)},
            {"3", new Byte((byte) 0x33)},
            {"4", new Byte((byte) 0x34)},
            {"5", new Byte((byte) 0x35)},
            {"6", new Byte((byte) 0x36)},
            {"7", new Byte((byte) 0x37)},
            {"8", new Byte((byte) 0x38)},
            {"9", new Byte((byte) 0x39)},
            {":", new Byte((byte) 0x3a)},
            {";", new Byte((byte) 0x3b)},
            {"<", new Byte((byte) 0x3c)},
            {"=", new Byte((byte) 0x3d)},
            {">", new Byte((byte) 0x3e)},
            {"?", new Byte((byte) 0x3f)},
            {"¡", new Byte((byte) 0x40)},
            {"A", new Byte((byte) 0x41)},
            {"B", new Byte((byte) 0x42)},
            {"C", new Byte((byte) 0x43)},
            {"D", new Byte((byte) 0x44)},
            {"E", new Byte((byte) 0x45)},
            {"F", new Byte((byte) 0x46)},
            {"G", new Byte((byte) 0x47)},
            {"H", new Byte((byte) 0x48)},
            {"I", new Byte((byte) 0x49)},
            {"J", new Byte((byte) 0x4a)},
            {"K", new Byte((byte) 0x4b)},
            {"L", new Byte((byte) 0x4c)},
            {"M", new Byte((byte) 0x4d)},
            {"N", new Byte((byte) 0x4e)},
            {"O", new Byte((byte) 0x4f)},
            {"P", new Byte((byte) 0x50)},
            {"Q", new Byte((byte) 0x51)},
            {"R", new Byte((byte) 0x52)},
            {"S", new Byte((byte) 0x53)},
            {"T", new Byte((byte) 0x54)},
            {"U", new Byte((byte) 0x55)},
            {"V", new Byte((byte) 0x56)},
            {"W", new Byte((byte) 0x57)},
            {"X", new Byte((byte) 0x58)},
            {"Y", new Byte((byte) 0x59)},
            {"Z", new Byte((byte) 0x5a)},
            {"Ä", new Byte((byte) 0x5b)},
            {"Ö", new Byte((byte) 0x5c)},
            {"Ñ", new Byte((byte) 0x5d)},
            {"Ü", new Byte((byte) 0x5e)},
            {"§", new Byte((byte) 0x5f)},
            {"¿", new Byte((byte) 0x60)},
            {"a", new Byte((byte) 0x61)},
            {"b", new Byte((byte) 0x62)},
            {"c", new Byte((byte) 0x63)},
            {"d", new Byte((byte) 0x64)},
            {"e", new Byte((byte) 0x65)},
            {"f", new Byte((byte) 0x66)},
            {"g", new Byte((byte) 0x67)},
            {"h", new Byte((byte) 0x68)},
            {"i", new Byte((byte) 0x69)},
            {"j", new Byte((byte) 0x6a)},
            {"k", new Byte((byte) 0x6b)},
            {"l", new Byte((byte) 0x6c)},
            {"m", new Byte((byte) 0x6d)},
            {"n", new Byte((byte) 0x6e)},
            {"o", new Byte((byte) 0x6f)},
            {"p", new Byte((byte) 0x70)},
            {"q", new Byte((byte) 0x71)},
            {"r", new Byte((byte) 0x72)},
            {"s", new Byte((byte) 0x73)},
            {"t", new Byte((byte) 0x74)},
            {"u", new Byte((byte) 0x75)},
            {"v", new Byte((byte) 0x76)},
            {"w", new Byte((byte) 0x77)},
            {"x", new Byte((byte) 0x78)},
            {"y", new Byte((byte) 0x79)},
            {"z", new Byte((byte) 0x7a)},
            {"ä", new Byte((byte) 0x7b)},
            {"ö", new Byte((byte) 0x7c)},
            {"ñ", new Byte((byte) 0x7d)},
            {"ü", new Byte((byte) 0x7e)},
//            {"à", new Byte((byte) 0x7f)},

            {"Ă", new Byte((byte) 0xA1)},
            {"Â", new Byte((byte) 0xA2)},
            {"Ê", new Byte((byte) 0xA3)},
            {"Ô", new Byte((byte) 0xA4)},
            {"Ơ", new Byte((byte) 0xA5)},
            {"Ư", new Byte((byte) 0xA6)},
            {"Đ", new Byte((byte) 0xA7)},
            {"ă", new Byte((byte) 0xA8)},
            {"â", new Byte((byte) 0xA9)},
            {"ê", new Byte((byte) 0xAA)},
            {"ô", new Byte((byte) 0xAB)},
            {"ơ", new Byte((byte) 0xAC)},
            {"ư", new Byte((byte) 0xAD)},
            {"đ", new Byte((byte) 0xAE)},
            {"à", new Byte((byte) 0xB5)},
            {"ả", new Byte((byte) 0xB6)},
            {"ã", new Byte((byte) 0xB7)},
            {"á", new Byte((byte) 0xB8)},
            {"ạ", new Byte((byte) 0xB9)},
            {"ằ", new Byte((byte) 0xB9)},
            {"ẳ", new Byte((byte) 0xBC)},
            {"ẵ", new Byte((byte) 0xBD)},
            {"ắ", new Byte((byte) 0xBE)},
            {"ặ", new Byte((byte) 0xC6)},
            {"ầ", new Byte((byte) 0xC7)},
            {"ẩ", new Byte((byte) 0xC7)},
            {"ẫ", new Byte((byte) 0xC7)},
            {"ấ", new Byte((byte) 0xCA)},
            {"ậ", new Byte((byte) 0xCB)},
            {"è", new Byte((byte) 0xCC)},
            {"ẻ", new Byte((byte) 0xCE)},
            {"ẽ", new Byte((byte) 0xCF)},
            {"é", new Byte((byte) 0xD0)},
            {"ẹ", new Byte((byte) 0xD1)},
            {"ề", new Byte((byte) 0xD2)},
            {"ể", new Byte((byte) 0xD3)},
            {"ễ", new Byte((byte) 0xD4)},
            {"ế", new Byte((byte) 0xD5)},
            {"ệ", new Byte((byte) 0xD6)},
            {"ì", new Byte((byte) 0xD7)},
            {"ỉ", new Byte((byte) 0xD8)},
            {"ĩ", new Byte((byte) 0xDC)},
            {"í", new Byte((byte) 0xDD)},
            {"ị", new Byte((byte) 0xDE)},
            {"ò", new Byte((byte) 0xDF)},
            {"ỏ", new Byte((byte) 0xE1)},
            {"õ", new Byte((byte) 0xE2)},
            {"ó", new Byte((byte) 0xE3)},
            {"ọ", new Byte((byte) 0xE4)},
            {"ồ", new Byte((byte) 0xE5)},
            {"ổ", new Byte((byte) 0xE6)},
            {"ỗ", new Byte((byte) 0xE7)},
            {"ố", new Byte((byte) 0xE8)},
            {"ộ", new Byte((byte) 0xE9)},
            {"ờ", new Byte((byte) 0xEA)},
            {"ở", new Byte((byte) 0xEB)},
            {"ỡ", new Byte((byte) 0xEC)},
            {"ớ", new Byte((byte) 0xED)},
            {"ợ", new Byte((byte) 0xEE)},
            {"ù", new Byte((byte) 0xEF)},
            {"ủ", new Byte((byte) 0xF1)},
            {"ũ", new Byte((byte) 0xF2)},
            {"ú", new Byte((byte) 0xF3)},
            {"ụ", new Byte((byte) 0xF4)},
            {"ừ", new Byte((byte) 0xF5)},
            {"ử", new Byte((byte) 0xF6)},
            {"ữ", new Byte((byte) 0xF7)},
            {"ứ", new Byte((byte) 0xF8)},
            {"ự", new Byte((byte) 0xF9)},
            {"ỳ", new Byte((byte) 0xFA)},
            {"ỷ", new Byte((byte) 0xFB)},
            {"ỹ", new Byte((byte) 0xFC)},
            {"ý", new Byte((byte) 0xFD)},
            {"ỵ", new Byte((byte) 0xFE)},
    };

    private static final Object[][] gsmExtensionCharacters = {
            {"\n", new Byte((byte) 0x0a)},
            {"^", new Byte((byte) 0x14)},
            {" ", new Byte((byte) 0x1b)}, // reserved for future extensions
            {"{", new Byte((byte) 0x28)},
            {"}", new Byte((byte) 0x29)},
            {"\\", new Byte((byte) 0x2f)},
            {"[", new Byte((byte) 0x3c)},
            {"~", new Byte((byte) 0x3d)},
            {"]", new Byte((byte) 0x3e)},
            {"|", new Byte((byte) 0x40)},
            {"€", new Byte((byte) 0x65)}
    };

    public static final Object[][] gmsCharsetLib = {
            {"^", "14"},
            {" ", "20"}, // reserved for future extensions
            {"{", "28"},
            {"}", "29"},
            {"[", "3c"},
            {"~", "3d"},
            {"]", "3e"},
            {"|", "40"},
            {"€", "65"},
            {"@", "00"},
            {"£", "01"},
            {"$", "02"},
            {"¥", "03"},
            {"Ç", "09"},
            {"Ø", "0b"},
            {"ø", "0c"},
            {"Å", "0e"},
            {"_", "11"},
            {"Æ", "1c"},
            {"æ", "1d"},
            {"ß", "1e"},
            {"É", "1f"},
            {"!", "21"},
            {"#", "23"},
            {"¤", "24"},
            {"%", "25"},
            {"&", "26"},
            {"'", "27"},
            {"(", "28"},
            {")", "29"},
            {"*", "2a"},
            {"+", "2b"},
            {",", "2c"},
            {"-", "2d"},
            {".", "2e"},
            {"/", "2f"},
            {"0", "30"},
            {"1", "31"},
            {"2", "32"},
            {"3", "33"},
            {"4", "34"},
            {"5", "35"},
            {"6", "36"},
            {"7", "37"},
            {"8", "38"},
            {"9", "39"},
            {":", "3a"},
            {";", "3b"},
            {"<", "3c"},
            {"=", "3d"},
            {">", "3e"},
            {"?", "3f"},
            {"¡", "40"},
            {"A", "41"},
            {"B", "42"},
            {"C", "43"},
            {"D", "44"},
            {"E", "45"},
            {"F", "46"},
            {"G", "47"},
            {"H", "48"},
            {"I", "49"},
            {"J", "4a"},
            {"K", "4b"},
            {"L", "4c"},
            {"M", "4d"},
            {"N", "4e"},
            {"O", "4f"},
            {"P", "50"},
            {"Q", "51"},
            {"R", "52"},
            {"S", "53"},
            {"T", "54"},
            {"U", "55"},
            {"V", "56"},
            {"W", "57"},
            {"X", "58"},
            {"Y", "59"},
            {"Z", "5a"},
            {"Ä", "5b"},
            {"Ö", "5c"},
            {"Ñ", "5d"},
            {"Ü", "5e"},
            {"§", "5f"},
            {"¿", "60"},
            {"a", "61"},
            {"b", "62"},
            {"c", "63"},
            {"d", "64"},
            {"e", "65"},
            {"f", "66"},
            {"g", "67"},
            {"h", "68"},
            {"i", "69"},
            {"j", "6a"},
            {"k", "6b"},
            {"l", "6c"},
            {"m", "6d"},
            {"n", "6e"},
            {"o", "6f"},
            {"p", "70"},
            {"q", "71"},
            {"r", "72"},
            {"s", "73"},
            {"t", "74"},
            {"u", "75"},
            {"v", "76"},
            {"w", "77"},
            {"x", "78"},
            {"y", "79"},
            {"z", "7a"},
            {"ä", "7b"},
            {"ö", "7c"},
            {"ñ", "7d"},
            {"ü", "7e"},
            {"Ă", "A1"},
            {"Â", "A2"},
            {"Ê", "A3"},
            {"Ô", "A4"},
            {"Ơ", "A5"},
            {"Ư", "A6"},
            {"Đ", "A7"},
            {"ă", "A8"},
            {"â", "A9"},
            {"ê", "AA"},
            {"ô", "AB"},
            {"ơ", "AC"},
            {"ư", "AD"},
            {"đ", "AE"},
            {"à", "B5"},
            {"ả", "B6"},
            {"ã", "B7"},
            {"á", "B8"},
            {"ạ", "B9"},
            {"ằ", "B9"},
            {"ẳ", "BC"},
            {"ẵ", "BD"},
            {"ắ", "BE"},
            {"ặ", "C6"},
            {"ầ", "C7"},
            {"ẩ", "C7"},
            {"ẫ", "C7"},
            {"ấ", "CA"},
            {"ậ", "CB"},
            {"è", "CC"},
            {"ẻ", "CE"},
            {"ẽ", "CF"},
            {"é", "D0"},
            {"ẹ", "D1"},
            {"ề", "D2"},
            {"ể", "D3"},
            {"ễ", "D4"},
            {"ế", "D5"},
            {"ệ", "D6"},
            {"ì", "D7"},
            {"ỉ", "D8"},
            {"ĩ", "DC"},
            {"í", "DD"},
            {"ị", "DE"},
            {"ò", "DF"},
            {"ỏ", "E1"},
            {"õ", "E2"},
            {"ó", "E3"},
            {"ọ", "E4"},
            {"ồ", "E5"},
            {"ổ", "E6"},
            {"ỗ", "E7"},
            {"ố", "E8"},
            {"ộ", "E9"},
            {"ờ", "EA"},
            {"ở", "EB"},
            {"ỡ", "EC"},
            {"ớ", "ED"},
            {"ợ", "EE"},
            {"ù", "EF"},
            {"ủ", "F1"},
            {"ũ", "F2"},
            {"ú", "F3"},
            {"ụ", "F4"},
            {"ừ", "F5"},
            {"ử", "F6"},
            {"ữ", "F7"},
            {"ứ", "F8"},
            {"ự", "F9"},
            {"ỳ", "FA"},
            {"ỷ", "FB"},
            {"ỹ", "FC"},
            {"ý", "FD"},
            {"ỵ", "FE"},
    };

//    byte[] sample = new byte[]{
////                    (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09, (byte) 0x0A, (byte) 0x0B, (byte) 0x0C, (byte) 0x0D, (byte) 0x0E, (byte) 0x0F,
////                    (byte) 0x10, (byte) 0x11, (byte) 0x12, (byte) 0x13, (byte) 0x14, (byte) 0x15, (byte) 0x16, (byte) 0x17, (byte) 0x18, (byte) 0x19, (byte) 0x1A, (byte) 0x1B, (byte) 0x1C, (byte) 0x1D, (byte) 0x1E, (byte) 0x1F,
////                    (byte) 0x20, (byte) 0x21, (byte) 0x22, (byte) 0x23, (byte) 0x24, (byte) 0x25, (byte) 0x26, (byte) 0x27, (byte) 0x28, (byte) 0x29, (byte) 0x2A, (byte) 0x2B, (byte) 0x2C, (byte) 0x2D, (byte) 0x2E, (byte) 0x2F,
////                    (byte) 0x30, (byte) 0x31, (byte) 0x32, (byte) 0x33, (byte) 0x34, (byte) 0x35, (byte) 0x36, (byte) 0x37, (byte) 0x38, (byte) 0x39, (byte) 0x3A, (byte) 0x3B, (byte) 0x3C, (byte) 0x3D, (byte) 0x3E, (byte) 0x3F,
////                    (byte) 0x40, (byte) 0x41, (byte) 0x42, (byte) 0x43, (byte) 0x44, (byte) 0x45, (byte) 0x46, (byte) 0x47, (byte) 0x48, (byte) 0x49, (byte) 0x4A, (byte) 0x4B, (byte) 0x4C, (byte) 0x4D, (byte) 0x4E, (byte) 0x4F,
////                    (byte) 0x50, (byte) 0x51, (byte) 0x52, (byte) 0x53, (byte) 0x54, (byte) 0x55, (byte) 0x56, (byte) 0x57, (byte) 0x58, (byte) 0x59, (byte) 0x5A, (byte) 0x5B, (byte) 0x5C, (byte) 0x5D, (byte) 0x5E, (byte) 0x5F,
////                    (byte) 0x60, (byte) 0x61, (byte) 0x62, (byte) 0x63, (byte) 0x64, (byte) 0x65, (byte) 0x66, (byte) 0x67, (byte) 0x68, (byte) 0x69, (byte) 0x6A, (byte) 0x6B, (byte) 0x6C, (byte) 0x6D, (byte) 0x6E, (byte) 0x6F,
////                    (byte) 0x70, (byte) 0x71, (byte) 0x72, (byte) 0x73, (byte) 0x74, (byte) 0x75, (byte) 0x76, (byte) 0x77, (byte) 0x78, (byte) 0x79, (byte) 0x7A, (byte) 0x7B, (byte) 0x7C, (byte) 0x7D, (byte) 0x7E, (byte) 0x7F,
//
////                  * 33 byte trắng
////                    (byte) 0x80, (byte) 0x81, (byte) 0x82, (byte) 0x83, (byte) 0x84, (byte) 0x85, (byte) 0x86, (byte) 0x87, (byte) 0x88, (byte) 0x89, (byte) 0x8A, (byte) 0x8B, (byte) 0x8C, (byte) 0x8D, (byte) 0x8E, (byte) 0x8F, (byte) 0x90, (byte) 0x91, (byte) 0x92, (byte) 0x93, (byte) 0x94, (byte) 0x95, (byte) 0x96, (byte) 0x97, (byte) 0x98, (byte) 0x99, (byte) 0x9A, (byte) 0x9B, (byte) 0x9C, (byte) 0x9D, (byte) 0x9E, (byte) 0x9F, (byte) 0xA0,
//
////                    * 14 byte: Ă Â Ê Ô Ơ Ư Đ ă â ê ô ơ ư đ
//            (byte) 0xA1, // Ă
//            (byte) 0xA2, // Â
//            (byte) 0xA3, // Ê
//            (byte) 0xA4, // Ô
//            (byte) 0xA5, // Ơ
//            (byte) 0xA6, // Ư
//            (byte) 0xA7, // Đ
//            (byte) 0xA8, // ă
//            (byte) 0xA9, // â
//            (byte) 0xAA, // ê
//            (byte) 0xAB, // ô
//            (byte) 0xAC, // ơ
//            (byte) 0xAD, // ư
//            (byte) 0xAE, // đ
//
////                    * 6 byte trắng: ######################################################
////                    (byte) 0xAF, (byte) 0xB0, (byte) 0xB1, (byte) 0xB2, (byte) 0xB3, (byte) 0xB4,
//
////                    * 5 byte: à ả ã á ạ
//            (byte) 0xB5,
////                   ả
//            (byte) 0xB6,
////                    ã
//            (byte) 0xB7,
////                    á
//            (byte) 0xB8,
////                    ạ
//            (byte) 0xB9,
//
////                    * 1 byte trắng: ######################################################
////                    (byte) 0xBA,
//
////                    * 4 byte: ằ ẳ ẵ ắ
//            (byte) 0xBB, // ằ
//            (byte) 0xBC, // ẳ
//            (byte) 0xBD, // ẵ
//            (byte) 0xBE, // ắ
//
////                    * 7 byte trắng: ######################################################
////                    (byte) 0xBF, (byte) 0xC0, (byte) 0xC1, (byte) 0xC2, (byte) 0xC3, (byte) 0xC4, (byte) 0xC5,
//
////                    * 7 byte: ặ ầ ẩ ẫ ấ ậ è
//            (byte) 0xC6, // ặ
//            (byte) 0xC7, // ầ
//            (byte) 0xC8, // ẩ
//            (byte) 0xC9, // ẫ
//            (byte) 0xCA, // ấ
//            (byte) 0xCB, // ậ
//            (byte) 0xCC, // è
//
////                    * 1 byte trắng: ######################################################
////                    (byte) 0xCD,
//
////                    * 11 byte: ẻ ẽ é ẹ ề ể ễ ế ệ ì ỉ
//            (byte) 0xCE, // ẻ
//            (byte) 0xCF, // ẽ
//            (byte) 0xD0, // é
//            (byte) 0xD1, // ẹ
//            (byte) 0xD2, // ề
//            (byte) 0xD3, // ể
//            (byte) 0xD4, // ễ
//            (byte) 0xD5, // ế
//            (byte) 0xD6, // ệ
//            (byte) 0xD7, // ì
//            (byte) 0xD8, // ỉ
//
////                    * 3 byte trắng: ######################################################
////                    (byte) 0xD9, (byte) 0xDA, (byte) 0xDB,
//
////                    * 4 byte: ĩ í ị ò
//            (byte) 0xDC, // ĩ
//            (byte) 0xDD, // í
//            (byte) 0xDE, // ị
//            (byte) 0xDF, // ò
//
////                    * 1 byte trắng: ######################################################
////                    (byte) 0xE0,
//
////                    * 15 byte: ỏ õ ó ọ ồ ổ ỗ ố ộ ờ ở ỡ ớ ợ ù
//            (byte) 0xE1, // ỏ
//            (byte) 0xE2, //  õ
//            (byte) 0xE3, //  ó
//            (byte) 0xE4, // ọ
//            (byte) 0xE5, // ồ
//            (byte) 0xE6, //  ổ
//            (byte) 0xE7, // ỗ
//            (byte) 0xE8, // ố
//            (byte) 0xE9, //  ộ
//            (byte) 0xEA, //  ờ
//            (byte) 0xEB, // ở
//            (byte) 0xEC, //  ỡ
//            (byte) 0xED, //  ớ
//            (byte) 0xEE, //  ợ
//            (byte) 0xEF, // ù
//
////                    * 1 byte trắng: ######################################################
////                   (byte) 0xF0,
//
////                    * 14 byte: ủ ũ ú ụ ừ ử ữ ứ ự ỳ ỷ ỹ ý ỵ
//            (byte) 0xF1, //  ủ
//            (byte) 0xF2, //  ũ
//            (byte) 0xF3, //  ú
//            (byte) 0xF4, //  ụ
//            (byte) 0xF5, //  ừ
//            (byte) 0xF6, //  ử
//            (byte) 0xF7, //  ữ
//            (byte) 0xF8, //  ứ
//            (byte) 0xF9, //  ự
//            (byte) 0xFA, //  ỳ
//            (byte) 0xFB, //  ỷ
//            (byte) 0xFC, //  ỹ
//            (byte) 0xFD, //  ý
//            (byte) 0xFE, //  ỵ
//
////                    * 1 byte trắng: ######################################################
////                    (byte) 0xFF
//    };

    private static Logger logger = Logger.getLogger(CharsetESP.class.getName());

    static {
        // default alphabet
        int len = gsmCharacters.length;
        for (int i = 0; i < len; i++) {
            Object[] map = gsmCharacters[i];
            defaultEncodeMap.put((String) map[0], (Byte) map[1]);
            defaultDecodeMap.put((Byte) map[1], (String) map[0]);
        }

        // extended alphabet
        len = gsmExtensionCharacters.length;
        for (int i = 0; i < len; i++) {
            Object[] map = gsmExtensionCharacters[i];
            extEncodeMap.put((String) map[0], (Byte) map[1]);
            extDecodeMap.put((Byte) map[1], (String) map[0]);
        }
    }

    public byte[] decoder(String text) {

        Log.d("TEST", gsmCharacters + "");
        String textHex = "";
        for (char ch : text.toCharArray()) {
            String elem = String.valueOf(ch);
            if (elem.equalsIgnoreCase(" ")) {
                //            white space: 0x00
                textHex = textHex + "20";
            } else if (elem.equalsIgnoreCase("a")) {
                //            a: 0x61
                Log.d("elem_of_text", "a !" + Arrays.toString(new byte[]{(byte) 0x61}));
                textHex = textHex + "61";
            } else if (elem.equalsIgnoreCase("à")) {
                //            à: 0xB5
                Log.d("elem_of_text", "à !" + Arrays.toString(new byte[]{(byte) 0xB5}));
                textHex = textHex + "B5";
            } else if (elem.equalsIgnoreCase("ả")) {
                //            ả: 0xB6
                Log.d("elem_of_text", "ả !" + Arrays.toString(new byte[]{(byte) 0xB6}));
                textHex = textHex + "B6";
            } else if (elem.equalsIgnoreCase("ã")) {
                //            ã: 0xB7
                Log.d("elem_of_text", "ã !" + Arrays.toString(new byte[]{(byte) 0xB7}));
                textHex = textHex + "B7";
            } else if (elem.equalsIgnoreCase("á")) {
                //            á: 0xB8
                Log.d("elem_of_text", "á !" + Arrays.toString(new byte[]{(byte) 0xB8}));
                textHex = textHex + "B8";
            } else if (elem.equalsIgnoreCase("ạ")) {
                //            ạ: 0xB9
                Log.d("elem_of_text", "ạ !" + Arrays.toString(new byte[]{(byte) 0xB9}));
                textHex = textHex + "B9";
            } else {
                textHex = textHex + "7C";
            }
        }

        int len = textHex.length();
        byte[] textBytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            textBytes[i / 2] = (byte) ((Character.digit(textHex.charAt(i), 16) << 4)
                    + Character.digit(textHex.charAt(i + 1), 16));
        }

        return textBytes;
    }

    /**
     * Initializes a new charset with the given canonical name and alias
     * set.
     *
     * @param canonicalName The canonical name of this charset
     * @param aliases       An array of this charset's aliases, or null if it has no aliases
     * @throws IllegalCharsetNameException If the canonical name or any of the aliases are illegal
     */
    protected CharsetESP(String canonicalName, String[] aliases) {
        super(canonicalName, aliases);
    }

    @Override
    public CharsetEncoder newEncoder() {
        return new CharsetESPEncoder(this);
    }

    @Override
    public CharsetDecoder newDecoder() {
        return new CharsetESPDecoder(this);
    }

    @Override
    public boolean contains(Charset cs) {
        return (false);
    }

    private class CharsetESPEncoder extends CharsetEncoder {
        /**
         * Constructor, call the superclass constructor with the
         * Charset object and the encodings sizes from the
         * delegate encoder.
         */
        CharsetESPEncoder(Charset cs) {
            super(cs, 1, 2);
        }

        /**
         * Implementation of the encoding loop.
         */
        protected CoderResult encodeLoop(CharBuffer cb, ByteBuffer bb) {
            CoderResult cr = CoderResult.UNDERFLOW;

            while (cb.hasRemaining()) {
                if (!bb.hasRemaining()) {
                    cr = CoderResult.OVERFLOW;
                    break;
                }
                char ch = cb.get();

                // first check the default alphabet
                Byte b = (Byte) defaultEncodeMap.get("" + ch);
                if (debug)
                    logger.finest("Encoding ch " + ch + " to byte " + b);
                if (b != null) {
                    bb.put((byte) b.byteValue());
                } else {
                    // check extended alphabet
                    b = (Byte) extEncodeMap.get("" + ch);
                    if (debug)
                        logger.finest("Trying extended map to encode ch " + ch + " to byte " + b);
                    if (b != null) {
                        // since the extended character set takes two bytes
                        // we have to check that there is enough space left
                        if (bb.remaining() < 2) {
                            // go back one step
                            cb.position(cb.position() - 1);
                            cr = CoderResult.OVERFLOW;
                            break;
                        }
                        // all ok, add it to the buffer
                        bb.put((byte) 0x1b);
                        bb.put((byte) b.byteValue());
                    } else {
                        // no match found, send a ?
                        b = new Byte((byte) 0x3F);
                        bb.put((byte) b.byteValue());
                    }
                }
            }
            return cr;
        }
    }

    // --------------------------------------------------------

    /**
     * The decoder implementation for the Gsm 7Bit Charset.
     */
    private class CharsetESPDecoder extends CharsetDecoder {
        /**
         * Constructor, call the superclass constructor with the
         * Charset object and pass alon the chars/byte values
         * from the delegate decoder.
         */
        CharsetESPDecoder(Charset cs) {
            super(cs, 1, 1);
        }

        /**
         * Implementation of the decoding loop.
         */
        protected CoderResult decodeLoop(ByteBuffer bb, CharBuffer cb) {
            CoderResult cr = CoderResult.UNDERFLOW;

            while (bb.hasRemaining()) {
                if (!cb.hasRemaining()) {
                    cr = CoderResult.OVERFLOW;
                    break;
                }
                byte b = bb.get();

                // first check the default alphabet
                if (debug)
                    logger.finest("Looking up byte " + b);
                String s = (String) defaultDecodeMap.get(new Byte(b));
                if (s != null) {
                    char ch = s.charAt(0);
                    if (ch != '\u001B') {
                        if (debug)
                            logger.finest("Found string " + s);
                        cb.put(ch);
                    } else {
                        if (debug)
                            logger.finest("Found escape character");
                        // check the extended alphabet
                        if (bb.hasRemaining()) {
                            b = bb.get();
                            s = (String) extDecodeMap.get(new Byte(b));
                            if (s != null) {
                                if (debug)
                                    logger.finest("Found extended string " + s);
                                ch = s.charAt(0);
                                cb.put(ch);
                            } else {
                                cb.put('?');
                            }
                        }
                    }
                } else {
                    cb.put('?');
                }
            }
            return cr;
        }
    }
}
