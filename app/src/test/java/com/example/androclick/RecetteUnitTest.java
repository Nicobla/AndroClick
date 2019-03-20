package com.example.androclick;

import org.junit.Test;

import static org.junit.Assert.*;

public class RecetteUnitTest {
    @Test
    public void getNom_isCorrect() {
        Recette recette = new Recette("Recette test");

        String expected = "Recette test";
        String result = recette.getNom();
        assertEquals(expected, result);
    }
    @Test
    public void getNom_isIncorrect() {
        Recette recette = new Recette("Recette aaaa");

        String expected = "Recette test";
        String result = recette.getNom();
        assertNotEquals(expected, result);
    }

    @Test
    public void getSauces_isCorrect() {
        Recette recette = new Recette();

        int expected = 0;
        int result = recette.getSauces().size();
        assertEquals(expected, result);
    }
    @Test
    public void addSauces_isWorking() {
        Recette recette = new Recette();
        recette.addSauce(new Sauce());

        int expected = 1;
        int result = recette.getSauces().size();
        assertEquals(expected, result);
    }
    @Test
    public void getViandes_isCorrect() {
        Recette recette = new Recette();

        int expected = 0;
        int result = recette.getViandes().size();
        assertEquals(expected, result);
    }
    @Test
    public void addViandes_isWorking() {
        Recette recette = new Recette();
        recette.addViande(new Viande());

        int expected = 1;
        int result = recette.getViandes().size();
        assertEquals(expected, result);
    }
    @Test
    public void getSupplements_isCorrect() {
        Recette recette = new Recette();

        int expected = 0;
        int result = recette.getSupplements().size();
        assertEquals(expected, result);
    }
    @Test
    public void addSupplements_isWorking() {
        Recette recette = new Recette();
        recette.addSupplement(new Supplement());

        int expected = 1;
        int result = recette.getSupplements().size();
        assertEquals(expected, result);
    }
}