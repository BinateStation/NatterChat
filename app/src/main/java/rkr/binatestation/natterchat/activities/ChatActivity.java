package rkr.binatestation.natterchat.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import rkr.binatestation.natterchat.R;
import rkr.binatestation.natterchat.fragments.ListFragment;
import rkr.binatestation.natterchat.models.ChatContactModel;
import rkr.binatestation.natterchat.models.ChatMessageModel;
import rkr.binatestation.natterchat.models.EmptyStateModel;
import rkr.binatestation.natterchat.models.Status;
import rkr.binatestation.natterchat.utils.SessionUtils;

import static rkr.binatestation.natterchat.utils.Constant.KEY_ACTION_CHAT_MESSAGE;
import static rkr.binatestation.natterchat.utils.Constant.KEY_TABLE_CHATS;

public class ChatActivity extends BaseActivity implements View.OnClickListener {

    private static final String KEY_CHAT_CONTACT_MODEL = "CHAT_CONTACT_MODEL";
    private static final String TAG = "ChatActivity";
    private ListFragment mListFragment;
    private EditText mFieldMessageEditText;
    private ChatContactModel mChatContactModel;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive() called with: context = [" + context + "], intent = [" + intent + "]");
        }
    };

    public static Intent newInstance(Context context, ChatContactModel chatContactModel) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(KEY_CHAT_CONTACT_MODEL, chatContactModel);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setBackActionEnabled();
        mChatContactModel = getIntent().getParcelableExtra(KEY_CHAT_CONTACT_MODEL);
        mFieldMessageEditText = findViewById(R.id.field_message);
        View actionSendMessage = findViewById(R.id.action_send_message);
        mListFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.list_fragment);
        if (mListFragment != null) {
            mListFragment.getLinearLayoutManager().setStackFromEnd(true);
            setData();
        }

        actionSendMessage.setOnClickListener(this);
    }

    private void setData() {
        if (mChatContactModel != null && mListFragment != null) {
            ArrayList<Object> data = ChatContactModel.getData(mChatContactModel.getChatMessages());
            if (data.size() > 0) {
                mListFragment.getAdapter().setData(data);
            } else {
                mListFragment.getAdapter().addEmptyState(EmptyStateModel.getChatMessageEmptyState());
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter(KEY_ACTION_CHAT_MESSAGE));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mChatContactModel != null && mChatContactModel.getReceiver() != null) {
            SessionUtils.setChatResumed(
                    this,
                    true,
                    mChatContactModel.getReceiver().getId()
            );
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SessionUtils.setChatResumed(this, false, "");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.action_send_message) {
            readInput();
        }
    }


    private void readInput() {
        if (mFieldMessageEditText != null) {
            String message = mFieldMessageEditText.getText().toString().trim();
            if (TextUtils.isEmpty(message)) {
                mFieldMessageEditText.setError(getString(R.string.type_a_message));
                mFieldMessageEditText.requestFocus();
            } else {
                actionSendMessage(message);
            }
        }
    }

    private void actionSendMessage(String message) {
        long time = Calendar.getInstance().getTimeInMillis();
        ChatMessageModel chatMessageModel = new ChatMessageModel(
                mChatContactModel.getId() + time,
                message,
                time,
                Status.PENDING.getValue(),
                mChatContactModel.getSender()
        );
        mFieldMessageEditText.setText("");
        addMessage(chatMessageModel);
        sendMessage(chatMessageModel);
    }

    private void addMessage(ChatMessageModel chatMessageModel) {
        if (mListFragment != null) {
            mListFragment.getAdapter().add(chatMessageModel);
            mListFragment.scrollToEnd();
        }
    }

    private void sendMessage(ChatMessageModel chatMessageModel) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        mChatContactModel.getChatMessages().add(chatMessageModel);
        myRef.child(KEY_TABLE_CHATS).child(mChatContactModel.getId()).setValue(mChatContactModel);
    }
}
