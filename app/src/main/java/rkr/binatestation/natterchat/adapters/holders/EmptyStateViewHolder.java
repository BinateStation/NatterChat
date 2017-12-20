package rkr.binatestation.natterchat.adapters.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import rkr.binatestation.natterchat.R;
import rkr.binatestation.natterchat.adapters.RecyclerViewAdapter;
import rkr.binatestation.natterchat.models.EmptyStateModel;


/**
 * Created by RKR on 21-09-2017.
 * EmptyStateViewHolder
 */

public class EmptyStateViewHolder extends RecyclerViewAdapterBaseViewHolder {
    public static final int LAYOUT_ID = R.layout.adapter_empty_state;

    private ImageView iconImageView;
    private TextView titleTextView;
    private TextView messageTextView;

    public EmptyStateViewHolder(View itemView, RecyclerViewAdapter adapter) {
        super(itemView, adapter);

        iconImageView = itemView.findViewById(R.id.icon);
        titleTextView = itemView.findViewById(R.id.title);
        messageTextView = itemView.findViewById(R.id.message);
    }

    public void bindView(Object object) {
        if (object instanceof EmptyStateModel) {
            EmptyStateModel emptyStateModel = (EmptyStateModel) object;
            iconImageView.setImageResource(emptyStateModel.getIcon());
            titleTextView.setText(emptyStateModel.getTitle());
            messageTextView.setText(emptyStateModel.getMessage());
        }
    }
}
