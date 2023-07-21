package edu.school21.printer.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import edu.school21.printer.logic.Logic;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Parameters(separators = "=")

public class Program {
    @Parameter(names = {"--white", "-w"}, required = true)
    private String white;

    @Parameter(names = {"--black", "-b"}, required = true)
    private String black;

    public static void main(String[] args) throws IOException {
        Program program = new Program();
        JCommander.newBuilder()
                .addObject(program)
                .build()
                .parse(args);

        BufferedImage image = ImageIO.read(Program.class.getResource("/resources/it.bmp"));
        Logic logic = new Logic(image, program.white, program.black);

        logic.setCharsFromImage();
    }
}