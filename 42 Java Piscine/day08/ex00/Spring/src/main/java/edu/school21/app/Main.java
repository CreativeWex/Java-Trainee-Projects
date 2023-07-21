package edu.school21.app;

import edu.school21.preprocessor.PreProcessor;
import edu.school21.preprocessor.PreProcessorToUpperImpl;
import edu.school21.printer.PrinterWithPrefixImpl;
import edu.school21.renderer.Renderer;
import edu.school21.renderer.RendererErrImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext printerWithPrefix = new ClassPathXmlApplicationContext("context.xml");
        PrinterWithPrefixImpl printer = printerWithPrefix.getBean("printerErrWithPrefix", PrinterWithPrefixImpl.class);
        printer.print("Hello!");

//        PreProcessor preProcessor = new PreProcessorToUpperImpl();
//        Renderer renderer = new RendererErrImpl(preProcessor);
//        PrinterWithPrefixImpl printer = new PrinterWithPrefixImpl(renderer);
//        printer.setPrefix("Prefix ");
//        printer.print("Hello!") ;
    }
}
