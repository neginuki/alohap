package aloha;

import org.eclipse.jface.action.IAction;

import aloha.meta.RepositoryMeta;

public class RepositoryTestMethod extends AlohaGenerator {

    @Override
    public void run(IAction action) {
        RepositoryMeta meta = new RepositoryMeta(getCurrentFile());
    }

}
