package edu.spbspu.amd.morze_app.morzeCoder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MorzeСoder {
    private StringBuffer cur_buf;
    private char res_char;
    private boolean can_decode;

    private static final HashMap<String, Character> morze_decoding_map;
    private static final HashMap<Character, String> morze_encoding_map;
    static {
        morze_decoding_map = new HashMap<>();
        morze_decoding_map.put(".-", 'a');
        morze_decoding_map.put("-...", 'b');
        morze_decoding_map.put("-.-", 'c');
        morze_decoding_map.put("-..", 'd');
        morze_decoding_map.put("..--.", 'e');
        morze_decoding_map.put("..-.", 'f');
        morze_decoding_map.put("--.", 'g');
        morze_decoding_map.put("....", 'h');
        morze_decoding_map.put("..", 'i');
        morze_decoding_map.put(".---", 'j');
        morze_decoding_map.put("-.", 'k');
        morze_decoding_map.put(".-.-.", 'l');
        morze_decoding_map.put("--", 'm');
        morze_decoding_map.put("-.-.", 'n');
        morze_decoding_map.put("---", 'o');
        morze_decoding_map.put(".--.", 'p');
        morze_decoding_map.put("--.-", 'q');
        morze_decoding_map.put(".-.", 'r');
        morze_decoding_map.put("...", 's');
        morze_decoding_map.put("-", 't');
        morze_decoding_map.put("..-", 'u');
        morze_decoding_map.put("...-", 'v');
        morze_decoding_map.put(".--", 'w');
        morze_decoding_map.put("-..-", 'x');
        morze_decoding_map.put("-.--", 'y');
        morze_decoding_map.put("--..", 'z');
        morze_decoding_map.put(".----", '1');
        morze_decoding_map.put("..---", '2');
        morze_decoding_map.put("...--", '3');
        morze_decoding_map.put("....-", '4');
        morze_decoding_map.put(".....", '5');
        morze_decoding_map.put("-....", '6');
        morze_decoding_map.put("--...", '7');
        morze_decoding_map.put("---..", '8');
        morze_decoding_map.put("----.", '9');
        morze_decoding_map.put("-----", '0');

        //between words
        morze_decoding_map.put("-.-..", ' ');

        morze_encoding_map = new HashMap<>();
        Iterator it = morze_decoding_map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            morze_encoding_map.put((Character)pair.getValue(), (String)pair.getKey());
        }
    }

    public MorzeСoder()
    {
        cur_buf = new StringBuffer();
        can_decode = false;
    }

    private Character findElemByBuffer()
    {
        if (morze_decoding_map.containsKey(cur_buf.toString())) {
            return morze_decoding_map.get(cur_buf.toString());
        }

        return '#';
    }

    /**
     *
     * @param sym = .|-|&|
     *            . = white dot
     *            - = white 3 * dot
     *            & = black 3 * dot is between characters
     */
    public void appendSym(char sym) throws Exception {
        if (can_decode)
            return;

        if (sym == '&')
        {
            res_char = findElemByBuffer();
            if (res_char != '#')
                can_decode = true;
            else
                throw new Exception("Wrong symbols sequence");
        }
        else
            //new char
            cur_buf.append(sym);
    }

    public boolean canDecode(){
        return can_decode;
    }

    public char getDecodedSym() throws Exception {
        if (can_decode) {
            cur_buf.delete(0, cur_buf.length());
            can_decode = false;
            return res_char;
        }
        else
            throw new Exception("Result char not ready");
    }

    public static char[] encode(String str)
    {
        StringBuilder res = new StringBuilder();

        for (char elem : str.toCharArray()) {
            res.append(morze_encoding_map.get(elem));

            //between chars
            res.append("&");
        }

        return res.toString().toCharArray();
    }
}
