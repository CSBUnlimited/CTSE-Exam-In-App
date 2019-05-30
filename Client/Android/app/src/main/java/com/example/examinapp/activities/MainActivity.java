package com.example.examinapp.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.examinapp.R;
import com.example.examinapp.adapters.ExamListAdapter;
import com.example.examinapp.enums.MainActivityViewEnum;
import com.example.examinapp.enums.NextScreenEnum;
import com.example.examinapp.enums.UserTypeEnum;
import com.example.examinapp.models.ExamModel;
import com.example.examinapp.models.LoadingInformationModel;
import com.example.examinapp.models.UserModel;
import com.example.examinapp.viewmodels.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MainActivityViewModel _mainActivityViewModel;

    private LinearLayout _fetchingExamsProgressLinearLayout;
    private TextInputEditText _searchExamTextInputEditText;
    private TextView _noExamsTextView;
    private ListView _examsListView;
    private LinearLayout _fetchingExamsErrorLinearLayout;
    private Button _tryAgainFetchingExamsButton;
    private FloatingActionButton _addNewExamFloatingActionButton;

    private List<ExamModel> _exams = new ArrayList<>();
    private LoadingInformationModel _loadingInformationModel;
    private MainActivityViewEnum _mainActivityViewEnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.addQuestionFloatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);



//        View view = findViewById(R.id.includeViewMain);
//        view.setVisibility(View.VISIBLE);

        // Get activity controllers
        _fetchingExamsProgressLinearLayout = findViewById(R.id.fetchingExamsProgressLinearLayout);
        _examsListView = findViewById(R.id.examsListView);
        _noExamsTextView = findViewById(R.id.noExamsTextView);
        _searchExamTextInputEditText = findViewById(R.id.searchExamTextInputEditText);
        _fetchingExamsErrorLinearLayout = findViewById(R.id.fetchingExamsErrorLinearLayout);
        _tryAgainFetchingExamsButton = findViewById(R.id.tryAgainFetchingExamsButton);
        _addNewExamFloatingActionButton = findViewById(R.id.addNewExamFloatingActionButton);
        _addNewExamFloatingActionButton.hide();

        // View model controllers
        _mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        _mainActivityViewModel.init();

        UserModel userModel = _mainActivityViewModel.getLoggedInUserModel();

        ((TextView)navigationView.getHeaderView(0).findViewById(R.id.peronNameTextView)).setText(userModel.getName());
        ((TextView)navigationView.getHeaderView(0).findViewById(R.id.peronUserTypeTextView)).setText(userModel.getUserTypeEnum().toString());

        if (userModel.getUserTypeEnum() == UserTypeEnum.Lecturer) {
            navigationView.getMenu().setGroupVisible(R.id.lectureMenuGroup, true);
            navigationView.getMenu().setGroupVisible(R.id.studentMenuGroup, false);

            _mainActivityViewEnum = MainActivityViewEnum.LecturerMyExams;
            _addNewExamFloatingActionButton.show();
        }
        else if (userModel.getUserTypeEnum() == UserTypeEnum.Student) {
            navigationView.getMenu().setGroupVisible(R.id.lectureMenuGroup, false);
            navigationView.getMenu().setGroupVisible(R.id.studentMenuGroup, true);

            _mainActivityViewEnum = MainActivityViewEnum.StudentAllExams;
            _addNewExamFloatingActionButton.hide();
        }

        // Subscribe to ViewModel call backs
        _mainActivityViewModel.getNextScreenEnum().observe(this, new Observer<NextScreenEnum>() {
            @Override
            public void onChanged(@Nullable NextScreenEnum nextScreenEnum) {
                if (nextScreenEnum == NextScreenEnum.Splash) {
                    Intent intent = new Intent(MainActivity.this, SplashActivity.class);
                    MainActivity.this.startActivity(intent);
                    finish();
                }
                else if (nextScreenEnum == NextScreenEnum.AddNewExam) {
                    Intent intent = new Intent(MainActivity.this, LecturerManageExamActivity.class);
                    MainActivity.this.startActivity(intent);
                }
            }
        });

        _mainActivityViewModel.getMainActivityViewEnum().observe(this, new Observer<MainActivityViewEnum>() {
            @Override
            public void onChanged(@Nullable MainActivityViewEnum mainActivityViewEnum) {

            }
        });

        _mainActivityViewModel.getExamList().observe(this, new Observer<List<ExamModel>>() {
            @Override
            public void onChanged(@Nullable List<ExamModel> examModels) {
                _exams = examModels;
                manageShowingExamList();
            }
        });

        _mainActivityViewModel.getExamsLoadingInformationModelData().observe(this, new Observer<LoadingInformationModel>() {
            @Override
            public void onChanged(@Nullable LoadingInformationModel loadingInformationModel) {
                _loadingInformationModel = loadingInformationModel;
                manageShowingExamList();
            }
        });

        // Activity controller actions
        _tryAgainFetchingExamsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mainActivityViewModel.setMainActivityViewEnumAndRequestData(_mainActivityViewEnum);
            }
        });

        _searchExamTextInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                _mainActivityViewModel.setSearchText(s.toString());
            }
        });

        _addNewExamFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickedView) {
//                Toast.makeText(clickedView.getContext(), "new " + "0", Toast.LENGTH_LONG).show();
                _mainActivityViewModel.navigateToAddNewExam();
            }
        });
    }

    private void manageShowingExamList() {
        if (_loadingInformationModel == null) {
            return;
        }

        _fetchingExamsProgressLinearLayout.setVisibility(View.GONE);
        _fetchingExamsErrorLinearLayout.setVisibility(View.GONE);
        _examsListView.setVisibility(View.GONE);
        _noExamsTextView.setVisibility(View.GONE);

        if (_loadingInformationModel.getIsPending()) {
            _fetchingExamsProgressLinearLayout.setVisibility(View.VISIBLE);
        }
        else {
            if (_loadingInformationModel.getIsSucess()) {
                if (_exams.size() > 0) {
                    ExamListAdapter examListAdapter = new ExamListAdapter(MainActivity.this, _exams);
                    _examsListView.setAdapter(examListAdapter);
                    _examsListView.setVisibility(View.VISIBLE);
                }
                else {
                    _noExamsTextView.setVisibility(View.VISIBLE);
                }
            }
            else if (_loadingInformationModel.getIsError()) {
                _fetchingExamsErrorLinearLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            _mainActivityViewModel.logoutLoggedInUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_lec_myExams) {
            _mainActivityViewEnum = MainActivityViewEnum.LecturerMyExams;
        }
        else if (id == R.id.nav_lec_allExams) {
            _mainActivityViewEnum = MainActivityViewEnum.LecturerAllExams;
        }
        else if (id == R.id.nav_stu_allExams) {
            _mainActivityViewEnum = MainActivityViewEnum.StudentAllExams;
        }
        else if (id == R.id.nav_stu_entrolledExams) {
            _mainActivityViewEnum = MainActivityViewEnum.StudentEntrolledExams;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return _mainActivityViewModel.setMainActivityViewEnumAndRequestData(_mainActivityViewEnum);
    }

    @Override
    protected void onResume() {
        super.onResume();

        _mainActivityViewModel.setMainActivityViewEnumAndRequestData(_mainActivityViewEnum);
    }
}
