package com.upmc.jamfinder.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.upmc.jamfinder.R;
import com.upmc.jamfinder.model.Jam;
import com.upmc.jamfinder.model.User;
import com.upmc.jamfinder.tools.UserTools;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JamDetailsActivity extends AppCompatActivity {

    private User mUser;
    private Jam mJam;

    @Bind(R.id.jams_details_name) TextView mJamName;
    @Bind(R.id.jam_details_creator) TextView mCreatorName;
    @Bind(R.id.jam_details_details) TextView mDetails;
    @Bind(R.id.jam_details_participants) TextView mParticipants;
    @Bind(R.id.jam_details_button_participants) ImageView mParticipantsButton;
    @Bind(R.id.jam_details_button_action) Button mActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jam_details);
        ButterKnife.bind(this);

        mUser = UserTools.getLoggedInUser(this);
        mJam = (Jam) getIntent().getExtras().get(getString(R.string.jam_details_jam_intent_key));


        mJamName.setText(mJam.getName());
        mCreatorName.setText("by "+mJam.getHost().getName());
        mDetails.setText(mJam.getDetails());
        mParticipants.setText("Participants : " + mJam.getParticipants().size());

        if(mJam.getHost().equals(mUser)){
            mActionButton.setText(getString(R.string.jam_details_edit));
        }
        else if(mJam.getParticipants().contains(mUser)){
            mActionButton.setText(getString(R.string.jam_details_not_going));
        }
        else{
            mActionButton.setText(getString(R.string.jam_details_going));
        }
    }

    @OnClick(R.id.jam_details_button_participants)
    public void showParticipant(){

    }

    @OnClick(R.id.jam_details_button_action)
    public void action(){
        String text=mActionButton.getText().toString();
        if(text.equals(getString(R.string.jam_details_edit))){
            //action for host
        }
        if(text.equals(getString(R.string.jam_details_not_going))){
            //action for participant
        }

        if(text.equals(getString(R.string.jam_details_going))){
            //action for not participant
        }
    }
}
