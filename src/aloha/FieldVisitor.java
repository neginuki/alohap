package aloha;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;

public class FieldVisitor extends ASTVisitor {
    List<String> fieldNames = new ArrayList<>();

    public boolean visit(FieldDeclaration node) {
        FieldDetailVisitor visitor = new FieldDetailVisitor();
        node.accept(visitor);

        System.out.println(visitor.getName().charAt(0));
        if (Character.isLowerCase(visitor.getName().charAt(0))) { // フィールド名の先頭一文字が小文字なら static final のフィールドじゃないだろうという解釈で
            fieldNames.add(visitor.getName());
        }

        return super.visit(node);
    }

    public List<String> getFieldNames() {
        return fieldNames;
    }
}
