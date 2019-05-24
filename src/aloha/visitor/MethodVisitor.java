package aloha.visitor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;

public class MethodVisitor extends ASTVisitor {

    private List<MethodDefinition> methods = new ArrayList<>();

    @Override
    public boolean visit(MethodDeclaration node) {
        methods.add(new MethodDefinition(node));
        return super.visit(node);
    }

    public List<MethodDefinition> getMethods() {
        return methods;
    }

    public static class MethodDefinition {

        public final String name;
        public final Type returnType;
        public final List<SingleVariableDeclaration> parameters;

        @SuppressWarnings("unchecked")
        public MethodDefinition(MethodDeclaration method) {
            name = method.getName().getFullyQualifiedName();
            returnType = method.getReturnType2();
            parameters = method.parameters();
        }
    }
}
