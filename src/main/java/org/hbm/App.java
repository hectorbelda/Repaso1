package org.hbm;

import java.io.*;
import java.util.Scanner;

public class App 
{
    static Scanner ent = new Scanner(System.in);
    public static void main( String[] args )
    {
        do {
            System.out.println("Elige una opción");
            System.out.println("1. Alta de empleado");
            System.out.println("2. Baja de empleado");
            System.out.println("3. Listar los empleados");
            System.out.println("4. Consulta de empleado");
            System.out.println("0. Salir del programa");

            int i = ent.nextInt();

            switch (i)
            {
                case 1: alta(); break;
                case 2: baja(); break;
                case 3: mostrar(); break;
                case 4: consulta(); break;
                case 0: System.exit(0);
                default:
                    System.out.println("Esa no es una opción válida");
            }
        } while (true);
    }

    public static void alta()
    {
        try(FileOutputStream fos = new FileOutputStream("empleados.dat", true);
            DataOutputStream dos = new DataOutputStream(fos);)
        {
            System.out.println("Introduce el ID del empleado: ");
            long id = ent.nextLong();
            ent.nextLine();
            System.out.println("Introduce el NOMBRE del empleado: ");
            String nom = ent.nextLine();
            System.out.println("Introduce el SALARIO del empleado: ");
            double salario = ent.nextDouble();
            dos.writeLong(id);
            dos.writeUTF(nom);
            dos.writeDouble(salario);
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }
    }

    public static void baja()
    {
        File f = new File ("empleados.dat");
        File f2 = new File ("empleados.new");
        if (f.exists())
        {
            try(FileInputStream fis = new FileInputStream(f);
                DataInputStream dis = new DataInputStream(fis);
                FileOutputStream fos = new FileOutputStream(f2);
                DataOutputStream dos = new DataOutputStream(fos);
                )
            {
                System.out.println("¿Qué empleado quieres dar de baja? (Seleccionar por ID): ");
                long delete = ent.nextLong();
                ent.nextLine();
                while (true)
                {
                    long id = dis.readLong();
                    String nom = dis.readUTF();
                    double salario = dis.readDouble();
                    if (id != delete)
                    {
                        dos.writeLong(id);
                        dos.writeUTF(nom);
                        dos.writeDouble(salario);
                    }
                }
            }
            catch (EOFException e)
            {
                f.delete();
                f2.renameTo(f);
            }
            catch (IOException e)
            {
                System.err.println(e.getMessage());
            }
        }
    }

    public static void mostrar()
    {
        File f = new File ("empleados.dat");
        if (f.exists())
        {
            try(FileInputStream fis = new FileInputStream(f);
                DataInputStream dis = new DataInputStream(fis);)
            {
                while (true)
                {
                    long id = dis.readLong();
                    String nom = dis.readUTF();
                    double salario = dis.readDouble();
                    System.out.println("ID: "+id+"\tNombre: "+nom+"\tSalario: "+salario);
                }
            }
            catch (EOFException e)
            {}
            catch (IOException e)
            {
                System.err.println(e.getMessage());
            }
        }
        else
            System.out.println("El ficheto 'empleados.dat' no existe.");
    }

    public static void consulta()
    {
        boolean empleadoEncontrado = false;
        File f = new File("empleados.dat");
        if (f.exists())
        {
            try(FileInputStream fis = new FileInputStream(f);
                DataInputStream dis = new DataInputStream(fis);)
            {
                System.out.println("¿Qué empleado quieres mostrar? (Selecciona por ID): ");
                long id2 = ent.nextLong();
                ent.nextLine();
                while (true)
                {
                    long id = dis.readLong();
                    String nom = dis.readUTF();
                    double salario = dis.readDouble();
                    if (id2 == id) {
                        System.out.println("ID: " + id + "\tNombre: " + nom + "\tSalario: " + salario);
                        empleadoEncontrado = true;
                    }
                }
            }
            catch (EOFException e)
            {}
            catch (IOException e)
            {
                System.err.println(e.getMessage());
            }
            if(!empleadoEncontrado)
                System.out.println("No hay ningún empleado con ese ID.");
        }
        else
            System.out.println("El ficheto 'empleados.dat' no existe.");
    }
}
