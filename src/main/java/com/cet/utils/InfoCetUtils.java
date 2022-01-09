package com.cet.utils;

import java.util.Objects;

public class InfoCetUtils {

    private static Integer covidContacto;
    private static Boolean fueConfirmado;

    public static void setCovidContactoAndFueConfirmado(Integer covidContactoPersona, Boolean fueConfirmadoPersona, Long idConfirmado, Long idContacto) {
        covidContacto = covidContactoPersona;
        fueConfirmado = fueConfirmadoPersona;

        if (covidContacto == 1 && !Objects.equals(idConfirmado, idContacto)) {
            covidContacto = 2;
            fueConfirmado = true;
        } else if (fueConfirmado) {
            covidContacto = 1;
            fueConfirmado = false;
        }
    }

    public static int getCovidContacto() {
        return covidContacto;
    }

    public static boolean getFueConfirmado() {
        return fueConfirmado;
    }

    public static void restartAttributes() {
        covidContacto = null;
        fueConfirmado = null;
    }

}
