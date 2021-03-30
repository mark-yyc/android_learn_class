package com.byted.camp.todolist.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.byted.camp.todolist.R;

public class NoteDialog extends Dialog implements View.OnClickListener{
    private EditText editText;
    private Button btCancel,btConfirm;
    private IOnCancelListener cancelListener;
    private IOnConfirmListener confirmListener;

    public void setCancel(IOnCancelListener cancelListener) {
        this.cancelListener=cancelListener;
    }
    public void setConfirm(IOnConfirmListener confirmListener){
        this.confirmListener=confirmListener;
    }

    public NoteDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.layout_alert_dialogue);
        editText=findViewById(R.id.edit_note);
        btCancel=findViewById(R.id.bt_cancel);
        btConfirm=findViewById(R.id.bt_confirm);

        btCancel.setOnClickListener(this);
        btConfirm.setOnClickListener(this);
    }

    public String getEditTextContent(){
        return editText.getText().toString();
    }

    public void setEditTextContent(String content){
        editText.setText(content);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_cancel && cancelListener != null) {
            cancelListener.onCancel(this);
        }
        if (view.getId() == R.id.bt_confirm && confirmListener != null) {
            confirmListener.onConfirm(this);
        }
    }
    public interface IOnCancelListener{
        void onCancel(NoteDialog dialog);
    }
    public interface IOnConfirmListener{
        void onConfirm(NoteDialog dialog);
    }
}
