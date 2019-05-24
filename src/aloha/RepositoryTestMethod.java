package aloha;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;

import aloha.meta.RepositoryMeta;

public class RepositoryTestMethod extends AlohaGenerator {

    @Override
    public void run(IAction action) {
        RepositoryMeta meta = new RepositoryMeta(getCurrentFile());

        Path testDirectory = getTestDirectory(getCurrentFile());
        System.out.println("@@@@@@@@ " + testDirectory);
    }

    public java.nio.file.Path getTestDirectory(IFile current) {
        String path = current.getParent().getLocation().toString();
        String testDirectory = path.replace("/src/main/", "/src/test/");

        //IPath testMethodClass = new Path(replace + "/HogehogeTest.java");

        try {

            //java.nio.file.Path testDir = new Path(replace).toFile().getParentFile().toPath();
            return Files.createDirectories(Paths.get(testDirectory));
            //Files.createFile(testMethodClass.toFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
