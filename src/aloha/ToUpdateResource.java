package aloha;

import java.util.Optional;

import org.apache.commons.lang3.text.StrBuilder;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;

public class ToUpdateResource extends AlohaGenerator {

    @Override
    public void run(IAction action) {
        Optional<IFile> inp = getUpdateResource(getCurrentFile());
        if (!inp.isPresent()) {
            MessageDialog.openInformation(null, "toUpdateResource", "対応する UpdateResource が存在しません。");
            return;
        }

        String sourceCode = createContents(new ResourceMeta(inp.get()));
        //new ResourceMeta(getCurrentFile()).addSourceCode(sourceCode);
        new ResourceMeta(getCurrentFile()).addSourceCode("private String hoge;");
    }

    Optional<IFile> getUpdateResource(IFile webResource) {
        String name = webResource.getName().split("Resource")[0];
        IFile updateResource = webResource.getParent().getFile(new Path(name + "UpdateResource.java"));

        return updateResource.exists() ? Optional.of(updateResource) : Optional.empty();
    }

    String createContents(ResourceMeta updateResource) {
        String updateResourceName = updateResource.getClassName();
        final String instance = "updateResource";

        StrBuilder buf = new StrBuilder().appendln("public " + updateResourceName + " toUpdateResource() {")
                .appendln(updateResourceName + " " + instance + " = new " + updateResourceName + "();");

        updateResource.getFieldNames().stream().forEach(field -> {
            buf.appendln(instance + "." + field + " = this." + field + ";");
        });

        buf.appendln("return " + instance + ";")//
                .append("}");

        return buf.toString();
    }
}
