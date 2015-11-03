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

public class JamDetailsActivity extends AppCompatActivity {

    private User mUser;
    private Jam mJam;

    private TextView mJamName;
    private TextView mCreatorName;
    private TextView mDetails;
    private TextView mParticipants;
    private ImageView mParticipantsButton;
    private Button mActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jam_details);

        mUser = UserTools.getLoggedInUser(this);
        mJam = (Jam) getIntent().getExtras().get(getString(R.string.jam_details_jam_intent_key));

        mJamName = (TextView) findViewById(R.id.jams_details_name);
        mCreatorName = (TextView) findViewById(R.id.jam_details_creator);
        mDetails = (TextView) findViewById(R.id.jam_details_details);
        mParticipants = (TextView) findViewById(R.id.jam_details_participants);
        mParticipantsButton = (ImageView) findViewById(R.id.jam_details_button_participants);
        mActionButton = (Button) findViewById(R.id.jam_details_button_action);

        mJamName.setText(mJam.getName());
        mCreatorName.setText(mJam.getHost().getName());
        mDetails.setText(mJam.getDetails());
        mParticipants.setText("Participants : " + mJam.getParticipants().size() );
        mParticipantsButton.setOnClickListener(mOnClick);
        if(mJam.getHost().equals(mUser)){
            mActionButton.setText(getString(R.string.jam_details_edit));
        }
        else if(mJam.getParticipants().contains(mUser)){
            mActionButton.setText(getString(R.string.jam_details_not_going));
        }
        else{
            mActionButton.setText(getString(R.string.jam_details_going));
        }
        mActionButton.setOnClickListener(mOnClick);

    }

    private View.OnClickListener mOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == mParticipantsButton.getId()){

            }
            else if(v.getId() == mActionButton.getId()){

            }
        }
    };

}
