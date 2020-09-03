package org.alfredo.tekmirapedia.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.alfredo.tekmirapedia.R;
import org.alfredo.tekmirapedia.helper.EnsiklopediaHelper;
import org.alfredo.tekmirapedia.helper.KamusHelper;
import org.alfredo.tekmirapedia.model.Ensiklopedia;
import org.alfredo.tekmirapedia.model.Kamus;

public class DetailKamusActivity extends AppCompatActivity {

    public static final String DATA_KAMUS = "data_kamus";
    private TextView indo, inggris, uraian;
    private Button copy;
    boolean isFavorite = false;
    private Menu menu;
    private Kamus kamus;
    private KamusHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kamus);

        indo = findViewById(R.id.tv_detail_indo_kamus);
        inggris = findViewById(R.id.tv_detail_inggris_kamus);
        uraian = findViewById(R.id.tv_detail_kamus);
        copy = findViewById(R.id.btn_copy_kamus);

        helper = KamusHelper.getInstance(getApplicationContext());

        kamus = getIntent().getParcelableExtra(DATA_KAMUS);
        setTitle(kamus.getIndo());
        indo.setText(kamus.getIndo());
        inggris.setText(kamus.getInggris());
        uraian.setText(kamus.getUraian());

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Penjelasan", uraian.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(DetailKamusActivity.this, "Berhasil Menyalin Penjelasan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fav, menu);
        this.menu = menu;
        setFavorite();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_fav) {
            if (kamus != null) {
                helper.open();
                if (isFavorite) {
                    isFavorite = false;
                    helper.deleteKamus(kamus.getIdKamus());
                    setFavorite();
                } else {
                    isFavorite = true;
                    helper.addFavoriteKamus(kamus);
                    setFavorite();
                }
                helper.close();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFavorite() {
        MenuItem favorite = menu.findItem(R.id.action_fav);
        if (isFavorite) {
            favorite.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite));
        } else {
            favorite.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (kamus != null) {
            isFavorite = helper.isFavoriteKamus(kamus.getIdKamus());
        }
        Log.d("AlreadyFavorite", String.valueOf(isFavorite));
    }
}
