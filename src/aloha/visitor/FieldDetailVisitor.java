package aloha.visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.SimpleName;

public class FieldDetailVisitor extends ASTVisitor {
    private String name;

    public boolean visit(SimpleName node) {
        name = node.getFullyQualifiedName();
        return super.visit(node);
    }

    public String getName() {
        return name;
    }
}
