package com.cet.utils;

import java.util.ArrayList;
import java.util.List;

public class FileDelimitersUtil {

    public static Character returnDelimiter(String content, char... delimiters) throws Exception {

        List<Character> delimitersFoundInFileContent = new ArrayList<>();

        for (char delimiter : delimiters) {
            boolean delimiterInFileFounded = content.contains(String.valueOf(delimiter));
            long numberOfOcurrences = content.chars().filter(x -> x == delimiter).count();

            if(delimiterInFileFounded && numberOfOcurrences > 1) {
                delimitersFoundInFileContent.add(delimiter);
            }
        }

        if (delimitersFoundInFileContent.size() > 1) {
            throw new Exception("More than one delimiters were found in the file");
        } else if(delimitersFoundInFileContent.size() == 0) {
            throw new Exception("File is not separated with the allowed delimiters");
        }

        return delimitersFoundInFileContent.get(0);
    }

}
