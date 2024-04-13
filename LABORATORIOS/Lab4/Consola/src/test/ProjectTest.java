package test;
import domain.*;


import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ProjectTest{
    public ProjectTest() {
        
    }
    
    @Before
    public void setUp(){    
    }
    
    @After
    public void tearDown(){
    }

    @Test
    public void shouldAddSimpleActivity() throws ProjectException {
        Project p = new Project();
        try {
            p.add("simple 1", "100", "200", "");
            assertEquals(5, p.numberActivitys());
            p.add("simple 2", "1420", "300", "");
            assertEquals(6, p.numberActivitys());
            p.add("simple 3", "200", "670", "");
            assertEquals(7, p.numberActivitys()); 

            Activity a1 = p.consult("simple 1");
            Activity a2 = p.consult("simple 2");
            Activity a3 = p.consult("simple 3");

            assertTrue(a1 instanceof Simple);
            assertTrue(a2 instanceof Simple);
            assertTrue(a3 instanceof Simple);

            assertEquals("simple 1. Costo:100.Tiempo:200", a1.data());
            assertEquals("simple 2. Costo:1420.Tiempo:300", a2.data());
            assertEquals("simple 3. Costo:200.Tiempo:670", a3.data());
        } catch (ProjectException e){
            fail("Threw a exception");
        }
    }

    @Test
    public void shouldAddComposedActivity() throws ProjectException {
        Project p = new Project();
        try {
            p.add("completo 1", "100", "Paralela", "Buscar datos\nEvaluar datos");
            assertEquals(5, p.numberActivitys());
            p.add("completo 2", "1420", "Paralela", "Buscar datos\nLimpiar datos");
            assertEquals(6, p.numberActivitys());
            p.add("completo 3", "200", "Paralela", "Preparar datos");
            assertEquals(7, p.numberActivitys()); 

            Activity a1 = p.consult("completo 1");
            Activity a2 = p.consult("completo 2");
            Activity a3 = p.consult("completo 3");

            assertTrue(a1 instanceof Composed);
            assertTrue(a2 instanceof Composed);
            assertTrue(a3 instanceof Composed);

            assertEquals("completo 1. Tipo Paralela. \n" +
                 "\tBuscar datos. Costo:50.Tiempo:50\n" +
                 "\tEvaluar datos. Costo:80.Tiempo:80", a1.data());
            assertEquals("completo 2. Tipo Paralela. \n" +
                 "\tBuscar datos. Costo:50.Tiempo:50\n" +
                 "\tLimpiar datos. Costo:100.Tiempo:100", a2.data());
            assertEquals("completo 3. Tipo Paralela. \n" +
                 "\tPreparar datos. Tipo Secuencial. \n" +
                 "\tBuscar datos. Costo:50.Tiempo:50\n" +
                 "\tEvaluar datos. Costo:80.Tiempo:80\n" +
                 "\tLimpiar datos. Costo:100.Tiempo:100", a3.data());
        } catch (ProjectException e){
            fail("Threw a exception");
        }
    }

    @Test
    public void shouldListActivities() {
        Project p = new Project();
        try {
            p.add("simple 1", "100", "200", "");
            p.add("completo 1", "100", "Paralela", "Buscar datos\nEvaluar datos");

            assertEquals("6 actividades\n" +
            ">completo 1. Tipo Paralela. \n" +
            "\tBuscar datos. Costo:50.Tiempo:50\n" +
            "\tEvaluar datos. Costo:80.Tiempo:80\n" +
            ">Buscar datos. Costo:50.Tiempo:50\n" +
            ">Limpiar datos. Costo:100.Tiempo:100\n" +
            ">simple 1. Costo:100.Tiempo:200\n" +
            ">Evaluar datos. Costo:80.Tiempo:80\n" +
            ">Preparar datos. Tipo Secuencial. \n" +
            "\tBuscar datos. Costo:50.Tiempo:50\n" +
            "\tEvaluar datos. Costo:80.Tiempo:80\n" +
            "\tLimpiar datos. Costo:100.Tiempo:100\n", p.toString());
        } catch (ProjectException e) {
            fail("Threw a exception");
        }
    }
}