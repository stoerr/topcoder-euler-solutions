package net.stoerr.euler.help;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class ParsingUtils {

    /**
     * Parses multiple lines with space separated items
     * @param in
     * @return
     * @throws IOException 
     */
    public static String[][] parseLines(Reader in) throws IOException {
        BufferedReader bufr = new BufferedReader(in);
        List<String[]> lines = new ArrayList<String[]>();
        String s;
        while (null != (s=bufr.readLine())) {
            String[] line = s.split("\\s+");
            lines.add(line);
        }
        return lines.toArray(new String[0][]);
    }
    
}
