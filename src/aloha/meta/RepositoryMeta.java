package aloha.meta;

import org.eclipse.core.resources.IFile;

import aloha.visitor.MethodVisitor;

public class RepositoryMeta extends AbstractClassMeta {

    private MethodVisitor visitor;

    public RepositoryMeta(IFile file) {
        super(file);
        visitor = new MethodVisitor();
        getNode().accept(visitor);
    }
}
