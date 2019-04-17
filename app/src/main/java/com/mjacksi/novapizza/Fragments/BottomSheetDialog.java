package com.mjacksi.novapizza.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mjacksi.novapizza.R;

import androidx.annotation.Nullable;

public class BottomSheetDialog extends BottomSheetDialogFragment {

    SheetListener listener;

    public BottomSheetDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout, container, false);

        TextView totalAmount = v.findViewById(R.id.amount);
        TextView address = v.findViewById(R.id.address);

        if (getArguments() != null) {
            totalAmount.setText(getArguments().getString("totalAmount"));
            address.setText(getArguments().getString("address"));
        }

        Button submitOrder = v.findViewById(R.id.order_now_button);
        submitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonClicked();
                dismiss();
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (SheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " should implement the SheetListener interface");
        }
    }

    public interface SheetListener {
        void onButtonClicked();
    }
}
