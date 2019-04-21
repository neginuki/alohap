package aloha.meta;

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
import org.eclipse.jface.text.Document;

public abstract class AbstractClassMeta {

    private String className;

    private CompilationUnit node;

    private ICompilationUnit source;

    private IType type;

    public AbstractClassMeta(IFile file) {
        source = ICompilationUnit.class.cast(JavaCore.create(file));
        @SuppressWarnings("deprecation")
        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setResolveBindings(true);
        parser.setSource(source);
        node = CompilationUnit.class.cast(parser.createAST(new NullProgressMonitor()));
        type = source.findPrimaryType();
        className = type.getElementName();
    }

    public String getClassName() {
        return className;
    }

    public CompilationUnit getNode() {
        return node;
    }

    public void addSourceCode(String sourceCode) {
        try {
            String contents = source.getBuffer().getContents().trim();
            String current = contents.substring(0, contents.length() - 1);
            contents = current + sourceCode + "\n}";
            final Document document = new Document(contents);
            source.getBuffer().setContents(document.get());
            source.save(new NullProgressMonitor(), false);
        } catch (JavaModelException e) {
            MessageDialog.openInformation(null, "toUpdateResource 失敗", "ソースコードの追加に失敗しました。 原因: " + e);
        }
    }
}
