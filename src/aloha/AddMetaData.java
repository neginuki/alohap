package aloha;

import java.util.List;

import org.apache.commons.lang3.text.StrBuilder;
import org.eclipse.jface.action.IAction;

import aloha.meta.ResourceMeta;

public class AddMetaData extends AlohaGenerator {

    @Override
    public void run(IAction action) {
        ResourceMeta resource = new ResourceMeta(getCurrentFile());
        resource.addSourceCode(createContents(resource));
    }

    String createContents(ResourceMeta meta) {
        String className = meta.getClassName();

        StrBuilder buf = new StrBuilder() //
                .append(createClassJavaDoc(className, "のメタ情報。"))
                .appendln("public class Meta {")
                .appendNewLine()
                .append(createClassJavaDoc(className, "のフィールド。"))
                .appendln("public class Fields {")
                .appendNewLine()
                .appendln("private Fields() {")
                .appendln("}")
                .append(createMetadataFields(meta.getFieldNames()))
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
}
