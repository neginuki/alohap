package aloha;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;

public abstract class AlohaGenerator implements IEditorActionDelegate {

    private IEditorPart editor;

    protected IFile getActiveFile() {
        IEditorInput editorInput = editor.getEditorInput();
        IFileEditorInput input = IFileEditorInput.class.cast(editorInput);
        return input.getFile();
    }

    @Override
    public void selectionChanged(IAction action, ISelection selection) {
    }

    @Override
    public void setActiveEditor(IAction action, IEditorPart targetEditor) {
        this.editor = targetEditor;
    }

    public IFile getCurrentFile() {
        IEditorInput editorInput = editor.getEditorInput();
        IFileEditorInput input = IFileEditorInput.class.cast(editorInput);
        return input.getFile();
    }
}
