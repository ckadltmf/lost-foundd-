package com.example.myapplication2.ViewPages;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.ClassObject.ObjectForm;
import com.example.myapplication2.R;
import com.squareup.picasso.Picasso;

public class ViewForm extends AppCompatActivity {
    TextView mObjectTitle,mLostFound,mCategory,mPlace,mDate,mDescription,mStatus;
    ImageView ViewPostImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        ObjectForm form= (ObjectForm) getIntent().getSerializableExtra("FormObject");

        mObjectTitle=findViewById(R.id.viewpostobject);
        mLostFound=findViewById(R.id.viewpostlostfound);
        mCategory=findViewById(R.id.viewpostcategory);
        mPlace=findViewById(R.id.viewpostplace);
        mDate=findViewById(R.id.viewpostdate);
        mDescription=findViewById(R.id.viewpostdescription);
        mStatus=findViewById(R.id.viewpoststatus);
        ViewPostImage=findViewById(R.id.PostimageView);

        mCategory.setText(form.getCategory());
        mLostFound.setText(form.getHappend());
        mDescription.setText(form.getDescription());
        mObjectTitle.setText(form.getObjectTitle());
        mDate.setText(form.getDate());
        mPlace.setText(form.getPlace());
        mStatus.setText(form.getStatus());
        Picasso.get().load(form.getImg()).into(ViewPostImage);
    }
}
