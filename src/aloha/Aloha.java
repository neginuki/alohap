package aloha;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.text.StrBuilder;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;

public class Aloha implements IEditorActionDelegate {

    private IEditorPart editor;

    @SuppressWarnings("deprecation")
    @Override
    public void run(IAction action) {
        IEditorInput editorInput = editor.getEditorInput();
        IFileEditorInput input = IFileEditorInput.class.cast(editorInput);
        IFile file = input.getFile();
        ICompilationUnit source = ICompilationUnit.class.cast(JavaCore.create(file));

        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setResolveBindings(true);
        parser.setSource(source);
        CompilationUnit node = CompilationUnit.class.cast(parser.createAST(new NullProgressMonitor()));

        FieldVisitor visitor = new FieldVisitor();
        node.accept(visitor);

        try {
            IType type = source.findPrimaryType();

            String contents = createContents(type.getElementName(), visitor.getFieldNames());

            type.createType(contents, null, true, new NullProgressMonitor());

            source.commitWorkingCopy(true, new NullProgressMonitor());
        } catch (JavaModelException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException("だめだこりゃー", e);
        }
    }

    String createContents(String className, List<String> fieldNames) {
        StrBuilder buf = new StrBuilder() //
                .append(createClassJavaDoc(className, "のメタ情報。"))
                .appendln("public class Meta {")
                .appendNewLine()
                .append(createClassJavaDoc(className, "のフィールド。"))
                .appendln("public class Fields {")
                .appendNewLine()
                .appendln("private Fields() {")
                .appendln("}")
                .append(createMetadataFields(fieldNames))
                .appendln("}")
                .append(createClassJavaDoc(className, "の制約。"))
                .appendln("public class Constrains {")
                .appendNewLine()
                .appendln("private Constrains() {")
                .appendln("}")
                .appendln("}")
                .append(createClassJavaDoc(className, "のエラーメッセージ。"))
                .appendln("public class Verifications {")
                .appendNewLine()
                .appendln("private Verifications() {")
                .appendln("}")
                .appendln("}")
                .appendln("}");

        return buf.toString();
    }

    private Object createMetadataFields(List<String> fieldNames) {
        StrBuilder buf = new StrBuilder();

        fieldNames.stream().forEach(fieldName -> {
            buf.appendNewLine() //
                    .appendln("/** " + fieldName + " */")
                    .appendln("public static final String " + fieldName.toUpperCase() + " = \"" + fieldName + "\";");
        });

        return buf.toString();
    }

    String createClassJavaDoc(String className, String description) {
        return new StrBuilder() //
                .appendln("/**")
                .appendln(" * {@link " + className + "} " + description)
                .appendln(" * @author Aloha-Eclipse-Plugin")
                .appendln(" */")
                .toString();
    }

    class FieldVisitor extends ASTVisitor {
        List<String> fieldNames = new ArrayList<>();

        public boolean visit(FieldDeclaration node) {
            FieldDetailVisitor visitor = new FieldDetailVisitor();
            node.accept(visitor);

            fieldNames.add(visitor.getName());

            return super.visit(node);
        }

        public List<String> getFieldNames() {
            return fieldNames;
        }
    }

    class FieldDetailVisitor extends ASTVisitor {
        private String name;

        public boolean visit(SimpleName node) {
            name = node.getFullyQualifiedName();
            return super.visit(node);
        }

        public String getName() {
            return name;
        }
    }

    @Override
    public void selectionChanged(IAction action, ISelection selection) {
    }

    @Override
    public void setActiveEditor(IAction action, IEditorPart targetEditor) {
        this.editor = targetEditor;
    }

}
