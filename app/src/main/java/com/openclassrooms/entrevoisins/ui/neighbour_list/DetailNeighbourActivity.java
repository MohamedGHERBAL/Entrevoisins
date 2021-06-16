package com.openclassrooms.entrevoisins.ui.neighbour_list;



import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;


public class DetailNeighbourActivity extends AppCompatActivity {

    public static final String EXTRA_NEIGHBOUR_ID = "EXTRA_NEIGHBOUR_INDEX";
    private Neighbour neighbour;
    private NeighbourApiService mApiService;
    private long neighbourId;

    @BindView(R.id.photo)
    ImageView mImageAvatar;

    @BindView(R.id.name_1)
    TextView mNameCard1;

    @BindView(R.id.address)
    TextView mAddress;

    @BindView(R.id.num)
    TextView mNum;
    
    @BindView(R.id.mail)
    TextView mMail;

    @BindView(R.id.aboutme)
    TextView mAboutme;
    
    @BindView(R.id.fab)
    FloatingActionButton fab;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail_neighbour);
            final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(mToolbar);

            neighbourId = getIntent().getLongExtra(EXTRA_NEIGHBOUR_ID, 0);
            mApiService = DI.getNeighbourApiService();
            ButterKnife.bind(this);
            neighbour = mApiService.getNeighboursById(neighbourId);

            initView();
            init();
            
            // Calling the action bar
            ActionBar actionBar = getSupportActionBar();
            // Showing the back button in action bar
            actionBar.setDisplayHomeAsUpEnabled(true);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

            fab.setOnClickListener(v -> setFavoriteMsg());

            AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar);

            mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                boolean isShow = false;
                int scrollRange = -1;

                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    if (scrollRange == -1) {
                        scrollRange = appBarLayout.getTotalScrollRange();
                    }
                }
            });
        }
        
        private void init() {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mApiService.checkStatusFab(neighbour.getId());
                    neighbour = mApiService.getNeighboursById(neighbourId);

                    initView();
                }
            });
        }

        private void initView() {
            setTitle(neighbour.getName());
            mNameCard1.setText(neighbour.getName());

            Glide.with(this)
                    .load(neighbour.getAvatarUrl())
                    .into(mImageAvatar);

            mAddress.setText(neighbour.getAddress());
            mNum.setText(neighbour.getPhoneNumber());
            mMail.setText("www.facebook.fr/" + neighbour.getName());
            mAboutme.setText(neighbour.getAboutMe());

            initStar();
        }

        private void initStar() {
            if (neighbour.getFavorite()) {
                fab.setImageResource(R.drawable.ic_baseline_star_24dp);
            } else {
                fab.setImageResource(R.drawable.ic_baseline_star_border_24);
            }
        }

        public void setFavoriteMsg() {
            if (mApiService.getFavorite(neighbour)) {
                mApiService.removeFavoriteNeighbourForToast(neighbour);

                Toast.makeText(this,
                        "Vous avez supprimé "+neighbour.getName()+" de vos favoris !",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                mApiService.addNeighbourFavorite(neighbour);

                Toast.makeText(this,
                        "Vous avez ajouté "+neighbour.getName()+" dans vos favoris !",
                        Toast.LENGTH_SHORT).show();
            }
            initStar();
        }

    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_scrolling, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            /*
            ** This event will enable the back function to the button on press
            */
            switch (item.getItemId()) {
                case android.R.id.home:
                    this.finish();
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }

}
