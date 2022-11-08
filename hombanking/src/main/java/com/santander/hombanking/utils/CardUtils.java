package com.santander.hombanking.utils;


import java.util.Random;

public final class CardUtils {

    private CardUtils() {
    }

    public static String getCardNumber() {
        // Creo un numero para la cuenta: 3333-XXXX-XXXX-XXXX
//        int numero = (int)(Math.random()*(-99999999 +1)+99999999); // Opcion 2 para crear un número random
        int numero = new Random().nextInt(10000);
        String digitos4Generados = String.format("%0" + 4 + "d", numero);
        String number = "3333-"+ digitos4Generados;

        numero = new Random().nextInt(10000);
        digitos4Generados = String.format("%0" + 4 + "d", numero);
        number += "-"+ digitos4Generados;

        numero = new Random().nextInt(10000);
        digitos4Generados = String.format("%0" + 4 + "d", numero);
        number += "-"+ digitos4Generados;
        return number;
    }

    public static int getCvv() {
        // Creo un numero para la cuenta: 3333-XXXX-XXXX-XXXX
//        int numero = (int)(Math.random()*(-99999999 +1)+99999999); // Opcion 2 para crear un número random

        int numero = new Random().nextInt(1000);
        String tresDigitos = String.format("%0" + 3 + "d", numero);
        int cvv = Integer.parseInt(tresDigitos);
        return cvv;
    }
}
