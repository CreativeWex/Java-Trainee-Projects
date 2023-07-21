package edu.school21.printer;

import edu.school21.renderer.Renderer;

import java.time.LocalDateTime;

public class PrinterWithDateTimeImpl implements Printer {
    LocalDateTime localDateTime;
    Renderer renderer;

    public PrinterWithDateTimeImpl(Renderer renderer) {
        this.renderer = renderer;
        this.localDateTime = LocalDateTime.now();
    }


    @Override
    public void print(String msg) {
        renderer.render(msg);
    }
}
