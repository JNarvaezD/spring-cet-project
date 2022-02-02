package com.cet.utils;

import java.util.ArrayList;
import java.util.List;

public class FileDelimitersUtil {

    public static Character returnDelimiter(String content, char... delimiters) throws Exception {

        List<Character> delimitersFoundInFileContent = new ArrayList<>();

        for (char delimiter : delimiters) {
            boolean delimiterInFileFounded = content.contains(String.valueOf(delimiter));

            if(delimiterInFileFounded) {
                delimitersFoundInFileContent.add(delimiter);
            }
        }

        if (delimitersFoundInFileContent.size() > 1) {
            throw new Exception("Se han encontrado mas de dos delimitadores en el archivo");
        } else if(delimitersFoundInFileContent.size() == 0) {
            throw new Exception("El archivo no esta separado por los delimitadores permitidos");
        }

        return delimitersFoundInFileContent.get(0);
    }

}
