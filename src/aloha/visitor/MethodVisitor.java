package aloha.visitor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class MethodVisitor extends ASTVisitor {

    List<String> methodNames = new ArrayList<>();

    @Override
    public boolean visit(MethodDeclaration node) {
        methodNames.add(node.getName().getFullyQualifiedName());
        return super.visit(node);
    }
}
