package com.pucmm.dhamarmj;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class EstudianteHandler {

    public void estudianteHandler() {
        CreateSomeStudents();
        startup();
    }

    List<Estudiante> estudiantes = new ArrayList<>();
    Estudiante currentEst;
    int index =-1;

    public void startup() {

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(EstudianteHandler.class, "/templates");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);

        get("/Estudiante/", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("titulo", "Crear Estudiante");
            attributes.put("list", estudiantes);
            return new ModelAndView(attributes, "tableEst");
        }, freeMarkerEngine);


        get("/crearEstudiante/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("titulo", "Crear Estudiante");
            return new ModelAndView(attributes, "formCreateEst");
        }, freeMarkerEngine);

        get("/editarEstudiante/:matricula", (request, response) -> {
            FindStudent(Integer.parseInt(request.params("matricula")));
            Estudiante est = currentEst;
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("titulo", "Actualizar Estudiante");
            attributes.put("estudiante", est);
            return new ModelAndView(attributes, "formEdit");
        }, freeMarkerEngine);

        get("/borrarEstudiante/:matricula", (request, response) -> {
            FindStudent(Integer.parseInt(request.params("matricula")));
            Estudiante est = currentEst;
            estudiantes.remove(est);
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("titulo", "Eliminar Estudiante");
            attributes.put("estudiante", est);
            attributes.put("eliminar", true);
            return new ModelAndView(attributes, "showEst");
        }, freeMarkerEngine);

        post("/guardarEstudiante/", (request, response) -> {

            Estudiante estudiante= new Estudiante(
                        Integer.parseInt(request.queryParams("matricula")),
                        request.queryParams("nombre") ,
                        request.queryParams("apellido") ,
                        request.queryParams("telefono"));
            estudiantes.add(estudiante);
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("titulo", "Salvando Estudiante");
            attributes.put("estudiante", estudiante);
            attributes.put("eliminar", false);
            return new ModelAndView(attributes, "showEst");
        }, freeMarkerEngine);

        get ("/actualizarEstudiante/", (request, response) -> {

            FindStudent(Integer.parseInt(request.queryParams("matricula")));
            Estudiante e = currentEst;
                e.setMatricula(Integer.parseInt(request.queryParams("matricula")));
                e.setNombre(request.queryParams("nombre"));
                e.setApellido(request.queryParams("apellido"));
                e.setTelefono(request.queryParams("telefono"));

                estudiantes.set (index, e);

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("titulo", "Actualizando Estudiante");
            attributes.put("estudiante", e);
            attributes.put("eliminar", false);
            return new ModelAndView(attributes, "showEst");
        }, freeMarkerEngine);
    }
    public void CreateSomeStudents(){
        Estudiante estudiante;
        for (int i=1; i< 6; i++)
        {
            estudiante= new Estudiante(
                    i + 20155000,
                    "Juan" + i,
                    "Perez" + i,
                    "809-528-965" + i);
            estudiantes.add(estudiante);
        }
    }

    public void FindStudent(int matricula){
        int i=0; boolean flag = false;
        while(i < estudiantes.size() && !flag)
         {
            if(estudiantes.get(i).getMatricula() == matricula){
               currentEst = estudiantes.get(i);
               index = i;
               flag = true;
            }
            i++;
        }
    }



}
