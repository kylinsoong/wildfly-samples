package org.jboss.shrinkwrap.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

public class Example {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        //1. create a java archive
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class);
        File out = new File("target/test.jar");
        out.getParentFile().mkdirs();
        ZipExporter exporter = archive.as(ZipExporter.class);
        try (FileOutputStream fos = new FileOutputStream(out)){
            exporter.exportTo(fos);
        }
        
        System.out.println(archive);
    }

}
