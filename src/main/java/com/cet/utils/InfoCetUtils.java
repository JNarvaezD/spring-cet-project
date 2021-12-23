package com.cet.utils;

public class InfoCetUtils {

    private static int covidContacto;
    private static boolean fueConfirmado;

    public static void setCovidContactoAndFueConfirmado(int covidContactoPersona, boolean fueConfirmadoPersona) {
        covidContacto = covidContactoPersona;
        fueConfirmado = fueConfirmadoPersona;

        if (covidContacto == 1) {
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

}
