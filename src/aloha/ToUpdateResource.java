package aloha;

import java.util.Optional;

import org.apache.commons.lang3.text.StrBuilder;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;

import aloha.meta.ResourceMeta;

public class ToUpdateResource extends AlohaGenerator {

    @Override
    public void run(IAction action) {
        Optional<IFile> updateResource = getUpdateResource(getCurrentFile());
        if (!updateResource.isPresent()) {
            MessageDialog.openInformation(null, "toUpdateResource", "対応する UpdateResource が存在しません。");
            return;
        }

        ResourceMeta resource = new ResourceMeta(getCurrentFile());
        resource.addSourceCode(createContents(new ResourceMeta(updateResource.get())));
    }

    Optional<IFile> getUpdateResource(IFile webResource) {
        String name = webResource.getName().split("Resource")[0];
        IFile updateResource = webResource.getParent().getFile(new Path(name + "UpdateResource.java"));

        return updateResource.exists() ? Optional.of(updateResource) : Optional.empty();
    }

    String createContents(ResourceMeta updateResource) {
        String updateResourceName = updateResource.getClassName();
        final String instance = "updateResource";

        StrBuilder buf = new StrBuilder()//
                .appendln("/**")
                .appendln(" * 更新用リソースに変換します。")
                .appendln(" * @return 更新用リソース")
                .appendln(" **/")
                .appendln("public " + updateResourceName + " toUpdateResource() {")
                .appendln(updateResourceName + " " + instance + " = new " + updateResourceName + "();");

        updateResource.getFieldNames().stream().forEach(field -> {
            buf.appendln(instance + "." + field + " = this." + field + ";");
        });

        buf.appendln("return " + instance + ";")//
                .append("}");

        return buf.toString();
    }
}
