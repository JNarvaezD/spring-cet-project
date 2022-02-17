package com.cet.utils;

import com.cet.Models.InfoCet;

import java.util.List;
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

    public static String returnNoSaleEnArchivo(List<InfoCet> contactosL, int covidContacto, boolean compartenGastos) {
        String mensaje = "";
        System.out.println("Gente " + contactosL);
        if(covidContacto == 1 && compartenGastos && contactosL.size() == 0) {
            mensaje += "Confirmado que comparte gastos sin grupo familiar conformado";
        }

        if(covidContacto == 1 && contactosL.size() > 0) {
            if(compartenGastos && contactosL.size() > 1 && contactosL.stream().filter(InfoCet::getProductoFinanciero).count() > 1) {
                mensaje += "Hay uno o varios contactos vinculados con campos por diligenciar";
            }
            if(compartenGastos && contactosL.size() > 1 && contactosL.stream().filter(InfoCet::getAutorizaEps).count() == 1) {
                mensaje += "Grupo familiar conformado sin persona autorizada para pago de CET";
            }
        }

        return mensaje;
    }

}
