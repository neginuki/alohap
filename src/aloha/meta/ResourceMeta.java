package aloha.meta;

import java.util.List;

import org.eclipse.core.resources.IFile;

import aloha.visitor.FieldVisitor;

public class ResourceMeta extends AbstractClassMeta {

    private FieldVisitor visitor;

    public ResourceMeta(IFile file) {
        super(file);
        visitor = new FieldVisitor();
        getNode().accept(visitor);
    }

    public List<String> getFieldNames() {
        return visitor.getFieldNames();
    }
}
