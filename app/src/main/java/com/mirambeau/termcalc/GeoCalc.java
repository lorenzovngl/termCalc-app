package com.mirambeau.termcalc;

public class GeoCalc extends Ax {
    static final double pi = 3.141592;

    final static String[] twoD = {"Square", "Rectangle", "Circle", "Ellipse", "Triangle", "Trapezoid", "Parallelogram", "Pentagon", "Hexagon", "Heptagon", "Octagon", "Nonagon", "Decagon"};
    final static String[] threeD = {"Cube", "Rectangular Prism", "Sphere", "Hemisphere", "Triangular Prism", "Pyramid (Triangular Base)", "Pyramid (Rectangular Base)",
            "Pentagonal Prism", "Cone", "Cylinder", "Regular Octahedron", "Dodecahedron", "Torus"};

    public boolean hasString(String[] array, String str, int numElements){
        int i;

        if (array == null || array.length == 0 || isNull(str))
            return false;

        if (numElements == 0) {
            for (i = 0; i < array.length; i++) {
                if (array[i].equals(str))
                    return true;
            }
        }
        else {
            for (i = 0; i < numElements; i++) {
                if (array[i].equals(str))
                    return true;
            }
        }

        return false;
    }

    public static double[] getInputs(String[] inputs){
        int i;

        double[] n = new double[inputs.length];

        for (i=0; i < inputs.length; i++){
            if (!isNull(inputs[i]) && isFullNum(inputs[i])){
                n[i] = Double.parseDouble(inputs[i]);
            }
        }

        return n;
    }

    public static double getNumSides(String shape){
        int i;

        String[] polygons = {"pentagon", "hexagon", "heptagon", "octagon", "nonagon", "decagon"};

        for (i=0; i < polygons.length; i++){
            if (shape.equals(polygons[i])){
                return i + 5;
            }
        }

        return 1;
    }

    public static String correctShapeTitle(String shape){
        if (shape == null || shape.equals("\0") || shape.length() < 1)
            return null;

        int i;

        for (i=0; i < twoD.length; i++){
            if (shape.equalsIgnoreCase(threeD[i]))
                return twoD[i].toLowerCase();
            if (shape.equalsIgnoreCase(twoD[i]))
                return shape.toLowerCase();
        }

        return shape;
    }

    public static double area(String shape, String[] inputs){
        double[] n = getInputs(inputs);

        switch (shape) {
            case "square": return Math.pow(n[0], 2);
            case "rectangle": return n[0] * n[1];
            case "circle": return Math.pow(n[0], 2) * pi;
            case "ellipse": return pi * n[0] * n[1];
            case "triangle": return (n[0] * n[1]) / 2.0;
            case "trapezoid": return ((n[0] + n[1]) / 2.0) * n[2];
            case "parallelogram": return n[1] * n[0];
            case "pentagon": case "hexagon": case "heptagon": case "octagon": case "nonagon": case "decagon":
                return (getNumSides(shape) / 2.0) * n[0] * n[1];
        }

        return -1;
    }

    public static double volume(String shape, String[] inputs){
        double[] n = getInputs(inputs);

        switch (correctShapeTitle(shape)) {
            case "square": return Math.pow(n[0], 3);
            case "rectangle": return n[0] * n[1] * n[2];
            case "circle": return Math.pow(n[0], 3) * pi * (4.0 / 3.0);
            case "ellipse": return (2.0 / 3.0) * pi * Math.pow(n[0], 3);
            case "triangle": return (n[0] * n[1] * n[2]) / 2.0;
            //Pyramids
            case "trapezoid": return (n[0] * n[1]) / 3;
            case "parallelogram": return (n[0] * n[1] * n[2]) / 3.0;

            case "pentagon": return 2.5 * n[0] * n[1] * n[2];
            //Cone
            case "hexagon": return (pi * n[1] * Math.pow(n[0], 2)) / 3.0;
            //Cylinder
            case "heptagon": return pi * Math.pow(n[0], 2) * n[1];
            //Octahedron
            case "octagon": return (Math.sqrt(2) / 3.0) * Math.pow(n[0], 3);
            //Dodecahedron
            case "nonagon": return ((15 + 7 * Math.sqrt(5)) / 4.0) * Math.pow(n[0], 3);
            //Torus
            case "decagon": return pi * Math.pow(n[1], 2) * 2 * pi * n[0];
        }

        return -1;
    }

    public static double sa(String shape, String[] inputs){
        double[] n = getInputs(inputs);

        switch (correctShapeTitle(shape)) {
            case "square": return Math.pow(n[0], 2) * 6;
            case "rectangle": return 2 * ((n[0] * n[1]) + (n[0] * n[2]) + (n[1] * n[2]));
            case "circle": return Math.pow(n[0], 2) * 4 * pi;
            case "ellipse": return 3 * pi * Math.pow(n[0], 2);
            case "triangle": return (2 * (n[0] * n[1])) + (n[2] * ((2 * n[0]) + (2 * n[1])));
            //Pyramids
            case "trapezoid": return n[0] + (0.5 * n[1] * n[2]);
            case "parallelogram":return (n[0] * n[1]) + (n[0] * Math.sqrt(Math.pow(n[1] / 2, 2) + Math.pow(n[2], 2))) + (n[1] * Math.sqrt(Math.pow(n[0] / 2, 2) + Math.pow(n[2], 2)));

            case "pentagon": return 5 * n[0] * (n[1] + n[2]);
            //Cone
            case "hexagon": return pi * n[0] * (n[0] + Math.sqrt(Math.pow(n[1], 2) + Math.pow(n[0], 2)));
            //Cylinder
            case "heptagon": return 2 * pi * n[0] * (n[1] + n[0]);
            //Octahedron
            case "octagon": return 2 * Math.sqrt(3) * Math.pow(n[0], 2);
            //Dodecahedron
            case "nonagon": return 3 * Math.pow(n[0], 2) * Math.sqrt(25 + (10 * Math.sqrt(5)));
            //Torus
            case "decagon": return 4 * Math.pow(pi, 2) * n[0] * n[1];
        }

        return -1;
    }

    public static double parse(String shape, String[] inputs, int tab){
        if (tab == 0)
            return area(shape, inputs);
        else if (tab == 1)
            return volume(shape, inputs);
        else if (tab == 2)
            return sa(shape, inputs);
        else
            return -1;
    }
}
