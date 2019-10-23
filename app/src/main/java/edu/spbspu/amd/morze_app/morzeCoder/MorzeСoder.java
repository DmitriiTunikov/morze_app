package edu.spbspu.amd.morze_app.morzeCoder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MorzeСoder {
    private StringBuffer cur_buf;
    private char res_char;
    private boolean can_decode;

    private static final HashMap<Character, String> morze_map;
    static {
        morze_map = new HashMap<Character, String>();
        morze_map.put('a',".-");
        morze_map.put('b',"-...");
        morze_map.put('c',"-.-");
        morze_map.put('d',"-..");
        morze_map.put('e',".");
        morze_map.put('f',"..-.");
        morze_map.put('g',"--.");
        morze_map.put('h',"....");
        morze_map.put('i',"..");
        morze_map.put('j',".---");
        morze_map.put('k',"-.");
        morze_map.put('l',".-..");
        morze_map.put('m',"--");
        morze_map.put('n',"-.");
        morze_map.put('o',"---");
        morze_map.put('p',".--.");
        morze_map.put('q',"--.-");
        morze_map.put('r',".-.");
        morze_map.put('s',"...");
        morze_map.put('t',"-");
        morze_map.put('u',"..-");
        morze_map.put('v',"...-");
        morze_map.put('w',".--");
        morze_map.put('x',"-..-");
        morze_map.put('y',"-.--");
        morze_map.put('z',"--..");
        morze_map.put('1',".----");
        morze_map.put('2',"..---");
        morze_map.put('3',"...--");
        morze_map.put('4',"....-");
        morze_map.put('5',".....");
        morze_map.put('6',"-....");
        morze_map.put('7',"--...");
        morze_map.put('8',"---..");
        morze_map.put('9',"----.");
        morze_map.put('0',"-----");

        //between words
        morze_map.put(' ',"-.-.");
    }

    public MorzeСoder()
    {
        cur_buf = new StringBuffer();
        can_decode = false;
    }

    private Character findElemByBuffer()
    {
        Iterator it = morze_map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if (pair.getValue().equals(cur_buf.toString()))
                return (Character) pair.getKey();
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

        //new char
        if (sym == '&')
        {
            res_char = findElemByBuffer();
            if (res_char != '#')
                can_decode = true;
            else
                throw new Exception("wrong symbols sequence");
        }
        else
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
            throw new Exception("result char not ready");
    }

    public static char[] encode(String str)
    {
        StringBuilder res = new StringBuilder();

        for (char elem : str.toCharArray()) {
            res.append(morze_map.get(elem));

            //between chars
            res.append("&");
        }

        return res.toString().toCharArray();
    }
}
