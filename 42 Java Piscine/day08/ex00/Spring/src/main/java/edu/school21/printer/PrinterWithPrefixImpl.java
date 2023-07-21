package edu.school21.printer;

import edu.school21.renderer.Renderer;

public class PrinterWithPrefixImpl implements Printer{
    Renderer renderer;
    String prefix = "Prefix ";

    public PrinterWithPrefixImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void print(String msg) {
        renderer.render(prefix + msg);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
