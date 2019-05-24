package aloha.meta;

import java.util.List;

import org.eclipse.core.resources.IFile;

import aloha.visitor.MethodVisitor;
import aloha.visitor.MethodVisitor.MethodDefinition;

public class RepositoryMeta extends AbstractClassMeta {

    private MethodVisitor visitor;

    public RepositoryMeta(IFile file) {
        super(file);
        visitor = new MethodVisitor();
        getNode().accept(visitor);
    }

    public List<MethodDefinition> getMethods() {
        return visitor.getMethods();
    }
}
