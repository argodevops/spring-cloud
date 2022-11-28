package org.argo.javafoundations;

import java.util.Optional;
import java.util.Random;

public class Demonstration {
    
    public static void main(){
        WhatIsJava();
        JavaBasics();
        JavaElements();
        Operator();
        WorkWithStrings();
        RandomAndMath();
        DecisionStatements("Kirk");
        LoopingStatements();
    }

    private static void p(String text) {
        System.out.println(text);
    }

    private static void o(Object object) {
        System.out.println(String.format("%s", object));
    }

    public static void WhatIsJava(){
        p("Java is an Object-Oriented programming language with a write-once-run-anywhere philosophy...");
    }

    public static void JavaBasics(){
        p("blah blah blah");
    }

    public static void JavaElements(){
        p("Conventions");
        String reservedWord = "String";
        // Single line comment
        /*
         * Multiline comment
         */

        Optional<String> importedPackage;

        p("The java.lang package is automatically imported");
    }

    private final String finalVar;

    public Demonstration(String what) {
        finalVar = what;
        
        // Casting
        int x = 10;
        float y = x;
        Object cast = (Object) finalVar;

        // Strings
        String string1 = "string1";
        String string2 = new String("string2");
        String string3 = null;
    }

    public static void Operator() {
        int a = 10;
        int b = 4;
        int c = a + b;
        int d = a - b;
        int e = (a * a) - (a ^ 2) / (a % b) ;

        a++;
        b--;
        a+=1;
        b-=a;
        a = a + 1;
        b = b - b;

        if (a >= b) {
            p("A: " + a + "; B: " + b);
        } else {
            p("B: " + b + "; A: " + a);
        }

        Boolean alwaysTrue = (a >= b) || (b >= a);
        Boolean alwaysFalse = (a > b) && (b > a);

    }

    public static void WorkWithStrings() {
        Object o = new Object();
        p(String.valueOf(o));
        p(String.valueOf(100^99));
        String string1 = "Local scope %s";
        p(String.format(string1, "variable"));

        p(String.format("An empty initialised String exists: %b", new String()));
    }

    public static void RandomAndMath() {
        Random r = new Random();
        r.setSeed(r.nextLong());
        double normalDist = r.nextGaussian(r.nextDouble(), r.nextDouble());
        p(String.valueOf(normalDist));

        p(String.valueOf(Math.cos(Math.PI)));
    }

    public static void DecisionStatements(String switchName) {
        if (Math.PI > new Random().nextDouble()) {
            p("Big pie");
        } else {
            p("Not so big pie");
        }

        switch (switchName) {
            case "Kirk":
                p("Hello Kirk, keep up the Java");
                break;
            case "Damian":
                p("Hello Damian, let's get cracking");
                break;
            default:
                p("Nice to meet you, whoever you are");
        }

        String string1 = "equality";
        String string2 = new String("equality");
        String string3 = null;
        String string4 = string3;
        string3 = "That these things are equal is %b";

        p(String.format(string3, string1 == string2));
        p(String.format(string3, String.valueOf(string1) == String.valueOf(string2)));
        p(String.format(string3, string1.equals(string2)));

        p(String.format(string3, string3 == string4));
        p(String.format(string3, String.valueOf(string3) == String.valueOf(string4)));
        p(String.format(string3, string3.equals(string4)));

        String string5 = "short";
        String string6 = "longlonglong";
        p(String.format("string5 compares to string6 as %s", string5.compareTo(string6)));
    }

    public static void LoopingStatements(){

    }

}
