package aloha;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.dialogs.MessageDialog;

public class ResourceMeta {

    private String className;

    private ICompilationUnit source;

    private FieldVisitor visitor;

    private IType type;

    public ResourceMeta(IFile file) {
        source = ICompilationUnit.class.cast(JavaCore.create(file));
        @SuppressWarnings("deprecation")
        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setResolveBindings(true);
        parser.setSource(source);
        CompilationUnit node = CompilationUnit.class.cast(parser.createAST(new NullProgressMonitor()));

        visitor = new FieldVisitor();
        node.accept(visitor);

        type = source.findPrimaryType();
        className = type.getElementName();
    }

    public String getClassName() {
        return className;
    }

    public List<String> getFieldNames() {
        return visitor.getFieldNames();
    }

    public void addSourceCode(String sourceCode) {
        try {
            type.createType(sourceCode, null, true, new NullProgressMonitor());
            source.commitWorkingCopy(true, new NullProgressMonitor());
        } catch (JavaModelException e) {
            MessageDialog.openInformation(null, "toUpdateResource 失敗", "ソースコードの追加に失敗しました。 原因: " + e);
        }
    }
}
