package android.training.hdtask;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.training.hdtask.utils.CacheStorage;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * Created by priya on 11/5/15.
 */
public class PrimeNumFragment extends Fragment {

    private int limit;
    private ArrayList<Integer> primeList = new ArrayList<Integer>();
    private ArrayAdapter<Integer> adapter;
    private View view;
    private ListView listView;
    private EditText userEnteredValue;
    private Button getBtn;

    public PrimeNumFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new ArrayAdapter<Integer>(getActivity().getApplicationContext(), R.layout.text, primeList);
        listView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.prime_num_fragment, null);
        listView = (ListView) view.findViewById(R.id.prime_list);
        userEnteredValue = (EditText) view.findViewById(R.id.prime_num);
        getBtn = (Button) view.findViewById(R.id.getvalues);
        if (savedInstanceState != null) {
            limit = savedInstanceState.getInt("Limit");
            primeList = savedInstanceState.getIntegerArrayList("PrimeList");
        }
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                String value = userEnteredValue.getText().toString();
                if (!value.isEmpty()) {
                    limit = Integer.parseInt(value);
                    primeList.clear();
                    if (CacheStorage.readObject(getActivity().getApplicationContext(), limit) != null) {
                        primeList.addAll((ArrayList<Integer>) CacheStorage.readObject(getActivity().getApplicationContext(), limit));
                    }else {
                        primeList.addAll(getAllPrimeNumbers(Integer.parseInt(value)));
                    }
                } else {
                    Toast.makeText(getActivity(), "Please enter value", Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Limit", limit);
        outState.putIntegerArrayList("PrimeList", primeList);
    }

    private ArrayList<Integer> getAllPrimeNumbers(int limit) {
        ArrayList<Integer> primeList = new ArrayList<Integer>();
        boolean[] isPrime = new boolean[limit + 1];
        for (int i = 2; i < limit; i++) {
            isPrime[i] = true;
        }
        for (int i = 2; i < limit; i++) {
            if (isPrime[i]) {
                primeList.add(i);
                for (int j = i; j * i <= limit; j++) {
                    isPrime[i * j] = false;
                }
            }
        }
        if (primeList != null && primeList.size() > 0) {
            try {
                CacheStorage.writeObject(getActivity().getApplicationContext(), limit, primeList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return primeList;
    }
}
