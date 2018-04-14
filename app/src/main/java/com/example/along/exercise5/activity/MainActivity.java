package com.example.along.exercise5.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.along.exercise5.AddItem;
import com.example.along.exercise5.DeleteItem;
import com.example.along.exercise5.DoneItem;
import com.example.along.exercise5.EditItem;
import com.example.along.exercise5.R;
import com.example.along.exercise5.adapter.MyFragmentPagerAdapter;
import com.example.along.exercise5.fragment.AddItemFragment;
import com.example.along.exercise5.model.Item;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements AddItem, EditItem, DeleteItem, DoneItem{
    private Realm realm;
    private Button bt_Add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        realm = Realm.getDefaultInstance();

        ViewPager viewPager = findViewById(R.id.viewpager);
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        final TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        bt_Add = findViewById(R.id.bt_Add);
        bt_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddItemFragment addItemFragment = new AddItemFragment();
                addItemFragment.show(getFragmentManager(),"AddDialog");
            }
        });
    }

    @Override
    public void AddToDo(final int id, final String name, final String dueTo, final int priority) {
        if (!isStringNullOrWhiteSpace(name)) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Item item = realm.createObject(Item.class);
                    item.setId(id);
                    item.setName(name);
                    item.setDate(dueTo);
                    item.setPriority(priority);
                }
            });
        } else {
            Toast.makeText(this,"Can not be empty",Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isStringNullOrWhiteSpace(String value) {
        if (value == null) {
            return true;
        }
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isWhitespace(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void EditToDo(final int id, final String name, final String dueTo, final int priority) {
        if (!isStringNullOrWhiteSpace(name)) {
            final Item item = realm.where(Item.class).equalTo("id",id).findFirst();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    item.setId(id);
                    item.setName(name);
                    item.setDate(dueTo);
                    item.setPriority(priority);
                    item.setDone(false);
                }
            });
        } else {
            Toast.makeText(this,"Can not be empty",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void DeleteToDo(int id) {
        final Item item = realm.where(Item.class).equalTo("id",id).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                item.deleteFromRealm();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void DoneToDo(int id) {
        final Item item = realm.where(Item.class).equalTo("id",id).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                item.setDone(true);
            }
        });
    }
}
