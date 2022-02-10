package com.cet.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InfoCetUtilsTest {

    @Test
    void givenSameIdsShoudReturnSameCovidContactoAndFueConfirmado() {
        InfoCetUtils.setCovidContactoAndFueConfirmado(1,false, 1L, 1L);
        assertFalse(InfoCetUtils.getFueConfirmado());
        assertEquals(1, InfoCetUtils.getCovidContacto());
    }

    @Test
    void givenDifferentIdsShoudReturnTwoForCovidContactoAndFalseForFueConfirmado() {
        InfoCetUtils.setCovidContactoAndFueConfirmado(2,false, 1L, 2L);
        assertFalse(InfoCetUtils.getFueConfirmado());
        assertEquals(2, InfoCetUtils.getCovidContacto());
    }

    @Test
    void givenDifferentIdsAndOneForCovidContactoShoudReturnTwoForCovidContactoAndRTrueForFueConfirmado() {
        InfoCetUtils.setCovidContactoAndFueConfirmado(1,false, 1L, 2L);
        assertTrue(InfoCetUtils.getFueConfirmado());
        assertEquals(2, InfoCetUtils.getCovidContacto());
    }

    @Test
    void fueConfirmadoBeingTrueShouldReturnOneForCovidContactoAndFalseForFueConfirmado() {
        InfoCetUtils.setCovidContactoAndFueConfirmado(2,true, 1L, 2L);
        assertFalse(InfoCetUtils.getFueConfirmado());
        assertEquals(1, InfoCetUtils.getCovidContacto());
    }

}
