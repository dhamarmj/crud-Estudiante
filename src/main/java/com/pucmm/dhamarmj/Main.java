package com.pucmm.dhamarmj;

import static spark.Spark.port;

public class Main {
    public static void main(String[] args) throws Exception {

        if(args.length >0)
            port(Integer.parseInt(args[0]));

        new EstudianteHandler().estudianteHandler();
    }
}
