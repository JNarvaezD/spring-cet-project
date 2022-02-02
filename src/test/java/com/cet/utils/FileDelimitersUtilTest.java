package com.cet.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileDelimitersUtilTest {

    @Test
    void hasOnlyOneDelimiter() throws Exception {
        String contentFile = "628604|01/09/2020|90316619|CC|1003645405|MENDOZA|MORENO|JEAN|CARLOS|05/04/1997|M|CCF033||3045871192|1" +
                "658405|04/09/2020|106807926|CC|1003465008|OVIEDO|GARNAUT|CRISTIAN|CAMILO|10/12/2000|M|CCF033||3127526393|1";

        assertEquals('|', FileDelimitersUtil.returnDelimiter(contentFile, '|', ',', ';'));
    }

    @Test
    void hasMoreThanOneDelimiter() throws Exception {
        String contentFile = "628604|01/09/2020|90316619|CC|1003645405|MENDOZA|MORENO|JEAN|CARLOS|05/04/1997|M|CCF033||3045871192|1" +
                "658405|04/09/2020|106807926|CC|1003465008|OVIEDO,GARNAUT|CRISTIAN|CAMILO|10/12/2000|M|CCF033||3127526393|1";

        Exception exception = assertThrows(Exception.class, () -> FileDelimitersUtil.returnDelimiter(contentFile, '|', ',', ';'));
        assertEquals("Se han encontrado mas de dos delimitadores en el archivo", exception.getMessage());
    }

    @Test
    void hasNotAllowedDelimiters() throws Exception {
        String contentFile = "628604-01/09/2020-90316619-CC-1003645405-MENDOZA-MORENO-JEAN-CARLOS-05/04/1997-M-CCF033-3045871192-1" +
                "658405-04/09/2020-106807926-CC-1003465008-OVIEDO-GARNAUT-CRISTIAN-CAMILO-10/12/2000-M-CCF033-3127526393-1";

        Exception exception = assertThrows(Exception.class, () -> FileDelimitersUtil.returnDelimiter(contentFile, '|', ',', ';'));
        assertEquals("El archivo no esta separado por los delimitadores permitidos", exception.getMessage());
    }
}